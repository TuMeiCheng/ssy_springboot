package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Page;
import com.wande.ssy.dao.EqpDao;
import com.wande.ssy.dao.EqpSortDao;
import com.wande.ssy.entity.Eqp;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.utils.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EqpDaoImpl implements EqpDao {

    @Autowired
    private EqpSortDao eqpSortDao;


    
    /* 添加eqp器材
     * @param: [eqp]
     * @return: java.lang.Boolean */
    @Override
    public Boolean insert(Eqp eqp) {
        return eqp.save();
    }

    /* 获取器材列表
     * @param: [supplierId, eqpsortId]
     * @return: java.util.List<com.wande.ssy.entity.Eqp> */
    @Override
    public List<Eqp> getEqpList(int supplierId, int eqpsortId) {
        String sql = "select * from eqp_eqp where 1=1";
        if (eqpsortId != -1) {
            sql += " and eqpsortId in (" + StringUtil.encodeSQL(eqpSortDao.getEqpsortIdsByPid(eqpsortId)) + ")";
        }
        if (supplierId != -1) {
            sql += " and supplierId=" + supplierId;
        }
        sql += " order by createTime desc";
        System.out.println(sql);
         List<Eqp> eqps =  new Eqp().find(sql);
         return eqps;
    }

    /* 获取分页
     * @param: [params, pageNo, pageSize]
     * @return: com.ynm3k.mvc.model.DataPage<com.wande.ssy.entity.Eqp> */
    @Override
    public DataPage<Eqp> getEqpByPage(Map params, Integer pageNo, Integer pageSize) {
        List<Eqp> list = new ArrayList<Eqp>();
        //取出Map中的参数
        Integer eqpsortId = (Integer) params.get("eqpsortId");	// 器材分类
        Integer supplierId = (Integer) params.get("supplierId");	// 所属供应商ID
        Integer hasVideo = (Integer) params.get("hasVideo");	// 有无视频
        String keyword = (String) params.get("keyword");	// 关键字(型号或名称)
        Integer isNull = -1;
        //查询数据
        String sql_select = "select * ";
        String sql = "from eqp_eqp where 1=1";
        if ( eqpsortId != isNull) {
            sql += " and eqpsortId in (" + StringUtil.encodeSQL(eqpSortDao.getEqpsortIdsByPid(eqpsortId)) + ")";
        }
        if (supplierId != isNull) {
            sql += " and supplierId=" + supplierId;
        }
        if (hasVideo != isNull) {
            sql += hasVideo == 1 ? " and video!=''" : " and video=''";
        }
        if(StringUtil.isNotEmpty(keyword)){
            sql += " and ( catesn like '%" + StringUtil.encodeSQL(keyword) + "%' or name like '%" + StringUtil.encodeSQL(keyword) + "%')";
        }
        sql += " order by createTime desc";
        System.out.println(sql_select+sql);
        Page<Eqp> areaPage = new Eqp().paginate(pageNo,pageSize,sql_select,sql);
        System.out.println("pageSize:"+areaPage.getList().size());
        return new DataPage<Eqp>(areaPage.getList(), areaPage.getTotalRow(), pageNo, pageSize);

    }

    /* 更新器材库eqp
     * @param: [obj]
     * @return: java.lang.Boolean */
    @Override
    public Boolean update(Eqp obj) {
        return  obj.update();
    }

    /* 根据id查询单条器材库数据
     * @param: [eqpId]
     * @return: com.wande.ssy.entity.Eqp */
    @Override
    public Eqp getOneByEqpId(Integer eqpId) {
        return new Eqp().findById(eqpId);
    }

    @Override
    public Map<Integer, Eqp> getEqpMapInIds(String eqpIds) {
        eqpIds = StringUtil.isEmpty(eqpIds) ? "''" : eqpIds;
        Map<Integer, Eqp> list = new HashMap<Integer, Eqp>();
        //查询数据
        String sql="select * from eqp_eqp where eqpId in(" + eqpIds + ")";
        try {
            List<Eqp> list2 = new Eqp().find(sql);
            for (Eqp eqp : list2) {
                list.put(eqp.getEqpId(),eqp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<Integer, Eqp>();

    }
}
