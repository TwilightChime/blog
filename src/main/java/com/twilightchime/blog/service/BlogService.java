package com.twilightchime.blog.service;

import com.twilightchime.blog.model.common.PageRequest;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.dto.blog.request.BlogRequestDTO;
import com.twilightchime.blog.model.vo.BlogDetailVO;
import jakarta.annotation.Nonnull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogService {
    BlogDetailVO getBlogDetail(Long id);

    @Transactional(readOnly = true)
    PageResult<BlogDetailVO> getBlogByPage(@Nonnull PageRequest pageRequest);

    @Transactional(readOnly = true)
    PageResult<BlogDetailVO> getBlogByPage(@Nonnull PageRequest pageRequest, String keyword, String type);

    BlogDetailVO createBlog(@Nonnull BlogRequestDTO blogRequestDTO);

    BlogDetailVO updateBlog(@Nonnull BlogRequestDTO blogRequestDTO);

    void deleteBlog(Long id);

    Long countBlog();

    Long countViews();

    Long countAppreciate();

    Long countComment();

    List<String> ViewCountByMonth();

    List<String> BlogCountByMonth();

    List<String> appreciateCountByMonth();
}
