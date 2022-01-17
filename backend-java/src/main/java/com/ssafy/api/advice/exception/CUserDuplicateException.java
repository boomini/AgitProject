package com.ssafy.api.advice.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class CUserDuplicateException extends DataIntegrityViolationException {
    public CUserDuplicateException(String msg) {
        super(msg);
    }

    public CUserDuplicateException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
