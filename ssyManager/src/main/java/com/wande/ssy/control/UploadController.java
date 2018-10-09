package com.wande.ssy.control;

import com.wande.ssy.utils.*;
import com.wande.ssy.utils.qiniu.QiniuUtil;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.webutil.NetUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController {

    //文件上传地址
    @Value("${eqp.uploadPath}")
    private String webRootPath;


    /**
     *
    */
    @RequestMapping("/uploadBase64")
    public Object uploadBase64(HttpServletRequest request){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
        String imgdata = "";
        imgdata = multipartRequest.getParameter("file");

        //web应用目录  E:\workspace\service_eqp\WebContent
        String webPath = this.webRootPath;
        System.out.println("文件上传地址："+webPath);
        //去掉最后一个/的域名地址 : http://192.168.1.160:90
        String hostPath = PathUtil.getHostPathExt(request);
        String savePath = UploadConfig.Upload_Repair_Dir + DateTimeUtil.formatDate("yyyyMMdd") + UploadConfig.path_separator;
        String fileName = DateTimeUtil.formatDate("yyyyMMddHHmmss");
        long maxSize = 1024 * 1024 * 2;		//2M
        UploadMsg ret = UploadUtil.saveFileByBase64(webPath + savePath, fileName, imgdata, "image", maxSize);
        if (ret.getErrCode() == 0) {
            return  RespWrapper.makeResp(0, "上传成功", hostPath + savePath + ret.getObj());
        } else {
            return RespWrapper.makeResp(1001, ret.getErrMsg(), null);
        }
    }


    /**
     *  上传文件
    */
    @RequestMapping("/uploadFile")
    public Object uploadFile(HttpServletRequest request,
                             HttpServletResponse response){
        //这样传值是为了上传图片文件夹管理
        String type = NetUtil.getStringParameter(request, "type", "image");
        String dir  = NetUtil.getStringParameter(request, "dir", "image");

        response.setContentType("text/html; charset=UTF-8");
        if(!ServletFileUpload.isMultipartContent(request)){
            return RespWrapper.makeResp(1001, "请选择上传文件!", null);
        }
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        List<FileItem> items;
        try {
            items = upload.parseRequest(request);
            System.out.println(items.size());
            //web应用目录  E:\workspace\service_eqp\WebContent
            String webPath = this.webRootPath;
            //去掉最后一个/的域名地址 : http://192.168.1.160:90
            String hostPath = PathUtil.getHostPathExt(request);
            //实际保存文件的文件夹路径
            String savePath = UploadConfig.getUploadDir(dir) + DateTimeUtil.formatDate("yyyyMM") + UploadConfig.path_separator;
            String fileName = DateTimeUtil.formatDate("yyyyMMddHHddss");
            UploadMsg res = UploadUtil.saveSinFileByList(items, webPath+savePath, fileName, type);
            if (res.getErrCode() == 0) {
                String ret = hostPath + savePath + (String) res.getObj();
               return RespWrapper.makeResp(0, "", ret);
            } else {
                return  RespWrapper.makeResp(1001, res.getErrMsg(), null);
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
           return RespWrapper.makeResp(1001, "系统繁忙!", null);
        }
    }



    /**
     *  上传视频
    */
    @RequestMapping("/uploadVideo")
    public Object uploadVideo(HttpServletRequest request,
                              HttpServletResponse response){

        //这样传值是为了上传图片文件夹管理
        String type = NetUtil.getStringParameter(request, "type", "image");
        String dir  = NetUtil.getStringParameter(request, "dir", "image");
        System.out.println(dir+type);

        response.setContentType("text/html; charset=UTF-8");
        if(!ServletFileUpload.isMultipartContent(request)){
            return RespWrapper.makeResp(1001, "请选择上传文件!", null);

        }
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        List<FileItem> items;
        try {
            Map<String,String> resp = new HashMap<>();

            items = upload.parseRequest(request);
            //web应用目录  E:\workspace\service_eqp\WebContent
            String webPath = this.webRootPath;
            //去掉最后一个/的域名地址 : http://192.168.1.160:90
            String hostPath = PathUtil.getHostPathExt(request);
            //实际保存文件的文件夹路径
            String savePath = UploadConfig.getUploadDir(dir) + DateTimeUtil.formatDate("yyyyMM") + UploadConfig.path_separator;
            String fileName = DateTimeUtil.formatDate("yyyyMMddHHddss");
            UploadMsg res = UploadUtil.saveSinFileByList(items, webPath+savePath, fileName, type);
            if (res.getErrCode() == 0) {
                String ret = hostPath + savePath + (String) res.getObj();
                resp.put("hostPath", hostPath);
                resp.put("savePaht", savePath);
                resp.put("webPaht", webPath);
                resp.put("video_local", ret);
                if(QiniuUtil.used) {
                    System.out.println("webPath:"+webPath+"...savePath:"+savePath+"    res.getObj():"+(String) res.getObj());

                    String fileDiskPath = webPath + savePath + (String) res.getObj();
                    System.out.println("fileDiskPath: "+fileDiskPath);
                    ret = QiniuUtil.UploadFile(fileDiskPath);
                    resp.put("video", ret);
                }

               return RespWrapper.makeResp(0, "", resp);
            } else {
                return RespWrapper.makeResp(1001, res.getErrMsg(), null);
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
          return  RespWrapper.makeResp(1001, "系统繁忙!", null);
        }

    }








}
