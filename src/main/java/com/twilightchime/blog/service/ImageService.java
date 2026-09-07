package com.twilightchime.blog.service;

import com.twilightchime.blog.entity.Image;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    @Transactional
    String uploadImage(MultipartFile file);

    @Transactional(readOnly = true)
    List<Image> listImage();

    void deleteImage(Long id);
}
