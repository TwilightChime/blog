package com.twilightchime.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Knife4j API文档")
                        .version("1.0")
                        .description("个人博客的接口开发文档")
                );
    }

    @Bean
    public GroupedOpenApi defaultOpenAPI() {
        return GroupedOpenApi.builder()
                .group("default")
                .packagesToScan("com.twilightchime.blog.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi blogOpenApi() {
        return GroupedOpenApi.builder()
                .group("blog")
                .pathsToMatch("/blog/**")
                .build();
    }
}
