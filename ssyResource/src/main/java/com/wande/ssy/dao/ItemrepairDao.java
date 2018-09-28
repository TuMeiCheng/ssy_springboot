package com.wande.ssy.dao;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.ItemRepair;
import com.wande.ssy.entity.ItemrepairExt;
import com.ynm3k.mvc.model.DataPage;

import java.util.Map;

public interface ItemrepairDao {


    DataPage<ItemrepairExt> getItemrepairByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize);

    ItemRepair getOneItemrepair(int repairId);

    //增加网络报修记录
    boolean insert(ItemRepair obj);
}
