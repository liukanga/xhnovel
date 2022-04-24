package com.ziqing.xhnovel.model;

import lombok.Data;

import java.util.Date;

@Data
public class Base {
    /**
     * 编号
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;

}
