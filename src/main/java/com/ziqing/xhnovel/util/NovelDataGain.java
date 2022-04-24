package com.ziqing.xhnovel.util;

import com.ziqing.xhnovel.model.Chapter;
import com.ziqing.xhnovel.model.Novel;
import com.ziqing.xhnovel.model.User;
import com.ziqing.xhnovel.service.ChapterService;
import com.ziqing.xhnovel.service.NovelService;
import com.ziqing.xhnovel.service.UserService;
import com.ziqing.xhnovel.service.impl.ChapterServiceImpl;
import com.ziqing.xhnovel.service.impl.NovelServiceImpl;
import com.ziqing.xhnovel.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author 刘梓清
 */
@Slf4j
@Component
public class NovelDataGain {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private NovelServiceImpl novelService;
    @Autowired
    private ChapterServiceImpl chapterService;

    //    @Scheduled(cron = "0 0/20 * * * ? ")
    public void saveNovelData() {

        Random random = new Random();
        try {
            Document bqDoc = Jsoup.connect("https://www.bqg999.cc/xuanhuan/")
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "zh-CN,zh;q=0.9,be;q=0.8,ko;q=0.7,eo;q=0.6")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36")
                    .get();

            Element divElt = bqDoc.getElementsByClass("l bd").get(0);
            Elements s2s = divElt.getElementsByClass("s2");
            for (Element e : s2s) {
                String novUrl = e.getElementsByTag("a").attr("href");
                Document novDoc = Jsoup.connect(Constants.Bq_URL + novUrl)
                        .header("Accept", "*/*")
                        .header("Accept-Encoding", "gzip, deflate, br")
                        .header("Accept-Language", "zh-CN,zh;q=0.9,be;q=0.8,ko;q=0.7,eo;q=0.6")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36")
                        .get();

                Element cover = novDoc.getElementsByClass("cover").get(0);
                Element small = novDoc.getElementsByClass("small").get(0);
                Element intro = novDoc.getElementsByClass("intro").get(0);
                Element listmain = novDoc.getElementsByClass("listmain").get(0);

                String imgUrl = cover.getElementsByTag("img").attr("src");
                String name = cover.getElementsByTag("img").attr("alt");
                String author = small.getElementsByTag("span").get(0).text().substring(3);
                String status = small.getElementsByTag("span").get(2).text().substring(3);
                String details = intro.text().substring(3);
                Novel novel;
                List<Novel> novelList1 = novelService.queryBookByName(name);
                if (novelList1 == null || CollectionUtils.isEmpty(novelList1)) {
                    novel = new Novel();
                    novel.setImgUrl(imgUrl);
                    novel.setNName(name);
                    novel.setStatus(status);
                    novel.setDetails(details);
                    novel.setKeyWords(author + name);
                    novel.setScore(0);
                    novelService.addNovel(novel);
                    System.out.println(name);
                } else {
                    novel = novelList1.get(0);
                }

                User resUser = userService.queryByName(author);
                List<Novel> novelList;
                if (Objects.isNull(resUser)) {
                    resUser = new User();
                    resUser.setName(author);
                    resUser.setStatus(2);
                    resUser.setDetails("代表作《" + name + "》");
                    resUser.setDuration(random.nextInt(12) + 1);
                    resUser.setPassword("" + System.currentTimeMillis());
                    userService.register(resUser);
                    novelList = new ArrayList<>();
                } else {
                    List<Novel> novels = resUser.getNovels();
                    if (novels.contains(novel)) {
                        continue;
                    }
                    resUser.setDetails("、《" + name + "》");
                    novelList = resUser.getNovels();
                }

                List<Chapter> chapters = new ArrayList<>();
                Elements cTags = listmain.getElementsByTag("dd");
                int index = 1;
                for (Element ele : cTags) {

                    String cptUrl = ele.getElementsByTag("a").attr("href");
                    try {
                        Document docCt = Jsoup.connect(Constants.Bq_URL + cptUrl)
                                .header("Accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
                                .header("Accept-Encoding", "gzip, deflate, br")
                                .header("Accept-Language", "zh-CN,zh;q=0.9,be;q=0.8,ko;q=0.7,eo;q=0.6")
                                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36")
                                .get();

                        Element cont1 = docCt.getElementsByClass("content").get(0);
                        Element cont2 = docCt.getElementById("content");
                        Element textinfo = docCt.getElementsByClass("textinfo").get(0);
                        String cName = cont1.getElementsByTag("h1").text();
                        String text = cont2.text();
                        int len = text.length() - 1;
                        String subStr = text.substring(0, len - 48);
                        String mark = novel.getId() + "" + index;
                        String ctime = textinfo.getElementsByTag("span").get(3).text().substring(7);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date gmt = sdf.parse(ctime);
                        String num = textinfo.getElementsByTag("span").get(2).text().substring(3);
                        int wordNum = Integer.parseInt(num);

                        List<Chapter> list = chapterService.queryByCName(cName);
                        if (!CollectionUtils.isEmpty(list) && !Objects.isNull(list.get(0))) {
                            index++;
                            continue;
                        }
                        Chapter chapter = new Chapter();
                        chapter.setCName(cName);
                        chapter.setContent(subStr);
                        chapter.setNid(novel.getId());
                        chapter.setMark(mark);
                        chapter.setGmtCreated(gmt);
                        chapter.setWordNum(wordNum);
                        try {
                            chapterService.insertChapter(chapter);
                        } catch (Exception sqlException) {
                            log.error("此章节内容为乱码{}", cName, sqlException);
                            chapter.setContent("");
                            chapterService.insertChapter(chapter);
                        }
                        chapters.add(chapter);
                        index++;
                    } catch (IOException chpException) {
                        log.error("连接章节链接失败！", chpException);
                        novel.setChapters(chapters);
                        novel.setAid(resUser.getId());
                        novelService.updateNovel(novel);

                        novelList.add(novel);
                        resUser.setNovels(novelList);
                        userService.updateUser(resUser);
                    }
                }
                novel.setChapters(chapters);
                novel.setAid(resUser.getId());
                novelService.updateNovel(novel);
                novelList.add(novel);
                resUser.setNovels(novelList);
                userService.updateUser(resUser);
            }
        } catch (Exception e) {
            log.error("连接网站失败！", e);
        }

    }

}
