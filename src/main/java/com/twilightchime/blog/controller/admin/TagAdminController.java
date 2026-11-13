package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.common.Result;
import com.twilightchime.blog.model.dto.tag.request.TagCreateRequest;
import com.twilightchime.blog.model.dto.tag.request.TagPageRequest;
import com.twilightchime.blog.model.dto.tag.request.TagUpdateRequest;
import com.twilightchime.blog.model.vo.TagVO;
import com.twilightchime.blog.service.TagService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class TagAdminController {
    private final TagService tagService;

    @GetMapping("/tags")
    public Result<PageResult<TagVO>> getTagPage(TagPageRequest tagPageRequest) {
        log.info("获取标签分页列表请求: num={}, size={}", tagPageRequest.getPageNumber(), tagPageRequest.getPageSize());
        PageResult<TagVO> pageResult = tagService.getTagByPage(tagPageRequest);
        log.info("获取标签分页列表成功: num={}, size={}", tagPageRequest.getPageNumber(), tagPageRequest.getPageSize());
        return Result.ok("tag", pageResult);
    }

    @GetMapping("/TagList")
    public Result<List<TagVO>> getTagList(@RequestParam @NotBlank String tagName, @RequestParam(defaultValue = "5") @Min(1) int limit) {
        return Result.ok("获取标签列表", tagService.getTagList(tagName, limit));
    }

    @PostMapping("/tag/create")
    public Result<TagVO> createTag(@RequestBody TagCreateRequest tagCreateRequest) {
        return Result.ok("新增标签成功", tagService.createTag(tagCreateRequest));
    }

    @PostMapping("/tag/update")
    public Result<TagVO> updateTag(@RequestBody TagUpdateRequest tagUpdateRequest) {
        return Result.ok("更新标签成功", tagService.updateTag(tagUpdateRequest));
    }

    @GetMapping("/tag/delete/{id}")
    public Result<Void> deleteTag(@PathVariable Long id){
        tagService.deleteTag(id);
        return Result.ok("删除标签成功");
    }


//    @PostMapping("/tags")
//    public Result<Void> postTag(@RequestBody Map<String, TagRequestDTO> para) {
//        TagRequestDTO tagRequestDTO = para.get("tag");
//        TagDetailVO tagDetailVO = tagService.postTag(tagRequestDTO);
//        if (tagRequestDTO.getId() == null) {
//            return Result.ok("新建标签成功");
//        } else {
//            return Result.ok("修改标签成功");
//        }
//    }
}
