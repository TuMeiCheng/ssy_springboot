package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wande.ssy.dao.EqpSortDao;
import com.wande.ssy.dao.ItemFlowDao;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.ItemFlow;
import com.wande.ssy.entity.ItemflowExt;
import com.wande.ssy.utils.DataFilter;
import com.wande.ssy.utils.DateTimeUtil;
import com.wande.ssy.utils.StringUtil;
import com.ynm3k.mvc.model.DataPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;

@Repository
public class ItemFlowDaoImpl implements ItemFlowDao {

    @Autowired
    private EqpSortDao eqpSortDao;

    @Autowired
    private DataFilter dataFilter ;





    @Override
    public ItemFlow getOneItemflow(int itemflowId) {
           return  new ItemFlow().findById(itemflowId);
    }

    @Override
    public DataPage<ItemflowExt> getItemflowByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize) {

        List<ItemflowExt> list = new ArrayList<ItemflowExt>();

        //解析参数
        int projectId = params.get("projectId") == null ? Integer.MAX_VALUE :(Integer)params.get("projectId");  // 项目分类ID
        int eqpId =  params.get("eqpId") == null ? Integer.MAX_VALUE :(Integer)params.get("eqpId");              // 器材ID
        int eqpsortId =  params.get("eqpsortId") == null ? Integer.MAX_VALUE :(Integer)params.get("eqpsortId"); // 器材分类ID
        int areaId =  params.get("areaId") == null ? Integer.MAX_VALUE :(Integer)params.get("areaId");           // 场地ID
        long flowBy =  params.get("flowBy") == null ? Long.MAX_VALUE :(Long)params.get("flowBy");                // 巡检人ID
        long repairBy =  params.get("repairBy") == null ? Long.MAX_VALUE :(Long)params.get("repairBy");         //维修人ID
        int reasonType =  params.get("reasonType") == null ? Integer.MAX_VALUE :(Integer)params.get("reasonType");//报修理由类别
        int statusNew =  params.get("statusNew") == null ? Integer.MAX_VALUE :(Integer)params.get("statusNew"); // 巡检后的状态
        String expireYearStr = params.get("expireYearStr") == null ? "" :(String)params.get("expireYearStr");  // 到期时间
        int flowType =  params.get("projectId") == null ? Integer.MAX_VALUE :(Integer)params.get("projectId");  // 1器材码管理, 2产地码管理


        //查询数据
        String sql_select = "select a.*,b.installTime, b.expireTime,b.eqpId,b.supplierId,b.repairBy,b.areaId,b.itemsn,b.eqpsortId,b.status";
        String sql = " from eqp_itemflow a join eqp_item b on a.itemId = b.itemId where 1=1";
        if (eqpsortId != Integer.MAX_VALUE) {
            sql += " and b.eqpsortId=" + eqpsortId;
        } else if (projectId != Integer.MAX_VALUE){
            sql += " and b.eqpsortId in (" + StringUtil.encodeSQL(eqpSortDao.getEqpsortIdsByPid(projectId)) + ")";
        }
        if (eqpId != Integer.MAX_VALUE) {
            sql += " and b.eqpId=" + eqpId;
        }
        if (areaId != Integer.MAX_VALUE) {
            sql += " and b.areaId=" + areaId;
        }
        if (flowBy != Long.MAX_VALUE) {	//负责人ID
            sql += " and b.flowBy=" + flowBy;
        }
        if (repairBy != Long.MAX_VALUE) {	//维修负责人ID
            sql += " and b.repairBy=" + repairBy;
        }
        if (reasonType != Integer.MAX_VALUE) {	//报修原因ID
            sql += " and a.reasonType=" + reasonType;
        }
        if (statusNew != Integer.MAX_VALUE) {	//状态ID
            sql += " and a.statusNew=" + statusNew;
        }
        if (flowType != Integer.MAX_VALUE) {	// 管理方式
            sql += " and a.flowType=" + flowType;
        }
        //剩余年限
        if (StringUtil.isNotEmpty(expireYearStr)) {
            Date currentDate = new Date();
            int startMonth = Integer.parseInt(expireYearStr.substring(0, expireYearStr.indexOf("-")));
            int endMonth = Integer.parseInt(expireYearStr.substring(expireYearStr.indexOf("-")+1, expireYearStr.length()));
            long startTime = DateTimeUtil.addMonths2Time(currentDate, startMonth);
            long endTime = DateTimeUtil.addMonths2Time(currentDate, endMonth);
            sql += " and ( expireTime>" + startTime + " and expireTime <=" + endTime +")";
        }
        sql += dataFilter.getFilter_A(admin);
        sql += " order by a.createTime desc";
        System.out.println("sql:  >>  "+sql_select+sql);
        try {
            Page<Record> pageRecord = Db.paginate(pageNo,pageSize,sql_select,sql);
            List<Record> recordList =pageRecord.getList();
            Iterator<Record> iterator = recordList.iterator();
            while (iterator.hasNext()) {
                ItemflowExt obj = parseToExt(iterator.next());
                list.add(obj);
            }
            return new DataPage<ItemflowExt>(list, pageRecord.getTotalRow(), pageNo, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new DataPage<ItemflowExt>(list, 0, pageNo, pageSize);

    }

    /**
     * 根据itemflowIds 获取Itemflow列表, 用于优化查询数据, 少查SQL
     * @param itemflowIds
     * @return
     */
    @Override
    public Map<Integer, ItemFlow> getItemflowMapInIds(String itemflowIds) {

        itemflowIds = StringUtil.isEmpty(itemflowIds) ? "''" : itemflowIds;
        Map<Integer, ItemFlow> list = new HashMap<Integer, ItemFlow>();
        //查询数据
        String sql="select * from eqp_itemflow where itemflowId in(" + itemflowIds + ")";
        try {
            List<Record> list2 = Db.find(sql);
            Iterator<Record> iterator = list2.iterator();
            while (iterator.hasNext()) {
                ItemFlow obj = parseToObj(iterator.next());
                list.put(obj.getItemflowId(), obj);
            }
            System.out.println("sql:"+sql);
            System.out.println("list大小："+list.size());
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HashMap<Integer, ItemFlow>();

    }


    /**
     * 根据followId获取一条数据
     *
     * @param followId
     * @return
     */
    @Override
    public ItemFlow getOneItemflowByFollowId(int followId) {
        String sql = "select * from eqp_itemflow where followId=" + followId;
        try {
           Record rs = Db.findFirst(sql);
            ItemFlow obj = parseToObj(rs);
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    /* 增加一条巡检记录
     * @param: [obj]
     * @return: boolean */
    @Override
    public boolean insert(ItemFlow obj) {
        return obj.save();
    }


    /**
     * DB结果集转实体类
     * @return
     * @throws SQLException
     */
    public ItemFlow parseToObj(Record rs) throws SQLException{
        ItemFlow obj = new ItemFlow();
        obj.setItemflowId(rs.getInt("itemflowId"));
        obj.setOrgId(rs.getInt("orgId"));
        obj.setAgencyId(rs.getLong("agencyId"));
        obj.setAreaId(rs.getInt("areaId"));
        obj.setItemId(rs.getInt("itemId"));
        obj.setActionName(rs.getStr("actionName"));
        obj.setStatusOld(rs.getInt("statusOld"));
        obj.setStatusNew(rs.getInt("statusNew"));
        obj.setFollowId(rs.getInt("followId"));
        obj.setReasonType(rs.getInt("reasonType"));
        obj.setImgsnap(rs.getStr("imgsnap"));
        obj.setRemark(rs.getStr("remark"));
        obj.setSourcefrom(rs.getInt("sourcefrom"));
        obj.setIp(rs.getStr("ip"));
        obj.setLocationLng(rs.getDouble("locationLng"));
        obj.setLocationLat(rs.getDouble("locationLat"));
        obj.setCheck(rs.getInt("check"));
        obj.setCreateTime(rs.getLong("createTime"));
        obj.setCreateBy(rs.getLong("createBy"));
        obj.setModifyTime(rs.getLong("modifyTime"));
        obj.setModifyBy(rs.getLong("modifyBy"));
        obj.setFlowType(rs.getInt("flowType"));
        return obj;
    }


    /**
     * DB转结果集, left join item扩展信息填充到这个Bean
     * @param rs
     * @return
     * @throws SQLException
     */
    public ItemflowExt parseToExt(Record rs) throws SQLException{
        ItemflowExt obj = new ItemflowExt();
        obj.setItemflowId(rs.getInt("itemflowId"));
        obj.setOrgId(rs.getInt("orgId"));
        obj.setAgencyId(rs.getLong("agencyId"));
        obj.setAreaId(rs.getInt("areaId"));
        obj.setItemId(rs.getInt("itemId"));
        obj.setActionName(rs.getStr("actionName"));
        obj.setStatusOld(rs.getInt("statusOld"));
        obj.setStatusNew(rs.getInt("statusNew"));
        obj.setFollowId(rs.getInt("followId"));
        obj.setReasonType(rs.getInt("reasonType"));
        obj.setImgsnap(rs.getStr("imgsnap"));
        obj.setRemark(rs.getStr("remark"));
        obj.setSourcefrom(rs.getInt("sourcefrom"));
        obj.setIp(rs.getStr("ip"));
        obj.setLocationLng(rs.getDouble("locationLng"));
        obj.setLocationLat(rs.getDouble("locationLat"));
        obj.setCheck(rs.getInt("check"));
        obj.setCreateTime(rs.getLong("createTime"));
        obj.setCreateBy(rs.getLong("createBy"));
        obj.setModifyTime(rs.getLong("modifyTime"));
        obj.setModifyBy(rs.getLong("modifyBy"));
        //item
        obj.setStatus(rs.getInt("status"));
        obj.setInstallTime(rs.getLong("installTime"));
        obj.setExpireTime(rs.getLong("expireTime"));
        obj.setEqpId(rs.getInt("eqpId"));
        obj.setSupplierId(rs.getInt("supplierId"));
        obj.setRepairBy(rs.getLong("repairBy"));
        obj.setItemsn(rs.getStr("itemsn"));
        obj.setEqpsortId(rs.getInt("eqpsortId"));
        return obj;
    }
}
