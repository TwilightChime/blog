package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.model.common.PageRequest;
import com.twilightchime.blog.model.common.PageResult;
import com.twilightchime.blog.model.common.Result;
import com.twilightchime.blog.exception.BusinessException;
import com.twilightchime.blog.model.dto.user.request.UserPageRequest;
import com.twilightchime.blog.model.dto.user.request.UserUpdateRequest;
import com.twilightchime.blog.service.UserService;
import com.twilightchime.blog.model.vo.UserDetailVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
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

//    @PostMapping("/getUserPage")
//    public Result<PageResult<UserDetailVO>> getUserPage(@Nonnull @RequestBody Map<String, Object> para) {
//        PageRequest pageRequest = new PageRequest();
//        pageRequest.setPageNumber(Integer.parseInt(para.get("pageNumber").toString()));
//        pageRequest.setPageSize(Integer.parseInt(para.get("pageSize").toString()));
//        pageRequest.setSortField(para.get("sortField").toString());
//        pageRequest.setSortOrder(para.get("sortOrder").toString());
//
//        PageResult<UserDetailVO> result = userService.getUserByPage(pageRequest);
//        return Result.ok("获取用户分页信息", result);
//    }

    @GetMapping("/users")
    public Result<PageResult<UserDetailVO>> getUserPage(@Valid UserPageRequest userPageRequest) {
        log.info("获取用户分页列表请求: num={}, size={}", userPageRequest.getPageNumber(), userPageRequest.getPageSize());
        PageResult<UserDetailVO> result = userService.getUserByPage(userPageRequest);
        log.info("获取用户分页列表成功: num={}, size={}", userPageRequest.getPageNumber(), userPageRequest.getPageSize());
        return Result.ok("获取用户分页信息", result);
    }

    @GetMapping("/userList")
    public Result<List<UserDetailVO>> getAllUser() {
        return Result.ok("blogList", userService.getAllUser());
    }

//    @PostMapping("/user")
//    public Result<UserDetailVO> addUser(@Nonnull @RequestBody Map<String , UserRequestDTO> para) {
//        UserRequestDTO userRequestDto = para.get("user");
//        if (userRequestDto.getId() == null) {
//            return Result.error("更新的用户id为空值");
//        } else {
//            return Result.ok("修改用户信息成功", userService.saveUser(userRequestDto));
//        }
//    }

    @PostMapping("/user/update")
    public Result<UserDetailVO> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        log.info("修改用户信息请求: userId={}, username={}", userUpdateRequest.getId(), userUpdateRequest.getUsername());
        if (userUpdateRequest.getId() == null) {
            throw new BusinessException("更新用户id不能为空");
        }
        UserDetailVO userDetailVO = userService.updateUser(userUpdateRequest);
        log.info("修改用户信息成功: userId={}, username={}", userUpdateRequest.getId(), userUpdateRequest.getUsername());
        return Result.ok("修改用户信息成功", userDetailVO);
    }

    @DeleteMapping("/user/delete/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        log.info("删除用户请求: userId={}", id);
        userService.deleteUser(id);
        log.info("删除用户成功: userId={}", id);
        return Result.ok("delete user success");
    }


}


