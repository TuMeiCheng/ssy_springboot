package com.wande.ssy.dao;

import com.wande.ssy.entity.Org;

public interface OrgDao {

   String getOrgIdsByPid(Integer parentId);

   Org getOneOrg(Integer id);
}
