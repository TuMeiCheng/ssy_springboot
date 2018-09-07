package com.wande.ssy.dao;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Item;

import java.util.List;

public interface ItemDao {

    List<Item> getItemList(Admin admin);
}
