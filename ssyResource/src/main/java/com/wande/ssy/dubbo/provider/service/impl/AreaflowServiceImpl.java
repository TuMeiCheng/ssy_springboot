package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.AreaFlowDao;
import com.wande.ssy.dubbo.provider.service.AreaflowService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.AreaFlow;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service(interfaceClass = AreaflowService.class)
public class AreaflowServiceImpl implements AreaflowService {

    @Autowired
    private AreaFlowDao areaFlowDao;


    @Override
    public RespWrapper<Boolean> addItemflow(AreaFlow obj) {
        Boolean bln = areaFlowDao.insert(obj);
        return RespWrapper.makeResp(0, "", bln);

    }

    @Override
    public RespWrapper<Boolean> updateItemflow(AreaFlow obj) {
        return null;
    }

    @Override
    public RespWrapper<AreaFlow> getOneItemflow(int id) {
        return null;
    }

    @Override
    public RespWrapper<AreaFlow> getOneAreaflowByFollowId(int followId) {
        return null;
    }

    @Override
    public RespWrapper<DataPage<AreaFlow>> getAreaflowByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public RespWrapper<Map<Integer, AreaFlow>> getAreaflowMapInIds(String AreaflowIds) {
        return null;
    }
}
