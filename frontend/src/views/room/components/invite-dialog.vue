<template>
  <el-dialog
    custom-class="invite-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
  >

    <!-- header -->
    <template #title>
      <!-- <span style="font-size: 20px; font-weight: bolder;"> -->
      <span>
        초 대 하 기
      </span>
    </template>

    <!-- content -->
    <div class="d-flex flex-column justify-content-around" style="height: 150px;">
      <div style="border: 1px solid black; background-color: black; border-radius: 5px; font-size: 20px; padding: 5px 1rem;">
        여기에 안내 문구를 입력해주세요.
      </div>
      <div class="d-flex justify-content-between">
        <div style="width: 100%" class="me-2">
          <el-input placeholder="User@example.com" v-model="state.inputEmail" clearable></el-input>
        </div>
        <div>
          <el-button>초대코드 전송</el-button>
        </div>
      </div>
    </div>

    <!-- footer -->
  </el-dialog>
</template>

<script>
import { reactive, computed } from 'vue'

export default {
  name: 'invite-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    }
  },
  setup(props, { emit }) {
    const state = reactive({
      dialogVisible: computed(() => props.open),
      inputEmail: '',
    })

    const handleClose = function () {
      state.inputEmail = ''
      emit('closeInviteDialog')
    }

    return { state, handleClose }
  }
}
</script>

<style>
.invite-dialog {
  width: 700px;
  height: 300px;
}
</style>
