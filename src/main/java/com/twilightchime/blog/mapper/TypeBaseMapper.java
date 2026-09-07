package com.twilightchime.blog.mapper;

import com.twilightchime.blog.dto.TypeBaseDTO;
import com.twilightchime.blog.entity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TypeBaseMapper {
    TypeBaseDTO toBaseDTO(Type entity);

    @Mapping(target = "blogs", ignore = true)
    Type toEntity(TypeBaseDTO dto);

    List<TypeBaseDTO> toBaseDTOList(List<Type> entityList);
}
