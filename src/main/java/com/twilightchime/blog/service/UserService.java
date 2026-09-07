package com.twilightchime.blog.service;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.dto.request.UserPwdDTO;
import com.twilightchime.blog.dto.request.UserRequestDTO;
import com.twilightchime.blog.vo.UserDetailVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    UserDetailVO createUser(UserPwdDTO userPwdDto);

    UserDetailVO login(UserPwdDTO userPwdDto);

    UserDetailVO getUser(String username);

    UserDetailVO getUser(Long id);

    PageResult<UserDetailVO> getUserByPage(PageRequest pageRequest);

    @Transactional(readOnly = true)
    List<UserDetailVO> getAllUser();

    UserDetailVO saveUser(UserRequestDTO userRequestDto);

    void deleteUser(Long id);
}
