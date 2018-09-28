package com.wande.ssy.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {


    //定义切入点logAuthorize
    @Pointcut("execution(public * com.wande.ssy.control.*.*(..))")
    public void logAuthorize() {}

    //声明环绕通知
    @Around("logAuthorize()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        long begin = System.nanoTime();
        //开始执行方法
        Object o = pjp.proceed();
        long end = System.nanoTime();
        //调用耗时
        long url_time = (end-begin)/1000000;
        if ( url_time > 5000){ //接口调用时长超过5秒
            log.info("【效率过低】 >> URL: {},  >> 调用时间：{}", request.getRequestURL().toString(),url_time);
        }else {
            log.info("URL: {},  >> 调用时间：{}", request.getRequestURL().toString(),url_time);
        }
        return o;
    }
}
