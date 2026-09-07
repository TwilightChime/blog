package com.twilightchime.blog.service;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.dao.TagRepository;
import com.twilightchime.blog.dto.request.TagRequestDTO;
import com.twilightchime.blog.entity.Tag;
import com.twilightchime.blog.exception.BusinessException;
import com.twilightchime.blog.mapper.request.TagRequestMapper;
import com.twilightchime.blog.mapper.vo.TagDetailMapper;
import com.twilightchime.blog.vo.TagDetailVO;
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
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagDetailMapper tagDetailMapper;
    private final TagRequestMapper tagRequestMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResult<TagDetailVO> getTagByPage(@Nonnull PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize(), pageRequest.getSort());
        Page<Tag> tagPage = tagRepository.findAll(pageable);
        return tagDetailMapper.toPageResult(tagPage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDetailVO> getFullTagList(){
        return tagRepository.findAll().stream().map(tagDetailMapper::toDetailVO).toList();
    }

    @Override
    public TagDetailVO postTag(@Nonnull TagRequestDTO tagRequestDTO) {
        Tag tag;
        if (tagRequestDTO.getId() == null) {    //新建标签
            Tag tagRepositoryByName = tagRepository.findByName(tagRequestDTO.getName());
            if (tagRepositoryByName != null){
                throw new BusinessException("标签名称已存在");
            }
            tag = tagRequestMapper.toEntity(tagRequestDTO);
        } else {    //更新标签
            tag = tagRepository.findById(tagRequestDTO.getId()).orElseThrow(() -> {
                log.error("不存在该标签");
                return new BusinessException("不存在该标签");
            });
            tagRequestMapper.updateEntity(tagRequestDTO, tag);
        }
        return tagDetailMapper.toDetailVO(tagRepository.save(tag));
    }

    @Override
    public void deleteTag(Long id){
        tagRepository.deleteById(id);
    }
}
