package com.twilightchime.blog.mapper;

import com.twilightchime.blog.dto.TagBaseDTO;
import com.twilightchime.blog.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagBaseMapper {
    TagBaseDTO toBaseDTO(Tag entity);

    @Mapping(target = "blogs", ignore = true)
    Tag toEntity(TagBaseDTO dto);

    List<TagBaseDTO> toBaseDTOList(List<Tag> entityList);
}
