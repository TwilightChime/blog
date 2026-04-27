package com.twilightchime.blog.convert;

import com.twilightchime.blog.entity.User;
import com.twilightchime.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserConvert {
    public User toEntity(UserVo vo) {
        User entity = new User();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }
    public UserVo toVo(User entity) {
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
