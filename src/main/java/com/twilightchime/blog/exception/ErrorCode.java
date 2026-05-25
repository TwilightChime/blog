package com.twilightchime.blog.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESS(200, "操作成功"),
    ERROR(500, "系统错误"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未登录或token失效"),
    FORBIDDEN(403, "权限不足"),
    NOT_FOUND(404, "资源不存在"),

    // 业务错误码 1000-1999 用户相关
    BUSINESS_ERROR(1000, "业务错误"),
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_ALREADY_EXISTS(1002, "用户已存在"),
    USER_PASSWORD_ERROR(1003, "密码错误"),
    USER_LOCKED(1004, "用户已被锁定"),

    // 参数验证错误 3000-3999
    PARAM_VALIDATION_FAILED(3001, "参数验证失败");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
