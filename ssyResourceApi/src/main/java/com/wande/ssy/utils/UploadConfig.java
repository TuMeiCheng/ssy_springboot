package com.wande.ssy.utils;

/**
 * 上传配置管理
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月12日下午4:07:40
 */
public class UploadConfig {

	public static final String extendion_separator = ",";// 文件扩展名分隔符
	public static final String path_separator = "/";// 文件扩展名分隔符
	
//	public static final String Default_empty = "/upload/empty.png";	//默认空图片
	public static final String Default_user_img = "/upload/user.jpg";	//默认用户头像
	
	public static final String Upload_DIR = "/upload";	//上传路径
	
//	public static final String Upload_flow       = "/image/";		// 巡检图片上传路径
//	public static final String Upload_area       = "/area/";		// 场地上传图片
//	public static final String Upload_user       = "/user/";		// 头像上传目录
	public static final String Upload_media      = "/media/";		// 媒体文件上传目录
	public static final String Upload_mediaImg   = "/mediaImg/";	// 媒体缩略图
	public static final String Upload_other      = "/other/";		// 默认上传文件夹
//	public static final String Upload_File_Dir   = "/file/";		// 其它文件上传目录
	public static final String Upload_Repair_Dir = "/upload/repair/";		// 巡检上传路径
	
	public static final String Excel_flow_Dir    = "/upload/excel/flow/";	//导出巡检记录excel文件目录
	public static final String Excel_repair_Dir  = "/upload/excel/repair/";	//导出巡检记录excel文件目录
	public static final String Excel_Qrcode_Dir  = "/upload/excel/qrcode/";	//导出二维码记录excel文件目录
	public static final String Excel_Item_Dir    = "/upload/excel/item/";	//导出器材记录excel文件目录
	
	/**
	 * 根据 上传类型获取对应上传路径
	 * 目前只有video和videoImg
	 * 
	 * @param dir
	 * @return
	 */


	
	/**=====================   截取视频图片的设置    ======================*/
	public static final String FFMPEG_DIR = "D:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe";
	public static final int FFMPEG_WIDTH = 850;	//截取宽度
	public static final int FFMPEG_HEIGHT = 480;	//截取高度
	public static final int FFMPEG_HOUT = 0;	//截取的小时
	public static final int FFMPEG_MIN = 0;		//截取的分钟
	public static final int FFMPEG_SECOND = 2;	//截取的秒钟
	
	/**=====================   APP    ======================*/
	public static final float appVersion = 1.1F;
	public static final String appDownloadUrl = "/api/app/download";
	
	/**
	 * 获取APP版本号
	 * 
	 * @return
	 */
	public static Float getAppVersion() {
		return appVersion;
	}	
	
	/**
	 * 获取APP下载路径
	 * 
	 * @param reqiest
	 * @return
	 */
	//public static String getAppDownloadUrl(HttpServletRequest reqiest) {
	//	return PathUtil.getHostPathExt(reqiest) + appDownloadUrl;
	//}
}
