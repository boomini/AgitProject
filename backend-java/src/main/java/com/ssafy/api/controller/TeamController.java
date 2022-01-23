package com.ssafy.api.controller;

import com.ssafy.api.advice.exception.CUserDuplicateException;
import com.ssafy.api.advice.exception.CUserNotFoundException;
import com.ssafy.api.dto.TeamDto;

import com.ssafy.api.dto.UserDto;
import com.ssafy.api.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.Team;
import com.ssafy.db.repository.UserRepositorySupport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "팀 API", tags = {"Team"})
@RestController
@RequestMapping("/api/v1/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @PostMapping()
    @ApiOperation(value = "팀생성", notes = "팀정보를 통해 팀 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증실패"),
            @ApiResponse(code = 1000, message = "팀 중복"),
            @ApiResponse(code = 500, message = "서버 오류")
    })

    public ResponseEntity<? extends BaseResponseBody> register (
            @RequestBody @ApiParam(value="팀가입 정보", required = true) TeamDto teamDto) throws Exception {

        //임의로 리턴된 Team 인스턴스. 현재 코드는 팀 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
        try {
            teamService.createTeam(teamDto);
        } catch (DataIntegrityViolationException e) {
            //회원중복시 예외처리
            throw new CUserDuplicateException();
        }

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }


}
