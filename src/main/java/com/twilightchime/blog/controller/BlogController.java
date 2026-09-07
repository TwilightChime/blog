package com.twilightchime.blog.controller;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.service.BlogService;
import com.twilightchime.blog.vo.BlogDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

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

    @GetMapping("/blog/{id}")
    public Result<BlogDetailVO> getBlogById(@PathVariable Long id) {
        BlogDetailVO blogDetail = blogService.getBlogDetail(id);
        return Result.ok("获取博客详情", blogDetail);
    }

}
