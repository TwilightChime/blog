package com.twilightchime.blog.model.vo;

import lombok.*;

@Data
@AllArgsConstructor
public class UserLoginVO {
    private UserDetailVO user;
    private String token;

}
