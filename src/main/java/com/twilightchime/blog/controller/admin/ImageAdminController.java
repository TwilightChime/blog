package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.model.common.Result;
import com.twilightchime.blog.entity.Image;
import com.twilightchime.blog.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/images")
public class ImageAdminController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        log.info("上传图片请求: name={}", file.getOriginalFilename());
        String imageUrl = imageService.uploadImage(file);
        log.info("上传图片成功: imageUrl={}", imageUrl);
        return Result.ok("upload image", imageUrl);
    }

    @GetMapping("/getImageList")
    public Result<List<Image>> getAllImages() {
        log.info("获取所有图片列表请求");
        List<Image> imgList = imageService.listImage();
        log.info("获取所有图片列表成功");
        return Result.ok("imageList", imgList);
    }

    @GetMapping("/delImg/{id}")
    public Result<Void> deleteImageById(@PathVariable Long id) {
        log.info("删除图片请求: imgId={}", id);
        imageService.deleteImage(id);
        log.info("删除图片成功: imgId={}", id);
        return Result.ok("delete image");
    }
}
