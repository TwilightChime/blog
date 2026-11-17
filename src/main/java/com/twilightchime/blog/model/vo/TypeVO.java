package com.twilightchime.blog.model.vo;

import com.twilightchime.blog.model.dto.blog.BlogBaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class TypeVO {
    private Long id;
    private String name;
    private String pic_url;
    private String color;
}
