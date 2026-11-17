package com.twilightchime.blog.convert.request;

import com.twilightchime.blog.entity.Type;
import com.twilightchime.blog.model.dto.type.request.TypeUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TypeUpdateConvert {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blogs", ignore = true)
    void mergeToEntity(TypeUpdateRequest dto, @MappingTarget Type entity);
}
