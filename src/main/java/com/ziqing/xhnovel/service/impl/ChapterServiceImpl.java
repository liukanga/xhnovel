package com.ziqing.xhnovel.service.impl;
import com.alibaba.fastjson.JSON;
import com.ziqing.xhnovel.bean.ChapterEntity;
import com.ziqing.xhnovel.dao.ChapterDao;
import com.ziqing.xhnovel.dao.NovelChapterDao;
import com.ziqing.xhnovel.model.BasePageParam;
import com.ziqing.xhnovel.model.Chapter;
import com.ziqing.xhnovel.model.Paging;
import com.ziqing.xhnovel.service.ChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;

    @Autowired
    private NovelChapterDao novelChapterDao;

    @Override
    public Chapter queryChapter(Long id) {
        ChapterEntity chapterEntity = chapterDao.queryChapterById(id);

        return toModel(chapterEntity);
    }

    @Override
    public int updateChapter(Chapter chapter) {
        chapterDao.updateChapter(toDO(chapter));
        return 0;
    }

    @Override
    public int queryAllCountByNid(Long nid) {
        return chapterDao.queryAllCountByNid(nid);
    }

    @Override
    public int insertChapter(Chapter chapter) {
        ChapterEntity chapterEntity = toDO(chapter);
        chapterDao.addChapter(chapterEntity);
        chapter.setId(chapterEntity.getId());

        return 0;
    }

    @Override
    public List<Chapter> queryAllChapters() {

        List<ChapterEntity> chapterEntities = chapterDao.queryAllChapters();
        List<Chapter> chapters = new ArrayList<>();
        if(!CollectionUtils.isEmpty(chapterEntities)){
            for(ChapterEntity chapterEntity : chapterEntities){
                chapters.add(toModel(chapterEntity));
            }
        }

        return chapters;
    }

    @Override
    public Paging<Chapter> pageQuery(BasePageParam param) {
        int totalCount = chapterDao.queryAllCountByNid(param.getPid());
        List<ChapterEntity> chapterEntities = chapterDao.pageQuery(param);
        List<Chapter> chapters = new ArrayList<>();
        for(ChapterEntity chapterEntity : chapterEntities){
            chapters.add(toModel(chapterEntity));
        }
        return new Paging<>(param.getPagination(), param.getPageSize(), totalCount, chapters);
    }

    @Override
    public List<Chapter> queryByNid(Long nid) {
        List<ChapterEntity> chapterEntities = chapterDao.queryByNid(nid);
        List<Chapter> chapters = new ArrayList<>();
        try {
            for(ChapterEntity chapterEntity : chapterEntities){
                chapters.add(toModel(chapterEntity));
            }
        }catch (Exception e){
            log.error("该小说章节为空！{}", nid, e);
        }

        return chapters;
    }

    @Override
    public List<Chapter> queryByCName(String cName) {
        List<ChapterEntity> chapterEntities = chapterDao.queryByName(cName);
        List<Chapter> chapters = new ArrayList<>();
        try {
            for(ChapterEntity chapterEntity : chapterEntities){
                chapters.add(toModel(chapterEntity));
            }
        }catch (Exception e){
            log.error("该小说章节为空！{}", cName, e);
        }

        return chapters;
    }

    @Override
    public int insertNovelChapter(Long chid, Long nid){
        Map<String,Long> params = new HashMap<>();
        params.put("chid", chid);
        params.put("nid", nid);

        novelChapterDao.insert(params);

        return 0;
    }

    @Override
    public int removeChapter(Long id) {
        chapterDao.removeChapter(id);
        return 0;
    }

    private Chapter toModel(ChapterEntity chapterEntity){

        Chapter chapter = new Chapter();
        try {
            if(chapterEntity.getId()!=null){
                chapter.setId(chapterEntity.getId());
            }
            chapter.setNid(chapterEntity.getNid());
            chapter.setCName(chapterEntity.getCName());
            chapter.setContent(chapterEntity.getContent());
            chapter.setWordNum(chapterEntity.getWordNum());
            chapter.setMark(chapterEntity.getMark());
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (!Objects.isNull(chapterEntity.getGmtCreated())){
                    chapter.setGmtCreated(sdf.parse(sdf.format(chapterEntity.getGmtCreated())));
                }
                if (!Objects.isNull(chapter.getGmtModified())){
                    chapter.setGmtModified(sdf.parse(sdf.format(chapterEntity.getGmtModified())));
                }
            }catch (ParseException e){
                log.error("******转换日期失败", e);
            }
        }catch (Exception e){
            log.error("Chapter DO 转为 Model 失败！{}", JSON.toJSON(chapterEntity), e);
        }
        return chapter;
    }

    private ChapterEntity toDO(Chapter chapter){

        ChapterEntity chapterEntity = new ChapterEntity();

        try{
            if(chapter.getId()!=null){
                chapterEntity.setId(chapter.getId());
            }
            chapterEntity.setNid(chapter.getNid());
            chapterEntity.setCName(chapter.getCName());
            chapterEntity.setContent(chapter.getContent());
            chapterEntity.setWordNum(chapter.getWordNum());
            chapterEntity.setMark(chapter.getMark());
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (!Objects.isNull(chapter.getGmtCreated())){
                    chapterEntity.setGmtCreated(sdf.parse(sdf.format(chapter.getGmtCreated())));
                }
                if (!Objects.isNull(chapter.getGmtModified())){
                    chapterEntity.setGmtModified(sdf.parse(sdf.format(chapter.getGmtModified())));
                }
            }catch (ParseException e){
                log.error("******转换日期失败", e);
            }
        }catch (Exception e){
            log.error("Chapter Model 转为 DO 失败！", e);
        }
        return chapterEntity;
    }
}
