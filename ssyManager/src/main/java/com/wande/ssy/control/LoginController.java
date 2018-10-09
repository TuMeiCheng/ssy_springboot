package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.AdminService;
import com.wande.ssy.dubbo.provider.service.LogService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.enums.AdminRole;
import com.wande.ssy.utils.BeanUtil;
import com.wande.ssy.utils.LogUtil;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.webutil.NetUtil;
import com.ynm3k.utils.string.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class LoginController {


    @Reference(interfaceClass=AdminService.class)
    private AdminService adminService;

    @Reference(interfaceClass=LogService.class)
    private LogService logService;


    /**
     *  系统用户登录
     */
    @RequestMapping("/login")
    public Object Login(HttpServletRequest request,
                        HttpServletResponse response){

        //获取参数
        String account = NetUtil.getStringParameter(request, "account", "");
        String password = NetUtil.getStringParameter(request, "password", "");
        System.out.println("账户:"+account+" 密码："+password);

        //参数校验
        if (StringUtil.isEmpty(account) || StringUtil.isEmpty(password)) {
            throw new RespException(1001, "用户名或密码不能为空!");
        }
        //调用远程Service
        RespWrapper<String> resp = adminService.login(account, password, "");

        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        Admin admin = adminService.getAdminBySid(resp.getObj()).getObj();
        if (admin != null){
            logService.addLog(LogUtil.getLog(admin, request, admin.getAccount() + "用户账号登录:"));
        }
        //TODO 此处向请求端写入cookie 保存登录用户信息sid  正式服务器可能是由前端来写存cookie，到时候可以去掉
        //将用户登录信息写入cookie
        Cookie cookie = new Cookie("EQP_B_SID",resp.getObj());
        System.out.println(resp.getObj());
        response.addCookie(cookie);
        //返回结果
        return resp;
    }


    /**
     * 获取当前登录用户
     */
    @RequestMapping("/getLoginAdmin")
    public Object getLoginAdmin(HttpServletRequest request){
        Admin obj = NetUtil.getAttribute(request, "admin", Admin.class);
        if (obj == null) {
            throw new RespException(1001, "登录已过期,无法获取!");
        }
        Map<String, Object> sb =  BeanUtil.beanToMap(obj);
        sb.put("roleName", AdminRole.getName(obj.getRoleId()));
        sb.put("isAdmin", obj.getRoleId() == -10000);
        return RespWrapper.makeResp(0, "当前登录用户!", sb);
    }



}
