package com.twilightchime.blog.convert.request;

import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.model.dto.user.request.UserRegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegisterConvert {
    User toEntity(UserRegisterRequest dto);
}
