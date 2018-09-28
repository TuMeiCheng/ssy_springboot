package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.LogService;
import com.wande.ssy.dubbo.provider.service.RegionService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Region;
import com.wande.ssy.utils.LogUtil;
import com.wande.ssy.utils.StringUtil;
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
@RequestMapping("/region")
public class RegionController {

    @Reference(interfaceClass=RegionService.class)
    private RegionService regionService;


    @Reference(interfaceClass=LogService.class)
    private LogService logService;


    /* 添加地区
     * @param: []
     * @return: java.lang.Object */
    @RequestMapping("/addRegion")
    public Object AddRegion(@Valid Region obj ,
                            BindingResult bindingResult,
                            HttpServletRequest request){
        //效验参数
        String attribute;  //当前效验的参数名称
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            attribute = fieldError.getField();
            if (attribute.equals("regionId")) {
                //跳过不需要验证的参数
                continue;
            }
            log.info("【添加省市区region】，{}参数错误： {} ",attribute,fieldError.getDefaultMessage());
            return new RespWrapper(1001,"【 "+attribute+" 】"+fieldError.getDefaultMessage(),null);
        }
        if (StringUtil.isEmpty(obj.getScode()) || !StringUtil.isAcronym(obj.getScode())) {
            throw new RespException(1001, "拼音简称不能为空，且只能输入大写字母!");
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
        //调用远程Service
        RespWrapper<Integer> resp = regionService.addRegion(obj);
        //添加操作日志
        if (resp.getErrCode() == 0){
            logService.addLog(LogUtil.getLog(admin, request, "添加地区:" + obj.getName() + "(" + resp.getObj() + ")"));
        }
        //返回结果
        return resp;
    }


    /**
     * 根据regionId获取一个地区
     * Data
     */
    @RequestMapping("/getOneRegion")
    public Object getOneRegion(@RequestParam(value = "regionId",defaultValue = "-1") Integer regionId // 地区ID
                                ){
        //参数校验
        if (regionId == -1) {
            throw new RespException(1001, "地区ID不能为空!");
        }
        RespWrapper<Region> resp = regionService.getOneRegion(regionId);
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(1001, resp.getErrMsg());
        }
        //返回结果
        return resp;
    }


    /**
     * 获取地区region列表
     *
     */
    @RequestMapping("/getRegionList")
    public  Object getRegionList(@RequestParam(value = "parentId",defaultValue = "-1")Integer parentId){
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");

        RespWrapper<List<Region>> resp =regionService.getRegionListByParentId(admin, parentId);
        //返回结果
        return resp;
    }



    /**
     *  获取管辖机构下拉选
     *  Integer.MAX_VALUE  0x7fffffff
     */
    @RequestMapping("/getRegionTreeSelect")
    public  Object getRegionTreeSelect(){
        RespWrapper<List<Region>> rets = regionService.getRegionList(Integer.MAX_VALUE);
        if (rets.getErrCode() != 0) {
            throw new RespException(1001, rets.getErrMsg());
        }
        List<Region> objs = rets.getObj();
        TreeUtil<Region> treeUtil = new TreeUtil<Region>();
        List<Region> result = treeUtil.buidTree(objs, Region.class, "regionId", "parentId", 0);
        for (Region o : result) {
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


    /* 更新省市区
     * @param: [obj, bindingResult, request]
     * @return: java.lang.Object */
    @RequestMapping("updateRegion")
    public Object updateRegion(@Valid Region obj ,
                               BindingResult bindingResult,
                               HttpServletRequest request){
        //效验参数
        String attribute;  //当前效验的参数名称
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            attribute = fieldError.getField();
            if (attribute.equals("parentId")) {
                //跳过不需要验证的参数
                continue;
            }
            log.info("【更新省市区region】，{}参数错误： {} ",attribute,fieldError.getDefaultMessage());
            return new RespWrapper(1001,"【 "+attribute+" 】"+fieldError.getDefaultMessage(),null);
        }
        if (StringUtil.isEmpty(obj.getScode()) || !StringUtil.isAcronym(obj.getScode())) {
            throw new RespException(1001, "拼音简称不能为空，且只能输入大写字母!");
        }
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");
        //封装数据
        obj.setModifyBy(admin.getUin());				// 创建人
        obj.setModifyTime(System.currentTimeMillis());	// 创建时间,毫秒

        //调用远程Service
        RespWrapper<Boolean> resp = regionService.updateRegion(obj);

        if (resp.getErrCode() == 0){
            logService.addLog(LogUtil.getLog(admin, request, "更新地区:" + obj.getName() + "(" + obj.getRegionId() + ")"));
        }
        //返回结果
        return resp;
    }


}
