<template>
  <div style="dispaly: flex; justify-content: center; align-items:center; width: 100%; margin-bottom: 20px">
    <div v-if = "state.infos.length >= 3">
      <el-carousel :interval="4000" type="card" height="200px">
        <el-carousel-item v-for="info in state.infos" :key="info.startDate">
          <h3>팀 이름: {{ info.teamName }} <br>
              시작 날짜: {{ info.startDate }} <br>
              종료 날짜: {{ info.endDate }} <br>
              D-day: {{ info.dday }}일 <br>
              약속 제목: {{ info.eventTitle }} <br>
              약속 내용: {{ info.eventContent }}
          </h3>
        </el-carousel-item>
      </el-carousel>
    </div>
    <div v-else-if = "state.infos.length === 2">
      <el-carousel height="200px" direction="vertical" :autoplay="false">
        <el-carousel-item v-for="info in state.infos" :key="info.startDate">
        <h3>팀 이름: {{ info.teamName }} <br>
            시작 날짜: {{ info.startDate }} <br>
            종료 날짜: {{ info.endDate }} <br>
            D-day: {{ info.dday }}일 <br>
            약속 제목: {{ info.eventTitle }} <br>
            약속 내용: {{ info.eventContent }}
        </h3>
      </el-carousel-item>
    </el-carousel>
    </div>
    <div v-else-if = "state.infos.length === 1">
      <el-carousel height="200px" direction="vertical" :autoplay="false">
        <el-carousel-item v-for="info in state.infos" :key="info.startDate">
        <h3>팀 이름: {{ info.teamName }} <br>
            시작 날짜: {{ info.startDate }} <br>
            종료 날짜: {{ info.endDate }} <br>
            D-day: {{ info.dday }}일 <br>
            약속 제목: {{ info.eventTitle }} <br>
            약속 내용: {{ info.eventContent }}
        </h3>
      </el-carousel-item>
    </el-carousel>
    </div>
    <!-- 확인필요! -->
    <div v-else-if = "state.infos.length === 0">
      <el-empty :image-size="200"></el-empty>
    </div>
  </div>
  <div style="width:100%;">
    <div style="width: 83vw;" class="d-flex flex-row">
      <div id="scape">
        <div class="landing">
          <div class="mountain-scene">
            <div id="clouds">
              <div class="cloud"></div>
              <div class="cloud"></div>
              <div class="cloud"></div>
              <div class="cloud"></div>

            </div>
            <!-- background mountains -->
            <div class="small-mountains">
              <div class="tri"></div>
              <div class="tri"></div>
              <div class="tri"></div>
            </div>
            <div class="tall-mountains">
              <div class="tri2"></div>
              <div class="tri2"></div>
              <div class="tri2"></div>
            </div>
            <!-- foreground mountains -->
            <div class="tiny-mountains">
              <div class="tri3"></div>
              <div class="tri3"></div>
              <div class="tri3"></div>
              <div class="tri3"></div>
              <div class="tri3"></div>
            </div>
          </div>
          <div class="ground d-flex justify-content-evenly">
            <div>
              <el-button type="text" @click="state.beforeDialogOpen = true" style="margin-left:100px; font-size: 1.5rem; color: #13C7A3"
                >이전 약속</el-button
              >
            </div>
              <div class="page-wrapper">
                <div class="loader">
                  <div class="jelly">
                    <div class="body"></div>
                    <div class="eye"></div>
                    <div class="eye"></div>
                    <div class="mouth"></div>
                  </div>
                  <div class="shadow"></div>
                </div>
              </div>
            <div>
              <el-button type="text" @click="state.afterDialogOpen = true" style="margin-right:100px; font-size: 1.5rem; color: #13C7A3"
                >다음 약속</el-button
              >
            </div>
          </div>
        </div>
      </div>
      <div class="iphone" style="margin-left: -50px">
        <div class="header">
          <div class="order-summary">
            <div class="order-status">{{ state.profileinfo.userId }}</div>
            <div class="order-date">
            {{ state.profileinfo.nickName }} <el-button style=" min-height: 15px; padding: 10px; margin-left: 10px; font-size: 1.5rem;" @click="state.nicknameDialogOpen = true">수정하기</el-button>
            </div>
            <div class="order-day">
            {{ state.profileinfo.nickName }}의 생일: {{ state.profileinfo.birthDay }}
            </div>
          </div>
        </div>
        <div class="hero-img-container">
          <img src="https://drive.google.com/uc?id=15iXUI6DkRr5Zcp0yH5uF2U47ycr-WzUY" class="hero-img">
        </div>
      </div>
    </div>
  </div>

  <before-dialog
    :open="state.beforeDialogOpen"
    :info="state.pros.beforepro[0]"
    @closeBeforeDialog="onCloseBeforeDialog" />
  <after-dialog
    :open="state.afterDialogOpen"
    :info="state.pros.afterpro[0]"
    @closeAfterDialog="onCloseAfterDialog" />
  <nickname-dialog
    :open="state.nicknameDialogOpen"
    :info="state.profileinfo"
    @closeNicknameDialog="onCloseNicknameDialog"
    @edit-nickname="editNickname"
    />
  <birthday-dialog
  :open="state.birthdayDialogOpen"
  :info="state.profileinfo"
  @closeBirthdayDialog="onCloseBirthdayDialog"
  @create-birthday="createBirthday"
  />

