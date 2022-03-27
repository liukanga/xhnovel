package com.ziqing.xhnovel.service;

import com.ziqing.xhnovel.exception.XHNDBException;
import com.ziqing.xhnovel.model.Comment;

import java.util.List;

public interface CommentService {

    Long insertComment(Comment comment) throws XHNDBException;

    List<Comment> loadCommentByUserId(Long userId);

}
