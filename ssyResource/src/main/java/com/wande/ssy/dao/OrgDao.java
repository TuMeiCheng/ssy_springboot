package com.wande.ssy.dao;

import com.wande.ssy.entity.Org;

import java.util.List;
import java.util.Map;

public interface OrgDao {

   String getOrgIdsByPid(Integer parentId);

   Org getOneOrg(Integer id);

   /**
    * 根据orgIds 获取org列表, 用于优化查询数据, 少查SQL
    * @param orgIds
    * @return
    */
   Map<Integer, Org> getOrgMapInIds(String orgIds);


    boolean isExist( String name, int i);

    int insert(Org obj);

    boolean updatePath(Org obj);

    Org getOneOrgByRegionId(int regionId);

    List<Org> getOrgList(int parentId);

    boolean update(Org org);
}
