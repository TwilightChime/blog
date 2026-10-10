package com.twilightchime.blog.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRequestDTO {
    @Min(value = 1, message = "用户id不能小于1")
    private Long id;
    private String nickname;

    @NotBlank(message = "用户名不为空")
    private String username;

    @Email(message = "邮箱格式错误")
    private String email;
    private String avatar;
    private String loginProvince;
    private String loginCity;
    private String loginLat;
    private String loginLng;
    private String type;
    @JsonIgnore
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime lastLoginTime;
}
