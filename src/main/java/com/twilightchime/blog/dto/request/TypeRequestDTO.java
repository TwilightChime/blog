package com.twilightchime.blog.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TypeRequestDTO {
    @Min(value = 1, message = "分类id不能小于1")
    private Long id;

    @NotBlank(message = "分类名不能为空")
    private String name;
    private String pic_url;
    private String color;
}
