package com.wande.ssy.dao;

import com.wande.ssy.entity.Qrcode;
import com.ynm3k.mvc.model.DataPage;

import java.util.List;
import java.util.Map;

public interface QrcodeDao {

    //检查二维码是否存在相同code唯一标识
    boolean checkCodeExist(String code);

    //添加二维码
    boolean insert(List<Qrcode> qrcode_list);

    //根据id查询二维码
    Qrcode getOneQrcode(int qrcodeId);

    //删除二维码
    boolean deleteQrcode(int qrcodeId);

    /**
     * 导出未出厂记录的二维码
     * @return
     */
    List<Qrcode> getExportList();


    /**
     * 更新二维码状态
     * @param status
     * @param qrcodeIds
     * @return
     */
    boolean updateStatus(int status, String qrcodeIds);

    /**
     * 更新二维码所属导出记录
     * @return
     */
    boolean updateExport(int rs, String qrcodeIds);

    // 分页多条件查询
    DataPage<Qrcode> getQrcodeByPage(Map<String, Object> params, int pageNo, int pageSize);
}
