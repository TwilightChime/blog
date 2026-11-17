package com.twilightchime.blog.controller;

import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.common.Result;
import com.twilightchime.blog.model.dto.type.request.TypePageRequest;
import com.twilightchime.blog.model.vo.TypeVO;
import com.twilightchime.blog.service.TypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TypeController {

    private final TypeService typeService;

    @GetMapping("/Types")
    public Result<PageResult<TypeVO>> getTypePage(@Valid TypePageRequest typePageRequest) {
        log.info("获取分类分页列表请求: num={}, size={}", typePageRequest.getPageNumber(), typePageRequest.getPageSize());
        PageResult<TypeVO> pageResult = typeService.getTypeByPage(typePageRequest);
        log.info("获取分类分页列表成功: num={}, size={}", typePageRequest.getPageNumber(), typePageRequest.getPageSize());
        return Result.ok("type", pageResult);
    }

    @GetMapping("/fullTypeList")
    public Result<List<TypeVO>> getFullTypeList() {
        log.info("获取所有分类列表请求");
        List<TypeVO> typeList = typeService.getFullTypeList();
        log.info("获取所有分类列表成功");
        return Result.ok("获取所有分类列表", typeList);
    }

}
