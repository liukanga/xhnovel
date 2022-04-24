package com.ziqing.xhnovel.bean;

import lombok.Data;

@Data
public class ImageEntity {

    private String fileName;

    private String urlPath;

    private String localPath;

    public ImageEntity(String fileName, String localPath, String urlPath){
        this.fileName = fileName;
        this.localPath = localPath;
        this.urlPath = urlPath;
    }
}
