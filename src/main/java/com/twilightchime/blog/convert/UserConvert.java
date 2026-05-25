package com.twilightchime.blog.convert;

import com.twilightchime.blog.dto.UserCreateDto;
import com.twilightchime.blog.dto.UserInfoDto;
import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserConvert {

    public User toUser(UserCreateDto userCreateDto) {
        User user = new User();
        BeanUtils.copyProperties(userCreateDto, user);
        return user;
    }

    public UserInfoDto toUserInfoDto(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(user, userInfoDto);
        return userInfoDto;
    }

    public UserVo toUserVo(User user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }
}
