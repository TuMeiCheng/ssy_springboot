package com.wande.ssy.handler;

import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class RespExceptionHandler {


    //拦截自定义异常统一处理
    @ResponseBody
    @ExceptionHandler(value = RespException.class)
    public RespWrapper handlerAuthorizeException(RespException e) {
        return  RespWrapper.makeResp(e.getErrCode(), e.getMessage(),null);
    }



}
