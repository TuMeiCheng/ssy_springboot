package com.wande.ssy.dao;

import com.wande.ssy.entity.Eqp;
import com.ynm3k.mvc.model.DataPage;

import java.util.List;
import java.util.Map;

public interface EqpDao {

    //添加eqp器材
    Boolean insert(Eqp eqp);

    //获取器材列表
    List<Eqp>  getEqpList(int supplierId, int eqpsortId);

    //获取分页
    DataPage<Eqp>  getEqpByPage(Map params, Integer pageNo, Integer pageSize);

    //更新eqp
    Boolean update(Eqp obj);

    //根据id查询单条器材库数据
    Eqp getOneByEqpId(Integer eqpId);
}
