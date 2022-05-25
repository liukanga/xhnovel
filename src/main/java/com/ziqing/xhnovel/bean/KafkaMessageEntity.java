package com.ziqing.xhnovel.bean;

import com.ziqing.xhnovel.model.Novel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class KafkaMessageEntity {

    private String message;

    private List<Novel> data;

    private LocalDateTime dateTime;

}
