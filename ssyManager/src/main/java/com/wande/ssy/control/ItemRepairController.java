package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.*;
import com.wande.ssy.entity.*;
import com.wande.ssy.enums.ItemStatus;
import com.wande.ssy.utils.*;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.model.SuperBean;
import com.ynm3k.mvc.webutil.NetUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("/itemrepair")
public class ItemRepairController {

    @Reference(interfaceClass = ItemrepairService.class)
    private ItemrepairService itemrepairService;

    @Reference(interfaceClass = OrgService.class)
    private OrgService orgService;

    @Reference(interfaceClass = UserService.class)
    private UserService userService;

    @Reference(interfaceClass = EqpsortService.class)
    private EqpsortService eqpsortService;

    @Reference(interfaceClass = EqpService.class)
    private EqpService eqpService;

    @Reference(interfaceClass = SupplierService.class)
    private SupplierService supplierService;

    @Reference(interfaceClass = AreaService.class)
    private AreaService areaService;

    @Reference(interfaceClass = AdminService.class)
    private AdminService adminService;

    @Reference(interfaceClass = BrokenreasonService.class)
    private BrokenreasonService brokenreasonService;

    @Reference(interfaceClass = ItemflowService.class)
    private ItemflowService itemflowService;

    @Reference(interfaceClass = ItemService.class)
    private ItemService itemService;

