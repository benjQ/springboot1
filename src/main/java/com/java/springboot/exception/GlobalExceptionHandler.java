package com.java.springboot.exception;


import com.java.springboot.entity.ResultMsg;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {Exception.class})
    public Object defaultErrorHandler(Exception e) throws Exception {
        ResultMsg resultMsg=new ResultMsg();
        resultMsg.setMsg(e.getMessage());
        resultMsg.setStatusCode(400);

        return resultMsg;
    }

}

