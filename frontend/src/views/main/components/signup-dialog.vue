<template>
  <el-dialog custom-class="signup-dialog" title="회원가입" v-model="state.dialogVisible" @close="handleClose" :destroy-on-close="true" :close-on-click-modal="false" :close-on-press-escape="false">
    <!-- <marquee width="350px">멀리 떨어진 가족들과 추억을 나눠보세요!</marquee> -->
    <el-form :model="state.form" status-icon :rules="state.rules" ref="signupForm" :label-position="state.form.align">
      <el-form-item prop="id" label="아이디" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.id" autocomplete="off" style="width: 70%"></el-input>
        <el-button size="small" style="float: right; margin-top:5px;" @click="checkDup">중복 확인</el-button>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item prop="passwordConfirm" label="비밀번호 재확인" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.passwordConfirm" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item prop="name" label="이름" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="birthDate" label="생년월일" :label-width="state.formLabelWidth" >
        <el-date-picker
          style="width: 100%;"
          v-model="state.form.birthDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="Pick a day"
          :disabled-date="disabledDate"
          :shortcuts="shortcuts"
      >
      </el-date-picker>
      </el-form-item>
      <el-form-item prop="nickname" label="별명" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="agreement" style="margin: 0 auto 20px; width: 100%;" class="checkbox">
        <el-checkbox v-model="state.form.agreement">
          가입 시, 인프런의
            <router-link to="/customercenter" @click="clickCustomercenter">이용약관</router-link>,
            <!-- <a target="_blank" href="https://www.inflearn.com/policy/terms-of-service">이용약관</a>, -->
            <a target="_blank" href="https://www.inflearn.com/policy/privacy">개인정보취급방침</a>
          에 동의합니다
        </el-checkbox>
        <!-- <el-button type="text" center>가입 시, 가제의 이용약관 개인정보취급방침에 동의합니다.</el-button> -->
      </el-form-item>
    </el-form>


    <template #footer>
      <span class="dialog-footer">

        <el-button type="primary" @click="clickSignup">회원가입</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<style>
.signup-dialog {
  width: 500px !important;
  height: 550px;
}
.signup-dialog .checkbox .el-form-item__content {
  float: left;
  margin-left: 3rem !important;
}
.signup-dialog .el-dialog__body {
  padding-bottom: 0px;
}
.signup-dialog .el-dialog__headerbtn {
  float: right;
}
.signup-dialog .el-form-item__content {
  margin-left: 0 !important;
  float: right;
  width: 300px;
  display: inline-block;
}
.signup-dialog .el-form-item {
  margin-bottom: 20px;
}
.signup-dialog .el-form-item__error {
  font-size: 12px;
  color: red;
}
.signup-dialog .el-input__suffix {
  display: none;
}
.signup-dialog .el-dialog__footer {
  margin: 0 calc(50% - 80px);
  padding-top: 0;
  display: inline-block;
}
.signup-dialog .dialog-footer .el-button {
  width: 120px;
}
.signup-dialog .el-checkbox a:hover {
  background-color: rgba(141, 29, 216, 0.322);
}

</style>
<script>
import { reactive, computed, ref, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'signup-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    // 마운드 이후 바인딩 될 예정 - 컨텍스트에 노출시켜야함. <return>
    const signupForm = ref(null)

    // 비밀번호 확인 유효성 검사
    // 비밀번호(password)와 비밀번호 재확인(passwordConfirm)의 일치 유무 판별
    const validatePass = (rule, value) => {
      if (value !== state.form.password) {
        return new Error("비밀번호가 일치하지 않습니다.")
      } else {
        return true
      }
    }

    const validateAgree = (rule, value) => {
      if (!value) {
        return new Error("약관에 동의해주세요.")
      } else {
        return true
      }
    }

    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */
    const state = reactive({
      form: {
        agreement: false,
        id: '',
        password: '',
        passwordConfirm: '',
        name: '',
        nickname: '',
        align: 'left',
        birthDate: new Date(),
      },
      rules: {
        id: [
          { required: true, message: '아이디를 입력해주세요.', trigger: 'blur' },
          { min: 5, max: 16, message: '아이디의 길이는 5 ~ 16자 이내로 해주세요.', trigger: 'change' },
        ],
        password: [
          { required: true, message: '비밀번호를 입력해주세요.', trigger: 'blur' },
          // { min: 5, max: 16, message: '5 ~ 16자 영문, 숫자, 특수문자를 혼합하여 사용해주세요.', trigger: 'change' },
          { pattern: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{5,16}$/, message: '5 ~ 16자 영문, 숫자, 특수문자를 혼합하여 사용해주세요.'}
        ],
        passwordConfirm: [
          { required: true, message: '필수 정보입니다.', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' },
        ],
        name: [
          { required: true, message: '이름을 입력해주세요.', trigger: 'blur' },
          { min: 2, max: 10, message: '2 ~ 10자 이내로 해주세요.', trigger: 'change' },
          { pattern: /^[a-zA-zㄱ-ㅎ|ㅏ-ㅣ|가-힣]{2,10}$/, message: '한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)'}
        ],
        agreement: [
          { validator: validateAgree, message: '약관을 동의해주세요.'},
        ]
      },
      dialogVisible: computed(() => props.open),
      isAvailable: computed(function () {
        return signupForm.value.validate((valid) => valid)
      }),
      formLabelWidth: '120px',
    })

    onMounted(() => {
      // console.log(loginForm.value)
    })

    const clickSignup = function () {
      // 회원가입 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      console.log(state.isAvailable)
      signupForm.value.validate((valid) => {
        console.log(valid)
        if (valid) {
          const arr = state.form.birthDate.split("-")
          const year = arr[0]
          const month = arr[1]
          const day = arr[2]
          console.log(year, month, day)
          store.dispatch('root/requestRegister', { userId: state.form.id, password: state.form.password, name: state.form.name, year: year, month: month, day: day, nickName: state.form.nickname })
          .then(function (result) {
            console.log('로딩 스피너 넣으면 됨')
            alert('accessToken: ' + result.data.accessToken)
          })
          .catch(function (err) {
            alert(err)
          })
        } else {
          // swal('필수 항목을 입력해주세요.')
          swal({
            title: "회원가입 실패",
            text: "필수 항목을 입력해주세요.",
            icon: "error",
            button: "돌아가기",
          });
        }
      });
    }

    const handleClose = function () {
      state.form.id = ''
      state.form.password = ''
      state.form.passwordConfirm = ''
      state.form.name = ''
      state.form.nickname = ''
      state.form.agreement = false
      state.form.birthDate = new Date()
      emit('closeSignupDialog')
    }

    const clickCustomercenter = function () {
      store.commit('root/setMenuActive', 2)
      document.querySelector(".signup-dialog").style.visibility = "hidden"
    }

    const checkDup = function () {
      store.dispatch('root/checkDupId', { id: state.form.id, password: state.form.password, name: state.form.name })
      .then(res => {
        console.log('아이디 확인')
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
    }

    return { signupForm, state, clickSignup, handleClose, clickCustomercenter, checkDup }
  }
}
</script>
