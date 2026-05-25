package com.twilightchime.blog.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {
    private ErrorCode code;
    private Object data;

    public BusinessException(String message) {
        super(message);
        this.code = ErrorCode.BUSINESS_ERROR;
    }
    public BusinessException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = ErrorCode.BUSINESS_ERROR;
    }
}
