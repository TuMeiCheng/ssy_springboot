package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.UserService;
import com.wande.ssy.entity.User;
import com.wande.ssy.utils.ResultJson;
import com.ynm3k.mvc.model.RespWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(interfaceClass=UserService.class)
    private UserService userService;

    @RequestMapping("/AddUser")
    public  Object  add(@Valid User user,BindingResult bindingResult){
        //效验参数
        if (bindingResult.hasErrors()) {
            log.info("添加User账户，参数错误：{}",bindingResult.getFieldError().getDefaultMessage());
            return new RespWrapper(1002,bindingResult.getFieldError().getDefaultMessage(),null);
        }

        RespWrapper<User> respWrapper = userService.addUser(user);

       return  ResultJson.ReturnJson(respWrapper);

    }


}
