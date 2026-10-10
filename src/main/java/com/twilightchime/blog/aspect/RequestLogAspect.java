package com.twilightchime.blog.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class RequestLogAspect {
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object logRequest(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName =signature.getDeclaringType().getSimpleName() + "." + signature.getName();

        log.info("方法调用: {} 参数={}", methodName, Arrays.toString(pjp.getArgs()));
        try {
            Object result = pjp.proceed();
            long cost = System.currentTimeMillis() - start;
            log.info("方法返回: {}, 耗时={}ms, 结果={}", methodName, cost, result);
            return result;
        } catch (Exception e) {
            long cost = System.currentTimeMillis() - start;
            log.error("方法异常: {}, 耗时={}ms", methodName, cost, e);
            throw e;
        }
    }

    @Around("execution(* com.twilightchime.blog.controller..*.*(..))")
    public Object monitorSlowApi(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long cost = System.currentTimeMillis() - start;
            if (cost > 1000) {  // 超过 1 秒告警
                log.warn("慢接口: method={}, cost={}ms", pjp.getSignature().toShortString(), cost);
            }
        }
    }
}
