package com.twilightchime.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 关闭 CSRF（前后端分离项目必须关闭，否则 POST 请求 403）
                .csrf(csrf -> csrf.disable())

                // 2. 关闭默认表单登录和 HTTP Basic
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // 3. 无状态会话（JWT 场景）
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. 配置路径权限
                .authorizeHttpRequests(auth -> auth
                        // 放行认证接口
                        .requestMatchers("/api/auth/**", "/login", "/register").permitAll()
                        // 放行 Swagger / Knife4j 相关资源
                        .requestMatchers(
                                "/doc.html",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/favicon.ico"
                        ).permitAll()
                        // 放行静态资源
                        .requestMatchers("/static/**", "/assets/**").permitAll()
                        // 其他请求需要登录
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    // 配置密码加密器（避免启动时再生成默认密码）
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}