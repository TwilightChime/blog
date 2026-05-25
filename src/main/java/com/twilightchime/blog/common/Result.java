package com.twilightchime.blog.common;

import com.twilightchime.blog.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private ErrorCode errorCode;
    private Integer code;
    private String msg;
    private T data;
    private Long timestamp;

    public static <T> Result<T> ok(String msg) {
        return Result.<T>builder().errorCode(ErrorCode.SUCCESS).code(ErrorCode.SUCCESS.getCode()).msg(msg).timestamp(System.currentTimeMillis()).build();
    }

    public static <T> Result<T> ok(String msg, T data) {
        return Result.<T>builder().errorCode(ErrorCode.SUCCESS).code(ErrorCode.SUCCESS.getCode()).msg(msg).data(data).timestamp(System.currentTimeMillis()).build();
    }

    public static <T> Result<T> error(String msg) {
        return Result.<T>builder().errorCode(ErrorCode.ERROR).code(ErrorCode.ERROR.getCode()).msg(msg).timestamp(System.currentTimeMillis()).build();
    }

    public static <T> Result<T> error(ErrorCode errorCode, String msg) {
        return Result.<T>builder().errorCode(errorCode).code(errorCode.getCode()).msg(msg).timestamp(System.currentTimeMillis()).build();
    }

    public static <T> Result<T> error(T data, String msg) {
        return Result.<T>builder().errorCode(ErrorCode.ERROR).code(ErrorCode.ERROR.getCode()).msg(msg).data(data).timestamp(System.currentTimeMillis()).build();
    }
}
