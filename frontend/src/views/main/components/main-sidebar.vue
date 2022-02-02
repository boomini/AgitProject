<template>
  <el-row
    class="main-sidebar"
    :gutter="10"
    :style="{ 'width': width }">
    <div class="hide-on-small">
      <el-menu
        :default-active="String(state.activeIndex)"
        active-text-color="#ffd04b"
        class="el-menu-vertical-demo"
        @select="menuSelect">
        <el-menu-item v-for="(item, index) in state.menuItems" :key="index" :index="index.toString()">
          <i v-if="item.icon" :class="['ic', item.icon]"/>
          <span>{{ item.title }}</span>
          <div v-if="index === 4" style="border-top: 1px solid #dcdfe6;"/>
          <!-- <hr v-if="index === 4" style="color: black;"> -->
          <!-- <hr v-if="index === 4" style="color: black"> -->
        </el-menu-item>
        <el-menu-item>
          <el-button>방 생성 버튼</el-button>
        </el-menu-item>
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
</style>
<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'main-header',

  props: {
    width: {
      type: String,
      default: '240px'
    }
  },
  setup() {
    const store = useStore()
    const router = useRouter()


    const state = reactive({
      searchValue: null,
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

        const TeamItems = store.getters['root/getTeams']
        console.log('11111111111111111')
        console.log(TeamItems)
        console.log(MenuItems)

        return menuArray
      }),
      activeIndex: computed(() => store.getters['root/getActiveMenuIndex']),

    })

    if (state.activeIndex === -1) {
      state.activeIndex = 0
      store.commit('root/setMenuActive', 0)
    }

    const menuSelect = function (param) {
      store.commit('root/setMenuActive', param)
      // state.activeIndex = param
      const MenuItems = store.getters['root/getMenus']
      let keys = Object.keys(MenuItems)
      // console.log(param)
      router.push({
        name: keys[param]
      })

      if (state.activeIndex === 1 && state.isLogin === null) {
        setTimeout(function() {
          swal({
            title: "로그인이 필요한 서비스입니다.",
            text: "로그인 후 이용해주세요.",
            icon: "error",
            button: "돌아가기",
          });
          store.commit('root/setMenuActive', 0)
          // state.activeIndex = 0
          router.push({
            name: keys[0]
          })
        }, 100)
      }
    }

    return { state, menuSelect }
  }
}
</script>
