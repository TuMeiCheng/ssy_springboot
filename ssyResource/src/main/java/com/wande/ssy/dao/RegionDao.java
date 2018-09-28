package com.wande.ssy.dao;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Region;

import java.util.List;

public interface RegionDao {

    //根据地区id返回该对象的省市区对象列表
    List<Region> getRegionListById(int regionId);

    //根据id获取一条数据
    Region getOneRegion(Integer id);

    /**
     * 根据parentId获取所有下级地区对象
     * @param parentId	上级地区ID
     * @return	下级地区的所有ID用","拼接的字符串
     */
    String getRegionIdsByPid(int parentId);


    boolean isExist(String name, int i);

    int insert(Region obj);

    boolean updatePath(Region obj);

    /**
     *  根据当前登录系统用户, 获取当前用于所管辖的地区列表
     * @param admin
     * @param parentId	如果为Integer.MAX_VALUE,获取全部, 如果为正常数值,获取对应上级ID的地区
     * @return
     */
    List<Region> getRegionListByAdmin(Admin admin, int parentId);

    // 更新省市区
    boolean update(Region obj);

    List<Region> getRegionList(int maxValue);

    /**
     * 根据parentId获取所有下级器材分类对象
     * @param eqpsortId	上级地区ID
     * @return	下级分类的所有ID用","拼接的字符串
     */
    String getEqpsortIdsByPid(int eqpsortId);
}
