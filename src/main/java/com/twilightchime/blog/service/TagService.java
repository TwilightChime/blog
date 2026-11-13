package com.twilightchime.blog.service;

import com.twilightchime.blog.entity.Tag;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.dto.tag.request.TagCreateRequest;
import com.twilightchime.blog.model.dto.tag.request.TagPageRequest;
import com.twilightchime.blog.model.dto.tag.request.TagUpdateRequest;
import com.twilightchime.blog.model.vo.TagVO;

import java.util.List;
import java.util.Set;

public interface TagService {

    PageResult<TagVO> getTagByPage(TagPageRequest tagPageRequest);

    List<TagVO> getTagList(String name, int limit);

    TagVO createTag(TagCreateRequest tagCreateRequest);

    TagVO updateTag(TagUpdateRequest tagUpdateRequest);

    void deleteTag(Long id);

    Tag findOrThrowById(Long id);

    Tag findOrCreateByName(String name);

    Set<Tag> findOrCreateByNames(Set<String> nameList);

    long countByName(String name);

//    TagDetailVO postTag(TagRequestDTO tagRequestDTO);
}
