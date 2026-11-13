package com.twilightchime.blog.model.dto.blog;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogBaseDTO {
    private Long id;
    private String title;
//    private String content;
    private String description;
    private String firstPicture;
    private String flag;
    private Integer views;
    private Integer appreciation;
//    private boolean shareStatement;
//    private boolean sponsored;
//    private boolean published;
//    private boolean recommend;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
