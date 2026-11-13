package com.twilightchime.blog.convert.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.entity.Blog;
import com.twilightchime.blog.convert.TagBaseConvert;
import com.twilightchime.blog.convert.TypeBaseConvert;
import com.twilightchime.blog.convert.UserBaseConvert;
import com.twilightchime.blog.model.vo.BlogDetailVO;
import com.twilightchime.blog.utils.PageAdapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.Objects;

@Mapper(componentModel = "spring", uses = {TypeBaseConvert.class, TagBaseConvert.class, UserBaseConvert.class})
public interface BlogDetailConvert {

//    @Mapping(target = "type", source = "type")
//    @Mapping(target = "tags", source = "tags")
//    @Mapping(target = "user", source = "user")
    BlogDetailVO toDetailVO(Blog blog);

    default PageResult<BlogDetailVO> toPageResult(Page<Blog> page) {
        if (page.isEmpty()) {
            return PageResult.empty(page.getNumber() + 1, page.getSize());
        }

        return PageAdapter.from(page.map(this::toDetailVO));
    }

    default PageResult<BlogDetailVO> toPageResult(IPage<Blog> page) {
        if (page.getSize() == 0 || page.getRecords().isEmpty()) {
            return PageResult.empty((int)page.getCurrent(), (int)page.getSize());
        }

        return PageAdapter.from(page.convert(this::toDetailVO));
    }
}
