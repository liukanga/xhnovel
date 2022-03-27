package com.ziqing.xhnovel.dao;

import com.ziqing.xhnovel.bean.CommentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {

    Long insertComment(CommentEntity commentEntity);

    List<CommentEntity> loadCommentsByUserId(Long userId);

}
