package com.twilightchime.blog.mapper.vo;

import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.entity.Tag;
import com.twilightchime.blog.mapper.BlogBaseMapper;
import com.twilightchime.blog.vo.TagDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = { BlogBaseMapper.class })
public interface TagDetailMapper {

    @Mapping(target = "blogs", source = "blogs")
    TagDetailVO toDetailVO(Tag tag);

    default PageResult<TagDetailVO> toPageResult(Page<Tag> page) {
        if (page == null || page.isEmpty()) {
            return null;
        }
        return PageResult.of(page.map(this::toDetailVO));
    }
}