</template>

<script>
import { reactive, ref} from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import BeforeDialog from './components/before-dialog.vue'
import AfterDialog from './components/after-dialog.vue'
import NicknameDialog from './components/nickname-dialog.vue'
import BirthdayDialog from './components/birthday-dialog.vue'

export default {
  name: 'schedule',
  components: {
    BeforeDialog,
    AfterDialog,
    NicknameDialog,
    BirthdayDialog,
  },

  setup() {
  const centerDialogVisible = ref(false)
  // let beforepro = ''
  // let afterpro = ''
  const store = useStore()
  const router = useRouter()
  const state = reactive({
    infos: {
        eventTitle: '',
        eventContent: '',
        teamName: '',
        startDate: '1970-01-01',
        endDate: '1970-01-01',
        dday: '1970-01-01',
      },
     profileinfo: {
        birthDay: '1970-01-01',
        name: '',
        nickName: '',
        userId: '',
        id: '',
        cdate: '1970-01-01',
        emailType: '',
        password: ''
      },
    schedulelength: 0,
    pros : {
      beforepro: [],
      afterpro: []
    },
    afterDialogOpen: false,
    beforeDialogOpen: false,
    nicknameDialogOpen : false,
    birthdayDialogOpen : false,
  })
  const beforeschedule = []
  const afterschedule = []
  let beforeday = ''
  let afterday = ''


  const takeSchdule = function () {
      const token = store.getters['root/getJWTToken']
      store.dispatch('root/getSchedule', token)
      // console.log(token)
      .then(res => {
        // console.log(res.data.length)
        // console.log(res.data)
        // console.log('하이')
        let today = new Date()
        let date = today.getDate()
        state.schedulelength = res.data.length
        state.infos = res.data.slice().reverse()
        for (var i = 0; i < state.schedulelength; i++) {
          // console.log(state.infos[i].startDate)
          // let tempday = Number(state.infos[i].startDate.slice(8,12))
          let tempday = state.infos[i].dday
          // console.log(tempday)
          if (0 >= tempday) {
            beforeschedule.push(state.infos[i])
          } else {
            afterschedule.push(state.infos[i])
          }
        }
        let beforemax = -31
        for (var b = 0; b < beforeschedule.length; b++) {
          if (beforeschedule[b].dday >= beforemax) {
            beforemax = beforeschedule[b].dday
            beforeday = b
          }
        }
        console.log(beforeday)
        state.pros.beforepro.push(beforeschedule[beforeday])
        // console.log(beforepro)
        let aftermin = 32
        for (var a = 0; a < afterschedule.length; a++) {
          if (afterschedule[a].dday <= aftermin) {
            // console.log(a)
            aftermin = afterschedule[a].dday
            afterday = a
          }
        }
        console.log(afterday)
        state.pros.afterpro.push(afterschedule[afterday])
        // console.log(afterpro)
        // state.infos = computed(() => { return _.orderBy(state.infos, 'dday')} )
        // console.log(state.infos[2].teamName)
      })
      .catch(err => {
        console.log(err)
      })
    }

  // let today = new Date()
  // let year = today.getFullYear()
  // let month = today.getMonth() + 1
  // let date = today.getDate()
  // let realday = `${year}-${month}-${date}`
  // console.log(realday)
  console.log('맞냐')
  console.log(beforeschedule.sort())
  console.log(afterschedule.sort())
  console.log(state.pros.beforepro)
  // console.log(beforepro)
  // console.log(afterpro)

  takeSchdule()
  // console.log(beforeday)


  // const calcdate = function () {
  //   for (var i = 0; i < state.schedulelength; i++) {
  //     console.log(i)
  //   }
  // }

  const takeProfile = function () {
      const token = store.getters['root/getJWTToken']
      store.dispatch('root/getProfile', token)
      .then(res => {
        state.profileinfo = res.data
        console.log(res)
        if (state.profileinfo.birthDay == null) {
          state.birthdayDialogOpen = true
        }
      })
      .catch(err => {
        console.log(err)
      })
    }

    const editNickname = (nickname) => {
      state.profileinfo.nickName = nickname.nickname
    }

    const createBirthday = (birthday) => {
      state.profileinfo.birthDay = birthday.birthday
    }

    // const deleteuserId = function () {
    //   const token = store.getters['root/getJWTToken']
    //   const body = {
    //     'userId': state.profileinfo.userId,
    //   }
    //   store.dispatch('root/deleteUser',{ 'body': body, 'token': token})
    //   .then(res => {
    //       setTimeout(() => {
    //             swal({
    //               title: '회원탈퇴',
    //               text: '이용해주셔서 감사합니다.',
    //               icon: 'success',
    //               button: '확인',
    //             });
    //           }, 500)

    //           console.log(res)

    //           store.commit('root/setJWTTokenReset')
    //           localStorage.removeItem('JWT')
    //           store.commit('root/setMenuActive', 0)
    //           router.push({
    //             name: 'home',
    //           })

    //           // router.go(router.currentRoute)

    //     })
    //     .catch(err => {
    //       console.log(err)
    //     })
    // }

  takeProfile()




  const onCloseNicknameDialog = function () {
    state.nicknameDialogOpen = false
  }

  const onCloseBirthdayDialog = function () {
    state.birthdayDialogOpen = false
  }


  // calcdate()
  const onCloseAfterDialog = function () {
    state.afterDialogOpen = false
  }

  const onCloseBeforeDialog = function () {
    state.beforeDialogOpen = false
  }


    return { router, takeProfile, centerDialogVisible, state, beforeschedule, afterschedule, beforeday, afterday, takeSchdule, onCloseAfterDialog, onCloseBeforeDialog, onCloseNicknameDialog, editNickname, onCloseBirthdayDialog, createBirthday }
  }
}


