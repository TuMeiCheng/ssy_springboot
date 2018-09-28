package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wande.ssy.dao.AreaDao;
import com.wande.ssy.dao.RegionDao;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Area;
import com.wande.ssy.utils.DataFilter;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.utils.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AreaDaoImpl implements AreaDao{

    @Autowired
    private DataFilter dataFilter ;

    @Autowired
    private RegionDao regionDao ;




    /* 判断是否存在该场地
     * @param: [name, id]
     * @return: java.lang.Boolean */
    @Override
    public Boolean isExist(String name, Integer areaId) {
        String sql = "SELECT * FROM eqp_area where name='" + StringUtil.encodeSQL(name) + "'";
        if (areaId != 0) {
            sql += " and areaId !=" + areaId;
        }
        try{
            List<Area> list = new Area().find(sql);
            if(list != null && list.size() >0){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;

    }

    /* 添加场地
     * @param: [obj]
     * @return: java.lang.Boolean */
    @Override
    public Boolean insert(Area obj) {
           return obj.save();
    }

    /* 获取场地分页列表
     * @param: [admin, params, pageNo, pageSize]
     * @return: com.ynm3k.mvc.model.DataPage<com.wande.ssy.entity.Area> */
    @Override
    public DataPage<Area> getAreaByPage(Admin admin, Map params, Integer pageNo, Integer pageSize) {

        String areaIds = "";

        //解析参数
        String  sql_select = "select * ";
        String sql = "from eqp_area where 1=1";
        if (params != null) {
            if (params.containsKey("orgId")) {
                sql += " and orgId=" + params.get("orgId");
            }
            if (params.containsKey("agencyId")) {
                sql += " and agencyId=" + params.get("agencyId");
            }
            if (params.containsKey("provideWay")) {
                sql += " and provideWay=" + params.get("provideWay");
            }
            if (params.containsKey("regionId")) {
                sql += " and regionId IN ( SELECT regionId FROM eqp_region where FIND_IN_SET('"+params.get("regionId")+"',path)) ";
            }
            if (params.containsKey("areaType")) {
                sql += " and areaType=" + params.get("areaType");
            }
            if(params.containsKey("keyword")){
                sql += " and ( name like '%" + StringUtil.encodeSQL((String) params.get("keyword")) + "%' or regionFullName like '%" + StringUtil.encodeSQL((String) params.get("keyword")) + "%')";
            }
            if (params.containsKey("areaIds")) {
                areaIds = params.get("areaIds").toString();
            }
        }

        /** 此处为了暴露给智慧公园展示数据, 而在2017-3-20版本中, 智慧公园与器材面向对象不一样, 两边的用户不同, 所以暂且, 在智慧公园那边进行关联场地ID, 提供根据场地ID的查询*/
        if (admin != null) {
            sql += dataFilter.getFilter(admin);
        } else {
            if (!("all".equals(areaIds))) {
                areaIds = StringUtil.isEmpty(areaIds) ? "''" : areaIds;
                sql += "areaId in (" + areaIds + ")";
            }
        }
        sql += " order by createTime desc";
        System.out.println(sql_select+sql);
        Page<Area> areaPage = new Area().paginate(pageNo,pageSize,sql_select,sql);
        System.out.println("pageSize:"+areaPage.getList().size());

        return new DataPage<Area>(areaPage.getList(), areaPage.getTotalRow(), pageNo, pageSize);

    }

    /**
     * 根据角色过滤场地下拉选列表
     * @param admin
     * @return*/
    @Override
    public List<Area> getAreaSelect(Admin admin) {
        String sql = "select * from eqp_area where areaId in (select areaId from eqp_item where 1=1";
        sql += dataFilter.getFilter(admin);
        sql += ")";
        try {
            System.out.println(sql);
           List<Area> list = new Area().find(sql);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ArrayList<Area>();
    }
    
    /* 更新场地
     * @param: [area]
     * @return: java.lang.Boolean */
    @Override
    public Boolean update(Area area) {
        return area.update();
    }

    /* 根据id查询
     * @param: [id]
     * @return: com.wande.ssy.entity.Area */
    @Override
    public Area getOneArea(Integer id) {
        return new Area().findById(id);
    }

    /**
     * 根据左侧树形点击的行政区域ID查找所有场地ids
     */
    @Override
    public String getAreaListByRegionId(int regionId) {
        List<Integer> idList = new ArrayList<Integer>();
        String regionIds = regionDao.getRegionIdsByPid(regionId);
        String sql="select areaId as areaId from eqp_area where regionId in(" + regionIds + ")";
        try {
            List<Record> list = Db.find(sql);
            if (list!=null && list.size() >0 ){
                for (Record record : list) {
                    Integer id = record.get("areaId");
                    idList.add(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "''";
        }
        String ids = idList.size() == 0 || idList == null ? "''" : com.wande.ssy.utils.StringUtil.join(idList, ",");
        return ids;
    }

    @Override
    public Map<Integer, Area> getAreaMapInIds(String areaIds) {
        areaIds = StringUtil.isEmpty(areaIds) ? "''" : areaIds;
        Map<Integer, Area> list = new HashMap<Integer, Area>();
        String sql="select * from eqp_area where areaId in(" + areaIds + ")";
        try {
            List<Area> lists = new Area().find(sql);
            for (Area obj : lists) {
                list.put(obj.getAreaId(), obj);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<Integer, Area>();

    }

    @Override
    public int getAreaIdByQrcode(String qrcode) {
        //查询数据
        String sql = "select areaId as areaId from eqp_area where qrcode = "+ "'"+qrcode+"'";
        System.out.println("sql >>: "+sql);
        try {
            Record rs = Db.findFirst(sql);
            if (rs != null) {
                int areaId = rs.getInt("areaId");
                return areaId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }
}
