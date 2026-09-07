package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.dto.request.TypeRequestDTO;
import com.twilightchime.blog.service.TypeService;
import com.twilightchime.blog.vo.TypeDetailVO;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class TypeAdminController {
    private final TypeService typeService;

    @GetMapping("/getFullTypeList")
    public Result<List<TypeDetailVO>> getFullTypeList(){
        return Result.ok("获取所有分类列表", typeService.getFullTypeList());
    }

    @PostMapping("/types")
    public Result<Void> postType(@Nonnull @RequestBody Map<String, TypeRequestDTO> para){
        TypeRequestDTO type = para.get("type");
        TypeDetailVO typeDetailVO= typeService.postType(type);
        if(type.getId() == null){
            return Result.ok("新增标签成功");
        } else {
            return Result.ok("修改标签成功");
        }
    }

    @GetMapping("/types/{id}/delete")
    public Result<Void> deleteType(@PathVariable Long id){
        typeService.deleteType(id);
        return Result.ok("删除标签成功");
    }

}
