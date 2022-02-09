// API
import $axios from 'axios'

// User
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

// 이메일로 보낸 인증 번호 가져오기
export function getAuthNumber ({ state }, payload) {
  console.log('authNumber', state, payload)
  const url = `/email/check/auth/sendEmail?userEmail=${payload.userEmail}`
  console.log(url);
  return $axios({method:'get', url: url})

}

// 닉네임 수정
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

//google토큰확인
export function verifyToken ({state},payload){
  console.log('verifyToken', state, payload)
  const url='auth/token';
  let body=payload;
  return $axios.post(url,body)
}

// 회원탈퇴
export function deleteUser ({ state }, payload) {
  const userId = payload.body.userId
  const url = '/user/delete'
  const token = payload.token
  const body = payload.body
  console.log(body)
  return $axios({ method: 'delete', url: url, headers: { Authorization: `Bearer ${token}`}, data: body})
}

// Team
// 로그인한 유저가 소속된 팀 정보 가져오기
export function getTeamInfo ({ state }, payload) {
  console.log('getTeamInfo', state, payload)
  const url = '/user/teamList'
  const token = payload
  return $axios({ method: 'get', url: url, headers: { Authorization: `Bearer ${token}`}})
}

//해당팀 정보 가져오기
export function getTeamInfoDetail ({ state }, payload) {
  console.log('getTeamInfoDetail', state, payload)
  const url = `/team/info/${payload}`
  return $axios({ method: 'get', url: url})
}

// 팀 생성하기
export function registerTeam ({ state }, payload) {
  console.log('registerTeam', state, payload)
  const url = `/team?userId=${payload.userId}`
  const teamDto = payload.teamDto
  const token = payload.token
  return $axios({ method: 'post', url: url, data: teamDto, headers: { Authorization: `Bearer ${token}`}})
}

//팀원초대하기
export function addTeamMember ({ state }, payload) {
  console.log('addTeamMember', state, payload)
  const url = `/team/${payload.teamId}/member/${payload.userId}`
  return $axios({ method: 'get', url: url})
}

//팀원 state 확인하기
export function checkTeamMember ({ state }, payload) {
  console.log('checkTeamMember', state, payload)
  const url = `/team/check/${payload.teamId}`
  const token = payload.token;
  return $axios({ method: 'get', url: url,headers: { Authorization: `Bearer ${token}`}})
}

//팀원 state 수락 확인하기
export function confirmTeamMember ({ state }, payload) {
  console.log('confirmTeamMember', state, payload)
  const url = `/team/confirm/${payload.teamId}`
  const token = payload.token;
  return $axios({ method: 'get', url: url,headers: { Authorization: `Bearer ${token}`}})
}

//팀원 state 거절 확인하기
export function rejectTeamMember ({ state }, payload) {
  console.log('rejectTeamMember', state, payload)
  const url = `/team/reject/${payload.teamId}`
  const token = payload.token;
  return $axios({ method: 'get', url: url,headers: { Authorization: `Bearer ${token}`}})
}

// 팀원 조회
export function takeMember ({ state }, payload) {
  console.log(payload.body)
  console.log('안돠나')
  const teamId = payload.body.teamId
  const url = `/team/${teamId}/users`
  console.log(url)
  // const token = payload.token
  // const body = payload.body
  // return $axios({ method: 'get', url: url, headers: { Authorization: `Bearer ${token}`}})
  return $axios({ method: 'get', url: url})
}


// 게시판
// 특정 달의 달력 개요(일정) 가져오기
export function getEventCount({ state }, payload) {
  const teamId = payload.teamId
  const reqDate = payload.reqDate
  const url = `team/${teamId}/teamEvents/${reqDate}`
  return $axios({
    method: 'get',
    url: url
  })
}

// 특정 일자 게시판 조회
export function getBoardDetail ({ state }, payload) {
  console.log('특정 일자 게시판 조회 함수')
  const teamId = payload.teamId
  const uploadDate = payload.uploadDate
  const url = `/team/${teamId}/${uploadDate}`
  return $axios({
    method: 'get',
    url: url
  })
}

// 특정 달의 달력 개요 가져오기
export function getCategoryCount({ state }, payload) {
  const teamId = parseInt(payload.teamId)
  const uploadDate = payload.uploadDate
  const url = `/team/${teamId}/count/${uploadDate}`
  return $axios({
    method: 'get',
    url: url
  })
}

// 게시글
// 게시글 작성
export function addArticle ({ state }, payload) {
  console.log('게시글 작성 함수')
  console.log(payload)
  const url = '/article'
  const body = payload.body
  return $axios({
    method: 'post',
    url: url,
    data: body
  })
}

// 유저 한달 일정 가져오기
export function getSchedule ({ state }, payload) {
  // console.log('getTeamInfo', state, payload)
  console.log(payload)
  const url = 'event/user/month'
  const token = payload
  return $axios({ method: 'get', url: url, headers: { Authorization: `Bearer ${token}`}})
}

// 스케줄 추가하기
export function createSchedule ({ state }, payload) {
  console.log(payload)
  const teamId = payload.teamId
  // const url = `/event/${teamId}`
  const body = payload.body
  const url = `/event/${teamId}`
  // const url = `/event/${teamId}?eventContent=${body.eventContent}&eventTitle=${body.eventTitle}&eventDate=${body.eventDate}`
  const token = payload.token
  return $axios({ method: 'post', url: url, data: body, headers: { Authorization: `Bearer ${token}`}})
}

// 이미지
// 업로드 이미지
export function uploadImage ({state}, payload){
  console.log(state);
  console.log(payload.token);
  const header = {
    Authorization : `Bearer ${payload.token}`,
    'Content-Type' : 'multipart/form-data',
  }
  console.log(payload.formData.get('upfile'));
  console.log(payload.formData.get('uploadDate'));
  console.log(payload.formData.get('teamId'));
  return $axios.post('/image', payload.formData,{header})
}

//이미지리스트
export function getListImage ({state}, payload){
  const url = `/image/${payload.teamId}/${payload.uploadDate}`
  return $axios({
    method: 'get',
    url: url
  })
}

// 비디오
// 업로드 비디오
export function uploadVideo ({state}, payload){
  console.log(state);
  console.log(payload.token);
  const header = {
    Authorization : `Bearer ${payload.token}`,
    'Content-Type' : 'multipart/form-data',
  }
  console.log(payload.formData.get('upfile'));
  console.log(payload.formData.get('uploadDate'));
  console.log(payload.formData.get('teamId'));
  return $axios.post('/video', payload.formData,{header})
}




// WebRtc
// 채팅
export function sendMessage({state}, payload){
  console.log(payload)
  var messageData = {
    content: payload.msssage,
    secretName: state.secretName
  }
  state.session.signal({
    type: 'chat',
    data: JSON.stringify(messageData),
    to: [],
  })
}


