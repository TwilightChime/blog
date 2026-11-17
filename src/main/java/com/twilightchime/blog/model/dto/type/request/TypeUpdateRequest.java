package com.twilightchime.blog.model.dto.type.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TypeUpdateRequest {
    @NotNull(message = "分类id不能为空")
    @Min(1)
    private Long id;
    private String name;
    private String pic_url;
    private String color;
}
