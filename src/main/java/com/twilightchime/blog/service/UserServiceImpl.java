package com.twilightchime.blog.service;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.dao.UserRepository;
import com.twilightchime.blog.dto.request.UserPwdDTO;
import com.twilightchime.blog.dto.request.UserRequestDTO;
import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.exception.BusinessException;
import com.twilightchime.blog.mapper.request.UserPwdMapper;
import com.twilightchime.blog.mapper.request.UserRequestMapper;
import com.twilightchime.blog.mapper.vo.UserDetailMapper;
import com.twilightchime.blog.vo.UserDetailVO;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailMapper userDetailMapper;
    private final UserPwdMapper userPwdMapper;
    private final UserRequestMapper userRequestMapper;

    @Override
    public UserDetailVO createUser(@Nonnull UserPwdDTO userPwdDto) {
        if (userRepository.findByUsername(userPwdDto.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }
        if (userPwdDto.getPassword() == null || userPwdDto.getPassword().isEmpty()) {
            throw new BusinessException("密码填写错误");
        }
        User user = userPwdMapper.toEntity(userPwdDto);
        user.setUpdateTime(LocalDateTime.now());
        return userDetailMapper.toDetailVO(userRepository.save(user));
    }

    @Override
    public UserDetailVO login(@Nonnull UserPwdDTO userPwdDto) {
        User user = userRepository.findByUsername(userPwdDto.getUsername());
        if (user == null || !user.getPassword().equals(userPwdDto.getPassword()) ) {
            throw new BusinessException("用户名或密码错误");
        }
        userPwdMapper.updateEntity(userPwdDto, user);
        return userDetailMapper.toDetailVO(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailVO getUser(String username) {
        log.debug("查询用户名{}", username);
        try {
            return userDetailMapper.toDetailVO(userRepository.findByUsername(username));
        } catch (Exception e){
            log.error("查询用户名异常");
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailVO getUser(Long id) {
        log.debug("查询用户id{}", id);
        return userDetailMapper.toDetailVO(userRepository.findById(id).orElseThrow(() -> {
            log.info("该用户id{}不存在", id);
            return new BusinessException("用户id不存在");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<UserDetailVO> getUserByPage(@Nonnull PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize(), pageRequest.getSort());
        Page<User> userPage = userRepository.findAll(pageable);
        return userDetailMapper.toPageResult(userPage);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDetailVO> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userDetailMapper::toDetailVO).toList();
    }

    @Override
    public UserDetailVO saveUser(@Nonnull UserRequestDTO userRequestDto){
        User user = userRepository.findById(userRequestDto.getId()).orElseThrow(() -> new BusinessException("用户id不存在"));
        userRequestMapper.updateEntity(userRequestDto, user);
        return userDetailMapper.toDetailVO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
