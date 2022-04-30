package com.ziqing.xhnovel.dao;

import com.ziqing.xhnovel.bean.UserComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserCommentDao {

    Long insert(UserComment userComment);

    int delete(Map<String, Long> paramMap);

    int update(UserComment userComment);

    List<UserComment> query(Map<String, Long> paramMap);

}
