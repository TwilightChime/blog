package com.twilightchime.blog.mapper.request;

import com.twilightchime.blog.dto.request.TagRequestDTO;
import com.twilightchime.blog.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TagRequestMapper {
    @Mapping(target = "blogs", ignore = true)
    Tag toEntity(TagRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blogs", ignore = true)
    void updateEntity(TagRequestDTO dto, @MappingTarget Tag entity);
}
