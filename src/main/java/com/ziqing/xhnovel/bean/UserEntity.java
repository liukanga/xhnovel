package com.ziqing.xhnovel.bean;

import com.ziqing.xhnovel.model.Base;
import lombok.Data;

@Data
public class UserEntity extends Base {

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
     * 小说集合、我的作品、我的书架  JSON数据格式
     */
    private String novelIds;
    /**
     * 写作时长
     */
    private int duration;
    /**
     * 详细信息
     */
    private String details;


}
