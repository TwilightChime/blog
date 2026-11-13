package com.twilightchime.blog.service;

import com.twilightchime.blog.model.common.PageRequest;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.dto.blog.request.BlogCreateRequest;
import com.twilightchime.blog.model.dto.blog.request.BlogPageRequest;
import com.twilightchime.blog.model.dto.blog.request.BlogUpdateRequest;
import com.twilightchime.blog.model.vo.BlogDetailVO;
import com.twilightchime.blog.model.vo.BlogVO;

import java.util.List;
import java.util.Set;

public interface BlogService {

    PageResult<BlogDetailVO> getBlogByPage(PageRequest pageRequest);

    PageResult<BlogVO> getBlogByPage(BlogPageRequest blogPageRequest);

    BlogDetailVO getBlogDetail(Long id);

    BlogDetailVO createBlog(BlogCreateRequest blogCreateRequest, Long typeId, Set<String> tagNameList, Long userId);

    BlogDetailVO updateBlog(BlogUpdateRequest blogUpdateRequest, Long typeId, Set<String> tagNameList);

    void deleteBlog(Long id);

    Long countBlog();

    Long countViews();

    Long countAppreciate();

    Long countComment();

    List<String> ViewCountByMonth();

    List<String> BlogCountByMonth();

    List<String> appreciateCountByMonth();
}
