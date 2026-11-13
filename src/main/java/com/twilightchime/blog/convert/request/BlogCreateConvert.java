package com.twilightchime.blog.convert.request;

import com.twilightchime.blog.entity.Blog;
import com.twilightchime.blog.model.dto.blog.request.BlogCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BlogCreateConvert {
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "user", ignore = true)
    Blog toEntity(BlogCreateRequest dto);
}
