package com.twilightchime.blog.service;

import com.twilightchime.blog.entity.Type;
import com.twilightchime.blog.model.common.PageRequest;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.dto.type.request.TypeRequestDTO;
import com.twilightchime.blog.model.vo.TypeDetailVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TypeService {

    PageResult<TypeDetailVO> getTypeByPage(PageRequest pageRequest);

    @Transactional()
    List<TypeDetailVO> getFullTypeList();

    TypeDetailVO postType(TypeRequestDTO typeRequestDTO);

    void deleteType(Long id);

    Type getByIdOrThrow(Long id);
}
