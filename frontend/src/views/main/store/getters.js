// 플랫폼 관련 정보 가져오기
export function getIsDesktopPlatform (state) {
  return state.isDesktopPlatform
}
// 메뉴 객체 가져오기
export function getMenus (state) {
	return state.menus
}
// Active된 메뉴 인덱스 가져오기
export function getActiveMenuIndex (state) {
	const keys = Object.keys(state.menus)
	return keys.findIndex(item => item === state.activeMenu)
}
// JWT 토큰 정보 가져오기
export function getJWTToken (state) {
  return state.JWTToken
}

export function getTeams (state) {
  return state.userTeams
}

export function getMessages (state) {
  return state.messages
}

export function getSecretName (state) {
  return state.secretName
}
