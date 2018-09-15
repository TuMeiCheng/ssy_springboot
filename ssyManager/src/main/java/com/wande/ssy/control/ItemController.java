package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.ItemService;
import com.wande.ssy.dubbo.provider.service.LogService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/item")
public class ItemController {

    @Reference(interfaceClass=ItemService.class)
    private ItemService itemService;

    //日志服务
    @Reference(interfaceClass = LogService.class)
    private LogService logService;


    /**
     * 根据搜索条件导出分页列表
     * */
    @RequestMapping("/exportItem")
    public Object ExportItem(@Valid Item item,
                             @RequestParam(value = "projectId",defaultValue = "-1") Integer projectId, //项目分类ID
                             @RequestParam(value = "regionId",defaultValue = "-1") Integer regionId, //地区id
                             @RequestParam(value = "installYear",defaultValue = "") String installYear, //安装时间
                             @RequestParam(value = "expireYearStr",defaultValue = "") String expireYearStr, //到期时间
                             HttpServletRequest request){
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(2);
        admin.setUin(30L);
        admin.setAccount("admin");
        //封装参数
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("Item",item);
        params.put("projectId",projectId);   // 项目分类ID
        params.put("regionId",regionId);    // 地区id
        params.put("installYear",installYear);  //安装时间
        params.put("expireYearStr",expireYearStr);  //到期时间

        //设置参数,调用远程Service
        this.itemService.getItemByPage(admin, params, 1, 1000);	//只导出1000条

        return null;
    }




}
