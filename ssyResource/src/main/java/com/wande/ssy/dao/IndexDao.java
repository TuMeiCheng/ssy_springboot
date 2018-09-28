package com.wande.ssy.dao;

import com.wande.ssy.entity.Admin;
import com.ynm3k.mvc.model.SuperBean;

import java.util.List;
import java.util.Map;

public interface IndexDao {

    SuperBean getAreaItemCount(Admin admin, Map<String, Object> params);


    //设施安全警示接口
    List<SuperBean> getEqpSafeChart(Admin admin, Map<String, Object> params);


    /**
     * 四、设施状态分布
     *
     * @param admin
     * @param params
     * @return
     */
    List<SuperBean> getEqpStatusChart(Admin admin, Map<String, Object> params);


    /**
     * 三、设备安装年份
     *
     * @param admin
     * @param params
     * @return
     */
    List<SuperBean> getEqpTimeChart(Admin admin, Map<String, Object> params);


    /**
     * 一、安装地点分布
     *
     * @param admin
     * @param params
     * @return
     */
    Object getInstallAreaChart(Admin admin, Map<String, Object> params);


    /**
     * 二、供货商器材数量
     *
     * @param admin
     * @param params
     * @return
     */
    List<SuperBean> getSupplierChart(Admin admin, Map<String, Object> params);
}
