package com.twilightchime.blog.service;

import com.twilightchime.blog.convert.request.UserLoginConvert;
import com.twilightchime.blog.convert.request.UserRegisterConvert;
import com.twilightchime.blog.convert.request.UserUpdateConvert;
import com.twilightchime.blog.convert.vo.UserDetailConvert;
import com.twilightchime.blog.convert.vo.UserVoConvert;
import com.twilightchime.blog.model.common.PageRequest;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.dao.UserRepository;
import com.twilightchime.blog.model.dto.user.request.UserLoginRequest;
import com.twilightchime.blog.model.dto.user.request.UserPageRequest;
import com.twilightchime.blog.model.dto.user.request.UserRegisterRequest;
import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.exception.BusinessException;
import com.twilightchime.blog.model.dto.user.request.UserUpdateRequest;
import com.twilightchime.blog.model.vo.UserTokenVO;
import com.twilightchime.blog.model.vo.UserVO;
import com.twilightchime.blog.utils.JwtUtils;
import com.twilightchime.blog.model.vo.UserDetailVO;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailConvert userDetailConvert;
    private final EmailService emailService;
    private final JwtUtils jwtUtils;
    private final UserRegisterConvert userRegisterConvert;
    private final UserVoConvert userVoConvert;
    private final UserLoginConvert userLoginConvert;
    private final UserUpdateConvert userUpdateConvert;

    @Override
    public UserTokenVO register(UserRegisterRequest userRegisterRequest) {
        if (existsByUsername(userRegisterRequest.getUsername())) {
            throw new BusinessException("用户名已存在");
        }
        User user = userRegisterConvert.toEntity(userRegisterRequest);
        UserVO userVO = userVoConvert.toUserVO(userRepository.save(user));

        List<String> roles = Collections.singletonList(userVO.getType());
        String token = jwtUtils.generateToken(userVO.getUsername(), userVO.getId(), roles);
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        emailService.sendWelcomeEmail(userVO.getEmail(), userVO.getUsername());
                    }
                }
        );

        return new UserTokenVO(userVO, token);
    }

    @Override
    public UserTokenVO login(UserLoginRequest userLoginRequest) {
        User user = findOrThrowByUsername(userLoginRequest.getUsername());
        if (!user.getPassword().equals(userLoginRequest.getPassword())) {
            throw new BusinessException("密码错误");
        }

        userLoginConvert.mergeToEntity(userLoginRequest, user);
        UserVO userVO = userVoConvert.toUserVO(userRepository.save(user));

        List<String> roles = Collections.singletonList(userVO.getType());
        String token = jwtUtils.generateToken(userVO.getUsername(), userVO.getId(), roles);

        return new UserTokenVO(userVO, token);
    }

//    @Override
//    public UserLoginVO createUser(UserPwdDTO userPwdDto) {
//        if (userRepository.existsByUsername(userPwdDto.getUsername())) {
//            throw new BusinessException("用户名已存在");
//        }
//
//        User user = userPwdConvert.toEntity(userPwdDto);
//        user.setUpdateTime(LocalDateTime.now());
//        UserDetailVO userDetailVO = userDetailConvert.toDetailVO(userRepository.save(user));
//
//        List<String> roles = Collections.singletonList(userDetailVO.getType());
//        String token = jwtUtils.generateToken(userDetailVO.getUsername(), userDetailVO.getId(), roles);
//
//        TransactionSynchronizationManager.registerSynchronization(
//                new TransactionSynchronization() {
//                    @Override
//                    public void afterCommit() {
//                        emailService.sendWelcomeEmail(userDetailVO.getEmail(), userDetailVO.getUsername());
//                    }
//                }
//        );
//
//        return new UserLoginVO(userDetailVO, token);
//    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<UserDetailVO> getUserByPage(UserPageRequest userPageRequest) {
        Pageable pageable = userPageRequest.getPageable();
        Page<User> userPage = userRepository.findPageByNameLike(userPageRequest.getUsername(), pageable);

        return userDetailConvert.toPageResult(userPage);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDetailVO> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userDetailConvert::toDetailVO).toList();
    }

    @Override
    public UserDetailVO updateUser(UserUpdateRequest userUpdateRequest){
        User user = userRepository.findById(userUpdateRequest.getId()).orElseThrow(() -> new BusinessException("用户id不存在"));
        userUpdateConvert.mergeToEntity(userUpdateRequest, user);
        return userDetailConvert.toDetailVO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailVO getUser(String username) {
        log.debug("查询用户名{}", username);
        try {
            return userDetailConvert.toDetailVO(userRepository.findByUsername(username).orElseThrow());
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
    public User findOrThrowById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BusinessException("用户id不存在"));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User findOrThrowByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new BusinessException("用户名不存在"));
    }
}
