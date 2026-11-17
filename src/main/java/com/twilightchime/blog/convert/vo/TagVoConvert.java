package com.twilightchime.blog.convert.vo;

import com.twilightchime.blog.entity.Tag;
import com.twilightchime.blog.model.vo.TagVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagVoConvert {
    TagVO toVo(Tag tag);

//    default PageResult<TagVO> toPageResult(Page<Tag> page) {
//        if (page == null || page.isEmpty()) {
//            return null;
//        }
//        return PageResult.of(page.map(this::toVo));
//        return PageAdapter.from(page.map(this::toVo));
//    }
}
