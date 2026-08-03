package com.ait.pa.action.workManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ArMonthSer;
import com.ait.ar.service.CycleSer;
import com.ait.hrm.dao.HrmDao;
import com.ait.hrm.dao.TransferOrderDao;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.action.salary.PaInputItemCtroller;
import com.ait.pa.dao.BonusInputItemParamDao;
import com.ait.pa.dao.InsuranceInputItemDao;
import com.ait.pa.dao.PaBasicItemDao;
import com.ait.pa.dao.PaInputItemParamDao;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.insurance.InsuranceInputItemSer;
import com.ait.pa.service.salary.PaCalculateSer;
import com.ait.pa.service.salary.PaInputItemParamSer;
import com.ait.pa.service.salary.PaInputItemSer;
import com.ait.pa.service.wagebase.PaBasicItemSer;
import com.ait.paEcc.service.PaEccService;
import com.ait.report.hr.service.HrReportC01Ser;
import com.ait.report.pa.service.PaReportSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.bean.JsonSort;
import com.ait.sys.dao.AjaxDao;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.PaCalcUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC (c) Company: LDCC
 * 
 * @fileName ExcelExportCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-3-29 下午02:53:03
 * @version 5.0
 */
@Controller
@RequestMapping(value = "/pa/workManagement")
public class PaParemExcelExportCtroller {
	@Autowired
	 private PaReportSer paReportSer;
	@Autowired
	private AjaxDao ajaxDao;
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;	
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	private ArMonthSer arMonthSer;
	
	@Autowired
	private HrReportC01Ser hrReportSer;

	@Autowired
	private InsuranceInputItemDao insuranceInputItemDao;
	
	@Autowired
	private PaInputItemParamDao paInputItemParamDao;
	
	@Autowired
	private PaBasicItemDao paBasicItemDao;
	
	@Autowired
	private BonusInputItemParamDao bonusInputItemParamDao;

	@Autowired
	private EmpInfoSer empInfoSer;
	
	@Autowired
	private TransferOrderDao transferOrderDao;
	
	@Autowired
	private HrmDao hrmDao;

	@Autowired
	private InsuranceInputItemSer insuranceInputItemSer1;
	
	@Autowired
	private PaEccService paEccService;
	Logger logger = Logger.getLogger(PaInputItemCtroller.class);

	@Autowired
	private PaInputItemSer paInputItemSer;
	
	@Autowired
	private ExcelUtilSer excelUtilSer1;

	@Autowired
	private PaInputItemParamSer paInputItemParamSer;

	@Autowired
	private InsuranceInputItemSer insuranceInputItemSer;
	
	@Autowired
	private PaCalculateSer paCalculateSer;

	@Autowired
	private CompanySer companySer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private PaBasicItemSer paBasicItemSer;
	@Autowired
	private CycleSer cycleSer;	


	/**
	 * 跳转输入项目数据主页面(view Pa Input Item Data)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaParamDownloud")
	public ModelAndView viewPaParamDownloud(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		modelMap
				.put("compList", this.companySer.getCompanyItemAllList(request));

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());

		request.setAttribute("FSE_FLAG", "N");
		List proList = this.paInputItemParamSer
				.getPaInputItemParamListNotPageNum(request);
		modelMap.put("proList", proList);
		modelMap.put("itemType", request.getParameter("itemType"));

		return new ModelAndView("/pa/workManagement/viewPaParamDownloud", modelMap);
	}*/
	

	
	/**
	 * excel模板下载
	 * @param request
	 * @param response
	 * @return 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/paParemDownloadExcelTemplate")
	public void paParemDownloadExcelTemplate(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String PARENT_CODE_NO_SQL = "select t.param_item_no || ' > ' || sy.content DESCRIPTION,       t.param_item_no CODE_NO,        sy.content CODENAME  from pa_param_item t, pa_param_item_param pi ,sy_global_name sy  "
			+ " where t.param_item_no = pi.param_item_no   and t.param_item_no = sy.no   and pi.distinct_field = 'PERSON_ID'   and pi.CPNY_ID = '" + admin.getCpnyId() + "' "
			+ " AND T.ACTIVITY = 1  and sy.language ='" + admin.getLanguage() + "'";
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + fileName + "_out.xls";
		// execl导出处理
		XLSTransformer transformer = new XLSTransformer();
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
		
			List rsxj = empInfoSer.getCodeListBySql(PARENT_CODE_NO_SQL ); //输入项目代码
			
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = transformer.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,rsxj,0);
				this.createName(wb, "rs", "TemplateCode!$A$2:$A$" + (rsxj == null ? 2 : rsxj.size() + 1));
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xls");
		try {
			File file=new File(destFileName);
			InputStream inputStream=new FileInputStream(file);
			OutputStream os=response.getOutputStream();
			byte[] b=new byte[102400];
			int length;
			while((length=inputStream.read(b))>0){
				os.write(b,0,length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return new ModelAndView("pa/workManagement/viewPaParamDownloud");
	}
	
	/** 
	 * 获取名称，设置引用
	 * @param wb 
	 * @param name 
	 * @param expression 
	 * @return 
	 */  
	public void createName(Workbook wb, String name, String expression){  
		Name refer = wb.getName(name);
	    refer.setRefersToFormula(expression);  
	    refer.setNameName(name);  
	}

