package com.wande.ssy.dao;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Item;
import com.ynm3k.mvc.model.DataPage;

import java.util.List;
import java.util.Map;

public interface ItemDao {

    List<Item> getItemList(Admin admin);

    DataPage getItemByPage(Admin admin, Map params, Integer pageNo, Integer pageSize);

    Item getOneItem(int itemId);

    Item getOneItemByItemsn(String  itemsn);

    List<Item> getItemsByAreaId(int areaId);
}
