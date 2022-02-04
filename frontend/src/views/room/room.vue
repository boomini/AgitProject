<template>
  <div class="row">
    <div class="col-10">
      <el-calendar ref="calendar">
        <!-- 달력 헤더 부분 -->
        <template #header="{ date }">
          <span>
            <h3>{{ roomName }}</h3>
            <div>{{ roomDescription }}</div>
          </span>

          <h3>{{ date }}</h3>

          <span>
            <div class="d-flex justify-content-between mb-2">
              <el-button type="danger" @click="state.createArticleDialogOpen = true">게시글 작성</el-button>
              <el-button type="success" @click="state.createScheduleDialogOpen = true">일정 추가</el-button>
              <el-button type="warning" @click="state.uploadImageDialogOpen = true">사진 등록</el-button>
              <el-button type="warning" @click="state.uploadVideoDialogOpen = true">동영상 등록</el-button>
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
            <div>
              <!-- {{ data }} -->
              {{ parseInt(data.day.split('-').slice(2)[0], 10) }}
            </div>
            <div style="" class="badge-box">
              <div v-if="data.type === 'current-month'" class="badge-tag schedule">
                <div class="ms-3">
                  일정
                </div>
                <div class="me-3">
                  <span class="badge">4</span>
                </div>
              </div>
              <div v-if="parseInt(data.day.split('-').slice(2)[0], 10) <= 10 && !(data.type === 'prev-month' || data.type === 'next-month')" class="badge-tag picture">
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
              </div>
            </div>
          </div>
        </template>
      </el-calendar>
    </div>

    <!-- 오른쪽 멤버 화면 -->
    <div class="col-2 d-flex flex-column justify-content-between" style="border: 1px solid black; border-radius: 20px; margin-bottom: 180px;">
      <div>
        <div>멤 버</div>
        <div v-for="i in [1, 2, 3, 4, 5]" :key="i" class="d-flex align-items-center">
          <div class="d-flex align-items-center mb-2">
            <el-avatar :size="50" :src="state.circleUrl"></el-avatar>
            <span style="height: 50px; line-height: 50px;">
              사람 {{ i }}
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
          <el-button style="width: 100%" @click="joinConference(1)">
            회의하기
          </el-button>
        </div>
      </div>
    </div>
  </div>

  <!-- 초대코드 전송 다이얼로그 -->
  <invite-dialog
    :open="state.inviteDialogOpen"
    @closeInviteDialog="onCloseInviteDialog"/>

  <!-- 일정 추가 다이얼로그 -->
  <create-schedule-dialog
    :open="state.createScheduleDialogOpen"
    @closeCreateScheduleDialog="onCloseCreateScheduleDialog"/>

  <!-- 사진 추가 다이얼로그 -->
  <upload-image-dialog
    :open="state.uploadImageDialogOpen"
    @closeUploadImageDialog="onCloseUploadImageDialog"/>

  <!-- 동영상 추가 다이얼로그 -->
  <upload-video-dialog
    :open="state.uploadVideoDialogOpen"
    @closeUploadVideoDialog="onCloseUploadVideoDialog"/>

  <!-- 게시글 추가 다이얼로그 -->
  <create-article-dialog
    :open="state.createArticleDialogOpen"
    @closeCreateArticleDialog="onCloseCreateArticleDialog"/>

  <!-- 게시글 클릭 시 나오는 세부화면 -->
  <el-drawer
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
  </el-drawer>
</template>

<script>
import { ref, reactive, computed, onBeforeMount } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useStore } from 'vuex'
import InviteDialog from './components/invite-dialog.vue'
import CreateScheduleDialog from './components/create-schedule-dialog.vue'
import CreateArticleDialog from './components/create-article-dialog.vue'
import UploadImageDialog from './components/upload-image-dialog.vue'
import UploadVideoDialog from './components/upload-video-dialog.vue'


export default {
  name: 'room-board',
  components: {
    InviteDialog,
    CreateScheduleDialog,
    CreateArticleDialog,
    UploadImageDialog,
    UploadVideoDialog,
  },
  props: {
    roomId: {
      type: Number,
    },
    roomName: {
      type: String,
    },
    roomDescription: {
      type: String,
    },
    roomPicture: {
      type: String,
    }
  },
  setup() {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    const drawer = ref(false)
    const calendar = ref(new Date())
    // const inviteDialogOpen = ref(false)
    // const createScheduleDialogOpen = ref(false)

    const clickOnDate = function (data) {
      const date = data.day.split('-')
      state.year = parseInt(date[0], 10)
      state.month = parseInt(date[1], 10)
      state.day = parseInt(date[2], 10)

      if (data.type === 'current-month') {
        drawer.value = true
      } else {
        drawer.value = false
      }
    }

    const state = reactive({
      team: {
        teamId: '',
        teamName: '',
        teamDescription: '',
        teamPicture: '',
        articleList: [],
        imageList: [],
        videoList: [],
        uploadDate: ''
      },
      createScheduleDialogOpen: false,
      createArticleDialogOpen: false,
      uploadImageDialogOpen: false,
      uploadVideoDialogOpen: false,
      inviteDialogOpen: false,
      year : '',
      month : '',
      day : '',
      title : computed(() => `${state.year}년 ${state.month}월 ${state.day}일 게시판`),
      circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
    })

    const selectDate = (val) => {
      calendar.value.selectDate(val)
      const date = calendar.value.date
      state.team.uploadDate = `${date.$y}-${date.$M + 1}`
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

    const onCloseUploadImageDialog = function () {
      state.uploadImageDialogOpen = false
    }

    const onCloseUploadVideoDialog = function () {
      state.uploadVideoDialogOpen = false
    }

    const joinConference = function (roomId) {
      router.push({
        name: 'conference-detail',
        params: {
          conferenceId: roomId
        }
      })
    }

    onBeforeMount(() => {
      state.team.teamId = route.params.roomId
      state.team.teamName = route.params.roomName
      state.team.teamDescription = route.params.roomDescription
      state.team.teamPicture = route.params.roomPicture
      const today = new Date()
      state.team.uploadDate = `${today.getFullYear()}-${today.getMonth() + 1}`

      // 이번 달 달력 가져오기
      const payload = {
        'teamId': state.team.teamId,
        'uploadDate': state.team.uploadDate
      }
      store.dispatch('root/getCategoryCount', payload)
      .then(function (result) {
        console.log('성공')
      })
      .catch(function (error) {
        console.log('실패')
      })
    })

    return { clickOnDate, drawer, state, selectDate, calendar, onCloseInviteDialog, onCloseCreateScheduleDialog, onCloseUploadImageDialog, onCloseUploadVideoDialog, onCloseCreateArticleDialog, joinConference }
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
  height: 150px;
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
</style>
