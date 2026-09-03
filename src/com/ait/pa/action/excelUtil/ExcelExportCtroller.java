package com.ait.pa.action.excelUtil;

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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.axis.utils.Admin;
import org.apache.commons.lang.ObjectUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ait.ar.service.ArMonthSer;
import com.ait.ess.service.EssEmpInfoSer;
import com.ait.ess.service.TempEmpSer;
import com.ait.hrm.dao.HrmDao;
import com.ait.hrm.dao.TransferOrderDao;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.dao.BonusInputItemParamDao;
import com.ait.pa.dao.InsuranceInputItemDao;
import com.ait.pa.dao.PaBasicItemDao;
import com.ait.pa.dao.PaInputItemParamDao;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.imp.excelUtil.ExcelTemplateUtil;
import com.ait.pa.service.insurance.InsuranceInputItemSer;
import com.ait.paEcc.service.PaEccService;
import com.ait.report.hr.service.HrReportC01Ser;
import com.ait.report.pa.service.PaReportSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.bean.JsonSort;
import com.ait.sys.dao.AjaxDao;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.PaCalcUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright: LDCC (c) Company: LDCC
 * 
 * @fileName ExcelExportCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-3-29 下午02:53:03
 * @version 5.0
 */
@Controller
@RequestMapping(value = "/pa/excelExport")
public class ExcelExportCtroller {
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
	private EssEmpInfoSer essEmpInfoSer;
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
	private InsuranceInputItemSer insuranceInputItemSer;
	
	@Autowired
	private PaEccService paEccService;

	@Autowired
	private TempEmpSer tempEmpSer;
	/**
	 * 导出数据时需要调用的方法（url传参太多的时候用） Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportResult")
	@ResponseBody
	public LinkedHashMap exportResult(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,String parameterPa)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		List aliasListExpType = new ArrayList();
		List aliasSortList = new ArrayList();
		String paramNumStr = request.getParameter("paramNum");
		String partOfTbname = request.getParameter("tableNamePart");
		String payScheduleNo = request.getParameter("PAY_SCHEDULE_NO");
//		String empTypeGroup = request.getParameter("empTypeGroup");
		String statNo = request.getParameter("statNo");
		String deptNo = request.getParameter("deptNo");
		String sqlContent = "SELECT";
		if (paramNumStr != null && !paramNumStr.equals("")) {
			int paramNum = Integer.parseInt(paramNumStr);
			for (int i = 1; i < paramNum; i++) {
				String aliasValue = request.getParameter("alias" + i);
				String aliasName = request.getParameter("aliasName" + i);
				String aliasType = request.getParameter("aliasType" + i);
				String str  = request.getParameter("aliasSort" + i);
				String aliasExpFlag = request.getParameter("aliasExpFlag" + i);
				String aliasDataType = request.getParameter("aliasDataType" + i);
				if(str.equals("")){
					str="999";
				}
				Integer aliasSort = new Integer(str);
				aliasSortList.add(new JsonSort(aliasValue,aliasName,aliasType,aliasSort,aliasExpFlag,aliasDataType));
			}
				
			Collections.sort(aliasSortList);

			for(int k = 0; k < aliasSortList.size(); k++){
				String i18nStr = "";
				String i18nStrContent = "";
				String aliasValue = ((JsonSort)aliasSortList.get(k)).getAliasValue();
				Integer aliasSort = ((JsonSort)aliasSortList.get(k)).getAliasSort() ;
				String aliasDataType = ((JsonSort)aliasSortList.get(k)).getAliasDataType();
				String aliasType = ((JsonSort)aliasSortList.get(k)).getAliasType();
				String aliasExpType = ((JsonSort)aliasSortList.get(k)).getAliasExpFlag();
				aliasNameList.add(((JsonSort)aliasSortList.get(k)).getAliasName());
				aliasList.add(aliasValue+"@"+aliasSort+"@"+aliasDataType);
				aliasListExpType.add(aliasExpType);
//				if (!aliasValue.equalsIgnoreCase("PAY_STEP")&&!aliasValue.equalsIgnoreCase("PAY_GRADE")
//						&& aliasType.equals("Y")) {
//					if (aliasValue.equalsIgnoreCase("person_id")) {
//						if (sqlContent.equals("SELECT")) {
//							sqlContent += " (select e.empid from hr_employee e where e.person_id=t.person_id) "
//									+ aliasValue;
//						} else {
//							sqlContent += ", (select e.empid from hr_employee e where e.person_id=t.person_id) "
//									+ aliasValue;
//						}
//					} else {
//						if (aliasValue.equalsIgnoreCase("deptno")) {
//							i18nStrContent = "(select content from hr_department_name n where n.deptno=t.deptno and n.language(+)='"
//									+ admin.getLanguage() + "')";
//							i18nStr = i18nStrContent + "is not null then"
//									+ i18nStrContent;
//						}else if(aliasValue.equalsIgnoreCase("date_started") || aliasValue.equalsIgnoreCase("date_left")
//								|| aliasValue.equalsIgnoreCase("end_probation_date") || aliasValue.equalsIgnoreCase("prob_pay_rat") ){
//							i18nStrContent = "t."+aliasValue;
//							i18nStr = i18nStrContent + " is not null " + " then " + i18nStrContent;
//						}else {
//							if(aliasValue.equalsIgnoreCase("GIVE_DATE")){
//								i18nStrContent = "t."+aliasValue;
//								i18nStr = i18nStrContent + " is not null " + " then " + " TO_CHAR( "+i18nStrContent +" ,'yyyy-MM-dd')";
//							}else if(aliasValue.equalsIgnoreCase("PA_MONTH")){
//								i18nStrContent = " (select content "
//									+ " from sy_global_name"
//									+ " where 1 = 2)" ;
//								i18nStr = i18nStrContent + " is not null "
//								+ " then " + i18nStrContent;
//							}
//							else{
//								i18nStrContent = " (select content "
//									+ " from sy_global_name"
//									+ " where no(+) = t." + aliasValue
//									+ " and language(+) = '"
//									+ admin.getLanguage() + "')";
//								i18nStr = i18nStrContent + " is not null "
//								+ " then " + i18nStrContent;
//							}
//						}
//						if (sqlContent.equals("SELECT")) {
//							if(aliasValue.equalsIgnoreCase("GIVE_DATE")){
//								sqlContent += " (case when " + i18nStr + " else "
//								+ "TO_CHAR(" + aliasValue + " ,'yyyy-MM-dd')" + " end) " + aliasValue;
//							}else{
//								sqlContent += " (case when " + i18nStr + " else t."
//								+ aliasValue + " end) " + aliasValue;
//							}
//						} else {
//							if(aliasValue.equalsIgnoreCase("GIVE_DATE")){
//								sqlContent += ", (case when " + i18nStr
//								+ " else " + "TO_CHAR(" + aliasValue + " ,'yyyy-MM-dd')" + " end) "
//								+ aliasValue;
//							}else{
//								sqlContent += ", (case when " + i18nStr
//								+ " else t." + aliasValue + " end) "
//								+ aliasValue;
//							}
//						}
//					}
//				} else {
					if (sqlContent.equals("SELECT")) {
						sqlContent += " " + aliasValue;
					} else {
						sqlContent += ", " + aliasValue;
					}
//				}
					
			}
		}
		//sqlContent += " from " + partOfTbname + admin.getPersonId() + " t";
		//sqlContent += " from " + partOfTbname + admin.getCpnyId() + " t WHERE CREATED_BY ='"+ admin.getPersonId() +"'";
	//	sqlContent += " from " + partOfTbname + admin.getCpnyId() + " t WHERE CREATED_BY ='"+ admin.getPersonId() +"' and pa_month='"+paMonth+"'";
		sqlContent += " from " + partOfTbname + admin.getCpnyId() + " t WHERE   PAY_SCHEDULE_NO="+payScheduleNo;

		//		if (empTypeGroup != null && !"".equals(empTypeGroup)) {
//			sqlContent += " and EMPTYPE_GROUPNO = '"+empTypeGroup+"'";
//		}
//		if (statNo != null && !"".equals(statNo)) {
//			sqlContent += " and T.STAT_NO = '"+statNo+"'";
//		}
		
		if (deptNo != null && !"".equals(deptNo)) {
			sqlContent += " and EXISTS (SELECT * FROM HR_DEPARTMENT B1 WHERE B1.DEPTNO = T.DEPTNO START WITH B1.DEPTNO = '"+deptNo+"' CONNECT BY PRIOR B1.DEPTNO = B1.PARENT_DEPT_NO)";
		}
		
		sqlContent += " and EXISTS (SELECT * FROM PA_SUPERVISOR_INFO PS WHERE PS.DEPTNO = T.DEPTNO and PS.PERSON_ID = " + admin.getPersonId() +")";
		
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);

		map.put("sqlContent", sqlContent);

		//去除后面的部门权限
		//map.put("PA_ADMIN_ID", admin.getPersonId());

		// this.excelUtilSer.exportExcel(request,response,modelMap,map,aliasNameList,aliasList);
		String pathStr = "";
		LinkedHashMap resultMap = new LinkedHashMap();
		if (PaCalcUtil.getPaCalcFlag() == 0) {
			try {
				pathStr = this.excelUtilSer.exportIntoExcel(request, response,
						modelMap, map, aliasNameList, aliasList,aliasListExpType);
				resultMap.put("pathStr", pathStr);
			} catch (Exception e) {
				resultMap.put("pathStr", "N");
				e.printStackTrace();
			} finally {
				PaCalcUtil.setPaCalcFlag(0);
			}
		} else {
			resultMap.put("pathStr", "K");
		}

		return resultMap;
	}

	/**
	 * 导出数据时需要调用的方法（url传参太多的时候用） Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadResult")
	public void downloadInsuranceResult(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String pathStr = request.getParameter("pathstr");
		this.excelUtilSer.download(request, response, pathStr);
	}


	/**
	 * 历史工资信息导出Excel专用2012-09-10(Lufeng)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaHistoryResult")
	@ResponseBody
	public LinkedHashMap exportPaHistoryResult(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		List aliasListExpType = new ArrayList();
		List aliasSortList = new ArrayList();
		String paBeginMonth = request.getParameter("paBeginMonth")!=null?request.getParameter("paBeginMonth").toString():"";
		String paBeginGiveDate = request.getParameter("paBeginGiveDate")!=null?request.getParameter("paBeginGiveDate").toString():"";
		String paEndMonth = request.getParameter("paEndMonth")!=null?request.getParameter("paEndMonth").toString():"";
		String paEndGiveDate = request.getParameter("paEndGiveDate")!=null?request.getParameter("paEndGiveDate").toString():"";
		String key = request.getParameter("key")!=null?request.getParameter("key").toString():"";
		String deptNo = request.getParameter("deptNo")!=null?request.getParameter("deptNo").toString():"";
		String paramNumStr = request.getParameter("paramNum");
		String partOfTbname = request.getParameter("tableNamePart");
		String sqlContent = "SELECT";
		if (paramNumStr != null && !paramNumStr.equals("")) {
			int paramNum = Integer.parseInt(paramNumStr);
			
			for (int i = 1; i < paramNum; i++) {
				String aliasValue = request.getParameter("alias" + i);
				String aliasName = request.getParameter("aliasName" + i);
				String aliasType = request.getParameter("aliasType" + i);
				String aliasExpFlag = request.getParameter("aliasExpFlag" + i);
				String str  = request.getParameter("aliasSort" + i);
				if(str.equals("")){
					str="999";
				}
				Integer aliasSort = new Integer(str);
				aliasSortList.add(new JsonSort(aliasValue,aliasName,aliasType,aliasSort,aliasExpFlag));
			}
				
			Collections.sort(aliasSortList);

			
			for(int k = 0; k < aliasSortList.size(); k++){
				String aliasValue = ((JsonSort)aliasSortList.get(k)).getAliasValue();
				String aliasName = ((JsonSort)aliasSortList.get(k)).getAliasName();
				String aliasType = ((JsonSort)aliasSortList.get(k)).getAliasType();
				String aliasExpType = ((JsonSort)aliasSortList.get(k)).getAliasExpFlag();
				aliasNameList.add(aliasName);
				aliasList.add(aliasValue);
				aliasListExpType.add(aliasExpType);
				String i18nStr = "";
				String i18nStrContent = "";
				if (!aliasValue.equalsIgnoreCase("PAY_STEP") && aliasType.equals("Y")) {
					if (aliasValue.equalsIgnoreCase("person_id")) {
						if (sqlContent.equals("SELECT")) {
							sqlContent += " (select e.empid from hr_employee e where e.person_id=t.person_id) " + aliasValue;
						} else {
							sqlContent += ", (select e.empid from hr_employee e where e.person_id=t.person_id) " + aliasValue;
						}
					} else {
						if (aliasValue.equalsIgnoreCase("deptno")) {
							i18nStrContent = "(select content from hr_department_name n where n.deptno=t.deptno and n.language(+)='" + admin.getLanguage() + "')";
							i18nStr = i18nStrContent + "is not null then"+ i18nStrContent;
						}else if(aliasValue.equalsIgnoreCase("date_started") || aliasValue.equalsIgnoreCase("date_left")){
							i18nStrContent = "t."+aliasValue;
							i18nStr = i18nStrContent + " is not null " + " then " + i18nStrContent;
						}else {
							if(aliasValue.equalsIgnoreCase("GIVE_DATE")){
								i18nStrContent = "t."+aliasValue;
								i18nStr = i18nStrContent + " is not null " + " then " + " TO_CHAR( "+i18nStrContent +" ,'yyyy-MM-dd')";
							}else{
								i18nStrContent = " (select content " + " from sy_global_name" + " where no(+) = t." + aliasValue
								+ " and language(+) = '" + admin.getLanguage() + "')";
								i18nStr = i18nStrContent + " is not null " + " then " + i18nStrContent;
							}
						}
						if (sqlContent.equals("SELECT")) {
							if(aliasValue.equalsIgnoreCase("GIVE_DATE")){
								sqlContent += " (case when " + i18nStr + " else "
								+ "TO_CHAR(" + aliasValue + " ,'yyyy-MM-dd')" + " end) " + aliasValue;
							}else{
								sqlContent += " (case when " + i18nStr + " else t." + aliasValue + " end) " + aliasValue;
							}
						} else {
							if(aliasValue.equalsIgnoreCase("GIVE_DATE")){
								sqlContent += ", (case when " + i18nStr
								+ " else " + "TO_CHAR(" + aliasValue + " ,'yyyy-MM-dd')" + " end) "
								+ aliasValue;
							}else{
								sqlContent += ", (case when " + i18nStr + " else t." + aliasValue + " end) " + aliasValue;
							}
						}
					}
				} else {
					if (sqlContent.equals("SELECT")) {
						sqlContent += " " + aliasValue;
					} else {
						sqlContent += ", " + aliasValue;
					}
				}
			}
		}
		sqlContent += " from " + partOfTbname + admin.getCpnyId() + " T WHERE 1 = 1";
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		map.put("PA_ADMIN_ID", admin.getPersonId());
		map.put("PA_BEGIN_MONTH", paBeginMonth);
		map.put("PA_BEGIN_GIVE_DATE", paBeginGiveDate);
		map.put("PA_END_MONTH", paEndMonth);
		map.put("PA_END_GIVE_DATE", paEndGiveDate);
		map.put("KEY", key);
		map.put("DEPTNO", deptNo);
		
		String pathStr = "";
		LinkedHashMap resultMap = new LinkedHashMap();
		if (PaCalcUtil.getPaCalcFlag() == 0) {
			try {
				pathStr = this.excelUtilSer.exportPaHistoryExcel(request, response,modelMap, map, aliasNameList, aliasList,aliasListExpType);
				resultMap.put("pathStr", pathStr);
			} catch (Exception e) {
				resultMap.put("pathStr", "N");
				e.printStackTrace();
			} finally {
				PaCalcUtil.setPaCalcFlag(0);
			}
		} else {
			resultMap.put("pathStr", "K");
		}

		return resultMap;
	}

	/**
	 * 历史工资信息导出Excel专用2012-09-10(Lufeng)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadPaHistoryResult")
	public void downloadPaHistoryResult(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String pathStr = request.getParameter("pathstr");
		this.excelUtilSer.downloadPaHistory(request, response, pathStr);
	}
	
	/**
	 * 人员任情表信息导出Excel专用2013-01-23(Lufeng)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaRiseInfoExcel")
	@ResponseBody
	public LinkedHashMap exportPaRiseInfoExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		String pathStr = "";
		LinkedHashMap resultMap = new LinkedHashMap();
		List<LinkedHashMap> empPaRiseList = new ArrayList<LinkedHashMap>();
		empPaRiseList = this.hrReportSer.getEmpPaRiseExcelList(request);
		try {
			if((empPaRiseList!=null?empPaRiseList.size():0)>0){
				pathStr = this.excelUtilSer.exportPaRiseInfoExcel(request, response,modelMap,empPaRiseList);
				resultMap.put("pathStr", pathStr);
			}else{
				resultMap.put("pathStr", "N");
			}
		} catch (Exception e) {
			resultMap.put("pathStr", "N");
			e.printStackTrace();
		} 
		
		return resultMap;
	}
	
	/**
	 * 人员任情表信息导出Excel专用2013-01-23(Lufeng)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadPaRiseInfo")
	public void downloadPaRiseInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String pathStr = request.getParameter("pathstr");
		this.excelUtilSer.downloadPaRiseInfo(request, response, pathStr);
	}
	
	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportInsuranceInputItemDataExcelIsNull")
	public void exportInsuranceInputItemDataExcelIsNull(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		// 取得数据项目信息
		LinkedHashMap isInputItemInfo = (LinkedHashMap) this.insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(isInputItemInfo.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		String distinctField2 = ObjectUtils.toString(isInputItemInfo.get("DISTINCT_FIELD_2ND"));
		String language = admin.getLanguage();
		String adminId = admin.getAdminID();
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		String filed1Name = ObjectUtils.toString(isInputItemInfo.get("DISTINCT_FIELD_NAME"));
		String filed2Name = ObjectUtils.toString(isInputItemInfo.get("DISTINCT_FIELD_2ND_NAME"));
		aliasNameList.add(filed1Name);
		if (!filed2Name.equals("")) {
			aliasNameList.add(filed2Name);
		}
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request));//备注
		
		aliasList.add("FIELD1_NAME");
		if (!filed2Name.equals("")) {
			aliasList.add("FIELD2_NAME");
		}
		aliasList.add("START_MONTH");
		aliasList.add("END_MONTH");
		aliasList.add("RETURN_VALUE");
		aliasList.add("REMARK");
		String sqlContent = null;

		if (distinctField.equals("CPNY_ID")) {
			sqlContent = "SELECT A.PARAM_DATA_NO, " 
					+ "A.PARAM_NO, "
					+ "A.FIELD1_VALUE, " 
					+ "A.FIELD2_VALUE, "
					+ "SY1.CONTENT     FIELD1_NAME, " 
					+ "RETURN_VALUE, "
					+ "A.START_MONTH, " 
					+ "A.END_MONTH, " 
					+ "A.CPNY_ID, "
					+ "SY3.CONTENT     CPNY_NAME,A.REMARK REMARK "
					+ "FROM IS_PARAM_DATA_OTHER A, "
					+ "  HR_COMPANY          HR, "
					+ "  SY_GLOBAL_NAME      SY1, "
					+ "  HR_COMPANY          HC, "
					+ "  SY_GLOBAL_NAME      SY3 "
					+ " WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND SY3.LANGUAGE(+) = '" + language + "' "
					+ "  AND A.FIELD1_VALUE = HC.CPNY_ID "
					+ "  AND HC.CPNY_NO = SY1.NO(+) "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND SY1.LANGUAGE(+) = '" + language + "' ";
		} else if ((distinctField.equals("WORK_AREA") || distinctField
				.equals("SOCIAL_SECURITY_AREA"))
				&& admin.getCpnyId().equals("C01")) {
			sqlContent = "SELECT A.PARAM_DATA_NO, "
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, "
					+ " A.FIELD2_VALUE, "
					+ " SY1.CONTENT     FIELD1_NAME, "
					+ " SY2.CONTENT     FIELD2_NAME, "
					+ " RETURN_VALUE, "
					+ " A.START_MONTH, "
					+ " A.END_MONTH, "
					+ " A.CPNY_ID, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM IS_PARAM_DATA_OTHER A, "
					+ "      HR_COMPANY          HR, "
					+ "      SY_GLOBAL_NAME      SY1, "
					+ "      SY_GLOBAL_NAME      SY2, "
					+ "      SY_GLOBAL_NAME      SY3 "
					+ "WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND SY3.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.FIELD1_VALUE = SY1.NO(+) "
					+ "  AND SY1.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.FIELD2_VALUE = SY2.NO(+) "
					+ "  AND SY2.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND A.FIELD1_VALUE IN ( "
					+ "		  SELECT DISTINCT T.DEPT_DISTINGUISH_NO FROM HR_DEPARTMENT T WHERE T.DEPTNO IN  "
					+ "		  (SELECT DEPTNO FROM PA_SUPERVISOR_INFO T WHERE T.PERSON_ID = '"+ adminId + "') " 
					+ "		  )";
		} else if (distinctField2.equals("DEPTNO")) {
			sqlContent = "	SELECT A.PARAM_DATA_NO, " 
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, " 
					+ " A.FIELD2_VALUE, "
					+ " get_global_name(A.FIELD1_VALUE,'" + language+ "')     FIELD1_NAME, " 
					+ " SY2.CONTENT     FIELD2_NAME, "
					+ " RETURN_VALUE, " 
					+ " A.START_MONTH,  "
					+ " A.END_MONTH, " 
					+ " A.CPNY_ID, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM IS_PARAM_DATA_OTHER A, "
					+ "   HR_COMPANY          HR, "
					+ "  hr_department_name      SY2, "
					+ "   SY_GLOBAL_NAME      SY3 "
					+ " WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND SY3.LANGUAGE(+) = '" + language + "' "
					+ "  AND A.FIELD2_VALUE = SY2.deptno(+) "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND SY2.LANGUAGE(+) = '" + language + "' ";
		} else {
			sqlContent = "SELECT A.PARAM_DATA_NO, " 
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, " 
					+ " A.FIELD2_VALUE, "
					+ " SY1.CONTENT     FIELD1_NAME, "
					+ " SY2.CONTENT     FIELD2_NAME, " 
					+ " RETURN_VALUE, "
					+ " A.START_MONTH, " 
					+ " A.END_MONTH, " 
					+ " A.CPNY_ID, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM IS_PARAM_DATA_OTHER A, "
					+ " HR_COMPANY          HR, "
					+ " SY_GLOBAL_NAME      SY1, "
					+ " SY_GLOBAL_NAME      SY2, "
					+ " SY_GLOBAL_NAME      SY3 "
					+ "WHERE A.CPNY_ID = HR.CPNY_ID "
					+ " AND HR.CPNY_NO = SY3.NO(+) "
					+ " AND SY3.LANGUAGE(+) =  '" + language + "' "
					+ " AND A.FIELD1_VALUE = SY1.NO(+) "
					+ " AND SY1.LANGUAGE(+) =  '" + language + "' "
					+ " AND A.FIELD2_VALUE = SY2.NO(+) "
					+ " AND SY2.LANGUAGE(+) ='" + language + "'"
					+ " AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ " AND A.CPNY_ID='" + admin.getCpnyId() + "'"
					+ " ORDER BY A.FIELD1_VALUE,A.FIELD2_VALUE";
		}
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		this.excelUtilSer.exportExcel(request, response, modelMap, map,
				aliasNameList, aliasList);
	}
	
	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaInputItemDataExcelIsNull")
	public void exportPaInputItemDataExcelIsNull(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		// 取得数据项目信息
		LinkedHashMap isInputItemInfo = (LinkedHashMap) paInputItemParamDao.getPaInputItemParamInfo(paramMap) ;
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(isInputItemInfo
				.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		String distinctField2 = ObjectUtils.toString(isInputItemInfo
				.get("DISTINCT_FIELD_2ND"));
		String language = admin.getLanguage();
		String adminId = admin.getAdminID();
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		String filed1Name = ObjectUtils.toString(isInputItemInfo
				.get("DISTINCT_FIELD_NAME"));
		String filed2Name = ObjectUtils.toString(isInputItemInfo
				.get("DISTINCT_FIELD_2ND_NAME"));
		aliasNameList.add(filed1Name);
		if (!filed2Name.equals("")) {
			aliasNameList.add(filed2Name);
		}
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.endMonth", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.dataValue", request));//数值

		aliasList.add("FIELD1_NAME");
		if (!filed2Name.equals("")) {
			aliasList.add("FIELD2_NAME");
		}
		aliasList.add("START_MONTH");
		aliasList.add("END_MONTH");
		aliasList.add("RETURN_VALUE");
		String sqlContent = null;

		if (distinctField.equals("CPNY_ID")) {
			sqlContent = "SELECT A.PARAM_DATA_NO, " 
					+ "A.PARAM_NO, "
					+ "A.FIELD1_VALUE, " 
					+ "A.FIELD2_VALUE, "
					+ "SY1.CONTENT     FIELD1_NAME, " 
					+ "RETURN_VALUE, "
					+ "A.START_MONTH, " 
					+ "A.END_MONTH, " 
					+ "A.CPNY_ID, "
					+ "SY3.CONTENT     CPNY_NAME "
					+ "FROM PA_PARAM_DATA_OTHER A, "
					+ "  HR_COMPANY          HR, "
					+ "  SY_GLOBAL_NAME      SY1, "
					+ "  HR_COMPANY          HC, "
					+ "  SY_GLOBAL_NAME      SY3 "
					+ " WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND SY3.LANGUAGE(+) = '" + language + "' "
					+ "  AND A.FIELD1_VALUE = HC.CPNY_ID "
					+ "  AND HC.CPNY_NO = SY1.NO(+) "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND SY1.LANGUAGE(+) = '" + language + "' ";
		} else if ((distinctField.equals("WORK_AREA") || distinctField
				.equals("SOCIAL_SECURITY_AREA"))
				&& admin.getCpnyId().equals("C01")) {
			sqlContent = "SELECT A.PARAM_DATA_NO, "
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, "
					+ " A.FIELD2_VALUE, "
					+ " SY1.CONTENT     FIELD1_NAME, "
					+ " SY2.CONTENT     FIELD2_NAME, "
					+ " RETURN_VALUE, "
					+ " A.START_MONTH, "
					+ " A.END_MONTH, "
					+ " A.CPNY_ID, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM PA_PARAM_DATA_OTHER A, "
					+ "      HR_COMPANY          HR, "
					+ "      SY_GLOBAL_NAME      SY1, "
					+ "      SY_GLOBAL_NAME      SY2, "
					+ "      SY_GLOBAL_NAME      SY3 "
					+ "WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND SY3.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.FIELD1_VALUE = SY1.NO(+) "
					+ "  AND SY1.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.FIELD2_VALUE = SY2.NO(+) "
					+ "  AND SY2.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND A.FIELD1_VALUE IN ( "
					+ "		  SELECT DISTINCT T.DEPT_DISTINGUISH_NO FROM HR_DEPARTMENT T WHERE T.DEPTNO IN  "
					+ "		  (SELECT DEPTNO FROM PA_SUPERVISOR_INFO T WHERE T.PERSON_ID = '"+ adminId + "') " 
					+ "		  )";
		} else if (distinctField2.equals("DEPTNO")) {
			sqlContent = "	SELECT A.PARAM_DATA_NO, " 
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, " 
					+ " A.FIELD2_VALUE, "
					+ " get_global_name(A.FIELD1_VALUE,'" + language+ "')     FIELD1_NAME, " 
					+ " SY2.CONTENT     FIELD2_NAME, "
					+ " RETURN_VALUE, " 
					+ " A.START_MONTH,  "
					+ " A.END_MONTH, " 
					+ " A.CPNY_ID, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM PA_PARAM_DATA_OTHER A, "
					+ "   HR_COMPANY          HR, "
					+ "  hr_department_name      SY2, "
					+ "   SY_GLOBAL_NAME      SY3 "
					+ " WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND SY3.LANGUAGE(+) = '" + language + "' "
					+ "  AND A.FIELD2_VALUE = SY2.deptno(+) "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND SY2.LANGUAGE(+) = '" + language + "' ";
		} else {
			sqlContent = "SELECT A.PARAM_DATA_NO, " 
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, " 
					+ " A.FIELD2_VALUE, "
					+ " SY1.CONTENT     FIELD1_NAME, "
					+ " SY2.CONTENT     FIELD2_NAME, " 
					+ " RETURN_VALUE, "
					+ " A.START_MONTH, " 
					+ " A.END_MONTH, " 
					+ " A.CPNY_ID, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM PA_PARAM_DATA_OTHER A, "
					+ " HR_COMPANY          HR, "
					+ " SY_GLOBAL_NAME      SY1, "
					+ " SY_GLOBAL_NAME      SY2, "
					+ " SY_GLOBAL_NAME      SY3 "
					+ "WHERE A.CPNY_ID = HR.CPNY_ID "
					+ " AND HR.CPNY_NO = SY3.NO(+) "
					+ " AND SY3.LANGUAGE(+) =  '" + language + "' "
					+ " AND A.FIELD1_VALUE = SY1.NO(+) "
					+ " AND SY1.LANGUAGE(+) =  '" + language + "' "
					+ " AND A.FIELD2_VALUE = SY2.NO(+) "
					+ " AND SY2.LANGUAGE(+) ='" + language + "'"
					+ " AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ " AND A.CPNY_ID='" + admin.getCpnyId() + "'"
					+ " ORDER BY A.FIELD1_VALUE,A.FIELD2_VALUE";
		}
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		this.excelUtilSer.exportExcel(request, response, modelMap, map,
				aliasNameList, aliasList);
	}
	
	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaBasicInputItemDataExcelIsNull")
	public void exportPaBasicInputItemDataExcelIsNull(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		// 取得数据项目信息
		LinkedHashMap isInputItemInfo = (LinkedHashMap) this.paBasicItemDao.getPaBasicItemDataInfo(paramMap) ;
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(isInputItemInfo
				.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		String distinctField2 = ObjectUtils.toString(isInputItemInfo
				.get("DISTINCT_FIELD_2ND"));
		String language = admin.getLanguage();
		String adminId = admin.getAdminID();
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		String filed1Name = ObjectUtils.toString(isInputItemInfo
				.get("DISTINCT_FIELD_NAME"));
		String filed2Name = ObjectUtils.toString(isInputItemInfo
				.get("DISTINCT_FIELD_2ND_NAME"));
		aliasNameList.add(filed1Name);
		if (!filed2Name.equals("")) {
			aliasNameList.add(filed2Name);
		}
		aliasNameList.add(TipMessage.getTipMessage(
				"public.title.startDate", request));//开始日期
		aliasNameList.add(TipMessage.getTipMessage(
				"public.title.endDate", request));//结束日期
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request));//备注
		aliasList.add("FIELD1_NAME");
		if (!filed2Name.equals("")) {
			aliasList.add("FIELD2_NAME");
		}
		aliasList.add("START_DATE");
		aliasList.add("END_DATE");
		aliasList.add("RETURN_VALUE");
		aliasList.add("REMARK");
		String sqlContent = null;

		if (distinctField.equals("CPNY_ID")) {
			sqlContent = "SELECT A.BASIC_DATA_NO, " 
					+ "A.PARAM_NO, "
					+ "A.FIELD1_VALUE, " 
					+ "A.FIELD2_VALUE, "
					+ "SY1.CONTENT     FIELD1_NAME, " 
					+ "RETURN_VALUE, "
					+ "A.START_DATE, " 
					+ "A.END_DATE, " 
					+ "A.CPNY_ID, "
					+ "SY3.CONTENT     CPNY_NAME "
					+ "FROM PA_BASIC_DATA_OTHER A, "
					+ "  HR_COMPANY          HR, "
					+ "  SY_GLOBAL_NAME      SY1, "
					+ "  HR_COMPANY          HC, "
					+ "  SY_GLOBAL_NAME      SY3 "
					+ " WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND SY3.LANGUAGE(+) = '" + language + "' "
					+ "  AND A.FIELD1_VALUE = HC.CPNY_ID "
					+ "  AND HC.CPNY_NO = SY1.NO(+) "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND SY1.LANGUAGE(+) = '" + language + "' ";
		} else if ((distinctField.equals("WORK_AREA") || distinctField
				.equals("SOCIAL_SECURITY_AREA"))
				&& admin.getCpnyId().equals("C01")) {
			sqlContent = "SELECT A.BASIC_DATA_NO, "
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, "
					+ " A.FIELD2_VALUE, "
					+ " SY1.CONTENT     FIELD1_NAME, "
					+ " SY2.CONTENT     FIELD2_NAME, "
					+ " RETURN_VALUE, "
					+ " A.START_DATE, "
					+ " A.END_DATE, "
					+ " A.CPNY_ID, "
					+ " A.REMARK, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM PA_BASIC_DATA_OTHER A, "
					+ "      HR_COMPANY          HR, "
					+ "      SY_GLOBAL_NAME      SY1, "
					+ "      SY_GLOBAL_NAME      SY2, "
					+ "      SY_GLOBAL_NAME      SY3 "
					+ "WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND SY3.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.FIELD1_VALUE = SY1.NO(+) "
					+ "  AND SY1.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.FIELD2_VALUE = SY2.NO(+) "
					+ "  AND SY2.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND A.FIELD1_VALUE IN ( "
					+ "		  SELECT DISTINCT T.DEPT_DISTINGUISH_NO FROM HR_DEPARTMENT T WHERE T.DEPTNO IN  "
					+ "		  (SELECT DEPTNO FROM PA_SUPERVISOR_INFO T WHERE T.PERSON_ID = '"+ adminId + "') " 
					+ "		  )";
		} else if (distinctField2.equals("DEPTNO")) {
			sqlContent = "	SELECT A.BASIC_DATA_NO, " 
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, " 
					+ " A.FIELD2_VALUE, "
					+ " get_global_name(A.FIELD1_VALUE,'" + language+ "')     FIELD1_NAME, " 
					+ " SY2.CONTENT     FIELD2_NAME, "
					+ " RETURN_VALUE, " 
					+ " A.START_DATE,  "
					+ " A.END_DATE, " 
					+ " A.CPNY_ID, "
					+ " A.REMARK, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM PA_BASIC_DATA_OTHER A, "
					+ "   HR_COMPANY          HR, "
					+ "  hr_department_name      SY2, "
					+ "   SY_GLOBAL_NAME      SY3 "
					+ " WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND SY3.LANGUAGE(+) = '" + language + "' "
					+ "  AND A.FIELD2_VALUE = SY2.deptno(+) "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND SY2.LANGUAGE(+) = '" + language + "' ";
		} else {
			sqlContent = "SELECT A.BASIC_DATA_NO, " 
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, " 
					+ " A.FIELD2_VALUE, "
					+ " SY1.CONTENT     FIELD1_NAME, "
					+ " SY2.CONTENT     FIELD2_NAME, " 
					+ " RETURN_VALUE, "
					+ " A.START_DATE, " 
					+ " A.END_DATE, " 
					+ " A.CPNY_ID, "
					+ " A.REMARK, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM PA_BASIC_DATA_OTHER A, "
					+ " HR_COMPANY          HR, "
					+ " SY_GLOBAL_NAME      SY1, "
					+ " SY_GLOBAL_NAME      SY2, "
					+ " SY_GLOBAL_NAME      SY3 "
					+ "WHERE A.CPNY_ID = HR.CPNY_ID "
					+ " AND HR.CPNY_NO = SY3.NO(+) "
					+ " AND SY3.LANGUAGE(+) =  '" + language + "' "
					+ " AND A.FIELD1_VALUE = SY1.NO(+) "
					+ " AND SY1.LANGUAGE(+) =  '" + language + "' "
					+ " AND A.FIELD2_VALUE = SY2.NO(+) "
					+ " AND SY2.LANGUAGE(+) ='" + language + "'"
					+ " AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ " AND A.CPNY_ID='" + admin.getCpnyId() + "'"
					+ " ORDER BY A.FIELD1_VALUE,A.FIELD2_VALUE";
		}
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		this.excelUtilSer.exportExcel(request, response, modelMap, map,
				aliasNameList, aliasList);
	}
	
	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportBonusInputItemDataExcelIsNull")
	public void exportBonusInputItemDataExcelIsNull(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		// 取得数据项目信息
		LinkedHashMap isInputItemInfo = (LinkedHashMap) this.bonusInputItemParamDao.getPaBonusInputItemParamInfo(paramMap);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(isInputItemInfo
				.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		String distinctField2 = ObjectUtils.toString(isInputItemInfo
				.get("DISTINCT_FIELD_2ND"));
		String language = admin.getLanguage();
		String adminId = admin.getAdminID();
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		String filed1Name = ObjectUtils.toString(isInputItemInfo
				.get("DISTINCT_FIELD_NAME"));
		String filed2Name = ObjectUtils.toString(isInputItemInfo
				.get("DISTINCT_FIELD_2ND_NAME"));
		aliasNameList.add(filed1Name);
		if (!filed2Name.equals("")) {
			aliasNameList.add(filed2Name);
		}
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.endMonth", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.dataValue", request));//数值

		aliasList.add("FIELD1_NAME");
		if (!filed2Name.equals("")) {
			aliasList.add("FIELD2_NAME");
		}
		aliasList.add("START_MONTH");
		aliasList.add("END_MONTH");
		aliasList.add("RETURN_VALUE");
		String sqlContent = null;

		if (distinctField.equals("CPNY_ID")) {
			sqlContent = "SELECT A.PARAM_DATA_NO, " 
					+ "A.PARAM_NO, "
					+ "A.FIELD1_VALUE, " 
					+ "A.FIELD2_VALUE, "
					+ "SY1.CONTENT     FIELD1_NAME, " 
					+ "RETURN_VALUE, "
					+ "A.START_MONTH, " 
					+ "A.END_MONTH, " 
					+ "A.CPNY_ID, "
					+ "SY3.CONTENT     CPNY_NAME "
					+ "FROM BN_PARAM_DATA_OTHER A, "
					+ "  HR_COMPANY          HR, "
					+ "  SY_GLOBAL_NAME      SY1, "
					+ "  HR_COMPANY          HC, "
					+ "  SY_GLOBAL_NAME      SY3 "
					+ " WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND SY3.LANGUAGE(+) = '" + language + "' "
					+ "  AND A.FIELD1_VALUE = HC.CPNY_ID "
					+ "  AND HC.CPNY_NO = SY1.NO(+) "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND SY1.LANGUAGE(+) = '" + language + "' ";
		} else if ((distinctField.equals("WORK_AREA") || distinctField
				.equals("SOCIAL_SECURITY_AREA"))
				&& admin.getCpnyId().equals("C01")) {
			sqlContent = "SELECT A.PARAM_DATA_NO, "
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, "
					+ " A.FIELD2_VALUE, "
					+ " SY1.CONTENT     FIELD1_NAME, "
					+ " SY2.CONTENT     FIELD2_NAME, "
					+ " RETURN_VALUE, "
					+ " A.START_MONTH, "
					+ " A.END_MONTH, "
					+ " A.CPNY_ID, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM BN_PARAM_DATA_OTHER A, "
					+ "      HR_COMPANY          HR, "
					+ "      SY_GLOBAL_NAME      SY1, "
					+ "      SY_GLOBAL_NAME      SY2, "
					+ "      SY_GLOBAL_NAME      SY3 "
					+ "WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND SY3.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.FIELD1_VALUE = SY1.NO(+) "
					+ "  AND SY1.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.FIELD2_VALUE = SY2.NO(+) "
					+ "  AND SY2.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND A.FIELD1_VALUE IN ( "
					+ "		  SELECT DISTINCT T.DEPT_DISTINGUISH_NO FROM HR_DEPARTMENT T WHERE T.DEPTNO IN  "
					+ "		  (SELECT DEPTNO FROM PA_SUPERVISOR_INFO T WHERE T.PERSON_ID = '"+ adminId + "') " 
					+ "		  )";
		} else if (distinctField2.equals("DEPTNO")) {
			sqlContent = "	SELECT A.PARAM_DATA_NO, " 
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, " 
					+ " A.FIELD2_VALUE, "
					+ " get_global_name(A.FIELD1_VALUE,'" + language+ "')     FIELD1_NAME, " 
					+ " SY2.CONTENT     FIELD2_NAME, "
					+ " RETURN_VALUE, " 
					+ " A.START_MONTH,  "
					+ " A.END_MONTH, " 
					+ " A.CPNY_ID, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM BN_PARAM_DATA_OTHER A, "
					+ "   HR_COMPANY          HR, "
					+ "  hr_department_name      SY2, "
					+ "   SY_GLOBAL_NAME      SY3 "
					+ " WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND SY3.LANGUAGE(+) = '" + language + "' "
					+ "  AND A.FIELD2_VALUE = SY2.deptno(+) "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND SY2.LANGUAGE(+) = '" + language + "' ";
		} else {
			sqlContent = "SELECT A.PARAM_DATA_NO, " 
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, " 
					+ " A.FIELD2_VALUE, "
					+ " SY1.CONTENT     FIELD1_NAME, "
					+ " SY2.CONTENT     FIELD2_NAME, " 
					+ " RETURN_VALUE, "
					+ " A.START_MONTH, " 
					+ " A.END_MONTH, " 
					+ " A.CPNY_ID, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM BN_PARAM_DATA_OTHER A, "
					+ " HR_COMPANY          HR, "
					+ " SY_GLOBAL_NAME      SY1, "
					+ " SY_GLOBAL_NAME      SY2, "
					+ " SY_GLOBAL_NAME      SY3 "
					+ "WHERE A.CPNY_ID = HR.CPNY_ID "
					+ " AND HR.CPNY_NO = SY3.NO(+) "
					+ " AND SY3.LANGUAGE(+) =  '" + language + "' "
					+ " AND A.FIELD1_VALUE = SY1.NO(+) "
					+ " AND SY1.LANGUAGE(+) =  '" + language + "' "
					+ " AND A.FIELD2_VALUE = SY2.NO(+) "
					+ " AND SY2.LANGUAGE(+) ='" + language + "'"
					+ " AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ " AND A.CPNY_ID='" + admin.getCpnyId() + "'"
					+ " ORDER BY A.FIELD1_VALUE,A.FIELD2_VALUE";
		}
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		this.excelUtilSer.exportExcel(request, response, modelMap, map,
				aliasNameList, aliasList);
	}

	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportInsuranceInputItemDataExcelIsNotNull")
	public void exportInsuranceInputItemDataExcelIsNotNull(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String language = admin.getLanguage();
		String adminId = admin.getAdminID();
		String deptno = request.getParameter("deptno");

		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage(
				"public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage(
				"public.title.name", request));//姓名
		aliasNameList.add(TipMessage.getTipMessage(
				"public.title.deptName", request));//部门
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.postGrade", request));//职级
		aliasNameList.add(TipMessage.getTipMessage(
				"hr.viewPersonalInfo.title.STATUS_NAME", request));//员工状态
//		aliasNameList.add(TipMessage.getTipMessage(
//				"liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY", request));//试用与否
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.companyLegalPerson", request));//公司法人
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.endMonth", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage(
				"hr.viewPromote.title.REMARK", request));//备注
		
		aliasList.add("EMPID");
		aliasList.add("LOCAL_NAME");
		aliasList.add("DEPT_NAME");
		aliasList.add("POST_GRADE_NAME");
		aliasList.add("EMP_OFFICE");
//		aliasList.add("IN_THE_DIFFERENCE");
		aliasList.add("CPNY_NAME");
		aliasList.add("START_MONTH");
		aliasList.add("END_MONTH");
		aliasList.add("RETURN_VALUE");
		aliasList.add("REMARK");
		
		String sqlContent = " SELECT HE.EMPID, " + " HE.LOCAL_NAME, "
				+ " HD.CONTENT     DEPT_NAME, "
				+ " SY0.CONTENT    POST_GRADE_NAME, "
				+ " SY1.CONTENT    STATUS_NAME, "
				+ " SY2.CONTENT    CPNY_NAME, " + " T.START_MONTH, "
				+ " T.END_MONTH, " + " T.RETURN_VALUE, " + " T.PARAM_DATA_NO ,"
				+ " GET_GLOBAL_NAME( HE.EMP_OFFICE,'"+language+"') EMP_OFFICE,"
//				+ " HE.IN_THE_DIFFERENCE, T.REMARK REMARK"
				+ "  T.REMARK REMARK"
				+ "  FROM IS_PARAM_DATA      T, " + " HR_EMPLOYEE        HE, "
				+ " HR_COMPANY         HR, " + " HR_DEPARTMENT_NAME HD, "
				+ " SY_GLOBAL_NAME     SY0, " + " SY_GLOBAL_NAME     SY1, "
				+ " SY_GLOBAL_NAME     SY2 "
				+ "  WHERE T.PERSON_ID = HE.PERSON_ID "
				+ "  AND HE.DEPTNO = HD.DEPTNO(+) "
				+ "   AND HE.EMP_TYPE_CODE  IN ( SELECT E.EMP_TYPE_CODE FROM PA_SUPERVISOR_EMPTYPE_INFO E,HR_JOB_TYPE_SETUP F WHERE E.EMP_TYPE_CODE = F.JOBTYPE_NO "
				                + "    AND E.PERSON_ID =" 
								+ admin.getAdminID() 
				                +  "   AND F.CPNY_ID ='"
				                + admin.getCpnyId()+"')"
				+ "  AND HD.LANGUAGE(+) = '"
				+ language
				+ "' "
				+ "  AND HE.POST_GRADE_NO = SY0.NO(+) "
				+ "  AND SY0.LANGUAGE(+) = '"
				+ language
				+ "' "
				+ "  AND HE.STATUS_CODE = SY1.NO(+) "
				+ "  AND SY1.LANGUAGE(+) = '"
				+ language
				+ "' "
				+ "  AND T.CPNY_ID = HR.CPNY_ID "
				+ "  AND HR.CPNY_NO = SY2.NO(+) "
				+ "  AND SY2.LANGUAGE(+) = '"
				+ admin.getLanguage()
				+ "' "
				+ "	 AND T.PARAM_NO='"
				+ request.getParameter("id")
				+ "'	 AND T.CPNY_ID='"
				+ admin.getCpnyId()
				+ "'"
				+ "  AND EXISTS( "
				+ " SELECT * "
				+ "  FROM PA_SUPERVISOR_INFO "
				+ " WHERE PA_SUPERVISOR_INFO.DEPTNO = HE.DEPTNO "
				+ " AND PA_SUPERVISOR_INFO.PERSON_ID ='"
				+ adminId
				+ "') ";
			if(request.getParameter("empid")!=null&&!request.getParameter("empid").equals("")){
				sqlContent=sqlContent+"  AND  ( HE.EMPID LIKE '%' || "+request.getParameter("empid")+" || '%' OR HE.LOCAL_NAME LIKE '%' || "+request.getParameter("empid")+" || '%' )";
			}
			if(deptno!=null&&!deptno.equals("")){
				sqlContent=sqlContent+ " AND EXISTS(SELECT * "
						               +" FROM HR_DEPARTMENT "
						               +" WHERE HR_DEPARTMENT.DEPTNO = HE.DEPTNO "
						               +" START WITH HR_DEPARTMENT.DEPTNO = '"+deptno+"' "
						               +" CONNECT BY PRIOR HR_DEPARTMENT.DEPTNO = HR_DEPARTMENT.PARENT_DEPT_NO "+
										" AND HR_DEPARTMENT.DEPTNO IN(SELECT DEPTNO FROM PA_SUPERVISOR_INFO WHERE PERSON_ID= " +
										 admin.getAdminID() 
										+"))	  ";
						               ;
			}
			if(request.getParameter("year")!=null&&request.getParameter("month")!=null&&!request.getParameter("year").equals("")&&!request.getParameter("month").equals("")){
				String IS_MONTH=request.getParameter("year")+request.getParameter("month");
				sqlContent=sqlContent+ " AND  T.START_MONTH IS NOT NULL "
               +"  AND LENGTH(TRIM(T.START_MONTH))=6 "
               +"  AND (T.END_MONTH IS NULL OR LENGTH(TRIM(T.END_MONTH)) = 6) "
               +"  AND ADD_MONTHS(TO_DATE('"+IS_MONTH+"', 'YYYYMM'), -0) BETWEEN "
               +"  TO_DATE(TRIM(T.START_MONTH), 'YYYYMM') AND"
               +"  NVL(TO_DATE(T.END_MONTH, 'YYYYMM'), TO_DATE('204912','YYYYMM')) ";
			}
		sqlContent=sqlContent+ " ORDER BY HE.EMPID";
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		this.excelUtilSer.exportExcel(request, response, modelMap, map,
				aliasNameList, aliasList);
	}
	
	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaInputItemDataExcelIsNotNull")
	public void exportPaInputItemDataExcelIsNotNull(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String language = admin.getLanguage();
		String adminId = admin.getAdminID();
		String empid=request.getParameter("empid");
		String CODE_NO=request.getParameter("seach_CODE_NO");
		String deptno=request.getParameter("deptno");
		String year=request.getParameter("year");
//		String month=request.getParameter("month");
		String remark=request.getParameter("remark");
		String jobtypegroup=request.getParameter("jobtypegroup");
		String DISTINCT_FIELD=request.getParameter("DISTINCT_FIELD");//按职级还是PERSON_ID下载
		
		//因有数据导出时不选月份，报错，作如下更改
		Calendar c = Calendar.getInstance();
		String nowmonth = String.valueOf(c.get(Calendar.MONTH)+1);
		String month="";
		if (request.getParameter("month") != null 
				&& !"".equals(request.getParameter("month"))
				&& !"null".equals(request.getParameter("month"))) {
			month=request.getParameter("month");
		}else {
			month = nowmonth;
		}
		
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		
		if ("POST_GRADE_NO".equals(DISTINCT_FIELD)) {
			aliasNameList.add(TipMessage.getTipMessage(
					"pa.insurance.title.postGrade", request));//职级
			aliasNameList.add(TipMessage.getTipMessage(
					"pa.insurance.title.startMonth", request));//开始月
			aliasNameList.add(TipMessage.getTipMessage(
					"pa.insurance.title.endMonth", request));//结束月
			aliasNameList.add(TipMessage.getTipMessage(
					"pa.insurance.title.dataValue", request));//数值
			aliasNameList.add(TipMessage.getTipMessage(
					"hr.viewPromote.title.REMARK", request));//备注
		}else {
			aliasNameList.add(TipMessage.getTipMessage(
					"public.title.empId", request));//工号
			aliasNameList.add(TipMessage.getTipMessage(
					"public.title.name", request));//姓名
			aliasNameList.add(TipMessage.getTipMessage(
					"public.title.deptName", request));//部门
			aliasNameList.add(TipMessage.getTipMessage(
					"pa.insurance.title.postGrade", request));//职级
//			aliasNameList.add(TipMessage.getTipMessage(
//					"pa.insurance.title.status", request));//状态
			aliasNameList.add(TipMessage.getTipMessage(
					"hr.viewPersonalInfo.title.STATUS_NAME", request));//员工状态
//			aliasNameList.add(TipMessage.getTipMessage(
//					"liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY", request));//试用与否
			aliasNameList.add(TipMessage.getTipMessage(
					"pa.insurance.title.startMonth", request));//开始月
			aliasNameList.add(TipMessage.getTipMessage(
					"pa.insurance.title.endMonth", request));//结束月
			aliasNameList.add(TipMessage.getTipMessage(
					"pa.insurance.title.dataValue", request));//数值
			aliasNameList.add(TipMessage.getTipMessage(
					"hr.viewPromote.title.REMARK", request));//备注
		}
		
		
		if ("POST_GRADE_NO".equals(DISTINCT_FIELD)) {
			aliasList.add("POST_GRADE_NAME");
			aliasList.add("START_MONTH");
			aliasList.add("END_MONTH");
			aliasList.add("RETURN_VALUE");
			aliasList.add("REMARK");
		}else {
			aliasList.add("EMPID");
			aliasList.add("LOCAL_NAME");
			aliasList.add("DEPT_NAME");
			aliasList.add("POST_GRADE_NAME");
//			aliasList.add("STATUS_NAME");
			aliasList.add("EMP_OFFICE");
//			aliasList.add("IN_THE_DIFFERENCE");
			aliasList.add("START_MONTH");
			aliasList.add("END_MONTH");
			aliasList.add("RETURN_VALUE");
			aliasList.add("REMARK");
		}
		String sqlContent="";
		if ("POST_GRADE_NO".equals(DISTINCT_FIELD)) {
			sqlContent = " SELECT  GET_GLOBAL_NAME(T.FIELD1_VALUE,'"+language+"') POST_GRADE_NAME, "
			 + " T.START_MONTH, "
			+ " T.END_MONTH, " + " T.RETURN_VALUE, " + " T.REMARK, " + " T.PARAM_DATA_NO "
			+ "  FROM PA_PARAM_DATA_OTHER      T" 
			+ "  WHERE   T.ACTIVITY =1 "
			+ "	 AND T.PARAM_NO="
			+ request.getParameter("id")
			+ "	 AND T.CPNY_ID='"
			+ admin.getCpnyId()
			+ "'";
			if((year!=null&&!year.equals(""))&&(month!=null&&!month.equals(""))){
				//String startTime=year+(Integer.parseInt(month)-1)+"";
				String endTime=year+month+"";
				//sqlContent+=" AND T.START_MONTH BETWEEN  '"+startTime+"' and '"+endTime+"' ";
				sqlContent+= " AND TO_DATE('"+endTime+"', 'YYYYMM') BETWEEN "
					+" TO_DATE(T.START_MONTH, 'YYYYMM') AND "
					+" NVL(TO_DATE(T.END_MONTH, 'YYYYMM'), TO_DATE('999912','YYYYMM')) ";

			}
			if(CODE_NO!=null&&!"".equals(CODE_NO)){
				sqlContent+=" AND (T.FIELD1_VALUE = '"+CODE_NO+"')";
			}
			sqlContent+= " ORDER BY T.FIELD1_VALUE";
		}else {
			sqlContent = " SELECT HE.EMPID, " + " HE.LOCAL_NAME, "
			+ " GET_dept_NAME(HE.DEPTNO,'"+language+"')     			DEPT_NAME, "
			+ " SY0.CONTENT    			POST_GRADE_NAME, "
//			+ " SY1.CONTENT    STATUS_NAME, "
			+ " GET_GLOBAL_NAME(HE.EMP_OFFICE,'"
			+ language
			+ "')  			EMP_OFFICE,"
//			+ " HE.IN_THE_DIFFERENCE  	IN_THE_DIFFERENCE,"
			+ " SY2.CONTENT    CPNY_NAME, " + " T.START_MONTH, "
			+ " T.END_MONTH, " + " T.RETURN_VALUE, " + " T.REMARK, " + " T.PARAM_DATA_NO "
			+ "  FROM PA_PARAM_DATA      T, " + " HR_EMPLOYEE        HE, "
			+ " HR_COMPANY         HR, " 
			+ " SY_GLOBAL_NAME     SY0, " 
//			+ " SY_GLOBAL_NAME     SY1, "
			+ " SY_GLOBAL_NAME     SY2 "
			+ "  WHERE T.PERSON_ID = HE.PERSON_ID "
			/*+ "   AND HE.EMP_TYPE_CODE  IN ( SELECT E.EMP_TYPE_CODE FROM PA_SUPERVISOR_EMPTYPE_INFO E,HR_JOB_TYPE_SETUP F WHERE E.EMP_TYPE_CODE = F.JOBTYPE_NO "
            + "    AND E.PERSON_ID =" 
			+ admin.getAdminID() 
            +  "   AND F.CPNY_ID ='"
            + admin.getCpnyId()+"')"*/
			+ "  AND HE.POST_GRADE_NO = SY0.NO(+) "
			+ "  AND SY0.LANGUAGE(+) = '"
			+ language
			+ "' "
//			+ "  AND HE.STATUS_CODE = SY1.NO(+) "
//			+ "  AND SY1.LANGUAGE(+) = '"
//			+ language
//			+ "' "
			+ "  AND T.CPNY_ID = HR.CPNY_ID "
			+ "  AND T.ACTIVITY =1 "
			+ "  AND (HE.DATE_LEFT IS NULL OR TO_CHAR(HE.DATE_LEFT,'YYYYMM')>= to_char(add_months(to_date("+year+month+",'yyyymm'),-2),'yyyymm')) "
			+ "  AND HR.CPNY_NO = SY2.NO(+) "
			+ "  AND SY2.LANGUAGE(+) = '"
			+ admin.getLanguage()
			+ "' "
			+ "	 AND T.PARAM_NO="
			+ request.getParameter("id")
			+ "	 AND T.CPNY_ID='"
			+ admin.getCpnyId()
			+ "'";
//			+ "  AND EXISTS( "
//			+ " SELECT * "
//			+ "  FROM PA_SUPERVISOR_INFO "
//			+ " WHERE PA_SUPERVISOR_INFO.DEPTNO = HE.DEPTNO "
//			+ " AND PA_SUPERVISOR_INFO.PERSON_ID ='"
//			+ adminId
//			+ "') ";
			if(empid!=null&&!empid.equals("")){
				sqlContent+=" AND (HE.EMPID LIKE '%"+empid+"%' OR HE.LOCAL_NAME LIKE '%"+empid+"%') ";
			}
			if(remark!=null&&!remark.equals("")){
				sqlContent+=" AND (T.REMARK LIKE '%"+remark+"%')";
			}
			if(deptno!=null&&!deptno.equals("")){
				sqlContent+=" AND EXISTS (SELECT * "
					+" FROM HR_DEPARTMENT B1 "
					+" WHERE B1.DEPTNO = HE.DEPTNO"
					+" START WITH B1.DEPTNO = '"+deptno+"'"
					+" CONNECT BY PRIOR B1.DEPTNO = B1.PARENT_DEPT_NO)" ;
//					" AND B1.DEPTNO IN(SELECT DEPTNO FROM PA_SUPERVISOR_INFO WHERE PERSON_ID= " +
//					 admin.getAdminID() 
//					+"))	  ";
			}
			if((year!=null&&!year.equals(""))&&(month!=null&&!month.equals(""))){
				//String startTime=year+(Integer.parseInt(month)-1)+"";
				String endTime=year+month+"";
				//sqlContent+=" AND T.START_MONTH BETWEEN  '"+startTime+"' and '"+endTime+"' ";
				sqlContent+= " AND TO_DATE('"+endTime+"', 'YYYYMM') BETWEEN "
					+" TO_DATE(T.START_MONTH, 'MMYYYY') AND "
					+" NVL(TO_DATE(T.END_MONTH, 'MMYYYY'), TO_DATE('129999','MMYYYY')) ";

			}				
			sqlContent+= " ORDER BY HE.EMPID";
		}
		
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		this.excelUtilSer.exportExcel(request, response, modelMap, map,
				aliasNameList, aliasList);
	}
	
	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaBasicInputItemDataExcelIsNotNull")
	public void exportPaBasicInputItemDataExcelIsNotNull(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String language = admin.getLanguage();
		String adminId = admin.getAdminID();
		String empid=request.getParameter("empid");
		String deptno=request.getParameter("deptno");
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));//姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName", request));//部门
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.postGrade", request));//职级
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.STATUS_NAME", request));//状态
		aliasNameList.add(TipMessage.getTipMessage("public.title.startDate", request));//开始日期
		aliasNameList.add(TipMessage.getTipMessage("public.title.endDate", request));//结束日期
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request));//备注
		
		aliasList.add("EMPID");
		aliasList.add("LOCAL_NAME");
		aliasList.add("DEPT_NAME");
		aliasList.add("POST_GRADE_NAME");
		aliasList.add("STATUS_NAME");
		aliasList.add("START_DATE");
		aliasList.add("END_DATE");
		aliasList.add("RETURN_VALUE");
		aliasList.add("REMARK");
		String sqlContent = " SELECT HE.EMPID, " + " HE.LOCAL_NAME, "
				+ " HD.CONTENT     DEPT_NAME, "
				+ " SY0.CONTENT    POST_GRADE_NAME, "
				+ " SY1.CONTENT    STATUS_NAME, "
				+ " SY2.CONTENT    CPNY_NAME, " + " T.START_DATE, "
				+ " T.END_DATE, " + " T.RETURN_VALUE, " + " T.BASIC_DATA_NO, "+"T.REMARK "
				+ "  FROM PA_BASIC_DATA      T, " + " HR_EMPLOYEE        HE, "
				+ " HR_COMPANY         HR, " + " HR_DEPARTMENT_NAME HD, "
				+ " SY_GLOBAL_NAME     SY0, " + " SY_GLOBAL_NAME     SY1, "
				+ " SY_GLOBAL_NAME     SY2 "
				+ "  WHERE T.PERSON_ID = HE.PERSON_ID "
				+ "  AND HE.DEPTNO = HD.DEPTNO(+) "
				+ "  AND HD.LANGUAGE(+) = '"
				+ language
				+ "' "
				+ "  AND HE.POST_GRADE_NO = SY0.NO(+) "
				+ "  AND SY0.LANGUAGE(+) = '"
				+ language
				+ "' "
				+ "  AND HE.EMP_OFFICE = SY1.NO(+) "
				+ "  AND SY1.LANGUAGE(+) = '"
				+ language
				+ "' "
				+ "  AND T.CPNY_ID = HR.CPNY_ID "
				+ "  AND HR.CPNY_NO = SY2.NO(+) "
				+ "  AND SY2.LANGUAGE(+) = '"
				+ admin.getLanguage()
				+ "' "
				+ "	 AND T.PARAM_NO="
				+ request.getParameter("id")
				+ "	 AND T.CPNY_ID='"
				+ admin.getCpnyId()
				+ "'"
				+ "  AND EXISTS( "
				+ " SELECT * "
				+ "  FROM PA_SUPERVISOR_INFO "
				+ " WHERE PA_SUPERVISOR_INFO.DEPTNO = HE.DEPTNO "
				+ " AND PA_SUPERVISOR_INFO.PERSON_ID ='"
				+ adminId
				+ "') ";
		if(empid!=null&&!empid.equals("")){
			sqlContent+=" AND (HE.EMPID LIKE '%"+empid+"%' OR HE.LOCAL_NAME LIKE '%"+empid+"%') ";
		}
		if(deptno!=null&&!deptno.equals("")){
			sqlContent+=" AND EXISTS (SELECT * "
		         +"FROM HR_DEPARTMENT B1 "
		         +" WHERE B1.DEPTNO = HE.DEPTNO "
		         +" START WITH B1.DEPTNO = '"+deptno+"' "
		       +" CONNECT BY PRIOR B1.DEPTNO = B1.PARENT_DEPT_NO)	  ";
		}
		//TO_CHAR(ADD_MONTHS(TO_DATE(#PA_MONTH:VARCHAR#, 'YYYY-MM-DD'),-1),'YYYYMM')
		//BETWEEN TO_CHAR(A.START_DATE,'YYYYMM')
		//AND TO_CHAR(NVL(A.END_DATE,TO_DATE('2049-12-31','YYYY-MM-DD')),'YYYYMM')
		if((year!=null&&!year.equals(""))&&(month!=null&&!month.equals(""))){
			//String startTime=year+(Integer.parseInt(month)-1)+"";
			String endTime=year+"-"+month+"-"+"01";
			//sqlContent+=" AND T.START_MONTH BETWEEN  '"+startTime+"' and '"+endTime+"' ";
			sqlContent+= " AND TO_CHAR(ADD_MONTHS(TO_DATE('"+endTime+"', 'YYYY-MM-DD'),-0),'YYYYMM')"
				+"BETWEEN TO_CHAR(T.START_DATE,'YYYYMM')"
				+"AND TO_CHAR(NVL(T.END_DATE,TO_DATE('2049-12-31','YYYY-MM-DD')),'YYYYMM')";

		}
		sqlContent+= " ORDER BY HE.EMPID";
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		this.excelUtilSer.exportExcel(request, response, modelMap, map,
				aliasNameList, aliasList);
	}
	
	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportBonusInputItemDataExcelIsNotNull")
	public void exportBonusInputItemDataExcelIsNotNull(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String language = admin.getLanguage();
		String adminId = admin.getAdminID();
		String empid=request.getParameter("empid");
		String deptno=request.getParameter("deptno");
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));//姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName", request));//部门
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.postGrade", request));//职级
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.STATUS_NAME", request));//员工状态
//		aliasNameList.add(TipMessage.getTipMessage("liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY", request));//试用与否
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.companyLegalPerson", request));//公司法人
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request));//备注
		

		aliasList.add("EMPID");
		aliasList.add("LOCAL_NAME");
		aliasList.add("DEPT_NAME");
		aliasList.add("POST_GRADE_NAME");
		aliasList.add("STATUS_NAME");//员工状态
