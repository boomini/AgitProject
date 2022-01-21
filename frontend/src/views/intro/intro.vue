<template>
  <div id="container">
    <div class="hello">
      <!-- card 1 -->
      <div
        data-aos="fade-left"
        class="card d-flex  flex-row border border-white"
        style="width: 50%; height: 500px; margin-left: auto; margin-right: auto; margin-top: 150px; z-index: -1"
      >
      <!-- 카드가 가운데로 오게 할려면 left margin 400px -->
        <img
          src="https://cdn.pixabay.com/photo/2019/09/02/15/58/family-4447538_960_720.jpg"
          class="card-img-top"
          alt="..."
          style="width:60%"
        />
        <div class="card-body" style="margin-left:80px">
          <h5 class="card-title" style="margin-top: 48%">아지트(Agit) 소개 </h5>
          <p class="card-text" style="color: #808080; width:100%;">
            아지트는 가족, 연인, 친구들의 아름다운 추억을 간직하기 위한 서비스를 제공합니다.
          </p>
          <p class="card-text" style="color: #808080; width:100%;">
            다양한 추억을 공유해보세요.
          </p>
          <a href="#" class="btn btn-warning" >회원가입</a>
        </div>
      </div>

      <br><br><br><br><br><br>
      <!-- 공백은 br 6개 -->

      <div
        data-aos="fade-right"
        class="card d-flex  flex-row border border-white"
        style="width: 50%; height: 500px; margin-left: auto; margin-right: auto; margin-top: 150px; z-index: -1"

      >
      <!-- 카드가 가운데로 오게 할려면 left margin 400px -->
        <div class="card-body" style="margin-right:100px">
          <h5 class="card-title" style="margin-top: 48%">한 눈에 보는 일정 관리</h5>
          <p class="card-text" style="color: #808080">
            나의 스케줄을 한 눈에 확인 해보세요.
          </p>
          <a href="#" class="btn btn-warning" >회원가입</a>
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
        style="width: 50%; height: 500px; margin-left: auto; margin-right: auto; margin-top: 150px; z-index: -1"

      >
      <!-- 카드가 가운데로 오게 할려면 left margin 400px -->
        <img
          src="https://cdn.pixabay.com/photo/2016/06/29/21/14/women-1487825_960_720.jpg"
          class="card-img-top"
          alt="..."
          style="width:60%"
        />
        <div class="card-body" style="margin-left:80px">
          <h5 class="card-title" style="margin-top: 48%">온라인 모임</h5>
          <p class="card-text" style="color: #808080; width:100%;">
            화상으로 반가운 얼굴들을 만날 수 있습니다.
          </p>
          <a href="#" class="btn btn-warning" >회원가입</a>
        </div>
      </div>

      <br><br><br><br><br><br>
      <!-- 공백은 br 6개 -->

      <div
        data-aos="fade-right"
        class="card d-flex  flex-row border border-white"
        style="width: 50%; height: 500px; margin-left: auto; margin-right: auto;  margin-top: 150px; z-index: -1"

      >
      <!-- 카드가 가운데로 오게 할려면 left margin 400px -->
        <div class="card-body" style="margin-right:100px">
          <h5 class="card-title" style="margin-top: 48%">나만의 추억 앨범 <i class="fas fa-heart" style="color:red;"></i></h5>
          <p class="card-text" style="color: #808080">
            사진, 동영상, 일정 등을 앨범에 담아보세요.
          </p>
          <a href="#" class="btn btn-warning" >회원가입</a>
        </div>
        <img
          src="https://cdn.pixabay.com/photo/2019/12/16/10/45/postage-stamps-4699092_960_720.jpg"
          class="card-img-top"
          alt="..."
          style="width:60%"
        />
      </div>
    </div>
    <div
      data-aos="zoom-in"
      style="width: 18rem; margin: 200px auto;"
    >
      <el-button @click="clickSignup">회원가입하러 가기</el-button>
      <el-button @click="clickLogin">로그인하기</el-button>
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
