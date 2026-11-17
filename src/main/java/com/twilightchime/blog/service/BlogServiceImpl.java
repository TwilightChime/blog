package com.twilightchime.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.twilightchime.blog.convert.request.BlogCreateConvert;
import com.twilightchime.blog.convert.request.BlogUpdateConvert;
import com.twilightchime.blog.convert.vo.BlogDetailConvert;
import com.twilightchime.blog.entity.Tag;
import com.twilightchime.blog.entity.Type;
import com.twilightchime.blog.mapper.BlogQueryMapper;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.dao.BlogRepository;
import com.twilightchime.blog.model.dto.BlogTagDTO;
import com.twilightchime.blog.model.dto.blog.request.BlogCreateRequest;
import com.twilightchime.blog.model.dto.blog.request.BlogPageRequest;
import com.twilightchime.blog.entity.Blog;
import com.twilightchime.blog.exception.BusinessException;
import com.twilightchime.blog.model.dto.blog.request.BlogUpdateRequest;
import com.twilightchime.blog.model.dto.tag.TagBaseDTO;
import com.twilightchime.blog.model.vo.BlogDetailVO;
import com.twilightchime.blog.model.vo.BlogVO;
import com.twilightchime.blog.utils.PageAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final BlogDetailConvert blogDetailConvert;
    private final BlogQueryMapper blogQueryMapper;
    private final TypeService typeService;
    private final TagService tagService;
    private final UserService userService;
    private final BlogCreateConvert blogCreateConvert;
    private final BlogUpdateConvert blogUpdateConvert;

