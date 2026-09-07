package com.twilightchime.blog.service;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.dto.request.BlogRequestDTO;
import com.twilightchime.blog.vo.BlogDetailVO;
import jakarta.annotation.Nonnull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BlogService {
    BlogDetailVO getBlogDetail(Long id);

    BlogDetailVO postBlog(BlogRequestDTO blogRequestDTO);

    @Transactional(readOnly = true)
    PageResult<BlogDetailVO> getBlogByPage(@Nonnull PageRequest pageRequest);

    @Transactional(readOnly = true)
    PageResult<BlogDetailVO> getBlogByPage(@Nonnull PageRequest pageRequest, String keyword, String type);

    void deleteBlog(Long id);

    Long countBlog();

    Long countViews();

    Long countAppreciate();

    Long countComment();

    List<String> ViewCountByMonth();

    List<String> BlogCountByMonth();

    List<String> appreciateCountByMonth();
}
