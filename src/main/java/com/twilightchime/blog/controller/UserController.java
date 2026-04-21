package com.twilightchime.blog.controller;

import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.service.UserService;
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
    public User getUsers(@PathVariable String username) {
        return userService.getUser(username);
    }
}
