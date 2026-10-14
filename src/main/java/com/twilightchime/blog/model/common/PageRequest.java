package com.twilightchime.blog.model.common;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class PageRequest {
    @Min(value = 1, message = "页码最小为1")
    private int pageNumber = 1;

    @Min(value = 5, message = "每页大小最小为5")
    @Max(value = 100, message = "每页大小最大为100")
    private int pageSize = 5;

    private String sortField = null;
    private String sortOrder = "desc";

    public Sort getSort() {
        if (sortField == null || sortField.trim().isEmpty()) {
            return Sort.unsorted();
        }
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.equalsIgnoreCase("asc") ? "asc" : "desc");
        return Sort.by(direction, sortField);
    }

    // 获取Pageable对象（用于Spring Data JPA）
    public Pageable getPageable() {
        return org.springframework.data.domain.PageRequest.of(pageNumber - 1, pageSize, getSort());
    }
}
