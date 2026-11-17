package com.twilightchime.blog.service;

import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.dto.user.request.*;
import com.twilightchime.blog.model.vo.UserDetailVO;
import com.twilightchime.blog.model.vo.UserTokenVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    UserTokenVO register(UserRegisterRequest userRegisterRequest);

    UserTokenVO login(UserLoginRequest userLoginRequest);

//    UserLoginVO createUser(UserPwdDTO userPwdDto);

    PageResult<UserDetailVO> getUserByPage(UserPageRequest userPageRequest);

    @Transactional(readOnly = true)
    List<UserDetailVO> getAllUser();

    UserDetailVO updateUser(UserUpdateRequest userUpdateRequest);

    void deleteUser(Long id);

    UserDetailVO getUser(String username);

    UserDetailVO getUser(Long id);

    User findOrThrowById(Long id);

    boolean existsByUsername(String username);

    User findOrThrowByUsername(String username);
}
