package com.twilightchime.blog.convert.vo;

import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.model.vo.UserVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserVoConvert {
    UserVO toUserVO(User user);
}
