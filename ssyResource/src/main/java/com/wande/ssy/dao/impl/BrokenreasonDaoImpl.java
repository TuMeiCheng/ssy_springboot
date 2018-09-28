package com.wande.ssy.dao.impl;

import com.wande.ssy.dao.BrokenreasonDao;
import com.wande.ssy.entity.Brokenreason;
import com.wande.ssy.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BrokenreasonDaoImpl implements BrokenreasonDao {


    /**
     * 根据parentId获取报修原因列表
     * @param parentId	Integer.MAX_VALUE则获取全部, 有值则根据父级ID去筛选器材分类
     * @return
     */
    @Override
    public List<Brokenreason> getBrokenreasonList(int parentId) {
        List<Brokenreason> list = new ArrayList<Brokenreason>();
        //查询数据
        String sql = "select * from eqp_brokenreason where 1=1";
        if (parentId != Integer.MAX_VALUE) {
            sql += " and parentId=" + parentId;
        }
        try {
          return new Brokenreason().find(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Brokenreason>();
    }

    @Override
    public Map<Integer, Brokenreason> getBrokenreasonMapInIds(String reasonIds) {

        reasonIds = StringUtil.isEmpty(reasonIds) ? "''" : reasonIds;
        Map<Integer, Brokenreason> list = new HashMap<Integer, Brokenreason>();
        //查询数据
        String sql="select * from eqp_brokenreason where reasonId in(" + reasonIds + ")";
        try {
            List<Brokenreason> list1 = new Brokenreason().find(sql);
            for (Brokenreason obj : list1) {
                list.put(obj.getReasonId(), obj);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<Integer, Brokenreason>();
    }

    @Override
    public Brokenreason getOneBrokenreason(int reasonId) {
        return new Brokenreason().findById(reasonId);
    }
}
