package com.ziqing.xhnovel.dao;

import com.ziqing.xhnovel.bean.UserNovel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserNovelDao {

    Long insert(UserNovel userNovel);

    int delete(Map<String, Long> paramMap);

    int update(UserNovel userNovel);

    List<UserNovel> queryByUid(Long uid);

    List<UserNovel> query(Map<String, Long> paramMap);
}
