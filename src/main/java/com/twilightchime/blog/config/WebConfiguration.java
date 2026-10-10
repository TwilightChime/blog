package com.twilightchime.blog.config;

import com.twilightchime.blog.interceptor.LogInterceptor;
import com.twilightchime.blog.interceptor.LoginInterceptor;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final String fileUploadPath;
    private final LoginInterceptor loginInterceptor;

    @Autowired
    private LogInterceptor logInterceptor;

    public WebConfiguration(
            @Value("${file.upload.path}") String fileUploadPath,
            LoginInterceptor loginInterceptor) {
        this.fileUploadPath = fileUploadPath;
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
//        List<String> excludePath = new ArrayList<>();
//        //排除拦截，除了注册登录(此时还没token)，其他都拦截
//        excludePath.add("/register");  //登录
//        excludePath.add("/login");     //注册
//        excludePath.add("/static/**");  //静态资源
//        excludePath.add("/assets/**");  //静态资源
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns(excludePath);
//        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(@Nonnull ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" +  fileUploadPath)
                .setCachePeriod(3600)
                .resourceChain(true);
    }

    //cors配置已在Spring Security过滤层
//    @Override
//    public void addCorsMappings(@Nonnull CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("http://localhost:5173")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
}
