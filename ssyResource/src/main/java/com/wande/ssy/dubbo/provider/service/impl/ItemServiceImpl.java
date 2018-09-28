package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.ItemDao;
import com.wande.ssy.dubbo.provider.service.ItemService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Item;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service(interfaceClass = ItemService.class)
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;


    @Override
    public RespWrapper<Boolean> addItem(Item obj) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> updateItem(Item obj) {
        return null;
    }

    @Override
    public RespWrapper<Item> getOneItem(int itemId) {
        try {
            Item obj = itemDao.getOneItem(itemId);
            if (obj != null) {
                return RespWrapper.makeResp(0, "", obj);
            } else {
                return RespWrapper.makeResp(1001, "该器材不存在!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespWrapper.makeResp(1001, "系统繁忙!", null);
    }

    @Override
    public RespWrapper<Item> getOneItemByItemsn(String itemsn) {
        try {
            Item obj = itemDao.getOneItemByItemsn(itemsn);
            if (obj != null) {
                return RespWrapper.makeResp(0, "", obj);
            } else {
                return RespWrapper.makeResp(1001, "该器材不存在!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespWrapper.makeResp(1001, "系统繁忙!", null);

    }

    @Override
    public RespWrapper<Boolean> updateItemStatus(Admin admin, int itemId, int status) {
        return null;
    }

    @Override
    public RespWrapper<DataPage<Item>> getItemByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize) {

        DataPage<Item> page = itemDao.getItemByPage(admin, params, pageNo, pageSize);
        return RespWrapper.makeResp(0, "", page);
    }
}
