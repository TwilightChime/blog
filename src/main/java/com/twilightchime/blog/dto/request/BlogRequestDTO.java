package com.twilightchime.blog.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "博客请求参数")
@Data
public class BlogRequestDTO {
    @Schema(description = "博客id")
    private Long id;
    @Schema(description = "博客标题", example = "我的一篇博客", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private Integer appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
    private String description;
}
