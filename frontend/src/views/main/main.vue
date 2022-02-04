<template>
  <!-- <el-container class="main-wrapper">
    <main-header
      :height="`70px`"
      @openLoginDialog="onOpenLoginDialog"
      @openSignupDialog="onOpenSignupDialog"
      style="position: fixed; top: 0; background-color: white;"
      />
    <el-container class="main-container">
      <el-aside class="hide-on-small" width="240px">
        <main-sidebar
          :width="`240px`"/>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
    <main-footer :height="`110px`"/>
  </el-container> -->
  <!-- <div class="main-wrapper"> -->
    <main-header
      :height="`70px`"
      @openLoginDialog="onOpenLoginDialog"
      @openSignupDialog="onOpenSignupDialog"
      style="position:fixed; top: 0; background-color: white;"
      />
    <div class="main-container" style="position: fixed; top: 70px;">
      <div class="hide-on-small" width="240px" style="height: 120%">
        <main-sidebar
          :width="`240px`"/>
      </div>
    </div>
    <main-footer
      :height="`80px`"
      style="position:fixed; bottom: 0; width: 100%; background-color: white;"/>
  <!-- </div> -->
  <div style="position: absolute; top: 90px; left: 260px; width: 80%;">
    <router-view @openLoginDialog="onOpenLoginDialog" @openSignupDialog="onOpenSignupDialog"></router-view>

  </div>
  <login-dialog
    :open="loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog"/>
  <signup-dialog
    :open="signupDialogOpen"
    @closeSignupDialog="onCloseSignupDialog"/>
</template>
<style>
  @import "https://unpkg.com/element-plus@1.0.2-beta.44/lib/theme-chalk/index.css";
  @import './main.css';
  @import '../../common/css/common.css';
  @import '../../common/css/element-plus.css';

   *{font-family: 'Jua', sans-serif;}


</style>
<script>
//import LoginDialog from './components/login-dialog'
import LoginDialog from './components/login-dialog'
import SignupDialog from './components/signup-dialog'
import MainHeader from './components/main-header'
import MainSidebar from './components/main-sidebar'
import MainFooter from './components/main-footer'

export default {
  name: 'Main',
  components: {
    MainHeader,
    MainSidebar,
    MainFooter,
    LoginDialog,
    SignupDialog
  },
  data () {
     return {
      loginDialogOpen: false,
      signupDialogOpen: false,
    }
  },
  methods: {
    onOpenLoginDialog () {
      this.loginDialogOpen = true
    },
    onCloseLoginDialog () {
      this.loginDialogOpen = false
    },
    onOpenSignupDialog () {
      this.signupDialogOpen = true
    },
    onCloseSignupDialog () {
      this.signupDialogOpen = false
    }
  },
}
</script>
