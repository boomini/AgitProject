<template>
  <el-dialog custom-class="login-dialog" v-model="state.dialogVisible" @close="handleClose" :modal="state.form.modalStatus">
    <!-- header -->
    <template #title>
      <span>
        로 그 인
      </span>
    </template>

    <!-- cpntent -->
    <el-form :model="state.form" :rules="state.rules" ref="loginForm" :label-position="state.form.align">
      <el-form-item prop="id" label="아이디" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.id" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password" autocomplete="off" show-password @keyup.enter="clickLogin"></el-input>
      </el-form-item>
    </el-form>

    <!-- footer -->
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickLogin" v-loading.fullscreen="loading">로그인</el-button>
      </span>
      <div>
        아아아아아아
      </div>
    </template>
  </el-dialog>
</template>
<style>
.login-dialog {
  width: 400px !important;
  height: 280px;
}
.login-dialog .el-dialog__headerbtn {
  float: right;
}
.login-dialog .el-form-item__content {
  margin-left: 0 !important;
  float: right;
  width: 200px;
  display: inline-block;
}
.login-dialog .el-form-item {
  margin-bottom: 20px;
}
.login-dialog .el-form-item__error {
  font-size: 12px;
  color: red;
}
.login-dialog .el-input__suffix {
  display: none;
}
.login-dialog .el-dialog__footer {
  margin: 0 calc(50% - 80px);
  padding-top: 0;
  display: inline-block;
}
.login-dialog .dialog-footer .el-button {
  width: 120px;
}
</style>
<script>
import { reactive, computed, ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'login-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    // 마운드 이후 바인딩 될 예정 - 컨텍스트에 노출시켜야함. <return>
    const loginForm = ref(null)
    const router = useRouter()
    // const dialogVisible = ref(false)
    const loading = ref(false) // 로딩 스피너를 보여줄 변수. true면 로딩화면을 사용자에게 보여줌.

    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */
    const state = reactive({
      form: {
        id: '',
        password: '',
        align: 'left',
        modalStatus: true
      },
      rules: {
        id: [
          { required: true, message: '아이디를 입력해주세요.', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '비밀번호를 입력해주세요.', trigger: 'blur' }
        ]
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px'
    })

    onMounted(() => {
      // console.log(loginForm.value)
    })


    const clickLogin = function () {
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      loginForm.value.validate((valid) => {
        if (valid) {
          console.log('submit')
          loading.value = true
          const userId = state.form.id
          const userPw = state.form.password
          store.dispatch('root/requestLogin', { userId: userId, password: userPw })
          .then(function (result) {
            router.push({ name: 'home' })
            store.commit('root/setMenuActive', 0)

            localStorage.setItem('JWT', result.data.accessToken)
            store.commit('root/setJWTToken', result.data.accessToken)

            // 로그인한 유저가 가입한 팀 정보 가져오기
            const token = store.getters['root/getJWTToken']
            store.dispatch('root/getTeamInfo', token)
            .then(function (result) {
              store.commit('root/setUserTeam', result.data)
              // console.l
              console.log('회원 팀정보 가져오기')
              // console.log(result.data)
              console.log(store.getters['root/getTeams'])
              console.log('팀정보 가져오기 성공')
            })

            handleClose() // 로그인 모달 끄기
          })
          // 로딩 스피너를 바로 꺼버리면 사용자가 볼 수 없으므로
          // 작업 중인 것을 볼 수 있도록 조금의 여유를 주고 로딩 스피너를 끔.
          .then(() => {
            setTimeout(() => {
              loading.value = false
            }, 500)
          })
          .catch(function (err) {
            setTimeout(() => {
              loading.value = false
              swal({
                title: "아이디 혹은 비밀번호가 틀렸습니다.",
                text: "회원정보를 올바르게 입력했는지 확인해주세요.",
                icon: "error",
                button: "확인",
              });
            }, 500)
          })
        }
      })
    }

    const handleClose = function () {
      state.form.id = ''
      state.form.password = ''
      emit('closeLoginDialog')
    }

    return { loginForm, state, clickLogin, handleClose, loading }
  }
}
</script>
