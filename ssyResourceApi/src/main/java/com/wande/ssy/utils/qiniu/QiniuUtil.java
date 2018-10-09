package com.wande.ssy.utils.qiniu;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.wande.ssy.utils.DateTimeUtil;
import com.ynm3k.utils.conf.ConfigManager;

import java.util.Random;

public class QiniuUtil {
	
	// 七牛云的秘钥是成对儿的    
	// 官网的建议:AK允许包含在数据传输中,而SK只允许保存在服务器
    // 以 SecretKey 为参数，配合适当的签名算法，可以得到原始信息的数字签名，防止内容在传递过程中被伪造或篡改。(非服务器直接上传的情况下)
	public  static String accessKey = "LEHu0IqREi17E4E8K48m_fKaYnWQjHipkZlJwSIb";
	public  static String secretKey = "QHYMg8lYnNNDjePSjyq8LQAXotcKJhvXf922lwua";
	// 存储空间名称
	public  static String videoBucket = "wande-video";
	public  static String urlPath= "http://file.wandetech.com"; 
	
	public static boolean used = false;
	
	static{
		used = ConfigManager.getBoolByKey("upload.qiniu_used", false);
		accessKey = ConfigManager.getStringByKey("upload.qiniu_accessKey", "");
		secretKey = ConfigManager.getStringByKey("upload.qiniu_secretKey", "");
		videoBucket = ConfigManager.getStringByKey("upload.qiniu_videoBucket", "");
		urlPath = ConfigManager.getStringByKey("upload.qiniu_urlPath", "");
	}
	
	
	/**
	 * 获取一定长度的随机大写英文字符串
	 * @param length  指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String RandomStr(int length) {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static String UploadFile(String localFilePath){
		Configuration cfg = new Configuration(Zone.autoZone());
		UploadManager uploadManager = new UploadManager(cfg);
		String fileName = DateTimeUtil.formatDate("yyyyMM")+"/" + DateTimeUtil.formatDate("yyyyMMddHHmmssSSS") + RandomStr(4);
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(videoBucket);
		try {
		    Response response = uploadManager.put(FileUtil.File2byte(localFilePath), fileName, upToken);
		    //解析上传成功的结果
		    DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
		    return urlPath+"/"+putRet.key;
		} catch (QiniuException ex) {
		    Response r = ex.response;
		    System.err.println(r.toString());
		    try {
		        System.err.println(r.bodyString());
		    } catch (QiniuException ex2) {
		        //ignore
		    }
		    return null;
		}
	}
	public static void main(String[] args) {
		System.out.println(QiniuUtil.used);
		
		String localFilePath = "http://hwdeal.com/upload/media/201805/20180525092541.mp4";
		String url = QiniuUtil.UploadFile(localFilePath);
		System.out.println(url);
		
		// http://file.wandetech.com/201807/20180707154458981ISXC  E:\51eq
	}
	
}
