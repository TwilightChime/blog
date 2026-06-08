package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.convert.UserConvert;
import com.twilightchime.blog.service.UserService;
import com.twilightchime.blog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserAdminController {
    private final UserService userService;
    private final UserConvert userConvert;

    @PostMapping("/getUsers")
    public Result<PageResult<UserVo>> getUsers(@RequestBody Map<String, Object> para) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(Integer.parseInt(para.get("pageNumber").toString()));
        pageRequest.setPageSize(Integer.parseInt(para.get("pageSize").toString()));
        pageRequest.setSortField(para.get("sortField").toString());
        pageRequest.setSortOrder(para.get("sortOrder").toString());
        PageResult<UserVo> result = userService.getUsersByPage(pageRequest);
        return null;
    }
}
