package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wande.ssy.dao.EqpDao;
import com.wande.ssy.dubbo.provider.service.EqpService;
import com.wande.ssy.entity.Eqp;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = EqpService.class)
public class EqpServiceImpl implements EqpService {
    @Autowired
    private EqpDao eqpDao;



    @Override
    public RespWrapper addEqp(Eqp obj) {
        Record r = Db.findById("eqp_eqp","eqpId",16);
        System.out.println(r.toString());
        return new RespWrapper(23,"添加器材",r);
    }

    @Override
    public RespWrapper<Boolean> updateEqp(Eqp obj) {
        return null;
    }

    @Override
    public RespWrapper<Eqp> getOneEqp(int eqpId) {
        return null;
    }

    @Override
    public RespWrapper<DataPage<Eqp>> getEqpByPage(Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public RespWrapper<List<Eqp>> getEqpByList(int supplierId, int eqpsortId) {
        return null;
    }

    @Override
    public RespWrapper<Map<Integer, Eqp>> getEqpMapInIds(String eqpIds) {
        return null;
    }
}
