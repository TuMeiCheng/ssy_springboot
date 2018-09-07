package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.RegionagencyDao;
import com.wande.ssy.dubbo.provider.service.RegionagencyService;
import com.wande.ssy.entity.RegionAgency;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = RegionagencyService.class)
public class RegionagencyServiceImpl implements RegionagencyService {

    @Autowired
    private RegionagencyDao regionagencyDao;


    @Override
    public RespWrapper<RegionAgency> getOneRegionagencyByAgencyId(long uin) {
        return null;
    }
}
