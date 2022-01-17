package com.ssafy.api.advice;

import com.ssafy.api.advice.exception.CUserDuplicateException;
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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<? extends BaseResponseBody> defaultException(HttpServletRequest request, Exception e){
        return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Error"));
    }

    @ExceptionHandler(CUserDuplicateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<? extends BaseResponseBody> userDuplicationException(HttpServletRequest request, CUserDuplicateException e){
        return ResponseEntity.status(404).body(BaseResponseBody.of(404, "UserDuplicatedException"));
    }



}
