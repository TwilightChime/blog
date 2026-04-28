package com.twilightchime.blog.vo;

import com.twilightchime.blog.entity.Tag;
import com.twilightchime.blog.entity.Type;
import com.twilightchime.blog.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<TagVo> tags;
    private UserVo user;

    public void setType(Type type) {
        this.type = new TypeVo();
        BeanUtils.copyProperties(type,this.type, "blogs");
    }
    public void setTags(List<Tag> tags) {
        this.tags = tags.stream().map(tag -> {
            TagVo tagVo = new TagVo();
            BeanUtils.copyProperties(tag,tagVo, "blogs");
            return tagVo;
        }).collect(Collectors.toList());
    }
    public void setUser(User user) {
        this.user = new UserVo();
        BeanUtils.copyProperties(user, this.user, "blogs");
    }
}
