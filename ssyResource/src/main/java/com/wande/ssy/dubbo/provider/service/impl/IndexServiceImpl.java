package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dubbo.provider.service.IndexService;
import com.wande.ssy.entity.Admin;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.model.SuperBean;

import java.util.List;
import java.util.Map;


@Service(interfaceClass = IndexService.class)
public class IndexServiceImpl implements IndexService {
    @Override
    public RespWrapper<SuperBean> getAreaItemCount(Admin admin, Map<String, Object> params) {
        return null;
    }

    @Override
    public RespWrapper<SuperBean> getInstallAreaChart(Admin admin, Map<String, Object> params) {
        return null;
    }

    @Override
    public RespWrapper<List<SuperBean>> getSupplierChart(Admin admin, Map<String, Object> params) {
        return null;
    }

    @Override
    public RespWrapper<List<SuperBean>> getEqpTimeChart(Admin admin, Map<String, Object> params) {
        return null;
    }

    @Override
    public RespWrapper<List<SuperBean>> getEqpStatusChart(Admin admin, Map<String, Object> params) {
        return null;
    }

    @Override
    public RespWrapper<List<SuperBean>> getEqpSafeChart(Admin admin, Map<String, Object> params) {
        return null;
    }
}
