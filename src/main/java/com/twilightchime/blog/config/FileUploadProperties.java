package com.twilightchime.blog.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class FileUploadProperties {
    private String path = "./uploads/image/storage";
    private String pathAvatar = "./uploads/avatar/";
    private Long maxSize = 52428800L;
    private String allowedTypes = "image/jpeg,image/jpg,image/png,image/gif,image/ico";

    @PostConstruct
    public void init() {

    }


}
