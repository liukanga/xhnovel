package com.ziqing.xhnovel.service.impl;


import com.ziqing.xhnovel.bean.NovelEntity;
import com.ziqing.xhnovel.dao.NovelDao;
import com.ziqing.xhnovel.model.Novel;
import com.ziqing.xhnovel.service.ChapterService;
import com.ziqing.xhnovel.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
        
        for(NovelEntity novelEntity : novelEntities){
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
        for(NovelEntity novelEntity : novelEntities){
            novels.add(toModel(novelEntity));
        }

        return novels;
    }

    @Override
    public int addNovel(Novel novel) {
        NovelEntity novelEntity = toDO(novel);
        int i = novelDao.addNovel(novelEntity);
        if(i>0){
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

    public NovelEntity toDO(Novel novel){

        NovelEntity novelEntity = new NovelEntity();
        if(novel.getId()!=null){
            novelEntity.setId(novel.getId());
        }

        novelEntity.setNName(novel.getNName());
        novelEntity.setAid(novel.getAid());

        /*List<Chapter> chapters = novel.getChapters();
        List<Long> chapterIds = new ArrayList<>();
        if(chapters!=null&&!CollectionUtils.isEmpty(chapters)){
            for(Chapter chapter : chapters){
                chapterIds.add(chapter.getId());
            }
        }
        String cIds = JSON.toJSONString(chapterIds);
        novelDO.setChapterIds(cIds);*/

        if(!StringUtils.isEmpty(novel.getDetails())){
            novelEntity.setDetails(novel.getDetails());
        }
        if(!StringUtils.isEmpty(novel.getImgUrl())){
            novelEntity.setImgUrl(novel.getImgUrl());
        }
        if(!StringUtils.isEmpty(novel.getStatus())){
            novelEntity.setStatus(novel.getStatus());
        }
        if(!StringUtils.isEmpty(novel.getKeyWords())){
            novelEntity.setKeyWords(novel.getKeyWords());
        }

        novelEntity.setScore(novel.getScore());
        novelEntity.setGmtCreated(novel.getGmtCreated());
        novelEntity.setGmtModified(novel.getGmtModified());
        return novelEntity;
    }
    
    
    public Novel toModel(NovelEntity novelEntity){


        Novel novel = new Novel();
        
        if(novelEntity.getId()!=null){
            novel.setId(novelEntity.getId());
        }
        novel.setAid(novelEntity.getAid());
        novel.setNName(novelEntity.getNName());
        if(!StringUtils.isEmpty(novelEntity.getDetails())){
            novel.setDetails(novelEntity.getDetails());
        }

        /*String chapterIds = novelDO.getChapterIds();
        List<Long> cIds = JSON.parseObject(chapterIds, Constants.TRE_Long);
        List<Chapter> chapters = new ArrayList<>();
        if(cIds!=null&&!CollectionUtils.isEmpty(cIds)){
            for(Long id : cIds){
                Chapter chapter = chapterService.queryChapter(id);
                chapters.add(chapter);
            }
        }
        novel.setChapters(chapters);*/

        if(!StringUtils.isEmpty(novelEntity.getStatus())){
            novel.setStatus(novelEntity.getStatus());
        }
        if(!StringUtils.isEmpty(novelEntity.getImgUrl())){
            novel.setImgUrl(novelEntity.getImgUrl());
        }
        if(!StringUtils.isEmpty(novelEntity.getKeyWords())){
            novel.setKeyWords(novelEntity.getKeyWords());
        }

        novel.setScore(novelEntity.getScore());
        novel.setGmtCreated(novelEntity.getGmtCreated());
        novel.setGmtModified(novelEntity.getGmtModified());
        return novel;

    }
}
