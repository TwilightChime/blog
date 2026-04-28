package com.twilightchime.blog.vo;

import com.twilightchime.blog.entity.Blog;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TypeVo {
    private Long id;
    private String name;
    private String pic_url;
    private String color;

    private List<BlogVo> blogs;

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs.stream().map(blog ->  {
            BlogVo blogVo = new BlogVo();
            BeanUtils.copyProperties(blog, blogVo, "type", "tags", "user");
            return blogVo;
        }).collect(Collectors.toList());
    }
}
