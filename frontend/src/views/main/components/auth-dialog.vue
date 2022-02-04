<template>
  <el-dialog custom-class="signup-dialog" v-model="state.dialogVisible" @close="handleClose" :destroy-on-close="true" :close-on-click-modal="false" :close-on-press-escape="false" top="100px">
    <!-- header -->
    <template #title>
      <span>
        인증
      </span>
    </template>

    <!-- content -->
    <el-form :model="state.form" status-icon :rules="state.rules" ref="signupForm" :label-position="state.form.align">
      <el-form-item prop="id" label="아이디" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.id" autocomplete="off" style="width: 70%" :disabled="state.form.isValidatedId" id="id-input" placeholder="ID"></el-input>
        <el-button size="small" style="float: right; margin-top:5px;" @click="checkDup" :disabled="state.form.isValidatedId">중복 확인</el-button>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password" autocomplete="off" show-password placeholder="Password"></el-input>
      </el-form-item>
      <el-form-item prop="passwordConfirm" label="비밀번호 재확인" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.passwordConfirm" autocomplete="off" show-password placeholder="Password Confirmation"></el-input>
      </el-form-item>
      <el-form-item prop="name" label="이름" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.name" autocomplete="off" placeholder="Name"></el-input>
      </el-form-item>
      <el-form-item prop="birthDate" label="생년월일" :label-width="state.formLabelWidth" >
        <el-date-picker
          style="width: 100%;"
          v-model="state.form.birthDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="Birthday"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item prop="nickname" label="별명" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.nickname" autocomplete="off" placeholder="Nickname"></el-input>
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

    <!-- footer -->
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickSignup" v-loading.fullscreen="loading">회원가입</el-button>
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
