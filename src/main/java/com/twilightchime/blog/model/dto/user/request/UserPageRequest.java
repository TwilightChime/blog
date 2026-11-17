package com.twilightchime.blog.model.dto.user.request;

import com.twilightchime.blog.model.common.PageRequest;
import lombok.Data;

@Data
public class UserPageRequest extends PageRequest {
    private String username;
}
