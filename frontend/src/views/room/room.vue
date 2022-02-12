<template>
  <div class="row">
    <div class="col-10">
      <el-calendar ref="calendar">
        <!-- 달력 헤더 부분 -->
        <template #header="{ date }">
          <span>
            <h3>{{ state.team.teamName }}</h3>
            <div>{{ state.team.teamDescription }}</div>
          </span>

          <div class="d-flex flex-column">
            <h3>{{ date }}</h3>
            <div>
              레전드
            </div>
          </div>

          <span>
            <div class="d-flex justify-content-between mb-2">
              <el-button type="danger" @click="state.createArticleDialogOpen = true">
                <!-- 게시글 작성 -->
                <i class="fa-regular fa-pen-to-square"></i>
              </el-button>
              <el-button type="success" @click="state.createScheduleDialogOpen = true">
                <!-- 일정 추가 -->
                <i class="fa-regular fa-calendar-days"></i>
              </el-button>
              <el-button type="warning" @click="state.uploadImageDialogOpen = true">
                <!-- 사진 등록 -->
                <i class="fa-regular fa-images"></i>
              </el-button>
              <el-button type="warning" @click="state.uploadVideoDialogOpen = true">
                <!-- 동영상 등록 -->
                <i class="fa-solid fa-video"></i>
              </el-button>
            </div>
            <div class="d-flex justify-content-between">
              <el-button size="small" @click="selectDate('prev-year')"
                >&lt;&lt;</el-button
              >
              <el-button size="small" @click="selectDate('prev-month')"
                >&lt;</el-button
              >
              <el-button size="small" @click="selectDate('today')">Today</el-button>
              <el-button size="small" @click="selectDate('next-month')"
                >&gt;</el-button
              >
              <el-button size="small" @click="selectDate('next-year')"
                >&gt;&gt;</el-button
              >
            </div>
          </span>
        </template>

        <!-- 달력 날짜 부분 -->
        <template #dateCell="{ data }" >
          <div class="d-flex flex-column justify-content-between" style="height: 100%" @click="clickOnDate(data)">
            <div class="row">
              <!-- {{ data }} -->
              <span class="col-3">
                {{ data.day.split('-')[2] }}
              </span>
              <span class="col-9">
                <!-- <span v-bind="state.dict.articleCntDict"> -->
                <span v-if="data.day.toString() in state.dict.articleCntDict" class="badge-box-test">
                  <span class="badge-tag-test schedule">{{ state.dict.articleCntDict[data.day.toString()] }}</span>
                </span>
                <!-- </span> -->
                <span v-if="data.day.toString() in state.dict.imageCntDict" class="badge-box-test">
                  <span class="badge-tag-test video">{{ state.dict.imageCntDict[data.day.toString()] }}</span>
                </span>
                <span v-if="data.day.toString() in state.dict.videoCntDict" class="badge-box-test">
                  <span class="badge-tag-test picture">{{ state.dict.videoCntDict[data.day.toString()] }}</span>
                </span>
              </span>
            </div>
            <div class="badge-box">
              <!-- <div v-if="data.type === 'current-month'" class="badge-tag schedule">
                <div class="ms-3">
                  일정
                </div>
                <div class="me-3">
                  <span class="badge">4</span>
                </div>
              </div> -->
              <div v-if="data.day.toString() in state.dict.eventDictEnd && data.type === 'current-month'">
                <div class="badge-tag article" v-for="(item, index) in state.dict.eventDictEnd[data.day.toString()]" :key="index">
                  <div class="ms-3">
                    {{ item.title }}
                  </div>
                  <div class="me-3">
                    <span class="badge">D-day</span>
                  </div>
                </div>
              </div>
              <div v-if="data.day.toString() in state.dict.eventDictStart && data.type === 'current-month'">
                <div class="badge-tag article" v-for="(item, index) in state.dict.eventDictStart[data.day.toString()]" :key="index">
                  <div class="ms-3">
                    {{ item.title }}
                  </div>
                  <div class="me-3">
                    <span class="badge">Start</span>
                  </div>
                </div>
              </div>
              <!-- <div v-if="parseInt(data.day.split('-').slice(2)[0], 10) <= 10 && !(data.type === 'prev-month' || data.type === 'next-month')" class="badge-tag picture">
                <div class="ms-3">
                  사진
                </div>
                <div class="me-3">
                  <span class="badge">4</span>
                </div>
              </div>
              <div v-if="parseInt(data.day.split('-').slice(2)[0], 10) <= 20 && !(data.type === 'prev-month' || data.type === 'next-month')" class="badge-tag video">
                <div class="ms-3">
                  동영상
                </div>
                <div class="me-3">
                  <span class="badge">4</span>
                </div>
              </div> -->
            </div>
          </div>
        </template>
      </el-calendar>
    </div>

    <!-- 오른쪽 멤버 화면 -->
    <div class="col-2 d-flex flex-column justify-content-between" style="border: 1px solid black; border-radius: 20px; margin-bottom: 180px;">
      <div>
        <div>멤 버</div>
        <div v-for="member in state.teamMembers" :key="member.id" class="d-flex align-items-center">
          <div class="d-flex align-items-center mb-2">
            <el-avatar :size="50" :src="state.circleUrl"></el-avatar>
            <span style="height: 50px; line-height: 50px;">
              {{ member.name }}
            </span>
          </div>
        </div>
      </div>
      <div style="text-align: center; margin-bottom: 1rem">
        <div>
          <el-button style="width: 100%; margin-bottom: 0.5rem;" @click="state.inviteDialogOpen = true">
            초대하기
          </el-button>
        </div>
        <div>
          <el-button style="width: 100%" @click="joinConference(state.team.teamId)">
            회의하기
          </el-button>
        </div>
      </div>
    </div>
  </div>

  <!-- 초대코드 전송 다이얼로그 -->
  <invite-dialog
    :open="state.inviteDialogOpen"
    :info="state.team.teamId"
    @closeInviteDialog="onCloseInviteDialog"/>

  <!-- 일정 추가 다이얼로그 -->
  <create-schedule-dialog
    :open="state.createScheduleDialogOpen"
    :info="state.team"
    @closeCreateScheduleDialog="onCloseCreateScheduleDialog"
    @createSchedule="onCreateEvent"/>

  <!-- 사진 추가 다이얼로그 -->
  <upload-image-dialog
    :open="state.uploadImageDialogOpen"
    :info="state.team.teamId"
    @closeUploadImageDialog="onCloseUploadImageDialog"
    @createImage="onCreateEvent"/>

  <!-- 동영상 추가 다이얼로그 -->
  <upload-video-dialog
    :open="state.uploadVideoDialogOpen"
    :info="state.team.teamId"
    @closeUploadVideoDialog="onCloseUploadVideoDialog"
    @createVideo="onCreateEvent"/>

  <!-- 게시글 추가 다이얼로그 -->
  <create-article-dialog
    :open="state.createArticleDialogOpen"
    :info="state.team"
    @closeCreateArticleDialog="onCloseCreateArticleDialog"
    @createArticle="onCreateEvent"/>

  <!-- 게시글 클릭 시 나오는 세부화면 -->
  <board
    :data="state.boardData"
    :open="state.boardOpen"
    @closeBoard="onCloseBoard"/>
  <!-- <el-drawer
    v-model="drawer"
    :title= "state.title"
    :direction="rtl"
  >
    <div style="border: 1px solid gray; height: 200px;">
      사진
      <div class="d-flex">
        <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
        <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
        <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
      </div>
    </div>
    <div style="border: 1px solid gray; height: 200px;">
      동영상
    </div>
    <div style="border: 1px solid gray; height: 200px;">
      TO-DO
    </div>
    <div style="border: 1px solid gray; height: 200px;">
      회의 메모
    </div>
  </el-drawer> -->
