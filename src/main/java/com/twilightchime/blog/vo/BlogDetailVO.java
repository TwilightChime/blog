package com.twilightchime.blog.vo;

import com.twilightchime.blog.dto.TagBaseDTO;
import com.twilightchime.blog.dto.TypeBaseDTO;
import com.twilightchime.blog.dto.UserBaseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BlogDetailVO {
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

    private TypeBaseDTO type;
    private List<TagBaseDTO> tags;
    private UserBaseDTO user;
}
