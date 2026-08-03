package com.ait.pa.action.salary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.ait.ar.service.ArEatMealCountSer;
import com.ait.pa.service.salary.PortalSer;
import com.ait.report.hr.service.HrReportSer;
import com.ait.sys.action.PostCtroller;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.exception.ConfigurationException;
import com.ait.web.util.IthrC12ToPortalIfUtil;
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
public class C12SapCtroller {
	Logger logger = Logger.getLogger(PostCtroller.class);

	@Autowired
	private PortalSer portalSer;

	@Autowired
	private ArEatMealCountSer arEatMealCountSer;
	
	@Autowired
	private  HrReportSer hrReportSer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");

	/*-------------------------C12------接口数据处理部分-----包括自动和手动两部分--------------------*/
	/*-------C12------自动部分---------------------*/
	/**
	 * C12每天凌晨1点半自动生成att接口文件
	 * @param paramMap
	 * @return int
	 * @throws ConfigurationException
	 * @throws com.ait.web.config.ConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public int doC12SapAtt() throws ConfigurationException, com.ait.web.config.ConfigurationException{
		String sapType = "";
		String cpnyId = "C12";
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC12SapPath = config.getString("hrm.file.tosap.portal.ToC12Sap");
	    String portalC12MdPath = config.getString("hrm.file.tosap.portal.ToC12Att");
	    LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("MAKE_TYPE", "A");
		paramMap.put("CREATED_BY", "SYS");
		
		List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getHrDeptForMessC12() ;
	   	if(empList.size()>0){
	   		portalMap.put("DEPT", empList);
	   	}
  		List<String[]> userInfoList = new ArrayList<String[]>();
  		userInfoList = (List<String[]>)this.hrReportSer.getHrUserInfoForMessC12();
	   	if(userInfoList.size()>0){
	   		portalMap.put("USERINFO", userInfoList);
	   	}
		
  	 	IthrC12ToPortalIfUtil ifUtil = new IthrC12ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC12SapPath,portalC12MdPath,sapType);
	 	List<String> portalResultList = new ArrayList<String>();
	 	portalResultList = ifUtil.makeTxtForC12Att();
	 	
	 	int logResult = -1 ;
	 	logResult = this.portalSer.addPortalLog(portalResultList,"C12");
	 	
	 	return logResult;
	}
	
	/**
	 * C12每天考勤食堂 吃饭次数，初始化
	 * @param paramMap
	 * @return int
	 * @throws ConfigurationException
	 * @throws com.ait.web.config.ConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public int initMealCountC12Auto(){
		LinkedHashMap paramMap = new LinkedHashMap();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(c.getTime());
		
		paramMap.put("CPNY_ID", "C12");
		paramMap.put("CREATED_BY", "system");
		paramMap.put("FROM_DATE", date);
		paramMap.put("END_DATE", date);
		
		List<String> portalResultList = new ArrayList<String>();
	 	int logResult = -1 ;
	 	portalResultList = this.arEatMealCountSer.initMealCount(paramMap);
	 	logResult = this.portalSer.addPortalLog(portalResultList,"C12");
	 	return logResult;
	}
	
	/**
	 * C12每天考勤食堂 吃饭次数，发送
	 * @param paramMap
	 * @return int
	 * @throws ConfigurationException
	 * @throws com.ait.web.config.ConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public int doC12Arcard() throws ConfigurationException, com.ait.web.config.ConfigurationException{
		String sapType = "";
		String cpnyId = "C12";
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC12SapPath = config.getString("hrm.file.tosap.portal.ToC12Sap");
	    String portalC12MdPath = config.getString("hrm.file.tosap.portal.ToC12Att");
	    LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("MAKE_TYPE", "A");
		paramMap.put("CREATED_BY", "SYS");
		
		List<String[]> eatingCntList = new ArrayList<String[]>();
		int eatCount = 0;
		//2014-01-01之前，都是根据日历来发送食堂刷卡次数的
		//eatingCntList = (List<String[]>)this.hrReportSer.getPersonListC12() ;// HR TO 考勤食堂 吃饭次数
	   	//int eatCount = this.hrReportSer.getPersonCountC12();
	    //2014-01-01之后，要根据表 ar_eat_count_att 里的初始化结果来生成
		eatingCntList = (List<String[]>)this.hrReportSer.getPersonListC12New() ;// HR TO 考勤食堂 吃饭次数
	   	eatCount = this.hrReportSer.getPersonCountC12New();
	   	
	   	sapType = eatCount+"";
	   	if(eatingCntList.size()>0){
	   		portalMap.put("AREATCOUNT", eatingCntList);
	   	}
		
	   	IthrC12ToPortalIfUtil ifUtil = new IthrC12ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC12SapPath,portalC12MdPath,sapType);
	 	List<String> portalResultList = new ArrayList<String>();
	 	portalResultList = ifUtil.makeTxtForC12Eat();
	 	
	 	int logResult = -1 ;
	 	logResult = this.portalSer.addPortalLog(portalResultList,"C12");
	 	
	 	return logResult;
	}
	
	/**
	 * C12每天凌晨1点半自动生成sap接口文件
	 * @param paramMap
	 * @return int
	 * @throws ConfigurationException
	 * @throws com.ait.web.config.ConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public int doC12SapHrm() throws ConfigurationException, com.ait.web.config.ConfigurationException{
		String sapType = "";
		String cpnyId = "C12";
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC12SapPath = config.getString("hrm.file.tosap.portal.ToC12Sap");
	    String portalC12MdPath = config.getString("hrm.file.tosap.portal.ToC12MD");
	    LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("MAKE_TYPE", "A");
		paramMap.put("CREATED_BY", "SYS");
		
		List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getHrPerInfoForSapC12() ;
	   	if(empList.size()>0){
	   		portalMap.put("EMY", empList);
	   	}
	   	//2013-01-28 27:08 暂时注释
	   	/*List<String[]> moneyAwardList = new ArrayList<String[]>();
	   	moneyAwardList = (List<String[]>)this.hrReportSer.getMoneyAwardForSapC12() ;//HRTOSAP 奖金
	   	if(moneyAwardList.size()>0){
	   		portalMap.put("FI_SA", moneyAwardList);
	   	}*/
		
