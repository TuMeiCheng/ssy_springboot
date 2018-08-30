package com.wande.ssy.filter;

import sun.applet.Main;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

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
//            path = path.substring(0,path.length()-3);
            path = path.replace("/ssyResource/","/");
            path = path.replace(".do","");
            System.out.println("是.do请求，URI修改后： >> "+path);
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

    public static void main(String [] args){
        System.out.println("hhh");
        String path = "/dign/sweo/og/sldg.do";
        String  path2 = path.substring(0,path.length()-3);
        System.out.println(path2);

    }
}
