package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.AreaDao;
import com.wande.ssy.dubbo.provider.service.AreaService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Area;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = AreaService.class)
public class AreaServiceImpl implements AreaService{

    @Autowired
    private AreaDao areaDao;


    @Override
    public RespWrapper<Integer> addArea(Area obj) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> updateArea(Area obj) {
        return null;
    }

    @Override
    public RespWrapper<Area> getOneArea(int areaId) {
        return null;
    }

    @Override
    public RespWrapper<DataPage<Area>> getAreaByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public RespWrapper<List<Area>> getAreaSelect(Admin admin) {
        return null;
    }

    @Override
    public RespWrapper<Map<Integer, Area>> getAreaMapInIds(String areaIds) {
        return null;
    }

    @Override
    public RespWrapper<Map<String, Object>> getAreaDetailsByQrcode(String qrcode) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> updateAreaState(Admin admin, int itemId, int status) {
        return null;
    }
}
