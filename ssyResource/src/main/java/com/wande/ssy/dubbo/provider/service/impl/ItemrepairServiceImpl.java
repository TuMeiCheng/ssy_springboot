package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.ItemrepairDao;
import com.wande.ssy.dubbo.provider.service.ItemrepairService;
import com.wande.ssy.entity.ItemRepair;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = ItemrepairService.class)
public class ItemrepairServiceImpl implements ItemrepairService {

    @Autowired
    private ItemrepairDao itemrepairDao;


    @Override
    public RespWrapper<Boolean> addItemrepair(ItemRepair obj) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> updateItemrepair(ItemRepair obj) {
        return null;
    }

    @Override
    public RespWrapper<ItemRepair> getOneItemRepair(int repairId) {
        return null;
    }
}
