package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.AdminService;
import com.wande.ssy.dubbo.provider.service.IndexService;
import com.wande.ssy.entity.Admin;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.model.SuperBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/ali")
@RestController
public class AliController {

    @Reference(interfaceClass=AdminService.class)
    private AdminService adminService;

    @Reference(interfaceClass=IndexService.class)
    private IndexService indexService;

    @RequestMapping("/aliData")
    public Object aliData(@RequestParam(value = "provinceId",defaultValue = "0") Integer provinceId,    // 省份ID
                          @RequestParam(value = "cityId",defaultValue = "0") Integer cityId,    // 城市ID
                          @RequestParam(value = "regionId",defaultValue = "0") Integer regionId,    // 地区ID
                          @RequestParam(value = "eqpsortId",defaultValue = "0") Integer eqpsortId){ // 分类ID

        //当前登录用户
        Admin admin = new Admin();
        Integer parentId = regionId != 0 ? regionId : cityId != 0 ? cityId : provinceId != 0 ? provinceId : 0;

        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("parentId", parentId);
        params.put("eqpsortId", eqpsortId);

        RespWrapper<SuperBean> resp = indexService.getAreaItemCount(admin, params);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("areaCount", resp.getObj().getLong("areaCount"));
        resultMap.put("itemCount", resp.getObj().getLong("itemCount"));
        List<Object> li = new ArrayList<>();
        li.add(resultMap);
        //返回结果
       return li;
    }
}
