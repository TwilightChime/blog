package com.twilightchime.blog.service;

import com.twilightchime.blog.convert.vo.TagVoConvert;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.dao.TagRepository;
import com.twilightchime.blog.model.dto.tag.request.TagCreateRequest;
import com.twilightchime.blog.model.dto.tag.request.TagPageRequest;
import com.twilightchime.blog.entity.Tag;
import com.twilightchime.blog.exception.BusinessException;
import com.twilightchime.blog.model.dto.tag.request.TagUpdateRequest;
import com.twilightchime.blog.model.vo.TagVO;
import com.twilightchime.blog.utils.PageAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagVoConvert tagVoConvert;

    @Override
    @Transactional(readOnly = true)
    public PageResult<TagVO> getTagByPage(TagPageRequest tagPageRequest) {
        Pageable pageable = tagPageRequest.getPageable();
        Page<Tag> tagPage = tagRepository.findPageByNameLike(tagPageRequest.getTagName(), pageable);
        return PageAdapter.from(tagPage.map(tagVoConvert::toVo));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagVO> getTagList(String name, int limit){
        return tagRepository.findListByNameLike(name, limit).stream().map(tagVoConvert::toVo).toList();
    }

    @Override
    public TagVO createTag(TagCreateRequest tagCreateRequest) {
        Tag tag = tagRepository.findByName(tagCreateRequest.getName()).orElse(null);
        return tagVoConvert.toVo(Objects.requireNonNullElseGet(tag, () -> tagRepository.save(new Tag(tagCreateRequest.getName()))));
    }

    @Override
    public TagVO updateTag(TagUpdateRequest tagUpdateRequest) {
        if (existsByName(tagUpdateRequest.getName())) {
            throw new BusinessException("标签名已存在");
        }
        Tag tag = findOrThrowById(tagUpdateRequest.getId());
        tag.setName(tagUpdateRequest.getName());
        return tagVoConvert.toVo(tagRepository.save(tag));
    }

    @Override
    public void deleteTag(Long id){
        tagRepository.deleteById(id);
    }

    @Override
    public Tag findOrThrowById(Long id){
        return tagRepository.findById(id).orElseThrow(() -> new BusinessException("标签id不存在"));
    }

    @Override
    public Tag findOrCreateByName(String name) {
        return tagRepository.findByName(name).orElseGet(() -> tagRepository.save(new Tag(name)));
    }

    @Override
    public Set<Tag> findOrCreateByNames(Set<String> nameList) {
        return nameList.stream().map(this::findOrCreateByName).collect(Collectors.toSet());
    }

    @Override
    public boolean existsByName(String name) {
        return tagRepository.existsByName(name);
    }

    @Override
    public long countByName(String name) {
        return tagRepository.countByName(name);
    }


//    @Override
//    public TagDetailVO postTag(TagRequestDTO tagRequestDTO) {
//        Tag tag;
//        if (tagRequestDTO.getId() == null) {    //新建标签
//            Tag tagRepositoryByName = tagRepository.findByName(tagRequestDTO.getName()).orElse(null);
//            if (tagRepositoryByName != null){
//                throw new BusinessException("标签名称已存在");
//            }
//            tag = tagRequestConvert.toEntity(tagRequestDTO);
//        } else {    //更新标签
//            tag = tagRepository.findById(tagRequestDTO.getId()).orElseThrow(() -> {
//                log.error("不存在该标签");
//                return new BusinessException("不存在该标签");
//            });
//            tagRequestConvert.updateEntity(tagRequestDTO, tag);
//        }
//        return tagDetailConvert.toDetailVO(tagRepository.save(tag));
//    }
}
