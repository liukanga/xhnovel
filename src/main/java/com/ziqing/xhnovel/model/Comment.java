package com.ziqing.xhnovel.model;

import lombok.Data;

@Data
public class Comment extends Base {

    /**
     * 评论者
     */
    private Long commentatorId;
    /**
     * 作者
     */
    private Long userId;
    /**
     * 评论内容
     */
    private String content;

}
