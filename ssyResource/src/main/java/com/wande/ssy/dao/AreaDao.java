package com.wande.ssy.dao;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Area;
import com.ynm3k.mvc.model.DataPage;

import java.util.List;
import java.util.Map;

public interface AreaDao {

    //判断是否存在该场地
    Boolean isExist(String  name ,Integer id);

    //添加场地
    Boolean insert(Area obj);

    //获取场地分页列表
   DataPage<Area> getAreaByPage(Admin admin, Map params, Integer pageNo, Integer pageSize);

    /**
     * 根据角色过滤场地下拉选列表
     * @param admin
     * @return
     */
   List<Area> getAreaSelect(Admin admin);

   //更新场地
   Boolean update(Area area);

   //根据id查询
    Area getOneArea(Integer id);



    //根据左侧树形点击的行政区域ID查找所有场地ids
    String getAreaListByRegionId(int regionId);

    /**
     * 根据areaIds 获取area列表, 用于优化查询数据, 少查SQL
     * @param areaIds
     * @return
     */
    Map<Integer, Area> getAreaMapInIds(String areaIds);

    int getAreaIdByQrcode(String qrcode);
}
