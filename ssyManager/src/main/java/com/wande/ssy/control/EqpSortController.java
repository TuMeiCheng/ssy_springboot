package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.EqpsortService;
import com.wande.ssy.dubbo.provider.service.LogService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.EqpSort;
import com.wande.ssy.enums.EqpProjectSort;
import com.wande.ssy.utils.LogUtil;
import com.wande.ssy.utils.TreeUtil;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/eqpsort")
public class EqpSortController {

    @Reference(interfaceClass=EqpsortService.class)
    private EqpsortService eqpsortService;

    @Reference(interfaceClass = LogService.class)
    private LogService logService;

    /* 添加器材分类
     * @param: [obj, bindingResult, regionIds, request]
     * @return: java.lang.Object */
    @RequestMapping("/addEqpsort")
    public Object AddEqp(@Valid EqpSort obj,
                         BindingResult bindingResult,
                         HttpServletRequest request){
        //效验参数
        String attribute; //当前效验的参数
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            attribute = fieldError.getField();
            if (attribute.equals("eqpsortId")) {
                //跳过不需要验证的参数
                continue;
            }
            log.info("【添加器材分类EqpSort】，{}参数错误： {} ",attribute,fieldError.getDefaultMessage());
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
        obj.setCreateTime(System.currentTimeMillis());	// 创建时间,毫秒
        //调用远程服务保存
        RespWrapper resp = this.eqpsortService.addEqpsort(obj);
        if (resp.getErrCode() == 0){
            //添加操作日志
            logService.addLog(LogUtil.getLog(admin, request, "添加器材分类:" + obj.getEqpsortName() + "(" + resp.getObj() + ")"));
        }
        return  resp;
    }

    /**
     * 器材分类下拉选列表*/
    @RequestMapping("/getEqpsortSelect")
    public Object getEqpsortSelect(@RequestParam("parentId") Integer parentId){
       return eqpsortService.getEqpSortList(parentId);
    }

    /**
     * 器材分类树形下拉选列表*/
    @RequestMapping("/getEqpsortTreeSelect")
    public Object getEqpsortTreeSelect(@RequestParam(value = "parentId",defaultValue = "-1") Integer parentId){
        RespWrapper<List<EqpSort>> rets = this.eqpsortService.getEqpSortList(-1);
        if (rets.getErrCode() != 0) {
            throw new RespException(1001, rets.getErrMsg());
        }
        List<EqpSort> objs = rets.getObj();

        TreeUtil<EqpSort> treeUtil = new TreeUtil<EqpSort>();
        List<EqpSort> result = treeUtil.buidTree(objs, EqpSort.class, "eqpsortId", "parentId", 0);
        for (EqpSort o : result) {
            StringBuilder t = new StringBuilder();
            t.append("");
            for (int i = 1; i < o.getLevel(); i++) {
                t.append("==");
            }
            t.append(o.getEqpsortName());
            o.set("namelabel", t.toString());
        }
        return  RespWrapper.makeResp(0, "", result);
    }


    /**
     * 器材分类下拉选列表*/
    @RequestMapping("/getProjectSortSelect")
    public Object getProjectSortSelect(@RequestParam("parentId") Integer parentId){
        return RespWrapper.makeResp(0, "", EqpProjectSort.getSelect());
    }

    /**
     * 更新器材分类*/
    @RequestMapping("/updateEqpsort")
    public Object updateEqpsort(@Valid EqpSort obj,
                                BindingResult bindingResult,
                                HttpServletRequest request){
        //效验参数
        String attribute; //当前效验的参数
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            attribute = fieldError.getField();
            if (attribute.equals("parentId")) {
                //跳过不需要验证的参数
                continue;
            }
            log.info("【更新器材分类EqpSort】，{}参数错误： {} ",attribute,fieldError.getDefaultMessage());
            return new RespWrapper(1001,"【 "+attribute+" 】"+fieldError.getDefaultMessage(),null);
        }
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(2);
        admin.setUin(30L);
        admin.setAccount("admin");
        //封装参数
        obj.setModifyBy(admin.getUin());		//修改人
        obj.setModifyTime(System.currentTimeMillis());	//修改时间
        //调用远程Service
        RespWrapper<Boolean> resp = this.eqpsortService.updateEqpsort(obj);
        if (resp.getErrCode() == 0)
        logService.addLog(LogUtil.getLog(admin, request, "更新器材分类:" + obj.getEqpsortName() + "(" + obj.getEqpsortId() + ")"));
        //返回结果
        return resp;
    }








}
