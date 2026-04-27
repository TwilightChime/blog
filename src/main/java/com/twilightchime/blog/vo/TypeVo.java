package com.twilightchime.blog.vo;

import lombok.Data;

import java.util.List;
@Data
public class TypeVo {
    private Long id;
    private String name;
    private String pic_url;
    private String color;

    private List<BlogVo> blogs;
}
