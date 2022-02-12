<template>
  <div v-if="info">
    <el-dialog custom-class="after-dialog" v-model="state.dialogVisible" title="다음 약속" width="30%" style="margin: 50%">
      <span>
      팀 이름: {{ info.teamName }} <br>
      시작 날짜: {{ info.startDate }} <br>
      종료 날짜: {{ info.endDate }} <br>
      D-day: {{ info.dday }}일 <br>
      약속 제목: {{ info.eventTitle }} <br>
      약속 내용: {{ info.eventContent }}
      </span>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="handleClose()"
            >Confirm</el-button
          >
        </span>
      </template>
    </el-dialog>
  </div>
  <div v-else-if="info == undefined">
    <el-dialog custom-class="before-dialog" v-model="state.dialogVisible" title="다음 약속" width="30%" style="margin: 50%">
      <span>
      일정을 추가해주세요!!
      </span>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="handleClose()"
            >Confirm</el-button
          >
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'


export default {
  name: 'after-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    },
    info: {
      type: Object,
      required: true,
    },
  },
  setup(props, {emit}) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      dialogVisible: computed(() => props.open),
    })




    const handleClose = function () {
      emit('closeAfterDialog')
    }

    return { state, store, router, handleClose }
  }
}
</script>

<style>

</style>
