package com.twilightchime.blog.service;

import com.twilightchime.blog.convert.UserConvert;
import com.twilightchime.blog.dao.UserRepository;
import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConvert userConvert;
    @Override
    public UserVo getUser(String username) {
        User user = userRepository.findByUsername(username);
        return userConvert.toVo(user);
    }
}
