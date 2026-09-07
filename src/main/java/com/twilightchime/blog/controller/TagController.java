package com.twilightchime.blog.controller;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.service.TagService;
import com.twilightchime.blog.vo.TagDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/getTagList")
    public Result<PageResult<TagDetailVO>> getTagPage(@RequestParam(required = false) String sortOrder,
                                                @RequestParam(required = false, defaultValue = "1") String pageNumber,
                                                @RequestParam(required = false, defaultValue = "5") String pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(Integer.parseInt(pageNumber));
        pageRequest.setPageSize(Integer.parseInt(pageSize));
        PageResult<TagDetailVO> pageResult = tagService.getTagByPage(pageRequest);
        return Result.ok("tag", pageResult);
    }

    @GetMapping("/getFullTagList")
    public Result<List<TagDetailVO>> getFullTagList() {
        return Result.ok("获取所有标签列表", tagService.getFullTagList());
    }

}
