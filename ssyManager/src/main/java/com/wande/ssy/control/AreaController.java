package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.AreaService;
import com.wande.ssy.dubbo.provider.service.LogService;
import com.wande.ssy.dubbo.provider.service.OrgService;
import com.wande.ssy.dubbo.provider.service.RegionService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Area;
import com.wande.ssy.entity.Org;
import com.wande.ssy.entity.Region;
import com.wande.ssy.enums.AreaType;
import com.wande.ssy.enums.ProvideWay;
import com.wande.ssy.utils.LogUtil;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.webutil.NetUtil;
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
@RequestMapping("/area")
public class AreaController {

    @Reference(interfaceClass=AreaService.class)
    private AreaService areaService;

    @Reference(interfaceClass=RegionService.class)
    private RegionService regionService;

    @Reference(interfaceClass=OrgService.class)
    private OrgService orgService;

    @Reference(interfaceClass = LogService.class)
    private LogService logService;


    /* 添加场地
     * @param: [obj, bindingResult, request]
     * @return: java.lang.Object */
    @RequestMapping("/addArea")
    public  Object addArea(@Valid Area obj ,
                              BindingResult bindingResult,
                              HttpServletRequest request){
        //效验参数
        String attribute;  //当前效验的参数名称
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            attribute = fieldError.getField();
            if (attribute.equals("areaId") || attribute.equals("bbb")) {
                //跳过不需要验证的参数
                continue;
            }
            log.info("添加Area场地，参数错误：{}",fieldError.getDefaultMessage());
            return new RespWrapper(1001,fieldError.getDefaultMessage(),null);
        }
        Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;

        //封装参数
        obj.setAgencyId(admin.getUin());				// 管理公司ID
        obj.setCreateBy(admin.getUin());				// 创建人
        obj.setCreateTime(System.currentTimeMillis());	// 创建时间,毫秒

        RespWrapper resp = this.areaService.addArea(obj);
        if (resp.getErrCode() == 0){
            //添加操作日志
            logService.addLog(LogUtil.getLog(admin, request, "添加场地:" + obj.getName() + "(" + resp.getObj() + ")"));
        }
        return resp;
    }

    /* 获取场地分页列表
     * @param: []
     * @return: java.lang.Object */
    @RequestMapping("/getAreaPage")
    public Object getAreaPage(@RequestParam(value = "pageNo" ,defaultValue = "1") Integer pageNo,
                              @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                              @RequestParam(value = "keyword",defaultValue ="") String keyword,
                              @RequestParam(value = "regionId",defaultValue = "-1") Integer regionId,
                              @RequestParam(value = "areaType",defaultValue = "-1") Integer areaType,
                              HttpServletRequest request){

        Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户

        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
        if (StringUtil.isNotEmpty(keyword)){
            params.put("keyword",keyword);
        }
        if (regionId != -1){
            params.put("regionId",regionId);
        }
        if (areaType != -1){
            params.put("areaType",areaType);
        }
        //调用远程服务
        RespWrapper<DataPage<Area>> resp = this.areaService.getAreaByPage(admin,params,pageNo,pageSize);
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        DataPage<Area> page = resp.getObj();
        List<Area> ret = page.getRecord();
        for (Area o : ret) {
            Region region = regionService.getOneRegion(o.getRegionId()).getObj();
            Org org = orgService.getOneOrg(o.getOrgId()).getObj();
            o.set("regionName", region == null ? "" : region.getName());
            o.set("orgName", org == null ? "" : org.getName());
            o.set("provideWayName", ProvideWay.getName(o.getProvideWay()));
            o.set("areaTypeName", AreaType.getName(o.getAreaType()));
            o.set("url", "http://www.hwdeal.com/mobile/qrcode/"+(o.getQrcode() == null ? "" : o.getQrcode()));
        }
        //返回结果
        return  resp;
    }

    /**
     * 根据当前登录角色过滤场地下拉选列表
     */
    @RequestMapping("/getAreaSelect")
    public Object getAreaSelect(HttpServletRequest request){
        Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
       return areaService.getAreaSelect(admin);
    }

    /**
     * 获取场地提供方式
     * */
    @RequestMapping("/getProvideWaySelect")
    public Object getProvideWaySelect(){
        return RespWrapper.makeResp(0, "", ProvideWay.getSelect());
    }

    /* 更新场地
     * @param: []
     * @return: java.lang.Object */
    @RequestMapping("/updateArea")
    public Object updateArea(@Valid Area obj ,
                             BindingResult bindingResult,
                             HttpServletRequest request){
        //效验参数
        String attribute;  //当前效验的参数名称
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            attribute = fieldError.getField();
            if (attribute.equals("orgId") || attribute.equals("name") || attribute.equals("regionId")) {
                //跳过不需要验证的参数 参数效验使用的是注解式的，在此方法参数列表中@Valid 对应的实体bena里面查看
                continue;
            }
            log.info("【更新场地】，{}参数错误： {} ",attribute,fieldError.getDefaultMessage());
            return new RespWrapper(1001,"【"+attribute+"】"+fieldError.getDefaultMessage(),null);
        }
        Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;

        RespWrapper<Area> rObj = this.areaService.getOneArea(obj.getAreaId());
        if (rObj.getErrCode() != 0 || rObj.getObj() == null) {
            throw new RespException(1001, rObj.getErrMsg());
        }
        //封装参数
        obj.setModifyBy(admin.getUin());				// 最后修改人
        obj.setModifyTime(System.currentTimeMillis());	// 修改时间
        //调用远程Service
        RespWrapper<Boolean> resp = this.areaService.updateArea(obj);
        if (rObj.getErrCode() != 0){
            logService.addLog(LogUtil.getLog(admin, request, "更新场地:" + obj.getName() + "(" + obj.getAreaId() + ")"));
        }
        //返回结果
        return resp;
    }



}
