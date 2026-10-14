package com.twilightchime.blog.model.vo;

import com.twilightchime.blog.model.dto.blog.BlogBaseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDetailVO {
    private Long id;
    private String nickname;
    private String username;
    private String email;
    private String avatar;
    private String loginProvince;
    private String loginCity;
    private String loginLat;
    private String loginLng;
    private String type;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime lastLoginTime;

    private List<BlogBaseDTO> blogs;
}
