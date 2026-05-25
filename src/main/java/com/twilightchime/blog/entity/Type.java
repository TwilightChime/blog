package com.twilightchime.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "t_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_seq")
    @SequenceGenerator(name = "default_seq",
            sequenceName = "hibernate_sequence",
            allocationSize = 1)
    private Long id;
    private String name;
    private String pic_url;
    private String color;
    @OneToMany(mappedBy = "type")
    @JsonIgnoreProperties("type")
    private List<Blog> blogs;
}
