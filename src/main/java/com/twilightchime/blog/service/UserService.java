package com.twilightchime.blog.service;

import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.model.common.PageRequest;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.dto.user.request.UserPwdDTO;
import com.twilightchime.blog.model.dto.user.request.UserRequestDTO;
import com.twilightchime.blog.model.vo.UserDetailVO;
import com.twilightchime.blog.model.vo.UserLoginVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    UserLoginVO createUser(UserPwdDTO userPwdDto);

    UserLoginVO login(UserPwdDTO userPwdDto);

    UserDetailVO getUser(String username);

    UserDetailVO getUser(Long id);

    PageResult<UserDetailVO> getUserByPage(PageRequest pageRequest);

    @Transactional(readOnly = true)
    List<UserDetailVO> getAllUser();

    UserDetailVO saveUser(UserRequestDTO userRequestDto);

    void deleteUser(Long id);

    User getUserOrThrow(Long id);
}
