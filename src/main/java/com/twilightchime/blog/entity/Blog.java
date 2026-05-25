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
@Table(name = "t_blog")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_seq")
    @SequenceGenerator(name = "default_seq",
            sequenceName = "hibernate_sequence",
            allocationSize = 1)
    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private Integer appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String description;
    @ManyToOne
    @JsonIgnoreProperties("blogs")
    private Type type;
    @ManyToMany
    @JsonIgnoreProperties("blogs")
    private List<Tag> tags;
    @ManyToOne
    @JsonIgnoreProperties("blogs")
    private User user;
//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Comment> comments;
}
