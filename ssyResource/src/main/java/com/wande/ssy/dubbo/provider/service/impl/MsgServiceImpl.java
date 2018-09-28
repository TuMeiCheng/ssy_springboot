package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.MsgDao;
import com.wande.ssy.dubbo.provider.service.MsgService;
import com.wande.ssy.entity.Msg;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service(interfaceClass = MsgService.class)
public class MsgServiceImpl implements MsgService {
    @Autowired
    private MsgDao msgDao;


    @Override
    public RespWrapper<Boolean> addMsg(Msg obj) {
        boolean rs = msgDao.insert(obj);
        if (rs) {
            return RespWrapper.makeResp(0, "", rs);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙", null);
        }
    }

    @Override
    public RespWrapper<DataPage<Msg>> getMsgByPage(Map<String, Object> params, int pageNo, int pageSize) {
        DataPage<Msg> page = this.msgDao.getMsgByPage(params, pageNo, pageSize);
        return RespWrapper.makeResp(0, "查询成功!", page);
    }
}
