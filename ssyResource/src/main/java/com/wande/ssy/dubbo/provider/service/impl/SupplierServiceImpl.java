package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.SupplierDao;
import com.wande.ssy.dubbo.provider.service.SupplierService;
import com.wande.ssy.entity.Supplier;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = SupplierService.class)
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;


    @Override
    public RespWrapper<Integer> addSupplier(Supplier obj) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> updateSupplier(Supplier obj) {
        return null;
    }

    @Override
    public RespWrapper<Supplier> getOneSupplier(int supplierId) {
        try {
            Supplier obj = this.supplierDao.getOneSupplier(supplierId);
            if (obj != null) {
                return RespWrapper.makeResp(0, "", obj);
            } else {
                return RespWrapper.makeResp(1001, "该器材供应商不存在!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespWrapper.makeResp(1001, "系统繁忙!", null);

    }

    @Override
    public RespWrapper<List<Supplier>> getSupplierList() {
        return null;
    }

    @Override
    public RespWrapper<DataPage<Supplier>> getSupplierByPage(Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public RespWrapper<Map<Integer, Supplier>> getSupplierMapInIds(String supplierIds) {
        return null;
    }
}
