package com.ssafy.api.advice;

import com.ssafy.api.advice.exception.CArticleNotFoundException;
import com.ssafy.api.advice.exception.CBoardNotFoundException;
import com.ssafy.api.advice.exception.CUserDuplicateException;
import com.ssafy.api.advice.exception.CUserNotFoundException;
import com.ssafy.common.model.response.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected ResponseEntity<? extends BaseResponseBody> defaultException(HttpServletRequest request, Exception e){
//        return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Error"));
//    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<? extends BaseResponseBody> defaultException(HttpServletRequest request, Exception e){
        return ResponseEntity.status(500).body(BaseResponseBody.of(500, "서버 Error"));
    }


    @ExceptionHandler(CUserDuplicateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<? extends BaseResponseBody> userDuplicationException(HttpServletRequest request, CUserDuplicateException e){
        return ResponseEntity.status(1000).body(BaseResponseBody.of(1000, "이미 존재하는 ID입니다."));
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<? extends BaseResponseBody> userNotFoundException(HttpServletRequest request, CUserNotFoundException e){
        return ResponseEntity.status(1001).body(BaseResponseBody.of(1001, "해당 유저가 존재하지 않습니다."));
    }

    @ExceptionHandler(CBoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<? extends BaseResponseBody> boardNotFoundException(HttpServletRequest request, CBoardNotFoundException e){
        return ResponseEntity.status(401).body(BaseResponseBody.of(401, "해당 게시글이 존재하지 않습니다."));
    }

    @ExceptionHandler(CArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<? extends BaseResponseBody> articleNotFoundException(HttpServletRequest request, CArticleNotFoundException e){
        return ResponseEntity.status(401).body(BaseResponseBody.of(401, "해당 게시글이 존재하지 않습니다."));
    }
}
