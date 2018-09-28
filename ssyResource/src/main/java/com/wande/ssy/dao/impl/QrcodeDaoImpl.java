package com.wande.ssy.dao.impl;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wande.ssy.dao.QrcodeDao;
import com.wande.ssy.entity.Qrcode;
import com.wande.ssy.enums.QrcodeStatus;
import com.wande.ssy.utils.DateTimeUtil;
import com.wande.ssy.utils.StringUtil;
import com.ynm3k.mvc.model.DataPage;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class QrcodeDaoImpl implements  QrcodeDao {


    //检查二维码是否存在相同code唯一标识
    @Override
    public boolean checkCodeExist(String code) {
        String sql = "select code from eqp_qrcode where code='" + StringUtil.encodeSQL(code) + "'";
        try {
            Record record = Db.findFirst(sql);
            if(record != null){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* 添加二维码
     * @param: [qrcode_list]
     * @return: boolean */
    @Override
    @Before(Tx.class)
    public boolean insert(List<Qrcode> qrcode_list) {
        //添加事务控制
        Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                Db.batchSave(qrcode_list, qrcode_list.size());
                return true;
            }
        });
        return true;
    }



    /* 根据id获取二维码
     * @param: [qrcodeId]
     * @return: com.wande.ssy.entity.Qrcode */
    @Override
    public Qrcode getOneQrcode(int qrcodeId) {
        String sql = "select * from eqp_qrcode where isdel=0 and qrcodeId=" + qrcodeId;
        Qrcode qrcode = new Qrcode().findFirst(sql);
        return qrcode;
    }


    //删除二维码
    @Override
    public boolean deleteQrcode(int qrcodeId) {
        return new Qrcode().deleteById(qrcodeId);
    }


    /**
     * 导出未出厂记录的二维码
     * @return
     */
    @Override
    public List<Qrcode> getExportList() {
        String sql = "SELECT * FROM eqp_qrcode WHERE isdel=0 and status=" + QrcodeStatus.UNOUT.getValue() +" order by qrcodeId desc";
        try {
            List<Qrcode> list = new Qrcode().find(sql);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Qrcode>();
    }


    /**
     * 更新二维码状态
     * @param status
     * @param qrcodeIds
     * @return
     */
    @Override
    public boolean updateStatus(int status, String qrcodeIds) {
        String sql = "update eqp_qrcode set `status` = " + status + " where qrcodeId in (" + qrcodeIds + ") ";
        System.out.println("SQL:"+sql);
        try {
          if ( Db.update(sql) > 0 ){
              return true;
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 更新二维码所属导出记录
     * @return
     */
    @Override
    public boolean updateExport(int exportId, String qrcodeIds) {
        String sql = "update eqp_qrcode set exportId=" + exportId + " where qrcodeId in (" + qrcodeIds + ")";
        try {
            if (Db.update(sql) >0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    //分页多条件查询
    @Override
    public DataPage<Qrcode> getQrcodeByPage(Map<String, Object> params, int pageNo, int pageSize) {
        Long agencyId = (Long)params.get("agencyId"); // 管理公司ID
        Integer status = (Integer) params.get("status"); // 0无 1未出厂 2出厂 3使用
        Integer exportId =(Integer) params.get("exportId"); // 导出记录ID
        Long startTime = (Long) params.get("startTime") ; // 开始时间(毫秒)
        Long endTime = (Long) params.get("endTime"); // 结束时间(毫秒)
        String keyword = (String ) params.get("keyword"); // 关键字
        //查询数据
        String sql_select = "select * ";
        String sql = "from eqp_qrcode where isdel=0";
        if (agencyId != -1) {
            sql += " and agencyId=" + agencyId;
        }
        if (status != -1) {
            sql += " and status=" + status;
        }
        if (exportId != -1) {
            sql += " and exportId=" + exportId;
        }
        if (startTime != 0 && endTime != 0 && startTime <= endTime) {
            sql += " and createTime>=" + startTime + " and createTime<=" + (endTime + DateTimeUtil.ONE_DAY);
        }
        if(StringUtil.isNotEmpty(keyword)){
            sql += " and code like '%"+StringUtil.encodeSQL(keyword)+"%'";
        }
        sql += " order by createTime desc";

        //查询
        Page<Qrcode> qrcodePage = new Qrcode().paginate(pageNo,pageSize,sql_select,sql);

        return new DataPage<Qrcode>(qrcodePage.getList(), qrcodePage.getTotalRow(), pageNo, pageSize);
    }
}
