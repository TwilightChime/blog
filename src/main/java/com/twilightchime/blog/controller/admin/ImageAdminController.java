package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.entity.Image;
import com.twilightchime.blog.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/images")
public class ImageAdminController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        String imageUrl = imageService.uploadImage(file);
        return Result.ok("upload image", imageUrl);
    }

    @GetMapping("/getImageList")
    public Result<List<Image>> getAllImages() {
        return Result.ok("imageList", imageService.listImage());
    }

    @GetMapping("/delImg/{id}")
    public Result<Void> deleteImageById(@PathVariable Long id) {
        imageService.deleteImage(id);
        return Result.ok("delete image");
    }
}
