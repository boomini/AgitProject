<template>
    <div id="chat-container">
        <!-- <div id="img-div"><img src="resources/images/openvidu_grey_bg_transp_cropped.png" /></div> -->
          <!-- <div class="form-group">
            <p>
              <label>이름 설정</label>
              <input v-model="state.myUserName" class="form-control" type="text" required>
            </p>
            <p>
              <label>세션 이름</label>
              <input v-model="state.mySessionId" class="form-control" type="text" required>
            </p>
          </div> -->
      <div v-if="!state.session">
        <div class="d-flex flex-column align-items-center my-5">
          <h1>회의실 참가하기!</h1>
          <h3 class="my-3">현재 접속하려는 방: {{ state.teamName}}의 방</h3>
          <div>
            <button id="join-btn" class="btn btn-lg btn-success my-3" @click="joinSession()">Join!</button>
          </div>
        </div>
      </div>

      <div class="d-flex-row justify-content-between my-3" v-if="state.session">
        <div class="d-flex">
          <h1 class="offset-4">{{ state.teamName }}의 방입니다.</h1>
          <!-- 비디오 토글 버튼 -->
          <div class="d-flex offset-2">
            <div v-if="state.publisher.stream.videoActive">
              <button id="video-btn" class="btn btn-danger my-3 mx-3" @click="changeVideoState()">OFF</button>
            </div>
            <div v-else>
              <button id="video-btn" class="btn btn-success my-3 mx-3" @click="changeVideoState()">ON</button>
            </div>
            <!-- 오디오 토글 버튼 -->
            <div v-if="state.publisher.stream.audioActive">
              <button id="video-btn" class="btn btn-danger my-3 mx-3" @click="changeAudioState()">Mute</button>
            </div>
            <div v-else>
              <button id="video-btn" class="btn btn-success my-3 mx-3" @click="changeAudioState()">ON</button>
            </div>
            <input class="btn btn-large btn-danger my-3 mx-4" type="button" id="close-btn" @click="closeSession()" value="방 나가기">
          </div>
        </div>
        <div class="d-flex justify-content-between">
            <!-- <div id="main-video" class="col-3 mx-3">
              <user-video :stream-manager="state.mainStreamManager"/>
            </div> -->
          <div class="d-flex flex-wrap mx-3">
            <user-video :stream-manager="state.publisher"/>
            <user-video v-for="sub in state.subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
          </div>
          <div id="chat-box">
            <chat-live :session="state.session" @sendMessage="sendMessage"/>
          </div>
        </div>
      </div>
    </div>
</template>
<style scoped>
  #join-btn{
    width: 20vh;
  }
  #video-btn{
    width: 10vh;
    height: 7vh;
  }
  #chat-container {
    position: absolute;
    top: -90px;
    left: -250px;
    margin-left: 0;
    margin-top: 0;
    z-index: 10;
    background-color: white;
    width: 125%;
    height: 100vh;
    background-size:cover;
  }
  #chat-box{
    margin-right: 5vh;
  }

  #close-btn{
    width: 20vh;
    height: 7vh;
  }

</style>
<script>
import { reactive, onMounted, onUnmounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'
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
    const router = useRouter()
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
      myUserName: 'Person1',
      roomId: computed(() => route.params.conferenceId),
      teamName: '',
      userName: '',
      videoStatus: true,
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

    // 세션 Exit 버튼 눌렀을 때
    const closeSession = function (){
      leaveSession()
      router.push({
        name: 'room-board',
        params: {
          roomId: state.roomId
        }
      })

    }

    const joinSession = function () {
      state.OV = new OpenVidu()
      state.session = state.OV.initSession()
      state.session.on('streamCreated', ({ stream }) => {
        console.log(stream)
        const subscriber = state.session.subscribe(stream)
        state.subscribers.push(subscriber)
        // console.log("===============================")
        // console.log(state.subscribers)
      })

      state.session.on('streamDestroyed', ({ stream }) => {
				const index = state.subscribers.indexOf(stream.streamManager, 0)
				if (index >= 0) {
					state.subscribers.splice(index, 1)
				}
			})
      state.myUserName = state.userName
      state.mySessionId = state.roomId

      state.session.on('exception', ({ exception }) => {
				console.warn(exception)
			})


      // 채팅
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
        store.commit('root/setMessages', data)
      })

      // 음성 인지
      // state.publisher.on('publisherStartSpeaking', (event) => {
      //       console.log('User ' + event.connection.connectionId + ' start speaking');
      // });



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
      store.commit('root/clearMessages')
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
    const sendMessage = function (message){
      var messageData = {
        content: message,
        secretName: store.getters['root/getSecretName'],
      }
        state.session.signal({
          type: 'chat',
          data: JSON.stringify(messageData),
          to: [],
        })
    }

    const takeProfile = function () {
      const token = store.getters['root/getJWTToken']
      store.dispatch('root/getProfile', token)
      .then(res => {
        state.userName = res.data.nickName
      })
      .catch(err => {
        console.log(err)
      })
    }
    const getTeamInfo = function () {
      store.dispatch('root/getTeamInfoDetail', route.params.conferenceId)
      .then(res => {
        state.teamName = res.data.teamName
      })
      .catch(err => {
        console.log(err)
      })
    }
    // 비디오 토글
    const changeVideoState = function (){
      // state.publisher.stream.videoActive = !state.publisher.stream.videoActive
      console.log(state.publisher)
      state.publisher.publishVideo(state.publisher.stream.videoActive)
    }

    // 오디오 토글
    const changeAudioState = function (){
      state.publisher.stream.audioActive = !state.publisher.stream.audioActive
      state.publisher.publishAudio(state.publisher.stream.audioActive)

    }
    getTeamInfo()
    takeProfile()
    return { state, OPENVIDU_SERVER_URL, OPENVIDU_SERVER_SECRET, instance, joinSession, leaveSession, updateMainVideoStreamManager,
          getToken, createSession, createToken, sendMessage, closeSession, takeProfile, getTeamInfo, changeVideoState, changeAudioState }
  }
}
</script>
