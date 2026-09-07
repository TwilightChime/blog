package com.twilightchime.blog.controller;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.service.TypeService;
import com.twilightchime.blog.vo.TypeDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;

    @GetMapping("/getTypeList")
    public Result<PageResult<TypeDetailVO>> getTypePage(@RequestParam(required = false) String sortOrder,
                                                        @RequestParam(required = false, defaultValue = "1") String pageNumber,
                                                        @RequestParam(required = false, defaultValue = "5") String pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(Integer.parseInt(pageNumber));
        pageRequest.setPageSize(Integer.parseInt(pageSize));
        PageResult<TypeDetailVO> pageResult = typeService.getTypeByPage(pageRequest);
        return Result.ok("type", pageResult);
    }

    @GetMapping("/getFullTypeList")
    public Result<List<TypeDetailVO>> getFullTypeList() {
        return Result.ok("获取所有分类列表", typeService.getFullTypeList());
    }

}
