package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Page;
import com.wande.ssy.dao.AreaDao;
import com.wande.ssy.dao.EqpSortDao;
import com.wande.ssy.dao.ItemDao;
import com.wande.ssy.dao.OrgDao;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Item;
import com.wande.ssy.utils.DataFilter;
import com.wande.ssy.utils.DateTimeUtil;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.utils.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class ItemDaoImpl implements ItemDao {

    @Autowired
    private DataFilter dataFilter;

    @Autowired
    private EqpSortDao eqpSortDao ;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private AreaDao areaDao ;







    /**
     * 根据当前登录角色获取eqp_item列表
     * @param admin
     * @return
     */
    @Override
    public List<Item> getItemList(Admin admin) {
        String sql = "select * from eqp_item where 1=1";
        sql += dataFilter.getFilter(admin);
        System.out.println(sql);
        try {
            Item item = new Item();
            List<Item> item_list=  item.find(sql);
            return item_list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Item>();
    }


    /**
     * 分页多条件查询
     * @param admin
     * @param params
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public DataPage getItemByPage(Admin admin, Map params, Integer pageNo, Integer pageSize) {
        //取出查询条件参数
        Item item = (Item)params.get("Item");
        Integer  projectId =  (Integer) params.get("projectId");   // 项目分类ID
        Integer regionId = (Integer) params.get("regionId");    // 地区id
        String  installYear = (String )params.get("installYear");  //安装时间
        String expireYearStr = (String )params.get("expireYearStr");  //到期时间
        //查询数据
        String sql_select = "select * ";
        String sql = "from eqp_item where isdel=0";
        if (item.getEqpsortId() != null) {
            sql += " and eqpsortId=" + item.getEqpsortId();
        } else if (projectId != -1){
            sql += " and eqpsortId in (" + StringUtil.encodeSQL(eqpSortDao.getEqpsortIdsByPid(projectId)) + ")";
        }
        if (item.getOrgId() != null) {
            sql += " and orgId in(" + orgDao.getOrgIdsByPid(item.getOrgId()) + ")";
        }
        if (item.getAreaId() != null) {
            sql += " and areaId=" + item.getAreaId();
        }
        if (item.getEqpId() != null) {
            sql += " and eqpId=" + item.getEqpId();
        }
        if (item.getSupplierId() != null) {
            sql += " and supplierId=" + item.getSupplierId();
        }
        if (item.getProvideWay() != null) {
            sql += " and provideWay=" + item.getProvideWay();
        }
        if (item.getFlowBy() !=null) {
            sql += " and flowBy=" + item.getFlowBy();
        }
        if (item.getRepairBy() !=null) {
            sql += " and repairBy=" + item.getRepairBy();
        }
        if (item.getStatus() != null) {
            sql += " and status=" + item.getStatus();
        }
        if (StringUtil.isNotEmpty(installYear)) {
            sql += " and FROM_UNIXTIME(installTime/1000,'%Y') = '" + StringUtil.encodeSQL(installYear) +"'";
        }
        if (item.getItemType() != null) {
            sql += " and itemType=" + item.getItemType();
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
        if (item.getCreateBy() != null) {
            sql += " and createBy=" + item.getCreateBy();
        }
        if (regionId != -1) {
            sql += " and areaId IN ("+ areaDao.getAreaListByRegionId(regionId) +") and areaId !=0";
        }
        sql += dataFilter.getFilter(admin);
        sql += " order by createTime desc";
        System.out.println("sql:"+sql_select+sql);

        Page<Item> areaPage = item.paginate(pageNo,pageSize,sql_select,sql);
        System.out.println("pageSize:"+areaPage.getList().size());

        return new DataPage<Item>(areaPage.getList(), areaPage.getTotalRow(), pageNo, pageSize);
    }

    @Override
    public Item getOneItem(int itemId) {
        return  new Item().findById(itemId);
    }

    /**
     * 根据器材 二维码编号 获取一条数据
     *
     * @param itemsn
     * @return
     */
    public Item getOneItemByItemsn(String itemsn){
        String sql = "select * from eqp_item where itemsn='" + StringUtil.encodeSQL(itemsn) + "'";
           Item obj = new Item().findFirst(sql);
            if (obj != null ) {
                return obj;
            }
        return null;
    }

    @Override
    public List<Item> getItemsByAreaId(int areaId) {
        //查询数据
        String sql = "select * from eqp_item where isdel=0";
        sql += " and areaId=" + areaId;
        System.out.println("sql:.."+sql);
        List<Item> items = new Item().find(sql);
        return items;
    }


}
