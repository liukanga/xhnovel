package com.ziqing.xhnovel.model;

import lombok.Data;

import java.util.List;

@Data
public class User extends Base {

    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 身份
     */
    private int status;
    /**
     * 小说集合、我的作品、我的书架
     */
    private List<Novel> novels;
    /**
     * 写作时长
     */
    private int duration;
    /**
     * 详细信息
     */
    private String details;

}
