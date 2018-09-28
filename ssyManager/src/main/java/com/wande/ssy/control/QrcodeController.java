package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.*;
import com.wande.ssy.entity.*;
import com.wande.ssy.enums.AdminRole;
import com.wande.ssy.enums.QrcodeStatus;
import com.wande.ssy.utils.DateTimeUtil;
import com.wande.ssy.utils.POIUtil;
import com.wande.ssy.utils.PathUtil;
import com.wande.ssy.utils.UploadConfig;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespException;
import com.ynm3k.mvc.model.RespWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/qrcode")
public class QrcodeController {

    @Reference(interfaceClass=QrcodeService.class)
    private QrcodeService qrcodeService;

    @Reference(interfaceClass=SupplierService.class)
    private SupplierService supplierService;

    @Reference(interfaceClass=EqpService.class)
    private EqpService eqpService;

    @Reference(interfaceClass=AdminService.class)
    private AdminService adminService;



    /**
     *  添加二维码
    */
    @RequestMapping("/addQrcode")
    public Object addQrcode(@Valid Qrcode obj ,
                            BindingResult bindingResult,
                            @RequestParam(value = "qrcodeNum",defaultValue = "0") Integer qrcodeNum,  //生成数量
                            HttpServletRequest request){
        //效验参数
        String attribute;  //当前效验的参数名称
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            attribute = fieldError.getField();
            if (attribute.equals("aaa")) {
                //跳过不需要验证的参数
                continue;
            }
            log.info("【添加二维码】，{}参数错误： {} ",attribute,fieldError.getDefaultMessage());
            return new RespWrapper(1001,"【 "+attribute+" 】"+fieldError.getDefaultMessage(),null);
        }
        if (qrcodeNum == 0) {
            throw new RespException(1001, "生成二维码数量不能为空!");
        }
        if (qrcodeNum > 50) {
            throw new RespException(1001, "生成二维码数量不能大于50个!");
        }
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");
        //封装参数
        //调用远程Service
        RespWrapper<String> resp = qrcodeService.addQrcode(qrcodeNum, obj.getAreaId(), obj.getStandardcode(), admin, obj.getIsAreaQrcode());
        //返回结果
        return resp;
    }


    /**
     *  删除二维码
     *  @param  qrcodeId 二维码ID
    */
    @RequestMapping("/deleteQrcode")
    public Object deleteQrcode(@RequestParam(value = "qrcodeId",defaultValue = "0") Integer qrcodeId){ 	//二维码ID
        //参数校验
        if (qrcodeId == 0) {
            throw new RespException(1001, "二维码ID不能为空!");
        }
        //调用远程Service
        RespWrapper<Boolean> resp = qrcodeService.deleteQrcode(qrcodeId);
        //返回结果
        return resp;
    }



    /**
     * 导出二维码(未出厂)
    */
    @RequestMapping("/exportQrcode")
    public Object exportQrcode(HttpServletResponse response){

        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");

        RespWrapper<List<Qrcode>> resp = qrcodeService.getExportList();
        if (resp.getErrCode() != 0) {
            throw new RespException(1001, resp.getErrMsg());
        }
        if (resp.getObj().size() <= 0) {
            throw new RespException(1001, "没有可导出的二维码!");
        }

        // 修改导出的二维码的状态
        String qrcodeIds = "";
        int qrcodeNum = 0;
        for (Qrcode q : resp.getObj()) {
            qrcodeIds += q.getQrcodeId() + ",";
            qrcodeNum++;
        }
        qrcodeIds = qrcodeIds.length() == 0 ? "''" : qrcodeIds.substring(0, qrcodeIds.length()-1);
        RespWrapper<Boolean> resp2 = qrcodeService.updateStatue(QrcodeStatus.OUT.getValue(), qrcodeIds);

        //新增导出
        Export export = new Export();
        export.setExportTime(System.currentTimeMillis());
        export.setExportNum(qrcodeNum);
        export.setExportBy(admin.getUin());
        qrcodeService.addExport(export, qrcodeIds);

        if (resp2.getErrCode() == 0) {
            //将导出文件保存到本地
            String fileName = DateTimeUtil.formatDate("yyyy-MM-dd_HHmmss") + "(" + admin.getUin() + ").xls";
            String fileDir = PathUtil.getWebRootPath() + UploadConfig.Excel_Qrcode_Dir + DateTimeUtil.formatDate("yyyy-MM-dd") + "/" + fileName;
            String sheetName = "qrcode";
            POIUtil poiUtil = new POIUtil(fileDir, sheetName);
            String titleColumn[] = {"qrcodeId", "code", "url"};
            String titleName[] = {"ID", "编号", "实际URL"};
            int titleSize[] = {13, 13, 13};
            poiUtil.Bean2Excel(titleColumn, titleName, titleSize, resp.getObj());
//				ServletUtil.instance.writeToJSON(request, response, resp);
            try {
                //设置response, 导出记录
                response.reset();
                response.setContentType("application/x-msdownload");
                response.setHeader("Location",fileName);
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                OutputStream ou = response.getOutputStream();
                InputStream in = new FileInputStream(fileDir);
                byte[] buffer = new byte[1024];
                int i = -1;
                while ((i = in.read(buffer)) != -1) {
                    ou.write(buffer, 0, i);
                }
                ou.flush();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RespException(1001, "系统繁忙!");
            }
        } else {
            throw new RespException(1001, "系统繁忙!");
        }
        return null;
    }



    /* 查询导出记录分页列表
     * @param: [pageNo, pageSize, startTime, endTime, areaType]
     * @param: startTime 开始时间
     * @param: endTime  结束时间
     * @return: java.lang.Object */
    @RequestMapping("/getExportPage")
    public Object getExportPage(@RequestParam(value = "pageNo" ,defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                                @RequestParam(value = "startTime",defaultValue ="0") Long startTime,
                                @RequestParam(value = "endTime",defaultValue = "0") Integer endTime,
                                @RequestParam(value = "areaType",defaultValue = "0") Integer areaType){
        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");

        if (startTime != 0 && endTime != 0 && startTime > endTime) {
            throw new RespException(1001, "开始时间必须小于结束时间!");
        }
        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
        if (startTime != 0) {
            params.put("startTime", startTime);
        }
        if (endTime != 0) {
            params.put("endTime", endTime);
        }
        RespWrapper<DataPage<Export>> resp = qrcodeService.getExportByPage(params, pageNo, pageSize);
        if (resp.getErrCode() != 0 && resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        List<Export> ret = resp.getObj().getRecord();
        for (Export o : ret) {
            o.set("exportName", o.getExportBy() == 1 ? "超级管理员" : "");      //暂时只有超级管理员有权限可以导出, 所以写死, 减少SQL查询
        }
        //返回结果
        return resp;
    }
    
    
    
    /**
     * 获取二维码分页列表
    */
    @RequestMapping("/getQrcodePage")
    public Object getQrcodePage(@Valid Item item,
                                @RequestParam(value = "status",defaultValue = "-1") Integer status, //状态0无 1未出厂 2出厂 3使用
                                @RequestParam(value = "exportId",defaultValue = "-1") Integer exportId, //导出记录ID
                                @RequestParam(value = "startTime",defaultValue = "0") Long startTime, //开始时间
                                @RequestParam(value = "endTime",defaultValue = "0") Long endTime, //结束时间
                                @RequestParam(value = "keyword",defaultValue = "") String keyword, //关键字:二维码编号
                                @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo, //当前页
                                @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize){

        //TODO 当前登录用户
        //Admin admin = NetUtil.getAttribute(request, "admin", Admin.class); 	// 当前登录用户;
        Admin admin = new Admin();
        admin.setRoleId(1);
        admin.setUin(8L);
        admin.setAccount("admin");

        if (startTime != 0 && endTime != 0 && startTime > endTime) {
            throw new RespException(1001, "开始时间必须小于结束时间!");
        }

        //设置参数,调用远程Service
        Map<String,Object> params = new HashMap<String,Object>();
        if (admin.getRoleId() == AdminRole.AGENCY.getValue()) {
            params.put("agencyId", admin.getUin());
        }else {
            params.put("agencyId", -1L);
        }
            params.put("status",status);
            params.put("exportId",exportId);
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            params.put("keyword",keyword);

        RespWrapper<DataPage<Qrcode>> resp = qrcodeService.getQrcodeByPage(params, pageNo, pageSize);
        if (resp.getErrCode() != 0 || resp.getObj() == null) {
            throw new RespException(resp.getErrCode(), resp.getErrMsg());
        }
        //扩展数据
        List<Qrcode> ret = resp.getObj().getRecord();
        System.out.println("查询出的二维码集合大小：" + ret.size());
        for (Qrcode o : ret) {
            Supplier supplier = supplierService.getOneSupplier(o.getSupplierId()).getObj();
            Eqp eqp = eqpService.getOneEqp(o.getEqpId()).getObj();
            Admin agency = adminService.getAdminByUin(o.getAgencyId()).getObj();
            o.set("supplierName", supplier == null ? "" : supplier.getName());
            o.set("eqpName", eqp == null ? "" : eqp.getName());
            o.set("agencyName", agency == null ? "" : agency.getName());
        }
        // 返回结果
        return resp;
    }




}
