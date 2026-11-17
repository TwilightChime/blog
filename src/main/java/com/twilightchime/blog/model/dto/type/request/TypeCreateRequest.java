package com.twilightchime.blog.model.dto.type.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TypeCreateRequest {
    @NotBlank(message = "分类名不能为空")
    private String name;
    private String pic_url;
    private String color;
}
