package com.wande.ssy.dao;

import com.wande.ssy.entity.Log;
import com.ynm3k.mvc.model.DataPage;

import java.util.Map;

public interface LogDao {

    //添加日志
    Boolean insert(Log log);

    DataPage<Log> getLogByPage(Map<String, Object> params, int pageNo, int pageSize);
}
