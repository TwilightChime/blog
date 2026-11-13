package com.twilightchime.blog.controller;

import com.twilightchime.blog.model.common.Result;
import com.twilightchime.blog.model.dto.user.request.UserPwdDTO;
import com.twilightchime.blog.service.UserService;
import com.twilightchime.blog.model.vo.UserDetailVO;
import com.twilightchime.blog.model.vo.UserLoginVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public Result<UserLoginVO> createUser(@Valid @RequestBody UserPwdDTO userPwdDTO) {
        log.info("用户注册请求: name={}", userPwdDTO.getUsername());
        UserLoginVO user = userService.createUser(userPwdDTO);
        log.info("用户注册成功: id={}, username={}", user.getUser().getId(), userPwdDTO.getUsername());
        return Result.ok("注册用户", user);
    }

    @PostMapping("/login")
    public Result<UserLoginVO> login(@Valid @RequestBody UserPwdDTO userPwdDTO) {
        log.info("用户登录请求: name={}", userPwdDTO.getUsername());
        UserLoginVO user = userService.login(userPwdDTO);
        log.info("用户登录成功: id={}, username={}", user.getUser().getId(), userPwdDTO.getUsername());
        return Result.ok("登录用户", user);
    }

    @GetMapping("/username/{username}")
    public Result<UserDetailVO> getUserByUsername(@PathVariable @NotBlank String username) {
        log.info("查找用户名请求: username={}", username);
        UserDetailVO user = userService.getUser(username);
        log.info("查找用户名成功: id={}, username={}", user.getId(), username);
        return Result.ok("获取用户信息", user);
    }

    @GetMapping("/userid/{id}")
    public Result<UserDetailVO> getUserByID(@PathVariable @Min(1) Long id) {
        log.info("查找用户id请求: id={}", id);
        UserDetailVO user = userService.getUser(id);
        log.info("查找用户id成功: id={}, username={}", id, user.getUsername());
        return Result.ok("userInfo", user);
    }
}
