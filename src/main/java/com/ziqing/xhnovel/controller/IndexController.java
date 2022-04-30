package com.ziqing.xhnovel.controller;


import com.alibaba.fastjson.JSON;
import com.ziqing.xhnovel.bean.ImageEntity;
import com.ziqing.xhnovel.exception.XHNException;
import com.ziqing.xhnovel.model.Result;
import com.ziqing.xhnovel.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class IndexController {


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

}
