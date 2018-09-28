package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.OrgDao;
import com.wande.ssy.dubbo.provider.service.OrgService;
import com.wande.ssy.entity.Org;
import com.wande.ssy.utils.CopyPropertiesUtils;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrgService.class)
public class OrgServiceImpl implements OrgService {
    @Autowired
    private OrgDao orgDao;


    @Override
    public RespWrapper<Integer> addOrg(Org obj) {

        if (this.orgDao.isExist(obj.getName(), 0)) {
            return RespWrapper.makeResp(1001, "该体育局名称已经存在!", null);
        }
        int rs = orgDao.insert(obj);
        if (rs > 0) {
            Org parent = orgDao.getOneOrg(obj.getParentId());
            String path = parent == null ? (rs + "") : parent.getPath() + "," + (rs + "");
            obj.setOrgId(rs);
            obj.setPath(path);
            obj.setLevel(path.split(",").length);
            boolean urs = orgDao.updatePath(obj);
            if (urs) {
                return RespWrapper.makeResp(0, "", rs);
            }
        }
        System.out.println("OrgServiceImpl29,添加出错!");
        return RespWrapper.makeResp(1001, "系统繁忙!", null);
    }

    @Override
    public RespWrapper<Boolean> updateOrg(Org obj) {
        Org org = this.orgDao.getOneOrg(obj.getOrgId());
        if (org == null) {
            throw new RespException(1001, "该管辖机构不存在");
        }
        //将obj中的要修改参数拷贝到查询到的修改bean >> eqp 中并且过滤掉obj中属性值为null的属性
        BeanUtils.copyProperties(obj, org, CopyPropertiesUtils.getNullPropertyNames(obj));
        org.setModifyTime(System.currentTimeMillis());
        //保存更新到数据库
        boolean rs = orgDao.update(org);
        if (rs) {
            return RespWrapper.makeResp(0, "", true);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", false);
        }
    }

    @Override
    public RespWrapper<Org> getOneOrg(int orgId) {

        try {
            Org obj = this.orgDao.getOneOrg(orgId);
            if (obj != null) {
                return RespWrapper.makeResp(0, "", obj);
            } else {
                return RespWrapper.makeResp(1001, "该体育局不存在!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespWrapper.makeResp(1001, "系统繁忙!", null);
        }

    }

    @Override
    public RespWrapper<Org> getOneOrgByRegionId(int regionId) {
        try {
            Org obj = orgDao.getOneOrgByRegionId(regionId);
            if (obj != null) {
                return RespWrapper.makeResp(0, "", obj);
            } else {
                return RespWrapper.makeResp(1001, "该地区不存在管辖机构!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespWrapper.makeResp(1001, "系统繁忙!", null);
        }

    }

    @Override
    public RespWrapper<DataPage<Org>> getOrgByPage(Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public RespWrapper<List<Org>> getOrgList(int parentId) {

        List<Org> rets = orgDao.getOrgList(parentId);
        return RespWrapper.makeResp(0, "", rets);

    }

    @Override
    public RespWrapper<Map<Integer, Org>> getOrgMapInIds(String orgIds) {
        return RespWrapper.makeResp(0, "", this.orgDao.getOrgMapInIds(orgIds));
    }
}
