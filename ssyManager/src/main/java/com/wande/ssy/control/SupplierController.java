package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.AdminService;
import com.wande.ssy.dubbo.provider.service.LogService;
import com.wande.ssy.dubbo.provider.service.SupplierService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Supplier;
import com.wande.ssy.utils.LogUtil;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
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

/*
 *@author tmc
 *@date 2018/9/19 14:19
 * 器材供应商接口层
 */
@Slf4j
@RestController
@RequestMapping("/supplier")
public class SupplierController {

    //供应商服务
    @Reference(interfaceClass=SupplierService.class)
    private SupplierService supplierService;

    //系统用户服务
    @Reference(interfaceClass=AdminService.class)
    private AdminService adminService;

    //日志服务
    @Reference(interfaceClass=LogService.class)
    private LogService logService;




    /**
     * 添加供应商
     */
    @RequestMapping("/addSupplier")
    public  Object addSupplier(@Valid Supplier obj ,
                          BindingResult bindingResult,
                          HttpServletRequest request){
        //效验参数
        String attribute;  //当前效验的参数名称
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            attribute = fieldError.getField();
            if (attribute.equals("supplierId") || attribute.equals("status")) {
                //跳过不需要验证的参数
                continue;
            }
            log.info("【添加供应商Supplier】，{}参数错误： {} ",attribute,fieldError.getDefaultMessage());
            return new RespWrapper(1001,"【 "+attribute+" 】"+fieldError.getDefaultMessage(),null);
        }
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");
        //封装参数
        obj.setStatus(0);					// 1启用，0禁止
        obj.setCreateBy(admin.getUin());	//创建人
        obj.setCreateTime(System.currentTimeMillis());	//创建时间,毫秒
        //调用远程服务
        RespWrapper<Integer> resp = this.supplierService.addSupplier(obj);
        if (resp.getErrCode() == 0){
            logService.addLog(LogUtil.getLog(admin, request, "添加供应商:" + obj.getName() + "(" + resp.getObj() + ")"));
        }
        //返回结果
        return resp;
    }


    /* 获取供应商列表
     * @param: []
     * @return: java.lang.Object */
    @RequestMapping("/getSupplierList")
    public  Object getSupplierList(){
        RespWrapper<List<Supplier>> resp = supplierService.getSupplierList();
        return resp;
    }


    /* 获取供应商分页列表
     * @param: []
     * @return: java.lang.Object */
    @RequestMapping("/getSupplierPage")
    public Object getSupplierPage(@RequestParam(value = "pageNo" ,defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                                  @RequestParam(value = "keyword",defaultValue ="") String keyword  // 关键字搜索  供应商名称
                                   ){
        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("keyword",keyword);
        RespWrapper<DataPage<Supplier>> resp = supplierService.getSupplierByPage(params, pageNo, pageSize);
        if (resp.getErrCode() != 0 && resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        List<Supplier> ret = resp.getObj().getRecord();
        for (Supplier o : ret) {
            Admin admin = adminService.getAdminByUin(o.getCreateBy()).getObj();
            o.set("createName", admin == null ? "" : admin.getName());
        }
        return resp;
    }



    /* 更新供应商
     * @param: []
     * @return: java.lang.Object */
    @RequestMapping("/updateSupplier")
    public Object UpdateSupplier(@Valid Supplier obj,
                                 BindingResult bindingResult,
                                 HttpServletRequest request){
        //效验参数
        String attribute;  //当前效验的参数名称
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            attribute = fieldError.getField();    //当前效验参数
            log.info("【更新器材供应商Supplier】，{}参数错误： {} ",attribute, fieldError.getDefaultMessage());
            return new RespWrapper(1001,"【 "+attribute+" 】"+fieldError.getDefaultMessage(),null);
        }
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");
        //封装参数
        obj.setModifyBy(admin.getUin());				// 最后修改人

        //调用远程Service
        RespWrapper<Boolean> resp = supplierService.updateSupplier(obj);
        if (resp.getErrCode() == 0){
            logService.addLog(LogUtil.getLog(admin, request, "更新供应商:" + obj.getName() + "(" + obj.getSupplierId() + ")"));
        }
        //返回结果
        return resp;
    }

}
