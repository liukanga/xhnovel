package com.ziqing.xhnovel.bean;

import com.ziqing.xhnovel.model.Base;
import lombok.Data;

@Data
public class NovelEntity extends Base {

    /**
     * 小说名
     */
    private String nName;
    /**
     * 小说封面
     */
    private String imgUrl;
    /**
     * 小说章节  JSON数据格式
     */
    private String chapterIds;
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
