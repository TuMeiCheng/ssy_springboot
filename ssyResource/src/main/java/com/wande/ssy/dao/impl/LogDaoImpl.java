package com.wande.ssy.dao.impl;

import com.wande.ssy.dao.LogDao;
import com.wande.ssy.entity.Log;
import org.springframework.stereotype.Repository;

@Repository
public class LogDaoImpl implements LogDao {

    //添加日志
    @Override
    public Boolean insert(Log log) {
        return log.save();
    }
}
