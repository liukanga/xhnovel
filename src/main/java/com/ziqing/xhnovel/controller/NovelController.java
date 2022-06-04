package com.ziqing.xhnovel.controller;

import com.ziqing.xhnovel.model.*;
import com.ziqing.xhnovel.service.ChapterService;
import com.ziqing.xhnovel.service.NovelService;
import com.ziqing.xhnovel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping("/novel")
@Controller
public class NovelController {

    @Autowired
    private NovelService novelService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChapterService chapterService;

    @GetMapping("/toAddNovel")
    public String toAddNovel(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("hNo", currentUser.getStatus());
        return "/addNovel";
    }

    @GetMapping("/query/cid/{cid}/{next}")
    public String readById(@PathVariable("cid")Long cid,
                           @PathVariable("next")boolean next,
                           HttpServletRequest request,
                           Model model){
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        Chapter chapter = chapterService.queryChapter(cid);
        Long nid = chapter.getNid();
        Novel novel = novelService.queryNovelById(nid);
        User author = userService.queryUserById(novel.getAid());
        Chapter nextChapter = chapterService.queryChapter(cid);
        if (!chapter.getNid().equals(nextChapter.getNid())){
            return "redirect: /novel/toChapterList?nid="+nextChapter.getNid();
        }
        if(next){
            model.addAttribute("chapter", nextChapter);
        }else{
            model.addAttribute("chapter", chapter);
        }
        model.addAttribute("novel", novel);
        model.addAttribute("author", author);
        boolean isLogin = true;
        if (Objects.isNull(currentUser)){
            User defaultUser = new User();
            defaultUser.setName("未登录！");
            isLogin = false;
            model.addAttribute("currentUser", defaultUser);
        }else{
            model.addAttribute("currentUser", currentUser);
        }
        model.addAttribute("isLogin", isLogin);
        return "readePage";
    }


    private String convertStringToHtml(String text){

        String[] texts = text.split("  ");
        StringBuilder sb = new StringBuilder();
        for (String s : texts){


        }

        return null;
    }

