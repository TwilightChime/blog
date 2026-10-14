package com.twilightchime.blog.controller;

import com.twilightchime.blog.model.common.PageRequest;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.common.Result;
import com.twilightchime.blog.service.TypeService;
import com.twilightchime.blog.model.vo.TypeDetailVO;
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
public class TypeController {

    private final TypeService typeService;

    @GetMapping("/getTypeList")
    public Result<PageResult<TypeDetailVO>> getTypePage(@RequestParam(required = false) String sortOrder,
                                                        @RequestParam(required = false, defaultValue = "1") @Min(1) String pageNumber,
                                                        @RequestParam(required = false, defaultValue = "5") @Min(1) @Max(100) String pageSize) {
        log.info("获取分类分页列表请求: num={}, size={}", pageNumber, pageSize);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(Integer.parseInt(pageNumber));
        pageRequest.setPageSize(Integer.parseInt(pageSize));
        PageResult<TypeDetailVO> pageResult = typeService.getTypeByPage(pageRequest);
        log.info("获取分类分页列表成功: num={}, size={}", pageNumber, pageSize);
        return Result.ok("type", pageResult);
    }

    @GetMapping("/getFullTypeList")
    public Result<List<TypeDetailVO>> getFullTypeList() {
        log.info("获取所有分类列表请求");
        List<TypeDetailVO> typeList = typeService.getFullTypeList();
        log.info("获取所有分类列表成功");
        return Result.ok("获取所有分类列表", typeList);
    }

}
