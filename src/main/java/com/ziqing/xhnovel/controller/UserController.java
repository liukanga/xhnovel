package com.ziqing.xhnovel.controller;

import com.ziqing.xhnovel.bean.ImageEntity;
import com.ziqing.xhnovel.model.*;
import com.ziqing.xhnovel.service.CommentService;
import com.ziqing.xhnovel.service.NovelService;
import com.ziqing.xhnovel.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/loginPage")
    public String login() {
        return "login";
    }

    @GetMapping("/reg")
    public String register() {
        return "reg";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result<User> login(@NonNull @RequestBody User user, HttpServletRequest request) {

        Result<User> result = userService.login(user.getId(), user.getPassword(), user.getStatus());
        if (result.isSuccess()) {
            result.setMessage("登录成功！");
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", result.getData());
        log.info("当前用户id：{}", result.getData().getId());
        return result;
    }


    @PostMapping("/register")
    @ResponseBody
    public Result<User> register(@RequestBody User user, HttpServletRequest request) {

        HttpSession session = request.getSession();
        ImageEntity imageEntity = (ImageEntity) session.getAttribute("imageEntity");

        user.setImageUrl(imageEntity.getUrlPath());
        Result<User> res = userService.register(user);

        if (res.isSuccess()) {
            res.setMessage("注册成功！");
        }
        return res;
    }

    @GetMapping("/remove/{aid}")
    public String removeUser(@PathVariable("aid") Long aid) {
        int i = userService.removeUser(aid);
        if (i == 0) {
            return "error";
        }
        return "redirect:/user/toUserList";
    }

    @GetMapping("/toUserList")
    public String userList(@RequestParam(value = "username", required = false, defaultValue = "") String username,
                           @RequestParam(value = "status", required = false, defaultValue = "0") int status,
                           @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "12") int pageSize,
                           HttpServletRequest request,
                           Model model) {

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        log.info("登录用户为：{}", currentUser.getName());
        log.info("当前用户id：{}", currentUser.getId());

        BasePageParam param = new BasePageParam(pageNo - 1, pageSize, null);
        Paging<User> userPaging = null;
        if (StringUtils.hasText(username)) {
            userPaging = userService.pageQuery(param, username, status);
        } else {
            userPaging = userService.pageQuery(param, null, status);
        }

        List<User> userList = userPaging.getData();

        model.addAttribute("userList", userList);
        model.addAttribute("pageObj", userPaging);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("hNo", currentUser.getStatus());

        return "userList";
    }

    @GetMapping("/toHomePage")
    public String homePage(@RequestParam(value = "uid") Long uid,
                           HttpServletRequest request,
                           Model model) {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        User user = userService.queryUserById(uid);
        List<Comment> commentList = commentService.loadCommentByUserId(uid);

        model.addAttribute("user", user);
        model.addAttribute("novels", user.getNovels());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("hNo", currentUser.getStatus());
        model.addAttribute("commentList", commentList);

        return "homePage";
    }

}
