package com.twilightchime.blog.model.vo;

import com.twilightchime.blog.model.dto.blog.BlogBaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class TagDetailVO {
    private Long id;
    private String name;

    private List<BlogBaseDTO> blogs;
}
