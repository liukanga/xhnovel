package com.ziqing.xhnovel.controller;

import com.ziqing.xhnovel.exception.XHNException;
import com.ziqing.xhnovel.model.*;
import com.ziqing.xhnovel.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/addComment")
    @ResponseBody
    public Result<Comment> addComment(@RequestBody Comment comment){

        Result<Comment> result = new Result<>();

        try {
            Long id = commentService.insertComment(comment);
            comment.setId(id);
            result.setMessage("添加评论成功");
            result.setSuccess(true);
            result.setCode(200);
            result.setData(comment);
        }catch (XHNException e){
            log.error("********* 添加评论失败", e);
            result.setMessage("添加评论失败");
            result.setSuccess(false);
            result.setCode(400);
        }

        return result;

    }

    @GetMapping("/toCommentList")
    public String userList(@RequestParam(value = "userId", required = false) Long userId,
                           @RequestParam(value = "commentatorId", required = false) Long commentatorId,
                           @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "8") int pageSize,
                           HttpServletRequest request,
                           Model model) {

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        log.info("登录用户为：{}", currentUser.getName());
        log.info("当前用户id：{}", currentUser.getId());

        BasePageParam param = new BasePageParam(pageNo - 1, pageSize, null);
        Paging<Comment> commentPaging = commentService.pageQuery(param, userId, commentatorId);

        List<Comment> commentList = commentPaging.getData();

        model.addAttribute("userList", commentList);
        model.addAttribute("pageObj", commentPaging);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("hNo", currentUser.getStatus());

        return "commentList";
    }

}
