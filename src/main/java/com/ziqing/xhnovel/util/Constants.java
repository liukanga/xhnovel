package com.ziqing.xhnovel.util;



import com.alibaba.fastjson.TypeReference;
import com.ziqing.xhnovel.model.Chapter;

import java.util.Arrays;
import java.util.List;

public class Constants {


    public static final TypeReference<List<Long>> TRE_Long = new TypeReference<>() {};

    public static final TypeReference<Chapter> TRE_Chapter = new TypeReference<>() {};

    public static final List<Integer> STATUS_LIST = Arrays.asList(1,2,3);

    public static final String Bq_URL = "https://www.bqg999.cc";


}
