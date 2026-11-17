package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.common.Result;
import com.twilightchime.blog.model.dto.blog.request.BlogCreateRequest;
import com.twilightchime.blog.model.dto.blog.request.BlogPageRequest;
import com.twilightchime.blog.model.dto.blog.request.BlogUpdateRequest;
import com.twilightchime.blog.model.vo.BlogVO;
import com.twilightchime.blog.service.BlogService;
import com.twilightchime.blog.model.vo.BlogDetailVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
public class BlogAdminController {

    private final BlogService blogService;

    @GetMapping("/blogs")
    public Result<PageResult<BlogVO>> getBlogPage(@Valid BlogPageRequest blogPageRequest) {
        log.info("获取博客分页列表请求: num={}, size={}, keyword={}, typeId={}, tagId={}", blogPageRequest.getPageNumber(),
                blogPageRequest.getPageSize(), blogPageRequest.getKeyword(), blogPageRequest.getTypeId(), blogPageRequest.getTagIds());

        PageResult<BlogVO> result = blogService.getBlogByPage(blogPageRequest);

        log.info("获取博客分页列表成功: num={}, size={}, keyword={}, typeId={}, tagId={}", blogPageRequest.getPageNumber(),
                blogPageRequest.getPageSize(), blogPageRequest.getKeyword(), blogPageRequest.getTypeId(), blogPageRequest.getTagIds());
        return Result.ok("获取blog分页信息", result);
    }

    @GetMapping("/blog/{id}")
    public Result<BlogDetailVO> getBlog(@PathVariable @Min(1) Long id) {
        log.info("获取博客详情请求: id={}", id);
        BlogDetailVO blogDetailVO = blogService.getBlogDetail(id);
        log.info("获取博客详情成功: id={}, title={}", id, blogDetailVO.getTitle());
        return Result.ok("获取blog内容详情", blogDetailVO);
    }

    @PostMapping("/blog/create")
    public Result<BlogDetailVO> createBlog(@Valid @RequestBody BlogCreateRequest blog,
                                           @RequestParam(required = false) @Min(1) Long typeId,
                                           @RequestParam(required = false) Set<String> tagNameList,
                                           @RequestParam @Min(1) @NotNull(message = "用户id不能为空") Long userId) {
        log.info("创建博客请求: title={}, flag={}", blog.getTitle(), blog.getFlag());
        BlogDetailVO blogDetailVO = blogService.createBlog(blog, typeId, tagNameList, userId);
        log.info("创建博客成功: blogId={}, title={}", blogDetailVO.getId(), blog.getTitle());
        return Result.ok("新建博客成功");
    }

    @PostMapping("/blog/update")
    public Result<BlogDetailVO> updateBlog(@Valid @RequestBody BlogUpdateRequest blogUpdateRequest,
                                           @RequestParam(required = false) @Min(1) Long typeId,
                                           @RequestParam(required = false) Set<String> tagNameList) {
        log.info("修改博客请求: blogId={}, title={}", blogUpdateRequest.getId(), blogUpdateRequest.getTitle());
        BlogDetailVO blogDetailVO = blogService.updateBlog(blogUpdateRequest, typeId, tagNameList);
        log.info("修改博客成功: blogId={}, title={}", blogDetailVO.getId(), blogUpdateRequest.getTitle());
        return Result.ok("修改博客成功");
    }

    @GetMapping("/blog/delete/{id}")
    public Result<Void> deleteBlog(@PathVariable @Min(1) Long id) {
        log.info("删除博客请求: blogId={}", id);
        blogService.deleteBlog(id);
        log.info("删除博客成功: blogId={}", id);
        return Result.ok("删除博客成功");
    }
}
