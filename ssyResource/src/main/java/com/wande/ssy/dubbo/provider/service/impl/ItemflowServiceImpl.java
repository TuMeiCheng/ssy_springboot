package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.ItemFlowDao;
import com.wande.ssy.dubbo.provider.service.ItemflowService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.ItemFlow;
import com.wande.ssy.entity.ItemflowExt;
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
        boolean rs = itemFlowDao.insert(obj);
        if (rs) {
            return RespWrapper.makeResp(0, "", true);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙,稍后再试!", false);
        }

    }

    @Override
    public RespWrapper<Boolean> updateItemflow(ItemFlow obj) {
        return null;
    }

    @Override
    public RespWrapper<ItemFlow> getOneItemflow(int itemflowId) {
        try {
            ItemFlow obj = itemFlowDao.getOneItemflow(itemflowId);
            if (obj != null) {
                return RespWrapper.makeResp(0, "", obj);
            } else {
                return RespWrapper.makeResp(1001, "该器材巡检不存在!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespWrapper.makeResp(1001, "系统繁忙!", null);

    }

    @Override
    public RespWrapper<ItemFlow> getOneItemflowByFollowId(int followId) {

        try {
            ItemFlow obj = itemFlowDao.getOneItemflowByFollowId(followId);
            if (obj != null) {
                return RespWrapper.makeResp(0, "", obj);
            } else {
                return RespWrapper.makeResp(1001, "该器材巡检不存在!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespWrapper.makeResp(1001, "系统繁忙!", null);

    }

    @Override
    public RespWrapper<DataPage<ItemflowExt>> getItemflowByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize) {
        DataPage<ItemflowExt> page = itemFlowDao.getItemflowByPage(admin, params, pageNo, pageSize);
        return RespWrapper.makeResp(0, "", page);
    }

    @Override
    public RespWrapper<Map<Integer, ItemFlow>> getItemflowMapInIds(String itemflowIds) {
        return RespWrapper.makeResp(0, "", itemFlowDao.getItemflowMapInIds(itemflowIds));
    }
}