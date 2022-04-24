package com.ziqing.xhnovel.service.impl;

import com.ziqing.xhnovel.bean.CommentEntity;
import com.ziqing.xhnovel.dao.CommentDao;
import com.ziqing.xhnovel.exception.XHNDBException;
import com.ziqing.xhnovel.model.Comment;
import com.ziqing.xhnovel.model.User;
import com.ziqing.xhnovel.service.CommentService;
import com.ziqing.xhnovel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = {XHNDBException.class})
    public Long insertComment(Comment comment) throws XHNDBException{
        try {
            return commentDao.insertComment(toDO(comment));
        }catch (Exception e){
            log.error("******** 插入评论到数据库中失败.",e);
            throw new XHNDBException("插入评论异常");
        }
    }

    @Override
    public List<Comment> loadCommentByUserId(Long userId) {
        List<Comment> commentList = new ArrayList<>();
        List<CommentEntity> commentEntityList = commentDao.loadCommentsByUserId(userId);
        commentEntityList.stream().forEach(commentEntity -> commentList.add(toModel(commentEntity)));
        return commentList;
    }

    private Comment toModel(CommentEntity commentEntity){
        Comment comment = new Comment();
        if (commentEntity.getId()!=null){
            comment.setId(commentEntity.getId());
        }
        comment.setUserId(commentEntity.getUserId());
        comment.setCommentatorId(commentEntity.getCommentatorId());
        comment.setContent(commentEntity.getContent());
        comment.setGmtCreated(commentEntity.getGmtCreated());
        comment.setGmtModified(commentEntity.getGmtModified());

        return comment;
    }

    private CommentEntity toDO(Comment comment){
        CommentEntity commentEntity = new CommentEntity();
        if (comment.getId()!=null){
            commentEntity.setId(commentEntity.getId());
        }
        commentEntity.setUserId(comment.getUserId());
        commentEntity.setCommentatorId(comment.getCommentatorId());
        commentEntity.setContent(comment.getContent());
        commentEntity.setGmtCreated(comment.getGmtCreated());
        commentEntity.setGmtModified(comment.getGmtModified());

        return commentEntity;
    }
}
