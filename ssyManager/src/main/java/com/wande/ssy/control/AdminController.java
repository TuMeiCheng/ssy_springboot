package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.AdminService;
import com.wande.ssy.dubbo.provider.service.LogService;
import com.wande.ssy.dubbo.provider.service.RegionagencyService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.RegionAgency;
import com.wande.ssy.enums.AdminRole;
import com.wande.ssy.enums.AdminStatus;
import com.wande.ssy.utils.LogUtil;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.utils.string.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Reference(interfaceClass=AdminService.class)
    private AdminService adminService;

    @Reference(interfaceClass=LogService.class)
    private LogService logService;

    @Reference(interfaceClass=RegionagencyService.class)
    private RegionagencyService regionagencyService;



    /**
     * 添加系统用户
     * @param obj  // 登录账号 // 密码// 姓名// 邮箱// 电话
     * @param regionIds 管理公司的 管辖区域(格式1,2,3)
     */
     @RequestMapping("/addAgency")
        public Object AddAgency(@Valid Admin obj,
                                BindingResult bindingResult,
                                @RequestParam("regionIds") String regionIds,
                                HttpServletRequest request){
         //效验参数
         for (FieldError fieldError : bindingResult.getFieldErrors()) {
             String attribute = fieldError.getField();
             if (attribute.equals("orgId") || attribute.equals("status") || attribute.equals("uin")) {
                 //跳过不需要验证的参数
                 continue;
             }
             log.info("添加Admin系统账户，参数错误：{}",fieldError.getDefaultMessage());
             return new RespWrapper(1001,fieldError.getDefaultMessage(),null);
         }

         //TODO 当前登录用户
         //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
         Admin admin = new Admin();
         admin.setRoleId(1);
         admin.setUin(8L);
         admin.setAccount("admin");
         //封装数据
         obj.setCreateBy(admin.getUin());	            // 创建人
         obj.setRoleId(AdminRole.AGENCY.getValue());    // 系统用户角色
         //调用远程服务保存

        RespWrapper resp = this.adminService.addAdmin(obj,obj.getPassword(),regionIds);
         if (resp.getErrCode() == 0){
             //添加操作日志
             logService.addLog(LogUtil.getLog(admin, request, "添加管理公司账号:" + obj.getAccount() + "(" + resp.getObj() + ")"));
         }
         return  resp;
        }

    /**
     * 添加系统用户(体育局)
     * @author vwFisher(422578659@qq.com)
     */
        @RequestMapping("addOrg")
        public Object addOrg(@Valid Admin obj,
                             BindingResult bindingResult,
                             HttpServletRequest request){
            //效验参数
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String attribute = fieldError.getField();
                if (attribute.equals("status") || attribute.equals("uin")) {//跳过不需要验证的参数
                    continue;
                }
                log.info("添加Admin体育局账户，参数错误：{}",fieldError.getDefaultMessage());
                return new RespWrapper(1001,fieldError.getDefaultMessage(),null);
            }

            //TODO 当前登录用户
            //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
            Admin admin = new Admin();
            admin.setRoleId(1);
            admin.setUin(8L);
            admin.setAccount("admin");
            //封装数据
            obj.setCreateBy(admin.getUin());	            // 创建人
            obj.setRoleId(AdminRole.ORG.getValue());		// 体育局 角色
            RespWrapper<Long> resp = this.adminService.addAdmin(obj,obj.getPassword(),"");
            if (resp.getErrCode() == 0){
                RespWrapper rrr = logService.addLog(LogUtil.getLog(admin, request, "添加体育局账号:" + obj.getAccount() + "(" + resp.getObj() + ")"));
            }
            return resp;
        }


        /* 修改密码
         * @param: [oldPwd, newPwd1, newPwd2]
         * @return: java.lang.Object */
        @RequestMapping("/changePwd")
       public Object ChangePwd(@RequestParam("oldPwd") String oldPwd,      //旧密码
                               @RequestParam("newPwd1") String newPwd1     //新密码
                               ,@RequestParam("newPwd2") String newPwd2){  //新密码（第二次）

            //校验参数
            if (StringUtil.isEmpty(oldPwd)) {
                throw new RespException(1001, "旧密码不能为空!");
            }
            if (StringUtil.isEmpty(newPwd1) || StringUtil.isEmpty(newPwd2)) {
                throw new RespException(1001, "新密码不能为空!");
            }
            if (!newPwd1.equals(newPwd2)) {
                throw new RespException(1001, "两次密码输入不一致!");
            }
            //TODO 当前登录用户
            //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
            Admin admin = new Admin();
            admin.setRoleId(1);
            admin.setUin(8L);
            admin.setAccount("admin");
            //调用远程服务
            RespWrapper resp = this.adminService.changePwd(admin.getAccount(),oldPwd,newPwd1);
            return resp;
        }

        /* 获取admin状态下拉选项
         * @param: []
         * @return: java.lang.Object */
        @RequestMapping("/getAdminStatusSelect")
        public Object getAdminStatusSelect(){
           return  RespWrapper.makeResp(0, "", AdminStatus.getSelect());
        }

        /* 获取系统管理员账户分页列表
         * @param: [pageNo, pageSize, status, keyword]
         * @return: java.lang.Object */
        @RequestMapping("/getAgencyPage")
    public  Object getAgencyPage(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                 @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                                 @RequestParam(value = "status",defaultValue ="-1") Integer status,   // 状态 0 正常 10 禁用
                                 @RequestParam(value = "keyword",defaultValue ="") String keyword){  // 关键字,账号或名称
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("roleId",AdminRole.AGENCY.getValue());	//管理公司角色
            if (status != -1) {
                params.put("status",status);
            }
            if (StringUtil.isNotEmpty(keyword)) {
                params.put("keyword",keyword);
            }
            //调用远程服务查询
            RespWrapper<DataPage<Admin>> resp = adminService.getAdminByPage(params,pageNo,pageSize);
            if (resp.getErrCode() != 0 || resp.getObj() == null) {
                throw new RespException(resp.getErrCode(), resp.getErrMsg());
            }

            List<Admin> ret = resp.getObj().getRecord();
            for (Admin o : ret) {
                o.set("roleName", AdminRole.getName(o.getRoleId()));
                if (o.getRoleId() == AdminRole.AGENCY.getValue()) {
                    RegionAgency ca = this.regionagencyService.getOneRegionagencyByAgencyId(o.getUin()).getObj();
                    o.set("regionIds", ca == null ? "" : ca.getRegionIds());
                }
            }

            return resp;
        }

    /* 获取体育局账户分页列表
     * @param: [pageNo, pageSize, status, keyword]
     * @return: java.lang.Object */
    @RequestMapping("/getOrgPage")
    public  Object getOrgPage(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                 @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                                 @RequestParam(value = "status",defaultValue ="-1") Integer status,   // 状态 0 正常 10 禁用
                                 @RequestParam(value = "keyword",defaultValue ="") String keyword){  // 关键字,账号或名称
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("roleId",AdminRole.ORG.getValue());	//体育局角色
        if (status != -1) {
            params.put("status",status);
        }
        if (StringUtil.isNotEmpty(keyword)) {
            params.put("keyword",keyword);
        }
        //调用远程服务查询
        RespWrapper<DataPage<Admin>> resp = adminService.getAdminByPage(params,pageNo,pageSize);
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }

        List<Admin> ret = resp.getObj().getRecord();
        for (Admin o : ret) {
            o.set("roleName", AdminRole.getName(o.getRoleId()));
            if (o.getRoleId() == AdminRole.AGENCY.getValue()) {
                RegionAgency ca = this.regionagencyService.getOneRegionagencyByAgencyId(o.getUin()).getObj();
                o.set("regionIds", ca == null ? "" : ca.getRegionIds());
            }
        }
        return resp;
    }


    /* 修改系统管理员
     * @param: [pageNo, pageSize, status, keyword]
     * @return: java.lang.Object */
    @RequestMapping("/updateAgency")
    public  Object updateAgency(@Valid Admin obj ,
                                BindingResult bindingResult,
                               @RequestParam("regionIds") String  regionIds,
                                HttpServletRequest request){
        //效验参数
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String attribute = fieldError.getField();
            if (attribute.equals("password") || attribute.equals("orgId") || attribute.equals("account")) {
                //跳过不需要验证的参数
                continue;
            }
                log.info("【修改admin系统账户】，参数错误：{}",fieldError.getDefaultMessage());
                return new RespWrapper(1001,fieldError.getDefaultMessage(),null);


        }
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");

        //封装参数
        obj.setModifyBy(admin.getUin());		// 修改人

        //调用远程服务
        RespWrapper resp =  this.adminService.updateAdmin(obj,regionIds);
        if (resp.getErrCode() == 0){
            RespWrapper rrr = logService.addLog(LogUtil.getLog(admin, request, "更新管理公司账号:" + obj.getAccount() + "(" + obj.getUin() + ")"));
        }
        return resp;

    }
    /* 修改体育局账户
     * @param: [pageNo, pageSize, status, keyword]
     * @return: java.lang.Object */
    @RequestMapping("/updateOrg")
    public  Object updateOrg(@Valid Admin obj ,
                             BindingResult bindingResult,
                             HttpServletRequest request){
        //效验参数
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String attribute = fieldError.getField();
            if (attribute.equals("password") || attribute.equals("orgId") || attribute.equals("account")) {
                //跳过不需要验证的参数
                continue;
            }
            log.info("【修改admin系统账户】，参数错误：{}",fieldError.getDefaultMessage());
            return new RespWrapper(1001,fieldError.getDefaultMessage(),null);
        }
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");
        //封装参数
        obj.setModifyBy(admin.getUin());		// 修改人
        RespWrapper resp =  this.adminService.updateAdmin(obj,"");
        if (resp.getErrCode() == 0){
            logService.addLog(LogUtil.getLog(admin, request, "更新体育局系统账号:" + obj.getAccount() + "(" + obj.getUin() + ")"));
        }
        return resp;
    }

    /* 修改admin账户
     * @param: [pageNo, pageSize, status, keyword]
     * @return: java.lang.Object */
    @RequestMapping("/updateSelf")
    public  Object updateSelf(@Valid Admin obj ,
                             BindingResult bindingResult,
                             HttpServletRequest request){
        //效验参数
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String attribute = fieldError.getField();
            if (attribute.equals("password") || attribute.equals("orgId")
                    || attribute.equals("account") || attribute.equals("uin") || attribute.equals("status") ) {
                //跳过不需要验证的参数
                continue;
            }
            log.info("【修改admin系统账户】，参数错误：{}",fieldError.getDefaultMessage());
            return new RespWrapper(1001,fieldError.getDefaultMessage(),null);
        }
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");
        //封装参数
        obj.setModifyBy(admin.getUin());		// 修改人
        RespWrapper resp =  this.adminService.updateAdmin(obj,"");
        if (resp.getErrCode() == 0){
            RespWrapper rrr = logService.addLog(LogUtil.getLog(admin, request, "更新admin系统账号:" + obj.getAccount() + "(" + obj.getUin() + ")"));
        }
        return resp;
    }




}
