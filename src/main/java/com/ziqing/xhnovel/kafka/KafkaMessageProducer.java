package com.ziqing.xhnovel.kafka;

import com.ziqing.xhnovel.service.impl.NovelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author 刘梓清
 * 
 */
@Component
public class KafkaMessageProducer {


    @Autowired
    private NovelServiceImpl novelService;



}
