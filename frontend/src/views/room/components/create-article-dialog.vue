<template>
  <el-dialog
    custom-class="create-article-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
  >
    <!-- header -->
    <template #title>
      <span>
        게 시 글 작 성
      </span>
    </template>

    <!-- content -->
    <div>
      <div>
        일정을 추가해보세요.
      </div>
      <div>
        <el-form :model="state.form" :rules="state.rules" ref="createArticleForm" :label-position="state.form.align">
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
            <el-input
              v-model="state.form.content"
              maxlength="500"
              placeholder="약속의 내용을 입력해주세요."
              show-word-limit
              rows="10"
              type="textarea"
              resize="none"
            >
            </el-input>
          </el-form-item>
        </el-form>
      </div>
    </div>

    <!-- footer -->
    <template #footer>
      <span>
        <el-button @click="handleClose">취소</el-button>
        <el-button type="primary" @click="createArticle">약속잡기</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'

export default {
  name: 'create-article-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const createArticleForm = ref(null)

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
      emit('closeCreateArticleDialog')
    }

    const createArticle = function () {
      console.log('약속 생성 함수 작동')
      handleClose()
    }

    return { state, handleClose, createArticleForm, createArticle }
  }
}
</script>

<style>
.create-schedule-dialog {
  width: 700px;
  height: 600px;
}
</style>
