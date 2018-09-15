package com.wande.ssy.dao;

import com.wande.ssy.entity.Region;

import java.util.List;

public interface RegionDao {

    //根据地区id返回该对象的省市区对象列表
    List<Region> getRegionListById(int regionId);

    //根据id获取一条数据
    Region getOneRegion(Integer id);;



}
