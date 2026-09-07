package com.twilightchime.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("blogs")
    private Type type;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("blogs")
    private List<Tag> tags;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("blogs")
    private User user;
//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Comment> comments;
}
