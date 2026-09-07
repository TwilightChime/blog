package com.twilightchime.blog.service;

import com.twilightchime.blog.config.FileUploadProperties;
import com.twilightchime.blog.dao.ImageRepository;
import com.twilightchime.blog.entity.Image;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final FileUploadProperties fileUploadProperties;


    @SneakyThrows
    @Override
    public String uploadImage(@Nonnull MultipartFile file) {
        String contentType = file.getContentType();
        if (!isAllowedFileType(contentType)) {
            throw new IllegalArgumentException("Not allowed file type: " + contentType);
        }
        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = getFileExtension(originalFilename);
        String uniqueFileName = UUID.randomUUID() + "." + fileExtension;

        Path uploadPath = Paths.get(fileUploadProperties.getPath());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath);

        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        Long width = (long) bufferedImage.getWidth();
        Long height = (long) bufferedImage.getHeight();

        Image image = new Image();
        image.setFile_path(uniqueFileName);
        image.setName(StringUtils.hasText(uniqueFileName) ? uniqueFileName : originalFilename);
        image.setOriginal_filename(originalFilename);
        image.setFile_size(file.getSize());
        image.setWidth(width);
        image.setHeight(height);
        imageRepository.save(image);
        return image.getFile_path();
    }

    private boolean isAllowedFileType(String contentType) {
        if (contentType == null) return false;
        return Arrays.asList(fileUploadProperties.getAllowedTypes().split(",")).contains(contentType.trim());
    }

    @Nonnull
    private String getFileExtension(@Nonnull String originalFilename) {
        int index = originalFilename.lastIndexOf('.');
        return index == -1 ? "" : originalFilename.substring(index);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Image> listImage() {
        return imageRepository.findAll();
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}


