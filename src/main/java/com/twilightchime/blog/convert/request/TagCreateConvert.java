package com.twilightchime.blog.convert.request;

import com.twilightchime.blog.entity.Tag;
import com.twilightchime.blog.model.dto.tag.request.TagCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TagCreateConvert {
    @Mapping(target = "blogs", ignore = true)
    Tag toEntity(TagCreateRequest dto);
}
