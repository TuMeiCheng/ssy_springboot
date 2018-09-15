package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.EqpService;
import com.wande.ssy.dubbo.provider.service.EqpsortService;
import com.wande.ssy.dubbo.provider.service.LogService;
import com.wande.ssy.dubbo.provider.service.SupplierService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Eqp;
import com.wande.ssy.entity.EqpSort;
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

@Slf4j
@RestController
@RequestMapping("/eqp")
public class EqpController {

    @Reference(interfaceClass=EqpService.class)
    private EqpService eqpService;

    @Reference(interfaceClass=EqpsortService.class)
    private EqpsortService eqpsortService;

    @Reference(interfaceClass=SupplierService.class)
    private SupplierService supplierService;

    @Reference(interfaceClass = LogService.class)
    private LogService logService;
    
    /* 添加器材
     * @param: [obj, bindingResult, regionIds, request]
     * @return: java.lang.Object */
    @RequestMapping("/addEqp")
    public Object AddEqp(@Valid Eqp obj,
                            BindingResult bindingResult,
                            HttpServletRequest request){
        //效验参数
        String attribute; //当前效验的参数
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            attribute = fieldError.getField();
            if (attribute.equals("eqpId") || attribute.equals("status")) {
                //跳过不需要验证的参数
                continue;
            }
            log.info("【添加器材Eqp】，{}参数错误： {} ",attribute,fieldError.getDefaultMessage());
            return new RespWrapper(1001,"【 "+attribute+" 】"+fieldError.getDefaultMessage(),null);
        }
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(2);
        admin.setUin(30L);
        admin.setAccount("admin");
        //封装数据
        obj.setCreateBy(admin.getUin());	            // 创建人
        obj.setStatus(0);								// 0启用，1禁止
        obj.setCreateTime(System.currentTimeMillis());	// 创建时间,毫秒
        //调用远程服务保存
        RespWrapper resp = this.eqpService.addEqp(obj);
        if (resp.getErrCode() == 0){
            //添加操作日志
            logService.addLog(LogUtil.getLog(admin, request, "添加器材:" + obj.getName() + "(" + resp.getObj() + ")"));
        }
        return  resp;
    }


    /* 器材列表
     * @param: []
     * @return: java.lang.Object */
    @RequestMapping("/getEqpList")
    public Object getEqpList(@RequestParam(value = "eqpsortId",defaultValue = "-1") Integer eqpsortId, // 器材分类
                             @RequestParam(value = "supplierId",defaultValue = "-1") Integer supplierId //所属供应商ID
                            ){
        //设置参数,调用远程Service
        RespWrapper<List<Eqp>> resp = this.eqpService.getEqpByList(supplierId, eqpsortId);
        //返回结果
        return resp;
    }


    /* 器材分页列表
     * @param: [pageNo, pageSize, eqpsortId, supplierId, hasVideo, keyword]
     * @return: java.lang.Object */
    @RequestMapping("/getEqpPage")
    public Object getEqpPage(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                             @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                             @RequestParam(value = "eqpsortId",defaultValue ="-1") Integer eqpsortId,   // 器材分类
                             @RequestParam(value = "supplierId",defaultValue ="-1") Integer supplierId, // 所属供应商id
                             @RequestParam(value = "hasVideo",defaultValue ="-1") Integer hasVideo,     // 是否有视频
                             @RequestParam(value = "keyword",defaultValue ="") String keyword){         // 关键字:器材/名称

            //封装参数
            Map<String,Object> params = new HashMap<String,Object>();
                params.put("eqpsortId",eqpsortId);
                params.put("supplierId",supplierId);
                params.put("hasVideo",hasVideo);
                params.put("keyword",keyword);
            //调用远程Service
            RespWrapper<DataPage<Eqp>> resp =this.eqpService.getEqpByPage(params, pageNo, pageSize);
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        DataPage<Eqp> page = resp.getObj();
        List<Eqp> ret = page.getRecord();
        for (Eqp o : ret) {
            Supplier supplier = supplierService.getOneSupplier(o.getSupplierId()).getObj();
            EqpSort eqpsort = eqpsortService.getOneEqpSort(o.getEqpsortId()).getObj();
            o.set("supplierName", supplier == null ? "" : supplier.getName());
            o.set("eqpsortName", eqpsort == null ? "" : eqpsort.getEqpsortName());
        }
        //返回结果
        return  resp;
    }


    /* 更新器材
     * @param: [eqp, bindingResult, request]
     * @return: java.lang.Object */
    @RequestMapping("/updateEqp")
    public Object updateEqp(@Valid Eqp obj ,
                            BindingResult bindingResult,
                            HttpServletRequest request){
        //效验参数
        String attribute; //当前效验的参数
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            attribute = fieldError.getField();
            if (attribute.equals("supplierId")) {
                //跳过不需要验证的参数
                continue;
            }
            log.info("【更新器材Eqp】，{}参数错误： {} ",attribute,fieldError.getDefaultMessage());
            return new RespWrapper(1001,"【 "+attribute+" 】"+fieldError.getDefaultMessage(),null);
        }
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(2);
        admin.setUin(30L);
        admin.setAccount("admin");
        //封装数据
        obj.setModifyBy(admin.getUin());	// 最后修改人
        //调用远程服务
        RespWrapper<Boolean> resp = this.eqpService.updateEqp(obj);

        if (resp.getErrCode() != 0){
            logService.addLog(LogUtil.getLog(admin, request, "更新器材:" + obj.getName() + "(" + obj.getEqpId() + ")"));
        }
        //返回结果
        return resp;
    }










}