    /**
     * 根据搜索条件导出分页列表
     */
    @RequestMapping("/exportItemrepair")
    public Object exportItemrepair(HttpServletRequest request,
                                   HttpServletResponse response) {

        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");

        int projectId = NetUtil.getIntParameter(request, "projectId", Integer.MAX_VALUE);    // 项目分类ID
        int eqpId = NetUtil.getIntParameter(request, "eqpId", Integer.MAX_VALUE);            // 器材ID
        int eqpsortId = NetUtil.getIntParameter(request, "eqpsortId", Integer.MAX_VALUE);    // 器材分类ID
        int areaId = NetUtil.getIntParameter(request, "areaId", Integer.MAX_VALUE);        // 场地ID
        int status = NetUtil.getIntParameter(request, "status", Integer.MAX_VALUE);        // 网络报修单状态(0待跟进 1已跟进  维修单的状态)
        String expireYearStr = NetUtil.getStringParameter(request, "expireYearStr", "");    // 到期时间
        int reasonType = NetUtil.getIntParameter(request, "reasonType", Integer.MAX_VALUE);    // 报修理由类别
        String keyword = NetUtil.getStringParameter(request, "keyword", "");            // 器材编码

        //设置参数,调用远程Service
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("projectId", projectId);
        params.put("eqpId", eqpId);
        params.put("eqpsortId", eqpsortId);
        params.put("areaId", areaId);
        params.put("status", status);
        params.put("expireYearStr", expireYearStr);
        params.put("reasonType", reasonType);
        params.put("keyword", keyword);
        RespWrapper<DataPage<ItemrepairExt>> resp = itemrepairService.getItemRepairByPage(admin, params, 1, 1000);    //只导出1000条
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(1001, "系统繁忙!");
        }
        if (resp.getObj().getRecord().size() <= 0) {
            throw new RespException(1001, "没有记录导出!");
        }
        List<ItemrepairExt> list = resp.getObj().getRecord();
        //扩展
        Set<Integer> orgIds = new HashSet<>();
        Set<Long> adminIds = new  HashSet<>();
        Set<Integer> reasionIds = new HashSet<>();
        Set<Integer> areaIds = new HashSet<>();
        Set<Integer> eqpIds = new HashSet<>();
        Set<Integer> supplierIds = new HashSet<>();
        for (ItemrepairExt o : list) {
                orgIds.add(o.getOrgId());
                adminIds.add(o.getAgencyId());
                reasionIds.add(o.getReasonType());
                eqpIds.add(o.getEqpId());
                supplierIds.add(o.getSupplierId());
                areaIds.add(o.getAreaId());
        }
        Map<Integer, Org> orgMap = orgService.getOrgMapInIds(StringUtil.join(orgIds, ",")).getObj();
        Map<Long, Admin> adminMap = adminService.getAdminMapInIds(StringUtil.join(adminIds, ",")).getObj();
        Map<Integer, Brokenreason> reasonMap = brokenreasonService.getBrokenreasonMapInIds(StringUtil.join(reasionIds, ",")).getObj();
        Map<Integer, Area> areaMap = areaService.getAreaMapInIds(StringUtil.join(areaIds, ",")).getObj();
        Map<Integer, Eqp> eqpMap = eqpService.getEqpMapInIds(StringUtil.join(eqpIds, ",")).getObj();
        Map<Integer, Supplier> supplierMap = supplierService.getSupplierMapInIds(StringUtil.join(supplierIds, ",")).getObj();
        for (ItemrepairExt o : list) {
            //b.eqpId,b.areaId,b.supplierId,b.itemsn,b.expireTime,b.status itemStatus
            Org org = orgMap.get(o.getOrgId());
            Admin adminA = adminMap.get(o.getAgencyId());
            Brokenreason reason = reasonMap.get(o.getReasonType());
            Area area = areaMap.get(o.getAreaId());
            Eqp eqp = eqpMap.get(o.getEqpId());
            Supplier supplier = supplierMap.get(o.getSupplierId());
            o.set("orgName", org == null ? "" : org.getName());
            o.set("agencyName", adminA == null ? "" : adminA.getName());
            o.set("reasonName", reason == null ? "" : reason.getName());
            o.set("eqpName", eqp == null ? "" : eqp.getName());
            o.set("eqpCatesn", eqp == null ? "" : eqp.getCatesn());
            o.set("supplierName", supplier == null ? "" : supplier.getName());
            o.set("regionFullName", area == null ? "" : area.getRegionFullName());
            o.set("areaName", area == null ? "" : area.getName());
            o.set("statusNewName", ItemStatus.getName(o.getStatus()));
            o.set("itemStatusName", ItemStatus.getName(o.getItemStatus()));
            o.set("createDate", DateTimeUtil.formatDate(new Date(o.getCreateTime()), "yyyy-MM-dd"));
            long expireTime = o.getExpireTime();
            if (expireTime == 0) {
                o.set("leftTime", "");
            } else {
                o.set("leftTime", expireTime - System.currentTimeMillis() <= 0 ? "已过期" : DateTimeUtil.getSubtractTimeStr(expireTime, System.currentTimeMillis(), 2));
            }
            if (o.getItemflowId() == 0) {
                o.set("followBy", "");
                o.set("followName", "");
                o.set("followPhone", "");
            } else {
                ItemFlow itemflow = itemflowService.getOneItemflow(o.getItemflowId()).getObj();
                o.set("followBy", o.getItemflowId().toString());
                if (itemflow == null) {
                    o.set("followName", "");
                    o.set("followPhone", "");
                } else {
                    User userC = userService.getOneUser(itemflow.getCreateBy()).getObj();
                    o.set("followName", userC == null ? "" : userC.getName());
                    o.set("followPhone", userC == null ? "" : userC.getPhone());
                }
            }
        }
        List<Map<String, Object>> dataset = new ArrayList<Map<String, Object>>();
        System.out.println(list.size());
        for (ItemrepairExt obj : list) {
            dataset.add( BeanUtil.beanToMap(obj));
        }
        //将导出文件保存到本地
        String fileName = DateTimeUtil.formatDate("yyyy-MM-dd_HHmmss") + "(" + admin.getUin() + ").xls";
        String fileDir = PathUtil.getWebRootPath() + UploadConfig.Excel_repair_Dir + DateTimeUtil.formatDate("yyyy-MM-dd") + "/" + fileName;
        String sheetName = "repair";
        POIUtil poiUtil = new POIUtil(fileDir, sheetName);
        String titleColumn[] = {"createDate", "orgName", "agencyName", "itemsn", "eqpName", "eqpCatesn", "areaName",
                "reasonName", "remark", "leftTime", "supplierName", "itemStatusName", "statusNewName", "followName", "followPhone"};
        String titleName[] = {"报修时间", "管辖部门", "所属代理商", "设施编号", "设施名称", "设施型号", "安装地点",
                "报修原因", "备注", "剩余年限", "所属供应商", "设施状态", "跟进状态", "跟进人员", "联系方式"};
        int titleSize[] = {13, 13, 13, 13, 13, 13, 13,
                13, 13, 13, 13, 13, 13, 13, 13};
        poiUtil.Map2Excel(titleColumn, titleName, titleSize, dataset);
        try {
            //设置response, 导出记录
            response.reset();
            response.setContentType("application/x-msdownload");
            response.setHeader("Location", fileName);
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
        return resp;
    }




