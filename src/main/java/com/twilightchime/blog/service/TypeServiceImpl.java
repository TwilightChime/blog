package com.twilightchime.blog.service;

import com.twilightchime.blog.convert.request.TypeRequestConvert;
import com.twilightchime.blog.convert.vo.TypeDetailConvert;
import com.twilightchime.blog.model.common.PageRequest;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.dao.TypeRepository;
import com.twilightchime.blog.model.dto.type.request.TypeRequestDTO;
import com.twilightchime.blog.entity.Type;
import com.twilightchime.blog.exception.BusinessException;
import com.twilightchime.blog.model.vo.TypeDetailVO;
import jakarta.annotation.Nonnull;
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
    private final TypeDetailConvert typeDetailConvert;
    private final TypeRequestConvert typeRequestConvert;

    @Override
    @Transactional(readOnly = true)
    public PageResult<TypeDetailVO> getTypeByPage(@Nonnull PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize(), pageRequest.getSort());
        Page<Type> typePage = typeRepository.findAll(pageable);
        return typeDetailConvert.toPageResult(typePage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeDetailVO> getFullTypeList(){
        return typeRepository.findAll().stream().map(typeDetailConvert::toDetailVO).toList();
    }

    @Override
    public TypeDetailVO postType(@Nonnull TypeRequestDTO typeRequestDTO) {
        Type type;
        if (typeRequestDTO.getId() == null){
            if (typeRepository.findByName(typeRequestDTO.getName()) != null){
                throw new BusinessException("分类名称已存在");
            }
            type = typeRequestConvert.toEntity(typeRequestDTO);
        } else {
            type = typeRepository.findById(typeRequestDTO.getId()).orElseThrow(() -> new BusinessException("该分类不存在"));
            typeRequestConvert.updateEntity(typeRequestDTO, type);
        }
        return typeDetailConvert.toDetailVO(typeRepository.save(type));
    }

    @Override
    public void deleteType(Long id){
        typeRepository.deleteById(id);
    }

    @Override
    public Type getByIdOrThrow(Long id){
        return typeRepository.findById(id).orElseThrow(() -> new BusinessException("分类Id不存在"));
    }
}
