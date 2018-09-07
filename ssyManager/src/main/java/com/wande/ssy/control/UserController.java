package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.ItemService;
import com.wande.ssy.dubbo.provider.service.OrgService;
import com.wande.ssy.dubbo.provider.service.UserService;
import com.wande.ssy.entity.User;
import com.wande.ssy.utils.PathUtil;
import com.wande.ssy.utils.ResultJson;
import com.wande.ssy.utils.UploadConfig;
import com.ynm3k.mvc.model.RespWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(interfaceClass=UserService.class)
    private UserService userService;

    @Reference(interfaceClass=OrgService.class)
    private OrgService orgService;

    @Reference(interfaceClass=ItemService.class)
    private ItemService itemService;

    @RequestMapping("/addUser")
    public  Object  add(@Valid User user,
                        BindingResult bindingResult
                        , HttpServletRequest request){
        //效验参数
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String attribute = fieldError.getField();
            if (attribute.equals("uin") || attribute.equals("status")) {
                continue;
            }
            log.info("添加User账户，参数错误：{}",bindingResult.getFieldError().getDefaultMessage());
            return new RespWrapper(1002,bindingResult.getFieldError().getDefaultMessage(),null);
        }
        //封装参数
        user.setImg(PathUtil.getHostPathExt(request) + UploadConfig.Default_user_img);
        //调用远程服务保存到数据库
        RespWrapper<Boolean> respWrapper = userService.addUser(user);

       return  ResultJson.ReturnJson(respWrapper);

    }

    /** 更新User用户
     * @param: [user, bindingResult, request]
     * @return: java.lang.Object */
    @RequestMapping("/updateUser")
    public Object updateUser(@Valid User user
                            ,BindingResult bindingResult){
        this.orgService.addOrg(null);
        //效验参数
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String attribute = fieldError.getField();
            if (attribute.equals("password")) {//排除不需要效验的参数
                continue;
            }
            log.info("更新User账户，参数错误：{}",bindingResult.getFieldError().getDefaultMessage());
            return new RespWrapper(1002,bindingResult.getFieldError().getDefaultMessage(),null);
        }
        return this.userService.updateUser(user);
    }


    /*获取用户下拉器材选项
     * @param: []
     * @return: java.lang.Object */
    @RequestMapping("/GetItemBySelect")
    public Object GetItemBySelect(){
        this.itemService.addItem(null);


        return null;


    }


}