</template>

<script>

import { ref, reactive, computed, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import InviteDialog from './components/invite-dialog.vue'
import CreateScheduleDialog from './components/create-schedule-dialog.vue'
import CreateArticleDialog from './components/create-article-dialog.vue'
import UploadImageDialog from './components/upload-image-dialog.vue'
import UploadVideoDialog from './components/upload-video-dialog.vue'
import Board from './components/board/board.vue'


export default {
  name: 'room-board',
  components: {
    InviteDialog,
    CreateScheduleDialog,
    CreateArticleDialog,
    UploadImageDialog,
    UploadVideoDialog,
    Board,
  },
  setup() {
    const store = useStore()
    const router = useRouter()
    const calendar = ref()

    // const inviteDialogOpen = ref(false)
    // const createScheduleDialogOpen = ref(false)

    // 월 강제로 2자리 맞추기 (ex: 2 -> 02)
    function convertMonth (month) {
      if (month < 10) {
        return `0${month}`
      } else {
        return month.toString()
      }
    }

    // json -> dict
    function convertListToDict (list, dict) {
      for (let i = 0; i < list.length; i++) {
        let count = ''
        const length = list[i].count
        if (length >= 100) {
          count = '99+'
        } else {
          count = length.toString()
        }
        const uploadDate = list[i].uploadDate
        dict[uploadDate] = count
      }
    }

    function convertEventToDict (list, dict, key, value) {
      for (let i = 0; i < list.length; i++) {
        const dictKey = list[i][key.toString()]
        const dictValue = list[i][value.toString()]
        const title = list[i].eventTitle
        const content = {
          'value': dictValue,
          'title': title,
        }
        if (dictKey in dict) {
          dict[dictKey].push(content)
        } else{
          dict[dictKey] = [content]
        }
      }
    }

    // 팀 세부정보 가져오기
    function getTeamDetail () {
      let url = window.location.href;
      state.team.teamId = url.split('/').reverse()[0];

      store.dispatch('root/getTeamInfoDetail', state.team.teamId)
      .then(function(result){
        console.log('팀 세부정보 가져오기')
        console.log(result.data);
        state.team = result.data;
        // state.team.teamId = result.data.id;
        // console.log(state.team.teamId);
        // console.log(state.team.teamName)
      })
    }

    // 달력 개요(게시글, 사진, 동영상) 새로고침
    function reloadCalendar () {
      console.log('달력 개요 새로고침')

      store.dispatch('root/getTeamInfoDetail', state.team.teamId)
      .then(function(result){
        // console.log(result.data);
        state.team = result.data;
        state.team.teamId = result.data.id;
        // console.log(state.team.teamId);
        // console.log(state.team.teamName)
      })
      const payload = {
        'teamId': state.team.teamId,
        'uploadDate': state.team.uploadDate
      }
      store.dispatch('root/getCategoryCount', payload)
      .then(function (result) {
        console.log('해당 달의 개요 가져오기 성공')
        console.log(result.data)
        convertListToDict(result.data.articleCntList, state.dict.articleCntDict)
        convertListToDict(result.data.imageCntList, state.dict.imageCntDict)
        convertListToDict(result.data.videoCntList, state.dict.videoCntDict)
        // console.log(state.dict.articleCntDict)
      })
      .catch(function (error) {
        console.log('해당 달의 개요 가져오기 실패')
      })
    }

    // 달력 개요(일정) 새로고침
    function reloadEvent () {
      const payload = {
        'teamId': state.team.teamId,
        'reqDate': state.team.uploadDate
      }
      console.log('fiiciciciciicic')
      console.log(state.team.teamId)
      console.log(state.team.uploadDate)
      store.dispatch('root/getEventCount', payload)
      .then(function (result) {
        console.log('일정 가져오는데 성공하였습니다.')
        state.dict.eventDictStart = {}
        state.dict.eventDictEnd = {}
        convertEventToDict(result.data, state.dict.eventDictStart, 'startDate', 'endDate')
        convertEventToDict(result.data, state.dict.eventDictEnd, 'endDate', 'startDate')
        console.log(state.dict.eventDictEnd)
        console.log(state.dict.eventDictStart)
      })
      .catch(function (error) {
        console.log('일정 가져오는데 실패하였습니다.')
      })
    }


    const checkUserState = function(){
      // let url = window.location.href;
      // state.team.teamId = url.split('/').reverse()[0];

      if(state.isLogin==null){
        setTimeout(() => {
                swal({
                  title: "로그인 필요한 페이지",
                  text: "로그인 후 이용해주세요.",
                  icon: "success",
                  button: "확인",
                });
              }, 500)
       router.push({
        name: 'intro',
        })
      } else{
        console.log(state.isLogin);
        store.dispatch('root/checkTeamMember', {teamId: state.team.teamId, token:state.isLogin} )
        .then(function(result){
          console.log(result.data);
          console.log(result);
        })
        .catch(function(err){
          console.log(err.response)
          if(err.response.data.statusCode==1005){

            getTeamDetail();
            //비활성화 된 회원
            console.log(state.team.teamId);
            console.log(state.team.teamName);
            router.push({
              name: 'RoomConfirm',
              params: {
                roomId: state.team.teamId,
                roomName: state.team.teamName,
              },
            })
          }else if(err.response.data.statusCode==1002){
            //접근불가한 User
              router.push({
                name: 'Error'
              })
            }
          }
        )
      }
    }

    function takeMember () {
      const token = store.getters['root/getJWTToken']
      const body = {
        'teamId': state.team.teamId
      }
      store.dispatch('root/takeMember', {'body': body, 'token': token})
      .then(res=> {
        console.log(res.data)
        state.teamMembers = res.data
        console.log(state.teamMembers[0].name)
      })
      .catch(err => {
        console.log(err)
        // console.log('기달')
      })
    }

    const state = reactive({
      team: {
        teamId: '',
        teamName: '',
        teamDescription: '',
        teamPicture: '',
        uploadDate: ''
      },
      dict: {
        articleCntDict: {},
        imageCntDict: {},
        videoCntDict: {},
        eventDictStart: {},
        eventDictEnd: {}
      },
      boardData: {
        teamId: '',
        articleList: [],
        eventResList: [],
        videoList: [],
        imageList: [],
        uploadDate: '1970-01-01'
      },
      createScheduleDialogOpen: false,
      createArticleDialogOpen: false,
      uploadImageDialogOpen: false,
      uploadVideoDialogOpen: false,
      inviteDialogOpen: false,
      boardOpen: false,
      // year : '',
      // month : '',
      // day : '',
      token:'',
      // clickDate: '',
      title : computed(() => `${state.year}년 ${state.month}월 ${state.day}일 게시판`),
      circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
      teamMembers: null,
      isLogin: computed(() => store.getters['root/getJWTToken'])
    })

    const clickOnDate = function (data) {
      if (data.type === 'current-month') {
        const teamId = state.team.teamId
        const uploadDate = data.day
        const payload = {
          'teamId': teamId,
          'uploadDate': uploadDate
        }
        store.dispatch('root/getBoardDetail', payload)
        .then(function (result) {
          console.log('게시판 상세조회 성공')
          console.log(result.data);
          state.boardData = result.data
          state.boardData['uploadDate'] = uploadDate
          state.boardOpen = true
        })
        .catch(function (error) {
          console.log('게시판 상세조회 실패')
        })
      } else {
        state.boardOpen = false
      }

    }

    const selectDate = (val) => {
      let url = window.location.href;
      state.team.teamId = url.split('/').reverse()[0];

      calendar.value.selectDate(val)
      const date = calendar.value.date
      const year = date.$y
      const month = convertMonth(date.$M + 1)
      state.team.uploadDate = `${year}-${month}`

      reloadCalendar()
      reloadEvent()
    }

    const onCloseInviteDialog = function () {
      state.inviteDialogOpen = false
    }

    const onCloseCreateScheduleDialog = function () {
      state.createScheduleDialogOpen = false
    }

    const onCloseCreateArticleDialog = function () {
      state.createArticleDialogOpen = false
    }

    const onCreateEvent = function () {
      const today = new Date()
      const year = today.getFullYear()
      const month = convertMonth(today.getMonth() + 1)
      state.team.uploadDate = `${year}-${month}`
      calendar.value.selectDate('today')
      reloadCalendar()
      reloadEvent()
    }

    const onCloseUploadImageDialog = function () {
      state.uploadImageDialogOpen = false
    }

    const onCloseUploadVideoDialog = function () {
      state.uploadVideoDialogOpen = false
    }

    const onCloseBoard = function () {
      state.boardOpen = false
    }

    const joinConference = function (roomId) {
      router.push({
        name: 'conference-detail',
        params: {
          conferenceId: roomId
        }
      })
      // router.push({
      //   name: 'Error',
      // })
    }

    // const getTeamDetail = function(){
    //   store.dispatch('root/getTeamInfoDetail', state.team.teamId)
    //   .then(function(result){
    //     console.log(result.data);
    //     state.team = result.data;
    //     state.team.teamId = result.data.id;
    //     console.log(state.team.teamId);
    //     console.log(state.team.teamName)
    //   })
    // }

    // const checkUserState = function(){
    //   let url = window.location.href;
    //   state.team.teamId = url.split('/').reverse()[0];

    //   if(state.isLogin==null){
    //     setTimeout(() => {
    //             swal({
    //               title: "로그인 필요한 페이지",
    //               text: "로그인 후 이용해주세요.",
    //               icon: "success",
    //               button: "확인",
    //             });
    //           }, 500)
    //    router.push({
    //     name: 'intro',
    //     })
    //   }
    //   else{
    //     console.log(state.isLogin);
    //     store.dispatch('root/checkTeamMember', {teamId: state.team.teamId, token:state.isLogin} )
    //   .then(function(result){
    //     console.log(result.data);
    //     console.log(result);
    //   }).catch(function(err){
    //     console.log(err.response)
    //     if(err.response.data.statusCode==1005){

    //       getTeamDetail();
    //       //비활성화 된 회원
    //       console.log(state.team.teamId);
    //       console.log(state.team.teamName);
    //       router.push({
    //       name: 'RoomConfirm',
    //       params: {
    //         roomId: state.team.teamId,
    //         roomName: state.team.teamName,
    //       },
    //     })
    //     }else if(err.response.data.statusCode==1002){
    //       //접근불가한 User
    //         router.push({
    //           name: 'Error'
    //         })

    //       }
    //     })
    //   }
    // }

    //   const takeMember = function () {
    //     const token = store.getters['root/getJWTToken']
    //     const body = {
    //       'teamId': state.team.teamId
    //     }
    //     store.dispatch('root/takeMember', {'body': body, 'token': token})
    //     .then(res=> {
    //       console.log(res.data)
    //       state.teamMembers = res.data
    //       console.log(state.teamMembers[0].name)
    //     })
    //     .catch(err => {
    //       console.log(err)
    //       // console.log('기달')
    //     })
    //   }

    onBeforeMount(() => {
      let url = window.location.href;
      state.team.teamId = url.split('/').reverse()[0];
      console.log(state.team.teamId);
      const today = new Date()
      const year = today.getFullYear()
      const month = convertMonth(today.getMonth() + 1)
      state.team.uploadDate = `${year}-${month}`

      checkUserState();
      getTeamDetail();
      reloadCalendar()
      reloadEvent()
      takeMember()

    })

    return { clickOnDate, state, selectDate, calendar, onCloseInviteDialog, onCloseCreateScheduleDialog, onCloseUploadImageDialog, onCloseUploadVideoDialog, onCloseCreateArticleDialog, joinConference, onCloseBoard, onCreateEvent }
  }

}
</script>

<style>


/* .el-calender .el-calendar-table {
  tran
} */

/* 달력 바닥이 다 보이도록 마진 설정 */
.el-calendar .el-calendar-table {
  margin-bottom: 150px;
}

/* .el-calendar .el-calendar__header {
  pointer-events: none;
} */

/* 달력 날짜 한 칸이 너무 작아서 높이 설정 */
.el-calendar .el-calendar-table > tbody > tr > td .el-calendar-day{
  height: 180px;
}

/* 게시판 내에 존재하는 일정, 사진, 동영상 등의 뱃지 css */
.badge-tag {
  font-size: 11px;
  border-radius: 5px;
  color: white;
  font-weight: bolder;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.5rem;
  padding: 0;
}
.badge-tag-test {
  font-size: 0.8rem;
  border-radius: 10px;
  color: white;
  font-weight: bolder;
  /* display: flex; */
  /* justify-content: space-between; */
  /* align-items: center; */
  /* margin-bottom: 0.5rem; */
  /* padding-left: 2px;
  padding-right: 2px;
  padding-top: 2px; */
  padding: 0px 7px;
  /* margin-right: 50px; */
}

.badge-tag .badge {
  margin: 0;
  text-align: center;
  background-color: grey;
}

.badge-box {
  width: 90%;
  margin: 0 auto;
  line-height: 150%;
}
.badge-box-test {
  width: 90%;
  margin: 0 auto;
  line-height: 150%;
  margin-right: 5px;
}

.badge-box .schedule {
  border: 1px solid #ff6c7fd5;
  background-color: #ff6c7fd5;
}

.badge-box .picture {
  border: 1px solid #6c82ffd5;
  background-color: #6c82ffd5;
}

.badge-box .video {
  border: 1px solid #6b6b6bd5;
  background-color: #6b6b6bd5;
}
.badge-box-test .schedule {
  border: 1px solid #ff6c7fd5;
  background-color: #ff6c7fd5;
}

.badge-box-test .picture {
  border: 1px solid #6c82ffd5;
  background-color: #6c82ffd5;
}

.badge-box-test .video {
  border: 1px solid #6b6b6bd5;
  background-color: #6b6b6bd5;
}

.badge-box .article {
  border: 1px solid #6ac7b3d5;
  background-color: #6ac7b3d5;
}
</style>
