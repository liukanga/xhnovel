package com.ziqing.xhnovel.service.impl;


import com.ziqing.xhnovel.bean.NovelEntity;
import com.ziqing.xhnovel.dao.NovelDao;
import com.ziqing.xhnovel.model.BasePageParam;
import com.ziqing.xhnovel.model.Novel;
import com.ziqing.xhnovel.model.Paging;
import com.ziqing.xhnovel.service.ChapterService;
import com.ziqing.xhnovel.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘梓清
 */
@Service
public class NovelServiceImpl implements NovelService {

    @Autowired
    private NovelDao novelDao;
    @Autowired
    private ChapterService chapterService;

    @Override
    public List<Novel> queryAllBooks() {

        List<NovelEntity> novelEntities = novelDao.queryAllBooks();
        List<Novel> novels = new ArrayList<>();

        for (NovelEntity novelEntity : novelEntities) {
            novels.add(toModel(novelEntity));
        }

        return novels;
    }

    @Override
    public Novel queryNovelById(Long nid) {

        NovelEntity novelEntity = novelDao.queryNovelById(nid);

        return toModel(novelEntity);
    }

    @Override
    public List<Novel> queryBookByName(String name) {

        List<NovelEntity> novelEntities = novelDao.queryBookByName(name);

        List<Novel> novels = new ArrayList<>();
        for (NovelEntity novelEntity : novelEntities) {
            novels.add(toModel(novelEntity));
        }

        return novels;
    }

    @Override
    public int addNovel(Novel novel) {
        NovelEntity novelEntity = toDO(novel);
        int i = novelDao.addNovel(novelEntity);
        if (i > 0) {
            novel.setId(novelEntity.getId());
            return 1;
        }
        return -1;
    }

    @Override
    public int removeNovel(Long id) {
        return novelDao.removeNovel(id);
    }

    @Override
    public int updateNovel(Novel novel) {
        return novelDao.updateNovel(toDO(novel));
    }

    @Override
    public Paging<Novel> queryNovelByKeyWords(BasePageParam param, String keyWords) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageSize", param.getPageSize());
        paramMap.put("pagination", param.getPagination());
        if (StringUtils.hasText(keyWords)) {
            keyWords = "%" + keyWords + "%";
        }
        paramMap.put("keyWords", keyWords);
        int totalCount = novelDao.pageQueryAll(paramMap);
        List<NovelEntity> novelEntities = novelDao.queryNovelByKeyWords(paramMap);
        List<Novel> novelList = new ArrayList<>();
        for (NovelEntity novelEntity : novelEntities) {
            novelList.add(toModel(novelEntity));
        }
        return new Paging<>(param.getPagination(), param.getPageSize(), totalCount, novelList);
    }

    public NovelEntity toDO(Novel novel) {

        NovelEntity novelEntity = new NovelEntity();
        if (novel.getId() != null) {
            novelEntity.setId(novel.getId());
        }

        novelEntity.setNName(novel.getNName());
        novelEntity.setAid(novel.getAid());

        if (StringUtils.hasText(novel.getDetails())) {
            novelEntity.setDetails(novel.getDetails());
        }
        if (StringUtils.hasText(novel.getImgUrl())) {
            novelEntity.setImgUrl(novel.getImgUrl());
        }
        if (StringUtils.hasText(novel.getStatus())) {
            novelEntity.setStatus(novel.getStatus());
        }
        if (StringUtils.hasText(novel.getKeyWords())) {
            novelEntity.setKeyWords(novel.getKeyWords());
        }

        novelEntity.setScore(novel.getScore());
        novelEntity.setGmtCreated(novel.getGmtCreated());
        novelEntity.setGmtModified(novel.getGmtModified());
        return novelEntity;
    }


    public Novel toModel(NovelEntity novelEntity) {


        Novel novel = new Novel();

        if (novelEntity.getId() != null) {
            novel.setId(novelEntity.getId());
        }
        novel.setAid(novelEntity.getAid());
        novel.setNName(novelEntity.getNName());
        if (StringUtils.hasText(novelEntity.getDetails())) {
            novel.setDetails(novelEntity.getDetails());
        }

        if (StringUtils.hasText(novelEntity.getStatus())) {
            novel.setStatus(novelEntity.getStatus());
        }
        if (StringUtils.hasText(novelEntity.getImgUrl())) {
            novel.setImgUrl(novelEntity.getImgUrl());
        }
        if (StringUtils.hasText(novelEntity.getKeyWords())) {
            novel.setKeyWords(novelEntity.getKeyWords());
        }

        novel.setScore(novelEntity.getScore());
        novel.setGmtCreated(novelEntity.getGmtCreated());
        novel.setGmtModified(novelEntity.getGmtModified());
        return novel;

    }
}
