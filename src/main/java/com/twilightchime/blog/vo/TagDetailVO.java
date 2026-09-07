package com.twilightchime.blog.vo;

import com.twilightchime.blog.dto.BlogBaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class TagDetailVO {
    private Long id;
    private String name;

    private List<BlogBaseDTO> blogs;
}
