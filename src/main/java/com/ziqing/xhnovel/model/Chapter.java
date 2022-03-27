package com.ziqing.xhnovel.model;

import lombok.Data;

@Data
public class Chapter extends Base {

    /**
     * 章节名
     */
    private String cName;
    /**
     * 正文内容
     */
    private String content;
    /**
     * 所属小说编号
     */
    private Long nid;
    /**
     * 字数
     */
    private int wordNum;
    /**
     * 标识
     */
    private String mark;


}
