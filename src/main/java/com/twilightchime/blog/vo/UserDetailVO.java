package com.twilightchime.blog.vo;

import com.twilightchime.blog.dto.BlogBaseDTO;
import com.twilightchime.blog.entity.Blog;
import jakarta.annotation.Nonnull;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
