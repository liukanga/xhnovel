package com.ziqing.xhnovel.dao;

import com.ziqing.xhnovel.bean.NovelChapter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NovelChapterDao {

    Long insert(Map<String, Long> paramMap);

    int delete(Map<String, Long> paramMap);

    int update(NovelChapter novelChapter);

    List<NovelChapter> query(Map<String, Long> paramMap);

}
