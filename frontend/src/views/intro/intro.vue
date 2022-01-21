<template>
  <div id="container">


    <div class="hello">
      <div class="d-flex flex-row-reverse" style="margin-top:20px; margin-right: 30px;">
        <el-row :gutter="20">
          <!-- v-if문으로 수정 예정 -->
          <el-button @click="clickSignup" style="color: #32BEBE;">회원가입</el-button>
          <el-button @click="clickLogin" style="color: #32BEBE;">로그인</el-button>
         </el-row>
      </div>
      <!-- card 1 -->
      <div
        data-aos="fade-left"
        class="card d-flex  flex-row border border-white"
        style="width: 70%; height: 100%; margin-left: auto; margin-right: auto; margin-top: 30px; z-index: -1; padding-top: 0px"
      >
      <!-- 카드가 가운데로 오게 할려면 left margin 400px -->
        <img
          src="https://cdn.pixabay.com/photo/2019/09/02/15/58/family-4447538_960_720.jpg"
          class="card-img-top"
          alt="..."
          style="width:60%"
        />
        <div class="card-body" style="margin-left:80px">
          <h5 class="card-title" style="margin-top: 48%">아지트(Agit) 소개 <i class="fas fa-home" style="color: #FFD732"></i></h5>
          <p class="card-text" style="color: #808080; width:100%;">
            아지트는 가족, 연인, 친구들의 아름다운 추억을 간직하기 위한 서비스를 제공합니다.
          </p>
          <p class="card-text" style="color: #808080; width:100%;">
            다양한 추억을 공유해보세요.
          </p>

        </div>
      </div>

      <br><br><br><br><br><br>
      <!-- 공백은 br 6개 -->

      <div
        data-aos="fade-right"
        class="card d-flex  flex-row border border-white"
        style="width: 70%; height: 100%; margin-left: auto; margin-right: auto; margin-top: 150px; z-index: -1"

      >
      <!-- 카드가 가운데로 오게 할려면 left margin 400px -->
        <div class="card-body" style="margin-right:100px">
          <h5 class="card-title" style="margin-top: 48%">한 눈에 보는 일정 관리 <i class="fas fa-book" style="color: #4646CD"></i></h5>
          <p class="card-text" style="color: #808080">
            나의 스케줄을 한 눈에 확인 해보세요.
          </p>

        </div>
        <img
          src="https://cdn.pixabay.com/photo/2019/05/15/18/53/agenda-4205694_960_720.jpg"
          class="card-img-top"
          alt="..."
          style="width:60%"
        />
      </div>

      <br><br><br><br><br><br>
      <!-- 공백은 br 6개 -->

       <div
        data-aos="fade-left"
        class="card d-flex  flex-row border border-white"
        style="width: 70%; height: 100%; margin-left: auto; margin-right: auto; margin-top: 150px; z-index: -1"

      >
      <!-- 카드가 가운데로 오게 할려면 left margin 400px -->
        <img
          src="https://cdn.pixabay.com/photo/2016/06/29/21/14/women-1487825_960_720.jpg"
          class="card-img-top"
          alt="..."
          style="width:60%"
        />
        <div class="card-body" style="margin-left:80px">
          <h5 class="card-title" style="margin-top: 48%">온라인 모임 <i class="fas fa-smile-wink" style="color: #FF6A89"></i></h5>
          <p class="card-text" style="color: #808080; width:100%;">
            화상으로 반가운 얼굴들을 만날 수 있습니다.
          </p>

        </div>
      </div>

      <br><br><br><br><br><br>
      <!-- 공백은 br 6개 -->

      <div
        data-aos="fade-right"
        class="card d-flex  flex-row border border-white"
        style="width: 70%; height: 100%; margin-left: auto; margin-right: auto;  margin-top: 150px; z-index: -1"

      >
      <!-- 카드가 가운데로 오게 할려면 left margin 400px -->

        <div class="card-body" style="margin-right:100px">
          <h5 class="card-title" style="margin-top: 48%">나만의 추억 앨범 <i class="fas fa-heart" style="color:red;"></i></h5>
          <p class="card-text" style="color: #808080">
            사진, 동영상, 일정 등을 앨범에 담아보세요.
          </p>

        </div>
        <img
          src="https://cdn.pixabay.com/photo/2019/12/16/10/45/postage-stamps-4699092_960_720.jpg"
          class="card-img-top"
          alt="..."
          style="width:60%"
        />
      </div>
    </div>

    <br><br><br><br>


    <div
      data-aos="zoom-in"
      style="width: 18rem; margin: 100px auto;"
      class="d-flex flex-column align-items-center"
    >
      <img
          src="https://cdn.pixabay.com/photo/2019/11/18/06/43/flowers-4634053_960_720.png"
          alt="..."
          style="width:230%; height: 150%;"
        />
      <div style="position: absolute;">
        <img :src="require(`@/assets/images/agit_logo.png`)" alt="logo image" style="width:90%; height: 90%; margin-left: 10%; margin-top: 65%">
        <div style="width:90%; height: 90%; margin-left: 24%; margin-top: 12%">
          <el-button @click="clickSignup" style="color: #32BEBE; background-color: #FFFA82">회원가입</el-button>
          <el-button @click="clickLogin" style="color: #32BEBE; background-color: #FFFA82;">로그인</el-button>
        </div>
      </div>


    </div>
  </div>


</template>




<script>
import { onMounted } from 'vue'
import { useStore } from 'vuex'
import AOS from 'aos';
import 'aos/dist/aos.css';



export default {
  name: 'Intro',
  setup (props, { emit }) {
    const store = useStore()

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      AOS.init({
        duration: 1200,
      })
      // AOS.refresh()
      store.commit('root/setMenuActiveMenuName', 'intro')

    })

    const clickLogin = () => {
      emit('openLoginDialog')
    }

    const clickSignup = () => {
      emit('openSignupDialog')
    }

    return { clickLogin, clickSignup }
  }
}
</script>

<style scoped>
#container {
  position: absolute;
  top: -90px;
  left: -260px;
  margin-left: 0;
  margin-top: 0;
  z-index: 10;
  background-color: white;
  width: 125%;
}
</style>
