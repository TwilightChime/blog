package com.twilightchime.blog.service;

import com.twilightchime.blog.convert.request.UserPwdConvert;
import com.twilightchime.blog.convert.request.UserRequestConvert;
import com.twilightchime.blog.convert.vo.UserDetailConvert;
import com.twilightchime.blog.model.common.PageRequest;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.dao.UserRepository;
import com.twilightchime.blog.model.dto.user.request.UserPwdDTO;
import com.twilightchime.blog.model.dto.user.request.UserRequestDTO;
import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.exception.BusinessException;
import com.twilightchime.blog.utils.JwtUtils;
import com.twilightchime.blog.model.vo.UserDetailVO;
import com.twilightchime.blog.model.vo.UserLoginVO;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailConvert userDetailConvert;
    private final UserPwdConvert userPwdConvert;
    private final UserRequestConvert userRequestConvert;
    private final EmailService emailService;
    private final JwtUtils jwtUtils;

    @Override
    public UserLoginVO createUser(UserPwdDTO userPwdDto) {
        if (userRepository.existsByUsername(userPwdDto.getUsername())) {
            throw new BusinessException("用户名已存在");
        }

        User user = userPwdConvert.toEntity(userPwdDto);
        user.setUpdateTime(LocalDateTime.now());
        UserDetailVO userDetailVO = userDetailConvert.toDetailVO(userRepository.save(user));

        List<String> roles = Collections.singletonList(userDetailVO.getType());
        String token = jwtUtils.generateToken(userDetailVO.getUsername(), userDetailVO.getId(), roles);

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        emailService.sendWelcomeEmail(userDetailVO.getEmail(), userDetailVO.getUsername());
                    }
                }
        );

        return new UserLoginVO(userDetailVO, token);
    }

    @Override
    public UserLoginVO login(UserPwdDTO userPwdDto) {

        User user = userRepository.findByUsername(userPwdDto.getUsername());
        if (user == null || !user.getPassword().equals(userPwdDto.getPassword()) ) {
            throw new BusinessException("用户名或密码错误");
        }
        userPwdConvert.updateEntity(userPwdDto, user);

        UserDetailVO userDetailVO = userDetailConvert.toDetailVO(userRepository.save(user));

        List<String> roles = Collections.singletonList(userDetailVO.getType());
        String token = jwtUtils.generateToken(userDetailVO.getUsername(), userDetailVO.getId(), roles);

        return new UserLoginVO(userDetailVO, token);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailVO getUser(String username) {
        log.debug("查询用户名{}", username);
        try {
            return userDetailConvert.toDetailVO(userRepository.findByUsername(username));
        } catch (Exception e){
            log.error("查询用户名异常");
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailVO getUser(Long id) {
        log.debug("查询用户id{}", id);
        return userDetailConvert.toDetailVO(userRepository.findById(id).orElseThrow(() -> {
            log.info("该用户id{}不存在", id);
            return new BusinessException("用户id不存在");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<UserDetailVO> getUserByPage(@Nonnull PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize(), pageRequest.getSort());
        Page<User> userPage = userRepository.findAll(pageable);
        return userDetailConvert.toPageResult(userPage);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDetailVO> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userDetailConvert::toDetailVO).toList();
    }

    @Override
    public UserDetailVO saveUser(@Nonnull UserRequestDTO userRequestDto){
        User user = userRepository.findById(userRequestDto.getId()).orElseThrow(() -> new BusinessException("用户id不存在"));
        userRequestConvert.updateEntity(userRequestDto, user);
        return userDetailConvert.toDetailVO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BusinessException("用户id不存在"));
    }
}
