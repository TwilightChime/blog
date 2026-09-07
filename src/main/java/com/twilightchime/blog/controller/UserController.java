package com.twilightchime.blog.controller;

import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.dto.request.UserPwdDTO;
import com.twilightchime.blog.service.UserService;
import com.twilightchime.blog.utils.JwtUtils;
import com.twilightchime.blog.vo.UserDetailVO;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Result<Map<String, Object>> createUser(@Nonnull @RequestBody Map<String, UserPwdDTO> para) {
        UserPwdDTO userPwdDto = para.get("user");

        UserDetailVO user = userService.createUser(userPwdDto);
        String token = JwtUtils.sign(user);
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("user", user);
        userInfo.put("token", token);
        return Result.ok("注册用户", userInfo);
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Nonnull @RequestBody Map<String, UserPwdDTO> para) {
        UserPwdDTO userPwdDto = para.get("user");

        UserDetailVO user = userService.login(userPwdDto);
        String token = JwtUtils.sign(user);
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("user", user);
        userInfo.put("token", token);
        return Result.ok("登录用户", userInfo);
    }

    @GetMapping("/username/{username}")
    public Result<UserDetailVO> getUserByUsername(@PathVariable String username) {
        UserDetailVO user = userService.getUser(username);
        return Result.ok("获取用户信息", user);
    }

    @GetMapping("/userid/{id}")
    public Result<UserDetailVO> getUserByID(@PathVariable Long id) {
        UserDetailVO user = userService.getUser(id);
        return Result.ok("userInfo", user);
    }
}
