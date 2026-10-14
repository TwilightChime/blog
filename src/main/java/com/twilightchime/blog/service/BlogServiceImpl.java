package com.twilightchime.blog.service;

import com.twilightchime.blog.model.common.PageRequest;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.dao.BlogRepository;
import com.twilightchime.blog.model.dto.blog.request.BlogRequestDTO;
import com.twilightchime.blog.entity.Blog;
import com.twilightchime.blog.exception.BusinessException;
import com.twilightchime.blog.mapper.request.BlogRequestMapper;
import com.twilightchime.blog.mapper.vo.BlogDetailMapper;
import com.twilightchime.blog.model.vo.BlogDetailVO;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
//    @Cacheable(value = "blog:list")
    public PageResult<BlogDetailVO> getBlogByPage(@Nonnull PageRequest pageRequest) {
        log.info("查询博客数据分页列表: num={}, size={}", pageRequest.getPageNumber(), pageRequest.getPageSize());
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize(), pageRequest.getSort());
        Page<Blog> blogPage = blogRepository.findAll(pageable);
        return blogDetailMapper.toPageResult(blogPage);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResult<BlogDetailVO> getBlogByPage(@Nonnull PageRequest pageRequest, String keyword, String type) {
        log.info("查询博客数据分页列表: num={}, size={}, keyword={}, type={}", pageRequest.getPageNumber(), pageRequest.getPageSize(), keyword, type);
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
    @Cacheable(value = "blog:detail", key = "#id")
    public BlogDetailVO getBlogDetail(Long id) {
        log.info("查询博客详情: blogId={}", id);
        Blog blog = blogRepository.findById(id).orElseThrow(() -> {
            log.warn("查询博客不存在: blogId={}", id);
            return new BusinessException("Blog Not Found");
        });
        return blogDetailMapper.toDetailVO(blog);
    }

    @Override
    @CacheEvict(value = "blog:list", allEntries = true)
    public BlogDetailVO createBlog(BlogRequestDTO blogRequestDTO) {
        log.info("创建博客: title={}, flag={}", blogRequestDTO.getTitle(), blogRequestDTO.getFlag());
        if (blogRequestDTO.getId() == null) {
            blogRequestDTO.setViews(0);
            Blog blog = blogRequestMapper.toEntity(blogRequestDTO);
            return blogDetailMapper.toDetailVO(blogRepository.save(blog));
        } else {
            log.warn("创建博客失败, id不为null: blogId={}", blogRequestDTO.getId());
            throw new BusinessException("博客id有值, 无法创建博客");
        }

    }

    @Override
    @CacheEvict(value = "blog:detail", key = "#blogRequestDTO.id")
    public BlogDetailVO updateBlog(@Nonnull BlogRequestDTO blogRequestDTO) {
        log.info("更新博客: blogId={}, title={}", blogRequestDTO.getId(), blogRequestDTO.getTitle());
        if (blogRequestDTO.getId() == null) {
            log.warn("更新博客失败, id为空: blogTitle={}", blogRequestDTO.getTitle());
            throw new BusinessException("博客id为空, 无法更新博客");
        }
        Blog blog = blogRepository.findById(blogRequestDTO.getId()).orElseThrow(() -> {
            log.warn("更新博客失败, 博客id不存在: blogId={}", blogRequestDTO.getId());
            return new BusinessException("该博客id不存在, 无法更新博客");
        });
        blogRequestMapper.updateEntity(blogRequestDTO, blog);
        return blogDetailMapper.toDetailVO(blogRepository.save(blog));
    }

    @Override
    @CacheEvict(value = "blog:detail", key = "#id")
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @CacheEvict(value = "blog:detail", allEntries = true)
    public void clearCache() {}

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

