package com.twilightchime.blog.convert;

import com.twilightchime.blog.dto.BlogCreateDto;
import com.twilightchime.blog.entity.Blog;
import com.twilightchime.blog.vo.BlogVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class BlogConvert {
    public Blog toBlog(BlogCreateDto blogCreateDto) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogCreateDto, blog);
        return blog;
    }
    public BlogVo toBlogVo(Blog blog) {
        BlogVo blogVo = new BlogVo();
        BeanUtils.copyProperties(blog, blogVo);
        return blogVo;
    }
}
