package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.ExportDao;
import com.wande.ssy.dao.QrcodeDao;
import com.wande.ssy.dubbo.provider.service.QrcodeService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Export;
import com.wande.ssy.entity.Qrcode;
import com.wande.ssy.enums.QrcodeStatus;
import com.wande.ssy.utils.DateTimeUtil;
import com.wande.ssy.utils.RandomUtil;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = QrcodeService.class)
public class QrcodeServiceImpl implements QrcodeService {

    @Autowired
    private QrcodeDao qrcodeDao;

    @Autowired
    private ExportDao exportDao;

    //二维码扫码地址
    @Value("${qrcode.prex}")
    private String Qrcode_Prex;


    @Override
    public RespWrapper<String> addQrcode(int qrcodeNum, int areaId, int standardcode, Admin admin, int isAreaQrcode) {
        int count = 0;
        List<Qrcode> qrcode_list = new ArrayList<Qrcode>(qrcodeNum);
        for (int num=0; num<qrcodeNum; num++) {
            String codePrex = DateTimeUtil.formatDate("yyyyMM");
            String code = getUniqueCode(codePrex);
            Qrcode q = new Qrcode();
            if(isAreaQrcode == 1) {
                q.setCode("ar_"+code);
                q.setUrl(Qrcode_Prex + "ar_" + code);
            }
            else {
                q.setCode(code);
                q.setUrl(Qrcode_Prex + code);
            }
            q.setAreaId(areaId);
            q.setStandardcode(standardcode);
            q.setCodePrex(codePrex);
            q.setStatus(QrcodeStatus.UNOUT.getValue());
            q.setIsdel(0);
            q.setCreateTime(System.currentTimeMillis());
            q.setCreateBy(admin.getUin());
            q.setIsAreaQrcode(isAreaQrcode);

            qrcode_list.add(q);

        }

        if (qrcodeDao.insert(qrcode_list)) {
            return RespWrapper.makeResp(0, "", "添加" + qrcodeNum + "个二维码成功!");
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", null);
        }
    }

    @Override
    public RespWrapper<DataPage<Qrcode>> getQrcodeByPage(Map<String, Object> params, int pageNo, int pageSize) {
        DataPage<Qrcode> page = qrcodeDao.getQrcodeByPage(params, pageNo, pageSize);
        return RespWrapper.makeResp(0, "", page);
    }

    @Override
    public RespWrapper<Boolean> deleteQrcode(int qrcodeId) {

        Qrcode qrcode = qrcodeDao.getOneQrcode(qrcodeId);
        if (qrcode == null) {
            return RespWrapper.makeResp(1001, "该二维码不存在!", null);
        }
        if (qrcode.getStatus() != QrcodeStatus.UNOUT.getValue()) {
            return RespWrapper.makeResp(1001, "已出厂使用的二维码无法删除!", null);
        }
        boolean rs = qrcodeDao.deleteQrcode(qrcodeId);
        if (rs) {
            return RespWrapper.makeResp(0, "", true);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", false);
        }

    }

    @Override
    public RespWrapper<List<Qrcode>> getExportList() {
        return RespWrapper.makeResp(0, "", qrcodeDao.getExportList());
    }

    @Override
    public RespWrapper<Boolean> updateStatue(int status, String qrcodeIds) {
        boolean rs = qrcodeDao.updateStatus(status, qrcodeIds);
        if (rs) {
            return RespWrapper.makeResp(0, "", true);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", false);
        }

    }

    @Override
    public RespWrapper<Boolean> addExport(Export obj, String qrcodeIds) {
        int rs = exportDao.insert(obj);
        if (rs > 0) {
            boolean rs2 = qrcodeDao.updateExport(rs, qrcodeIds);
            if (rs2) {
                return RespWrapper.makeResp(0, "", true);
            } else {
                return RespWrapper.makeResp(1001, "系统繁忙!", false);
            }
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", false);
        }
    }

    @Override
    public RespWrapper<DataPage<Export>> getExportByPage(Map<String, Object> params, int pageNo, int pageSize) {
        DataPage<Export> page = exportDao.getExportByPage(params, pageNo, pageSize);
        return RespWrapper.makeResp(0, "", page);
    }

    /**
     * 获取唯一存在的二维码
     * @param codePrex	二维码前缀
     * @return
     */
    private String getUniqueCode(String codePrex){
        String code = codePrex + RandomUtil.createCode(7, "1,2");
        while(qrcodeDao.checkCodeExist(code)){
            code = codePrex + RandomUtil.createCode(7, "1,2");
        }
        return code;
    }
}
