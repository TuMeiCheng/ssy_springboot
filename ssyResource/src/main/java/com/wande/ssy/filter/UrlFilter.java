package com.wande.ssy.filter;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

public class UrlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponseWrapper httpResponse = new HttpServletResponseWrapper((HttpServletResponse) response);
        System.out.println(httpRequest.getRequestURI());
        String path=httpRequest.getRequestURI();
        System.out.println(path);
        if(path.indexOf(".do")>0){//是.do请求
            //把结尾的.do去掉
            System.out.println("拦截到.do请求：>> "+path);
            path = path.replace("/ssyResource/","/");
            path = path.replace(".do","");
            System.out.println("是.do请求，URI修改后： >> "+path);
            //转发请求
            httpRequest.getRequestDispatcher(path).forward(request,response);
        }
        else {
            chain.doFilter(request,response);

        }
        return;
    }

    @Override
    public void destroy() {

    }

}
