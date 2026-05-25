package com.twilightchime.blog.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemException extends RuntimeException {

    private ErrorCode code;

    public SystemException(String message, Throwable cause) {
        super(message, cause);
        this.code = ErrorCode.ERROR;
    }
}
