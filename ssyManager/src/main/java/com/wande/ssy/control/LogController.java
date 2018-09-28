package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.AdminService;
import com.wande.ssy.dubbo.provider.service.LogService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Log;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/log")
public class LogController {

    @Reference(interfaceClass=LogService.class)
    private LogService logService;

    @Reference(interfaceClass=AdminService.class)
    private AdminService adminService;

    /**
     * 获取日志分页列表
     */
    @RequestMapping("/getLogPage")
    public  Object GetLogPage(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                              @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                              @RequestParam(value = "desc",defaultValue = "") String desc,
                              @RequestParam(value = "account",defaultValue = "") String  account){

        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("desc", desc);
        params.put("account", account);
        RespWrapper<DataPage<Log>> resp = logService.getLogByPage(params, pageNo, pageSize);
        if (resp.getErrCode() != 0 && resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        List<Log> rets = resp.getObj().getRecord();
        for (Log log : rets) {
            Admin admin = adminService.getAdminByUin(log.getCreateBy()).getObj();
            log.set("adminName", admin == null ? "" : admin.getName());
        }
        //返回结果
        return resp;

    }
}
