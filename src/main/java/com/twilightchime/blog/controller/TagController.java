package com.twilightchime.blog.controller;

import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.common.Result;
import com.twilightchime.blog.model.dto.tag.request.TagPageRequest;
import com.twilightchime.blog.model.vo.TagVO;
import com.twilightchime.blog.service.TagService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TagController {

    private final TagService tagService;

    @GetMapping("/tags")
    public Result<PageResult<TagVO>> getTagPage(TagPageRequest tagPageRequest) {
        log.info("获取标签分页列表请求: num={}, size={}", tagPageRequest.getPageNumber(), tagPageRequest.getPageSize());
        PageResult<TagVO> pageResult = tagService.getTagByPage(tagPageRequest);
        log.info("获取标签分页列表成功: num={}, size={}", tagPageRequest.getPageNumber(), tagPageRequest.getPageSize());
        return Result.ok("tag", pageResult);
    }

    @GetMapping("/tagList")
    public Result<List<TagVO>> getTagList(@RequestParam @NotBlank String tagName, @RequestParam(defaultValue = "5") @Min(1) int limit) {
        log.info("获取标签列表请求");
        List<TagVO> tagList = tagService.getTagList(tagName, limit);
        log.info("获取标签列表成功");
        return Result.ok("获取标签列表", tagList);
    }

}
