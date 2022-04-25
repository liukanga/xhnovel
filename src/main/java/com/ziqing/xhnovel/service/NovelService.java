package com.ziqing.xhnovel.service;


import com.ziqing.xhnovel.model.BasePageParam;
import com.ziqing.xhnovel.model.Novel;
import com.ziqing.xhnovel.model.Paging;

import java.util.List;

public interface NovelService {

    List<Novel> queryAllBooks();

    Novel queryNovelById(Long nid);

    List<Novel> queryBookByName(String name);

    int addNovel(Novel novel);

    int removeNovel(Long id);

    int updateNovel(Novel novel);

    Paging<Novel> queryNovelByKeyWords(BasePageParam param, String keyWords);

}
