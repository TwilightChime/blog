package com.twilightchime.blog.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagRequestDTO {
    @Min(value = 1, message = "标签id不能小于1")
    private Long id;

    @NotBlank(message = "标签名不能为空")
    private String name;
}
