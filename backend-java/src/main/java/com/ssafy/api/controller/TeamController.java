package com.ssafy.api.controller;

import com.ssafy.api.advice.exception.CUserDuplicateException;
import com.ssafy.api.advice.exception.CUserNotFoundException;
import com.ssafy.api.dto.*;

import com.ssafy.api.service.ArticleService;
import com.ssafy.api.service.ImageService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "팀 API", tags = {"Team"})
@RestController
@RequestMapping("/api/v1/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    ArticleService articleService;

    @Autowired
    ImageService imageService;

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

    @GetMapping("/{teamId}")
    @ApiOperation(value = "team에서 작성한 전체 calendar", notes = "teamID 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<BoardDto> getTeamsBoardList(@ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId){
        BoardDto boardDto = new BoardDto();

        List<ArticleDto> articleDtoList = articleService.getArticleListById(teamId);
        List<ImageDto> imageDtoList = imageService.getImageListById(teamId);
        boardDto.setArticleList(articleDtoList);
        boardDto.setImageList(imageDtoList);
        return ResponseEntity.status(200).body(boardDto);
    }


    @GetMapping("/{teamId}/{uploadDate}")
    @ApiOperation(value = "team에서 특정 일자에 작성한 전체 게시글 조회", notes = "team name, date(yyyy-mm-dd) 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })

    public ResponseEntity<BoardDto> getTeamsArticleListAtDate(@ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId,
                                                                      @ApiParam(value = "uploadDate", required = true) @PathVariable("uploadDate") String uploadDate){
        List<ArticleDto> articleDto = articleService.getTeamsArticleListAtDate(uploadDate, teamId);
        List<ImageDto> imageDto = imageService.getImageListAtDateByTeamId(uploadDate,teamId);
        BoardDto boardDto = new BoardDto();
        boardDto.setArticleList(articleDto);
        boardDto.setImageList(imageDto);
        boardDto.setTeamId(teamId);
        return ResponseEntity.status(200).body(boardDto);

    }

    @GetMapping("/{teamId}/count/{uploadDate}")
    @ApiOperation(value = "team에서 특정 일자에 작성한 전체 게시글 조회", notes = "team name, date(yyyy-mm-dd) 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<Map<String, List<DayCountDto>>> getTeamsBoardCountListAtDate(@ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId,
                                                                      @ApiParam(value = "uploadDate", required = true) @PathVariable("uploadDate") String uploadDate){

        List<DayCountDto> imagecntList = imageService.getTeamImagesCountByMonth(uploadDate,teamId);
        List<DayCountDto> articleList = articleService.getTeamArticleCountByMonth(uploadDate, teamId);
        Map<String, List<DayCountDto>> responseEntity = new HashMap<String, List<DayCountDto>>();
        responseEntity.put("imageCntList", imagecntList);
        responseEntity.put("articleCntList", articleList);
        return ResponseEntity.status(200).body(responseEntity);
    }

}
