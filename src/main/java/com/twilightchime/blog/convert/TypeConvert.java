package com.twilightchime.blog.convert;

import com.twilightchime.blog.dto.TypeCreateDto;
import com.twilightchime.blog.entity.Type;
import com.twilightchime.blog.vo.TypeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TypeConvert {
    public Type toType(TypeCreateDto typeCreateDto) {
        Type type = new Type();
        BeanUtils.copyProperties(typeCreateDto, type);
        return type;
    }
    public TypeVo toTypeVo(Type type) {
        TypeVo typeVo = new TypeVo();
        BeanUtils.copyProperties(type, typeVo);
        return typeVo;
    }
}
