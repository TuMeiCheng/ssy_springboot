package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wande.ssy.dao.ExportDao;
import com.wande.ssy.entity.Export;
import com.wande.ssy.utils.DateTimeUtil;
import com.wande.ssy.utils.StringUtil;
import com.ynm3k.mvc.model.DataPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ExportDaoImpl implements ExportDao {


    /**
     * 添加方法
     *
     * @param obj
     * @return
     */
    @Override
    public int insert(Export obj) {
        if (obj.save()) {
            List<Record> list = Db.find("select max(exportId) as exportId from eqp_export");
            if (list != null && list.size()>0){
                Record record = list.get(0);
                return record.getInt("exportId");
            }
        }
        return 0;
    }

    /* 分页多条件查询
     * @param: [params, pageNo, pageSize]
     * @return: com.ynm3k.mvc.model.DataPage<com.wande.ssy.entity.Export> */
    @Override
    public DataPage<Export> getExportByPage(Map<String, Object> params, int pageNo, int pageSize) {
        List<Export> list = new ArrayList<Export>();

        //定义参数
        long exportTime = Long.MAX_VALUE;	//导出时间
        long startTime = 0;	//开始时间(毫秒)
        long endTime = 0;   //结束时间(毫秒)

        //解析参数
        if (params != null) {
            if (params.containsKey("exportTime")) {
                exportTime = StringUtil.convertLong(params.get("exportTime").toString(), Long.MAX_VALUE);
            }
            if (params.containsKey("startTime")) {
                startTime = StringUtil.convertLong(params.get("startTime").toString(), 0);
            }
            if (params.containsKey("endTime")) {
                endTime = StringUtil.convertLong(params.get("endTime").toString(), 0);
            }
        }

        //查询数据
        String sql_select = "select * ";
        String sql = "from eqp_export where 1=1";
        if (exportTime!=Long.MAX_VALUE) {
            sql += " and exportTime=" + exportTime;
        }
        if (startTime != 0 && endTime != 0 && startTime <= endTime) {
            sql += " and exportTime>=" + startTime + " and exportTime<=" + (endTime + DateTimeUtil.ONE_DAY);
        }
        sql += " order by exportTime desc";
        log.info("SQL: {}",sql_select+sql);
        try {
            Db.paginate(pageNo, pageSize, sql_select, sql);
            Page<Export> exportPage = new Export().paginate(pageNo,pageSize,sql_select,sql);
            return new DataPage<Export>(exportPage.getList(), exportPage.getTotalRow(), pageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataPage<Export>(list, 0, pageNo, pageSize);

    }
}
