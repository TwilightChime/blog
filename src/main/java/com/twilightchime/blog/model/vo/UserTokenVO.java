package com.twilightchime.blog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTokenVO {
    private UserVO user;
    private String token;

}
