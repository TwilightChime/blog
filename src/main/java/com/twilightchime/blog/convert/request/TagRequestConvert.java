package com.twilightchime.blog.convert.request;

import com.twilightchime.blog.model.dto.tag.request.TagRequestDTO;
import com.twilightchime.blog.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TagRequestConvert {
    @Mapping(target = "blogs", ignore = true)
    Tag toEntity(TagRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blogs", ignore = true)
    void updateEntity(TagRequestDTO dto, @MappingTarget Tag entity);
}
