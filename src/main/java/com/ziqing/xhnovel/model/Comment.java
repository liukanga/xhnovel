package com.ziqing.xhnovel.model;

import lombok.Data;

@Data
public class Comment extends Base {

    /**
     * 评论者
     */
    private User commentator;
    /**
     * 作者
     */
    private User user;
    /**
     * 评论内容
     */
    private String content;

}
