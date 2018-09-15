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

        RegionAgency ca = this.regionagencyDao.getOneRegionagency(uin);
        if (ca == null) {
            return RespWrapper.makeResp(1001, "不存在该信息!", null);
        } else {
            return RespWrapper.makeResp(0, "", ca);
        }

    }
}
