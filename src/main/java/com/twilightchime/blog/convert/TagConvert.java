package com.twilightchime.blog.convert;

import com.twilightchime.blog.dto.TagCreateDto;
import com.twilightchime.blog.entity.Tag;
import com.twilightchime.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TagConvert {
    public Tag toTag(TagCreateDto tagCreateDto) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagCreateDto, tag);
        return tag;
    }
    public TagVo toTagVo(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }
}
