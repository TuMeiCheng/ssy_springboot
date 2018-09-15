package com.wande.ssy.dao.impl;

import com.wande.ssy.dao.SupplierDao;
import com.wande.ssy.entity.Supplier;
import org.springframework.stereotype.Repository;

@Repository
public class SupplierDaoImpl implements SupplierDao {

    //根据id查询
    @Override
    public Supplier getOneSupplier(Integer supplierId) {
        return new Supplier().findById(supplierId);
    }
}
