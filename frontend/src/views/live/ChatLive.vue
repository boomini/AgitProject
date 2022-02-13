<template>
    <div class="chat-panel">
    <div class="chat-box p-2 d-flex flex-column">
      <div class="header text-left d-flex justify-content-center align-items-center">
        <span class="title">
          채팅
        </span>
        <!-- <button
          class="btn close-btn m-3"
          @click="toggleChatPanel"
        >
          <i class="fas fa-times"></i>
        </button> -->
      </div>
      <!-- 채팅 내역 -->
      <div
        id="chat-area"
        :height="state.form.chatHeight"
      >
        <div
          class="mt-2 mx-2 text-left message"
          v-for="(message, i) of state.messages"
          :key="i"
        >
          <div class="message-title">
            <span class="mr-2 message-header">{{ message.sender }} :</span>
            <span class="m-3 message-header">{{ message.time }}</span>
          </div>
          <div>
            {{ message.message }}
          </div>
        </div>
      </div>
      <div class="footer d-flex mt-auto">
        <div class="px-2 py-0">
          <input
            class="text-box"
            v-model="state.form.message"
            @keyup.enter="clickSendMessage"
          >
        </div>
        <div class="col-2 p-0">
          <button
            class="send-btn"
            @click="clickSendMessage"
          >
            <i class="fas fa-paper-plane color-g"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed, watch } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { OpenVidu } from 'openvidu-browser'

export default {
  name: "ChatLive",

  setup(props, { emit }){
  const store = useStore()
  const router = useRouter()

  const state = reactive({
    form:{
      message: "",
      chatHeight: "30vh",
    },
    session: props.session,
    messages: computed(() => store.getters['root/getMessages']),
  })

  // watch(props.messages, setTimeout(() => {
  //   var chatDiv = document.getElementById("chat-area");
  //   chatDiv.scrollTo({
  //     top: chatDiv.scrollHeight - chatDiv.clientHeight,
  //     behavior: 'smooth'
  //   });
  // }, 50)
  // )

  const clickSendMessage = function (){
    if (state.form.message.trim()){
      emit("sendMessage", state.form.message)
      state.form.message = ""
     }
    }

    return {state, clickSendMessage}

  }
}
</script>
<style scoped>
.chat-box {
  width: 45vh;
  height: 83vh;
  border-radius: 15px;
  border-width: 0.3vh;
  border-style: solid;
  border-color: rgb(55, 120, 81);
  background-color: rgb(85, 174, 121);
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.25);
}
.color-g{
  color: #323f45;
}

.header {
  position: relative;
  background-color: rgb(70, 148, 101);
}

.close-btn {
  position: absolute;
  color: white;
  top: 3px;
  right: 10px;
}

.text-box {
  background-color: #7d7d7d;
  width: 35vh;
  border-radius: 12px;
  color: black;
  padding-left: 10px;
  height: 5svh;
}

.text-box:focus {
  outline: none;
}

.title {
  padding-left: 5%;
  font-family: 'Jua' !important;
  font-size: 1.0rem !important;
  color: black;
}

.header {
  width: 100%;
  border-radius: 20px;
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.45);
  height: 4vh;
}
.message-title {

  font-size: 0.8rem;
}

.message-header {
  font-family: 'Nanum Gothic', sans-serif;
}

.message {
  color: black;
}

.send-btn {
  color: white;
}

#chat-area {
  overflow-y: auto;
}

#chat-area::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

#chat-area::-webkit-scrollbar-track {
  background: #37474F;
}

#chat-area::-webkit-scrollbar-corner {
  background: #37474F;
}

#chat-area::-webkit-scrollbar-thumb {
  background:  #b0a2c8;
}

#chat-area::-webkit-scrollbar-button {
  background-color: #37474F;
  height: 0;
}
</style>