//		aliasList.add("IN_THE_DIFFERENCE");//试用与否
		aliasList.add("CPNY_NAME");//公司法人
		aliasList.add("START_MONTH");
		aliasList.add("END_MONTH");
		aliasList.add("RETURN_VALUE");
		aliasList.add("REMARK");
//		String sqlContent = " SELECT HE.EMPID, " + " HE.LOCAL_NAME, "+" HE.IN_THE_DIFFERENCE, T.REMARK, "
		String sqlContent = " SELECT HE.EMPID, " + " HE.LOCAL_NAME, "+"  T.REMARK, "
				+ " HD.CONTENT     DEPT_NAME, "
				+ " SY0.CONTENT    POST_GRADE_NAME, "
				+ " SY1.CONTENT    STATUS_NAME, "
				+ " SY2.CONTENT    CPNY_NAME, " + " T.START_MONTH, "
				+ " T.END_MONTH, " + " T.RETURN_VALUE, " + " T.PARAM_DATA_NO "
				+ "  FROM BN_PARAM_DATA      T, " + " HR_EMPLOYEE        HE, "
				+ " HR_COMPANY         HR, " + " HR_DEPARTMENT_NAME HD, "
				+ " SY_GLOBAL_NAME     SY0, " + " SY_GLOBAL_NAME     SY1, "
				+ " SY_GLOBAL_NAME     SY2 "
				+ "  WHERE T.PERSON_ID = HE.PERSON_ID "
				+ "  AND HE.DEPTNO = HD.DEPTNO(+) "
				+ "  AND HD.LANGUAGE(+) = '"
				+ language
				+ "' "
				+ "  AND HE.POST_GRADE_NO = SY0.NO(+) "
				+ "  AND SY0.LANGUAGE(+) = '"
				+ language
				+ "' "
				+ "  AND HE.STATUS_CODE = SY1.NO(+) "
				+ "  AND SY1.LANGUAGE(+) = '"
				+ language
				+ "' "
				+ "  AND T.CPNY_ID = HR.CPNY_ID "
				+ "  AND HR.CPNY_NO = SY2.NO(+) "
				+ "  AND SY2.LANGUAGE(+) = '"
				+ admin.getLanguage()
				+ "' "
				+ "	 AND T.PARAM_NO="
				+ request.getParameter("id")
				+ "	 AND T.CPNY_ID='"
				+ admin.getCpnyId()
				+ "'"
				+ "  AND EXISTS( "
				+ " SELECT * "
				+ "  FROM PA_SUPERVISOR_INFO "
				+ " WHERE PA_SUPERVISOR_INFO.DEPTNO = HE.DEPTNO "
				+ " AND PA_SUPERVISOR_INFO.PERSON_ID ='"
				+ adminId
				+ "') ";
				if(empid!=null&&!empid.equals("")){
					sqlContent+=" AND (HE.EMPID LIKE '%"+empid+"%' OR HE.LOCAL_NAME LIKE '%"+empid+"%') ";
				}
				if(deptno!=null&&!deptno.equals("")){
					sqlContent+=" EXISTS (SELECT *"
				         +"FROM HR_DEPARTMENT B1"
				         +"WHERE B1.DEPTNO = HE.DEPTNO"
				         +"START WITH B1.DEPTNO = "+deptno+""
				       +"CONNECT BY PRIOR B1.DEPTNO = B1.PARENT_DEPT_NO)	  ";
				}
				if((year!=null&&!year.equals(""))&&(month!=null&&!month.equals(""))){
					//String startTime=year+(Integer.parseInt(month)-1)+"";
					String endTime=year+month+"";
					//sqlContent+=" AND T.START_MONTH BETWEEN  '"+startTime+"' and '"+endTime+"' ";
					sqlContent+= " AND T.START_MONTH IS NOT NULL"
                    +" AND LENGTH(T.START_MONTH)=6 "
					   +" AND ((T.END_MONTH IS NOT NULL AND LENGTH(T.END_MONTH)=6) OR T.END_MONTH IS NULL ) "
                    +" AND ADD_MONTHS(TO_DATE('"+endTime+"', 'YYYYMM'), -0) BETWEEN "
                    +" TO_DATE(T.START_MONTH, 'YYYYMM') AND "
                   +" NVL(TO_DATE(T.END_MONTH, 'YYYYMM'), TO_DATE('204912','YYYYMM')) ";

				}
				
			sqlContent+= " ORDER BY HE.EMPID";
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		this.excelUtilSer.exportExcel(request, response, modelMap, map,
				aliasNameList, aliasList);
	}

	/**
	 * 下载导入模板需要写的方法 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportInsuranceInputItemDataExcelIsNotNullModule")
	public void exportInsuranceInputItemDataExcelIsNotNullModule(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));//姓名
		//aliasNameList.add(TipMessage.getTipMessage("public.title.deptName", request));//部门
		//aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.postGrade", request));//职级
		//aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.status", request));//状态
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//备注

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "12000001");
		map.put("CELL1", "测试李");
		//map.put("CELL2", "风险管理部");
		//map.put("CELL3", "1");
		//map.put("CELL4", "在职");
		map.put("CELL2", "201202");
		map.put("CELL3", "201202");
		map.put("CELL4", "0.5");
		map.put("CELL5", "备注");
		list.add(map);

		LinkedHashMap map2 = new LinkedHashMap();
		map2.put("CELL0", "12000002");
		map2.put("CELL1", "测试王");
		//map2.put("CELL2", "风险管理部");
		//map2.put("CELL3", "2");
		//map2.put("CELL4", "在职");
		map2.put("CELL2", "201202");
		map2.put("CELL3", "201202");
		map2.put("CELL4", "1111");
		map2.put("CELL5", "备注");
		list.add(map2);

		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}

	/**
	 * 下载导入模板需要写的方法 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportInsuranceInputItemDataExcelIsNullModule")
	public void exportInsuranceInputItemDataExcelIsNullModule(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		String filed1Name = request.getParameter("FIELD1_NAME");
		String filed2Name = request.getParameter("FIELD2_NAME");
		if (filed1Name != null) {
			aliasNameList.add(filed1Name);
		}
		if (filed2Name != null) {
			aliasNameList.add(filed2Name);
		}
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//备注

		String filed1Value = request.getParameter("FIELD1");// LOCAL_NAME
		String sqlNotPersonContent = "select distinct S.content from PA_HR_V t,SY_GLOBAL_NAME S WHERE t."
				+ filed1Value
				+ " = S.NO(+) AND S.LANGUAGE(+)='"
				+ admin.getLanguage()
				+ "' AND T.CPNY_ID='"
				+ admin.getCpnyId()
				+ "' AND S.CONTENT IS NOT NULL";// and rownum<3";
		LinkedHashMap sqlmap = new LinkedHashMap();
		List contentList = null;
		if(filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("CPNY_ID")){
			String sqlPersonContent = "SELECT distinct SY.CONTENT  FROM HR_EMPLOYEE HE,HR_COMPANY HC,SY_GLOBAL_NAME SY "
										+" WHERE HE.CPNY_ID = HC.CPNY_ID AND HC.CPNY_NO = SY.NO(+) "
										+" AND SY.LANGUAGE(+) = '"+ admin.getLanguage()
										+"'AND HE.CPNY_ID = '"
										+  admin.getCpnyId()
										+ "'";
										//+"'AND ROWNUM < 3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}else if(filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("DEPTNO")){
			String sqlPersonContent = "SELECT distinct SY.CONTENT  FROM HR_EMPLOYEE HE,HR_DEPARTMENT_NAME SY "
										+" WHERE HE.DEPTNO = SY.DEPTNO(+) "
										+" AND SY.LANGUAGE(+) = '"+ admin.getLanguage()
										+"'AND HE.CPNY_ID = '"
										+  admin.getCpnyId()
										+ "'";
										//+"'AND ROWNUM < 3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}else if (filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && !filed1Value.equals("LOCAL_NAME")
				&& !filed1Value.equals("PERSON_ID")) {
			sqlmap.put("sqlContent", sqlNotPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		} else if (filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("LOCAL_NAME")) {
			String sqlPersonContent = "SELECT distinct LOCAL_NAME  CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId()+ "'";// + "' AND ROWNUM<3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		} else if (filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("PERSON_ID")) {
			String sqlPersonContent = "SELECT distinct EMPID  CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId()+ "'";// + "' AND ROWNUM<3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}
		String filed2Value = request.getParameter("FIELD2");// PERSON_ID
		String sqlNotPersonContent2 = "select distinct S.content from PA_HR_V t,SY_GLOBAL_NAME S WHERE t."
				+ filed2Value
				+ " = S.NO(+) AND S.LANGUAGE(+)='"
				+ admin.getLanguage()
				+ "' AND T.CPNY_ID='"
				+ admin.getCpnyId()
				+ "' " + " AND S.CONTENT IS NOT NULL";// and rownum<3";
		LinkedHashMap sqlmap2 = new LinkedHashMap();
		List contentList2 = null;
		if(filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && filed2Value.equals("DEPTNO")){
			String sqlPersonContent = "SELECT  distinct SY.CONTENT  FROM HR_EMPLOYEE HE,HR_DEPARTMENT_NAME SY "
										+" WHERE HE.DEPTNO = SY.DEPTNO(+) "
										+" AND SY.LANGUAGE(+) = '"+ admin.getLanguage()
										+"'AND HE.CPNY_ID = '"
										+  admin.getCpnyId();
										//+"'AND ROWNUM < 3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}else if (filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && !filed2Value.equals("LOCAL_NAME")
				&& !filed2Value.equals("PERSON_ID")) {
			sqlmap2.put("sqlContent", sqlNotPersonContent2);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap2);
		} else if (filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && filed2Value.equals("LOCAL_NAME")) {
			String sqlPersonContent = "SELECT LOCAL_NAME  CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId()+ "'";// + "' AND ROWNUM<3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap);
		} else if (filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && filed2Value.equals("PERSON_ID")) {
			String sqlPersonContent = "SELECT distinct EMPID CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId() + "'";//+ "' AND ROWNUM<3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}
		List list = new ArrayList();
		int maxSize=-1;
		if(contentList!=null&&contentList.size()>0){
			maxSize=contentList.size();
		}
		if(contentList2!=null&&contentList2.size()>contentList.size()){
			maxSize=contentList2.size();
		}
		
		for(int i=0;i<maxSize;i++){
			LinkedHashMap map = new LinkedHashMap();
			if (filed1Name != null) {
				String cellStr = "";
				if (contentList != null && contentList.size() > 0&& i<contentList.size()&& contentList.get(i) != null) {
					cellStr = ((Map) (contentList.get(i))).get("CONTENT") == null ? ""
							: ((Map) (contentList.get(i))).get("CONTENT")
									.toString();
				}
				map.put("CELL0", cellStr);
			}
			if (filed2Name != null) {
				String cellStr = "";
				if (contentList2 != null && contentList2.size() > 0&& i<contentList2.size()&& contentList2.get(i) != null) {
					cellStr = ((Map) (contentList2.get(i))).get("CONTENT") == null ? ""
							: ((Map) (contentList2.get(i))).get("CONTENT")
									.toString();
				}
				map.put("CELL1", cellStr);
			}
			if(i>=2){
				map.put("CELL2", "");
				map.put("CELL3", "");
				map.put("CELL4", "");
				map.put("CELL5", "");
			}else{
				map.put("CELL2", "201202");
				map.put("CELL3", "201202");
				map.put("CELL4", "0.5");
				map.put("CELL5", "备注");
			}
			list.add(map);
		}
		

		/*map.put("CELL2", "201202");
		map.put("CELL3", "201202");
		map.put("CELL4", "0.5");
		list.add(map);

		LinkedHashMap map2 = new LinkedHashMap();
		if (filed1Name != null) {
			String cellStr = "";
			if (contentList != null && contentList.size() > 1
					&& contentList.get(1) != null) {
				cellStr = ((Map) (contentList.get(1))).get("CONTENT") == null ? ""
						: ((Map) (contentList.get(1))).get("CONTENT")
								.toString();
			} else if (contentList != null && contentList.size() == 1) {
				cellStr = ((Map) (contentList.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList.get(0))).get("CONTENT")
								.toString();
			}
			map2.put("CELL0", cellStr);
		}
		if (filed2Name != null) {
			String cellStr = "";
			if (contentList2 != null && contentList2.size() > 1
					&& contentList2.get(1) != null) {
				cellStr = ((Map) (contentList2.get(1))).get("CONTENT") == null ? ""
						: ((Map) (contentList2.get(1))).get("CONTENT")
								.toString();
			} else if (contentList2 != null && contentList2.size() == 1) {
				cellStr = ((Map) (contentList2.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList2.get(0))).get("CONTENT")
								.toString();
			}
			map2.put("CELL1", cellStr);
		}
		map2.put("CELL2", "201202");
		map2.put("CELL3", "201202");
		map2.put("CELL4", "0.75");
		list.add(map2);*/
		
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);

	}
	
	/**
	 * 下载导入模板需要写的方法 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportBonusInputItemDataExcelIsNotNullModule")
	public void exportBonusInputItemDataExcelIsNotNullModule(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));//姓名
		/*aliasNameList.add(TipMessage.getTipMessage("public.title.deptName", request));//部门
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.postGrade", request));//职级
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.status", request));//状态
*/		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//备注
		

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "12000001");
		map.put("CELL1", "测试李");
	/*	map.put("CELL2", "风险管理部");
		map.put("CELL3", "1");
		map.put("CELL4", "在职");*/
		map.put("CELL2", "201202");
		map.put("CELL3", "201202");
		map.put("CELL4", "0.5");
		map.put("CELL5", "备注");
		list.add(map);

		LinkedHashMap map2 = new LinkedHashMap();
		map2.put("CELL0", "12000002");
		map2.put("CELL1", "测试王");
		/*map2.put("CELL2", "风险管理部");
		map2.put("CELL3", "2");
		map2.put("CELL4", "在职");*/
		map2.put("CELL2", "201202");
		map2.put("CELL3", "201202");
		map2.put("CELL4", "0.5");
		map2.put("CELL5", "备注");
		list.add(map2);

		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}
	
	/**
	 * 下载导入模板需要写的方法 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportBonusInputItemDataExcelIsNullModule")
	public void exportBonusInputItemDataExcelIsNullModule(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		String filed1Name = request.getParameter("FIELD1_NAME");
		String filed2Name = request.getParameter("FIELD2_NAME");
		if (filed1Name != null) {
			aliasNameList.add(filed1Name);
		}
		if (filed2Name != null) {
			aliasNameList.add(filed2Name);
		}
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值

		String filed1Value = request.getParameter("FIELD1");// LOCAL_NAME
		String sqlNotPersonContent = "select S.content from PA_HR_V t,SY_GLOBAL_NAME S WHERE t."
				+ filed1Value
				+ " = S.NO(+) AND S.LANGUAGE(+)='"
				+ admin.getLanguage()
				+ "' AND T.CPNY_ID='"
				+ admin.getCpnyId()
				+ "' AND S.CONTENT IS NOT NULL and rownum<3";
		LinkedHashMap sqlmap = new LinkedHashMap();
		List contentList = null;
		if(filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("CPNY_ID")){
			String sqlPersonContent = "SELECT SY.CONTENT  FROM HR_EMPLOYEE HE,HR_COMPANY HC,SY_GLOBAL_NAME SY "
										+" WHERE HE.CPNY_ID = HC.CPNY_ID AND HC.CPNY_NO = SY.NO(+) "
										+" AND SY.LANGUAGE(+) = '"+ admin.getLanguage()
										+"'AND HE.CPNY_ID = '"
										+  admin.getCpnyId()
										+"'AND ROWNUM < 3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}else if(filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("DEPTNO")){
			String sqlPersonContent = "SELECT SY.CONTENT  FROM HR_EMPLOYEE HE,HR_DEPARTMENT_NAME SY "
										+" WHERE HE.DEPTNO = SY.DEPTNO(+) "
										+" AND SY.LANGUAGE(+) = '"+ admin.getLanguage()
										+"'AND HE.CPNY_ID = '"
										+  admin.getCpnyId()
										+"'AND ROWNUM < 3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}else if (filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && !filed1Value.equals("LOCAL_NAME")
				&& !filed1Value.equals("PERSON_ID")) {
			sqlmap.put("sqlContent", sqlNotPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		} else if (filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("LOCAL_NAME")) {
			String sqlPersonContent = "SELECT LOCAL_NAME  CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId() + "' AND ROWNUM<3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		} else if (filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("PERSON_ID")) {
			String sqlPersonContent = "SELECT EMPID  CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId() + "' AND ROWNUM<3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}
		String filed2Value = request.getParameter("FIELD2");// PERSON_ID
		String sqlNotPersonContent2 = "select S.content from PA_HR_V t,SY_GLOBAL_NAME S WHERE t."
				+ filed2Value
				+ " = S.NO(+) AND S.LANGUAGE(+)='"
				+ admin.getLanguage()
				+ "' AND T.CPNY_ID='"
				+ admin.getCpnyId()
				+ "' " + " AND S.CONTENT IS NOT NULL and rownum<3";
		LinkedHashMap sqlmap2 = new LinkedHashMap();
		List contentList2 = null;
		if(filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && filed2Value.equals("DEPTNO")){
			String sqlPersonContent2 = "SELECT SY.CONTENT  FROM HR_EMPLOYEE HE,HR_DEPARTMENT_NAME SY "
										+" WHERE HE.DEPTNO = SY.DEPTNO(+) "
										+" AND SY.LANGUAGE(+) = '"+ admin.getLanguage()
										+"'AND HE.CPNY_ID = '"
										+  admin.getCpnyId()
										+"'AND ROWNUM < 3";
			sqlmap2.put("sqlContent", sqlPersonContent2);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap2);
		}else if (filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && !filed2Value.equals("LOCAL_NAME")
				&& !filed2Value.equals("PERSON_ID")) {
			sqlmap2.put("sqlContent", sqlNotPersonContent2);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap2);
		} else if (filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && filed2Value.equals("LOCAL_NAME")) {
			String sqlPersonContent2 = "SELECT LOCAL_NAME  CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId() + "' AND ROWNUM<3";
			sqlmap2.put("sqlContent", sqlPersonContent2);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap2);
		} else if (filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && filed2Value.equals("PERSON_ID")) {
			String sqlPersonContent2 = "SELECT EMPID CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId() + "' AND ROWNUM<3";
			sqlmap2.put("sqlContent", sqlPersonContent2);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap2);
		}
		List list = new ArrayList();

		LinkedHashMap map = new LinkedHashMap();
		if (filed1Name != null) {
			String cellStr = "";
			if (contentList != null && contentList.size() > 0
					&& contentList.get(0) != null) {
				cellStr = ((Map) (contentList.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList.get(0))).get("CONTENT")
								.toString();
			}
			map.put("CELL0", cellStr);
		}
		if (filed2Name != null) {
			String cellStr = "";
			if (contentList2 != null && contentList2.size() > 0
					&& contentList2.get(0) != null) {
				cellStr = ((Map) (contentList2.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList2.get(0))).get("CONTENT")
								.toString();
			}
			map.put("CELL1", cellStr);
		}

		map.put("CELL2", "201202");
		map.put("CELL3", "201202");
		map.put("CELL4", "0.5");
		list.add(map);

		LinkedHashMap map2 = new LinkedHashMap();
		if (filed1Name != null) {
			String cellStr = "";
			if (contentList != null && contentList.size() > 1
					&& contentList.get(1) != null) {
				cellStr = ((Map) (contentList.get(1))).get("CONTENT") == null ? ""
						: ((Map) (contentList.get(1))).get("CONTENT")
								.toString();
			} else if (contentList != null && contentList.size() == 1) {
				cellStr = ((Map) (contentList.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList.get(0))).get("CONTENT")
								.toString();
			}
			map2.put("CELL0", cellStr);
		}
		if (filed2Name != null) {
			String cellStr = "";
			if (contentList2 != null && contentList2.size() > 1
					&& contentList2.get(1) != null) {
				cellStr = ((Map) (contentList2.get(1))).get("CONTENT") == null ? ""
						: ((Map) (contentList2.get(1))).get("CONTENT")
								.toString();
			} else if (contentList2 != null && contentList2.size() == 1) {
				cellStr = ((Map) (contentList2.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList2.get(0))).get("CONTENT")
								.toString();
			}
			map2.put("CELL1", cellStr);
		}
		map2.put("CELL2", "201202");
		map2.put("CELL3", "201202");
		map2.put("CELL4", "0.75");
		list.add(map2);

		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}

	/**
	 * 下载导入模板需要写的方法（工资维护--输入项目数据） Description:download the module for upload
	 * data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaInputItemDataExcelIsNotNullModule")
	public void exportPaInputItemDataExcelIsNotNullModule(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) throws Exception {
		String DISTINCT_FIELD = request.getParameter("DISTINCT_FIELD");
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		if ("PERSON_ID".equals(DISTINCT_FIELD)) {
			aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
			aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));//姓名
		}
		if ("POST_GRADE_NO".equals(DISTINCT_FIELD)) {
			if ("TSTO".equals(admin.getCpnyId())) {
				aliasNameList.add(TipMessage.getTipMessage("职级代码", request));//职级
			}else {
				aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.postGrade", request));//职级
				aliasNameList.add(TipMessage.getTipMessage("hrm.recruitManage.Wage_type", request));//工资类型
			}
		}
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//备注
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		if ("PERSON_ID".equals(DISTINCT_FIELD)) {
			map.put("CELL0", "12000001");
			map.put("CELL1", "Test1");
			map.put("CELL2", "022012");
			map.put("CELL3", "022012");
			map.put("CELL4", "0.5");
			map.put("CELL5", "remark");
		}
		if ("POST_GRADE_NO".equals(DISTINCT_FIELD)) {
			if ("TSTO".equals(admin.getCpnyId())) {
				map.put("CELL0", "14015930");
				map.put("CELL1", "14016036");
			}else {
				map.put("CELL0", "14015948");
				map.put("CELL1", "14016036");
			}
			map.put("CELL2", "022012");
			map.put("CELL3", "022012");
			map.put("CELL4", "0.5");
			map.put("CELL5", "remark");
		}
		
		list.add(map);
		LinkedHashMap map2 = new LinkedHashMap();
		if ("PERSON_ID".equals(DISTINCT_FIELD)) {
			map2.put("CELL0", "12000002");
			map2.put("CELL1", "Test2");
			map2.put("CELL2", "022012");
			map2.put("CELL3", "022012");
			map2.put("CELL4", "1111");
			map2.put("CELL5", "remark");
		}
		if ("POST_GRADE_NO".equals(DISTINCT_FIELD)) {
			if ("TSTO".equals(admin.getCpnyId())) {
				map2.put("CELL0", "14015930");
				map2.put("CELL1", "14016035");
			}else {
				map2.put("CELL0", "14015930");
				map2.put("CELL1", "14016035");
			}
			map2.put("CELL2", "022012");
			map2.put("CELL3", "022012");
			map2.put("CELL4", "1111");
			map2.put("CELL5", "remark");
		}
		
		list.add(map2);

		/*LinkedHashMap map = new LinkedHashMap();
		List personList = empInfoSer.getEmpListToModelExcel(request);*/
		
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		if ("PERSON_ID".equals(DISTINCT_FIELD)) {
			this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
		}
		if ("POST_GRADE_NO".equals(DISTINCT_FIELD)) {
			List mapNameList = new ArrayList();
			List mapList = new ArrayList();
			mapNameList.add("Rank CODE Name");
			mapNameList.add("Rank CODE");
			mapNameList.add("Rank CODE value");
			mapNameList.add("Wage types CODE");
			String sqlBasicItem="";
			if ("TSTO".equals(admin.getCpnyId())) {
				sqlBasicItem="SELECT SN.CONTENT ,S.CODE_NO ,SC.DESCRIPTION CODE_MA FROM SY_CODE S, SY_CODE_PARAM SP,SY_GLOBAL_NAME SN,SY_CODE SC  WHERE S.CODE_NO = SP.CODE_NO(+)  AND S.CODE_NO=SC.CODE_NO(+) AND S.CODE_NO=SN.NO AND  SN.LANGUAGE='zh' AND S.PARENT_CODE_NO IN ( '14014289','14014290','14014291','14014292','14014293','14015578')  AND SP.CPNY_ID='"+admin.getCpnyId()+"'";
			}else {
				sqlBasicItem="SELECT SN.CONTENT ,S.CODE_NO ,SC.DESCRIPTION CODE_MA FROM SY_CODE S, SY_CODE_PARAM SP,SY_GLOBAL_NAME SN,SY_CODE SC  WHERE S.CODE_NO = SP.CODE_NO(+)  AND S.CODE_NO=SC.CODE_NO(+) AND S.CODE_NO=SN.NO AND  SN.LANGUAGE='zh' AND S.PARENT_CODE_NO IN ( '14015088','14015089','14015090','14015091','14015578','14643')  AND SP.CPNY_ID='"+admin.getCpnyId()+"'";	
			}
			mapList.add(sqlBasicItem);
			String name ="pa_inputitem_param";
			this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList,name);
		}
	}
	
	/**
	 * 下载导入模板需要写的方法（工资维护--输入项目数据） Description:download the module for upload
	 * data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaMonthInputItemDataExcelIsNotNullModule")
	public void exportPaMonthInputItemDataExcelIsNotNullModule(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) throws Exception {
		String DISTINCT_FIELD = request.getParameter("DISTINCT_FIELD");
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		if ("PERSON_ID".equals(DISTINCT_FIELD)) {
			aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
			aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));//姓名
		}
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//结束月
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		if ("PERSON_ID".equals(DISTINCT_FIELD)) {
			map.put("CELL0", "12000001");
			map.put("CELL1", "Test1");
			map.put("CELL2", "022012");
			map.put("CELL3", "022012");
		}
		if ("POST_GRADE_NO".equals(DISTINCT_FIELD)) {
			if ("TSTO".equals(admin.getCpnyId())) {
				map.put("CELL0", "14015930");
				map.put("CELL1", "14016036");
			}else {
				map.put("CELL0", "14015948");
				map.put("CELL1", "14016036");
			}
			map.put("CELL2", "022012");
			map.put("CELL3", "022012");
		}
		
		list.add(map);
		LinkedHashMap map2 = new LinkedHashMap();
		if ("PERSON_ID".equals(DISTINCT_FIELD)) {
			map2.put("CELL0", "12000002");
			map2.put("CELL1", "Test2");
			map2.put("CELL2", "022012");
			map2.put("CELL3", "022012");
		}
		if ("POST_GRADE_NO".equals(DISTINCT_FIELD)) {
			if ("TSTO".equals(admin.getCpnyId())) {
				map2.put("CELL0", "14015930");
				map2.put("CELL1", "14016035");
			}else {
				map2.put("CELL0", "14015930");
				map2.put("CELL1", "14016035");
			}
			map2.put("CELL2", "022012");
			map2.put("CELL3", "022012");
		}
		
		list.add(map2);

		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		if ("PERSON_ID".equals(DISTINCT_FIELD)) {
			this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
		}
	}

	/**
	 * 下载导入模板需要写的方法（工资维护--输入项目数据） Description:download the module for upload
	 * data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaBasicItemDataExcelIsNotNullModule")
	public void exportPaBasicItemDataExcelIsNotNullModule(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));//姓名
//		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName", request));//部门
//		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.postGrade", request));//职级
//		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.status", request));//状态
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
	//	aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//备注
		
		List list = new ArrayList();

		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "12000001");
		map.put("CELL1", "测试李");
//		map.put("CELL2", "风险管理部");
//		map.put("CELL3", "1");
//		map.put("CELL4", "在职");
		map.put("CELL2", "2010/09/01");
//		map.put("CELL3", "2010/09/01");
		map.put("CELL3", "0.5");
		map.put("CELL4", "备注");

		list.add(map);

		LinkedHashMap map2 = new LinkedHashMap();
		map2.put("CELL0", "12000002");
		map2.put("CELL1", "测试王");
//		map2.put("CELL2", "风险管理部");
//		map2.put("CELL3", "2");
//		map2.put("CELL4", "在职");
		map2.put("CELL2", "2010/09/01");
//		map2.put("CELL3", "2010/09/01");
		map2.put("CELL3", "1111");
		map2.put("CELL4", "备注");
		list.add(map2);

		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}

	/**
	 * 下载导入模板需要写的方法（工资维护--输入项目数据） Description:download the module for upload
	 * data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportPaInputItemDataExcelIsNullModule")
	public void exportPaInputItemDataExcelIsNullModule(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		
		this.exportInsuranceInputItemDataExcelIsNullModule(request, response,
				modelMap);
	}
	/**
	 * 下载导入模板需要写的方法（调令处理-调令操作） Description:download the module for upload
	 * data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportDiaoLingInputItemDataExcelIsNullModuleType")
	public void exportDiaoLingInputItemDataExcelIsNullModuleType(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		
		this.exportInsuranceInputItemDataExcelIsNullModule1(request, response,
				modelMap);
	}
	/**
	 * 下载导入模板需要写的方法 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportInsuranceInputItemDataExcelIsNullModule1")
	public void exportInsuranceInputItemDataExcelIsNullModule1(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		
		/////////////////////////////////////////////////////////////
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//		List aliasNameList = new ArrayList();
//
//		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",request));// 工号
//		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));// 姓名
//		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",request));// 部门
//		aliasNameList.add(TipMessage.getTipMessage("ar.viewarcardrecord.title.shijian", request));// 时间
//		aliasNameList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing", request));// 类型(请填写sheet2中的数据)
//		aliasNameList.add(TipMessage.getTipMessage("ar.viewarcardrecord.title.beizhu", request));// 备注
//
//		List list = new ArrayList();
//
//		String sqlDetailInfo = "SELECT E.EMPID,E.LOCAL_NAME, HD.CONTENT DEPTNAME "
//				+ " FROM HR_EMPLOYEE E, HR_DEPARTMENT_NAME HD "
//				+ " WHERE E.DEPTNO = HD.DEPTNO(+) "
//				+ " AND HD.LANGUAGE(+) = '"
//				+ admin.getLanguage()
//				+ "' "
//				+ " AND E.STATUS_CODE <> 1375   "
//				+ " AND EXISTS(                            "
//				+ "            SELECT *                          "
//				+ "            FROM AR_SUPERVISOR_INFO                  "
//				+ "           WHERE AR_SUPERVISOR_INFO.DEPTNO = E.DEPTNO    "
//				+ "             AND AR_SUPERVISOR_INFO.PERSON_ID = '"
//				+ admin.getPersonId()
//				+ "' "
//				+ "          ) 				"
//				+ " ORDER BY HD.DEPTNO,EMPID ";
//		LinkedHashMap sqlDetailmap = new LinkedHashMap();
//		sqlDetailmap.put("sqlContent", sqlDetailInfo);
//		List itemDetailList = this.excelUtilSer
//				.getContentNoByFiled(sqlDetailmap);
//
//		for (int i = 0; i < itemDetailList.size(); i++) {
//			LinkedHashMap map = new LinkedHashMap();
//			Map empInfo = (Map) itemDetailList.get(i);
//
//			if (empInfo != null) {
//				map.put("CELL0", empInfo.get("EMPID") == null ? "" : empInfo
//						.get("EMPID").toString());
//				map.put("CELL1", empInfo.get("LOCAL_NAME") == null ? ""
//						: empInfo.get("LOCAL_NAME").toString());
//				map.put("CELL2", empInfo.get("DEPTNAME") == null ? "" : empInfo
//						.get("DEPTNAME").toString());
//			} else {
//				map.put("CELL0", "");
//				map.put("CELL1", "");
//				map.put("CELL2", "");
//			}
//
//			if (i == 0) {
//				map.put("CELL3", "2012-01-01 09:00");
//				map.put("CELL4", "IN");
//			}
//
//			if (i == 1) {
//				map.put("CELL3", "2012-01-01 18:00");
//				map.put("CELL4", "OUT");
//			}
//
//			list.add(map);
//		}
//
//		// 开始设定sheet的列名以及数据
//		String sqlItemName = "SELECT 'IN' CONTENT FROM DUAL "
//				+ " UNION SELECT 'OUT' CONTENT FROM DUAL ";
//		LinkedHashMap sqlmap = new LinkedHashMap();
//		sqlmap.put("sqlContent", sqlItemName);
//		List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
//		itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",
//				request));// 类型(请填写sheet1中的数据)
//
//		String name = "viewArCardRecord_module1";
//		
//		LinkedHashMap sqlContentmap = this.excelUtilSer
//				.putIntoSqlContentMap(list);
//		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,
//				sqlContentmap, aliasNameList, null, itemList, name);
		
		
		//////////////////////////////////////////////////////////////
		
		
		
		
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		String code=request.getParameter("code");
		LinkedHashMap paramMap1 = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		paramMap1.put("specialParam", admin.getSpecialParam());
		paramMap1.put("userNo", admin.getUserNo());
		//paramMap1.put("DEPTNO", request.getParameter("dep"));
		paramMap1.put("ADMIN_ID", admin.getAdminID());
		paramMap1.put("CODE",  code);
		paramMap1.put("CPNYID",admin.getCpnyId());
		paramMap1.put("CPNY_ID",admin.getCpnyId());
		//paramMap.put("LOCAL LIKE",request.getParameter("namePeople"));
		paramMap1.put("LAN", admin.getLanguage());
		paramMap1.put("interLanguage", admin.getLanguage());
		paramMap1.put("ADMINID", admin.getAdminID());
		paramMap1.put("userNo", admin.getUserNo());
		paramMap1.put("deptNo", admin.getDeptNo());
		
		paramMap1.put("TRANS_CONFIG_FLAG", "2");
		paramMap1.put("NOT_DISTINCT", "'TRANS_ORDER_TYPE','EMPID','LOCAL_NAME'");
		List listTitle=this.transferOrderDao.getTranferOrderTitile(paramMap1);
		
		String sql= ",";
		
		for(int j=0;j<listTitle.size();j++){
			Map map=(Map)listTitle.get(j);
			sql+=map.get("DISTINCT_FIELD")+",";
			aliasNameList.add(j, map.get("CONTENT"));
		}

		if(sql.indexOf(",LOCAL_NAME,")<0){
			sql = ",LOCAL_NAME" + sql;
			aliasNameList.add(0, "姓名");
		}
		if(sql.indexOf(",EMPID,")<0){
			sql = ",EMPID" + sql;
			aliasNameList.add(0, "工号");
		}
		if(sql.indexOf(",TRANS_ORDER_TYPE,")<0){
			sql = ",TRANS_ORDER_TYPE" + sql;
			aliasNameList.add(0, "调令类型");
		}
		
		
		List transTypeList = new ArrayList();
		List deptList = new ArrayList();
		List gradeLevelList = new ArrayList();
		List dutyList = new ArrayList();
		List postGradeList = new ArrayList();
		List postList = new ArrayList();
		List positionList = new ArrayList();
		List workAreaList = new ArrayList();
		List partDeptList = new ArrayList();
		List partPostGradeList = new ArrayList();
		List partPostList = new ArrayList();
		List partDutyList = new ArrayList();
		List partPositionList = new ArrayList();
		List hrDiffList = new ArrayList();
		List positionPauseTypeList = new ArrayList();
		List resignTypeList = new ArrayList();
		List resignReasonList = new ArrayList();
		String transDate,transEndDate,empid,localName,paCalDate,transReason,remark,transOrderNo,positiveDates,tempCpny,tempDate;
		int maxSize = 1;
		
//		调令类型	TRANS_ORDER_TYPE
		if(sql.indexOf(",TRANS_ORDER_TYPE,")>=0){
			paramMap1.put("PARENT_CODE_NO", "123313");
			transTypeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap1) ;
			if(transTypeList != null && transTypeList.size() > maxSize){
				maxSize = transTypeList.size();
			}
		}	
		
//		工号	EMPID
		if(sql.indexOf(",EMPID,")>=0){
			empid = "12000001";
		}
//		姓名	LOCAL_NAME
		if(sql.indexOf(",LOCAL_NAME,")>=0){
			localName = "姓名이름" ;
		}
//		调令日期	TRANS_ORDER_DATE
		if(sql.indexOf(",TRANS_ORDER_DATE,")>=0){
			transDate = "2013-09-01";
		}
//		调令结束日	TRANS_ORDER_ENDDATE
		if(sql.indexOf(",TRANS_ORDER_ENDDATE,")>=0){
			transEndDate = "2013-09-01";
		}	
//		部门	DEPTNO
		if(sql.indexOf(",DEPTNO,")>=0){
			deptList  = hrmDao.getDeptTree("hrm.getDeptTreeForHr", paramMap1);
			if(deptList != null && deptList.size() > maxSize){
				maxSize = deptList.size();
			}
		}
//		职等	GRADE_LEVEL
		if(sql.indexOf(",GRADE_LEVEL,")>=0){
			paramMap1.put("PARENT_CODE_NO", "3683");
			gradeLevelList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap1) ;
			if(gradeLevelList != null && gradeLevelList.size() > maxSize){
				maxSize = gradeLevelList.size();
			}
		}
//		职责	DUTY_NO
		if(sql.indexOf(",DUTY_NO,")>=0){
			dutyList = transferOrderDao.getDutyList(paramMap1);
			if(dutyList != null && dutyList.size() > maxSize){
				maxSize = dutyList.size();
			}
		}
//		职级	POST_GRADE_NO
		if(sql.indexOf(",POST_GRADE_NO,")>=0){
			postGradeList = transferOrderDao.getPostGradeList(paramMap1);
			if(postGradeList != null && postGradeList.size() > maxSize){
				maxSize = postGradeList.size();
			}
		}
//		职级名称	POST_NO
		if(sql.indexOf(",POST_NO,")>=0){
			postList = transferOrderDao.getPostList(paramMap1);
			if(postList != null && postList.size() > maxSize){
				maxSize = postList.size();
			}
		}
//		职位	POSITION_NO
		if(sql.indexOf(",POSITION_NO,")>=0){
			positionList = transferOrderDao.getPositionList(paramMap1);
			if(positionList != null && positionList.size() > maxSize){
				maxSize = positionList.size();
			}
		}
//		工作地	WORK_AREA_NAME
		if(sql.indexOf(",WORK_AREA_NAME,")>=0){
			workAreaList = transferOrderDao.getWorkAreaList(paramMap1);
			if(workAreaList != null && workAreaList.size() > maxSize){
				maxSize = workAreaList.size();
			}
		}
//		兼职部门	PARTTIME_DEPT
		if(sql.indexOf(",PARTTIME_DEPT,")>=0){
			partDeptList = hrmDao.getDeptTree("hrm.getDeptTreeForHr", paramMap1);
			if(partDeptList != null && partDeptList.size() > maxSize){
				maxSize = partDeptList.size();
			}
		}
//		兼职职级	PARTTIME_POST_GRADE_NO
		if(sql.indexOf(",PARTTIME_POST_GRADE_NO,")>=0){
			partPostGradeList = transferOrderDao.getPostGradeList(paramMap1);
			if(partPostGradeList != null && partPostGradeList.size() > maxSize){
				maxSize = partPostGradeList.size();
			}
		}
//		兼职职级名称	PARTTIME_POST_NO
		if(sql.indexOf(",PARTTIME_POST_NO,")>=0){
			partPostList = transferOrderDao.getPostList(paramMap1);
			if(partPostList != null && partPostList.size() > maxSize){
				maxSize = partPostList.size();
			}
		}
//		兼职职责	PARTTIME_DUTY
		if(sql.indexOf(",PARTTIME_DUTY,")>=0){
			partDutyList = transferOrderDao.getDutyList(paramMap1);
			if(partDutyList != null && partDutyList.size() > maxSize){
				maxSize = partDutyList.size();
			}
		}
//		兼职职位	PARTTIME_POSITION_NO
		if(sql.indexOf(",PARTTIME_POSITION_NO,")>=0){
			partPositionList = transferOrderDao.getPositionList(paramMap1);
			if(partPositionList != null && partPositionList.size() > maxSize){
				maxSize = partPositionList.size();
			}
		}
//		代职部门	REPLACE_DEPT
		if(sql.indexOf(",REPLACE_DEPT,")>=0){
			partDeptList = hrmDao.getDeptTree("hrm.getDeptTreeForHr", paramMap1);
			if(partDeptList != null && partDeptList.size() > maxSize){
				maxSize = partDeptList.size();
			}
		}
//		代职职级	REPLACE_POST_GRADE_NO
		if(sql.indexOf(",REPLACE_POST_GRADE_NO,")>=0){
			partPostGradeList = transferOrderDao.getPostGradeList(paramMap1);
			if(partPostGradeList != null && partPostGradeList.size() > maxSize){
				maxSize = partPostGradeList.size();
			}
		}
//		代职职级名称	REPLACE_POST_NO
		if(sql.indexOf(",REPLACE_POST_NO,")>=0){
			partPostList = transferOrderDao.getPostList(paramMap1);
			if(partPostList != null && partPostList.size() > maxSize){
				maxSize = partPostList.size();
			}
		}
//		代职职责	REPLACE_DUTY
		if(sql.indexOf(",REPLACE_DUTY,")>=0){
			partDutyList = transferOrderDao.getDutyList(paramMap1);
			if(partDutyList != null && partDutyList.size() > maxSize){
				maxSize = partDutyList.size();
			}
		}
//		代职职位	REPLACE_POSITION_NO
		if(sql.indexOf(",REPLACE_POSITION_NO,")>=0){
			partPositionList = transferOrderDao.getPositionList(paramMap1);
			if(partPositionList != null && partPositionList.size() > maxSize){
				maxSize = partPositionList.size();
			}
		}
//		详细人力区分	DETAIL_HR_DIFF
		if(sql.indexOf(",DETAIL_HR_DIFF,")>=0){
			paramMap1.put("PARENT_CODE_NO", "1368");
			hrDiffList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap1) ;
			if(hrDiffList != null && hrDiffList.size() > maxSize){
				maxSize = hrDiffList.size();
			}
		}
//		工资结算日	SAL_CALCULATE_DATE
		if(sql.indexOf(",SAL_CALCULATE_DATE,")>=0){
			paCalDate = "2013-09-01";
		}
//		调令事由	TRANSFER_ORDER_REASON
		if(sql.indexOf(",TRANSFER_ORDER_REASON,")>=0){
			transReason = "调令事由" ;
		}
//		转正日期POSITIVE_DATES
		if(sql.indexOf(",POSITIVE_DATES,")>=0){
			positiveDates="2013-01-01";
		}
//		休职类型POSITION_PAUSE_TYPE
		if(sql.indexOf(",POSITION_PAUSE_TYPE,")>=0){
			paramMap1.put("PARENT_CODE_NO", "13965");
			positionPauseTypeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap1) ;
			if(positionPauseTypeList != null && positionPauseTypeList.size() > maxSize){
				maxSize = positionPauseTypeList.size();
			}
		}
//		离职类型RESIGN_TYPE
		if(sql.indexOf(",RESIGN_TYPE,")>=0){
			paramMap1.put("PARENT_CODE_NO", "643");
			resignTypeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap1) ;
			if(resignTypeList != null && resignTypeList.size() > maxSize){
				maxSize = resignTypeList.size();
			}
		}
//		离职原因RESIGN_REASON
		if(sql.indexOf(",RESIGN_REASON,")>=0){
			paramMap1.put("PARENT_CODE_NO", "4265");
			resignReasonList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap1) ;
			if(resignReasonList != null && resignReasonList.size() > maxSize){
				maxSize = resignReasonList.size();
			}
		}
//		借调公司TEMP_CPNY
		if(sql.indexOf(",TEMP_CPNY,")>=0){
			tempCpny = "借调公司";
		}
//		调令编号	TRANSFER_ORDER_NO
		if(sql.indexOf(",TRANSFER_ORDER_NO,")>=0){
			transOrderNo = "调令编号" ;
		}
//		借调期间TEMP_DATE
		if(sql.indexOf(",TEMP_DATE,")>=0){
			tempDate = "借调期间";
		}
//		备注	REMARK
		if(sql.indexOf(",REMARK,")>=0){
			remark  = "备注" ;
		}
			
		List itemDetailList = new ArrayList();
		for(int i=0 ; i<maxSize ; i++){
			LinkedHashMap map = new LinkedHashMap();
			int j = 0;
//			调令类型	TRANS_ORDER_TYPE
			if(sql.indexOf(",TRANS_ORDER_TYPE,")>=0){
				if(i==0){
					for(int z=0 ;z<maxSize;z++){
						Map empInfo = (Map) transTypeList.get(z);
						if(empInfo.get("CODE_NO").toString().equals(code)){
							map.put("CELL0", empInfo.get("CODE_NAME"));
							break;
							}
					}
				}else{
					map.put("CELL"+j, "");
				}
				j++;
				/*if(i < transTypeList.size()){
					Map empInfo = (Map) transTypeList.get(i);
					if(empInfo.get("CODE_NO").toString().equals(code)){
					map.put("CELL0", empInfo.get("CODE_NAME"));
					}else{
						map.put("CELL"+j+1, "");
					}
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}*/
			}
