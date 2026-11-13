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
import java.util.Set;

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
    private String description;
    private String firstPicture;
    private String flag;
    private Integer views;
    private Integer appreciation;
//    private boolean shareStatement;
//    private boolean sponsored;
//    private boolean published;
//    private boolean recommend;

    @CreatedDate
    @Column(name = "create_time", insertable = false, updatable = false)
    private LocalDateTime createTime;


    @LastModifiedDate
    @Column(name = "update_time", insertable = false, updatable = false)
    private LocalDateTime updateTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    @JsonIgnoreProperties("blogs")
    private Type type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("blogs")
    private Set<Tag> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("blogs")
    private User user;
//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Comment> comments;
}
