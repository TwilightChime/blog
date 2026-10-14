package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.model.common.Result;
import com.twilightchime.blog.model.dto.tag.request.TagRequestDTO;
import com.twilightchime.blog.service.TagService;
import com.twilightchime.blog.model.vo.TagDetailVO;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class TagAdminController {
    private final TagService tagService;

    @GetMapping("/getFullTagList")
    public Result<List<TagDetailVO>> getFullTagList(){
        return Result.ok("获取所有标签", tagService.getFullTagList());
    }

    @PostMapping("/tags")
    public Result<Void> postTag(@Nonnull @RequestBody Map<String, TagRequestDTO> para) {
        TagRequestDTO tagRequestDTO = para.get("tag");
        TagDetailVO tagDetailVO = tagService.postTag(tagRequestDTO);
        if (tagRequestDTO.getId() == null) {
            return Result.ok("新建标签成功");
        } else {
            return Result.ok("修改标签成功");
        }
    }

    @GetMapping("/tags/{id}/delete")
    public Result<Void> deleteTag(@Nonnull @PathVariable Long id){
        tagService.deleteTag(id);
        return Result.ok("删除标签成功");
    }

}
