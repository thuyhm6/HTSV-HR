package com.ait.pa.action.salary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.pa.service.salary.PaResultSer;
import com.ait.pa.service.salary.PortalSer;
import com.ait.report.hr.service.HrReportSer;
import com.ait.sys.action.PostCtroller;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.exception.ConfigurationException;
import com.ait.web.task.uploadHrToSapIFData;
import com.ait.web.util.IthrC01ToPortalIfUtil;
import com.ait.web.util.IthrC12ToPortalIfUtil;
import com.ait.web.util.IthrToPortalIfUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;

/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName ControlCtroller.java
 * @author lufeng(lufeng@ait.net.cn)
 * @Date 2012-3-7 下午05:22:19
 * @version 5.0
 *
 */
@Controller
@RequestMapping(value = "/pa/salary")
public class PortalCtroller {
	Logger logger = Logger.getLogger(PostCtroller.class);

	@Autowired
	private PortalSer portalSer;

	@Autowired
	private  HrReportSer hrReportSer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private PaResultSer paResultSer ;
	
	public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");
	/**
	 * 跳转到Portal的查看页面(Jump to Portal list)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPortalSap")
	public ModelAndView viewInterfaceInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List portalLogList = this.portalSer.getPortalInfoList(request);
		int portalLogListCnt = this.portalSer.getPortalInfoListCnt(request) ;
		modelMap.put("portalLogList", portalLogList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, portalLogListCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2406")) ;
		
		return new ModelAndView("/pa/salary/viewPortalSap", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPortalSapItem")
	public ModelAndView viewPortalSapItem(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.addAllAttributes(this.paResultSer.getPaPortalAllItem(request)) ;
		List portalLogList = this.portalSer.getSapItemList(request);
		modelMap.put("portalLogList", portalLogList);
		
		return new ModelAndView("/pa/salary/viewPortalSapItem",modelMap);
	}
	
	/**
	 * 导出数据时需要调用的方法（url传参太多的时候用） Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/addItemToSap")
	@ResponseBody
	public LinkedHashMap addItemToSap(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasList = new ArrayList();
		String paramNumStr = request.getParameter("paramNum");
		if (paramNumStr != null && !paramNumStr.equals("")) {
			int paramNum = Integer.parseInt(paramNumStr);
			for (int i = 1; i < paramNum; i++) {
				LinkedHashMap sapMap = new LinkedHashMap();
				String aliasValue = request.getParameter("alias" + i);
				sapMap.put("ITEM_ID", aliasValue);
				sapMap.put("CREATED_BY", admin.getPersonId());
				sapMap.put("CPNY_ID", admin.getCpnyId());	
				aliasList.add(sapMap);
			}
			
		}
		LinkedHashMap resultMap = new LinkedHashMap();
		if(this.portalSer.deleteSAPItem(request) == 1){			
			if(this.portalSer.addSAPItem(request,aliasList) == 1){
				resultMap.put("pathStr", "Y");
			}else{
				resultMap.put("pathStr", "N");
			}
		}else{
			resultMap.put("pathStr", "K");
		}
		return resultMap;
	}
	
	/**
	 * 跳转到C01的SAP日志查看页面(Jump to SAP Log info for query )
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPortalC01Sap")
	public ModelAndView viewC01SapInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List portalLogList = this.portalSer.getPortalInfoList(request);
		int portalLogListCnt = this.portalSer.getPortalInfoListCnt(request) ;
		
		modelMap.put("portalLogList", portalLogList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, portalLogListCnt) ;
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "23723")) ;
		
		return new ModelAndView("/pa/salary/viewPortalC01Sap", modelMap);
	}
	
	/**
	 * 跳转到C01的生成SUMMARY后的信息查看页面(Jump to sap summary info pre)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewC01SapSummary")
	public ModelAndView viewC01SapSummaryList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List sapSummaryList = this.portalSer.getC01SapSummaryList(request);
		int sapSummaryListCnt = this.portalSer.getC01SapSummaryListCnt(request) ;
		
		modelMap.put("sapSummaryList", sapSummaryList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, sapSummaryListCnt) ;
		
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat ("yyyy-MM-dd");
		modelMap.put("SUM_GIVE_DATE", sb.format(date));
		modelMap.put("SUM_SEND_DATE", sb.format(date));
		
		String ddate = "";
		ddate = sb.format(date);
		String b[] = ddate.split("-");
		modelMap.put("sum_sapYear", b[0].trim().toString());
	    modelMap.put("sum_sapMonth", b[1].trim().toString());
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "23740")) ;
		
		return new ModelAndView("/pa/salary/viewC01SapSummary", modelMap);
	}
	
	/**
	 * 跳转到C01的生成SUMMARY信息查看页面(Jump to sap SUMMARY info pre)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewC01SapSummaryExcel")
	public ModelAndView viewC01SapSummaryExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List sapSummaryList = this.portalSer.getC01SapSummaryAllExcelList(request);
		int sapSummaryListCnt = this.portalSer.getC01SapSummaryListCnt(request) ;
		
		modelMap.put("sapSummaryList", sapSummaryList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, sapSummaryListCnt) ;
		
		return new ModelAndView("/pa/salary/viewC01SapSummaryExcel", modelMap);
	}
	
	/**
	 * 跳转到C01的生成SAP前的工资信息查看页面(Jump to sap pa info pre)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewC01PortalSapPre")
	public ModelAndView viewC01SapPaInfoPreList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List sapPaPreList = this.portalSer.getC01PaActualSalaryAllList(request);
		int sapPaPreListCnt = this.portalSer.getC01PaActualSalaryAllListCnt(request) ;
		
		modelMap.put("sapPaPreList", sapPaPreList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, sapPaPreListCnt) ;
		
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat ("yyyy-MM-dd");
		modelMap.put("FIRM_GIVE_DATE", sb.format(date));
		modelMap.put("FIRM_SEND_DATE", sb.format(date));
		
		String ddate = "";
		ddate = sb.format(date);
		String b[] = ddate.split("-");
		modelMap.put("firm_sapYear", b[0].trim().toString());
	    modelMap.put("firm_sapMonth", b[1].trim().toString());
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "23740")) ;
		
		return new ModelAndView("/pa/salary/viewC01PortalSapPre", modelMap);
	}
	
	/**
	 * 跳转到C01的生成SAP前的工资信息查看页面(Jump to sap pa info pre)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewC01PortalSapPreExcel")
	public ModelAndView viewC01PortalSapPreExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List sapPaPreList = this.portalSer.getC01PaActualSalaryAllExcelList(request);
		int sapPaPreListCnt = this.portalSer.getC01PaActualSalaryAllListCnt(request) ;
		
		modelMap.put("sapPaPreList", sapPaPreList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, sapPaPreListCnt) ;
		
		return new ModelAndView("/pa/salary/viewC01PortalSapPreExcel", modelMap);
	}
	
	/**
	 * 导出发送SAP信息有问题的人员信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSapErrorEmpExcel")
	public ModelAndView viewSapErrorEmmpExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List sapErrorEmpList = this.portalSer.getSapErrorEmpInfoList(request);
		
		modelMap.put("sapErrorEmpList", sapErrorEmpList);
		return new ModelAndView("/pa/salary/viewSapErrorEmpExcel", modelMap);
	}
	
	/**
	 * 将pa_month月份的闸北、嘉兴人员转为现金标志人员
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addZhaBeiAndJiaXingCash")
	@ResponseBody
	public String addZhaBeiAndJiaXingCash(HttpServletRequest request)throws Exception {
		String result = "";
	 	int logResult = -1 ;
	 	logResult = this.portalSer.addZhaBeiAndJiaXingCash(request);
	 	if(logResult==1){
	 		result = "Y";
	 	}else{
	 		result = "N";
		}

		return result;		
	}
	
	/**
	 * 导出薪资表里(85)闸北店,(87)嘉兴店的人员信息，因为这些人不通过转账系统转账，需要转换为现金人员导入系统
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewZhaBeiAndJiaXingEmpPaExcel")
	public ModelAndView viewZhaBeiAndJiaXingEmpPaExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List sapErrorEmpList = this.portalSer.getZhaBeiAndJiaXingEmpPaInfoList(request);
		
		modelMap.put("sapErrorEmpList", sapErrorEmpList);
		return new ModelAndView("/pa/salary/viewZhaBeiAndJiaXingEmpPaExcel", modelMap);
	}
	
	/**
	 * 跳转到C01的生成SAP后的工资信息查看页面(Jump to sap pa info after)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewC01PortalSapAfter")
	public ModelAndView viewC01SapPaInfoAfterList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat ("yyyy-MM-dd");
		modelMap.put("SEND_DATE", sb.format(date));
		modelMap.put("SAP_GIVE_DATE", sb.format(date));
		modelMap.put("SAP_SEND_DATE", sb.format(date));
		
		String ddate = "";
		ddate = sb.format(date);
		String b[] = ddate.split("-");
		modelMap.put("sapYear", b[0].trim().toString());
	    modelMap.put("sapMonth", b[1].trim().toString());
	    
		List sapPaAfterList = this.portalSer.getC01PaActualSalaryAfterList(request);
		int sapPaAfterListCnt = this.portalSer.getC01PaActualSalaryAfterListCnt(request) ;
		
		modelMap.put("sapPaAfterList", sapPaAfterList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, sapPaAfterListCnt) ;
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "23741")) ;
		
		return new ModelAndView("/pa/salary/viewC01PortalSapAfter", modelMap);
	}
	
	/**
	 * 跳转到C01的生成SAP后的工资信息导出页面(Jump to sap pa info pre)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewC01PortalSapAfterExcel")
	public ModelAndView viewC01PortalSapAfterExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List sapPaAfterList = this.portalSer.getC01PaActualSalaryAfterList(request);
		int sapPaAfterListCnt = this.portalSer.getC01PaActualSalaryAfterListCnt(request) ;
		
		modelMap.put("sapPaAfterList", sapPaAfterList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, sapPaAfterListCnt) ;
		
		return new ModelAndView("/pa/salary/viewC01PortalSapAfterExcel", modelMap);
	}
	
	/**
	 * C01、C02生成SAP接口文件（手动的，页面控制）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPortalInfo")
	@ResponseBody
	public String addPortalInfo(HttpServletRequest request)throws Exception {
		String result = "";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String cpnyId = admin.getCpnyId();
		String sapType = request.getParameter("SapType")!=null?request.getParameter("SapType").toString():"Temp";
		//创建者取不到person_id时，去用户名
	 	String create_by = admin.getPersonId()!=null?admin.getPersonId().toString():admin.getUsername().toString();
	 	request.setAttribute("CREATED_BY", create_by);
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC01SapPath = config.getString("hrm.file.portal.c01sappath");
	    String portalC01MdPath = config.getString("hrm.file.portal.c01mdpath");
		//本地测试用地址
	    /*String portalPath = "d://";
	    String portalC01SapPath = "d://";
	    String portalC01MdPath = "d://";*/
	    if("C01".equals(cpnyId)){
	    	//C01要生成SAP的人事信息
	    	if(sapType!=null && "SAP_HRM".equals(sapType)){
	    		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
	    		paramMap.put("MAKE_TYPE", "H");
	    		paramMap.put("CREATED_BY", create_by);
	    		List<String[]> empInfoList = new ArrayList<String[]>();
			   	empInfoList = (List<String[]>)this.portalSer.getC01EmpInfoList(paramMap);
			   	
		  	 	if(empInfoList.size()>0){
		  	 		portalMap.put("SAP_HR_EMPLOYEE_1002_", empInfoList);
		  	 	}
		  	//C01要生成SAP的部门信息
	    	}else if(sapType!=null && "SAP_DEPARTMENT".equals(sapType)){
	    		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
	    		paramMap.put("MAKE_TYPE", "H");
	    		paramMap.put("CREATED_BY", create_by);
	    		List<String[]> departMentList = new ArrayList<String[]>();
			 	departMentList = (List<String[]>)this.portalSer.getC01DepartMentInfoList(paramMap);
			 	
	    		if(departMentList.size()>0){
		  	 		portalMap.put("SAP_HR_DEPARTMENT_1004_", departMentList);
		  	 	}
	    	//C01要生成GMD的人事信息
	    	}else if(sapType!=null && "GMD_HRM".equals(sapType)){
	    		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
	    		paramMap.put("MAKE_TYPE", "H");
	    		paramMap.put("CREATED_BY", create_by);
	    		List<String[]> empPostInfoList = new ArrayList<String[]>();
			   	empPostInfoList = (List<String[]>)this.portalSer.getC01EmpPostInfoList(paramMap);
			   	
	    		if(empPostInfoList.size()>0){
		  	 		portalMap.put("GMD_HR_EMPLOYEE_1001_", empPostInfoList);
		  	 	}
			//C01要生成SAP工资信息的SUMMARY信息--SAP凭证信息
	    	}else if(sapType!=null && "C01SUMMARY".equals(sapType)){
	    		request.setAttribute("MAKE_TYPE", "H");
		  	 	List<String[]> paSummaryList = new ArrayList<String[]>();
			   	paSummaryList = (List<String[]>)this.portalSer.getC01PaSummaryInfoList(request);
			   	
			   	if(paSummaryList.size()>0){
			  	 	portalMap.put("SAP_PA_SUMMARY_1001_", paSummaryList);
			 	}
			//C01要生成SAP工资信息的FIRMBANKING信息--SAP发放薪资信息
	    	}else if(sapType!=null && "C01FIRM".equals(sapType)){
	    		//验证是否要发送的人员里是否有银行ID为空、银行卡号为空、银行是中国的银行支行ID为空、非韩籍人员人事名字跟账号名字不同的人员
	    		String errorStr = "Error";
	    		List<LinkedHashMap> empErrorList = new ArrayList<LinkedHashMap>();
	    		LinkedHashMap errorMap = new LinkedHashMap();
	    		empErrorList = this.portalSer.checkEmpSapInfoList(request);
	    		
	    		//如果发送到SAP的人员信息都没有问题，则发送
	    		if(empErrorList==null || (empErrorList!=null && empErrorList.size()==0)){
		    		request.setAttribute("MAKE_TYPE", "H");
		    		List<String[]> paActualSalaryList = new ArrayList<String[]>();
				   	paActualSalaryList = (List<String[]>)this.portalSer.getC01PaActualSalaryInfoList(request);
		    		if(paActualSalaryList.size()>0){
				   		portalMap.put("SAP_PA_FIRMBANKING_1003_", paActualSalaryList);
				   	}
		    	//如果发送到SAP的人员信息有问题，则提示有问题人员
	    		}else{
	    			if(empErrorList.get(0)!=null){
	    				errorMap = (LinkedHashMap)(empErrorList.get(0));
	    			}else{
	    				errorMap.put("EMPID", "Error");
	    			}
	    			errorStr = errorMap.get("EMPID")!=null?errorMap.get("EMPID").toString():"Error";
	    			//返回提一个有问题的工号
	    			return errorStr;
	    		}
	    	}
	    }else if("C02".equals(cpnyId)){
	    	List<String[]> paSummaryList = new ArrayList<String[]>();
		   	paSummaryList = (List<String[]>)this.portalSer.getPaSummaryInfoList(request);
		   	List<String[]> departMentList = new ArrayList<String[]>();
		 	departMentList = (List<String[]>)this.portalSer.getDepartMentInfoList(request);
			
		 	if(paSummaryList.size()>0){
		  	 	portalMap.put("PA_SUMMARY_1001_", paSummaryList);
		  	 	portalMap.put("HR_DEPARTMENT_1002_", departMentList);
		 	}else{
		 		return "S";
		 	}
	    }else{//其它法人暂时不允许使用此功能
	    	return "S";
	    }
	 	IthrC01ToPortalIfUtil ifUtil = new IthrC01ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC01SapPath,portalC01MdPath,sapType);
	 	List<String> resultList = new ArrayList<String>();
	 	if("C01".equals(cpnyId)){//C01使用此text生成方法
	 		resultList = ifUtil.makeTxtForC01Sap();
	 	}else if("C02".equals(cpnyId)){//C02使用此text生成方法
	 		resultList = ifUtil.makeTxtForSap();
	 	}
	 	
	 	request.setAttribute("portalResultList", resultList);
	 	int logResult = -1 ;
	 	logResult = this.portalSer.addPortalLog(request);
	 	if(logResult==1){
	 		result = "Y";
	 	}else{
	 		result = "N";
		}

		return result;		
	}
	
	/**
	 * C01每天凌晨1点半自动生成SAP接口文件
	 * @param paramMap
	 * @return int
	 * @throws ConfigurationException
	 * @throws com.ait.web.config.ConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public int doC01SapPortal() throws ConfigurationException, com.ait.web.config.ConfigurationException{
		String result = "";
		String sapType = "C01HRM";
		String cpnyId = "C01";
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC01SapPath = config.getString("hrm.file.portal.c01sappath");
	    String portalC01MdPath = config.getString("hrm.file.portal.c01mdpath");
		//本地测试用地址
	    /*String portalPath = "d://";
	    String portalC01SapPath = "d://";
	    String portalC01MdPath = "d://";*/
	    LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("MAKE_TYPE", "A");
		paramMap.put("CREATED_BY", "SYS");
		List<String[]> empInfoList = new ArrayList<String[]>();
	   	empInfoList = (List<String[]>)this.portalSer.getC01EmpInfoList(paramMap);
	   	List<String[]> empPostInfoList = new ArrayList<String[]>();
	   	empPostInfoList = (List<String[]>)this.portalSer.getC01EmpPostInfoList(paramMap);
	   	List<String[]> departMentList = new ArrayList<String[]>();
	 	departMentList = (List<String[]>)this.portalSer.getC01DepartMentInfoList(paramMap);
	 	
  	 	if(empInfoList.size()>0){
  	 		portalMap.put("SAP_HR_EMPLOYEE_1002_", empInfoList);
  	 	}
  	 	if(empPostInfoList.size()>0){
  	 		portalMap.put("GMD_HR_EMPLOYEE_1001_", empPostInfoList);
  	 	}
  	 	if(departMentList.size()>0){
  	 		portalMap.put("SAP_HR_DEPARTMENT_1004_", departMentList);
  	 	}
 
  	 	IthrC01ToPortalIfUtil ifUtil = new IthrC01ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC01SapPath,portalC01MdPath,sapType);
	 	List<String> portalResultList = new ArrayList<String>();
	 	
	 	portalResultList = ifUtil.makeTxtForC01Sap();
	 	
	 	int logResult = -1 ;
	 	logResult = this.portalSer.addPortalLog(portalResultList,"C01");
	 	
	 	return logResult;
	}
}