//			工号	EMPID
			if(sql.indexOf(",EMPID,")>=0){
				if(i==0){
					empid = "12000001";
					map.put("CELL"+j, empid);
				}else{
					map.put("CELL"+j, "");
				}
				j++;
			}
//			姓名	LOCAL_NAME
			if(sql.indexOf(",LOCAL_NAME,")>=0){
				if(i==0){
					localName = "姓名이름" ;
					map.put("CELL"+j, localName);
				}else{
					map.put("CELL"+j, "");
				}
				j++;
			}
//			调令日期	TRANS_ORDER_DATE
			if(sql.indexOf(",TRANS_ORDER_DATE,")>=0){
				if(i==0){
					transDate = "2013-09-01";
					map.put("CELL"+j, transDate);
				}else{
					map.put("CELL"+j, "");
				}
				j++;
			}
//			调令结束日	TRANS_ORDER_ENDDATE
			if(sql.indexOf(",TRANS_ORDER_ENDDATE,")>=0){
				if(i==0){
					transEndDate = "2013-09-01";
					map.put("CELL"+j, transEndDate);
				}else{
					map.put("CELL"+j, "");
				}
				j++;
			}		
			
//			部门	DEPTNO
			if(sql.indexOf(",DEPTNO,")>=0){
				if(i < deptList.size()){
					Map empInfo = (Map) deptList.get(i);
					map.put("CELL"+j, empInfo.get("DEPTNAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			职等	GRADE_LEVEL
			if(sql.indexOf(",GRADE_LEVEL,")>=0){
				if(i < gradeLevelList.size()){
					Map empInfo = (Map) gradeLevelList.get(i);
					map.put("CELL"+j, empInfo.get("CODE_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			职责	DUTY_NO
			if(sql.indexOf(",DUTY_NO,")>=0){
				if(i < dutyList.size()){
					Map empInfo = (Map) dutyList.get(i);
					map.put("CELL"+j, empInfo.get("DUTY_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			职级	POST_GRADE_NO
			if(sql.indexOf(",POST_GRADE_NO,")>=0){
				if(i < postGradeList.size()){
					Map empInfo = (Map) postGradeList.get(i);
					map.put("CELL"+j, empInfo.get("POST_GRADE_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			职级名称	POST_NO
			if(sql.indexOf(",POST_NO,")>=0){
				if(i < postList.size()){
					Map empInfo = (Map) postList.get(i);
					map.put("CELL"+j, empInfo.get("POST_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			职位	POSITION_NO
			if(sql.indexOf(",POSITION_NO,")>=0){
				if(i < positionList.size()){
					Map empInfo = (Map) positionList.get(i);
					map.put("CELL"+j, empInfo.get("POSITION_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			工作地	WORK_AREA_NAME
			if(sql.indexOf(",WORK_AREA_NAME,")>=0){
				if(i < workAreaList.size()){
					Map empInfo = (Map) workAreaList.get(i);
					map.put("CELL"+j, empInfo.get("WORKAREA_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			兼职部门	PARTTIME_DEPT
			if(sql.indexOf(",PARTTIME_DEPT,")>=0){
				if(i < partDeptList.size()){
					Map empInfo = (Map) partDeptList.get(i);
					map.put("CELL"+j, empInfo.get("DEPTNAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			兼职职级	PARTTIME_POST_GRADE_NO
			if(sql.indexOf(",PARTTIME_POST_GRADE_NO,")>=0){
				if(i < partPostGradeList.size()){
					Map empInfo = (Map) partPostGradeList.get(i);
					map.put("CELL"+j, empInfo.get("POST_GRADE_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			兼职职级名称	PARTTIME_POST_NO
			if(sql.indexOf(",PARTTIME_POST_NO,")>=0){
				if(i < partPostList.size()){
					Map empInfo = (Map) partPostList.get(i);
					map.put("CELL"+j, empInfo.get("POST_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			兼职职责	PARTTIME_DUTY
			if(sql.indexOf(",PARTTIME_DUTY,")>=0){
				if(i < partDutyList.size()){
					Map empInfo = (Map) partDutyList.get(i);
					map.put("CELL"+j, empInfo.get("DUTY_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			兼职职位	PARTTIME_POSITION_NO
			if(sql.indexOf(",PARTTIME_POSITION_NO,")>=0){
				if(i < partPositionList.size()){
					Map empInfo = (Map) partPositionList.get(i);
					map.put("CELL"+j, empInfo.get("POSITION_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			代职部门	REPLACE_DEPT
			if(sql.indexOf(",REPLACE_DEPT,")>=0){
				if(i < partDeptList.size()){
					Map empInfo = (Map) partDeptList.get(i);
					map.put("CELL"+j, empInfo.get("DEPTNAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			代职职级	REPLACE_POST_GRADE_NO
			if(sql.indexOf(",REPLACE_POST_GRADE_NO,")>=0){
				if(i < partPostGradeList.size()){
					Map empInfo = (Map) partPostGradeList.get(i);
					map.put("CELL"+j, empInfo.get("POST_GRADE_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			代职职级名称	REPLACE_POST_NO
			if(sql.indexOf(",REPLACE_POST_NO,")>=0){
				if(i < partPostList.size()){
					Map empInfo = (Map) partPostList.get(i);
					map.put("CELL"+j, empInfo.get("POST_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			代职职责	REPLACE_DUTY
			if(sql.indexOf(",REPLACE_DUTY,")>=0){
				if(i < partDutyList.size()){
					Map empInfo = (Map) partDutyList.get(i);
					map.put("CELL"+j, empInfo.get("DUTY_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			代职职位	REPLACE_POSITION_NO
			if(sql.indexOf(",REPLACE_POSITION_NO,")>=0){
				if(i < partPositionList.size()){
					Map empInfo = (Map) partPositionList.get(i);
					map.put("CELL"+j, empInfo.get("POSITION_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			详细人力区分	DETAIL_HR_DIFF
			if(sql.indexOf(",DETAIL_HR_DIFF,")>=0){
				if(i < hrDiffList.size()){
					Map empInfo = (Map) hrDiffList.get(i);
					map.put("CELL"+j, empInfo.get("CODE_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			工资结算日	SAL_CALCULATE_DATE
			if(sql.indexOf(",SAL_CALCULATE_DATE,")>=0){
				if(i==0){
					paCalDate = "2013-09-01";
					map.put("CELL"+j, paCalDate);
				}else{
					map.put("CELL"+j, "");
				}
				j++;
			}
//			调令事由	TRANSFER_ORDER_REASON
			if(sql.indexOf(",TRANSFER_ORDER_REASON,")>=0){
				if(i==0){
					transReason = "调令事由" ;
					map.put("CELL"+j, transReason);
				}else{
					map.put("CELL"+j, "");
				}
				j++;
			}
//			转正日期 POSITIVE_DATES
			if(sql.indexOf(",POSITIVE_DATES,")>=0){
				if(i==0){
					positiveDates = "2013-09-01";
					map.put("CELL"+j, positiveDates);
				}else{
					map.put("CELL"+j, "");
				}
				j++;
			}
//			休职类型 POSITION_PAUSE_TYPE
			if(sql.indexOf(",POSITION_PAUSE_TYPE,")>=0){
				if(i < positionPauseTypeList.size()){
					Map empInfo = (Map) positionPauseTypeList.get(i);
					map.put("CELL"+j, empInfo.get("CODE_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			离职类型 RESIGN_TYPE
			if(sql.indexOf(",RESIGN_TYPE,")>=0){
				if(i < resignTypeList.size()){
					Map empInfo = (Map) resignTypeList.get(i);
					map.put("CELL"+j, empInfo.get("CODE_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			离职原因 RESIGN_REASON
			if(sql.indexOf(",RESIGN_REASON,")>=0){
				if(i < resignReasonList.size()){
					Map empInfo = (Map) resignReasonList.get(i);
					map.put("CELL"+j, empInfo.get("CODE_NAME"));
					j++;
				}else{
					map.put("CELL"+j, "");
					j++;
				}
			}
//			借调公司
			if(sql.indexOf(",TEMP_CPNY,")>=0){
				if(i==0){
					tempCpny  = "借调公司" ;
					map.put("CELL"+j, tempCpny);
				}else{
					map.put("CELL"+j, "");
				}
				j++;
			}			
//			调令编号	TRANSFER_ORDER_NO
			if(sql.indexOf(",TRANSFER_ORDER_NO,")>=0){
				if(i==0){
					transOrderNo = "调令编号" ;
					map.put("CELL"+j, transOrderNo);
				}else{
					map.put("CELL"+j, "");
				}
				j++;
			}
//			借调期间
			if(sql.indexOf(",TEMP_DATE,")>=0){
				if(i==0){
					tempDate  = "借调期间" ;
					map.put("CELL"+j, tempDate);
				}else{
					map.put("CELL"+j, "");
				}
				j++;
			}			
//			备注	REMARK
			if(sql.indexOf(",REMARK,")>=0){
				if(i==0){
					remark  = "备注" ;
					map.put("CELL"+j, remark);
				}else{
					map.put("CELL"+j, "");
				}
				j++;
			}
			itemDetailList.add(map);
		}

		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(itemDetailList);
	
		
	

//		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
//		List sheet2List=new ArrayList();
//		
//		for(int j=0;j<listTitle.size();j++){
//			Map map=(Map)listTitle.get(j);
//			String sql=map.get("DISTINCT_FIELD")+","+map.get("CONTENT")+"-";
//			String val=map.get("CONTENT")+","+map.get("OUTPUT_TYPE")+","+map.get("DISTINCT_FIELD")+map.get("PARENT_TABLE_NAME")+map.get("PARENT_CODE_NO");
//			Map map1=new LinkedHashMap<String, String>();
//			
//			
//			sheet2List.add(j, map);
//			
//		}
			
//			// 开始设定sheet的列名以及数据
//			String sqlItemName = "SELECT 'IN' CONTENT FROM DUAL "
//					+ " UNION SELECT 'OUT' CONTENT FROM DUAL ";
//			LinkedHashMap sqlmap = new LinkedHashMap();
//			sqlmap.put("sqlContent", sqlItemName);
//			List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
//			itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",
//					request));// 类型(请填写sheet1中的数据)

			
			String name = "trans_module1";
			
		
//		this.excelUtilSer.exportExcelTwoSheetDiaoling(request, response, modelMap, sqlContentmap, aliasNameList, list, sheet2List);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, null, name);
	}
	/**
	 * 下载导入模板需要写的方法（工资维护--输入项目数据） Description:download the module for upload
	 * data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportPaBasicItemDataExcelIsNullModule")
	public void exportPaBasicItemDataExcelIsNullModule(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		String filed1Name = request.getParameter("FIELD1_NAME");
		String filed2Name = request.getParameter("FIELD2_NAME");
		if (filed1Name != null) {
			aliasNameList.add(filed1Name);
		}
		if (filed2Name != null) {
			aliasNameList.add(filed2Name);
		}
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
		//aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//备注
		
		String filed1Value = request.getParameter("FIELD1");// LOCAL_NAME
		String sqlNotPersonContent = //" SELECT U.CONTENT  FROM (" +
				"select S.content from PA_HR_V t,SY_GLOBAL_NAME S WHERE t."
				+ filed1Value
				+ " = S.NO(+) AND S.LANGUAGE(+)='"
				+ admin.getLanguage()
				+ "' AND T.CPNY_ID='"
				+ admin.getCpnyId()
				+ "' AND S.CONTENT IS NOT NULL "
				+" GROUP BY S.CONTENT, S.NO";
				/*+") U" 
				+" WHERE rownum<3";*/
		LinkedHashMap sqlmap = new LinkedHashMap();
		List contentList = null;
		if(filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("CPNY_ID")){
			String sqlPersonContent = "SELECT SY.CONTENT  FROM HR_EMPLOYEE HE,HR_COMPANY HC,SY_GLOBAL_NAME SY "
										+" WHERE HE.CPNY_ID = HC.CPNY_ID AND HC.CPNY_NO = SY.NO(+) "
										+" AND SY.LANGUAGE(+) = '"+ admin.getLanguage()
										+"'AND HE.CPNY_ID = '"
										+  admin.getCpnyId()
										+"'AND ROWNUM < 3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}else if(filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("DEPTNO")){
			String sqlPersonContent = "SELECT U.CONTENT  FROM (SELECT SY.CONTENT  FROM HR_EMPLOYEE HE,HR_DEPARTMENT_NAME SY "
										+" WHERE HE.DEPTNO = SY.DEPTNO(+) "
										+" AND SY.LANGUAGE(+) = '"+ admin.getLanguage()
										+"'AND HE.CPNY_ID = '"
										+  admin.getCpnyId()
										+" GROUP BY S.CONTENT, S.NO) U" 
										+"'WHERE ROWNUM < 3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}else if (filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && !filed1Value.equals("LOCAL_NAME")
				&& !filed1Value.equals("PERSON_ID")) {
			sqlmap.put("sqlContent", sqlNotPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		} else if (filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("LOCAL_NAME")) {
			String sqlPersonContent = "SELECT LOCAL_NAME  CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId() + "' AND ROWNUM<3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		} else if (filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("PERSON_ID")) {
			String sqlPersonContent = "SELECT EMPID  CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId() + "' AND ROWNUM<3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}
		String filed2Value = request.getParameter("FIELD2");// PERSON_ID
		String sqlNotPersonContent2 = "select S.content from PA_HR_V t,SY_GLOBAL_NAME S WHERE t."
				+ filed2Value
				+ " = S.NO(+) AND S.LANGUAGE(+)='"
				+ admin.getLanguage()
				+ "' AND T.CPNY_ID='"
				+ admin.getCpnyId()
				+ "' " + " AND S.CONTENT IS NOT NULL and rownum<3";
		LinkedHashMap sqlmap2 = new LinkedHashMap();
		List contentList2 = null;
		if(filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && filed2Value.equals("DEPTNO")){
			String sqlPersonContent = "SELECT SY.CONTENT  FROM HR_EMPLOYEE HE,HR_DEPARTMENT_NAME SY "
										+" WHERE HE.DEPTNO = SY.DEPTNO(+) "
										+" AND SY.LANGUAGE(+) = '"+ admin.getLanguage()
										+"'AND HE.CPNY_ID = '"
										+  admin.getCpnyId()
										+"'AND ROWNUM < 3";
			sqlmap2.put("sqlContent", sqlPersonContent);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap2);
		}else if (filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && !filed2Value.equals("LOCAL_NAME")
				&& !filed2Value.equals("PERSON_ID")) {
			sqlmap2.put("sqlContent", sqlNotPersonContent2);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap2);
		} else if (filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && filed2Value.equals("LOCAL_NAME")) {
			String sqlPersonContent = "SELECT LOCAL_NAME  CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId() + "' AND ROWNUM<3";
			sqlmap2.put("sqlContent", sqlPersonContent);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap2);
		} else if (filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && filed2Value.equals("PERSON_ID")) {
			String sqlPersonContent = "SELECT EMPID CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId() + "' AND ROWNUM<3";
			sqlmap2.put("sqlContent", sqlPersonContent);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap2);
		}
		List list = new ArrayList();
		int maxSize=-1;
		if(contentList.size()>0){
			maxSize=contentList.size();
		}
		for(int i=0;i<maxSize;i++){
			LinkedHashMap map = new LinkedHashMap();
			if (filed1Name != null) {
				String cellStr = "";
				if (contentList != null && contentList.size() > 0
						&& contentList.get(i) != null) {
					cellStr = ((Map) (contentList.get(i))).get("CONTENT") == null ? ""
							: ((Map) (contentList.get(i))).get("CONTENT")
									.toString();
				}
				map.put("CELL0", cellStr);
			}
			if (filed2Name != null) {
				String cellStr = "";
				if (contentList2 != null && contentList2.size() > 0
						&& contentList2.get(i) != null) {
					cellStr = ((Map) (contentList2.get(i))).get("CONTENT") == null ? ""
							: ((Map) (contentList2.get(i))).get("CONTENT")
									.toString();
				}
				map.put("CELL1", cellStr);
			}
			if(i>1){
				map.put("CELL2", "");
				//map.put("CELL3", "2010/09/01");
				map.put("CELL3", "");
				map.put("CELL4", "");
				list.add(map);
			}else{
			map.put("CELL2", "2013/09/01");
			//map.put("CELL3", "2010/09/01");
			map.put("CELL3", "0.5");
			map.put("CELL4", "备注");
			list.add(map);
			}
		}
		
		/*LinkedHashMap map = new LinkedHashMap();
		if (filed1Name != null) {
			String cellStr = "";
			if (contentList != null && contentList.size() > 0
					&& contentList.get(0) != null) {
				cellStr = ((Map) (contentList.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList.get(0))).get("CONTENT")
								.toString();
			}
			map.put("CELL0", cellStr);
		}
		if (filed2Name != null) {
			String cellStr = "";
			if (contentList2 != null && contentList2.size() > 0
					&& contentList2.get(0) != null) {
				cellStr = ((Map) (contentList2.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList2.get(0))).get("CONTENT")
								.toString();
			}
			map.put("CELL1", cellStr);
		}

		map.put("CELL2", "2013/09/01");
		//map.put("CELL3", "2010/09/01");
		map.put("CELL3", "0.5");
		map.put("CELL4", "备注");
		list.add(map);

		LinkedHashMap map2 = new LinkedHashMap();
		if (filed1Name != null) {
			String cellStr = "";
			if (contentList != null && contentList.size() > 1
					&& contentList.get(1) != null) {
				cellStr = ((Map) (contentList.get(1))).get("CONTENT") == null ? ""
						: ((Map) (contentList.get(1))).get("CONTENT")
								.toString();
			} else if (contentList != null && contentList.size() == 1) {
				cellStr = ((Map) (contentList.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList.get(0))).get("CONTENT")
								.toString();
			}
			map2.put("CELL0", cellStr);
		}
		if (filed2Name != null) {
			String cellStr = "";
			if (contentList2 != null && contentList2.size() > 1
					&& contentList2.get(1) != null) {
				cellStr = ((Map) (contentList2.get(1))).get("CONTENT") == null ? ""
						: ((Map) (contentList2.get(1))).get("CONTENT")
								.toString();
			} else if (contentList2 != null && contentList2.size() == 1) {
				cellStr = ((Map) (contentList2.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList2.get(0))).get("CONTENT")
								.toString();
			}
			map2.put("CELL1", cellStr);
		}
		map2.put("CELL2", "2013/09/01");
		//map2.put("CELL3", "2010/09/01");
		map2.put("CELL3", "0.75");
		map2.put("CELL4", "备注");
		list.add(map2);*/

		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}

	/**
	 * 下载导入模板-考勤汇总 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportArMonthModule")
	public void exportArMonthModule(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();

		aliasNameList.add(TipMessage.getTipMessage(
				"ar.excelexport.title.armonth", request));// 考勤月
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",
				request));// 工号
		aliasNameList.add(TipMessage
				.getTipMessage("public.title.name", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",
				request));// 部门

		List getArColumnsList = new ArrayList();

		getArColumnsList = arMonthSer.getArColumns(request); // 获取显示汇总项目列名

		if (getArColumnsList.size() > 0) {
			for (int j = 0; j < getArColumnsList.size(); j++) {
				aliasNameList.add(((LinkedHashMap) getArColumnsList.get(j))
						.get("ITEM_NAME"));
			}
		}

		List list = new ArrayList();

		Map paramMap = new LinkedHashMap();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("STAT_NO", request.getParameter("STAT_NO"));
		paramMap.put("AR_MONTH", request.getParameter("AR_MONTH"));
		paramMap.put("condition", request.getParameter("condition"));
		paramMap.put("deptNO", request.getParameter("deptNO"));
		paramMap.put("EmpTypeCodeNo", request.getParameter("EmpTypeCodeNo"));
		paramMap.put("EmpOffice", request.getParameter("EmpOffice"));
		paramMap.put("JobTypeGroupNo", request.getParameter("JobTypeGroupNo"));

		List personalList = new ArrayList();
		personalList = this.excelUtilSer.exportPersonalList(paramMap);
		for (int k = 0; k < personalList.size(); k++) {
			LinkedHashMap getValueMap = (LinkedHashMap) personalList.get(k);
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", paramMap.get("AR_MONTH"));
			map.put("CELL1", getValueMap.get("EMPID"));
			map.put("CELL2", getValueMap.get("LOCAL_NAME"));
			map.put("CELL3", getValueMap.get("DEPTNAME"));

			if (getArColumnsList.size() > 0) {
				for (int n = 4; n < getArColumnsList.size() + 4; n++) {
					map.put("CELL" + n, 0);
				}
			}

			list.add(map);
		}

		String name = "viewArMonth_module";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null,name);
	}
	
	
	
	/**
	 * 公会管理下载模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/downloadPaLaborUnion")
	public void downloadPaLaborUnion(HttpServletRequest request,HttpServletResponse response,
							ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();


		aliasNameList.add("工号");
		aliasNameList.add("姓名");
		aliasNameList.add("入会日(退会人员不用填此列)");
		aliasNameList.add("退会日(入会人员不用填此列)");
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "AIT001");
		map.put("CELL1", "AIT");
		map.put("CELL2", "2012-06-01");
		map.put("CELL3", "2012-06-30");

		
		list.add(map);
		String name = "LaborUnion";
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null,name);
	}
	
	/**
	 * 保险基数管理下载模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/downloadInstanceNum")
	public void downloadInstanceNum(HttpServletRequest request,HttpServletResponse response,
							ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();


		aliasNameList.add("工号");
		aliasNameList.add("姓名");
		aliasNameList.add("年度基数");
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "12000006");
		map.put("CELL1", "李性现");
		map.put("CELL2", "150.65");
		
		list.add(map);
		String name = "instancebasenum";
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null,name);
	}
	
	

	/**
	 * 经济补偿金计算下载模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/exportPaEccModule")
	public void exportPaEccModule(HttpServletRequest request,HttpServletResponse response,
							ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();

		aliasNameList.add("年月");
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",
				request));// 工号
//		aliasNameList.add(TipMessage
//				.getTipMessage("public.title.name", request));// 姓名
//		aliasNameList.add("离职类型");
		aliasNameList.add("经济补偿金结算日");
		aliasNameList.add("经济补偿金");
		aliasNameList.add("待通知金");
		aliasNameList.add("备注");
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "201402");
		map.put("CELL1", "1234");
		map.put("CELL2", "2014-02-10");
		map.put("CELL3", "100万");
		map.put("CELL4", "100");
		map.put("CELL5", "aaaa");
		list.add(map);
		String name = "paEcc";
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null,name);
	}
	
	/**
	 * 导出  经济补偿金记录
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/exportPaEccInfo")
	public void exportPaEccInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String language = admin.getLanguage();
		String CPNY_ID = admin.getCpnyId();
//		String ar_supervisior_info = admin.getPersonId();
		String paMonth = request.getParameter("year")+request.getParameter("month");
		String batches = request.getParameter("batches");
//		String STAT_NO = request.getParameter("STAT_NO");
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
//		List getArColumnsList = arMonthSer.getArColumns(request); // 获取显示汇总项目列名

		String tableName = "T_PA_ECC_RESULT";
		Map parameterMap = new LinkedHashMap();
		parameterMap.put("PA_MONTH", paMonth);
		parameterMap.put("batches", batches);
		String settlementFlag = paEccService.getEccFlag(request);
		if(settlementFlag.equals("1")){
			tableName = "PA_ECC_HISTORY";
		}
		aliasList.add("PA_MONTH");
		aliasList.add("EMPID");
		aliasList.add("LOCAL_NAME");
		aliasList.add("LEFT_DATE");
		aliasList.add("LEFT_TYPE");
		aliasList.add("AVG_SALARY");
		aliasList.add("COMPENSATION_MONTH");
		aliasList.add("NOTICE_AMT");
		aliasList.add("ECC_AMT");
		aliasList.add("TAX");
		aliasList.add("AVG_SALARY_FD");
		aliasList.add("COMPENSATION_MONTH_FD");
		
		aliasNameList.add("年月");
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",
				request));// 工号
		aliasNameList.add(TipMessage
				.getTipMessage("public.title.name", request));// 姓名
//		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",
//				request));// 部门
		//职位aliasNameList.add("");
		aliasNameList.add("离职日期");
		aliasNameList.add("离职类型");
		aliasNameList.add("平均工资");
		aliasNameList.add("补偿月数");
		aliasNameList.add("待通知金");
		aliasNameList.add("经济补偿金");
		aliasNameList.add("税金");
		aliasNameList.add("平均法定工资");
		aliasNameList.add("法定补偿月数");
		String sqlContent = "SELECT T.PA_MONTH,"+
						      " T.EMPID," +
						      " GET_EMP_LOCAL_NAME(T.PERSON_ID) LOCAL_NAME,"+
						      " T.LEFT_DATE,"+
						      " T.LEFT_TYPE,"+
						      " T.AVG_SALARY,"+
						      " T.COMPENSATION_MONTH,"+
						      " T.NOTICE_AMT,"+
						      " T.ECC_AMT,"+
						      " T.TAX,"+
						      " T.AVG_SALARY_FD,"+
						      " T.COMPENSATION_MONTH_FD " +
						      "FROM "+tableName+" T " +
								"WHERE T.CPNY_ID = '"+CPNY_ID+"' "+
								" AND T.PA_MONTH = '"+paMonth+"' " +
								" AND T.BATCHES=" + batches;
//		if(STAT_NO !=null && !"".equals(STAT_NO)){
//			sqlContent = sqlContent + " AND HR.STAT_NO = '"+STAT_NO+"'";
//		}
		
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		
		String name = "viewEccMonth_export";
		
		this.excelUtilSer.exportExcelByName(request, response, modelMap, map,
				aliasNameList, aliasList, name);
	}

	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportArItemDataExcel")
	public void exportArItemDataExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String language = admin.getLanguage();
		String CPNY_ID = admin.getCpnyId();
		String ar_supervisior_info = admin.getPersonId();
		String arMonth = request.getParameter("arMonth");
		String STAT_NO = request.getParameter("STAT_NO");
		
		String DEPT_NO = request.getParameter("DEPT_NO");
		String JobTypeGroupNo = request.getParameter("JobTypeGroupNo");
		String EmpTypeCodeNo = request.getParameter("EmpTypeCodeNo");
		String EmpOffice = request.getParameter("EmpOffice");
		String condition = request.getParameter("condition");
		
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		List getArColumnsList = arMonthSer.getArColumns(request); // 获取显示汇总项目列名

		aliasList.add("AR_MONTH");
		aliasList.add("EMPID1");
		aliasList.add("LOCAL_NAME1");
		aliasList.add("DEPTNAME1");
		aliasList.add("EMP_TYPE_NAME");

		aliasNameList.add(TipMessage.getTipMessage(
				"ar.excelexport.title.armonth", request));// 考勤月
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",
				request));// 工号
		aliasNameList.add(TipMessage
				.getTipMessage("public.title.name", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",
				request));// 部门
		aliasNameList.add("人员类型");// 部门
		for (int k = 0; k < getArColumnsList.size(); k++) {
			LinkedHashMap map = new LinkedHashMap();
			map = (LinkedHashMap) getArColumnsList.get(k);
			aliasList.add(map.get("COLUMN_NAME"));
			aliasNameList.add(map.get("ITEM_NAME"));
		}
/*
		String sqlContent = "	SELECT AR.*,HR_EMPLOYEE.EMPID EMPID1,HR_EMPLOYEE.LOCAL_NAME LOCAL_NAME1,HR_DEPARTMENT_NAME.CONTENT DEPTNAME1 "
				+ " FROM AR_SUMMARY_"
				+ CPNY_ID
				+ " AR , HR_EMPLOYEE ,HR_DEPARTMENT_NAME "
				+ " WHERE AR.AR_MONTH = '"
				+ arMonth
				+ "'"
				+ " AND AR.PERSON_ID = HR_EMPLOYEE.PERSON_ID "
				+ " AND HR_EMPLOYEE.DEPTNO = HR_DEPARTMENT_NAME.DEPTNO(+) "
				+ " AND HR_DEPARTMENT_NAME.LANGUAGE(+) = '"
				+ language
				+ "'"
				+ " AND EXISTS( "
				+ "	  	SELECT * "
				+ "		  FROM AR_SUPERVISOR_INFO "
				+ "		 WHERE AR_SUPERVISOR_INFO.DEPTNO = HR_EMPLOYEE.DEPTNO "
				+ "		   AND AR_SUPERVISOR_INFO.PERSON_ID = '"
				+ ar_supervisior_info + "'" + "	  )";
*/
		
		String sqlContent = "	SELECT AR.*,HR.EMPID EMPID1,HR.LOCAL_NAME LOCAL_NAME1,GET_DEPT_NAME(HR.DEPTNO,'zh' )  DEPTNAME1,AR.EMP_TYPE_NAME "
			+ " FROM AR_SUMMARY_"
			+ CPNY_ID
			+ " AR , HR_EMPLOYEE_FOR_CAL HR "
			+ " WHERE AR.AR_MONTH = '"
			+ arMonth
			+ "'"
			+ " AND AR.PERSON_ID = HR.PERSON_ID "
			+ " AND EXISTS( "
			+ "	  	SELECT * "
			+ "		  FROM AR_SUPERVISOR_INFO "
			+ "		 WHERE AR_SUPERVISOR_INFO.DEPTNO = HR.DEPTNO "
			+ "		   AND AR_SUPERVISOR_INFO.PERSON_ID = '"
			+ ar_supervisior_info + "'" + "	  )";
		 
			if(STAT_NO !=null && !"".equals(STAT_NO)){
				sqlContent = sqlContent + " AND (    SELECT STAT_NO "
                                        +" FROM AR_STATISTIC_EMPTYPE ASE "
                                        +"  WHERE ASE.EMP_TYPE_CODE =HR.EMP_TYPE_CODE "
                                        +" AND ASE.CPNY_ID =  '"+CPNY_ID+"' "
                                        +"  AND ROWNUM = 1 )=  '"+STAT_NO+"'";
			}
			
			if(DEPT_NO !=null && !"".equals(DEPT_NO)){
				sqlContent = sqlContent + " AND     EXISTS ( "
					+"  SELECT     * "
                    +"	FROM HR_DEPARTMENT B1 "
                    +" 	WHERE B1.DEPTNO=HR.DEPTNO "
                 	+" START WITH B1.DEPTNO = '"+DEPT_NO +"' "
                 	+" CONNECT BY PRIOR B1.DEPTNO = B1.PARENT_DEPT_NO ) ";
             }
			
			if(JobTypeGroupNo !=null && !"".equals(JobTypeGroupNo)){
				sqlContent = sqlContent + " AND HR.EMP_TYPE_CODE IN (SELECT JOBTYPE_NO "
			         +" FROM HR_JOB_TYPE_SETUP P "
			         +" WHERE JOBTYPE_GROUP_NO = '"+JobTypeGroupNo+"'"
			         +"  AND CPNY_ID = '"+CPNY_ID+"' ) ";
			          
             }
			
			if(EmpTypeCodeNo !=null && !"".equals(EmpTypeCodeNo)){
				sqlContent = sqlContent + " AND  HR.EMP_TYPE_CODE ='"+EmpTypeCodeNo+"'";
			          
             }
			if(EmpOffice !=null && !"".equals(EmpOffice)){
				sqlContent = sqlContent + " AND    HR.emp_office  ='"+EmpOffice+"'";
			          
             }
			if(condition !=null && !"".equals(condition)){
				sqlContent = sqlContent + " AND     (   HR.EMPID LIKE '%"+condition+"%' "
		                               +"  OR HR.LOCAL_NAME LIKE '%"+condition+"%')	";
			          
             }
		System.out.println(sqlContent);
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		
		String name = "viewArMonth_export";
		
		this.excelUtilSer.exportExcelByName(request, response, modelMap, map,
				aliasNameList, aliasList, name);
	}

	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportArDetailExcel")
	public void exportArDetailExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String condition = request.getParameter("condition") == null ? ""
				: request.getParameter("condition");
		String deptID = request.getParameter("deptID") == null ? "" : request
				.getParameter("deptID");
		String sDate = request.getParameter("sDate") == null ? "" : request
				.getParameter("sDate").replaceAll("-","/");
		String eDate = request.getParameter("eDate") == null ? "" : request
				.getParameter("eDate").replaceAll("-","/");
		String itemNo = request.getParameter("itemNo") == null ? "" : request
				.getParameter("itemNo");
		String status = request.getParameter("status") == null ? "" : request
				.getParameter("status");

		String language = admin.getLanguage();
		String cpnyId = admin.getCpnyId();
		String supervisor = admin.getAdminID();
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		// String filed1Name=request.getParameter("FIELD1_NAME");
		// String filed2Name=request.getParameter("FIELD2_NAME");
		// aliasNameList.add(filed1Name);
		// if(filed2Name!=null){
		// aliasNameList.add(filed2Name);
		// }工号 姓名 考勤的开始时间 考勤的结束时间 当天日期 当月 值 明细项目
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewardetail.title.date", request));// 日期
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",
				request));// 工号
		aliasNameList.add(TipMessage
				.getTipMessage("public.title.name", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",
				request));// 部门
		
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewCompanyCalendar.title.banci", request));// 班次
		 if (!cpnyId.equals("TSTO")) {
		     aliasNameList.add( "是否夜班"); 
		 }
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewcycle.title.zhuangtai", request));// 状态
		aliasNameList.add("考勤开始日期");// 考勤的开始时间
		aliasNameList.add("考勤开始时间");// 考勤的开始时间
		aliasNameList.add("考勤结束日期");// 考勤的结束时间
		aliasNameList.add("考勤结束时间");// 考勤的结束时间
		aliasNameList.add(TipMessage.getTipMessage(
				"ess.viewApply.title.length", request));// 长度
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.excelexport.title.lockon", request));// 是否锁定

		aliasList.add("AR_DATE_STR");
		aliasList.add("EMPID");
		aliasList.add("LOCAL_NAME");
		aliasList.add("DEPTNAME");
		aliasList.add("SHIFT_NAME");
		 if (!cpnyId.equals("TSTO")) {
	            	aliasList.add("ISYEBAN");
		   }
		aliasList.add("ITEM_NAME");
		aliasList.add("FROMTIME");
		aliasList.add("FROMTIME1");
		aliasList.add("TOTIME");
		aliasList.add("TOTIME1");
		aliasList.add("QUANTITY");
		aliasList.add("LOCK_YN");

		String sqlContent = " SELECT AR_DETAIL.PK_NO,						"
				+ "       AR_DETAIL.PERSON_ID,						"
				+ "       AR_DETAIL.AR_DATE_STR,					"
				+ "       AR_DETAIL.DATE_TYPE,						"
				+ "       AR_DETAIL.AR_MONTH_STR,					"
				+ "       AR_DETAIL.ITEM_NO,						"
				+ "       NVL(TO_CHAR(AR_DETAIL.FROM_TIME, 'YYYY-MM-DD'),'') FROMTIME,	"
				+ "       NVL(TO_CHAR(AR_DETAIL.FROM_TIME, 'HH24:MI:SS'),'') FROMTIME1,	"
				+ "       NVL(TO_CHAR(AR_DETAIL.TO_TIME, 'YYYY-MM-DD'),'') TOTIME,		"
				+ "       NVL(TO_CHAR(AR_DETAIL.TO_TIME, 'HH24:MI:SS'),'') TOTIME1,		"
				+ "       AR_DETAIL.SHIFT_NO,						"
				+ "       AR_DETAIL.QUANTITY,						"
				+ "       AR_DETAIL.UNIT UNIT,						"
				+ "       AR_DETAIL.LOCK_YN,						"
				+ "       GET_DAILY_STATUS(AR_DETAIL.Ar_Date_Str,'"
				+ cpnyId
				+ "', HR_EMPLOYEE.PERSON_ID) status,			"
				+ "       HR_EMPLOYEE.EMPID,						"
				+ "             HR_EMPLOYEE.LOCAL_NAME,				"
				+ "             HISTORY.DEPTNO,					"
				+ "             HR_DEPARTMENT_NAME.CONTENT DEPTNAME,	"
				+ "             B.CONTENT SHIFT_NAME,				"
				+ "       C.CONTENT ITEM_NAME						";
			
			 if (!cpnyId.equals("TSTO")) {
				 sqlContent  = sqlContent+" , (CASE   WHEN INSTR(get_global_name( AR_DETAIL.SHIFT_NO,'zh'), '夜') <> 0 THEN 'Y'  ELSE 'N'  END) ISYEBAN ";
			   }
				
				
				sqlContent  = sqlContent +"    FROM AR_DETAIL_"
				+ cpnyId
				+ " AR_DETAIL,		"
				+ "         HR_EMPLOYEE,							"
				+ "         HR_DEPARTMENT_NAME,	   HR_EMP_HISTORY_STATUS HISTORY		,			"
				+ "         SY_GLOBAL_NAME B,						"
				+ "         SY_GLOBAL_NAME C						"
				+ "   WHERE AR_DETAIL.PERSON_ID = HR_EMPLOYEE.PERSON_ID					"
				+ "  AND HR_EMPLOYEE.PERSON_ID = HISTORY.PERSON_ID  "
				+ "   AND AR_DETAIL.AR_DATE_STR = HISTORY.DATE_STR "
				+ "     AND HISTORY.DEPTNO = HR_DEPARTMENT_NAME.DEPTNO(+)			"
				+ "     AND HR_DEPARTMENT_NAME.LANGUAGE(+) = '"
				+ language
				+ "'			"
				+ "     AND AR_DETAIL.SHIFT_NO = B.NO(+)								"
				+ "     AND B.LANGUAGE(+) = '"
				+ language
				+ "'							"
				+ "     AND AR_DETAIL.ITEM_NO = C.NO(+)									"
				+ "     AND C.LANGUAGE(+) = '"
				+ language
				+ "'							"
				+ "     AND EXISTS(														"
				+ "            SELECT *													"
				+ "            FROM AR_SUPERVISOR_INFO									"
				+ "           WHERE AR_SUPERVISOR_INFO.DEPTNO = HISTORY.DEPTNO		"
				+ "             AND AR_SUPERVISOR_INFO.PERSON_ID = '"
				+ supervisor + "' " + "          ) " 
		        +"	AND   EXISTS(                                                        "
	 		  	+" SELECT *                                                              "
				+"  FROM AR_SUPERVISOR_EMPTYPE_INFO                                      "
				+"  WHERE AR_SUPERVISOR_EMPTYPE_INFO.EMP_TYPE_CODE = HR_EMPLOYEE.EMP_TYPE_CODE "
				+"   AND AR_SUPERVISOR_EMPTYPE_INFO.PERSON_ID ='"
				+ supervisor + "' " + "          ) " ;
		if (!"".equals(condition)) {
			sqlContent = sqlContent
					+ " AND (   HR_EMPLOYEE.EMPID LIKE '%' || '" + condition
					+ "' || '%' " + " OR HR_EMPLOYEE.LOCAL_NAME LIKE '%' || '"
					+ condition + "' || '%' " + " ) ";
		}

		if (!"".equals(deptID)) {
			sqlContent = sqlContent
					+ " AND EXISTS ( 		"
					+ " 		SELECT     *	"
					+ "             	FROM HR_DEPARTMENT B1							"
					+ "              WHERE B1.DEPTNO=HISTORY.DEPTNO				"
					+ "          	START WITH B1.DEPTNO = '"
					+ deptID
					+ "'			"
					+ "          	CONNECT BY PRIOR B1.DEPTNO = B1.PARENT_DEPT_NO	"
					+ "         )													";
		}

		if (!"".equals(sDate)) {
			sqlContent = sqlContent
					+ " AND  AR_DATE_STR  >=  '" + sDate+"'" ;
		}

		if (!"".equals(eDate)) {
			sqlContent = sqlContent
					+ " AND  AR_DATE_STR  <=  '"+ eDate+"'" ;
		}

		if (!"".equals(itemNo)) {
			sqlContent = sqlContent + " AND AR_DETAIL.ITEM_NO = '" + itemNo
					+ "' ";
		}

		if (!"".equals(status)) {
			sqlContent = sqlContent + " AND AR_DETAIL.LOCK_YN = '" + status
					+ "' ";
		}

		sqlContent = sqlContent
				+ " ORDER BY AR_DETAIL.AR_DATE_STR, AR_DETAIL.PERSON_ID, AR_DETAIL.ITEM_NO ";

		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);

		map.put("sqlContent", sqlContent);
		
		String name = "viewArDetail_export";
		
		this.excelUtilSer.exportExcelByName(request, response, modelMap, map,
				aliasNameList, aliasList, name);
	}

	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportArDetailEssListExcel")
	public void exportArDetailEssListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String personId = admin.getPersonId();
		String sDate = request.getParameter("sDate") == null ? "" : request.getParameter("sDate");
		String eDate = request.getParameter("eDate") == null ? "" : request.getParameter("eDate");
		String itemNo = request.getParameter("itemNo") == null ? "" : request.getParameter("itemNo");
		String status = request.getParameter("status") == null ? "" : request.getParameter("status");

		String language = admin.getLanguage();
		String cpnyId = admin.getCpnyId();
		//String supervisor = admin.getAdminID();
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		// String filed1Name=request.getParameter("FIELD1_NAME");
		// String filed2Name=request.getParameter("FIELD2_NAME");
		// aliasNameList.add(filed1Name);
		// if(filed2Name!=null){
		// aliasNameList.add(filed2Name);
		// }工号 姓名 考勤的开始时间 考勤的结束时间 当天日期 当月 值 明细项目
		aliasNameList.add(TipMessage.getTipMessage("ar.viewardetail.title.date", request));// 日期
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",request));// 工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",request));// 部门
		aliasNameList.add(TipMessage.getTipMessage("ar.viewCompanyCalendar.title.banci", request));// 班次
		aliasNameList.add(TipMessage.getTipMessage("ar.viewcycle.title.zhuangtai", request));// 状态
		aliasNameList.add(TipMessage.getTipMessage("ar.excelexport.title.kaoqingstart", request));// 考勤的开始时间
		aliasNameList.add(TipMessage.getTipMessage("ar.excelexport.title.kaoqingend", request));// 考勤的结束时间
		aliasNameList.add(TipMessage.getTipMessage("ess.viewApply.title.length", request));// 长度
		aliasNameList.add(TipMessage.getTipMessage("ar.excelexport.title.lockon", request));// 是否锁定

		aliasList.add("AR_DATE_STR");
		aliasList.add("EMPID");
		aliasList.add("LOCAL_NAME");
		aliasList.add("DEPTNAME");
		aliasList.add("SHIFT_NAME");
		aliasList.add("ITEM_NAME");
		aliasList.add("FROMTIME");
		aliasList.add("TOTIME");
		aliasList.add("QUANTITY");
		aliasList.add("LOCK_YN");

		String sqlContent = " SELECT AR_DETAIL.PK_NO,						"
				+ "       AR_DETAIL.PERSON_ID,						"
				+ "       AR_DETAIL.AR_DATE_STR,					"
				+ "       AR_DETAIL.DATE_TYPE,						"
				+ "       AR_DETAIL.AR_MONTH_STR,					"
				+ "       AR_DETAIL.ITEM_NO,						"
				+ "       NVL(TO_CHAR(AR_DETAIL.FROM_TIME, 'YYYY-MM-DD HH24:MI:SS'),'') FROMTIME,	"
				+ "       NVL(TO_CHAR(AR_DETAIL.TO_TIME, 'YYYY-MM-DD HH24:MI:SS'),'') TOTIME,		"
				+ "       AR_DETAIL.SHIFT_NO,						"
				+ "       AR_DETAIL.QUANTITY,						"
				+ "       AR_DETAIL.UNIT UNIT,						"
				+ "       AR_DETAIL.LOCK_YN,						"
				+ "       GET_DAILY_STATUS(AR_DETAIL.Ar_Date_Str,'"
				+ cpnyId
				+ "', HR_EMPLOYEE.PERSON_ID) status,			"
				+ "       HR_EMPLOYEE.EMPID,						"
				+ "             HR_EMPLOYEE.LOCAL_NAME,				"
				+ "             HR_EMPLOYEE.DEPTNO,					"
				+ "             HR_DEPARTMENT_NAME.CONTENT DEPTNAME,	"
				+ "             B.CONTENT SHIFT_NAME,				"
				+ "       C.CONTENT ITEM_NAME						"
				+ "    FROM AR_DETAIL_"
				+ cpnyId
				+ " AR_DETAIL,		"
				+ "         HR_EMPLOYEE,							"
				+ "         HR_DEPARTMENT_NAME,						"
				+ "         SY_GLOBAL_NAME B,						"
				+ "         SY_GLOBAL_NAME C						"
				+ "   WHERE AR_DETAIL.PERSON_ID = HR_EMPLOYEE.PERSON_ID					"
				+ "     AND HR_EMPLOYEE.DEPTNO = HR_DEPARTMENT_NAME.DEPTNO(+)			"
				+ "     AND HR_DEPARTMENT_NAME.LANGUAGE(+) = '"
				+ language
				+ "'			"
				+ "     AND AR_DETAIL.SHIFT_NO = B.NO(+)								"
				+ "     AND B.LANGUAGE(+) = '"
				+ language
				+ "'							"
				+ "     AND AR_DETAIL.ITEM_NO = C.NO(+)									"
				+ "     AND C.LANGUAGE(+) = '"
				+ language
				+ "'							" ;
		
		if (!"".equals(personId)) {
			sqlContent = sqlContent + " AND AR_DETAIL.PERSON_ID = '" + personId + "' ";
		}
		
		if (!"".equals(sDate)) {
			sqlContent = sqlContent + " AND TO_DATE(AR_DATE_STR, 'YYYY-MM-DD') >= TO_DATE('" + sDate + "','YYYY-MM-DD') ";
		}

		if (!"".equals(eDate)) {
			sqlContent = sqlContent + " AND TO_DATE(AR_DATE_STR, 'YYYY-MM-DD') <= TO_DATE('" + eDate + "','YYYY-MM-DD') ";
		}

		if (!"".equals(itemNo)) {
			sqlContent = sqlContent + " AND AR_DETAIL.ITEM_NO = '" + itemNo + "' ";
		}

		if (!"".equals(status)) {
			sqlContent = sqlContent + " AND AR_DETAIL.LOCK_YN = '" + status + "' ";
		}

		sqlContent = sqlContent + " ORDER BY AR_DETAIL.AR_DATE_STR, AR_DETAIL.PERSON_ID, AR_DETAIL.ITEM_NO ";

		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		String name = "viewArDetailEss_export";
		
		this.excelUtilSer.exportExcelByName(request, response, modelMap, map,aliasNameList, aliasList, name);
	}

	
	/**
	 * 下载导入模板-考勤明细维护导入模板 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportArDetailDataExcelModel")
	public void exportArDetailDataExcelModel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();

		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewardetail.title.date", request));// 日期
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.excelexport.title.month", request));// 月份
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",
				request));// 工号
		aliasNameList.add(TipMessage
				.getTipMessage("public.title.name", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",
				request));// 部门

		aliasNameList.add("考勤类型(请填写sheet2中的数据)");// 状态(请填写sheet2中的数据)
		aliasNameList.add("考勤开始日期");// 考勤的开始时间
		aliasNameList.add("考勤开始时间");// 考勤的开始时间
		aliasNameList.add("考勤结束日期");// 考勤的结束时间
		aliasNameList.add("考勤结束时间");// 考勤的结束时间
		aliasNameList.add("时长");// 长度
		aliasNameList.add("是否删除当天考勤(Y/N)");// 长度
	//	aliasNameList.add(TipMessage.getTipMessage(
	//			"hr.viewPersonalInfo.title.STATUS_NAME", request));// 员工状态

		List list = new ArrayList();
				
		 LinkedHashMap map2=new LinkedHashMap();
		 map2.put("CELL0","2012/04/01");
		 map2.put("CELL1","201204");
		 map2.put("CELL2","12001001");
		 map2.put("CELL3","李某某");
		 map2.put("CELL4","测试部门1");
				
		 map2.put("CELL5","正常出勤");
		 map2.put("CELL6","2012/04/01");
		 map2.put("CELL7","09:00:00");
		 map2.put("CELL8","2012/04/01");
		 map2.put("CELL9","18:00:00");
		 map2.put("CELL10","8");
		 map2.put("CELL11","N");
				
		 list.add(map2);
				
		 LinkedHashMap map=new LinkedHashMap();
		 map.put("CELL0","2012/06/22");
		 map.put("CELL1","201206");
		 map.put("CELL2","12003001");
		 map.put("CELL3","张某某");
		 map.put("CELL4","测试部门1");
				
		 map.put("CELL5","事假");
		 map.put("CELL6","2012/04/01");
		 map.put("CELL7","09:00:00");
		 map.put("CELL8","2012/04/01");
		 map.put("CELL9","18:00:00");
		 map.put("CELL10","8");
		 map.put("CELL11","N");
		 list.add(map);
/*		String sqlDetailInfo = "SELECT E.EMPID,E.LOCAL_NAME, DEPT.CONTENT DEPTNAME "
				+ " FROM HR_EMPLOYEE E,"
				+ " HR_EMP_PA_INFO HI,"
				+ " (SELECT HD.DEPTNO, HD.CONTENT "
				+ " FROM AR_SUPERVISOR_INFO SUP, "
				+ " HR_DEPARTMENT_NAME HD, "
				+ " HR_DEPARTMENT      H "
				+ " WHERE H.CPNY_ID = '"
				+ admin.getCpnyId()
				+ "' "
				+ " AND HD.DEPTNO = SUP.DEPTNO "
				+ " AND H.DEPTNO = HD.DEPTNO(+) "
				+ " AND HD.LANGUAGE(+) = '"
				+ admin.getLanguage()
				+ "' "
				+ " AND SUP.PERSON_ID = "
				+ admin.getPersonId()
				+ ") DEPT "
				+ " WHERE DEPT.DEPTNO = E.DEPTNO "
				+ " AND E.CPNY_ID = '"
				+ admin.getCpnyId()
				+ "' "
				+ " AND E.PERSON_ID = HI.PERSON_ID "
				+ " AND HI.CALC_FLAG = 'Y' " + " ORDER BY E.EMPID";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);

			if (i < 2) {
				map.put("CELL0", "2012/06/22");
				map.put("CELL1", "201206");
				if (empInfo != null) {
					map.put("CELL2", empInfo.get("EMPID") == null ? ""
							: empInfo.get("EMPID").toString());
					map.put("CELL3", empInfo.get("LOCAL_NAME") == null ? ""
							: empInfo.get("LOCAL_NAME").toString());
					map.put("CELL4", empInfo.get("DEPTNAME") == null ? ""
							: empInfo.get("DEPTNAME").toString());
				} else {
					map.put("CELL2", "");
					map.put("CELL3", "");
					map.put("CELL4", "");
				}
				map.put("CELL5", TipMessage.getTipMessage(
						"ar.excelexport.title.zhengchangchuqin", request));// 正常出勤
				map.put("CELL6", "2012/06/22 09:00:00");
				map.put("CELL7", "2012/06/22 18:00:00");
				map.put("CELL8", "8");
				map.put("CELL9", "正式");
			} else {
				map.put("CELL0", "");
				map.put("CELL1", "");
				if (empInfo != null) {
					map.put("CELL2", empInfo.get("EMPID") == null ? ""
							: empInfo.get("EMPID").toString());
					map.put("CELL3", empInfo.get("LOCAL_NAME") == null ? ""
							: empInfo.get("LOCAL_NAME").toString());
					map.put("CELL4", empInfo.get("DEPTNAME") == null ? ""
							: empInfo.get("DEPTNAME").toString());
				} else {
					map.put("CELL2", "");
					map.put("CELL3", "");
					map.put("CELL4", "");
				}
				map.put("CELL5", "");
				map.put("CELL6", "");
				map.put("CELL7", "");
				map.put("CELL8", "");
				map.put("CELL9", "");
			}
			list.add(map);
		}*/

		// 开始设定sheet的列名以及数据
		String sqlItemName = "SELECT S.CONTENT FROM AR_ITEM_PARAM A,SY_GLOBAL_NAME S "
				+ "WHERE A.ITEM_NO=S.NO(+) AND S.LANGUAGE(+)='"
				+ admin.getLanguage()
				+ "'"
				+ " AND A.GROUP_NO = 'constant' " 
				+ " AND A.CPNY_ID='"
				+ admin.getCpnyId() + "' and A.ACTIVITY=1";
		LinkedHashMap sqlmap = new LinkedHashMap();
		sqlmap.put("sqlContent", sqlItemName);
		List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		// 开始设定sheet
		String sqlItemName1 = " SELECT S1.CONTENT " +
							  " FROM SY_CODE T," +
							  " SY_GLOBAL_NAME S1 " +
							  " WHERE T.CODE_NO = S1.NO(+)" +
							  " AND S1.LANGUAGE(+) ='" + admin.getLanguage() + "'" +
							  " AND T.PARENT_CODE_NO = '1372' " +
							  " AND EXISTS (SELECT * " +
							  " FROM SY_CODE_PARAM P " +
							  " WHERE T.CODE_NO = P.CODE_NO " +
							  " AND P.CPNY_ID = '"+ admin.getCpnyId() +"' " +
							  " AND P.ACTIVITY = 1 ) ";
		//员工状态
		LinkedHashMap sqlmap1 = new LinkedHashMap();
		sqlmap1.put("sqlContent", sqlItemName1);
		List itemList1 = this.excelUtilSer.getContentNoByFiled(sqlmap1);
		
		itemList.add(TipMessage.getTipMessage("ar.viewcycle.title.zhuangtai",
				request));// 状态
		
		itemList1.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.STATUS_NAME",
				request));// 员工状态

		String name = "viewArDetail_module";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelThreeSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, itemList, itemList1, name);
	}

	/**
	 * 下载导入模板-刷卡导入模板 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportArCardRecordExcelModel")
	public void exportArCardRecordExcelModel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();

		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",request));// 工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",request));// 部门
		aliasNameList.add("日期（请自行设定文本格式）");// 时间
		aliasNameList.add(TipMessage.getTipMessage("ar.viewarcardrecord.title.shijian", request)+"（请自行设定文本格式）");// 时间
		aliasNameList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing", request));// 类型(请填写sheet2中的数据)
		aliasNameList.add(TipMessage.getTipMessage("ar.viewarcardrecord.title.beizhu", request));// 备注

		List list = new ArrayList();

		String sqlDetailInfo = "SELECT E.EMPID,E.LOCAL_NAME,GET_DEPT_NAME(E.DEPTNO, '"+admin.getLanguage()+"') DEPTNAME "
				+ " FROM HR_EMPLOYEE E "
			 
				+ " WHERE  E.STATUS_CODE <> 1375   "
				/*+ " AND EXISTS(                            "
				+ "            SELECT *                          "
				+ "            FROM AR_SUPERVISOR_INFO                  "
				+ "           WHERE AR_SUPERVISOR_INFO.DEPTNO = E.DEPTNO    "
				+ "             AND AR_SUPERVISOR_INFO.PERSON_ID = '"
				+ admin.getPersonId()
				+ "' "
				+ "          ) 				"*/
				+ " AND ROWNUM <= 2 ORDER BY E.DEPTNO,EMPID ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
      	List itemDetailList = this.excelUtilSer
	 		.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);

			if (empInfo != null) {
				map.put("CELL0", empInfo.get("EMPID") == null ? "" : empInfo
						.get("EMPID").toString());
				map.put("CELL1", empInfo.get("LOCAL_NAME") == null ? ""
						: empInfo.get("LOCAL_NAME").toString());
				map.put("CELL2", empInfo.get("DEPTNAME") == null ? "" : empInfo
						.get("DEPTNAME").toString());
			} else {
				map.put("CELL0", "");
				map.put("CELL1", "");
				map.put("CELL2", "");
			}

			if (i == 0) {
				map.put("CELL3", "2012-01-01");
				map.put("CELL4", "09:00:00");
				map.put("CELL5", "IN");
			}

			if (i == 1) {
			 
				map.put("CELL3", "2012-01-01");
				map.put("CELL4", "18:00:00");
				map.put("CELL5", "OUT");
			}

			list.add(map);
		}

		// 开始设定sheet的列名以及数据
		String sqlItemName = "SELECT 'IN' CONTENT FROM DUAL "
				+ " UNION SELECT 'OUT' CONTENT FROM DUAL ";
		LinkedHashMap sqlmap = new LinkedHashMap();
		sqlmap.put("sqlContent", sqlItemName);
		List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",
				request));// 类型(请填写sheet1中的数据)

		String name = "viewArCardRecord_module1";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, itemList, name);
	}
	
	/**
	 * 下载导入模板-评价信息模板 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportEvaluateInfoExcelModel")
	public void exportEvaluateInfoExcelModel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",request));// 工号
		aliasNameList.add("评价年度（请按照模版格式填写）");
		aliasNameList.add("一月");
		aliasNameList.add("二月");
		aliasNameList.add("三月");
		aliasNameList.add("四月");
		aliasNameList.add("五月");
		aliasNameList.add("六月");
		aliasNameList.add("七月");
		aliasNameList.add("八月");
		aliasNameList.add("九月");
		aliasNameList.add("十月");
		aliasNameList.add("十一月");
		aliasNameList.add("十二月");
		
		List list = new ArrayList();
				
		 LinkedHashMap map=new LinkedHashMap();
		 map.put("CELL0","");
		 map.put("CELL1","2016");
		 list.add(map);
		
		/* String sqlDetailInfo = "SELECT E.EMPID,E.LOCAL_NAME,GET_DEPT_NAME(E.DEPTNO, '"+admin.getLanguage()+"') DEPTNAME "
		+ " FROM HR_EMPLOYEE E "
		
		+ " WHERE  E.STATUS_CODE <> 1375   "
		/*+ " AND EXISTS(                            "
				+ "            SELECT *                          "
				+ "            FROM AR_SUPERVISOR_INFO                  "
				+ "           WHERE AR_SUPERVISOR_INFO.DEPTNO = E.DEPTNO    "
				+ "             AND AR_SUPERVISOR_INFO.PERSON_ID = '"
				+ admin.getPersonId()
				+ "' "
				+ "          ) 				"
		+ " AND ROWNUM <= 2 ORDER BY E.DEPTNO,EMPID ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
		.getContentNoByFiled(sqlDetailmap);
		
		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			
			if (empInfo != null) {
				map.put("CELL0", empInfo.get("EMPID") == null ? "" : empInfo
						.get("EMPID").toString());
				map.put("CELL1", empInfo.get("LOCAL_NAME") == null ? ""
						: empInfo.get("LOCAL_NAME").toString());
				map.put("CELL2", empInfo.get("DEPTNAME") == null ? "" : empInfo
						.get("DEPTNAME").toString());
			} else {
				map.put("CELL0", "");
				map.put("CELL1", "");
				map.put("CELL2", "");
			}
			
			if (i == 0) {
				map.put("CELL3", "2012-01-01");
				map.put("CELL4", "09:00:00");
				map.put("CELL5", "IN");
			}
			
			if (i == 1) {
				
				map.put("CELL3", "2012-01-01");
				map.put("CELL4", "18:00:00");
				map.put("CELL5", "OUT");
			}
			
			list.add(map);
		} */
		
		// 开始设定sheet的列名以及数据
		String sqlItemName = "SELECT 'IN' CONTENT FROM DUAL "
			+ " UNION SELECT 'OUT' CONTENT FROM DUAL ";
		LinkedHashMap sqlmap = new LinkedHashMap();
		sqlmap.put("sqlContent", sqlItemName);
		List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",
				request));// 类型(请填写sheet1中的数据)
		
		String name = "viewEvaluateInfo";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
		.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, itemList, name);
	}

	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportArCardRecordExcel")
	public void exportArCardRecordExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String condition = request.getParameter("condition") == null ? ""
				: request.getParameter("condition");
		String deptNO = request.getParameter("deptNO") == null ? "" : request
				.getParameter("deptNO");
		String STIME = request.getParameter("STIME") == null ? "" : request
				.getParameter("STIME");
		String RTIME = request.getParameter("RTIME") == null ? "" : request
				.getParameter("RTIME");
		String DoorType = request.getParameter("DoorType") == null ? ""
				: request.getParameter("DoorType");
		String RecordSource = request.getParameter("RecordSource") == null ? ""
				: request.getParameter("RecordSource");

		String language = admin.getLanguage();
		String cpnyId = admin.getCpnyId();
		String supervisor = admin.getAdminID();
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		
		
		// String filed1Name=request.getParameter("FIELD1_NAME");
		// String filed2Name=request.getParameter("FIELD2_NAME");
		// aliasNameList.add(filed1Name);
		// if(filed2Name!=null){
		// aliasNameList.add(filed2Name);
		// }工号 姓名 考勤的开始时间 考勤的结束时间 当天日期 当月 值 明细项目
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",
				request));// 工号
		aliasNameList.add(TipMessage
				.getTipMessage("public.title.name", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",
				request));// 部门

		aliasNameList.add( "日期");// 时间
		aliasNameList.add("时间");// 时间
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewarcardrecord.title.leixing", request));// 类型
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewarcardrecord.title.shujulaiyuan", request));// 数据来源
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewarcardrecord.title.beizhu", request));// 备注

		aliasList.add("EMPID");
		aliasList.add("LOCAL_NAME");
		aliasList.add("DEPTNAME");
	//	aliasList.add("R_TIME");
		aliasList.add("R_TIME_DATE");
		aliasList.add("R_TIME_TIME");
		aliasList.add("DOOR_TYPE");
		aliasList.add("INSERT_BY");
		aliasList.add("REMARK");

		String sqlContent = " SELECT  A.RECORD_NO,	" + " A.CARD_NO, 			"
				+ " TO_CHAR(A.R_TIME,'YYYY-MM-DD HH24:MI:SS') R_TIME,	"
				+ " TO_CHAR(A.R_TIME,'YYYY-MM-DD') R_TIME_DATE,	"
				+ " TO_CHAR(A.R_TIME,'HH24:MI:SS') R_TIME_TIME,	"
				+ " TO_CHAR(A.R_TIME,'YYYY-MM-DD') R_DATE,			"
				+ " TO_CHAR(A.R_TIME,'HH24') R_HOUR,					"
				+ " TO_CHAR(A.R_TIME,'MI') R_MINITE,					"
				+ " TO_CHAR(A.INSERT_TIME,'YYYY-MM-DD HH24:MI') INSERT_TIME,	"
				+ " A.DOOR_TYPE,								" + " A.REMARK,									"
				+ " B.EMPID,									" + " B.LOCAL_NAME,								"
				+ " C.CONTENT		DEPTNAME,					" + " INSERT_BY									"
				+ " FROM AR_MAC_RECORDS_" + cpnyId + " A,		"
				+ " HR_EMPLOYEE B,							" + " HR_DEPARTMENT_NAME C						"
				+ " WHERE  A.PERSON_ID =  B.PERSON_ID			"
				+ " AND B.DEPTNO = C.DEPTNO(+)				" + " AND C.LANGUAGE(+) = '"
				+ language + "'	" + " AND EXISTS ( SELECT *								"
				+ " 		FROM AR_SUPERVISOR_INFO F					"
				+ " 		WHERE B.DEPTNO = F.DEPTNO					"
				+ " 		AND F.PERSON_ID = '" + supervisor + "'		" + " 	) ";
		if (!"".equals(condition)) {
			sqlContent = sqlContent + " AND (   B.EMPID LIKE '%' || '"
					+ condition + "' || '%' "
					+ " OR B.LOCAL_NAME LIKE '%' || '" + condition
					+ "' || '%' " + " ) ";
		}
		
		if (!"".equals(deptNO)) {
			sqlContent = sqlContent
					+ " AND EXISTS ( 		"
					+ " 		SELECT     *	"
					+ "             	FROM HR_DEPARTMENT B1							"
					+ "              WHERE B1.DEPTNO=B.DEPTNO				"
					+ "          	START WITH B1.DEPTNO = '"
					+ deptNO
					+ "'			"
					+ "          	CONNECT BY PRIOR B1.DEPTNO = B1.PARENT_DEPT_NO	"
					+ "         )													";
		}

		if (!"".equals(STIME)) {
			sqlContent = sqlContent
					+ " AND TO_DATE(TO_CHAR(A.R_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') >= TO_DATE('"
					+ STIME + "','YYYY-MM-DD') ";
		}

		if (!"".equals(RTIME)) {
			sqlContent = sqlContent
					+ " AND TO_DATE(TO_CHAR(A.R_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') <= TO_DATE('"
					+ RTIME + "','YYYY-MM-DD') ";
		}

		if (!"".equals(DoorType)) {
			sqlContent = sqlContent + " AND A.DOOR_TYPE = '" + DoorType + "' ";
		}

		if (!"".equals(RecordSource)) {
			sqlContent = sqlContent + " AND A.INSERT_BY = '" + RecordSource
					+ "' ";
		}

		sqlContent = sqlContent
				+ " ORDER BY TO_CHAR(A.R_TIME,'YYYY-MM-DD') DESC,B.EMPID,A.DOOR_TYPE ";

		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);

		map.put("sqlContent", sqlContent);
		
		String name = "viewArCardRecord_export";
		this.excelUtilSer.exportExcelByName(request, response, modelMap, map,
				aliasNameList, aliasList, name);
	}

	/**
	 * 下载导入模板-考勤明细维护导入模板 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportArShiftExcelModel")
	public void exportArShiftExcelModel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",request));// 工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",request));// 部门
		//aliasNameList.add(TipMessage.getTipMessage("ar.viewCompanyCalendar.title.banci", request));// 班次
		//aliasNameList.add(TipMessage.getTipMessage("ar.viewardetail.title.date", request));// 日期
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat datef = new SimpleDateFormat("yyyy/MM/dd");
		//默认选择当前月的最后一天
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		Date endTime = cal.getTime();
		String endTime1 = datef.format(endTime);
		int endDay = Integer.parseInt(endTime1.substring(8,10));
		//如果指定导入模版的月份的话
		String shiftYear = request.getParameter("shiftYear")!=null?request.getParameter("shiftYear").toString():"";
		String shiftMonth = request.getParameter("shiftMonth")!=null?request.getParameter("shiftMonth").toString():"";
		String monthStr = shiftYear + shiftMonth;
		LinkedHashMap monthMap = (LinkedHashMap)arMonthSer.getLastDayByMonth(monthStr);
		if(monthMap!=null && monthMap.get("DAY_STR")!=null){
			endDay = Integer.parseInt(monthMap.get("DAY_STR").toString());
		}
		
		for (int i=1;i<=endDay ;i++){
			if(i<10){
				aliasNameList.add(shiftYear+"/"+shiftMonth+"/"+ "0" + i);
			}else{
				aliasNameList.add(shiftYear+"/"+shiftMonth+"/"+ i);
			}
		}		
		Map paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("STAT_NO", request.getParameter("STAT_NO"));
		paramMap.put("shiftYear", shiftYear);
		paramMap.put("shiftMonth", shiftMonth);
		
		if( request.getParameter("group").equals("3")){
			paramMap.put("dynamicGroup", request.getParameter("dynamicGroup"));
		}
		List personalList = new ArrayList();
		List list = new ArrayList();
		personalList = this.excelUtilSer.exportPersonalList(paramMap);
		for (int k = 0; k < personalList.size(); k++) {
			LinkedHashMap getValueMap = (LinkedHashMap) personalList.get(k);
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", getValueMap.get("EMPID"));
			map.put("CELL1", getValueMap.get("LOCAL_NAME"));
			map.put("CELL2", getValueMap.get("DEPTNAME"));
			map.put("CELL3", "");
			map.put("CELL4", "");
			map.put("CELL5", "");
			map.put("CELL6", "");
			map.put("CELL7", "");
			map.put("CELL8", "");
			map.put("CELL9", "");
			map.put("CELL10", "");
			map.put("CELL11", "");
			map.put("CELL12", "");
			map.put("CELL13", "");
			map.put("CELL14", "");
			map.put("CELL15", "");
			map.put("CELL16", "");
			map.put("CELL17", "");
			map.put("CELL18", "");
			map.put("CELL19", "");
			map.put("CELL20", "");
			map.put("CELL21", "");
			map.put("CELL22", "");
			map.put("CELL23", "");
			map.put("CELL24", "");
			map.put("CELL25", "");
			map.put("CELL26", "");
			map.put("CELL27", "");
			map.put("CELL28", "");
			map.put("CELL29", "");
			map.put("CELL30", "");
			map.put("CELL31", "");
			map.put("CELL32", "");
			map.put("CELL33", "");
		
			map.put("CELL34", "Please input the shift ID");//输入班次ID
			
			list.add(map);
			
		}
		
		String name = "addEmpShiftView_module";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		//this.excelUtilSer.exportExcelByName(request, response, modelMap,sqlContentmap, aliasNameList, null,name);
		
		// 开始设定sheet的列名以及数据
		String sqlItemName = "SELECT SHIFT_ID, GET_GLOBAL_NAME(SHIFT_NO, 'vi') SHIFT_NAME " +
							" FROM AR_SHIFT010 " +
							" WHERE CPNY_ID = '"+admin.getCpnyId()+"' " +
							"   AND ACTIVITY = 1 " +
							" ORDER BY SHIFT_ID ";
		LinkedHashMap sqlmap = new LinkedHashMap();
		sqlmap.put("sqlContent", sqlItemName);
		List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",request));// 类型(请填写sheet1中的数据)
		
		this.excelUtilSer.exportExcelOneByOneSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, itemList, name);
	}

	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportArShiftExcel")
	public void exportArShiftExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String FROM_DATE = request.getParameter("FROM_DATE") == null ? ""
				: request.getParameter("FROM_DATE");
		String TO_DATE = request.getParameter("TO_DATE") == null ? "" : request
				.getParameter("TO_DATE");

		String language = admin.getLanguage();
		String cpnyId = admin.getCpnyId();
		String supervisor = admin.getAdminID();
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		// String filed1Name=request.getParameter("FIELD1_NAME");
		// String filed2Name=request.getParameter("FIELD2_NAME");
		// aliasNameList.add(filed1Name);
		// if(filed2Name!=null){
		// aliasNameList.add(filed2Name);
		// }工号 姓名 考勤的开始时间 考勤的结束时间 当天日期 当月 值 明细项目
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",
				request));// 工号
		aliasNameList.add(TipMessage
				.getTipMessage("public.title.name", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",
				request));// 部门
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewCompanyCalendar.title.banci", request));// 班次
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewardetail.title.date", request));// 日期

		aliasNameList.add(TipMessage.getTipMessage(
				"ar.excelexport.title.createempid", request));// 创建人工号
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.excelexport.title.createname", request));// 创建人姓名
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.excelexport.title.createdate", request));// 创建时间

		aliasList.add("EMPID");
		aliasList.add("LOCAL_NAME");
		aliasList.add("DEPTNAME");
		aliasList.add("SHIFT_NAME");
		aliasList.add("AR_DATE_STR");
		aliasList.add("R_EMPID");
		aliasList.add("R_NAME");
		aliasList.add("CREATE_DATE");

		String sqlContent = " SELECT HR_EMPLOYEE.EMPID,	"
				+ " HR_EMPLOYEE.LOCAL_NAME,	"
				+ " GET_DEPT_NAME(HE.DEPTNO,'" + language + "') DEPTNAME,	"
				+ " HE.LOCAL_NAME R_NAME,	"
				+ " HE.EMPID R_EMPID,	"
				+ " AR.AR_DATE_STR,	"
				+ " SY1.CONTENT SHIFT_NAME,	"
				+ " TO_CHAR(AR.CREATE_DATE, 'DD/MM/YYYY') CREATE_DATE	"
				+ " FROM AR_SCHEDULE_"
				+ cpnyId
				+ " AR, HR_EMPLOYEE, HR_EMPLOYEE HE, SY_GLOBAL_NAME SY1	"
				+ " WHERE AR.PERSON_ID = HR_EMPLOYEE.PERSON_ID	"
				+ " AND AR.CREATED_BY = HE.PERSON_ID(+)	"
				+ " AND AR.SHIFT_NO = SY1.NO(+) " + " AND SY1.LANGUAGE(+) = '"
				+ language + "'	" + " AND EXISTS ( SELECT *								"
				+ " 		FROM AR_SUPERVISOR_INFO F					"
				+ " 		WHERE HR_EMPLOYEE.DEPTNO = F.DEPTNO					"
				+ " 		AND F.PERSON_ID = '" + supervisor + "'		" + " 	) ";
		if (!"".equals(FROM_DATE)) {
			sqlContent = sqlContent
					+ " AND TO_DATE(AR.AR_DATE_STR,'YYYY-MM-DD') >= TO_DATE('"
					+ FROM_DATE + "','DD-MM-YYYY')";
		}

		if (!"".equals(TO_DATE)) {
			sqlContent = sqlContent
					+ " AND TO_DATE(AR.AR_DATE_STR,'YYYY-MM-DD') <= TO_DATE('"
					+ TO_DATE + "','DD-MM-YYYY')";
		}

		sqlContent = sqlContent
				+ " ORDER BY HR_EMPLOYEE.DEPTNO,HR_EMPLOYEE.EMPID ";

		String name = "addEmpShiftView_export";
		
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);

		map.put("sqlContent", sqlContent);
		this.excelUtilSer.exportExcelByName(request, response, modelMap, map,
				aliasNameList, aliasList,name);
	}
	
	/**
	 * 下载导入模板-刷卡导入模板 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportArCardRecordExcelModeltwo")
	public void exportArCardRecordExcelModeltwo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();

		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",
				request));// 工号
		aliasNameList.add(TipMessage
				.getTipMessage("public.title.name", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",
				request));// 部门
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.title.date", request));// 日期
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewArCardRecord.title.shijian1", request));// 时间1(上班)
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewArCardRecord.title.shijian2", request));// 时间2(下班)
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewArCardRecord.title.shijian3", request));// 时间3(上班)
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewArCardRecord.title.shijian4", request));// 时间4(下班)

		List list = new ArrayList();

		String sqlDetailInfo = "SELECT E.EMPID,E.LOCAL_NAME, HD.CONTENT DEPTNAME "
				+ " FROM HR_EMPLOYEE E, HR_DEPARTMENT_NAME HD "
				+ " WHERE E.DEPTNO = HD.DEPTNO(+) "
				+ " AND HD.LANGUAGE(+) = '"
				+ admin.getLanguage()
				+ "' "
				+ " AND E.STATUS_CODE <> 1375   "
				+ " AND EXISTS(                            "
				+ "            SELECT *                          "
				+ "            FROM AR_SUPERVISOR_INFO                  "
				+ "           WHERE AR_SUPERVISOR_INFO.DEPTNO = E.DEPTNO    "
				+ "             AND AR_SUPERVISOR_INFO.PERSON_ID = '"
				+ admin.getPersonId()
				+ "' "
				+ "          ) 				"
				+ " ORDER BY HD.DEPTNO,EMPID ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);

			if (empInfo != null) {
				map.put("CELL0", empInfo.get("EMPID") == null ? "" : empInfo
						.get("EMPID").toString());
				map.put("CELL1", empInfo.get("LOCAL_NAME") == null ? ""
						: empInfo.get("LOCAL_NAME").toString());
				map.put("CELL2", empInfo.get("DEPTNAME") == null ? "" : empInfo
						.get("DEPTNAME").toString());
			} else {
				map.put("CELL0", "");
				map.put("CELL1", "");
				map.put("CELL2", "");
			}

			if (i == 0) {
				map.put("CELL3", "2012-01-01");
				map.put("CELL4", "08:00:00");
				map.put("CELL5", "11:00:00");
				map.put("CELL6", "14:00:00");
				map.put("CELL7", "19:00:00");
			}
			list.add(map);
		}

//		List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
//		itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",
//				request));// 类型(请填写sheet1中的数据)

		String name = "viewArCardRecord_module2";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null,name);
	}
	
	/**
	 * 下载导入模板-刷卡导入模板 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportArCardRecordExcelModelthree")
	public void exportArCardRecordExcelModelthree(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();

		aliasNameList.add(TipMessage.getTipMessage("hr.viewCondSql.title.KAHAO",
				request));// 卡号
		aliasNameList.add(TipMessage
				.getTipMessage("public.title.name", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",
				request));// 部门
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewarcardrecord.title.shijian", request));// 时间
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.excelexport.title.leixing", request));// 类型(请填写sheet2中的数据)
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewarcardrecord.title.beizhu", request));// 备注

		List list = new ArrayList();

		String sqlDetailInfo = "SELECT E.EMPID,E.LOCAL_NAME, HD.CONTENT DEPTNAME, " 
				+ " CASE WHEN "
				+ " 		SYSDATE >= NVL(DATE_START,TO_DATE('1900-12-31','YYYY-MM-DD')) "  
				+ " 		AND SYSDATE <= NVL(DATE_END,TO_DATE('9999-12-31','YYYY-MM-DD')) "
				+ " 	THEN AR.CARD_NO "
				+ " 	ELSE "
				+ " 		'' "
				+ " END CARD_NO "
				+ " FROM HR_EMPLOYEE E, HR_DEPARTMENT_NAME HD, AR_CARD_ASSOCIATE AR "
				+ " WHERE E.PERSON_ID = AR.PERSON_ID(+) " 
				+ " AND E.DEPTNO = HD.DEPTNO(+) "
				+ " AND HD.LANGUAGE(+) = '"
				+ admin.getLanguage()
				+ "' "
				+ " AND E.STATUS_CODE <> '1375'   "
				+ " AND EXISTS(                            "
				+ "            SELECT *                          "
				+ "            FROM AR_SUPERVISOR_INFO                  "
				+ "           WHERE AR_SUPERVISOR_INFO.DEPTNO = E.DEPTNO    "
				+ "             AND AR_SUPERVISOR_INFO.PERSON_ID = '"
				+ admin.getPersonId()
				+ "' "
				+ "          ) 				"
				+ " AND E.CPNY_ID = '"+admin.getCpnyId()+"' "
				+ " ORDER BY HD.DEPTNO,EMPID ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);

			if (empInfo != null) {
				map.put("CELL0", empInfo.get("CARD_NO") == null ? "" : empInfo
						.get("CARD_NO").toString());
				map.put("CELL1", empInfo.get("LOCAL_NAME") == null ? ""
						: empInfo.get("LOCAL_NAME").toString());
				map.put("CELL2", empInfo.get("DEPTNAME") == null ? "" : empInfo
						.get("DEPTNAME").toString());
			} else {
				map.put("CELL0", "");
				map.put("CELL1", "");
				map.put("CELL2", "");
			}

			if (i == 0) {
				map.put("CELL3", "2012-01-01 09:00");
				map.put("CELL4", "IN");
			}

			if (i == 1) {
				map.put("CELL3", "2012-01-01 18:00");
				map.put("CELL4", "OUT");
			}

			list.add(map);
		}

		// 开始设定sheet的列名以及数据
		String sqlItemName = "SELECT 'IN' CONTENT FROM DUAL "
				+ " UNION SELECT 'OUT' CONTENT FROM DUAL ";
		LinkedHashMap sqlmap = new LinkedHashMap();
		sqlmap.put("sqlContent", sqlItemName);
		List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",
				request));// 类型(请填写sheet1中的数据)

		String name = "viewArCardRecord_module3";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, itemList, name);
	}
	
	
	/**
	 * 年终奖预提Excel导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportNianZhongJiangYuTiInfoList")
	public void exportNianZhongJiangYuTiInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage
				.getTipMessage("pa.salary.canShu.nianDu", request));// 年度
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.jiTiBiLv",
				request)+"%");// 计提比率
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zhiFuYueFen", request));// 支付月份
		List list = new ArrayList();
		//年度
		modelMap.put("niandu", request.getParameter("seach_niandu"));
		//法人
		modelMap.put("faren", request.getParameter("seach_faren"));
		String sqlDetailInfo = "select p.year_flag ND,  "
							      + "  p.cpny_id FR,  "
							      + "  p.pay_rat JTBL,  "
							      + "  p.payment_date ZFYF "
							      + " from PA_ANNUAL_BONUS_PARAM p "
							      + " where 1 = 1 " ;
						if(null != modelMap.get("niandu")  && !"".equals(modelMap.get("niandu")) ){
							sqlDetailInfo = sqlDetailInfo + "  and p.year_flag = '" +  (String)modelMap.get("niandu")+"'";
						}
						if(null != modelMap.get("faren")  && !"".equals(modelMap.get("faren")) ){
							sqlDetailInfo = sqlDetailInfo + "  and p.cpny_id = '" +  (String)modelMap.get("faren")+"'";
						}
						sqlDetailInfo = sqlDetailInfo + " order by p.pay_seq asc ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);
		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

		// 开始设定sheet的列名以及数据
//		String sqlItemName = "SELECT 'IN' CONTENT FROM DUAL "
//				+ " UNION SELECT 'OUT' CONTENT FROM DUAL ";
//		LinkedHashMap sqlmap = new LinkedHashMap();
//		sqlmap.put("sqlContent", sqlItemName);
		List itemList = null;
//		itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",
//				request));// 类型(请填写sheet1中的数据)

		String name = "viewNianZhongJiangYuTiInfoModule";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, itemList, name);
	}
	
	/**
	 * 年终奖预提Excel导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportNianZhongJiangYuTiJiSuanInfoList")
	public void exportNianZhongJiangYuTiJiSuanInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zhiFuYueFen", request));// 支付月份
		aliasNameList.add(TipMessage.getTipMessage(
				"public.title.deptName", request));// 部门
		aliasNameList.add(TipMessage.getTipMessage(
				"alert.pa.pasalarycanshu.shehao", request));// 社号
		aliasNameList.add(TipMessage.getTipMessage(
				"alert.pa.pasalarycanshu.xingming", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage(
				"alert.pa.pasalarycanshu.jisuanjishu", request));// 计算基数
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.jiTiBiLv", request)+"%");// 计提比率
		aliasNameList.add(TipMessage.getTipMessage(
				"alert.pa.pasalarycanshu.jitijine", request));// 计提金额
		List list = new ArrayList();
		//法人
		modelMap.put("faren", request.getParameter("seach_faren"));
		//支付年
		modelMap.put("zhifunian", request.getParameter("seach_zhifunian"));
		//支付月
		modelMap.put("zhifuyue", request.getParameter("seach_zhifuyue"));
		//部门
		modelMap.put("bumen", request.getParameter("seach_bumen"));
		//社号/姓名
		modelMap.put("shehaoxingming", request.getParameter("seach_shehaoxingming"));
		
		String sqlDetailInfo = "select p.cpny_id        FR, "
					        + " p.PAYMENT_DATE   ZFYF, "
					        + " d.org_name_local BM, "
					        + " e.empid          SH, "
					        + " e.local_name     XM, "
					        + " p.BASE_SALARY    JSJS, "
					        + " p.pay_rat        JTBL, "
					        + " p.BONUS_PAY      JTJE "
					        + " from PA_ANNUAL_BONUS p, hr_department d, hr_employee e "
					        + " where p.person_id = e.person_id "
					        + " and d.deptno = e.deptno " ;
		
						if(null != modelMap.get("faren")  && !"".equals(modelMap.get("faren")) ){
							sqlDetailInfo = sqlDetailInfo + "  and p.cpny_id = '" +  ((String)modelMap.get("faren")).trim() + "'";
						}
						
						if(null != modelMap.get("zhifunian")  && !"".equals(modelMap.get("zhifunian")) && null != modelMap.get("zhifuyue")  && !"".equals(modelMap.get("zhifuyue")) ){
							sqlDetailInfo = sqlDetailInfo + "  and p.PAYMENT_DATE = '" +  ((String)modelMap.get("zhifunian")).trim()+"' || '"  +  ((String)modelMap.get("zhifuyue")).trim() + "'";
						}
						
						if(null != modelMap.get("bumen")  && !"".equals(modelMap.get("bumen")) ){
							sqlDetailInfo = sqlDetailInfo + "  and d.org_name_local like '%" +  ((String)modelMap.get("bumen")).trim()+"%'";
						}
						
						if(null != modelMap.get("shehaoxingming")  && !"".equals(modelMap.get("shehaoxingming")) ){
							sqlDetailInfo = sqlDetailInfo + "  and (e.local_name like '%" +  ((String)modelMap.get("shehaoxingming")).trim()+"%' or e.empid like '%" +  ((String)modelMap.get("shehaoxingming")).trim()+"%' )";
						}
						
						sqlDetailInfo = sqlDetailInfo + " order by p.PAYMENT_DATE desc ";
						
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);
		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

		// 开始设定sheet的列名以及数据
//		String sqlItemName = "SELECT 'IN' CONTENT FROM DUAL "
//				+ " UNION SELECT 'OUT' CONTENT FROM DUAL ";
//		LinkedHashMap sqlmap = new LinkedHashMap();
//		sqlmap.put("sqlContent", sqlItemName);
//		List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		List itemList = null;
//		itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",
//				request));// 类型(请填写sheet1中的数据)

		String name = "viewNianZhongJiangYuTiJiSuanInfoModule";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, itemList, name);
	}
	
	/**
	 * 正规职预提Excel导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportZhengGuiZhiJiSuanInfoList")
	public void exportZhengGuiZhiJiSuanInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.qici", request));// 期次
		aliasNameList.add(TipMessage.getTipMessage(
				"public.title.deptName", request));// 部门
		aliasNameList.add(TipMessage.getTipMessage(
				"alert.pa.pasalarycanshu.shehao", request));// 社号
		aliasNameList.add(TipMessage.getTipMessage(
				"alert.pa.pasalarycanshu.xingming", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage(
				"alert.pa.pasalarycanshu.yutijisuan", request));//  预提工资
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.accdivcode", request));//ACC_DIV_CODE
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.renyuanleixing", request));// 人员类型
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.chuansongzhuangtai", request));// 人员类型
		List list = new ArrayList();
		//法人
		modelMap.put("faren", request.getParameter("seach_faren"));
		//支付年
		modelMap.put("zhifunian", request.getParameter("seach_zhifunian"));
		//支付月
		modelMap.put("zhifuyue", request.getParameter("seach_zhifuyue"));
		//部门
		modelMap.put("bumen", request.getParameter("seach_bumen"));
		//社号/姓名
		modelMap.put("shehaoxingming", request.getParameter("seach_shehaoxingming"));
		
		String sqlDetailInfo = "select t.legal_entity_name CPNY_ID,"+
							   "    t.period_name PERIOD_NAME,"+
							 
							   "    d.org_name_local BM,"+
							   "   t.employee_no EMPNO,"+
							    "    e.local_name LOCAL_NAME,"+
							  
							     "  t.salary_amount SALARY_AMOUNT, "+
							     "  nvl(t.attribute14,0) ACC_DIV_CODE, "+
							    
							     "  g.content LEIXING,"+
							     "   t.transfer_flag TRANSFER_FLAG "+
							"  from XXACF_CHRS_SALARY_S_IF t,hr_employee e,hr_department d, HR_JOB_TYPE_SETUP ht, sy_global_name g "+
							" where  t.legal_entity_name=e.cpny_id"+
							 
						 
							" and e.deptno=d.deptno  "+
							"and  e.emp_type_code = ht.jobtype_no "+
			               "  and e.cpny_id = ht.cpny_id "+
			               "  and ht.jobtype_no = g.no "+
			                " and g.language='zh' "+
							" and e.empid=t.employee_no " ;
		
						if(null != modelMap.get("faren")  && !"".equals(modelMap.get("faren")) ){
							sqlDetailInfo = sqlDetailInfo + "  and e.cpny_id = '" +  ((String)modelMap.get("faren")).trim() + "'";
						}
						
						if(null != modelMap.get("zhifunian")  && !"".equals(modelMap.get("zhifunian")) && null != modelMap.get("zhifuyue")  && !"".equals(modelMap.get("zhifuyue")) ){
							sqlDetailInfo = sqlDetailInfo + "  and t.period_name = '" +  ((String)modelMap.get("zhifunian")).trim()+"'||'-'||'"  +  ((String)modelMap.get("zhifuyue")).trim() + "'";
						}
						
						if(null != modelMap.get("bumen")  && !"".equals(modelMap.get("bumen")) ){
							sqlDetailInfo = sqlDetailInfo + "  and d.org_name_local like '%" +  ((String)modelMap.get("bumen")).trim()+"%'";
						}
						
						if(null != modelMap.get("shehaoxingming")  && !"".equals(modelMap.get("shehaoxingming")) ){
							sqlDetailInfo = sqlDetailInfo + "  and (e.local_name like '%" +  ((String)modelMap.get("shehaoxingming")).trim()+"%' or e.empid like '%" +  ((String)modelMap.get("shehaoxingming")).trim()+"%' )";
						}
						
						sqlDetailInfo = sqlDetailInfo + " order by t.period_name desc ";
						
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);
		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

		// 开始设定sheet的列名以及数据
		String sqlItemName = "SELECT 'IN' CONTENT FROM DUAL "
				+ " UNION SELECT 'OUT' CONTENT FROM DUAL ";
		LinkedHashMap sqlmap = new LinkedHashMap();
		sqlmap.put("sqlContent", sqlItemName);
		List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",
				request));// 类型(请填写sheet1中的数据)

		String name = "viewyutilistxml";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, itemList, name);
	}
	
	/**
	 * 派遣地Excel导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaiQianDiGuanLiInfoList")
	public void exportPaiQianDiGuanLiInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",request));// 法人
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.chengShiDengJi", request));// 城市等级
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.shengFen",request));// 省份
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.chengShiMingCheng", request));// 城市名称
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.diQuMingCheng", request));// 地区名称
		aliasNameList.add("启用状态");//启用状态

		List list = new ArrayList();
		//法人
		modelMap.put("faren", request.getParameter("seach_faren"));
		//城市等级
		modelMap.put("csdj", request.getParameter("seach_chengshidengji"));
		//地区名称
		modelMap.put("dqmc", request.getParameter("seach_diqumingcheng"));
		
		String sqlDetailInfo= " select s.pqd_faren                           fr,      "
					        + "        g.content                             csdj,    "
					        + "        m.state_nm                            sf,      "
					        + "        m.city_nm                             csmc,    "
					        + "        m.region_nm                           dqmc,    "
					        + "        decode(s.activity,'1','启用','未启用') activity "
					        + "   from sy_dispatch s, INC_CITY_TO_CITYLEVEL_MAPP_DIS m, sy_global_name g "
					        + "  where s.pqd_chengshidengji = g.no         "
					        + "    and g.language = 'zh'                   "
					        + "    and s.pqd_shengfen = m.state_cd         "
					        + "    and s.pqd_chengshimingcheng = m.city_cd "
					        + "    and s.pqd_diqumingcheng = m.region_cd   ";

		//String sqlDetailInfo = "select s.pqd_faren fr , " 
		//+ " get_global_name(s.pqd_chengshidengji, '"+(String)paramMap.get("interLanguage")+"') csdj, " 
		//+ " get_global_name(s.pqd_shengfen, '"+(String)paramMap.get("interLanguage")+"') sf, " 
		//+ " get_global_name(s.pqd_chengshimingcheng, '"+(String)paramMap.get("interLanguage")+"') csmc, " 
		//+ " get_global_name(s.pqd_diqumingcheng, '"+(String)paramMap.get("interLanguage")+"') dqmc " 
		//+ "  from sy_dispatch s " 
		//+ "  where 1 = 1 " ;
		//if(null != modelMap.get("faren")  && !"".equals(modelMap.get("faren")) ){
			//sqlDetailInfo = sqlDetailInfo + "  and s.pqd_faren = '" +  (String)modelMap.get("faren")+"'";
		//}
		//				
		//if(null != modelMap.get("csdj")  && !"".equals(modelMap.get("csdj")) ){
			//sqlDetailInfo = sqlDetailInfo  + "  and get_global_name(s.pqd_chengshidengji, '"+(String)paramMap.get("interLanguage")+"') = '" +  (String)modelMap.get("csdj")+"'" ;
		//}
		//if(null != modelMap.get("dqmc") && !"".equals(modelMap.get("dqmc"))){
			//sqlDetailInfo = sqlDetailInfo 
			//+ "  and get_global_name(s.pqd_diqumingcheng, '"+(String)paramMap.get("interLanguage")+"') "
			//+ "	like '%"+(String)modelMap.get("dqmc")+"%'";
		//}
		//sqlDetailInfo = sqlDetailInfo + " order by s.pqd_no asc ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer.getContentNoByFiled(sqlDetailmap);
		
		if(itemDetailList.size() == 0){
			sqlDetailInfo = "select 'NONE' fr , 'NONE' csdj, 'NONE' sf, 'NONE' csmc, 'NONE' dqmc,'未启用' activity from dual";
			sqlDetailmap.remove("sqlContent");
			sqlDetailmap.put("sqlContent", sqlDetailInfo);
			
			itemDetailList = this.excelUtilSer.getContentNoByFiled(sqlDetailmap);
		}

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

		// 开始设定sheet的列名以及数据
		//String sqlItemName = "SELECT 'IN' CONTENT FROM DUAL " + " UNION SELECT 'OUT' CONTENT FROM DUAL ";
		//LinkedHashMap sqlmap = new LinkedHashMap();
		//sqlmap.put("sqlContent", sqlItemName);
		//List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		//itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",request));// 类型(请填写sheet1中的数据)

		String name = "viewPaiQianDiInfoExcel";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,sqlContentmap, aliasNameList, null, null, name);
	}
	
	/**
	 * 最低工资标准非促销员Excel导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempInfoList")
	public void exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//RESULT_FLAG出错与否
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.niandu",
				request));// 年度
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.fulidiqu",
				request));// 福利地区
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zuidigongzi", request));// 最低工资
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.shepinggongzi", request));// 社平工资
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zuixiaojishu", request));// 最小基数
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zuidajishu", request));// 最大基数
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.qufen",
				request));// 区分
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.yanZhengJieGuo", request));// 验证结果

		List list = new ArrayList();

		String sqlDetailInfo = " select  p.pqd_faren         FR, " 
										+ " p.pqd_niandu   ND, " 
										+ " nvl(get_global_name(p.pqd_fulidiqu, '"+(String)paramMap.get("interLanguage")+"'), p.pqd_fulidiqu)       FLDQ, " 
										+ " p.pqd_zuidigongzi   ZDGZ, " 
										+ " p.pqd_shepinggongzi SPGZ, " 
										+ " p.pqd_zuixiaojishu  ZXJS, " 
										+ " p.pqd_zuidajishu    ZDJS, " 
										+ " decode(p.pqd_qufen, 'SQ', '应发', 'SH', '实得', p.pqd_qufen) QF, " 
										+ " nvl(p.pqd_update_yz_result, 'NONE')    YZJG " 
										+ " from pa_low_salary_unpromo_temp p " 
										+ " where 1 = 1 ";
		
		if (null != paramMap.get("RESULT_FLAG") && !"".equals(paramMap.get("RESULT_FLAG"))) {
			sqlDetailInfo = sqlDetailInfo + "  and p.pqd_yz_result = '"
					+ (String) paramMap.get("RESULT_FLAG") + "' or p.pqd_yz_result = 'R'" ;
		}
		
		sqlDetailInfo = sqlDetailInfo + " order by p.pqd_no asc ";
						
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);
		
		if(itemDetailList.size() == 0){
			sqlDetailInfo = "select 'NONE'  , 'NONE' , 'NONE' , 'NONE' , 'NONE' , 'NONE' , 'NONE' from dual";
			sqlDetailmap.remove("sqlContent");
			sqlDetailmap.put("sqlContent", sqlDetailInfo);
			
			itemDetailList = this.excelUtilSer
			.getContentNoByFiled(sqlDetailmap);
		}

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

		String name = "viewZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempInfo";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);//contentList
		
		this.excelUtilSer.exportExcelTwoSheetByNameZuiDiGongZiFei(request, response, modelMap,
				sqlContentmap, aliasNameList, null, null, name);
	}
	
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportZuiDiGongZiBiaoZhunCuXiaoYuanTempInfoList")
	public void exportZuiDiGongZiBiaoZhunCuXiaoYuanTempInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//RESULT_FLAG出错与否
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.niandu",
				request));// 年度
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.citycd",
				request));// CITY_CD
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.chengShiMingCheng", request));// 城市名称
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zuidigongzi", request));// 最低工资
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.qufen", request));// 区分
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.yanZhengJieGuo", request));// 验证结果

		List list = new ArrayList();

		String sqlDetailInfo = " select  p.pqd_niandu         ND, " 
										+ " nvl(get_global_name(p.pqd_city_cd, '"+(String)paramMap.get("interLanguage")+"'), p.pqd_city_cd)    CITYCD, " 
										+ " nvl(get_global_name(p.pqd_chengshimingcheng, '"+(String)paramMap.get("interLanguage")+"'), p.pqd_chengshimingcheng)       CSMC, " 
										+ " p.pqd_zuidigongzi    ZDGZ, " 
										+ " decode(p.pqd_qufen, 'SQ', '应发', 'SH', '实得', p.pqd_qufen) QF, " 
										+ " nvl(p.pqd_update_yz_result, 'NONE')    YZJG " 
										+ " from pa_low_salary_promo_temp p " 
										+ " where 1 = 1 ";
		
		if (null != paramMap.get("RESULT_FLAG") && !"".equals(paramMap.get("RESULT_FLAG"))) {
			sqlDetailInfo = sqlDetailInfo + "  and p.pqd_yz_result = '"
					+ (String) paramMap.get("RESULT_FLAG") + "' or p.pqd_yz_result = 'R'" ;
		}
		
		sqlDetailInfo = sqlDetailInfo + " order by p.pqd_no asc ";
						
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);
		
		if(itemDetailList.size() == 0){
			sqlDetailInfo = "select 'NONE'  , 'NONE' , 'NONE' , 'NONE' , 'NONE' , 'NONE' from dual";
			sqlDetailmap.remove("sqlContent");
			sqlDetailmap.put("sqlContent", sqlDetailInfo);
			
			itemDetailList = this.excelUtilSer
			.getContentNoByFiled(sqlDetailmap);
		}

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

		String name = "viewZuiDiGongZiBiaoZhunCuXiaoYuanTempInfo";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);//contentList
		
		this.excelUtilSer.exportExcelTwoSheetByNameZuiDiGongZiFei(request, response, modelMap,
				sqlContentmap, aliasNameList, null, null, name);
	}	
	
	/**
	 * 最低工资标准非促销员Excel导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfoList")
	public void exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String faren = admin.getCpnyId();
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.niandu",
				request));// 年度
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.fulidiqu",
				request));// 福利地区
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zuidigongzi", request));// 最低工资
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.shepinggongzi", request));// 社平工资
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zuixiaojishu", request));// 最小基数
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zuidajishu", request));// 最大基数
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.qufen",
				request));// 区分

		List list = new ArrayList();

		String sqlDetailInfo = " select  p.pqd_faren         FR, " 
										+ " p.pqd_niandu   ND, " 
										+ " get_global_name(p.pqd_fulidiqu, '"+(String)paramMap.get("interLanguage")+"')      FLDQ, " 
										+ " p.pqd_zuidigongzi   ZDGZ, " 
										+ " p.pqd_shepinggongzi SPGZ, " 
										+ " p.pqd_zuixiaojishu  ZXJS, " 
										+ " p.pqd_zuidajishu    ZDJS, " 
										+ " decode(p.pqd_qufen, 'SQ', '应发', 'SH', '实得', p.pqd_qufen) QF " 
										+ " from pa_low_salary_unpromo p " 
										+ " where 1 = 1 ";
		
		if (null == paramMap.get("faren") || "".equals(paramMap.get("faren"))) {
			sqlDetailInfo = sqlDetailInfo + "  and p.pqd_faren = '" + faren
					+ "'";
		}

		if (null != paramMap.get("faren") && !"".equals(paramMap.get("faren"))) {
			sqlDetailInfo = sqlDetailInfo + "  and p.pqd_faren = '"
					+ (String) paramMap.get("faren") + "'";
		}
		
		if (null != paramMap.get("niandu") && !"".equals(paramMap.get("niandu"))) {
			sqlDetailInfo = sqlDetailInfo + "  and p.pqd_niandu = '"
					+ (String) paramMap.get("niandu") + "'";
		}
		
		sqlDetailInfo = sqlDetailInfo + " order by p.pqd_no asc ";
						
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);
		
		if(itemDetailList.size() == 0){
			sqlDetailInfo = "select 'NONE'  , 'NONE' , 'NONE' , 'NONE' , 'NONE' , 'NONE' from dual";
			sqlDetailmap.remove("sqlContent");
			sqlDetailmap.put("sqlContent", sqlDetailInfo);
			
			itemDetailList = this.excelUtilSer
			.getContentNoByFiled(sqlDetailmap);
		}

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

		String name = "viewZuiDiGongZiBiaoZhunFeiCuXiaoYuanModule";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		
		this.excelUtilSer.exportExcelTwoSheetByNameZuiDiGongZiFei(request, response, modelMap,
				sqlContentmap, aliasNameList, null, null, name);
	}
	
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportZuiDiGongZiBiaoZhunCuXiaoYuanInfoList")
	public void exportZuiDiGongZiBiaoZhunCuXiaoYuanInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//niandu csmc
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.niandu",
				request));// 年度
		aliasNameList.add("省份");// CITY_CD
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.chengShiMingCheng", request));// 城市名称
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.zuidigongzi", request));// 最低工资
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.qufen", request));// 区分

		List list = new ArrayList();
		
		String sqlDetailInfo =  " select  p.pqd_niandu         ND, " + 
								" nvl(GET_STATE_CITY_REGION_NAME(p.pqd_city_cd , 'STATE'), 'NONE')    CITYCD,  " + 
								" nvl(GET_STATE_CITY_REGION_NAME(p.pqd_chengshimingcheng , 'CITY'), 'NONE')    CSMC, " + 
								" p.pqd_zuidigongzi   ZDGZ,  " + 
								" decode(p.pqd_qufen, 'SQ', '应发', 'SH', '实得'， 'NONE')   QF" + 
								" from pa_low_salary_promo p  " + 
								" where 1 = 1 ";
		
		if (null != paramMap.get("niandu") && !"".equals(paramMap.get("niandu"))) {
			sqlDetailInfo = sqlDetailInfo + "  and p.pqd_niandu = '"
					+ (String) paramMap.get("niandu") + "'";
		}
		
		if (null != paramMap.get("csmc") && !"".equals(paramMap.get("csmc"))) {
			sqlDetailInfo = sqlDetailInfo + "  and p.pqd_chengshimingcheng = '"
					+ (String) paramMap.get("csmc") + "'";
		}
		
		sqlDetailInfo = sqlDetailInfo + " order by p.pqd_no asc ";
						
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);
		
		if(itemDetailList.size() == 0){
			sqlDetailInfo = "select 'NONE'  , 'NONE' , 'NONE' , 'NONE' , 'NONE' from dual";
			sqlDetailmap.remove("sqlContent");
			sqlDetailmap.put("sqlContent", sqlDetailInfo);
			
			itemDetailList = this.excelUtilSer
			.getContentNoByFiled(sqlDetailmap);
		}

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

		String name = "viewZuiDiGongZiBiaoZhunCuXiaoYuanModule";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		
		this.excelUtilSer.exportExcelTwoSheetByNameZuiDiGongZiFei(request, response, modelMap,
				sqlContentmap, aliasNameList, null, null, name);
	}
	
	
	/**
	 * 派遣地Excel导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaiQianDiGuanLiTempInfoList")
	public void exportPaiQianDiGuanLiTempInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//RESULT_FLAG  E 或者 N
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",request));// 法人
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.chengShiDengJi", request));// 城市等级
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.shengFen",request));// 省份
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.chengShiMingCheng", request));// 城市名称
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.diQuMingCheng", request));// 地区名称
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.yanZhengJieGuo", request));//验证结果

		List list = new ArrayList();
		String sqlDetailInfo = " SELECT TT.PQD_FAREN, "
							        +"  TT.PQD_CHENGSHIDENGJI, "
							        +"  TT.PQD_SHENGFEN, "
							        +"  TT.PQD_CHENGSHIMINGCHENG, "
							        +"  TT.PQD_DIQUMINGCHENG, "
							        +"  TT.ACTIVITY, "
							        +"  TT.PQD_DAORU_RESULT, "
							        +"  TT.PQD_001 CHECK_RESULT "
							    +" FROM SY_DISPATCH_TEMP TT "
							   +" WHERE TT.CREATED_BY = '" + admin.getPersonId() + "'"
							+" ORDER BY TT.PQD_NO ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer.getContentNoByFiled(sqlDetailmap);
		
		if(itemDetailList.size() == 0){
			sqlDetailInfo = "select 'NONE' PQD_FAREN , 'NONE' PQD_CHENGSHIDENGJI, 'NONE' PQD_SHENGFEN, 'NONE' PQD_CHENGSHIMINGCHENG, 'NONE'  from dual";
			sqlDetailmap.remove("sqlContent");
			sqlDetailmap.put("sqlContent", sqlDetailInfo);
			
			itemDetailList = this.excelUtilSer.getContentNoByFiled(sqlDetailmap);
		}
		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}
		// 开始设定sheet的列名以及数据
		//String sqlItemName = "SELECT 'IN' CONTENT FROM DUAL " + " UNION SELECT 'OUT' CONTENT FROM DUAL ";
		//LinkedHashMap sqlmap = new LinkedHashMap();
		//sqlmap.put("sqlContent", sqlItemName);
		//List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		//itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",request));// 类型(请填写sheet1中的数据)
		String name = "viewPaiQianDiInfoDaoChuModule";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,sqlContentmap, aliasNameList, null, null, name);
	}
	
	/**
	 * 派遣津贴标准导入结果--Excel导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaiQianDiJinTieBiaoZhunTempInfoList")
	public void exportPaiQianDiJinTieBiaoZhunTempInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.zhiZe",
				request));// 职责
		aliasNameList.add(TipMessage
				.getTipMessage("pa.salary.canShu.chengShiDengJi", request));// 城市等级
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.diQuMingCheng", request));// 地区名称
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.shuZhi",
				request));// 数值
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.yanZhengJieGuo", request));// 验证结果

		List list = new ArrayList();
		
		//法人
		modelMap.put("faren", request.getParameter("seach_faren"));
		//职责
		modelMap.put("zhize", request.getParameter("seach_zhize"));
		//城市等级
		modelMap.put("csdj", request.getParameter("seach_chengshidengji"));
		//地区名称
		modelMap.put("dqmc", request.getParameter("seach_diqumingcheng"));
		
		
		String sqlDetailInfo = "select s.pqd_faren fr , " 
						+ " s.pqd_zhize zz, "
						+ "  nvl(get_global_name(s.pqd_chengshidengji, '"+(String)paramMap.get("interLanguage")+"'), s.pqd_chengshidengji) csdj, "
						+ "  nvl(get_region_name(s.pqd_diqumingcheng), s.pqd_diqumingcheng) dqmc, " 
						+ " s.pqd_shuzhi sz,  "
						+ " nvl(s.pqd_001, 'NONE') yzjg  "
						+ "  from sy_dispatch_allow_std_temp s " 
						+ "  where 1 = 1 " ;
						if(null != modelMap.get("RESULT_FLAG")  && !"".equals(modelMap.get("RESULT_FLAG")) ){
							sqlDetailInfo = sqlDetailInfo + "  and s.pqd_daoru_result = '" +  (String)modelMap.get("RESULT_FLAG")+"'";
						}
						
						sqlDetailInfo = sqlDetailInfo + " order by s.pqd_no asc ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);
		if(itemDetailList.size() == 0){
			sqlDetailInfo = " select 'NONE', 'NONE', 'NONE', 'NONE', 'NONE', 'NONE' from dual ";
			sqlDetailmap.remove("sqlContent");
			sqlDetailmap.put("sqlContent", sqlDetailInfo);
			itemDetailList = this.excelUtilSer
			.getContentNoByFiled(sqlDetailmap);
		}

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

		String name = "viewPaiQianDiJinTieBiaoZhunInfoModule";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, null, name);
	}
	
	/**
	 * 派遣津贴标准Excel导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaiQianDiJinTieBiaoZhunInfoList")
	public void exportPaiQianDiJinTieBiaoZhunInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.zhiZe",
				request));// 职责
		aliasNameList.add(TipMessage
				.getTipMessage("pa.salary.canShu.chengShiDengJi", request));// 城市等级
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.diQuMingCheng", request));// 地区名称
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.shuZhi",
				request));// 数值
//		aliasNameList.add(TipMessage.getTipMessage(
//				"pa.salary.canShu.beiZhu", request));// 备注

		List list = new ArrayList();
		
		//法人
		modelMap.put("faren", request.getParameter("seach_faren"));
		//职责
		modelMap.put("zhize", request.getParameter("seach_zhize"));
		//城市等级
		modelMap.put("csdj", request.getParameter("seach_chengshidengji"));
		//地区名称
		modelMap.put("dqmc", request.getParameter("seach_diqumingcheng"));
		

		String sqlDetailInfo =    "  select s.pqd_faren fr , " 
								+ "  s.pqd_zhize zz, "
								+ "  get_global_name(s.pqd_chengshidengji, '"+(String)paramMap.get("interLanguage")+"') csdj, "
								+ "  m.region_nm dqmc, " 
								+ "  s.pqd_shuzhi sz  "
								+ "  from sy_dispatch_allow_std s, INC_CITY_TO_CITYLEVEL_MAPP_DIS m " 
								+ "  where m.region_cd = s.pqd_diqumingcheng " ;
						if(null != modelMap.get("faren")  && !"".equals(modelMap.get("faren")) ){
							sqlDetailInfo = sqlDetailInfo + "  and s.pqd_faren = '" +  (String)modelMap.get("faren")+"'";
						}
						
						if(null != modelMap.get("zhize")  && !"".equals(modelMap.get("zhize")) ){
							sqlDetailInfo = sqlDetailInfo + "  and s.pqd_zhize = '" +  (String)modelMap.get("zhize")+"'";
						}
						
						if(null != modelMap.get("csdj")  && !"".equals(modelMap.get("csdj")) ){
							sqlDetailInfo = sqlDetailInfo  + "  and get_global_name(s.pqd_chengshidengji, '"+(String)paramMap.get("interLanguage")+"') = '" +  (String)modelMap.get("csdj")+"'" ;
						}
						
						if(null != modelMap.get("dqmc") && !"".equals(modelMap.get("dqmc"))){
							sqlDetailInfo = sqlDetailInfo 
							+ "  and s.pqd_diqumingcheng "
							+ "	like '"+(String)modelMap.get("dqmc")+"'";
						}
						
						sqlDetailInfo = sqlDetailInfo + " order by s.pqd_no asc ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);
		if(itemDetailList.size() == 0){
			sqlDetailInfo = " select 'NONE', 'NONE', 'NONE', 'NONE', 'NONE' from dual ";
			sqlDetailmap.remove("sqlContent");
			sqlDetailmap.put("sqlContent", sqlDetailInfo);
			itemDetailList = this.excelUtilSer
			.getContentNoByFiled(sqlDetailmap);
		}

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

		String name = "viewPaiQianDiJinTieBiaoZhunInfoModule";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, null, name);
	}
	
	/**
	 * 下载派遣地管理的模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportPaiQianDiGuanLiMoBanModle_old")
	public void exportPaiQianDiGuanLiMoBanModle(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();

		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage
				.getTipMessage("pa.salary.canShu.chengShiDengJi", request));// 城市等级
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.shengFen",
				request));// 省份
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.chengShiMingCheng", request));// 城市名称
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.diQuMingCheng", request));// 地区名称

		List list = new ArrayList();

		String sqlDetailInfo = "select p.pqd_faren fr, " +
				"p.pqd_chengshidengji csdj, " +
				"p.pqd_shengfen sf, " +
				"p.pqd_chengshimingcheng csmc, " +
				"p.pqd_diqumingcheng dqmc " +
					"from sy_dispatch p " +
						"where p.pqd_no = (select min(pp.pqd_no) from sy_dispatch pp )";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

		// 开始设定sheet的列名以及数据
		String sqlItemName = "SELECT 'IN' CONTENT FROM DUAL "
				+ " UNION SELECT 'OUT' CONTENT FROM DUAL ";
		LinkedHashMap sqlmap = new LinkedHashMap();
		sqlmap.put("sqlContent", sqlItemName);
		List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",
				request));// 类型(请填写sheet1中的数据)

		String name = "viewPaiQianDiInfoModule";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, itemList, name);
	}
	
	/**
	 * 下载派遣津贴标准的模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 *  pa.salary.canShu.faRen                               = 法人
		pa.salary.canShu.chengShiDengJi                      = 城市等级
		pa.salary.canShu.diQuMingCheng                       = 地区名称
		pa.salary.canShu.shengFen                            = 省份
		pa.salary.canShu.chengShiMingCheng                   = 城市名称
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/old_exportPaiQianDiJinTieBiaoZhunMoBanModle")
	public void exportPaiQianDiJinTieBiaoZhunMoBanModle(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();

		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.faRen",
				request));// 法人
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.zhiZe",
				request));// 职责
		aliasNameList.add(TipMessage
				.getTipMessage("pa.salary.canShu.chengShiDengJi", request));// 城市等级
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.diQuMingCheng", request));// 地区名称
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.salary.canShu.shuZhi", request));// 数值
		aliasNameList.add(TipMessage.getTipMessage("pa.salary.canShu.beiZhu",
				request));// 备注
		List list = new ArrayList();

		String sqlDetailInfo = "select p.pqd_faren fr, " +
				"p.pqd_zhize zz, " +
				"p.pqd_chengshidengji csdj, " +
				"p.pqd_diqumingcheng dqmc, " +
				"p.pqd_shuzhi sz, " +
				"p.pqd_beizhu bz " +
					"from sy_dispatch_allow_std p " +
						"where p.pqd_no = (select min(pp.pqd_no) from sy_dispatch_allow_std pp )";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);
			list.add(empInfo);
		}

		// 开始设定sheet的列名以及数据
		String sqlItemName = "SELECT 'IN' CONTENT FROM DUAL "
				+ " UNION SELECT 'OUT' CONTENT FROM DUAL ";
		LinkedHashMap sqlmap = new LinkedHashMap();
		sqlmap.put("sqlContent", sqlItemName);
		List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",
				request));// 类型(请填写sheet1中的数据)

		String name = "viewPaiQianDiJinTieBiaoZhunInfoModule";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, itemList, name);
	}
	
	
	/**
	 * 下载导入模板-刷卡导入模板 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportArCardRecordExcelModelfour")
	public void exportArCardRecordExcelModelfour(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();

		aliasNameList.add(TipMessage.getTipMessage("hr.viewCondSql.title.KAHAO",
				request));// 饭卡号
		aliasNameList.add(TipMessage
				.getTipMessage("public.title.name", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",
				request));// 部门
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewarcardrecord.title.shijian", request));// 时间
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.excelexport.title.leixing", request));// 类型(请填写sheet2中的数据)
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewarcardrecord.title.beizhu", request));// 备注

		List list = new ArrayList();

		String sqlDetailInfo = "SELECT E.EMPID,E.LOCAL_NAME, HD.CONTENT DEPTNAME, AR.CARD_NO "
				+ " FROM HR_EMPLOYEE E, HR_DEPARTMENT_NAME HD, AR_DINING_CARD_ASSOCIATE AR "
				+ " WHERE E.PERSON_ID = AR.PERSON_ID(+) " 
				+ " AND E.DEPTNO = HD.DEPTNO(+) "
				+ " AND HD.LANGUAGE(+) = '"
				+ admin.getLanguage()
				+ "' "
				+ " AND E.STATUS_CODE <> 1375   "
				+ " AND EXISTS(                            "
				+ "            SELECT *                          "
				+ "            FROM AR_SUPERVISOR_INFO                  "
				+ "           WHERE AR_SUPERVISOR_INFO.DEPTNO = E.DEPTNO    "
				+ "             AND AR_SUPERVISOR_INFO.PERSON_ID = '"
				+ admin.getPersonId()
				+ "' "
				+ "          ) 				"
				+ " ORDER BY HD.DEPTNO,EMPID ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer
				.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap map = new LinkedHashMap();
			Map empInfo = (Map) itemDetailList.get(i);

			if (empInfo != null) {
				map.put("CELL0", empInfo.get("CARD_NO") == null ? "" : empInfo
						.get("CARD_NO").toString());
				map.put("CELL1", empInfo.get("LOCAL_NAME") == null ? ""
						: empInfo.get("LOCAL_NAME").toString());
				map.put("CELL2", empInfo.get("DEPTNAME") == null ? "" : empInfo
						.get("DEPTNAME").toString());
			} else {
				map.put("CELL0", "");
				map.put("CELL1", "");
				map.put("CELL2", "");
			}

			if (i == 0) {
				map.put("CELL3", "2012-01-01 09:00");
				map.put("CELL4", "IN");
			}

			if (i == 1) {
				map.put("CELL3", "2012-01-01 18:00");
				map.put("CELL4", "OUT");
			}

			list.add(map);
		}

		// 开始设定sheet的列名以及数据
		String sqlItemName = "SELECT 'IN' CONTENT FROM DUAL "
				+ " UNION SELECT 'OUT' CONTENT FROM DUAL ";
		LinkedHashMap sqlmap = new LinkedHashMap();
		sqlmap.put("sqlContent", sqlItemName);
		List itemList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		itemList.add(TipMessage.getTipMessage("ar.excelexport.title.leixing1",
				request));// 类型(请填写sheet1中的数据)

		String name = "viewArCardRecord_module3";
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelTwoSheetByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null, itemList, name);
	}
	
	/**
	 * 下载导入模板需要写的方法（工资查看-C01专用--SAP特殊人员导入模版） Description:download the module for upload
	 * data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportSapSpecialEmpModel")
	public void exportSapSpecialEmpModel(
			HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("ar.viewarprogress.title.gongziyue", request));//工资月
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));//姓名
		aliasNameList.add(TipMessage.getTipMessage("pa.title.message.sapSendPaType", request));//Sap工资发放类型
		
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "201210");
		map.put("CELL1", "12000001");
		map.put("CELL2", "测试李");
		map.put("CELL3", "C(发放现金人员标志)");
		list.add(map);

		LinkedHashMap map2 = new LinkedHashMap();
		map2.put("CELL0", "201210");
		map2.put("CELL1", "12000002");
		map2.put("CELL2", "测试王");
		map2.put("CELL3", "W(待转账人员标志)");
		list.add(map2);
		
		LinkedHashMap map3 = new LinkedHashMap();
		map3.put("CELL0", "201210");
		map3.put("CELL1", "12000003");
		map3.put("CELL2", "测试张");
		map3.put("CELL3", "F(重新发送人员标志)");
		list.add(map3);
		
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}
	
	/**
	 * 奖励，惩罚下载导入模版
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-29 上午10:20:44 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportRewardOrPunishment")
	public void exportRewardOrPunishment(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap)throws Exception{
		List aliasNameList = new ArrayList();
		List sheet2Contents=null;
		String transferOrderType=request.getParameter("transferOrderType");
		if(transferOrderType != null && !"".equals(transferOrderType)){
			aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.EMPID", request));//工号
			aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.LOCAL_NAME", request));//姓名
			if("reward".equals(transferOrderType)){
				aliasNameList.add(TipMessage.getTipMessage("hr.viewReward.title.REWARD_DATE", request));//奖励日期
				aliasNameList.add(TipMessage.getTipMessage("hr.viewReward.title.REWARD_TYPE_NAME", request));//奖励类型
				aliasNameList.add(TipMessage.getTipMessage("hr.viewReward.title.REWARD_BONUS", request));//奖励金额
				aliasNameList.add(TipMessage.getTipMessage("hr.viewReward.title.REWARD_CONTENTS", request));//功绩内容
			}else{
				aliasNameList.add(TipMessage.getTipMessage("hr.viewReward.title.DATE_PUNISHED", request));//惩戒日期
				aliasNameList.add(TipMessage.getTipMessage("hr.viewReward.title.PUN_TYPE_NAME", request));//惩戒类型
				aliasNameList.add(TipMessage.getTipMessage("hr.viewReward.title.PUN_BONUS", request));//惩戒金额
				aliasNameList.add(TipMessage.getTipMessage("hr.viewReward.title.PUN_CONTENTS", request));//惩戒事由
			}
		}
		List personList = new ArrayList();
		LinkedHashMap map=new LinkedHashMap();
		map.put("CELL0", "12000001");
		map.put("CELL1", "IT");
		map.put("CELL2", "2013/01/01");
		personList.add(map);
		sheet2Contents=this.excelUtilSer.getExcelExportTypeList(request);
		if("reward".equals(transferOrderType)){
			sheet2Contents.add(TipMessage.getTipMessage("hr.viewReward.title.REWARD_TYPE_NAME", request));//sheet2表头
		}else{
			sheet2Contents.add(TipMessage.getTipMessage("hr.viewReward.title.PUN_TYPE_NAME", request));//sheet2表头
		}
		
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(personList);
		//this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
		this.excelUtilSer.exportExcelTwoSheet(request, response, modelMap, sqlContentmap, aliasNameList, null, sheet2Contents);
	}
	
	
	//-----------------------lufeng 2013-09-02-------------------------
	/**
	 * 导出保险申请的数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportInsuranceInputApplyDataExcelIsNotNull")
	public void exportInsuranceInputApplyDataExcelIsNotNull(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String language = admin.getLanguage();
		String adminId = admin.getAdminID();

		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage(
				"public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage(
				"public.title.name", request));//姓名
		aliasNameList.add(TipMessage.getTipMessage(
				"public.title.deptName", request));//部门
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.postGrade", request));//职级
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.status", request));//状态
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.endMonth", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage(
				"pa.ins.alert.message.exportdata.netAddress", request));//网址

		aliasList.add("EMPID");
		aliasList.add("LOCAL_NAME");
		aliasList.add("DEPT_NAME");
		aliasList.add("POST_GRADE_NAME");
		aliasList.add("STATUS_NAME");
		aliasList.add("START_MONTH");
		aliasList.add("END_MONTH");
		aliasList.add("RETURN_VALUE");
		aliasList.add("URL_STR");
		String sqlContent = " SELECT HE.EMPID, " + " HE.LOCAL_NAME, "
					+ " HD.CONTENT     DEPT_NAME, "
					+ " SY0.CONTENT    POST_GRADE_NAME, "
					+ " SY1.CONTENT    STATUS_NAME, "
					+ " SY2.CONTENT    CPNY_NAME, " 
					+ " T.START_MONTH, "
					+ " T.END_MONTH, " 
					+ " T.RETURN_VALUE, "
					+ " T.URL_STR, " 
					+ " T.PARAM_DATA_NO "
				+ " FROM IS_PARAM_DATA_APPLY      T, " 
					+ " HR_EMPLOYEE        HE, "
					+ " HR_COMPANY         HR, " 
					+ " HR_DEPARTMENT_NAME HD, "
					+ " SY_GLOBAL_NAME     SY0, " 
					+ " SY_GLOBAL_NAME     SY1, "
					+ " SY_GLOBAL_NAME     SY2 "
				+ " WHERE T.PERSON_ID = HE.PERSON_ID "
					+ "  AND HE.DEPTNO = HD.DEPTNO(+) "
					+ "  AND T.AFFIRM_FLAG = 1 "
					+ "  AND HD.LANGUAGE(+) = '" + language + "' "
					+ "  AND HE.POST_GRADE_NO = SY0.NO(+) "
					+ "  AND SY0.LANGUAGE(+) = '" + language + "' "
					+ "  AND HE.STATUS_CODE = SY1.NO(+) "
					+ "  AND SY1.LANGUAGE(+) = '" + language + "' "
					+ "  AND T.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY2.NO(+) "
					+ "  AND SY2.LANGUAGE(+) = '" + admin.getLanguage() + "' "
					+ "	 AND T.PARAM_NO=" + request.getParameter("id")
					+ "	 AND T.CPNY_ID='" + admin.getCpnyId() + "'"
				//+ "  AND EXISTS( "
				//+ " SELECT * "
				//+ "  FROM PA_SUPERVISOR_INFO "
				//+ " WHERE PA_SUPERVISOR_INFO.DEPTNO = HE.DEPTNO "
				//+ " AND PA_SUPERVISOR_INFO.PERSON_ID ='"
				//+ adminId
				//+ "') "
				+ " ORDER BY HE.EMPID";
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		this.excelUtilSer.exportExcel(request, response, modelMap, map,
				aliasNameList, aliasList);
	}
	
	/**
	 * 导出数据时需要调用的方法 Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportInsuranceInputApplyDataExcelIsNull")
	public void exportInsuranceInputApplyDataExcelIsNull(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		// 取得数据项目信息
		LinkedHashMap isInputItemInfo = (LinkedHashMap) this.insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap);
		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(isInputItemInfo.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		String distinctField2 = ObjectUtils.toString(isInputItemInfo.get("DISTINCT_FIELD_2ND"));
		String language = admin.getLanguage();
		String adminId = admin.getAdminID();
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		String filed1Name = ObjectUtils.toString(isInputItemInfo.get("DISTINCT_FIELD_NAME"));
		String filed2Name = ObjectUtils.toString(isInputItemInfo.get("DISTINCT_FIELD_2ND_NAME"));
		aliasNameList.add(filed1Name);
		if (!filed2Name.equals("")) {
			aliasNameList.add(filed2Name);
		}
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
	//	aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request));//备注
		aliasNameList.add(TipMessage.getTipMessage("pa.ins.alert.message.exportdata.netAddress", request));//网址

		aliasList.add("FIELD1_NAME");
		if (!filed2Name.equals("")) {
			aliasList.add("FIELD2_NAME");
		}
		aliasList.add("START_MONTH");
		
		aliasList.add("RETURN_VALUE");
		aliasList.add("REMARK");
		aliasList.add("URL_STR");
		String sqlContent = null;

		if (distinctField.equals("CPNY_ID")) {
			sqlContent = "SELECT A.PARAM_DATA_NO, " 
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, " 
					+ " A.FIELD2_VALUE, "
					+ " SY1.CONTENT     FIELD1_NAME, " 
					+ " RETURN_VALUE, "
					+ " URL_STR, "
					+ " A.START_MONTH, " 
					+ " A.END_MONTH, " 
					+ " A.CPNY_ID, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM IS_PARAM_DATA_OTHER_APPLY A, "
					+ "  HR_COMPANY          HR, "
					+ "  SY_GLOBAL_NAME      SY1, "
					+ "  HR_COMPANY          HC, "
					+ "  SY_GLOBAL_NAME      SY3 "
					+ " WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND A.AFFIRM_FLAG = 1 "
					+ "  AND SY3.LANGUAGE(+) = '" + language + "' "
					+ "  AND A.FIELD1_VALUE = HC.CPNY_ID "
					+ "  AND HC.CPNY_NO = SY1.NO(+) "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND SY1.LANGUAGE(+) = '" + language + "' ";
		} else if ((distinctField.equals("WORK_AREA") || distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")) {
			sqlContent = "SELECT A.PARAM_DATA_NO, "
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, "
					+ " A.FIELD2_VALUE, "
					+ " SY1.CONTENT     FIELD1_NAME, "
					+ " SY2.CONTENT     FIELD2_NAME, "
					+ " RETURN_VALUE, "
					+ " URL_STR, "
					+ " A.START_MONTH, "
					+ " A.END_MONTH, "
					+ " A.CPNY_ID, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM IS_PARAM_DATA_OTHER_APPLY A, "
					+ "      HR_COMPANY          HR, "
					+ "      SY_GLOBAL_NAME      SY1, "
					+ "      SY_GLOBAL_NAME      SY2, "
					+ "      SY_GLOBAL_NAME      SY3 "
					+ " WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND A.AFFIRM_FLAG = 1 "
					+ "  AND SY3.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.FIELD1_VALUE = SY1.NO(+) "
					+ "  AND SY1.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.FIELD2_VALUE = SY2.NO(+) "
					+ "  AND SY2.LANGUAGE(+) = '"+ language+ "' "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND A.FIELD1_VALUE IN ( "
					+ "		  SELECT DISTINCT T.DEPT_DISTINGUISH_NO FROM HR_DEPARTMENT T WHERE T.DEPTNO IN  "
					+ "		  (SELECT DEPTNO FROM PA_SUPERVISOR_INFO T WHERE T.PERSON_ID = '"+ adminId + "') " 
					+ "		  )";
		} else if (distinctField2.equals("DEPTNO")) {
			sqlContent = "	SELECT A.PARAM_DATA_NO, " 
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, " 
					+ " A.FIELD2_VALUE, "
					+ " get_global_name(A.FIELD1_VALUE,'" + language+ "')     FIELD1_NAME, " 
					+ " SY2.CONTENT     FIELD2_NAME, "
					+ " RETURN_VALUE, " 
					+ " URL_STR, "
					+ " A.START_MONTH,  "
					+ " A.END_MONTH, " 
					+ " A.CPNY_ID, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM IS_PARAM_DATA_OTHER_APPLY A, "
					+ "  HR_COMPANY          HR, "
					+ "  hr_department_name      SY2, "
					+ "  SY_GLOBAL_NAME      SY3 "
					+ " WHERE A.CPNY_ID = HR.CPNY_ID "
					+ "  AND HR.CPNY_NO = SY3.NO(+) "
					+ "  AND A.AFFIRM_FLAG = 1 "
					+ "  AND SY3.LANGUAGE(+) = '" + language + "' "
					+ "  AND A.FIELD2_VALUE = SY2.deptno(+) "
					+ "  AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ "  AND SY2.LANGUAGE(+) = '" + language + "' ";
		} else {
			sqlContent = "SELECT A.PARAM_DATA_NO, " 
					+ " A.PARAM_NO, "
					+ " A.FIELD1_VALUE, " 
					+ " A.FIELD2_VALUE, "
					+ " SY1.CONTENT     FIELD1_NAME, "
					+ " SY2.CONTENT     FIELD2_NAME, " 
					+ " RETURN_VALUE, "
					+ " URL_STR, "
					+ " A.START_MONTH, " 
					+ " A.REMARK, " 
					+ " A.CPNY_ID, "
					+ " SY3.CONTENT     CPNY_NAME "
					+ " FROM IS_PARAM_DATA_OTHER_APPLY A, "
					+ " HR_COMPANY          HR, "
					+ " SY_GLOBAL_NAME      SY1, "
					+ " SY_GLOBAL_NAME      SY2, "
					+ " SY_GLOBAL_NAME      SY3 "
					+ "WHERE A.CPNY_ID = HR.CPNY_ID "
					+ " AND HR.CPNY_NO = SY3.NO(+) "
					+ " AND A.AFFIRM_FLAG = 0 "
					+ " AND SY3.LANGUAGE(+) =  '" + language + "' "
					+ " AND A.FIELD1_VALUE = SY1.NO(+) "
					+ " AND SY1.LANGUAGE(+) =  '" + language + "' "
					+ " AND A.FIELD2_VALUE = SY2.NO(+) "
					+ " AND SY2.LANGUAGE(+) ='" + language + "'"
					+ " AND A.PARAM_NO="+ request.getParameter("seach_PARAM_NO")
					+ " AND A.CPNY_ID='" + admin.getCpnyId() + "'" 
					+ "AND  A.APPLY_STATUS = '0' "
					+ " ORDER BY A.FIELD1_VALUE,A.FIELD2_VALUE";
		}
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		this.excelUtilSer.exportExcel(request, response, modelMap, map,
				aliasNameList, aliasList);
	}
	
	/**
	 * 下载导入模板需要写的方法 Description:download the module for upload data
	 * 工资基础-保险维护 -保险申请-有PERSON_ID
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportInsuranceInputApplyDataExcelIsNotNullModule")
	public void exportInsuranceInputApplyDataExcelIsNotNullModule(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));//姓名
		//aliasNameList.add(TipMessage.getTipMessage("public.title.deptName", request));//部门
		//aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.postGrade", request));//职级
		//aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.status", request));//状态
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
		//aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//备注
		aliasNameList.add(TipMessage.getTipMessage("pa.ins.alert.message.exportdata.netAddress", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//网址

		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "12000001");
		map.put("CELL1", "测试李");
//		map.put("CELL2", "风险管理部");
//		map.put("CELL3", "1");
//		map.put("CELL4", "在职");
//		map.put("CELL5", "201309");
		map.put("CELL2", "201309");
		map.put("CELL3", "0.5");
		map.put("CELL4", "备注");
		map.put("CELL5", "http://globalhr.lotte.net");
		list.add(map);

		LinkedHashMap map2 = new LinkedHashMap();
		map2.put("CELL0", "12000002");
		map2.put("CELL1", "测试王");
//		map2.put("CELL2", "风险管理部");
//		map2.put("CELL3", "2");
//		map2.put("CELL4", "在职");
//		map2.put("CELL5", "201309");
		map2.put("CELL2", "201309");
		map2.put("CELL3", "1111");
		map2.put("CELL4", "备注");
		map2.put("CELL5", "http://globalhr.lotte.net");
		list.add(map2);

		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}
	
	/**
	 * 下载导入模板需要写的方法 Description:download the module for upload data
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportInsuranceInputApplyDataExcelIsNullModule")
	public void exportInsuranceInputApplyDataExcelIsNullModule(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		String filed1Name = request.getParameter("FIELD1_NAME");
		String filed2Name = request.getParameter("FIELD2_NAME");
		if (filed1Name != null) {
			aliasNameList.add(filed1Name);
		}
		if (filed2Name != null) {
			aliasNameList.add(filed2Name);
		}
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
		//aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//备注
		aliasNameList.add(TipMessage.getTipMessage("pa.ins.alert.message.exportdata.netAddress", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//网址
		

		String filed1Value = request.getParameter("FIELD1");// LOCAL_NAME
		String sqlNotPersonContent = "select distinct S.content from PA_HR_V t,SY_GLOBAL_NAME S WHERE t."
				+ filed1Value
				+ " = S.NO(+) AND S.LANGUAGE(+)='"
				+ admin.getLanguage()
				+ "' AND T.CPNY_ID='"
				+ admin.getCpnyId()
				+ "' AND S.CONTENT IS NOT NULL ";// group by S.content ) where  rownum<3";
		LinkedHashMap sqlmap = new LinkedHashMap();
		List contentList = null;
		if(filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("CPNY_ID")){
			String sqlPersonContent = "SELECT distinct SY.CONTENT  FROM HR_EMPLOYEE HE,HR_COMPANY HC,SY_GLOBAL_NAME SY "
										+" WHERE HE.CPNY_ID = HC.CPNY_ID AND HC.CPNY_NO = SY.NO(+) "
										+" AND SY.LANGUAGE(+) = '"+ admin.getLanguage()
										+"'AND HE.CPNY_ID = '"
										+  admin.getCpnyId();
										//+"'AND ROWNUM < 3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}else if(filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("DEPTNO")){
			String sqlPersonContent = "SELECT distinct SY.CONTENT  FROM HR_EMPLOYEE HE,HR_DEPARTMENT_NAME SY "
										+" WHERE HE.DEPTNO = SY.DEPTNO(+) "
										+" AND SY.LANGUAGE(+) = '"+ admin.getLanguage()
										+"'AND HE.CPNY_ID = '"
										+  admin.getCpnyId();
										//+"'AND ROWNUM < 3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}else if (filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && !filed1Value.equals("LOCAL_NAME")
				&& !filed1Value.equals("PERSON_ID")) {
			sqlmap.put("sqlContent", sqlNotPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		} else if (filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("LOCAL_NAME")) {
			String sqlPersonContent = "SELECT distinct LOCAL_NAME  CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId();// + "' AND ROWNUM<3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		} else if (filed1Name != null && filed1Value != null
				&& !filed1Value.equals("") && filed1Value.equals("PERSON_ID")) {
			String sqlPersonContent = "SELECT distinct EMPID  CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId();// + "' AND ROWNUM<3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}
		String filed2Value = request.getParameter("FIELD2");// PERSON_ID
		String sqlNotPersonContent2 = "select distinct S.content from PA_HR_V t,SY_GLOBAL_NAME S WHERE t."
				+ filed2Value
				+ " = S.NO(+) AND S.LANGUAGE(+)='"
				+ admin.getLanguage()
				+ "' AND T.CPNY_ID='"
				+ admin.getCpnyId()
				+ "' " + " AND S.CONTENT IS NOT NULL ";// and rownum<3";
		LinkedHashMap sqlmap2 = new LinkedHashMap();
		List contentList2 = null;
		if(filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && filed2Value.equals("DEPTNO")){
			String sqlPersonContent = "SELECT distinct SY.CONTENT  FROM HR_EMPLOYEE HE,HR_DEPARTMENT_NAME SY "
										+" WHERE HE.DEPTNO = SY.DEPTNO(+) "
										+" AND SY.LANGUAGE(+) = '"+ admin.getLanguage()
										+"'AND HE.CPNY_ID = '"
										+  admin.getCpnyId();
										//+"'AND ROWNUM < 3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}else if (filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && !filed2Value.equals("LOCAL_NAME")
				&& !filed2Value.equals("PERSON_ID")) {
			sqlmap2.put("sqlContent", sqlNotPersonContent2);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap2);
		} else if (filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && filed2Value.equals("LOCAL_NAME")) {
			String sqlPersonContent = "SELECT distinct LOCAL_NAME  CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId();// + "' AND ROWNUM<3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap);
		} else if (filed2Name != null && filed2Value != null
				&& !filed2Value.equals("") && filed2Value.equals("PERSON_ID")) {
			String sqlPersonContent = "SELECT distinct EMPID CONTENT FROM HR_EMPLOYEE WHERE CPNY_ID='"
					+ admin.getCpnyId(); //+ "' AND ROWNUM<3";
			sqlmap.put("sqlContent", sqlPersonContent);
			contentList2 = this.excelUtilSer.getContentNoByFiled(sqlmap);
		}
		List list = new ArrayList();
		int maxSize=-1;
		if(contentList!=null&&contentList.size()>0){
			maxSize=contentList.size();
		}
		if(contentList2!=null&&contentList2.size()>contentList.size()){
			maxSize=contentList2.size();
		}
		
		for(int i=0;i<maxSize;i++){
			LinkedHashMap map = new LinkedHashMap();
			if (filed1Name != null) {
				String cellStr = "";
				if (contentList != null && contentList.size() > 0&& i<contentList.size()&& contentList.get(i) != null) {
					cellStr = ((Map) (contentList.get(i))).get("CONTENT") == null ? ""
							: ((Map) (contentList.get(i))).get("CONTENT")
									.toString();
				}
				map.put("CELL0", cellStr);
			}
			if (filed2Name != null) {
				String cellStr = "";
				if (contentList2 != null && contentList2.size() > 0&& i<contentList2.size()&& contentList2.get(i) != null) {
					cellStr = ((Map) (contentList2.get(i))).get("CONTENT") == null ? ""
							: ((Map) (contentList2.get(i))).get("CONTENT")
									.toString();
				}
				map.put("CELL1", cellStr);
			}
			if(i>=2){
				map.put("CELL2", "");
				//map.put("CELL3", "201202");
				map.put("CELL4", "");
				map.put("CELL5", "");
				map.put("CELL6", "");
			}else{
				map.put("CELL2", "201202");
				//map.put("CELL3", "201202");
				map.put("CELL4", "0.5");
				map.put("CELL5", "备注");
				map.put("CELL6", "http://globalhr.lotte.net");
			}
			list.add(map);
		}
		/*LinkedHashMap map = new LinkedHashMap();
		if (filed1Name != null) {
			String cellStr = "";
			if (contentList != null && contentList.size() > 0
					&& contentList.get(0) != null) {
				cellStr = ((Map) (contentList.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList.get(0))).get("CONTENT")
								.toString();
			}
			map.put("CELL0", cellStr);
		}
		if (filed2Name != null) {
			String cellStr = "";
			if (contentList2 != null && contentList2.size() > 0
					&& contentList2.get(0) != null) {
				cellStr = ((Map) (contentList2.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList2.get(0))).get("CONTENT")
								.toString();
			}
			map.put("CELL1", cellStr);
		}

		map.put("CELL2", "201202");
		//map.put("CELL3", "201202");
		map.put("CELL4", "0.5");
		map.put("CELL5", "备注");
		map.put("CELL6", "http://globalhr.lotte.net");
		
		
		list.add(map);

		LinkedHashMap map2 = new LinkedHashMap();
		if (filed1Name != null) {
			String cellStr = "";
			if (contentList != null && contentList.size() > 1
					&& contentList.get(1) != null) {
				cellStr = ((Map) (contentList.get(1))).get("CONTENT") == null ? ""
						: ((Map) (contentList.get(1))).get("CONTENT")
								.toString();
			} else if (contentList != null && contentList.size() == 1) {
				cellStr = ((Map) (contentList.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList.get(0))).get("CONTENT")
								.toString();
			}
			map2.put("CELL0", cellStr);
		}
		if (filed2Name != null) {
			String cellStr = "";
			if (contentList2 != null && contentList2.size() > 1
					&& contentList2.get(1) != null) {
				cellStr = ((Map) (contentList2.get(1))).get("CONTENT") == null ? ""
						: ((Map) (contentList2.get(1))).get("CONTENT")
								.toString();
			} else if (contentList2 != null && contentList2.size() == 1) {
				cellStr = ((Map) (contentList2.get(0))).get("CONTENT") == null ? ""
						: ((Map) (contentList2.get(0))).get("CONTENT")
								.toString();
			}
			map2.put("CELL1", cellStr);
		}
		map2.put("CELL2", "201202");
		//map2.put("CELL3", "201202");
		map2.put("CELL4", "0.75");
		map2.put("CELL5", "备注");
		map2.put("CELL6", "http://globalhr.lotte.net");

		list.add(map2);*/

		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);

	}
	
	/**
	 * 下载导入模板需要写的方法   工资系统 保险维护  项目批量导入使用
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/itemBatchImportExcelIsNotNullModule")
	public void itemBatchImportExcelIsNotNullModule(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("liang.public.title.ItemName", request));//项目名称
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));//姓名
		//aliasNameList.add(TipMessage.getTipMessage("public.title.deptName", request));//部门

		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//备注
       
		List list = new ArrayList();
		List getItemNameList = this.insuranceInputItemSer.getItemNameList(request);
		for(int i=0 ; i<getItemNameList.size();i++){
			
		
		LinkedHashMap map = new LinkedHashMap();
		if(i==0){
			map.put("CELL0",((Map)getItemNameList.get(i)).get("ALIAS_NAME").toString());
			map.put("CELL1", "12000001");
			map.put("CELL2", "测试李");
			//map.put("CELL3", "风险管理部");
			map.put("CELL3", "201202");
			map.put("CELL4", "201202");
			map.put("CELL5", "0.5");
			map.put("CELL6", "备注");
		}else{
			map.put("CELL0", ((Map)getItemNameList.get(i)).get("ALIAS_NAME").toString());
			map.put("CELL1", "");
			map.put("CELL2", "");
			//map.put("CELL3", "");
			map.put("CELL3", "");
			map.put("CELL4", "");
			map.put("CELL5", "");
			map.put("CELL6", "");
		}
		
		list.add(map);
		}

//		LinkedHashMap map2 = new LinkedHashMap();
//		map2.put("CELL0", "12000002");
//		map2.put("CELL1", "测试王");
//		map2.put("CELL2", "风险管理部");
//		map2.put("CELL3", "2");
//		map2.put("CELL4", "在职");
//		map2.put("CELL5", "201202");
//		map2.put("CELL6", "201202");
//		map2.put("CELL7", "1111");
//		list.add(map2);

		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}
	
	/**
	 * 下载导入模板需要写的方法   工资系统 工资维护  项目批量导入使用 输入项目
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/itemBatchImportExcelIsNotNullModulePa")
	public void itemBatchImportExcelIsNotNullModulePa(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("liang.public.title.ItemName", request));//项目名称
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));//姓名
	//	aliasNameList.add(TipMessage.getTipMessage("public.title.deptName", request));//部门

		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request)+"(日期格式:YYYYMM)");//开始月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.endMonth", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//结束月
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request)+"(不能为空)");//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//备注

		List list = new ArrayList();
		List mapNameList = new ArrayList();
		List mapList = new ArrayList();
		List getItemNameList = this.insuranceInputItemSer.getItemNameListPa(request);
		for(int i=0 ; i<getItemNameList.size();i++){

			LinkedHashMap map = new LinkedHashMap();
			if(i==0){
				map.put("CELL0",((Map)getItemNameList.get(i)).get("ALIAS_NAME").toString());
				map.put("CELL1", "12000001");
				map.put("CELL2", "测试李");
			//	map.put("CELL3", "风险管理部");
				map.put("CELL3", "201202");
				map.put("CELL4", "201202");
				map.put("CELL5", "0.5");
				map.put("CELL6", "备注");
			}else{
				map.put("CELL0", ((Map)getItemNameList.get(i)).get("ALIAS_NAME").toString());
				map.put("CELL1", "");
				map.put("CELL2", "");
				map.put("CELL3", "");
				map.put("CELL4", "");
				map.put("CELL5", "");
				map.put("CELL6", "");
				//map.put("CELL7", "");
			}
			list.add(map);
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		mapNameList.add("输入项目名称参考值");
		String sqlParamItem="SELECT NVL(S.CONTENT, ' ') CONTENT FROM Pa_Param_Item_Param T,SY_GLOBAL_NAME S WHERE T.PARAM_ITEM_NO=S.NO AND  T.Distinct_Field = 'PERSON_ID' AND S.LANGUAGE='zh' AND  T.ACTIVITY = 1 AND T.CPNY_ID='"+admin.getCpnyId()+"'";
		mapList.add(sqlParamItem);
		String name ="itemParam";
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList,name);
	}
	
	/**
	 * 下载导入模板需要写的方法   工资系统 工资维护  项目批量导入使用 基础项目
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/itemBatchImportExcelIsNotNullModulePaBasis")
	public void itemBatchImportExcelIsNotNullModulePaBasis(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("liang.public.title.ItemName", request));//项目名称
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));//姓名
		//aliasNameList.add(TipMessage.getTipMessage("public.title.deptName", request));//部门

		aliasNameList.add(TipMessage.getTipMessage("public.title.startDate", request)+"(日期格式:YYYYMM)");//开始日期
		aliasNameList.add(TipMessage.getTipMessage("public.title.endDate", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//结束日期
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request)+"(不能为空)");//数值
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//备注

		List list = new ArrayList();
		List mapNameList = new ArrayList();
		List mapList = new ArrayList();
		List getItemNameList = this.insuranceInputItemSer.getItemNameListPa(request);
		for(int i=0 ; i<getItemNameList.size();i++){

			LinkedHashMap map = new LinkedHashMap();
			if(i==0){
				map.put("CELL0",((Map)getItemNameList.get(i)).get("ALIAS_NAME").toString());
				map.put("CELL1", "12000001");
				map.put("CELL2", "测试李");
				//map.put("CELL3", "风险管理部");
				map.put("CELL3", "2013/01/01");
				map.put("CELL4", "2013/01/30");
				map.put("CELL5", "1");
				map.put("CELL6", "备注");
				
			}else{
				map.put("CELL0", ((Map)getItemNameList.get(i)).get("ALIAS_NAME").toString());
				map.put("CELL1", "");
				map.put("CELL2", "");
				map.put("CELL3", "");
				map.put("CELL4", "");
				map.put("CELL5", "");
				map.put("CELL6", "");
				//map.put("CELL7", "");
				
			}
			list.add(map);
		}
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		//String item =request.getParameter("type");
		//if ("507".equals(item)) {
			mapNameList.add("基础项目名称参考值");
			String sqlBasicItem="SELECT NVL(S.CONTENT, ' ') CONTENT FROM PA_BASIC_ITEM_PARAM T,SY_GLOBAL_NAME S WHERE T.ITEM_NO=S.NO AND  T.Distinct_Field = 'PERSON_ID' AND S.LANGUAGE='zh' AND  T.ACTIVITY = 1 AND T.CPNY_ID='"+admin.getCpnyId()+"'";
			mapList.add(sqlBasicItem);
		//} else {
		    
			
		//}
		String name ="itemBasic";
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList,name);
	}
	/**
	 * 导出数据时需要调用的方法 Description: 工资系统-工资查看-年工资查看
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportViewPaInfoByYearExcel")
	@ResponseBody
	public LinkedHashMap exportViewPaInfoByYearExcel(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		//Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		// 取得数据项目信息
		//LinkedHashMap isInputItemInfo = (LinkedHashMap) this.insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap);
		// 取得该项目参数的DISTINCT_FIELD
		
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		/*<th style="text-align: center"><spring:message code="ar.viewcycle.title.xuhao" /><!-- 序号 --></th>
		<th style="text-align: center"><spring:message code="liang.hr.viewWorkInfo.title.farenmingcheng"/><!-- 法人名称 --></th>
		<th style="text-align: center"><spring:message code="pa.payear.title.payear"/><!-- 年份 --></th>
		<th style="text-align: center"><spring:message code="public.title.empId"/><!-- 工号 --></th>
		<th style="text-align: center"><spring:message code="public.title.empName"/><!-- 姓名 --></th>
		<th style="text-align: center"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/> <!-- 部门 --></th>
		<th style="text-align: center"><spring:message code="hr.viewPersonalInfo.title.POST_NAME"/><!--职级名称 --></th>
		<th style="text-align: center"><spring:message code="hr.viewPersonalInfo.title.TRADEUNION_GONGHUINEIBUZHIZE"/><!-- 职责 --></th>
		<th style="text-align: center"><spring:message code="main.home.message.ruzhiriqi"/><!-- 入职日期 --></th>
		<th style="text-align: center">01<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">02<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">03<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">04<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">05<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">06<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">07<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">08<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">09<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">10<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">11<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">12<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center"><spring:message code="liang.hr.viewWorkInfo.title.zongji"/><!-- 总计 --></th>*/
		aliasNameList.add(TipMessage.getTipMessage("ar.viewcycle.title.xuhao", request));//序号
		aliasNameList.add(TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.farenmingcheng", request));//法人名称
		aliasNameList.add(TipMessage.getTipMessage("pa.payear.title.payear", request));//年份
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage("public.title.empName", request));//姓名
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.DEPTNAME", request));//部门
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.POST_NAME", request));//职级名称
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.TRADEUNION_GONGHUINEIBUZHIZE", request));//职责
		aliasNameList.add(TipMessage.getTipMessage("main.home.message.ruzhiriqi", request));//入职日期
		aliasNameList.add("01"+TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.MONTH", request));//月
		aliasNameList.add("02"+TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.MONTH", request));//月
		aliasNameList.add("03"+TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.MONTH", request));//月
		aliasNameList.add("04"+TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.MONTH", request));//月
		aliasNameList.add("05"+TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.MONTH", request));//月
		aliasNameList.add("06"+TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.MONTH", request));//月
		aliasNameList.add("07"+TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.MONTH", request));//月
		aliasNameList.add("08"+TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.MONTH", request));//月
		aliasNameList.add("09"+TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.MONTH", request));//月
		aliasNameList.add("10"+TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.MONTH", request));//月
		aliasNameList.add("11"+TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.MONTH", request));//月
		aliasNameList.add("12"+TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.MONTH", request));//月
		aliasNameList.add(TipMessage.getTipMessage("liang.hr.viewWorkInfo.title.zongji", request));//月
		Map paramMap1 = ObjectBindUtil.getRequestParamData(request,"seach_") ; 
		aliasList = paReportSer.getPaInfoByYearList(request);
		
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		
		//this.excelUtilSer.exportExcel(request, response, modelMap, map,aliasNameList, aliasList);
		String pathStr = "";
		LinkedHashMap resultMap = new LinkedHashMap();
		if (PaCalcUtil.getPaCalcFlag() == 0) {
			try {
				pathStr = this.excelUtilSer.exportIntoExcel1(request, response,
						modelMap, map, aliasNameList, aliasList);
				resultMap.put("pathStr", pathStr);
			} catch (Exception e) {
				resultMap.put("pathStr", "N");
				e.printStackTrace();
			} finally {
				PaCalcUtil.setPaCalcFlag(0);
			}
		} else {
			resultMap.put("pathStr", "K");
		}
		return resultMap;
	}
	/**
	 * 导出数据时需要调用的方法 Description: 报表中心-非定制报表-员工信息查看
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/viewEmployeeListExcel")
	@ResponseBody
	public LinkedHashMap viewEmployeeListExcel(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		//Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		// 取得数据项目信息
		//LinkedHashMap isInputItemInfo = (LinkedHashMap) this.insuranceInputItemDao.getInsuranceInputItemParamInfo(paramMap);
		// 取得该项目参数的DISTINCT_FIELD
		
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		/*<th style="text-align: center"><spring:message code="ar.viewcycle.title.xuhao" /><!-- 序号 --></th>
		<th style="text-align: center"><spring:message code="liang.hr.viewWorkInfo.title.farenmingcheng"/><!-- 法人名称 --></th>
		<th style="text-align: center"><spring:message code="pa.payear.title.payear"/><!-- 年份 --></th>
		<th style="text-align: center"><spring:message code="public.title.empId"/><!-- 工号 --></th>
		<th style="text-align: center"><spring:message code="public.title.empName"/><!-- 姓名 --></th>
		<th style="text-align: center"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/> <!-- 部门 --></th>
		<th style="text-align: center"><spring:message code="hr.viewPersonalInfo.title.POST_NAME"/><!--职级名称 --></th>
		<th style="text-align: center"><spring:message code="hr.viewPersonalInfo.title.TRADEUNION_GONGHUINEIBUZHIZE"/><!-- 职责 --></th>
		<th style="text-align: center"><spring:message code="main.home.message.ruzhiriqi"/><!-- 入职日期 --></th>
		<th style="text-align: center">01<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">02<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">03<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">04<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">05<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">06<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">07<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">08<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">09<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">10<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">11<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center">12<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
		<th style="text-align: center"><spring:message code="liang.hr.viewWorkInfo.title.zongji"/><!-- 总计 --></th>*/
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.EMPID", request));//工号
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.LOCAL_NAME", request));//姓名
		aliasNameList.add(TipMessage.getTipMessage("hr.viewHire.title.LOCALNAMEANDPINYIN", request));//(拼音)
		//aliasNameList.add(TipMessage.getTipMessage("hr.viewHire.title.LOCALNAMEANDPINYIN", request));//姓名(拼音)
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.IDCARD_NO", request));//身份证号
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.SEX", request));//性别
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.DOB", request));//出生日期
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.DEPTNAME", request));//部门
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.GRADE_LEVEL_NAME", request));//职等
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.DUTY_NAME", request));//职责
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.POST_GRADE_NAME", request));//职级
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.POST_NAME", request));//职级名称(职务)
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.ENTRY_DATE", request));//入司日期 即子公司入职日期 
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.IN_THE_DIFFERENCE", request));//在职区分
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.NOW_DEPARTMENT_DATE", request));//现部门异动日期
		aliasNameList.add(TipMessage.getTipMessage("liang.hr.viewPersonalInfo.title.ADVANCEMENT_DATE", request));//晋升日期
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.DATE_LEFT", request));//离职日期
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.ON_THE_JOB_WORK_SENIORITY", request)+"(Year)");//司内工作年资
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.ON_THE_JOB_WORK_SENIORITY", request)+"(Month)");//司内工作年资
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.EMPLOYMENT_TYPE", request));//雇佣类型
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.WORK_TIME_TYPE", request));//工作时间类型
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.CONTRACT_TYPE", request));//契约类型
		aliasNameList.add(TipMessage.getTipMessage("liang.hr.viewPersonalInfo.title.PARTICULAR_HUMAN_DISTINGUISH", request));//详细人力区分
		Map paramMap1 = ObjectBindUtil.getRequestParamData(request,"seach_") ; 
		String sta="";
		String CpnyStatus="";
		if(request.getParameter("STATUS_1")==null){
			sta="0";
		}else{
			sta=request.getParameter("STATUS_1");
		}
		//是否查询子部门
		if(request.getParameter("CPNYSTATUS")==null){
			CpnyStatus="1";
		}else{
			CpnyStatus=request.getParameter("CPNYSTATUS");
		}
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(paramMap.get("PERSON_ID")==null){
			paramMap.put("PERSON_ID",admin.getPersonId());
		}
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());
		if(request.getParameter("status_code_emp")!=null){
			
			if(request.getParameter("status_code_emp").equals("0")){
				paramMap.put("STATUS", null);
			}else{
				paramMap.put("STATUS", "abc");
			}
		}
		String CPNYSTATUS=request.getParameter("CPNYSTATUS");
		
		if(CPNYSTATUS==null){
			paramMap.put("CPNYSTATUS", null);
		}
		else if(request.getParameter("seach_DEPTNO")==null||request.getParameter("seach_DEPTNO").equals("")){
			paramMap.put("CPNYSTATUS", null);
		}else{
			paramMap.put("CPNYSTATUS",  request.getParameter("seach_DEPTNO"));
		}
		
		
		
		if(paramMap.get("CPNYSTATUS")!=null){
			paramMap.put("DEPTNO", null);
		}else{
			paramMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		}
		
		//List linkMap = (List)empInfoSer.getPersonalInfoForInformation(request);
		
		aliasList = (List) empInfoSer.getPersonalInfoForInformation(request);
		
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		
		//this.excelUtilSer.exportExcel(request, response, modelMap, map,aliasNameList, aliasList);
		String pathStr = "";
		LinkedHashMap resultMap = new LinkedHashMap();
		if (PaCalcUtil.getPaCalcFlag() == 0) {
			try {
				pathStr = this.excelUtilSer.exportIntoExcel2(request, response,
						modelMap, map, aliasNameList, aliasList);
				resultMap.put("pathStr", pathStr);
			} catch (Exception e) {
				resultMap.put("pathStr", "N");
				e.printStackTrace();
			} finally {
				PaCalcUtil.setPaCalcFlag(0);
			}
		} else {
			resultMap.put("pathStr", "K");
		}
		return resultMap;
	}
	/**
	 * 公会管理下载模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/downloadHrSendAddress")
	public void downloadHrSendAddress(HttpServletRequest request,HttpServletResponse response,
							ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();

		/*htm+='<th class="td_center">发令日期</th>';
		htm+='<th class="td_center">生效日期</th>';
		htm+='<th class="td_center">社号</th>';
		htm+='<th class="td_center">姓名</th>';
		htm+='<th class="td_center">现部门</th>';
		htm+='<th class="td_center">现职位</th>';
		htm+='<th class="td_center">现职责</th>';
		htm+='<th class="td_center">现职级</th>';
		htm+='<th class="td_center">现员工类型</th>';
		htm+='<th class="td_center">工作地</th>';
		htm+='<th class="td_center">派遣地</th>';
		htm+='<th class="td_center">备注</th>';*/
		aliasNameList.add("发令日期");
		aliasNameList.add("生效日期");
		aliasNameList.add("社号");
		aliasNameList.add("派遣地");
		aliasNameList.add("备注");
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "2012-06-01");
		map.put("CELL1", "2012-06-01");
		map.put("CELL2", "CH0000");
		map.put("CELL3", "北京");
		map.put("CELL4", "备注");

		
		list.add(map);
		String name = "派遣地";
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelByName(request, response, modelMap,
				sqlContentmap, aliasNameList, null,name);
	}
	
	/**
	 * 参保对象Excel导入模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportViewInsuranceObjectModule")
	public void exportViewInsuranceObjectModule(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		List aliasNameList = new ArrayList();
		aliasNameList.add("工资月*");
		aliasNameList.add("社号*");
		aliasNameList.add("姓名");
		aliasNameList.add("部门");
		aliasNameList.add("计算标识(社保)*");
		if(admin.getCpnyId().equals("LGEHN") || admin.getCpnyId().equals("LGEHZ") || admin.getCpnyId().equals("LGEYT") ){
		aliasNameList.add("计算标识(公积金)*");}
		aliasNameList.add("备注[可以为空]");//备注
		
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat ("yyyy-MM-dd");
		String ddate = "";
		ddate = sb.format(date);
		String b[] = ddate.split("-");
		String year = b[0].trim().toString();
		String month = b[1].trim().toString();
		int i = 5;
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", year+month);
		map.put("CELL1", "12000001");
		map.put("CELL2", "张三");
		map.put("CELL3", "人事部");
		map.put("CELL4", "Y");
		if(admin.getCpnyId().equals("LGEHN") || admin.getCpnyId().equals("LGEHZ") || admin.getCpnyId().equals("LGEYT")){
		map.put("CELL5", "Y");
			i=i+1;
		}
		map.put("CELL"+i, "");
		list.add(map);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		//this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
		String fileName = "viewInsObjectImport";
		this.excelUtilSer.exportIsParamDataExcel(request, response, modelMap,sqlContentmap, aliasNameList, null,fileName);
	}
	
	/**
	 * 考勤决裁例外Excel导入模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportArExceptionAffirmModle")
	public void exportArExceptionAffirmModle(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		List aliasNameList = new ArrayList();
		aliasNameList.add("社号");
		aliasNameList.add("姓名");
		aliasNameList.add("开始日期");
		aliasNameList.add("结束日期");
		aliasNameList.add("是否进行申请"+"("+"1表示申请,0表示不申请"+")");
		
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat ("yyyy-MM-dd");
		String ddate = "";
		ddate = sb.format(date);
		String b[] = ddate.split("-");
		String year = b[0].trim().toString();
		String month = b[1].trim().toString();
		String day = b[2].trim().toString();
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0","11111112");
		map.put("CELL1","admin");
		map.put("CELL2",year+month+day);
		map.put("CELL3", year+month+day);
		map.put("CELL4","1");
		//map.put("CELL2", "HR部");
		list.add(map);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		//this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
		String fileName = "viewArAffrimExceptionImport";
		this.excelUtilSer.exportIsParamDataExcel(request, response, modelMap,sqlContentmap, aliasNameList, null,fileName);
	}

	
	
	
	
	
	/**
	 * 导出数据时需要调用的方法 Description:动态组导出
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/exportDynamicGroupInfoList")
	public void exportDynamicGroupInfoList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String language = admin.getLanguage();
		String CPNYID = admin.getCpnyId();
		String PERSONID = admin.getPersonId();
		String adminId = admin.getAdminID();
		String GROUP_NO=request.getParameter("GROUP_NO");
		String NO=request.getParameter("NO");
		String KEY=request.getParameter("KEY");
		String DEPTNO=request.getParameter("DEPTNO");
		String EmpTypeCodeNo=request.getParameter("EmpTypeCodeNo");
		String EmpOffice=request.getParameter("EmpOffice");
		String JobTypeGroupNo=request.getParameter("JobTypeGroupNo");
		
		
		List aliasNameList = new ArrayList();
		List aliasList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage(
				"public.title.empId", request));//工号
		aliasNameList.add(TipMessage.getTipMessage(
				"public.title.name", request));//姓名
		aliasNameList.add(TipMessage.getTipMessage(
				"public.title.deptName", request));//部门

		aliasNameList.add(TipMessage.getTipMessage(
				"hr.viewPersonalInfo.title.STATUS_NAME", request));//员工状态

		aliasList.add("EMPID");
		aliasList.add("LOCAL_NAME");
		aliasList.add("DEPTNAME");
		aliasList.add("STATUS");

		String sqlContent =" SELECT HR.EMPID,"
				+" HR.PERSON_ID,"
				+" HR.LOCAL_NAME,"
				+" GET_DEPT_NAME(HR.DEPTNO,'"+language+"') DEPTNAME,"
				+" GET_CODE_NAME(HR.STATUS_CODE,'"+language+"') STATUS"
				+" FROM AR_EMP_GROUP       AR,"
				+	"HR_EMPLOYEE        HR"
				+" WHERE AR.PERSON_ID = HR.PERSON_ID(+)"
				+" AND EXISTS ("
				+" SELECT 1 FROM AR_SUPERVISOR_INFO B"
				+" WHERE B.DEPTNO = HR.DEPTNO"
				+" AND B.PERSON_ID = '"+PERSONID+"'"
				+" )";
			if(GROUP_NO!=null&&!GROUP_NO.equals("")){
				sqlContent+=" AND AR.GROUP_NO = '"+GROUP_NO+"' ";
			}
			if(NO!=null&&!NO.equals("")){
				sqlContent+=" AND AR.GROUP_NO = '"+NO+"'";
			}
			if(KEY!=null&&!KEY.equals("")){
				sqlContent+=" AND (HR.EMPID LIKE upper('%'||'"+KEY+"'||'%' ) " 
						+" OR  HR.LOCAL_NAME LIKE upper('%'||'"+KEY+"'||'%')"
						+" OR  HR.CHINESE_PINYIN LIKE upper('%'||'"+KEY+"'||'%'))";
			}
			if(DEPTNO!=null&&!DEPTNO.equals("")){
				sqlContent+=" AND  EXISTS (SELECT * FROM HR_DEPARTMENT  "
						+" WHERE HR.DEPTNO=HR_DEPARTMENT.DEPTNO"
						+" START WITH HR_DEPARTMENT.DEPTNO =#DEPTNO:VARCHAR# "
						+" CONNECT BY PRIOR HR_DEPARTMENT.DEPTNO = HR_DEPARTMENT.PARENT_DEPT_NO )";
			}
			if(EmpTypeCodeNo!=null&&!EmpTypeCodeNo.equals("")){
				sqlContent+= " AND HR.EMP_TYPE_CODE = '"+EmpTypeCodeNo+"'";

			}
			if(EmpOffice!=null&&!EmpOffice.equals("")){
				sqlContent+= " AND   HR.emp_office ='"+EmpOffice+"'";

			}
			sqlContent+= " ORDER BY HR.EMPID ,HR.DEPTNO";
		LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
		map.put("sqlContent", sqlContent);
		String name="viewDynamicGroup_list";
		this.excelUtilSer.exportExcelByName(request, response, modelMap, map,
				aliasNameList, aliasList, name);
	}
	
	/**
	 * excel模板下载
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadExcelTemplate")
	public void downloadExcelTemplate(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String CODE_NO_SQL = "SELECT T.CODE_NO || ' > ' || SY.CONTENT DESCRIPTION, T.CODE_NO, SY.CONTENT CODENAME FROM SY_CODE T, SY_CODE_PARAM SP, SY_GLOBAL_NAME SY"
			+ " WHERE T.CODE_NO = SY.NO(+) AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '" + admin.getCpnyId() + "' "
			+ " AND SY.LANGUAGE(+) = '" + admin.getLanguage() + "' "
			+ " AND T.ACTIVITY = 1 "
			+ " AND T.PARENT_CODE_NO IN ( '";
		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' ORDER BY ORDER_NO ";
		String COST_CENTER_SQL = "SELECT CODE_NO || ' > ' || CODE_NAME DESCRIPTION, CODE_NO,CODE_NAME CODENAME FROM ORG_COST_CENTER WHERE ACTIVITY = '14013912' AND CPNY_ID = '" + admin.getCpnyId() + "' ";
		//String WORK_AREA_SQL = "SELECT CODE_NO || ' > ' || CODE_NAME DESCRIPTION, CODE_NO,CODE_NAME CODENAME FROM ORG_WORK_AREA WHERE ACTIVITY = '14013912' AND CPNY_ID = '" + admin.getCpnyId() + "' ";
		
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理C:\Users\Administrator\AppData\Local\Temp
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List rs = empInfoSer.getCodeList("1359", request); //入社区分
			List rsxj = empInfoSer.getCodeListBySql(CODE_NO_SQL + "400414','400435','400429' )");//入社详细区分
			List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL); //系统部门
			List zq = empInfoSer.getCodeList("14015812", request); //职群
			List dj = empInfoSer.getCodeListBySql(CODE_NO_SQL + "14015813','14015815' )"); //职级
			List zyyw = empInfoSer.getCodeList("400098", request); //主要业务
			List zz = empInfoSer.getCodeList("14014036", request); //职责
			List yglx = empInfoSer.getCodeList("13864", request); //员工类型
			//List cbzx = empInfoSer.getCodeListBySql(COST_CENTER_SQL); //成本中心
			List zzxl = empInfoSer.getCodeList("13769", request); //最终学历代码
			List xb = empInfoSer.getCodeList("1324", request); //性别
			List gj = empInfoSer.getCodeList("870", request); //国籍
			List minzu = empInfoSer.getCodeList("210942", request); //民族
			List jhqf = empInfoSer.getCodeList("1709", request); //结婚区分
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,rs,0);
				this.composeTemplateCodeInfo(wb,rsxj,4);
				this.composeTemplateCodeInfo(wb,dept,8);
				this.composeTemplateCodeInfo(wb,dj,12);
				this.composeTemplateCodeInfo(wb,zyyw,16);
				this.composeTemplateCodeInfo(wb,zz,20);
				this.composeTemplateCodeInfo(wb,yglx,24);
				//this.composeTemplateCodeInfo(wb,cbzx,28);
				this.composeTemplateCodeInfo(wb,zq,28);
				this.composeTemplateCodeInfo(wb,zzxl,32);
				this.composeTemplateCodeInfo(wb,xb,36);
				this.composeTemplateCodeInfo(wb,gj,40);
				this.composeTemplateCodeInfo(wb,minzu,44);
				this.composeTemplateCodeInfo(wb,jhqf,48);
				this.createName(wb, "rs", "TemplateCode!$A$2:$A$" + (rs == null ? 2 : rs.size() + 1));
				this.createName(wb, "rsxj", "TemplateCode!$E$2:$E$" + (rsxj == null ? 2 : rsxj.size() + 1));
				this.createName(wb, "dept", "TemplateCode!$I$2:$I$" + (dept == null ? 2 : dept.size() + 1));
				this.createName(wb, "dj", "TemplateCode!$M$2:$M$" + (dj == null ? 2 : dj.size() + 1));
				this.createName(wb, "zyyw", "TemplateCode!$Q$2:$Q$" + (zyyw == null ? 2 : zyyw.size() + 1));
				this.createName(wb, "zz", "TemplateCode!$U$2:$U$" + (zz == null ? 2 : zz.size() + 1));
				this.createName(wb, "yglx", "TemplateCode!$Y$2:$Y$" + (yglx == null ? 2 : yglx.size() + 1));
				//this.createName(wb, "cbzx", "TemplateCode!$AC$2:$AC$" + (cbzx == null ? 2 : cbzx.size() + 1));
				this.createName(wb, "zq", "TemplateCode!$AC$2:$AC$" + (zq == null ? 2 : zq.size() + 1));
				this.createName(wb, "zzxl", "TemplateCode!$AG$2:$AG$" + (zzxl == null ? 2 : zzxl.size() + 1));
				this.createName(wb, "xb", "TemplateCode!$AK$2:$AK$" + (xb == null ? 2 : xb.size() + 1));
				this.createName(wb, "gj", "TemplateCode!$AO$2:$AO$" + (gj == null ? 2 : gj.size() + 1));
				this.createName(wb, "minzu", "TemplateCode!$AS$2:$AS$" + (minzu == null ? 2 : minzu.size() + 1));
				this.createName(wb, "jhqf", "TemplateCode!$AW$2:$AW$" + (jhqf == null ? 2 : jhqf.size() + 1));
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	/**
	 * excel模板下载
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadExcelTemplateHAE")
	public void downloadExcelTemplateHAE(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String CODE_NO_SQL = "SELECT NVL(T.DESCRIPTION,T.CODE_NO) || ' > ' || SY.CONTENT DESCRIPTION, T.CODE_NO, SY.CONTENT CODENAME FROM SY_CODE T, SY_CODE_PARAM SP, SY_GLOBAL_NAME SY"
			+ " WHERE T.CODE_NO = SY.NO(+) AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '" + admin.getCpnyId() + "' "
			+ " AND SY.LANGUAGE(+) = '" + admin.getLanguage() + "' "
			+ " AND T.ACTIVITY = 1 "
			+ " AND T.PARENT_CODE_NO IN ( '";
		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' ORDER BY ORDER_NO ";
		String COST_CENTER_SQL = "SELECT CODE_NO || ' > ' || CODE_NAME DESCRIPTION, CODE_NO,CODE_NAME CODENAME FROM ORG_COST_CENTER WHERE ACTIVITY = '14013912' AND CPNY_ID = '" + admin.getCpnyId() + "' ";
		//String WORK_AREA_SQL = "SELECT CODE_NO || ' > ' || CODE_NAME DESCRIPTION, CODE_NO,CODE_NAME CODENAME FROM ORG_WORK_AREA WHERE ACTIVITY = '14013912' AND CPNY_ID = '" + admin.getCpnyId() + "' ";
		
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理C:\Users\Administrator\AppData\Local\Temp
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List rs = empInfoSer.getCodeList("1359", request); //入社区分
			List rsxj = empInfoSer.getCodeListBySql(CODE_NO_SQL + "400414','400435','400429' )");//入社详细区分
			List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL); //系统部门
			List dj = empInfoSer.getCodeListBySql(CODE_NO_SQL + "14015814','14015815' )"); //职级
			List nzdj = empInfoSer.getCodeList("14016325", request); //年资等级
			List zyyw = empInfoSer.getCodeList("400098", request); //主要业务
			List zq = empInfoSer.getCodeList("14015812", request); //职群
			List zz = empInfoSer.getCodeList("14014036", request); //职责
			List yglx = empInfoSer.getCodeList("13864", request); //员工类型
			List cbzx = empInfoSer.getCodeListBySql(COST_CENTER_SQL); //成本中心
			List zzxl = empInfoSer.getCodeList("13769", request); //最终学历代码
			List bycj = empInfoSer.getCodeList("14014324", request); //毕业成绩
			List wynl = empInfoSer.getCodeList("14015514", request); //外语能力
			List xb = empInfoSer.getCodeList("1324", request); //性别
			List gj = empInfoSer.getCodeList("870", request); //国籍
			List minzu = empInfoSer.getCodeList("210942", request); //民族
			List jhqf = empInfoSer.getCodeList("1709", request); //结婚区分
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,rs,0);
				this.composeTemplateCodeInfo(wb,rsxj,4);
				this.composeTemplateCodeInfo(wb,dept,8);
				this.composeTemplateCodeInfo(wb,dj,12);
				this.composeTemplateCodeInfo(wb,nzdj,16);
				this.composeTemplateCodeInfo(wb,zyyw,20);
				this.composeTemplateCodeInfo(wb,zq,24);
				this.composeTemplateCodeInfo(wb,zz,28);
				this.composeTemplateCodeInfo(wb,yglx,32);
				this.composeTemplateCodeInfo(wb,cbzx,36);
				this.composeTemplateCodeInfo(wb,zzxl,40);
				this.composeTemplateCodeInfo(wb,bycj,44);
				this.composeTemplateCodeInfo(wb,wynl,48);
				this.composeTemplateCodeInfo(wb,xb,52);
				this.composeTemplateCodeInfo(wb,gj,56);
				this.composeTemplateCodeInfo(wb,minzu,60);
				this.composeTemplateCodeInfo(wb,jhqf,64);
				this.createName(wb, "rs", "TemplateCode!$A$2:$A$" + (rs == null ? 2 : rs.size() + 1));
				this.createName(wb, "rsxj", "TemplateCode!$E$2:$E$" + (rsxj == null ? 2 : rsxj.size() + 1));
				this.createName(wb, "dept", "TemplateCode!$I$2:$I$" + (dept == null ? 2 : dept.size() + 1));
				this.createName(wb, "dj", "TemplateCode!$M$2:$M$" + (dj == null ? 2 : dj.size() + 1));
				this.createName(wb, "nzdj", "TemplateCode!$Q$2:$Q$" + (nzdj == null ? 2 : nzdj.size() + 1));
				this.createName(wb, "zyyw", "TemplateCode!$U$2:$U$" + (zyyw == null ? 2 : zyyw.size() + 1));
				this.createName(wb, "zq", "TemplateCode!$Y$2:$Y$" + (zq == null ? 2 : zq.size() + 1));
				this.createName(wb, "zz", "TemplateCode!$AC$2:$AC$" + (zz == null ? 2 : zz.size() + 1));
				this.createName(wb, "yglx", "TemplateCode!$AG$2:$AG$" + (yglx == null ? 2 : yglx.size() + 1));
				this.createName(wb, "cbzx", "TemplateCode!$AK$2:$AK$" + (cbzx == null ? 2 : cbzx.size() + 1));
				this.createName(wb, "zzxl", "TemplateCode!$AO$2:$AO$" + (zzxl == null ? 2 : zzxl.size() + 1));
				this.createName(wb, "bycj", "TemplateCode!$AS$2:$AS$" + (bycj == null ? 2 : bycj.size() + 1));
				this.createName(wb, "wynl", "TemplateCode!$AW$2:$AW$" + (wynl == null ? 2 : wynl.size() + 1));
				this.createName(wb, "xb", "TemplateCode!$BA$2:$BA$" + (xb == null ? 2 : xb.size() + 1));
				this.createName(wb, "gj", "TemplateCode!$BE$2:$BE$" + (gj == null ? 2 : gj.size() + 1));
				this.createName(wb, "minzu", "TemplateCode!$BI$2:$BI$" + (minzu == null ? 2 : minzu.size() + 1));
				this.createName(wb, "jhqf", "TemplateCode!$BM$2:$BM$" + (jhqf == null ? 2 : jhqf.size() + 1));
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	/**
	 * excel模板下载     HAE简历录入
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadExcelAddRecTemplate")
	public void downloadExcelAddRecTemplate(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理C:\Users\Administrator\AppData\Local\Temp
		if (admin.getCpnyId() == "HAE" || "HAE".equals(admin.getCpnyId())) {
			try {
				LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
				List xb = empInfoSer.getCodeList("1324", request); //性别
				List gj = empInfoSer.getCodeList("870", request); //国籍
				List jhqf = empInfoSer.getCodeList("1709", request); //结婚区分
				List xl = empInfoSer.getCodeList("13769", request); //学历
				List bycj = empInfoSer.getCodeList("14014324", request); //毕业成绩
				List wynl = empInfoSer.getCodeList("14015514", request); //外语能力
				
				InputStream is = new FileInputStream(templateFileName ); 
				try {
					Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
					this.composeTemplateCodeInfo(wb,xb,0);
					this.composeTemplateCodeInfo(wb,gj,4);
					this.composeTemplateCodeInfo(wb,jhqf,8);
					this.composeTemplateCodeInfo(wb,xl,12);
					this.composeTemplateCodeInfo(wb,bycj,16);
					this.composeTemplateCodeInfo(wb,wynl,20);
					this.createName(wb, "xb", "TemplateCode!$A$2:$A$" + (xb == null ? 2 : xb.size() + 1));
					this.createName(wb, "gj", "TemplateCode!$E$2:$E$" + (gj == null ? 2 : gj.size() + 1));
					this.createName(wb, "jhqf", "TemplateCode!$I$2:$I$" + (jhqf == null ? 2 : jhqf.size() + 1));
					this.createName(wb, "xl", "TemplateCode!$M$2:$M$" + (xl == null ? 2 : xl.size() + 1));
					this.createName(wb, "bycj", "TemplateCode!$Q$2:$Q$" + (bycj == null ? 2 : bycj.size() + 1));
					this.createName(wb, "wynl", "TemplateCode!$U$2:$U$" + (wynl == null ? 2 : wynl.size() + 1));
					
					OutputStream os = new FileOutputStream(destFileName);
					wb.write(os);
			        is.close();
			        os.flush();
			        os.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (admin.getCpnyId() == "HTSV" || "HTSV".equals(admin.getCpnyId())) {
			try {
				LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
				List xb = empInfoSer.getCodeList("1324", request); //性别
				List gj = empInfoSer.getCodeList("870", request); //国籍
				List gwqf = empInfoSer.getCodeList("90000339", request);//岗位区分
				List xl = empInfoSer.getCodeList("13769", request); //学历
				List zjlx = empInfoSer.getCodeList("4575", request); //证件类型
				List jhqf = empInfoSer.getCodeList("1709", request); //结婚区分
				
				InputStream is = new FileInputStream(templateFileName ); 
				try {
					Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
					this.composeTemplateCodeInfo(wb,xb,0);
					this.composeTemplateCodeInfo(wb,gj,4);
					this.composeTemplateCodeInfo(wb,gwqf,8);
					this.composeTemplateCodeInfo(wb,xl,12);
					this.composeTemplateCodeInfo(wb,zjlx,16);
					this.composeTemplateCodeInfo(wb,jhqf,20);
					this.createName(wb, "xb", "TemplateCode!$A$2:$A$" + (xb == null ? 2 : xb.size() + 1));
					this.createName(wb, "gj", "TemplateCode!$E$2:$E$" + (gj == null ? 2 : gj.size() + 1));
					this.createName(wb, "gwqf", "TemplateCode!$I$2:$I$" + (gwqf == null ? 2 : gwqf.size() + 1));
					this.createName(wb, "xl", "TemplateCode!$M$2:$M$" + (xl == null ? 2 : xl.size() + 1));
					this.createName(wb, "zjlx", "TemplateCode!$Q$2:$Q$" + (zjlx == null ? 2 : zjlx.size() + 1));
					this.createName(wb, "jhqf", "TemplateCode!$U$2:$U$" + (jhqf == null ? 2 : jhqf.size() + 1));
					
					OutputStream os = new FileOutputStream(destFileName);
					wb.write(os);
			        is.close();
			        os.flush();
			        os.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	@RequestMapping("/downloadExcelTemplateReward")
	public void downloadExcelTemplateReward(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/SPC_SH/" + fileName + ".xls";
		destFileName +=  "/resources/template/SPC_SH/" + fileName + "_out.xlsx";
		// execl导出处理C:\Users\Administrator\AppData\Local\Temp
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
		
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
		String POST_GRADE_SQL = "SELECT S.CODE_NO || ' > ' || get_global_name(S.CODE_NO, '"+admin.getLanguage()+"') DESCRIPTION, S.CODE_NO,get_global_name(S.CODE_NO, '"+admin.getLanguage()+"') CODENAME " +
				"FROM SY_CODE S,SY_CODE_PARAM SP WHERE S.CODE_NO=SP.CODE_NO(+)  AND S.PARENT_CODE_NO in ('14015813', '14015815') AND SP.CPNY_ID = '" + admin.getCpnyId() + "' ";
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List rs = empInfoSer.getCodeList("14013956", request); //发令区分
			List rsxj = empInfoSer.getCodeListBySql(PARENT_CODE_NO_SQL + "14013956' )"); //发令原因
			List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL); //部门
			List zq = empInfoSer.getCodeList("14015812", request); //职群
			List zj = empInfoSer.getCodeListBySql(POST_GRADE_SQL); //职级
			List zz = empInfoSer.getCodeList("14014036", request); //职责
			List yglx = empInfoSer.getCodeList("13864", request); //员工类型
			List zyyw = empInfoSer.getCodeList("400098", request); //主要业务
			List cbzx = empInfoSer.getCodeListBySql(COST_CENTER_SQL); //成本中心
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,rs,0);
				this.composeTemplateCodeInfo(wb,rsxj,4);
				this.composeTemplateCodeInfo(wb,dept,8);
				this.composeTemplateCodeInfo(wb,zq,12);
				this.composeTemplateCodeInfo(wb,zj,16);
				this.composeTemplateCodeInfo(wb,zz,20);
				this.composeTemplateCodeInfo(wb,yglx,24);
				this.composeTemplateCodeInfo(wb,zyyw,28);
				this.composeTemplateCodeInfo(wb,cbzx,32);
				this.createName(wb, "rs", "TemplateCode!$A$2:$A$" + (rs == null ? 2 : rs.size() + 1));
				this.createName(wb, "rsxj", "TemplateCode!$E$2:$E$" + (rsxj == null ? 2 : rsxj.size() + 1));
				this.createName(wb, "dept", "TemplateCode!$I$2:$I$" + (dept == null ? 2 : dept.size() + 1));
				this.createName(wb, "zq", "TemplateCode!$M$2:$M$" + (zq == null ? 2 : zq.size() + 1));
				this.createName(wb, "zj", "TemplateCode!$Q$2:$Q$" + (zj == null ? 2 : zj.size() + 1));
				this.createName(wb, "zz", "TemplateCode!$U$2:$U$" + (zz == null ? 2 : zz.size() + 1));
				this.createName(wb, "yglx", "TemplateCode!$Y$2:$Y$" + (yglx == null ? 2 : yglx.size() + 1));
				this.createName(wb, "zyyw", "TemplateCode!$AC$2:$AC$" + (zyyw == null ? 2 : zyyw.size() + 1));
				this.createName(wb, "cbzx", "TemplateCode!$AG$2:$AG$" + (cbzx == null ? 2 : cbzx.size() + 1));
				
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	
	/**
	 * excel模板下载   HEA
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadExcelExpTemplateHAE")
	public void downloadExcelExpTemplateHAE(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String PARENT_CODE_NO_SQL = "SELECT NVL(T.DESCRIPTION,T.CODE_NO) || ' > ' || SY.CONTENT DESCRIPTION, T.CODE_NO, SY.CONTENT CODENAME FROM SY_CODE T, SY_CODE_PARAM SP, SY_GLOBAL_NAME SY"
			+ " WHERE T.CODE_NO = SY.NO(+) AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '" + admin.getCpnyId() + "' "
			+ " AND SY.LANGUAGE(+) = '" + admin.getLanguage() + "' "
			+ " AND T.ACTIVITY = 1 "
			+ " AND T.PARENT_CODE_NO IN ( SELECT CODE_NO FROM SY_CODE WHERE PARENT_CODE_NO = '";
		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' ";
		String COST_CENTER_SQL = "SELECT CODE_NO || ' > ' || CODE_NAME DESCRIPTION, CODE_NO,CODE_NAME CODENAME FROM ORG_COST_CENTER WHERE ACTIVITY = '14013912' AND CPNY_ID = '" + admin.getCpnyId() + "' ";
		String WORK_AREA_SQL = "SELECT CODE_NO || ' > ' || CODE_NAME DESCRIPTION, CODE_NO,CODE_NAME CODENAME FROM ORG_WORK_AREA WHERE ACTIVITY = '14013912' AND CPNY_ID = '" + admin.getCpnyId() + "' ";
		String POST_GRADE_SQL = "SELECT S.CODE_NO || ' > ' || get_global_name(S.CODE_NO, '"+admin.getLanguage()+"') DESCRIPTION, S.CODE_NO,get_global_name(S.CODE_NO, '"+admin.getLanguage()+"') CODENAME " +
				"FROM SY_CODE S,SY_CODE_PARAM SP WHERE S.CODE_NO=SP.CODE_NO(+)  AND S.PARENT_CODE_NO in ('14015813', '14015815') AND SP.CPNY_ID = '" + admin.getCpnyId() + "' ";
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List rs = empInfoSer.getCodeList("14013956", request); //发令区分
			List rsxj = empInfoSer.getCodeListBySql(PARENT_CODE_NO_SQL + "14013956' )"); //发令原因
			List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL); //部门
			List zq = empInfoSer.getCodeList("14015812", request); //职群
			List zj = empInfoSer.getCodeListBySql(POST_GRADE_SQL); //职级
			List zz = empInfoSer.getCodeList("14014036", request); //职责
			List yglx = empInfoSer.getCodeList("13864", request); //员工类型
			List zyyw = empInfoSer.getCodeList("400098", request); //主要业务
			List cbzx = empInfoSer.getCodeListBySql(COST_CENTER_SQL); //成本中心
			List nzdj = empInfoSer.getCodeList("14016325", request); //年资等级
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,rs,0);
				this.composeTemplateCodeInfo(wb,rsxj,4);
				this.composeTemplateCodeInfo(wb,dept,8);
				this.composeTemplateCodeInfo(wb,zq,12);
				this.composeTemplateCodeInfo(wb,zj,16);
				this.composeTemplateCodeInfo(wb,zz,20);
				this.composeTemplateCodeInfo(wb,yglx,24);
				this.composeTemplateCodeInfo(wb,zyyw,28);
				this.composeTemplateCodeInfo(wb,cbzx,32);
				this.composeTemplateCodeInfo(wb,nzdj,36);
				this.createName(wb, "rs", "TemplateCode!$A$2:$A$" + (rs == null ? 2 : rs.size() + 1));
				this.createName(wb, "rsxj", "TemplateCode!$E$2:$E$" + (rsxj == null ? 2 : rsxj.size() + 1));
				this.createName(wb, "dept", "TemplateCode!$I$2:$I$" + (dept == null ? 2 : dept.size() + 1));
				this.createName(wb, "zq", "TemplateCode!$M$2:$M$" + (zq == null ? 2 : zq.size() + 1));
				this.createName(wb, "zj", "TemplateCode!$Q$2:$Q$" + (zj == null ? 2 : zj.size() + 1));
				this.createName(wb, "zz", "TemplateCode!$U$2:$U$" + (zz == null ? 2 : zz.size() + 1));
				this.createName(wb, "yglx", "TemplateCode!$Y$2:$Y$" + (yglx == null ? 2 : yglx.size() + 1));
				this.createName(wb, "zyyw", "TemplateCode!$AC$2:$AC$" + (zyyw == null ? 2 : zyyw.size() + 1));
				this.createName(wb, "cbzx", "TemplateCode!$AG$2:$AG$" + (cbzx == null ? 2 : cbzx.size() + 1));
				this.createName(wb, "nzdj", "TemplateCode!$AK$2:$AK$" + (nzdj == null ? 2 : nzdj.size() + 1));

				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 *  工资数据批量导入Excel导入模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	
	
	@RequestMapping("/downloadPaExcelTemplate")
	public void downloadPaExcelTemplate(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String item_code="";
		String[] names=request.getParameterValues("PARAM_NO");
		if(names!=null&&names.length>0){
			
			for (int i = 0; i < names.length; i++) {
				if(i==names.length-1){
					item_code+="'"+names[i]+"'";
				}else{
					
					item_code+="'"+names[i]+"',";
				}
			}
			
		}else{
			item_code="''";
			
		}
		
		String PA_SQL = 
			" SELECT DISTINCT   GET_GLOBAL_NAME(T.PARAM_ITEM_NO, '"+admin.getLanguage()+"') DESCRIPTION,"+
            " T.PARAM_NO CODE_NO,"+
            " GET_GLOBAL_NAME(T.PARAM_ITEM_NO, '"+admin.getLanguage()+"') CODENAME "+
            "  FROM PA_PARAM_ITEM_PARAM T"+
   "  WHERE T.DISTINCT_FIELD = 'PERSON_ID' "+
   " AND T.cpny_id='"+admin.getCpnyId()+"'"+
      " AND T.PARAM_ITEM_NO in ("+item_code+")";
			
			;
		
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + fileName + "_out.xlsx";
		// execl导出处理
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			
			List paItem = empInfoSer.getCodeListBySql(PA_SQL); //工作地

		
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				
				this.composeTemplateCodeInfo(wb,paItem,0);
				
				
				this.createName(wb, "pa", "TemplateCode!$A$2:$A$" + (paItem == null ? 2 : paItem.size() + 1));
				
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	
	/**
	 * excel模板下载 小时工入职
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadTempEmpExcelTemplate")
	public void downloadTempEmpExcelTemplate(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' ";
		
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理C:\Users\Administrator\AppData\Local\Temp
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL); //部门

			List xb = empInfoSer.getCodeList("1324", request); //性别
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,dept,0);
				this.composeTemplateCodeInfo(wb,xb,4);
				this.createName(wb, "dept", "TemplateCode!$A$2:$A$" + (dept == null ? 2 : dept.size() + 1));
				this.createName(wb, "xb", "TemplateCode!$E$2:$E$" + (xb == null ? 2 : xb.size() + 1));
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	/**
	 * excel模板下载 考勤
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadExcelAttendanceApply")
	public void downloadExcelAttendanceApply(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' ";
		String atendanceSql = "SELECT T.CODE_NO || ' > ' || SY.CONTENT DESCRIPTION, T.CODE_NO, SY.CONTENT CODENAME FROM SY_CODE T, SY_CODE_PARAM SP, SY_GLOBAL_NAME SY WHERE T.CODE_NO = SY.NO(+) AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '" + admin.getCpnyId() + "'"+
					"AND SY.LANGUAGE(+) = '" + admin.getLanguage() + "' AND T.PARENT_CODE_NO = '21' AND T.ACTIVITY = 1 ORDER BY T.ORDERNO";
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理C:\Users\Administrator\AppData\Local\Temp
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL); //部门
			List xb = empInfoSer.getCodeListBySql(atendanceSql); //考勤类型
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,dept,0);
				this.composeTemplateCodeInfo(wb,xb,4);
				this.createName(wb, "dept", "TemplateCode!$A$2:$A$" + (dept == null ? 2 : dept.size() + 1));
				this.createName(wb, "xb", "TemplateCode!$E$2:$E$" + (xb == null ? 2 : xb.size() + 1));
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	/**
	 * excel模板下载 考勤
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadExcelPaEmp")
	public void downloadExcelPaEmp(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' ";

		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理C:\Users\Administrator\AppData\Local\Temp
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL); //部门
			List bankType = empInfoSer.getCodeList("14015883", request);
			
			InputStream is = new FileInputStream(templateFileName); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,dept,0);
				this.composeTemplateCodeInfo(wb,bankType,4);
				this.createName(wb, "dept", "TemplateCode!$A$2:$A$" + (dept == null ? 2 : dept.size() + 1));
				this.createName(wb, "bankType", "TemplateCode!$E$2:$E$" + (bankType == null ? 2 : bankType.size() + 1));
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	
	
	/**
	 * excel模板下载 培训信息
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	
	@RequestMapping("/downloadExcelTrain")
	public void downloadExcelTrain(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理C:\Users\Administrator\AppData\Local\Temp
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			
			List bankType = empInfoSer.getCodeList1("123459", request);//培训区分
			List dept = empInfoSer.getCodeList1("123271", request); //培训形式
			List trainType = empInfoSer.getCodeList1("14015534", request); //培训结果
			
			InputStream is = new FileInputStream(templateFileName); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,dept,0);
				this.composeTemplateCodeInfo(wb,bankType,4);
				this.composeTemplateCodeInfo(wb,trainType,8);
				this.createName(wb, "dept", "TemplateCode!$A$2:$A$" + (dept == null ? 2 : dept.size() + 1));
				this.createName(wb, "bankType", "TemplateCode!$E$2:$E$" + (bankType == null ? 2 : bankType.size() + 1));
				this.createName(wb, "trainType", "TemplateCode!$I$2:$I$" + (trainType == null ? 2 : trainType.size() + 1));

				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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

	
	/**
	 * excel模板下载 加班
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadExcelOtApply")
	public void downloadExcelOtApply(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' ";
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";

		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL); //部门
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,dept,0);
				this.createName(wb, "dept", "TemplateCode!$A$2:$A$" + (dept == null ? 2 : dept.size() + 1));
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	/**
	 * excel模板下载
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadExcelDeptTemplate")
	public void downloadExcelDeptTemplate(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' ";
		String WORK_AREA_SQL = "SELECT CODE_NO || ' > ' || CODE_NAME DESCRIPTION, CODE_NO,CODE_NAME CODENAME FROM ORG_WORK_AREA WHERE ACTIVITY = '14013912' AND CPNY_ID = '" + admin.getCpnyId() + "' ";
		
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理C:\Users\Administrator\AppData\Local\Temp
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL); //部门
			List zzlx = empInfoSer.getCodeList("14013950", request); //组织类型
			List gzd = empInfoSer.getCodeListBySql(WORK_AREA_SQL); //工作地
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,dept,0);
				this.composeTemplateCodeInfo(wb,gzd,4);
				this.composeTemplateCodeInfo(wb,zzlx,8);

				this.createName(wb, "dept", "TemplateCode!$A$2:$A$" + (dept == null ? 2 : dept.size() + 1));
				this.createName(wb, "gzd", "TemplateCode!$E$2:$E$" + (gzd == null ? 2 : gzd.size() + 1));
				this.createName(wb, "zzlx", "TemplateCode!$I$2:$I$" + (zzlx == null ? 2 : zzlx.size() + 1));
				
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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

	/**
	 * excel模板下载 小时工入职
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadTempEmpSalExcelTemplate")
	public void downloadTempEmpSalExcelTemplate(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' ";
		
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理C:\Users\Administrator\AppData\Local\Temp
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL); //部门
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,dept,0);
				this.createName(wb, "dept", "TemplateCode!$A$2:$A$" + (dept == null ? 2 : dept.size() + 1));
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	/**
	 * excel模板下载
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadChangeShopTemplate")
	public void downloadChangeShopTemplate(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' AND DEPT_TYPE = '14015496' ORDER BY ORDER_NO ";
		
		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + fileName + "_out.xlsx";
		// execl导出处理C:\Users\Administrator\AppData\Local\Temp
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List dept = empInfoSer.getCodeListBySql(DEPT_NO_SQL); //部门
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,dept,0);
				this.createName(wb, "dept", "TemplateCode!$A$2:$A$" + (dept == null ? 2 : dept.size() + 1));
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	/**
	 * 排班导入
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadShopShiftExcelTemplate")
	public void downloadShopShiftExcelTemplate(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理C:\Users\Administrator\AppData\Local\Temp
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List item = this.tempEmpSer.viewTempEmpList(request,"getShiftItemList"); //性别

			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,item,0);
				this.createName(wb, "item", "TemplateCode!$A$2:$A$" + (item == null ? 2 : item.size() + 1));
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	/**
	 * excel模板下载
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadExcelArSummary")
	public void downloadExcelArSummary(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
	
	/**
	 * excel模板下载 个人工作日历
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadExcelEmpCalendarTemplate")
	public void downloadExcelEmpCalendarTemplate(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String WORK_AREA_SQL = "SELECT T.SHIFT_NO || ' > ' || S.CONTENT DESCRIPTION, T.SHIFT_NO CODE_NO,S.CONTENT CODENAME FROM AR_SHIFT010 T, SY_GLOBAL_NAME S "
				+ "WHERE T.ACTIVITY = 1 AND T.SHIFT_NO = S.NO(+) AND S.LANGUAGE(+) = '"+admin.getLanguage()+"' AND CPNY_ID = '" + admin.getCpnyId() + "' ";

		String fileName = request.getParameter("file");
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/" + admin.getCpnyId() + "/" + fileName + ".xls";
		destFileName +=  "/resources/template/" + admin.getCpnyId() + "/" + fileName + "_out.xlsx";
		// execl导出处理C:\Users\Administrator\AppData\Local\Temp
		try {
			LinkedHashMap datamap = ObjectBindUtil.getRequestParamData(request);
			List dt = empInfoSer.getCodeList("1439", request); //Date type

			List sc = empInfoSer.getCodeListBySql(WORK_AREA_SQL); //Shift
			
			InputStream is = new FileInputStream(templateFileName ); 
			try {
				Workbook wb = ExcelTemplateUtil.transformXLS(is, datamap);
				this.composeTemplateCodeInfo(wb,dt,0);
				this.composeTemplateCodeInfo(wb,sc,4);
				this.createName(wb, "dept", "TemplateCode!$A$2:$A$" + (dt == null ? 2 : dt.size() + 1));
				this.createName(wb, "xb", "TemplateCode!$E$2:$E$" + (sc == null ? 2 : sc.size() + 1));
				OutputStream os = new FileOutputStream(destFileName);
				wb.write(os);
		        is.close();
		        os.flush();
		        os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
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
