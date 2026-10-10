package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.dto.request.BlogRequestDTO;
import com.twilightchime.blog.service.BlogService;
import com.twilightchime.blog.vo.BlogDetailVO;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class BlogAdminController {

    private final BlogService blogService;

    @PostMapping("/getBlogList")
    public Result<PageResult<BlogDetailVO>> getBlogPage(@Nonnull @RequestBody Map<String, Object> para) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(Integer.parseInt(para.get("pageNumber").toString()));
        pageRequest.setPageSize(Integer.parseInt(para.get("pageSize").toString()));
//        pageRequest.setSortField(para.get("sortField").toString());
//        pageRequest.setSortOrder(para.get("sortOrder").toString());
        String title = (String) para.get("title");
        String typeId = (String) para.get("typeId");

        log.info("获取博客分页列表请求: num={}, size={}, title={}, typeId={}", pageRequest.getPageNumber(), pageRequest.getPageSize(), title, typeId);
        PageResult<BlogDetailVO> result = blogService.getBlogByPage(pageRequest, title, typeId);
        log.info("获取博客分页列表成功: num={}, size={}, title={}, typeId={}", pageRequest.getPageNumber(), pageRequest.getPageSize(), title, typeId);
        return Result.ok("获取blog分页信息", result);
    }

    @PostMapping("/blogs")
    public Result<BlogDetailVO> postBlog(@Nonnull @RequestBody Map<String, BlogRequestDTO> para) {
        BlogRequestDTO blogRequestDTO = para.get("blog");

        if (blogRequestDTO.getId() == null) {
            log.info("创建博客请求: title={}, flag={}", blogRequestDTO.getTitle(), blogRequestDTO.getFlag());
            BlogDetailVO blogDetailVO = blogService.createBlog(blogRequestDTO);
            log.info("创建博客成功: blogId={}, title={}", blogDetailVO.getId(), blogRequestDTO.getTitle());
            return Result.ok("新建博客成功");
        }  else {
            log.info("修改博客请求: blogId={}, title={}", blogRequestDTO.getId(), blogRequestDTO.getTitle());
            BlogDetailVO blogDetailVO = blogService.updateBlog(blogRequestDTO);
            log.info("修改博客成功: blogId={}, title={}", blogDetailVO.getId(), blogRequestDTO.getTitle());
            return Result.ok("修改博客成功");
        }
    }

    @GetMapping("/blogs/{id}/delete")
    public Result<Void> deleteBlog(@PathVariable Long id) {
        log.info("删除博客请求: blogId={}", id);
        blogService.deleteBlog(id);
        log.info("删除博客成功: blogId={}", id);
        return Result.ok("删除博客成功");
    }
}
