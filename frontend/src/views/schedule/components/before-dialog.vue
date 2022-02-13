<template>
  <div v-if="info">
    <el-dialog custom-class="before-dialog" v-model="state.dialogVisible" title="이전 약속" width="30%" style="margin: 50%">
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
    <el-dialog custom-class="before-dialog" v-model="state.dialogVisible" title="이전 약속" width="30%" style="margin: 50%">
      <p>
      지난 일정이 없습니다.
      </p>
      <br>
      <p>팀 생성 후 일정을 추가할 수 있습니다 <i class="em em-closed_book" aria-role="presentation" aria-label="CLOSED BOOK" style="margin-bottom:4px"></i></p>
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
  name: 'before-dialog',
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

  // console.log(props.info)
  // console.log('여기냐')


    const handleClose = function () {
      emit('closeBeforeDialog')
    }

    return { state, store, router, handleClose }
  }
}
</script>

<style>

</style>
