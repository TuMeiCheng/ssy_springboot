package com.wande.ssy.control;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.IUserBussness;

@RestController
@RequestMapping("/test")
public class TestControl {

    @Reference
    private IUserBussness userBussness;

    @RequestMapping("/getInfo")
    public Object getInfo(Integer userId){


        return userBussness.getInfo(userId);
    }
	
}
