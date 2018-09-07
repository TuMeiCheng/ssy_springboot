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
        return null;
    }

    @Override
    public RespWrapper<List<Brokenreason>> getBrokenreasonList(int parentId) {
        return null;
    }

    @Override
    public RespWrapper<Map<Integer, Brokenreason>> getBrokenreasonMapInIds(String reasonIds) {
        return null;
    }
}
