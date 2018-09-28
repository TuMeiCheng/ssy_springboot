package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.RegionDao;
import com.wande.ssy.dubbo.provider.service.RegionService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Region;
import com.wande.ssy.utils.CopyPropertiesUtils;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = RegionService.class)
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionDao regionDao;


    /* 添加省市区region
     * @param: [obj]
     * @return: com.ynm3k.mvc.model.RespWrapper<java.lang.Integer> */
    @Override
    public RespWrapper<Integer> addRegion(Region obj) {
        if (regionDao.isExist(obj.getName(), 0)) {
            return RespWrapper.makeResp(1001, "该地区名称已经存在!", null);
        }
        int rs = regionDao.insert(obj);
        if (rs > 0) {
            Region parent = regionDao.getOneRegion(obj.getParentId());
            String path = parent == null ? (rs + "") : parent.getPath() + "," + (rs + "");
            obj.setRegionId(rs);
            obj.setPath(path);
            obj.setLevel(path.split(",").length);
            boolean urs = regionDao.updatePath(obj);
            if (urs) {
                return RespWrapper.makeResp(0, "", rs);
            } else {
                System.out.println("RegionServiceImp,更新path出错!");
                return RespWrapper.makeResp(1001, "系统繁忙!", null);
            }
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", null);
        }
    }

    /* 更新省市区
     * @param: [obj]
     * @return: com.ynm3k.mvc.model.RespWrapper<java.lang.Boolean> */
    @Override
    public RespWrapper<Boolean> updateRegion(Region obj) {
        if (regionDao.getOneRegion(obj.getRegionId()) == null) {
            return RespWrapper.makeResp(1001, "不存在该地区!", false);
        }
        if (regionDao.isExist(obj.getName(), obj.getRegionId())) {
            return RespWrapper.makeResp(1001, "该地区名称已经存在!", null);
        }
        Region region = this.regionDao.getOneRegion(obj.getRegionId());
        //将obj中的要修改参数拷贝到查询到的修改bean >> region 中并且过滤掉obj中属性值为null的属性
        BeanUtils.copyProperties(obj, region, CopyPropertiesUtils.getNullPropertyNames(obj));
        region.setModifyTime(System.currentTimeMillis());
        //保存更新到数据库
        boolean rs = this.regionDao.update(region);
        if (rs) {
            return RespWrapper.makeResp(0, "", true);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", false);
        }
    }

    @Override
    public RespWrapper<Region> getOneRegion(int regionId) {
        try {
            Region obj = regionDao.getOneRegion(regionId);
            if (obj != null) {
                return RespWrapper.makeResp(0, "", obj);
            } else {
                return RespWrapper.makeResp(1001, "该地区表不存在!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespWrapper.makeResp(1001, "系统繁忙!", null);
    }

    @Override
    public RespWrapper<DataPage<Region>> getRegionByPage(Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public RespWrapper<List<Region>> getRegionListByParentId(Admin admin, int parentId) {
        return RespWrapper.makeResp(0, "", regionDao.getRegionListByAdmin(admin, parentId));
    }

    @Override
    public RespWrapper<List<Region>> getRegionList(int regionId) {
        List<Region> rets = this.regionDao.getRegionList(Integer.MAX_VALUE);
        return RespWrapper.makeResp(0, "", rets);
    }
}
