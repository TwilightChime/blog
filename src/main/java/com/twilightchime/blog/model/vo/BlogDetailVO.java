package com.twilightchime.blog.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.twilightchime.blog.model.dto.tag.TagBaseDTO;
import com.twilightchime.blog.model.dto.type.TypeBaseDTO;
import com.twilightchime.blog.model.dto.user.UserBaseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BlogDetailVO {
    private Long id;
    private String title;
    private String content;
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
