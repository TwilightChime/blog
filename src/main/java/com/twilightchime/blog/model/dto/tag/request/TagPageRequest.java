package com.twilightchime.blog.model.dto.tag.request;

import com.twilightchime.blog.model.common.PageRequest;
import lombok.Data;

@Data
public class TagPageRequest extends PageRequest {
    private String tagName;
}
