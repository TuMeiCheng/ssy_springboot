package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.EqpSortDao;
import com.wande.ssy.dubbo.provider.service.EqpsortService;
import com.wande.ssy.entity.EqpSort;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = EqpsortService.class)
public class EqpsortServiceImpl implements EqpsortService {
    @Autowired
    private EqpSortDao eqpSortDao;


    @Override
    public RespWrapper<Integer> addEqpsort(EqpSort obj) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> updateEqpsort(EqpSort obj) {
        return null;
    }

    @Override
    public RespWrapper<EqpSort> getOneEqpSort(int EqpSortId) {
        return null;
    }

    @Override
    public RespWrapper<DataPage<EqpSort>> getEqpSortByPage(Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public RespWrapper<List<EqpSort>> getEqpSortList(int parentId) {
        return null;
    }

    @Override
    public RespWrapper<Map<Integer, EqpSort>> getEqpSortMapInIds(String EqpSortIds) {
        return null;
    }
}
