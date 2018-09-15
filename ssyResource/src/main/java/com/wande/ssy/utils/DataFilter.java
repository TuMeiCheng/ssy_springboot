package com.wande.ssy.utils;

import com.wande.ssy.dao.OrgDao;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.enums.AdminRole;
import com.ynm3k.utils.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 数据过滤器
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月13日上午10:13:06
 */
@Component
public class DataFilter {

	@Autowired
	private OrgDao orgDao;

	/**
	 * 数据过滤SQL, 带对象标识符a
	 * @param admin	当前登录系统用户
	 * @return
	 */
	public String getFilter_A(Admin admin){
		if (admin.getRoleId() == AdminRole.AGENCY.getValue()) {
			return " and (a.agencyId=" + admin.getUin() + ")";
		} else if (admin.getRoleId() == AdminRole.ORG.getValue()){
			//return " and a.orgId in(" + StringUtil.encodeSQL(orgManager.getOrgIdsByPid(orgId)) + ")";
			return " and a.orgId in(" + StringUtil.encodeSQL(orgDao.getOrgIdsByPid(admin.getOrgId())) + ")";
		} else {
			return "";
		}
	}

	/**
	 * 数据过滤SQL, 不对象带标识符a
	 * @param admin	当前登录系统用户
	 * @return
	 */
	public String getFilter(Admin admin){
		if (admin.getRoleId() == AdminRole.AGENCY.getValue()) {
			return " and (agencyId=" + admin.getUin() + ")";
		} else if (admin.getRoleId() == AdminRole.ORG.getValue()){
			return " and orgId in(" + StringUtil.encodeSQL(orgDao.getOrgIdsByPid(admin.getOrgId())) + ")";
		} else {
			return "";
		}
	}
}
