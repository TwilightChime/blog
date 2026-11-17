package com.twilightchime.blog.controller;

import com.twilightchime.blog.model.common.Result;
import com.twilightchime.blog.model.dto.user.request.UserLoginRequest;
import com.twilightchime.blog.model.dto.user.request.UserRegisterRequest;
import com.twilightchime.blog.model.vo.UserTokenVO;
import com.twilightchime.blog.service.UserService;
import com.twilightchime.blog.model.vo.UserDetailVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    public Result<UserTokenVO> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        log.info("用户注册请求: name={}", userRegisterRequest.getUsername());
        UserTokenVO user = userService.register(userRegisterRequest);
        log.info("用户注册成功: id={}, username={}", user.getUser().getId(), userRegisterRequest.getUsername());
        return Result.ok("注册用户", user);
    }

    @PostMapping("/login")
    public Result<UserTokenVO> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        log.info("用户登录请求: name={}", userLoginRequest.getUsername());
        UserTokenVO user = userService.login(userLoginRequest);
        log.info("用户登录成功: id={}, username={}", user.getUser().getId(), userLoginRequest.getUsername());
        return Result.ok("登录用户", user);
    }

    @GetMapping("/user/{username}")
    public Result<UserDetailVO> getUser(@PathVariable @NotBlank String username) {
        log.info("查找用户名请求: username={}", username);
        UserDetailVO user = userService.getUser(username);
        log.info("查找用户名成功: id={}, username={}", user.getId(), username);
        return Result.ok("获取用户信息", user);
    }

    @GetMapping("/user/{id}")
    public Result<UserDetailVO> getUser(@PathVariable @NotNull @Min(1) Long id) {
        log.info("查找用户id请求: id={}", id);
        UserDetailVO user = userService.getUser(id);
        log.info("查找用户id成功: id={}, username={}", id, user.getUsername());
        return Result.ok("userInfo", user);
    }
}
