package com.twilightchime.blog.service;

import com.twilightchime.blog.vo.UserVo;

public interface UserService {
    UserVo getUser(String username);
}
