import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/home/home'
import ConferencesDetail from '@/views/conferences/conference-detail'
import History from '@/views/history/history'
import Customercenter from '@/views/customercenter/service-info'
import Intro from '@/views/intro/intro'
import Schedule from '@/views/schedule/schedule'
import Room from '@/views/room/room'

const fullMenu = require('@/views/main/menu.json')
function makeRoutesFromMenu () {
  let routes = Object.keys(fullMenu).map((key) => {
    if (key === 'home') {
      return { path: fullMenu[key].path, name: key, component: Home  }
    } else if (key === 'history') {
      return { path: fullMenu[key].path, name: key, component: History }
    } else if (key === 'customercenter') {
      return { path: fullMenu[key].path, name: key, component: Customercenter }
    } else if (key === 'intro') {
      return { path: fullMenu[key].path, name: key, component: Intro }
    } else if (key === 'schedule') {
      return { path: fullMenu[key].path, name: key, component: Schedule }
    } else if (key === 'room1') {
      return { path: fullMenu[key].path, name: key, component: Room, params: { roomId: fullMenu[key].params.roomId} }
    } else { // menu.json 에 들어있는 로그아웃 메뉴
      return null
    }
  })
  // 로그아웃 파싱한 부분 제거
  routes = routes.filter(item => item)
  // menu 자체에는 나오지 않는 페이지 라우터에 추가(방 상세보기)
  routes.push({
    path: '/conferences/:conferenceId',
    name: 'conference-detail',
    component: ConferencesDetail
  })

  routes.push({
    path: '/room/:roomId',
    name: 'room-board',
    component: Room
  })

  return routes
}

const routes = makeRoutesFromMenu()

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.afterEach((to) => {
  console.log(to)
})

export default router
