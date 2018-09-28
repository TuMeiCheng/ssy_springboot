package com.wande.ssy.dao;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.ItemFlow;
import com.wande.ssy.entity.ItemflowExt;
import com.ynm3k.mvc.model.DataPage;

import java.util.Map;

public interface ItemFlowDao {

    ItemFlow getOneItemflow(int itemflowId);

    DataPage<ItemflowExt> getItemflowByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize);

    Map<Integer, ItemFlow> getItemflowMapInIds(String itemflowIds);

    ItemFlow getOneItemflowByFollowId(int followId);

    //增加一条巡检记录
    boolean insert(ItemFlow obj);
}
