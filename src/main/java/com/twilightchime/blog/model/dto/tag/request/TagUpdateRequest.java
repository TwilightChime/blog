package com.twilightchime.blog.model.dto.tag.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagUpdateRequest {
    @NotNull(message = "标签id不能为空")
    @Min(1)
    private Long id;

    @NotBlank
    private String name;
}
