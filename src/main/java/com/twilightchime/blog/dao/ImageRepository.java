package com.twilightchime.blog.dao;

import com.twilightchime.blog.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
