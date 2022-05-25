package com.ziqing.xhnovel.service.impl;

import com.ziqing.xhnovel.bean.CommentEntity;
import com.ziqing.xhnovel.bean.NovelEntity;
import com.ziqing.xhnovel.bean.UserComment;
import com.ziqing.xhnovel.dao.CommentDao;
import com.ziqing.xhnovel.dao.UserCommentDao;
import com.ziqing.xhnovel.exception.XHNDBException;
import com.ziqing.xhnovel.model.*;
import com.ziqing.xhnovel.service.CommentService;
import com.ziqing.xhnovel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCommentDao userCommentDao;

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

    @Override
    public int removeComment(Long id) {
        return commentDao.removeComment(id);
    }

    private Comment toModel(CommentEntity commentEntity){
        Comment comment = new Comment();
        if (commentEntity.getId()!=null){
            comment.setId(commentEntity.getId());
        }
        User user = userService.queryUserById(commentEntity.getUserId());
        User commentator = userService.queryUserById(commentEntity.getCommentatorId());

        if (user!=null){
            comment.setUser(user);
        }else {
            commentDao.removeComment(comment.getId());
        }

        if (commentator!=null){
            comment.setCommentator(commentator);
        }else {
            commentDao.removeComment(comment.getId());
        }

        comment.setContent(commentEntity.getContent());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (!Objects.isNull(commentEntity.getGmtCreated())){
                comment.setGmtCreated(sdf.parse(sdf.format(commentEntity.getGmtCreated())));
            }
            if (!Objects.isNull(commentEntity.getGmtModified())){
                comment.setGmtModified(sdf.parse(sdf.format(commentEntity.getGmtModified())));
            }
        }catch (ParseException e){
            log.error("******转换日期失败", e);
        }

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
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (!Objects.isNull(comment.getGmtCreated())){
                commentEntity.setGmtCreated(sdf.parse(sdf.format(comment.getGmtCreated())));
            }
            if (!Objects.isNull(comment.getGmtModified())){
                commentEntity.setGmtModified(sdf.parse(sdf.format(comment.getGmtModified())));
            }
        }catch (ParseException e){
            log.error("******转换日期失败", e);
        }

        return commentEntity;
    }
}
