package com.ssafy.api.controller;

import com.ssafy.api.advice.exception.CUserDuplicateException;
import com.ssafy.api.request.UserPatchPostReq;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.request.UserLoginPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepositorySupport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;




/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 * 주석한줄(윤희영)
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 중복"),
        @ApiResponse(code = 500, message = "서버 오류")
    })

	public ResponseEntity<? extends BaseResponseBody> register (
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) throws Exception {

		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		try {
			User user = userService.createUser(registerInfo);
		} catch (DataIntegrityViolationException e) {
			//회원중복시 예외처리
			throw new CUserDuplicateException("UserDuplicatedException", e);
		}

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	// 아이디 중복 체크 메서드
	@GetMapping("/{userId}")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "아이디 존재 유무를 응답한다.")
	@ApiResponses({
			@ApiResponse(code = 409, message = "이미 존재하는 ID"),
			@ApiResponse(code = 200, message = "사용할 수 있는 ID")
	})
	public ResponseEntity<? extends BaseResponseBody> validateId(@PathVariable("userId") String userId) {
		User user = userService.getUserByUserId(userId);
		// 해당 아이디의 유저가 존재하지 않는다면 사용 가능한 ID
		if (user == null) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "사용할 수 있는 ID"));
		}
		// 아이디의 유저가 존재한다면 사용 불가능한 ID
		return ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 존재하는 ID 입니다."));
	}
	
	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })

	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.getUserByUserId(userId);

		return ResponseEntity.status(200).body(UserRes.of(user));
	}


	@PatchMapping("/{userId}")
	public ResponseEntity<? extends  BaseResponseBody> updateUser(@PathVariable("userId") String userId,
																  @RequestBody @ApiParam(value="회원 정보 수정", required = true)UserPatchPostReq userPatchPostReq){
		User user = userService.updateUserByUserId(userId, userPatchPostReq);

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));

	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<? extends BaseResponseBody> deleteUser(@PathVariable("userId") String userId){
		User user = userService.deleteUserByUserId(userId);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
	}
}
