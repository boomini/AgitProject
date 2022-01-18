<template>
  <div>
    <el-calendar v-model="value" @click="clickOnDate"/>
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

    const clickOnDate = function () {
      state.year = value.value.getFullYear()
      state.month = value.value.getMonth() + 1
      state.day = value.value.getDate()

      console.log(state.year)

      drawer.value = true
      console.log(drawer)
    }

    const state = reactive({
      year : '',
      month : '',
      day : '',
      title : computed(() => `${state.year}년 ${state.month}월 ${state.day}일 게시판`)
    })

    return { value, clickOnDate, drawer, state }
  }
}
</script>

<style>

</style>
