package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wande.ssy.dao.OrgDao;
import com.wande.ssy.dao.RegionDao;
import com.wande.ssy.dao.RegionagencyDao;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Org;
import com.wande.ssy.entity.Region;
import com.wande.ssy.entity.RegionAgency;
import com.wande.ssy.enums.AdminRole;
import com.wande.ssy.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RegionDaoImpl implements RegionDao {

    @Autowired
    private RegionagencyDao regionagencyDao;

    @Autowired
    private OrgDao orgDao ;




    
    /* 根据id返回该对象的省市区对象列表
     * @param: [regionId]
     * @return: java.util.List<com.wande.ssy.entity.Region> */
    @Override
    public List<Region> getRegionListById(int regionId) {
        String sql = "select * from eqp_region"
                + " where FIND_IN_SET(regionId, (select path from eqp_region where regionId = " + regionId + "))";
        try {
            List list = new Region().find(sql);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Region>();

    }

    /* 获取一条数据
     * @param: [id]
     * @return: com.wande.ssy.entity.Region */
    @Override
    public Region getOneRegion(Integer id) {
        return new Region().findById(id);
    }



    /**
     * 根据parentId获取所有下级地区对象
     * @param parentId	上级地区ID
     * @return	下级地区的所有ID用","拼接的字符串
     */
    @Override
    public String getRegionIdsByPid(int parentId) {
        List<String> idList = new ArrayList<String>();
        String sql = "select path as path from eqp_region where FIND_IN_SET("+parentId+",path)";
        try {
            List<Record> list = Db.find(sql);
            if (list!=null && list.size() >0 ){
                for (Record record : list) {
                    String id = record.get("path");
                    idList.add(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "''";
        }
        String ids = idList.size() == 0 || idList == null ? "''" : StringUtil.join(idList, ",");
        return ids;
    }

    //检查是否存在
    @Override
    public boolean isExist(String name, int regionId) {
        String sql = "SELECT * FROM eqp_region where name='" + StringUtil.encodeSQL(name) + "'";
        if (regionId != 0) {
            sql += " and regionId !=" + regionId;
        }
        try{
            Region region = new Region().findFirst(sql);
            if(region != null){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;

    }

    /* 添加省市区 
     * @param: [obj]
     * @return: int */
    @Override
    public int insert(Region obj) {
        if (obj.save()) {
            List<Record> list = Db.find("select max(regionId) as regionId from eqp_region");
            if (list != null && list.size()>0){
                Record record = list.get(0);
                System.out.println(record.toString());
                return record.getInt("regionId");
            }
        }
        return 0;
    }

    /* 更新省市区region
     * @param: [obj]
     * @return: boolean */
    @Override
    public boolean updatePath(Region obj) {
        return obj.update();
    }


    /**
     *  根据当前登录系统用户, 获取当前用于所管辖的地区列表
     * @param admin
     * @param parentId	如果为Integer.MAX_VALUE,获取全部, 如果为正常数值,获取对应上级ID的地区
     * @return
     */
    @Override
    public List<Region> getRegionListByAdmin(Admin admin, int parentId) {
        List<Region> list = new ArrayList<Region>();
        int roleId = admin.getRoleId();
        String sql = "";
        if (roleId == AdminRole.AGENCY.getValue()) {
            RegionAgency ra = regionagencyDao.getOneRegionagency(admin.getUin());
            if (ra == null)
                return list;
            sql += "select * from eqp_region where regionId in(" + getRegionListInId(ra.getRegionIds()) + ")";
        } else if (roleId == AdminRole.ORG.getValue()) {
            Org org = orgDao.getOneOrg(admin.getOrgId());
            if (org == null)
                return list;
            String regionIds = getRegionIdsByPid(org.getRegionId());
            regionIds = StringUtil.isEmpty(regionIds) ? "''" : regionIds;
            sql += "select * from eqp_region where regionId in(" + regionIds + ")";
        } else if (roleId == AdminRole.ADMIN.getValue()) {
            sql += "select * from eqp_region where 1=1";
        } else {
            return list;
        }
        if (parentId != -1) {
            sql += " and parentId=" + parentId;
        }
        try {
            list = new Region().find(sql);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Region>();
    }

    /* 更新省市区
     * @param: [obj]
     * @return: boolean */
    @Override
    public boolean update(Region obj) {
        return obj.update();
    }

    @Override
    public List<Region> getRegionList(int parentId) {
        //查询数据
        String sql = "select * from eqp_region where 1=1";
        if (parentId != Integer.MAX_VALUE) {
            sql += " and parentId=" + parentId;
        }
        try {
        List<Region> regionList = new Region().find(sql);
            return regionList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Region>();
    }


    /**
     * 根据parentId获取所有下级器材分类对象
     * @param parentId	上级地区ID
     * @return	下级分类的所有ID用","拼接的字符串
     */
    @Override
    public String getEqpsortIdsByPid(int parentId) {

        List<Integer> ids = new ArrayList<Integer>();
        String sql = "select eqpsortId as eqpsortId from eqp_eqpsort where FIND_IN_SET("+parentId+",path)";
        try {
           List<Record> recordList = Db.find(sql);
            for (Record record : recordList) {
                ids.add(record.get("eqpsortId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "''";
        }
        return ids.size() == 0 || ids == null ? "''" : StringUtil.join(ids, ",");

    }


    private String getRegionListInId(String regionIds) {
        StringBuilder resultPath = new StringBuilder();
        String sql = "select path as path from eqp_region where regionId in(" + regionIds + ")";
        try {
            List<Record> list = Db.find(sql);
            for (Record record : list) {
                resultPath.append((String )record.get("path")).append(",");
            }
            String path = resultPath.toString();
            if (path.length() > 0) {
                path = path.substring(0, path.length() - 1);
            }
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
