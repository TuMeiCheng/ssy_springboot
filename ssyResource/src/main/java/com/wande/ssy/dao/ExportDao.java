package com.wande.ssy.dao;

import com.wande.ssy.entity.Export;
import com.ynm3k.mvc.model.DataPage;

import java.util.Map;

public interface ExportDao {
    /**
     * 添加方法
     *
     * @param obj
     * @return
     */
    int insert(Export obj);

    DataPage<Export> getExportByPage(Map<String, Object> params, int pageNo, int pageSize);
}
