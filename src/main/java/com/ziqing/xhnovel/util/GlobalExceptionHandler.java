package com.ziqing.xhnovel.util;

import com.ziqing.xhnovel.exception.XHNException;
import com.ziqing.xhnovel.exception.XHNDBException;
import com.ziqing.xhnovel.exception.XHNValidateException;
import com.ziqing.xhnovel.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({XHNException.class})
    public Result<String> xhnExceptionHandler(Exception e){
        Result<String> result = new Result<>();
        result.setCode(400);
        result.setMessage(e.getMessage());
        result.setSuccess(false);
        return result;
    }

    @ExceptionHandler({XHNDBException.class})
    public Result<String> xhnDBExceptionHandler(XHNDBException e){
        Result<String> result = new Result<>();
        result.setCode(400);
        result.setMessage(e.getMessage());
        result.setSuccess(false);
        return result;
    }

    @ExceptionHandler({XHNValidateException.class})
    public Result<String> xhnValidateException(XHNValidateException e){
        Result<String> result = new Result<>();
        result.setCode(400);
        result.setMessage(e.getMessage());
        result.setSuccess(false);
        return result;
    }

}
