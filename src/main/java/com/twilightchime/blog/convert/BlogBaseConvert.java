package com.twilightchime.blog.convert;

import com.twilightchime.blog.model.dto.blog.BlogBaseDTO;
import com.twilightchime.blog.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BlogBaseConvert {
    BlogBaseDTO toBaseDTO(Blog entity);

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "user", ignore = true)
    Blog toEntity(BlogBaseDTO dto);

    List<BlogBaseDTO> toBaseDTOList(List<Blog> entityList);
}
