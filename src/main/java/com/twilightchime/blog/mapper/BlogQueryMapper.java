package com.twilightchime.blog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.twilightchime.blog.model.dto.BlogTagDTO;
import com.twilightchime.blog.model.dto.blog.request.BlogPageRequest;
import com.twilightchime.blog.model.vo.BlogVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface BlogQueryMapper {
    IPage<BlogVO> selectBlogListWithFilter(IPage<BlogVO> page, @Param("req") BlogPageRequest req);

    List<BlogTagDTO> selectTagsByBlogIds(List<Long> blogIds);

}
