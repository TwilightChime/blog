package com.twilightchime.blog.convert.vo;

import com.twilightchime.blog.convert.BlogBaseConvert;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.model.vo.UserDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = { BlogBaseConvert.class })
public interface UserDetailConvert {

    @Mapping(target = "blogs", source = "blogs")
    UserDetailVO toDetailVO(User user);

    default PageResult<UserDetailVO> toPageResult(Page<User> page) {
        if (page == null || page.isEmpty()) {
            return null;
        }
        return PageResult.of(page.map(this::toDetailVO));
    }
}
