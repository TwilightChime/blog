package com.twilightchime.blog.convert;

import com.twilightchime.blog.model.dto.user.UserBaseDTO;
import com.twilightchime.blog.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserBaseConvert {
    UserBaseDTO toBaseDTO(User entity);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "blogs", ignore = true)
    User toEntity(UserBaseDTO dto);

    List<UserBaseDTO> toBaseDTOList(List<User> entityList);
}
