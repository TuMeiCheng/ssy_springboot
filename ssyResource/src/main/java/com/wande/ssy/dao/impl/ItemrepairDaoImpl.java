package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wande.ssy.dao.EqpSortDao;
import com.wande.ssy.dao.ItemrepairDao;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.ItemRepair;
import com.wande.ssy.entity.ItemrepairExt;
import com.wande.ssy.utils.DataFilter;
import com.wande.ssy.utils.DateTimeUtil;
import com.wande.ssy.utils.StringUtil;
import com.ynm3k.mvc.model.DataPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class ItemrepairDaoImpl implements ItemrepairDao {

    @Autowired
    private EqpSortDao eqpSortDao;

    @Autowired
    private DataFilter dataFilter;





    /**
     * 分页多条件查询
     * @param params
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public DataPage<ItemrepairExt> getItemrepairByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize) {
        //定义参数
        int projectId = (int)params.get("projectId");	// 项目分类ID
        int eqpId = (int)params.get("eqpId");		    // 器材ID
        int eqpsortId = (int)params.get("eqpsortId");	// 器材分类ID
        int areaId = (int)params.get("areaId");		    // 场地ID
        int status = (int)params.get("status");		    // 巡检后的状态
        String expireYearStr = (String) params.get("expireYearStr");	// 到期时间
        int reasonType = (int)params.get("reasonType");     // 报修理由类别
        String keyword =(String ) params.get("keyword");    //关键字(器材编号)
        Integer itemType = (Integer)params.get("itemType") == null ? Integer.MAX_VALUE : (Integer)params.get("itemType");// 1器材码管理, 2产地码管理
        //查询数据
        String sql_select = "select b.`status` itemStatus, a.status repairStatus, b.eqpId, b.supplierId, b.itemsn, b.expireTime, a.* ";
        String sql = " from eqp_itemrepair a join eqp_item b on a.itemId = b.itemId where 1=1";
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
        if (status != Integer.MAX_VALUE) {	//状态ID
            sql += " and a.status=" + status;
        }
        if (itemType != Integer.MAX_VALUE) {	//二维码类型 （区分场地码和二维码）ID
            sql += " and b.itemType=" + itemType;
        }
        //剩余年限
        if (StringUtil.isNotEmpty(expireYearStr)) {
            Date currentDate = new Date();
            int startMonth = Integer.parseInt(expireYearStr.substring(0, expireYearStr.indexOf("-")));
            int endMonth = Integer.parseInt(expireYearStr.substring(expireYearStr.indexOf("-")+1, expireYearStr.length()));
            long startTime = DateTimeUtil.addMonths2Time(currentDate, startMonth);
            long endTime = DateTimeUtil.addMonths2Time(currentDate, endMonth);
            sql += " and ( b.expireTime>" + startTime + " and b.expireTime <=" + endTime +")";
        }
        if (reasonType != Integer.MAX_VALUE) {	//报修原因ID
            sql += " and a.reasonType=" + reasonType;
        }
        if (StringUtil.isNotEmpty(keyword)) {
            sql += " and b.itemsn like '%"+ StringUtil.encodeSQL(keyword) + "%'";
        }
        sql += dataFilter.getFilter_A(admin);
        sql += " order by a.createTime desc ";
        System.out.println("sql:  >>  "+sql_select+sql);

        Page<Record>  page = Db.paginate(pageNo,pageSize,sql_select,sql);
        Record record = page.getList().get(0);
        System.out.println(record.toString());

        List<ItemrepairExt> list = new ArrayList<ItemrepairExt>();
        for (Record re : page.getList()) {
            ItemrepairExt itemrepairExt = this.parseToExt(re);
            list.add(itemrepairExt);
        }
        return new DataPage<ItemrepairExt>(list, page.getTotalRow(), pageNo, pageSize);
    }

    @Override
    public ItemRepair getOneItemrepair(int repairId) {
        return new ItemRepair().findById(repairId);
    }

    /* 增加网络报修记录
     * @param: [obj]
     * @return: boolean */
    @Override
    public boolean insert(ItemRepair obj) {
        return obj.save();
    }


    /**
     * DB结果集转实体类(扩展Item信息)
     * @return
     * @throws SQLException
     */
    public ItemrepairExt parseToExt(Record rs) {
        ItemrepairExt obj = new ItemrepairExt();
        obj.setRepairId(rs.getInt("repairId"));
        obj.setOrgId(rs.getInt("orgId"));
        obj.setAgencyId(rs.getLong("agencyId"));
        obj.setAreaId(rs.getInt("areaId"));
        obj.setItemId(rs.getInt("itemId"));
        obj.setItemflowId(rs.getInt("itemflowId"));
        obj.setReasonType(rs.getInt("reasonType"));
        obj.setImgsnap(rs.getStr("imgsnap"));
        obj.setRemark(rs.getStr("remark"));
        obj.setAcceptResult(rs.getStr("acceptResult"));
        obj.setIp(rs.getStr("ip"));
        obj.setStatus(rs.getInt("repairStatus"));
        obj.setCreateTime(rs.getLong("createTime"));
        obj.setCreateBy(rs.getLong("createBy"));
        obj.setModifyTime(rs.getLong("modifyTime"));
        obj.setModifyBy(rs.getLong("modifyBy"));
        //item
        obj.setEqpId(rs.getInt("eqpId"));
        obj.setSupplierId(rs.getInt("supplierId"));
        obj.setItemsn(rs.getStr("itemsn"));
        obj.setExpireTime(rs.getLong("expireTime"));
        obj.setItemStatus(rs.getInt("itemStatus"));
        return obj;
    }
}
