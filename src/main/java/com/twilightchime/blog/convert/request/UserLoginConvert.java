package com.twilightchime.blog.convert.request;

import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.model.dto.user.request.UserLoginRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserLoginConvert {
    void mergeToEntity(UserLoginRequest dto, @MappingTarget User entity);
}
