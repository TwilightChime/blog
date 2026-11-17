package com.twilightchime.blog.service;

import com.twilightchime.blog.entity.Type;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.dto.type.request.TypeCreateRequest;
import com.twilightchime.blog.model.dto.type.request.TypePageRequest;
import com.twilightchime.blog.model.dto.type.request.TypeUpdateRequest;
import com.twilightchime.blog.model.vo.TypeVO;

import java.util.List;

public interface TypeService {

    PageResult<TypeVO> getTypeByPage(TypePageRequest typePageRequest);

    List<TypeVO> getFullTypeList();

    TypeVO createType(TypeCreateRequest typeCreateRequest);

    TypeVO updateType(TypeUpdateRequest typeUpdateRequest);

    void deleteType(Long id);

    Type findOrThrowById(Long id);

    boolean existsByName(String name);

//    TypeDetailVO postType(TypeRequestDTO typeRequestDTO);
}
