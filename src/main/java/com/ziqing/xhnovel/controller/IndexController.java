package com.ziqing.xhnovel.controller;


import com.alibaba.fastjson.JSON;
import com.ziqing.xhnovel.bean.ImageEntity;
import com.ziqing.xhnovel.bean.KafkaMessageEntity;
import com.ziqing.xhnovel.model.Chapter;
import com.ziqing.xhnovel.model.Novel;
import com.ziqing.xhnovel.model.Result;
import com.ziqing.xhnovel.model.User;
import com.ziqing.xhnovel.service.ChapterService;
import com.ziqing.xhnovel.service.NovelService;
import com.ziqing.xhnovel.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private NovelService novelService;

    @GetMapping("/")
    public String index(){

        return "redirect:/novel/query/cid/27566/false";
    }


    @PostMapping("/image")
    @ResponseBody
    public Result<ImageEntity> uploadImage(@RequestPart(value = "file", required = false)MultipartFile file, HttpServletRequest request){

        Result<ImageEntity> result = new Result<>();
        try {
            ImageEntity imageEntity = FileUtils.upload(file);
            log.info("图片文件json：" + JSON.toJSONString(imageEntity));

            result.setData(imageEntity);
            result.setSuccess(true);
            result.setCode(200);
            result.setMessage("上传图片成功");

            HttpSession session = request.getSession();
            session.setAttribute("imageUrl", imageEntity.getUrlPath());

        }catch (Exception e){
            log.error("********* 上传文件失败", e);
            result.setSuccess(false);
            result.setCode(400);
            result.setMessage("上传文件失败");
        }


        return result;
    }


    @GetMapping("/loginPage")
    public String login() {
        return "login";
    }

    @GetMapping("/exit")
    public String signOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        User currentUser = (User)session.getAttribute("user");
        if (!Objects.isNull(currentUser)){
            session.removeAttribute(currentUser.getId() + "isFirst");
            session.removeAttribute("user");
        }
        return "login";
    }

    @GetMapping("/reg")
    public String register(@RequestParam(value = "page", required = false, defaultValue = "1")int page, Model model) {

        model.addAttribute("page", page);
        return "reg";
    }

    @Autowired
    private ChapterService chapterService;


    @GetMapping("/uiuitest")
    @ResponseBody
    public List<Long> addUserNovelTable(){

        List<Novel> novelList = novelService.queryAllBooks();

        for (Novel novel : novelList){
            Long nid = novel.getId();
            List<Chapter> chapters = chapterService.queryByNid(nid);
            Collections.sort(chapters, new Comparator<Chapter>() {
                @Override
                public int compare(Chapter o1, Chapter o2) {
                    Long id1 = o1.getId();
                    Long id2 = o1.getId();
                    if (id1.equals(id2)) {
                        return 0;
                    } else {
                        return id1 > id2 ? 1 : -1;
                    }
                }
            });
            int index = 1;
            for (Chapter chapter1 : chapters){
                chapter1.setMark(nid + "-" + index);
                chapterService.updateChapter(chapter1);
                index++;
            }

        }

         return new ArrayList<>();

    }


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("sendMessages")
    public void sendMessage(){
        KafkaMessageEntity kafkaMessageEntity = new KafkaMessageEntity();
        List<Novel> novels = novelService.queryAllBooks();
        kafkaMessageEntity.setMessage("今日推送");
        kafkaMessageEntity.setData(novels);
        kafkaMessageEntity.setDateTime(LocalDateTime.now());
        kafkaTemplate.send("xh-novel-push", "", JSON.toJSONString(kafkaMessageEntity));

    }

}
