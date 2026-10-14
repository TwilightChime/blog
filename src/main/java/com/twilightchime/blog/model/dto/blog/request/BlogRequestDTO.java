package com.twilightchime.blog.model.dto.blog.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "博客请求参数")
@Data
public class BlogRequestDTO {
    @Schema(description = "博客id")
    @Min(value = 1, message = "id不能小于1")
    private Long id;

    @Schema(description = "博客标题", example = "我的一篇博客", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "博客标题不能为空")
    private String title;

    @NotBlank(message = "博客内容不能为空")
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
