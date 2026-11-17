package com.twilightchime.blog.service;

import com.twilightchime.blog.convert.request.TypeCreateConvert;
import com.twilightchime.blog.convert.request.TypeUpdateConvert;
import com.twilightchime.blog.convert.vo.TypeVoConvert;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.dao.TypeRepository;
import com.twilightchime.blog.model.dto.type.request.TypeCreateRequest;
import com.twilightchime.blog.model.dto.type.request.TypePageRequest;
import com.twilightchime.blog.entity.Type;
import com.twilightchime.blog.exception.BusinessException;
import com.twilightchime.blog.model.dto.type.request.TypeUpdateRequest;
import com.twilightchime.blog.model.vo.TypeVO;
import com.twilightchime.blog.utils.PageAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    private final TypeVoConvert typeVoConvert;
    private final TypeCreateConvert typeCreateConvert;
    private final TypeUpdateConvert typeUpdateConvert;

    @Override
    @Transactional(readOnly = true)
    public PageResult<TypeVO> getTypeByPage(TypePageRequest typePageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(typePageRequest.getPageNumber() - 1, typePageRequest.getPageSize(), typePageRequest.getSort());
        Page<Type> typePage = typeRepository.findPageByNameLike(typePageRequest.getName(), pageable);
        return PageAdapter.from(typePage.map(typeVoConvert::toVo));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeVO> getFullTypeList(){
        return typeRepository.findAll().stream().map(typeVoConvert::toVo).toList();
    }

    @Override
    public TypeVO createType(TypeCreateRequest typeCreateRequest) {
        if (existsByName(typeCreateRequest.getName())) {
            throw new BusinessException("分类名称已存在");
        }
        Type type = typeCreateConvert.toEntity(typeCreateRequest);
        return typeVoConvert.toVo(typeRepository.save(type));
    }

    @Override
    public TypeVO updateType(TypeUpdateRequest typeUpdateRequest) {
        if (existsByName(typeUpdateRequest.getName())) {
            throw new BusinessException("分类名称已存在");
        }
        Type type = findOrThrowById(typeUpdateRequest.getId());
        typeUpdateConvert.mergeToEntity(typeUpdateRequest, type);
        return typeVoConvert.toVo(typeRepository.save(type));
    }

    @Override
    public void deleteType(Long id){
        typeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Type findOrThrowById(Long id){
        return typeRepository.findById(id).orElseThrow(() -> new BusinessException("分类Id不存在"));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return typeRepository.existsByName(name);
    }

    //    @Override
//    public TypeDetailVO postType(TypeRequestDTO typeRequestDTO) {
//        Type type;
//        if (typeRequestDTO.getId() == null){
//            if (typeRepository.findByName(typeRequestDTO.getName()) != null){
//                throw new BusinessException("分类名称已存在");
//            }
//            type = typeRequestConvert.toEntity(typeRequestDTO);
//        } else {
//            type = typeRepository.findById(typeRequestDTO.getId()).orElseThrow(() -> new BusinessException("该分类不存在"));
//            typeRequestConvert.updateEntity(typeRequestDTO, type);
//        }
//        return typeDetailConvert.toDetailVO(typeRepository.save(type));
//    }
}
