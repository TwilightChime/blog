package com.twilightchime.blog.convert;

import com.twilightchime.blog.entity.Type;
import com.twilightchime.blog.vo.TypeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TypeConvert {
    public Type toEntity(Type vo) {
        Type entity = new Type();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }
    public TypeVo toVo(Type entity) {
        TypeVo vo = new TypeVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