	   	IthrC12ToPortalIfUtil ifUtil = new IthrC12ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC12SapPath,portalC12MdPath,sapType);
	 	List<String> portalResultList = new ArrayList<String>();
	 	
	 	portalResultList = ifUtil.makeTxtForC12Sap();
	 	
	 	int logResult = -1 ;
	 	
	 	if(empList.size() > 1){
	 		logResult = this.portalSer.addPortalLog(portalResultList,"C12");
	 	}else{
	 		logResult = 1;
	 	}	 	
	 	
	 	return logResult;
	}
	
	/**
	 * C12每天凌晨1点半自动生成EMC（销售系统）文件
	 * @param paramMap
	 * @return int
	 * @throws ConfigurationException
	 * @throws com.ait.web.config.ConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public int doC12EmcHrm() throws ConfigurationException, com.ait.web.config.ConfigurationException{
		String sapType = "";
		String cpnyId = "C12";
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.temp.path");
	    String portalC12SapPath = config.getString("hrm.file.tosap.portal.ToC12Sap");
	    String portalC12MdPath = config.getString("hrm.file.tosap.portal.ToC12MD");
	    LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("MAKE_TYPE", "A");
		paramMap.put("CREATED_BY", "SYS");
		
		List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getHrEmployeeForTxtC12() ;
	   	if(empList.size()>0){
	   		portalMap.put("EMC", empList);
	   	}
		
	   	IthrC12ToPortalIfUtil ifUtil = new IthrC12ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC12SapPath,portalC12MdPath,sapType);
	 	List<String> portalResultList = new ArrayList<String>();
	 	
	 	portalResultList = ifUtil.makeTxtForC12Emc();
	 	
	 	int logResult = -1 ;
	 	if(empList.size() > 1){
	 		logResult = this.portalSer.addPortalLog(portalResultList,"C12");
	 	}else{
	 		logResult = 1;
	 	}
	 	
	 	return logResult;
	}
	
	/*----C12-----手动控制部分------------*/
	/**
	 * 员工信息 (employee information)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewC12HrToMarketInterface")
	public ModelAndView viewContractInfoForSearchList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List empList = this.hrReportSer.getHrEmployeeC12ForInterfaceList();
		int empCnt = this.hrReportSer.getHrEmployeeC12ForInterfaceCnt() ;
		
		modelMap.put("itemList", empList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, empCnt) ;
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "23201")) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView("/report/hr/viewC12HrToMarketInterface",modelMap);
	}
	
	/**
	 *  C12 HR TO 销售系统  EMC to MD
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addC12DateForInterface")
	@ResponseBody
	public String addC12PortalInfo(HttpServletRequest request,HttpServletResponse response)throws Exception {
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.tosap.temp.path");
	    String portalC12SapPath = config.getString("hrm.file.tosap.portal.ToC12Sap");
	    String portalC12MdPath = config.getString("hrm.file.tosap.portal.ToC12MD");
	    LinkedHashMap paramMap = new LinkedHashMap();
		String result = "Y";
		String sapType = "";
		String cpnyId = "C12";
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		paramMap.put("MAKE_TYPE", "H");
		paramMap.put("CREATED_BY", admin.getPersonId()!=null?admin.getPersonId():admin.getUsername());
		
	   	List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getHrEmployeeForTxtC12() ;
	   	if(empList.size()>0){
	   		portalMap.put("EMC", empList);
	   	}
	   	
  	 	IthrC12ToPortalIfUtil ifUtil = new IthrC12ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC12SapPath,portalC12MdPath,sapType);
	 	List<String> resultList = ifUtil.makeTxtForC12Emc();
	 	
	 	int logResult = -1 ;
	 	//插入生成日志信息
	 	logResult = this.portalSer.addPortalLog(resultList,"C12");
	 	if(logResult==-1){
	 		result = "N";
	 	}else{
	 		result = "Y";
	 	}
	 	
		return result;		
	}
	
	/**
	 *  C12 HR TO 考勤食堂  DEPT USUERINFO 次数    -> att 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addC12DateForMessDeptInterface")
	@ResponseBody
	public String addC12PortalInfo1(HttpServletRequest request, HttpServletResponse response)throws Exception {
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.tosap.temp.path");
	    String portalC12SapPath = config.getString("hrm.file.tosap.portal.ToC12Sap");
	    String portalC12MdPath = config.getString("hrm.file.tosap.portal.ToC12Att");
	    LinkedHashMap paramMap = new LinkedHashMap();
		String result = "Y";
		String sapType = "";
		String cpnyId = "C12";
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		paramMap.put("MAKE_TYPE", "H");
		paramMap.put("CREATED_BY", admin.getPersonId()!=null?admin.getPersonId():admin.getUsername());
		
		List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getHrDeptForMessC12() ;
	   	if(empList.size()>0){
	   		portalMap.put("DEPT", empList);
	   	}
  		List<String[]> userInfoList = new ArrayList<String[]>();
  		userInfoList = (List<String[]>)this.hrReportSer.getHrUserInfoForMessC12();
	   	if(userInfoList.size()>0){
	   		portalMap.put("USERINFO", userInfoList);
	   	}
	   	
	   	//要记入sapType这个参数，所以不能再之前生成
	   	IthrC12ToPortalIfUtil ifUtil = new IthrC12ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC12SapPath,portalC12MdPath,sapType);
	 	List<String> resultList = new ArrayList<String>();
	 	resultList = ifUtil.makeTxtForC12Att();
	 	int logResult = -1 ;
	 	//插入生成日志信息
	 	logResult = this.portalSer.addPortalLog(resultList,"C12");
	 	if(logResult==-1){
	 		result = "N";
	 	}else{
	 		result = "Y";
	 	}
	 	List<String[]> eatingCntList = new ArrayList<String[]>();
		eatingCntList = (List<String[]>)this.hrReportSer.getPersonListC12() ;// HR TO 考勤食堂 吃饭次数
	   	int eatCount = this.hrReportSer.getPersonCountC12();
	   	sapType = eatCount+"";
	   	if(eatingCntList.size()>0){
	   		portalMap.put("AREATCOUNT", eatingCntList);
	   	}
	   	IthrC12ToPortalIfUtil ifUtil1 = new IthrC12ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC12SapPath,portalC12MdPath,sapType);
	 	List<String> resultList1 = new ArrayList<String>();
	 	resultList1 = ifUtil1.makeTxtForC12Eat();
	 	//插入生成日志信息
	 	logResult = this.portalSer.addPortalLog(resultList1,"C12");
	 	if(logResult==-1){
	 		result = "N";
	 	}else{
	 		result = "Y";
	 	}
		return result;		
	}
	
	/**
	 *  C12 HR TO 考勤食堂 人事  dept  userinfo  -> att
	 * @param request
	 * @return
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addC12DateForMessUserInfoInterface")
	@ResponseBody
	public String addC12PortalInfo2(HttpServletRequest request, HttpServletResponse response)throws Exception {
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.tosap.temp.path");
	    String portalC12SapPath = config.getString("hrm.file.tosap.portal.ToC12Sap");
	    String portalC12MdPath = config.getString("hrm.file.tosap.portal.ToC12Att");
	    LinkedHashMap paramMap = new LinkedHashMap();
		String result = "Y";
		String sapType = "";
		String cpnyId = "C12";
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		paramMap.put("MAKE_TYPE", "H");
		paramMap.put("CREATED_BY", admin.getPersonId()!=null?admin.getPersonId():admin.getUsername());
		
		List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getHrDeptForMessC12() ;
	   	if(empList.size()>0){
	   		portalMap.put("DEPT", empList);
	   	}
  		List<String[]> userInfoList = new ArrayList<String[]>();
  		userInfoList = (List<String[]>)this.hrReportSer.getHrUserInfoForMessC12();
	   	if(userInfoList.size()>0){
	   		portalMap.put("USERINFO", userInfoList);
	   	}
	   	IthrC12ToPortalIfUtil ifUtil = new IthrC12ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC12SapPath,portalC12MdPath,sapType);
	 	List<String> resultList = new ArrayList<String>();
	 	resultList = ifUtil.makeTxtForC12Att();
	 	
	 	int logResult = -1 ;
	 	//插入生成日志信息
	 	logResult = this.portalSer.addPortalLog(resultList,"C12");
	 	if(logResult==-1){
	 		result = "Y";
	 	}else{
	 		result = "N";
	 	}
		return result;		
	}*/
	
	/**
	 *  C12 HR TO SAP 人事信息 EMY  FI_SA  ->  SAP
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addC12DateForSapPersonnelInfoInterface")
	@ResponseBody
	public String addC12PortalInfo3(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//正式服务器用地址
	    String portalPath = config.getString("hrm.file.tosap.temp.path");
	    String portalC12SapPath = config.getString("hrm.file.tosap.portal.ToC12Sap");
	    String portalC12MdPath = config.getString("hrm.file.tosap.portal.ToC12Att");
	    LinkedHashMap paramMap = new LinkedHashMap();
		String result = "Y";
		String sapType = "";
		String cpnyId = "C12";
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String , List<String[]>> portalMap = new HashMap<String, List<String[]>>();
		paramMap.put("MAKE_TYPE", "H");
		paramMap.put("CREATED_BY", admin.getPersonId()!=null?admin.getPersonId():admin.getUsername());
		
		List<String[]> empList = new ArrayList<String[]>();
	   	empList = (List<String[]>)this.hrReportSer.getHrPerInfoForSapC12() ;
  	 	portalMap.put("EMY", empList);
  	 	
	   	//List<String[]> moneyAwardList = new ArrayList<String[]>();
	   	//moneyAwardList = (List<String[]>)this.hrReportSer.getMoneyAwardForSapC12() ;//HRTOSAP 奖金
  	 	//portalMap.put("FI_SA", moneyAwardList);
  	 	
  	 	IthrC12ToPortalIfUtil ifUtil = new IthrC12ToPortalIfUtil(portalPath,portalMap,cpnyId,portalC12SapPath,portalC12MdPath,sapType);
	 	List<String> resultList = new ArrayList<String>();
	 	resultList = ifUtil.makeTxtForC12Sap();
	 	
	 	int logResult = -1 ;
	 	//插入生成日志信息
	 	logResult = this.portalSer.addPortalLog(resultList,"C12");
	 	if(logResult==1){
	 		result = "Y";
	 	}else{
	 		result = "N";
	 	}
		return result;		
	}
	
	/**
	 *  C12 HR TO SAP 奖金
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
