package com.twilightchime.blog.controller;

import com.twilightchime.blog.aspect.RateLimit;
import com.twilightchime.blog.model.common.PageRequest;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.common.Result;
import com.twilightchime.blog.service.BlogService;
import com.twilightchime.blog.model.vo.BlogDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "博客管理", description = "博客增删改查接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/regs")
public class BlogController {

    private final BlogService blogService;

    @Operation(summary = "获取博客列表", description = "支持分页查询")
    @RateLimit(key = "blog:list", count = 30, time = 60)
    @GetMapping("/blogs")
    public Result<PageResult<BlogDetailVO>> getBlogPage(@RequestParam(required = false) String sortOrder,
                                                  @RequestParam(required = false, defaultValue = "1") @Min(1) String pageNumber,
                                                  @RequestParam(required = false, defaultValue = "5") @Min(5) @Max(100) String pageSize) {
        log.info("获取博客分页列表请求: num={}, size={}", pageNumber, pageSize);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(Integer.parseInt(pageNumber));
        pageRequest.setPageSize(Integer.parseInt(pageSize));
//        pageRequest.setSortField(para.get("sortField").toString());
//        pageRequest.setSortOrder(para.get("sortOrder").toString());


        PageResult<BlogDetailVO> result = blogService.getBlogByPage(pageRequest);
        log.info("获取博客分页列表成功: num={}, size={}", pageNumber, pageSize);
        return Result.ok("获取blog分页信息", result);
    }

    @Operation(summary = "根据博客id获取详情")
    @GetMapping("/blog/{id}")
    public Result<BlogDetailVO> getBlogById(@PathVariable @Min(1) Long id) {
        log.info("获取博客详情请求: blogId={}", id);
        BlogDetailVO blogDetail = blogService.getBlogDetail(id);
        log.info("获取博客详情成功: blogId={}", id);
        return Result.ok("获取博客详情", blogDetail);
    }

}