	private void composeTemplateCodeInfo(Workbook wb,List list,int x){
		Sheet sheet = wb.getSheetAt(1);
		for(int i=0;i<list.size() ;i++){
			Map map = (Map)list.get(i);
			Row row = getRow(sheet, i + 1);
			Cell cell1 = getCell(row,x);
			Cell cell2 = getCell(row,x + 1);
			Cell cell3 = getCell(row,x + 2);
			cell1.setCellValue(String.valueOf(map.get("DESCRIPTION")));
			cell2.setCellValue(String.valueOf(map.get("CODE_NO")));
			cell3.setCellValue(String.valueOf(map.get("CODENAME")));
		}
	}

	private Row getRow(Sheet sheet, int x){
		Row row = sheet.getRow(x);
		if(row == null){
			row = sheet.createRow(x);
		}
		return row;
	}
	private Cell getCell(Row row,int x){
		Cell cell = row.getCell(x);
		if(cell == null){
			cell = row.createCell(x);
		}
		return cell;
	}
	
	/**
	 * excel模板下载
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadExcelExpTemplate")
	public void downloadExcelExpTemplate(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String PARENT_CODE_NO_SQL = "SELECT NVL(T.DESCRIPTION,T.CODE_NO) || ' > ' || SY.CONTENT DESCRIPTION, T.CODE_NO, SY.CONTENT CODENAME FROM SY_CODE T, SY_CODE_PARAM SP, SY_GLOBAL_NAME SY"
			+ " WHERE T.CODE_NO = SY.NO(+) AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '" + admin.getCpnyId() + "' "
			+ " AND SY.LANGUAGE(+) = '" + admin.getLanguage() + "' "
			+ " AND T.ACTIVITY = 1 "
			+ " AND T.PARENT_CODE_NO IN ( SELECT CODE_NO FROM SY_CODE WHERE PARENT_CODE_NO = '";
		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' ";
		String COST_CENTER_SQL = "SELECT CODE_NO || ' > ' || CODE_NAME DESCRIPTION, CODE_NO,CODE_NAME CODENAME FROM ORG_COST_CENTER WHERE ACTIVITY = '14013912' AND CPNY_ID = '" + admin.getCpnyId() + "' ";
		String WORK_AREA_SQL = "SELECT CODE_NO || ' > ' || CODE_NAME DESCRIPTION, CODE_NO,CODE_NAME CODENAME FROM ORG_WORK_AREA WHERE ACTIVITY = '14013912' AND CPNY_ID = '" + admin.getCpnyId() + "' ";
		
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + fileName + "_out.xls";
		// execl导出处理
		XLSTransformer transformer = new XLSTransformer();
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List rs = empInfoSer.getCodeList("123313", request); //入社区分
			List rsxj = empInfoSer.getCodeListBySql(PARENT_CODE_NO_SQL + "14013956' )"); //入社详细区分
			List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL); //部门
			List zyyw = empInfoSer.getCodeList("400098", request); //主要业务
			List zz = empInfoSer.getCodeList("13813", request); //职责
			List gzd = empInfoSer.getCodeListBySql(WORK_AREA_SQL); //工作地
			List cbzx = empInfoSer.getCodeListBySql(COST_CENTER_SQL); //成本中心
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = transformer.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,rs,0);
				this.composeTemplateCodeInfo(wb,rsxj,4);
				this.composeTemplateCodeInfo(wb,dept,8);
				this.composeTemplateCodeInfo(wb,zyyw,12);
				this.composeTemplateCodeInfo(wb,zz,16);
				this.composeTemplateCodeInfo(wb,gzd,24);
				this.composeTemplateCodeInfo(wb,cbzx,28);
				this.composeTemplateCodeInfo(wb,dept,32);
				this.createName(wb, "rs", "TemplateCode!$A$2:$A$" + (rs == null ? 2 : rs.size() + 1));
				this.createName(wb, "rsxj", "TemplateCode!$E$2:$E$" + (rsxj == null ? 2 : rsxj.size() + 1));
				this.createName(wb, "dept", "TemplateCode!$I$2:$I$" + (dept == null ? 2 : dept.size() + 1));
				this.createName(wb, "zyyw", "TemplateCode!$M$2:$M$" + (zyyw == null ? 2 : zyyw.size() + 1));
				this.createName(wb, "zz", "TemplateCode!$Q$2:$Q$" + (zz == null ? 2 : zz.size() + 1));
				this.createName(wb, "gzd", "TemplateCode!$Y$2:$Y$" + (gzd == null ? 2 : gzd.size() + 1));
				this.createName(wb, "cbzx", "TemplateCode!$AC$2:$AC$" + (cbzx == null ? 2 : cbzx.size() + 1));
				this.createName(wb, "glz", "TemplateCode!$AG$2:$AG$" + (dept == null ? 2 : dept.size() + 1));
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xls");
		try {
			File file=new File(destFileName);
			InputStream inputStream=new FileInputStream(file);
			OutputStream os=response.getOutputStream();
			byte[] b=new byte[102400];
			int length;
			while((length=inputStream.read(b))>0){
				os.write(b,0,length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
