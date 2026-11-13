package com.twilightchime.blog.convert.vo;

import com.twilightchime.blog.convert.BlogBaseConvert;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.entity.Tag;
import com.twilightchime.blog.model.vo.TagDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = { BlogBaseConvert.class })
public interface TagDetailConvert {

    @Mapping(target = "blogs", source = "blogs")
    TagDetailVO toDetailVO(Tag tag);

    default PageResult<TagDetailVO> toPageResult(Page<Tag> page) {
        if (page == null || page.isEmpty()) {
            return null;
        }
        return PageResult.of(page.map(this::toDetailVO));
    }
}
