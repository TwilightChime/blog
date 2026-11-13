package com.twilightchime.blog.model.dto.blog.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "创建博客-请求参数")
@Data
public class BlogCreateRequest {
    @Schema(description = "博客标题", example = "我的一篇博客", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "博客标题不能为空")
    private String title;

    @NotBlank(message = "博客内容不能为空")
    private String content;
    private String description;
    private String firstPicture;
    private String flag;

//    private Integer views;
//    private Integer appreciation;
//    private boolean shareStatement;
//    private boolean sponsored;
//    private boolean published;
//    private boolean recommend;
//    @JsonIgnore
//    private LocalDateTime createTime;
//    @JsonIgnore
//    private LocalDateTime updateTime;
}
