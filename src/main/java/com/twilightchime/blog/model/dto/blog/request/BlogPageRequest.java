package com.twilightchime.blog.model.dto.blog.request;

import com.twilightchime.blog.model.common.PageRequest;
import lombok.Data;

import java.util.Set;

@Data
public class BlogPageRequest extends PageRequest {
    private String keyword;
    private Long typeId;
    private Set<Long> tagIds;
}
