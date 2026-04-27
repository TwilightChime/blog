package com.twilightchime.blog.convert;

import com.twilightchime.blog.entity.Tag;
import com.twilightchime.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TagConvert {
    public Tag toEntity(TagVo vo) {
        Tag entity = new Tag();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }
    public TagVo toVo(Tag entity) {
        TagVo vo = new TagVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
