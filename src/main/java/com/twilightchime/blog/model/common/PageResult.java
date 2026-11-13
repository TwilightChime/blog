package com.twilightchime.blog.model.common;

import jakarta.annotation.Nonnull;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Collections;
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
    public static <T> PageResult<T> of(Page<T> page) {
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

    public static <T> PageResult<T> of(List<T> content, int number, int size, long totalElements, int totalPages, boolean first, boolean last) {
        PageResult<T> result = new PageResult<>();
        result.setContent(content);
        result.setNumber(number);
        result.setSize(size);
        result.setTotalElements(totalElements);
        result.setTotalPages(totalPages);
        result.setFirst(first);
        result.setLast(last);
        return result;
    }

    // 空结果（用于提前返回）
    public static <T> PageResult<T> empty(int current, int size) {
        return of(Collections.emptyList(), current, size, 0, 0, true, true);
    }
}
