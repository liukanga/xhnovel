package com.ziqing.xhnovel.bean;

import com.ziqing.xhnovel.model.Base;
import lombok.Data;

@Data
public class ChapterEntity extends Base {

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
