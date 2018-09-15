package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.AreaDao;
import com.wande.ssy.dao.RegionDao;
import com.wande.ssy.dubbo.provider.service.AreaService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Area;
import com.wande.ssy.entity.Region;
import com.wande.ssy.utils.CopyPropertiesUtils;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = AreaService.class)
public class AreaServiceImpl implements AreaService{

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private RegionDao regionDao;


    /* 添加场地
     * @param: [obj]
     * @return: com.ynm3k.mvc.model.RespWrapper<java.lang.Integer> */
    @Override
    public RespWrapper<Integer> addArea(Area obj) {
        if (areaDao.isExist(obj.getName(), 0)) {
            return RespWrapper.makeResp(1001, "该场地名称已存在!", null);
        }
        List<Region> regionList = regionDao.getRegionListById(obj.getRegionId());
        StringBuilder sb = new StringBuilder();
        sb.append("");
        for (Region r : regionList) {
            sb.append(r.getName());
        }
        String regionFullName = sb.toString();
        obj.setRegionFullName(regionFullName);
        Boolean bln = areaDao.insert(obj);
        if (bln) {
            return RespWrapper.makeResp(0, "",1);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", null);
        }
    }

    /* 更新场地
     * @param: [obj]
     * @return: com.ynm3k.mvc.model.RespWrapper<java.lang.Boolean> */
    @Override
    public RespWrapper<Boolean> updateArea(Area obj) {
        if (this.areaDao.isExist(obj.getName(), obj.getAreaId())) {
            return RespWrapper.makeResp(1001, "该场地名称已存在!", false);
        }
        Area area = getOneArea(obj.getAreaId()).getObj();
        //将obj中的要修改参数拷贝到查询到的修改bean >> area 中并且不拷贝obj中属性值为null的属性
        BeanUtils.copyProperties(obj, area, CopyPropertiesUtils.getNullPropertyNames(obj));
        area.setModifyTime(System.currentTimeMillis());
        //保存更新到数据库
        boolean rs = this.areaDao.update(area);
        if (rs) {
            return RespWrapper.makeResp(0, "", true);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙,稍后再试!", false);
        }
    }

    @Override
    public RespWrapper<Area> getOneArea(int areaId) {
        try {
            Area obj = this.areaDao.getOneArea(areaId);
            if (obj != null) {
                return RespWrapper.makeResp(0, "", obj);
            } else {
                return RespWrapper.makeResp(1001, "该场地不存在!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespWrapper.makeResp(1001, "系统繁忙!", null);
    }

    /* 根据登录用户筛选场地分页列表
     * @param: [admin, params, pageNo, pageSize]
     * @return: com.ynm3k.mvc.model.RespWrapper<com.ynm3k.mvc.model.DataPage<com.wande.ssy.entity.Area>> */
    @Override
    public RespWrapper<DataPage<Area>> getAreaByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize) {
        DataPage<Area> page = this.areaDao.getAreaByPage(admin, params, pageNo, pageSize);
        return RespWrapper.makeResp(0, "", page);
    }

    /* 根据用户获取场地下拉框选项
     * @param: [admin]
     * @return: com.ynm3k.mvc.model.RespWrapper<java.util.List<com.wande.ssy.entity.Area>> */
    @Override
    public RespWrapper<List<Area>> getAreaSelect(Admin admin) {
        return RespWrapper.makeResp(0, "", areaDao.getAreaSelect(admin));
    }

    @Override
    public RespWrapper<Map<Integer, Area>> getAreaMapInIds(String areaIds) {
        return null;
    }

    @Override
    public RespWrapper<Map<String, Object>> getAreaDetailsByQrcode(String qrcode) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> updateAreaState(Admin admin, int itemId, int status) {
        return null;
    }
}
