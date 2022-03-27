package com.ziqing.xhnovel.dao;



import com.ziqing.xhnovel.bean.ChapterEntity;
import com.ziqing.xhnovel.model.BasePageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChapterDao {

    ChapterEntity queryChapterById(@Param("id")Long id);

    int updateChapter(ChapterEntity chapterEntity);

    int queryAllCountByNid(Long nid);

    int addChapter(ChapterEntity chapterEntity);

    List<ChapterEntity> queryAllChapters();

    List<ChapterEntity> pageQuery(BasePageParam param);

    List<ChapterEntity> queryByNid(Long nid);

    List<ChapterEntity> queryByName(String mark);

}
