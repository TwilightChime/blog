package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.common.PageRequest;
import com.twilightchime.blog.common.PageResult;
import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.dto.request.UserRequestDTO;
import com.twilightchime.blog.service.UserService;
import com.twilightchime.blog.vo.UserDetailVO;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserAdminController {
    private final UserService userService;
//    @PostMapping("/setAvatar")
//    public Result<UserDetailVO> setAvatar(@Nonnull @RequestBody Map<String, Object> para) {
//        String picUrl = (String) para.get("pic_url");
//        long id = Long.parseLong(para.get("user_id").toString());
//        UserDetailVO u = userService.getUser(id);
//        if (u == null) {
//            return Result.error("ErrorCode.USER_NOT_EXIST");
//        } else {
//            u.setAvatar(picUrl);
//            User user = userService.saveUser(userConvert.toUserDto(u));
//            return Result.ok("userInfo", userConvert.toUserVo(user));
//        }
//    }

    @PostMapping("/getUserPage")
    public Result<PageResult<UserDetailVO>> getUserPage(@Nonnull @RequestBody Map<String, Object> para) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(Integer.parseInt(para.get("pageNumber").toString()));
        pageRequest.setPageSize(Integer.parseInt(para.get("pageSize").toString()));
        pageRequest.setSortField(para.get("sortField").toString());
        pageRequest.setSortOrder(para.get("sortOrder").toString());

        PageResult<UserDetailVO> result = userService.getUserByPage(pageRequest);
        return Result.ok("获取用户分页信息", result);
    }

    @GetMapping("users")
    public Result<List<UserDetailVO>> getAllUser() {
        return Result.ok("blogList", userService.getAllUser());
    }

    @PostMapping("/user")
    public Result<UserDetailVO> addUser(@Nonnull @RequestBody Map<String , UserRequestDTO> para) {
        UserRequestDTO userRequestDto = para.get("user");
        if (userRequestDto.getId() == null) {
            return Result.error("更新的用户id为空值");
        } else {
            return Result.ok("修改用户信息成功", userService.saveUser(userRequestDto));
        }
    }

    @DeleteMapping("/users/{id}/delete")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.ok("delete user success");
    }


}


