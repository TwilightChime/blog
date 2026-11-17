package com.twilightchime.blog.model.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotNull(message = "用户id不能为空")
    @Min(value = 1, message = "用户id不能小于1")
    private Long id;
    private String nickname;

    @NotBlank(message = "用户名不为空")
//    @Pattern(regexp = "^[a-zA-Z0-9_]{4,20}$", message = "用户名格式不正确")
    private String username;

    @NotBlank(message = "密码不为空")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
//            message = "密码必须包含大小写字母和数字，至少8位")
    private String password;

    @Email(message = "邮箱格式错误")
    private String email;
    private String avatar;
    private String loginProvince;
    private String loginCity;
    private String loginLat;
    private String loginLng;
    private String type;
}
