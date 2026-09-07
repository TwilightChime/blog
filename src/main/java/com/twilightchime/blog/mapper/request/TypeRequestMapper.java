package com.twilightchime.blog.mapper.request;

import com.twilightchime.blog.dto.request.TypeRequestDTO;
import com.twilightchime.blog.entity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TypeRequestMapper {
    @Mapping(target = "blogs", ignore = true)
    Type toEntity(TypeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blogs", ignore = true)
    void updateEntity(TypeRequestDTO dto, @MappingTarget Type entity);
}
