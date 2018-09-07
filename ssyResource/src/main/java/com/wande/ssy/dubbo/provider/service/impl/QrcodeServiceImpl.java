package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.QrcodeDao;
import com.wande.ssy.dubbo.provider.service.QrcodeService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Export;
import com.wande.ssy.entity.Qrcode;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = QrcodeService.class)
public class QrcodeServiceImpl implements QrcodeService {

    @Autowired
    private QrcodeDao qrcodeDao;


    @Override
    public RespWrapper<String> addQrcode(int qrcodeNum, int areaId, int standardcode, Admin admin, int isAreaQrcode) {
        return null;
    }

    @Override
    public RespWrapper<DataPage<Qrcode>> getQrcodeByPage(Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> deleteQrcode(int qrcodeId) {
        return null;
    }

    @Override
    public RespWrapper<List<Qrcode>> getExportList() {
        return null;
    }

    @Override
    public RespWrapper<Boolean> updateStatue(int status, String qrcodeIds) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> addExport(Export obj, String qrcodeIds) {
        return null;
    }

    @Override
    public RespWrapper<DataPage<Export>> getExportByPage(Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }
}
