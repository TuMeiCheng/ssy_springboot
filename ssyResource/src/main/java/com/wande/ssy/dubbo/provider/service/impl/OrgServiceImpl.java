package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.OrgDao;
import com.wande.ssy.dubbo.provider.service.OrgService;
import com.wande.ssy.entity.Org;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrgService.class)
public class OrgServiceImpl implements OrgService {
    @Autowired
    private OrgDao orgDao;


    @Override
    public RespWrapper<Integer> addOrg(Org obj) {
        orgDao.getOrgIdsByPid(2);
        return null;
    }

    @Override
    public RespWrapper<Boolean> updateOrg(Org obj) {
        return null;
    }

    @Override
    public RespWrapper<Org> getOneOrg(int orgId) {
        return null;
    }

    @Override
    public RespWrapper<Org> getOneOrgByRegionId(int regionId) {
        return null;
    }

    @Override
    public RespWrapper<DataPage<Org>> getOrgByPage(Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public RespWrapper<List<Org>> getOrgList(int parentId) {
        return null;
    }

    @Override
    public RespWrapper<Map<Integer, Org>> getOrgMapInIds(String orgIds) {
        return null;
    }
}
