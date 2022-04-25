package com.ziqing.xhnovel.model;

import lombok.Data;

@Data
public class BasePageParam {

    private int pagination;

    private int pageSize;

    private Long pid;

    public BasePageParam(int pagination, int pageSize, Long pid){
        this.pagination = pagination;
        this.pageSize = pageSize;
        this.pid = pid;
    }

}
