package com.ziqing.xhnovel.controller;

import com.ziqing.xhnovel.model.Chapter;
import com.ziqing.xhnovel.model.Novel;
import com.ziqing.xhnovel.model.Result;
import com.ziqing.xhnovel.model.User;
import com.ziqing.xhnovel.service.ChapterService;
import com.ziqing.xhnovel.service.NovelService;
import com.ziqing.xhnovel.service.UserService;
import com.ziqing.xhnovel.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RequestMapping("/chapter")
@Controller
public class ChapterController {

    @Autowired
    private ChapterService chapterService;
    @Autowired
    private NovelService novelService;
    @Autowired
    private UserService userService;

    @GetMapping("/testDemo")
    public String fill(){
        try {
            Document novDoc = Jsoup.connect("https://www.bqg999.cc/book/1009873373/")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36")
                    .get();
            Element listmain = novDoc.getElementsByClass("listmain").get(0);

            Novel novel = novelService.queryNovelById(3L);

            List<Chapter> chapters = new ArrayList<>();
            Elements cTags = listmain.getElementsByTag("dd");
            int index = 1;

            for(int i=0;i<cTags.size();i++){

                Element ele = cTags.get(i);

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
                    int len = text.length()-1;
                    String subStr = text.substring(0, len - 48);
                    String mark = novel.getId()+""+index;
                    String ctime = textinfo.getElementsByTag("span").get(3).text().substring(7);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date gmt = sdf.parse(ctime);
                    String num = textinfo.getElementsByTag("span").get(2).text().substring(3);
                    int wordNum = Integer.parseInt(num);
                    List<Chapter> list = chapterService.queryByCName(cName);
                    if(!CollectionUtils.isEmpty(list)&&!Objects.isNull(list.get(0))){
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
                    }catch (Exception sqlException){
                        log.error("此章节内容为乱码{}", cName, sqlException);
                        chapter.setContent("");
                        chapterService.insertChapter(chapter);
                    }
                    chapters.add(chapter);
                    index++;
                }catch (IOException chpException){
                    log.error("连接章节链接失败！",chpException);
                    novel.setChapters(chapters);
                    novelService.updateNovel(novel);
                }
            }
            novel.setChapters(chapters);
            novelService.updateNovel(novel);
        }catch (Exception e){
            log.error("小说连接断开！",e);
        }
        log.info("填充完毕");
        return "redirect:/novel/query";
    }

    @GetMapping("/modify")
    public String modify(@RequestParam("cid")Long cid, HttpServletRequest request){

        Chapter chapter = chapterService.queryChapter(cid);

        HttpSession session = request.getSession();
        session.setAttribute("chapter", chapter);

        return "redirect:/chapter/toModifyChapter?page=1";

    }

    @GetMapping("/toModifyChapter")
    public String toModifyChapter(@RequestParam("page")int page, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        model.addAttribute("page", page);
        model.addAttribute("chapter", new Chapter());
        model.addAttribute("hNo", currentUser.getStatus());
        model.addAttribute("currentUser", currentUser);

        if (page == 1){
            Chapter chapter = (Chapter) session.getAttribute("chapter");
            model.addAttribute("chapter", chapter);
        }

        return "modifyChapter";
    }

    @PostMapping("/modifyChapter")
    public String modifyChapter(@RequestParam(name = "cName")String name,
                                @RequestParam(name = "content")String content, HttpServletRequest request){


        HttpSession session = request.getSession();
        Chapter chapter = (Chapter) session.getAttribute("chapter");

        chapter.setCName(name);
        chapter.setCName(content);
        chapter.setGmtModified(new Date());

        int i = chapterService.updateChapter(chapter);

        if (i == 0){
            return "redirect:/novel/toModifyNovel?nid="+chapter.getNid();
        }

        return "error";
    }

    @PostMapping("/addChapter")
    public String addChapter(@RequestParam(name = "cName")String name,
                             @RequestParam(name = "content")String content, HttpServletRequest request){

        HttpSession session = request.getSession();
        Long nid = (Long) session.getAttribute("novelId");
        Novel novel = novelService.queryNovelById(nid);
        List<Chapter> chapters = novel.getChapters();
        if(CollectionUtils.isEmpty(chapters)){
            chapters = new ArrayList<>();
        }
        Chapter chapter = new Chapter();
        chapter.setCName(name);
        chapter.setContent(content);
        chapter.setNid(novel.getId());
        StringBuilder sb = new StringBuilder();
        sb.append(novel.getId());
        sb.append("-");
        sb.append(chapters.size()+1);
        chapter.setMark(sb.toString());
        chapter.setWordNum(content.getBytes(StandardCharsets.UTF_8).length);
        chapter.setGmtModified(new Date());
        chapter.setGmtCreated(new Date());

        int i = chapterService.insertChapter(chapter);

        chapters.add(chapter);
        novel.setChapters(chapters);
        novelService.updateNovel(novel);

        if (i == 0){
            return "redirect:/novel/toModifyNovel?nid="+novel.getId();
        }

        return "modifyChapter";
    }

    @GetMapping("/removeChapter")
    public String removeChapter(@RequestParam("cid") Long cid,
                                @RequestParam("nid") Long nid){

        chapterService.removeChapter(cid);

        return "redirect:/novel/toModifyNovel?nid="+nid;

    }

}
