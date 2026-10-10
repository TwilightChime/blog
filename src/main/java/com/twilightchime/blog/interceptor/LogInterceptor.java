package com.twilightchime.blog.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        log.info("HTTP 请求: {} {} IP={}",request.getMethod(),request.getRequestURI(),request.getRemoteAddr());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long cost = System.currentTimeMillis() - (Long) request.getAttribute("startTime");
        log.info("HTTP 响应: {} 状态={} 耗时={}ms", request.getRequestURI(), response.getStatus(), cost);
    }
}
