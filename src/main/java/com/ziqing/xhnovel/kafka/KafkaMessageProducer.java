package com.ziqing.xhnovel.kafka;

import com.alibaba.fastjson.JSON;
import com.ziqing.xhnovel.bean.KafkaMessageEntity;
import com.ziqing.xhnovel.bean.NovelEntity;
import com.ziqing.xhnovel.model.Novel;
import com.ziqing.xhnovel.service.impl.NovelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 刘梓清
 * 
 */
@Component
public class KafkaMessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private NovelServiceImpl novelService;

    public void sendMessage(){
        KafkaMessageEntity kafkaMessageEntity = new KafkaMessageEntity();
        List<Novel> novels = novelService.queryAllBooks();
        List<NovelEntity> novelEntityList = new ArrayList<>();
        for (Novel novel : novels){
            novelEntityList.add(novelService.toDO(novel));
        }
        kafkaMessageEntity.setMessage("今日推送");
        kafkaMessageEntity.setData(novelEntityList);
        kafkaMessageEntity.setDateTime(LocalDateTime.now());
        kafkaTemplate.send("xh-novel-push", "", JSON.toJSONString(kafkaMessageEntity));

    }

}
