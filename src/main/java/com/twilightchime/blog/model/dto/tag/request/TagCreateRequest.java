package com.twilightchime.blog.model.dto.tag.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagCreateRequest {
    @NotBlank(message = "标签不能为空")
    private String name;
}
