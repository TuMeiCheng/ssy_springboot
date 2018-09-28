package com.wande.ssy.dao;

import com.wande.ssy.entity.Msg;
import com.ynm3k.mvc.model.DataPage;

import java.util.Map;

public interface MsgDao {

    DataPage<Msg> getMsgByPage(Map<String, Object> params, int pageNo, int pageSize);

    boolean insert(Msg obj);
}
