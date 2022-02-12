<template>
  <el-row
    class="main-sidebar"
    :gutter="10"
    :style="{ 'width': width, 'height': 'calc(100vh - 120px)' }">
    <div class="hide-on-small">
      <el-menu
        :default-active="String(state.activeIndex)"
        active-text-color="#ffd04b"
        class="el-menu-vertical-demo"
        @select="menuSelect">
        <!-- <el-menu-item v-for="(item, index) in state.menuItems" :key="index" :index="index">
          <i v-if="item.icon" :class="['ic', item.icon]"/>
          <span>{{index}} {{ item.title }}</span>
          <div v-if="index === 4" style="border-top: 1px solid #dcdfe6;"/>
        </el-menu-item> -->
        <el-scrollbar native id="sidebar">
          <el-menu-item v-for="(item, index) in state.userTeams" :key="index + state.menuItems.length" :index="index + state.menuItems.length">
            <!-- <i v-if="item.icon" :class="['ic', item.icon]"/> -->
            <!-- <span>{{index + state.menuItems.length}} {{ item.teamName }}</span> -->
            <span>{{ item.id }} {{ item.teamName }}</span>
            <!-- <div v-if="index === 4" style="border-top: 1px solid #dcdfe6;"/> -->
            <!-- <hr v-if="index === 4" style="color: black;"> -->
            <!-- <hr v-if="index === 4" style="color: black"> -->
          </el-menu-item>
          <el-button @click="registerTeam">방 생성 버튼</el-button>
          <!-- <el-button @click="state.registerTeamDialogOpen = true">방 생성 버튼</el-button> -->
        </el-scrollbar>
      </el-menu>
    </div>
  </el-row>


</template>
<style>
.main-sidebar .el-menu {
  margin-top: 0;
  padding-left: 0;
}
.main-sidebar .hide-on-small {
  height: 100%;
}
.main-sidebar .hide-on-small .el-menu {
  height: 100%;
}
.main-sidebar .el-menu .el-menu-item {
  cursor: pointer;
  border-right: none;
}
.main-sidebar .el-menu .el-menu-item .ic {
  margin-right: 5px;
}
#sidebar {
  height: calc(100% - 224px);
}
</style>
<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
// import jwt_decode from 'jwt-decode'



export default {
  name: 'main-sidebar',
  props: {
    width: {
      type: String,
      default: '240px'
    }
  },
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      searchValue: null,
      // registerTeamDialogOpen: false,
      isLogin: computed(() => store.getters['root/getJWTToken']),
      menuItems: computed(() => {
        const MenuItems = store.getters['root/getMenus']
        let keys = Object.keys(MenuItems)
        let menuArray = []
        for (let i = 0; i < keys.length; ++i) {
          let menuObject = {}
          menuObject.icon = MenuItems[keys[i]].icon
          menuObject.title = MenuItems[keys[i]].name
          menuArray.push(menuObject)
        }

        // const TeamItems = store.getters['root/getTeams']
        // console.log('유저가 가입한 팀 정보 라우터에 추가')
        // keys = Object.keys(TeamItems)
        // for (let i = 0; i < TeamItems.length; i++) {
        //   let menuObject = {}
        //   menuObject.icon = TeamItems[keys[i]].teamPicture
        //   menuObject.title = TeamItems[keys[i]].teamName
        //   menuArray.push(menuObject)
        // }
        // console.log('방 정보 리스트')
        // console.log(menuArray)
        return menuArray
      }),
      activeIndex: computed(() => store.getters['root/getActiveMenuIndex']),
      userTeams: computed(() => store.state.root.userTeams)

    })

    if (state.activeIndex === -1) {
      state.activeIndex = 0
      store.commit('root/setMenuActive', 0)
    }

    const menuSelect = function (param) {
      // store.commit('root/setMenuActive', param)
      console.log('방 클릭')
      console.log(param)
      // state.activeIndex = param
      // const MenuItems = [store.getters['root/getMenus'], store.getters['root/getTeams']]
      const getMenus = store.getters['root/getMenus']
      const getTeams = store.getters['root/getTeams']
      // console.log('방 정보')
      // console.log(getMenus[0])
      // console.log(getTeams)
      const MenuItems = store.getters['root/getMenus']
      // const MenuItems = Object.assign({}, getMenus, getTeams)
      // console.log(MenuItems)
      // let keys = Object.keys(MenuItems)
      const commonMenuKeys = Object.keys(MenuItems)
      if (param < commonMenuKeys.length) {
        router.push({
          name: commonMenuKeys[param]
        })
      } else {
        const key = param - commonMenuKeys.length
        const roomId = getTeams[key.toString()].id
        // const roomName = getTeams[key.toString()].teamName
        // const roomDescription = getTeams[key.toString()].teamDescription
        // const roomPicture = getTeams[key.toString()].teamPicture
        // console.log(roomId)
        router.push({
          name: 'room-board',
          params: {
            roomId: roomId,
            // roomName: roomName,
            // roomDescription: roomDescription,
            // roomPicture: roomPicture,
          },
        })
      }

      // if (state.activeIndex === 1 && state.isLogin === null) {
      //   setTimeout(function() {
      //     swal({
      //       title: "로그인이 필요한 서비스입니다.",
      //       text: "로그인 후 이용해주세요.",
      //       icon: "error",
      //       button: "돌아가기",
      //     });
      //     store.commit('root/setMenuActive', 0)
      //     // state.activeIndex = 0
      //     router.push({
      //       name: keys[0]
      //     })
      //   }, 100)
      // }
    }

    // 방 생성하기
    const registerTeam = () => {
      emit('openRegisterTeamDialog')
    }
    // const registerTeam = function () {
    //   emit('openRegisterTeamDialog')
    //   const token = store.getters['root/getJWTToken']
    //   const userId = jwt_decode(token).sub
    //   console.log(userId)
    //   const teamDto = {
    //     'teamBoss': userId,
    //     'teamDescription': '간단한 소개',
    //     'teamMember': 0,
    //     'teamPicture': '',
    //     'teamName': '연습용 방123',
    //     'teamPassword': 'qwer1234!'
    //   }

    //   store.dispatch('root/registerTeam', { 'teamDto': teamDto, 'userId': userId, 'token': token })
    //   .then(function (result) {
    //     console.log('성공')
    //     console.log(result)
    //   })
    //   .catch(function (error) {
    //     console.log('실패')
    //     console.log(error)
    //   })
    // }

    const onCloseRegisterTeamDialog = function () {
      // state.registerTeamDialogOpen = false
    }

    const onRegisterSuccess = function () {
      console.log('adsjfoiasdjfoiasdjfaspjdf')
      console.log(state.userTeams)
    }

    return { state, menuSelect, registerTeam, onCloseRegisterTeamDialog, onRegisterSuccess }
  }
}
</script>
