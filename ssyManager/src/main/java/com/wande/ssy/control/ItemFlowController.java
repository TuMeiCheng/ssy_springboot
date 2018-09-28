package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.*;
import com.wande.ssy.entity.*;
import com.wande.ssy.enums.ItemStatus;
import com.wande.ssy.enums.Sourcefrom;
import com.wande.ssy.utils.*;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.model.SuperBean;
import com.ynm3k.mvc.webutil.NetUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("/itemflow")
public class ItemFlowController {

    @Reference(interfaceClass=ItemflowService.class)
    private ItemflowService itemflowService;

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


    @Reference(interfaceClass = ItemService.class)
    private ItemService itemService;




    /**
     * 根据搜索条件导出分页列表
    */
    @RequestMapping("/exportItemflow")
    public Object exportItemflow(HttpServletRequest request,
                                 HttpServletResponse response){
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");

        int    projectId     = NetUtil.getIntParameter(request, "projectId", Integer.MAX_VALUE);	// 项目分类ID
        int    eqpId         = NetUtil.getIntParameter(request, "eqpId", Integer.MAX_VALUE);			// 器材ID
        int    eqpsortId     = NetUtil.getIntParameter(request, "eqpsortId", Integer.MAX_VALUE);	// 器材分类ID
        int    areaId        = NetUtil.getIntParameter(request, "areaId", Integer.MAX_VALUE);		// 场地ID
        long   flowBy        = NetUtil.getLongParameter(request, "flowBy", Long.MAX_VALUE);			// 巡检人ID
        long   repairBy      = NetUtil.getLongParameter(request, "repairBy", Long.MAX_VALUE);		// 维修人ID
        int    reasonType    = NetUtil.getIntParameter(request, "reasonType", Integer.MAX_VALUE);	// 报修理由类别
        int    statusNew     = NetUtil.getIntParameter(request, "status", Integer.MAX_VALUE);		// 器材状态,0未安装,1正常,2报修,3报废////巡检后的状态
        String expireYearStr = NetUtil.getStringParameter(request, "expireYearStr", "");	// 到期时间
        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
            params.put("projectId",projectId);
            params.put("eqpId",eqpId);
            params.put("eqpsortId",eqpsortId);
            params.put("areaId",areaId);
            params.put("flowBy",flowBy);
            params.put("repairBy",repairBy);
            params.put("reasonType",reasonType);
            params.put("statusNew",statusNew);
            params.put("expireYearStr",expireYearStr);

        RespWrapper<DataPage<ItemflowExt>> resp = itemflowService.getItemflowByPage(admin, params, 1, 1000);	//只导出1000条
        if (resp.getErrCode() != 0) {
            throw new RespException(1001, "系统繁忙!");
        }
        if (resp.getObj().getRecord().size() <= 0) {
            throw new RespException(1001, "没有记录导出!");
        }
        //扩展数据
        List<ItemflowExt> ret = resp.getObj().getRecord();
        Set<Integer> orgIds = new HashSet<Integer>();
        Set<Long> adminIds = new HashSet<Long>();
        Set<Integer> itemflowIds = new HashSet<Integer>();
        Set<Integer> reasionIds = new HashSet<Integer>();
        Set<Integer> areaIds = new HashSet<Integer>();
        Set<Integer> eqpIds = new HashSet<Integer>();
        Set<Integer> supplierIds = new HashSet<Integer>();
        Set<Long> userIds = new HashSet<Long>();
        for (ItemflowExt o : ret) {
            System.out.println(o.toString());
                orgIds.add(o.getOrgId());
                adminIds.add(o.getAgencyId());
                itemflowIds.add(o.getItemflowId());
                reasionIds.add(o.getReasonType());
                eqpIds.add(o.getEqpId());
                supplierIds.add(o.getSupplierId());
                areaIds.add(o.getAreaId());
                userIds.add(o.getCreateBy());
                userIds.add(o.getRepairBy());
        }
        Map<Integer, Org> orgMap = orgService.getOrgMapInIds(StringUtil.join(orgIds, ",")).getObj();
        Map<Long, Admin> adminMap = adminService.getAdminMapInIds(StringUtil.join(adminIds, ",")).getObj();
        Map<Integer, ItemFlow> itemflowMap = itemflowService.getItemflowMapInIds(StringUtil.join(itemflowIds, ",")).getObj();
        Map<Integer, Brokenreason> reasonMap = brokenreasonService.getBrokenreasonMapInIds(StringUtil.join(reasionIds, ",")).getObj();
        Map<Integer, Area> areaMap = areaService.getAreaMapInIds(StringUtil.join(areaIds, ",")).getObj();
        Map<Integer, Eqp> eqpMap = eqpService.getEqpMapInIds(StringUtil.join(eqpIds, ",")).getObj();
        Map<Integer, Supplier> supplierMap = supplierService.getSupplierMapInIds(StringUtil.join(supplierIds, ",")).getObj();
        Map<Long, User> userMap = userService.getUserMapInIds(StringUtil.join(userIds, ",")).getObj();

        for (ItemflowExt o : ret) {
            Org org = orgMap.get(o.getOrgId());
            Admin adminA = adminMap.get(o.getAgencyId());
            ItemFlow itemflow = itemflowMap.get(o.getItemflowId());
            Brokenreason reason = reasonMap.get(o.getReasonType());
            Area area = areaMap.get(o.getAreaId());
            Eqp eqp = eqpMap.get(o.getEqpId());
            Supplier supplier = supplierMap.get(o.getSupplierId());
            User userC = userMap.get(o.getCreateBy());
            User userR = userMap.get(o.getRepairBy());
            //b.installTime, b.expireTime,b.eqpId,b.supplierId,b.repairBy,b.areaId,b.itemsn,b.eqpsortId
            o.set("orgName", org == null ? "" : org.getName());
            o.set("agencyName", adminA == null ? "" : adminA.getName());
            o.set("reasonName", reason == null ? "" : reason.getName());
            o.set("eqpName", eqp == null ? "" : eqp.getName());
            o.set("eqpCatesn", eqp == null ? "" : eqp.getCatesn());
            o.set("supplierName", supplier == null ? "" : supplier.getName());
            o.set("regionFullName", area == null ? "" : area.getRegionFullName());
            o.set("areaName", area == null ? "" : area.getName());
            o.set("statusOldName", ItemStatus.getName(o.getStatusOld()));
            o.set("statusNewName", ItemStatus.getName(o.getStatusNew()));
            o.set("sourcefromName", Sourcefrom.getName(o.getSourcefrom()));
            o.set("createName", userC == null ? "" : userC.getName());
            o.set("createPhone", userC == null ? "" : userC.getPhone());
            o.set("repairName", userR == null ? "" : userR.getName());
            o.set("repairPhone", userR == null ? "" : userR.getPhone());
            o.set("repairTime", itemflow == null ? "" : itemflow.getCreateTime()+"");
            o.set("repairDate", itemflow == null ? "" : DateTimeUtil.formatDate(new Date(itemflow.getCreateTime()), "yyyy-MM-dd"));
            o.set("createDate", DateTimeUtil.formatDate(new Date(o.getCreateTime()), "yyyy-MM-dd"));
            o.set("installDate", DateTimeUtil.formatDate(new Date(o.getInstallTime()), "yyyy-MM-dd"));
            long expireTime = o.getExpireTime();
            o.set("expireDate", DateTimeUtil.formatDate(new Date(expireTime), "yyyy-MM-dd"));
            if (expireTime == 0) {
                o.set("leftTime", "");
            } else {
                o.set("leftTime", expireTime - System.currentTimeMillis() <= 0 ? "已过期" : DateTimeUtil.getSubtractTimeStr(expireTime, System.currentTimeMillis(), 2));
            }
        }
        List<Map<String, Object>> dataset = new ArrayList<Map<String, Object>>();
        for (ItemflowExt obj : ret) {
            dataset.add( BeanUtil.beanToMap(obj));
        }
        //将导出文件保存到本地
        String fileName = DateTimeUtil.formatDate("yyyy-MM-dd_HHmmss") + "(" + admin.getUin() + ").xls";
        String fileDir = PathUtil.getWebRootPath() + UploadConfig.Excel_flow_Dir + DateTimeUtil.formatDate("yyyy-MM-dd") + "/" + fileName;
        String sheetName = "item";
        POIUtil poiUtil = new POIUtil(fileDir, sheetName);
        String titleColumn[] = {"imgsnap","eqpName","eqpCatesn","supplierName","createDate","createName", "createPhone","installDate","itemsn","leftTime",
                "orgName","areaName","agencyName","statusOldName","statusNewName", "reasonName","remark","repairDate","repairName","repairPhone"};
        String titleName[] = {"巡检照片", "设施名称","设施型号", "所属供应商", "巡检时间", "巡检负责人","联系电话","安装时间","设施编号","设施剩余期限",
                "管辖部门","安装地点","所属代理商","巡检前","巡检后","报修原因","备注","维修时间","维修负责人","联系电话"};

        int titleSize[] = {13, 13, 13, 13, 13, 13, 13, 13, 13, 13,
                13, 13, 13, 13, 13, 13, 13, 13, 13, 13};
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
     * 获取一条巡检记录详情
    */
    @RequestMapping("/getItemflowInfo")
    public Object getItemflowInfo(@RequestParam(value = "itemflowId",defaultValue = "-1") Integer itemflowId){

        if (itemflowId == -1) {
            throw new RespException(1001, "巡检记录ID不能为空!");
        }

        RespWrapper<ItemFlow> resp = itemflowService.getOneItemflow(itemflowId);
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        SuperBean sb = new SuperBean();
        ItemFlow flow = putInfo(resp.getObj());

        if (flow.getFollowId() != 0) {
            RespWrapper<ItemFlow> resp2 = itemflowService.getOneItemflow(flow.getFollowId());
            if (resp2.getErrCode() == 0) {
                sb.put("itemFlow", flow);
                sb.put("itemFollow", putInfo(resp2.getObj()));
            } else {
                sb.put("itemFlow", flow);
                sb.put("itemFollow", "");
            }
        } else if (flow.getStatusNew() == ItemStatus.NORMAL.getValue()) {
            RespWrapper<ItemFlow> resp3 = itemflowService.getOneItemflowByFollowId(flow.getItemflowId());
            if (resp3.getErrCode() == 0) {
                sb.put("itemFlow", putInfo(resp3.getObj()));
                sb.put("itemFollow", flow);
            } else {
                sb.put("itemFlow", flow);
                sb.put("itemFollow", "");
            }
        } else {
            sb.put("itemFlow", flow);
            sb.put("itemFollow", "");
        }
        return  RespWrapper.makeResp(0, "", sb);
    }



    /**
     *  获取巡检记录分页列表
    */
    @RequestMapping("/getItemflowPage")
    public Object getItemflowPage(HttpServletRequest request){

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
        long   flowBy        = NetUtil.getLongParameter(request, "flowBy", Long.MAX_VALUE);			// 巡检人ID
        long   repairBy      = NetUtil.getLongParameter(request, "repairBy", Long.MAX_VALUE);		// 维修人ID
        int    reasonType    = NetUtil.getIntParameter(request, "reasonType", Integer.MAX_VALUE);	// 报修理由类别
        int    statusNew     = NetUtil.getIntParameter(request, "statusNew", Integer.MAX_VALUE);	// 器材状态,0未安装,1正常,2报修,3报废////巡检后的状态
        String expireYearStr = NetUtil.getStringParameter(request, "expireYearStr", "");  // 到期时间
        int    flowType     = NetUtil.getIntParameter(request, "flowType", Integer.MAX_VALUE);		// 1器材码管理, 2产地码管理


        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
            params.put("projectId",projectId);
            params.put("eqpId",eqpId);
            params.put("eqpsortId",eqpsortId);
            params.put("areaId",areaId);
            params.put("flowBy",flowBy);
            params.put("repairBy",repairBy);
            params.put("reasonType",reasonType);
            params.put("statusNew",statusNew);
            params.put("expireYearStr",expireYearStr);
            params.put("flowType",flowType);
        RespWrapper<DataPage<ItemflowExt>> resp = itemflowService.getItemflowByPage(admin, params, pageNo, pageSize);
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        //扩展数据
        List<ItemflowExt> ret = resp.getObj().getRecord();
        Set<Integer> orgIds = new HashSet<Integer>();
        Set<Long> adminIds = new HashSet<Long>();
        Set<Integer> itemflowIds = new HashSet<Integer>();
        Set<Integer> reasionIds = new HashSet<Integer>();
        Set<Integer> areaIds = new HashSet<Integer>();
        Set<Integer> eqpIds = new HashSet<Integer>();
        Set<Integer> supplierIds = new HashSet<Integer>();
        Set<Long> userIds = new HashSet<Long>();
        for (ItemflowExt o : ret) {
                orgIds.add(o.getOrgId());
                adminIds.add(o.getAgencyId());
                itemflowIds.add(o.getFollowId());
                reasionIds.add(o.getReasonType());
                eqpIds.add(o.getEqpId());
                supplierIds.add(o.getSupplierId());
                areaIds.add(o.getAreaId());
                userIds.add(o.getCreateBy());
                userIds.add(o.getRepairBy());
        }
        Map<Integer, Org> orgMap = orgService.getOrgMapInIds(StringUtil.join(orgIds, ",")).getObj();
        Map<Long, Admin> adminMap = adminService.getAdminMapInIds(StringUtil.join(adminIds, ",")).getObj();
        Map<Integer, ItemFlow> itemflowMap = itemflowService.getItemflowMapInIds(StringUtil.join(itemflowIds, ",")).getObj();
        Map<Integer, Brokenreason> reasonMap = brokenreasonService.getBrokenreasonMapInIds(StringUtil.join(reasionIds, ",")).getObj();
        Map<Integer, Area> areaMap = areaService.getAreaMapInIds(StringUtil.join(areaIds, ",")).getObj();
        Map<Integer, Eqp> eqpMap = eqpService.getEqpMapInIds(StringUtil.join(eqpIds, ",")).getObj();
        Map<Integer, Supplier> supplierMap = supplierService.getSupplierMapInIds(StringUtil.join(supplierIds, ",")).getObj();
        Map<Long, User> userMap = userService.getUserMapInIds(StringUtil.join(userIds, ",")).getObj();

        for (ItemflowExt o : ret) {
            Org org = orgMap.get(o.getOrgId());
            Admin adminA = adminMap.get(o.getAgencyId());
            ItemFlow itemflow = itemflowMap.get(o.getFollowId());
            Brokenreason reason = reasonMap.get(o.getReasonType());
            Area area = areaMap.get(o.getAreaId());
            Eqp eqp = eqpMap.get(o.getEqpId());
            Supplier supplier = supplierMap.get(o.getSupplierId());
            User userC = userMap.get(o.getCreateBy());
            User userR = userMap.get(o.getRepairBy());
            //b.installTime, b.expireTime,b.eqpId,b.supplierId,b.repairBy,b.areaId,b.itemsn,b.eqpsortId
            o.set("orgName", org == null ? "" : org.getName());
            o.set("agencyName", adminA == null ? "" : adminA.getName());
            o.set("reasonName", reason == null ? "" : reason.getName());
            o.set("eqpName", eqp == null ? "" : eqp.getName());
            o.set("eqpCatesn", eqp == null ? "" : eqp.getCatesn());
            o.set("supplierName", supplier == null ? "" : supplier.getName());
            o.set("regionFullName", area == null ? "" : area.getRegionFullName());
            o.set("areaName", area == null ? "" : area.getName());
            o.set("statusOldName", ItemStatus.getName(o.getStatusOld()));
            o.set("statusNewName", ItemStatus.getName(o.getStatusNew()));
            o.set("sourcefromName", Sourcefrom.getName(o.getSourcefrom()));
            o.set("createName", userC == null ? "" : userC.getName());
            o.set("createPhone", userC == null ? "" : userC.getPhone());
            o.set("repairName", userR == null ? "" : userR.getName());
            o.set("repairPhone", userR == null ? "" : userR.getPhone());
            o.set("repairTime", itemflow == null ? "" : itemflow.getCreateTime());
            o.set("repairDate", itemflow == null ? "" : DateTimeUtil.formatDate(new Date(itemflow.getCreateTime()), "yyyy-MM-dd"));
            o.set("createDate", DateTimeUtil.formatDate(new Date(o.getCreateTime()), "yyyy-MM-dd"));
            o.set("installDate", DateTimeUtil.formatDate(new Date(o.getInstallTime()), "yyyy-MM-dd"));
            long expireTime = o.getExpireTime();
            o.set("expireDate", DateTimeUtil.formatDate(new Date(expireTime), "yyyy-MM-dd"));
            if (expireTime == 0) {
                o.set("leftTime", "");
            } else {
                o.set("leftTime", expireTime - System.currentTimeMillis() <= 0 ? "已过期" : DateTimeUtil.getSubtractTimeStr(expireTime, System.currentTimeMillis(), 2));
            }
        }

        //返回结果
        return resp;
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
            flow.set("status", 0);
        } else {
            Eqp eqp = eqpService.getOneEqp(item.getEqpId()).getObj();
            flow.set("itemsn", item.getItemsn());
            flow.set("eqpName", eqp == null ? "" : eqp.getName());
            flow.set("status", item.getStatus());
        }
        Brokenreason reason = brokenreasonService.getOneBrokenreason(flow.getReasonType()).getObj();
        flow.set("reasonName", reason == null ? "" : reason.getName());
        return flow;
    }


}
