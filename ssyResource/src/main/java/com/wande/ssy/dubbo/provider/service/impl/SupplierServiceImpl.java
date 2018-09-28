package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.SupplierDao;
import com.wande.ssy.dubbo.provider.service.SupplierService;
import com.wande.ssy.entity.Supplier;
import com.wande.ssy.utils.CopyPropertiesUtils;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = SupplierService.class)
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;


    @Override
    public RespWrapper<Integer> addSupplier(Supplier obj) {

        if (supplierDao.isExist(obj.getName(), 0)) {
            return RespWrapper.makeResp(1001, "该名称已经存在!", null);
        }
        String csn = supplierDao.getNewCsn();
        if (csn == null) {
            return RespWrapper.makeResp(1001, "系统繁忙!", null);
        }
        obj.setCsn(csn);
        int rs = supplierDao.insert(obj);
        if (rs > 0) {
            return RespWrapper.makeResp(0, "", rs);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", null);
        }

    }

    @Override
    public RespWrapper<Boolean> updateSupplier(Supplier obj) {
        Supplier supplier = this.supplierDao.getOneSupplier(obj.getSupplierId());
        if (supplier == null) {
            return RespWrapper.makeResp(1001, "不存在该供应商!", false);
        }
        //将obj中的要修改参数拷贝到查询到的修改bean >> supplier 中并且过滤掉obj中属性值为null的属性
        BeanUtils.copyProperties(obj, supplier, CopyPropertiesUtils.getNullPropertyNames(obj));
        supplier.setModifyTime(System.currentTimeMillis());     //修改时间
        //保存更新到数据库
        boolean rs = supplierDao.update(supplier);
        if (rs) {
            return RespWrapper.makeResp(0, "", true);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", false);
        }
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
        return RespWrapper.makeResp(0, "", supplierDao.getSupplierList());
    }

    @Override
    public RespWrapper<DataPage<Supplier>> getSupplierByPage(Map<String, Object> params, int pageNo, int pageSize) {
        DataPage<Supplier> page = this.supplierDao.getSupplierByPage(params, pageNo, pageSize);
        return RespWrapper.makeResp(0, "", page);
    }

    @Override
    public RespWrapper<Map<Integer, Supplier>> getSupplierMapInIds(String supplierIds) {
        return RespWrapper.makeResp(0, "", this.supplierDao.getSupplierMapInIds(supplierIds));
    }
}
