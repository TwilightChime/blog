package com.twilightchime.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogInfoDto {
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
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String description;
}
