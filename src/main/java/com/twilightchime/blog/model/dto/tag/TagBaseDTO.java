package com.twilightchime.blog.model.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagBaseDTO {
    private Long id;
    private String name;
}
