package com.ziqing.xhnovel.model;


import lombok.Data;

@Data
public class Result<T> {

    private boolean success = false;

    private String message;

    private String code;

    private T data;

    public static <T> Result<T> create(){
        return new Result<T>();
    }

    public boolean isSuccess(){
        return success;
    }

}
