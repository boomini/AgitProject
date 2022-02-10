<template>
    <div id="chat-container">
      <div class="d-flex justify-content-between my-3">
        {{ $route.params.conferenceId + '번 방 상세 보기 페이지' }}
        <button class="mx-4" @click="closeSession()">X</button>
      </div>
      <div id="join" v-if="!state.session">
        <div id="img-div"><img src="resources/images/openvidu_grey_bg_transp_cropped.png" /></div>
        <div id="join-dialog" class="jumbotron vertical-center d-flex-column offset-3 col-6">
          <h1 class="text-center">Join a video session!</h1>
            <!-- <p>
              <label>이름 설정</label>
              <input v-model="state.myUserName" class="form-control" type="text" required>
            </p>
            <p>
              <label>세션 이름</label>
              <input v-model="state.mySessionId" class="form-control" type="text" required>
            </p> -->
            <h2 class="text-center my-5">현재 들어가려는 방: {{ state.teamName }}</h2>
            <p class="text-center">
              <button class="btn btn-lg btn-success" @click="joinSession()">Join!</button>
            </p>
        </div>
      </div>

      <div id="session" v-if="state.session">
        <div id="session-header">
          <h1 id="session-title">{{ state.mySessionId }}</h1>
          <input class="btn btn-large btn-danger my-3" type="button" id="buttonLeaveSession" @click="closeSession()" value="Leave session">
        </div>
          <!-- 프레젠테이션 용 -->
          <!-- <div id="main-video" class="col-3 mx-3">
            <user-video :stream-manager="state.mainStreamManager"/>
          </div> -->
        <div class="d-flex">
          <div>
            <user-video :stream-manager="state.publisher" @click="updateMainVideoStreamManager(state.publisher)"/>
          </div>
          <div id="video-container d-flex flex-wrap">
            <user-video v-for="sub in state.subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click="updateMainVideoStreamManager(sub)"/>
          </div>
          <div class="offset-3 col-3" id="chat">
            <chat-live :session="state.session" @sendMessage="sendMessage"/>
          </div>
        </div>
      </div>
    </div>
</template>
<style scoped>
  #chat-container {
    position: absolute;
    top: -90px;
    left: -250px;
    margin-left: 0;
    margin-top: 0;
    z-index: 10;
    background-color: white;
    width: 125%;
    height: 150vh;
    background-size:cover;
  }

  #video-container video {
    position: relative;
    display: inline;
    float: left;
    width: 60%;
    cursor: pointer;
  }

  #video-container p {
    display: inline-block;
    background: #f8f8f8;
    padding-left: 5px;
    padding-right: 5px;
    color: #777777;
    font-weight: bold;
    border-bottom-right-radius: 4px;
  }

  video {
    width: 100%;
    height: auto;
  }


  #main-video p {
    display: inline-block;
    background: #f8f8f8;
    font-size: 22px;
    color: #777777;
    font-weight: bold;
    border-bottom-right-radius: 4px;
  }

  #session img {
    width: 100%;
    height: auto;
    display: inline-block;
    object-fit: contain;
    vertical-align: baseline;
  }

  #session #video-container img {
    position: relative;
    float: left;
    width: 50%;
    cursor: pointer;
    object-fit: cover;
    height: 180px;
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
    // 이 팀에 속한 유저인지
    // 아니면 이제 그냥 alert
    const state = reactive({
      conferenceId: '',
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
			subscribers: [],
      mySessionId: 'Session',
      myUserName: 'Person1',
      isLogin: computed(() => store.getters['root/getJWTToken']),
      roomId: computed(() => route.params.conferenceId),
      teamId: null,
    })
    checkUserState()
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

    const checkUserState = function(){
      let url = window.location.href;
      state.teamId = url.split('/').reverse()[0];
      console.log("hihi")
      console.log(state.teamId)
      console.log(state.isLogin)
      if(state.isLogin==null){
        setTimeout(() => {
                swal({
                  title: "로그인 필요한 페이지",
                  text: "로그인 후 이용해주세요.",
                  icon: "success",
                  button: "확인",
                });
              }, 100)
       router.push({
        name: 'intro',
        })
      }
      else{
        console.log(state.isLogin);
        store.dispatch('root/checkTeamMember', {teamId: state.teamId, token:state.isLogin} )
      .then(function(result){
        console.log(result.data);
        console.log(result);
      }).catch(function(err){
        console.log(err.response)
          //접근불가한 User
        router.push({
          name: 'Error'
        })
        })
      }
    }

    const joinSession = function () {
      state.OV = new OpenVidu()
      getTeamInfo()
      takeProfile()
      console.log(state.userNickName)
      // console.log(state.mySessionId)
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
        state.myUserName = res.data.nickName
      })
      .catch(err => {
        console.log(err)
      })
    }
    const getTeamInfo = function () {
      store.dispatch('root/getTeamInfoDetail', route.params.conferenceId)
      .then(res => {
        state.mySessionId = `${res.data.teamName}팀의 방`
      })
      .catch(err => {
        console.log(err)
      })
    }

    const getTeamName = function () {
      store.dispatch('root/getTeamInfoDetail', route.params.conferenceId)
      .then(res => {
        state.teamName = `${res.data.teamName}팀의 방`
      })
      .catch(err => {
        console.log(err)
      })
    }


    const setScrollTop = function() {
      window.scrollTo(0, 0)
    }

    getTeamName()
    setScrollTop()

    return { state, OPENVIDU_SERVER_URL, OPENVIDU_SERVER_SECRET, instance, joinSession, leaveSession, updateMainVideoStreamManager,
          getToken, createSession, createToken, sendMessage, closeSession, getTeamInfo, setScrollTop, takeProfile, getTeamName, checkUserState}
  }
}
</script>
