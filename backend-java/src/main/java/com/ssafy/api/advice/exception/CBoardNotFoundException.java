package com.ssafy.api.advice.exception;

public class CBoardNotFoundException extends RuntimeException {
    public CBoardNotFoundException() {
    }
    public CBoardNotFoundException(String msg) {
        super(msg);
    }
    public CBoardNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
