package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.dto.request.BlogRequestDTO;
import com.twilightchime.blog.service.BlogService;
import com.twilightchime.blog.vo.BlogDetailVO;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

        PageResult<BlogDetailVO> result = blogService.getBlogByPage(pageRequest, title, typeId);
        return Result.ok("获取blog分页信息", result);
    }

    @PostMapping("/blogs")
    public Result<BlogDetailVO> postBlog(@Nonnull @RequestBody Map<String, BlogRequestDTO> para) {
        BlogRequestDTO blogRequestDTO = para.get("blog");
        BlogDetailVO blogDetailVO = blogService.postBlog(blogRequestDTO);
        if (blogRequestDTO.getId() == null) {
            return Result.ok("新建博客成功");
        }
        return Result.ok("修改博客成功");
    }

    @GetMapping("/blogs/{id}/delete")
    public Result<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return Result.ok("删除博客成功");
    }
}
