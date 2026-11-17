package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.common.Result;
import com.twilightchime.blog.model.dto.type.request.TypeCreateRequest;
import com.twilightchime.blog.model.dto.type.request.TypePageRequest;
import com.twilightchime.blog.model.dto.type.request.TypeUpdateRequest;
import com.twilightchime.blog.model.vo.TypeVO;
import com.twilightchime.blog.service.TypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class TypeAdminController {
    private final TypeService typeService;

    @GetMapping("/Types")
    public Result<PageResult<TypeVO>> getTypePage(@Valid TypePageRequest typePageRequest) {
        log.info("获取分类分页列表请求: num={}, size={}", typePageRequest.getPageNumber(), typePageRequest.getPageSize());
        PageResult<TypeVO> pageResult = typeService.getTypeByPage(typePageRequest);
        log.info("获取分类分页列表成功: num={}, size={}", typePageRequest.getPageNumber(), typePageRequest.getPageSize());
        return Result.ok("type", pageResult);
    }

    @GetMapping("/fullTypeList")
    public Result<List<TypeVO>> getFullTypeList(){
        return Result.ok("获取所有分类列表", typeService.getFullTypeList());
    }

    @PostMapping("/type/create")
    public Result<TypeVO> createType(@Valid @RequestBody TypeCreateRequest typeCreateRequest) {
        TypeVO typeVO = typeService.createType(typeCreateRequest);
        return Result.ok("新增分类成功", typeVO);
    }

    @PostMapping("/type/update")
    public Result<TypeVO> updateType(@Valid @RequestBody TypeUpdateRequest typeUpdateRequest) {
        TypeVO typeVO = typeService.updateType(typeUpdateRequest);
        return Result.ok("更新分类成功", typeVO);
    }

    @GetMapping("/type/delete/{id}")
    public Result<Void> deleteType(@PathVariable Long id){
        typeService.deleteType(id);
        return Result.ok("删除标签成功");
    }

//    @PostMapping("/types")
//    public Result<Void> postType(@Nonnull @RequestBody Map<String, TypeRequestDTO> para){
//        TypeRequestDTO type = para.get("type");
//        TypeDetailVO typeDetailVO= typeService.postType(type);
//        if(type.getId() == null){
//            return Result.ok("新增标签成功");
//        } else {
//            return Result.ok("修改标签成功");
//        }
//    }

}
