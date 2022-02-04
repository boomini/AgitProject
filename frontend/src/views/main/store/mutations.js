export function setPlatform (state, isDesktop) {
  state.isDesktopPlatform = isDesktop
}

export function setMenuActive (state, index) {
	const keys = Object.keys(state.menus)
	state.activeMenu = keys[index]
}

export function setMenuActiveMenuName (state, menuName) {
	state.activeMenu = menuName
}

export function setJWTToken (state, token) {
  state.JWTToken = token
}

export function setJWTTokenReset (state) {
  state.JWTToken = null
}

export function setUserTeam (state, team) {
  state.userTeam = team
}
