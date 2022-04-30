package com.ziqing.xhnovel.dao;

import com.ziqing.xhnovel.bean.CommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentDao {

    Long insertComment(CommentEntity commentEntity);

    List<CommentEntity> loadCommentsByUserId(Long userId);

    List<CommentEntity> pageQuery(Map<String, Object> paramMap);

    Integer pageQueryAll(Map<String, Object> paramMap);

    int removeComment(@Param("id")Long id);

}
