package com.ait.report.hr.action;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.hrm.dao.EmpInfoDao;
import com.ait.report.ar.service.ArReportSer;
import com.ait.report.hr.dao.HrReportC01Dao;
import com.ait.report.hr.service.HrReportC01Ser;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.task.uploadHrToSapIFData;
import com.ait.web.util.IthrToPortalIfUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;
/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: HrReportCtroller.java
 * @Description: Controller Class HrReportCtroller.java
 * @Create date: April 14, 2012 3:29:38 PM
 * @Create by: hanzhe (hanzhe@ait.net.cn)
 * @version 5.1
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping(value = "/report/hrc01")
public class HrReportC01Ctroller {
	Logger logger = Logger.getLogger(HrReportCtroller.class);
	@Autowired
	private HrReportC01Ser hrReportSer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	
    public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");
	
	
	/**
	 * 员工的调任信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpPaRiseInfo")
	public ModelAndView viewEmpPaRiseInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		if(request.getParameter("seach_OPERATION_NO")==null){
			modelMap.put("PERSONPA_RESIGNATION", "YES");
		}
		modelMap.put("gradeLevelList", this.hrReportSer.getGradeLevelList(request));
		
		return new ModelAndView("/report/hrc01/viewEmpPaRiseInfo",modelMap);
	}
	
	/**
	 * 员工的调任信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEmpPaRiseExcel")
	public ModelAndView viewEmpPaRiseExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		modelMap.put("COMPANY_NAME", admin.getCpnyName());
		modelMap.put("empPaRiseList",this.hrReportSer.getEmpPaRiseExcelList(request));
		
		return new ModelAndView("/report/hrc01/viewEmpPaRiseExcel",modelMap);
	}
	
	/**
	 * 员工的调任信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/viewPoiEmpPaRiseExcel")
	public void viewPoiEmpPaRiseExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("COMPANY_NAME", admin.getCpnyName());
		modelMap.put("empPaRiseList",this.hrReportSer.getEmpPaRiseExcelList(request));
		try{    
	        HSSFWorkbook wb = new HSSFWorkbook();    
	        HSSFSheet sheet = wb.createSheet("paRiseInfo");    
	        HSSFCellStyle style = wb.createCellStyle(); // 样式对象    
	   
	            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平    
	        HSSFRow row = sheet.createRow((short) 0);    
	        HSSFRow row2 = sheet.createRow((short) 1);  
	        //照片后的“”不可删除
	        String[] columns = {"No.","现职地","社保地","入职地","照片","","姓名","部门","职级","性别","出生年月",
	        		            "年龄","入职日期","最高学历","合同到期日","学校名称","专业","月工资 ","联系方式" };
	        
	        int num = 0; 
	        for(int i = 0; i < 4; i++) { // 循环18次，每一次都要跨单元格显示 
	        	String colName = columns[i]!=null?columns[i]:"";
	        	sheet.addMergedRegion(new Region(0, (short) i, 1, (short) i));
	            HSSFCell ce = row.createCell((short) i);    
	            ce.setCellValue(colName); //表格的第一行第一列显示的名称  
	            ce.setCellStyle(style); //样式，居中    
	        }
	        //照片
	        String photoName = columns[4]!=null?columns[4]:"";
        	sheet.addMergedRegion(new Region(0, (short) 4, 1, (short) 5));
            HSSFCell cePhoto = row.createCell((short) 4);
            cePhoto.setCellValue(photoName);  
            cePhoto.setCellStyle(style);  
            
	        for(int i = 6; i < 19; i++) { // 循环18次，每一次都要跨单元格显示 
	        	String colName = columns[i]!=null?columns[i]:"";
	        	sheet.addMergedRegion(new Region(0, (short) i, 1, (short) i));
	            HSSFCell ce = row.createCell((short) i);    
	            ce.setCellValue(colName); //表格的第一行第一列显示的名称  
	            ce.setCellStyle(style); //样式，居中    
	        }
	        // 社外经历单元格合并    
	        // 四个参数分别是：起始行，起始列，结束行，结束列    
	        sheet.addMergedRegion(new Region(0, (short) 19, 0,(short) 21));    
	        HSSFCell cellOut = row.createCell((short) 19);    
	        cellOut.setCellValue("社外经历"); // 跨单元格显示的数据    
	        cellOut.setCellStyle(style); // 样式    
	        // 不跨单元格显示的数据，如：分两行，上一行分别两格为一格，下一行就为两格，“数量”，“金额”     
	        HSSFCell cellOut1 = row2.createCell((short) 19);    
	        HSSFCell cellOut2 = row2.createCell((short) 20);
	        HSSFCell cellOut3 = row2.createCell((short) 21);
	        cellOut1.setCellValue("时间");    
	        cellOut1.setCellStyle(style);        
	        cellOut2.setCellValue("单位");    
	        cellOut2.setCellStyle(style); 
	        cellOut3.setCellValue("职务");    
	        cellOut3.setCellStyle(style);
	        
	        // 社内经历单元格合并    
	        // 四个参数分别是：起始行，起始列，结束行，结束列    
	        sheet.addMergedRegion(new Region(0, (short) 22, 0,(short) 25));    
	        HSSFCell cellIn = row.createCell((short) 22);    
	        cellIn.setCellValue("社外经历"); // 跨单元格显示的数据    
	        cellIn.setCellStyle(style); // 样式    
	        // 不跨单元格显示的数据，如：分两行，上一行分别两格为一格，下一行就为两格，“数量”，“金额”     
	        HSSFCell cellIn1 = row2.createCell((short) 22);    
	        HSSFCell cellIn2 = row2.createCell((short) 23);
	        HSSFCell cellIn3 = row2.createCell((short) 24);
	        HSSFCell cellIn4 = row2.createCell((short) 25);
	        cellIn1.setCellValue("时间");    
	        cellIn1.setCellStyle(style);        
	        cellIn2.setCellValue("单位");    
	        cellIn2.setCellStyle(style); 
	        cellIn3.setCellValue("职务");    
	        cellIn3.setCellStyle(style);
	        cellIn4.setCellValue("薪资");    
	        cellIn4.setCellStyle(style);
	        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("E://pander.jpg"));
			byte[] buf = new byte[bis.available()];
			while ((bis.read(buf)) != -1){}
			FileOutputStream fileOut = null; 
	        BufferedImage bufferImg =null; 
	        BufferedImage bufferImg1 = null; 
		    //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray 
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(); 
            ByteArrayOutputStream byteArrayOut1 = new ByteArrayOutputStream(); 
            bufferImg = ImageIO.read(new File("E:/pander.jpg")); 
            bufferImg1 = ImageIO.read(new File("E:/pander.jpg")); 
            ImageIO.write(bufferImg,"jpg",byteArrayOut); 
            ImageIO.write(bufferImg1,"jpg",byteArrayOut1); 
	         
	        HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); 
	        HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,455,255,(short) 26,0,(short)27,5);
            HSSFCell cell = row.createCell((short) 26);
            //插入图片
            patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
            cellIn.setCellStyle(style); // 样式    
            
            fileOut = new FileOutputStream("E://paRiseInfo.xls");    
            wb.write(fileOut);    
            fileOut.close();    
            System.out.print("OK");    
        } catch (Exception ex) {    
            ex.printStackTrace();    
        }    
	}

	/**
	 * 跳转到乐天玛特--在职人数统计信息报表页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpOnStatusInfo")
	public ModelAndView viewEmpOnStatusInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String ddate = "";
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    ddate=sdf.format(dt);
	    String b[] = ddate.split("-");
	    
	    modelMap.put("YEAR_ONSTATUS", b[0].trim().toString());
	    modelMap.put("MONTH_INS", b[1].trim().toString());
	    modelMap.put("loginName", admin.getUsername()!=null?admin.getUsername().toString():"NOIT");
	    
		return new ModelAndView("/report/hrc01/viewEmpOnStatusInfo",modelMap);
	}
	
	/**
	 * 乐天玛特--在职人数统计信息，导出Excel用
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewEmpOnStatusExcel")
	public ModelAndView viewEmpOnStatusExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		//AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//LinkedHashMap linkMap = (LinkedHashMap)hrReportSer.getSysdate(request);
		modelMap.put("YEAR_ONSTATUS", request.getParameter("seach_YEAR_ONSTATUS"));
		modelMap.put("MONTH_ONSTATUS", request.getParameter("seach_MONTH_ONSTATUS"));
		
		modelMap.put("empOnStatusList",this.hrReportSer.getEmpOnStatusExcelList(request));
		
		return new ModelAndView("/report/hrc01/viewEmpOnStatusExcel",modelMap);
	}
	
	/**
	 * 员工在离职查询(query the emp status info)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPersonalStatusInfo")
	public ModelAndView viewPersonalStatusInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	    List personStatusInfoList = this.hrReportSer.getPersonalStatusInfoList(request);
		int personStatusInfoListCnt = this.hrReportSer.getPersonalStatusInfoListCnt(request);
		//子部门选择条件默认选择
		if(request.getParameter("seach_EXPORT_EMP_STATUS")==null){
			modelMap.put("EXPORT_EMP_STATUS", "YES");
		}else{
			modelMap.put("EXPORT_EMP_STATUS", request.getParameter("seach_EXPORT_EMP_STATUS").toString());
		}
	    modelMap.put("personStatusInfoList",personStatusInfoList);
	    modelMap.put(UiUtil.TOTAL_COUNT_NAME, personStatusInfoListCnt);
		modelMap.put("numPerPage", request.getParameter("numPerPage")!=null?request.getParameter("numPerPage"):10);
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("DEPTNO_STATUS", request.getParameter("seach_DEPTNO_STATUS")!=null?request.getParameter("seach_DEPTNO_STATUS"):"");
		modelMap.put("KEY_STATUS", request.getParameter("seach_KEY_STATUS")!=null?request.getParameter("seach_KEY_STATUS"):"");
		modelMap.put("EMP_OFFICE_STATUS", request.getParameter("seach_EMP_OFFICE_STATUS")!=null?request.getParameter("seach_EMP_OFFICE_STATUS"):"");
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "1018")) ;
		
		return new ModelAndView("/report/hrc01/viewPersonalStatusInfo",modelMap);
	}
	
	/**
	 * 员工在离职查询(query the emp status info)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPersonalStatusInfoExcel")
	public ModelAndView viewPersonalStatusInfoExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("personStatusInfoList",this.hrReportSer.getPersonalStatusInfoExcelList(request));
		
		return new ModelAndView("/report/hrc01/viewPersonalStatusInfoExcel",modelMap);
	}
}