//    @Transactional(readOnly = true)
//    @Override
////    @Cacheable(value = "blog:list")
//    public PageResult<BlogDetailVO> getBlogByPage(PageRequest pageRequest) {
//        log.info("查询博客数据分页列表: num={}, size={}", pageRequest.getPageNumber(), pageRequest.getPageSize());
//        Pageable pageable = pageRequest.getPageable();
//        Page<Blog> blogPage = blogRepository.findAll(pageable);
//        return PageAdapter.from(blogPage.map(blogDetailConvert::toDetailVO));
//    }

    @Transactional(readOnly = true)
    @Override
    public PageResult<BlogVO> getBlogByPage(BlogPageRequest blogPageRequest) {
        log.info("查询博客数据分页列表: num={}, size={}, keyword={}, typeId={}, tagIds={}", blogPageRequest.getPageNumber(),
                blogPageRequest.getPageSize(), blogPageRequest.getKeyword(), blogPageRequest.getTypeId(), blogPageRequest.getTagIds());

//        Specification<Blog> specification = (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            if (blogPageRequest.getKeyword() != null && !blogPageRequest.getKeyword().isEmpty()) {
//                Join<Blog, User> join = root.join("user");
//                Predicate titleLike = cb.like(root.get("title"), "%" + blogPageRequest.getKeyword() + "%");
//                Predicate userLike = cb.like(join.get("username"), "%" + blogPageRequest.getKeyword() + "%");
//                Predicate contentLike = cb.like(root.get("content"), "%" + blogPageRequest.getKeyword() + "%");
//                predicates.add(cb.or(titleLike, userLike, contentLike));
//            }
//            if (blogPageRequest.getTypeId() != null) {
//                Join<Blog, Type> join = root.join("type");
//                predicates.add(cb.equal(join.get("id"), blogPageRequest.getTypeId()));
//            }
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//        Pageable pageable = blogPageRequest.getPageable();
//        Page<Blog> blogPage = blogRepository.findAll(specification, pageable);

        IPage<BlogVO> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(blogPageRequest.getPageNumber(), blogPageRequest.getPageSize());
        IPage<BlogVO> blogPage = blogQueryMapper.selectBlogListWithFilter(page, blogPageRequest);

        List<Long> blogIds = blogPage.getRecords().stream().map(BlogVO::getId).toList();
        if (blogIds.isEmpty()) {
            return PageResult.empty((int) blogPage.getCurrent(), (int) blogPage.getSize());
        }
        List<BlogTagDTO> tagRows = blogQueryMapper.selectTagsByBlogIds(blogIds);
        Map<Long, Set<TagBaseDTO>> tagMap = tagRows.stream().collect(
                Collectors.groupingBy(
                        BlogTagDTO::getBlogId, Collectors.mapping(
                                row -> new TagBaseDTO(row.getId(), row.getName()), Collectors.toSet()
                        )
                )
        );
        blogPage.getRecords().forEach(blog -> blog.setTags(tagMap.getOrDefault(blog.getId(), Collections.emptySet())));

        return PageAdapter.from(blogPage);
    }

    @Transactional(readOnly = true)
    @Override
    @Cacheable(value = "blog:detail", key = "#id")
    public BlogDetailVO getBlogDetail(Long id) {
        log.info("查询博客详情: blogId={}", id);
        Blog blog = blogRepository.findById(id).orElseThrow(() -> {
            log.warn("查询博客不存在: blogId={}", id);
            return new BusinessException("Blog Not Found");
        });
        return blogDetailConvert.toDetailVO(blog);
    }

    @Override
    @CacheEvict(value = "blog:list", allEntries = true)
    public BlogDetailVO createBlog(BlogCreateRequest blogCreateRequest, Long typeId, Set<String> tagNameList, Long userId) {
        log.info("创建博客: title={}, flag={}", blogCreateRequest.getTitle(), blogCreateRequest.getFlag());

        Blog blog = blogCreateConvert.toEntity(blogCreateRequest);
        if (typeId != null) {
            Type type = typeService.findOrThrowById(typeId);
            blog.setType(type);
        }
        if (tagNameList != null && !tagNameList.isEmpty()) {
            Set<Tag> tags = tagService.findOrCreateByNames(tagNameList);
            blog.setTags(tags);
        }
        blog.setUser(userService.findOrThrowById(userId));

        return blogDetailConvert.toDetailVO(blogRepository.save(blog));
    }

    @Override
    @CacheEvict(value = "blog:detail", key = "#blogUpdateRequest.id")
    public BlogDetailVO updateBlog(BlogUpdateRequest blogUpdateRequest, Long typeId, Set<String> tagNameList) {
        log.info("更新博客: blogId={}, title={}", blogUpdateRequest.getId(), blogUpdateRequest.getTitle());

        Blog blog = blogRepository.findById(blogUpdateRequest.getId()).orElseThrow(() -> {
            log.warn("更新博客失败, 博客id不存在: blogId={}", blogUpdateRequest.getId());
            return new BusinessException("该博客id不存在, 无法更新博客");
        });
        blogUpdateConvert.mergeToEntity(blogUpdateRequest, blog);

        if (typeId != null) {
            blog.setType(typeService.findOrThrowById(typeId));
        }

        if (tagNameList != null && !tagNameList.isEmpty()) {
            blog.setTags(tagService.findOrCreateByNames(tagNameList));
        }

        return blogDetailConvert.toDetailVO(blogRepository.save(blog));
    }

    @Override
    @CacheEvict(value = "blog:detail", key = "#id")
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @CacheEvict(value = "blog:detail", allEntries = true)
    public void clearCache() {}

    @Transactional(readOnly = true)
    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public Long countViews() {
        return blogRepository.countViews();
    }

    @Transactional(readOnly = true)
    @Override
    public Long countAppreciate() {
        return blogRepository.countAppreciate();
    }

    @Transactional(readOnly = true)
    @Override
    public Long countComment() {
        return blogRepository.countComment();
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> ViewCountByMonth() {
        return blogRepository.ViewCountByMonth();
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> BlogCountByMonth() {
        return blogRepository.BlogCountByMonth();
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> appreciateCountByMonth() {
        return blogRepository.appreciateCountByMonth();
    }
}

