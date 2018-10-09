package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.*;
import com.wande.ssy.entity.*;
import com.wande.ssy.enums.ItemStatus;
import com.wande.ssy.enums.ProvideWay;
import com.wande.ssy.enums.Standardcode;
import com.wande.ssy.utils.*;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.model.SuperBean;
import com.ynm3k.mvc.webutil.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/item")
public class ItemController {

    @Reference(interfaceClass=ItemService.class)
    private ItemService itemService;

    @Reference(interfaceClass=AreaService.class)
    private AreaService areaService;

    @Reference(interfaceClass=OrgService.class)
    private OrgService orgService;

    @Reference(interfaceClass=AdminService.class)
    private AdminService adminService;

    @Reference(interfaceClass=UserService.class)
    private UserService userService;

    @Reference(interfaceClass=SupplierService.class)
    private SupplierService supplierService;

    @Reference(interfaceClass=EqpService.class)
    private EqpService eqpService;

    @Reference(interfaceClass=EqpsortService.class)
    private EqpsortService eqpsortService;



    //日志服务
    @Reference(interfaceClass = LogService.class)
    private LogService logService;


    /**
     * 根据搜索条件导出分页列表
     * */
    @RequestMapping("/exportItem")
    public Object ExportItem(@Valid Item item,
                             @RequestParam(value = "projectId",defaultValue = "-1") Integer projectId, //项目分类ID
                             @RequestParam(value = "regionId",defaultValue = "-1") Integer regionId, //地区id
                             @RequestParam(value = "installYear",defaultValue = "") String installYear, //安装时间
                             @RequestParam(value = "expireYearStr",defaultValue = "") String expireYearStr, //到期时间
                             HttpServletResponse response,
                             HttpServletRequest request){
        // 当前登录用户
       Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        //封装参数
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("Item",item);
        params.put("projectId",projectId);   // 项目分类ID
        params.put("regionId",regionId);    // 地区id
        params.put("installYear",installYear);  //安装时间
        params.put("expireYearStr",expireYearStr);  //到期时间

        //设置参数,调用远程Service
        RespWrapper<DataPage<Item>> resp = this.itemService.getItemByPage(admin, params, 1, 20);	//只导出1000条
        if (resp.getErrCode() != 0) {
            throw new RespException(1001, "系统繁忙!");
        }
        if (resp.getObj().getRecord().size() <= 0) {
            throw new RespException(1001, "没有记录导出!");
        }

        // 扩展数据
        List<Item> ret = resp.getObj().getRecord();
        List<String> areaIds = new ArrayList<String>();
        List<String> orgIds = new ArrayList<String>();
        List<String> eqpIds = new ArrayList<String>();
        List<String> supplierIds = new ArrayList<String>();
        List<String> eqpsortIds = new ArrayList<String>();
        List<String> adminIds = new ArrayList<String>();
        List<String> userIds = new ArrayList<String>();
        for (Item o : ret) {
            if (!areaIds.contains(o.getAreaId()))
                areaIds.add(o.getAreaId()+"");
            if (!orgIds.contains(o.getOrgId()))
                orgIds.add(o.getOrgId()+"");
            if (!eqpIds.contains(o.getEqpId()))
                eqpIds.add(o.getEqpId()+"");
            if (!supplierIds.contains(o.getSupplierId()))
                supplierIds.add(o.getSupplierId()+"");
            if (!eqpsortIds.contains(o.getEqpsortId()))
                eqpsortIds.add(o.getEqpsortId()+"");
            if (!adminIds.contains(o.getAgencyId()))
                adminIds.add(o.getAgencyId()+"");
            if (!userIds.contains(o.getCreateBy()))
                userIds.add(o.getCreateBy()+"");
            if (!userIds.contains(o.getFlowBy()))
                userIds.add(o.getFlowBy()+"");
            if (!userIds.contains(o.getRepairBy()))
                userIds.add(o.getRepairBy()+"");
        }
        Map<Integer, Area> areaMap = areaService.getAreaMapInIds(StringUtil.join(areaIds, ",")).getObj();
        Map<Integer, Org> orgMap = orgService.getOrgMapInIds(StringUtil.join(orgIds, ",")).getObj();
        Map<Integer, Eqp> eqpMap = eqpService.getEqpMapInIds(StringUtil.join(eqpIds, ",")).getObj();
        Map<Integer, Supplier> supplierMap = supplierService.getSupplierMapInIds(StringUtil.join(supplierIds, ",")).getObj();
        Map<Integer, EqpSort> eqpsortMap = eqpsortService.getEqpsortMapInIds(StringUtil.join(eqpsortIds, ",")).getObj();
        Map<Long, Admin> adminMap = adminService.getAdminMapInIds(StringUtil.join(adminIds, ",")).getObj();
        Map<Long, User> userMap =userService.getUserMapInIds(StringUtil.join(userIds, ",")).getObj();

        for (Item o : ret) {
            Area area = areaMap.get(o.getAreaId());
            Org org = orgMap.get(o.getOrgId());
            Eqp eqp = eqpMap.get(o.getEqpId());
            Supplier supplier = supplierMap.get(o.getSupplierId());
            EqpSort eqpsort = eqpsortMap.get(o.getEqpsortId());
            Admin adminA = adminMap.get(o.getAgencyId());
            User userC = userMap.get(o.getCreateBy());
            User userF = userMap.get(o.getFlowBy());
            User userR = userMap.get(o.getRepairBy());
            o.set("regionFullName", area == null ? "" : area.getRegionFullName());
            o.set("areaName", area == null ? "" : area.getName());
            o.set("longitude", area == null ? "" : area.getLongitude());
            o.set("latitude", area == null ? "" : area.getLatitude());
            o.set("orgName", org == null ? "" : org.getName());
            o.set("agencyName", adminA == null ? "" : adminA.getName());
            o.set("eqpName", eqp == null ? "" : eqp.getName());
            o.set("eqpCatesn", eqp == null ? "" : eqp.getCatesn());
            o.set("supplierName", supplier == null ? "" : supplier.getName());
            o.set("createName", userC == null ? "" : userC.getName());
            o.set("flowName" , userF == null ? "" : userF.getName());
            o.set("flowPhone" , userF == null ? "" : userF.getPhone());
            o.set("repairName"	, userR	== null ? "" : userR.getName());
            o.set("repairPhone" , userR == null ? "" : userR.getPhone());
            o.set("sortName", eqpsort == null ? "" : eqpsort.getEqpsortName());
            o.set("statusName", ItemStatus.getName(o.getStatus()));
            o.set("standardcodeName", Standardcode.getName(o.getStandardcode()));
            o.set("provideWayName", ProvideWay.getName(o.getProvideWay()));
            o.set("installDate", DateTimeUtil.formatDate(new Date(o.getInstallTime()), "yyyy-MM-dd"));
            long expireTime = o.getExpireTime();
            o.set("expireDate", DateTimeUtil.formatDate(new Date(expireTime), "yyyy-MM-dd"));
            if (expireTime == 0) {
                o.set("leftTime", "");
            } else {
                o.set("leftTime", expireTime - System.currentTimeMillis() <= 0 ? "已过期" : DateTimeUtil.getSubtractTimeStr(expireTime, System.currentTimeMillis(), 2));
            }
        }
        //转为Map<String, Object>
        List<Map<String, Object>> dataset = new ArrayList<Map<String, Object>>(5000);
        System.out.println("大小："+ret.size());
        // TODO 数组越界
        for (Item obj : ret) {
            dataset.add( BeanUtil.beanToMap(obj));
            //dataset.add(SuperBean.makeSuperBean(obj));
        }
        //将导出文件保存到本地
        String fileName = DateTimeUtil.formatDate("yyyy-MM-dd_HHmmss") + "(" + admin.getUin() + ").xls";
        String fileDir = PathUtil.getWebRootPath() + UploadConfig.Excel_Item_Dir + DateTimeUtil.formatDate("yyyy-MM-dd") + "/" + fileName;
        String sheetName = "item";
        POIUtil poiUtil = new POIUtil(fileDir, sheetName);
        String titleColumn[] = {"areaName","orgName","agencyName","supplierName","sortName","eqpName","eqpCatesn",
                "standardcodeName", "createName", "flowName", "flowPhone", "repairName", "repairPhone",
                "statusName","installDate","expireDate","leftTime"};
        String titleName[] = {"安装地点","管辖部门","所属代理商","所属供应商","所属分类","设施名称","设施型号",
                "设施标准","负责人","巡检负责人","联系人","维修负责人","联系电话",
                "状态","安装时间","到期时间","设施剩余期限"};
        int titleSize[] = {13, 13, 13, 13, 13, 13, 13,
                13, 13, 13, 13, 13, 13,
                13, 13, 13, 13};
        poiUtil.Map2Excel(titleColumn, titleName, titleSize, dataset);
        try {
            //设置response, 导出记录
            response.reset();
            response.setContentType("application/x-msdownload");
            response.setHeader("Location",fileName);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            OutputStream ou = response.getOutputStream();
            InputStream in = new FileInputStream(fileDir);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = in.read(buffer)) != -1) {
                ou.write(buffer, 0, i);
            }
            ou.flush();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RespException(1001, "系统繁忙!");
        }

        return null;
    }


    /**
     * 获取安装年份下拉选列表
     */
    @RequestMapping("/getInstallYearSelect")
    public Object getInstallYearSelect(){

        List<SuperBean> result = new ArrayList<SuperBean>();
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        int year = c.get(Calendar.YEAR);
        int startYear = year - 10;
        while (startYear ++ < year) {
            SuperBean r = new SuperBean();
            r.put("id", startYear);
            r.put("installYear", startYear+"年");
            result.add(r);
        }
        return RespWrapper.makeResp(0, "", result);
    }

    /* 多条件分页查询
     * @param: [item, projectId, regionId, installYear, expireYearStr, pageNo, pageSize, request]
     * @return: java.lang.Object */
    @RequestMapping("/getItemPage")
    public Object getItemPage(@Valid Item item,
                              @RequestParam(value = "projectId",defaultValue = "-1") Integer projectId, //项目分类ID
                              @RequestParam(value = "regionId",defaultValue = "-1") Integer regionId, //地区id
                              @RequestParam(value = "installYear",defaultValue = "") String installYear, //安装时间
                              @RequestParam(value = "expireYearStr",defaultValue = "") String expireYearStr, //到期时间
                              @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo, //当前页
                              @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize, //页面大小
                              HttpServletRequest request){
        // 当前登录用户
       Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        //封装参数
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("Item",item);
        params.put("projectId",projectId);   // 项目分类ID
        params.put("regionId",regionId);    // 地区id
        params.put("installYear",installYear);  //安装时间
        params.put("expireYearStr",expireYearStr);  //到期时间

        //设置参数,调用远程Service
        RespWrapper<DataPage<Item>> resp = this.itemService.getItemByPage(admin, params, pageNo, pageSize);	//只导出1000条
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }

        // 扩展数据
        List<Item> ret = resp.getObj().getRecord();
        List<String> areaIds = new ArrayList<String>();
        List<String> orgIds = new ArrayList<String>();
        List<String> eqpIds = new ArrayList<String>();
        List<String> supplierIds = new ArrayList<String>();
        List<String> eqpsortIds = new ArrayList<String>();
        List<String> adminIds = new ArrayList<String>();
        List<String> userIds = new ArrayList<String>();
        for (Item o : ret) {
            if (!areaIds.contains(o.getAreaId()))
                areaIds.add(o.getAreaId()+"");
            if (!orgIds.contains(o.getOrgId()))
                orgIds.add(o.getOrgId()+"");
            if (!eqpIds.contains(o.getEqpId()))
                eqpIds.add(o.getEqpId()+"");
            if (!supplierIds.contains(o.getSupplierId()))
                supplierIds.add(o.getSupplierId()+"");
            if (!eqpsortIds.contains(o.getEqpsortId()))
                eqpsortIds.add(o.getEqpsortId()+"");
            if (!adminIds.contains(o.getAgencyId()))
                adminIds.add(o.getAgencyId()+"");
            if (!userIds.contains(o.getCreateBy()))
                userIds.add(o.getCreateBy()+"");
            if (!userIds.contains(o.getFlowBy()))
                userIds.add(o.getFlowBy()+"");
            if (!userIds.contains(o.getRepairBy()))
                userIds.add(o.getRepairBy()+"");
        }
        Map<Integer, Area> areaMap = areaService.getAreaMapInIds(StringUtil.join(areaIds, ",")).getObj();
        Map<Integer, Org> orgMap = orgService.getOrgMapInIds(StringUtil.join(orgIds, ",")).getObj();
        Map<Integer, Eqp> eqpMap = eqpService.getEqpMapInIds(StringUtil.join(eqpIds, ",")).getObj();
        Map<Integer, Supplier> supplierMap = supplierService.getSupplierMapInIds(StringUtil.join(supplierIds, ",")).getObj();
        Map<Integer, EqpSort> eqpsortMap = eqpsortService.getEqpsortMapInIds(StringUtil.join(eqpsortIds, ",")).getObj();
        Map<Long, Admin> adminMap = adminService.getAdminMapInIds(StringUtil.join(adminIds, ",")).getObj();
        Map<Long, User> userMap =userService.getUserMapInIds(StringUtil.join(userIds, ",")).getObj();

        for (Item o : ret) {
            System.out.println(o.toString());
            Area area = areaMap.get(o.getAreaId());
            Org org = orgMap.get(o.getOrgId());
            Eqp eqp = eqpMap.get(o.getEqpId());
            Supplier supplier = supplierMap.get(o.getSupplierId());
            EqpSort eqpsort = eqpsortMap.get(o.getEqpsortId());
            Admin adminA = adminMap.get(o.getAgencyId());
            User userC = userMap.get(o.getCreateBy());
            User userF = userMap.get(o.getFlowBy());
            User userR = userMap.get(o.getRepairBy());
            o.set("regionFullName", area == null ? "" : area.getRegionFullName());
            o.set("areaName", area == null ? "" : area.getName());
            o.set("longitude", area == null ? "" : area.getLongitude());
            o.set("latitude", area == null ? "" : area.getLatitude());
            o.set("orgName", org == null ? "" : org.getName());
            o.set("agencyName", adminA == null ? "" : adminA.getName());
            o.set("eqpName", eqp == null ? "" : eqp.getName());
            o.set("eqpCatesn", eqp == null ? "" : eqp.getCatesn());
            o.set("supplierName", supplier == null ? "" : supplier.getName());
            o.set("createName", userC == null ? "" : userC.getName());
            o.set("flowName" , userF == null ? "" : userF.getName());
            o.set("flowPhone" , userF == null ? "" : userF.getPhone());
            o.set("repairName"	, userR	== null ? "" : userR.getName());
            o.set("repairPhone" , userR == null ? "" : userR.getPhone());
            o.set("sortName", eqpsort == null ? "" : eqpsort.getEqpsortName());
            o.set("statusName", ItemStatus.getName(o.getStatus()));
            o.set("standardcodeName", Standardcode.getName(o.getStandardcode()));
            o.set("provideWayName", ProvideWay.getName(o.getProvideWay()));
            o.set("installDate", DateTimeUtil.formatDate(new Date(o.getInstallTime()), "yyyy-MM-dd"));
            long expireTime = o.getExpireTime();
            o.set("expireDate", DateTimeUtil.formatDate(new Date(expireTime), "yyyy-MM-dd"));
            if (expireTime == 0) {
                o.set("leftTime", "");
            } else {
                o.set("leftTime", expireTime - System.currentTimeMillis() <= 0 ? "已过期" : DateTimeUtil.getSubtractTimeStr(expireTime, System.currentTimeMillis(), 2));
            }
        }
        return resp;
    }


    /**
     * 获取器材标准
     *
     */
    @RequestMapping("/getStandardcodeSelect")
    public  Object getStandardcodeSelect(){
        //移除掉器材标准中的老国际，现在器材标准只有 无标准  和  新国际  两种
        List<Map<String, Object>>  state_list = Standardcode.getSelect();
        for(int i=0;i<state_list.size();i++) {
            Map<String, Object> map = state_list.get(i);
            if(map.get("standardcodeName").toString().equals("老国际")) {
                state_list.remove(i);
                break;
            }
        }
        return RespWrapper.makeResp(0, "", state_list);
    }






}
