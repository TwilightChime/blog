package com.twilightchime.blog.service;

import com.twilightchime.blog.dto.UserCreateDto;
import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.vo.UserVo;

public interface UserService {
    User createUser(UserCreateDto userCreateDto);

    User login(UserCreateDto userCreateDto);

    User getUser(String username);

    User getUser(Long id);
}
