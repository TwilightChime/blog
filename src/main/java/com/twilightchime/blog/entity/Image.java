package com.twilightchime.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_seq")
    @SequenceGenerator(name = "default_seq",
            sequenceName = "hibernate_sequence",
            allocationSize = 1)
    private Long id;
    private String name;

    @Column(name = "file_path")
    private String path;

    @Column(name = "thumbnail_path")
    private String thumbnail;

    @Column(name = "original_filename")
    private String originalName;

    @Column(name = "file_size")
    private Long size;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;
    private Long width;
    private Long height;
}
