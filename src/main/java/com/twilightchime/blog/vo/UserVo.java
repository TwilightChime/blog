package com.twilightchime.blog.vo;

import com.twilightchime.blog.entity.Blog;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserVo {
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private String loginProvince;
    private String loginCity;
    private String loginLat;
    private String loginLng;
    private String type;
    private Date createTime;
    private Date updateTime;
    private Date lastLoginTime;

    private List<BlogVo> blogs;
}
