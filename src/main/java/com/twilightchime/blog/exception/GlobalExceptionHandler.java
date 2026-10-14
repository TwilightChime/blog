package com.twilightchime.blog.exception;

import com.twilightchime.blog.model.common.Result;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(@Nonnull BusinessException e) {
        log.warn("业务异常 BusinessException: code={}, msg={}", e.getCode(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Result<Void> handleResourceNotFound(@Nonnull ResourceNotFoundException e) {
        log.warn("资源异常 ResourceNotFoundException: code={}, msg={}", e.getCode(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(@Nonnull MethodArgumentNotValidException e) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach((fieldError) -> {fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());});
        log.warn("[参数校验失败] 字段错误: {}", fieldErrors);
        return Result.error(ErrorCode.PARAM_VALIDATION_FAILED, "VALIDATION_FAILED", fieldErrors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(@Nonnull ConstraintViolationException e) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        e.getConstraintViolations().forEach((constraintViolation) -> {fieldErrors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());});
        log.warn("[参数校验失败] 约束违规: {}", fieldErrors);
        return Result.error(ErrorCode.PARAM_VALIDATION_FAILED, "VALIDATION_FAILED", fieldErrors);
    }

    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(@Nonnull BindException e) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        e.getFieldErrors().forEach((fieldError) -> {fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());});
        log.warn("[参数校验失败] 绑定错误: {}", fieldErrors);
        return Result.error(ErrorCode.PARAM_VALIDATION_FAILED, "VALIDATION_FAILED", fieldErrors);
    }

    @ExceptionHandler(Exception.class)
    public Result<Exception> handleException(Exception e) {
        log.error("系统异常 SystemException:", e);
        return Result.error(ErrorCode.ERROR, "系统错误, 稍后重试");
    }
}
