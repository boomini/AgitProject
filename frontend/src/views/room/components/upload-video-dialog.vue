<template>
  <el-dialog
    custom-class="upload-video-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
  >
    <!-- header -->
    <template #title>
      <span>
        동 영 상 추 가
      </span>
    </template>

    <!-- content -->
    <div>
      <div>
        동영상을 추가해보세요.
      </div>
      <div>
        <el-form :model="state.form" :rules="state.rules" ref="uploadVideoForm" :label-position="state.form.align">
          <el-form-item prop="schedule" label="일자">
            <el-date-picker
              style="width: 100%;"
              v-model="state.form.schedule"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="일자를 선택해주세요."
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item prop="content" label="내용">
          </el-form-item>
          <input
            type="file"
            accept="video/*"
            multiple
          >
        </el-form>
      </div>
    </div>

    <!-- footer -->
    <template #footer>
      <span>
        <el-button @click="handleClose">취소</el-button>
        <el-button type="primary" @click="uploadVideo">동영상등록</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'

export default {
  name: 'create-schedule-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const uploadVideoForm = ref(null)

    const state = reactive({
      form: {
        align: 'left',
        content: '',
        schedule: '',
      },
      rules: {
        schedule: [
          { required: true, message: '날짜 선택은 필수입니다.', trigger: 'blur'}
        ],
        content: [
          { required: true, message: '내용 입력은 필수입니다.', trigger: 'blur'}
        ]
      },
      dialogVisible: computed(() => props.open),
    })

    const handleClose = function () {
      state.form.content = ''
      state.form.schedule = ''
      emit('closeUploadVideoDialog')
    }

    const uploadVideo = function () {
      console.log('비디오등록')
      handleClose()
    }

    return { state, handleClose, uploadVideoForm, uploadVideo }
  }
}
</script>

<style>
.upload-video-dialog {
  width: 700px;
  height: 600px;
}
</style>
