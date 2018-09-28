package com.wande.ssy.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * POI导出Excel操作工具类
 * 面向对象使用Bean2Excel
 * 如果个人需求需要导出图片使用Map2Excel
 * 示例代码在本类的main方法中
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年2月8日 下午2:42:10
 */
public class POIUtil {
	
	HttpServletResponse response;							// 响应浏览器的设置
	private String fileName = "excel";						// 文件名
	private String fileDir;									// 文件保存路径
	private String sheetName;								// sheet名
	private String titleFontType = "Arial Unicode MS";		// 表头字体
	private String titleBackColor = "C1FBEE";				// 表头背景色
	private short  titleFontSize = 12;						// 表头字号
	private String address = "";							// 添加自动筛选的列 如 A:M	
	private String contentFontType = "Arial Unicode MS";	// 正文字体
	private short  contentFontSize = 12;					// 正文字号
	private String floatDecimal = ".00";					// Float类型数据小数位
	private String doubleDecimal = ".00";					// Double类型数据小数位
	private String colFormula[] = null;						// 设置列的公式
	
	DecimalFormat floatDecimalFormat  = new DecimalFormat(floatDecimal);
	DecimalFormat doubleDecimalFormat = new DecimalFormat(doubleDecimal);
	
	private HSSFWorkbook workbook = null;
	
	public POIUtil(String fileDir,String sheetName) {
	     this.fileDir = fileDir;
	     this.sheetName = sheetName;
	     workbook = new HSSFWorkbook();
	}
	
	public POIUtil(HttpServletResponse response,String fileName,String sheetName) {
		 this.response = response;
		 this.sheetName = sheetName;
	     workbook = new HSSFWorkbook();
	}
	
    /**
     * 设置表头字体.
     * 
     * @param titleFontType
     */
	public void setTitleFontType(String titleFontType) {
		this.titleFontType = titleFontType;
	}
	
    /**
     * 设置表头背景色.
     * 
     * @param titleBackColor 十六进制
     */
	public void setTitleBackColor(String titleBackColor) {
		this.titleBackColor = titleBackColor;
	}
	
    /**
     * 设置表头字体大小.
     * 
     * @param titleFontSize
     */
	public void setTitleFontSize(short titleFontSize) {
		this.titleFontSize = titleFontSize;
	}
	
    /**
     * 设置表头自动筛选栏位,如A:AC.
     * 
     * @param address
     */
	public void setAddress(String address) {
		this.address = address;
	}
	
    /**
     * 设置正文字体.
     * 
     * @param contentFontType
     */
	public void setContentFontType(String contentFontType) {
		this.contentFontType = contentFontType;
	}
	
    /**
     * 设置正文字号.
     * 
     * @param contentFontSize
     */
	public void setContentFontSize(short contentFontSize) {
		this.contentFontSize = contentFontSize;
	}
	
	/**
	 * 设置float类型数据小数位 默认.00
	 * 
	 * @param doubleDecimal 如 ".00"
	 */
    public void setDoubleDecimal(String doubleDecimal) {
		this.doubleDecimal = doubleDecimal;
	}
    
	/**
     * 设置doubel类型数据小数位 默认.00
     * 
     * @param floatDecimalFormat 如 ".00
     */
	public void setFloatDecimalFormat(DecimalFormat floatDecimalFormat) {
		this.floatDecimalFormat = floatDecimalFormat;
	}
	
	/**
	 * 设置列的公式 
	 * 
	 * @param colFormula  存储i-1列的公式 涉及到的行号使用@替换 如A@+B@
	 */
	public void setColFormula(String[] colFormula) {
		this.colFormula = colFormula;
	}
	
