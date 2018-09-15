package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.EqpSortDao;
import com.wande.ssy.dubbo.provider.service.EqpsortService;
import com.wande.ssy.entity.EqpSort;
import com.wande.ssy.utils.CopyPropertiesUtils;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = EqpsortService.class)
public class EqpsortServiceImpl implements EqpsortService {
    @Autowired
    private EqpSortDao eqpSortDao;


    /* 添加器材分类
     * @param: [obj]
     * @return: com.ynm3k.mvc.model.RespWrapper<java.lang.Integer> */
    @Override
    public RespWrapper<Integer> addEqpsort(EqpSort obj) {

        if (eqpSortDao.isExist(obj.getEqpsortName(), 0)) {
            return RespWrapper.makeResp(1001, "该器材分类名称已经存在!", null);
        }
        int rs = eqpSortDao.insert(obj);
        if (rs >0) {
            EqpSort parent = eqpSortDao.getOneEqpsort(obj.getParentId());
            String path = parent == null ? (rs + "") : parent.getPath() + "," + (rs + "");
            obj.setProjectId(parent == null ? 1 : Integer.parseInt(parent.getPath().substring(0, 1)));
            obj.setEqpsortId(rs);
            obj.setPath(path);
            obj.setLevel(path.split(",").length);
            boolean urs = eqpSortDao.update(obj);
            if (urs) {
                return RespWrapper.makeResp(0, "", rs);
            } else {
                System.out.println("EqpsortServiceImpl37,更新path出错!");
                return RespWrapper.makeResp(1001, "系统繁忙!", null);
            }
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", null);
        }

    }

    /* 更新器材分类
     * @param: [obj]
     * @return: com.ynm3k.mvc.model.RespWrapper<java.lang.Boolean> */
    @Override
    public RespWrapper<Boolean> updateEqpsort(EqpSort obj) {
        //获取要更新的器材详情
        EqpSort eqpSort = this.eqpSortDao.getOneEqpsort(obj.getEqpsortId());
        if (eqpSort == null) {
            return RespWrapper.makeResp(1001, "该器材分类不存在!", null);
        }
        //将obj中的要修改参数拷贝到查询到的修改bean >> eqpSort 中并且过滤掉obj中属性值为null的属性
        BeanUtils.copyProperties(obj, eqpSort, CopyPropertiesUtils.getNullPropertyNames(obj));
        eqpSort.setModifyTime(System.currentTimeMillis());
        //保存更新到数据库
        System.out.println(eqpSort.toString());
        boolean rs = eqpSortDao.update(eqpSort);
        if (rs) {
            return RespWrapper.makeResp(0, "", true);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", false);
        }
    }

    @Override
    public RespWrapper<EqpSort> getOneEqpSort(int EqpSortId) {
        try {
            EqpSort obj = eqpSortDao.getOneEqpsort(EqpSortId);
            if (obj != null) {
                return RespWrapper.makeResp(0, "", obj);
            } else {
                return RespWrapper.makeResp(1001, "该器材分类不存在!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespWrapper.makeResp(1001, "系统繁忙!", null);

    }

    @Override
    public RespWrapper<DataPage<EqpSort>> getEqpSortByPage(Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public RespWrapper<List<EqpSort>> getEqpSortList(int parentId) {
        return RespWrapper.makeResp(0, "", eqpSortDao.getEqpsortList(parentId));
    }

    @Override
    public RespWrapper<Map<Integer, EqpSort>> getEqpSortMapInIds(String EqpSortIds) {
        return null;
    }
}
