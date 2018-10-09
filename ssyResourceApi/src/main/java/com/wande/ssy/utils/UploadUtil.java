package com.wande.ssy.utils;

import com.ynm3k.utils.crypto.BASE64Coding;
import org.apache.commons.fileupload.FileItem;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 * 上传文件工具类(Base64字符串, 文件流)
 * 当程序启动时候,可以调用init()进行配置的初始化(可以使用properties进行初始化)
 * 
 * 获取web项目根目录
 * request.getRealPath("/");
 * request.getSession().getServletContext().getRealPath("/");
 * 输出:E:\workspace\service_eqp\WebContent
 * 
 * 获取域名
 * StringBuffer url = request.getRequestURL(); 
 * String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
 * 输出:http://192.168.1.160:90/
 * 
 * 需要commons-fileupload-1.3.1.jar, commons-io-2.4.jar
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年2月8日 下午5:13:02
 */
public class UploadUtil {
    
    private static final long FILE_COPY_BUFFER_SIZE = 1024 * 1024 * 30;
	
	private static HashMap<String, String> allowMap = new HashMap<String, String>();
	static {
		allowMap.put("image", "gif,jpg,jpeg,png,bmp");
		allowMap.put("media", "swf,flv,mp3,mp4,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		allowMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		allowMap.put("other", "");
	}

	/** 默认上传大小为1M */
	private static long maxSize = 1024*1024*1;	
	
	public static void init(long maxSize) {
		setMaxSize(maxSize);
	}
	
	/**
	 * 如果传入的除了maxSize的其他参数为null或者为空, 则采取默认配置
	 * 
	 * @param maxSize	上传大小的限制
	 * @param image		允许上传的图片类型
	 * @param media		允许上传的媒体类型
	 * @param file		允许上传的文件类型
	 * @param other		允许上传的其他文件类型
	 */
	public static void init(long maxSize, String image, String media, String file, String other) {
		setMaxSize(maxSize);
		if (image != null | image.equals("")) setAllowImage(image);
		if (media != null | media.equals("")) setAllowMedia(media);
		if (file != null | file.equals("")) setAllowFile(file);
		if (other != null | other.equals("")) setAllowOther(other);
	}
	
	/**
	 * 如果properties 为空, 则采用默认配置
	 * @param properties
	 */
	public static void init(Properties properties) {
		if (properties != null) {
			init(Long.parseLong((String)properties.get("maxSize")), 
					(String) properties.get("image"), (String) properties.get("media"), 
					(String) properties.get("file"), (String) properties.get("other"));
		}
	}
	
	public static String getAllowImage() {
		return allowMap.get("image");
	}
	
	public static void setAllowImage(String allowFiles){
		allowMap.put("image", allowFiles);
	}

	public static String getAllowMedia() {
		return allowMap.get("media");
	}
	
	public static void setAllowMedia(String allowFiles){
		allowMap.put("media", allowFiles);
	}

	public static String getAllowFile() {
		return allowMap.get("file");
	}
	
	public static void setAllowFile(String allowFiles){
		allowMap.put("file", allowFiles);
	}

	public static String getAllowOther() {
		return allowMap.get("other");
	}
	
	public static void setAllowOther(String allowFiles){
		allowMap.put("other", allowFiles);
	}
	
	public static long getMaxSize() {
		return maxSize;
	}

	public static void setMaxSize(long maxSize) {
		UploadUtil.maxSize = maxSize;
	}

	/**
	 * Base64上传经过Base64编码的文件(一般上传图片)
	 * 例如
		String date = "data:image/png;base64,iVBORw0KGgoAAAA";
		UploadMsg a = uploadFileByBase64("F:/", "c", date, "image");
		System.out.println(a.getErrMsg());
	 * 
	 * @param saveDir		实际保存的物理地址
	 * @param fileName		保存的文件名
	 * @param base64Data	Base64文件数据(一般为图片)
	 * @param allowFiles	上传文件的类型选择(image, media, file, other)
	 */
	public static UploadMsg saveFileByBase64(String saveDir, String fileName, String base64Data, String allowFiles) {
		return saveFileByBase64(saveDir, fileName, base64Data, allowFiles, maxSize);
	}

	/**
	 * Base64上传经过Base64编码的文件(一般上传图片)
	 * 
	 * @param saveDir		实际保存的物理地址  如(F:/upload/)
	 * @param fileName		保存的文件名 (如 20160809)
	 * @param base64Data	Base64文件数据(一般为图片)(带前缀的数据)
	 * @param allowFiles	上传文件的类型选择(image, media, file, other)
	 * @param maxSize		临时配置的上传最大大小
	 * @return 所保存的文件名(如:2016.png)
	 */
	public static UploadMsg saveFileByBase64(String saveDir, String fileName, String base64Data, String allowFiles, long maxSize) {
		if (!allowMap.containsKey(allowFiles)) {
			return new UploadMsg(1, "输入的allowFiles不在读,可选参数有(image, media,file,other),分别代表图片文件,媒体文件,文件,其他文件", null);
		}
		byte[] data = BASE64Coding.decodeBuffer(base64Data.substring(base64Data.indexOf(",")+1));
		if (data == null || base64Data.equals("")) {
			return new UploadMsg(1, "数据为空!", null);
		}
		if (data.length > maxSize) {
			return new UploadMsg(1, "文件大小>" + maxSize + ",超过限制!", null);
		}
		String suffix = base64Data.substring(base64Data.indexOf("/")+1, base64Data.indexOf(";")).toLowerCase();
		if(!allowMap.get(allowFiles).contains(suffix)) {
			return new UploadMsg(1, "不允许的上传类型!只允许" + allowFiles +"!", null);
		}
		String filePath = saveDir + fileName + "." + suffix;
		File file = new File(filePath);
		File parentPath = file.getParentFile();
		if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
			return new UploadMsg(1, "创建文件夹失败!", null);
		}
		if (!parentPath.canWrite()) {
			return new UploadMsg(1, "上传目录没有写权限!", null);
		}
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(data);
			bos.flush();
			bos.close();
			return new UploadMsg(0, "上传成功!", fileName + "." + suffix);
		} catch (Exception e) {
			return new UploadMsg(1, "上传失败!", null);
		}
	}
	
	/**
	 * InputStream流 上传文件
	 * 上一层代码例子:
		if (!ServletFileUpload.isMultipartContent(request)) {
			System.out.println("请选择上传文件!");
			return;
		}
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;
		FileItemStream fileStream = null;
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		if (isAjaxUpload) {
			upload.setHeaderEncoding("UTF-8");
		}
		try {
			FileItemIterator iterator = upload.getItemIterator(getRequest());
			while (iterator.hasNext()) {
				fileStream = iterator.next();
				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}
			if(fileStream == null) {
				System.out.println("上传文件为空!");
			}
			InputStream is = fileStream.openStream();
			String uploadFileName = fileStream.getName();
			String suffix = uploadFileName.substring(uploadFileName.lastIndexOf("."));
			//实际物理保存位置
			String saveDir = "F:/";
			//实际保存的文件名称 + 文件后缀名
			String fileName = "test"+suffix;
			String allowFiles = "image";
			long maxSize = 1024*1024*2;
			UploadMsg msg = UploadUtil.uploadFileByInputStream(is, saveDir, fileName, allowFiles);
			UploadMsg msg = UploadUtil.uploadFileByInputStream(is, saveDir, fileName, allowFiles, maxSize);
			is.close();
			System.out.println("msg:"+msg.getErrCode() + "," + msg.getErrMsg());
			render("/test/success.html");
		} catch (FileUploadException e) {
		
		} catch (IOException localIOException) {

		}
	 * 
	 * @param is			文件的inputStream
	 * @param saveDir		保存的目录
	 * @param fileName		保存的文件名
	 * @param allowFiles	上传文件的类型选择(image, media, file, other)
	 * @return	UploadMsg	封装类,里面包含错误信息和错误码,以及反馈前端的对象(返回的图片url在上一层自己定义)
	 */
	public static UploadMsg saveFileByInputStream(InputStream is, String saveDir, String fileName, String allowFiles) {
		return saveFileByInputStream(is, saveDir, fileName, allowFiles, maxSize);
	}

	/**
	 * InputStream流 上传文件
	 * 
	 * @param is			文件的inputStream
	 * @param saveDir		保存的目录
	 * @param fileName		保存的文件名
	 * @param allowFiles	上传文件的类型选择(image, media, file, other)
	 * @param maxSize		临时配置上传文件的大小限制
	 * @return	UploadMsg	封装类,里面包含错误信息和错误码,以及反馈前端的对象(返回的图片url在上一层自己定义)
	 */
	public static UploadMsg saveFileByInputStream(InputStream is, String saveDir, String fileName, String allowFiles, long maxSize) {
		if (!allowMap.containsKey(allowFiles)) {
			return new UploadMsg(1, "输入的allowFiles不在读,可选参数有(image, media,file,other),分别代表图片文件,媒体文件,文件,其他文件", null);
		}
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		if (!allowMap.get(allowFiles).contains(suffix)) {
			return new UploadMsg(1, "不允许的上传类型!只允许" + allowFiles +"格式!", null);
		}
		fileName = fileName.substring(0, fileName.lastIndexOf(".")+1) + suffix;

		File tmpFile = getTmpFile();
		
		byte[] dataBuf = new byte[2048];
		BufferedInputStream bis = new BufferedInputStream(is, 8192);
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpFile), 8192);
			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();
			if (tmpFile.length() > maxSize) {
				tmpFile.delete();
				return new UploadMsg(1, "文件大小>" + maxSize + ",超过限制!", null);
			}
			return saveTmpFile(tmpFile, saveDir+fileName);
		} catch (IOException localIOException) {
			return new UploadMsg(1, "上传失败!", null);
		}
	}
	
	/**
	 * 无上限大小 的InputStream流 上传文件
	 * 
	 * @param is			文件的inputStream
	 * @param saveDir		保存的目录
	 * @param fileName		保存的文件名
	 * @param allowFiles	上传文件的类型选择(image, media, file, other)
	 * @return	UploadMsg	封装类,里面包含错误信息和错误码,以及反馈给前端的对象(返回的图片url在上一层自己定义)
	 */
	public static UploadMsg saveFileByInputStreamNoUpperSize(InputStream is, String saveDir, String fileName, String allowFiles) {
		if (!allowMap.containsKey(allowFiles)) {
			return new UploadMsg(1, "输入的allowFiles不在读,可选参数有(image, media,file,other),分别代表图片文件,媒体文件,文件,其他文件", null);
		}
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		if(!allowMap.get(allowFiles).contains(suffix)) {
			return new UploadMsg(1, "不允许的上传类型!只允许" + allowFiles +"格式!", null);
		}
		fileName = fileName.substring(0, fileName.lastIndexOf(".")+1) + suffix;
		
		File tmpFile = getTmpFile();
	    byte[] dataBuf = new byte[2048];
	    BufferedInputStream bis = new BufferedInputStream(is, 8192);
	    try {
	    	BufferedOutputStream bos = new BufferedOutputStream(
	        new FileOutputStream(tmpFile), 8192);
	    	
	    	int count = 0;
	    	while ((count = bis.read(dataBuf)) != -1) {
	    		bos.write(dataBuf, 0, count);
	    	}
	    	bos.flush();
	    	bos.close();
	    	return saveTmpFile(tmpFile, saveDir + fileName);
	    } catch (IOException localIOException) {
			return new UploadMsg(1, "上传失败!", null);
	    }
	}
	
	/**
	 * List 单文件批量上传, 返回文件名
	 * 上一层代码例子:
		response.setContentType("text/html; charset=UTF-8");
		if(!ServletFileUpload.isMultipartContent(request)){
			System.out.println("请选择上传文件");
			return;
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items;
		try {
			items = upload.parseRequest(request);
			long maxSize = 1024*1024*2; 
			//web应用目录  E:\workspace\service_eqp\WebContent
			String webPath = request.getSession().getServletContext().getRealPath("/");
			StringBuffer url = request.getRequestURL();  
			String hostPath = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
			//域名目录:http://192.168.1.160:90
			hostPath = hostPath.substring(0, hostPath.length()-1);
			//实际保存文件的文件夹路径
			String savePath = SystemConfig.upload_mediaImg + DateTimeUtil.formatDate("yyyyMM") + SystemConfig.path_separator;
			String fileName = DateTimeUtil.formatDate("yyyyMMddHHddss");
			UploadMsg res = UploadUtil.saveSinFileByList(items, webPath+savePath, fileName, "image", maxSize);
		System.out.println(res.getErrCode() + "," + res.getErrMsg());
			if (res.getErrCode() == 0) {
				String retUrl = (String) res.getObj();
				String ret = hostPath + savePath + retUrl;
			System.out.println("ret:"+ret);
				ServletUtil.instance.writeToJSON(out, language, RespWrapper.makeResp(res.getErrCode(), "", ret));
			} else {
				ServletUtil.instance.writeToJSON(out, language, RespWrapper.makeResp(res.getErrCode(), res.getErrMsg(), null));
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			ServletUtil.instance.writeToJSON(out, language, RespWrapper.makeResp(1001, "系统繁忙!", null));
		}
	 * 
	 * @param items			List items = upload.parseRequest(getRequest());
	 * @param saveDir		保存的目录
	 * @param savefileName	所要保存的文件名称
	 * @param allowFiles	上传文件的类型选择(image, media, file, other)
	 * @param maxSize		临时配置上传文件的大小限制
	 * @return	UploadMsg	封装类,里面包含错误信息和错误码,以及反馈前端的对象(返回给前端的图片url在上一层自己定义)
	 * 						返回的obj中包含有返回的文件名信息在returnUrl的文件名称的信息
	 */
	public static UploadMsg saveSinFileByList(List<FileItem> items, String saveDir, String savefileName, String allowFiles) {
		return saveSinFileByList(items, saveDir, savefileName, allowFiles, maxSize);
	}

	/**
	 * List 单文件批量上传, 返回文件名
	 * 
	 * @param items			List items = upload.parseRequest(getRequest());
	 * @param saveDir		保存的目录
	 * @param savefileName	所要保存的文件名称(如果为null, 则取文件本身的名称)
	 * @param allowFiles	上传文件的类型选择(image, media, file, other)
	 * @param maxSize		临时配置上传文件的大小限制
	 * @return	UploadMsg	封装类,里面包含错误信息和错误码,以及反馈前端的对象(返回给前端的图片url在上一层自己定义)
	 * 						返回的obj中包含有返回的文件名信息在returnUrl的文件名称的信息
	 */
	public static UploadMsg saveSinFileByList(List<FileItem> items, String saveDir, String savefileName, String allowFiles, long maxSize) {
		if (!allowMap.containsKey(allowFiles)) {
			return new UploadMsg(1, "输入的allowFiles不在读,可选参数有(image, media,file,other),分别代表图片文件,媒体文件,文件,其他文件", null);
		}	
		int saveSum = 0;
		if (items.size() != 0) {
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				String uploadFileName = item.getName();
				if (!item.isFormField()) {
					// 检查文件大小
					if (item.getSize() > maxSize) {
						return new UploadMsg(1, "存在文件大小>" + maxSize + ",超过限制!", null);
					}
					// 检查扩展名
					if (!allowMap.get(allowFiles).contains(uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase())) {
						return new UploadMsg(1, "存在不允许的上传类型!只允许" + allowFiles + "格式!", null);
					}
					saveSum++;
				}
			}
			if (saveSum > 1) {
				return new UploadMsg(1, "只允许上传一张图片!", null);
			}
			Iterator<FileItem> itr = items.iterator();
			int saveCount = 0;
			File savePath = new File(saveDir);
			String fileName = "";
			if (!savePath.exists()) {
				savePath.mkdirs();
			}
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (!item.isFormField()) {
					fileName = item.getName();
					if (saveCount == 1) {
						return new UploadMsg(0, "上传成功!", fileName);
					}
					if (savefileName != null)
						fileName = savefileName + "." + item.getName().substring(item.getName().lastIndexOf(".") + 1).toLowerCase();
					try {
						File uploadedFile = new File(saveDir + fileName);
						item.write(uploadedFile);
						saveCount++;
					} catch (Exception e) {
						return new UploadMsg(1, "上传失败!", null);
					}
				}
			}
			return new UploadMsg(0, "上传成功!", fileName);
		} else {
			return new UploadMsg(1, "没有文件上传!", null);
		}
	}

	/**
	 * List 多文件批量上传, 返回List的文件名对象
	 * 上一层代码例子:
		response.setContentType("text/html; charset=UTF-8");
		if(!ServletFileUpload.isMultipartContent(request)){
			System.out.println("请选择上传文件");
			return;
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items;
		try {
			items = upload.parseRequest(request);
			//web应用目录  E:\workspace\service_eqp\WebContent
			List<String> fileNameList = new ArrayList<String>();
			for (int i=0; i<items.size(); i++) {
				fileNameList.add(DateTimeUtil.formatDate("yyyyMMddHHddss") + new Random().nextInt(2));
			}
			long maxSize = 1024*1024*2; 
			String webPath = request.getSession().getServletContext().getRealPath("/");
			StringBuffer url = request.getRequestURL();  
			String hostPath = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
			//域名目录:http://192.168.1.160:90
			hostPath = hostPath.substring(0, hostPath.length()-1);
			//实际保存文件的文件夹路径
			String savePath = SystemConfig.upload_mediaImg + DateTimeUtil.formatDate("yyyyMM") + SystemConfig.path_separator;
			UploadMsg res = UploadUtil.saveMulFileByList(items, webPath+savePath, fileNameList, "image", maxSize);
			if (res.getErrCode() == 0) {
				List<String> retUrl = (List<String>) res.getObj();
					for (String ret : retUrl) {
						ret = hostPath + savePath + ret;
						System.out.println("1:"+ret);
					}
					ServletUtil.instance.writeToJSON(out, language, RespWrapper.makeResp(res.getErrCode(), "", retUrl));
			} else {
				ServletUtil.instance.writeToJSON(out, language, RespWrapper.makeResp(res.getErrCode(), res.getErrMsg(), null));
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			ServletUtil.instance.writeToJSON(out, language, RespWrapper.makeResp(1001, "系统繁忙!", null));
		}
	 * 
	 * @param items			List items = upload.parseRequest(getRequest());
	 * @param saveDir		保存的目录
	 * @param fileNameList	因为支持批量上传,所以上传的fileNameList必须为List,与上传图片的位置一一对应
	 * @param allowFiles	上传文件的类型选择(image, media, file, other)
	 * @return	UploadMsg	封装类,里面包含错误信息和错误码,以及反馈前端的对象(返回的图片url在上一层自己定义)
	 */
	public static UploadMsg saveMulFileByList(List<FileItem> items, String saveDir, List<String> fileNameList, String allowFiles) {
		return saveMulFileByList(items, saveDir, fileNameList, allowFiles, maxSize);
	}
	
	/**
	 * List 多文件批量上传, 返回List的文件名对象
	 * 
	 * @param items			List items = upload.parseRequest(getRequest());
	 * @param saveDir		保存的目录
	 * @param fileNameList	因为支持批量上传,所以上传的fileNameList必须为List,与上传图片的位置一一对应
	 * @param allowFiles	上传文件的类型选择(image, media, file, other)
	 * @param maxSize		临时配置上传文件的大小限制
	 * @return	UploadMsg	封装类,里面包含错误信息和错误码,以及反馈前端的对象(返回的图片url在上一层自己定义)
	 * 						返回的obj中包含有返回的文件名信息在returnUrl的List中信息
	 */
	public static UploadMsg saveMulFileByList(List<FileItem> items, String saveDir, List<String> fileNameList, String allowFiles, long maxSize) {
		if (!allowMap.containsKey(allowFiles)) {
			return new UploadMsg(1, "输入的allowFiles不在读,可选参数有(image, media,file,other),分别代表图片文件,媒体文件,文件,其他文件", null);
		}	
		int saveSum = 0;
		if (items.size() != 0) {
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				String uploadFileName = item.getName();
				if (!item.isFormField()) {
					// 检查文件大小
					if (item.getSize() > maxSize) {
						return new UploadMsg(1, "存在文件大小>" + maxSize + ",超过限制!", null);
					}
					// 检查扩展名
					if (!allowMap.get(allowFiles).contains(uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase())) {
						return new UploadMsg(1, "存在不允许的上传类型!只允许" + allowFiles + "格式!", null);
					}
					saveSum++;
				}
			}
			Iterator<FileItem> itr = items.iterator();
			List<String> retUrl = new ArrayList<String>();
			int index = 0;
			int saveCount = 0;
			File savePath = new File(saveDir);
			if (!savePath.exists()) {
				savePath.mkdirs();
			}
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (!item.isFormField()) {
					String fileName = item.getName();
					fileName = fileNameList.get(index++) + "." + item.getName().substring(item.getName().lastIndexOf(".") + 1).toLowerCase();;
					try {
						File uploadedFile = new File(saveDir + fileName);
						item.write(uploadedFile);
						retUrl.add(fileName);
						saveCount++;
					} catch (Exception e) {
						return new UploadMsg(1, "上传失败,已经上传" + saveCount + "个文件!", retUrl);
					}
				}
			}
			return new UploadMsg(0, "上传" + saveSum + "个文件成功!", retUrl);
		} else {
			return new UploadMsg(1, "没有文件上传!", null);
		}
	}
	
	/**
	 * 保存临时文件, 用于复制文件并自定义名称
	 * @return	file	临时文件对象
	 */
	private static File getTmpFile() {
		File tmpDir = getTempDirectory();
		String rand = (Math.random() * 10000.0D)+"";
		String tmpFileName = rand.replace(".", "");
		return new File(tmpDir, tmpFileName);
	}
	
	/**
	 * 保存文件
	 * 
	 * @param tmpFile	临时文件
	 * @param path		按自定义路径名称进行复制
	 * @return	UploadMsg	错误信息的封装类
	 */
	private static UploadMsg saveTmpFile(File tmpFile, String path) {
	    File targetFile = new File(path);
		if ((!targetFile.getParentFile().exists()) && (!targetFile.getParentFile().mkdirs())) {
			return new UploadMsg(1, "创建文件夹" + targetFile.getParentFile() + "失败!", null);
		}
	    if (!targetFile.getParentFile().canWrite()) {
			return new UploadMsg(1, "上传目录" + targetFile.getParentFile() + "没有写权限!", null);
	    }
	    if (targetFile.exists()) {
			return new UploadMsg(1, "该文件名已经存在!", null);
	    }
	    try {
	    	moveFile(tmpFile, targetFile);
	    } catch (IOException e) {
			return new UploadMsg(1, "上传失败!", null);
	    }
		return new UploadMsg(0, "上传成功!", targetFile.getAbsolutePath());
	}
	
	/**
	 * 获取临时文件夹的字符串路径
	 * @return	String
	 */
    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }
    
    /**
     * 获取临时文件夹的File对象
     * @return	File
     */
    public static File getTempDirectory() {
        return new File(getTempDirectoryPath());
    }
    
    /**
     * 移动文件到新的位置, 目标文件必须不存在
     *
     * @param srcFile  the file to be moved
     * @param destFile the destination file
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException          if source or destination is invalid
     * @throws IOException          if an IO error occurs moving the file
     */
    public static void moveFile(final File srcFile, final File destFile) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        }
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' is a directory");
        }
        if (destFile.exists()) {
            throw new IOException("Destination '" + destFile + "' already exists");
        }
        if (destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' is a directory");
        }
        final boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
            copyFile(srcFile, destFile);
            if (!srcFile.delete()) {
                deleteQuietly(destFile);
                throw new IOException("Failed to delete original file '" + srcFile +
                        "' after copy to '" + destFile + "'");
            }
        }
    }
    
    /**
     * 复制文件到新的位置, 如果目标文件存在,会直接覆盖掉
     *
     * @param srcFile          源文件
     * @param destFile         目标文件
     */
    public static void copyFile(final File srcFile, final File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }

    /**
     * 复制文件到新的位置, 如果目标文件存在,会直接覆盖掉
     *
     * @param srcFile          源文件
     * @param destFile         目标文件
     * @param preserveFileDate 是否要复制原来文件的日期
     */
    public static void copyFile(final File srcFile, final File destFile, final boolean preserveFileDate) throws IOException {
        checkFileRequirements(srcFile, destFile);
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        }
        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
        }
        final File parentFile = destFile.getParentFile();
        if (parentFile != null) {
            if (!parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("Destination '" + parentFile + "' directory cannot be created");
            }
        }
        if (destFile.exists() && destFile.canWrite() == false) {
            throw new IOException("Destination '" + destFile + "' exists but is read-only");
        }
        doCopyFile(srcFile, destFile, preserveFileDate);
    }
    
    /**
     * 检查文件副本的要求
     * 
     * @param src the source file
     * @param dest the destination
     * @throws FileNotFoundException if the destination does not exist
     */
    private static void checkFileRequirements(File src, File dest) throws FileNotFoundException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (dest == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!src.exists()) {
            throw new FileNotFoundException("Source '" + src + "' does not exist");
        }
    }
    
    /**
     * 内部复制文件的方法。这个缓存原始文件长度,抛出IOException
     * 如果输出文件长度不同于当前的输入文件长度。
     * 所以它可能会失败如果文件大小变化。
     * 它也可能失败”IllegalArgumentException:消极的大小“如果输入文件截断一部分
     * 通过复制数据和新文件大小小于当前位置
     *
     * @param srcFile          源文件
     * @param destFile         目标文件
     * @param preserveFileDate 是否要复制原来文件的日期
     */
    private static void doCopyFile(final File srcFile, final File destFile, final boolean preserveFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel input = null;
        FileChannel output = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            input = fis.getChannel();
            output = fos.getChannel();
            final long size = input.size(); 
            long pos = 0;
            long count = 0;
            while (pos < size) {
                final long remain = size - pos;
                count = remain > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : remain;
                final long bytesCopied = output.transferFrom(input, pos, count);
                if (bytesCopied == 0) { // IO-385 - can happen if file is truncated after caching the size
                    break; // ensure we don't loop forever
                }
                pos += bytesCopied;
            }
        } finally {
        	output.close();
        	fos.close();
        	input.close();
        	fis.close();
        }

        final long srcLen = srcFile.length();
        final long dstLen = destFile.length();
        if (srcLen != dstLen) {
            throw new IOException("Failed to copy full contents from '" +
                    srcFile + "' to '" + destFile + "' Expected length: " + srcLen + " Actual: " + dstLen);
        }
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }
    
    /**
     * 删除一个文件,不会抛出异常。如果文件是一个目录,删除它和所有子目录。
     * File.delete之间的区别(),这个方法是:
     * 1.目录被删除不需要空的。
     * 2.得到异常时不能删除一个文件或目录。(java.io.File methods returns a boolean)
     *
     * @param file 需要删除的文件,一定不能为null
     * @return {@code true} if the file or directory was deleted, otherwise {@code false}
     */
    public static boolean deleteQuietly(final File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (final Exception ignored) {
        }

        try {
            return file.delete();
        } catch (final Exception ignored) {
            return false;
        }
    }
    
    /**
     * 清除一个没有删除干净的目录
     *
     * @param directory 要清除的目录
     * @throws IOException              in case cleaning is unsuccessful
     * @throws IllegalArgumentException if {@code directory} does not exist or is not a directory
     */
    public static void cleanDirectory(final File directory) throws IOException {
        final File[] files = verifiedListFiles(directory);

        IOException exception = null;
        for (final File file : files) {
            try {
                forceDelete(file);
            } catch (final IOException ioe) {
                exception = ioe;
            }
        }
        if (null != exception) {
            throw exception;
        }
    }
    
    /**
     * 验证该目录是否存在, 并且是否是目录,而且可以读取到该目录下的文件
     * 
     * @param directory 目录列表
     * @return 目录中的文件,没有则为null
     * @throws IOException if an I/O error occurs
     */
    private static File[] verifiedListFiles(File directory) throws IOException {
        if (!directory.exists()) {
            final String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            final String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        final File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }
        return files;
    }
    
    /**
     * 删除一个文件。如果文件是一个目录,删除它和所有子目录。
     * File.delete之间的区别(),这个方法是:
     * 1.目录被删除不需要空的。
     * 2.得到异常时不能删除一个文件或目录。(java.io.File methods returns a boolean)
     *
     * @param file 要删除的文件或者目录,一定不能为null
     * @throws NullPointerException  if the directory is {@code null}
     * @throws FileNotFoundException if the file was not found
     * @throws IOException           in case deletion is unsuccessful
     */
    public static void forceDelete(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            final boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                final String message =
                        "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }
    
    /**
     * 递归删除目录(包括目录下所有文件)
     * Deletes a directory recursively.
     *
     * @param directory directory to delete
     * @throws IOException              in case deletion is unsuccessful
     * @throws IllegalArgumentException if {@code directory} does not exist or is not a directory
     */
    public static void deleteDirectory(final File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        cleanDirectory(directory);
        if (!directory.delete()) {
            final String message = "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }
}