</script>

<style>
  /* test */
  .el-carousel__item {
  background-color: #FAFAA0;
  color: #13C7A3;
  font-size: 10px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
  z-index: -3;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #FAFAA0;
    color: #13C7A3;
  }

  .el-carousel__item:nth-child(2n + 1) {
    background-color: #FAEB78;
    color: #20B2AA;
  }

  #scape {
    width: 55vw;
    height: 100%;
    overflow-x: hidden;
    margin: 0px;
  }

  .page-wrapper {
  width: 100%;
  height: 3%;
  display: flex;
  align-items: center;
  justify-content: center;
  }

  .loader {
    position: relative;
    width: 100px;
    margin: 50px;
  }

  .loader .jelly {
    animation: jumping 1s infinite;
  }

  .loader .body {
    width: 100px;
    height: 200px;
    border-radius: 50px;
    background: linear-gradient( to bottom,#32BEBE 60%, yellow);
    animation: body 1s infinite;
  }

  .loader .mouth {
    position: absolute;
    top: 85px;
    left: 27px;
    width: 40px;
    height: 20px;
    border: 3px solid #2a333b;
    border-radius: 0 0 40px 40px;
    background-color: #363b2a;
    overflow: hidden;
    z-index: 1;
  }

  .loader .mouth:before {
    content: '';
    display: block;
    position: absolute;
    top: 10px;
    left: 0;
    width: 40px;
    height: 20px;
    border-radius: 50%;
    background-color: #13C7A3;
  }

  .loader .eye {
    position: absolute;
    top: 45px;
    width: 10px;
    height: 20px;
    border-radius: 50%;
    background-color: #2a333b;
    animation: eye 6s infinite;
  }

  .loader .eye:nth-child(even) {
    left: 30px;
  }

  .loader .eye:nth-child(odd) {
    right: 30px;
  }

  .loader .shadow {
    position: absolute;
    bottom: -10px;
    z-index: -1;
    width: 100px;
    height: 20px;
    border-radius: 50%;
    background-color: rgba(42,51,59,0.2);
    animation: shadow 1s infinite;
  }

  @-moz-keyframes jumping {
    0% {
      transform: translateY(-40px);
    }
    70% {
      transform: scale(1.02, 0.9) translateY(10px);
    }
    100% {
      transform: translateY(-40px);
    }
  }

  @-webkit-keyframes jumping {
    0% {
      transform: translateY(-40px);
    }
    70% {
      transform: scale(1.02, 0.9) translateY(10px);
    }
    100% {
      transform: translateY(-40px);
    }
  }

  @-o-keyframes jumping {
    0% {
      transform: translateY(-40px);
    }
    70% {
      transform: scale(1.02, 0.9) translateY(10px);
    }
    100% {
      transform: translateY(-40px);
    }
  }

  @keyframes jumping {
    0% {
      transform: translateY(-40px);
    }
    70% {
      transform: scale(1.02, 0.9) translateY(10px);
    }
    100% {
      transform: translateY(-40px);
    }
  }

  @-moz-keyframes body {
    0% {
      border-radius: 50px;
    }
    70% {
      border-radius: 50px 50px 40px 40px;
    }
    100% {
      border-radius: 50px;
    }
  }

  @-webkit-keyframes body {
    0% {
      border-radius: 50px;
    }
    70% {
      border-radius: 50px 50px 40px 40px;
    }
    100% {
      border-radius: 50px;
    }
  }

  @-o-keyframes body {
    0% {
      border-radius: 50px;
    }
    70% {
      border-radius: 50px 50px 40px 40px;
    }
    100% {
      border-radius: 50px;
    }
  }

  @keyframes body {
    0% {
      border-radius: 50px;
    }
    70% {
      border-radius: 50px 50px 40px 40px;
    }
    100% {
      border-radius: 50px;
    }
  }

  @-moz-keyframes eye {
    0% {
      transform: scale(1);
    }
    2% {
      transform: scale(2, 0.2);
    }
    4% {
      transform: scale(1);
    }
    6% {
      transform: scale(2, 0.2);
    }
    8% {
      transform: scale(1);
    }
  }

  @-webkit-keyframes eye {
    0% {
      transform: scale(1);
    }
    2% {
      transform: scale(2, 0.2);
    }
    4% {
      transform: scale(1);
    }
    6% {
      transform: scale(2, 0.2);
    }
    8% {
      transform: scale(1);
    }
  }

  @-o-keyframes eye {
    0% {
      transform: scale(1);
    }
    2% {
      transform: scale(2, 0.2);
    }
    4% {
      transform: scale(1);
    }
    6% {
      transform: scale(2, 0.2);
    }
    8% {
      transform: scale(1);
    }
  }

  @keyframes eye {
    0% {
      transform: scale(1);
    }
    2% {
      transform: scale(2, 0.2);
    }
    4% {
      transform: scale(1);
    }
    6% {
      transform: scale(2, 0.2);
    }
    8% {
      transform: scale(1);
    }
  }

  @-moz-keyframes shadow {
    0% {
      transform: scaleX(0.9);
    }
    70% {
      transform: scaleX(1);
    }
    100% {
      transform: scaleX(0.9);
    }
  }

  @-webkit-keyframes shadow {
    0% {
      transform: scaleX(0.9);
    }
    70% {
      transform: scaleX(1);
    }
    100% {
      transform: scaleX(0.9);
    }
  }

  @-o-keyframes shadow {
    0% {
      transform: scaleX(0.9);
    }
    70% {
      transform: scaleX(1);
    }
    100% {
      transform: scaleX(0.9);
    }
  }

  @keyframes shadow {
    0% {
      transform: scaleX(0.9);
    }
    70% {
      transform: scaleX(1);
    }
    100% {
      transform: scaleX(0.9);
    }
  }


  .landing {
  position: relative;
  margin: auto;
  display: block;
  width: 100%;
  height: 67vh;
  background: rgb(190, 231, 232);
  background: linear-gradient(
    158deg,
    rgba(190, 231, 232, 1) 0%,
    #FAFAAA 55%

  );
}
.mountain-scene {
  position: relative;
  height: 75%;
  /* border-style: solid; */
  /* overflow:hidden; */
  /* margin: 0; */
}

/* Background Mountains

Triangles made using transform rotate
width = 1.41 x height(padding-bottom) */

.tri {
  width: 30%;
  padding-bottom: 21%;
  position: relative;
  overflow: hidden;
}
.tri:before {
  content: "";
  position: absolute;

  width: 100%;
  height: 104%;
  background: rgb(254, 194, 178);
  background: linear-gradient(
    180deg,
    #369F36 0%,
    #FAFAAA 55%
  );
  border-radius: 20px;

  -webkit-transform-origin: 0 100%;
  -ms-transform-origin: 0 100%;
  transform-origin: 0 100%;

  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}

.small-mountains .tri:nth-child(1) {
  bottom: 0;
  position: absolute;
  left: -7%;
}

.small-mountains .tri:nth-child(2) {
  bottom: 0;
  position: absolute;
  left: 40%;
}

.small-mountains .tri:nth-child(3) {
  bottom: 0;
  position: absolute;
  right: -10%;
}

.tri2 {
  width: 48%;
  padding-bottom: 33%;
  position: relative;
  overflow: hidden;
}
.tri2:before {
  content: "";
  position: absolute;

  width: 100%;
  height: 104%;
  /* background: #726DA8; */
  background: rgb(254, 194, 178);
  background: linear-gradient(
    180deg,
    #FFCD28		 0%,
    #FAFAAA 55%
  );
  border-radius: 20px;

  -webkit-transform-origin: 0 100%;
  -ms-transform-origin: 0 100%;
  transform-origin: 0 100%;

  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}

.tall-mountains .tri2:nth-child(1) {
  bottom: 0;
  position: absolute;
  left: 0%;
}

.tall-mountains .tri2:nth-child(2) {
  bottom: 0;
  position: absolute;
  left: 14%;
}

.tall-mountains .tri2:nth-child(3) {
  bottom: 0;
  position: absolute;
  left: 50%;
}

/* Foreground mountains */

.tri3 {
  width: 19%;
  padding-bottom: 13%;
  position: relative;
  overflow: hidden;
}

.tri3:before {
  content: "";
  position: absolute;

  width: 100%;
  height: 104%;
  background: rgb(233, 243, 244);
  background: linear-gradient(
    180deg,
    rgba(233, 243, 244, 1) 0%,
    rgba(190, 231, 232, 1) 72%
  );
  border-radius: 18px;

  -webkit-transform-origin: 0 100%;
  -ms-transform-origin: 0 100%;
  transform-origin: 0 100%;

  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}

.tiny-mountains .tri3:nth-child(1) {
  bottom: 0;
  position: absolute;
  left: 9%;
}
.tiny-mountains .tri3:nth-child(2) {
  bottom: -3%;
  position: absolute;
  left: 31%;
}

.tiny-mountains .tri3:nth-child(3) {
  bottom: -7%;
  position: absolute;
  left: 42%;
}

.tiny-mountains .tri3:nth-child(4) {
  bottom: 0;
  position: absolute;
  left: 72%;
}

.tiny-mountains .tri3:nth-child(5) {
  bottom: -5%;
  position: absolute;
  left: 62%;
}
.ground {
  position: relative;
  height: 25%;
  background: rgb(255, 246, 202);
  background: linear-gradient(
    180deg,
    rgba(255, 246, 202, 1) 0%,
    rgba(255, 229, 164, 1) 72%
  );
}

#clouds {
  padding: 4vw 0;
}

