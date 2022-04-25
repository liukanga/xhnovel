package com.ziqing.xhnovel.dao;


import com.ziqing.xhnovel.bean.NovelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface NovelDao {


    List<NovelEntity> queryAllBooks();

    NovelEntity queryNovelById(@Param("id")Long id);

    List<NovelEntity> queryBookByName(@Param("nName") String nName);

    int addNovel(NovelEntity novelEntity);

    int removeNovel(@Param("id")Long id);

    int updateNovel(NovelEntity novelEntity);

    Integer pageQueryAll(Map<String, Object> paramMap);

    List<NovelEntity> queryNovelByKeyWords(Map<String, Object> paramMap);


}
