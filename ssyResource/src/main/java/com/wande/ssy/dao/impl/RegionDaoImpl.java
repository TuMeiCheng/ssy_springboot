package com.wande.ssy.dao.impl;

import com.wande.ssy.dao.RegionDao;
import com.wande.ssy.entity.Region;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RegionDaoImpl implements RegionDao {
    
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
}
