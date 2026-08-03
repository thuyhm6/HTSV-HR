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
@RequestMapping(value = "/report/hr")
public class C11SapCtroller {
	Logger logger = Logger.getLogger(PostCtroller.class);

	@Autowired
	private PortalSer portalSer;

	@Autowired
	private  HrReportSer hrReportSer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");

	/*-------------------------C11------接口数据处理部分-----包括自动和手动两部分--------------------*/
	/*-------C11------自动部分---------------------*/
	/**
	 * C11每天凌晨1点半自动生成att接口文件
	 * @param paramMap
	 * @return int
	 * @throws ConfigurationException
	 * @throws com.ait.web.config.ConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public int doC11SapAtt() throws ConfigurationException, com.ait.web.config.ConfigurationException{
		String sapType = "";
		String cpnyId = "C11";
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC11SapPath = config.getString("hrm.file.tosap.portal.ToSap");
	    String portalC11MdPath = config.getString("hrm.file.tosap.portal.ToAtt");
	    LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("MAKE_TYPE", "A");
		paramMap.put("CREATED_BY", "SYS");
		
		List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getHrDeptForMess() ;
	   	if(empList.size()>0){
	   		portalMap.put("DEPT", empList);
	   	}
  		List<String[]> userInfoList = new ArrayList<String[]>();
  		userInfoList = (List<String[]>)this.hrReportSer.getHrUserInfoForMess();
	   	if(userInfoList.size()>0){
	   		portalMap.put("USERINFO", userInfoList);
	   	}
		
  	 	IthrC01ToPortalIfUtil ifUtil = new IthrC01ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC11SapPath,portalC11MdPath,sapType);
	 	List<String> portalResultList = new ArrayList<String>();
	 	
	 	portalResultList = ifUtil.makeTxtForC11Att();
	 	
	 	int logResult = -1 ;
	 	logResult = this.portalSer.addPortalLog(portalResultList,"C11");
	 	
	 	return logResult;
	}
	
	/**
	 * C11每天考勤食堂 吃饭次数
	 * @param paramMap
	 * @return int
	 * @throws ConfigurationException
	 * @throws com.ait.web.config.ConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public int doC11Arcard() throws ConfigurationException, com.ait.web.config.ConfigurationException{
		String sapType = "";
		String cpnyId = "C11";
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC11SapPath = config.getString("hrm.file.tosap.portal.ToSap");
	    String portalC11MdPath = config.getString("hrm.file.tosap.portal.ToAtt");
	    LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("MAKE_TYPE", "A");
		paramMap.put("CREATED_BY", "SYS");
		
		List<String[]> eatingCntList = new ArrayList<String[]>();
		eatingCntList = (List<String[]>)this.hrReportSer.getPersonList() ;// HR TO 考勤食堂 吃饭次数
	   	int eatCount = this.hrReportSer.getPersonCount();
	   	sapType = eatCount+"";
	   	if(eatingCntList.size()>0){
	   		portalMap.put("AREATCOUNT", eatingCntList);
	   	}
		
  	 	IthrC01ToPortalIfUtil ifUtil = new IthrC01ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC11SapPath,portalC11MdPath,sapType);
	 	List<String> portalResultList = new ArrayList<String>();
	 	
	 	portalResultList = ifUtil.makeTxtForC11Eat();
	 	
	 	int logResult = -1 ;
	 	logResult = this.portalSer.addPortalLog(portalResultList,"C11");
	 	
	 	return logResult;
	}
	
	/**
	 * C11每天凌晨1点半自动生成sap接口文件
	 * @param paramMap
	 * @return int
	 * @throws ConfigurationException
	 * @throws com.ait.web.config.ConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public int doC11SapHrm() throws ConfigurationException, com.ait.web.config.ConfigurationException{
		String sapType = "";
		String cpnyId = "C11";
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC11SapPath = config.getString("hrm.file.tosap.portal.ToSap");
	    String portalC11MdPath = config.getString("hrm.file.tosap.portal.ToMD");
	    LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("MAKE_TYPE", "A");
		paramMap.put("CREATED_BY", "SYS");
		
		List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getHrPerInfoForSap() ;
	   	if(empList.size()>0){
	   		portalMap.put("EMY", empList);
	   	}
	   	//2012-08-30 23:57 暂时注释
	   	/*List<String[]> moneyAwardList = new ArrayList<String[]>();
	   	moneyAwardList = (List<String[]>)this.hrReportSer.getMoneyAwardForSap() ;//HRTOSAP 奖金
	   	if(moneyAwardList.size()>0){
	   		portalMap.put("FI_SA", moneyAwardList);
	   	}*/
		
