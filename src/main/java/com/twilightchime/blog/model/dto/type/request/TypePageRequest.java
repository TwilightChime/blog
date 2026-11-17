package com.twilightchime.blog.model.dto.type.request;

import com.twilightchime.blog.model.common.PageRequest;
import lombok.Data;

@Data
public class TypePageRequest extends PageRequest {
    private String name;
}
