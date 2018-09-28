package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Msg;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.Map;

/**
 * @author stanley
 * 2017年6月13日
 */
public interface MsgService {

	 RespWrapper<Boolean> addMsg(Msg obj);

	 RespWrapper<DataPage<Msg>> getMsgByPage(Map<String, Object> params, int pageNo, int pageSize);
}
