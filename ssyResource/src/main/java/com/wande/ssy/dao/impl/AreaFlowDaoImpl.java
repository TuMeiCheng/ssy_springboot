package com.wande.ssy.dao.impl;

import com.wande.ssy.dao.AreaFlowDao;
import com.wande.ssy.entity.AreaFlow;
import org.springframework.stereotype.Repository;

@Repository
public class AreaFlowDaoImpl implements AreaFlowDao {
    @Override
    public Boolean insert(AreaFlow obj) {
        return obj.save();
    }
}
