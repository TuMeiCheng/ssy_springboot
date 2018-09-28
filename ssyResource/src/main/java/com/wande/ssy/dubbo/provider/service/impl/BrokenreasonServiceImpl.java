package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.BrokenreasonDao;
import com.wande.ssy.dubbo.provider.service.BrokenreasonService;
import com.wande.ssy.entity.Brokenreason;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = BrokenreasonService.class)
public class BrokenreasonServiceImpl implements BrokenreasonService {

    @Autowired
    private BrokenreasonDao brokenreasonDao;


    @Override
    public RespWrapper<Brokenreason> getOneBrokenreason(int reasonId) {
        Brokenreason obj = brokenreasonDao.getOneBrokenreason(reasonId);
        if (obj == null) {
            return RespWrapper.makeResp(0, "不存在该对象!", null);
        } else {
            return RespWrapper.makeResp(0, "", obj);
        }

    }

    @Override
    public RespWrapper<List<Brokenreason>> getBrokenreasonList(int parentId) {
        List<Brokenreason> rets = this.brokenreasonDao.getBrokenreasonList(parentId);
        return RespWrapper.makeResp(0, "", rets);
    }

    @Override
    public RespWrapper<Map<Integer, Brokenreason>> getBrokenreasonMapInIds(String reasonIds) {
        return RespWrapper.makeResp(0, "", brokenreasonDao.getBrokenreasonMapInIds(reasonIds));
    }
}
