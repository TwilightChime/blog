package com.twilightchime.blog.convert.request;

import com.twilightchime.blog.entity.Blog;
import com.twilightchime.blog.model.dto.blog.request.BlogUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BlogUpdateConvert {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "user", ignore = true)
    void mergeToEntity(BlogUpdateRequest dto, @MappingTarget Blog entity);
}
