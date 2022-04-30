package com.ziqing.xhnovel.service;

import com.ziqing.xhnovel.exception.XHNDBException;
import com.ziqing.xhnovel.model.BasePageParam;
import com.ziqing.xhnovel.model.Comment;
import com.ziqing.xhnovel.model.Paging;

import java.util.List;

public interface CommentService {

    Long insertComment(Comment comment) throws XHNDBException;

    List<Comment> loadCommentByUserId(Long userId);

    Paging<Comment> pageQuery(BasePageParam param, Long userId, Long commentatorId);

    int removeComment(Long id);

}
