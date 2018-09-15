package com.wande.ssy.dao;

import com.wande.ssy.entity.Supplier;

public interface SupplierDao {

    //根据id查询
    Supplier getOneSupplier(Integer supplierId);

}
