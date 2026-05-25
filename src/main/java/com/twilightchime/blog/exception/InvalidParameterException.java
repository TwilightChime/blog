package com.twilightchime.blog.exception;

public class InvalidParameterException extends BusinessException {
    public InvalidParameterException(String paramName, String message) {
        super(String.format("Parameter %s not found: %s", paramName, message));
        this.setCode(ErrorCode.PARAM_VALIDATION_FAILED);
    }
}
