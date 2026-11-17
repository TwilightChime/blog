package com.twilightchime.blog.convert.request;

import com.twilightchime.blog.entity.Type;
import com.twilightchime.blog.model.dto.type.request.TypeCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TypeCreateConvert {
    @Mapping(target = "blogs", ignore = true)
    Type toEntity(TypeCreateRequest dto);
}
