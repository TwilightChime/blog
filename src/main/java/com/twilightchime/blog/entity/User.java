package com.twilightchime.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_seq")
    @SequenceGenerator(name = "default_seq",
            sequenceName = "hibernate_sequence",
            allocationSize = 1)
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
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime lastLoginTime;
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Blog> blogs;
}
