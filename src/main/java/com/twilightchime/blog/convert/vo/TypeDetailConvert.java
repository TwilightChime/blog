package com.twilightchime.blog.convert.vo;

import com.twilightchime.blog.convert.BlogBaseConvert;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.entity.Type;
import com.twilightchime.blog.model.vo.TypeDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = { BlogBaseConvert.class })
public interface TypeDetailConvert {

    @Mapping(target = "blogs", source = "blogs")
    TypeDetailVO toDetailVO(Type type);

    default PageResult<TypeDetailVO> toPageResult(Page<Type> page) {
        if (page == null || page.isEmpty()) {
            return null;
        }
        return PageResult.of(page.map(this::toDetailVO));
    }
}
