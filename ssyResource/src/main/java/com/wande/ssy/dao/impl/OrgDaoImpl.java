package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wande.ssy.dao.OrgDao;
import com.wande.ssy.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class OrgDaoImpl implements OrgDao {


    @Override
    public String getOrgIdsByPid(Integer parentId) {
        List<Integer> idList = new ArrayList<Integer>();
        String sql = "select orgId from eqp_org where FIND_IN_SET("+parentId+",path)";
        try {
            List<Record> rs = Db.find(sql);
            Iterator iterator = rs.iterator();
            while (iterator.hasNext()) {
                Record r =(Record) iterator.next();
                idList.add(r.get("orgId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "''";
        }
        String ids = idList.size() == 0 || idList == null ? "''" : StringUtil.join(idList, ",");
        return ids;
    }

}
