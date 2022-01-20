package com.ssafy.api.controller;

import com.ssafy.api.advice.exception.CArticleNotFoundException;
import com.ssafy.api.dto.ArticleDto;
import com.ssafy.api.service.ArticleService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Article;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "게시글 API", tags = {"Article"})
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping()
    @ApiOperation(value = "게시글 작성", notes = "<strong>글 제목과 글 내용</strong>를 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> addArticle (
            @RequestBody @ApiParam(value="글 작성 정보", required = true) ArticleDto articleDto) throws Exception {

        //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음

        Article article = articleService.addArticle(articleDto);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping()
    @ApiOperation(value = "전체 게시글 조회", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<ArticleDto>> getList(){
        List<ArticleDto> articleDtoList = articleService.getArticleList();

        return ResponseEntity.status(200).body(articleDtoList);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "특정 게시글 조회", notes = "게시글 id 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<ArticleDto> getArticleDetail(@ApiParam(value = "id", required = true) @PathVariable("id") Long id){
        Article article = articleService.getArticleDetailById(id);
        // 수정하려는 글이 없으면 예외 처리
        if(article.getId() == null){
            throw new CArticleNotFoundException();
        }
        ArticleDto articleDto = new ArticleDto(article);
        return ResponseEntity.status(200).body(articleDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "게시글 수정", notes = "게시글 id 이용하여 조회 후 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends  BaseResponseBody> updateArticleDetail(@ApiParam(value = "id", required = true) @PathVariable("id") Long id, ArticleDto articleDto){
        if(!articleService.updateArticleDetailById(id, articleDto)){
            // 수정하려는 글이 없으면 예외처리
            throw new CArticleNotFoundException();
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "수정 성공"));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글 id 이용하여 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> deleteArticleDetail(@ApiParam(value = "id", required = true) @PathVariable("id") Long id){
        if(!articleService.deleteArticleDetailById(id)){
            // 삭제하려는 글이 없으면 예외 처리
            throw new CArticleNotFoundException();
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "삭제 성공"));
    }
}
