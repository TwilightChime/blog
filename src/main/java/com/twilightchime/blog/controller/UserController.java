package com.twilightchime.blog.controller;

import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.dto.request.UserPwdDTO;
import com.twilightchime.blog.service.UserService;
import com.twilightchime.blog.vo.UserDetailVO;
import com.twilightchime.blog.vo.UserLoginVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regs")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public Result<UserLoginVO> createUser(@Valid @RequestBody UserPwdDTO userPwdDTO) {
        UserLoginVO user = userService.createUser(userPwdDTO);
        return Result.ok("注册用户", user);
    }

    @PostMapping("/login")
    public Result<UserLoginVO> login(@Valid @RequestBody UserPwdDTO userPwdDTO) {
        UserLoginVO user = userService.login(userPwdDTO);
        return Result.ok("登录用户", user);
    }

    @GetMapping("/username/{username}")
    public Result<UserDetailVO> getUserByUsername(@PathVariable @NotBlank String username) {
        UserDetailVO user = userService.getUser(username);
        return Result.ok("获取用户信息", user);
    }

    @GetMapping("/userid/{id}")
    public Result<UserDetailVO> getUserByID(@PathVariable @Min(1) Long id) {
        UserDetailVO user = userService.getUser(id);
        return Result.ok("userInfo", user);
    }
}
