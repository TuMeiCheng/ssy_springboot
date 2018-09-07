package com.wande.ssy.dao.impl;

import com.wande.ssy.dao.ItemDao;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Item;
import com.wande.ssy.utils.DataFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao {

    @Autowired
    private DataFilter dataFilter;


    /**
     * 根据当前登录角色获取eqp_item列表
     * @param admin
     * @return
     */
    @Override
    public List<Item> getItemList(Admin admin) {
        String sql = "select * from eqp_item where 1=1";
        sql += dataFilter.getFilter(admin);
        System.out.println(sql);
        try {
            Item item = new Item();
            List<Item> item_list=  item.find(sql);
            return item_list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Item>();
    }



}
