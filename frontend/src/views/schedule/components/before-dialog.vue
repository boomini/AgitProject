<template>
  <div>
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




    const handleClose = function () {
      emit('closeBeforeDialog')
    }

    return { state, store, router, handleClose }
  }
}
</script>

<style>

</style>
