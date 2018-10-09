package com.wande.ssy.aspect;


import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.AdminService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.enums.AdminRole;
import com.wande.ssy.utils.StringUtil;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.webutil.CookieBox;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {

    @Reference(interfaceClass=AdminService.class)
    private AdminService adminService;


    protected static final List<String> commonURI = new ArrayList<String>();
    protected static final List<String> SysURI = new ArrayList<String>();
    protected static final List<String> AgencyURI = new ArrayList<String>();
    protected static final List<String> letPass = new ArrayList<>();

    static {
        initList();
    }



    //定义切入点logAuthorize
    @Pointcut("execution(public * com.wande.ssy.control.*.*(..))")
    public void logAuthorize() {}

    //声明环绕通知
    @Around("logAuthorize()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        long begin = System.nanoTime();
        //开始执行方法
        Object o = pjp.proceed();
        long end = System.nanoTime();
        //调用耗时
        long url_time = (end-begin)/1000000;
        if ( url_time > 5000){ //接口调用时长超过5秒
            log.info("【效率过低】 >> URL: {},  >> 调用时间：{}", request.getRequestURL().toString(),url_time);
        }else {
            log.info("URL: {},  >> 调用时间：{}", request.getRequestURL().toString(),url_time);
        }
        return o;
    }


    //声明前置通知
    @Before("logAuthorize()")
    public void  apiValidator(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        System.out.println("前置通知");
        String uri = request.getRequestURI();
        //TODO url地址过滤 正式服务器里需要修改
        uri = uri.replace("http://localhost:8080/ssyManager", "");

        System.out.println("url: >>   "+uri);

        // 不拦截地址
        if(letPass.contains(uri)){
            return ;
        }
        String sid = "";
        CookieBox box = new CookieBox(request, response);
        Cookie ck = box.getCookie("EQP_B_SID");
        if (ck != null) {
            sid = ck.getValue();
        }
        if (StringUtil.isEmpty(sid)) {
            throw new RespException(-100, "用户未登录!");
        }
        //根据cookie获取当前登录用户
        RespWrapper<Admin> resp = adminService.getAdminBySid(sid);
        if(resp.getErrCode()!=0 || resp.getObj()==null){
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        request.setAttribute("admin", resp.getObj());

        //权限认证
        Admin admin = resp.getObj();
        boolean isCheck = false;
        if (commonURI.contains(uri)) {
            isCheck = true;
        } else if (admin.getRoleId() == AdminRole.ADMIN.getValue() && SysURI.contains(uri)) {
            isCheck = true;
        } else if (admin.getRoleId() == AdminRole.AGENCY.getValue() && AgencyURI.contains(uri)) {
            isCheck = true;
        } else {
            isCheck = false;
        }

        System.out.println("获取用户："+request.getAttribute("admin").toString());

        if (!isCheck) {
            throw new RespException(1001, "您没有该权限!");
        }

    }

    //初始化权限集合
    private static void initList() {
        //基础,都有
        commonURI.add("/ssyManager/admin/login");
        commonURI.add("/ssyManager/getLoginAdmin");
        commonURI.add("/ssyManager/msg/getMsgByPage");
        commonURI.add("/ssyManager/msg/getNewMsg");
        //报表, 都有
        commonURI.add("/ssyManager/index/getSelectValue");
        commonURI.add("/ssyManager/index/getAreaItemCount");
        commonURI.add("/ssyManager/index/getInstallAreaChart");
        commonURI.add("/ssyManager/index/getSupplierChart");
        commonURI.add("/ssyManager/index/getEqpTimeChart");
        commonURI.add("/ssyManager/index/getEqpStatusChart");
        commonURI.add("/ssyManager/index/getEqpSafeChart");

        //系统用户
        SysURI.add("/ssyManager/admin/getOrgPage");
        SysURI.add("/ssyManager/admin/addOrg");
        SysURI.add("/ssyManager/admin/updateOrg");
        SysURI.add("/ssyManager/admin/getAgencyPage");
        SysURI.add("/ssyManager/admin/addAgency");
        SysURI.add("/ssyManager/admin/updateAgency");
        commonURI.add("/ssyManager/admin/updateSelf");
        commonURI.add("/ssyManager/admin/changePwd");

        //系统用户下拉选
        SysURI.add("/ssyManager/admin/getAdminStatusSelect");
        AgencyURI.add("/ssyManager/admin/getAdminStatusSelect");

        //2.4 log 管理员
        SysURI.add("/ssyManager/log/getLogPage");

        //2.5 org
        commonURI.add("/ssyManager/org/getOrgTreeSelect");
        SysURI.add("/ssyManager/org/addOrg");
        SysURI.add("/ssyManager/org/updateOrg");
        //添加管辖机构选择地区的时候使用
        SysURI.add("/ssyManager/org/checkOrgByRegion");
        //添加场地的时候使用
        AgencyURI.add("/ssyManager/org/getOneOrgByRegion");

        //2.6 region
        SysURI.add("/ssyManager/region/getRegionTreeSelect");
        SysURI.add("/ssyManager/region/addRegion");
        SysURI.add("/ssyManager/region/updateRegion");
        //超管, 管理公司
        commonURI.add("/ssyManager/region/getRegionList");
        commonURI.add("/ssyManager/region/getOneRegion");

        // 2.7 area
        SysURI.add("/ssyManager/area/getAreaPage");
        AgencyURI.add("/ssyManager/area/getAreaPage");
        AgencyURI.add("/ssyManager/area/addArea");
        AgencyURI.add("/ssyManager/area/updateArea");
        commonURI.add("/ssyManager/area/getProvideWaySelect");
        commonURI.add("/ssyManager/area/getAreaSelect");

        // 2.8 qrcode
        SysURI.add("/ssyManager/qrcode/getQrcodePage");
        AgencyURI.add("/ssyManager/qrcode/getQrcodePage");
        SysURI.add("/ssyManager/qrcode/addQrcode");
        SysURI.add("/ssyManager/qrcode/deleteQrcode");
        SysURI.add("/ssyManager/qrcode/exportQrcode");
        SysURI.add("/ssyManager/qrcode/getExportPage");

        // 2.9 supplier
        SysURI.add("/ssyManager/supplier/getSupplierPage");
        SysURI.add("/ssyManager/supplier/addSupplier");
        SysURI.add("/ssyManager/supplier/updateSupplier");
        commonURI.add("/ssyManager/supplier/getSupplierList");

        //2.10 eqpsort
        commonURI.add("/ssyManager/eqpsort/getEqpsortTreeSelect");
        SysURI.add("/ssyManager/eqpsort/addEqpsort");
        SysURI.add("/ssyManager/eqpsort/updateEqpsort");
        commonURI.add("/ssyManager/eqpsort/getEqpsortSelect");
        commonURI.add("/ssyManager/eqpsort/getProjectSortSelect");

        //2.11 eqp
        SysURI.add("/ssyManager/eqp/getEqpPage");
        SysURI.add("/ssyManager/eqp/addEqp");
        SysURI.add("/ssyManager/eqp/updateEqp");
        commonURI.add("/ssyManager/eqp/getEqpList");

        //2.12 item
        commonURI.add("/ssyManager/item/getItemPage");
        commonURI.add("/ssyManager/item/exportItem");
        commonURI.add("/ssyManager/item/getStandardcodeSelect");
        commonURI.add("/ssyManager/item/getInstallYearSelect");

        //2.13 itemflow
        commonURI.add("/ssyManager/itemflow/getItemflowPage");
        commonURI.add("/ssyManager/itemflow/exportItemflow");
        commonURI.add("/ssyManager/itemflow/getItemflowInfo");

        //2.14 itemrepair
        commonURI.add("/ssyManager/itemrepair/getRepairPage");
        commonURI.add("/ssyManager/itemrepair/exportItemrepair");
        commonURI.add("/ssyManager/itemrepair/getItemrepairInfo");

        //2.14 brokenreason
        commonURI.add("/ssyManager/brokenreason/getBrokenreasonTreeSelect");

        //2.15 upload
        SysURI.add("/ssyManager/upload/uploadFile");
        SysURI.add("/ssyManager/upload/uploadVideo");
        SysURI.add("/ssyManager/upload/uploadVideo2");

        // 管理公司
        AgencyURI.add("/ssyManager/user/getUserPage");
        AgencyURI.add("/ssyManager/user/addUser");
        AgencyURI.add("/ssyManager/user/updateUser");
        commonURI.add("/ssyManager/user/getItemBySelect");

        //消息提醒
        AgencyURI.add("/ssyManager/msg/getNewMsg");
        AgencyURI.add("/ssyManager/msg/getMsgByPage");

        // letPass 不拦截地址
            //阿里云数据报表
            letPass.add("/ssyManager/ali/aliData");
            //用户登录
            letPass.add("/ssyManager/login");
            //开放市民扫码模块
            letPass.add("/ssyManager/mobile/getAreaInfoByItemsn");
            letPass.add("/ssyManager/mobile/getItemInfoByItemsn");
            letPass.add("/ssyManager/mobile/handle");
            //TODO 临时开放接口，正式去除
            letPass.add("/ssyManager/upload/uploadBase64");
    }
}
