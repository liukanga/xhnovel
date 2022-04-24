package com.ziqing.xhnovel.dao;


import com.ziqing.xhnovel.bean.NovelEntity;
import com.ziqing.xhnovel.model.BasePageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NovelDao {


    List<NovelEntity> queryAllBooks();

    NovelEntity queryNovelById(@Param("id")Long id);

    List<NovelEntity> queryBookByName(@Param("nName") String nName);

    int addNovel(NovelEntity novelEntity);

    int removeNovel(@Param("id")Long id);

    int updateNovel(NovelEntity novelEntity);

    List<NovelEntity> pageQueryAll(BasePageParam param);


}