    /**
     *  获取一条巡检记录详情
    */
    @RequestMapping("/getItemrepairInfo")
    public Object getItemrepairInfo(HttpServletRequest request){

        Integer repairId = NetUtil.getIntParameter(request, "repairId", Integer.MAX_VALUE);	// 网络报修单ID
        if (repairId == Integer.MAX_VALUE) {
            throw new RespException(1001, "网络报修单ID不能为空!");
        }
        RespWrapper<ItemRepair> rRepair = itemrepairService.getOneItemRepair(repairId);
        if (rRepair.getErrCode() != 0 || rRepair.getObj() == null) {
            throw new RespException(rRepair.getErrCode(), rRepair.getErrMsg());
        }
        SuperBean sb = new SuperBean();
        ItemRepair repair = putInfo(rRepair.getObj());
        if (repair.getItemflowId() != 0) {
            RespWrapper<ItemFlow> resp2 = itemflowService.getOneItemflow(repair.getItemflowId());
            if (resp2.getErrCode() == 0) {
                sb.put("itemFlow", repair);
                sb.put("itemFollow", putInfo(resp2.getObj()));
            } else {
                sb.put("itemFlow", repair);
                sb.put("itemFollow", "");
            }
        } else {
            sb.put("itemFlow", repair);
            sb.put("itemFollow", "");
        }
        return  RespWrapper.makeResp(0, "", sb);
    }

    /**
     * 扩展flow, 巡检记录的信息
     *
     * @param flow
     * @return
     */
    private ItemFlow putInfo(ItemFlow flow) {
        Item item = itemService.getOneItem(flow.getItemId()).getObj();
        if (item == null) {
            flow.set("itemsn", "");
            flow.set("eqpName", "");
        } else {
            Eqp eqp = eqpService.getOneEqp(item.getEqpId()).getObj();
            flow.set("itemsn", item.getItemsn());
            flow.set("eqpName", eqp == null ? "" : eqp.getName());
        }
        Brokenreason reason = brokenreasonService.getOneBrokenreason(flow.getReasonType()).getObj();
        flow.set("reasonName", reason == null ? "" : reason.getName());
        return flow;
    }

    /**
     * 扩展repair, 网络报修记录的信息
     *
     * @param reapir
     * @return
     */
    private ItemRepair putInfo(ItemRepair reapir) {
        Item item = itemService.getOneItem(reapir.getItemId()).getObj();
        if (item == null) {
            reapir.set("itemsn", "");
            reapir.set("eqpName", "");
        } else {
            Eqp eqp = eqpService.getOneEqp(item.getEqpId()).getObj();
            reapir.set("itemsn", item.getItemsn());
            reapir.set("eqpName", eqp == null ? "" : eqp.getName());
        }
        Brokenreason reason = brokenreasonService.getOneBrokenreason(reapir.getReasonType()).getObj();
        reapir.set("reasonName", reason == null ? "" : reason.getName());
        reapir.set("statusOld", 1);
        reapir.set("statusNew", 2);
        return reapir;
    }



