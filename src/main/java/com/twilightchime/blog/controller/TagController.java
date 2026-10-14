package com.twilightchime.blog.controller;

import com.twilightchime.blog.model.common.PageRequest;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.common.Result;
import com.twilightchime.blog.service.TagService;
import com.twilightchime.blog.model.vo.TagDetailVO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/regs")
public class TagController {

    private final TagService tagService;

    @GetMapping("/getTagList")
    public Result<PageResult<TagDetailVO>> getTagPage(@RequestParam(required = false) String sortOrder,
                                                @RequestParam(required = false, defaultValue = "1") @Min(1) String pageNumber,
                                                @RequestParam(required = false, defaultValue = "5") @Min(5) @Max(100) String pageSize) {
        log.info("获取标签分页列表请求: num={}, size={}", pageNumber, pageSize);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(Integer.parseInt(pageNumber));
        pageRequest.setPageSize(Integer.parseInt(pageSize));
        PageResult<TagDetailVO> pageResult = tagService.getTagByPage(pageRequest);
        log.info("获取标签分页列表成功: num={}, size={}", pageNumber, pageSize);
        return Result.ok("tag", pageResult);
    }

    @GetMapping("/getFullTagList")
    public Result<List<TagDetailVO>> getFullTagList() {
        log.info("获取所有标签列表请求");
        List<TagDetailVO> tagList = tagService.getFullTagList();
        log.info("获取所有标签列表成功");
        return Result.ok("获取所有标签列表", tagList);
    }

}
