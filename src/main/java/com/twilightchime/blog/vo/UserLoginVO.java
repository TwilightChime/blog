package com.twilightchime.blog.vo;

import lombok.*;

@Data
@AllArgsConstructor
public class UserLoginVO {
    private UserDetailVO user;
    private String token;

}
