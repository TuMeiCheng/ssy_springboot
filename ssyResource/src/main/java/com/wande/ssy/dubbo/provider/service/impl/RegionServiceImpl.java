package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.RegionDao;
import com.wande.ssy.dubbo.provider.service.RegionService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Region;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = RegionService.class)
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionDao regionDao;


    @Override
    public RespWrapper<Integer> addRegion(Region obj) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> updateRegion(Region obj) {
        return null;
    }

    @Override
    public RespWrapper<Region> getOneRegion(int regionId) {
        try {
            Region obj = regionDao.getOneRegion(regionId);
            if (obj != null) {
                return RespWrapper.makeResp(0, "", obj);
            } else {
                return RespWrapper.makeResp(1001, "该地区表不存在!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespWrapper.makeResp(1001, "系统繁忙!", null);
    }

    @Override
    public RespWrapper<DataPage<Region>> getRegionByPage(Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public RespWrapper<List<Region>> getRegionListByParentId(Admin admin, int parentId) {
        return null;
    }

    @Override
    public RespWrapper<List<Region>> getRegionList(int regionId) {
        return null;
    }
}
