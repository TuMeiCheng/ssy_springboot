package com.wande.ssy.dao;

import com.wande.ssy.entity.RegionAgency;

public interface RegionagencyDao {

    Boolean insert(RegionAgency regionAgency);

    //根据uin获取RegionAgency
    RegionAgency getOneRegionagency(Long uin);

    //更新
    Boolean  update(RegionAgency regionAgency);


}
