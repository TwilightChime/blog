package com.twilightchime.blog.service;

import com.twilightchime.blog.convert.UserConvert;
import com.twilightchime.blog.dao.UserRepository;
import com.twilightchime.blog.dto.UserCreateDto;
import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConvert userConvert;

    @Override
    public User createUser(UserCreateDto userCreateDto) {
        if (userRepository.findByUsername(userCreateDto.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }
        if (userCreateDto.getPassword() == null || userCreateDto.getPassword().equals("")) {
            throw new BusinessException("密码填写错误");
        }
        User user = userConvert.toUser(userCreateDto);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User login(UserCreateDto userCreateDto) {
        User user = userRepository.findByUsername(userCreateDto.getUsername());
        if (user == null || !user.getPassword().equals(userCreateDto.getPassword()) ) {
            throw new BusinessException("用户名或密码错误");
        }
        user.setLoginProvince(userCreateDto.getLoginProvince());
        user.setLoginCity(userCreateDto.getLoginCity());
        user.setLoginLat(userCreateDto.getLoginLat());
        user.setLoginLng(userCreateDto.getLoginLng());
        user.setLastLoginTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        log.debug("查询用户名{}", username);
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e){
            log.error("查询用户名异常");
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public User getUser(Long id) {
        log.debug("查询用户id{}", id);
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.info("该用户id{}不存在", id);
            return new BusinessException("用户id不存在");
        });
        return user;
    }
}
