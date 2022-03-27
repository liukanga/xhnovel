package com.ziqing.xhnovel.util;

import com.ziqing.xhnovel.model.BasePageParam;
import com.ziqing.xhnovel.model.Paging;

import java.util.List;

public class PagingUtil{

    public Paging<?> toPaging(BasePageParam param, List<?> data){

        return new Paging<>(param.getPagination(), param.getPageSize(), data.size(), data);

    }

}
