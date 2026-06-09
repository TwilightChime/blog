package com.twilightchime.blog.controller;

import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.convert.UserConvert;
import com.twilightchime.blog.dto.UserCreateDto;
import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.exception.ErrorCode;
import com.twilightchime.blog.service.UserService;
import com.twilightchime.blog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserConvert userConvert;

    @PostMapping("/register")
    public Result<Map<String, Object>> createUser(@RequestBody Map<String, UserCreateDto> para) {
        UserCreateDto userCreateDto = para.get("user");

        User user = userService.createUser(userCreateDto);
        String token = String.valueOf(ErrorCode.UNAUTHORIZED);
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("user", userConvert.toUserInfoDto(user));
        userInfo.put("token", token);
        return Result.ok("注册用户", userInfo);
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, UserCreateDto> para) {
        UserCreateDto userCreateDto = para.get("user");

        User user = userService.login(userCreateDto);
        String token = String.valueOf(ErrorCode.UNAUTHORIZED);
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("user", userConvert.toUserInfoDto(user));
        userInfo.put("token", token);
        return Result.ok("登录用户", userInfo);
    }

    @GetMapping("/username/{username}")
    public Result<UserVo> getUserByUsername(@PathVariable String username) {
        User user = userService.getUser(username);
        return Result.ok("获取用户信息", userConvert.toUserVo(user));
    }

    @GetMapping("/userid/{id}")
    public Result<UserVo> getUserByID(@PathVariable Long id) {
        User user = userService.getUser(id);
        return Result.ok("userInfo", userConvert.toUserVo(user));
    }
}
