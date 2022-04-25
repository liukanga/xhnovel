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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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

    @GetMapping("/addNovel")
    @ResponseBody
    public Map<String, Novel> addNovel(){

        Novel novel = new Novel();

        novel.setNName("武极天下");
        novel.setAid(1314L);
        novel.setDetails("一个梦想进入武府圣地的普通少年，立志追求极致武学。　　然而面对竞争激烈的考核，又有世家子弟的借势压人，小小平凡少年如何立足？　　宗门传承严格保密，核心功法概不外传，在功法传承如此难得天衍大陆，即便进了武府和宗门，想学到顶级武学又谈何容易？　　来自神域的小小魔方，展开一个强者的世界。　　功法要学就学最顶尖。　　生活职业要选就选别人都不会的。　　热血的对决，天才的竞争，三尺枪芒，千里直驱，武道极致，独步天下！");
        List<Chapter> chapters = new ArrayList<>();
        chapters.add(chapterService.queryChapter(1L));
        chapters.add(chapterService.queryChapter(2L));

        novel.setChapters(chapters);

        novelService.addNovel(novel);

        Map<String, Novel> result = new HashMap<>();

        result.put("addResult", novel);

        return result;

    }

    @GetMapping("/remove/{uid}/{nid}/{status}")
    public String removeNovel(@PathVariable("uid")Long uid,
                              @PathVariable("nid")Long nid,
                              @PathVariable("status")int status,
                              Model model,
                              RedirectAttributes ra){

        if(status==1){
            novelService.removeNovel(nid);
            return "redirect:/novel/query";
        }
        Result<User> result = removeNovelFromMyNovels(uid, nid);
        if(!result.isSuccess()){
            return  "error";
        }

        ra.addFlashAttribute(result.getData());
        return "redirect:/novel/toNovelList";
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

    @GetMapping("/toChapterList")
    public String chapterList(@RequestParam(value = "nid", required = false)Long nid,
                              @RequestParam(value = "pageNo", required = false, defaultValue = "1")int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "28")int pageSize,
                              Model model){

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

        return "chapterList";

    }

    @GetMapping("/toNovelList/{aid}")
    public String novelList(@PathVariable("aid") Long aid, Model model){

        User user = userService.queryUserById(aid);
        model.addAttribute("currentUser", user);
        model.addAttribute("hNo", user.getStatus());
        model.addAttribute("novelList", user.getNovels());
        model.addAttribute("page", 1);
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
    public String queryNovelByKeyWords(@RequestParam(value = "keyWords", required = false, defaultValue = "")String keyWords,
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
        if (StringUtils.hasText(keyWords)){
            novelPaging = novelService.queryNovelByKeyWords(param, keyWords);
        }else{
            novelPaging = novelService.queryNovelByKeyWords(param, null);
        }

        List<Novel> novelList = novelPaging.getData();

        model.addAttribute("novelList", novelList);
        model.addAttribute("pageObj",novelPaging);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("hNo", currentUser.getStatus());
        model.addAttribute("title", "小说列表");
        model.addAttribute("page", 2);

        return "novelList";
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


    private Result<User> removeNovelFromMyNovels(Long uid, Long nid){
        Result<User> result = new Result<>();
        User user = userService.queryUserById(uid);
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
