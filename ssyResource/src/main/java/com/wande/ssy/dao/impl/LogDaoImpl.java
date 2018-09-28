package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Page;
import com.wande.ssy.dao.LogDao;
import com.wande.ssy.entity.Log;
import com.ynm3k.mvc.model.DataPage;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class LogDaoImpl implements LogDao {

    //添加日志
    @Override
    public Boolean insert(Log log) {
        return log.save();
    }

    @Override
    public DataPage<Log> getLogByPage(Map<String, Object> params, int pageNo, int pageSize) {
        List<Log> list = new ArrayList<Log>();
        //查询数据
        String sql_select = "select * ";
        String sql = "from eqp_log where 1=1";
        if(params!=null&&params.get("desc")!=null&&!params.get("desc").toString().trim().isEmpty()){
            sql += " and desc like '%"+params.get("desc")+"%'";
        }
        if(params!=null&&params.get("account")!=null&&!params.get("account").toString().trim().isEmpty()){
            sql += " and account like '%"+params.get("account")+"%'";
        }
        sql += " order by createTime desc";
        try {
            Page<Log> logPage =new Log().paginate(pageNo,pageSize,sql_select,sql);
            System.out.println("pageSize:"+logPage.getList().size());

            return new DataPage<Log>(logPage.getList(), logPage.getTotalRow(), pageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataPage<Log>(list, 0, pageNo, pageSize);

    }
}
