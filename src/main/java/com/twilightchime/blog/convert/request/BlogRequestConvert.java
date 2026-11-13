package com.twilightchime.blog.convert.request;

import com.twilightchime.blog.model.dto.blog.request.BlogRequestDTO;
import com.twilightchime.blog.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BlogRequestConvert {
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "user", ignore = true)
    Blog toEntity(BlogRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(BlogRequestDTO dto, @MappingTarget Blog entity);
}
