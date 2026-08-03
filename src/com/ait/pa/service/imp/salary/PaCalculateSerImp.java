package com.ait.pa.service.imp.salary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ar.dao.ArMonthCalculateDao;
import com.ait.pa.dao.PaCalculateDao;
import com.ait.pa.service.salary.PaCalculateSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaCalculateSerImp.java
 * @Description:
 * @Create date: 2012-1-17 下午03:16:35
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class PaCalculateSerImp implements PaCalculateSer {

	Logger logger = Logger.getLogger(PaCalculateSerImp.class);
	
	@Autowired
	private PaCalculateDao paCalculateDao ;
	
	@Autowired
	private ArMonthCalculateDao arMonthCalculateDao;
	
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String paCalculate(HttpServletRequest request) {
		String returnString = "" ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_SUPERVISOR_ID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH",request.getParameter("paMonth"));
		paramMap.put("PACAL_GIVE_DATE",request.getParameter("giveDate"));
		paramMap.put("STAT_NO",request.getParameter("statNo"));
		paramMap.put("supervisorId",admin.getPersonId());

		returnString = paCalculateDao.paCalculate(paramMap) ;
		
		return returnString ;
	}
	
	@SuppressWarnings("unchecked")
	public String paWithholdingCalculate(HttpServletRequest request) {
		String returnString = "" ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_SUPERVISOR_ID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH",request.getParameter("paMonth"));
		paramMap.put("PACAL_GIVE_DATE",request.getParameter("giveDate"));
		paramMap.put("STAT_NO",request.getParameter("statNo"));
		paramMap.put("EMP_TYPE",request.getParameter("emptype"));
		paramMap.put("supervisorId",admin.getPersonId());
		
		if(this.paWithholdingMonthlyStatus(paramMap)){
		    returnString = paCalculateDao.paWithholdingCalculate(paramMap) ;
		}else{
			returnString = "<font color='Red'>对不起，预提工资已经锁定无法计算！</font>";
		}
		return returnString ;
	}
	
	@SuppressWarnings("unchecked")
	public String paWithholdingArCalculate(HttpServletRequest request) {
		String returnString = "" ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_SUPERVISOR_ID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH",request.getParameter("paMonth"));
//		paramMap.put("PACAL_GIVE_DATE",request.getParameter("giveDate"));
		paramMap.put("STAT_NO",request.getParameter("statNo"));
		paramMap.put("EMP_TYPE",request.getParameter("emptype"));
		paramMap.put("supervisorId",admin.getPersonId());
		
		if(this.paWithholdingMonthlyStatus(paramMap)){
		    returnString = paCalculateDao.paWithholdingArCalculate(paramMap) ;
		}else{
			returnString = "<font color='Red'>对不起，预提工资已经锁定无法计算！</font>";
		}
		return returnString ;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Boolean paWithholdingMonthlyStatus(LinkedHashMap paramMap) {
		boolean cnt = true;
		int sumnum = 0;
		int emptypenum = 1;
		int deptnum = 1;
		char[] emptype = paramMap.get("empType").toString().toCharArray();
		char[] dept = paramMap.get("deptid").toString().toCharArray();
		for(int i = 0;i< emptype.length;i++){
			if('!' == emptype[i]){
				emptypenum++;
			}
		}
		for(int i = 0;i< dept.length;i++){
			if('!' == dept[i]){
				deptnum++;
			}
		}
		String[] emptypeno = new String[emptypenum];
		String[] deptno = new String[deptnum];
		
		if(paramMap.get("deptid").toString().indexOf("!") < 0){
			deptno[0] = paramMap.get("deptid").toString();
		}else{
			deptno =  paramMap.get("deptid").toString().split("!");
		}
		if(paramMap.get("empType").toString().indexOf("!") < 0){
			emptypeno[0] =  paramMap.get("empType").toString();
		}else{ 
			emptypeno = paramMap.get("empType").toString().split("!");
		}
		for(int i=0;i<deptno.length;i++){
			for(int j = 0;j<emptypeno.length;j++){
				paramMap.put("DEPTID", deptno[i].replaceAll("'", "").trim());
				paramMap.put("EMPTYPEID", emptypeno[j].replaceAll("'", "").trim());
				if(paCalculateDao.paWithholdingMonthlyStatusCnt(paramMap) == 0 ){
					paCalculateDao.insertpaWithholdingMonthlyStatus(paramMap);
				}else{
					List list =  paCalculateDao.getpaWithholdingMonthlyStatusList(paramMap);
					sumnum += Integer.parseInt(((LinkedHashMap)list.get(0)).get("PA_WITHHOLDING_LOCK_FLAG").toString());
				}
			}
		}
		
		return cnt = sumnum == 0 ? true : false;
	
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String paSalaryConfirm(HttpServletRequest request) {
		String returnString = "" ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_SUPERVISOR_ID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH",request.getParameter("paMonth"));
		paramMap.put("PACAL_GIVE_DATE",request.getParameter("giveDate"));
		paramMap.put("STAT_NO",request.getParameter("statNo"));
		paramMap.put("FLAG", request.getParameter("flag"));
		paramMap.put("supervisorId",admin.getPersonId());

		returnString = paCalculateDao.paSalaryConfirm(paramMap) ;
		
		return returnString ;
	}
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String paSalaryTransfer(HttpServletRequest request) {
		String returnString = "" ;
		String returnStringtwo="";
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_SUPERVISOR_ID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH",request.getParameter("paMonth"));
		paramMap.put("PACAL_GIVE_DATE",request.getParameter("giveDate"));
		paramMap.put("STAT_NO",request.getParameter("statNo"));
		paramMap.put("PAY_AREA_CD",request.getParameter("deptid"));
		String  deptid = request.getParameter("deptid");
		paramMap.put("FLAG", request.getParameter("flag"));
		paramMap.put("V_OBJECT", request.getParameter("duixiang"));
		paramMap.put("supervisorId",admin.getPersonId());
		//行轉列工資
	//	arMonthCalculateDao.rowTranscolsPa(paramMap);
		String pay1 = request.getParameter("statNo")!=null?request.getParameter("statNo"):"";
		
		String []  pay_cd = pay1.split(",");
		List	list  =paCalculateDao.getpaEmpGoup(paramMap);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				LinkedHashMap map = (LinkedHashMap)list.get(i);
					if(admin.getCpnyId().equals("TSTO")){
						for(int j=0;j<pay_cd.length;j++){
							
							paramMap.put("PAY_AREA_CD",map.get("GROUP_NO"));
							paramMap.put("STAT_NO",pay_cd[j]);
							returnString = paCalculateDao.paSalaryTransfer(paramMap) ;
						 
							returnStringtwo +=  returnString;
							 
						}
					}else{
						paramMap.put("PAY_AREA_CD",map.get("GROUP_NO"));
						returnString = paCalculateDao.paSalaryTransfer(paramMap) ;
					    returnStringtwo +=  returnString;
						 
						
					}
					
				
			}
		} 
		
		return returnStringtwo ;
	}
	
	/**
	 * 营业员预提传送方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String paSalaryTransferYuti(HttpServletRequest request) {
		String returnString = "" ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_SUPERVISOR_ID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH",request.getParameter("paMonth"));
		paramMap.put("PACAL_GIVE_DATE",request.getParameter("giveDate"));
		paramMap.put("STAT_NO",request.getParameter("statNo"));
		//paramMap.put("PAY_AREA_CD",request.getParameter("deptid"));
		String  deptidsum = request.getParameter("deptid");
		paramMap.put("FLAG", request.getParameter("flag"));
		paramMap.put("V_OBJECT", request.getParameter("duixiang"));
		paramMap.put("supervisorId",admin.getPersonId());
		String deptid = "";
		String [] dd = deptidsum.split(",");
		
		for(int m=0;m<dd.length;m++){
			deptid =dd[m];
			paramMap.put("PAY_AREA_CD",deptid);
			if(returnString.equals("工资未关闭,传送失败")){
				returnString  = paCalculateDao.paSalaryTransferYuti(paramMap);
			}else{
				returnString += paCalculateDao.paSalaryTransferYuti(paramMap);
			}
		  	 
	 	}

		
		
		return returnString ;
	}
	
	@SuppressWarnings("unchecked")
	public String paApplyclosed(HttpServletRequest request) {
		String returnString = "" ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_SUPERVISOR_ID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH",request.getParameter("paMonth"));
		paramMap.put("PACAL_GIVE_DATE",request.getParameter("giveDate"));
		paramMap.put("STAT_NO",request.getParameter("statNo"));
		paramMap.put("supervisorId",admin.getPersonId());

		returnString = paCalculateDao.paSalaryConfirm(paramMap) ;
		
		return returnString ;
	}
	
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String paSalaryConfirmApply(HttpServletRequest request) {
		String returnString = "" ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_SUPERVISOR_ID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PA_MONTH",request.getParameter("paMonth"));
		paramMap.put("PACAL_GIVE_DATE",request.getParameter("giveDate"));
		paramMap.put("STAT_NO",request.getParameter("statNo"));
		paramMap.put("supervisorId",admin.getPersonId());

		returnString = paCalculateDao.paSalaryConfirmApply(paramMap) ;
		
		return returnString ;
	}
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaCalculateTypeList(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.paCalculateDao.getPaCalculateTypeList(paramMap) ;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getArStatisticList(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.paCalculateDao.getArStatisticList(paramMap) ;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaStatisticList(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.paCalculateDao.getPaStatisticList(paramMap) ;
	}
	
	/**
	 * 方法说明（中文，英文）  传送财务用 获取人员类型组
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getArStatisticPatransferList(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("YING_CU",request.getAttribute("YING_CU"));
		
		return this.paCalculateDao.getArStatisticPatransferList(paramMap) ;
	}
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getPaifClose(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
 
		paramMap.put("PA_MONTH",request.getParameter("paMonth") == null?"":request.getParameter("paMonth"));
	 
		paramMap.put("STAT_NO",request.getParameter("STAT_NO") == null?"":request.getParameter("STAT_NO"));
		
		paramMap.put("deptid",request.getParameter("deptid") == null?"":request.getParameter("deptid"));
		
		paramMap.put("V_OBJECT", request.getParameter("duixiang") == null?"":request.getParameter("duixiang"));
		return this.paCalculateDao.getPaifClose(paramMap) ;
	}
	
	/**
	 * 营业员预提方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getPaifCloseYuti(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
 
		paramMap.put("PA_MONTH",request.getParameter("paMonth") == null?"":request.getParameter("paMonth"));
	 
		paramMap.put("STAT_NO",request.getParameter("STAT_NO") == null?"":request.getParameter("STAT_NO"));
		
		paramMap.put("deptid",request.getParameter("deptid") == null?"":request.getParameter("deptid"));
		
		paramMap.put("V_OBJECT", request.getParameter("duixiang") == null?"":request.getParameter("duixiang"));
		return this.paCalculateDao.getPaifCloseYuti(paramMap) ;
	}
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPaifClose2(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
 
		paramMap.put("PA_MONTH",request.getParameter("paMonth") == null?"":request.getParameter("paMonth"));
	 
		paramMap.put("STAT_NO",request.getParameter("STAT_NO") == null?"":request.getParameter("STAT_NO"));
		
		paramMap.put("deptid",request.getParameter("deptid") == null?"":request.getParameter("deptid"));
		
		paramMap.put("V_OBJECT", request.getParameter("duixiang") == null?"":request.getParameter("duixiang"));
		return this.paCalculateDao.getPaifClose2(paramMap) ;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDeptAreaList(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		return this.paCalculateDao.getDeptAreaList(paramMap) ;
	}
	@SuppressWarnings("unchecked")
	public List getDeptAreaListByHr(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		return this.paCalculateDao.getDeptAreaListByHr(paramMap) ;
	}
	
	 /**
	  * 查询工资担当列表(get ArSupervisor List)
	  * @param request
	  * @return List
	  * @throws 
	*/
	@SuppressWarnings("unchecked")
	public List getPaSupervisorList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PA_SUPERVISIOR_INFO",admin.getPersonId());
		return paCalculateDao.getPaSupervisorList(paramMap);
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getCheckPaCalculateType(HttpServletRequest request){
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.paCalculateDao.getCheckPaCalculateType(paramMap) ;
	}

	@Override
	public List getSalaryProvideDatePa(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		LinkedHashMap  paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		retrunList = paCalculateDao.getSalaryProvideDatePa(paramMap) ;
		
		
		return retrunList ; 
	}
	
	@Override
	public List getSalaryProvideDatePa2(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// 页面提交数据
		LinkedHashMap  paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		retrunList = paCalculateDao.getSalaryProvideDatePa2(paramMap) ;
		
		
		return retrunList ; 
	}
	
	@Override
	public int paapplyCloseOpen(HttpServletRequest request) {
		int num = 0;
		
		// 页面提交数据
		try {
			LinkedHashMap  paramMap = ObjectBindUtil.getRequestParamData(request) ;
			String flag = paramMap.get("FLAG").toString();
			String AR_DEPT_NOS= paramMap.get("deptid").toString() ;
			String[] NOS = AR_DEPT_NOS .split("!");
			for (int i = 0; i < NOS.length; i++) {
				paramMap.put("DEPT_NO", NOS[i]);
				if(flag.equals("1")){
					paCalculateDao.updatepaapplyCloseOpen(paramMap);
				}else{
					String num1 = paCalculateDao.getpaapplyCloseOpen(paramMap);
					if(num1.equals("0")){
						paCalculateDao.updatepaapplyCloseOpen(paramMap);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num ; 
	}
	
	
	
	
	@Override
	public String paapplyCloseOpenstr(HttpServletRequest request) {
		String num = "";
		
		// 页面提交数据
		try {
			LinkedHashMap  paramMap = ObjectBindUtil.getRequestParamData(request) ;
			String flag = paramMap.get("FLAG").toString();
			if(paramMap.get("interCpnyID").toString().equals("TSTO")){
				String AR_DEPT_NOS= paramMap.get("deptid").toString() ;
				String[] NOS = AR_DEPT_NOS .split("!");
				for (int i = 0; i < NOS.length; i++) {
					paramMap.put("DEPT_NO", NOS[i]);
					if(flag.equals("1")){
						paCalculateDao.updatepaapplyCloseOpen(paramMap);
						num= "工资申请关闭成功！";
					}else{
						String num1 = paCalculateDao.getpaapplyCloseOpen(paramMap);
						if(num1.equals("0")){
							paCalculateDao.updatepaapplyCloseOpen(paramMap);
							num= "工资申请关闭解除成功！";
						}else{
							num = "工资以申请确认，无法申请关闭解除！";
						}
					}
				}
			}else{
				if(flag.equals("1")){
					paCalculateDao.updatepaapplyCloseOpen(paramMap);
					num= "工资申请关闭成功！";
				}else{
					String num1 = paCalculateDao.getpaapplyCloseOpen(paramMap);
					if(num1.equals("0")){
						paCalculateDao.updatepaapplyCloseOpen(paramMap);
						num= "工资申请关闭解除成功！";
					}else{
						num = "工资以申请确认，无法申请关闭解除！";
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			num ="工资申请关闭或解除失败，请重试！";
		}
		return num ; 
	}
	
	
	@Override
	public String updatepapaOpenFlag(HttpServletRequest request) {
		String num = "";
		
		// 页面提交数据
		try {
			LinkedHashMap  paramMap = ObjectBindUtil.getRequestParamData(request) ;
			String flag = paramMap.get("FLAG").toString();
			String AR_DEPT_NOS= paramMap.get("deptid").toString() ;
			String[] NOS = AR_DEPT_NOS .split("!");
			String num1="";
			for (int i = 0; i < NOS.length; i++) {
				paramMap.put("DEPT_NO", NOS[i]);
			
				if("TSTO".equals(paramMap.get("interCpnyID"))){
					String DEPT_NAME = paCalculateDao.getpaapplyDEPT_Name(paramMap);
					String is_no_tran = paCalculateDao.getpaapplyCloseOpenIsNo(paramMap);
					
					if("F".equals(is_no_tran)){
						  num += DEPT_NAME+"财务未传送,开放失败;";
					}else{
						 num1 = paCalculateDao.getpaapplyCloseOpen(paramMap);
						if(num1.equals("1")){
						  paCalculateDao.updatepapaOpenFlag(paramMap);
						  num += DEPT_NAME+"工资开放成功！";
						}else{
						  num = DEPT_NAME+"工资未确认，无法开放工资！";
						  break;
						}
					}
					
					
				}else{
					  num1 = paCalculateDao.getpaapplyCloseOpen(paramMap);
					if(num1.equals("1")){
					  paCalculateDao.updatepapaOpenFlag(paramMap);
					  num = "工资开放成功！";
					}else{
					  num = "工资未确认，无法开放工资！";
					  break;
					}
				}
				
				
 
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			num ="工资开放失败，请重试！";
		}
		return num ; 
	}
	
	
	@Override
	public String paWithholdingapplyCloseOpenstr(HttpServletRequest request) {
		String num = "";
		
		// 页面提交数据
		try {
			LinkedHashMap  paramMap = ObjectBindUtil.getRequestParamData(request) ;
			String flag = paramMap.get("FLAG").toString();
			int emptypenum = 1;
			int deptnum = 1;
			char[] emptype = paramMap.get("empType").toString().toCharArray();
			char[] dept = paramMap.get("deptid").toString().toCharArray();
			for(int i = 0;i< emptype.length;i++){
				if('!' == emptype[i]){
					emptypenum++;
				}
			}
			for(int i = 0;i< dept.length;i++){
				if('!' == dept[i]){
					deptnum++;
				}
			}
			String[] emptypeno = new String[emptypenum];
			String[] deptno = new String[deptnum];
			
			if(paramMap.get("deptid").toString().indexOf("!") < 0){
				deptno[0] = paramMap.get("deptid").toString();
			}else{
				deptno =  paramMap.get("deptid").toString().split("!");
			}
			if(paramMap.get("empType").toString().indexOf("!") < 0){
				emptypeno[0] =  paramMap.get("empType").toString();
			}else{ 
				emptypeno = paramMap.get("empType").toString().split("!");
			}
			
			for(int i=0;i<deptno.length;i++){
				for(int j = 0;j<emptypeno.length;j++){
					paramMap.put("DEPTID", deptno[i].replaceAll("'", "").trim());
					paramMap.put("EMPTYPEID", emptypeno[j].replaceAll("'", "").trim());
					if(flag.equals("1")){
						paCalculateDao.updatepaWithholdingapplyCloseOpen(paramMap);
						num= "预提工资申请关闭成功！</br>";
					}else{
						 String num1 = paCalculateDao.getpaWithholdingapplyCloseOpen(paramMap);
						 LinkedHashMap map = (LinkedHashMap) paCalculateDao.getpaDepeNameAndEmpTypeName(paramMap);
						 if(num1.equals("0")){
							 paCalculateDao.updatepaWithholdingapplyCloseOpen(paramMap);
							 num= "预提工资申请关闭解除成功！</br>";
						 }else{
							 String de = "大区："+map.get("DEPTNAME")+" ，员工类型："+map.get("EMPTYPENAME")+"。  ";
							 num += de+"预提工资以申请确认，无法申请关闭解除！</br>";
						 }
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			num ="预提工资申请关闭或解除失败，请重试！";
		}
		return num ; 
	}
	
	@Override
	public String isapplyCloseOpenstr(HttpServletRequest request) {
		String num = "";
		
		// 页面提交数据
		try {
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
			String flag = paramMap.get("FLAG").toString();
			if(paramMap.get("interCpnyID").toString().equals("TSTO")){
				String AR_DEPT_NOS= paramMap.get("deptid").toString() ;
				String[] NOS = AR_DEPT_NOS .split("!");
				for (int i = 0; i < NOS.length; i++) {
					paramMap.put("DEPT_NO", NOS[i]);
					if(flag.equals("1")){
						paCalculateDao.updateisapplyCloseOpen(paramMap);
						num= "保险申请关闭成功！";
					}else{
						paCalculateDao.updateisapplyCloseOpen(paramMap);
						num= "保险申请关闭解除成功！";
					}
				}
			}else{
				if(flag.equals("1")){
					paCalculateDao.updateisapplyCloseOpen(paramMap);
					num= "保险申请关闭成功！";
				}else{
					paCalculateDao.updateisapplyCloseOpen(paramMap);
					num= "保险申请关闭解除成功！";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			num ="工资申请关闭或解除失败，请重试！";
		}
		return num ; 
	}
	
	
	
	
	/**
	 * 
	 * 预提工资
	 *   营业员和促销员
	 * 
	 * 
	 * 
	 */
	
	
	
	/**
	 * 取营业员和促销员类型ID
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Map getJobTypeAreaList(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		Map messMap = new LinkedHashMap();
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		paramMap.put("PERSON_ID", admin.getPersonId());
		int sum = 0;
		List typelist = new ArrayList();
		List jobgroup = this.paCalculateDao.getJobTypeAreaList(paramMap) ;
		for(int i = 0;i < jobgroup.size();i++){
			LinkedHashMap jobmap = (LinkedHashMap)jobgroup.get(i);
			paramMap.put("JOBTYPE_GROUP_NO", jobmap.get("JOBTYPE_GROUP_NO"));
			List list = this.paCalculateDao.getEmpTypeAreaList(paramMap);
			for(int j = 0 ;j < list.size();j++ ){
				LinkedHashMap empmap = (LinkedHashMap)list.get(j);
				empmap.put("GROUP", "g"+i);
				typelist.add(empmap);
			}
			jobmap.put("EMPTYPE",list);
			jobmap.put("GROUP", "g"+i);
		}
		messMap.put("joblist", jobgroup);
		messMap.put("emptypelist", typelist);
		
		return messMap ;
	}

	/* 
	* Title: getPaConfirmMonth
	* Description:查询工资汇总项目 为pn工资确认页面的查询工资服务
	* @author 孙鹏  
	* @date 2015年3月27日 下午3:30:03  
	* @param request
	* @return 
	* @see com.ait.pa.service.salary.PaCalculateSer#getPaConfirmMonth(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public List getPaConfirmMonth(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;		
//		paramMap.put("supervisor", admin.getPersonId());
//		paramMap.put("statno", paramMap.get("STAT_NO_PA"));
		paramMap.put("deptno", paramMap.get("deptNO")==null?"0":paramMap.get("deptNO"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(paramMap.get("paYear")!=null&&paramMap.get("paMonth")!=null){
			paramMap.put("paMonth", paramMap.get("paYear").toString()+paramMap.get("paMonth").toString());		
			}else{
			paramMap.put("paMonth", new SimpleDateFormat("yyyyMM").format(Calendar.getInstance().getTime()).toString());
		} 
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
					 this.paCalculateDao.getPaConfrimMonth(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = paCalculateDao.getPaConfrimMonth(paramMap) ;
		}
		
		return retrunList ;
	}

	@Override
	public int getPaConfirmMonthCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;

		paramMap.put("deptno", paramMap.get("deptNO")==null?"0":paramMap.get("seach_deptNO"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(paramMap.get("paYear")!=null&&paramMap.get("paMonth")!=null){
			paramMap.put("paMonth", paramMap.get("paYear").toString()+paramMap.get("paMonth").toString());		
			}else{
			paramMap.put("paMonth", new SimpleDateFormat("yyyyMM").format(Calendar.getInstance().getTime()).toString());
		} 
	     
		return paCalculateDao.getPaConfrimMonthCnt(paramMap);
	}
	
	
}
