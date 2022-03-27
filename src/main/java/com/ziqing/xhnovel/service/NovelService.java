package com.ziqing.xhnovel.service;


import com.ziqing.xhnovel.model.Novel;

import java.util.List;

public interface NovelService {

    List<Novel> queryAllBooks();

    Novel queryNovelById(Long nid);

    List<Novel> queryBookByName(String name);

    int addNovel(Novel novel);

    int removeNovel(Long id);

    int updateNovel(Novel novel);

}
