package com.wande.ssy.dao;

import com.wande.ssy.entity.EqpSort;

import java.util.List;

public interface EqpSortDao {

    /**
     * 根据parentId获取所有下级器材分类对象
     * @param eqpsortId	上级地区ID
     * @return	下级分类的所有ID用","拼接的字符串
     */
    String getEqpsortIdsByPid(Integer eqpsortId);

    //根据eqpSortId查询
    EqpSort getOneEqpsort(Integer eqpSortId);

    //判读器材分类名称是否已经存在
    Boolean isExist(String  eqpSortName,Integer eqpsortId);

    //添加器材分类
    Integer insert(EqpSort eqpSort);

    //更新器材分类
    Boolean update(EqpSort eqpSort);

    //获取器材分类列表
    List<EqpSort> getEqpsortList(Integer parentId);
}
