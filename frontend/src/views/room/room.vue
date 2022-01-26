<template>
  <div class="row">
    <div class="col-10">
      <el-calendar ref="calendar">
        <!-- 달력 헤더 부분 -->
        <template #header="{ date }">
          <span>
            <h3>방 이름</h3>
            <div>간단한 방 소개</div>
          </span>

          <h3>{{ date }}</h3>

          <span>
            <div class="d-flex justify-content-between mb-2">
              <el-button type="success" style="width: 100%" @click="createScheduleDialogOpen = true">일정 추가</el-button>
              <el-button type="warning" style="width: 100%">일정 수정</el-button>
            </div>
            <div>
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
          <el-button style="width: 100%; margin-bottom: 0.5rem;" @click="inviteDialogOpen = true">
            초대하기
          </el-button>
        </div>
        <div>
          <el-button style="width: 100%">
            회의하기
          </el-button>
        </div>
      </div>
    </div>
  </div>


  <!-- 초대코드 전송 다이얼로그 -->
  <el-dialog
    custom-class="invite-dialog"
    v-model="inviteDialogOpen"
    width="30%"
  >
    <template #title>
      <span style="font-size: 20px; font-weight: bolder;">
        초 대 하 기
      </span>
    </template>
    <div class="d-flex flex-column justify-content-around" style="height: 150px;">
      <div style="border: 1px solid black; background-color: black; border-radius: 5px; font-size: 20px; padding: 5px 1rem;">
        여기에 안내 문구를 입력해주세요.
      </div>
      <div class="d-flex justify-content-between">
        <div style="width: 100%" class="me-2">
          <el-input placeholder="User@example.com" v-model="state.inputEmailId" clearable></el-input>

          <!-- <div class="col-4">
          </div>
          <div class="col-1 text-center">
            <span style="font-size: 20px;"> @ </span>
          </div>



          <div class="col-6">
            <el-input v-model="state.inputEmailServer">
              <template #append>
                <el-select v-model="state.inputEmailServer" placeholder="Select" style="width: 110px">
                  <el-option
                    v-for="item in state.emailOptions"
                    :key="item.label"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </template>
            </el-input>
          </div> -->




        </div>

        <el-button>초대코드 전송</el-button>
      </div>

    </div>
    <!-- <template #footer>
      <span class="dialog-footer">
        <el-button @click="inviteDialogOpen = false">Cancel</el-button>
        <el-button type="primary" @click="inviteDialogOpen = false"
          >Confirm</el-button
        >
      </span>
    </template> -->
  </el-dialog>

  <!-- 일정 추가 다이얼로그 -->
  <el-dialog
    custom-class="create-schedule-dialog"
    v-model="createScheduleDialogOpen"
    width="30%"
  >

    <!-- 타이틀 -->
    <template #title>
      <span style="font-size: 20px; font-weight: bolder; position: absolute; top: 2px; left: 20px;">
        일 정 추 가
      </span>
    </template>


    <div>
      일정을 추가해보세요.

    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="inviteDialogOpen = false">Cancel</el-button>
        <el-button type="primary" @click="inviteDialogOpen = false"
          >Confirm</el-button
        >
      </span>
    </template>
  </el-dialog>

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
import { ref, reactive, computed } from 'vue'
import InviteDialog from './invite-dialog.vue'


export default {
  name: 'room-board',
  setup() {
    const drawer = ref(false)
    const calendar = ref()
    const inviteDialogOpen = ref(false)
    const createScheduleDialogOpen = ref(false)

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
      year : '',
      month : '',
      day : '',
      title : computed(() => `${state.year}년 ${state.month}월 ${state.day}일 게시판`),
      circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
      inputEmailId: '',
      inputEmailServer: '',
      emailOptions: [
        {
          label: '직접입력',
          value: ''
        },
        {
          label: '네이버',
          value: 'naver.com'
        },
        {
          label: '다음',
          value: 'hanmail.com'
        },
        {
          label: '구글',
          value: 'google.com'
        },
        {
          label: '야후',
          value: 'yahoo.co.kr'
        },
        ]
    })

    const selectDate = (val) => {
      calendar.value.selectDate(val)
    }

    return { clickOnDate, drawer, state, selectDate, calendar, inviteDialogOpen, createScheduleDialogOpen }
  }
}
</script>

<style>
.invite-dialog {
  width: 700px;
  height: 300px;
}

.el-dialog {
  border-radius: 15px;
  border: 1px solid black;
  width: 700px;
  height: 550px;
}

.el-dialog > .el-dialog__header {
  background-color: #abdfd4;
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
  border-bottom: 1px solid black;
  height: 15px;
  color: white;
  text-shadow:
    -1px -1px 0 black,
    1px -1px 0 black,
    -1px 1px 0 black,
    1px 1px 0 black;
}

.el-dialog > .el-dialog__header > .el-dialog__headerbtn {
  position: absolute;
  top: 3px;
  right: 20px;
  padding: 0;
  /* font-size: 16px; */
  /* border: 1px solid red;
  border-radius: 100%; */
}

.el-dialog > .el-dialog__header > .el-dialog__headerbtn > .el-dialog__close {
  border: 1px solid #dd5a4e;
  border-radius: 50%;
  background-color: #ff6053;
  color: black;
  font-size: 10px;
}

.el-dialog > .el-dialog__header > .el-dialog__headerbtn > .el-dialog__close:hover {
  color: white;
  transition: 0.2s;
}
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
