package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.ItemFlowDao;
import com.wande.ssy.dubbo.provider.service.ItemflowService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.ItemFlow;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
@Service(interfaceClass = ItemflowService.class)
public class ItemflowServiceImpl implements ItemflowService {
    @Autowired
    private ItemFlowDao itemFlowDao;


    @Override
    public RespWrapper<Boolean> addItemflow(ItemFlow obj) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> updateItemflow(ItemFlow obj) {
        return null;
    }

    @Override
    public RespWrapper<ItemFlow> getOneItemflow(int itemflowId) {
        return null;
    }

    @Override
    public RespWrapper<ItemFlow> getOneItemflowByFollowId(int followId) {
        return null;
    }

    @Override
    public RespWrapper<DataPage<ItemFlow>> getItemflowByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public RespWrapper<Map<Integer, ItemFlow>> getItemflowMapInIds(String itemflowIds) {
        return null;
    }
}