/* base of cloud */
.cloud {
  height: 4vw;
  width: 13vw;
  background: white;
  border-radius: 40vw;
  position: relative;
}

/* smaller circle within cloud */
.cloud:before {
  content: "";
  position: absolute;
  top: -2vw;
  height: 6vw;
  width: 6vw;
  left: 2vw;
  border-radius: 50%;
  background: white;
}

/* larger circle within cloud */
.cloud:after {
  content: "";
  position: absolute;
  height: 6vw;
  width: 6vw;
  top: -3vw;
  left: 5vw;
  border-radius: 50%;
  background: white;
}

#clouds .cloud:nth-child(1) {
  opacity: 0.7;
  animation: moveclouds 17s linear infinite;
  -webkit-animation: moveclouds 17s linear infinite;
  -moz-animation: moveclouds 17s linear infinite;
  -o-animation: moveclouds 17s linear infinite;
}
#clouds .cloud:nth-child(2) {
  left: 75%;
  opacity: 0.5;
  animation: moveclouds 22s linear infinite;
  -webkit-animation: moveclouds 22s linear infinite;
  -moz-animation: moveclouds 22s linear infinite;
  -o-animation: moveclouds 22s linear infinite;
}
#clouds .cloud:nth-child(3) {
  left: 50%;
  z-index: 1;
  animation: moveclouds 15s linear infinite;
  -webkit-animation: moveclouds 15s linear infinite;
  -moz-animation: moveclouds 15s linear infinite;
  -o-animation: moveclouds 15s linear infinite;
}
#clouds .cloud:nth-child(4) {
  left: 90%;
  opacity: 0.7;
  transform: scale(0.6);
  -webkit-transform: scale(0.6);
  -moz-transform: scale(0.6);
  -o-transform: scale(0.6);
  animation: moveclouds 16s linear infinite;
  -webkit-animation: moveclouds 16s linear infinite;
  -moz-animation: moveclouds 16s linear infinite;
  -o-animation: moveclouds 16s linear infinite;
}
#clouds .cloud:nth-child(5) {
  left: 100%;
  opacity: 0.4;
  z-index: 1;

  transform: scale(0.8);
  -webkit-transform: scale(0.8);
  -moz-transform: scale(0.8);
  -o-transform: scale(0.8);

  animation: moveclouds 22s linear infinite;
  -webkit-animation: moveclouds 22s linear infinite;
  -moz-animation: moveclouds 22s linear infinite;
  -o-animation: moveclouds 22s linear infinite;
}
@keyframes moveclouds {
  0% {
    margin-left: 1000px;
  }
  100% {
    margin-left: -1000px;
  }
}

