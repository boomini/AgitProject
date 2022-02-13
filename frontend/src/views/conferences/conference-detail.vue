<template>
  <div id="chat-container">
    <div v-if="!state.session">
      <div class="d-flex flex-column justify-content-center align-items-center offset-3 join-room">
        <h1>회의실 참가하기!</h1>
        <h3 class="my-3">현재 접속하려는 방: {{ state.teamName }}의 방</h3>
        <div>
          <el-button type="primary" @click="joinSession()" id="join-btn">
            <i class="fa-solid fa-right-to-bracket"></i>
          </el-button>
        </div>
      </div>
    </div>

    <div class="d-flex-row justify-content-between my-3" v-if="state.session">
      <div class="d-flex offset-1">
        <!-- 비디오 토글 버튼 -->
          <div v-if="state.publisher.stream.videoActive">
            <i class="fa-solid fa-video-slash toggle-icon-off mx-5 text-center" @click="changeVideoState()"></i>
          </div>
          <div v-else>
            <i class="fa-solid fa-video toggle-icon-on mx-5 text-center" style="padding-left: 0.95vh" @click="changeVideoState()"></i>
          </div>
          <!-- 오디오 토글 버튼 -->
          <div v-if="state.publisher.stream.audioActive">
           <i class="fa-solid fa-microphone-slash toggle-icon-off mx-5 text-center" @click="changeAudioState()"></i>
          </div>
          <div v-else>
            <i class="fa-solid fa-microphone toggle-icon-on mx-5 text-center" style="padding-left: 0.9vh" @click="changeAudioState()"></i>
          </div>
        <div class="d-flex justify-content-between align-items-center offset-1" id="header">
          <h1 class="text-center">Room: {{ state.teamName }}</h1>
          <h2 id="close-btn" class="d-flex justify-content-center align-items-center" @click="closeSession()">X</h2>
        </div>
      </div>
      <div class="d-flex justify-content-between">
        <!-- <div id="main-video" class="col-3 mx-3">
              <user-video :stream-manager="state.mainStreamManager"/>
            </div> -->
        <div class="d-flex flex-wrap mx-3">
          <user-video :stream-manager="state.publisher" :border-color="state.BorderColor"/>
          <user-video
            v-for="sub in state.subscribers"
            :key="sub.stream.connection.connectionId"
            :stream-manager="sub"
            :border-color="sub.element"
          />
        </div>
        <div id="chat-box">
          <chat-live :session="state.session" @sendMessage="sendMessage" />
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
.join-room{
  margin-top: 20vh;
  border-color: black;
  border-style: solid;
  border-radius: 2vh;
  width: 100vh;
  height: 40vh;
  background-color: rgb(87, 193, 129);
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.2);
}

#join-btn {
  width: 15vh;
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.25);
}

#join-btn i{
  transform: scale(1.2);
}

#video-btn {
  width: 10vh;
  height: 7vh;
}
#chat-container {
  position: absolute;
  top: -90px;
  left: -100px;
  margin-left: 0;
  margin-top: 0;
  z-index: 10;
  background-color: #36393f;
  width: 212.85vh;
  height: 100vh;
  background-size: cover;
}
#chat-box {
  margin-right: 5vh;
}

