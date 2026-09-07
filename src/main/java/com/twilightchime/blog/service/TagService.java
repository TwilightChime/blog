package com.twilightchime.blog.service;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.dto.request.TagRequestDTO;
import com.twilightchime.blog.vo.TagDetailVO;

import java.util.List;

public interface TagService {

    PageResult<TagDetailVO> getTagByPage(PageRequest pageRequest);

    List<TagDetailVO> getFullTagList();

    TagDetailVO postTag(TagRequestDTO tagRequestDTO);

    void deleteTag(Long id);
}
