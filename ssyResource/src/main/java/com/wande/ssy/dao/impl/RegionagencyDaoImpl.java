package com.wande.ssy.dao.impl;

import com.wande.ssy.dao.RegionagencyDao;
import com.wande.ssy.entity.RegionAgency;
import com.ynm3k.utils.string.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegionagencyDaoImpl implements RegionagencyDao {


    @Override
    public Boolean insert(RegionAgency regionAgency) {
       return regionAgency.save();
    }

    /* 根据uin获取RegionAgency
     * @param: [uin]
     * @return: com.wande.ssy.entity.RegionAgency */
    @Override
    public RegionAgency getOneRegionagency(Long agencyId) {
        String sql = "select * from eqp_regionagency where agencyId = ?";
        try {
            List<RegionAgency> list = new RegionAgency().find(sql,agencyId);
            if (list!=null && list.size()>0){
                return list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /* 更新
     * @param: [regionAgency]
     * @return: java.lang.Boolean */
    @Override
    public Boolean update(RegionAgency obj) {
        try {
            obj.findById(obj.getId()).set("regionIds",StringUtil.encodeSQL(obj.getRegionIds())).update();
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
