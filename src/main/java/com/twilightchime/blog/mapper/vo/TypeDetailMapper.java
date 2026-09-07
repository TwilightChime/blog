package com.twilightchime.blog.mapper.vo;

import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.entity.Type;
import com.twilightchime.blog.mapper.BlogBaseMapper;
import com.twilightchime.blog.vo.TypeDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = { BlogBaseMapper.class })
public interface TypeDetailMapper {

    @Mapping(target = "blogs", source = "blogs")
    TypeDetailVO toDetailVO(Type type);

    default PageResult<TypeDetailVO> toPageResult(Page<Type> page) {
        if (page == null || page.isEmpty()) {
            return null;
        }
        return PageResult.of(page.map(this::toDetailVO));
    }
}
