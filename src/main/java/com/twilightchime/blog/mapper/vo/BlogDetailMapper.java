package com.twilightchime.blog.mapper.vo;

import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.entity.Blog;
import com.twilightchime.blog.mapper.TagBaseMapper;
import com.twilightchime.blog.mapper.TypeBaseMapper;
import com.twilightchime.blog.mapper.UserBaseMapper;
import com.twilightchime.blog.vo.BlogDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {TypeBaseMapper.class, TagBaseMapper.class, UserBaseMapper.class})
public interface BlogDetailMapper {

    @Mapping(target = "type", source = "type")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "user", source = "user")
    BlogDetailVO toDetailVO(Blog blog);

//    default PageResult<BlogDetailVO> toPageDTO(Page<Blog> page) {
//        if (page == null || page.isEmpty()) {
//            return null;
//        }
//        List<BlogDetailVO> blogDetailVOList = page.getContent().stream().map(this::toDetailVO).toList();
//        return PageResult.of()
//    }
    default PageResult<BlogDetailVO> toPageResult(Page<Blog> page) {
        if (page == null || page.isEmpty()) {
            return null;
        }
        return PageResult.of(page.map(this::toDetailVO));
    }
}
