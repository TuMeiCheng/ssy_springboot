package com.wande.ssy.dao;

import com.wande.ssy.entity.Brokenreason;

import java.util.List;
import java.util.Map;

public interface BrokenreasonDao {

    /**
     * 根据parentId获取报修原因列表
     * @param parentId	Integer.MAX_VALUE则获取全部, 有值则根据父级ID去筛选器材分类
     * @return
     */
    List<Brokenreason> getBrokenreasonList(int parentId);

    Map<Integer, Brokenreason> getBrokenreasonMapInIds(String reasonIds);

    Brokenreason getOneBrokenreason(int reasonId);
}
