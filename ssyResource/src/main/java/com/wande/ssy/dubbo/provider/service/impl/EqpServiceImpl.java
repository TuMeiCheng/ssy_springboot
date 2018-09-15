package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.EqpDao;
import com.wande.ssy.dubbo.provider.service.EqpService;
import com.wande.ssy.entity.Eqp;
import com.wande.ssy.utils.CopyPropertiesUtils;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = EqpService.class)
public class EqpServiceImpl implements EqpService {
    @Autowired
    private EqpDao eqpDao;


    /* 添加器材
     * @param: [obj]
     * @return: com.ynm3k.mvc.model.RespWrapper */
    @Override
    public RespWrapper addEqp(Eqp obj) {
        Boolean  bln = this.eqpDao.insert(obj);
        if (bln) {
            return RespWrapper.makeResp(0, "", 1);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", null);
        }
    }

    /* 更新器材
     * @param: [obj]
     * @return: com.ynm3k.mvc.model.RespWrapper<java.lang.Boolean> */
    @Override
    public RespWrapper<Boolean> updateEqp(Eqp obj) {
        Eqp eqp = eqpDao.getOneByEqpId(obj.getEqpId());
        if (eqp == null){
            return RespWrapper.makeResp(1001, "【更新器材Eqp】该器材不存在!", false);
        }
        //将obj中的要修改参数拷贝到查询到的修改bean >> eqp 中并且过滤掉obj中属性值为null的属性
        BeanUtils.copyProperties(obj, eqp, CopyPropertiesUtils.getNullPropertyNames(obj));
        eqp.setModifyTime(System.currentTimeMillis());
        //保存更新到数据库
        System.out.println(eqp.toString());
        boolean rs = eqpDao.update(eqp);
        if (rs) {
            return RespWrapper.makeResp(0, "", true);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", false);
        }
    }

    @Override
    public RespWrapper<Eqp> getOneEqp(int eqpId) {
        return null;
    }

    @Override
    public RespWrapper<DataPage<Eqp>> getEqpByPage(Map<String, Object> params, int pageNo, int pageSize) {
        DataPage<Eqp> page = this.eqpDao.getEqpByPage(params, pageNo, pageSize);
        return RespWrapper.makeResp(0, "", page);
    }

    @Override
    public RespWrapper<List<Eqp>> getEqpByList(int supplierId, int eqpsortId) {
        List<Eqp> ret = this.eqpDao.getEqpList(supplierId, eqpsortId);
        return RespWrapper.makeResp(0, "", ret);
    }

    @Override
    public RespWrapper<Map<Integer, Eqp>> getEqpMapInIds(String eqpIds) {
        return null;
    }
}