@-webkit-keyframes moveclouds {
  0% {
    margin-left: 1000px;
  }
  100% {
    margin-left: -1000px;
  }
}
@-moz-keyframes moveclouds {
  0% {
    margin-left: 1000px;
  }
  100% {
    margin-left: -1000px;
  }
}
@-o-keyframes moveclouds {
  0% {
    margin-left: 1000px;
  }
  100% {
    margin-left: -1000px;
  }
}

@import url('https://fonts.googleapis.com/css?family=Roboto:400,500,700');



a {
  text-decoration: none;
  color: white;
}

/* .container {
  display: flex;
  justify-content: center;
  align-content: flex-start;
  /* position:relative; */
  /* height: 88vh; */
  /* width: 100vw; */
/* } */


.iphone {
  background-color: #F4F4FB;
  height:83.7vh;
  width: 64vh;
  /* overflow: hidden; */
  position: relative;
  z-index: 2;
  transform:scale(0.8);
  transform-origin: top center;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 30%;
}

.order-summary {
  margin-left: 1.5rem;
  padding: 1rem;
  display: flex;
  flex-direction: column;
}

.order-summary > div {
  margin: 6px;
}

.order-status {
  color: #338A9A;
  font-size: 1.5rem;
  font-weight: 700;
  letter-spacing: 0.5px;
}

.order-date {
  font-size: 2rem;
  font-weight: 700;
}

.order-day {
  color: #338A9A;
  font-size: 1.5rem;
  font-weight: 300;
  letter-spacing: 0.5px;
}

.hero-img-container {
  width: 100%;
  height: 36vh;
   display: flex;
    justify-content: center;
    align-items: center;
  position: relative;
  padding-bottom: 20px;
}

.hero-img-container::before {
   height: 20px;
   width: 20px;
   background-color: #0268EE;
   position: absolute;
  top: 25px;
  right: 150px;
  content:'';
  border-radius: 50%;
}

.hero-img {
  width: 80%;
}

.img-photo {
  width: 90px;
  transform: rotate(-35deg)
}






</style>
