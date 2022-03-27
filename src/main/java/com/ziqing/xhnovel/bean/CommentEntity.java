package com.ziqing.xhnovel.bean;

import com.ziqing.xhnovel.model.Base;
import lombok.Data;


@Data
public class CommentEntity extends Base {

    /**
     * 评论id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 评论者id
     */
    private Long commentatorId;
    /**
     * 评论内容
     */
    private String content;

}
