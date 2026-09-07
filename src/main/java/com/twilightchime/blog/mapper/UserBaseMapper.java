package com.twilightchime.blog.mapper;

import com.twilightchime.blog.dto.UserBaseDTO;
import com.twilightchime.blog.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserBaseMapper {
    UserBaseDTO toBaseDTO(User entity);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "blogs", ignore = true)
    User toEntity(UserBaseDTO dto);

    List<UserBaseDTO> toBaseDTOList(List<User> entityList);
}
