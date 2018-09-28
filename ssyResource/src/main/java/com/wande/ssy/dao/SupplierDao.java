package com.wande.ssy.dao;

import com.wande.ssy.entity.Supplier;
import com.ynm3k.mvc.model.DataPage;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public interface SupplierDao {

    //根据id查询
    Supplier getOneSupplier(Integer supplierId);

    Map<Integer, Supplier> getSupplierMapInIds(String supplierIds);


    boolean isExist(@NotNull(message = "供应商名称不能为空!") String name, int i);

    String getNewCsn();

    int insert(Supplier obj);

    List<Supplier> getSupplierList();

    DataPage<Supplier> getSupplierByPage(Map<String, Object> params, int pageNo, int pageSize);

    boolean update(Supplier supplier);
}