    @GetMapping("/toChapterList")
    public String chapterList(@RequestParam(value = "nid")Long nid,
                              @RequestParam(value = "pageNo", required = false, defaultValue = "1")int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "28")int pageSize,
                              Model model, HttpServletRequest request){

        Novel novel = novelService.queryNovelById(nid);
        BasePageParam param = new BasePageParam(pageNo-1, pageSize, novel.getId());
        Paging<Chapter> chapterPaging = chapterService.pageQuery(param);
        List<Chapter> totalList = chapterPaging.getData();

        List<Chapter> list1 = new ArrayList<>();
        List<Chapter> list2 = new ArrayList<>();
        for(int i=0;i<totalList.size();i++){
            if(i%2==0){
                list1.add(totalList.get(i));
            }else{
                list2.add(totalList.get(i));
            }
        }
        Long aid = novel.getAid();
        User user = userService.queryUserById(aid);
        model.addAttribute("novel",novel);
        model.addAttribute("user",user);
        model.addAttribute("chapters1",list1);
        model.addAttribute("chapters2",list2);
        model.addAttribute("pageObj",chapterPaging);
        model.addAttribute("pageNo",pageNo);
        HttpSession session = request.getSession();
        User currentUser = (User)session.getAttribute("user");
        model.addAttribute("isCollect", novelService.isCollect(nid, currentUser.getId()) ? 1 : 0);
        if (pageSize != 28){
            model.addAttribute("chapters",totalList);
            return "modifyNovel";
        }
        model.addAttribute("currentUser",currentUser);
        return "chapterList";
    }

    @GetMapping("/toNovelList/{aid}")
    public String novelList(@PathVariable("aid") Long aid, Model model){
        User user = userService.queryUserById(aid);
        model.addAttribute("currentUser", user);
        model.addAttribute("hNo", 1);
        model.addAttribute("novelList", user.getNovels());
        model.addAttribute("page1", 1);
        model.addAttribute("page2", user.getStatus());
        model.addAttribute("myPage", 1);
        int status = user.getStatus();
        if(status==1){
            model.addAttribute("title", "小说列表");
        }else if(status==2){
            model.addAttribute("title", "我的作品");
        }else{
            model.addAttribute("title", "我的书架");
        }
        return "novelList";
    }


    @GetMapping("/query")
    public String queryNovelByKeyWords(@RequestParam(value = "keyWords", required = false)String keyWords,
                                       @RequestParam(value = "status", required = false)String status,
                                       @RequestParam(value = "pageNo", required = false, defaultValue = "1")int pageNo,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "8")int pageSize,
                                       HttpServletRequest request,
                                       Model model){
        HttpSession session = request.getSession();
        User currentUser = (User)session.getAttribute("user");
        log.info("登录用户为：{}", currentUser.getName());
        log.info("当前用户id：{}", currentUser.getId());
        BasePageParam param = new BasePageParam(pageNo-1, pageSize, null);
        Paging<Novel> novelPaging = null;
        novelPaging = novelService.queryNovelByKeyWords(param, keyWords, status);
        List<Novel> novelList = novelPaging.getData();
        List<Novel> novels = new ArrayList<>();
        model.addAttribute("novelList", novelList);
        model.addAttribute("pageObj",novelPaging);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("hNo", currentUser.getStatus());
        model.addAttribute("title", "小说列表");
        model.addAttribute("page1", currentUser.getStatus());
        model.addAttribute("page2", currentUser.getStatus());
        model.addAttribute("novls", novels);
        model.addAttribute("status", 0);
        if (currentUser.getStatus()!=1){
            model.addAttribute("page2", 4);
        }
        if (currentUser.getStatus()==3){
            String isFirst = (String) session.getAttribute(currentUser.getId()+"isFirst");
            if (!StringUtils.hasText(isFirst)){
                session.setAttribute(currentUser.getId()+"isFirst", "Yes");
                List<Novel> novels1 = novelService.queryAllBooks();
                novels.add(novels1.get(0));
                novels.add(novels1.get(3));
                novels.add(novels1.get(9));
                novels.add(novels1.get(15));
                novels.add(novels1.get(20));
                model.addAttribute("novls", novels);
                model.addAttribute("status", 1);
            }
        }


        return "novelList";
    }


    @GetMapping("/addNovel/bookShelf")
    public String addNovelToBookShelf(@RequestParam("nid")Long nid, HttpServletRequest request){
        HttpSession session = request.getSession();
        User currentUser = (User)session.getAttribute("user");
        List<Novel> novels = currentUser.getNovels();
        Novel novel = novelService.queryNovelById(nid);
        novels.add(novel);
        currentUser.setNovels(novels);
        userService.updateUser(currentUser);
        return "redirect:/novel/toChapterList?nid"+nid;
    }

    @GetMapping("/addNovel")
    public String insertNovel(@RequestParam("name")String name,
                                     @RequestParam("details")String details,
                                     @RequestParam("status")String status,
                                     @RequestParam("duration")int duration,
                                     HttpServletRequest request){
        HttpSession session = request.getSession();
        String imageUrl = (String) session.getAttribute("imageUrl");
        User user = (User) session.getAttribute("user");
        Novel novel = new Novel();
        novel.setNName(name);
        novel.setStatus(status);
        novel.setDetails(details);
        novel.setImgUrl(imageUrl);
        novel.setKeyWords(novel.getNName()+user.getName());
        novel.setAid(user.getId());
        novelService.addNovel(novel);
        List<Novel> novels = user.getNovels();
        novels.add(novel);
        user.setNovels(novels);
        user.setDuration(user.getDuration()+duration);
        userService.updateUser(user);
        return "redirect:/novel/toModifyNovel?nid="+novel.getId();
    }


    @GetMapping("/remove")
    public String removeNovel(@RequestParam("nid")Long nid,
                              @RequestParam("uid")Long uid, HttpServletRequest request){
        User currentUser = userService.queryUserById(uid);
        Novel novel = novelService.queryNovelById(nid);
        currentUser.getNovels().remove(novel);
        userService.updateUser(currentUser);
        if (currentUser.getStatus()==1){
            novelService.removeNovel(nid);
            return "redirect:/novel/query";
        }
        removeNovelFromMyNovels(currentUser, nid);
        return "redirect:/novel/toNovelList/"+uid;
    }

    @GetMapping("/toModifyNovel")
    public String toModifyNovel(@RequestParam("nid")Long nid, Model model, HttpServletRequest request){

        Novel novel = novelService.queryNovelById(nid);
        model.addAttribute("novel", novel);
        HttpSession session = request.getSession();
        session.setAttribute("novelId", novel.getId());
        User currentUser = (User) session.getAttribute("user");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("hNo", currentUser.getStatus());
        model.addAttribute("chapters", chapterService.queryByNid(nid));
        return "modifyNovel";
    }


    @PostMapping("/modifyNovel")
    @ResponseBody
    public Result<Novel> modifyNovel(@RequestBody Novel novel,
                              HttpServletRequest request, Model model){
        Result<Novel> result = new Result<>();
        Long nid = novel.getId();
        Novel mNovel = novelService.queryNovelById(nid);
        if (StringUtils.hasText(novel.getNName())){
            mNovel.setNName(novel.getNName());
        }
        if (StringUtils.hasText(novel.getStatus())){
            mNovel.setStatus(novel.getStatus());
        }
        mNovel.setGmtModified(new Date());
        novelService.updateNovel(novel);
        result.setSuccess(true);
        result.setData(novel);
        result.setMessage("更新小说成功");
        result.setCode(200);
        return result;
    }

    @GetMapping("/testDemo")
    @ResponseBody
    public List<Novel> testDemo(Model model){
        List<Novel> novels = new ArrayList<>();
        for(long a=66L;a<=86L;a++){
            Novel novel = novelService.queryNovelById(a);
            novels.add(novel);
            List<Chapter> chapters = chapterService.queryByNid(a);
            novel.setChapters(chapters);
            novelService.updateNovel(novel);
        }
        return novels;
    }

    private Result<User> removeNovelFromMyNovels(User user, Long nid){
        Result<User> result = new Result<>();
        Novel novel = novelService.queryNovelById(nid);
        List<Novel> novels = user.getNovels();
        novels.remove(novel);
        user.setNovels(novels);
        int i = userService.updateUser(user);
        result.setData(user);
        result.setSuccess(true);
        if(i==0){
            result.setCode(605);
            result.setMessage("更新用户信息失败");
            result.setSuccess(false);
        }
        return result;
    }
}
