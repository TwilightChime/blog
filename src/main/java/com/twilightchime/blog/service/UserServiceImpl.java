package com.twilightchime.blog.service;

import com.twilightchime.blog.dao.UserRepository;
import com.twilightchime.blog.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
