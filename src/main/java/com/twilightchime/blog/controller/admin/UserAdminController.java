package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.convert.UserConvert;
import com.twilightchime.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserAdminController {
    private final UserService userService;
    private final UserConvert userConvert;
}
