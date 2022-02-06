<template>
  <div>
    {{ $route.params.conferenceId + '번 방 상세 보기 페이지' }}

  </div>
  <div id="main-container" class="container">
		<div id="join" v-if="!state.session">
			<div id="img-div"><img src="resources/images/openvidu_grey_bg_transp_cropped.png" /></div>
			<div id="join-dialog" class="jumbotron vertical-center">
				<h1>Join a video session</h1>
				<div class="form-group">
					<p>
						<label>Participant</label>
						<input v-model="state.myUserName" class="form-control" type="text" required>
					</p>
					<p>
						<label>Session</label>
						<input v-model="state.mySessionId" class="form-control" type="text" required>
					</p>
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="joinSession()">Join!</button>
					</p>
				</div>
			</div>
		</div>

		<div id="session" class="d-flex-row justify-content-between" v-if="state.session">
			<div id="session-header">
				<h1 id="session-title">{{ state.mySessionId }}</h1>
				<input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="Leave session">
			</div>
      <div class="d-flex">
        <div>
          <div id="main-video" class="col-md-6">
            <user-video :stream-manager="state.mainStreamManager"/>
          </div>
          <div id="video-container" class="col-md-6">
            <user-video :stream-manager="state.publisher" @click="updateMainVideoStreamManager(state.publisher)"/>
            <user-video v-for="sub in state.subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click="updateMainVideoStreamManager(sub)"/>
          </div>
        </div>
        <div>
          <chat-live/>
        </div>
      </div>
		</div>
	</div>
</template>
<style>

</style>
<script>
import { reactive, onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import { OpenVidu } from 'openvidu-browser'
import UserVideo from '@/views/live/UserVideo.vue'
import ChatLive from '@/views/live/ChatLive.vue'
import axios from 'axios'
import moment from 'moment';

axios.defaults.headers.post['Content-Type'] = 'application/json';

export default {
  name: 'conference-detail',

  components: {
    UserVideo,
    ChatLive,
  },

  setup () {
    const route = useRoute()
    const store = useStore()
    const OPENVIDU_SERVER_URL = "https://i6a403.p.ssafy.io:5443"
    const OPENVIDU_SERVER_SECRET = "MY_SECRET"
    const instance = axios.create({
      baseURL: OPENVIDU_SERVER_URL,
      headers: {
        'Content-Type': 'application/json'
      }
    })

    const state = reactive({
      conferenceId: '',
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
			subscribers: [],
      mySessionId: 'SessionA',
      myUserName: 'Participant',
    })

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.conferenceId = route.params.conferenceId
      store.commit('root/setMenuActiveMenuName', 'home')
    })

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.conferenceId = ''
    })

    const joinSession = function () {
      state.OV = new OpenVidu()

      state.session = state.OV.initSession()

      state.session.on('streamCreated', ({ stream }) => {
        const subscriber = state.session.subscribe(stream)
        state.subscribers.push(subscriber)
      })

      state.session.on('streamDestroyed', ({ stream }) => {
				const index = state.subscribers.indexOf(stream.streamManager, 0)
				if (index >= 0) {
					state.subscribers.splice(index, 1)
				}
			})

      state.session.on('exception', ({ exception }) => {
				console.warn(exception)
			})

      state.session.on('signal:chat', (event) => {
        let eventData = JSON.parse(event.data);
        let data = new Object()
        let time = new Date()
        data.message = eventData.content;
        if (state.currentMode === 'anonymous') {
          data.sender = eventData.secretName;
        } else {
          data.sender = event.from.data.slice(15,-2);
        }
        data.time = moment(time).format('HH:mm')
        store.commit('setMessages', data)
      })

      getToken(state.mySessionId).then(token => {
				state.session.connect(token, { clientData: state.myUserName })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = state.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
							resolution: '640x480',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
						});

						state.mainStreamManager = publisher;
						state.publisher = publisher;

						// --- Publish your stream ---

						state.session.publish(state.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

			window.addEventListener('beforeunload', leaveSession)
    }

    const leaveSession = function () {
      if (state.session) state.session.disconnect();

			state.session = undefined;
			state.mainStreamManager = undefined;
			state.publisher = undefined;
			state.subscribers = [];
			state.OV = undefined;

			window.removeEventListener('beforeunload', leaveSession);
    }

    const updateMainVideoStreamManager = function (stream) {
			if (state.mainStreamManager === stream) return;
			state.mainStreamManager = stream;
		}

    const getToken = function (mySessionId) {
			return createSession(mySessionId).then(sessionId => createToken(sessionId));
		}

    const createSession = function (sessionId) {
			return new Promise((resolve, reject) => {
				instance
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		}

    const createToken = function (sessionId) {
			return new Promise((resolve, reject) => {
				instance
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		}

    return { state, OPENVIDU_SERVER_URL, OPENVIDU_SERVER_SECRET, instance, joinSession, leaveSession, updateMainVideoStreamManager,
          getToken, createSession, createToken }
  }
}
</script>
