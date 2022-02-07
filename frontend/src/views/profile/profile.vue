<template>
  <div>
    <div class="d-flex flex-row" style="max-width: 1200px; margin-left: 350px; margin-top: 50px;">
      <div class="d-flex justify-content-between" style="width: 100%">
        <div class="col-md-3">
          <img src="https://cdn.pixabay.com/photo/2016/06/15/16/16/man-1459246_960_720.png" class="img-fluid rounded-start" alt="본인 이미지 하나 들어가면 괜찮을듯" style="height: 80%">
        </div>
        <div class="col-md-9" style="margin-left: 0px;">
          <div class="card-body">
            <h5 class="card-title" style="margin-bottom: 5%;">{{ state.info.userId }}님의 프로필</h5>
            <p style="margin-bottom: 10px;">ID : {{ state.info.userId }}</p>
            <div style="margin-bottom: 10px;">
              <span>Nickname : {{ state.info.nickName }}</span> <el-button style=" min-height: 15px; padding: 10px; margin-left: 10px" @click="state.nicknameDialogOpen = true">수정하기</el-button>
            </div>
            <p>Birthday : {{ state.info.birthday }}</p>
            <p class="card-text"><small class="text-muted">최초 가입일 : {{state.info.cdate.slice(2, 10)}} </small></p>
            <el-button type="danger" @click="deleteuserId">회원 탈퇴</el-button>
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
    <!-- <div style="max-width: 1200px; margin-top: 20px; margin-left: 100px margin-bottom: 100px; z-index: -1">
      <el-carousel :interval="4000" type="card" height="200px">
      <el-carousel-item v-for="item in 6" :key="item">
        <h3>{{ item }}</h3>
      </el-carousel-item>
      </el-carousel>
    </div> -->

    <nickname-dialog
      :open="state.nicknameDialogOpen"
      :info="state.info"
      @closeNicknameDialog="onCloseNicknameDialog"
      @edit-nickname="editNickname"
      />

    <birthday-dialog
    :open="state.birthdayDialogOpen"
    :info="state.info"
    @closeBirthdayDialog="onCloseBirthdayDialog"
    @create-birthday="createBirthday"
    />
  </div>
</template>


<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import NicknameDialog from './components/nickname-dialog.vue'
import BirthdayDialog from './components/birthday-dialog.vue'

export default {
  name: 'Profile',
  components: {
    NicknameDialog,
    BirthdayDialog,
  },

  setup() {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      info: {
        birthday: '',
        name: '',
        nickName: '',
        userId: '',
        id: '',
        cdate: '1970-01-01',
        emailType: '',
        password: ''
      },
      // info: null,
      nicknameDialogOpen : false,
      birthdayDialogOpen : false,
    })

    const takeProfile = function () {
      const token = store.getters['root/getJWTToken']
      store.dispatch('root/getProfile', token)
      .then(res => {
        state.info = res.data
        console.log(res)
        if (state.info.birthday == null) {
          state.birthdayDialogOpen = true
        }
      })
      .catch(err => {
        console.log(err)
      })
    }

    const editNickname = (nickname) => {
      state.info.nickName = nickname.nickname
    }

    const createBirthday = (birthday) => {
      state.info.birthday = birthday.birthday
    }

    const deleteuserId = function () {
      const token = store.getters['root/getJWTToken']
      const body = {
        'userId': state.info.userId,
      }
      store.dispatch('root/deleteUser',{ 'body': body, 'token': token})
      .then(res => {
          setTimeout(() => {
                swal({
                  title: '회원탈퇴',
                  text: '이용해주셔서 감사합니다.',
                  icon: 'success',
                  button: '확인',
                });
              }, 500)

              console.log(res)

              store.commit('root/setJWTTokenReset')
              localStorage.removeItem('JWT')
              store.commit('root/setMenuActive', 0)
              router.push({
                name: 'home',
              })

              // router.go(router.currentRoute)

        })
        .catch(err => {
          console.log(err)
        })
    }

    takeProfile()
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

    const onCloseBirthdayDialog = function () {
      state.birthdayDialogOpen = false
    }








    return { store, router, takeProfile, state, activities, onCloseNicknameDialog, editNickname, deleteuserId, onCloseBirthdayDialog, createBirthday}
  }
}
</script>

<style>

</style>