#close-btn {
  font-size: 5vh;
  width: 10vh;
  height: 7vh;
  background-color: #b53638;
  border-style: solid;
  border-color: black;
  border-radius: 3vh;
  border-width: 0.6vh;
  cursor: pointer;
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.2);
}
.toggle-icon-off{
  transform: scale(2) translate(-80%, 60%);
  border-color: black;
  background-color: #b53638;
  border-style: solid;
  border-width: 2px;
  padding: 0.8vh;
  width: 5vh;
  border-radius: 100px;
  cursor: pointer;
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.2);
}
.toggle-icon-on{
  transform: scale(2) translate(-80%, 60%);
  border-color: black;
  background-color: #3d48c2;
  border-style: solid;
  border-width: 2px;
  padding: 0.8vh;
  width: 5vh;
  border-radius: 100px;
  cursor: pointer;
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.2);
}
#header{
  width: 132vh;
  margin-bottom: 2vh;
}
#header h1{
  border-color: black;
  border-style: solid;
  padding: 1vh;
  border-radius: 30px;
  background-color: rgb(85, 174, 121);
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.2);
  width: 60vh;
}
</style>
<script>
import { reactive, onMounted, onUnmounted, computed } from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/views/live/UserVideo.vue";
import ChatLive from "@/views/live/ChatLive.vue";
import axios from "axios";
import moment from "moment";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "conference-detail",

  components: {
    UserVideo,
    ChatLive,
  },

  setup() {
    const route = useRoute();
    const router = useRouter();
    const store = useStore();
    const OPENVIDU_SERVER_URL = "https://i6a403.p.ssafy.io:5443";
    const OPENVIDU_SERVER_SECRET = "MY_SECRET";
    const instance = axios.create({
      baseURL: OPENVIDU_SERVER_URL,
      headers: {
        "Content-Type": "application/json",
      },
    });

    const state = reactive({
      conferenceId: "",
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      mySessionId: "SessionA",
      myUserName: "Person1",
      roomId: computed(() => route.params.conferenceId),
      teamName: "",
      userName: "",
      videoStatus: true,
      audioStatus: true,
      BorderColor: 'black',
    });
    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.conferenceId = route.params.conferenceId;
      store.commit("root/setMenuActiveMenuName", "home");
    });

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.conferenceId = "";
    });

    // 세션 Exit 버튼 눌렀을 때
    const closeSession = function () {
      leaveSession();
      router.push({
        name: "room-board",
        params: {
          roomId: state.roomId,
        },
      });
    };

    const joinSession = function () {
      state.OV = new OpenVidu();
      state.session = state.OV.initSession();
      state.session.on("streamCreated", ({ stream }) => {
        console.log(stream);
        const subscriber = state.session.subscribe(stream);
        subscriber.element = "black"
        state.subscribers.push(subscriber);
        // console.log("===============================")
        // console.log(state.subscribers)
      });

      state.session.on("streamDestroyed", ({ stream }) => {
        const index = state.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          state.subscribers.splice(index, 1);
        }
      });
      state.myUserName = state.userName;
      state.mySessionId = state.roomId;

      state.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // 채팅
      state.session.on("signal:chat", (event) => {
        let eventData = JSON.parse(event.data);
        let data = new Object();
        let time = new Date();
        data.message = eventData.content;
        if (state.currentMode === "anonymous") {
          data.sender = eventData.secretName;
        } else {
          data.sender = event.from.data.slice(15, -2);
        }
        data.time = moment(time).format("HH:mm");
        store.commit("root/setMessages", data);
      });

      // 음성 인지
      state.session.on('publisherStartSpeaking', (event) => {
          // subscribers 들의 음성인지는 추가로 알아봐야 함
          const publisherId = state.publisher.stream.streamId
          // console.log(publisherId)
          // publisher의 event면 publisher만 변경
          const str_len = event.connection.connectionId.length
          if (publisherId.slice(-str_len) === event.connection.connectionId){
            state.BorderColor = "blue"
          }
          state.subscribers.forEach((subscriber) => {

            // subscirber의 event면 subscriber 변경
            if (subscriber.stream.streamId.slice(-str_len) === event.connection.connectionId){
                subscriber.element = "blue"
            }
          })
      });

      state.session.on('publisherStopSpeaking', (event) => {
          const publisherId = state.publisher.stream.streamId
          const str_len = event.connection.connectionId.length
          if (publisherId.slice(-str_len) === event.connection.connectionId){
            state.BorderColor = "black"
          }
          state.subscribers.forEach((subscriber) => {

            // publisher의 event면 publisher만 변경
            // subscirber의 event면 subscriber 변경
           if (subscriber.stream.streamId.slice(-str_len) === event.connection.connectionId){
                subscriber.element = "black"
            }
          })
      });

      getToken(state.mySessionId).then((token) => {
        state.session
          .connect(token, { clientData: state.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = state.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            state.mainStreamManager = publisher;
            state.publisher = publisher;

            // --- Publish your stream ---

            state.session.publish(state.publisher);
            state.videoStatus = state.publisher.stream.videoActive;
            state.audioStatus = state.publisher.stream.audioActive;
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", leaveSession);
    };

    const leaveSession = function () {
      if (state.session) state.session.disconnect();

      state.session = undefined;
      state.mainStreamManager = undefined;
      state.publisher = undefined;
      state.subscribers = [];
      state.OV = undefined;
      store.commit("root/clearMessages");
      window.removeEventListener("beforeunload", leaveSession);
    };

    const updateMainVideoStreamManager = function (stream) {
      if (state.mainStreamManager === stream) return;
      state.mainStreamManager = stream;
    };

    const getToken = function (mySessionId) {
      return createSession(mySessionId).then((sessionId) =>
        createToken(sessionId)
      );
    };

    const createSession = function (sessionId) {
      return new Promise((resolve, reject) => {
        instance
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    };

    const createToken = function (sessionId) {
      return new Promise((resolve, reject) => {
        instance
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    };
    const sendMessage = function (message) {
      var messageData = {
        content: message,
        secretName: store.getters["root/getSecretName"],
      };
      state.session.signal({
        type: "chat",
        data: JSON.stringify(messageData),
        to: [],
      });
    };

    const takeProfile = function () {
      const token = store.getters["root/getJWTToken"];
      store
        .dispatch("root/getProfile", token)
        .then((res) => {
          state.userName = res.data.nickName;
        })
        .catch((err) => {
          console.log(err);
        });
    };
    const getTeamInfo = function () {
      store
        .dispatch("root/getTeamInfoDetail", route.params.conferenceId)
        .then((res) => {
          state.teamName = res.data.teamName;
        })
        .catch((err) => {
          console.log(err);
        });
    };
    // 비디오 토글
    const changeVideoState = function () {
      state.videoStatus = !state.videoStatus;
      state.publisher.publishVideo(state.videoStatus);
    };

    // 오디오 토글
    const changeAudioState = function () {
      state.audioStatus = !state.audioStatus;
      state.publisher.publishAudio(state.audioStatus);
    };
    getTeamInfo();
    takeProfile();
    return {
      state,
      OPENVIDU_SERVER_URL,
      OPENVIDU_SERVER_SECRET,
      instance,
      joinSession,
      leaveSession,
      updateMainVideoStreamManager,
      getToken,
      createSession,
      createToken,
      sendMessage,
      closeSession,
      takeProfile,
      getTeamInfo,
      changeVideoState,
      changeAudioState,
    };
  },
};
</script>

