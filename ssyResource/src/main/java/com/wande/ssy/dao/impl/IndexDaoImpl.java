package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wande.ssy.dao.EqpSortDao;
import com.wande.ssy.dao.IndexDao;
import com.wande.ssy.dao.RegionDao;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.utils.DataFilter;
import com.wande.ssy.utils.StringUtil;
import com.ynm3k.mvc.model.SuperBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class IndexDaoImpl implements IndexDao {

    @Autowired
    private EqpSortDao eqpSortDao;

    @Autowired
    private RegionDao regionDao;

    @Autowired
    private DataFilter dataFilter ;



    @Override
    public SuperBean getAreaItemCount(Admin admin, Map<String, Object> params) {
        //定义参数
        int parentId = 0;	// 场地ID筛选
        int eqpsortId = 0;	// 器材分类ID
        String areaIds = "";	// 智慧公园系统, 公园关联的场地列表,格式为(1,2,3), 与admin做判断, 如果admin为null, 那么就使用这个

        //解析参数
        if (params != null) {
            parentId = StringUtil.convertInt(params.get("parentId").toString(), 0);
            eqpsortId = StringUtil.convertInt(params.get("eqpsortId").toString(), 0);
            if (params.containsKey("areaIds")) {
                areaIds = params.get("areaIds").toString();
            }
        }
        //拼接SQL语句
        String sql = "SELECT COUNT(DISTINCT a.areaId) areaCount, COUNT(b.itemId) itemCount FROM eqp_area a left join eqp_item b on a.areaId=b.areaId where b.isdel=0";
        if (parentId != 0) {
            sql += " and a.regionId in (" + StringUtil.encodeSQL(regionDao.getRegionIdsByPid(parentId)) + ")";
        }
        if (eqpsortId != 0) {
            sql += " and b.eqpsortId in (" + StringUtil.encodeSQL(eqpSortDao.getEqpsortIdsByPid(eqpsortId)) + ")";
        }
        /** 此处为了暴露给智慧公园展示数据, 而在2017-3-20版本中, 智慧公园与器材面向对象不一样, 两边的用户不同, 所以暂且, 在智慧公园那边进行关联场地ID, 提供根据场地ID的查询*/
        if (admin != null) {
            sql += dataFilter.getFilter_A(admin);
        } else {
            if (!("all".equals(areaIds))) {
                areaIds = StringUtil.isEmpty(areaIds) ? "''" : areaIds;
                sql += " and a.areaId in (" + areaIds + ")";
            }
        }
        SuperBean sb = new SuperBean();
        try {
            System.out.println("sql: >> "+sql);
            List<Record> list = Db.find(sql);
            if (list != null && list.size() > 0){
                Record record = list.get(0);
                System.out.println(record.toString());
                sb.put("areaCount",record.getLong("areaCount"));
                sb.put("itemCount", record.getLong("itemCount"));
            }
            return sb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.put("areaCount", 0L);
        sb.put("itemCount", 0L);
        return sb;
    }

    /**
     * 五、设施安全警示接口
     *
     * @param admin
     * @param params
     * @return
     */
    @Override
    public List<SuperBean> getEqpSafeChart(Admin admin, Map<String, Object> params) {

        List<SuperBean> rets = new ArrayList<SuperBean>();

        //定义参数
        Integer parentId = 0;	// 场地ID筛选
        Integer eqpsortId = 0;	// 器材分类ID
        String areaIds = "";// 智慧公园系统, 公园关联的场地列表,格式为(1,2,3), 与admin做判断, 如果admin为null, 那么就使用这个

        //解析参数
        if (params != null) {
            parentId = (Integer) params.get("parentId");	// 场地ID筛选
            eqpsortId = (Integer) params.get("eqpsortId");	// 器材分类ID
            if (params.containsKey("areaIds")) {
                areaIds = params.get("areaIds").toString();
            }
        }
        //拼接SQL语句
        String sql = "SELECT FROM_UNIXTIME(a.expireTime/1000,'%Y-%m') time,COUNT(a.itemId) num FROM eqp_item a left join eqp_area b on a.areaId=b.areaId where a.isdel=0";
        if (parentId != 0) {
            sql += " and b.regionId in (" + StringUtil.encodeSQL(regionDao.getRegionIdsByPid(parentId)) + ")";
        }
        if (eqpsortId != 0) {
            sql += " and a.eqpsortId in (" + StringUtil.encodeSQL(regionDao.getEqpsortIdsByPid(eqpsortId)) + ")";
        }
        /** 此处为了暴露给智慧公园展示数据, 而在2017-3-20版本中, 智慧公园与器材面向对象不一样, 两边的用户不同, 所以暂且, 在智慧公园那边进行关联场地ID, 提供根据场地ID的查询*/
        if (admin != null) {
            sql += dataFilter.getFilter_A(admin);
        } else {
            if (!("all".equals(areaIds))) {
                areaIds = StringUtil.isEmpty(areaIds) ? "''" : areaIds;
                sql += " and a.areaId in (" + areaIds + ")";
            }
        }
        sql += "  GROUP BY time";
        System.out.println("slq:  "+sql);
        try {
            List<Record> rs = Db.find(sql);
            for (Record r : rs) {
                System.out.println(r.toString());
                SuperBean sb = new SuperBean();
                sb.put("time", r.get("time"));	            //过期时间
                sb.put("num", r.getLong("num"));	//器材数量
                rets.add(sb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rets;
    }


    /**
     * 四、设施状态分布
     *
     * @param admin
     * @param params
     * @return
     */
    @Override
    public List<SuperBean> getEqpStatusChart(Admin admin, Map<String, Object> params) {

        List<SuperBean> rets = new ArrayList<SuperBean>();

        //定义参数
        int parentId = 0;	// 场地ID筛选
        int eqpsortId = 0;	// 器材分类ID
        String areaIds = "";	// 智慧公园系统, 公园关联的场地列表,格式为(1,2,3), 与admin做判断, 如果admin为null, 那么就使用这个

        //解析参数
        if (params != null) {
            if (params.containsKey("parentId")) {
                parentId = StringUtil.convertInt(params.get("parentId").toString(), 0);
            }
            if (params.containsKey("eqpsortId")) {
                eqpsortId = StringUtil.convertInt(params.get("eqpsortId").toString(), 0);
            }
            if (params.containsKey("areaIds")) {
                areaIds = params.get("areaIds").toString();
            }
        }
        //拼接SQL语句
        String sql = "select a.`status`, COUNT(a.itemId) num from eqp_item a left join eqp_area b on a.areaId=b.areaId where a.isdel=0";
        if (parentId != 0) {
            sql += " and b.regionId in (" + StringUtil.encodeSQL(regionDao.getRegionIdsByPid(parentId)) + ")";
        }
        if (eqpsortId != 0) {
            sql += " and a.eqpsortId in (" + StringUtil.encodeSQL(eqpSortDao.getEqpsortIdsByPid(eqpsortId)) + ")";
        }
        /** 此处为了暴露给智慧公园展示数据, 而在2017-3-20版本中, 智慧公园与器材面向对象不一样, 两边的用户不同, 所以暂且, 在智慧公园那边进行关联场地ID, 提供根据场地ID的查询*/
        if (admin != null) {
            sql += dataFilter.getFilter_A(admin);
        } else {
            if (!("all".equals(areaIds))) {
                areaIds = StringUtil.isEmpty(areaIds) ? "''" : areaIds;
                sql += " and a.areaId in (" + areaIds + ")";
            }
        }
        sql +=" GROUP BY a.`status`";
        try {
            System.out.println("sql: >> "+sql);
            List<Record> recordList = Db.find(sql);
            for (Record record : recordList) {
                SuperBean sb = new SuperBean();
                sb.put("status", record.getInt("status"));	//状态
                sb.put("value", record.getLong("num"));		//器材数量
                rets.add(sb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rets;
    }


    /**
     * 三、设备安装年份
     *
     * @param admin
     * @param params
     * @return
     */
    @Override
    public List<SuperBean> getEqpTimeChart(Admin admin, Map<String, Object> params) {

        List<SuperBean> rets = new ArrayList<SuperBean>();

        //定义参数
        int parentId = 0;	// 场地ID筛选
        int eqpsortId = 0;	// 器材分类ID
        String areaIds = "";	// 智慧公园系统, 公园关联的场地列表,格式为(1,2,3), 与admin做判断, 如果admin为null, 那么就使用这个

        //解析参数
        if (params != null) {
            if (params.containsKey("parentId")) {
                parentId = StringUtil.convertInt(params.get("parentId").toString(), 0);
            }
            if (params.containsKey("eqpsortId")) {
                eqpsortId = StringUtil.convertInt(params.get("eqpsortId").toString(), 0);
            }
            if (params.containsKey("areaIds")) {
                areaIds = params.get("areaIds").toString();
            }
        }
        //拼接SQL语句
        String sql = "SELECT FROM_UNIXTIME(a.installTime/1000,'%Y') time,COUNT(a.itemId) num FROM eqp_item a left join eqp_area b on a.areaId=b.areaId where a.isdel=0";
        if (parentId != 0) {
            sql += " and b.regionId in (" + StringUtil.encodeSQL(regionDao.getRegionIdsByPid(parentId)) + ")";
        }
        if (eqpsortId != 0) {
            sql += " and a.eqpsortId in (" + StringUtil.encodeSQL(eqpSortDao.getEqpsortIdsByPid(eqpsortId)) + ")";
        }
        /** 此处为了暴露给智慧公园展示数据, 而在2017-3-20版本中, 智慧公园与器材面向对象不一样, 两边的用户不同, 所以暂且, 在智慧公园那边进行关联场地ID, 提供根据场地ID的查询*/
        if (admin != null) {
            sql += dataFilter.getFilter_A(admin);
        } else {
            if (!("all".equals(areaIds))) {
                areaIds = StringUtil.isEmpty(areaIds) ? "''" : areaIds;
                sql += " and a.areaId in (" + areaIds + ")";
            }
        }
        sql += " GROUP BY time";
        try {
            System.out.println("sql: >> "+sql);
           List<Record> recordList = Db.find(sql);
            for (Record record : recordList) {
                SuperBean sb = new SuperBean();
                sb.put("name", record.getStr("time"));	//名称
                sb.put("value", record.getLong("num"));	//器材数量
                rets.add(sb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rets;
    }


    /**
     * 一、安装地点分布
     *
     * @param admin
     * @param params
     * @return
     */
    @Override
    public Object getInstallAreaChart(Admin admin, Map<String, Object> params) {

        List<SuperBean> rets = new ArrayList<SuperBean>();

        //定义参数
        int parentId = 0;	// 场地ID筛选
        int eqpsortId = 0;	// 器材分类ID
        String areaIds = "";	// 智慧公园系统, 公园关联的场地列表,格式为(1,2,3), 与admin做判断, 如果admin为null, 那么就使用这个

        //解析参数
        if (params != null) {
            if (params.containsKey("parentId")) {
                parentId = StringUtil.convertInt(params.get("parentId").toString(), 0);
            }
            if (params.containsKey("eqpsortId")) {
                eqpsortId = StringUtil.convertInt(params.get("eqpsortId").toString(), 0);
            }
            if (params.containsKey("areaIds")) {
                areaIds = params.get("areaIds").toString();
            }
        }

        String sql = "select"
                +" a.areaId,"
                +" a.`name`,"
                +" a.areaType,"
                +" a.longitude,"
                +" a.latitude,"
                +" (select COUNT(b.areaId) from eqp_item b where a.areaId = b.areaId and b.isdel=0) as value,"
                +" (select COUNT(b.areaId) from eqp_item b where a.areaId = b.areaId and b.`status` = 0 and b.isdel=0) as count0,"
                +" (select COUNT(b.areaId) from eqp_item b where a.areaId = b.areaId and b.`status` = 1 and b.isdel=0) as count1,"
                +" (select COUNT(b.areaId) from eqp_item b where a.areaId = b.areaId and b.`status` = 2 and b.isdel=0) as count2,"
                +" (select COUNT(b.areaId) from eqp_item b where a.areaId = b.areaId and b.`status` = 3 and b.isdel=0) as count3"
                +" from eqp_area a "
                +" where 1=1";



        /** 此处为了暴露给智慧公园展示数据, 而在2017-3-20版本中, 智慧公园与器材面向对象不一样, 两边的用户不同, 所以暂且, 在智慧公园那边进行关联场地ID, 提供根据场地ID的查询*/
        if (admin != null) {
            sql += dataFilter.getFilter_A(admin);
        } else {
            if (!("all".equals(areaIds))) {
                areaIds = StringUtil.isEmpty(areaIds) ? "''" : areaIds;
                sql += " and a.areaId in (" + areaIds + ")";
            }
        }
        try {
            System.out.println("sql: >> "+sql);
            List<Record> rs = Db.find(sql);
            for (Record r : rs) {
                SuperBean sb = new SuperBean();
                sb.put("areaId", r.getInt("areaId"));			// 场地id
                sb.put("name", r.getStr("name"));			// 场地名称
                sb.put("areaType", r.getInt("areaType"));		// 场地类型
                sb.put("longitude", r.getDouble("longitude"));	// 经度值
                sb.put("latitude", r.getDouble("latitude"));	// 维度值
                sb.put("value", r.getLong("value"));			// 该场地器材数量
                sb.put("count0", r.getLong("count0"));			// 该场地 状态是0的器材的数量
                sb.put("count1", r.getLong("count1"));			// 该场地 状态是1的器材的数量
                sb.put("count2", r.getLong("count2"));			// 该场地 状态是2的器材的数量
                sb.put("count3", r.getLong("count3"));			// 该场地 状态是3的器材的数量

                rets.add(sb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rets;
    }


    /**
     * 二、供货商器材数量
     *
     * @param admin
     * @param params
     * @return
     */
    @Override
    public List<SuperBean> getSupplierChart(Admin admin, Map<String, Object> params) {

        List<SuperBean> rets = new ArrayList<SuperBean>();

        //定义参数
        int parentId = 0;	// 场地ID筛选
        int eqpsortId = 0;	// 器材分类ID
        String areaIds = "";	// 智慧公园系统, 公园关联的场地列表,格式为(1,2,3), 与admin做判断, 如果admin为null, 那么就使用这个

        //解析参数
        if (params != null) {
            if (params.containsKey("parentId")) {
                parentId = StringUtil.convertInt(params.get("parentId").toString(), 0);
            }
            if (params.containsKey("eqpsortId")) {
                eqpsortId = StringUtil.convertInt(params.get("eqpsortId").toString(), 0);
            }
            if (params.containsKey("areaIds")) {
                areaIds = params.get("areaIds").toString();
            }
        }
        //拼接SQL语句
        String sql = "SELECT COUNT(a.itemId) num, a.supplierId FROM eqp_item a left join eqp_area b on a.areaId=b.areaId where a.isdel=0";
        if (parentId != 0) {
            sql += " and b.regionId in (" + StringUtil.encodeSQL(regionDao.getRegionIdsByPid(parentId)) + ")";
        }
        if (eqpsortId != 0) {
            sql += " and a.eqpsortId in (" + StringUtil.encodeSQL(eqpSortDao.getEqpsortIdsByPid(eqpsortId)) + ")";
        }
        /** 此处为了暴露给智慧公园展示数据, 而在2017-3-20版本中, 智慧公园与器材面向对象不一样, 两边的用户不同, 所以暂且, 在智慧公园那边进行关联场地ID, 提供根据场地ID的查询*/
        if (admin != null) {
            sql += dataFilter.getFilter_A(admin);
        } else {
            if (!("all".equals(areaIds))) {
                areaIds = StringUtil.isEmpty(areaIds) ? "''" : areaIds;
                sql += " and a.areaId in (" + areaIds + ")";
            }
        }
        sql += " GROUP BY a.supplierId order by num desc limit 6 ";
        try {
            System.out.println("sql: >> "+sql);
            List<Record> recordList = Db.find(sql);
            for (Record record : recordList) {
                SuperBean sb = new SuperBean();
                sb.put("supplierId", record.getInt("supplierId"));	// 供应商ID
                sb.put("value", record.getLong("num"));			    // 器材数量
                rets.add(sb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rets;

    }
}
