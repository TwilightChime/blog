package com.twilightchime.blog.convert.vo;

import com.twilightchime.blog.entity.Type;
import com.twilightchime.blog.model.vo.TypeVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeVoConvert {
    TypeVO toVo(Type type);

}
