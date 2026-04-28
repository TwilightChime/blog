package com.twilightchime.blog.vo;

import com.twilightchime.blog.entity.Blog;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserVo {
    private Long id;
    private String nickname;
    private String username;
    private String email;
    private String avatar;
    private String loginProvince;
    private String loginCity;
    private String loginLat;
    private String loginLng;
    private String type;
    private Date createTime;
    private Date updateTime;
    private Date lastLoginTime;

    private List<BlogVo> blogs;

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs.stream().map(blog -> {
            BlogVo blogVo = new BlogVo();
            BeanUtils.copyProperties(blog, blogVo, "type", "tags", "user");
            return blogVo;
        }).collect(Collectors.toList());

//        if (this.blogs == null) {
//            this.blogs = new ArrayList<>();
//        }
//        for (Blog blog : blogs) {
//            BlogVo vo = new BlogVo();
//            BeanUtils.copyProperties(blog, vo);
//            this.blogs.add(vo);
//        }
    }
}
