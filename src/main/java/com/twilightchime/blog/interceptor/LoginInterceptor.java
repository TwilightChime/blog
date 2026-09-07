package com.twilightchime.blog.interceptor;

import com.twilightchime.blog.exception.BusinessException;
import com.twilightchime.blog.exception.ErrorCode;
import com.twilightchime.blog.utils.JwtUtils;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println("预检 OPTIONS: " + request);
            return true;
        }

        String token = request.getHeader("token");
        System.out.println("LoginInterceptor preHandle: " + token);
        if (!StringUtils.hasText(token) || !JwtUtils.adminVerify(token)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }
        return true;
    }
}