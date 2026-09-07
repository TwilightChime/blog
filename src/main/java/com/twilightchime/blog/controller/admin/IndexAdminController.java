package com.twilightchime.blog.controller.admin;

import com.twilightchime.blog.common.Result;
import com.twilightchime.blog.service.BlogService;
import com.twilightchime.blog.service.TagService;
import com.twilightchime.blog.service.TypeService;
import com.twilightchime.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class IndexAdminController {
    private final BlogService blogService;
    private final TagService tagService;
    private final TypeService typeService;
    private final UserService userService;


    @GetMapping("/getBlogCount")
    public Result<Long> getBlogList() {
        return Result.ok("获取博客总数成功", blogService.countBlog());
    }
    //获取总阅读量
    @GetMapping("/getViewCount")
    public Result<Long> getViewCount() {
        return Result.ok("获取阅读总数成功", blogService.countViews());
    }

    //获取总点赞数
    @GetMapping("/getAppreciateCount")
    public Result<Long> getAppreciateCounts() {
        return Result.ok("获取赞赏总数成功", blogService.countAppreciate());
    }

    //获取总评论数
    @GetMapping("/getCommentCount")
    public Result<Long> getCommentCount() {
        return Result.ok("获取评论总数成功", blogService.countComment());
    }

    //根据月份统计阅读量
    @GetMapping("/getViewCountByMonth")
    public Result<List<String>> getBlogViewsByMonth() {
        return Result.ok("获取按月份统计阅读总数成功", blogService.ViewCountByMonth());
    }

    //根据月份统计博客发表数
    @GetMapping("/getBlogCountByMonth")
    public Result<List<String>> getBlogCountByMonth() {
        return Result.ok("获取按月份统计发表总数成功", blogService.BlogCountByMonth());
    }

    //根据月份统计评论数
    @GetMapping("/getCommentCountByMonth")
    public Result<List<String>> getCommentCountByMonth() {
//        return Result.ok("获取按月份统计评论总数成功", commentService.CommentCountByMonth());
        return null;
    }

    //根据月份统计评论数
    @GetMapping("/getAppreciateCountByMonth")
    public Result<List<String>> getAppreciateCountByMonth() {
        return Result.ok("获取按月份统计评论总数成功", blogService.appreciateCountByMonth());
    }
}
