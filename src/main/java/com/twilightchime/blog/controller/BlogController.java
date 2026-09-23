package com.twilightchime.blog.controller;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.service.BlogService;
import com.twilightchime.blog.vo.BlogDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "博客管理", description = "博客增删改查接口")
@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @Operation(summary = "获取博客列表", description = "支持分页查询")
    @GetMapping("/blogs")
    public Result<PageResult<BlogDetailVO>> getBlogPage(@RequestParam(required = false) String sortOrder,
                                                  @RequestParam(required = false, defaultValue = "1") String pageNumber,
                                                  @RequestParam(required = false, defaultValue = "5") String pageSize) {

        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(Integer.parseInt(pageNumber));
        pageRequest.setPageSize(Integer.parseInt(pageSize));
//        pageRequest.setSortField(para.get("sortField").toString());
//        pageRequest.setSortOrder(para.get("sortOrder").toString());

        PageResult<BlogDetailVO> result = blogService.getBlogByPage(pageRequest);
        return Result.ok("获取blog分页信息", result);
    }

    @Operation(summary = "根据博客id获取详情")
    @GetMapping("/blog/{id}")
    public Result<BlogDetailVO> getBlogById(@PathVariable Long id) {
        BlogDetailVO blogDetail = blogService.getBlogDetail(id);
        return Result.ok("获取博客详情", blogDetail);
    }

}
