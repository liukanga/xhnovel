package com.ziqing.xhnovel.service.impl;

import com.ziqing.xhnovel.bean.CommentEntity;
import com.ziqing.xhnovel.bean.NovelEntity;
import com.ziqing.xhnovel.dao.CommentDao;
import com.ziqing.xhnovel.exception.XHNDBException;
import com.ziqing.xhnovel.model.*;
import com.ziqing.xhnovel.service.CommentService;
import com.ziqing.xhnovel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Paging<Comment> pageQuery(BasePageParam param, Long userId, Long commentatorId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageSize", param.getPageSize());
        paramMap.put("pagination", param.getPagination());
        paramMap.put("userId", userId);
        paramMap.put("commentatorId", commentatorId);
        int totalCount = commentDao.pageQueryAll(paramMap);
        List<CommentEntity> novelEntities = commentDao.pageQuery(paramMap);
        List<Comment> commentList = new ArrayList<>();
        for (CommentEntity commentEntity : novelEntities) {
            commentList.add(toModel(commentEntity));
        }
        return new Paging<>(param.getPagination(), param.getPageSize(), totalCount, commentList);
    }

    private Comment toModel(CommentEntity commentEntity){
        Comment comment = new Comment();
        if (commentEntity.getId()!=null){
            comment.setId(commentEntity.getId());
        }
        comment.setUser(userService.queryUserById(commentEntity.getUserId()));
        comment.setCommentator(userService.queryUserById(commentEntity.getCommentatorId()));
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
        commentEntity.setUserId(comment.getUser().getId());
        commentEntity.setCommentatorId(comment.getCommentator().getId());
        commentEntity.setContent(comment.getContent());
        commentEntity.setGmtCreated(comment.getGmtCreated());
        commentEntity.setGmtModified(comment.getGmtModified());

        return commentEntity;
    }
}
