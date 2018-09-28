package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.*;
import com.wande.ssy.entity.*;
import com.wande.ssy.enums.FlowCheck;
import com.wande.ssy.enums.ItemStatus;
import com.wande.ssy.enums.RepairStatus;
import com.wande.ssy.enums.Sourcefrom;
import com.wande.ssy.utils.StringUtil;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.webutil.NetUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/mobile")
public class MobileController {

    @Reference(interfaceClass=AdminService.class)
    private AdminService adminService;

    @Reference(interfaceClass = AreaService.class)
    private AreaService areaService;

    @Reference(interfaceClass = ItemService.class)
    private ItemService itemService;

    @Reference(interfaceClass = EqpService.class)
    private EqpService eqpService;

    @Reference(interfaceClass = ItemflowService.class)
    private ItemflowService itemflowService;

    @Reference(interfaceClass = AreaflowService.class)
    private AreaflowService areaFlowService;


    @Reference(interfaceClass = ItemrepairService.class)
    private ItemrepairService itemrepairService;

    @Reference(interfaceClass = MsgService.class)
    private MsgService msgService;







    /**
     * 市民扫描模块
     * 根据器场地areaId 获取对应场地信息
     * @param itemsn ( = areaId ) //场地的itemsn 传的就是 areaId
     * 扫描的是场地码 Area
     * 解决场地 itemsn 数据库显示为 ByArea 的场地扫码
     */
    @RequestMapping("/getAreaInfoByItemsn")
    public Object getAreaInfoByItemsn(@RequestParam(value = "itemsn",defaultValue = "") String  itemsn){
        //场地的itemsn 传的就是 areaId
        if (StringUtil.isEmpty(itemsn)) {
            throw new RespException(1001, "场地编号不能为空");
        }
        if(itemsn.length()<5) {  // itemsn传的是areaid
            System.out.println("itemsn传的是areaid  >>  "+Integer.parseInt(itemsn));

        }else {  // itemsn传的是 二维码qrcode
            System.out.println("itemsn传的是 二维码qrcode  >>  "+itemsn);
        }
        //调用远程服务获取场地信息
        RespWrapper<Map<String, Object>> resp = areaService.getAreaDetailsByQrcode(itemsn);
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        } else {
            Map<String, Object> map = resp.getObj();
            return RespWrapper.makeResp(0, "", map);
        }
    }

    /**
     * 市民扫描模块
     * 根据器材编号获取对应信息
     * 扫描的是器材码
     */
    @RequestMapping("/getItemInfoByItemsn")
    public Object getItemInfoByItemsn(@RequestParam(value = "itemsn",defaultValue = "") String  itemsn){

        if (StringUtil.isEmpty(itemsn)) {
            throw new RespException(1001, "器材编号不能为空");
        }
        RespWrapper<Item> resp = itemService.getOneItemByItemsn(itemsn);
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        } else {
            Item item = resp.getObj();
            Eqp eqp = eqpService.getOneEqp(item.getEqpId()).getObj();
            if (eqp == null) {
                throw new RespException(1001, "无法读取对应的器材信息!");
            }
            Area area = areaService.getOneArea(item.getAreaId()).getObj();
            item.set("areaName", area == null ? "" : area.getName());
            item.set("areaId", area == null ? "" : area.getAreaId());
            item.set("eqpName", eqp.getCatesn() + eqp.getName());
            item.set("videoImg", eqp.getVideoImg());
            item.set("video", eqp.getVideo());
            item.set("hasVideo", !eqp.getVideo().equals(""));
            return  RespWrapper.makeResp(0, "", item);
        }
    }



    /**
     * 网页扫描, 网络报修
     *
     */
    @RequestMapping("/handle")
    public Object handle(HttpServletRequest request){

        String itemsn = NetUtil.getStringParameter(request, "itemsn", "");          // 器材编码
        String imgsnap = NetUtil.getStringParameter(request, "imgsnap", "");        // 图片URL
        int reasonType = NetUtil.getIntParameter(request, "reasonType", Integer.MAX_VALUE);  // 报修原因ID
        String remark = NetUtil.getStringParameter(request, "remark", "");          // 备注

        int isArea = NetUtil.getIntParameter(request, "isArea", 0);                 // 是否是场地报修 有代表场地报修，没有传 和 0 没有代表器材报修

        if (StringUtil.isEmpty(imgsnap)) {
            throw new RespException(1001, "请上传报修图片!");
        }
        if (reasonType == Integer.MAX_VALUE) {
            throw new RespException(1001, "请选择报修原因!");
        }

        // 封装网络报修记录实体bean
        ItemRepair repair = new ItemRepair();
        repair.setReasonType(reasonType);
        repair.setImgsnap(imgsnap);
        repair.setRemark(remark);
        repair.setIp(request.getRemoteAddr());
        repair.setStatus(RepairStatus.UNFOLLOW.getValue());
        repair.setCreateTime(System.currentTimeMillis());
        repair.setCreateBy(0L);

        RespWrapper<Boolean> retItem = null;
        // 1：没有 IsArea 传过来，代表器材报修
        // ---------------------------------------------------------
        if (isArea == 0) {
            if (StringUtil.isEmpty(itemsn)) {
                throw new RespException(1001, "器材编码不能为空!");
            }
            RespWrapper<Item> rItem = itemService.getOneItemByItemsn(itemsn);
            if (rItem.getErrCode() != 0 || rItem.getObj() == null) {
                throw new RespException(1001, "很抱歉,找不到该器材信息!");
            }
            Item item = rItem.getObj();
            // 器材已经在报修，防止用户刷新页面导致重复提交
            if (item.getStatus() == ItemStatus.REPAIR.getValue()) {
                throw new RespException(999, "该器材已经在报修中.....");
            }
            RespWrapper<Area> rArea = areaService.getOneArea(item.getAreaId());
            if (rArea.getErrCode() != 0 || rArea.getObj() == null) {
                throw new RespException(1001, "很抱歉,无法读取该器材所在场地的信息!");
            }

            Area area = rArea.getObj();

            // 补充 网络报修记录实体bean
            repair.setAreaId(item.getAreaId());
            repair.setOrgId(item.getOrgId());
            repair.setAgencyId(item.getAgencyId());
            repair.setItemId(item.getItemId());

            // 封装 器材巡检记录 实体bean
            ItemFlow flow = new ItemFlow();
            flow.setOrgId(item.getOrgId());
            flow.setAgencyId(item.getAgencyId());
            flow.setAreaId(item.getAreaId());
            flow.setItemId(item.getItemId());
            flow.setActionName("网络报修");
            flow.setStatusOld(item.getStatus());
            flow.setStatusNew(ItemStatus.REPAIR.getValue());
            flow.setReasonType(reasonType);
            flow.setImgsnap(imgsnap);
            flow.setRemark(remark);
            flow.setSourcefrom(Sourcefrom.CITIZEN.getValue());
            flow.setIp(request.getRemoteAddr());
            flow.setLocationLng(area.getLongitude());
            flow.setLocationLat(area.getLatitude());
            flow.setCheck(FlowCheck.PASS.getValue());
            flow.setCreateTime(System.currentTimeMillis());
            flow.setCreateBy(0L);
            // 增加一条器材巡检记录
            RespWrapper<Boolean> retFlow = itemflowService.addItemflow(flow);
            if (retFlow.getErrCode() != 0) {
                throw new RespException(retFlow.getErrCode(), retFlow.getErrMsg());
            }

            // 更新器材状态(网络报修竟然可以修改器材状态！！！！ ps:什么需求嘛)
            //retItem = itemService.updateItemStatus(null, item.getItemId(), ItemStatus.REPAIR.getValue());

        } else {
            // 2：场地报修-----------------------------------------------------------------------------------
            // 调用远程服务获取场地信息
            RespWrapper<Map<String, Object>> resp = areaService.getAreaDetailsByQrcode(itemsn);

            if (resp.getErrCode() != 0 || resp.getObj() == null) {
                throw new RespException(resp.getErrCode(), resp.getErrMsg());
            }
            Map<String, Object> map = resp.getObj();
            // 获取map中返回的场地详情
            Area area = (Area) map.get("area");

            //防止市民重复报修提交按钮
            if(area.getStatus() == ItemStatus.REPAIR.getValue()) {
                throw new RespException(999, "该场地已经在报修中.....");
            }

            // 补充 网络报修记录实体bean
            repair.setAreaId(area.getAreaId());
            repair.setOrgId(area.getOrgId());
            repair.setAgencyId(area.getAgencyId());

            // 封装 场地巡检记录 实体bean
            AreaFlow areaflow = new AreaFlow();
            areaflow.setAgencyId(area.getAgencyId());
            areaflow.setOrgId(area.getOrgId());
            areaflow.setAreaId(area.getAreaId());
            areaflow.setImgsnap(imgsnap);
            areaflow.setRemark(remark);
            areaflow.setIp(request.getRemoteAddr());
            areaflow.setLocationLng(area.getLongitude());
            ;
            areaflow.setLocationLat(area.getLatitude());
            areaflow.setCheck(FlowCheck.PASS.getValue());
            areaflow.setCreateTime(System.currentTimeMillis());
            areaflow.setCreateBy(0);
            // 增加一条场地巡检记录
            retItem = areaFlowService.addItemflow(areaflow);
            if (retItem.getErrCode() != 0) {
                throw new RespException(retItem.getErrCode(), retItem.getErrMsg());
            }

            // 更新场地状态 (垃圾需求，市民保修怎么可以更新场地状态呢)
            //retItem = areaService.updateAreaState(null, area.getAreaId(), ItemStatus.REPAIR.getValue());

        }

        // 增加一条网络报修记录
        RespWrapper<Boolean> retRepair = itemrepairService.addItemrepair(repair);
        if (retRepair.getErrCode() != 0) {
            throw new RespException(retRepair.getErrCode(), retRepair.getErrMsg());
        }

        // 添加消息提醒
        this.addMsg(itemsn + "【网络报修】");

        //TODO 由于市民保修不再更改器材和场地状态了，所有返回null 了 // 返回执行结果
        return retItem;
    }


    private boolean addMsg(String content) {
        Msg msg = new Msg();
        msg.setMtype("网络报修");
        msg.setContent(content);
        msg.setUrl("http://www.hwdeal.com/flow/repair.html");
        msg.setCreateTime(System.currentTimeMillis());
        RespWrapper<Boolean> resp = msgService.addMsg(msg);
        return resp.getObj();
    }

}
