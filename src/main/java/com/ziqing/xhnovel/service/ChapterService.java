package com.ziqing.xhnovel.service;



import com.ziqing.xhnovel.model.BasePageParam;
import com.ziqing.xhnovel.model.Chapter;
import com.ziqing.xhnovel.model.Paging;

import java.util.List;

public interface ChapterService {

    Chapter queryChapter(Long id);

    int updateChapter(Chapter chapter);

    int queryAllCountByNid(Long nid);

    int insertChapter(Chapter chapter);

    List<Chapter> queryAllChapters();

    Paging<Chapter> pageQuery(BasePageParam param);

    List<Chapter> queryByNid(Long nid);

    List<Chapter> queryByCName(String cName);

    int insertNovelChapter(Long chid, Long nid);

    int removeChapter(Long id);

}
