package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.EqpService;
import com.wande.ssy.entity.Eqp;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/eqp")
public class EqpController {

    @Reference(interfaceClass=EqpService.class)
    private EqpService eqpService;

    @RequestMapping("/addEqp")
    public String AddEqp(@Valid Eqp eqp){
        System.out.println(eqp.toJson()+"controller.....");
        RespWrapper respWrapper = eqpService.addEqp(eqp);
        return respWrapper.toString();


    }
}
