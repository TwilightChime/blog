package com.twilightchime.blog.convert;

import com.twilightchime.blog.model.dto.tag.TagBaseDTO;
import com.twilightchime.blog.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagBaseConvert {
    TagBaseDTO toBaseDTO(Tag entity);

    @Mapping(target = "blogs", ignore = true)
    Tag toEntity(TagBaseDTO dto);

    List<TagBaseDTO> toBaseDTOList(List<Tag> entityList);
}
