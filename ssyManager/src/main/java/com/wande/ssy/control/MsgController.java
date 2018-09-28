package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.MsgService;
import com.wande.ssy.entity.Msg;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/msg")
public class MsgController {

    @Reference(interfaceClass=MsgService.class)
    private MsgService msgService;

    /**
     * 获取消息分页列表
     */
     @RequestMapping("/getMsgByPage")
    public Object getMsgByPage(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                               @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize){

        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
        RespWrapper<DataPage<Msg>> resp = msgService.getMsgByPage(params, pageNo, pageSize);
        if (resp.getErrCode() != 0 && resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        //返回结果
        return resp;
    }

    //
    @RequestMapping("/getNewMsg")
    public Object getNewMsg(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                               @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){

        Map<String, Object> params = new HashMap<String, Object>();
        RespWrapper<DataPage<Msg>> resp = msgService.getMsgByPage(params, pageNo, pageSize);
        if (resp.getErrCode() != 0 && resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        //返回结果
        return resp;
    }




}
