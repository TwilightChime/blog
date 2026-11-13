package com.twilightchime.blog.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.twilightchime.blog.model.common.PageResult;
import org.springframework.data.domain.Page;

public final class PageAdapter {

    public static <T> PageResult<T> from(Page<T> page) {
        return PageResult.of(
                page.getContent(),
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    public static <T> PageResult<T> from(IPage<T> page) {
        return PageResult.of(
                page.getRecords(),
                (int) page.getCurrent(),        // MP 本来就是 1-based
                (int) page.getSize(),
                page.getTotal(),
                (int)page.getPages(),
                page.getCurrent() == 1,
                page.getCurrent() == page.getSize()
        );
    }
}
