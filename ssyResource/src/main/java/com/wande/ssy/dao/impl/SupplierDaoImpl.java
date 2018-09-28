package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wande.ssy.dao.SupplierDao;
import com.wande.ssy.entity.Supplier;
import com.wande.ssy.utils.DataFilter;
import com.wande.ssy.utils.StringUtil;
import com.ynm3k.mvc.model.DataPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SupplierDaoImpl implements SupplierDao {

    @Autowired
    private DataFilter dataFilter;

    //根据id查询
    @Override
    public Supplier getOneSupplier(Integer supplierId) {
        return new Supplier().findById(supplierId);
    }

    @Override
    public Map<Integer, Supplier> getSupplierMapInIds(String supplierIds) {
        supplierIds = StringUtil.isEmpty(supplierIds) ? "''" : supplierIds;
        Map<Integer, Supplier> list = new HashMap<Integer, Supplier>();
        //查询数据
        String sql="select * from eqp_supplier where supplierId in(" + supplierIds + ")";
        try {
            List<Supplier> list2 = new Supplier().find(sql);
            for (Supplier supplier : list2) {
                list.put(supplier.getSupplierId(),supplier);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<Integer, Supplier>();

    }

    @Override
    public boolean isExist(String name, int supplierId) {
        String sql = "SELECT * FROM eqp_supplier where name='" + StringUtil.encodeSQL(name) + "'";
        if (supplierId != 0) {
            sql += " and supplierId !=" + supplierId;
        }
        try{
            Supplier supplier = new Supplier().findFirst(sql);
            if (supplier != null){
                return  true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取最大的Csn供应商编码
     * @return	返回最大编码+1的字符串形式
     */
    @Override
    public String getNewCsn() {
        String sql = "select MAX(csn) csn from eqp_supplier";
        try {
            List<Record> list = Db.find(sql);
            if (list != null && list.size()>0){
                Record record = list.get(0);
                System.out.println(record.toString());
                String csn =  record.getStr("csn");
                int maxCsn = Integer.parseInt(csn);
                return (maxCsn + 1) + "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int insert(Supplier obj) {
        if (obj.save()) {
            List<Record> list = Db.find("select max(orgId) as orgId from eqp_org");
            if (list != null && list.size()>0){
                Record record = list.get(0);
                System.out.println(record.toString());
                return record.getInt("orgId");
            }
        }
        return 0;
    }

    @Override
    public List<Supplier> getSupplierList() {
        String sql = "select * from eqp_supplier";
        try {
            List<Supplier> suppliers = new Supplier().find(sql);
            return suppliers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Supplier>();
    }

    /* 后去供应商分页列表
     * @param: [params, pageNo, pageSize]
     * @return: com.ynm3k.mvc.model.DataPage<com.wande.ssy.entity.Supplier> */
    @Override
    public DataPage<Supplier> getSupplierByPage(Map<String, Object> params, int pageNo, int pageSize) {
        List<Supplier> list = new ArrayList<Supplier>();
        //定义参数
        int status = Integer.MAX_VALUE;	// 启用，禁止
        String keyword = "";	//关键字

        //解析参数
        if (params != null) {
            if (params.get("status") != null) {
                status = StringUtil.convertInt(params.get("status").toString(), Integer.MAX_VALUE);
            }
            if(params.get("keyword") != null){
                keyword = params.get("keyword").toString();
            }
        }
        //查询数据
        String sql_select = "select * ";
        String sql = "from eqp_supplier where 1=1";
        if (status!=Integer.MAX_VALUE) {
            sql += " and status=" + status;
        }
        if(StringUtil.isNotEmpty(keyword)){
            sql += " and name like '%"+StringUtil.encodeSQL(keyword)+"%'";
        }
        try {
            System.out.println("sql:"+sql_select+sql);
            Page<Supplier> supplierPage =new Supplier().paginate(pageNo,pageSize,sql_select,sql);
            System.out.println("pageSize:"+supplierPage.getList().size());

            return new DataPage<Supplier>(supplierPage.getList(), supplierPage.getTotalRow(), pageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataPage<Supplier>(list, 0, pageNo, pageSize);
    }

    /* 更新供应商
     * @param: [supplier]
     * @return: boolean */
    @Override
    public boolean update(Supplier supplier) {
        return supplier.update();
    }
}
