package com.twilightchime.blog.model.dto;

import com.twilightchime.blog.model.dto.tag.TagBaseDTO;
import lombok.Data;

@Data
public class BlogTagDTO extends TagBaseDTO {
    private Long blogId;
}
