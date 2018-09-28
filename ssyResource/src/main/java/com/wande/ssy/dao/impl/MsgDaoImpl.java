package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Page;
import com.wande.ssy.dao.MsgDao;
import com.wande.ssy.entity.Msg;
import com.ynm3k.mvc.model.DataPage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class MsgDaoImpl implements MsgDao {
    @Override
    public DataPage<Msg> getMsgByPage(Map<String, Object> params, int pageNo, int pageSize) {
        List<Msg> list = new ArrayList<Msg>();
        //查询数据
        String sql_select = "select * ";
        String sql = "from eqp_msg order by id desc";
        try {
            Page<Msg> msgPage = new Msg().paginate(pageNo,pageSize,sql_select,sql);
            return new DataPage<Msg>(msgPage.getList(), msgPage.getTotalRow(), pageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataPage<Msg>(list, 0, pageNo, pageSize);
    }

    @Override
    public boolean insert(Msg obj) {
        return obj.save();
    }
}
