package com.twilightchime.blog.model.vo;

import com.twilightchime.blog.model.dto.tag.TagBaseDTO;
import com.twilightchime.blog.model.dto.type.TypeBaseDTO;
import com.twilightchime.blog.model.dto.user.UserBaseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BlogVO {
    private Long id;
    private String title;
    private String description;
    private String firstPicture;
    private String flag;
    private Integer views;
    private Integer appreciation;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private TypeBaseDTO type;
    private Set<TagBaseDTO> tags;
    private UserBaseDTO user;
}
