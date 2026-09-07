package com.twilightchime.blog.service;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.dto.request.TypeRequestDTO;
import com.twilightchime.blog.vo.TypeDetailVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TypeService {

    PageResult<TypeDetailVO> getTypeByPage(PageRequest pageRequest);

    @Transactional()
    List<TypeDetailVO> getFullTypeList();

    TypeDetailVO postType(TypeRequestDTO typeRequestDTO);

    void deleteType(Long id);
}
