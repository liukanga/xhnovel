package com.ziqing.xhnovel.model;


import lombok.Data;

@Data
public class Result<T> {

    private boolean success = false;

    private String message;

    private Integer code;

    private T data;

    public boolean isSuccess(){
        return success;
    }

}
