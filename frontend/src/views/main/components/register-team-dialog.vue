<template>
  <el-dialog
    custom-class="register-team-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
  >
    <!-- header -->
    <template #title>
      <span>
        아지트 생성
      </span>
    </template>

    <!-- content -->
    <el-form :model="state.form" status-icon :rules="state.rules" ref="registerTeamForm" :label-position="state.form.align">
      <el-form-item prop="teamName" label="방이름" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.teamName" autocomplete="off" id="id-input" placeholder="방 이름을 입력해주세요."></el-input>
      </el-form-item>
      <el-form-item prop="teamPassword" label="방 비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.teamPassword" autocomplete="off" show-password placeholder="비밀번호를 설정해주세요."></el-input>
      </el-form-item>
      <el-form-item prop="teamDescription" label="팀 소개">
        <el-input
          v-model="state.form.teamDescription"
          maxlength="300"
          placeholder="어떤 방인지 간단한 소개를 해주세요."
          show-word-limit
          rows="6"
          type="textarea"
          resize="none"
        >
        </el-input>
      </el-form-item>
      <el-form-item prop="teamPicture" label="팀 사진" :label-width="state.formLabelWidth" >
        <!-- <el-input v-model="state.form.teamPicture" autocomplete="off" placeholder="사진 등록 버튼 추가"></el-input> -->
        <input
            type="file"
            accept="image/*"
            @change="uploadTeamPicture"
            ref="inputImage"
          />
        <span v-if="state.form.teamPicture !== null">
          <img :src="state.form.teamPicture.preview" alt="" height=100>
        </span>
      </el-form-item>
      <el-form-item prop="teamMember" label="팀원 수" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.teamMember" autocomplete="off" placeholder="팀원은 몇명이에요?"></el-input>
      </el-form-item>
    </el-form>


    <!-- footer -->
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="registerTeam">아지트 생성</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import jwt_decode from 'jwt-decode'

export default {
  name: 'register-team-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const registerTeamForm = ref(null)
    const inputImage = ref(null)
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      form: {
        align: 'left',
        teamName: '',
        teamPassword: '',
        teamDescription: '',
        teamPicture: null,
        teamMember: '',
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
      formLabelWidth: '120px',
    })

    const handleClose = function () {
      state.form.teamName = ''
      state.form.teamPassword = ''
      state.form.teamDescription = ''
      state.form.teamPicture = null
      state.form.teamMember = ''
      emit('closeRegisterTeamDialog')
    }

    const uploadTeamPicture = function () {
      console.log('이미지등록')
      for (let i = 0; i < inputImage.value.files.length; i++) {
        // console.log(URL.createObjectURL(inputImage.value.files[i]))
        // console.log(state.form.images)
        state.form.teamPicture = {
          file: inputImage.value.files[i],
          preview: URL.createObjectURL(inputImage.value.files[i])
        }
      }
      // handleClose()
    }

    const registerTeam = function () {
      const token = store.getters['root/getJWTToken']
      const userId = jwt_decode(token).sub
      console.log(state.form.teamPicture.preview)
      const teamDto = {
        'teamBoss': userId,
        'teamDescription': state.form.teamDescription,
        'teamMember': state.form.teamMember,
        'teamPicture': state.form.teamPicture.preview,
        'teamName': state.form.teamName,
        'teamPassword': state.form.teamPassword
      }

      store.dispatch('root/registerTeam', { 'teamDto': teamDto, 'userId': userId, 'token': token })
      .then(function (result) {
        alert('방 생성 성공')
        // 유저가 가입한 방 정보 갱신
        store.dispatch('root/getTeamInfo', token)
        .then(function (result) {
          store.commit('root/setUserTeam', result.data)
          handleClose()
        })
      })
      .catch(function (error) {
        console.log('실패')
        console.log(error)
      })
    }

    return { state, handleClose, registerTeamForm, inputImage, uploadTeamPicture, registerTeam}
  }
}
</script>

<style>
.register-team-dialog {
  width: 500px !important;
  height: 650px;
}
.register-team-dialog .checkbox .el-form-item__content {
  float: left;
  margin-left: 3rem !important;
}
.register-team-dialog .el-dialog__body {
  padding-bottom: 0px;
}
.register-team-dialog .el-dialog__headerbtn {
  float: right;
}
.register-team-dialog .el-form-item__content {
  margin-left: 0 !important;
  float: right;
  width: 300px;
  display: inline-block;
}
.register-team-dialog .el-form-item {
  margin-bottom: 20px;
}
.register-team-dialog .el-form-item__error {
  font-size: 12px;
  color: red;
}
.register-team-dialog .el-input__suffix {
  display: none;
}
.register-team-dialog .el-dialog__footer {
  margin: 0 calc(50% - 80px);
  padding-top: 0;
  display: inline-block;
}
.register-team-dialog .dialog-footer .el-button {
  width: 120px;
}
.register-team-dialog .el-checkbox a:hover {
  background-color: rgba(141, 29, 216, 0.322);
}
</style>
