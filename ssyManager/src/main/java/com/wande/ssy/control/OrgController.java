package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.LogService;
import com.wande.ssy.dubbo.provider.service.OrgService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Org;
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
@RequestMapping("/org")
public class OrgController {

    @Reference(interfaceClass=OrgService.class)
    private OrgService orgService;

    @Reference(interfaceClass=LogService.class)
    private LogService logService;



    /**
     * 添加管辖机构
     */
    @RequestMapping("/addOrg")
    public  Object addOrg(@Valid Org obj ,
                          BindingResult bindingResult,
                          HttpServletRequest request){
        //效验参数
        String attribute;  //当前效验的参数名称
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            attribute = fieldError.getField();
            if (attribute.equals("orgId")) {
                //跳过不需要验证的参数
                continue;
            }
            log.info("【添加管辖机构Org】，{}参数错误： {} ",attribute,fieldError.getDefaultMessage());
            return new RespWrapper(1001,"【 "+attribute+" 】"+fieldError.getDefaultMessage(),null);
        }
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");
        //封装参数
        obj.setCreateBy(admin.getUin());	//创建人
        obj.setCreateTime(System.currentTimeMillis());	//创建时间,毫秒
        //调用远程服务
        RespWrapper<Integer> resp = orgService.addOrg(obj);
        if (resp.getErrCode() == 0){
            logService.addLog(LogUtil.getLog(admin, request, "添加管辖机构:" + obj.getName() + "(" + resp.getObj() + ")"));
        }
        //返回结果
        return resp;
    }


    /**
     * 根据地区ID获取管辖机构(用于管辖机构添加时候选择地区(要保证该地区没有管辖机构的存在)
     * 添加管辖机构选择地区的时候使用(超管)
     * */
    @RequestMapping("/checkOrgByRegion")
    public Object checkOrgByRegion(@RequestParam(value = "regionId",defaultValue = "-1") Integer regionId){
        System.out.println(regionId);
        if (regionId == -1){
            throw new RespException(1001, "地区ID不能为空!");
        }
        RespWrapper<Org> resp = orgService.getOneOrgByRegionId(regionId);
        if (resp.getErrCode() == 0 || resp.getObj() != null) {
            throw new RespException(1001, "该地区已存在管辖机构!");
        }
        return resp;
    }


    /**
     * 获取管辖机构树形下拉选
     */
    @RequestMapping("/getOrgTreeSelect")
    public Object getOrgTreeSelect(){
        RespWrapper<List<Org>> resp = orgService.getOrgList(Integer.MAX_VALUE);
        if (resp.getErrCode() != 0 && resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        List<Org> objs = resp.getObj();
        TreeUtil<Org> treeUtil = new TreeUtil<Org>();
        List<Org> result = treeUtil.buidTree(objs, Org.class, "orgId", "parentId", 0);
        for (Org o : result) {
            StringBuilder t = new StringBuilder();
            t.append("");
            for (int i = 1; i < o.getLevel(); i++) {
                t.append("==");
            }
            t.append(o.getName());
            o.set("namelabel", t.toString());
        }
        return RespWrapper.makeResp(0, "", result);
    }


    /**
     * 更新管辖机构
     * @return
     */
    @RequestMapping("/updateOrg")
    public Object updateOrg(@Valid Org obj,
                            BindingResult bindingResult,
                            HttpServletRequest request){
        //效验参数
        String attribute;  //当前效验的参数名称
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            attribute = fieldError.getField();
            if (attribute.equals("parentId")  || attribute.equals("regionId")) {
                //跳过不需要验证的参数
                continue;
            }
            log.info("【更新管辖机构Org】，{}参数错误： {} ",attribute,fieldError.getDefaultMessage());
            return new RespWrapper(1001,"【 "+attribute+" 】"+fieldError.getDefaultMessage(),null);
        }
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");
        //封装参数
        obj.setModifyBy(admin.getUin());				// 修改人
        obj.setModifyTime(System.currentTimeMillis());	// 修改时间,毫秒
        //调用远程Service
        RespWrapper<Boolean> resp = orgService.updateOrg(obj);

        if (resp.getErrCode() == 0){
            logService.addLog(LogUtil.getLog(admin, request, "更新管辖机构:" + obj.getName() + "(" + obj.getOrgId() + ")"));
        }
        //返回结果
        return resp;
    }
}
