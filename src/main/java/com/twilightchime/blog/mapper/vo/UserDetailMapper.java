package com.twilightchime.blog.mapper.vo;

import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.mapper.BlogBaseMapper;
import com.twilightchime.blog.vo.UserDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = { BlogBaseMapper.class })
public interface UserDetailMapper {

    @Mapping(target = "blogs", source = "blogs")
    UserDetailVO toDetailVO(User user);

    default PageResult<UserDetailVO> toPageResult(Page<User> page) {
        if (page == null || page.isEmpty()) {
            return null;
        }
        return PageResult.of(page.map(this::toDetailVO));
    }
}