    /**
     *  获取网络报修单分页列表
    */
    @RequestMapping("/getRepairPage")
    public Object getRepairPage(HttpServletRequest request){
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");

        //获取参数
        int    pageNo        = NetUtil.getIntParameter(request, "pageNo", 1);
        int    pageSize      = NetUtil.getIntParameter(request, "pageSize", 20);
        int    projectId     = NetUtil.getIntParameter(request, "projectId", Integer.MAX_VALUE);	// 项目分类ID
        int    eqpId         = NetUtil.getIntParameter(request, "eqpId", Integer.MAX_VALUE);		    // 器材ID
        int    eqpsortId     = NetUtil.getIntParameter(request, "eqpsortId", Integer.MAX_VALUE);	// 器材分类ID
        int    areaId        = NetUtil.getIntParameter(request, "areaId", Integer.MAX_VALUE);		// 场地ID
        int    status        = NetUtil.getIntParameter(request, "status", Integer.MAX_VALUE);		// 网络报修单状态(0待跟进 1已跟进  维修单的状态)
        String expireYearStr = NetUtil.getStringParameter(request, "expireYearStr", "");	// 到期时间
        int    reasonType    = NetUtil.getIntParameter(request, "reasonType", Integer.MAX_VALUE);	// 报修理由类别
        String keyword       = NetUtil.getStringParameter(request, "keyword", "");			// 器材编码
        int    itemType      = NetUtil.getIntParameter(request, "itemType", Integer.MAX_VALUE);		// 1器材码管理, 2场地码管理

        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
            params.put("projectId",projectId);
            params.put("eqpId",eqpId);
            params.put("eqpsortId",eqpsortId);
            params.put("areaId",areaId);
            params.put("status",status);
            params.put("expireYearStr",expireYearStr);
            params.put("reasonType",reasonType);
            params.put("keyword",keyword);
            params.put("itemType",itemType);
        RespWrapper<DataPage<ItemrepairExt>> resp = itemrepairService.getItemRepairByPage(admin, params, pageNo, pageSize);
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        List<ItemrepairExt> ret = resp.getObj().getRecord();
        //扩展
        Set<Integer> orgIds = new HashSet<>();
        Set<Long> adminIds = new  HashSet<>();
        Set<Integer> reasionIds = new HashSet<>();
        Set<Integer> areaIds = new HashSet<>();
        Set<Integer> eqpIds = new HashSet<>();
        Set<Integer> supplierIds = new HashSet<>();
        for (ItemrepairExt o : ret) {
            orgIds.add(o.getOrgId());
            adminIds.add(o.getAgencyId());
            reasionIds.add(o.getReasonType());
            eqpIds.add(o.getEqpId());
            supplierIds.add(o.getSupplierId());
            areaIds.add(o.getAreaId());
        }
        Map<Integer, Org> orgMap = orgService.getOrgMapInIds(StringUtil.join(orgIds, ",")).getObj();
        Map<Long, Admin> adminMap = adminService.getAdminMapInIds(StringUtil.join(adminIds, ",")).getObj();
        Map<Integer, Brokenreason> reasonMap = brokenreasonService.getBrokenreasonMapInIds(StringUtil.join(reasionIds, ",")).getObj();
        Map<Integer, Area> areaMap = areaService.getAreaMapInIds(StringUtil.join(areaIds, ",")).getObj();
        Map<Integer, Eqp> eqpMap = eqpService.getEqpMapInIds(StringUtil.join(eqpIds, ",")).getObj();
        Map<Integer, Supplier> supplierMap = supplierService.getSupplierMapInIds(StringUtil.join(supplierIds, ",")).getObj();
        for (ItemrepairExt o : ret) {
            //b.eqpId,b.areaId,b.supplierId,b.itemsn,b.expireTime,b.status itemStatus
            Org org = orgMap.get(o.getOrgId());
            Admin adminA = adminMap.get(o.getAgencyId());
            Brokenreason reason = reasonMap.get(o.getReasonType());
            Area area = areaMap.get(o.getAreaId());
            Eqp eqp = eqpMap.get(o.getEqpId());
            Supplier supplier = supplierMap.get(o.getSupplierId());
            o.set("orgName", org == null ? "" : org.getName());
            o.set("agencyName", adminA == null ? "" : adminA.getName());
            o.set("reasonName", reason == null ? "" : reason.getName());
            o.set("eqpName", eqp == null ? "" : eqp.getName());
            o.set("eqpCatesn", eqp == null ? "" : eqp.getCatesn());
            o.set("supplierName", supplier == null ? "" : supplier.getName());
            o.set("regionFullName", area == null ? "" : area.getRegionFullName());
            o.set("areaName", area == null ? "" : area.getName());
            o.set("statusNewName", ItemStatus.getName(o.getStatus()));
            o.set("itemStatusName", ItemStatus.getName(o.getItemStatus()));
            o.set("createDate", DateTimeUtil.formatDate(new Date(o.getCreateTime()), "yyyy-MM-dd"));
            long expireTime = o.getExpireTime();
            if (expireTime == 0) {
                o.set("leftTime", "");
            } else {
                o.set("leftTime", expireTime - System.currentTimeMillis() <= 0 ? "已过期" : DateTimeUtil.getSubtractTimeStr(expireTime, System.currentTimeMillis(), 2));
            }
            if (o.getItemflowId() == 0) {
                o.set("followBy", "");
                o.set("followName", "");
                o.set("followPhone", "");
            } else {
                ItemFlow itemflow = itemflowService.getOneItemflow(o.getItemflowId()).getObj();
                o.set("followBy", o.getItemflowId().toString());
                if (itemflow == null) {
                    o.set("followName", "");
                    o.set("followPhone", "");
                } else {
                    User userC = userService.getOneUser(itemflow.getCreateBy()).getObj();
                    o.set("followName", userC == null ? "" : userC.getName());
                    o.set("followPhone", userC == null ? "" : userC.getPhone());
                }
            }
        }
        //返回结果
       return resp;
    }





}