	/**
     * List<JavaBean>写excel.
     * 
     * @param titleColumn 	 对应bean的属性名
     * @param titleName		excel表第一行标题
     * @param titleSize  	 列宽
     * @param dataList 		 数据
     */
	public void Bean2Excel(String titleColumn[],String titleName[],int titleSize[],List<?> dataList) {
    	//添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
    	Sheet sheet = workbook.createSheet(this.sheetName);  
    	//新建文件
    	OutputStream out = null;
    	try {	 
    		if (fileDir!=null) {
    			//有文件路径
    			File f = new File(fileDir);
    			if (!f.getParentFile().exists()) {
    				f.getParentFile().mkdirs();
    			}
    			out = new FileOutputStream(fileDir);
    		} else {
    			//否则，直接写到输出流中
    			out = response.getOutputStream();
    			fileName = fileName+".xls";
    			response.setContentType("application/x-msdownload");
    			response.setHeader("Content-Disposition", "attachment; filename="
    					+ URLEncoder.encode(fileName, "UTF-8"));
    		}
    		
    		//写入excel的表头
    		Row titleNameRow = workbook.getSheet(sheetName).createRow(0); 
    		//设置样式
    		HSSFCellStyle titleStyle = workbook.createCellStyle();  
    		titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, titleFontType, (short) titleFontSize);
	    	titleStyle = (HSSFCellStyle) setColor(titleStyle, titleBackColor, (short)10);
    		
    		for (int i = 0;i < titleName.length;i++) {
	    		sheet.setColumnWidth(i, titleSize[i]*256);    //设置宽度   		
	    		Cell cell = titleNameRow.createCell(i);
	    		cell.setCellStyle(titleStyle);
	    		cell.setCellValue(titleName[i].toString());
	    	}
	    	
	    	//为表头添加自动筛选
	    	if (!"".equals(address)) {
				CellRangeAddress c = (CellRangeAddress) CellRangeAddress.valueOf(address);
		    	sheet.setAutoFilter(c);
			}
	    	
	    	//通过反射获取数据并写入到excel中
	    	if (dataList!=null&&dataList.size()>0) {
	    		//设置样式
	    		HSSFCellStyle dataStyle = workbook.createCellStyle();  
	    		titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, contentFontType, (short) contentFontSize);
	    		
	    		if (titleColumn.length>0) {
	    	    	for (int rowIndex = 1;rowIndex<=dataList.size();rowIndex++) {
	    	    		Object obj = dataList.get(rowIndex-1);     //获得该对象
	    	    		Class clsss = obj.getClass();     //获得该对对象的class实例
	    	    		Row dataRow = workbook.getSheet(sheetName).createRow(rowIndex);    
	    	    		for (int columnIndex = 0;columnIndex<titleColumn.length;columnIndex++) {
	    	    			String title = titleColumn[columnIndex].toString().trim();
	    	    			if(!"".equals(title)){  //字段不为空
	    	    				//使首字母大写
								String UTitle = Character.toUpperCase(title.charAt(0))+ title.substring(1, title.length()); // 使其首字母大写;
								String methodName  = "get"+UTitle;
								
								// 设置要执行的方法
								Method method = clsss.getDeclaredMethod(methodName); 
								
								//获取返回类型
								String returnType = method.getReturnType().getName(); 
								
								String data = method.invoke(obj)==null?"":method.invoke(obj).toString();
								Cell cell = dataRow.createCell(columnIndex);
								if (data != null && !"".equals(data)) {
									if ("int".equals(returnType)) {
										cell.setCellValue(Integer.parseInt(data));
									} else if ("long".equals(returnType)) {
										cell.setCellValue(Long.parseLong(data));
									} else if ("float".equals(returnType)) {
										cell.setCellValue(floatDecimalFormat.format(Float.parseFloat(data)));
									} else if ("double".equals(returnType)) {
										cell.setCellValue(doubleDecimalFormat.format(Double.parseDouble(data)));
									} else {
										cell.setCellValue(data);
									}
								}
							} else { // 字段为空 检查该列是否是公式
								if (colFormula != null) {
									String sixBuf = colFormula[columnIndex].replace("@", (rowIndex + 1) + "");
									Cell cell = dataRow.createCell(columnIndex);
									cell.setCellFormula(sixBuf.toString());
								}
							}
		    	    	}
	    	    	}
	    	    }
	    	}
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {  
		    try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}  
	}
	
	/**
     * List<Map<String,Object>>写excel.
     * 需要导出图片时候, 需要将图片转为byte[]流
     * 判断如果是byte[]流 按图片导出, 否则按字符串导出
     * 
     * @param titleColumn	对应map的key名
     * @param titleName		excel表第一行标题
     * @param titleSize		列宽
     * @param dataMap		数据
     */
	public void Map2Excel(String titleColumn[], String titleName[], int titleSize[], List<Map<String, Object>> dataMap) {
		//添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
    	Sheet sheet = workbook.createSheet(this.sheetName);  
    	//新建文件
    	OutputStream out = null;
    	try {	 
    		if (fileDir != null) {
    			//有文件路径
    			File f = new File(fileDir);
    			if (!f.getParentFile().exists()) {
    				f.getParentFile().mkdirs();
    			}
    			out = new FileOutputStream(fileDir);    
    		} else {
    			//否则，直接写到输出流中
    			out = response.getOutputStream();
    			fileName = fileName+".xls";
    			response.setContentType("application/x-msdownload");
    			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
    		}
    		//写入excel的表头
    		Row titleNameRow = workbook.getSheet(sheetName).createRow(0); 
    		//设置样式
    		HSSFCellStyle titleStyle = workbook.createCellStyle();  
    		titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, titleFontType, (short) titleFontSize);
	    	titleStyle = (HSSFCellStyle) setColor(titleStyle, titleBackColor, (short)10);
    		
    		for(int i = 0;i < titleName.length;i++){
	    		sheet.setColumnWidth(i, titleSize[i]*256);    //设置宽度   		
	    		Cell cell = titleNameRow.createCell(i);
	    		cell.setCellStyle(titleStyle);
	    		cell.setCellValue(titleName[i].toString());
	    	}
	    	
	    	//为表头添加自动筛选
			if (!"".equals(address)) {
				CellRangeAddress c = (CellRangeAddress) CellRangeAddress.valueOf(address);
				sheet.setAutoFilter(c);
			}
			// 声明一个画图的顶级管理器
			HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
	    	//通过反射获取数据并写入到excel中
			if (dataMap != null && !dataMap.isEmpty()) {
	    		//设置样式
	    		HSSFCellStyle dataStyle = workbook.createCellStyle();  
	    		titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, contentFontType, (short) contentFontSize);
	    		
	    		if (titleColumn.length > 0) {
					for (int rowIndex = 1; rowIndex <= dataMap.size(); rowIndex++) {
	    	    		Map<String, Object> obj = dataMap.get(rowIndex-1);
	    	    		Row dataRow = workbook.getSheet(sheetName).createRow(rowIndex);  
						for (int columnIndex = 0; columnIndex < titleColumn.length; columnIndex++) {
	    	    			String title = titleColumn[columnIndex].toString().trim();
							if (!"".equals(title)) { // 字段不为空
								String textValue = null;
								Cell cell = dataRow.createCell(columnIndex);
								if (obj.get(titleColumn[columnIndex]) instanceof byte[]) {
									// 有图片时，设置行高为150px;
									dataRow.setHeightInPoints(150);
									// 设置图片所在列宽度为80px,注意这里单位的一个换算
//									sheet.setColumnWidth(i, (short) (35.7 * 80));
									sheet.setColumnWidth(columnIndex, (short) (120 * 120));
									byte[] bsValue = (byte[]) obj.get(titleColumn[columnIndex]);
									HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) columnIndex, rowIndex, (short) columnIndex, rowIndex);
									anchor.setAnchorType(2);
									patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
								} else {
									// 其它数据类型都当作字符串简单处理
									textValue = obj == null ? "" : obj.get(titleColumn[columnIndex]) == null ? "" : obj.get(titleColumn[columnIndex]).toString();
								}
								// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
								if (textValue != null) {
									Pattern p = Pattern.compile("^//d+(//.//d+)?$");
									Matcher matcher = p.matcher(textValue);
									if (matcher.matches()) {
										// 是数字当作double处理
										cell.setCellValue(Double.parseDouble(textValue));
									} else {
										HSSFRichTextString richString = new HSSFRichTextString(textValue);
										HSSFFont font3 = workbook.createFont();
										font3.setColor(HSSFColor.BLACK.index);
										richString.applyFont(font3);
										cell.setCellValue(richString);
									}
								}
	    	    			} else {   //字段为空 检查该列是否是公式
								if (colFormula != null) {
	    	    					String sixBuf = colFormula[columnIndex].replace("@", (rowIndex+1)+"");
	    	    					Cell cell = dataRow.createCell(columnIndex);
	    	    					cell.setCellFormula(sixBuf.toString());
	    	    				}
	    	    			}
	    	    		}
	    	    	}
	    	    }
	    	}
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {  
		    try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}  
	}
	
    /**
     * 将16进制的颜色代码写入样式中来设置颜色
     * 
     * @param style  保证style统一
     * @param color 颜色：66FFDD
     * @param index 索引 8-64 使用时不可重复
     * @return
     */
	public CellStyle setColor(CellStyle style, String color, short index) {
		if (color != "" && color != null) {
			// 转为RGB码
			int r = Integer.parseInt((color.substring(0, 2)), 16); // 转为16进制
			int g = Integer.parseInt((color.substring(2, 4)), 16);
			int b = Integer.parseInt((color.substring(4, 6)), 16);
			// 自定义cell颜色
			HSSFPalette palette = workbook.getCustomPalette();
			palette.setColorAtIndex((short) index, (byte) r, (byte) g, (byte) b);

			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(index);
		}
		return style;
	}
   
    /**
     * 设置字体并加外边框
     * 
     * @param style  样式
     * @param style  字体名
     * @param style  大小
     * @return
     */
	public CellStyle setFontAndBorder(CellStyle style, String fontName, short size) {
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints(size);
		font.setFontName(fontName);
		font.setBold(true);
		style.setFont(font);
		style.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
		style.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
		style.setBorderTop(CellStyle.BORDER_THIN);// 上边框
		style.setBorderRight(CellStyle.BORDER_THIN);// 右边框
		return style;
	}
	
	/**
	 * 删除文件
	 * 
	 * @param
	 * @return
	 */
	public boolean deleteExcel() {
		boolean flag = false;
		File file = new File(this.fileDir);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				file.delete();
				flag = true;
			}
		}
		return flag;
	}
	
    /**
	 * 删除文件
	 * @param  path
	 * @return
	 */
	public boolean deleteExcel(String path) {
		boolean flag = false;
		File file = new File(path);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				file.delete();
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 获取文件的byte[] 的数据
	 * 
	 * @param pathName
	 * @return
	 */
	@SuppressWarnings("resource")
	public static byte[] getFileByte(String pathName) {
		File file = new File(pathName);
		if (file.isFile()) {
			try {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(pathName));
				byte[] buf = new byte[bis.available()];
				while ((bis.read(buf)) != -1) {}
				return buf;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		return null;
	}
    
    public static void main(String[] args) {
    	System.out.println("--------------------Bean2Excel------------------------");
 /*   	POIUtil pee = new POIUtil("E:/test.xls","sheet1");
		//数据
        List<Test> dataList = new ArrayList();
        Test t1 = new Test("张三");
        Test t2 = new Test("李四");
        Test t3 = new Test("王五");
        Test t4 = new Test("赵六");
        
        dataList.add(t1);dataList.add(t2);dataList.add(t3);dataList.add(t4);
        //调用
        String titleColumn[] = {"name"};
        String titleName[] = {"姓名"};
        int titleSize[] = {13};
        //其他设置 set方法可全不调用
        String colFormula[] = new String[5];
        colFormula[4] = "D@*12";   //设置第5列的公式
        pee.setColFormula(colFormula);
        pee.setAddress("A:D");  //自动筛选 
        
        pee.wirteExcel(titleColumn, titleName, titleSize, dataList);*/
    	

    	System.out.println("--------------------Map2Excel------------------------");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		List<Map<String, Object>> dataset = new ArrayList<Map<String, Object>>();
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream("F://1.png"));
			byte[] buf = new byte[bis.available()];
			while ((bis.read(buf)) != -1) {}
			map.put("img", buf);
			map.put("date", "1");
			map2.put("img", buf);
			map2.put("date", "2");
			dataset.add(map);
			dataset.add(map2);
			POIUtil p = new POIUtil("f:/1.xls", "11");
	        String titleColumn[] = {"img", "date"};
	        String titleName[] = {"图片", "数据"};
	        int titleSize[] = {13, 13};
			p.Map2Excel(titleColumn, titleName, titleSize, dataset);
			bis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		///////////////////  示例代码
/*		String fileName = DateTimeUtil.formatDate("yyyy-MM-dd_HH(")+ u.getUin() + ").xls";
		String fileDir = PathUtil.getWebRootPath() + SystemConfig.Excel_Qrcode_Dir + fileName;
		String sheetName = "qrcode";
		POIUtil poiUtil = new POIUtil(fileDir, sheetName);
		String titleColumn[] = {"qrcodeId", "code", "url"};
	    String titleName[] = {"ID", "编号", "实际URL"};
	    int titleSize[] = {13, 13, 13};
		poiUtil.Bean2Excel(titleColumn, titleName, titleSize, resp.getObj());
//		ServletUtil.instance.writeToJSON(out, language, resp);
		try {
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
			throw new RespException(1001, "系统繁忙!");
		}*/
	}
}