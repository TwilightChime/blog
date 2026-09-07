package com.twilightchime.blog.service;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.dao.BlogRepository;
import com.twilightchime.blog.dto.request.BlogRequestDTO;
import com.twilightchime.blog.entity.Blog;
import com.twilightchime.blog.exception.BusinessException;
import com.twilightchime.blog.mapper.request.BlogRequestMapper;
import com.twilightchime.blog.mapper.vo.BlogDetailMapper;
import com.twilightchime.blog.vo.BlogDetailVO;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final BlogDetailMapper blogDetailMapper;
    private final BlogRequestMapper blogRequestMapper;

    @Transactional(readOnly = true)
    @Override
    public PageResult<BlogDetailVO> getBlogByPage(@Nonnull PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize(), pageRequest.getSort());
        Page<Blog> blogPage = blogRepository.findAll(pageable);
        return blogDetailMapper.toPageResult(blogPage);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResult<BlogDetailVO> getBlogByPage(@Nonnull PageRequest pageRequest, String keyword, String type) {
        Specification<Blog> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                Predicate titleLike = cb.like(root.get("title"), "%" + keyword + "%");
                Predicate contentLike = cb.like(root.get("content"), "%" + keyword + "%");
                predicates.add(cb.or(titleLike, contentLike));
            }

            if (type != null && !type.isEmpty()) {
                Predicate typeLike = cb.like(root.get("type"), "%" + type + "%");
                predicates.add(cb.or(typeLike));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize());
        Page<Blog> blogPage = blogRepository.findAll(specification, pageable);
        return blogDetailMapper.toPageResult(blogPage);
    }

    @Override
    public BlogDetailVO getBlogDetail(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new BusinessException("Blog Not Found"));
        return blogDetailMapper.toDetailVO(blog);
    }

    @Override
    public BlogDetailVO postBlog(@Nonnull BlogRequestDTO blogRequestDTO) {
        Blog blog;
        if (blogRequestDTO.getId() == null) {
            blogRequestDTO.setViews(0);
            blog = blogRequestMapper.toEntity(blogRequestDTO);
        } else {
            blog = blogRepository.findById(blogRequestDTO.getId()).orElseThrow(() -> new BusinessException("该博客不存在"));
            blogRequestMapper.updateEntity(blogRequestDTO, blog);
        }
        return blogDetailMapper.toDetailVO(blogRepository.save(blog));
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Override
    public Long countViews() {
        return blogRepository.countViews();
    }

    @Override
    public Long countAppreciate() {
        return blogRepository.countAppreciate();
    }

    @Override
    public Long countComment() {
        return blogRepository.countComment();
    }

    @Override
    public List<String> ViewCountByMonth() {
        return blogRepository.ViewCountByMonth();
    }

    @Override
    public List<String> BlogCountByMonth() {
        return blogRepository.BlogCountByMonth();
    }

    @Override
    public List<String> appreciateCountByMonth() {
        return blogRepository.appreciateCountByMonth();
    }
}

