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

// 이메일로 보낸 인증 번호 가져오기
export function getAuthNumber ({ state }, payload) {
  console.log('authNumber', state, payload)
  const url = `/email/check/auth/sendEmail?userEmail=${payload.userEmail}`
  console.log(url);
  return $axios({method:'get', url: url})

}

// 메인 페이지 정보 가져오기
export function getProfile ({ state }, payload) {
  console.log('getProfile', state, payload)
  const url = '/user/me'
  const token = payload
  return $axios({ method: 'get', url: url, headers: { Authorization: `Bearer ${token}`}})
}

// 로그인한 유저가 소속된 팀 정보 가져오기
export function getTeamInfo ({ state }, payload) {
  console.log('getTeamInfo', state, payload)
  const url = '/user/teamList'
  const token = payload
  return $axios({ method: 'get', url: url, headers: { Authorization: `Bearer ${token}`}})
}

// 팀 생성하기
export function registerTeam ({ state }, payload) {
  console.log('registerTeam', state, payload)
  const url = `/team?userId=${payload.userId}`
  const teamDto = payload.teamDto
  const token = payload.token
  return $axios({ method: 'post', url: url, data: teamDto, headers: { Authorization: `Bearer ${token}`}})
}

// 특정 팀 정보 가져오기
export function getTeamDetail ({ state }, payload) {
  console.log('getTeamDetail', state, payload)
}
export function editProfile ({ state }, payload) {
  // console.log('editProfile', state, payload)
  // console.log(payload)
  const url = '/user'
  const token = payload.token
  const body = payload.body
  // console.log(body)
  return $axios({ method: 'patch', url: url, headers: { Authorization: `Bearer ${token}`}, data: body})
  // return $axios.patch(url, body)
}

