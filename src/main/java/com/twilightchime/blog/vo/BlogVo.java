package com.twilightchime.blog.vo;

import lombok.Data;

import java.util.Date;
@Data
public class BlogVo {
    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private Integer appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private Date createTime;
    private Date updateTime;
    private String description;

    private TypeVo type;
    private TagVo tag;
    private UserVo user;
}
