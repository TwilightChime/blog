package com.twilightchime.blog.vo;

import lombok.Data;

import java.util.List;

@Data
public class TagVo {
    private Long id;
    private String name;

    private List<BlogVo> blogs;
}
