package com.twilightchime.blog.mapper.request;

import com.twilightchime.blog.dto.request.UserPwdDTO;
import com.twilightchime.blog.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserPwdMapper {
    @Mapping(target = "blogs", ignore = true)
    User toEntity(UserPwdDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "blogs", ignore = true)
    void updateEntity(UserPwdDTO dto, @MappingTarget User entity);
}
