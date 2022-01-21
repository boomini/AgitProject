// API
import $axios from 'axios'

// 로그인 요청
export function requestLogin ({ state }, payload) {
  console.log('requestLogin', state, payload)
  const url = '/auth/login'
  let body = payload
  return $axios.post(url, body)
}

// 회원가입 요청
export function requestRegister ({ state }, payload) {
  console.log('requestRegister', state, payload)
  const url = '/user'
  let body = payload
  return $axios.post(url, body)
}

// 중복 아이디 체크
export function checkDupId ({ state }, payload) {
  console.log('checkDupId', state, payload.userId)
  const url = `/user/${payload.userId}`
  console.log(url)
  // let body = payload
  return $axios.get(url)
}

// 메인 페이지 정보 가져오기
export function getProfile ({ state }, payload) {
  console.log('getProfile', state, payload)
  const url = '/user/me'
  const token = payload
  return $axios({ method: 'get', url: url, headers: { Authorization: `Bearer ${token}`}})
}
