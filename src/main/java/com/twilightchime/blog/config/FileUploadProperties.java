package com.twilightchime.blog.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class FileUploadProperties {
    @Value("${file.upload.path}")
    private String path;

    @Value("${file.upload.image.path}")
    private String pathImage;

    @Value("${file.upload.image.storage.path}")
    private String pathImageStorage;

    @Value("${file.upload.image.avatar.path}")
    private String pathImageAvatar;
    private Long maxSize = 52428800L;
    private String allowedTypes = "image/jpeg,image/jpg,image/png,image/gif,image/ico";

    @PostConstruct
    public void init() {

    }


}
