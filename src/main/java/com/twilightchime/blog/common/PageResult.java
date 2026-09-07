package com.twilightchime.blog.common;

import jakarta.annotation.Nonnull;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResult<T> {
    private List<T> content;
    private int number;     //当前页码
    private int size;       //每页大小
    private long totalElements;     //总记录数
    private int totalPages;     //总页数
    private boolean first;      //是否第一页
    private boolean last;       //是否最后一页

    // 从Spring Data Page对象构建
    @Nonnull
    public static <T> PageResult<T> of(@Nonnull Page<T> page) {
        PageResult<T> result = new PageResult<>();
        result.setContent(page.getContent());
        result.setNumber(page.getNumber() + 1);
        result.setSize(page.getSize());
        result.setTotalElements(page.getTotalElements());
        result.setTotalPages(page.getTotalPages());
        result.setFirst(page.isFirst());
        result.setLast(page.isLast());
        return result;
    }
}
