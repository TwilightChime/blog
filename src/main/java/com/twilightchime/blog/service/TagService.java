package com.twilightchime.blog.service;

import com.twilightchime.blog.model.common.PageRequest;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.dto.tag.request.TagRequestDTO;
import com.twilightchime.blog.model.vo.TagDetailVO;

import java.util.List;

public interface TagService {

    PageResult<TagDetailVO> getTagByPage(PageRequest pageRequest);

    List<TagDetailVO> getFullTagList();

    TagDetailVO postTag(TagRequestDTO tagRequestDTO);

    void deleteTag(Long id);
}
