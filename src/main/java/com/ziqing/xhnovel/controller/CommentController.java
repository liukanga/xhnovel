package com.ziqing.xhnovel.controller;

import com.ziqing.xhnovel.exception.XHNException;
import com.ziqing.xhnovel.model.Comment;
import com.ziqing.xhnovel.model.Result;
import com.ziqing.xhnovel.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
            Long cid = commentService.insertComment(comment);
            comment.setId(cid);
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

}
