package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.AdminService;
import com.wande.ssy.dubbo.provider.service.ItemService;
import com.wande.ssy.dubbo.provider.service.OrgService;
import com.wande.ssy.dubbo.provider.service.UserService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Item;
import com.wande.ssy.entity.User;
import com.wande.ssy.utils.PathUtil;
import com.wande.ssy.utils.ResultJson;
import com.wande.ssy.utils.StringUtil;
import com.wande.ssy.utils.UploadConfig;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.model.SuperBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(interfaceClass=UserService.class)
    private UserService userService;

    @Reference(interfaceClass=OrgService.class)
    private OrgService orgService;

    @Reference(interfaceClass=AdminService.class)
    private AdminService adminService;



    @Reference(interfaceClass=ItemService.class)
    private ItemService itemService;

    @RequestMapping("/addUser")
    public  Object  add(@Valid User user,
                        BindingResult bindingResult
                        , HttpServletRequest request){
        //效验参数
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String attribute = fieldError.getField();
            if (attribute.equals("uin") || attribute.equals("status")) {//跳过不需要验证的参数
                continue;
            }
            log.info("添加User账户，参数错误：{}",fieldError.getDefaultMessage());
            return new RespWrapper(1001,fieldError.getDefaultMessage(),null);
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
            log.info("更新User账户，参数错误：{}",fieldError.getDefaultMessage());
            return new RespWrapper(1002,fieldError.getDefaultMessage(),null);
        }
        return this.userService.updateUser(user);
    }


    /*获取用户下拉器材选项
     * @param: []
     * @return: java.lang.Object */
    @RequestMapping("/GetItemBySelect")
    public Object GetItemBySelect(){
        //TODO
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        RespWrapper<List<Item>> resp =this.userService.getItemBySelect(admin);
        if (resp.getErrCode() != 0 && resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        List<String> createList = new ArrayList<String>();
        List<String> flowList = new ArrayList<String>();
        List<String> repairList = new ArrayList<String>();
        for (Item i : resp.getObj()) {
            if (!createList.contains(i.getCreateBy()+"")) {
                createList.add(i.getCreateBy() + "");
            }
            if (!flowList.contains(i.getFlowBy()+"")) {
                flowList.add(i.getFlowBy() + "");
            }
            if (!repairList.contains(i.getRepairBy()+"")) {
                repairList.add(i.getRepairBy() + "");
            }
        }
        String createIds = StringUtil.join(createList, ",");
        String flowIds = StringUtil.join(flowList, ",");
        String repairIds = StringUtil.join(repairList, ",");
        Map ret = new SuperBean();
        ret.put("createBySelect", this.userService.getUserListInIds(createIds).getObj());
        ret.put("flowBySelect",this.userService.getUserListInIds(flowIds).getObj());
        ret.put("repairBySelect", this.userService.getUserListInIds(repairIds).getObj());

        //返回结果
        return new RespWrapper<>(0,"获取下来选成功",ret);
    }


    @RequestMapping("/getUserInfo")
    public Object getUserInfo(@RequestParam(value = "name",defaultValue = "") String  name ){
        //TODO
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        //参数校验
        if (StringUtil.isEmpty(name) ) {
            throw new RespException(1001, "******");
        }
        return null;
    }

    /**
     * APP用户分页列表
     * @author vwFisher(422578659@qq.com)
     * 2017年1月12日上午11:04:30
     */
    @RequestMapping("/GetUserPage")
    public  Object GetUserPage(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                               @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                               @RequestParam(value = "keyword",defaultValue = "") String keyword){
        //TODO
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);

        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("agencyId", admin.getUin());
        if (StringUtil.isNotEmpty(keyword)) {
            params.put("keyword",keyword);
        }

        RespWrapper<DataPage<User>> resp = this.userService.getUserByPage(params, pageNo, pageSize);
        if (resp.getErrCode() != 0) {
            throw new RespException(1001, resp.getErrMsg());
        }

        List<User> u_list = resp.getObj().getRecord();
        for (User o : u_list) {
            Admin agency = adminService.getAdminByUin(o.getAgencyId()).getObj();
            o.set("agencyName",agency.getName());
        }
        //返回结果
        return  resp;
    }


}
