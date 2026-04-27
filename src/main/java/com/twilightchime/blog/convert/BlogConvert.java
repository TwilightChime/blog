package com.twilightchime.blog.convert;

import com.twilightchime.blog.entity.Blog;
import com.twilightchime.blog.vo.BlogVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class BlogConvert {
    public Blog toEntity(BlogVo vo) {
        Blog entity = new Blog();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }
    public BlogVo toVo(Blog entity) {
        BlogVo vo = new BlogVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
