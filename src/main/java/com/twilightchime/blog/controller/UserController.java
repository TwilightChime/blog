package com.twilightchime.blog.controller;

import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.service.UserService;
import com.twilightchime.blog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/{username}")
    public Result<UserVo> getUsers(@PathVariable String username) {
        UserVo userVo = userService.getUser(username);
        return Result.ok("获取用户信息", userVo);
    }
}
