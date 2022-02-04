<template>
  <div>
    <div class="d-flex flex-row" style="max-width: 1200px; margin-left: 350px; margin-top: 50px;">
      <div class="d-flex justify-content-between" style="width: 100%">
        <div class="col-md-3">
          <img src="https://cdn.pixabay.com/photo/2016/06/15/16/16/man-1459246_960_720.png" class="img-fluid rounded-start" alt="본인 이미지 하나 들어가면 괜찮을듯" style="height: 80%">
        </div>
        <div class="col-md-9" style="margin-left: 0px;">
          <div class="card-body">
            <h5 class="card-title" style="margin-bottom: 5%;">{{ state.info.name }}님의 프로필</h5>
            <p style="margin-bottom: 10px;">ID : {{ state.info.userId }}</p>
            <div style="margin-bottom: 10px;">
              <span>Nickname : {{ state.info.nickName }}</span> <el-button style=" min-height: 15px; padding: 10px; margin-left: 10px" @click="state.nicknameDialogOpen = true">수정하기</el-button>
            </div>
            <p>Birthday : {{ state.info.year}}년 {{ state.info.month}}월 {{ state.info.day}}일</p>
            <p class="card-text"><small class="text-muted">최초 가입일 : {{state.info.cdate.slice(2, 10)}} </small></p>
            <el-button type="danger">회원 탈퇴</el-button>
          </div>
        </div>
      </div>
        <div>
          <!-- <el-timeline>
            <el-timeline-item
              v-for="(activity, index) in activities"
              :key="index"
              :timestamp="activity.timestamp"
            >
              {{ activity.content }}
            </el-timeline-item>
          </el-timeline> -->
        </div>
      <div>
      </div>
    </div>
    <div style="max-width: 1200px; margin-top: 20px; margin-left: 100px margin-bottom: 100px; z-index: -1">
      <el-carousel :interval="4000" type="card" height="200px">
      <el-carousel-item v-for="item in 6" :key="item">
        <h3>{{ item }}</h3>
      </el-carousel-item>
      </el-carousel>
    </div>






    <nickname-dialog
      :open="state.nicknameDialogOpen"
      :info="state.info"
      @closeNicknameDialog="onCloseNicknameDialog"
      @edit-nickname="editNickname"
      />
  </div>
</template>


<script>
import { reactive, onBeforeMount } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import NicknameDialog from './components/nickname-dialog.vue'

export default {
  name: 'Profile',
  components: {
    NicknameDialog,
  },

  setup() {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      info: {
        name: '',
        nickName: '',
        userId: '',
        year: '',
        month: '',
        day: '',
        cdate: '1970-01-01'
      },
      nicknameDialogOpen : false,
    })

    onBeforeMount(() => {
      const token = store.getters['root/getJWTToken']
      store.dispatch('root/getProfile', token)
      .then(res => {
        state.info = res.data
        console.log(state.info)
      })
      .catch(err => {
        console.log(err)
      })
    })

    const takeProfile = function () {
      // const token = store.getters['root/getJWTToken']
      // store.dispatch('root/getProfile', token)
      // .then(res => {
      //   state.info = res.data
      //   console.log(state.info)
      // })
      // .catch(err => {
      //   console.log(err)
      // })
    }

    const editNickname = (nickname) => {
      state.info.nickName = nickname.nickname
    }

    // takeProfile()
    // console.log(typeof(state.info))

    const activities = [
      {
        content: '지난 약속',
        timestamp: '2018-04-15',
      },
      {
        content: '오늘 날짜',
        timestamp: '2018-04-13',
      },
      {
        content: '다음 약속',
        timestamp: '2018-04-11',
      },
    ]

    const onCloseNicknameDialog = function () {
      state.nicknameDialogOpen = false
    }








    return { store, router, takeProfile, state, activities, onCloseNicknameDialog, editNickname}
  }
}
</script>

<style>

</style>
