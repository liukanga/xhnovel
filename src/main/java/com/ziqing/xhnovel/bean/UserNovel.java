package com.ziqing.xhnovel.bean;

import lombok.Data;

@Data
public class UserNovel {

    private Long id;

    private Long uid;

    private Long nid;

    public UserNovel(Long uid, Long nid) {
        this.uid = uid;
        this.nid = nid;
    }

    public UserNovel(){}
}
