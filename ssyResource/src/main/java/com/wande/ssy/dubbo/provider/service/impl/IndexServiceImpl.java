package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.IndexDao;
import com.wande.ssy.dubbo.provider.service.IndexService;
import com.wande.ssy.entity.Admin;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.model.SuperBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


@Service(interfaceClass = IndexService.class)
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IndexDao indexDao;
    
    
    
    @Override
    public RespWrapper<SuperBean> getAreaItemCount(Admin admin, Map<String, Object> params) {
        return RespWrapper.makeResp(0, "", indexDao.getAreaItemCount(admin, params));
    }

    @Override
    public RespWrapper<SuperBean> getInstallAreaChart(Admin admin, Map<String, Object> params) {
        SuperBean ret = new SuperBean();
        ret.put("data", indexDao.getInstallAreaChart(admin, params));
        return RespWrapper.makeResp(0, "", ret);
    }

    @Override
    public RespWrapper<List<SuperBean>> getSupplierChart(Admin admin, Map<String, Object> params) {
        List<SuperBean> rets = indexDao.getSupplierChart(admin, params);
        return RespWrapper.makeResp(0, "", rets);
    }

    @Override
    public RespWrapper<List<SuperBean>> getEqpTimeChart(Admin admin, Map<String, Object> params) {
        return RespWrapper.makeResp(0, "", indexDao.getEqpTimeChart(admin, params));
    }

    @Override
    public RespWrapper<List<SuperBean>> getEqpStatusChart(Admin admin, Map<String, Object> params) {
        List<SuperBean> rets = indexDao.getEqpStatusChart(admin, params);
        return RespWrapper.makeResp(0, "", rets);
    }

    @Override
    public RespWrapper<List<SuperBean>> getEqpSafeChart(Admin admin, Map<String, Object> params) {
        List<SuperBean> rets = indexDao.getEqpSafeChart(admin, params);
        return RespWrapper.makeResp(0, "", rets);
    }
}
