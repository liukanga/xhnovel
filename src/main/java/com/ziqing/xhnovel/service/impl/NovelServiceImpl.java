package com.ziqing.xhnovel.service.impl;


import com.ziqing.xhnovel.bean.NovelEntity;
import com.ziqing.xhnovel.bean.UserNovel;
import com.ziqing.xhnovel.dao.NovelDao;
import com.ziqing.xhnovel.dao.UserNovelDao;
import com.ziqing.xhnovel.model.BasePageParam;
import com.ziqing.xhnovel.model.Novel;
import com.ziqing.xhnovel.model.Paging;
import com.ziqing.xhnovel.service.ChapterService;
import com.ziqing.xhnovel.service.NovelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 刘梓清
 */
@Slf4j
@Service
public class NovelServiceImpl implements NovelService {

    @Autowired
    private NovelDao novelDao;
    @Autowired
    private ChapterService chapterService;

    @Autowired
    private UserNovelDao userNovelDao;

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

        return Objects.isNull(novelEntity) ? null : toModel(novelEntity);
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
    public Paging<Novel> queryNovelByKeyWords(BasePageParam param, String keyWords, String status) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageSize", param.getPageSize());
        paramMap.put("pagination", param.getPagination());
        paramMap.put("keyWords", keyWords);
        paramMap.put("status", status);
        int totalCount = novelDao.pageQueryAll(paramMap);
        List<NovelEntity> novelEntities = novelDao.queryNovelByKeyWords(paramMap);
        List<Novel> novelList = new ArrayList<>();
        for (NovelEntity novelEntity : novelEntities) {
            novelList.add(toModel(novelEntity));
        }
        return new Paging<>(param.getPagination(), param.getPageSize(), totalCount, novelList);
    }

    @Override
    public List<Novel> loadNovelByUserId(Long uid){

        List<UserNovel> relationList = userNovelDao.queryByUid(uid);
        List<Novel> novelList = new ArrayList<>();
        for (UserNovel userNovel : relationList){
            novelList.add(toModel(novelDao.queryNovelById(userNovel.getNid())));
        }
        return novelList;
    }

    @Override
    public int insertUserNovel(Long nid, Long uid){
        UserNovel userNovel = new UserNovel(uid, nid);

        Map<String, Long> params = new HashMap<>();
        params.put("uid", uid);
        params.put("nid", nid);

        List<UserNovel> query = userNovelDao.query(params);

        if (CollectionUtils.isEmpty(query)){
            userNovelDao.insert(userNovel);
        }
        return 0;
    }

    @Override
    public boolean isCollect(Long nid, Long uid) {
        Map<String, Long> params = new HashMap<>();
        params.put("uid", uid);
        params.put("nid", nid);
        List<UserNovel> query = userNovelDao.query(params);
        return !CollectionUtils.isEmpty(query);
    }

    @Override
    public int deleteUserNovel(Long nid, Long uid){
        Map<String, Long> params = new HashMap<>();
        params.put("uid", uid);
        params.put("nid", nid);

        return userNovelDao.delete(params);
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

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (!Objects.isNull(novel.getGmtCreated())){
                novelEntity.setGmtCreated(sdf.parse(sdf.format(novel.getGmtCreated())));
            }
            if (!Objects.isNull(novel.getGmtModified())){
                novelEntity.setGmtModified(sdf.parse(sdf.format(novel.getGmtModified())));
            }
        }catch (ParseException e){
            log.error("******转换日期失败", e);
        }
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
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (!Objects.isNull(novelEntity.getGmtCreated())){
                novel.setGmtCreated(sdf.parse(sdf.format(novelEntity.getGmtCreated())));
            }
            if (!Objects.isNull(novelEntity.getGmtModified())){
                novel.setGmtModified(sdf.parse(sdf.format(novelEntity.getGmtModified())));
            }
        }catch (ParseException e){
            log.error("******转换日期失败", e);
        }
        return novel;

    }




}
