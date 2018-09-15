package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wande.ssy.dao.EqpSortDao;
import com.wande.ssy.entity.EqpSort;
import com.wande.ssy.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EqpSortDaoImpl implements EqpSortDao {
    @Override
    public String getEqpsortIdsByPid(Integer eqpsortId) {
        List<Integer> list = new ArrayList<>();
        String sql = "select eqpsortId from eqp_eqpsort where FIND_IN_SET("+eqpsortId+",path)";
        try {
            List<Record>  records = Db.find(sql);
            for (Record record : records) {
                list.add(record.getInt("eqpsortId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
        String s = list.size() == 0 || list == null ? "-1" : StringUtil.join(list, ",");
        return s;
    }

    /* 根据eqpSortId查询
     * @param: [eqpSortId]
     * @return: com.wande.ssy.entity.EqpSort */
    @Override
    public EqpSort getOneEqpsort(Integer eqpSortId) {
        return new EqpSort().findById(eqpSortId);
    }

    /* 判读器材分类名称是否已经存在
     * @param: [eqpSortName, eqpsortId]
     * @return: java.lang.Boolean */
    @Override
    public Boolean isExist(String eqpSortName, Integer eqpsortId) {
        String sql = "SELECT * FROM eqp_eqpsort where eqpsortName='" + StringUtil.encodeSQL(eqpSortName) + "'";
        if (eqpsortId != 0) {
            sql += " and eqpsortId !=" + eqpsortId;
        }
       EqpSort eqpSort  = new EqpSort().findFirst(sql);
        if (eqpSort != null){
            return true;
        }
        return false;
    }

    /* 添加器材分类
     * @param: [eqpSort]
     * @return: java.lang.Boolean */
    @Override
    public Integer insert(EqpSort eqpSort) {
        //如果添加成功，返回新增分类的eqpSortId
        if (eqpSort.save()) {
            List<Record> list = Db.find("select max(eqpsortId) as eqpsortId from eqp_eqpsort");
            if (list != null && list.size()>0){
                Record record = list.get(0);
                System.out.println(record.toString());
                return record.getInt("eqpsortId");
            }
        }
        return 0;
    }

    /* 更新器材分类
     * @param: [eqpSort]
     * @return: java.lang.Boolean */
    @Override
    public Boolean update(EqpSort eqpSort) {
        return eqpSort.update();
    }

    /* 获取器材分类列表
     * @param: [parentId]
     * @return: java.util.List<com.wande.ssy.entity.EqpSort> */
    @Override
    public List<EqpSort> getEqpsortList(Integer parentId) {
        //查询数据
        String sql = "select * from eqp_eqpsort where 1=1";
        if (parentId != -1) {
            sql += " and path like '%" + parentId +",%'";
        }
        try {
            List<EqpSort> list = new EqpSort().find(sql);
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<EqpSort>();
    }


}
