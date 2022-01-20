<template>
  <div>
    <el-calendar v-model="value" @click="clickOnDate(data)">
      <template #dateCell="{ data }">
        <div class="d-flex flex-column justify-content-between" style="height: 100%">
          <div>
            {{ parseInt(data.day.split('-').slice(2)[0], 10) }}
          </div>
          <div style="width: 90%; margin: 0 auto; line-height: 150%">
            <div v-if="!(data.type === 'prev-month' || data.type === 'next-month')" style="font-size: 11px; border: 1px solid #ff6c7fd5; border-radius: 5px; background-color: #ff6c7fd5; color: white; font-weight: bolder;" class="d-flex justify-content-between mb-2">
              <div class="ms-3">
                일정
              </div>
              <div class="me-3">
                <span class="badge bg-secondary">4</span>
              </div>
            </div>
            <div v-if="parseInt(data.day.split('-').slice(2)[0], 10) <= 10 && !(data.type === 'prev-month' || data.type === 'next-month')" style="font-size: 11px; border: 1px solid #6c82ffd5; border-radius: 5px; background-color: #6c82ffd5; color: white; font-weight: bolder;" class="d-flex justify-content-between mb-2">
              <div class="ms-3">
                사진
              </div>
              <div class="me-3">
                <span class="badge bg-secondary">4</span>
              </div>
            </div>
            <div v-if="parseInt(data.day.split('-').slice(2)[0], 10) <= 20 && !(data.type === 'prev-month' || data.type === 'next-month')" style="font-size: 11px; border: 1px solid #6b6b6bd5; border-radius: 5px; background-color: #6b6b6bd5; color: white; font-weight: bolder;" class="d-flex justify-content-between">
              <div class="ms-3">
                동영상
              </div>
              <div class="me-3">
                <span class="badge bg-secondary">4</span>
              </div>
            </div>
          </div>
        </div>
      </template>
    </el-calendar>
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
  </div>
</template>

<script>
import { ref, reactive, computed } from 'vue'

export default {
  name: 'room-board',
  setup() {
    const value = ref(new Date())
    // const year = ref(value.value.getFullYear())
    const drawer = ref(false)

    const clickOnDate = function (data) {
      state.year = value.value.getFullYear()
      state.month = value.value.getMonth() + 1
      state.day = value.value.getDate()
      // state.data = data

      console.log(state.year)

      drawer.value = true
      // console.log(state.data)
    }

    const state = reactive({
      year : '',
      month : '',
      day : '',
      title : computed(() => `${state.year}년 ${state.month}월 ${state.day}일 게시판`),
      // data : ''
    })

    return { value, clickOnDate, drawer, state }
  }
}
</script>

<style>
/* 달력 바닥이 다 보이도록 마진 설정 */
.el-calendar .el-calendar-table {
  margin-bottom: 100px;
}

/* 달력 날짜 한 칸이 너무 작아서 높이 설정 */
.el-calendar .el-calendar-table > tbody > tr > td .el-calendar-day{
  height: 150px;
}

</style>
