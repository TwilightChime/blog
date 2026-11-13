package com.twilightchime.blog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // 1. 关闭 CSRF（前后端分离项目必须关闭，否则 POST 请求 403）
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 2. 关闭默认表单登录和 HTTP Basic
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 3. 无状态会话（JWT 场景）
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. 配置路径权限
                .authorizeHttpRequests(auth -> auth
                        // 放行认证接口
                        .requestMatchers("/api/**", "api/login", "api/register").permitAll()
                        // 放行 Swagger / Knife4j 相关资源
                        .requestMatchers(
                                "/doc.html",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/favicon.ico",
                                "/actuator/**"
                        ).permitAll()
                        // 放行静态资源
                        .requestMatchers("/static/**", "/assets/**", "/uploads/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("1")
                        // 其他请求需要登录
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 允许前端开发服务器地址（精确匹配，不要用 *）
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",   // Vite 默认
                "http://localhost:3000",   // Vue CLI 默认
                "http://127.0.0.1:5173",
                "http://localhost:5174"
        ));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        // 允许携带凭证（Cookie、Authorization）
        config.setAllowCredentials(true);
        // 暴露的响应头（前端需要读取的头）
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Disposition"));
        // 预检请求缓存时间（秒），减少 OPTIONS 请求次数
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // 配置密码加密器（避免启动时再生成默认密码）
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}