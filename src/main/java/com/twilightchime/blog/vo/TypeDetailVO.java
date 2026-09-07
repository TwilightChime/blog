package com.twilightchime.blog.vo;

import com.twilightchime.blog.dto.BlogBaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class TypeDetailVO {
    private Long id;
    private String name;
    private String pic_url;
    private String color;

    private List<BlogBaseDTO> blogs;

}
