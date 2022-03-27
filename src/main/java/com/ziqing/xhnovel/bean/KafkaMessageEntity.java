package com.ziqing.xhnovel.bean;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class KafkaMessageEntity {

    private String message;

    private List<NovelEntity> data;

    private LocalDateTime dateTime;

}
