package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.LogDao;
import com.wande.ssy.dubbo.provider.service.LogService;
import com.wande.ssy.entity.Log;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service(interfaceClass = LogService.class)
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;


    @Override
    public RespWrapper<Boolean> addLog(Log obj) {
            boolean rs = logDao.insert(obj);
            if (rs) {
                return RespWrapper.makeResp(0, "", rs);
            } else {
                return RespWrapper.makeResp(1001, "系统繁忙", null);
            }
    }

    @Override
    public RespWrapper<DataPage<Log>> getLogByPage(Map<String, Object> params, int pageNo, int pageSize) {
        DataPage<Log> page = this.logDao.getLogByPage(params, pageNo, pageSize);
        return RespWrapper.makeResp(0, "查询成功!", page);
    }
}
