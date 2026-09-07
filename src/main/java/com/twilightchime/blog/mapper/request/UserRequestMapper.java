package com.twilightchime.blog.mapper.request;

import com.twilightchime.blog.dto.request.UserRequestDTO;
import com.twilightchime.blog.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserRequestMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "blogs", ignore = true)
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "blogs", ignore = true)
    void updateEntity(UserRequestDTO dto, @MappingTarget User entity);
}
