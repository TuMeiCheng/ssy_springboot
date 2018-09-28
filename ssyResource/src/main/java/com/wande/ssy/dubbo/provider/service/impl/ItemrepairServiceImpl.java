package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.ItemrepairDao;
import com.wande.ssy.dubbo.provider.service.ItemrepairService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.ItemRepair;
import com.wande.ssy.entity.ItemrepairExt;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service(interfaceClass = ItemrepairService.class)
public class ItemrepairServiceImpl implements ItemrepairService {

    @Autowired
    private ItemrepairDao itemrepairDao;


    @Override
    public RespWrapper<Boolean> addItemrepair(ItemRepair obj) {
        boolean rs = itemrepairDao.insert(obj);
        if (rs) {
            return RespWrapper.makeResp(0, "", true);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙,稍后再试!", false);
        }

    }

    @Override
    public RespWrapper<Boolean> updateItemrepair(ItemRepair obj) {
        return null;
    }

    @Override
    public RespWrapper<ItemRepair> getOneItemRepair(int repairId) {
        try {
            ItemRepair obj = itemrepairDao.getOneItemrepair(repairId);
            if (obj != null) {
                return RespWrapper.makeResp(0, "", obj);
            } else {
                return RespWrapper.makeResp(1001, "该器材网路报修不存在!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespWrapper.makeResp(1001, "系统繁忙!", null);
    }

    @Override
    public RespWrapper<DataPage<ItemrepairExt>> getItemRepairByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize) {
        DataPage<ItemrepairExt> page = itemrepairDao.getItemrepairByPage(admin, params, pageNo, pageSize);
        return RespWrapper.makeResp(0, "", page);
    }
}
