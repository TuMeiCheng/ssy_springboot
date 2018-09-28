package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wande.ssy.dao.OrgDao;
import com.wande.ssy.entity.Org;
import com.wande.ssy.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrgDaoImpl implements OrgDao {


    @Override
    public String getOrgIdsByPid(Integer parentId) {
        List<Integer> idList = new ArrayList<Integer>();
        String sql = "select orgId from eqp_org where FIND_IN_SET("+parentId+",path)";
        try {
            List<Record> rs = Db.find(sql);
            Iterator iterator = rs.iterator();
            while (iterator.hasNext()) {
                Record r =(Record) iterator.next();
                idList.add(r.get("orgId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "''";
        }
        String ids = idList.size() == 0 || idList == null ? "''" : StringUtil.join(idList, ",");
        return ids;
    }

    @Override
    public Org getOneOrg(Integer id) {
        return new Org().findById(id);
    }

    /**
     * 根据orgIds 获取org列表, 用于优化查询数据, 少查SQL
     * @param orgIds
     * @return
     */
    @Override
    public Map<Integer, Org> getOrgMapInIds(String orgIds) {

        orgIds = StringUtil.isEmpty(orgIds) ? "''" : orgIds;
        Map<Integer, Org> list = new HashMap<Integer, Org>();
        //查询数据
        String sql="select * from eqp_org where orgId in(" + orgIds + ")";
        try {
           List<Org> list2 = new Org().find(sql);
            for (Org org : list2) {
                list.put(org.getOrgId(),org);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<Integer, Org>();
    }

    @Override
    public boolean isExist(String name, int orgId) {
        String sql = "SELECT * FROM eqp_org where name='" + StringUtil.encodeSQL(name) + "'";
        if (orgId != 0) {
            sql += " and orgId !=" + orgId;
        }
        try {
          Org org = new Org().findFirst(sql);
            if (org != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* 添加供应商
     * @param: [obj]
     * @return: int */
    @Override
    public int insert(Org obj) {
        if (obj.save()) {
            List<Record> list = Db.find("select max(supplierId) as supplierId from eqp_supplier");
            if (list != null && list.size()>0){
                Record record = list.get(0);
                return record.getInt("supplierId");
            }
        }
        return 0;
    }

    @Override
    public boolean updatePath(Org obj) {
        try {
         return obj.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public Org getOneOrgByRegionId(int regionId) {
        String sql = "select * from eqp_org where regionId=" + regionId;
        try {
          Org org = new Org().findFirst(sql);
           return org;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据parentId获取管辖机构List
     *
     * @param parentId	Integer.MAX_VALUE则获取全部, 有值则根据父级ID去筛选管辖机构
     * @return
     */
    @Override
    public List<Org> getOrgList(int parentId) {
        List<Org> list = new ArrayList<Org>();
        //查询数据
        String sql = "select * from eqp_org where 1=1";
        if (parentId != Integer.MAX_VALUE) {
            sql += " and parentId=" + parentId;
        }
        try {
            list = new Org().find(sql);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Org>();

    }

    //更新
    @Override
    public boolean update(Org org) {
        return org.update();
    }

}