  	 	IthrC01ToPortalIfUtil ifUtil = new IthrC01ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC11SapPath,portalC11MdPath,sapType);
	 	List<String> portalResultList = new ArrayList<String>();
	 	
	 	portalResultList = ifUtil.makeTxtForC11Sap();
	 	
	 	int logResult = -1 ;
	 	
	 	if(empList.size() > 1){
	 		logResult = this.portalSer.addPortalLog(portalResultList,"C11");
	 	}else{
	 		logResult = 1;
	 	}	 	
	 	
	 	return logResult;
	}
	
	/**
	 * C11每天凌晨1点半自动生成EMC（销售系统）文件
	 * @param paramMap
	 * @return int
	 * @throws ConfigurationException
	 * @throws com.ait.web.config.ConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public int doC11EmcHrm() throws ConfigurationException, com.ait.web.config.ConfigurationException{
		String sapType = "";
		String cpnyId = "C11";
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC11SapPath = config.getString("hrm.file.tosap.portal.ToSap");
	    String portalC11MdPath = config.getString("hrm.file.tosap.portal.ToMD");
	    LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("MAKE_TYPE", "A");
		paramMap.put("CREATED_BY", "SYS");
		
		List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getHrEmployeeForTxt() ;
	   	if(empList.size()>0){
	   		portalMap.put("EMC", empList);
	   	}
		
  	 	IthrC01ToPortalIfUtil ifUtil = new IthrC01ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC11SapPath,portalC11MdPath,sapType);
	 	List<String> portalResultList = new ArrayList<String>();
	 	
	 	portalResultList = ifUtil.makeTxtForC11Emc();
	 	
	 	int logResult = -1 ;
	 	if(empList.size() > 1){
	 		logResult = this.portalSer.addPortalLog(portalResultList,"C11");
	 	}else{
	 		logResult = 1;
	 	}
	 	
	 	return logResult;
	}
	
	/*----C11-----手动控制部分------------*/
	/**
	 * 员工信息 (employee information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewHrToMarketInterface")
	public ModelAndView viewContractInfoForSearchList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List empList = this.hrReportSer.getHrEmployeeForInterfaceList();
		int empCnt = this.hrReportSer.getHrEmployeeForInterfaceCnt() ;
		
		modelMap.put("itemList", empList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empCnt) ;
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "23201")) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/report/hr/viewHrToMarketInterface",modelMap);
	}
	
	/**
	 *  C11 HR TO 销售系统  EMC to MD
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEmcDataToSap")
	@ResponseBody
	public String addEmcDataToSap(HttpServletRequest request,HttpServletResponse response)throws Exception {
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC11SapPath = config.getString("hrm.file.tosap.portal.ToSap");
	    String portalC11MdPath = config.getString("hrm.file.tosap.portal.ToMD");
	    LinkedHashMap paramMap = new LinkedHashMap();
		String result = "Y";
		String sapType = "";
		String cpnyId = "C11";
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		paramMap.put("MAKE_TYPE", "H");
		paramMap.put("CREATED_BY", admin.getPersonId()!=null?admin.getPersonId():admin.getUsername());
		
	   	List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getHrEmployeeForTxt() ;
	   	if(empList.size()>0){
	   		portalMap.put("EMC", empList);
	   	}
	   	IthrC01ToPortalIfUtil ifUtil = new IthrC01ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC11SapPath,portalC11MdPath,sapType);
	   	List<String> resultList = new ArrayList<String>();
	 	
	   	resultList = ifUtil.makeTxtForC11Emc();
	 	
	 	int logResult = -1 ;
	 	//插入生成日志信息
	 	logResult = this.portalSer.addPortalLog(resultList,"C11");
	 	if(logResult==-1){
	 		result = "N";
	 	}else{
	 		result = "Y";
	 	}
	 	
		return result;		
	}
	
	/**
	 *  C11 HR TO 考勤食堂  DEPT USUERINFO 次数    -> att 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addAttDataToSap")
	@ResponseBody
	public String addAttDataToSap(HttpServletRequest request, HttpServletResponse response)throws Exception {
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC11SapPath = config.getString("hrm.file.tosap.portal.ToSap");
	    String portalC11MdPath = config.getString("hrm.file.tosap.portal.ToAtt");
	    LinkedHashMap paramMap = new LinkedHashMap();
		String result = "Y";
		String sapType = "";
		String cpnyId = "C11";
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		paramMap.put("MAKE_TYPE", "H");
		paramMap.put("CREATED_BY", admin.getPersonId()!=null?admin.getPersonId():admin.getUsername());
		
		List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getHrDeptForMess() ;
	   	if(empList.size()>0){
	   		portalMap.put("DEPT", empList);
	   	}
  		List<String[]> userInfoList = new ArrayList<String[]>();
  		userInfoList = (List<String[]>)this.hrReportSer.getHrUserInfoForMess();
	   	if(userInfoList.size()>0){
	   		portalMap.put("USERINFO", userInfoList);
	   	}
		
	   	//要记入sapType这个参数，所以不能再之前生成
	   	IthrC01ToPortalIfUtil ifUtil = new IthrC01ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC11SapPath,portalC11MdPath,sapType);
	   	List<String> resultList = new ArrayList<String>();
	 	
	   	resultList = ifUtil.makeTxtForC11Att();
	 	
	 	int logResult = -1 ;
	 	//插入生成日志信息
	 	logResult = this.portalSer.addPortalLog(resultList,"C11");
	 	if(logResult==-1){
	 		result = "N";
	 	}else{
	 		result = "Y";
	 	}
	 	
	 	List<String[]> eatingCntList = new ArrayList<String[]>();
		eatingCntList = (List<String[]>)this.hrReportSer.getPersonList() ;// HR TO 考勤食堂 吃饭次数
	   	int eatCount = this.hrReportSer.getPersonCount();
	   	sapType = eatCount+"";
	   	if(eatingCntList.size()>0){
	   		portalMap.put("AREATCOUNT", eatingCntList);
	   	}
		
  	 	IthrC01ToPortalIfUtil ifUtil1 = new IthrC01ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC11SapPath,portalC11MdPath,sapType);
  	 	List<String> resultList1 = new ArrayList<String>();
	 	
  	 	resultList1 = ifUtil1.makeTxtForC11Eat();
	 	//插入生成日志信息
	 	logResult = this.portalSer.addPortalLog(resultList1,"C11");
	 	if(logResult==-1){
	 		result = "N";
	 	}else{
	 		result = "Y";
	 	}
		return result;		
	}
	
	/**
	 *  C11 HR TO SAP 人事信息 EMY  FI_SA  ->  SAP
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEmyDataToSap")
	@ResponseBody
	public String addEmyDataToSap(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC11SapPath = config.getString("hrm.file.tosap.portal.ToSap");
	    String portalC11MdPath = config.getString("hrm.file.tosap.portal.ToMD");
	    LinkedHashMap paramMap = new LinkedHashMap();
		String result = "Y";
		String sapType = "";
		String cpnyId = "C11";
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		paramMap.put("MAKE_TYPE", "H");
		paramMap.put("CREATED_BY", admin.getPersonId()!=null?admin.getPersonId():admin.getUsername());
		
	 	List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getHrPerInfoForSap() ;
	   	if(empList.size()>0){
	   		portalMap.put("EMY", empList);
	   	}
	   	//2012-08-30 23:57 暂时注释
	   	/*List<String[]> moneyAwardList = new ArrayList<String[]>();
	   	moneyAwardList = (List<String[]>)this.hrReportSer.getMoneyAwardForSap() ;//HRTOSAP 奖金
	   	if(moneyAwardList.size()>0){
	   		portalMap.put("FI_SA", moneyAwardList);
	   	}*/
		
  	 	IthrC01ToPortalIfUtil ifUtil = new IthrC01ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC11SapPath,portalC11MdPath,sapType);
	 	List<String> resultList = new ArrayList<String>();
	 	
	 	resultList = ifUtil.makeTxtForC11Sap();
	 	
	 	int logResult = -1 ;
	 	//插入生成日志信息
	 	logResult = this.portalSer.addPortalLog(resultList,"C11");
	 	if(logResult==1){
	 		result = "Y";
	 	}else{
	 		result = "N";
	 	}
		return result;		
	}
	
	/**
	 *  C11 HR TO SAP 奖金
	 * @param request
	 * @return
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addC12DateForSapMoneyAwardInterface")
	@ResponseBody
	public String addC12PortalInfo4(HttpServletRequest request)
			throws Exception {
		String result = "Y";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
	   	List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getMoneyAwardForSapC12(request) ;
	   	
	   	Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMdd")).format(date);
		String dateStrm = (new SimpleDateFormat("hh:mm:ss")).format(date);
		String dateStrm1 = dateStrm.replaceAll(":", "");
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC12SapPath = config.getString("hrm.file.tosap.portal.ToC12Sap");
	    String portalC12MdPath = config.getString("hrm.file.tosap.portal.ToC12Att");
		//本地测试用地址
	    String portalPath = "E:";

  	 	portalMap.put("HR_MONEYAWARDFORSAP_1001_", empList);
  	 	String cpnyId = admin.getCpnyId();
		
	 	IthrToPortalIfUtil ifUtil = new IthrToPortalIfUtil(portalPath,portalMap,cpnyId);
	 	
	 	List<String> resultList = ifUtil.makeTxtForSapInterface("FI_BO"+dateStr+dateStrm1+".txt");
	 	int logResult = -1 ;
	 	//插入生成日志信息
	 	logResult = this.portalSer.addPortalLog(resultList,"C12");
	 	if(logResult==-1){
	 		result = "DY";
	 	}else{
	 		result = "DN";
	 	}

		return result;		
	}*/
}
