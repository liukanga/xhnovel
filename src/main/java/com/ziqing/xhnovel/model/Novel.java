package com.ziqing.xhnovel.model;

import lombok.Data;

import java.util.List;

@Data
public class Novel extends Base {

    /**
     * 小说名
     */
    private String nName;
    /**
     * 小说封面
     */
    private String imgUrl;
    /**
     * 小说章节
     */
    private List<Chapter> chapters;
    /**
     * 小说正文内容
     */
    private String details;
    /**
     * 关键词
     */
    private String keyWords;
    /**
     * 小说状态
     */
    private String status;
    /**
     * 作者编号
     */
    private Long aid;
    /**
     * 评分
     */
    private double score;


}
