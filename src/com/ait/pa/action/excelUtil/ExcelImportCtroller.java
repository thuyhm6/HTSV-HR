package com.ait.pa.action.excelUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ait.ar.dao.ShiftDao;
import com.ait.ar.service.ArCardRecordSer;
import com.ait.ar.service.EmpCalendarSer;
import com.ait.edu.dao.TrainEducationDao;
import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.ess.service.TempEmpSer;
import com.ait.evs.dao.EvsManageDao;
import com.ait.evs.service.EvsManageSer;
import com.ait.hrm.dao.TransferOrderDao;
import com.ait.hrm.service.RecruitManageSer;
import com.ait.is.service.AccumulationFundManageSer;
import com.ait.is.service.AccumulationFundSer;
import com.ait.org.service.OrgManageSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.insurance.InsuranceInputItemSer;
import com.ait.pa.service.workManagement.PaEmpAccountSer;
import com.ait.pa.service.workManagement.viewPaParamSer;
import com.ait.promoter.service.PromoterSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
/**
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName ExcelImportCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-3-27 下午05:25:57
 * @version 5.0
 * 
 */

@Controller
@RequestMapping(value = "/pa/excelImport")
@SuppressWarnings({ "unchecked", "static-access" })
public class ExcelImportCtroller {
	@Autowired
	private TransferOrderDao transferOrderDao;
	Logger logger = Logger.getLogger(ExcelImportCtroller.class);
	
	public final static int VARCHAR = 0;
	
	public final static int DMY_VARCHAR = 3; //DD/MM/YYYY格式的字符串

	public final static int DATE = 1;//yyyy-MM-dd

	public final static int NUMBER = 2;
	
	public final static int SINGLE = 0;
	
	public final static int SYSDATE = 2;
	
	public final static int DATE_HMS = 11;//yyyy-MM-dd hh:mm:ss
	
	public final static int DATE_HM = 12;//yyyy-MM-dd hh:mm
	
	public final static int INVOLUTE = 1;
	
	public final static int NVARCHAR = 13;//yyyy-MM-dd

	String filename = "";

	String path = "";
	
	String pathCpnyID="";

	String item_no = "";
	
	String type="";
	
	String language="";

	@Autowired
	private ExcelUtilSer excelUtilSer;
	
	@Autowired
	private ShiftDao shiftDao;
	
	@Autowired
	private TempEmpSer tempEmpSer;
	
	@Autowired
	private AccumulationFundSer accumulationFundSer;
	@Autowired
	private InfoApplySer infoApplySer;
	@Autowired
	private AccumulationFundManageSer accumulationFundManageSer;
	@Autowired
	private InsuranceInputItemSer insuranceInputItemSer;
	@Autowired
	private viewPaParamSer 	viewPaParamSer;
	@Autowired
	private TrainEducationDao eduTrainDao;
	@Autowired
	private EvsManageSer evsManageSer;
	@Autowired
	private EvsManageDao evsManageDao;
	@Autowired
	private OrgManageSer orgManageSer;
	@Autowired
	private InfoApplyLeaveSer infoApplyLeaveSer;
	@Autowired
	private PaEmpAccountSer paEmpAccountSer;
	@Autowired
	private RecruitManageSer recruitManageSer;
	@Autowired
	private ArCardRecordSer arCardRecordSer;
	@Autowired
	private EmpCalendarSer empCalendarSer;
	
	//	-------------------------------------------------------保险输入项目数据-------------------------------------------------------------------
	/**
	 * 导入数据 （调用时需要重写的方法） 保险-输入项目数据 - DISTINCT_FIELD ne 'PERSON_ID'
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importInsuranceInputItemDataExcelIsNull")
	public ModelAndView importInsuranceInputItemDataExcelIsNull(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		String paramNo=request.getParameter("id");
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("FIELD1_VALUE", "#CELL0#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
		aliasValueMap.put("START_MONTH", "#CELL1#");
		aliasValueMap.put("END_MONTH", "#CELL2#");
		aliasValueMap.put("RETURN_VALUE", "#CELL3#");
		aliasValueMap.put("REMARK", "#CELL4#");
		
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("PARAM_DATA_NO", "IS_PARAM_DATA_SEQ.NEXTVAL");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("FIELD1_VALUE", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("PARAM_DATA_NO", this.NUMBER);
		
		
		//第四步 可以为空的列 
		String aliasNullStr = ",4,";
				
		String filed1=request.getParameter("FIELD1");
		if(filed1!=null&&!filed1.equals("")){
			String sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
			  "' AND T."+filed1+" =S.NO(+) AND " +
			  " ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ')  AND ROWNUM=1";
			if(filed1.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}if(filed1.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}
			LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
			aliasValueI18nMap.put("FIELD1_VALUE", sqlI18nContent);
			if(filed1.equalsIgnoreCase("CPNY_ID") || filed1.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD1_VALUE_FLAG", "Y");
			}
			LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("IS_PARAM_DATA_OTHER",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			map.put("aliasNullStr", aliasNullStr);
			this.excelUtilSer.importData(request,response,map,modelMap);
		}
		modelMap.put("rel", "viewInsuranceInputItemData");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	

	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importInsuranceInputItemDataExcelIsNull2")
	public ModelAndView importInsuranceInputItemDataExcelIsNull2(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		String paramNo=request.getParameter("id");
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("FIELD1_VALUE", "#CELL0#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
		aliasValueMap.put("FIELD2_VALUE", "#CELL1#");
		aliasValueMap.put("START_MONTH", "#CELL2#");
		aliasValueMap.put("END_MONTH", "#CELL3#");
		aliasValueMap.put("RETURN_VALUE", "#CELL4#");
		aliasValueMap.put("REMARK", "#CELL5#");
		
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("PARAM_DATA_NO", "IS_PARAM_DATA_SEQ.nextval");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("FIELD1_VALUE", this.VARCHAR);
		aliasTypeMap.put("FIELD2_VALUE", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("PARAM_DATA_NO", this.NUMBER);
		
		//第四步 可以为空的列 
		String aliasNullStr = ",5,";
		//指定需要国际化的字段，以及国际化的语句
		String filed1=request.getParameter("FIELD1");
		String filed2=request.getParameter("FIELD2");
		if(filed1!=null&&!filed1.equals("")){
			String sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
								  "' AND T."+filed1+" =S.NO(+) AND " +
								  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			if(filed1.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}if(filed1.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}
			String sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
								  "' AND T."+filed2+" =S.NO(+) AND " +
								  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1" ;
			if(filed2.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed2+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1";
			}if(filed2.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed2+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1";
			}
			LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
			aliasValueI18nMap.put("FIELD1_VALUE", sqlI18nContent);
			aliasValueI18nMap.put("FIELD2_VALUE", sqlI18nContent2);
			if(filed1.equalsIgnoreCase("CPNY_ID") || filed1.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD1_VALUE_FLAG", "Y");
			}
			if(filed2.equalsIgnoreCase("CPNY_ID") || filed2.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD2_VALUE_FLAG", "Y");
			}
			LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("IS_PARAM_DATA_OTHER",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			map.put("aliasNullStr", aliasNullStr);
			this.excelUtilSer.importData(request,response,map,modelMap);
		}
		modelMap.put("rel", "viewInsuranceInputItemData");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	/** 
	 * 导入数据 （调用时需要重写的方法） IS 保险项目数据  DISTINCT_FIELD eq 'PERSON_ID'
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importInsuranceInputItemDataExcelIsNotNull")
	public ModelAndView importInsuranceInputItemDataExcelIsNotNull(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		String paramNo=request.getParameter("id");
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("EMPID", "#CELL0#");//社号
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");//姓名
		aliasValueMap.put("START_MONTH", "#CELL2#");//开始月
		aliasValueMap.put("END_MONTH", "#CELL3#");//开始月
		aliasValueMap.put("RETURN_VALUE", "#CELL4#");//数值
		aliasValueMap.put("REMARK", "#CELL5#");//备注
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("PARAM_NO", paramNo);
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("IS_PARAM_DATA_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/pa/insurance/viewImportExcelTempISParamDataList";
		String navTabId = "pa0409";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	
//	--------------------------------------------奖金输入项目数据-----------------------------------------------------
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importBonusInputItemDataExcelIsNull")
	public ModelAndView importBonusInputItemDataExcelIsNull(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		String paramNo=request.getParameter("id");
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("FIELD1_VALUE", "#CELL0#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
		aliasValueMap.put("START_MONTH", "#CELL1#");
		aliasValueMap.put("END_MONTH", "#CELL2#");
		aliasValueMap.put("RETURN_VALUE", "#CELL3#");
		
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("PARAM_DATA_NO", "BN_PARAM_DATA_SEQ.NEXTVAL");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("FIELD1_VALUE", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("PARAM_DATA_NO", this.NUMBER);
		

		String filed1=request.getParameter("FIELD1");
		if(filed1!=null&&!filed1.equals("")){
			String sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
			  "' AND T."+filed1+" =S.NO(+) AND " +
			  " ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ')  AND ROWNUM=1";
			if(filed1.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}if(filed1.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}
			LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
			aliasValueI18nMap.put("FIELD1_VALUE", sqlI18nContent);
			if(filed1.equalsIgnoreCase("CPNY_ID") || filed1.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD1_VALUE_FLAG", "Y");
			}
			LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("BN_PARAM_DATA_OTHER",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			
			this.excelUtilSer.importData(request,response,map,modelMap);
		}
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	

	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importBonusInputItemDataExcelIsNull2")
	public ModelAndView importBonusInputItemDataExcelIsNull2(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		String paramNo=request.getParameter("id");
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("FIELD1_VALUE", "#CELL0#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
		aliasValueMap.put("FIELD2_VALUE", "#CELL1#");
		aliasValueMap.put("START_MONTH", "#CELL2#");
		aliasValueMap.put("END_MONTH", "#CELL3#");
		aliasValueMap.put("RETURN_VALUE", "#CELL4#");
		
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("PARAM_DATA_NO", "BN_PARAM_DATA_SEQ.nextval");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("FIELD1_VALUE", this.VARCHAR);
		aliasTypeMap.put("FIELD2_VALUE", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("PARAM_DATA_NO", this.NUMBER);
		
		//指定需要国际化的字段，以及国际化的语句
		String filed1=request.getParameter("FIELD1");
		String filed2=request.getParameter("FIELD2");
		if(filed1!=null&&!filed1.equals("")){
			String sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
								  "' AND T."+filed1+" =S.NO(+) AND " +
								  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			if(filed1.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}if(filed1.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}
			String sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
								  "' AND T."+filed2+" =S.NO(+) AND " +
								  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1" ;
			if(filed2.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed2+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1";
			}if(filed2.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed2+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1";
			}
			LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
			aliasValueI18nMap.put("FIELD1_VALUE", sqlI18nContent);
			aliasValueI18nMap.put("FIELD2_VALUE", sqlI18nContent2);
			if(filed1.equalsIgnoreCase("CPNY_ID") || filed1.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD1_VALUE_FLAG", "Y");
			}
			if(filed2.equalsIgnoreCase("CPNY_ID") || filed2.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD2_VALUE_FLAG", "Y");
			}
			LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("BN_PARAM_DATA_OTHER",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			
			this.excelUtilSer.importData(request,response,map,modelMap);
		}
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importBonusInputItemDataExcelIsNotNull")
	public ModelAndView importBonusInputItemDataExcelIsNotNull(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		String paramNo=request.getParameter("id");
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PERSON_ID", "#CELL0#");
		aliasValueMap.put("START_MONTH", "#CELL2#");
		aliasValueMap.put("END_MONTH", "#CELL3#");
		aliasValueMap.put("RETURN_VALUE", "#CELL4#");
		aliasValueMap.put("REMARK", "#CELL5#");
		
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("PARAM_DATA_NO", "BN_PARAM_DATA_SEQ.NEXTVAL");
		//aliasValueMap.put("REMARK", "REMARK");
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("PARAM_DATA_NO", this.NUMBER);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		//第四步 可以为空的列 
		String aliasNullStr = ",5,";
		String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim(#CELL0#,' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("BN_PARAM_DATA",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("aliasNullStr", aliasNullStr);
		this.excelUtilSer.importData(request,response,map,modelMap);
		modelMap.put("rel", "viewBonusInputItemData");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	//------------------------------------------工资输入项目数据--------------------------------------------------
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPaInputItemDataExcelIsNull")
	public ModelAndView importPaInputItemDataExcelIsNull(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		String paramNo=request.getParameter("id");
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("FIELD1_VALUE", "#CELL0#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
		aliasValueMap.put("START_MONTH", "#CELL1#");
		aliasValueMap.put("END_MONTH", "#CELL2#");
		aliasValueMap.put("RETURN_VALUE", "#CELL3#");
		
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("PARAM_DATA_NO", "PA_PARAM_DATA_SEQ.NEXTVAL");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("FIELD1_VALUE", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("PARAM_DATA_NO", this.NUMBER);
		String filed1=request.getParameter("FIELD1");
		if(filed1!=null&&!filed1.equals("")){
			String sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
			  "' AND T."+filed1+" =S.NO(+) AND " +
			  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			if(filed1.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}if(filed1.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}
			LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
			aliasValueI18nMap.put("FIELD1_VALUE", sqlI18nContent);
			if(filed1.equalsIgnoreCase("CPNY_ID") || filed1.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD1_VALUE_FLAG", "Y");
			}
			LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_PARAM_DATA_OTHER",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			
			this.excelUtilSer.importData(request,response,map,modelMap);
		}
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）--工资维护输入项目数据
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPaInputItemDataExcelIsNotNull")
	public ModelAndView importPaInputItemDataExcelIsNotNull(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		String paramNo=request.getParameter("id");
		String DISTINCT_FIELD=request.getParameter("DISTINCT_FIELD");
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		if ("PERSON_ID".equals(DISTINCT_FIELD)) {
			//第二步，指定excel里要插入的列，以及默认要插入的列的值
			aliasValueMap.put("EMPID", "#CELL0#");//社号
			aliasValueMap.put("LOCAL_NAME", "#CELL1#");//姓名
			aliasValueMap.put("START_MONTH", "#CELL2#");//开始月
			aliasValueMap.put("END_MONTH", "#CELL3#");//开始月
			aliasValueMap.put("RETURN_VALUE", "#CELL4#");//数值
			aliasValueMap.put("REMARK", "#CELL5#");//备注
			aliasValueMap.put("ACTIVITY", 1);
			aliasValueMap.put("PARAM_NO", paramNo);
			//第三步，指定每列的类型
		
			aliasTypeMap.put("EMPID", this.VARCHAR);
			aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
			aliasTypeMap.put("START_MONTH", this.VARCHAR);
			aliasTypeMap.put("END_MONTH", this.VARCHAR);
			aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
			aliasTypeMap.put("REMARK", this.VARCHAR);
			aliasTypeMap.put("ACTIVITY", this.NUMBER);
			aliasTypeMap.put("PARAM_NO",this.NUMBER);
		}else {
			//第二步，指定excel里要插入的列，以及默认要插入的列的值
			aliasValueMap.put("POST_GRADE_NO", "#CELL0#");//社号
			aliasValueMap.put("WAGE_TYPE", "#CELL1#");//工资类型
			aliasValueMap.put("START_MONTH", "#CELL2#");//开始月
			aliasValueMap.put("END_MONTH", "#CELL3#");//开始月
			aliasValueMap.put("RETURN_VALUE", "#CELL4#");//数值
			aliasValueMap.put("REMARK", "#CELL5#");//备注
			aliasValueMap.put("ACTIVITY", 1);
			aliasValueMap.put("PARAM_NO", paramNo);
			//第三步，指定每列的类型
			aliasTypeMap.put("POST_GRADE_NO", this.VARCHAR);
			aliasTypeMap.put("WAGE_TYPE", this.VARCHAR);
			aliasTypeMap.put("START_MONTH", this.VARCHAR);
			aliasTypeMap.put("END_MONTH", this.VARCHAR);
			aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
			aliasTypeMap.put("REMARK", this.VARCHAR);
			aliasTypeMap.put("ACTIVITY", this.NUMBER);
			aliasTypeMap.put("PARAM_NO",this.NUMBER);
		}
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		if (!"PERSON_ID".equals(DISTINCT_FIELD)) {
			if ("TSTO".equals(admin.getCpnyId())) {
				String sqlI18nContent="SELECT  AA.CODE_NO POST_GRADE_NO FROM " +
				"(SELECT SN.CONTENT, S.CODE_NO,SC.DESCRIPTION CODE_MA FROM SY_CODE S, SY_CODE_PARAM SP, SY_GLOBAL_NAME SN,SY_CODE SC  WHERE S.CODE_NO = SP.CODE_NO(+) AND S.CODE_NO=SC.CODE_NO(+) AND S.CODE_NO = SN.NO  AND SN.LANGUAGE = 'zh' AND S.PARENT_CODE_NO IN ('14014289', '14014290', '14014291', '14014292', '14014293') AND SP.CPNY_ID = 'TSTO') AA" +
				" WHERE AA.CODE_MA=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM<2"; 
		       aliasValueI18nMap.put("POST_GRADE_NO", sqlI18nContent);
			}else {
				String sqlI18nContent="SELECT  AA.CODE_NO POST_GRADE_NO FROM " +
				"(SELECT SN.CONTENT, S.CODE_NO,SC.DESCRIPTION CODE_MA FROM SY_CODE S, SY_CODE_PARAM SP, SY_GLOBAL_NAME SN,SY_CODE SC  WHERE S.CODE_NO = SP.CODE_NO(+) AND S.CODE_NO=SC.CODE_NO(+) AND S.CODE_NO = SN.NO  AND SN.LANGUAGE = 'zh' AND S.PARENT_CODE_NO IN ('14015088','14015089','14015090','14015091','14015578','14643') AND SP.CPNY_ID = '"+admin.getCpnyId()+"') AA" +
				" WHERE AA.CODE_NO=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM<2"; 
		       aliasValueI18nMap.put("POST_GRADE_NO", sqlI18nContent);
			}
		
		}
		LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("PA_PARAM_DATA_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "";
		if ("PERSON_ID".equals(DISTINCT_FIELD)) {
			forwardUrl = "/pa/salary/viewImportExcelTempPaParamList";
		}else {
			forwardUrl = "/pa/salary/viewImportExcelTempPaParamGradeList";
		}
		String navTabId = "pa0212";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）--工资维护输入项目数据
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPaMonthInputItemDataExcelIsNotNull")
	public ModelAndView importPaMonthInputItemDataExcelIsNotNull(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		String paramNo=request.getParameter("id");
		String DISTINCT_FIELD=request.getParameter("DISTINCT_FIELD");
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		if ("PERSON_ID".equals(DISTINCT_FIELD)) {
			//第二步，指定excel里要插入的列，以及默认要插入的列的值
			aliasValueMap.put("EMPID", "#CELL0#");//社号
			aliasValueMap.put("LOCAL_NAME", "#CELL1#");//姓名
			aliasValueMap.put("START_MONTH", "#CELL2#");//开始月
			aliasValueMap.put("END_MONTH", "#CELL3#");//开始月
			aliasValueMap.put("ACTIVITY", 1);
			aliasValueMap.put("PARAM_NO", paramNo);
			//第三步，指定每列的类型
		
			aliasTypeMap.put("EMPID", this.VARCHAR);
			aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
			aliasTypeMap.put("START_MONTH", this.VARCHAR);
			aliasTypeMap.put("END_MONTH", this.VARCHAR);
			aliasTypeMap.put("ACTIVITY", this.NUMBER);
			aliasTypeMap.put("PARAM_NO",this.NUMBER);
		}else {
			//第二步，指定excel里要插入的列，以及默认要插入的列的值
			aliasValueMap.put("POST_GRADE_NO", "#CELL0#");//社号
			aliasValueMap.put("WAGE_TYPE", "#CELL1#");//工资类型
			aliasValueMap.put("START_MONTH", "#CELL2#");//开始月
			aliasValueMap.put("END_MONTH", "#CELL3#");//开始月
			aliasValueMap.put("ACTIVITY", 1);
			aliasValueMap.put("PARAM_NO", paramNo);
			//第三步，指定每列的类型
			aliasTypeMap.put("POST_GRADE_NO", this.VARCHAR);
			aliasTypeMap.put("WAGE_TYPE", this.VARCHAR);
			aliasTypeMap.put("START_MONTH", this.VARCHAR);
			aliasTypeMap.put("END_MONTH", this.VARCHAR);
			aliasTypeMap.put("ACTIVITY", this.NUMBER);
			aliasTypeMap.put("PARAM_NO",this.NUMBER);
		}
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		if (!"PERSON_ID".equals(DISTINCT_FIELD)) {
			if ("TSTO".equals(admin.getCpnyId())) {
				String sqlI18nContent="SELECT  AA.CODE_NO POST_GRADE_NO FROM " +
				"(SELECT SN.CONTENT, S.CODE_NO,SC.DESCRIPTION CODE_MA FROM SY_CODE S, SY_CODE_PARAM SP, SY_GLOBAL_NAME SN,SY_CODE SC  WHERE S.CODE_NO = SP.CODE_NO(+) AND S.CODE_NO=SC.CODE_NO(+) AND S.CODE_NO = SN.NO  AND SN.LANGUAGE = 'zh' AND S.PARENT_CODE_NO IN ('14014289', '14014290', '14014291', '14014292', '14014293') AND SP.CPNY_ID = 'TSTO') AA" +
				" WHERE AA.CODE_MA=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM<2"; 
		       aliasValueI18nMap.put("POST_GRADE_NO", sqlI18nContent);
			}else {
				String sqlI18nContent="SELECT  AA.CODE_NO POST_GRADE_NO FROM " +
				"(SELECT SN.CONTENT, S.CODE_NO,SC.DESCRIPTION CODE_MA FROM SY_CODE S, SY_CODE_PARAM SP, SY_GLOBAL_NAME SN,SY_CODE SC  WHERE S.CODE_NO = SP.CODE_NO(+) AND S.CODE_NO=SC.CODE_NO(+) AND S.CODE_NO = SN.NO  AND SN.LANGUAGE = 'zh' AND S.PARENT_CODE_NO IN ('14015088','14015089','14015090','14015091','14015578','14643') AND SP.CPNY_ID = '"+admin.getCpnyId()+"') AA" +
				" WHERE AA.CODE_NO=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM<2"; 
		       aliasValueI18nMap.put("POST_GRADE_NO", sqlI18nContent);
			}
		
		}
		LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("PA_PARAM_MONTH_DATA_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "";
		forwardUrl = "/pa/salary/viewImportExcelTempPaParamMonthList";
		String navTabId = "pa0212";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	/*	AdminBean admin=SessionUtil.getLoginUserFromSession(request);
	this.pathCpnyID=admin.getCpnyId();
	String paramNo=request.getParameter("id");
	
	//第二步，指定excel里要插入的列，以及默认要插入的列的值
	LinkedHashMap aliasValueMap = new LinkedHashMap();
	aliasValueMap.put("PERSON_ID", "#CELL0#");
	aliasValueMap.put("START_MONTH", "#CELL2#");
	aliasValueMap.put("END_MONTH", "#CELL3#");
	aliasValueMap.put("RETURN_VALUE", "#CELL4#");
	aliasValueMap.put("REMARK", "#CELL5#");//备注
	
	aliasValueMap.put("CPNY_ID", pathCpnyID);
	aliasValueMap.put("ACTIVITY", 1);
	aliasValueMap.put("CREATE_DATE","SYSDATE");
	aliasValueMap.put("CREATED_BY",admin.getPersonId());
	aliasValueMap.put("PARAM_NO", paramNo);
	aliasValueMap.put("PARAM_DATA_NO", "PA_PARAM_DATA_SEQ.NEXTVAL");
	
	//第三步，指定每列的类型
	LinkedHashMap aliasTypeMap = new LinkedHashMap();
	
	aliasTypeMap.put("PERSON_ID", this.VARCHAR);
	aliasTypeMap.put("START_MONTH", this.VARCHAR);
	aliasTypeMap.put("END_MONTH", this.VARCHAR);
	aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
	aliasTypeMap.put("REMARK", this.VARCHAR);
	
	aliasTypeMap.put("CPNY_ID", this.VARCHAR);
	aliasTypeMap.put("ACTIVITY", this.NUMBER);
	aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
	aliasTypeMap.put("CREATED_BY",this.VARCHAR);
	aliasTypeMap.put("PARAM_NO",this.NUMBER);
	aliasTypeMap.put("PARAM_DATA_NO", this.NUMBER);
	
	//第四步 可以为空的列 
	String aliasNullStr = ",5,";
	String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"' "; 
	LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
	aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
	
	LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_PARAM_DATA",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
	map.put("aliasNullStr", aliasNullStr);
	this.excelUtilSer.importData(request,response,map,modelMap);
	modelMap.put("rel", "viewPaInputItemData");
	return new ModelAndView("/pa/excelImport/alertMsg",modelMap);	*/	
	
	
	/**
	 * 导入数据 （调用时需要重写的方法）--工资维护输入项目数据
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPaInputItemDataExcelIsNotNullFSE")
	public ModelAndView importPaInputItemDataExcelIsNotNullFSE(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		String paramNo=request.getParameter("id");
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("EMPID", "#CELL0#");//社号
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");//姓名
		aliasValueMap.put("START_MONTH", "#CELL2#");//开始月
		aliasValueMap.put("END_MONTH", "#CELL3#");//开始月
		aliasValueMap.put("RETURN_VALUE", "#CELL4#");//数值
		aliasValueMap.put("REMARK", "#CELL5#");//备注
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("PARAM_NO", paramNo);
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("PA_PARAM_DATA_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/pa/salary/viewImportExcelTempPaParamListFSE";
		String navTabId = "pa0219";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPaInputItemDataExcelIsNull2")
	public ModelAndView importPaInputItemDataExcelIsNull2(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		String paramNo=request.getParameter("id");
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("FIELD1_VALUE", "#CELL0#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
		aliasValueMap.put("FIELD2_VALUE", "#CELL1#");
		aliasValueMap.put("START_MONTH", "#CELL2#");
		aliasValueMap.put("END_MONTH", "#CELL3#");
		aliasValueMap.put("RETURN_VALUE", "#CELL4#");
		
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("PARAM_DATA_NO", "PA_PARAM_DATA_SEQ.NEXTVAL");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("FIELD1_VALUE", this.VARCHAR);
		aliasTypeMap.put("FIELD2_VALUE", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("PARAM_DATA_NO", this.NUMBER);
		
		//指定需要国际化的字段，以及国际化的语句
		String filed1=request.getParameter("FIELD1");
		String filed2=request.getParameter("FIELD2");
		if(filed1!=null&&!filed1.equals("")){
			String sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
								  "' AND T."+filed1+" =S.NO(+) AND " +
								  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			if(filed1.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}if(filed1.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}
			String sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
								  "' AND T."+filed2+" =S.NO(+) AND " +
								  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1" ;
			if(filed2.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed2+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1";
			}if(filed2.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed2+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1";
			}
			LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
			aliasValueI18nMap.put("FIELD1_VALUE", sqlI18nContent);
			aliasValueI18nMap.put("FIELD2_VALUE", sqlI18nContent2);
			if(filed1.equalsIgnoreCase("CPNY_ID") || filed1.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD1_VALUE_FLAG", "Y");
			}if(filed2.equalsIgnoreCase("CPNY_ID") ||filed2.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD2_VALUE_FLAG", "Y");
			}
			LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_PARAM_DATA_OTHER",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			
			this.excelUtilSer.importData(request,response,map,modelMap);	
		}
	
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	//------------------------------------------基础项目数据--------------------------------------------------
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPaBasicItemDataExcelIsNull") 
	public ModelAndView importPaBasicItemDataExcelIsNull(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		String paramNo=request.getParameter("id");
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("FIELD1_VALUE", "#CELL0#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
		aliasValueMap.put("START_DATE", "#CELL1#");
		aliasValueMap.put("RETURN_VALUE", "#CELL2#");
		aliasValueMap.put("REMARK", "#CELL3#");//备注
		
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("BASIC_DATA_NO", "PA_BASE_DATA_SEQ.NEXTVAL");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("FIELD1_VALUE", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.DATE);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("BASIC_DATA_NO", this.NUMBER);
		
		//第四步 可以为空的列 
		String aliasNullStr = ",3,";
		String filed1=request.getParameter("FIELD1");
		if(filed1!=null&&!filed1.equals("")){
			String sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
			  "' AND T."+filed1+" =S.NO(+) AND " +
			  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			if(filed1.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}if(filed1.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}
			
			LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
			aliasValueI18nMap.put("FIELD1_VALUE", sqlI18nContent);
			if(filed1.equalsIgnoreCase("CPNY_ID") || filed1.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD1_VALUE_FLAG", "Y");
			}
			LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_BASIC_DATA_OTHER",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			map.put("aliasNullStr", aliasNullStr);
			this.excelUtilSer.importData(request,response,map,modelMap);
		}
		modelMap.put("rel", "viewPaBasicItemData");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）--工资维护输入项目数据
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPaBasicItemDataExcelIsNotNull")
	public ModelAndView importPaBasicItemDataExcelIsNotNull(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		String paramNo=request.getParameter("id");
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("EMPID", "#CELL0#");//社号
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");//姓名
		aliasValueMap.put("START_DATE", "#CELL2#");//开始月
		aliasValueMap.put("RETURN_VALUE", "#CELL3#");//数值
		aliasValueMap.put("REMARK", "#CELL4#");//备注
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("PARAM_NO", paramNo);
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("PA_BASIC_DATA_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/pa/wagebase/viewImportExcelTempPaBasicItemList";
		String navTabId = "pa0503";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);	
	}
		/*AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		String paramNo=request.getParameter("id");
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PERSON_ID", "#CELL0#");
		aliasValueMap.put("START_DATE", "#CELL2#");
		//aliasValueMap.put("END_DATE", "#CELL6#");
		aliasValueMap.put("RETURN_VALUE", "#CELL3#");
		aliasValueMap.put("REMARK", "#CELL4#");//备注
		
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("BASIC_DATA_NO", "PA_BASE_DATA_SEQ.NEXTVAL");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.DATE); 
		//aliasTypeMap.put("END_DATE", this.DATE);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("BASIC_DATA_NO", this.NUMBER);
		
		//第四步 可以为空的列 
		String aliasNullStr = ",4,";
		String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"' "; 
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_BASIC_DATA",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("aliasNullStr", aliasNullStr);
		this.excelUtilSer.importData(request,response,map,modelMap);
		modelMap.put("rel", "viewPaBasicItemData");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);*/		

	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPaBasicItemDataExcelIsNull2")
	public ModelAndView importPaBasicItemDataExcelIsNull2(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		String paramNo=request.getParameter("id");
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("FIELD1_VALUE", "#CELL0#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
		aliasValueMap.put("FIELD2_VALUE", "#CELL1#");
		aliasValueMap.put("START_DATE", "#CELL2#");
//		aliasValueMap.put("END_DATE", "#CELL3#");
		aliasValueMap.put("RETURN_VALUE", "#CELL3#");
		
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("FIELD1_VALUE", this.VARCHAR);
		aliasTypeMap.put("FIELD2_VALUE", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.DATE);
//		aliasTypeMap.put("END_DATE", this.DATE);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("BASIC_DATA_NO", this.NUMBER);
		
		//指定需要国际化的字段，以及国际化的语句
		String filed1=request.getParameter("FIELD1");
		String filed2=request.getParameter("FIELD2");
		if(filed1!=null&&!filed1.equals("")){
			String sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
								  "' AND T."+filed1+" =S.NO(+) AND " +
								  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			if(filed1.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}if(filed1.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}if(filed1.equalsIgnoreCase("OLD_POST_GRADE_NO")){
			     sqlI18nContent=" SELECT T."+filed1+" from HR_OLD_POST_GRADE t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+" =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}
			if(filed1.equalsIgnoreCase("DUTY_NO")){
			     sqlI18nContent=" SELECT T."+filed1+" from HR_DUTY t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+" =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}
			
			
			
			String sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
								  "' AND T."+filed2+" =S.NO(+) AND " +
								  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1" ;
			if(filed2.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed2+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1";
			}if(filed2.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed2+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1";
			}if(filed2.equalsIgnoreCase("OLD_POST_GRADE_NO")){
				sqlI18nContent2=" SELECT T."+filed2+" from HR_OLD_POST_GRADE t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed2+" =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1";
			}
			if(filed2.equalsIgnoreCase("DUTY_NO")){
				sqlI18nContent2=" SELECT T."+filed2+" from HR_DUTY t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed2+" =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1";
			}
			
			LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
			aliasValueI18nMap.put("FIELD1_VALUE", sqlI18nContent);
			aliasValueI18nMap.put("FIELD2_VALUE", sqlI18nContent2);
			if(filed1.equalsIgnoreCase("CPNY_ID") || filed1.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD1_VALUE_FLAG", "Y");
			}if(filed2.equalsIgnoreCase("CPNY_ID") || filed2.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD2_VALUE_FLAG", "Y");
			}
			LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_BASIC_DATA_OTHER",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			
			this.excelUtilSer.importData(request,response,map,modelMap);	
		}
		modelMap.put("rel", "viewPaBasicItemData");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	//---------------------------------------------跳转到上传附件的页面------------------------------------------------------------------
	/**
	 * 跳转到上传excel页面
	 * Description:the page to import the excel
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importInsuranceInputItemData")
	public ModelAndView importInsuranceInputItemData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		modelMap.put("id", request.getParameter("id"));
		modelMap.put("type", request.getParameter("type"));
		modelMap.put("importFunName", request.getParameter("importFunName"));
		modelMap.put("FIELD2_NAME", request.getParameter("FIELD2_NAME"));
		modelMap.put("FIELD1_NAME", request.getParameter("FIELD1_NAME"));
		return new ModelAndView("/pa/excelImport/importInsuranceInputItemData",modelMap);		
	} 
	
	/**
	 * 跳转到上传页面--派遣地管理的导入功能
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPaiQianDiGUanLiInfoData")
	public ModelAndView importPaiQianDiGUanLiInfoData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		modelMap.put("importFunName", request.getParameter("importFunName"));
		return new ModelAndView("/pa/excelImport/importPaiQianDiGUanLiInfoData",modelMap);		
	} 
	/**
	 * 跳转到上传页面--考勤决裁例外的导入功能
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importArExceptionAffirmInfoData")
	public ModelAndView importArExceptionAffirmInfoData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		modelMap.put("importFunName", request.getParameter("importFunName"));
		return new ModelAndView("/pa/excelImport/importArExceptionAffirmInfoData",modelMap);		
	} 
	/**
	 * 派遣津贴标准导入页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPaiQianDiJinTieBiaoZhunInfoData")
	public ModelAndView importPaiQianDiJinTieBiaoZhunInfoData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		modelMap.put("importFunName", request.getParameter("importFunName"));
		return new ModelAndView("/pa/excelImport/importPaiQianDiJinTieBiaoZhunInfoData",modelMap);		
	} 
	
	/**
	 * 跳转到上传excel页面
	 * Description:the page to import the excel
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importArItemData")
	public ModelAndView importArItemData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		modelMap.put("id", request.getParameter("id"));
		modelMap.put("type", request.getParameter("type"));
		modelMap.put("importFunName", request.getParameter("importFunName"));
		return new ModelAndView("/ar/excelImport/importArItemData",modelMap);		
	} 
	
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importArItemDataExcel")
	public ModelAndView importArItemDataExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("AR_MONTH", "#CELL0#");
		aliasValueMap.put("PERSON_ID", "#CELL1#");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("AR_MONTH", this.VARCHAR);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		
		String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL1# AND CPNY_ID='"+this.pathCpnyID+"' ";
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("AR_SUMMARY_"+pathCpnyID,aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		this.excelUtilSer.importArData(request,response,map,modelMap);
		return new ModelAndView("/ar/excelImport/alertMsg",modelMap);		
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importArDetailExcel")
	public ModelAndView importArDetailExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PERSON_ID", "#CELL2#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
		aliasValueMap.put("FROM_TIME", "#CELL6#");
		aliasValueMap.put("TO_TIME", "#CELL7#");
	//	aliasValueMap.put("AR_DATE_STR", "#CELL0#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
		String sqlGetAR_DATE_STR="SELECT TO_CHAR(TO_DATE('#CELL0#','YYYY/MM/DD') ,'YYYY/MM/DD') AR_DATE_STR FROM DUAL ";
		aliasValueMap.put("AR_DATE_STR", sqlGetAR_DATE_STR);
		aliasValueMap.put("AR_MONTH_STR", "#CELL1#");
		aliasValueMap.put("ITEM_NO", "#CELL5#");
		aliasValueMap.put("QUANTITY", "#CELL8#");
		//	aliasValueMap.put("STATUS_CODE", "#CELL9#");
	   // aliasValueMap.put("STATUS_NAME", "#CELL9#");
		String sqlIGetStatusCode="SELECT  GET_STATUS_CODE_DAILY(TO_CHAR(TO_DATE('#CELL0#','YYYY/MM/DD'),'YYYY/MM/DD'), PERSON_ID)   FROM HR_EMPLOYEE  WHERE EMPID = ltrim(rtrim('#CELL2#', ' '), ' ') AND CPNY_ID = '"+this.pathCpnyID+"' ";
		aliasValueMap.put("STATUS_CODE", sqlIGetStatusCode);
		
		String sqlIGetStatusName="SELECT S2.CONTENT STATUS_NAME  FROM   SY_GLOBAL_NAME S2 WHERE  S2.LANGUAGE  = '"+admin.getLanguage()+"' AND S2.No= ( SELECT  GET_STATUS_CODE_DAILY('#CELL0#', PERSON_ID)   FROM HR_EMPLOYEE  WHERE EMPID = ltrim(rtrim('#CELL2#', ' '), ' ') AND CPNY_ID = '"+this.pathCpnyID+"' )";
		aliasValueMap.put("STATUS_NAME", sqlIGetStatusName);
		
		String sqlIGetArShiftNo="SELECT GET_AR_SHIFTNO(PERSON_ID,TO_CHAR(TO_DATE('#CELL0#','YYYY/MM/DD'),'YYYY/MM/DD'),'"+this.pathCpnyID+"') FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL2#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"'";
		aliasValueMap.put("SHIFT_NO", sqlIGetArShiftNo);
		String sqlGetDateType="SELECT GET_AR_DATETYPE(PERSON_ID,TO_CHAR(TO_DATE('#CELL0#','YYYY/MM/DD'),'YYYY/MM/DD'),'"+this.pathCpnyID+"') FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL2#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"'";
		aliasValueMap.put("DATE_TYPE", sqlGetDateType);
		String unit="SELECT A.UNIT FROM AR_ITEM_PARAM A,SY_GLOBAL_NAME S WHERE A.ITEM_NO = S.NO(+) AND A.CPNY_ID = '"+this.pathCpnyID+"' AND S.CONTENT='#CELL5#' AND ROWNUM=1";
		aliasValueMap.put("UNIT",  unit);
		
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PK_NO", "AR_DETAIL_SEQ.NEXTVAL");
		aliasValueMap.put("LOCK_YN", "Y");
		aliasValueMap.put("REMARK", "EXECL导入");
		LinkedHashMap aliasHistoryValueMap = new LinkedHashMap();
		aliasHistoryValueMap.put("PK_NO", "AR_DETAIL_HISTORY_SEQ.NEXTVAL");
		aliasHistoryValueMap.put("CPNY_ID", this.pathCpnyID);
		aliasHistoryValueMap.put("PERSON_ID", "#CELL2#");
		aliasHistoryValueMap.put("AR_DATE_STR", "#CELL0#");
		aliasHistoryValueMap.put("NEW_ITEM_NO", "#CELL5#");
		aliasHistoryValueMap.put("NEW_QUANTITY", "#CELL8#");
		aliasHistoryValueMap.put("OPERATION","EXCEL");
		aliasHistoryValueMap.put("CREATED_BY",admin.getPersonId());
		aliasHistoryValueMap.put("CREATE_DATE","SYSDATE");
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("FROM_TIME", this.DATE_HMS);
		aliasTypeMap.put("TO_TIME", this.DATE_HMS);
		aliasTypeMap.put("AR_DATE_STR", this.VARCHAR);
		aliasTypeMap.put("AR_MONTH_STR", this.VARCHAR);
		aliasTypeMap.put("ITEM_NO", this.NUMBER);
		aliasTypeMap.put("QUANTITY", this.VARCHAR);
	    aliasTypeMap.put("STATUS_CODE", this.NUMBER);
	    aliasTypeMap.put("STATUS_NAME", this.VARCHAR);
		
		aliasTypeMap.put("SHIFT_NO", this.NUMBER);
		aliasTypeMap.put("DATE_TYPE", this.NUMBER);
		aliasTypeMap.put("UNIT",  this.VARCHAR);
//		
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PK_NO", this.NUMBER);
		aliasTypeMap.put("LOCK_YN", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		
		LinkedHashMap aliasHistoryTypeMap = new LinkedHashMap();
		aliasHistoryTypeMap.put("PK_NO", this.NUMBER);
		aliasHistoryTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasHistoryTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasHistoryTypeMap.put("AR_DATE_STR", this.VARCHAR);
		aliasHistoryTypeMap.put("NEW_ITEM_NO", this.NUMBER);
		aliasHistoryTypeMap.put("NEW_QUANTITY", this.VARCHAR);
		aliasHistoryTypeMap.put("OPERATION",this.VARCHAR);
		aliasHistoryTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasHistoryTypeMap.put("CREATE_DATE",this.SYSDATE);
		//指定需要国际化的字段，以及国际化的语句
		String sqlI18nContent="SELECT ITEM_NO FROM AR_ITEM_PARAM A ,SY_GLOBAL_NAME S WHERE A.ITEM_NO=S.NO(+) AND S.CONTENT=#CELL5# AND ROWNUM=1";
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap aliasHistoryValueI18nMap = new LinkedHashMap();
		aliasValueI18nMap.put("ITEM_NO", sqlI18nContent);
//		String sqlI18nContent1="SELECT S1.CODE_NO FROM SY_CODE S1,SY_GLOBAL_NAME S2 WHERE S1.CODE_NO = S2.NO(+) AND S2.LANGUAGE(+) = '"+admin.getLanguage()+"' AND S2.CONTENT LIKE #CELL9# AND S1.PARENT_CODE_NO = '1372' AND ROWNUM = 1 ";
//		aliasValueI18nMap.put("STATUS_CODE", sqlI18nContent1);
	
		String sqlGetPersonId="SELECT PERSON_ID FROM HR_EMPLOYEE E WHERE E.EMPID=#CELL2# AND CPNY_ID='"+this.pathCpnyID+"'";
		aliasValueI18nMap.put("PERSON_ID", sqlGetPersonId);
		
	
			
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("AR_DETAIL_"+this.pathCpnyID,aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		aliasHistoryValueI18nMap.put("ITEM_NO", sqlI18nContent);
		aliasHistoryValueI18nMap.put("NEW_ITEM_NO", sqlI18nContent);
		
		
		aliasHistoryValueI18nMap.put("PERSON_ID", sqlGetPersonId);
		LinkedHashMap historymap=this.excelUtilSer.putIntoAliasValueAndTypeMap("AR_DETAIL_HISTORY",aliasHistoryValueMap,aliasHistoryTypeMap,aliasHistoryValueI18nMap);
	   
		this.excelUtilSer.importArDetialData(request,response,map,historymap,modelMap);
		
		if(modelMap.get("statusCode")!=null&&modelMap.get("statusCode").toString().equals("200")){
			modelMap.put("navTabId", "ar0201");
		}
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importArCardRecordExcel")
	public ModelAndView importArCardRecordExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		String paramNo=request.getParameter("id");
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("EMPID", "#CELL0#");//社号
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");//姓名
		//aliasValueMap.put("DEPT", "#CELL2#");//部门
		aliasValueMap.put("AR_DATE_STR", "#CELL2#");//考勤日期
		aliasValueMap.put("R_DATE", "#CELL3#");//日期
		aliasValueMap.put("R_TIME", "#CELL4#");//时间
		aliasValueMap.put("DOOR_TYPE", "#CELL5#");//类型
		aliasValueMap.put("REMARK", "#CELL6#");//备注
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		//aliasTypeMap.put("DEPT", this.VARCHAR);
		aliasTypeMap.put("AR_DATE_STR", this.VARCHAR);
		aliasTypeMap.put("R_DATE", this.VARCHAR);
		aliasTypeMap.put("R_TIME", this.VARCHAR);
		aliasTypeMap.put("DOOR_TYPE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("AR_MAC_RECORDS_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/ar/attendanceMintenance/viewImportExcelTempMacRecordsList";
		String navTabId = "ar0104";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		this.arCardRecordSer.valImportExcelArCardData(request);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);	
	}
	
	@RequestMapping(value = "/importArCardMealExcel")
	public ModelAndView importArCardMealExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		String paramNo=request.getParameter("id");
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("EMPID", "#CELL0#");//社号
		aliasValueMap.put("EAT_NAME", "#CELL1#");//姓名
		//aliasValueMap.put("DEPT", "#CELL2#");//部门
		aliasValueMap.put("EAT_DATE", "#CELL2#");//考勤日期
		aliasValueMap.put("AMOUNT", "#CELL3#");//日期
		aliasValueMap.put("AR_DATE_STR", "#CELL4#");//时间
		aliasValueMap.put("EMPLOYEE_NAME", "#CELL5#");//类型
		aliasValueMap.put("DEPART_NAME", "#CELL6#");//备注
		aliasValueMap.put("TITLE", "#CELL7#");//备注
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("EAT_NAME", this.VARCHAR);
		//aliasTypeMap.put("DEPT", this.VARCHAR);
		aliasTypeMap.put("EAT_DATE", this.VARCHAR);
		aliasTypeMap.put("AMOUNT", this.VARCHAR);
		aliasTypeMap.put("AR_DATE_STR", this.VARCHAR);
		aliasTypeMap.put("EMPLOYEE_NAME", this.VARCHAR);
		aliasTypeMap.put("DEPART_NAME", this.VARCHAR);
		aliasTypeMap.put("TITLE", this.VARCHAR);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",SEQ,CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",AR_MAC_RECORDS_TSTO_EAT_SEQ.NEXTVAL,'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("AR_MAC_RECORDS_HTSV_EAT",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/ar/attendanceMintenance/viewArCardRecordMeal";
		String navTabId = "ar0104";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		this.arCardRecordSer.valImportExcelArCardData(request);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);	
	}
	/**
	 * 评价信息excel导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importEvaluateInfoExcel")
	public ModelAndView importEvaluateInfoExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		String paramNo=request.getParameter("id");
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		/*	aliasValueMap.put("EMPID", "#CELL0#");//社号
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");//姓名
		aliasValueMap.put("DEPT", "#CELL2#");//部门
		aliasValueMap.put("R_TIME", "#CELL3#");//时间
		aliasValueMap.put("DOOR_TYPE", "#CELL4#");//类型
		aliasValueMap.put("REMARK", "#CELL5#");//备注
		aliasValueMap.put("INSERT_BY", "H");
		aliasValueMap.put("ACTIVITY", 1);
		 */	
		aliasValueMap.put("PERSON_ID", "#CELL0#");//社号
		aliasValueMap.put("EVALUATE_YEAR", "#CELL1#");//评价年度
		aliasValueMap.put("JANUARY", "#CELL2#");//评价年度
		aliasValueMap.put("FEBRUARY", "#CELL3#");//评价年度
		aliasValueMap.put("MARCH", "#CELL4#");//评价年度
		aliasValueMap.put("APRIL", "#CELL5#");//评价年度
		aliasValueMap.put("MAY", "#CELL6#");//评价年度
		aliasValueMap.put("JUNE", "#CELL7#");//评价年度
		aliasValueMap.put("JULY", "#CELL8#");//评价年度
		aliasValueMap.put("AUGUST", "#CELL9#");//评价年度
		aliasValueMap.put("SEPTEMBER", "#CELL10#");//评价年度
		aliasValueMap.put("OCTOBER", "#CELL11#");//评价年度
		aliasValueMap.put("NOVEMBER", "#CELL12#");//评价年度
		aliasValueMap.put("DECEMBER", "#CELL13#");//评价年度
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PERSON_ID", this.NUMBER);
		aliasTypeMap.put("EVALUATE_YEAR", this.VARCHAR);
		aliasTypeMap.put("JANUARY", this.VARCHAR);
		aliasTypeMap.put("FEBRUARY", this.VARCHAR);
		aliasTypeMap.put("MARCH", this.VARCHAR);
		aliasTypeMap.put("APRIL", this.VARCHAR);
		aliasTypeMap.put("MAY", this.VARCHAR);
		aliasTypeMap.put("JUNE", this.VARCHAR);
		aliasTypeMap.put("JULY", this.VARCHAR);
		aliasTypeMap.put("AUGUST", this.VARCHAR);
		aliasTypeMap.put("SEPTEMBER", this.VARCHAR);
		aliasTypeMap.put("OCTOBER", this.VARCHAR);
		aliasTypeMap.put("NOVEMBER", this.VARCHAR);
		aliasTypeMap.put("DECEMBER", this.VARCHAR);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CREATED_BY,CREATE_DATE,CREATED_IP,EVALUATE_NO";
		String appendValue=",'" + admin.getPersonId()	+ "',sysdate,'" + admin.getAdminIP()+ "',HR_EVALUATE_INFO_SEQ.NEXTVAL";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_EVALUATE_INFO",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/ar/attendanceMintenance/viewImportExcelTempMacRecordsList";
		String navTabId = "ar0104";
		this.excelUtilSer.importEvaluateExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);	
	}
	/*AdminBean admin=SessionUtil.getLoginUserFromSession(request);
	this.pathCpnyID=admin.getCpnyId();
	this.language=admin.getLanguage();
	
	LinkedHashMap aliasValueMap = new LinkedHashMap();
	aliasValueMap.put("RECORD_NO", "AR_RECORDS_SEQ.NEXTVAL");
	aliasValueMap.put("R_TIME", "#CELL3#");
	aliasValueMap.put("PERSON_ID", "#CELL0#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
	aliasValueMap.put("DOOR_TYPE", "#CELL4#");
	aliasValueMap.put("INSERT_BY", "H");
	aliasValueMap.put("REMARK", "#CELL5#");
	aliasValueMap.put("ACTIVITY", "1");
	
	aliasValueMap.put("INSERT_TIME", "SYSDATE");
	aliasValueMap.put("CREATE_DATE", "SYSDATE");
	aliasValueMap.put("CREATED_BY", admin.getPersonId());
	
	LinkedHashMap aliasTypeMap = new LinkedHashMap();
	
	aliasTypeMap.put("RECORD_NO", this.NUMBER);
	aliasTypeMap.put("R_TIME", this.DATE_HMS);
	aliasTypeMap.put("PERSON_ID", this.VARCHAR);
	aliasTypeMap.put("DOOR_TYPE", this.VARCHAR);
	aliasTypeMap.put("INSERT_BY", this.VARCHAR );
	aliasTypeMap.put("REMARK", this.VARCHAR);
	aliasTypeMap.put("ACTIVITY", this.NUMBER);
	aliasTypeMap.put("INSERT_TIME", this.SYSDATE);
	aliasTypeMap.put("CREATE_DATE", this.SYSDATE);
	aliasTypeMap.put("CREATED_BY", this.VARCHAR);
	
	LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
	String sqlGetPersonId="SELECT PERSON_ID FROM HR_EMPLOYEE E WHERE E.EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"'";
	aliasValueI18nMap.put("PERSON_ID", sqlGetPersonId);
		
	LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("AR_MAC_RECORDS_"+this.pathCpnyID,aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		
	this.excelUtilSer.importArCardRecordData(request,response,map,modelMap);	

	if(modelMap.get("statusCode")!=null&&modelMap.get("statusCode").toString().equals("200")){
		modelMap.put("navTabId", "ar0104");
	}
	
	return new ModelAndView("/pa/excelImport/alertMsg",modelMap);*/
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importArShiftExcel")
	public ModelAndView importArShiftExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PERSON_ID", "#CELL0#");
		aliasValueMap.put("AR_DATE_STR", "replace(#CELLA#,'-','/')");
		aliasValueMap.put("PK_NO", "AR_SCHEDULE_SEQ.NEXTVAL");
		aliasValueMap.put("LOCK_YN", "N");
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		
		LinkedHashMap tempMap = new LinkedHashMap() ;
		tempMap.put("PERSON_ID", admin.getPersonId());
		String distinguishNo = "";
		distinguishNo = this.shiftDao.getDeptDistinguishNo(tempMap) ;
		
		/*Calendar cal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		// 当前月的最后一天
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		Date endTime = cal.getTime();
		String endTime1 = datef.format(endTime);
		int endDay = Integer.parseInt(endTime1.substring(8,10));
		for (int i=1;i<=endDay ;i++){
			if(i<10){
				aliasNameList.add(endTime1.substring(0, 8) +"0" + i);
			}else{
				aliasNameList.add(endTime1.substring(0, 8) + i);
			}	
		}	*/
		String sqlIGetArShiftID = "";
		if("".equals(distinguishNo)){
			sqlIGetArShiftID = " SELECT SHIFT_NO FROM AR_SHIFT010 WHERE CPNY_ID = '"+this.pathCpnyID+"' AND SHIFT_ID LIKE '#CELL34#' AND DEPT_DISTINGUISH_NO IS NULL ";
		}else{
			sqlIGetArShiftID = " SELECT SHIFT_NO FROM AR_SHIFT010 WHERE CPNY_ID = '"+this.pathCpnyID+"' AND SHIFT_ID LIKE '#CELL34#' AND (DEPT_DISTINGUISH_NO IS NULL OR DEPT_DISTINGUISH_NO = '"+distinguishNo+"') ";
		}
		
		aliasValueMap.put("SHIFT_NO", sqlIGetArShiftID);
		aliasValueMap.put("CREATED_BY", admin.getPersonId());
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("AR_DATE_STR", this.NVARCHAR);
		aliasTypeMap.put("PK_NO", this.NUMBER);
		aliasTypeMap.put("LOCK_YN", this.VARCHAR);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("SHIFT_NO", this.VARCHAR);
		aliasTypeMap.put("CREATED_BY",  this.VARCHAR);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String sqlGetPersonId="SELECT PERSON_ID FROM HR_EMPLOYEE E WHERE E.EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"'";
		aliasValueI18nMap.put("PERSON_ID", sqlGetPersonId);
			
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("AR_SCHEDULE_"+this.pathCpnyID,aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			
		this.excelUtilSer.importArShiftData(request,response,map,modelMap);	
	
		if(modelMap.get("statusCode")!=null&&modelMap.get("statusCode").toString().equals("200")){
			modelMap.put("navTabId", "ar0204");
		}
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importArCardRecordExcel3")
	public ModelAndView importArCardRecordExcel3(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("RECORD_NO", "AR_RECORDS_SEQ.NEXTVAL");
		aliasValueMap.put("R_TIME", "#CELL3#");
		aliasValueMap.put("DOOR_TYPE", "#CELL4#");
		aliasValueMap.put("INSERT_BY", "H");
		aliasValueMap.put("REMARK", "#CELL5#");
		aliasValueMap.put("ACTIVITY", "1");
		aliasValueMap.put("CARD_NO", "#CELL0#");
		
		aliasValueMap.put("INSERT_TIME", "SYSDATE");
		aliasValueMap.put("CREATE_DATE", "SYSDATE");
		aliasValueMap.put("CREATED_BY", admin.getPersonId());
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("RECORD_NO", this.NUMBER);
		aliasTypeMap.put("R_TIME", this.DATE_HMS);
		aliasTypeMap.put("DOOR_TYPE", this.VARCHAR);
		aliasTypeMap.put("INSERT_BY", this.VARCHAR );
		aliasTypeMap.put("REMARK", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("INSERT_TIME", this.SYSDATE);
		aliasTypeMap.put("CREATE_DATE", this.SYSDATE);
		aliasTypeMap.put("CREATED_BY", this.VARCHAR);
		aliasTypeMap.put("CARD_NO", this.VARCHAR);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String sqlGetPersonId="SELECT PERSON_ID FROM AR_CARD_ASSOCIATE E WHERE E.CARD_NO = '" + "#CELL0#" + "' " 
				+ " AND CPNY_ID='"+this.pathCpnyID+"' "
				+ " AND (SYSDATE >= NVL(DATE_START,TO_DATE('1900-12-31','YYYY-MM-DD')) " 
				+ "			AND SYSDATE <= NVL(DATE_END,TO_DATE('9999-12-31','YYYY-MM-DD'))) " ;
		aliasValueI18nMap.put("PERSON_ID", sqlGetPersonId);
		aliasValueMap.put("PERSON_ID", sqlGetPersonId);//#CELLj# ---j是指定的excel里头的第几列，从0开始
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("AR_MAC_RECORDS_"+this.pathCpnyID,aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			
		this.excelUtilSer.importArCardRecordData3(request,response,map,modelMap);	
	
		if(modelMap.get("statusCode")!=null&&modelMap.get("statusCode").toString().equals("200")){
			modelMap.put("navTabId", "ar0104");
		}
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importArCardRecordExcel4")
	public ModelAndView importArCardRecordExcel4(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("RECORD_NO", "AR_RECORDS_SEQ.NEXTVAL");
		aliasValueMap.put("R_TIME", "#CELL3#");
		aliasValueMap.put("DOOR_TYPE", "#CELL4#");
		aliasValueMap.put("INSERT_BY", "H");
		aliasValueMap.put("REMARK", "#CELL5#");
		aliasValueMap.put("ACTIVITY", "1");
		aliasValueMap.put("CARD_NO", "#CELL0#");
		
		aliasValueMap.put("INSERT_TIME", "SYSDATE");
		aliasValueMap.put("CREATE_DATE", "SYSDATE");
		aliasValueMap.put("CREATED_BY", admin.getPersonId());
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("RECORD_NO", this.NUMBER);
		aliasTypeMap.put("R_TIME", this.DATE_HMS);
		aliasTypeMap.put("DOOR_TYPE", this.VARCHAR);
		aliasTypeMap.put("INSERT_BY", this.VARCHAR );
		aliasTypeMap.put("REMARK", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("INSERT_TIME", this.SYSDATE);
		aliasTypeMap.put("CREATE_DATE", this.SYSDATE);
		aliasTypeMap.put("CREATED_BY", this.VARCHAR);
		aliasTypeMap.put("CARD_NO", this.VARCHAR);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String sqlGetPersonId="SELECT PERSON_ID FROM AR_DINING_CARD_ASSOCIATE E WHERE E.CARD_NO = '" + "#CELL0#" + "' AND CPNY_ID='"+this.pathCpnyID+"'";
		aliasValueI18nMap.put("PERSON_ID", sqlGetPersonId);
		aliasValueMap.put("PERSON_ID", sqlGetPersonId);//#CELLj# ---j是指定的excel里头的第几列，从0开始
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("AR_MAC_RECORDS_"+this.pathCpnyID,aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			
		this.excelUtilSer.importArCardRecordData4(request,response,map,modelMap);	
	
		if(modelMap.get("statusCode")!=null&&modelMap.get("statusCode").toString().equals("200")){
			modelMap.put("navTabId", "ar0104");
		}
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	/**
	 * 派遣地管理导入功能
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaiQianDiInfoModule")
	public ModelAndView viewPaiQianDiInfoModule(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String checkResult = "1";
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PQD_NO", "sy_dispatch_seq.NEXTVAL");
		aliasValueMap.put("PQD_FAREN", "#CELL0#");
		aliasValueMap.put("PQD_CHENGSHIDENGJI", "#CELL1#");
		aliasValueMap.put("PQD_SHENGFEN",  "#CELL2#");
		aliasValueMap.put("PQD_CHENGSHIMINGCHENG", "#CELL3#");
		aliasValueMap.put("PQD_DIQUMINGCHENG", "#CELL4#");
		aliasValueMap.put("CREATED_BY", admin.getAdminID());
		aliasValueMap.put("CREATE_DATE", "SYSDATE");
		aliasValueMap.put("ACTIVITY", "1");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PQD_NO", this.NUMBER);
		aliasTypeMap.put("PQD_FAREN",  this.VARCHAR);
		aliasTypeMap.put("PQD_CHENGSHIDENGJI",  this.VARCHAR);
		aliasTypeMap.put("PQD_SHENGFEN", this.VARCHAR);
		aliasTypeMap.put("PQD_CHENGSHIMINGCHENG",  this.VARCHAR);
		aliasTypeMap.put("PQD_DIQUMINGCHENG",  this.VARCHAR);
		aliasTypeMap.put("CREATED_BY", this.VARCHAR);
		aliasTypeMap.put("CREATE_DATE",  this.SYSDATE);
		aliasTypeMap.put("ACTIVITY",  this.VARCHAR);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map = this.excelUtilSer.putIntoAliasValueAndTypeMap("sy_dispatch_temp",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		this.excelUtilSer.importPaiQianDiInfoData(request,response,map,modelMap);	
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	/**
	 * 导入数据  考勤决裁例外
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importArExceptionAffirmInfoDataModule")
	public ModelAndView importArExceptionAffirmInfoDataModule(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.language=admin.getLanguage();
		this.pathCpnyID=admin.getCpnyId();
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("PERSON_ID", "#CELL0#");//社号
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");//姓名
		aliasValueMap.put("START_DATE", "#CELL2#");//开始时间
		aliasValueMap.put("END_DATE", "#CELL3#");//结束时间
		aliasValueMap.put("ACTIVITY", "#CELL4#");//标识
		
		//aliasValueMap.put("PERSON_ID","#CELL4#");
		aliasValueMap.put("EXCEPTION_NO","AR_EXCEPTION_AFFIRM_SEQ.NEXTVAL");
		
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.VARCHAR);
		aliasTypeMap.put("END_DATE", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.VARCHAR);
		
		
		aliasTypeMap.put("EXCEPTION_NO", this.NUMBER);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		String sqlGetPersonId="SELECT PERSON_ID FROM HR_EMPLOYEE E WHERE E.EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"'";
		aliasValueI18nMap.put("PERSON_ID", sqlGetPersonId);
		LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("AR_EXCEPTION_AFFIRM",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/ar/attendanceView/viewArAffirmExceptionList?pageNum=1&numPerPage=10"; 
		String navTabId = "ar0117";             
		this.excelUtilSer.importExcelData2(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	/**
	 * 离职员工薪资导入功能
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPaForLeftMen")
	public ModelAndView importPaForLeftMen(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("APPLY_NO", "PA_LEFTMEN_SEQ.NEXTVAL");
		aliasValueMap.put("CPNY_ID", admin.getCpnyId());
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");
		aliasValueMap.put("PA_MONTH_FOR", "#CELL2#");
		aliasValueMap.put("ITEM_TYPE",  "#CELL3#");
//		aliasValueMap.put("ITEM_NO", "#CELL4#");
//		aliasValueMap.put("ITEM_DATA", "#CELL5#");
		aliasValueMap.put("PA_MONTH", "#CELL4#");
		aliasValueMap.put("REMARK", "#CELL5#");
		aliasValueMap.put("CREATED_BY", admin.getAdminID());
		aliasValueMap.put("CREATE_DATE", "SYSDATE");
		aliasValueMap.put("ACTIVITY", "1");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("APPLY_NO", this.NUMBER);
		aliasTypeMap.put("CPNY_ID",  this.VARCHAR);
		aliasTypeMap.put("EMPID",  this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME",  this.VARCHAR);
		aliasTypeMap.put("PA_MONTH_FOR", this.VARCHAR);
		aliasTypeMap.put("ITEM_TYPE",  this.VARCHAR);
//		aliasTypeMap.put("ITEM_NO",  this.VARCHAR);
//		aliasTypeMap.put("ITEM_DATA",  this.VARCHAR);
		aliasTypeMap.put("PA_MONTH",  this.VARCHAR);
		aliasTypeMap.put("REMARK",  this.VARCHAR);
		aliasTypeMap.put("CREATED_BY", this.VARCHAR);
		aliasTypeMap.put("CREATE_DATE",  this.SYSDATE);
		aliasTypeMap.put("ACTIVITY",  this.NUMBER);
		
		/*LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		//String sqlI18nContent="	SELECT DECODE(#CELL7#,NULL,'无','','无',#CELL7#) REMARK FROM DUAL ";
		//aliasValueI18nMap.put("REMARK", sqlI18nContent);
		LinkedHashMap map = this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_LEFTMEN_ADDING_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		this.excelUtilSer.importPaForLeftMenInfoData(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);*/
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String aliasNullStr = "";
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_LEFTMEN_ADDING_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("aliasNullStr", aliasNullStr);
		//导入之后跳转到指定页面进行编辑
		String forwardUrl = "/pa/salary/viewAddPaInfoForEmpLeftList?pageNum=1&numPerPage=10";
		String navTabId = "pa0708";
		this.excelUtilSer.importPaForLeftData(request,response,map,modelMap,forwardUrl,navTabId);
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 最低工资标准（非促销员）导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo")
	public ModelAndView importZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PQD_NO", "pa_low_salary_unpromo_seq.nextval");
		aliasValueMap.put("PQD_FAREN", "#CELL0#");
		aliasValueMap.put("PQD_NIANDU", "#CELL1#");
		aliasValueMap.put("PQD_FULIDIQU", "#CELL2#");
		aliasValueMap.put("PQD_ZUIDIGONGZI",  "#CELL3#");
		aliasValueMap.put("PQD_SHEPINGGONGZI", "#CELL4#");
		aliasValueMap.put("PQD_ZUIXIAOJISHU", "#CELL5#");
		aliasValueMap.put("PQD_ZUIDAJISHU", "#CELL6#");
		aliasValueMap.put("PQD_QUFEN", "#CELL7#");
		aliasValueMap.put("CREATED_BY", admin.getAdminID());
		aliasValueMap.put("CREATE_DATE", "SYSDATE");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PQD_NO", this.NUMBER);
		aliasTypeMap.put("PQD_FAREN",  this.VARCHAR);
		aliasTypeMap.put("PQD_NIANDU",  this.VARCHAR);
		aliasTypeMap.put("PQD_FULIDIQU",  this.VARCHAR);
		aliasTypeMap.put("PQD_ZUIDIGONGZI", this.VARCHAR);
		aliasTypeMap.put("PQD_SHEPINGGONGZI",  this.VARCHAR);
		aliasTypeMap.put("PQD_ZUIXIAOJISHU",  this.VARCHAR);
		aliasTypeMap.put("PQD_ZUIDAJISHU",  this.VARCHAR);
		aliasTypeMap.put("PQD_QUFEN",  this.VARCHAR);
		aliasTypeMap.put("CREATED_BY", this.VARCHAR);
		aliasTypeMap.put("CREATE_DATE",  this.SYSDATE);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map = this.excelUtilSer.putIntoAliasValueAndTypeMap("pa_low_salary_unpromo_temp",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		this.excelUtilSer.importZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	@RequestMapping(value = "/importZuiDiGongZiBiaoZhunCuXiaoYuanInfo")
	public ModelAndView importZuiDiGongZiBiaoZhunCuXiaoYuanInfo(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PQD_NO", "pa_low_salary_promo_seq.nextval");
		aliasValueMap.put("PQD_NIANDU", "#CELL0#");
		aliasValueMap.put("PQD_CITY_CD", "#CELL1#");
		aliasValueMap.put("PQD_CHENGSHIMINGCHENG",  "#CELL2#");
		aliasValueMap.put("PQD_ZUIDIGONGZI", "#CELL3#");
		aliasValueMap.put("PQD_QUFEN", "#CELL4#");
		aliasValueMap.put("CREATED_BY", admin.getAdminID());
		aliasValueMap.put("CREATE_DATE", "SYSDATE");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PQD_NO", this.NUMBER);
		aliasTypeMap.put("PQD_NIANDU",  this.VARCHAR);
		aliasTypeMap.put("PQD_CITY_CD",  this.VARCHAR);
		aliasTypeMap.put("PQD_CHENGSHIMINGCHENG", this.VARCHAR);
		aliasTypeMap.put("PQD_ZUIDIGONGZI",  this.VARCHAR);
		aliasTypeMap.put("PQD_QUFEN",  this.VARCHAR);
		aliasTypeMap.put("CREATED_BY", this.VARCHAR);
		aliasTypeMap.put("CREATE_DATE",  this.SYSDATE);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map = this.excelUtilSer.putIntoAliasValueAndTypeMap("pa_low_salary_promo_temp",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		this.excelUtilSer.importZuiDiGongZiBiaoZhunCuXiaoYuanInfo(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 派遣津贴标准--导入功能
	 * viewPaiQianDiJinTieBiaoZhunInfoModule
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaiQianDiJinTieBiaoZhunInfoModule")
	public ModelAndView viewPaiQianDiJinTieBiaoZhunInfoModule(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PQD_NO", "sy_dispatch_allow_std_seq.NEXTVAL");
		aliasValueMap.put("PQD_FAREN", "#CELL0#");
		aliasValueMap.put("PQD_ZHIZE", "#CELL1#");
		aliasValueMap.put("PQD_CHENGSHIDENGJI", "#CELL2#");
		aliasValueMap.put("PQD_DIQUMINGCHENG", "#CELL3#");
		aliasValueMap.put("PQD_SHUZHI", "#CELL4#");
		//aliasValueMap.put("PQD_BEIZHU", "#CELL5#");
		aliasValueMap.put("CREATED_BY", admin.getAdminID());
		aliasValueMap.put("CREATE_DATE", "SYSDATE");
		aliasValueMap.put("ACTIVITY", "1");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PQD_NO",  this.NUMBER);
		aliasTypeMap.put("PQD_FAREN", this.VARCHAR);
		aliasTypeMap.put("PQD_ZHIZE", this.VARCHAR);
		aliasTypeMap.put("PQD_CHENGSHIDENGJI", this.VARCHAR);
		aliasTypeMap.put("PQD_DIQUMINGCHENG", this.VARCHAR);
		aliasTypeMap.put("PQD_SHUZHI", this.VARCHAR);
		//aliasTypeMap.put("PQD_BEIZHU", this.VARCHAR);
		aliasTypeMap.put("CREATED_BY", this.VARCHAR);
		aliasTypeMap.put("CREATE_DATE", this.SYSDATE);
		aliasTypeMap.put("ACTIVITY", this.VARCHAR);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("sy_dispatch_allow_std_temp",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		this.excelUtilSer.importPaiQianDiJinTieBiaoZhunTempInfoData(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importArCardRecordExcel2")
	public ModelAndView importArCardRecordExcel2(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("RECORD_NO", "AR_RECORDS_SEQ.NEXTVAL");
		aliasValueMap.put("INSERT_BY", "H");
		aliasValueMap.put("ACTIVITY", "1");
		aliasValueMap.put("INSERT_TIME", "SYSDATE");
		aliasValueMap.put("CREATE_DATE", "SYSDATE");
		aliasValueMap.put("CREATED_BY", admin.getPersonId());
		aliasValueMap.put("PERSON_ID", "#CELL0#");
		aliasValueMap.put("R_TIME", "#CELL3#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("RECORD_NO", this.NUMBER);
		aliasTypeMap.put("INSERT_BY", this.VARCHAR );
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("INSERT_TIME", this.SYSDATE);
		aliasTypeMap.put("CREATE_DATE", this.SYSDATE);
		aliasTypeMap.put("CREATED_BY", this.VARCHAR);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("R_TIME", this.DATE_HMS);
		aliasTypeMap.put("DOOR_TYPE", this.VARCHAR);
		
		String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"' ";
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("AR_MAC_RECORDS_"+pathCpnyID,aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		this.excelUtilSer.importArCardRecordData2(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	/**
	 * 发送SAP FIRM数据时导入的特殊控制人员信息
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importSapSpecialEmpInfo")
	public ModelAndView importSapSpecialEmpInfo(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PA_MONTH", "#CELL0#");
		aliasValueMap.put("PERSON_ID", "#CELL1#");
		aliasValueMap.put("SEND_TYPE", "#CELL3#");
		aliasValueMap.put("ACTIVITY", "1");
		aliasValueMap.put("CREATE_DATE", "SYSDATE");
		aliasValueMap.put("CREATED_BY", admin.getPersonId()!=null?admin.getPersonId().toString():admin.getUsername());
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PA_MONTH", this.VARCHAR);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR );
		aliasTypeMap.put("SEND_TYPE", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE", this.SYSDATE);
		aliasTypeMap.put("CREATED_BY", this.VARCHAR);
		
		String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL1# AND CPNY_ID='"+this.pathCpnyID+"' ";
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_DATA_TO_SAP_ACTUAL_SPECIAL",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		this.excelUtilSer.importSapSpecialEmpInfo(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}

	
	/**
	 * 跳转到上传excel页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-29 下午04:43:00 
	* @version V1.0
	 */
	@RequestMapping(value = "/importExcelData")
	public ModelAndView importExcelData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		modelMap.put("importFunName", request.getParameter("importFunName"));
		modelMap.put("paramMap", paramMap);
		return new ModelAndView("/inct/salesman/importExcelData",modelMap);		
	}
	

	/**
	 * 跳转到上传excel页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-29 下午04:43:00 
	* @version V1.0
	 */
	@RequestMapping(value = "/importPaExcelData")
	public ModelAndView importPaExcelData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		modelMap.put("importFunName", request.getParameter("importFunName"));
		modelMap.put("paramMap", paramMap);
		return new ModelAndView("/inct/salesman/importPaExcelData",modelMap);		
	}
	
	/**
	 * 跳转到上传excel页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author chenff
	* @date 2014-9-30 
	* @version V1.0
	 */
	@RequestMapping(value = "/importEvaluateInfoData")
	public ModelAndView importEvaluateInfoData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		modelMap.put("id", request.getParameter("id"));
		modelMap.put("importFunName", request.getParameter("importFunName"));
		modelMap.put("DISTINCT_FIELD", request.getParameter("DISTINCT_FIELD"));
		modelMap.put("paramMap", paramMap);
		return new ModelAndView("/hrm/empinfo/importEvaluateInfoData",modelMap);		
	}
	/**
	 * 跳转到上传excel页面
	 * @Copyright:   AIT (c)
	 * @Company:     AIT
	 * @Description: TODO
	 * @author chenff
	 * @date 2014-9-30 
	 * @version V1.0
	 */
	@RequestMapping(value = "/importPaBasicItemExcelData")
	public ModelAndView importPaBasicItemExcelData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		modelMap.put("id", request.getParameter("id"));
		modelMap.put("importFunName", request.getParameter("importFunName"));
		modelMap.put("DISTINCT_FIELD", request.getParameter("DISTINCT_FIELD"));
		modelMap.put("paramMap", paramMap);
		return new ModelAndView("/inct/salesman/importPaBasicItemExcelData",modelMap);		
	}
	/**
	 * 跳转到上传excel页面
	 * @Copyright:   AIT (c)
	 * @Company:     AIT
	 * @Description: TODO
	 * @author chenff
	 * @date 2014-9-30 
	 * @version V1.0
	 */
	@RequestMapping(value = "/importPaMonthBasicItemExcelData")
	public ModelAndView importPaMonthBasicItemExcelData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		modelMap.put("id", request.getParameter("id"));
		modelMap.put("importFunName", request.getParameter("importFunName"));
		modelMap.put("DISTINCT_FIELD", request.getParameter("DISTINCT_FIELD"));
		modelMap.put("paramMap", paramMap);
		return new ModelAndView("/inct/salesman/importPaMonthBasicItemExcelData",modelMap);		
	}
	/**
	 * 跳转到上传excel页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-29 下午04:43:00 
	* @version V1.0
	 */
	@RequestMapping(value = "/importExcelDataValidate")
	public ModelAndView importExcelDataValidate(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		modelMap.put("importFunName", request.getParameter("importFunName"));
		return new ModelAndView("/hrm/transferOrder/importExcelDataValidate",modelMap);		
	}
	
	
	/**
	 * 导入数据 （调用时需要重写的方法）--奖励
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-30 下午01:48:42 
	* @version V1.0
	 */
	@RequestMapping(value = "/importRewardExcel")
	public ModelAndView importRewardExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		String personSql="SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueMap.put("PERSON_ID", personSql);
		aliasValueMap.put("REWARD_DATE", "#CELL2#");
		aliasValueMap.put("TRANS_CODE", "#CELL3#");
		aliasValueMap.put("REWARD_BONUS", "#CELL4#");
		aliasValueMap.put("REWARD_CONTENTS", "#CELL5#");
		
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		String deptSql="SELECT E.DEPTNO FROM HR_EMPLOYEE E WHERE E.PERSON_ID=(SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"' )";
		aliasValueMap.put("DEPTNO", deptSql);
		String positionSql="SELECT E.POSITION_NO FROM HR_EMPLOYEE E WHERE E.PERSON_ID=(SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"' )";
		aliasValueMap.put("POSITION_NO", positionSql);
		String postSql="SELECT E.POST_NO FROM HR_EMPLOYEE E WHERE E.PERSON_ID=(SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"' )";
		aliasValueMap.put("POST_NO", postSql);
		aliasValueMap.put("TRANS_NO", "641");
		aliasValueMap.put("CPNY_ID", this.pathCpnyID);
		
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("REWARD_DATE", this.DATE);
		aliasTypeMap.put("TRANS_CODE", this.VARCHAR);
		aliasTypeMap.put("REWARD_BONUS", this.NUMBER);
		aliasTypeMap.put("REWARD_CONTENTS", this.VARCHAR);
		
		aliasTypeMap.put("ACTIVITY", this.VARCHAR);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("DEPTNO",this.VARCHAR);
		aliasTypeMap.put("POSITION_NO", this.VARCHAR);
		aliasTypeMap.put("POST_NO", this.VARCHAR);
		aliasTypeMap.put("TRANS_NO", this.VARCHAR);
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		
		String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"' "; 
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		String transCode_sqlI18n="SELECT  T.CODE_NO FROM  SY_CODE  T,SY_CODE_PARAM  SP,SY_GLOBAL_NAME S1 " +
				"WHERE T.PARENT_CODE_NO = 641 AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '"+this.pathCpnyID+"' AND T.CODE_NO = S1.NO(+) " +
				"AND S1.LANGUAGE(+)='"+admin.getLanguage()+"' AND T.ACTIVITY = 1 AND S1.CONTENT=#CELL3#";
		aliasValueI18nMap.put("TRANS_CODE", transCode_sqlI18n);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("HR_REWARD_SAVE",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		this.excelUtilSer.importDataForTransferOrder(request,response,map,modelMap);
		
//		modelMap.put("forwardUrl", "/hrm/transferOrder/viewRewardAndPunishment?pageNum=1&menuNo=123431&navTabId=hr0220");
//		modelMap.put("navTabId", "hr0220");

		return new ModelAndView("/hrm/transferOrder/alertMsg",modelMap);		
	}

	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/diaolingImportFunName")
	public ModelAndView diaolingImportFunName(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		//计算当前显示的表头
		String code=request.getParameter("code");
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CODE",  code);
		paramMap.put("CPNYID",admin.getCpnyId());
		paramMap.put("LAN", admin.getLanguage());
		retrunList = transferOrderDao.getTranferOrderTitile(paramMap);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		for(int j=0;j<retrunList.size();j++){
			Map map=(Map)retrunList.get(j);
			//String sql=map.get("DISTINCT_FIELD")+","+map.get("CONTENT")+"-";
			//String val=map.get("CONTENT")+","+map.get("OUTPUT_TYPE")+","+map.get("DISTINCT_FIELD");
			String col="#CELL#"+j;
			aliasValueMap.put(map.get("CONTENT"), col);
			aliasTypeMap.put(map.get("CONTENT"), this.VARCHAR);
			
		}
		aliasValueMap.put("EXP_INSIDE_NO", "hr_exp_insid_seq.nextval");//序列
		aliasValueMap.put("PERSONID", "111111");//员工序号
		aliasValueMap.put("TRANS_CODE", "TRANS_CODE");//员工序号
		aliasValueMap.put("STATUS_CODE", "STATUS_CODE");//员工状态
		aliasValueMap.put("AGENT_POST_GRADE_NO", "AGENT_POST_GRADE_NO");	//AGENT_POST_GRADE_NO	
		aliasValueMap.put("CURRENT_AFFIRM_ID", "CURRENT_AFFIRM_ID");	//CURRENT_AFFIRM_ID
		//paramMap.put("CREATE_DATE", new Date());	 //CREATE_DATE                  ,                                             
		aliasValueMap.put("CREATE_DATE", admin.getPersonId());//CREATED_BY     

		/*aliasValueMap.put("R_TIME", "#CELL3#");
		aliasValueMap.put("PERSON_ID", "#CELL0#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
		aliasValueMap.put("DOOR_TYPE", "#CELL4#");
		aliasValueMap.put("INSERT_BY", "H");
		aliasValueMap.put("REMARK", "#CELL5#");
		aliasValueMap.put("ACTIVITY", "1");
		
		aliasValueMap.put("INSERT_TIME", "SYSDATE");
		aliasValueMap.put("CREATE_DATE", "SYSDATE");
		aliasValueMap.put("CREATED_BY", admin.getPersonId());*/
		
		aliasTypeMap.put("EXP_INSIDE_NO", this.NUMBER);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.DATE_HMS);
		aliasTypeMap.put("END_DATE", this.DATE_HMS);
		aliasTypeMap.put("CREATE_DATE", this.DATE_HMS);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String sqlGetPersonId="SELECT PERSON_ID FROM HR_EMPLOYEE E WHERE E.EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"'";
		aliasValueI18nMap.put("PERSON_ID", sqlGetPersonId);
			
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("hr_experience_inside_save",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			
		this.excelUtilSer.importData(request,response,map,modelMap);	
	
		if(modelMap.get("statusCode")!=null&&modelMap.get("statusCode").toString().equals("200")){
			modelMap.put("navTabId", "ar0104");
		}
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}

	/**
	 * 导入数据 （调用时需要重写的方法）--惩罚
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-30 下午02:40:40 
	* @version V1.0
	 */
	@RequestMapping(value = "/importPunishmentExcel")
	public ModelAndView importPunishmentExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		String personSql="SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueMap.put("PERSON_ID", personSql);
		aliasValueMap.put("DATE_PUNISHED", "#CELL2#");
		aliasValueMap.put("TRANS_CODE", "#CELL3#");
		aliasValueMap.put("PUN_BONUS", "#CELL4#");
		aliasValueMap.put("PUN_REASON", "#CELL5#");
		
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		String deptSql="SELECT E.DEPTNO FROM HR_EMPLOYEE E WHERE E.PERSON_ID=(SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"' )";
		aliasValueMap.put("DEPTNO", deptSql);
		String positionSql="SELECT E.POSITION_NO FROM HR_EMPLOYEE E WHERE E.PERSON_ID=(SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"' )";
		aliasValueMap.put("POSITION_NO", positionSql);
		String postSql="SELECT E.POST_NO FROM HR_EMPLOYEE E WHERE E.PERSON_ID=(SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"' )";
		aliasValueMap.put("POST_NO", postSql);
		aliasValueMap.put("TRANS_NO", "642");
		aliasValueMap.put("PUN_TYPE_ID", "#CELL3#");
		aliasValueMap.put("CPNY_ID", this.pathCpnyID);
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("DATE_PUNISHED", this.DATE);
		aliasTypeMap.put("TRANS_CODE", this.VARCHAR);
		aliasTypeMap.put("PUN_BONUS", this.NUMBER);
		aliasTypeMap.put("PUN_REASON", this.VARCHAR);
		
		aliasTypeMap.put("ACTIVITY", this.VARCHAR);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("DEPTNO",this.VARCHAR);
		aliasTypeMap.put("POSITION_NO", this.VARCHAR);
		aliasTypeMap.put("POST_NO", this.VARCHAR);
		aliasTypeMap.put("TRANS_NO", this.VARCHAR);
		aliasTypeMap.put("PUN_TYPE_ID", this.VARCHAR);
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		
		String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"' "; 
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		String transCode_sqlI18n="SELECT  T.CODE_NO FROM  SY_CODE  T,SY_CODE_PARAM  SP,SY_GLOBAL_NAME S1 " +
				"WHERE T.PARENT_CODE_NO = 642 AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '"+this.pathCpnyID+"' AND T.CODE_NO = S1.NO(+) " +
				"AND S1.LANGUAGE(+)='"+admin.getLanguage()+"' AND T.ACTIVITY = 1 AND S1.CONTENT=#CELL3#";
		aliasValueI18nMap.put("TRANS_CODE", transCode_sqlI18n);
		aliasValueI18nMap.put("PUN_TYPE_ID", transCode_sqlI18n);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("HR_PUNISHMENT_SAVE",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		this.excelUtilSer.importDataForTransferOrder(request,response,map,modelMap);
		return new ModelAndView("/hrm/transferOrder/alertMsg",modelMap);		
	}
	
	//--------lufeng--------------保险输入项目，申请数据------------2013-09-02---------
	/**
	 * 导入数据 （调用时需要重写的方法）工资系统 -保险维护-保险申请-DISTINCT_FIELD eq 'PERSON_ID'  导入
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importInsuranceInputApplyDataExcelIsNotNull")
	public ModelAndView importInsuranceInputApplyDataExcelIsNotNull(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		String paramNo=request.getParameter("id");
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PERSON_ID", "#CELL0#");
		aliasValueMap.put("START_MONTH", "#CELL2#");
		aliasValueMap.put("RETURN_VALUE", "#CELL3#");
		aliasValueMap.put("REMARK", "#CELL4#");
		aliasValueMap.put("URL_STR", "#CELL5#");
		
		aliasValueMap.put("AFFIRM_FLAG", 0);
		aliasValueMap.put("APPLY_STATUS", 0);
		aliasValueMap.put("APPLY_DATE","SYSDATE");
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("PARAM_DATA_NO", "(select nvl(max(param_data_no),0)+1 from is_param_data_apply)");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		aliasTypeMap.put("URL_STR", this.VARCHAR);
		
		aliasTypeMap.put("AFFIRM_FLAG", this.NUMBER);
		aliasTypeMap.put("APPLY_STATUS", this.NUMBER);
		aliasTypeMap.put("APPLY_DATE",this.SYSDATE);
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("PARAM_DATA_NO", this.NUMBER);

		//第四步 可以为空的列 
		String aliasNullStr = ",4,5,";
		
		String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim(#CELL0#,' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("IS_PARAM_DATA_APPLY",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("aliasNullStr", aliasNullStr);
		this.excelUtilSer.importData(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importInsuranceInputApplyDataExcelIsNull")
	public ModelAndView importInsuranceInputApplyDataExcelIsNull(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		String paramNo=request.getParameter("id");
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("FIELD1_VALUE", "#CELL0#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
		aliasValueMap.put("START_MONTH", "#CELL1#");
		//aliasValueMap.put("END_MONTH", "#CELL2#");
		aliasValueMap.put("RETURN_VALUE", "#CELL2#");
		aliasValueMap.put("REMARK", "#CELL3#");
		aliasValueMap.put("URL_STR", "#CELL4#");
		
		aliasValueMap.put("AFFIRM_FLAG", 0);
		aliasValueMap.put("APPLY_STATUS", 0);
		aliasValueMap.put("APPLY_DATE","SYSDATE");
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("PARAM_DATA_NO", "(select nvl(max(param_data_no),0)+1 from IS_PARAM_DATA_OTHER_APPLY)");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("FIELD1_VALUE", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		aliasTypeMap.put("URL_STR", this.VARCHAR);
		
		aliasTypeMap.put("AFFIRM_FLAG", this.NUMBER);
		aliasTypeMap.put("APPLY_STATUS", this.NUMBER);
		aliasTypeMap.put("APPLY_DATE",this.SYSDATE);
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("PARAM_DATA_NO", this.NUMBER);

		//第四步 可以为空的列 
		String aliasNullStr = ",3,4,";
		

		String filed1=request.getParameter("FIELD1");
		if(filed1!=null&&!filed1.equals("")){
			String sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
			  "' AND T."+filed1+" =S.NO(+) AND " +
			  " ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ')  AND ROWNUM=1";
			if(filed1.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}if(filed1.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}
			LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
			aliasValueI18nMap.put("FIELD1_VALUE", sqlI18nContent);
			if(filed1.equalsIgnoreCase("CPNY_ID") || filed1.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD1_VALUE_FLAG", "Y");
			}
			LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("IS_PARAM_DATA_OTHER_APPLY",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			
			map.put("aliasNullStr", aliasNullStr);
			
			this.excelUtilSer.importData(request,response,map,modelMap);
		}
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importInsuranceInputApplyDataExcelIsNull2")
	public ModelAndView importInsuranceInputApplyDataExcelIsNull2(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		String paramNo=request.getParameter("id");
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("FIELD1_VALUE", "#CELL0#");//#CELLj# ---j是指定的excel里头的第几列，从0开始
		aliasValueMap.put("FIELD2_VALUE", "#CELL1#");
		aliasValueMap.put("START_MONTH", "#CELL2#");
		//aliasValueMap.put("END_MONTH", "#CELL3#");
		aliasValueMap.put("RETURN_VALUE", "#CELL3#");
		aliasValueMap.put("REMARK", "#CELL4#");
		aliasValueMap.put("URL_STR", "#CELL5#");
		
		aliasValueMap.put("AFFIRM_FLAG", 0);
		aliasValueMap.put("APPLY_STATUS", 0);
		aliasValueMap.put("APPLY_DATE","SYSDATE");
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("PARAM_DATA_NO", "(select nvl(max(param_data_no),0)+1 from IS_PARAM_DATA_OTHER_APPLY)");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("FIELD1_VALUE", this.VARCHAR);
		aliasTypeMap.put("FIELD2_VALUE", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		//aliasTypeMap.put("END_MONTH", this.VARCHAR);
		
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		aliasTypeMap.put("URL_STR", this.VARCHAR);
		
		aliasTypeMap.put("AFFIRM_FLAG", this.NUMBER);
		aliasTypeMap.put("APPLY_STATUS", this.NUMBER);
		aliasTypeMap.put("APPLY_DATE",this.SYSDATE);
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("PARAM_DATA_NO", this.NUMBER);
		
		//第四步 可以为空的列 
		String aliasNullStr = ",4,5,";
		
		//指定需要国际化的字段，以及国际化的语句
		String filed1=request.getParameter("FIELD1");
		String filed2=request.getParameter("FIELD2");
		if(filed1!=null&&!filed1.equals("")){
			String sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
								  "' AND T."+filed1+" =S.NO(+) AND " +
								  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			if(filed1.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}if(filed1.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent=" SELECT T."+filed1+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed1+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL0#,' '),' ') AND ROWNUM=1";
			}
			String sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,SY_GLOBAL_NAME S where CPNY_ID= '"+pathCpnyID+
								  "' AND T."+filed2+" =S.NO(+) AND " +
								  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1" ;
			if(filed2.equalsIgnoreCase("CPNY_ID")){
				sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,HR_COMPANY HC,SY_GLOBAL_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed2+"=HC.CPNY_ID AND HC.CPNY_NO =S.NO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1";
			}if(filed2.equalsIgnoreCase("DEPTNO")){
				sqlI18nContent2=" SELECT T."+filed2+" from PA_HR_V t,HR_DEPARTMENT_NAME S where T.CPNY_ID= '"+pathCpnyID+
				  "' AND T."+filed2+"=S.DEPTNO(+) AND " +
				  "  ltrim(rtrim(S.CONTENT,' '),' ')=ltrim(rtrim(#CELL1#,' '),' ') AND ROWNUM=1";
			}
			LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
			aliasValueI18nMap.put("FIELD1_VALUE", sqlI18nContent);
			aliasValueI18nMap.put("FIELD2_VALUE", sqlI18nContent2);
			if(filed1.equalsIgnoreCase("CPNY_ID") || filed1.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD1_VALUE_FLAG", "Y");
			}
			if(filed2.equalsIgnoreCase("CPNY_ID") || filed2.equalsIgnoreCase("DEPTNO")){
				aliasValueI18nMap.put("FIELD2_VALUE_FLAG", "Y");
			}
			LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("IS_PARAM_DATA_OTHER_APPLY",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			
			map.put("aliasNullStr", aliasNullStr);
			
			this.excelUtilSer.importData(request,response,map,modelMap);
		}
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}

	/**
	 * 导入数据 （调用时需要重写的方法）--异动
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-30 下午02:40:40 
	* @version V1.0
	 */
	@RequestMapping(value = "/importOrderOperationExcel")
	public ModelAndView importOrderOperationExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		
		LinkedHashMap transCodeAndPIdMap=this.excelUtilSer.getImportExcelTransferOrderType(request,response);
		LinkedHashMap aliasValueMap=null;
		LinkedHashMap aliasTypeMap=null;
		LinkedHashMap aliasValueI18nMap=null;
		LinkedHashMap aliasTempMap=null;
		if(transCodeAndPIdMap.get("token") == null){
			aliasValueMap=new LinkedHashMap();
			aliasTempMap=new LinkedHashMap();
			String insideNoSql="SELECT HR_EXP_INSID_SEQ.NEXTVAL EXP_INSIDE_NO FROM DUAL CONNECT BY LEVEL < 2 ";
			aliasValueMap.put("EXP_INSIDE_NO", insideNoSql);
			String transCodeSql="SELECT  T.CODE_NO TRANS_NO FROM  SY_CODE  T,SY_CODE_PARAM  SP,SY_GLOBAL_NAME S1 " +
			"WHERE T.PARENT_CODE_NO = 123313 AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '"+this.pathCpnyID+"' AND T.CODE_NO = S1.NO(+) " +
			"AND S1.LANGUAGE(+)='"+admin.getLanguage()+"' AND T.ACTIVITY = 1 AND S1.CONTENT='#CELL0#'";
			aliasValueMap.put("TRANS_NO", transCodeSql);
			String personSql="SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID='#CELL1#' AND CPNY_ID='"+this.pathCpnyID+"' "; 
			aliasValueMap.put("PERSON_ID", personSql);
			aliasValueMap.put("CREATED_BY", admin.getPersonId());
			aliasValueMap.put("CPNY_ID", admin.getCpnyId());
			
			String oldPostGradeNoSql="SELECT T.POST_GRADE_NO OLD_POST_GRADE_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueMap.put("OLD_POST_GRADE_NO", oldPostGradeNoSql);
			String curPostGradeNoSql="SELECT T.POST_GRADE_NO CUR_POST_GRADE_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueMap.put("CUR_POST_GRADE_NO", curPostGradeNoSql);
			String oldPositionNoSql="SELECT T.POSITION_NO OLD_POSITION_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueMap.put("OLD_POSITION_NO", oldPositionNoSql);
			String curPositionNoSql="SELECT T.POSITION_NO CUR_POSITION_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueMap.put("CUR_POSITION_NO", curPositionNoSql);
			String oldPostNoSql="SELECT T.POST_NO OLD_POST_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueMap.put("OLD_POST_NO", oldPostNoSql);
			String curPostNoSql="SELECT T.POST_NO CUR_POST_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueMap.put("CUR_POST_NO", curPostNoSql);
			String oldDeptNoSql="SELECT T.DEPTNO OLD_DEPTNO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueMap.put("OLD_DEPTNO", oldDeptNoSql);
			String curDeptNoSql="SELECT T.DEPTNO CUR_DEPTNO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueMap.put("CUR_DEPTNO", curDeptNoSql);
			String oldDutyNoSql="SELECT T.DUTY_NO OLD_DUTY_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueMap.put("OLD_DUTY_NO", oldDutyNoSql);
			String curDutyNoSql="SELECT T.DUTY_NO CUR_DUTY_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueMap.put("CUR_DUTY_NO", curDutyNoSql);
			String curGradeLevelSql="SELECT T.GRADE_LEVEL CUR_GRADE_LEVEL FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueMap.put("CUR_GRADE_LEVEL", curGradeLevelSql);
			
			
			String insertPostGradeNoSql="SELECT T.POST_GRADE_NO POST_GRADE_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasTempMap.put("POST_GRADE_NO", insertPostGradeNoSql);
			String insertPositionNoSql="SELECT T.POSITION_NO POSITION_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasTempMap.put("POSITION_NO", insertPositionNoSql);
			String insertPostNoSql="SELECT T.POST_NO POST_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasTempMap.put("POST_NO", insertPostNoSql);
			String insertDeptNoSql="SELECT T.DEPTNO DEPTNO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasTempMap.put("DEPTNO", insertDeptNoSql);
			String insertDutyNoSql="SELECT T.DUTY_NO DUTY_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasTempMap.put("DUTY_NO", insertDutyNoSql);
			String insertGradeLevelSql="SELECT T.GRADE_LEVEL GRADE_LEVEL FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasTempMap.put("GRADE_LEVEL", insertGradeLevelSql);
			String insertWorkAreaNameSql="SELECT T.WORK_AREA WORK_AREA FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasTempMap.put("WORK_AREA", insertWorkAreaNameSql);
			String insertEmpTypeCodeSql="SELECT T.EMP_TYPE_CODE DETAIL_HR_DIFF FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasTempMap.put("DETAIL_HR_DIFF", insertEmpTypeCodeSql);
//			String distinct_fields="DEPTNO,GRADE_LEVEL,DUTY_NO,POST_GRADE_NO,POST_NO,POSITION_NO,WORK_AREA_NAME,DETAIL_HR_DIFF";
			
			//部门编号
			String deptSql="SELECT T1.DEPTNO FROM HR_DEPARTMENT T1,HR_DEPARTMENT_NAME T2 WHERE T1.DEPTNO = T2.DEPTNO(+) AND T2.LANGUAGE = '"+admin.getLanguage()+"' AND T2.CONTENT = '_CONTENT_' AND T1.CPNY_ID = '"+this.pathCpnyID+"'";
			//职位编号
			String positionSql="SELECT T1.POSITION_NO FROM HR_POSITION T1,SY_GLOBAL_NAME T2 WHERE T1.POSITION_NO=T2.NO(+) AND T2.LANGUAGE='"+admin.getLanguage()+"' AND T2.CONTENT='_CONTENT_' AND T1.CPNY_ID='"+this.pathCpnyID+"'";
			//职级名称编号
			String postSql="SELECT T.POST_NO FROM HR_POST T, SY_GLOBAL_NAME SY WHERE T.POST_NO = SY.NO(+) AND SY.LANGUAGE(+) = '"+admin.getLanguage()+"' AND T.ACTIVITY = 1 AND T.CPNY_ID ='"+this.pathCpnyID+"' AND SY.CONTENT='_CONTENT_'";
			//职等编号
			String gradeLevelSql="SELECT SP.CODE_NO FROM SY_CODE T, SY_CODE_PARAM SP, SY_GLOBAL_NAME S1 " +
								 "WHERE T.PARENT_CODE_NO = 3683 AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '"+this.pathCpnyID+"' AND T.CODE_NO = S1.NO(+) " +
								 "AND S1.LANGUAGE(+) = '"+admin.getLanguage()+"' AND T.ACTIVITY = 1 AND S1.CONTENT = '_CONTENT_'";
			//职责编号
			String dutySql="SELECT T1.DUTY_NO FROM HR_DUTY T1,SY_GLOBAL_NAME T2 WHERE T1.DUTY_NO=T2.NO(+) AND T2.LANGUAGE='"+admin.getLanguage()+"' AND T2.CONTENT='_CONTENT_' AND T1.CPNY_ID='"+this.pathCpnyID+"'";
			//职级编号
			String postGradeSql="SELECT T1.POST_GRADE_NO FROM HR_POST_GRADE T1,SY_GLOBAL_NAME T2 WHERE T1.POST_GRADE_NO=T2.NO(+) AND T2.LANGUAGE='"+admin.getLanguage()+"' AND T2.CONTENT='_CONTENT_' AND T1.CPNY_ID='"+this.pathCpnyID+"'";
			// 工作地编号
			String workAreaSql="SELECT SP.CODE_NO WORK_AREA FROM SY_CODE T,SY_CODE_PARAM SP,SY_GLOBAL_NAME SY " +
								"WHERE T.CODE_NO = SP.CODE_NO AND T.PARENT_CODE_NO = '4604' AND SP.CPNY_ID = '"+this.pathCpnyID+"' " +
								"AND T.CODE_NO = SY.NO(+) AND SY.LANGUAGE(+) = '"+admin.getLanguage()+"' AND SY.CONTENT='_CONTENT_' ORDER BY T.ORDERNO";
			// 休职 类型
			String positionPauseType="SELECT SP.CODE_NO POSITION_PAUSE_TYPE FROM SY_CODE T,SY_CODE_PARAM SP,SY_GLOBAL_NAME SY " +
								"WHERE T.CODE_NO = SP.CODE_NO AND T.PARENT_CODE_NO = '13965' AND SP.CPNY_ID = '"+this.pathCpnyID+"' " +
								"AND T.CODE_NO = SY.NO(+) AND SY.LANGUAGE(+) = '"+admin.getLanguage()+"' AND SY.CONTENT='_CONTENT_' ORDER BY T.ORDERNO";
			// 离职 类型
			String resignType="SELECT SP.CODE_NO RESIGN_TYPE FROM SY_CODE T,SY_CODE_PARAM SP,SY_GLOBAL_NAME SY " +
								"WHERE T.CODE_NO = SP.CODE_NO AND T.PARENT_CODE_NO = '643' AND SP.CPNY_ID = '"+this.pathCpnyID+"' " +
								"AND T.CODE_NO = SY.NO(+) AND SY.LANGUAGE(+) = '"+admin.getLanguage()+"' AND SY.CONTENT='_CONTENT_' ORDER BY T.ORDERNO";
			// 离职原因
			String resignReason="SELECT SP.CODE_NO RESIGN_REASON FROM SY_CODE T,SY_CODE_PARAM SP,SY_GLOBAL_NAME SY " +
								"WHERE T.CODE_NO = SP.CODE_NO AND T.PARENT_CODE_NO = '4265' AND SP.CPNY_ID = '"+this.pathCpnyID+"' " +
								"AND T.CODE_NO = SY.NO(+) AND SY.LANGUAGE(+) = '"+admin.getLanguage()+"' AND SY.CONTENT='_CONTENT_' ORDER BY T.ORDERNO";

			//兼职部门
			String parttimeDeptSql="SELECT T1.DEPTNO PARTTIME_DEPT FROM HR_DEPARTMENT T1,HR_DEPARTMENT_NAME T2 WHERE T1.DEPTNO = T2.DEPTNO(+) AND T2.LANGUAGE = '"+admin.getLanguage()+"' AND T2.CONTENT = '_CONTENT_' AND T1.CPNY_ID = '"+this.pathCpnyID+"'";
			//代职部门
			String parttimeDeptSql1="SELECT T1.DEPTNO REPLACE_DEPT FROM HR_DEPARTMENT T1,HR_DEPARTMENT_NAME T2 WHERE T1.DEPTNO = T2.DEPTNO(+) AND T2.LANGUAGE = '"+admin.getLanguage()+"' AND T2.CONTENT = '_CONTENT_' AND T1.CPNY_ID = '"+this.pathCpnyID+"'";
			//兼职职级编号
			String deptPostGradeSql="SELECT T1.POST_GRADE_NO PARTTIME_POST_GRADE_NO FROM HR_POST_GRADE T1,SY_GLOBAL_NAME T2 WHERE T1.POST_GRADE_NO=T2.NO(+) AND T2.LANGUAGE='"+admin.getLanguage()+"' AND T2.CONTENT='_CONTENT_' AND T1.CPNY_ID='"+this.pathCpnyID+"'";
			//代职职级
			String deptPostGradeSql1="SELECT T1.POST_GRADE_NO REPLACE_POST_GRADE_NO FROM HR_POST_GRADE T1,SY_GLOBAL_NAME T2 WHERE T1.POST_GRADE_NO=T2.NO(+) AND T2.LANGUAGE='"+admin.getLanguage()+"' AND T2.CONTENT='_CONTENT_' AND T1.CPNY_ID='"+this.pathCpnyID+"'";
			//兼职职级名称编号
			String deptPostSql="SELECT T.POST_NO PARTTIME_POST_NO FROM HR_POST T, SY_GLOBAL_NAME SY WHERE T.POST_NO = SY.NO(+) AND SY.LANGUAGE(+) = '"+admin.getLanguage()+"' AND T.ACTIVITY = 1 AND T.CPNY_ID ='"+this.pathCpnyID+"' AND SY.CONTENT='_CONTENT_'";			
			//代职职级名称编号
			String deptPostSql1="SELECT T.POST_NO REPLACE_POST_NO FROM HR_POST T, SY_GLOBAL_NAME SY WHERE T.POST_NO = SY.NO(+) AND SY.LANGUAGE(+) = '"+admin.getLanguage()+"' AND T.ACTIVITY = 1 AND T.CPNY_ID ='"+this.pathCpnyID+"' AND SY.CONTENT='_CONTENT_'";			
			//兼职职责编号
			String deptDutySql="SELECT T1.DUTY_NO PARTTIME_DUTY FROM HR_DUTY T1,SY_GLOBAL_NAME T2 WHERE T1.DUTY_NO=T2.NO(+) AND T2.LANGUAGE='"+admin.getLanguage()+"' AND T2.CONTENT='_CONTENT_' AND T1.CPNY_ID='"+this.pathCpnyID+"'";
			//代职职责编号
			String deptDutySql1="SELECT T1.DUTY_NO REPLACE_DUTY FROM HR_DUTY T1,SY_GLOBAL_NAME T2 WHERE T1.DUTY_NO=T2.NO(+) AND T2.LANGUAGE='"+admin.getLanguage()+"' AND T2.CONTENT='_CONTENT_' AND T1.CPNY_ID='"+this.pathCpnyID+"'";
			//兼职职位编号
			String deptPositionSql="SELECT T1.POSITION_NO PARTTIME_POSITION_NO FROM HR_POSITION T1,SY_GLOBAL_NAME T2 WHERE T1.POSITION_NO=T2.NO(+) AND T2.LANGUAGE='"+admin.getLanguage()+"' AND T2.CONTENT='_CONTENT_' AND T1.CPNY_ID='"+this.pathCpnyID+"'";
			//代职职位编号
			String deptPositionSql1="SELECT T1.POSITION_NO REPLACE_POSITION_NO FROM HR_POSITION T1,SY_GLOBAL_NAME T2 WHERE T1.POSITION_NO=T2.NO(+) AND T2.LANGUAGE='"+admin.getLanguage()+"' AND T2.CONTENT='_CONTENT_' AND T1.CPNY_ID='"+this.pathCpnyID+"'";
			List aliasNameList = new ArrayList();
			aliasNameList.add(0, "EXP_INSIDE_NO");
			aliasNameList.add(1, "TRANS_NO");
			aliasNameList.add(2, "PERSON_ID");
			aliasNameList.add(3, "CREATED_BY");
			aliasNameList.add(4, "CPNY_ID");
			aliasNameList.add(5, "OLD_POST_GRADE_NO");
			aliasNameList.add(6, "OLD_POSITION_NO");
			aliasNameList.add(7, "OLD_POST_NO");
			aliasNameList.add(8, "OLD_DEPTNO");
			aliasNameList.add(9, "OLD_DUTY_NO");
			aliasNameList.add(10, "CUR_POST_GRADE_NO");
			aliasNameList.add(11, "CUR_POSITION_NO");
			aliasNameList.add(12, "CUR_POST_NO");
			aliasNameList.add(13, "CUR_DEPTNO");
			aliasNameList.add(14, "CUR_DUTY_NO");
			aliasNameList.add(15, "CUR_GRADE_LEVEL");
			aliasTypeMap = new LinkedHashMap();

			aliasTypeMap.put("EXP_INSIDE_NO", this.NUMBER);
			aliasTypeMap.put("TRANS_NO", this.VARCHAR);
			aliasTypeMap.put("PERSON_ID", this.VARCHAR);
			aliasTypeMap.put("CREATED_BY", this.VARCHAR);
			aliasTypeMap.put("CPNY_ID", this.VARCHAR);
			aliasTypeMap.put("OLD_POST_GRADE_NO", this.VARCHAR);
			aliasTypeMap.put("OLD_POSITION_NO", this.VARCHAR);
			aliasTypeMap.put("OLD_POST_NO", this.VARCHAR);
			aliasTypeMap.put("OLD_DEPTNO", this.VARCHAR);
			aliasTypeMap.put("OLD_DUTY_NO", this.VARCHAR);
			aliasTypeMap.put("CUR_POST_GRADE_NO", this.VARCHAR);
			aliasTypeMap.put("CUR_POSITION_NO", this.VARCHAR);
			aliasTypeMap.put("CUR_POST_NO", this.VARCHAR);
			aliasTypeMap.put("CUR_DEPTNO", this.VARCHAR);
			aliasTypeMap.put("CUR_DUTY_NO", this.VARCHAR);
			aliasTypeMap.put("CUR_GRADE_LEVEL", this.VARCHAR);
			
			aliasValueI18nMap=new LinkedHashMap();
			String insideNoSqlI18nContent="SELECT HR_EXP_INSID_SEQ.NEXTVAL EXP_INSIDE_NO FROM DUAL CONNECT BY LEVEL < 2 ";
			aliasValueI18nMap.put("EXP_INSIDE_NO", insideNoSqlI18nContent);

			String transCodeSqlI18nContent="SELECT  T.CODE_NO TRANS_NO  FROM  SY_CODE  T,SY_CODE_PARAM  SP,SY_GLOBAL_NAME S1 " +
			"WHERE T.PARENT_CODE_NO = 123313 AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '"+this.pathCpnyID+"' AND T.CODE_NO = S1.NO(+) " +
			"AND S1.LANGUAGE(+)='"+admin.getLanguage()+"' AND T.ACTIVITY = 1 AND S1.CONTENT='#CELL0#'";
			aliasValueI18nMap.put("TRANS_NO", transCodeSqlI18nContent);
			
			String sqlI18nContent="SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=#CELL1# AND CPNY_ID='"+this.pathCpnyID+"' ";
			aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);

			/*String oldPostGradeNoContent="SELECT T.POST_GRADE_NO OLD_POST_GRADE_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueI18nMap.put("OLD_POST_GRADE_NO", oldPostGradeNoContent);
			
			String oldPositionNoContent="SELECT T.POSITION_NO OLD_POSITION_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueI18nMap.put("OLD_POSITION_NO", oldPositionNoContent);
			
			String oldPostNoContent="SELECT T.POST_NO OLD_POST_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueI18nMap.put("OLD_POST_NO", oldPostNoContent);
			
			String oldDeptNoContent="SELECT T.DEPT_NO OLD_DEPTNO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueI18nMap.put("OLD_DEPTNO", oldDeptNoContent);
			
			String oldDutyNoContent="SELECT T.DUTY_NO OLD_DUTY_NO FROM HR_EMPLOYEE T WHERE T.PERSON_ID=(" + personSql + ")";
			aliasValueI18nMap.put("OLD_DUTY_NO", oldDutyNoContent);*/
			
			List titleList=this.excelUtilSer.getImportTransferOrderTypeConfigList(request);
			List insertList=this.excelUtilSer.getTransferOrderTypeByParams(request);
			for(int x = 0; x < insertList.size(); x++){
				Map map=(Map)insertList.get(x);
				aliasValueMap.put(map.get("INSIDE_TRANS"), aliasTempMap.get(map.get("INSIDE_TRANS")));
				aliasNameList.add(x+16,map.get("INSIDE_TRANS"));
				aliasTypeMap.put(map.get("INSIDE_TRANS"), this.VARCHAR);
				//aliasValueI18nMap.put(map.get("INSIDE_TRANS"), aliasTempMap.get(map.get("INSIDE_TRANS")));
			}
			for(int i = 0; i < titleList.size(); i++){
				Map map=(Map)titleList.get(i);
				if(map.containsValue("START_DATE") ||map.containsValue("END_DATE") ||map.containsValue("SAL_CALCULATE_DATE")||map.containsValue("POSITIVE_DATES")){//包括日期类型
					aliasValueMap.put(map.get("INSIDE_TRANS"), "#CELL"+(i+3)+"#");
					aliasTypeMap.put(map.get("INSIDE_TRANS"), this.DATE);
				}else{
					if(!"0".equals(map.get("PARENT_TABLE_NAME"))){//把所有有PARENT_TABLE_NAME的字段的CONTENT替换成对应的单元格(CELL)
						if("DEPTNO".equals(map.get("INSIDE_TRANS"))){//部门
							aliasValueMap.put("DEPTNO", deptSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("DEPTNO", this.VARCHAR);
							aliasValueI18nMap.put("DEPTNO", deptSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("GRADE_LEVEL".equals(map.get("INSIDE_TRANS"))){//职等
							aliasValueMap.put("GRADE_LEVEL", gradeLevelSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("GRADE_LEVEL", this.VARCHAR);
							aliasValueI18nMap.put("GRADE_LEVEL", gradeLevelSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("DUTY_NO".equals(map.get("INSIDE_TRANS"))){//职责
							aliasValueMap.put("DUTY_NO", dutySql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("DUTY_NO", this.VARCHAR);
							aliasValueI18nMap.put("DUTY_NO", dutySql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("POST_GRADE_NO".equals(map.get("INSIDE_TRANS"))){//职级
							aliasValueMap.put("POST_GRADE_NO", postGradeSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("POST_GRADE_NO", this.VARCHAR);
							aliasValueI18nMap.put("POST_GRADE_NO", postGradeSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("POST_NO".equals(map.get("INSIDE_TRANS"))){//职级名称
							aliasValueMap.put("POST_NO", postSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("POST_NO", this.VARCHAR);
							aliasValueI18nMap.put("POST_NO", postSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("POSITION_NO".equals(map.get("INSIDE_TRANS"))){//职位
							aliasValueMap.put("POSITION_NO", positionSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("POSITION_NO", this.VARCHAR);
							aliasValueI18nMap.put("POSITION_NO", positionSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("WORK_AREA".equals(map.get("INSIDE_TRANS"))){//工作地
							aliasValueMap.put("WORK_AREA", workAreaSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("WORK_AREA", this.VARCHAR);
							aliasValueI18nMap.put("WORK_AREA", workAreaSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("POSITION_PAUSE_TYPE".equals(map.get("INSIDE_TRANS"))){//工作地
							aliasValueMap.put("POSITION_PAUSE_TYPE", positionPauseType.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("POSITION_PAUSE_TYPE", this.VARCHAR);
							aliasValueI18nMap.put("POSITION_PAUSE_TYPE", positionPauseType.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("RESIGN_TYPE".equals(map.get("INSIDE_TRANS"))){//工作地
							aliasValueMap.put("RESIGN_TYPE", resignType.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("RESIGN_TYPE", this.VARCHAR);
							aliasValueI18nMap.put("RESIGN_TYPE", resignType.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("RESIGN_REASON".equals(map.get("INSIDE_TRANS"))){//工作地
							aliasValueMap.put("RESIGN_REASON", resignReason.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("RESIGN_REASON", this.VARCHAR);
							aliasValueI18nMap.put("RESIGN_REASON", resignReason.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("PARTTIME_DEPT".equals(map.get("INSIDE_TRANS"))){// 兼职部门
							aliasValueMap.put("PARTTIME_DEPT", parttimeDeptSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("PARTTIME_DEPT", this.VARCHAR);
							aliasValueI18nMap.put("PARTTIME_DEPT", parttimeDeptSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("PARTTIME_POST_GRADE_NO".equals(map.get("INSIDE_TRANS"))){// 兼职职级
							aliasValueMap.put("PARTTIME_POST_GRADE_NO", deptPostGradeSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("PARTTIME_POST_GRADE_NO", this.VARCHAR);
							aliasValueI18nMap.put("PARTTIME_POST_GRADE_NO", deptPostGradeSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("PARTTIME_POST_NO".equals(map.get("INSIDE_TRANS"))){// 兼职职级名称
							aliasValueMap.put("PARTTIME_POST_NO", deptPostSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("PARTTIME_POST_NO", this.VARCHAR);
							aliasValueI18nMap.put("PARTTIME_POST_NO", deptPostSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("PARTTIME_DUTY".equals(map.get("INSIDE_TRANS"))){// 兼职职责
							aliasValueMap.put("PARTTIME_DUTY", deptDutySql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("PARTTIME_DUTY", this.VARCHAR);
							aliasValueI18nMap.put("PARTTIME_DUTY", deptDutySql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("PARTTIME_POSITION_NO".equals(map.get("INSIDE_TRANS"))){// 兼职职位
							aliasValueMap.put("PARTTIME_POSITION_NO", deptPositionSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("PARTTIME_POSITION_NO", this.VARCHAR);
							aliasValueI18nMap.put("PARTTIME_POSITION_NO", deptPositionSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("REPLACE_DEPT".equals(map.get("INSIDE_TRANS"))){// 代职部门
							aliasValueMap.put("REPLACE_DEPT", parttimeDeptSql1.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("REPLACE_DEPT", this.VARCHAR);
							aliasValueI18nMap.put("REPLACE_DEPT", parttimeDeptSql1.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("REPLACE_POST_GRADE_NO".equals(map.get("INSIDE_TRANS"))){// 代职职级
							aliasValueMap.put("REPLACE_POST_GRADE_NO", deptPostGradeSql1.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("REPLACE_POST_GRADE_NO", this.VARCHAR);
							aliasValueI18nMap.put("REPLACE_POST_GRADE_NO", deptPostGradeSql1.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("REPLACE_POST_NO".equals(map.get("INSIDE_TRANS"))){// 代职职级名称
							aliasValueMap.put("REPLACE_POST_NO", deptPostSql1.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("REPLACE_POST_NO", this.VARCHAR);
							aliasValueI18nMap.put("REPLACE_POST_NO", deptPostSql1.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("REPLACE_DUTY".equals(map.get("INSIDE_TRANS"))){// 代职职责
							aliasValueMap.put("REPLACE_DUTY", deptDutySql1.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("REPLACE_DUTY", this.VARCHAR);
							aliasValueI18nMap.put("REPLACE_DUTY", deptDutySql1.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("REPLACE_POSITION_NO".equals(map.get("INSIDE_TRANS"))){// 代职职位
							aliasValueMap.put("REPLACE_POSITION_NO", deptPositionSql1.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("REPLACE_POSITION_NO", this.VARCHAR);
							aliasValueI18nMap.put("REPLACE_POSITION_NO", deptPositionSql1.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}
						
						/*else if ("DEPT_POST_GRADE_NO".equals(map.get("INSIDE_TRANS"))){// 兼职/代职职级
							aliasValueMap.put("DEPT_POST_GRADE_NO", deptPostGradeSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("DEPT_POST_GRADE_NO", this.VARCHAR);
							aliasValueI18nMap.put("DEPT_POST_GRADE_NO", deptPostGradeSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("DEPT_POST_NO".equals(map.get("INSIDE_TRANS"))){// 兼职/代职职级名称
							aliasValueMap.put("DEPT_POST_NO", deptPostSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("DEPT_POST_NO", this.VARCHAR);
							aliasValueI18nMap.put("DEPT_POST_NO", deptPostSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}else if ("DEPT_POSITION_NO".equals(map.get("INSIDE_TRANS"))){// 兼职/代职职位
							aliasValueMap.put("DEPT_POSITION_NO", deptPositionSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
							aliasTypeMap.put("DEPT_POSITION_NO", this.VARCHAR);
							aliasValueI18nMap.put("DEPT_POSITION_NO", deptPositionSql.replace("_CONTENT_", "#CELL"+(i+3)+"#"));
						}*/
					}else{//没有PARENT_TABLE_NAME字段
						aliasValueMap.put(map.get("INSIDE_TRANS"), "#CELL"+(i+3)+"#");
						aliasTypeMap.put(map.get("INSIDE_TRANS").toString(), this.VARCHAR);
					}
				}
			}
			LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("HR_EXPERIENCE_INSIDE_SAVE",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
			this.excelUtilSer.importDataForOrderOperation(request,response,map,modelMap,new Integer(transCodeAndPIdMap.get("transCode").toString()));
		}else{
			if ("7".equals(transCodeAndPIdMap.get("transCode"))) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"zxc.ar.alert.message.excelimport.importTypeMismatch", request));// 导入调令类型与页面调令类型不匹配!
			}
		}

		return new ModelAndView("/hrm/transferOrder/alertMsg",modelMap);		
	}
	
	
	/**
	 * 导入数据 （调用时需要重写的方法） IS 保险 项目批量导入
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	//@RequestMapping(value = "/importItemBatchImportExcelIsNotNull")
	public ModelAndView importItemBatchImportExcelIsNotNull(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		//String paramNo=request.getParameter("id");
		String  paramNo="SELECT T.PARAM_NO FROM IS_PARAM_ITEM_PARAM T, SY_GLOBAL_NAME SY3 WHERE T.PARAM_ITEM_NO = SY3.NO(+)"+
						"AND SY3.LANGUAGE(+) = '"+admin.getLanguage()+"'"+
						"AND T.Distinct_Field = 'PERSON_ID'"+
						"AND T.CPNY_ID = '"+this.pathCpnyID+"'"+
						"AND T.ACTIVITY = 1"+
						"AND SY3.CONTENT = '#CELL0#'";
				//aliasValueMap.put("TRANS_NO", transCodeSql);
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PERSON_ID", "#CELL1#");//工号
		aliasValueMap.put("START_MONTH", "#CELL3#");//开始月
		aliasValueMap.put("END_MONTH", "#CELL4#");//结束月
		aliasValueMap.put("RETURN_VALUE", "#CELL5#");//数值
		aliasValueMap.put("REMARK", "#CELL6#");//备注
		
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("BATCH_IMPORT_MARK", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CREATED_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("PARAM_DATA_NO", "IS_PARAM_DATA_SEQ.NEXTVAL");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("BATCH_IMPORT_MARK", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("PARAM_DATA_NO", this.NUMBER);
		
		//第四步 可以为空的列 
		String aliasNullStr = ",6,";
		String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim(#CELL1#,' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("IS_PARAM_DATA",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("Batch","Y");
		map.put("aliasNullStr", aliasNullStr);
		this.excelUtilSer.importData(request,response,map,modelMap);
		modelMap.put("forwardUrl", "/pa/insurance/viewItemBatchImport?pageNum=1&menuNo=123453&navTabId=pa0414");
		modelMap.put("navTabId", "pa0414");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法） PA 工资 输入项目
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	//@RequestMapping(value = "/importItemBatchImportExcelIsNotNullPa")
	public ModelAndView importItemBatchImportExcelIsNotNullPa(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		//String paramNo=request.getParameter("id");
		String  paramNo="SELECT T.PARAM_NO FROM PA_PARAM_ITEM_PARAM T, SY_GLOBAL_NAME SY3 WHERE T.PARAM_ITEM_NO = SY3.NO(+)"+
						"AND SY3.LANGUAGE(+) = '"+admin.getLanguage()+"'"+
						"AND T.Distinct_Field = 'PERSON_ID'"+
						"AND T.CPNY_ID = '"+this.pathCpnyID+"'"+
						"AND T.ACTIVITY = 1"+
						"AND SY3.CONTENT = '#CELL0#'";
				//aliasValueMap.put("TRANS_NO", transCodeSql);
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL1#");//工号
		aliasValueMap.put("START_MONTH", "#CELL3#");//开始月
		aliasValueMap.put("END_MONTH", "#CELL4#");//结束月
		aliasValueMap.put("RETURN_VALUE", "#CELL5#");//数值
		aliasValueMap.put("REMARK", "#CELL6#");//备注
		
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("BATCH_IMPORT_MARK", 1);
		aliasValueMap.put("UPLOAD_DATE","SYSDATE");
		aliasValueMap.put("UPLOAD_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("LINE_ID", "IS_PARAM_DATA_SEQ.NEXTVAL");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("START_MONTH", this.VARCHAR);
		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("BATCH_IMPORT_MARK", this.NUMBER);
		aliasTypeMap.put("UPLOAD_DATE",this.SYSDATE);
		aliasTypeMap.put("UPLOAD_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("LINE_ID", this.NUMBER);
		
		 //第四步 可以为空的列 
		String aliasNullStr = ",6,";
		String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim(#CELL1#,' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		insuranceInputItemSer.deleteErrorOldItemBatchData(request,"508");
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_PARAM_DATA_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("Batch","Y");
		map.put("aliasNullStr", aliasNullStr);
		this.excelUtilSer.importData(request,response,map,modelMap);
//		modelMap.put("forwardUrl", "/pa/salary/viewItemBatchImport?pageNum=1&menuNo=123455&navTabId=pa0218&seach_ITEM_DISTINGUISH=508");
//		modelMap.put("navTabId", "pa0218");    
//		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);	
		modelMap.put("forwardUrl", "/pa/salary/viewItemBatchImportTempList?ITEM_DISTINGUISH=508&pageNum=1");
		modelMap.put("navTabId", "pa0218");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);	

	}
	
	/**
	 * 导入数据 （调用时需要重写的方法） PA 工资 基础项目
	 * Description: 20150212孙鹏修改，将数据先导入临时表
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importItemBatchImportExcelIsNotNullPaBasis")
	public ModelAndView importItemBatchImportExcelIsNotNullPaBasis(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		//String paramNo=request.getParameter("id");
		String  paramNo="SELECT T.PARAM_NO FROM PA_BASIC_ITEM_PARAM T, SY_GLOBAL_NAME SY3 WHERE T.ITEM_NO = SY3.NO(+)"+
				"AND SY3.LANGUAGE(+) = '"+admin.getLanguage()+"'"+
				"AND T.Distinct_Field = 'PERSON_ID'"+
				"AND T.CPNY_ID = '"+this.pathCpnyID+"'"+
				"AND SY3.CONTENT = '#CELL0#'";
		//aliasValueMap.put("TRANS_NO", transCodeSql);
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL1#");//工号
		aliasValueMap.put("START_DATE", "#CELL3#");//开始日期
//		aliasValueMap.put("END_MONTH", "#CELL4#");//结束日期
		aliasValueMap.put("RETURN_VALUE", "#CELL5#");//数值
		aliasValueMap.put("REMARK", "#CELL6#");//备注
		
		aliasValueMap.put("CPNY_ID", pathCpnyID);
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("BATCH_IMPORT_MARK", 1);
		aliasValueMap.put("UPLOAD_DATE","SYSDATE");
		aliasValueMap.put("UPLOAD_BY",admin.getPersonId());
		aliasValueMap.put("PARAM_NO", paramNo);
		aliasValueMap.put("LINE_ID", "PA_BASE_DATA_SEQ.NEXTVAL");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.VARCHAR);
//		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("BATCH_IMPORT_MARK", this.NUMBER);
		aliasTypeMap.put("UPLOAD_DATE",this.SYSDATE);
		aliasTypeMap.put("UPLOAD_BY",this.VARCHAR);
		aliasTypeMap.put("PARAM_NO",this.NUMBER);
		aliasTypeMap.put("LINE_ID", this.NUMBER);
		
		 //第四步 可以为空的列 
		String aliasNullStr = ",6,";
		String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim(#CELL1#,' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		//删除旧数据包括错误数据
		insuranceInputItemSer.deleteErrorOldItemBatchData(request,"507");
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_BASIC_DATA_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("Batch","Y");
		map.put("aliasNullStr", aliasNullStr);
		
		this.excelUtilSer.importData(request,response,map,modelMap);
		//this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
//		modelMap.put("forwardUrl", "/pa/salary/viewItemBatchImport?pageNum=1&menuNo=123455&navTabId=pa0218&seach_ITEM_DISTINGUISH=507");
//		modelMap.put("navTabId", "pa0218");

		modelMap.put("forwardUrl", "/pa/salary/viewItemBatchImportTempList?ITEM_DISTINGUISH=507&pageNum=1");
		modelMap.put("navTabId", "pa0218");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	/**
	 * 导入数据 PaEcc T_PA_ECC_RESULT 经济补偿金
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importPaEccInfo")
	public ModelAndView importPaEccInfo(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		String PERSON_ID = "SELECT PERSON_ID FROM HR_EMPLOYEE " +
							"WHERE EMPID=ltrim(rtrim(#CELL1#,' '),' ') AND CPNY_ID='"+admin.getCpnyId()+"'";
		String batches = "SELECT NVL(MAX(BATCHES), 0) + 1 FROM PA_ECC_PROGRESS WHERE PA_MONTH=ltrim(rtrim(#CELL0#,' '),' ')";
		aliasValueMap.put("PA_MONTH", "#CELL0#");
		aliasValueMap.put("EMPID", "#CELL1#");
//		aliasValueMap.put("LEFT_TYPE", "#CELL2#");
		aliasValueMap.put("ECC_END_DATE", "#CELL2#");
		aliasValueMap.put("ECC_AMT", "#CELL3#");
		aliasValueMap.put("NOTICE_AMT", "#CELL4#");
		aliasValueMap.put("REMARK", "#CELL5#");
		aliasValueMap.put("BATCHES", batches);
		aliasValueMap.put("CPNY_ID", admin.getCpnyId());
		aliasValueMap.put("PERSON_ID", PERSON_ID);
		aliasValueMap.put("PA_ECC_NO", "PA_ECC_NO_SEQ.NEXTVAL");
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PA_MONTH", this.VARCHAR);
		aliasTypeMap.put("EMPID", this.VARCHAR);
//		aliasTypeMap.put("LEFT_TYPE", this.VARCHAR);
		aliasTypeMap.put("ECC_END_DATE", this.DATE);
		aliasTypeMap.put("ECC_AMT", this.VARCHAR);
		aliasTypeMap.put("NOTICE_AMT", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		aliasTypeMap.put("BATCHES", this.NUMBER);
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("PA_ECC_NO",this.NUMBER);
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
//		String sqlI18nContent="SELECT PERSON_ID FROM HR_EMPLOYEE " +
//								"WHERE EMPID=#CELL1# AND CPNY_ID='"+admin.getCpnyId()+"' ";
//		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("T_PA_ECC_RESULT",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("Batch","Y");
		this.excelUtilSer.importData(request,response,map,modelMap);
		modelMap.put("forwardUrl", "/paEcc/benchmark/viewEccBenchEmp?pageNum=1&menuNo=124920&navTabId=jx0001");
		modelMap.put("navTabId", "jx0001");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  HR_LABOR_UNION 工会系统
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importExcelBaseForHrLaborUnion")
	public ModelAndView importExcelBaseForHrLaborUnion(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();

		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("NAME", "#CELL1#");
		aliasValueMap.put("START_DATE", "#CELL2#");
		aliasValueMap.put("END_DATE", "#CELL3#");


		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("NAME", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.DATE);
		aliasTypeMap.put("END_DATE", this.DATE);

		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String aliasNullStr = ",2,3,";
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("HR_LABOR_UNION",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("Batch","Y");
		this.excelUtilSer.importData(request,response,map,modelMap);
		modelMap.put("forwardUrl", "/paEcc/benchmark/viewEccBenchEmp?pageNum=1&menuNo=123312&navTabId=jx0001");
		modelMap.put("navTabId", "jx0001");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  参保对象
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importViewInsuranceObject")
	public ModelAndView importViewInsuranceObject(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		int i = 5;
		aliasValueMap.put("PA_MONTH", "#CELL0#");
		aliasValueMap.put("PERSON_ID", "#CELL1#");
		aliasValueMap.put("LOCAL_NAME", "#CELL2#");
		aliasValueMap.put("DEPT_NAME", "#CELL3#");
		aliasValueMap.put("CALC_FLAG", "#CELL4#");
		if(admin.getCpnyId().equals("LGEHN") || admin.getCpnyId().equals("LGEHZ") || admin.getCpnyId().equals("LGEYT") ){
		aliasValueMap.put("CALC_GJJ_FLAG", "#CELL5#");
		i=i+1;
		}
		aliasValueMap.put("REMARK", "#CELL"+i+"#");
		
		aliasValueMap.put("CPNY_ID", admin.getCpnyId());
		aliasValueMap.put("CREATED_BY", admin.getPersonId());
		aliasValueMap.put("CREATE_DATE", "SYSDATE");
		aliasValueMap.put("ACTIVITY", "1");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PA_MONTH", this.VARCHAR);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("DEPT_NAME", this.VARCHAR);
		aliasTypeMap.put("CALC_FLAG", this.VARCHAR);
		if(admin.getCpnyId().equals("LGEHN") || admin.getCpnyId().equals("LGEHZ") || admin.getCpnyId().equals("LGEYT")){
		aliasTypeMap.put("CALC_GJJ_FLAG", this.VARCHAR);}
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("CREATED_BY", this.VARCHAR);
		aliasTypeMap.put("CREATE_DATE", this.SYSDATE);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String aliasNullStr = ",3,";
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("IS_CALC_OBJECT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("aliasNullStr", aliasNullStr);
		
		String forwardUrl = "/pa/insurance/viewInsCalcObjectImportList";
		String navTabId = "pa0413";
		this.excelUtilSer.importInsCalcObjectData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  公积金计算对象
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importViewFundCalcObject")
	public ModelAndView importViewFundCalcObject(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PA_MONTH", "#CELL0#");
		aliasValueMap.put("PERSON_ID", "#CELL1#");
		aliasValueMap.put("LOCAL_NAME", "#CELL2#");
		aliasValueMap.put("DEPT_NAME", "#CELL3#");
		aliasValueMap.put("CALC_FLAG", "#CELL4#");
		aliasValueMap.put("REMARK", "#CELL5#");
		
		aliasValueMap.put("CPNY_ID", admin.getCpnyId());
		aliasValueMap.put("CREATED_BY", admin.getPersonId());
		aliasValueMap.put("CREATE_DATE", "SYSDATE");
		aliasValueMap.put("ACTIVITY", "1");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PA_MONTH", this.VARCHAR);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("DEPT_NAME", this.VARCHAR);
		aliasTypeMap.put("CALC_FLAG", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("CREATED_BY", this.VARCHAR);
		aliasTypeMap.put("CREATE_DATE", this.SYSDATE);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String aliasNullStr = ",3,";
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("IS_CALC_OBJECT_FUND_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("aliasNullStr", aliasNullStr);
		
		String forwardUrl = "/pa/insurance/viewFundCalcObjectImportList";
		String navTabId = "pa0421";
		this.excelUtilSer.importFundCalcObjectData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  （基数管理）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importExclInsatanceBaseNum")
	public ModelAndView importExclInsatanceBaseNum(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();

		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("CHINESENAME", "#CELL1#");
		aliasValueMap.put("AVG_SALARY", "#CELL2#");


		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("chinesename", this.VARCHAR);
		aliasTypeMap.put("AVG_SALARY", this.NUMBER);

		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String aliasNullStr = ",2,3,";
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_BEN_BASE",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("Batch","Y");
		this.excelUtilSer.importData(request,response,map,modelMap);
		modelMap.put("forwardUrl", "/paEcc/benchmark/viewEccBenchEmp?pageNum=1&menuNo=123312&navTabId=jx0001");
		modelMap.put("navTabId", "jx0001");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}

	/**
	 * 导入数据  （基数管理）(公积金)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importExclInsatanceBaseAccumulationFundNum")
	public ModelAndView importExclInsatanceBaseAccumulationFundNum(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap){
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		//第一步 清空 历史表：PA_BENHS_BASE_IMP
		accumulationFundSer.deletePaBenImp();
		
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PERSON_ID", "#CELL0#");//工号
		aliasValueMap.put("CHINESENAME", "#CELL1#");//姓名
		aliasValueMap.put("SOCIAL_NO", "#CELL2#");//开始月
		aliasValueMap.put("AVG_SALARY", "#CELL3#");//数值
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("CHINESENAME", this.VARCHAR);
		aliasTypeMap.put("SOCIAL_NO", this.VARCHAR);
		aliasTypeMap.put("AVG_SALARY", this.NUMBER);
		
		//第四步 可以为空的列 
		LinkedHashMap aliasValueI18nMap = null;
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_BENHS_BASE_IMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("Batch","Y");
		this.excelUtilSer.importData(request,response,map,modelMap);
		
		//第五步：比对临时表数据后插入正式表
		this.accumulationFundSer.getPaBehsBaseImplList(request);
		
		
		modelMap.put("forwardUrl", "/is/accumulationfund/ViewCPFBaseManagementForSearch?pageNum=1&menuNo=124912&navTabId=bx0202");
		modelMap.put("navTabId", "bx0202");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);	
	}

	/**
	 * 导入数据  （参保管理）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importExclInsatanceJoinNum")
	public ModelAndView importExclInsatanceJoinNum(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap){
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();

		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("CHINESENAME", "#CELL1#");
		aliasValueMap.put("AVG_SALARY", "#CELL2#");


		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("chinesename", this.VARCHAR);
		aliasTypeMap.put("AVG_SALARY", this.NUMBER);

		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String aliasNullStr = ",2,3,";
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_BEN_BASE",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("Batch","Y");
		this.excelUtilSer.importData(request,response,map,modelMap);
		modelMap.put("forwardUrl", "/paEcc/benchmark/viewEccBenchEmp?pageNum=1&menuNo=123312&navTabId=jx0001");
		modelMap.put("navTabId", "jx0001");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	/**
	 * 导入数据  （对象增加）(公积金)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importManageAddFundNum")
	public ModelAndView importManageAddFundNum(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap){
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		//第一步 清空 历史表：PA_BENHS_MANAGE_ADD_IMP
		accumulationFundManageSer.deleteManageAddImp();
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PERSON_ID", "#CELL0#");//工号
		aliasValueMap.put("CHINESENAME", "#CELL1#");//姓名
		aliasValueMap.put("START_DATE", "#CELL2#");//开始月
		aliasValueMap.put("ENDOWMENT_BASE", "#CELL3#");//数值
//		aliasValueMap.put("SOCIAL_NO", "#CELL4#");//工号
//		aliasValueMap.put("MEDICARE_BASE", "#CELL5#");//姓名
//		aliasValueMap.put("SHENGYU_BASE", "#CELL6#");//开始月
//		aliasValueMap.put("COMPO_BASE", "#CELL7#");//数值
//		aliasValueMap.put("UNEMP_BASE", "#CELL8#");//数值
//		aliasValueMap.put("JOIN_VALUE", "#CELL9#");//数值
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("CHINESENAME", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.VARCHAR);
		aliasTypeMap.put("ENDOWMENT_BASE", this.NUMBER);
//		aliasValueMap.put("SOCIAL_NO", this.VARCHAR);
//		aliasValueMap.put("MEDICARE_BASE", this.NUMBER);
//		aliasValueMap.put("SHENGYU_BASE", this.NUMBER);
//		aliasValueMap.put("COMPO_BASE", this.NUMBER);
//		aliasValueMap.put("UNEMP_BASE", this.NUMBER);
//		aliasValueMap.put("JOIN_VALUE", this.NUMBER);
		
		//第四步 可以为空的列 
		LinkedHashMap aliasValueI18nMap = null;
//		String aliasNullStr = ",,,,5,6,7,8,9,";
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_BENHS_MANAGE_ADD_IMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("Batch","Y");
		this.excelUtilSer.importData(request,response,map,modelMap);
		
		//第五步：比对临时表数据后插入正式表
		this.accumulationFundManageSer.getManageAddImplList(request);
		
		modelMap.put("forwardUrl", "/is/accumulationfundmanage/ViewCPFJoinInsure?pageNum=1&menuNo=124913&navTabId=bx0203");
		modelMap.put("navTabId", "bx0203");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);	
	}
	/**
	 * 导入数据  （对象减少）(公积金)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importManageDelFundNum")
	public ModelAndView importManageDelFundNum(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap){
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		//第一步 清空 历史表：PA_BENHS_MANAGE_DEL_IMP
		accumulationFundManageSer.deleteManageDelImp();
		
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PERSON_ID", "#CELL0#");//工号
		aliasValueMap.put("CHINESENAME", "#CELL1#");//姓名
		aliasValueMap.put("END_DATE", "#CELL2#");//开始月
		aliasValueMap.put("ENDOWMENT_BASE", "#CELL3#");//数值
//		aliasValueMap.put("SOCIAL_NO", "#CELL4#");//工号
//		aliasValueMap.put("MEDICARE_BASE", "#CELL5#");//姓名
//		aliasValueMap.put("SHENGYU_BASE", "#CELL6#");//开始月
//		aliasValueMap.put("COMPO_BASE", "#CELL7#");//数值
//		aliasValueMap.put("UNEMP_BASE", "#CELL8#");//数值
//		aliasValueMap.put("JOIN_VALUE", "#CELL9#");//数值
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("CHINESENAME", this.VARCHAR);
		aliasTypeMap.put("END_DATE", this.VARCHAR);
		aliasTypeMap.put("ENDOWMENT_BASE", this.NUMBER);
//		aliasValueMap.put("SOCIAL_NO", this.VARCHAR);
//		aliasValueMap.put("MEDICARE_BASE", this.NUMBER);
//		aliasValueMap.put("SHENGYU_BASE", this.NUMBER);
//		aliasValueMap.put("COMPO_BASE", this.NUMBER);
//		aliasValueMap.put("UNEMP_BASE", this.NUMBER);
//		aliasValueMap.put("JOIN_VALUE", this.NUMBER);
		
		//第四步 可以为空的列 
		LinkedHashMap aliasValueI18nMap = null;
//		String aliasNullStr = ",,,,5,6,7,8,9,";
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_BENHS_MANAGE_Del_IMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("Batch","Y");
		this.excelUtilSer.importData(request,response,map,modelMap);
		
		//第五步：比对临时表数据后插入正式表
		this.accumulationFundManageSer.getManageDelImplList(request);
		
		modelMap.put("forwardUrl", "/is/accumulationfundmanage/ViewCPFStopInsure?pageNum=1&menuNo=124914&navTabId=bx0204");
		modelMap.put("navTabId", "bx0204");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);	
	}
	
	
	/**
	 * 导入数据  （对象）(管理公积金)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importObjManageFundNum")
	public ModelAndView importObjManageFundNum(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap){
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		//第一步 清空 历史表：PA_BENHS_MANAGE_IMP
		accumulationFundManageSer.deleteManageImp();
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PERSON_ID", "#CELL0#");//工号
		aliasValueMap.put("CHINESENAME", "#CELL1#");//姓名
		aliasValueMap.put("SOCIAL_NO", "#CELL2#");//开始月
		aliasValueMap.put("ENDOWMENT_BASE", "#CELL3#");//数值
//		aliasValueMap.put("SOCIAL_NO", "#CELL4#");//工号
//		aliasValueMap.put("MEDICARE_BASE", "#CELL5#");//姓名
//		aliasValueMap.put("SHENGYU_BASE", "#CELL6#");//开始月
//		aliasValueMap.put("COMPO_BASE", "#CELL7#");//数值
//		aliasValueMap.put("UNEMP_BASE", "#CELL8#");//数值
//		aliasValueMap.put("JOIN_VALUE", "#CELL9#");//数值
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("CHINESENAME", this.VARCHAR);
		aliasTypeMap.put("SOCIAL_NO", this.VARCHAR);
		aliasTypeMap.put("ENDOWMENT_BASE", this.NUMBER);
//		aliasValueMap.put("SOCIAL_NO", this.VARCHAR);
//		aliasValueMap.put("MEDICARE_BASE", this.NUMBER);
//		aliasValueMap.put("SHENGYU_BASE", this.NUMBER);
//		aliasValueMap.put("COMPO_BASE", this.NUMBER);
//		aliasValueMap.put("UNEMP_BASE", this.NUMBER);
//		aliasValueMap.put("JOIN_VALUE", this.NUMBER);
		
		//第四步 可以为空的列 
		LinkedHashMap aliasValueI18nMap = null;
//		String aliasNullStr = ",,,,5,6,7,8,9,";
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("PA_BENHS_MANAGE_IMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("Batch","Y");
		this.excelUtilSer.importData(request,response,map,modelMap);
		
		//第五步：比对临时表数据后插入正式表
		this.accumulationFundManageSer.getManageImplList(request);
		
		
		modelMap.put("forwardUrl", "/is/accumulationfundmanage/ViewBenshObjectManage?pageNum=1&menuNo=124915&navTabId=bx0205");
		modelMap.put("navTabId", "bx0205");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);	
	}
	/** 
	 * 导入数据 （调用时需要重写的方法） IS 保险项目数据  DISTINCT_FIELD eq 'PERSON_ID'
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importOrderOperationExcel_new")
	public ModelAndView importOrderOperationExcel_new(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		String paramNo=request.getParameter("id");
		/*  
	      hs.duty_no,hs.position_no,hs.work_area_name,*/
		//List list=this.transferOrderSer.getHrExperienceInsideByPersonId(request);
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap(); 
		aliasValueMap.put("TRANS_NO", "123314");
		aliasValueMap.put("START_DATE", "#CELL0#");
		aliasValueMap.put("END_DATE", "#CELL1#");
		aliasValueMap.put("PERSON_ID", "#CELL2#");
		String sendaddressSqlNo="SELECT CODE_NO SENDADDRESS_NO FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=125231 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL3#"+"'" ;
		//select CODE_NO from sy_code sc,sy_global_name sy where sc.parent_code_no='125231'
		//and sc.code_no=sy.no and sy.language='zh' and sy.content like '%北京%'
		aliasValueMap.put("SENDADDRESS_NO", sendaddressSqlNo);
		aliasValueMap.put("REMARK", "#CELL4#");
		aliasValueMap.put("CPNY_ID", admin.getCpnyId());
		aliasValueMap.put("ACTIVITY", "1");
		aliasValueMap.put("CREATE_DATE", "sysdate");
		aliasValueMap.put("CREATED_BY", admin.getPersonId());
		aliasValueMap.put("EXP_INSIDE_NO", "HR_EXP_INSID_SEQ.NEXTVAL");//序列
		String deptNoSql="SELECT DEPTNO FROM PA_HR_V HR WHERE HR.CPNY_ID='"+admin.getCpnyId()+"' AND HR.EMPID='"+"#CELL2#"+"'";
		aliasValueMap.put("DEPTNO", deptNoSql);
		String positionNoSql="SELECT DEPTNO FROM PA_HR_V HR WHERE HR.CPNY_ID='"+admin.getCpnyId()+"' AND HR.EMPID='"+"#CELL2#"+"'";
		aliasValueMap.put("POSITION_NO", positionNoSql);
		String dutyNoSql="SELECT DEPTNO FROM PA_HR_V HR WHERE HR.CPNY_ID='"+admin.getCpnyId()+"' AND HR.EMPID='"+"#CELL2#"+"'";
		aliasValueMap.put("DUTY_NO", dutyNoSql);
		//String positionNoSql1="SELECT DEPTNO FROM PA_HR_V HR WHERE HR.CPNY_ID='"+admin.getCpnyId()+"' AND HR.EMPID='"+"#CELL2#"+"'";
		//aliasValueMap.put("POSITION_NO", positionNoSql1);
		String workareaNoSql1="SELECT WORK_AREA FROM PA_HR_V HR WHERE HR.CPNY_ID='"+admin.getCpnyId()+"' AND HR.EMPID='"+"#CELL2#"+"'";
		aliasValueMap.put("WORK_AREA", workareaNoSql1);
		 //hs.duty_no,hs.position_no,hs.work_area_name,*/
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("TRANS_NO", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.DATE);
		aliasTypeMap.put("END_DATE", this.DATE);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("SENDADDRESS_NO", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		aliasTypeMap.put("EXP_INSIDE_NO", this.NUMBER);
		aliasTypeMap.put("DEPTNO", this.VARCHAR);
		aliasTypeMap.put("POSITION_NO", this.VARCHAR);
		aliasTypeMap.put("DUTY_NO", this.VARCHAR);
		aliasTypeMap.put("WORK_AREA", this.VARCHAR);
		//传入需要验证列的值 按照excel顺序传入
		List validateCell = new ArrayList();
		validateCell.add(this.DATE);
		validateCell.add(this.DATE);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		//传入需要验证列的名称 按照excel顺序传入
		List validateCellName = new ArrayList();
		//名称+type+type1+tableName+parentid(没有则为0)   type: 0-不需要验证 1需要    type1: 0-特鼠表(hr_employee)  1-查 sy_code
		validateCellName.add("发令日期,0");
		validateCellName.add("生效日期,0");
		validateCellName.add("社号,1,0,pa_hr_v");
		validateCellName.add("派遣地,1,1,125231");
		validateCellName.add("备注,-1");
		//保存社号存在位置
		int num=2;
		//第四步 可以为空的列 
		String aliasNullStr = ",5,";
		String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim(#CELL2#,' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("HR_EXPERIENCE_INSIDE_SAVE",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("aliasNullStr", aliasNullStr);
		this.excelUtilSer.importData2(request,response,map,modelMap,validateCell,validateCellName,num);
		//modelMap.put("rel", "viewInsuranceInputItemData");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	/**
	 * 导入数据  （营业员评价数据导入）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importExcelSalesEvalData")
	public ModelAndView importExcelSalesEvalData(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PAY_AREA_CD", "#CELL0#");
		aliasValueMap.put("YYYY", "#CELL1#");
		aliasValueMap.put("QUARTER", "#CELL2#");
		aliasValueMap.put("EVAL_TYPE", "#CELL3#");
		aliasValueMap.put("EMPNO", "#CELL4#");
		aliasValueMap.put("CURRENT_VALUE", "#CELL5#");
		aliasValueMap.put("LAST_VALUE", "#CELL6#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PAY_AREA_CD", this.VARCHAR);
		aliasTypeMap.put("YYYY", this.VARCHAR);
		aliasTypeMap.put("QUARTER", this.VARCHAR);
		aliasTypeMap.put("EVAL_TYPE", this.VARCHAR);
		aliasTypeMap.put("EMPNO", this.VARCHAR);
		aliasTypeMap.put("CURRENT_VALUE", this.NUMBER);
		aliasTypeMap.put("LAST_VALUE", this.NUMBER);
		
		
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",SUBSD_CD,UPDT_USER,UPDT_DTIME";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getEmpID()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("INC_SALS_EVAL_UPLOAD_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		this.excelUtilSer.importExcelSalesEvalData(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 协议管理导入数据
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importTrainAgreement")
	public ModelAndView importTrainAgreement(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("AGREE_NAME", "#CELL0#");
		aliasValueMap.put("EMPID", "#CELL1#");
		aliasValueMap.put("LOCAL_NAME", "#CELL2#");
		aliasValueMap.put("STUDY_START_DATE", "#CELL4#");
		aliasValueMap.put("STUDY_END_DATE", "#CELL5#");
		aliasValueMap.put("STUDY_DAY", "#CELL6#");
		aliasValueMap.put("SERVICE_YEAR", "#CELL7#");
		aliasValueMap.put("CON_START_DATE", "#CELL8#");
		aliasValueMap.put("CON_END_DATE", "#CELL9#");
		aliasValueMap.put("SER_START_DATE", "#CELL10#");
		aliasValueMap.put("SER_END_DATE", "#CELL11#");
		aliasValueMap.put("EXCHANGE_RATE", "#CELL12#");
		aliasValueMap.put("HQ_FREE", "#CELL13#");
		aliasValueMap.put("CGFY_FREE", "#CELL14#");
		aliasValueMap.put("JP_FREE", "#CELL15#");
		aliasValueMap.put("ZFBZ_FREE", "#CELL16#");
		aliasValueMap.put("CGBZ_FREE", "#CELL17#");
		aliasValueMap.put("CGBZ_FREE_FACT", "#CELL18#");
		aliasValueMap.put("SYBX_FREE", "#CELL19#");
		aliasValueMap.put("YX_PAY", "#CELL20#");
		aliasValueMap.put("JT_FREE", "#CELL21#");
		aliasValueMap.put("TX_FREE", "#CELL22#");
		aliasValueMap.put("TOTAL_FEE", "#CELL23#");
		aliasValueMap.put("AGREE_START_DATE", "#CELL24#");
		aliasValueMap.put("AGREE_END_DATE", "#CELL25#");
		aliasValueMap.put("LEFT_DATE", "#CELL26#");
		aliasValueMap.put("FACT_PAY", "#CELL27#");
		aliasValueMap.put("REMARK", "#CELL28#");
		

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("AGREE_NAME", this.VARCHAR);
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("STUDY_START_DATE", this.DATE);
		aliasTypeMap.put("STUDY_END_DATE", this.DATE);
		aliasTypeMap.put("STUDY_DAY", this.VARCHAR);
		aliasTypeMap.put("SERVICE_YEAR", this.VARCHAR);
		aliasTypeMap.put("CON_START_DATE", this.DATE);
		aliasTypeMap.put("CON_END_DATE", this.DATE);
		aliasTypeMap.put("SER_START_DATE", this.DATE);
		aliasTypeMap.put("SER_END_DATE", this.DATE);
		aliasTypeMap.put("EXCHANGE_RATE", this.VARCHAR);
		aliasTypeMap.put("HQ_FREE", this.VARCHAR);
		aliasTypeMap.put("CGFY_FREE", this.VARCHAR);
		aliasTypeMap.put("JP_FREE", this.VARCHAR);
		aliasTypeMap.put("ZFBZ_FREE", this.VARCHAR);
		aliasTypeMap.put("CGBZ_FREE", this.VARCHAR);
		aliasTypeMap.put("CGBZ_FREE_FACT", this.VARCHAR);
		aliasTypeMap.put("SYBX_FREE", this.VARCHAR);
		aliasTypeMap.put("YX_PAY", this.VARCHAR);
		aliasTypeMap.put("JT_FREE", this.VARCHAR);
		aliasTypeMap.put("TX_FREE", this.VARCHAR);
		aliasTypeMap.put("TOTAL_FEE", this.VARCHAR);
		aliasTypeMap.put("AGREE_START_DATE", this.DATE);
		aliasTypeMap.put("AGREE_END_DATE", this.DATE);
		aliasTypeMap.put("LEFT_DATE", this.DATE);
		aliasTypeMap.put("FACT_PAY", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CREATE_DATE,CREATED_BY,CREATED_IP,CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		//String agreeno=excelUtilSer.queryAgreeno();
		
		String appendValue=",sysdate"+ ",'"+admin.getAdminID()+"','"+ admin.getAdminIP()+"','"+ admin.getCpnyId()+ "','" +admin.getAdminID()+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("EDU_TRAIN_AGREEMENT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/edu/traineducation/trainAgreement";
		String navTabId = "edu0105";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		String statusCode=(String) modelMap.get("statusCode");
		if(statusCode.indexOf("200")>-1){
			this.excelUtilSer.insertEduTrainAgreement(modelMap);
			paramMap.put("CPNY_ID", admin.getCpnyId());
			//查询出导入的所有的agree_id为空的
			List elist=eduTrainDao.queryEmptyAgreeid(paramMap);
			if(elist!=null&&elist.size()>0){
				for(int i=0;i<elist.size();i++){
					LinkedHashMap linkmap=(LinkedHashMap) elist.get(i);
					paramMap.put("AGREE_NO", linkmap.get("AGREE_NO"));
					//先查询出是否有协议信息
					String agreeid="TRA000001";
					String maxAgreeId=eduTrainDao.queryMaxAgreeId(paramMap);
					if(maxAgreeId!=null && !"".equals(maxAgreeId)){
						String str=maxAgreeId.substring(maxAgreeId.indexOf("TRA")+4);
						String result = ""+(Integer.parseInt(str)+1);
						int size = 6-result.length(); 
						for(int j=0;j<size;j++){ 
						result="0"+result; 
						}
						agreeid="TRA"+result;
						paramMap.put("AGREE_ID", agreeid);
					}else{
						paramMap.put("AGREE_ID", agreeid);
					}
					
				   this.eduTrainDao.updateAgreeid(paramMap);
				}
			}
		}
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	
	/**
	 * 加班上限
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importOTLimitExcel")
	public ModelAndView importOTLimitExcel(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		pathCpnyID = admin.getCpnyId();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PERSON_ID", "#CELL0#");
		aliasValueMap.put("PERSON_NAME", "#CELL1#");
		aliasValueMap.put("LOCK_YN", "#CELL2#");
		aliasValueMap.put("LOCK_YN_100", "#CELL3#");
	
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("PERSON_NAME", this.VARCHAR);
		aliasTypeMap.put("LOCK_YN", this.NUMBER);
		aliasTypeMap.put("LOCK_YN_100", this.NUMBER);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",UPDATE_DATE,UPDATED_BY,UPDATED_IP,UPLOAD_BY,UPLOAD_DATE";
		//String agreeno=excelUtilSer.queryAgreeno();
		String appendValue=",sysdate"+ ",'"+admin.getAdminID()+"','"+ admin.getAdminIP()+"','"+admin.getAdminID()+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		
		String sqlI18nContent="SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim(#CELL0#,' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("ESS_OVERTIME_LIMIT_APPLY_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		//String forwardUrl = "/ess/infoApply/viewPersonOverTimeLimtList";
		String forwardUrl = "";
		String navTabId = "ar0704";
		//this.excelUtilSer.importExcelOTLimitData(request,response,map,modelMap);//导入到临时页面
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		String statusCode=(String) modelMap.get("statusCode");
		modelMap.put("UPLOAD_BY", admin.getAdminID());
		if(statusCode.indexOf("200")>-1){
		  this.excelUtilSer.insertOTLimit(modelMap);
		}
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/*评价对象Excel导入*/
	@RequestMapping(value="importEvsAffirmorExcel")
	public ModelAndView importEvsAffirmorExcel (HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		String resumeSEQ = request.getParameter("resumeSEQ");
		aliasValueMap.put("EMPLOYEE_ID", "#CELL0#");
		aliasValueMap.put("EMPLOYEE_NAME", "#CELL1#");
		aliasValueMap.put("EVALUATOR_ID_1", "#CELL2#");
		aliasValueMap.put("EVALUATOR_NAME_1", "#CELL3#");
		aliasValueMap.put("EVALUATOR_ID_2", "#CELL4#");
		aliasValueMap.put("EVALUATOR_NAME_1", "#CELL5#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPLOYEE_ID", this.VARCHAR);
		aliasTypeMap.put("EMPLOYEE_NAME", this.VARCHAR);
		aliasTypeMap.put("EVALUATOR_ID_1", this.VARCHAR);
		aliasTypeMap.put("EVALUATOR_NAME_1", this.VARCHAR);
		aliasTypeMap.put("EVALUATOR_ID_2", this.VARCHAR);
		aliasTypeMap.put("EVALUATOR_NAME_1", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField =  ",RESUME_SEQ,UPDATE_DATE,UPDATED_BY,UPDATED_IP,UPLOAD_BY,UPLOAD_DATE";
		String appendValue = ","+resumeSEQ+",sysdate"+",'"+admin.getAdminID()+"','"+admin.getAdminIP()+"','"+admin.getAdminID()+"',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String sqlI18nContent = "SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID = LTRIM(RTRIM(#CELL0#,' '),' ') AND CPNY_ID = '"+admin.getCpnyId()+"' ";
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("EVS_OBJECT_EVALUTOR_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		//String forwardUrl = "/ess/infoApply/viewPersonOverTimeLimtList";
		String forwardUrl = "";
		String navTabId = "ar0704";
		//this.excelUtilSer.importExcelOTLimitData(request,response,map,modelMap);//导入到临时页面
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		String statusCode=(String) modelMap.get("statusCode");
		modelMap.put("UPLOAD_BY", admin.getAdminID());
		modelMap.put("RESUME_SEQ", resumeSEQ);
		if(statusCode.indexOf("200")>-1){
			List objectList = this.evsManageSer.viewEvsInfoList(modelMap, "getEvsObjectEvalutorTemp");
			if (objectList.size() > 0) {
				for (int i = 0; i < objectList.size(); i++) {
					Map tempMap = (Map)objectList.get(i);
					tempMap.put("RESUME_SEQ", resumeSEQ);
					tempMap.put("adminID", admin.getAdminID());
					tempMap.put("adminIP", admin.getAdminIP());
					tempMap.put("interCpnyID", admin.getCpnyId());
					tempMap.put("affirmor0", tempMap.get("EMPLOYEE_ID"));
					tempMap.put("affirmor1", tempMap.get("EVALUATOR_ID_1"));
					tempMap.put("affirmor2", tempMap.get("EVALUATOR_ID_2"));
					String result = this.evsManageDao.addEvsObject(tempMap);
				}
			}
		}
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	/**
	 * 培训结果的导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importTrainResultEV")
	public ModelAndView importTrainResultEV(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		pathCpnyID = admin.getCpnyId();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		String BASIC_NO = request.getParameter("BASIC_NO");
		aliasValueMap.put("STU_EMPID", "#CELL0#");
		aliasValueMap.put("STU_LOCAL_NAME", "#CELL1#");
		aliasValueMap.put("DIFFICULTY", "#CELL2#");
		aliasValueMap.put("CONTENT_RICH", "#CELL3#");
		aliasValueMap.put("TIME_MODERATE", "#CELL4#");
		aliasValueMap.put("PRACTICABILITY", "#CELL5#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("STU_EMPID", this.VARCHAR);
		aliasTypeMap.put("STU_LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("DIFFICULTY", this.VARCHAR);
		aliasTypeMap.put("CONTENT_RICH", this.VARCHAR);
		aliasTypeMap.put("TIME_MODERATE", this.VARCHAR);
		aliasTypeMap.put("PRACTICABILITY", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",BASIC_NO,UPDATE_DATE,UPDATED_BY,UPDATED_IP,UPLOAD_BY,UPLOAD_DATE";
		//String agreeno=excelUtilSer.queryAgreeno();
		String appendValue=","+BASIC_NO+",sysdate"+ ",'"+admin.getAdminID()+"','"+ admin.getAdminIP()+"','"+admin.getAdminID()+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		
		//String sqlI18nContent="SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim(#CELL0#,' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
	    aliasValueI18nMap.put("BASIC_NO", BASIC_NO);
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("EDU_TRAIN_RESULT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "";
		String navTabId = "ar0704";
		//this.excelUtilSer.importExcelOTLimitData(request,response,map,modelMap);//导入到临时页面
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		String statusCode=(String) modelMap.get("statusCode");
		if(statusCode.indexOf("200")>-1){
		  this.excelUtilSer.insertTrainResult(modelMap);
		}
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	/**
	 * 培训结果的导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/teacherEvaluateImport")
	public ModelAndView teacherEvaluateImport(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		pathCpnyID = admin.getCpnyId();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		String [] parmater = request.getParameter("parmater").split("@");
		String BASIC_NO = parmater[1];
		String TEA_EMPID =  parmater[0];
		aliasValueMap.put("STU_EMPID", "#CELL0#");
		aliasValueMap.put("STU_LOCAL_NAME", "#CELL1#");
		aliasValueMap.put("GROOMING", "#CELL2#");
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("STU_EMPID", this.VARCHAR);
		aliasTypeMap.put("STU_LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("GROOMING", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",TEA_EMPID,BASIC_NO,UPDATE_DATE,UPDATED_BY,UPDATED_IP,UPLOAD_BY,UPLOAD_DATE";
		//String agreeno=excelUtilSer.queryAgreeno();
		String appendValue=","+TEA_EMPID+","+BASIC_NO+",sysdate"+ ",'"+admin.getAdminID()+"','"+ admin.getAdminIP()+"','"+admin.getAdminID()+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		
		//String sqlI18nContent="SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim(#CELL0#,' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueI18nMap.put("BASIC_NO", BASIC_NO);
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("edu_teacher_check_temp",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "";
		String navTabId = "ar0704";
		//this.excelUtilSer.importExcelOTLimitData(request,response,map,modelMap);//导入到临时页面
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		String statusCode=(String) modelMap.get("statusCode");
		if(statusCode.indexOf("200")>-1){
			  this.excelUtilSer.insertTeacherEvaluate(modelMap);
		}
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	/**
	 * 年假计划
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importVacPlanTSTO")
	public ModelAndView importVacPlanTSTO(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		pathCpnyID = admin.getCpnyId();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PERSON_ID", "#CELL0#");
		aliasValueMap.put("VAC_ID", "#CELL1#");
		aliasValueMap.put("JAN_VAC_CNT", "#CELL2#");
		aliasValueMap.put("FEB_VAC_CNT", "#CELL3#");
		aliasValueMap.put("MAR_VAC_CNT", "#CELL4#");
		aliasValueMap.put("APR_VAC_CNT", "#CELL5#");
		aliasValueMap.put("MAY_VAC_CNT", "#CELL6#");
		aliasValueMap.put("JUN_VAC_CNT", "#CELL7#");
		aliasValueMap.put("JUL_VAC_CNT", "#CELL8#");
		aliasValueMap.put("AUG_VAC_CNT", "#CELL9#");
		aliasValueMap.put("SEP_VAC_CNT", "#CELL10#");
		aliasValueMap.put("OCT_VAC_CNT", "#CELL11#");
		aliasValueMap.put("NOV_VAC_CNT", "#CELL12#");
		aliasValueMap.put("DEC_VAC_CNT", "#CELL13#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("VAC_ID", this.VARCHAR);
		aliasTypeMap.put("JAN_VAC_CNT", this.NUMBER);
		aliasTypeMap.put("FEB_VAC_CNT", this.NUMBER);
		aliasTypeMap.put("MAR_VAC_CNT", this.NUMBER);
		aliasTypeMap.put("APR_VAC_CNT", this.NUMBER);
		aliasTypeMap.put("MAY_VAC_CNT", this.NUMBER);
		aliasTypeMap.put("JUN_VAC_CNT", this.NUMBER);
		aliasTypeMap.put("JUL_VAC_CNT", this.NUMBER);
		aliasTypeMap.put("AUG_VAC_CNT", this.NUMBER);
		aliasTypeMap.put("SEP_VAC_CNT", this.NUMBER);
		aliasTypeMap.put("OCT_VAC_CNT", this.NUMBER);
		aliasTypeMap.put("NOV_VAC_CNT", this.NUMBER);
		aliasTypeMap.put("DEC_VAC_CNT", this.NUMBER);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",UPDATE_DATE,UPDATED_BY,UPDATED_IP,UPLOAD_BY,UPLOAD_DATE";
		//String agreeno=excelUtilSer.queryAgreeno();
		String appendValue=",sysdate"+ ",'"+admin.getAdminID()+"','"+ admin.getAdminIP()+"','"+admin.getAdminID()+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		
		String sqlI18nContent="SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim(#CELL0#,' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("AR_VAC_EMP_PLAN",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/ar/attendanceSettings/viewVacEmpList";
		String navTabId = "ar0232";
		//this.excelUtilSer.importExcelOTLimitData(request,response,map,modelMap);//导入到临时页面
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		String statusCode=(String) modelMap.get("statusCode");
		if(statusCode.indexOf("200")>-1){
			LinkedHashMap mapVac = new LinkedHashMap();
			mapVac.put("PERSON_ID", admin.getPersonId());
			this.excelUtilSer.insertVacPlan(mapVac);
		}
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 协议管理导入数据
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importStudentEvaluate")
	public ModelAndView importStudentEvaluate(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		String basicno=request.getParameter("basic_no");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");
		aliasValueMap.put("TEST_SCORE", "#CELL2#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("TEST_SCORE", this.VARCHAR);
		
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CREATE_DATE,CREATED_BY,CREATED_IP,CPNY_ID,UPLOAD_BY,UPLOAD_DATE,BASIC_NO";
		
		String appendValue=",sysdate"+ ",'"+admin.getAdminID()+"','"+ admin.getAdminIP()+"','"+ admin.getCpnyId()+ "','" +admin.getAdminID()+ "',sysdate"+",'"+basicno+"'";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("EDU_FREE_EMPLOYEE_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/edu/traineducation/studentEvaluateInfo?basicno="+basicno;
		modelMap.put("forwardUrl", forwardUrl);
		String navTabId = "edu0202";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		String statusCode=(String) modelMap.get("statusCode");
		if(statusCode.indexOf("200")>-1){
	      this.excelUtilSer.updateEduFreeEmployee(modelMap);
		}	
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 计划管理导入课程数据
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importTrainPlan")
	public ModelAndView importTrainPlan(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		String planno=request.getParameter("plan_no");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("COURSE_NAME_CODE", "#CELL0#");
		aliasValueMap.put("COURSE_DATE", "#CELL1#");
		aliasValueMap.put("COURSE_START_DATE", "#CELL2#");
		aliasValueMap.put("COURSE_END_DATE", "#CELL3#");
		aliasValueMap.put("DETAIL_ADDRESS", "#CELL4#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("COURSE_NAME_CODE", this.VARCHAR);
		aliasTypeMap.put("COURSE_DATE", this.DATE);
		aliasTypeMap.put("COURSE_START_DATE", this.VARCHAR);
		aliasTypeMap.put("COURSE_END_DATE", this.VARCHAR);
		aliasTypeMap.put("DETAIL_ADDRESS", this.VARCHAR);
		
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CREATE_DATE,CREATED_BY,CREATED_IP,CPNY_ID,UPLOAD_BY,UPLOAD_DATE,PLAN_NO";
		
		String appendValue=",sysdate"+ ",'"+admin.getAdminID()+"','"+ admin.getAdminIP()+"','"+ admin.getCpnyId()+ "','" +admin.getAdminID()+ "',sysdate"+",'"+planno+"'";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("EDU_TRAIN_SYLLABUS_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/edu/traineducation/planManagerInfo";
		String navTabId = "edu0103";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		String statusCode=(String) modelMap.get("statusCode");
		if(statusCode.indexOf("200")>-1){
			modelMap.put("PLAN_NO", planno);
			//先删除原有的课程表
			this.excelUtilSer.deleteEduTrainSyllabus(modelMap);
			this.excelUtilSer.updateEduTrainSyllabus(modelMap);
		}
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/** 
	 * 辅助信息批量导入
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importEmpInfo")
	public ModelAndView importEmpInfo(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		this.language=admin.getLanguage();
		
		String TEMPLATE_TYPE = request.getParameter("TEMPLATE_TYPE");
		LinkedHashMap aliasValueMap = new LinkedHashMap(); 
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap whereMap = new LinkedHashMap();
		//传入需要验证列的值 按照excel顺序传入
		List validateCellName = new ArrayList();
		List validateCell = new ArrayList();
		//保存社号存在位置
		int num=0;
		String tableName = "";
		String aliasNullStr = "";
		  
		if(!"215977".equals(TEMPLATE_TYPE)){
			if("215978".equals(TEMPLATE_TYPE)){//外国语
				//第二步，指定excel里要插入的列，以及默认要插入的列的值
				//String EMPID="	SELECT EMPID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
				aliasValueMap.put("LANGUAGE_NO","#CELL0#");
				aliasValueMap.put("PERSON_ID","#CELL1#");
				aliasValueMap.put("KAOSHIDATE", "#CELL2#");
				aliasValueMap.put("EXAM_NAME_CODE","#CELL3#");
				aliasValueMap.put("LANGUAGE_LEVEL_CODE","#CELL4#");
				aliasValueMap.put("MARK", "#CELL5#");
				aliasValueMap.put("ALLWANCE", "#CELL6#");
			
				aliasTypeMap.put("LANGUAGE_NO", this.VARCHAR);//社号
				aliasTypeMap.put("PERSON_ID", this.VARCHAR);//员工姓名
				aliasTypeMap.put("KAOSHIDATE", this.DATE);//证书颁发日期
				aliasTypeMap.put("EXAM_NAME_CODE", this.VARCHAR);//考试名
				aliasTypeMap.put("LANGUAGE_LEVEL_CODE", this.VARCHAR);//等级
				aliasTypeMap.put("MARK", this.VARCHAR);//分数
				aliasTypeMap.put("ALLWANCE", this.VARCHAR);//津贴标准
				
				//模板外的其它字段设置
				LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
				String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
				String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
				aliasValueAppendMap.put("appendField",appendField);
				aliasValueAppendMap.put("appendValue",appendValue);
				
				//LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
				LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HRM_TEMP_LANG_EVENT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
				
				String forwardUrl = "/hrm/empinfo/viewImportExcelTempLanguageDataList";
				String navTabId = "hrm4533";
				this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
			}else if("215980".equals(TEMPLATE_TYPE)){//工作经历
				//tableName = composeWorkExperience(aliasValueMap, aliasTypeMap, aliasValueI18nMap,validateCell,validateCellName);
				aliasValueMap.put("EMPID", "#CELL0#");
				aliasValueMap.put("LOCAL_NAME", "#CELL1#");
				aliasValueMap.put("START_DATE", "#CELL2#");
				aliasValueMap.put("END_DATE", "#CELL3#");
				aliasValueMap.put("CPNY_NAME", "#CELL4#");
				aliasValueMap.put("DEPT_NAME", "#CELL5#");
				aliasValueMap.put("POSITION", "#CELL6#");
				aliasValueMap.put("DUTY", "#CELL7#");
				aliasValueMap.put("PAYROLL", "#CELL8#");
				
				aliasTypeMap.put("EMPID", this.VARCHAR);//社号
				aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);//员工姓名
				aliasTypeMap.put("START_DATE", this.DATE);//开始日期
				aliasTypeMap.put("END_DATE", this.DATE);//结束日期
				aliasTypeMap.put("CPNY_NAME", this.VARCHAR);//工作单位
				aliasTypeMap.put("DEPT_NAME", this.VARCHAR);//部门
				aliasTypeMap.put("POSITION", this.VARCHAR);//职位
				aliasTypeMap.put("DUTY", this.VARCHAR);//职级
				aliasTypeMap.put("PAYROLL", this.NUMBER);//工资待遇
				//模板外的其它字段设置
				LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
				String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
				String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
				aliasValueAppendMap.put("appendField", appendField);
				aliasValueAppendMap.put("appendValue", appendValue);
				
				LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_WORK_EXPERIENCE_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
				String forwardUrl = "/hrm/empinfo/viewImportExcelTempWorkExperienceList";
				String navTabId = "hrm4533";
				this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
			}else if("215979".equals(TEMPLATE_TYPE)){//资格证
				//tableName = composeQualification(aliasValueMap, aliasTypeMap, aliasValueI18nMap);
				aliasValueMap.put("EMPID", "#CELL0#");
				aliasValueMap.put("LOCAL_NAME", "#CELL1#");
				aliasValueMap.put("QUAL_NAME", "#CELL2#");
				aliasValueMap.put("QUAL_LEVEL", "#CELL3#");
				aliasValueMap.put("QUAL_CARD_NO", "#CELL4#");
				aliasValueMap.put("QUAL_INSTITUTE", "#CELL5#");
				aliasValueMap.put("DATE_OBTAINED", "#CELL6#");
				aliasValueMap.put("QUAL_REMARK", "#CELL7#");
				
				aliasTypeMap.put("EMPID", this.VARCHAR);
				aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);//员工姓名
				aliasTypeMap.put("QUAL_NAME", this.VARCHAR);//资格证名称
				aliasTypeMap.put("QUAL_LEVEL", this.VARCHAR);//证件级别
				aliasTypeMap.put("QUAL_CARD_NO", this.VARCHAR);//职称
				aliasTypeMap.put("QUAL_INSTITUTE", this.VARCHAR);//颁发机构
				aliasTypeMap.put("DATE_OBTAINED", this.DATE);//颁发日期
				aliasTypeMap.put("QUAL_REMARK", this.VARCHAR);//津贴标准
				//模板外的其它字段设置
				LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
				String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
				String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
				aliasValueAppendMap.put("appendField", appendField);
				aliasValueAppendMap.put("appendValue", appendValue);
				LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_QUALIFICATION_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
				String forwardUrl = "/hrm/empinfo/viewImportExcelTempQualList";
				String navTabId = "hrm4533";
				this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
			}else if("278650".equals(TEMPLATE_TYPE)){//兼卖产品信息
				aliasValueMap.put("EMPID", "#CELL0#");//社号
				aliasValueMap.put("LOCAL_NAME", "#CELL1#");//员工姓名
				aliasValueMap.put("PRODUCT_NO", "#CELL2#");//培训名称
				aliasValueMap.put("REMARK", "#CELL3#");//培训名称
		
				aliasTypeMap.put("EMPID", this.VARCHAR);
				aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
				aliasTypeMap.put("PRODUCT_NO", this.VARCHAR);
				aliasTypeMap.put("REMARK", this.VARCHAR);
				
				//模板外的其它字段设置
				LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
				String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
				String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
				aliasValueAppendMap.put("appendField", appendField);
				aliasValueAppendMap.put("appendValue", appendValue);
				LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_EMP_SELL_PRODUCT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
				String forwardUrl = "/hrm/empinfo/viewImportProductDataList";
				String navTabId = "hrm4533";
				this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
			}else if("215981".equals(TEMPLATE_TYPE)){//培训
				aliasValueMap.put("EMPID", "#CELL0#");//社号
				aliasValueMap.put("LOCAL_NAME", "#CELL1#");//员工姓名
				aliasValueMap.put("COURSE_NAME", "#CELL2#");//培训名称
				aliasValueMap.put("MUST_CODE", "#CELL3#");//选修还是必修
				aliasValueMap.put("TRAINING_DIFFERENTIATE", "#CELL4#");//培训区分
				aliasValueMap.put("START_DATE", "#CELL5#");//起始日期
				aliasValueMap.put("END_DATE", "#CELL6#");//结束日期
				aliasValueMap.put("INSTITUTION_NAME", "#CELL7#");//培训机构
				aliasValueMap.put("TRAINING_METHOD", "#CELL8#");//培训方法
				aliasValueMap.put("TRAINING_TIME", "#CELL9#");//培训时间
				aliasValueMap.put("TRAINING_RESULT", "#CELL10#");//培训结果
				aliasValueMap.put("REMARKS", "#CELL11#");//备注
		
				aliasTypeMap.put("EMPID", this.VARCHAR);
				aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
				aliasTypeMap.put("COURSE_NAME", this.VARCHAR);
				aliasTypeMap.put("MUST_CODE", this.VARCHAR);
				aliasTypeMap.put("TRAINING_DIFFERENTIATE", this.VARCHAR);
				aliasTypeMap.put("START_DATE", this.DATE);
				aliasTypeMap.put("END_DATE", this.DATE);
				aliasTypeMap.put("INSTITUTION_NAME", this.VARCHAR);
				aliasTypeMap.put("TRAINING_METHOD", this.VARCHAR);
				aliasTypeMap.put("TRAINING_TIME", this.VARCHAR);
				aliasTypeMap.put("TRAINING_RESULT", this.VARCHAR);
				aliasTypeMap.put("REMARKS", this.VARCHAR);
				
				//模板外的其它字段设置
				LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
				String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
				String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
				aliasValueAppendMap.put("appendField", appendField);
				aliasValueAppendMap.put("appendValue", appendValue);
				LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_TRAINING_INFO_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
				String forwardUrl = "/hrm/empinfo/viewImportTrainingDataList";
				String navTabId = "hrm4533";
				this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
			}else if("215982".equals(TEMPLATE_TYPE)){//评价
				//tableName = composeEvsInfo(aliasValueMap, aliasTypeMap, aliasValueI18nMap,validateCell,validateCellName);
				aliasValueMap.put("EMPID", "#CELL0#");
				aliasValueMap.put("LOCAL_NAME", "#CELL1#");
				aliasValueMap.put("EV_PERIOD", "#CELL2#");
				aliasValueMap.put("EV_ACHI", "#CELL3#");
				aliasValueMap.put("EV_ATTI", "#CELL4#");
				aliasValueMap.put("EV_ABIL", "#CELL5#");
				aliasValueMap.put("EV_MARK", "#CELL6#");
				aliasValueMap.put("EV_GRADE", "#CELL7#");
				aliasValueMap.put("SUGGESTION", "#CELL8#");
				aliasValueMap.put("FINAL_SEQUENCE", "#CELL9#");
				aliasValueMap.put("TOTAL_PEOPLE", "#CELL10#");
				aliasValueMap.put("EV_REMARK", "#CELL11#");
				
				aliasTypeMap.put("EMPID", this.VARCHAR);//社号
				aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);//员工姓名
				aliasTypeMap.put("EV_PERIOD", this.VARCHAR);//评价期间
				aliasTypeMap.put("EV_ACHI", this.NUMBER);//绩效
				aliasTypeMap.put("EV_ATTI", this.NUMBER);//态度
				aliasTypeMap.put("EV_ABIL", this.NUMBER);//能力
				aliasTypeMap.put("EV_MARK", this.NUMBER);//评价分数
				aliasTypeMap.put("EV_GRADE", this.VARCHAR);//评价等级
				aliasTypeMap.put("SUGGESTION", this.VARCHAR);//意见
				aliasTypeMap.put("FINAL_SEQUENCE", this.VARCHAR);//最终顺位
				aliasTypeMap.put("TOTAL_PEOPLE", this.VARCHAR);//总评价人员数
				aliasTypeMap.put("EV_REMARK", this.VARCHAR);//备注
				//模板外的其它字段设置
				LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
				String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
				String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
				aliasValueAppendMap.put("appendField", appendField);
				aliasValueAppendMap.put("appendValue", appendValue);
				
				LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_EVS_INFO_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
				String forwardUrl = "/hrm/empinfo/viewImportExcelTempEvsList";
				String navTabId = "hrm4533";
				this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
			}else if("215983".equals(TEMPLATE_TYPE)){//工会
				//tableName = composeLabourUnion(aliasValueMap, aliasTypeMap, aliasValueI18nMap,validateCell,validateCellName);
				aliasValueMap.put("EMPID", "#CELL0#");
				aliasValueMap.put("LOCAL_NAME", "#CELL1#");
				aliasValueMap.put("RESPONSIBITITY", "#CELL2#");
				aliasValueMap.put("ADDDATE", "#CELL3#");
				aliasValueMap.put("QUITDATE", "#CELL4#");
				aliasValueMap.put("PAY_FLAG", "#CELL5#");
				aliasValueMap.put("PAY_TYPE", "#CELL6#");
				aliasValueMap.put("REMARK", "#CELL7#");
				
				aliasTypeMap.put("EMPID", this.VARCHAR);//社号
				aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);//员工姓名
				aliasTypeMap.put("RESPONSIBITITY", this.VARCHAR);//开始日期
				aliasTypeMap.put("ADDDATE", this.DATE);//结束日期
				aliasTypeMap.put("QUITDATE", this.DATE);//工作单位
				aliasTypeMap.put("PAY_FLAG", this.VARCHAR);//部门
				aliasTypeMap.put("PAY_TYPE", this.VARCHAR);//职位
				aliasTypeMap.put("REMARK", this.VARCHAR);//工作单位
				//模板外的其它字段设置
				LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
				String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
				String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
				aliasValueAppendMap.put("appendField", appendField);
				aliasValueAppendMap.put("appendValue", appendValue);
				
				LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_TRADEUNION_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
				String forwardUrl = "/hrm/empinfo/viewImportExcelTempTradeUnionList";
				String navTabId = "hrm4533";
				this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
			}else if("215984".equals(TEMPLATE_TYPE)){//残疾证
				//第二步，指定excel里要插入的列，以及默认要插入的列的值
				aliasValueMap.put("T_ID", "#CELL0#");
				aliasValueMap.put("PERSON_ID", "#CELL1#");
				aliasValueMap.put("DISABILITY_TYPE", "#CELL2#");
				aliasValueMap.put("ADDDATE", "#CELL3#");
				aliasValueMap.put("DISABILITY_VALIDITY","#CELL4#");
				aliasValueMap.put("REMARK", "#CELL5#");
				
				
				//第三步，指定每列的类型
				aliasTypeMap.put("T_ID", this.VARCHAR);
				aliasTypeMap.put("PERSON_ID", this.VARCHAR);
				aliasTypeMap.put("DISABILITY_TYPE", this.VARCHAR);
				aliasTypeMap.put("ADDDATE", this.DATE);
				aliasTypeMap.put("DISABILITY_VALIDITY", this.VARCHAR);
				aliasTypeMap.put("REMARK", this.VARCHAR);
				
				//模板外的其它字段设置
				LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
				String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
				String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
				aliasValueAppendMap.put("appendField",appendField);
				aliasValueAppendMap.put("appendValue",appendValue);
				
				//LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
				LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HRM_TEMP_DISABLE_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
				
				String forwardUrl = "/hrm/empinfo/viewImportExcelTempDisableDataList";
				String navTabId = "hrm4533";
				this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
			}else if("215985".equals(TEMPLATE_TYPE)){//紧急联系人
				//第二步，指定excel里要插入的列，以及默认要插入的列的值
				aliasValueMap.put("FAMILY_NO", "#CELL0#");
				aliasValueMap.put("PERSON_ID","#CELL1#");
				aliasValueMap.put("FAM_TYPE_CODE","#CELL2#");
				aliasValueMap.put("FAM_NAME", "#CELL3#");
				aliasValueMap.put("FAM_PHONE", "#CELL4#");
				
				//第三步，指定每列的类型
				aliasTypeMap.put("FAMILY_NO", this.VARCHAR);
				aliasTypeMap.put("PERSON_ID", this.VARCHAR);
				aliasTypeMap.put("FAM_TYPE_CODE", this.VARCHAR);
				aliasTypeMap.put("FAM_NAME", this.VARCHAR);
				aliasTypeMap.put("FAM_PHONE", this.VARCHAR);
				
				//模板外的其它字段设置
				LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
				String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
				String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
				aliasValueAppendMap.put("appendField",appendField);
				aliasValueAppendMap.put("appendValue",appendValue);
				
				//LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
				LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HRM_TEMP_CONTACT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
				
				String forwardUrl = "/hrm/empinfo/viewImportExcelTempContactDataList";
				String navTabId = "hrm4533";
				this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
			}else if("215986".equals(TEMPLATE_TYPE)){//黑色档案
				tableName = composeBadArchives(aliasValueMap, aliasTypeMap, aliasValueI18nMap,validateCell,validateCellName);
			}else if("216002".equals(TEMPLATE_TYPE)){//辅助信息

				//第二步，指定excel里要插入的列，以及默认要插入的列的值
				aliasValueMap.put("ASSIST_NO", "#CELL0#");
				aliasValueMap.put("PERSON_ID", "#CELL1#");
				aliasValueMap.put("TITLE", "#CELL2#");
				aliasValueMap.put("CONTENTA", "#CELL3#");
				
				//第三步，指定每列的类型
				aliasTypeMap.put("ASSIST_NO", this.VARCHAR);
				aliasTypeMap.put("PERSON_ID", this.VARCHAR);
				aliasTypeMap.put("TITLE", this.VARCHAR);
				aliasTypeMap.put("CONTENTA", this.VARCHAR);
				
				//模板外的其它字段设置
				LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
				String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
				String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
				aliasValueAppendMap.put("appendField",appendField);
				aliasValueAppendMap.put("appendValue",appendValue);
				//LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
				LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HRM_TEMP_ASSIST_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
				String forwardUrl = "/hrm/empinfo/viewImportExcelTempAssistDataList";
				String navTabId = "hrm4533";
				this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
			}else if("278650".equals(TEMPLATE_TYPE)){//兼卖产品
				aliasValueMap.put("EMPID", "#CELL0#");
				aliasValueMap.put("LOCAL_NAME", "#CELL1#");
				aliasValueMap.put("PRODUCT_NO", "#CELL2#");
				
				aliasTypeMap.put("EMPID", this.VARCHAR);//社号
				aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);//员工姓名
				aliasTypeMap.put("PRODUCT_NO", this.VARCHAR);//开始日期
				//模板外的其它字段设置
				LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
				String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
				String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
				aliasValueAppendMap.put("appendField", appendField);
				aliasValueAppendMap.put("appendValue", appendValue);
				
				LinkedHashMap map= this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_EMP_SELL_PRODUCT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
				String forwardUrl = "/hrm/empinfo/viewImportExcelTempSellProductList";
				String navTabId = "hrm4533";
				this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
			}
			
		}else {
			//第二步，指定excel里要插入的列，以及默认要插入的列的值
			aliasValueMap.put("EMPID", "#CELL0#");//员工号
			aliasValueMap.put("EMPNAME", "#CELL1#");//员工姓名
			aliasValueMap.put("IDCARD_ADDR", "#CELL2#");//身份证地址
			aliasValueMap.put("BORNPLACE_CODE", "#CELL3#");//籍贯
			aliasValueMap.put("NATION_CODE", "#CELL4#");//民族
			aliasValueMap.put("POLITY_CODE", "#CELL5#");//政治面貌
			aliasValueMap.put("WHETHER_COMMUNIST", "#CELL6#");//是否共产党员
			aliasValueMap.put("HEIGHT", "#CELL7#");
			aliasValueMap.put("WEIGHT", "#CELL8#");
			aliasValueMap.put("BLOOD_TYPE", "#CELL9#");
			aliasValueMap.put("DISABILITY_YN", "#CELL10#");//是否残疾
			aliasValueMap.put("RECRUITMENT_SOURCE_TYPE", "#CELL11#");//招聘来源
			aliasValueMap.put("LEAVE_REASON", "#CELL12#");//离职原因
			aliasValueMap.put("REMARK", "#CELL13#");//奖惩备注
			aliasValueMap.put("LOVE_FUND_PAYMENT_TYPE", "#CELL14#");//爱心基金支付
			aliasValueMap.put("IF_PAYMENT_LOVE_FUND", "#CELL15#");//是否支付爱心基金
			aliasValueMap.put("IF_PAYMENT_RENT", "#CELL16#");//负担房租标志:E->个人租房;C->公司租房
			aliasValueMap.put("IF_PAYMENT_MEDICAL", "#CELL17#");//负担医疗费标志
			aliasValueMap.put("IF_PAYMENT_EDUCATION", "#CELL18#");//负担教育费标志
			/*aliasValueMap.put("EMP_TYPE_CODE", "#CELL19#");//员工类型
*/			aliasValueMap.put("PROMTR_WORK_TP", "#CELL19#");//工作类型
			/*aliasValueMap.put("EMP_TYPE_START_DATE", "#CELL21#");//人员类型生效日期
*/			aliasValueMap.put("INSRAREA_ID_NAME", "#CELL20#");//福利地区(保险)
			aliasValueMap.put("WORK_AREA", "#CELL21#");//工作地
			aliasValueMap.put("MANUAL_NUM", "#CELL22#");//劳动手册编号
			aliasValueMap.put("OUTER_WORK_YEAR", "#CELL23#");//社外工龄
			aliasValueMap.put("INSRAREA_ID_INS_NAME", "#CELL24#");//福利地区(公积金)
			aliasValueMap.put("INSURANCE_COMPANY", "#CELL25#");//保险公司
			aliasValueMap.put("INSURANCE_TYPE_CODE", "#CELL26#");//保险类型
			aliasValueMap.put("YY_VAC_STD_DATE", "#CELL27#");//年假基准
			aliasValueMap.put("PROD_TP", "#CELL28#");//产品
			aliasValueMap.put("PROMTR_TP", "#CELL29#");//促销员所属
			aliasValueMap.put("STAR_TP", "#CELL30#");//星级级别
			aliasValueMap.put("PART_TIME_YN", "#CELL31#");//是否兼卖
			aliasValueMap.put("COMM_YN", "#CELL32#");//是否是共建促销员
			aliasValueMap.put("EVS_TYPE_NAME", "#CELL33#");//评价类型
			//第三步，指定每列的类型
			aliasTypeMap.put("EMPID", this.VARCHAR);//员工号
			aliasTypeMap.put("EMPNAME", this.VARCHAR);//员工姓名
			aliasTypeMap.put("IDCARD_ADDR", this.VARCHAR);//身份证地址
			aliasTypeMap.put("BORNPLACE_CODE", this.VARCHAR);//籍贯
			aliasTypeMap.put("NATION_CODE", this.VARCHAR);//民族
			aliasTypeMap.put("POLITY_CODE", this.VARCHAR);//政治面貌
			aliasTypeMap.put("WHETHER_COMMUNIST", this.VARCHAR);//是否共产党员
			aliasTypeMap.put("HEIGHT", this.VARCHAR);
			aliasTypeMap.put("WEIGHT", this.VARCHAR);
			aliasTypeMap.put("BLOOD_TYPE", this.VARCHAR);
			aliasTypeMap.put("DISABILITY_YN", this.VARCHAR);//是否残疾
			aliasTypeMap.put("RECRUITMENT_SOURCE_TYPE", this.VARCHAR);//招聘来源
			aliasTypeMap.put("LEAVE_REASON", this.VARCHAR);//离职原因
			aliasTypeMap.put("REMARK", this.VARCHAR);//奖惩备注
			aliasTypeMap.put("LOVE_FUND_PAYMENT_TYPE", this.VARCHAR);//爱心基金支付
			aliasTypeMap.put("IF_PAYMENT_LOVE_FUND", this.VARCHAR);//是否支付爱心基金
			aliasTypeMap.put("IF_PAYMENT_RENT", this.VARCHAR);//负担房租标志:E->个人租房;C->公司租房
			aliasTypeMap.put("IF_PAYMENT_MEDICAL", this.VARCHAR);//负担医疗费标志
			aliasTypeMap.put("IF_PAYMENT_EDUCATION", this.VARCHAR);//负担教育费标志
			/*aliasTypeMap.put("EMP_TYPE_CODE", this.VARCHAR);//员工类型
*/			aliasTypeMap.put("PROMTR_WORK_TP", this.VARCHAR);//工作类型
			/*aliasTypeMap.put("EMP_TYPE_START_DATE", this.VARCHAR);//人员类型生效日期
*/			aliasTypeMap.put("INSRAREA_ID_NAME", this.VARCHAR);//福利地区(保险)
			aliasTypeMap.put("WORK_AREA", this.VARCHAR);//工作地
			aliasTypeMap.put("MANUAL_NUM", this.VARCHAR);//劳动手册编号
			aliasTypeMap.put("OUTER_WORK_YEAR", this.NUMBER);//社外工龄
			aliasTypeMap.put("INSRAREA_ID_INS_NAME", this.VARCHAR);//福利地区(公积金)
			aliasTypeMap.put("INSURANCE_COMPANY", this.VARCHAR);//保险公司
			aliasTypeMap.put("INSURANCE_TYPE_CODE", this.VARCHAR);//保险类型
			aliasTypeMap.put("YY_VAC_STD_DATE", this.VARCHAR);//年假基准
			aliasTypeMap.put("PROD_TP", this.VARCHAR);//产品
			aliasTypeMap.put("PROMTR_TP", this.VARCHAR);//促销员所属
			aliasTypeMap.put("STAR_TP", this.VARCHAR);//星级级别
			aliasTypeMap.put("PART_TIME_YN", this.VARCHAR);//是否兼卖
			aliasTypeMap.put("COMM_YN", this.VARCHAR);//是否是共建促销员
			aliasTypeMap.put("EVS_TYPE_NAME", this.VARCHAR);//评价类型
			
			//模板外的其它字段设置
			LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
			String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
			String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
			aliasValueAppendMap.put("appendField", appendField);
			aliasValueAppendMap.put("appendValue", appendValue);
			
			LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_EMPLOYEE_BASE_MSG_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
			String forwardUrl = "/hrm/empinfo/importEmpInfoTempList";
			String navTabId = "hrm4533";
			this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		}
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	/**
	 * 组装员工基本信息验证参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 */
	private void composeEmpValidateInfo(List validateCell, List validateCellName){
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		//传入需要验证列的名称 按照excel顺序传入
		validateCellName.add("社号(必填),1,0,hr_employee");
		validateCellName.add("员工姓名(可为空),-1");
		validateCellName.add("家庭住址,-1");
		validateCellName.add("籍贯,1,1,774");
		validateCellName.add("民族,1,1,210942");
		validateCellName.add("政治面貌,1,1,210938");
		validateCellName.add("是否共产党员,1,1,123224");
		validateCellName.add("身高,-1");
		validateCellName.add("体重,-1");
		validateCellName.add("血型,1,1,4573");
		validateCellName.add("是否残疾,1,1,123224");
		validateCellName.add("招聘来源,1,1,3306");
		validateCellName.add("离职原因,-1");
		validateCellName.add("奖惩备注,-1");
		validateCellName.add("爱心基金支付方式,1,1,211654");
		validateCellName.add("是否支付爱心基金,1,1,123224");
		validateCellName.add("负担房租标志,1,1,123224");
		validateCellName.add("负担医疗费标志,1,1,123224");
		validateCellName.add("负担教育费标志,1,1,123224");
		validateCellName.add("人员类型(CHR),1,1,1368");
		validateCellName.add("工作类型(CHR),1,1,211554");
		validateCellName.add("工作地区,1,1,211557");
		validateCellName.add("劳动手册编号,-1");
		validateCellName.add("社外工龄,-1");
		validateCellName.add("福利地区,1,1,216736");
		validateCellName.add("保险公司,-1");
		validateCellName.add("保险类型 ,1,1,483");
		validateCellName.add("产品,1,1,211424");
		validateCellName.add("促销员所属,1,1,211837");
		validateCellName.add("星级级别,1,1,215954");
		validateCellName.add("是否兼卖,1,1,123224");
		validateCellName.add("是否共建促销员,1,1,123224");
	}
	/**
	 * 组装员工基本信息参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 */
	private LinkedHashMap composeBaseEmpInfo(String adminId){
		LinkedHashMap aliasValueMap = new LinkedHashMap(); 
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap whereMap = new LinkedHashMap();
		
		String aliasNullStr = "";
		
		//第一步，指定where条件
		String PERSON_ID="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		whereMap.put("PERSON_ID", PERSON_ID);
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		String WHETHER_COMMUNIST="SELECT CODE_NO WHETHER_COMMUNIST FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123224 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL6#"+"'" ;
		aliasValueMap.put("WHETHER_COMMUNIST", WHETHER_COMMUNIST);
		String EMP_TYPE_CODE="SELECT CODE_NO EMP_TYPE_CODE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=1368 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL19#"+"'" ;
		aliasValueMap.put("EMP_TYPE_CODE", EMP_TYPE_CODE);
		String PROMTR_WORK_TP="SELECT CODE_NO PROMTR_WORK_TP FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=211554 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL20#"+"'" ;
		aliasValueMap.put("PROMTR_WORK_TP", PROMTR_WORK_TP);
		String WORK_AREA="SELECT CODE_NO WORK_AREA FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=211557 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL21#"+"'" ;
		aliasValueMap.put("WORK_AREA", WORK_AREA);
		aliasValueMap.put("MANUAL_NUM", "#CELL22#");
		aliasValueMap.put("OUTER_WORK_YEAR", "#CELL23#");
		String INSRAREA_ID="SELECT CODE_NO INSRAREA_ID FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=216736 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL24#"+"'" ;
		aliasValueMap.put("INSRAREA_ID", INSRAREA_ID);
		String PROD_TP="SELECT CODE_NO PROD_TP FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=211424 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL27#"+"'" ;
		aliasValueMap.put("PROD_TP", PROD_TP);
		String PROMTR_TP="SELECT CODE_NO PROMTR_TP FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=211837 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL28#"+"'" ;
		aliasValueMap.put("PROMTR_TP", PROMTR_TP);
		String STAR_TP="SELECT CODE_NO STAR_TP FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=215954 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL29#"+"'" ;
		aliasValueMap.put("STAR_TP", STAR_TP);
		String PART_TIME_YN="SELECT CODE_NO PART_TIME_YN FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123224 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL30#"+"'" ;
		aliasValueMap.put("PART_TIME_YN", PART_TIME_YN);
		String COMM_YN="SELECT CODE_NO COMM_YN FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123224 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL31#"+"'" ;
		aliasValueMap.put("COMM_YN", COMM_YN);
		
		//第三步，指定每列的类型
		aliasTypeMap.put("WHETHER_COMMUNIST", this.VARCHAR);//是否共产党员
		aliasTypeMap.put("EMP_TYPE_CODE", this.VARCHAR);//员工类型
		aliasTypeMap.put("PROMTR_WORK_TP", this.VARCHAR);//工作类型
		aliasTypeMap.put("WORK_AREA", this.VARCHAR);//工作地
		aliasTypeMap.put("MANUAL_NUM", this.VARCHAR);//劳动手册编号
		aliasTypeMap.put("OUTER_WORK_YEAR", this.NUMBER);//外部工作年资
		aliasTypeMap.put("INSRAREA_ID", this.VARCHAR);//福利地区(住房)
		aliasTypeMap.put("PROD_TP", this.VARCHAR);//产品
		aliasTypeMap.put("PROMTR_TP", this.VARCHAR);//促销员所属
		aliasTypeMap.put("STAR_TP", this.VARCHAR);//星级级别
		aliasTypeMap.put("PART_TIME_YN", this.VARCHAR);//是否兼卖
		aliasTypeMap.put("COMM_YN", this.VARCHAR);//是否是共建促销员
		
		aliasValueMap.put("UPDATED_BY", adminId);
		aliasValueMap.put("UPDATE_DATE", "SYSDATE");
		
		aliasTypeMap.put("UPDATED_BY", this.VARCHAR);
		aliasTypeMap.put("UPDATE_DATE", this.SYSDATE);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("HR_EMPLOYEE",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("aliasNullStr", aliasNullStr);
		map.put("whereMap", whereMap);
		
		return map;
	}
	
	/**
	 * 组装员工基本信息参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 */
	private LinkedHashMap composePersonInfo(String adminId){
		LinkedHashMap aliasValueMap = new LinkedHashMap(); 
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap whereMap = new LinkedHashMap();
		String aliasNullStr = "";
		//第一步，指定where条件
		String PERSON_ID="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		whereMap.put("PERSON_ID", PERSON_ID);
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("IDCARD_ADDR", "#CELL2#");
		String BORNPLACE_CODE="SELECT CODE_NO BORNPLACE_CODE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=774 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL3#"+"'" ;
		aliasValueMap.put("BORNPLACE_CODE", BORNPLACE_CODE);
		String NATION_CODE="SELECT CODE_NO NATION_CODE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=210942 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL4#"+"'" ;
		aliasValueMap.put("NATION_CODE", NATION_CODE);
		String POLITY_CODE="SELECT CODE_NO POLITY_CODE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=210938 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL5#"+"'" ;
		aliasValueMap.put("POLITY_CODE", POLITY_CODE);
		aliasValueMap.put("HEIGHT", "#CELL7#");
		aliasValueMap.put("WEIGHT", "#CELL8#");
		String BLOOD_TYPE="SELECT CODE_NO BLOOD_TYPE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=4573 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL9#"+"'" ;
		aliasValueMap.put("BLOOD_TYPE", BLOOD_TYPE);
		String DISABILITY_YN="SELECT CODE_NO DISABILITY_YN FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123224 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL10#"+"'" ;
		aliasValueMap.put("DISABILITY_YN", DISABILITY_YN); 
		String RECRUITMENT_SOURCE_TYPE="SELECT CODE_NO RECRUITMENT_SOURCE_TYPE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=3306 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL11#"+"'" ;
		aliasValueMap.put("RECRUITMENT_SOURCE_TYPE", RECRUITMENT_SOURCE_TYPE); 
		aliasValueMap.put("LEAVE_REASON", "#CELL12#");
		aliasValueMap.put("REMARK", "#CELL13#");
		aliasValueMap.put("INSURANCE_COMPANY", "#CELL25#");
		String INSURANCE_TYPE_CODE="SELECT CODE_NO INSURANCE_TYPE_CODE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=483 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL26#"+"'" ;
		aliasValueMap.put("INSURANCE_TYPE_CODE", INSURANCE_TYPE_CODE); 
		
		//第三步，指定每列的类型
		aliasTypeMap.put("IDCARD_ADDR", this.VARCHAR);//身份证地址
		aliasTypeMap.put("BORNPLACE_CODE", this.VARCHAR);//籍贯
		aliasTypeMap.put("NATION_CODE", this.VARCHAR);//民族
		aliasTypeMap.put("POLITY_CODE", this.VARCHAR);//政治面貌
		aliasTypeMap.put("HEIGHT", this.VARCHAR);
		aliasTypeMap.put("WEIGHT", this.VARCHAR);
		aliasTypeMap.put("BLOOD_TYPE", this.VARCHAR);
		aliasTypeMap.put("DISABILITY_YN", this.VARCHAR);//是否残疾
		aliasTypeMap.put("RECRUITMENT_SOURCE_TYPE", this.VARCHAR);//招聘来源
		aliasTypeMap.put("LEAVE_REASON", this.VARCHAR);//离职原因
		aliasTypeMap.put("REMARK", this.VARCHAR);//奖惩备注
		aliasTypeMap.put("INSURANCE_COMPANY", this.VARCHAR);//保险公司
		aliasTypeMap.put("INSURANCE_TYPE_CODE", this.VARCHAR);//保险类型

		aliasValueMap.put("UPDATED_BY", adminId);
		aliasValueMap.put("UPDATE_DATE", "SYSDATE");		
		aliasTypeMap.put("UPDATED_BY", this.VARCHAR);
		aliasTypeMap.put("UPDATE_DATE", this.SYSDATE);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("HR_PERSONAL_INFO",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("aliasNullStr", aliasNullStr);
		map.put("whereMap", whereMap);
		return map;
	}
	
	/**
	 * 组装员工账户基本信息参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 */
	private LinkedHashMap composeEmpPaInfo(String adminId){
		LinkedHashMap aliasValueMap = new LinkedHashMap(); 
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap whereMap = new LinkedHashMap();

		String aliasNullStr = "";
		
		//第一步，指定where条件
		String PERSON_ID="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		whereMap.put("PERSON_ID", PERSON_ID);

		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		String LOVE_FUND_PAYMENT_TYPE="SELECT CODE_NO LOVE_FUND_PAYMENT_TYPE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=211654 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL115#"+"'" ;
		aliasValueMap.put("LOVE_FUND_PAYMENT_TYPE", LOVE_FUND_PAYMENT_TYPE);
		String IF_PAYMENT_LOVE_FUND="SELECT CODE_NO IF_PAYMENT_LOVE_FUND FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123224 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL16#"+"'" ;
		aliasValueMap.put("IF_PAYMENT_LOVE_FUND", IF_PAYMENT_LOVE_FUND);
		String IF_PAYMENT_RENT="SELECT CODE_NO IF_PAYMENT_RENT FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123224 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL17#"+"'" ;
		aliasValueMap.put("IF_PAYMENT_RENT", IF_PAYMENT_RENT);
		String IF_PAYMENT_MEDICAL="SELECT CODE_NO IF_PAYMENT_MEDICAL FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123224 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL18#"+"'" ;
		aliasValueMap.put("IF_PAYMENT_MEDICAL", IF_PAYMENT_MEDICAL);
		String IF_PAYMENT_EDUCATION="SELECT CODE_NO IF_PAYMENT_EDUCATION FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123224 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL19#"+"'" ;
		aliasValueMap.put("IF_PAYMENT_EDUCATION", IF_PAYMENT_EDUCATION);
		
		//第三步，指定每列的类型
		aliasTypeMap.put("LOVE_FUND_PAYMENT_TYPE", this.VARCHAR);//爱心基金支付
		aliasTypeMap.put("IF_PAYMENT_LOVE_FUND", this.VARCHAR);//是否支付爱心基金
		aliasTypeMap.put("IF_PAYMENT_RENT", this.VARCHAR);//负担房租标志:E->个人租房;C->公司租房
		aliasTypeMap.put("IF_PAYMENT_MEDICAL", this.VARCHAR);//负担医疗费标志
		aliasTypeMap.put("IF_PAYMENT_EDUCATION", this.VARCHAR);//负担教育费标志

		aliasValueMap.put("UPDATED_BY", adminId);
		aliasValueMap.put("UPDATE_DATE", "SYSDATE");		
		aliasTypeMap.put("UPDATED_BY", this.VARCHAR);
		aliasTypeMap.put("UPDATE_DATE", this.SYSDATE);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("HR_EMP_PA_INFO",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("aliasNullStr", aliasNullStr);
		map.put("whereMap", whereMap);
		return map;
	}
	
	/**
	 * 组装黑色档案信息导入参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 */
	private String composeBadArchives(LinkedHashMap aliasValueMap, LinkedHashMap aliasTypeMap, LinkedHashMap aliasValueI18nMap
			, List validateCell, List validateCellName){
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("ID", "hr_bad_archives_seq.NEXTVAL");
		String PERSON_ID="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueMap.put("PERSON_ID", PERSON_ID);
		aliasValueMap.put("HAPPEN_DATE", "#CELL2#");
		String ARCHIVES_TYPE="SELECT CODE_NO ARCHIVES_TYPE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=125239 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL3#"+"'" ;
		aliasValueMap.put("ARCHIVES_TYPE", ARCHIVES_TYPE);
		aliasValueMap.put("DETAIL_DESCRIPT", "#CELL4#");
		aliasValueMap.put("REMARK", "#CELL5#");
		
		//第三步，指定每列的类型
		aliasTypeMap.put("ID", this.NUMBER);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("HAPPEN_DATE", this.DATE);
		aliasTypeMap.put("ARCHIVES_TYPE", this.VARCHAR);
		aliasTypeMap.put("DETAIL_DESCRIPT", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.DATE);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		//传入需要验证列的名称 按照excel顺序传入
		validateCellName.add("社号(必填),1,0,hr_employee");
		validateCellName.add("员工姓名(可为空),-1");
		validateCellName.add("发生日期,0");
		validateCellName.add("类型,1,1,125239");
		validateCellName.add("详细描述,-1");
		validateCellName.add("备注,-1");
		
		return "HR_BAD_ARCHIVES";
	}
	
	/**
	 * 组装紧急联系人信息导入参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 */
	private String composeContacts(LinkedHashMap aliasValueMap, LinkedHashMap aliasTypeMap, LinkedHashMap aliasValueI18nMap
			, List validateCell, List validateCellName){
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("FAMILY_NO", "hr_family_seq.NEXTVAL");
		String PERSON_ID="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueMap.put("PERSON_ID", PERSON_ID);
		String FAM_TYPE_CODE="SELECT CODE_NO FAM_TYPE_CODE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=1693 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL2#"+"'" ;
		aliasValueMap.put("FAM_TYPE_CODE", FAM_TYPE_CODE);
		aliasValueMap.put("FAM_NAME", "#CELL3#");
		aliasValueMap.put("FAM_PHONE", "#CELL4#");
		
		//第三步，指定每列的类型
		aliasTypeMap.put("FAMILY_NO", this.NUMBER);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("FAM_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("FAM_NAME", this.VARCHAR);
		aliasTypeMap.put("FAM_PHONE", this.VARCHAR);
		
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		//传入需要验证列的名称 按照excel顺序传入
		validateCellName.add("社号(必填),1,0,hr_employee");
		validateCellName.add("员工姓名(可为空),-1");
		validateCellName.add("关系,1,1,1693");
		validateCellName.add("姓名,-1");
		validateCellName.add("联系电话,-1");
		
		return "HR_FAMILY";
	}
	
	/**
	 * 组装残疾信息导入参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 */
	private String composeDisable(LinkedHashMap aliasValueMap, LinkedHashMap aliasTypeMap, LinkedHashMap aliasValueI18nMap
			, List validateCell, List validateCellName){
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("T_ID", "HR_TRADEUNION_SEQ.NEXTVAL");
		String PERSON_ID="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueMap.put("PERSON_ID", PERSON_ID);
		String DISABILITY_TYPE=" SELECT CODE_NO DISABILITY_TYPE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123264 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL2#"+"'" ;
		aliasValueMap.put("DISABILITY_TYPE", DISABILITY_TYPE);
		aliasValueMap.put("ADDDATE", "#CELL3#");
		String DISABILITY_VALIDITY=" SELECT CODE_NO DISABILITY_VALIDITY FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123462 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL4#"+"'" ;
		aliasValueMap.put("DISABILITY_VALIDITY", DISABILITY_VALIDITY);
		aliasValueMap.put("REMARK", "#CELL5#");
		aliasValueMap.put("CPNY_ID", this.pathCpnyID);
		
		//第三步，指定每列的类型
		aliasTypeMap.put("T_ID", this.NUMBER);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("DISABILITY_TYPE", this.VARCHAR);
		aliasTypeMap.put("ADDDATE", this.DATE);
		aliasTypeMap.put("DISABILITY_VALIDITY", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);

		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.DATE);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		//传入需要验证列的名称 按照excel顺序传入
		validateCellName.add("社号(必填),1,0,hr_employee");
		validateCellName.add("员工姓名(可为空),-1");
		validateCellName.add("残疾类型,1,1,123264");
		validateCellName.add("签发日期,-1");
		validateCellName.add("有效期,1,1,123462");
		validateCellName.add("备注,-1");
		
		return "HR_DISABILITYINFO";
	}
	
	/**
	 * 组装工会信息导入参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 */
	private String composeLabourUnion(LinkedHashMap aliasValueMap, LinkedHashMap aliasTypeMap, LinkedHashMap aliasValueI18nMap
			, List validateCell, List validateCellName){
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("T_ID", "HR_TRADEUNION_SEQ.NEXTVAL");
		String USER_ID="	SELECT PERSON_ID USER_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueMap.put("USER_ID", USER_ID);
		String RESPONSIBITITY=" SELECT CODE_NO RESPONSIBITITY FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123251 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL2#"+"'" ;
		aliasValueMap.put("RESPONSIBITITY", RESPONSIBITITY);
		aliasValueMap.put("ADDDATE", "#CELL3#");
		aliasValueMap.put("QUITDATE", "#CELL4#");
		String PAY_FLAG=" SELECT CODE_NO PAY_FLAG FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123224 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL5#"+"'" ;
		aliasValueMap.put("PAY_FLAG", PAY_FLAG);
		String PAY_TYPE=" SELECT CODE_NO PAY_TYPE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=211654 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL6#"+"'" ;
		aliasValueMap.put("PAY_TYPE", PAY_TYPE);
		aliasValueMap.put("REMARK", "#CELL7#");
		
		//第三步，指定每列的类型
		aliasTypeMap.put("T_ID", this.NUMBER);
		aliasTypeMap.put("USER_ID", this.VARCHAR);
		aliasTypeMap.put("RESPONSIBITITY", this.VARCHAR);
		aliasTypeMap.put("ADDDATE", this.DATE);
		aliasTypeMap.put("QUITDATE", this.DATE);
		aliasTypeMap.put("PAY_FLAG", this.VARCHAR);
		aliasTypeMap.put("PAY_TYPE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);

		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.DATE);
		validateCell.add(this.DATE);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		//传入需要验证列的名称 按照excel顺序传入
		validateCellName.add("社号(必填),1,0,hr_employee");
		validateCellName.add("员工姓名(可为空),-1");
		validateCellName.add("职责,1,1,123251");
		validateCellName.add("起始日期,-1");
		validateCellName.add("终止日期,-1");
		validateCellName.add("会费支付状态,1,1,123224");
		validateCellName.add("支付方式,1,1,211654");
		validateCellName.add("备注,-1");
		
		return "HR_TRADEUNION";
	}
	
	/**
	 * 组装评价信息导入参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 */
	private String composeEvsInfo(LinkedHashMap aliasValueMap, LinkedHashMap aliasTypeMap, LinkedHashMap aliasValueI18nMap
			, List validateCell, List validateCellName){
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("EV_PERIOD", "#CELL2#");
		String PERSON_ID="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueMap.put("PERSON_ID", PERSON_ID);
		aliasValueMap.put("EV_ACHI", "#CELL3#");
		aliasValueMap.put("EV_ATTI", "#CELL4#");
		aliasValueMap.put("EV_ABIL", "#CELL5#");
		aliasValueMap.put("EV_MARK", "#CELL6#");
		String EV_GRADE=" SELECT CODE_NO EV_GRADE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=3538 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL7#"+"'" ;
		aliasValueMap.put("EV_GRADE", EV_GRADE);
		aliasValueMap.put("SUGGESTION", "#CELL8#");
		aliasValueMap.put("FINAL_SEQUENCE", "#CELL9#");
		aliasValueMap.put("TOTAL_PEOPLE", "#CELL10#");
		aliasValueMap.put("EV_REMARK", "#CELL11#");
		
		//第三步，指定每列的类型
		aliasTypeMap.put("EV_PERIOD", this.VARCHAR);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("EV_ACHI", this.NUMBER);
		aliasTypeMap.put("EV_ATTI", this.NUMBER);
		aliasTypeMap.put("EV_ABIL", this.NUMBER);
		aliasTypeMap.put("EV_MARK", this.NUMBER);
		aliasTypeMap.put("EV_GRADE", this.VARCHAR);
		aliasTypeMap.put("SUGGESTION", this.VARCHAR);
		aliasTypeMap.put("FINAL_SEQUENCE", this.VARCHAR);
		aliasTypeMap.put("TOTAL_PEOPLE", this.VARCHAR);
		aliasTypeMap.put("EV_REMARK", this.VARCHAR);

		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.NUMBER);
		validateCell.add(this.NUMBER);
		validateCell.add(this.NUMBER);
		validateCell.add(this.NUMBER);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		//传入需要验证列的名称 按照excel顺序传入
		validateCellName.add("社号(必填),1,0,hr_employee");
		validateCellName.add("员工姓名(可为空),-1");
		validateCellName.add("评价期间,-1");
		validateCellName.add("绩效,-1");
		validateCellName.add("态度,-1");
		validateCellName.add("能力,-1");
		validateCellName.add("评价分数,-1");
		validateCellName.add("评价等级,1,1,3538");
		validateCellName.add("意见,-1");
		validateCellName.add("最终顺位,-1");
		validateCellName.add("总评价人员数,-1");
		validateCellName.add("备注,-1");
		
		return "HR_EVS_INFO";
	}
	
	/**
	 * 组装辅培训信息导入参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 */
	private String composeTraining(LinkedHashMap aliasValueMap, LinkedHashMap aliasTypeMap, LinkedHashMap aliasValueI18nMap
			, List validateCell, List validateCellName){
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("TRAIN_NO", "HR_TRANS_NO_SEQ.NEXTVAL");
		String PERSON_ID="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueMap.put("PERSON_ID", PERSON_ID);
		aliasValueMap.put("COURSE_NAME", "#CELL2#");
		String MUST_CODE=" SELECT CODE_NO MUST_CODE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123376 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL3#"+"'" ;
		aliasValueMap.put("MUST_CODE", MUST_CODE);
		String TRAINING_DIFFERENTIATE=" SELECT CODE_NO TRAINING_DIFFERENTIATE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123459 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL4#"+"'" ;
		aliasValueMap.put("TRAINING_DIFFERENTIATE", TRAINING_DIFFERENTIATE);
		aliasValueMap.put("START_DATE", "#CELL5#");
		aliasValueMap.put("END_DATE", "#CELL6#");
		aliasValueMap.put("INSTITUTION_NAME", "#CELL7#");
		String TRAINING_METHOD=" SELECT CODE_NO TRAINING_METHOD FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123271 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL8#"+"'" ;
		aliasValueMap.put("TRAINING_METHOD", TRAINING_METHOD);
		aliasValueMap.put("TRAINING_TIME", "#CELL9#");
		aliasValueMap.put("TRAINING_RESULT", "#CELL10#");
		aliasValueMap.put("REMARKS", "#CELL11#");
		
		//第三步，指定每列的类型
		aliasTypeMap.put("TRAIN_NO", this.NUMBER);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("COURSE_NAME", this.VARCHAR);
		aliasTypeMap.put("MUST_CODE", this.VARCHAR);
		aliasTypeMap.put("TRAINING_DIFFERENTIATE", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.DATE);
		aliasTypeMap.put("END_DATE", this.DATE);
		aliasTypeMap.put("INSTITUTION_NAME", this.VARCHAR);
		aliasTypeMap.put("TRAINING_METHOD", this.VARCHAR);
		aliasTypeMap.put("TRAINING_TIME", this.VARCHAR);
		aliasTypeMap.put("TRAINING_RESULT", this.VARCHAR);
		aliasTypeMap.put("REMARKS", this.VARCHAR);
		
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.DATE);
		validateCell.add(this.DATE);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		//传入需要验证列的名称 按照excel顺序传入
		validateCellName.add("社号(必填),1,0,hr_employee");
		validateCellName.add("员工姓名(可为空),-1");
		validateCellName.add("培训名称,-1");
		validateCellName.add("选修/必修 ,1,1,123376");
		validateCellName.add("培训区分,1,1,123459");
		validateCellName.add("起始日期,-1");
		validateCellName.add("终止日期,-1");
		validateCellName.add("培训机构,-1");
		validateCellName.add("培训方法,1,1,123271");
		validateCellName.add("培训时间,-1");
		validateCellName.add("培训结果,-1");
		validateCellName.add("备注,-1");
		
		return "HR_TRAINING_INFO";
	}
	
	/**
	 * 组装工作经历信息导入参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 *//*
	private String composeWorkExperience(LinkedHashMap aliasValueMap, LinkedHashMap aliasTypeMap, LinkedHashMap aliasValueI18nMap
			, List validateCell, List validateCellName){
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("WORK_EXPER_NO", "HR_W_EXP_SEQ.NEXTVAL");
		String PERSON_ID="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueMap.put("PERSON_ID", PERSON_ID);
		aliasValueMap.put("START_DATE", "#CELL2#");
		aliasValueMap.put("END_DATE", "#CELL3#");
		aliasValueMap.put("CPNY_NAME", "#CELL4#");
		aliasValueMap.put("DEPT_NAME", "#CELL5#");
		aliasValueMap.put("POSITION", "#CELL6#");
		aliasValueMap.put("DUTY", "#CELL7#");
		aliasValueMap.put("PAYROLL", "#CELL8#");
		
		//第三步，指定每列的类型
		aliasTypeMap.put("WORK_EXPER_NO", this.NUMBER);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.DATE);
		aliasTypeMap.put("END_DATE", this.DATE);
		aliasTypeMap.put("CPNY_NAME", this.VARCHAR);
		aliasTypeMap.put("DEPT_NAME", this.VARCHAR);
		aliasTypeMap.put("POSITION", this.VARCHAR);
		aliasTypeMap.put("DUTY", this.VARCHAR);
		aliasTypeMap.put("PAYROLL", this.VARCHAR);

		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.DATE);
		validateCell.add(this.DATE);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		//传入需要验证列的名称 按照excel顺序传入
		validateCellName.add("社号(必填),1,0,hr_employee");
		validateCellName.add("员工姓名(可为空),-1");
		validateCellName.add("开始日期,-1");
		validateCellName.add("结束日期 ,-1");
		validateCellName.add("工作单位,-1");
		validateCellName.add("部门,-1");
		validateCellName.add("职位,-1");
		validateCellName.add("职级,-1");
		validateCellName.add("工资待遇,-1");
		
		return "HR_WORK_EXPERIENCE";
	}
	*/
	
	/**
	 * 组装资格证信息导入参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 */
	/*private String composeQualification(LinkedHashMap aliasValueMap, LinkedHashMap aliasTypeMap, LinkedHashMap aliasValueI18nMap){
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("QUAL_NO", "HR_W_EXP_SEQ.NEXTVAL");
		String PERSON_ID="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueMap.put("PERSON_ID", PERSON_ID);
		aliasValueMap.put("QUAL_NAME", "#CELL2#");
		String QUAL_LEVEL=" SELECT CODE_NO QUAL_LEVEL FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=14910 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL3#"+"'" ;
		aliasValueMap.put("QUAL_LEVEL", QUAL_LEVEL);
		String QUAL_GRADE=" SELECT CODE_NO QUAL_GRADE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=123485 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL4#"+"'" ;
		aliasValueMap.put("QUAL_GRADE", QUAL_GRADE);
		aliasValueMap.put("QUAL_INSTITUTE", "#CELL5#");
		aliasValueMap.put("DATE_OBTAINED", "#CELL6#");
		aliasValueMap.put("QUAL_REMARK", "#CELL7#");
		
		//第三步，指定每列的类型
		aliasTypeMap.put("QUAL_NO", this.NUMBER);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("QUAL_NAME", this.VARCHAR);//资格证名称
		aliasTypeMap.put("QUAL_LEVEL", this.VARCHAR);//证件级别
		aliasTypeMap.put("QUAL_GRADE", this.VARCHAR);//职称
		aliasTypeMap.put("QUAL_INSTITUTE", this.VARCHAR);//颁发机构
		aliasTypeMap.put("DATE_OBTAINED", this.DATE);//颁发日期
		aliasTypeMap.put("QUAL_REMARK", this.VARCHAR);//津贴标准
		
		return "HR_QUALIFICATION";
	}*/
	
	/**
	 * 组装外国语导入参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 */
	private String composeLanguage(LinkedHashMap aliasValueMap, LinkedHashMap aliasTypeMap, LinkedHashMap aliasValueI18nMap
			, List validateCell, List validateCellName){
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("LANGUAGE_NO", "HR_W_EXP_SEQ.NEXTVAL");
		String PERSON_ID="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueMap.put("PERSON_ID", PERSON_ID);
		aliasValueMap.put("KAOSHIDATE", "#CELL2#");
		String EXAM_NAME_CODE=" SELECT CODE_NO EXAM_NAME_CODE FROM HRM_TEMP_LANG_EVENT_TEMP SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=1394 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL3#"+"'" ;
		aliasValueMap.put("EXAM_NAME_CODE", EXAM_NAME_CODE);
		String LANGUAGE_LEVEL_CODE=" SELECT CODE_NO LANGUAGE_LEVEL_CODE FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO=1401 AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='zh' AND SY.CONTENT = '"+"#CELL4#"+"'" ;
		aliasValueMap.put("LANGUAGE_LEVEL_CODE", LANGUAGE_LEVEL_CODE);
		aliasValueMap.put("MARK", "#CELL5#");
		aliasValueMap.put("ALLWANCE", "#CELL6#");
		
		//第三步，指定每列的类型
		aliasTypeMap.put("LANGUAGE_NO", this.NUMBER);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("KAOSHIDATE", this.DATE);
		aliasTypeMap.put("EXAM_NAME_CODE", this.VARCHAR);
		aliasTypeMap.put("LANGUAGE_LEVEL_CODE", this.VARCHAR);
		aliasTypeMap.put("MARK", this.VARCHAR);
		aliasTypeMap.put("ALLWANCE", this.VARCHAR);
		

		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.DATE);
		validateCell.add(this.DATE);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		//传入需要验证列的名称 按照excel顺序传入
		validateCellName.add("社号(必填),1,0,hr_employee");
		validateCellName.add("员工姓名(可为空),-1");
		validateCellName.add("证书颁发日期,-1");
		validateCellName.add("考试名 ,1,1,1394");
		validateCellName.add("等级,1,1,1401");
		validateCellName.add("分数,-1");
		validateCellName.add("津贴标准（金额）,-1");
		
		return "HR_LANGUAGE_LEVEL";
	}
	
	
	/**
	 * 组装辅助信息导入参数
	 * @param aliasValueMap
	 * @param aliasTypeMap
	 * @param aliasValueI18nMap
	 * @author weizhengchen
	 * @return
	 */
	private String composeAssist(LinkedHashMap aliasValueMap, LinkedHashMap aliasTypeMap, LinkedHashMap aliasValueI18nMap
			, List validateCell, List validateCellName){
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		aliasValueMap.put("ASSIST_NO", "HR_ASSIST_INFO_SEQ.NEXTVAL");
		String PERSON_ID="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim('#CELL0#',' '),' ') AND CPNY_ID='"+this.pathCpnyID+"' "; 
		aliasValueMap.put("PERSON_ID", PERSON_ID);
		aliasValueMap.put("TITLE", "#CELL2#");
		aliasValueMap.put("CONTENT", "#CELL3#");
		
		//第三步，指定每列的类型
		aliasTypeMap.put("ASSIST_NO", this.NUMBER);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("TITLE", this.VARCHAR);
		aliasTypeMap.put("CONTENT", this.VARCHAR);
		
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.DATE);
		validateCell.add(this.DATE);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		validateCell.add(this.VARCHAR);
		//传入需要验证列的名称 按照excel顺序传入
		validateCellName.add("社号(必填),1,0,hr_employee");
		validateCellName.add("员工姓名(可为空),-1");
		validateCellName.add("标题,-1");
		validateCellName.add("内容 ,-1");
		
		return "HR_ASSIST_INFO";
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）--职责津贴标准个人
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPaAllowanceSelf")
	public ModelAndView importPaAllowanceSelf(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		String paramNo=request.getParameter("id");
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL0#");
		//aliasValueMap.put("POSITION_ALLOWANCE", "#CELL2#");
		aliasValueMap.put("PERCENT_ALLOWANCE", "#CELL1#");
		//aliasValueMap.put("START_DATE", "#CELL4#");
		aliasValueMap.put("VALID_MONTH", "#CELL2#");
		aliasValueMap.put("DEMO_ALLOWANCE", "#CELL3#");
		
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("CHANGE_DATE","SYSDATE");
		aliasValueMap.put("UPLOAD_BY",admin.getPersonId());
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("EMPID", this.VARCHAR);
		//aliasTypeMap.put("POSITION_ALLOWANCE", this.NUMBER);
		aliasTypeMap.put("PERCENT_ALLOWANCE", this.NUMBER);
		//aliasTypeMap.put("START_DATE", this.VARCHAR);
		aliasTypeMap.put("VALID_MONTH", this.NUMBER);
		aliasTypeMap.put("DEMO_ALLOWANCE", this.VARCHAR);
		
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("CHANGE_DATE",this.SYSDATE);
		aliasTypeMap.put("UPLOAD_BY",this.VARCHAR);
		//第四步 可以为空的列 
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();

		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		aliasValueAppendMap.put("appendField", "");
		aliasValueAppendMap.put("appendValue", "");
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_ALLOWANCE_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/pa/wagebase/viewImportPaAllowanceExcelSelfList";
		String navTabId = "pa0221";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）--工资计算对象
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	//@RequestMapping(value = "/importPaAccount")
	public ModelAndView importPaAccout(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("CHINESE_NAME", "#CELL1#");
		aliasValueMap.put("CALC_FLAG", "#CELL2#");
		aliasValueMap.put("UPDATE_REMARK", "#CELL3#");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("CHINESE_NAME", this.VARCHAR);
		aliasTypeMap.put("CALC_FLAG", this.VARCHAR);
		aliasTypeMap.put("UPDATE_REMARK", this.VARCHAR);
		//第四步 可以为空的列 
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("PA_ACCOUNT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/pa/wagebase/viewImportPaAccountExcelList";
		String navTabId = "pa0519";	
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	
	/**
	 * 导入数据 （调用时需要重写的方法）--职责津贴标准
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPaAllowance")
	public ModelAndView importPaAllowance(HttpServletRequest request,
		HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		String paramNo=request.getParameter("id");
		//第二步，指定excel里要插入的列，以及默认要插入的列的值
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("CPNY_NAME", "#CELL0#");
		aliasValueMap.put("DUTY_ALLOWANCE", "#CELL1#");
		aliasValueMap.put("TYPE_ALLOWANCE", "#CELL2#");
		aliasValueMap.put("DEPT_ID", "#CELL3#");
		aliasValueMap.put("POSITION_ALLOWANCE", "#CELL4#");
		
		aliasValueMap.put("ACTIVITY", 1);
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		aliasValueMap.put("UPLOAD_BY",admin.getPersonId());
		aliasValueMap.put("ALLOWANCE_ID", "HR_ALLOWANCE_GROUP_TEMP_SEQ.NEXTVAL");
		
		//第三步，指定每列的类型
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("CPNY_NAME", this.VARCHAR);
		aliasTypeMap.put("DUTY_ALLOWANCE", this.VARCHAR);
		aliasTypeMap.put("TYPE_ALLOWANCE", this.VARCHAR);
		aliasTypeMap.put("DEPT_ID", this.VARCHAR);
		aliasTypeMap.put("POSITION_ALLOWANCE", this.NUMBER);
		
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		aliasTypeMap.put("UPLOAD_BY",this.VARCHAR);
		aliasTypeMap.put("ALLOWANCE_ID", this.NUMBER);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		aliasValueAppendMap.put("appendField", "");
		aliasValueAppendMap.put("appendValue", "");
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_ALLOWANCE_GROUP_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/pa/wagebase/viewImportPaAllowanceExcelTempList";
		String navTabId = "pa0220";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  （营业员提成计算数据导入）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importExcelSalesIncCalcData")
	public ModelAndView importExcelSalesIncCalcData(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("EMPNO"		, "#CELL0#");
		aliasValueMap.put("INCTV_MON"	, "#CELL1#");
		aliasValueMap.put("ADJST_AMT"	, "#CELL2#");
		aliasValueMap.put("REMARK"		, "#CELL3#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPNO"			, this.VARCHAR);
		aliasTypeMap.put("INCTV_MON"		, this.VARCHAR);
		aliasTypeMap.put("ADJST_AMT"		, this.NUMBER);
		aliasTypeMap.put("REMARK"			, this.VARCHAR);
		
		//模板外的其它字段设置		
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",SUBSD_CD,UPDT_USER,UPDT_DTIME,INCTV_CALC_EXCEL_IMP_SEQ,IMP_EMPNO,IMP_DATE,RGST_DTIME";
		String appendValue=",'" + admin.getCpnyId()	+ "','" 
						  + admin.getEmpID()	
						  + "',sysdate,INC_INCTV_CALC_EXCEL_IMP_SEQ.NEXTVAL,'"
						  + admin.getEmpID() + "',to_char(sysdate,'yyyymmdd'),sysdate"
						  ; 
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("INC_INCTV_CALC_EXCEL_IMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		this.excelUtilSer.importExcelSalesInctData(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据 （调用时需要重写的方法）--费用申请导入
	 * Description: 
	 * defaultValues
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importEssApplication")
	public ModelAndView importEssApplication(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)throws Exception{
			AdminBean admin=SessionUtil.getLoginUserFromSession(request);
			//this.pathCpnyID=admin.getCpnyId();
			//String paramNo=request.getParameter("id");
			//第二步，指定excel里要插入的列，以及默认要插入的列的值
			LinkedHashMap aliasValueMap = new LinkedHashMap();
			aliasValueMap.put("COSTEMP","#CELL0#");
			aliasValueMap.put("START_DATE","#CELL1#");
			aliasValueMap.put("END_DATE","#CELL2#");
			aliasValueMap.put("MONEY","#CELL3#");
			aliasValueMap.put("TYPENAME","#CELL4#");
			aliasValueMap.put("DEMO","#CELL5#");
			
			aliasValueMap.put("ACTIVITY", "1");
			aliasValueMap.put("CREATE_DATE","SYSDATE");
			aliasValueMap.put("UPLOAD_BY",admin.getPersonId());
			aliasValueMap.put("REALSTATE","Y");
			aliasValueMap.put("CPNY_ID",admin.getCpnyId());
			
			//第三步，指定每列的类型
			LinkedHashMap aliasTypeMap = new LinkedHashMap();
			aliasTypeMap.put("COSTEMP", this.VARCHAR);
			aliasTypeMap.put("START_DATE", this.VARCHAR);
			aliasTypeMap.put("END_DATE", this.VARCHAR);
			aliasTypeMap.put("MONEY", this.VARCHAR);
			aliasTypeMap.put("TYPENAME", this.VARCHAR);
			aliasTypeMap.put("DEMO", this.VARCHAR);
			
			aliasTypeMap.put("ACTIVITY", this.VARCHAR);
			aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
			aliasTypeMap.put("UPLOAD_BY",this.VARCHAR);
			aliasTypeMap.put("REALSTATE",this.VARCHAR);
			aliasTypeMap.put("CPNY_ID",this.VARCHAR);
			
			//模板外的其它字段设置
			LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
			aliasValueAppendMap.put("appendField", "");
			aliasValueAppendMap.put("appendValue", "");
			
			LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
			LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("ESS_WAGEAPPLI_DETAIL_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
			String forwardUrl = "/ess/wageApplication/viewWageApplicationTempList";
			String navTabId = "ess333";
			this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
			return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}

	/**
	 * 导入数据  P加班数据
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importPOtApplyTemp")
	public ModelAndView importPOtApplyTemp(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("APPLY_NO", "ESS_APPLY_OT_TEMP_SEQ.NEXTVAL");
		aliasValueMap.put("PERSON_ID", "#CELL0#");
		aliasValueMap.put("OT_TYPE_NO", "31");
		aliasValueMap.put("OT_TYPE_CODE", "#CELL6#");
		aliasValueMap.put("OT_TIME_TYPE", "P");
		
		aliasValueMap.put("APPLY_OT_DATE", "#CELL2#");
		aliasValueMap.put("BEGIN_DAY_OFFSET", "0");
		aliasValueMap.put("OT_FROM_TIME", "#CELL2#");
		aliasValueMap.put("FROM_TIME", "#CELL3#");
		aliasValueMap.put("END_DAY_OFFSET", "0");
		aliasValueMap.put("OT_TO_TIME", "#CELL4#");
		aliasValueMap.put("TO_TIME", "#CELL5#");
		aliasValueMap.put("OT_DEDUCT_TIME", "0");
		
		aliasValueMap.put("OT_APPLY_HOUR", "0");
		aliasValueMap.put("OT_APPLY_MINUTE", "0");
		aliasValueMap.put("ADJUST_YN", "#CELL7#");
		aliasValueMap.put("TESHU_YN", "#CELL8#");
		aliasValueMap.put("OT_PLACE_TYPE", "#CELL9#");
		aliasValueMap.put("APPLY_OT_REMARK", "#CELL10#");
		aliasValueMap.put("APPLY_TYPE", "BATCH");
		aliasValueMap.put("CHECK_FLAG", "0");
		
		aliasValueMap.put("CREATE_DATE","SYSDATE");
	//	aliasValueMap.put("CREATED_BY",admin.getPersonId());
	//	aliasValueMap.put("UPLOAD_BY",admin.getPersonId());

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("APPLY_NO", this.NUMBER);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("OT_TYPE_NO", this.VARCHAR);
		aliasTypeMap.put("OT_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("OT_TIME_TYPE", this.VARCHAR);
		
		aliasTypeMap.put("APPLY_OT_DATE", this.DATE_HMS);
		aliasTypeMap.put("BEGIN_DAY_OFFSET", this.NUMBER);
		aliasTypeMap.put("OT_FROM_TIME", this.DATE_HMS);
		aliasTypeMap.put("FROM_TIME", this.VARCHAR);
		aliasTypeMap.put("END_DAY_OFFSET", this.NUMBER);
		aliasTypeMap.put("OT_TO_TIME", this.DATE_HMS);
		aliasTypeMap.put("TO_TIME",  this.VARCHAR);
		aliasTypeMap.put("OT_DEDUCT_TIME", this.NUMBER);
		
		aliasTypeMap.put("OT_APPLY_HOUR", this.NUMBER);
		aliasTypeMap.put("OT_APPLY_MINUTE", this.NUMBER);
		aliasTypeMap.put("ADJUST_YN", this.NUMBER);
		aliasTypeMap.put("TESHU_YN", this.NUMBER);
		aliasTypeMap.put("OT_PLACE_TYPE", this.VARCHAR);
		aliasTypeMap.put("APPLY_OT_REMARK", this.VARCHAR);
		aliasTypeMap.put("APPLY_TYPE", this.VARCHAR);
		aliasTypeMap.put("CHECK_FLAG", this.NUMBER);
		
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
	//	aliasTypeMap.put("CREATED_BY",this.VARCHAR);
	//	aliasTypeMap.put("UPLOAD_BY",this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CREATED_BY,UPLOAD_BY";
		String appendValue=",'" + admin.getPersonId()	+ "','" + admin.getPersonId()	+ "'";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
	 	LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String aliasNullStr = "";
	//	LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("ESS_APPLY_OT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		 
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("ESS_APPLY_OT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		map.put("aliasNullStr", aliasNullStr);
	 //	String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim(#CELL0#,' '),' ') AND CPNY_ID='"+admin.getCpnyId()+"' "; 
	 //	aliasValueI18nMap.put("PERSON_ID", "#CELL0#");
		
		String forwardUrl = "/ess/infoApply/viewPOtImportList?pageNum=1";
		String navTabId = "ess0213";
		//this.infoApplySer.cancelOvertimeApplyImport(request);//先删除创建者操作的加班临时表的残留数据
	//	this.excelUtilSer.importOtData(request,response,map,modelMap,forwardUrl,navTabId);
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  L加班数据
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importLOtApplyTemp")
	public ModelAndView importLOtApplyTemp(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("APPLY_NO", "ESS_APPLY_OT_TEMP_SEQ.NEXTVAL");
		aliasValueMap.put("PERSON_ID", "#CELL0#");
		aliasValueMap.put("OT_TYPE_NO", "31");
		aliasValueMap.put("OT_TYPE_CODE", "#CELL5#");
		aliasValueMap.put("OT_TIME_TYPE", "L");
		
		aliasValueMap.put("APPLY_OT_DATE", "#CELL2#");
		aliasValueMap.put("OT_APPLY_HOUR", "#CELL3#");
		aliasValueMap.put("OT_APPLY_MINUTE", "#CELL4#");
		
		aliasValueMap.put("ADJUST_YN", "#CELL6#");
		aliasValueMap.put("OT_PLACE_TYPE", "#CELL7#");
		aliasValueMap.put("APPLY_OT_REMARK", "#CELL8#");
		aliasValueMap.put("APPLY_TYPE", "BATCH");
		aliasValueMap.put("CHECK_FLAG", "0");
		
		aliasValueMap.put("CREATE_DATE","SYSDATE");
		//aliasValueMap.put("CREATED_BY",admin.getPersonId());
		//aliasValueMap.put("UPLOAD_BY",admin.getPersonId());

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("APPLY_NO", this.NUMBER);
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		
		aliasTypeMap.put("OT_TYPE_NO", this.VARCHAR);
		aliasTypeMap.put("OT_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("OT_TIME_TYPE", this.VARCHAR);
		
		aliasTypeMap.put("APPLY_OT_DATE", this.DATE);
		aliasTypeMap.put("OT_APPLY_HOUR", this.NUMBER);
		aliasTypeMap.put("OT_APPLY_MINUTE", this.NUMBER);
		
		aliasTypeMap.put("ADJUST_YN", this.NUMBER);
		aliasTypeMap.put("OT_PLACE_TYPE", this.VARCHAR);
		aliasTypeMap.put("APPLY_OT_REMARK", this.VARCHAR);
		
		aliasTypeMap.put("APPLY_TYPE", this.VARCHAR);
		aliasTypeMap.put("CHECK_FLAG", this.NUMBER);
		
		aliasTypeMap.put("CREATE_DATE",this.SYSDATE);
		//aliasTypeMap.put("CREATED_BY",this.VARCHAR);
		//aliasTypeMap.put("UPLOAD_BY",this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CREATED_BY,UPLOAD_BY";
		String appendValue=",'" + admin.getPersonId()	+ "','" + admin.getPersonId()	+ "'";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String aliasNullStr = "";
	//	LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("ESS_APPLY_OT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
	
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("ESS_APPLY_OT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		map.put("aliasNullStr", aliasNullStr);
	//	String sqlI18nContent="	SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID=ltrim(rtrim(#CELL0#,' '),' ') AND CPNY_ID='"+admin.getCpnyId()+"' "; 
	//	aliasValueI18nMap.put("PERSON_ID", sqlI18nContent);
		//导入之后跳转到指定页面进行编辑
		String forwardUrl = "/ess/infoApply/viewLOtImportList";
		String navTabId = "ess0231";
	//	this.excelUtilSer.importOtData(request,response,map,modelMap,forwardUrl,navTabId);
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  leave
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importLeaveTemp")
	public ModelAndView importLeaveTemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("APPLY_NAME", "#CELL1#");
		aliasValueMap.put("LEAVE_FROM_TIME","#CELL2#");
		aliasValueMap.put("LEAVE_FROM_SUB",  "#CELL3#");
		aliasValueMap.put("LEAVE_TO_TIME", "#CELL4#");
		aliasValueMap.put("LEAVE_TO_SUB", "#CELL5#");
		aliasValueMap.put("LEAVE_TYPE", "#CELL6#");
		aliasValueMap.put("LEAVE_REASON", "#CELL7#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("APPLY_NAME", this.VARCHAR);
		aliasTypeMap.put("LEAVE_FROM_TIME", this.DATE);
		aliasTypeMap.put("LEAVE_FROM_SUB", this.VARCHAR);
		aliasTypeMap.put("LEAVE_TO_TIME", this.DATE);
		aliasTypeMap.put("LEAVE_TO_SUB", this.VARCHAR);
		aliasTypeMap.put("LEAVE_TYPE", this.VARCHAR);
		aliasTypeMap.put("LEAVE_REASON", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("ESS_LEAVE_APPLY_TB_BATCH_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		String navTabId = request.getParameter("LEAVE_TYPE");
		String forwardUrl = "/ess/infoApplyLeave/viewImportExcelEssLeaveDataList?LEAVE_TYPE=" + navTabId; 
		modelMap.put("LEAVE_TYPE", navTabId);
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  leave
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importLeaveTempess0240")
	public ModelAndView importLeaveTempess0240(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("APPLY_NAME", "#CELL1#");
		aliasValueMap.put("LEAVE_FROM_TIME","#CELL2#");
		aliasValueMap.put("LEAVE_FROM_SUB",  "#CELL3#");
		aliasValueMap.put("LEAVE_TO_TIME", "#CELL4#");
		aliasValueMap.put("LEAVE_TO_SUB", "#CELL5#");
		aliasValueMap.put("LEAVE_TYPE", "#CELL6#");
		aliasValueMap.put("LEAVE_REASON", "#CELL7#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("APPLY_NAME", this.VARCHAR);
		aliasTypeMap.put("LEAVE_FROM_TIME", this.DATE);
		aliasTypeMap.put("LEAVE_FROM_SUB", this.VARCHAR);
		aliasTypeMap.put("LEAVE_TO_TIME", this.DATE);
		aliasTypeMap.put("LEAVE_TO_SUB", this.VARCHAR);
		aliasTypeMap.put("LEAVE_TYPE", this.VARCHAR);
		aliasTypeMap.put("LEAVE_REASON", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("ESS_LEAVE_APPLY_TB_BATCH_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		String navTabId = request.getParameter("LEAVE_TYPE");
		String forwardUrl = "/ess/infoApplyLeave/viewImportExcelEssLeaveDataListess0240?LEAVE_TYPE=" + navTabId; 
		modelMap.put("LEAVE_TYPE", navTabId);
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  （临促工资数据导入）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importExcelTempSalesData")
	public ModelAndView importExcelTempSalesData(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();

		aliasValueMap.put("EVENT_DEPTNO", "#CELL0#");
		aliasValueMap.put("START_DATE", "#CELL1#");
		aliasValueMap.put("END_DATE", "#CELL2#");
		aliasValueMap.put("PAY_DATE", "#CELL3#");
		aliasValueMap.put("EVENT_CONTENT", "#CELL4#");
		aliasValueMap.put("REMARK", "#CELL5#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EVENT_DEPTNO", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.DATE);
		aliasTypeMap.put("END_DATE", this.DATE);
		aliasTypeMap.put("PAY_DATE", this.VARCHAR);
		aliasTypeMap.put("EVENT_CONTENT", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("PA_TEMP_SELLER_EVENT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/pa/tempsale/viewImportExcelTempSalesDataList";
		String navTabId = "pa0701";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  （临促工资数据导入）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importExcelTempSalesAccrualData")
	public ModelAndView importExcelTempSalesAccrualData(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();

		aliasValueMap.put("EVENT_DEPTNO", "#CELL0#");
		aliasValueMap.put("PRODUCT_CODE", "#CELL1#");
		aliasValueMap.put("PAY_DATE", "#CELL2#");
		aliasValueMap.put("EVENT_CONTENT", "#CELL3#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EVENT_DEPTNO", this.VARCHAR);//支社
		aliasTypeMap.put("PRODUCT_CODE", this.VARCHAR);//产品类型
		aliasTypeMap.put("PAY_DATE", this.VARCHAR);//支付月
		aliasTypeMap.put("EVENT_CONTENT", this.VARCHAR);//工资
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("PA_TEMP_SELLER_EVENT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/pa/tempsale/viewImportExcelTempSalesAccrualDataList";
		String navTabId = "pa0711";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	/**
	 * 导入数据  （临促工资人员数据导入）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importExcelTempSalesEmpData")
	public ModelAndView importExcelTempSalesEmpData(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();

		aliasValueMap.put("EMP_NAME", "#CELL0#");
		aliasValueMap.put("EVENT_STORE_CODE", "#CELL1#");
		aliasValueMap.put("IDCARD_NO", "#CELL2#");
		aliasValueMap.put("BANK_NO", "#CELL3#");
		aliasValueMap.put("BANK_NAME", "#CELL4#");
		aliasValueMap.put("PROD_TP", "#CELL5#");
		aliasValueMap.put("CELLPHONE", "#CELL6#");
		aliasValueMap.put("EVS_GRADE", "#CELL7#");
		aliasValueMap.put("WORK_DAYS", "#CELL8#");
		aliasValueMap.put("EVENT_SALARY", "#CELL9#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMP_NAME", this.VARCHAR);
		aliasTypeMap.put("EVENT_STORE_CODE", this.VARCHAR);
		aliasTypeMap.put("IDCARD_NO", this.VARCHAR);
		aliasTypeMap.put("BANK_NO", this.VARCHAR);
		aliasTypeMap.put("BANK_NAME", this.VARCHAR);
		aliasTypeMap.put("PROD_TP", this.VARCHAR);
		aliasTypeMap.put("CELLPHONE", this.VARCHAR);
		aliasTypeMap.put("EVS_GRADE", this.VARCHAR);
		aliasTypeMap.put("WORK_DAYS", this.NUMBER);
		aliasTypeMap.put("EVENT_SALARY", this.NUMBER);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE,EVENT_ID";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate," + request.getParameter("EVENT_ID");
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("PA_TEMP_SELLER_INFO_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/pa/tempsale/viewImportExcelTempSalesEmpDataList";
		String navTabId = "pa0701_EMPINFO";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	/**
	 * 导入数据  （教育实绩数据导入）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importExcelPromotoGradeData")
	public ModelAndView importExcelPromotoGradeData(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();

		aliasValueMap.put("SUBSD_CD", "#CELL0#");
		aliasValueMap.put("PAY_AREA_CD", "#CELL1#");
		aliasValueMap.put("BRANCH_CD", "#CELL2#");
		aliasValueMap.put("EMPNO", "#CELL3#");
		aliasValueMap.put("SUBJT_GR", "#CELL4#");
		aliasValueMap.put("SUBJT_ID", "#CELL5#");
		aliasValueMap.put("GRADE_POINT", "#CELL6#");
		aliasValueMap.put("COURSE_S", "#CELL7#");
		aliasValueMap.put("LECTURER_S", "#CELL8#");
		aliasValueMap.put("NPS", "#CELL9#");
		aliasValueMap.put("TCR_NM", "#CELL10#");
		aliasValueMap.put("EDU_TIME", "#CELL11#");
		aliasValueMap.put("EDU_RM", "#CELL12#");
		aliasValueMap.put("USE_YN", "#CELL13#");
		aliasValueMap.put("SUBJT_TIME", "#CELL14#");
		aliasValueMap.put("NPS_REASON", "#CELL15#");
		aliasValueMap.put("NPS_NO_REASON", "#CELL16#");
		aliasValueMap.put("SALES_TALK", "#CELL17#");
		aliasValueMap.put("COM_CLUB_INFO", "#CELL18#");
		aliasValueMap.put("OTHER_FEEDBACK", "#CELL19#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("SUBSD_CD", this.VARCHAR);
		aliasTypeMap.put("PAY_AREA_CD", this.VARCHAR);
		aliasTypeMap.put("BRANCH_CD", this.VARCHAR);
		aliasTypeMap.put("EMPNO", this.VARCHAR);
		aliasTypeMap.put("SUBJT_GR", this.VARCHAR);
		aliasTypeMap.put("SUBJT_ID", this.VARCHAR);
		aliasTypeMap.put("GRADE_POINT", this.NUMBER);
		aliasTypeMap.put("COURSE_S", this.VARCHAR);
		aliasTypeMap.put("LECTURER_S", this.VARCHAR);
		aliasTypeMap.put("NPS", this.VARCHAR);
		aliasTypeMap.put("TCR_NM", this.VARCHAR);
		aliasTypeMap.put("EDU_TIME", this.VARCHAR);
		aliasTypeMap.put("EDU_RM", this.VARCHAR);
		aliasTypeMap.put("USE_YN", this.VARCHAR);
		aliasTypeMap.put("SUBJT_TIME", this.VARCHAR);
		aliasTypeMap.put("NPS_REASON", this.VARCHAR);
		aliasTypeMap.put("NPS_NO_REASON", this.VARCHAR);
		aliasTypeMap.put("SALES_TALK", this.VARCHAR);
		aliasTypeMap.put("COM_CLUB_INFO", this.VARCHAR);
		aliasTypeMap.put("OTHER_FEEDBACK", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",RGST_DTIME,UPDT_DTIME,UPDT_USER";
		String appendValue=",sysdate" + ",sysdate," + "'" + admin.getEmpID() + "'";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("EDU_PR_EVAL_EXCEL_IMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		this.excelUtilSer.importExcelPromotoGradeData(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}

	/**
	 * @Create date: 2014.07.08
	 */
	@Autowired
	private PromoterSer promoterSer ;
	
	@RequestMapping(value="/importIncBasicSetup")
	public ModelAndView importIncBasicSetup(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();

		aliasValueMap.put("PROD_ID", "#CELL0#");
		aliasValueMap.put("HEAD_UNIT_PRC", "#CELL1#");
		aliasValueMap.put("INC_RATE", "#CELL2#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PROD_ID", this.VARCHAR);
		aliasTypeMap.put("HEAD_UNIT_PRC", this.NUMBER);
		aliasTypeMap.put("INC_RATE", this.NUMBER);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",UPDT_USER";
		String appendValue=",'" + admin.getUserNo()	+ "'";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map = this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("INF_PROD_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		this.promoterSer.importIncBasicSetup(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}

	/**
	 * @Create date: 2014.07.16
	 */
	@RequestMapping(value="/importOfficeIncAdjust")
	public ModelAndView importOfficeIncAdjust(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("PAY_AREA_CD", "#CELL0#");
		aliasValueMap.put("PROD_ID", "#CELL1#");
		aliasValueMap.put("DIFF_RAT", "#CELL2#");
		aliasValueMap.put("FXD_AMT", "#CELL3#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PAY_AREA_CD", this.VARCHAR);
		aliasTypeMap.put("PROD_ID", this.VARCHAR);
		aliasTypeMap.put("DIFF_RAT", this.NUMBER);
		aliasTypeMap.put("FXD_AMT", this.NUMBER);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",UPDT_USER";
		String appendValue=",'" + admin.getUserNo()	+ "'";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map = this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("INC_INCTV_AMT_BY_OFICE_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		this.promoterSer.importOfficeIncAdjust(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}

	/**
	 * @Create date: 2014.07.16
	 */
	@RequestMapping(value="/importSellout")
	public ModelAndView importSellout(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("BRANCH", "#CELL0#");
		aliasValueMap.put("EMP_NO", "#CELL1#");
		aliasValueMap.put("SALE_MONTH", "#CELL2#");
		aliasValueMap.put("SALE_DAY", "#CELL3#");
		aliasValueMap.put("MODEL_CATEGORY_CODE", "#CELL4#");
		aliasValueMap.put("MODEL_CODE", "#CELL5#");
		aliasValueMap.put("SHIP_TO_CODE", "#CELL6#");
		aliasValueMap.put("CHANNEL_CODE", "#CELL7#");
		aliasValueMap.put("SALE_QTY", "#CELL8#");
		aliasValueMap.put("NOTICE_PRICE", "#CELL9#");
		aliasValueMap.put("SELLOUT_PRICE", "#CELL10#");
		aliasValueMap.put("IMP_SEQ", "#CELL11#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("BRANCH", this.VARCHAR);
		aliasTypeMap.put("EMP_NO", this.VARCHAR);
		aliasTypeMap.put("SALE_MONTH", this.VARCHAR);
		aliasTypeMap.put("SALE_DAY", this.DATE);
		aliasTypeMap.put("MODEL_CATEGORY_CODE", this.VARCHAR);
		aliasTypeMap.put("MODEL_CODE", this.VARCHAR);
		aliasTypeMap.put("SHIP_TO_CODE", this.VARCHAR);
		aliasTypeMap.put("CHANNEL_CODE", this.VARCHAR);
		aliasTypeMap.put("SALE_QTY", this.NUMBER);
		aliasTypeMap.put("NOTICE_PRICE", this.NUMBER);
		aliasTypeMap.put("SELLOUT_PRICE", this.NUMBER);
		aliasTypeMap.put("IMP_SEQ", this.NUMBER);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",MODIFY_EMP";
		String appendValue=",'" + admin.getUserNo()	+ "'";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map = this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("INF_PR_SELLOUT_UPLOAD_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		this.promoterSer.importSellout(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	
	/**
	 * 人员类型数据导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importJobTypeData")
	public ModelAndView importJobTypeData(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("CPNY_ID", "#CELL0#");
		aliasValueMap.put("JOBTYPE_GROUP_NO", "#CELL1#");
		aliasValueMap.put("JOBTYPE_NO", "#CELL2#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("JOBTYPE_GROUP_NO", this.VARCHAR);
		aliasTypeMap.put("JOBTYPE_NO", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_JOB_TYPE_SETUP_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/hrm/jobType/viewImportJobTypeDataList";
		String navTabId = "hr0034";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * FSE人员工资数据导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importFSESalaryData")
	public ModelAndView importFSESalaryData(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("CPNY_ID", "#CELL0#");
		aliasValueMap.put("EMPID", "#CELL1#");
		aliasValueMap.put("PA_MONTH", "#CELL2#");
		aliasValueMap.put("EMP_TYPE_CODE", "#CELL3#");
		aliasValueMap.put("PA_AREA_CD", "#CELL4#");
		aliasValueMap.put("SALARY_ITEM", "#CELL5#");
		aliasValueMap.put("SALARY_ITEM_FEE", "#CELL6#");
		aliasValueMap.put("ADD_FLAG", "#CELL7#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("PA_MONTH", this.VARCHAR);
		aliasTypeMap.put("EMP_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("PA_AREA_CD", this.VARCHAR);
		aliasTypeMap.put("SALARY_ITEM", this.VARCHAR);
		aliasTypeMap.put("SALARY_ITEM_FEE", this.VARCHAR);
		aliasTypeMap.put("ADD_FLAG", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("PA_SUMMARY_FSE_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/pa/salarycode/viewImportFSESalaryDataList";
		String navTabId = "pa2011";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入保险参数数据
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importIsParamDataTemp")
	public ModelAndView importIsParamDataTemp(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("DATA_NO", "IS_SETUP_INFO_SEQ.NEXTVAL");
		aliasValueMap.put("CPNY_ID", admin.getCpnyId());
		aliasValueMap.put("PAY_AREA_CD", "#CELL0#");
		aliasValueMap.put("INSRAREA_ID", "#CELL1#");
		aliasValueMap.put("INSURE_ID", "#CELL2#");
		aliasValueMap.put("INSURE_RATE", "#CELL3#");
		aliasValueMap.put("INSURE_VALUE", "#CELL4#");
		aliasValueMap.put("REMARK", "#CELL5#");
		aliasValueMap.put("CHECK_FLAG", "0");
		aliasValueMap.put("ACTIVITY", "1");
		aliasValueMap.put("UPLOAD_BY", admin.getPersonId());
		aliasValueMap.put("UPLOAD_DATE", "SYSDATE");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("DATA_NO", this.NUMBER);
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("PAY_AREA_CD", this.VARCHAR);
		aliasTypeMap.put("INSRAREA_ID", this.VARCHAR);
		aliasTypeMap.put("INSURE_ID", this.VARCHAR);
		aliasTypeMap.put("INSURE_RATE", this.NUMBER);
		aliasTypeMap.put("INSURE_VALUE", this.NUMBER);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		aliasTypeMap.put("CHECK_FLAG", this.NUMBER);
		aliasTypeMap.put("ACTIVITY", this.NUMBER);
		aliasTypeMap.put("UPLOAD_BY", this.VARCHAR);
		aliasTypeMap.put("UPLOAD_DATE", this.SYSDATE);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String sqlI18nContent = "";
		//导入时，福利地区、福利项目的名字直接导入临时表里，不进行code转换
		String aliasNullStr = "";
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("IS_SETUP_INFO_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("aliasNullStr", aliasNullStr);
		
		String forwardUrl = "/is/insurancesystem/viewInsuranceImportDataChList";
		String navTabId = "bx0121";
		this.excelUtilSer.importIsParamData(request,response,map,modelMap,forwardUrl,navTabId);
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入保险参数数据---没有大区编码的法人
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importIsParamDataNoPayAreaTemp")
	public ModelAndView importIsParamDataNoPayAreaTemp(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("DATA_NO", "IS_SETUP_INFO_SEQ.NEXTVAL");
		aliasValueMap.put("CPNY_ID", admin.getCpnyId());
		aliasValueMap.put("PAY_AREA_CD", "");
		aliasValueMap.put("INSRAREA_ID", "#CELL0#");
		aliasValueMap.put("INSURE_ID", "#CELL1#");
		aliasValueMap.put("INSURE_RATE", "#CELL2#");
		aliasValueMap.put("INSURE_VALUE", "#CELL3#");
		
		if(admin.getCpnyId().equals("LGEQA"))
		{
			aliasValueMap.put("CARRY_WAY", "#CELL4#");
			aliasValueMap.put("REMARK", "#CELL5#");
		}else{
			aliasValueMap.put("REMARK", "#CELL4#");
		}
		
		aliasValueMap.put("CHECK_FLAG", "0");
		aliasValueMap.put("UPLOAD_BY", admin.getPersonId());
		aliasValueMap.put("UPLOAD_DATE", "SYSDATE");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("DATA_NO", this.NUMBER);
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("PAY_AREA_CD", this.VARCHAR);
		aliasTypeMap.put("INSRAREA_ID", this.VARCHAR);
		aliasTypeMap.put("INSURE_ID", this.VARCHAR);
		aliasTypeMap.put("INSURE_RATE", this.NUMBER);
		aliasTypeMap.put("INSURE_VALUE", this.NUMBER);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		if(admin.getCpnyId().equals("LGEQA"))
		{
			aliasTypeMap.put("CARRY_WAY", this.VARCHAR);
		}
		aliasTypeMap.put("CHECK_FLAG", this.NUMBER);
		aliasTypeMap.put("UPLOAD_BY", this.VARCHAR);
		aliasTypeMap.put("UPLOAD_DATE", this.SYSDATE);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String sqlI18nContent = "";
		//导入时，福利地区、福利项目的名字直接导入临时表里，不进行code转换
		String aliasNullStr = "";
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMap("IS_SETUP_INFO_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap);
		map.put("aliasNullStr", aliasNullStr);
		
		String forwardUrl = "/is/insurancesystem/viewInsuranceImportDataNChList";
		String navTabId = "bx0122";
		this.excelUtilSer.importIsParamData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	

	/**
	 * 导入数据  （临促工资数据导入）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importExcelContractData")
	public ModelAndView importExcelContractData(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();

		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("CONTRACT_NUMBER", "#CELL9#");
		aliasValueMap.put("CONTRACT_TYPE", "#CELL10#");
		aliasValueMap.put("CONTRACT_VERSION", "#CELL11#");
		aliasValueMap.put("START_CONTRACT_DATE", "#CELL12#");
		aliasValueMap.put("END_CONTRACT_DATE", "#CELL13#");
		aliasValueMap.put("REMARK", "#CELL14#");
		aliasValueMap.put("WORK_AREA", "#CELL3#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("CONTRACT_NUMBER", this.VARCHAR);
		aliasTypeMap.put("CONTRACT_TYPE", this.VARCHAR);
		aliasTypeMap.put("CONTRACT_VERSION", this.VARCHAR);
		aliasTypeMap.put("START_CONTRACT_DATE", this.DATE);
		aliasTypeMap.put("END_CONTRACT_DATE", this.DATE);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		aliasTypeMap.put("WORK_AREA", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_CONTRACT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/hrm/contractInfo/viewImportExcelContractDataList";
		String navTabId = "hr0305";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	

	/**
	 * 导入数据  （临促工资数据导入）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importExcelContractData2")
	public ModelAndView importExcelContractData2(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();

		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("WORK_AREA", "#CELL2#");
		aliasValueMap.put("TOTAL_PERIOD_08", "#CELL3#");
		aliasValueMap.put("CONTRACT_NUMBER", "#CELL4#");
		aliasValueMap.put("CONTRACT_TYPE", "#CELL5#");
		aliasValueMap.put("CONTRACT_VERSION", "#CELL6#");
		aliasValueMap.put("START_CONTRACT_DATE", "#CELL7#");
		aliasValueMap.put("END_CONTRACT_DATE", "#CELL8#");
		aliasValueMap.put("REMARK", "#CELL9#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("WORK_AREA", this.VARCHAR);
		aliasTypeMap.put("TOTAL_PERIOD_08", this.VARCHAR);
		aliasTypeMap.put("CONTRACT_NUMBER", this.VARCHAR);
		aliasTypeMap.put("CONTRACT_TYPE", this.VARCHAR); 
		aliasTypeMap.put("CONTRACT_VERSION", this.VARCHAR);
		aliasTypeMap.put("START_CONTRACT_DATE", this.DATE);
		aliasTypeMap.put("END_CONTRACT_DATE", this.DATE);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_CONTRACT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/hrm/contractInfo/viewImportExcelContractDataList2";
		String navTabId = "hr0301";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  （离职人员数据批量导入）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 * @author PengHaixia
	 */
	@RequestMapping(value="/importExcelTempEmpResignData")
	public ModelAndView importExcelTempEmpResignData(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();

		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("RESIGN_DATE", "#CELL1#");
		aliasValueMap.put("RESIGN_TYPE_CODE", "#CELL2#");
		aliasValueMap.put("RESIGN_REASON", "#CELL3#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("RESIGN_DATE", this.DATE_HMS);
		aliasTypeMap.put("RESIGN_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("RESIGN_REASON", this.VARCHAR);		
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPDT_USER,UPDT_DTIME";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getEmpID()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_RESIGNATION_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		this.excelUtilSer.importExcelTempEmpResignData(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 临时职人员发令数据批量导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 * @author PengHaixia
	 */
	@RequestMapping(value="/importExcelTempEmpTransferOrderData")
	public ModelAndView importExcelTempEmpTransferOrderData(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();

		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("START_DATE", "#CELL1#");
		aliasValueMap.put("TRANSFER_ORDER_REASON", "#CELL2#");
		aliasValueMap.put("CUR_DEPTNO", "#CELL3#");
		aliasValueMap.put("CUR_EMP_TYPE_CODE", "#CELL4#");
		aliasValueMap.put("CUR_SHIFT_NO", "#CELL5#");
		aliasValueMap.put("CUR_POSITION_NO", "#CELL6#");
		aliasValueMap.put("CUR_PAY_GRADE", "#CELL7#");
		aliasValueMap.put("CUR_PAY_STEP", "#CELL8#");
		aliasValueMap.put("CUR_BASE_PAY", "#CELL9#");
		aliasValueMap.put("CUR_VARB_PAY", "#CELL10#");
		aliasValueMap.put("CUR_ANSAL", "#CELL11#");
		aliasValueMap.put("CUR_ID_CARD_NO", "#CELL12#");
		aliasValueMap.put("CUR_JOB_TITLE_CD", "#CELL13#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.DATE_HMS);
		aliasTypeMap.put("TRANSFER_ORDER_REASON", this.VARCHAR);
		aliasTypeMap.put("CUR_DEPTNO", this.VARCHAR);
		aliasTypeMap.put("CUR_EMP_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("CUR_SHIFT_NO", this.VARCHAR);
		aliasTypeMap.put("CUR_POSITION_NO", this.VARCHAR);
		aliasTypeMap.put("CUR_PAY_GRADE", this.VARCHAR);
		aliasTypeMap.put("CUR_PAY_STEP", this.VARCHAR);
		aliasTypeMap.put("CUR_BASE_PAY", this.VARCHAR);
		aliasTypeMap.put("CUR_VARB_PAY", this.VARCHAR);
		aliasTypeMap.put("CUR_ANSAL", this.VARCHAR);
		aliasTypeMap.put("CUR_ID_CARD_NO", this.VARCHAR);
		aliasTypeMap.put("CUR_JOB_TITLE_CD", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",TRANS_NO,CPNY_ID,UPDT_USER,UPDT_DTIME";
		String appendValue=",1365,'" + admin.getCpnyId()	+ "','" + admin.getEmpID()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_EXPERIENCE_INSIDE_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		this.excelUtilSer.importExcelTempEmpTransferOrderData(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 临时职人员发令数据批量导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 * @author PengHaixia
	 */
	@RequestMapping(value="/importExcelReguEmpTransferOrderData")
	public ModelAndView importExcelReguEmpTransferOrderData(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();

		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("START_DATE", "#CELL1#");
		aliasValueMap.put("TRANSFER_ORDER_REASON", "#CELL2#");
		aliasValueMap.put("CUR_EMP_TYPE_CODE", "#CELL3#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.DATE_HMS);
		aliasTypeMap.put("TRANSFER_ORDER_REASON", this.VARCHAR);
		aliasTypeMap.put("CUR_EMP_TYPE_CODE", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",TRANS_NO,CPNY_ID,UPDT_USER,UPDT_DTIME";
		String appendValue=",1365,'" + admin.getCpnyId()	+ "','" + admin.getEmpID()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_EXPERIENCE_INSIDE_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		this.excelUtilSer.importExcelReguEmpTransferOrderData(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  leave
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importArMacTemp")
	public ModelAndView importArMacTemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("APPLY_NAME", "#CELL1#");
		aliasValueMap.put("APPLY_DATE","#CELL2#");
		aliasValueMap.put("APPLY_TIME",  "#CELL3#");
		aliasValueMap.put("DOOR_TYPE", "#CELL4#");
		aliasValueMap.put("REMARK", "#CELL5#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("APPLY_NAME", this.VARCHAR);
		aliasTypeMap.put("APPLY_DATE", this.DATE);
		aliasTypeMap.put("APPLY_TIME", this.VARCHAR);
		aliasTypeMap.put("DOOR_TYPE", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("AR_MAC_RECORDS_APPLY_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/ess/recordApply/viewImportExcelEssArMacDataList?pageNum=1"; 
		String navTabId = "ess0301";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}

	@RequestMapping(value="/impTmpEmpBatch")
	public ModelAndView impTmpEmpBatch(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("CPNY_ID", "#CELL0#");
		aliasValueMap.put("DEPTNO", "#CELL1#");
		aliasValueMap.put("JOIN_COMPANY_DATE", "#CELL2#");
		aliasValueMap.put("JOIN_TYPE_CODE", "#CELL3#");
		aliasValueMap.put("LOCAL_NAME", "#CELL4#");
		aliasValueMap.put("CHINESE_PINYIN", "#CELL5#");
		aliasValueMap.put("PROB_STRT_DATE", "#CELL6#");
		aliasValueMap.put("END_PROBATION_DATE", "#CELL7#");
		aliasValueMap.put("PROB_PAY_RAT", "#CELL8#");
		aliasValueMap.put("SEXCODE", "#CELL9#");
		aliasValueMap.put("IDCARD_NO", "#CELL10#");
		aliasValueMap.put("FINAL_DEGREE_CODE", "#CELL11#");
		aliasValueMap.put("DOB", "#CELL12#");
		aliasValueMap.put("IDCARD_ADDR", "#CELL13#");
		aliasValueMap.put("CELLPHONE", "#CELL14#");
		aliasValueMap.put("EMAIL", "#CELL15#");
		aliasValueMap.put("REG_TYPE_CODE", "#CELL16#");
		aliasValueMap.put("REG_PLACE", "#CELL17#");
		aliasValueMap.put("PAY_GRADE", "#CELL18#");
		aliasValueMap.put("PAY_STEP", "#CELL19#");
		aliasValueMap.put("BASE_PAY", "#CELL20#");
		aliasValueMap.put("VARB_PAY", "#CELL21#");
		aliasValueMap.put("CARD_NAME", "#CELL22#");
		aliasValueMap.put("CARD_NO", "#CELL23#");
		aliasValueMap.put("INSRAREA_ID", "#CELL24#");
		aliasValueMap.put("EMP_TYPE_CODE", "#CELL25#");
		aliasValueMap.put("WORK_AREA", "#CELL26#");
		aliasValueMap.put("PROMTR_WORK_TP", "#CELL27#");
		aliasValueMap.put("SHIFT_NO", "#CELL28#");
		aliasValueMap.put("PROMTR_TP", "#CELL29#");
		aliasValueMap.put("STAR_TP", "#CELL30#");
		aliasValueMap.put("PROD_TP", "#CELL31#");
		aliasValueMap.put("PROD_ADD", "#CELL32#");
		aliasValueMap.put("JOB_TITLE_CD", "#CELL33#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("DEPTNO", this.VARCHAR);
		aliasTypeMap.put("JOIN_COMPANY_DATE", this.DATE);
		aliasTypeMap.put("JOIN_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("CHINESE_PINYIN", this.VARCHAR);
		aliasTypeMap.put("PROB_STRT_DATE", this.DATE);
		aliasTypeMap.put("END_PROBATION_DATE", this.DATE);
		aliasTypeMap.put("PROB_PAY_RAT", this.NUMBER);
		aliasTypeMap.put("SEXCODE", this.VARCHAR);
		aliasTypeMap.put("IDCARD_NO", this.VARCHAR);
		aliasTypeMap.put("FINAL_DEGREE_CODE", this.VARCHAR);
		aliasTypeMap.put("DOB", this.DATE);
		aliasTypeMap.put("IDCARD_ADDR", this.VARCHAR);
		aliasTypeMap.put("CELLPHONE", this.VARCHAR);
		aliasTypeMap.put("EMAIL", this.VARCHAR);
		aliasTypeMap.put("REG_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("REG_PLACE", this.VARCHAR);
		aliasTypeMap.put("PAY_GRADE", this.VARCHAR);
		aliasTypeMap.put("PAY_STEP", this.VARCHAR);
		aliasTypeMap.put("BASE_PAY", this.NUMBER);
		aliasTypeMap.put("VARB_PAY", this.NUMBER);
		aliasTypeMap.put("CARD_NAME", this.VARCHAR);
		aliasTypeMap.put("CARD_NO", this.VARCHAR);
		aliasTypeMap.put("INSRAREA_ID", this.VARCHAR);
		aliasTypeMap.put("EMP_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("WORK_AREA", this.VARCHAR);
		aliasTypeMap.put("PROMTR_WORK_TP", this.VARCHAR);
		aliasTypeMap.put("SHIFT_NO", this.VARCHAR);
		aliasTypeMap.put("PROMTR_TP", this.VARCHAR);
		aliasTypeMap.put("STAR_TP", this.VARCHAR);
		aliasTypeMap.put("PROD_TP", this.VARCHAR);
		aliasTypeMap.put("PROD_ADD", this.VARCHAR);
		aliasTypeMap.put("JOB_TITLE_CD", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getUserNo()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map = this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_TMPEMP_UPLOAD",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		this.promoterSer.importTempEmp(request,response,map,modelMap);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	

	/**
	 * 导入数据  leave
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importArDetailTemp")
	public ModelAndView importArDetailTemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("AR_DATE_STR", "#CELL0#");
		aliasValueMap.put("AR_MONTH", "#CELL1#");
		aliasValueMap.put("EMPID","#CELL2#");
		aliasValueMap.put("LOCAL_NAME",  "#CELL3#");
		aliasValueMap.put("DEPT_NAME", "#CELL4#");
		aliasValueMap.put("ITEM_NAME", "#CELL5#");
		aliasValueMap.put("AR_START_DATE", "#CELL6#");
		aliasValueMap.put("AR_START_TIME", "#CELL7#");
		aliasValueMap.put("AR_END_DATE", "#CELL8#");
		aliasValueMap.put("AR_END_TIME", "#CELL9#");
		aliasValueMap.put("LENGTH", "#CELL10#");
		aliasValueMap.put("DELETE_YN", "#CELL11#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("AR_DATE_STR", this.VARCHAR);
		aliasTypeMap.put("AR_MONTH", this.VARCHAR);
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("DEPT_NAME", this.VARCHAR);
		aliasTypeMap.put("ITEM_NAME", this.VARCHAR);
		aliasTypeMap.put("AR_START_DATE", this.VARCHAR);
		aliasTypeMap.put("AR_START_TIME", this.VARCHAR);
		aliasTypeMap.put("AR_END_DATE", this.VARCHAR);
		aliasTypeMap.put("AR_END_TIME", this.VARCHAR);
		aliasTypeMap.put("LENGTH", this.VARCHAR);
		aliasTypeMap.put("DELETE_YN", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("AR_DETAIL_EXCEL_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/ar/attendanceMintenance/viewImportArDetailTempList?pageNum=1"; 
		String navTabId = "ar0201";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	

	/**
	 * 导入数据  leave
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importArVacTemp")
	public ModelAndView importArVacTemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");
		aliasValueMap.put("VAC_NUM","#CELL2#");
		aliasValueMap.put("REMARK",  "#CELL3#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("VAC_NUM", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("ESS_ANNUAL_APPLY_BATCH_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/ess/annualadjustment/viewImportExcelEssArVacDataList?pageNum=1"; 
		String navTabId = "ess0201";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	

	/**
	 * 导入数据  leave
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importAbsenteeismTempess0307")
	public ModelAndView importAbsenteeismTempess0307(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");
		aliasValueMap.put("AR_DATE_STR","#CELL2#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("AR_DATE_STR", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("ESS_CARD_APPLY_TB_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		String navTabId = "ess0307";
		String forwardUrl = "/ess/infoApply/viewImportExcelEssAbsenteeismDataListess0307?pageNum=1"; 
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	

	/**
	 * 导入数据  leave
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importAffirmTemp")
	public ModelAndView importAffirmTemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("AFFIRM_OBJECT", "#CELL0#");
		aliasValueMap.put("APPLY_TYPE", "#CELL1#");
		aliasValueMap.put("AFFIRM_LEVEL","#CELL2#");
		aliasValueMap.put("AFFIRM_EMPID",  "#CELL3#");
		aliasValueMap.put("AFFIRM_LOCAL_NAME", "#CELL4#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("AFFIRM_OBJECT", this.VARCHAR);
		aliasTypeMap.put("APPLY_TYPE", this.VARCHAR);
		aliasTypeMap.put("AFFIRM_LEVEL", this.VARCHAR);
		aliasTypeMap.put("AFFIRM_EMPID", this.VARCHAR);
		aliasTypeMap.put("AFFIRM_LOCAL_NAME", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("SY_AFFIRM_RELATION_FINAL_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/sys/affirm/viewImportExcelAffirmDataList?pageNum=1"; 
		String navTabId = "sy0130";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	

	/**
	 * 导入数据  leave
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importMappingTemp")
	public ModelAndView importMappingTemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("NEW_EMPID", "#CELL0#");
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");
		aliasValueMap.put("OLD_EMPID","#CELL2#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("NEW_EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("OLD_EMPID", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("hr_emp_mapping_temp",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/sys/empMapping/viewImportExcelMappingDataList?pageNum=1"; 
		String navTabId = "sy0490";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 部门排序数据导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importDeptOrderData")
	public ModelAndView importDeptOrderData(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("CPNY_ID", "#CELL0#");
		aliasValueMap.put("DEPTNO", "#CELL1#");
		aliasValueMap.put("ORDERNO", "#CELL2#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("CPNY_ID", this.VARCHAR);
		aliasTypeMap.put("DEPTNO", this.VARCHAR);
		aliasTypeMap.put("ORDERNO", this.NUMBER);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_DEPARTMENT_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/org/orgManage/viewImportDeptNoDataList";
		String navTabId = "org11order";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	@RequestMapping(value = "/importItemBatchImportExcelIsNotNull")
	public ModelAndView importItemBatchImportExcelIsNotNullTest(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception {
		long startTime = System.nanoTime();
		Map<String, Object> param = new HashMap<String, Object>();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		param.put("admin", admin);
		param.put("TEMP_TABLE_NAME", "IS_PARAM_DATA_TEMP_TEST");
		param.put("LANGUAGE", admin.getLanguage());
		param.put("CREATE_BY", admin.getPersonId());
		param.put("PROC_CODE", "00001");
		
		try {
			excelUtilSer.importDataTest(request, response, param);
			modelMap.put("sign", "1");
			modelMap.put("statusCode", "200");
			modelMap.put("rel", "viewInsuranceInputItemDataViewApplyMark");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importsuccess", request));// 导入成功
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		
		modelMap.put("forwardUrl", "/pa/insurance/viewItemBatchImport?pageNum=1&menuNo=123453&navTabId=pa0414");
		modelMap.put("navTabId", "pa0414");
		
		long endTime = System.nanoTime();
		long lTime = endTime - startTime;
		System.out.println("TIME : " + lTime/1000000.0 + "(ms)");
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);		
	}
	
	
	@RequestMapping(value = "/importItemBatchImportExcelIsNotNullPa")
	public ModelAndView importItemBatchImportExcelIsNotNullPaTest(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		long startTime = System.nanoTime();
		Map<String, Object> param = new HashMap<String, Object>();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		param.put("admin", admin);
		param.put("TEMP_TABLE_NAME", "IS_PARAM_DATA_TEMP_TEST");
		param.put("LANGUAGE", admin.getLanguage());
		param.put("CREATE_BY", admin.getPersonId());
		param.put("PROC_CODE", "00002");
		
		try {
			excelUtilSer.importDataTest(request, response, param);
			modelMap.put("sign", "1");
			modelMap.put("statusCode", "200");
			modelMap.put("rel", "viewInsuranceInputItemDataViewApplyMark");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importsuccess", request));// 导入成功
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		
		modelMap.put("forwardUrl", "/pa/salary/viewItemBatchImport?pageNum=1&menuNo=123455&navTabId=pa0218&seach_ITEM_DISTINGUISH=508");
		modelMap.put("navTabId", "pa0218");
		
		long endTime = System.nanoTime();
		long lTime = endTime - startTime;
		System.out.println("TOTAL TIME : " + lTime/1000000.0 + "(ms)");
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);	
	}
	
	@RequestMapping(value = "/importPaAccount")
	public ModelAndView importPaAccountTest(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception {
		long startTime = System.nanoTime();
		Map<String, Object> param = new HashMap<String, Object>();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		param.put("admin", admin);
		param.put("TABLE_NAME", "PA_ACCOUNT_TEMP");
		param.put("LANGUAGE", admin.getLanguage());
		param.put("UPLOAD_BY", admin.getPersonId());
		
		try {
			excelUtilSer.imporExceltDataTest(request, response, param);
			
			modelMap.put("sign", "1");
			modelMap.put("statusCode", "200");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			modelMap.put("navTabId", "pa0519");
			modelMap.put("forwardUrl","/pa/wagebase/viewImportPaAccountExcelList");
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		
		long endTime = System.nanoTime();
		long lTime = endTime - startTime;
		System.out.println("TOTAL TIME : " + lTime/1000000.0 + "(ms)");
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	

	/**
	 * 导入数据  招聘批量导入  HTSV
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importRecruitTemp")
	public ModelAndView importRecruitTemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("VIETNAM_NAME", "#CELL1#");
		aliasValueMap.put("ENGLISH_NAME", "#CELL2#");
		aliasValueMap.put("DOB", "#CELL3#");
		aliasValueMap.put("DATE_STARTED", "#CELL4#");
		aliasValueMap.put("END_PROBATION_DATE", "#CELL5#");
		aliasValueMap.put("JOIN_TYPE", "#CELL7#");
		aliasValueMap.put("JOIN_DETAIL_TYPE", "#CELL10#");
		aliasValueMap.put("DEPTNO", "#CELL13#");
		aliasValueMap.put("POST_GRADE_NO", "#CELL16#");
		aliasValueMap.put("MAIN_BUSINESS", "#CELL19#");
		aliasValueMap.put("POSITION_NO", "#CELL22#");
		aliasValueMap.put("EMP_TYPE_CODE", "#CELL25#");
		aliasValueMap.put("POST_FAMILY", "#CELL28#");
		aliasValueMap.put("COST_CENTER", "#CELL13#");
		aliasValueMap.put("FINAL_DEGREE_CODE", "#CELL31#");
		aliasValueMap.put("END_DATE", "#CELL33#");
		aliasValueMap.put("INSTITUTION_NAME", "#CELL34#");
		aliasValueMap.put("SUBJECT_NAME", "#CELL35#");
		aliasValueMap.put("IDCARD_NO", "#CELL36#");
		aliasValueMap.put("IDCARD_S_DATE", "#CELL37#");
		aliasValueMap.put("ISSUING_AUTHORITY", "#CELL38#");
		aliasValueMap.put("SEXCODE", "#CELL40#");
		aliasValueMap.put("NATIONALITY_CODE", "#CELL43#");
		aliasValueMap.put("NATION_CODE", "#CELL46#");
		aliasValueMap.put("RELIGION_CODE", "#CELL48#");
		aliasValueMap.put("MARITAL_STATUS_CODE", "#CELL50#");
		aliasValueMap.put("EMAIL_SECOND", "#CELL52#");
		aliasValueMap.put("SING_ID", "#CELL53#");
		aliasValueMap.put("HOME_PHONE", "#CELL54#");
		aliasValueMap.put("TELEPHONE", "#CELL55#");
		aliasValueMap.put("ADDRESS_CONTENT", "#CELL56#");
		aliasValueMap.put("REG_PLACE", "#CELL57#");
		aliasValueMap.put("SHIFT_NO", "400224");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("VIETNAM_NAME", this.VARCHAR);
		aliasTypeMap.put("ENGLISH_NAME", this.VARCHAR);
		aliasTypeMap.put("DOB", this.VARCHAR);
		aliasTypeMap.put("DATE_STARTED", this.VARCHAR);
		aliasTypeMap.put("END_PROBATION_DATE", this.VARCHAR);
		aliasTypeMap.put("JOIN_TYPE", this.VARCHAR);
		aliasTypeMap.put("JOIN_DETAIL_TYPE", this.VARCHAR);
		aliasTypeMap.put("DEPTNO", this.VARCHAR);
		aliasTypeMap.put("POST_GRADE_NO", this.VARCHAR);
		aliasTypeMap.put("MAIN_BUSINESS", this.VARCHAR);
		aliasTypeMap.put("POSITION_NO", this.VARCHAR);
		aliasTypeMap.put("EMP_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("POST_FAMILY", this.VARCHAR);
		aliasTypeMap.put("COST_CENTER", this.VARCHAR);
		aliasTypeMap.put("FINAL_DEGREE_CODE", this.VARCHAR);
		aliasTypeMap.put("END_DATE", this.VARCHAR);
		aliasTypeMap.put("INSTITUTION_NAME", this.VARCHAR);
		aliasTypeMap.put("SUBJECT_NAME", this.VARCHAR);
		aliasTypeMap.put("IDCARD_NO", this.VARCHAR);
		aliasTypeMap.put("IDCARD_S_DATE", this.VARCHAR);
		aliasTypeMap.put("ISSUING_AUTHORITY", this.VARCHAR);
		aliasTypeMap.put("SEXCODE", this.VARCHAR);
		aliasTypeMap.put("NATIONALITY_CODE", this.VARCHAR);
		aliasTypeMap.put("NATION_CODE", this.VARCHAR);
		aliasTypeMap.put("RELIGION_CODE", this.VARCHAR);
		aliasTypeMap.put("MARITAL_STATUS_CODE", this.VARCHAR);
		aliasTypeMap.put("EMAIL_SECOND", this.VARCHAR);
		aliasTypeMap.put("SING_ID", this.VARCHAR);
		aliasTypeMap.put("HOME_PHONE", this.VARCHAR);
		aliasTypeMap.put("TELEPHONE", this.VARCHAR);
		aliasTypeMap.put("ADDRESS_CONTENT", this.VARCHAR);
		aliasTypeMap.put("REG_PLACE", this.VARCHAR);
		aliasTypeMap.put("SHIFT_NO", this.VARCHAR);
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",SEQ,PERSON_ID,REGISTER_SEQ,CPNY_ID,CREATE_DATE,CREATED_BY,CREATED_IP,UPDATE_DATE,UPDATED_BY,UPDATED_IP,ACTIVITY";
		String appendValue=",HR_EMPLOYEE_RECRUIT_BATCH_SEQ.NEXTVAL,HR_PERSON_ID_SEQ.NEXTVAL,'" + paramMap.get("REGISTER_SEQ") + "','" + admin.getCpnyId()	+ "',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP() + 
						"',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP()	+ "',0";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_EMPLOYEE_RECRUIT_BATCH",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		this.excelUtilSer.importExcelData(request,response,map,modelMap);
		modelMap.put("refCurrent", "refCurrent");
		modelMap.put("navTabId", "hr0203");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  招聘批量导入  HAE
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importRecruitTempHAE")
	public ModelAndView importRecruitTempHAE(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("VIETNAM_NAME", "#CELL1#");
		aliasValueMap.put("ENGLISH_NAME", "#CELL2#");
		aliasValueMap.put("DOB", "#CELL3#");
		aliasValueMap.put("DATE_STARTED", "#CELL4#");
		aliasValueMap.put("CONTRACT_START_DATE", "#CELL5#");
		aliasValueMap.put("CONTRACT_END_DATE", "#CELL6#");
		aliasValueMap.put("END_PROBATION_DATE", "#CELL7#");
		aliasValueMap.put("TEST_NOT", "#CELL8#");
		aliasValueMap.put("JOIN_TYPE", "#CELL10#");
		aliasValueMap.put("JOIN_DETAIL_TYPE", "#CELL13#");
		aliasValueMap.put("DEPTNO", "#CELL16#");
		aliasValueMap.put("POST_GRADE_NO", "#CELL19#");
		aliasValueMap.put("PAY_STEP_NO", "#CELL22#");
		aliasValueMap.put("MAIN_BUSINESS", "#CELL25#");
		aliasValueMap.put("POST_FAMILY", "#CELL28#");
		aliasValueMap.put("POSITION_NO", "#CELL31#");
		aliasValueMap.put("EMP_TYPE_CODE", "#CELL34#");
		aliasValueMap.put("COST_CENTER", "#CELL37#");
		aliasValueMap.put("FINAL_DEGREE_CODE", "#CELL40#");
		aliasValueMap.put("START_DATE", "#CELL42#");
		aliasValueMap.put("END_DATE", "#CELL43#");
		aliasValueMap.put("INSTITUTION_NAME", "#CELL44#");
		aliasValueMap.put("SUBJECT_NAME", "#CELL45#");
		aliasValueMap.put("GRADUATION_ACHIEVEMENT", "#CELL47#");
		aliasValueMap.put("AVERAGE_SCORE", "#CELL49#");
		aliasValueMap.put("LANGUAGE_ABILITY", "#CELL51#");
		aliasValueMap.put("IDCARD_NO", "#CELL53#");
		aliasValueMap.put("IDCARD_S_DATE", "#CELL54#");
		aliasValueMap.put("ISSUING_AUTHORITY", "#CELL55#");
		aliasValueMap.put("SEXCODE", "#CELL57#");
		aliasValueMap.put("NATIONALITY_CODE", "#CELL60#");
		aliasValueMap.put("NATION_CODE", "#CELL63#");
		aliasValueMap.put("MARITAL_STATUS_CODE", "#CELL66#");
		aliasValueMap.put("EMAIL_SECOND", "#CELL68#");
		aliasValueMap.put("EMAIL", "#CELL69#");
		aliasValueMap.put("SING_ID", "#CELL70#");
		aliasValueMap.put("HOME_PHONE", "#CELL71#");
		aliasValueMap.put("TELEPHONE", "#CELL72#");
		aliasValueMap.put("ADDRESS_CONTENT", "#CELL73#");
		aliasValueMap.put("REG_PLACE", "#CELL74#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("VIETNAM_NAME", this.VARCHAR);
		aliasTypeMap.put("ENGLISH_NAME", this.VARCHAR);
		aliasTypeMap.put("DOB", this.VARCHAR);
		aliasTypeMap.put("DATE_STARTED", this.VARCHAR);
		aliasTypeMap.put("CONTRACT_START_DATE", this.VARCHAR);
		aliasTypeMap.put("CONTRACT_END_DATE", this.VARCHAR);
		aliasTypeMap.put("END_PROBATION_DATE", this.VARCHAR);
		aliasTypeMap.put("TEST_NOT", this.VARCHAR);
		aliasTypeMap.put("JOIN_TYPE", this.VARCHAR);
		aliasTypeMap.put("JOIN_DETAIL_TYPE", this.VARCHAR);
		aliasTypeMap.put("DEPTNO", this.VARCHAR);
		aliasTypeMap.put("POST_GRADE_NO", this.VARCHAR);
		aliasTypeMap.put("PAY_STEP_NO", this.VARCHAR);
		aliasTypeMap.put("MAIN_BUSINESS", this.VARCHAR);
		aliasTypeMap.put("POST_FAMILY", this.VARCHAR);
		aliasTypeMap.put("POSITION_NO", this.VARCHAR);
		aliasTypeMap.put("EMP_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("COST_CENTER", this.VARCHAR);
		aliasTypeMap.put("FINAL_DEGREE_CODE", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.VARCHAR);
		aliasTypeMap.put("END_DATE", this.VARCHAR);
		aliasTypeMap.put("INSTITUTION_NAME", this.VARCHAR);
		aliasTypeMap.put("SUBJECT_NAME", this.VARCHAR);
		aliasTypeMap.put("GRADUATION_ACHIEVEMENT", this.VARCHAR);
		aliasTypeMap.put("AVERAGE_SCORE", this.VARCHAR);
		aliasTypeMap.put("LANGUAGE_ABILITY", this.VARCHAR);
		aliasTypeMap.put("IDCARD_NO", this.VARCHAR);
		aliasTypeMap.put("IDCARD_S_DATE", this.VARCHAR);
		aliasTypeMap.put("ISSUING_AUTHORITY", this.VARCHAR);
		aliasTypeMap.put("SEXCODE", this.VARCHAR);
		aliasTypeMap.put("NATIONALITY_CODE", this.VARCHAR);
		aliasTypeMap.put("NATION_CODE", this.VARCHAR);
		aliasTypeMap.put("MARITAL_STATUS_CODE", this.VARCHAR);
		aliasTypeMap.put("EMAIL_SECOND", this.VARCHAR);
		aliasTypeMap.put("EMAIL", this.VARCHAR);
		aliasTypeMap.put("SING_ID", this.VARCHAR);
		aliasTypeMap.put("HOME_PHONE", this.VARCHAR);
		aliasTypeMap.put("TELEPHONE", this.VARCHAR);
		aliasTypeMap.put("ADDRESS_CONTENT", this.VARCHAR);
		aliasTypeMap.put("REG_PLACE", this.VARCHAR);
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",SEQ,PERSON_ID,REGISTER_SEQ,CPNY_ID,CREATE_DATE,CREATED_BY,CREATED_IP,UPDATE_DATE,UPDATED_BY,UPDATED_IP,ACTIVITY";
		String appendValue=",HR_EMPLOYEE_RECRUIT_BATCH_SEQ.NEXTVAL,HR_PERSON_ID_SEQ.NEXTVAL,'" + paramMap.get("REGISTER_SEQ") + "','" + admin.getCpnyId()	+ "',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP() + 
						"',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP()	+ "',0";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_EMPLOYEE_RECRUIT_BATCH",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		this.excelUtilSer.importExcelData(request,response,map,modelMap);
		modelMap.put("refCurrent", "refCurrent");
		modelMap.put("navTabId", "hr0203");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  简历录用导入  HAE
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importRecPageTemp")
	public ModelAndView importRecPageTemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		if (admin.getCpnyId() == "HAE" || "HAE".equals(admin.getCpnyId())) {
			aliasValueMap.put("EMP_NAME", "#CELL0#");
			aliasValueMap.put("CERT_NUMBER", "#CELL1#");
			aliasValueMap.put("IDCARD_START_DATE", "#CELL2#");
			aliasValueMap.put("IDCARD_ADDRESS", "#CELL3#");
			aliasValueMap.put("EMP_BIRTHDAY", "#CELL4#");
			aliasValueMap.put("EMP_ADDRESS", "#CELL5#");
			aliasValueMap.put("SEX_CODE", "#CELL7#");
			aliasValueMap.put("NATIONALITY_CODE", "#CELL10#");
			aliasValueMap.put("MARITAL_STATUS_CODE", "#CELL13#");
			aliasValueMap.put("FINAL_SCHOOL", "#CELL15#");
			aliasValueMap.put("FINAL_EDU_CODE", "#CELL17#");
			aliasValueMap.put("SUBJECT", "#CELL19#");
			aliasValueMap.put("FINAL_GRAD_DATE", "#CELL20#");
			aliasValueMap.put("GRADUATION_ACHIEVEMENT", "#CELL22#");
			aliasValueMap.put("AVERAGE_SCORE", "#CELL24#");
			aliasValueMap.put("LANGUAGE_ABILITY", "#CELL26#");
			aliasValueMap.put("WORK_EXPERIENCE", "#CELL28#");
			aliasValueMap.put("OLD_COMPANY", "#CELL29#");
			aliasValueMap.put("EMP_TELPHONE", "#CELL30#");
			aliasValueMap.put("INTERVIEW_PERIOD", "#CELL31#");
			
			aliasTypeMap.put("EMP_NAME", this.VARCHAR);
			aliasTypeMap.put("CERT_NUMBER", this.VARCHAR);
			aliasTypeMap.put("IDCARD_START_DATE", this.VARCHAR);
			aliasTypeMap.put("IDCARD_ADDRESS", this.VARCHAR);
			aliasTypeMap.put("EMP_BIRTHDAY", this.VARCHAR);
			aliasTypeMap.put("EMP_ADDRESS", this.VARCHAR);
			aliasTypeMap.put("SEX_CODE", this.VARCHAR);
			aliasTypeMap.put("NATIONALITY_CODE", this.VARCHAR);
			aliasTypeMap.put("MARITAL_STATUS_CODE", this.VARCHAR);
			aliasTypeMap.put("FINAL_SCHOOL", this.VARCHAR);
			aliasTypeMap.put("FINAL_EDU_CODE", this.VARCHAR);
			aliasTypeMap.put("SUBJECT", this.VARCHAR);
			aliasTypeMap.put("FINAL_GRAD_DATE", this.VARCHAR);
			aliasTypeMap.put("GRADUATION_ACHIEVEMENT", this.VARCHAR);
			aliasTypeMap.put("AVERAGE_SCORE", this.VARCHAR);
			aliasTypeMap.put("LANGUAGE_ABILITY", this.VARCHAR);
			aliasTypeMap.put("WORK_EXPERIENCE", this.VARCHAR);
			aliasTypeMap.put("OLD_COMPANY", this.VARCHAR);
			aliasTypeMap.put("EMP_TELPHONE", this.VARCHAR);
			aliasTypeMap.put("INTERVIEW_PERIOD", this.VARCHAR);
		}
		
		if (admin.getCpnyId() == "HTSV" || "HTSV".equals(admin.getCpnyId())) {
			aliasValueMap.put("EMP_NAME", "#CELL0#");
			aliasValueMap.put("CERT_TYPE_CODE", "#CELL2#");
			aliasValueMap.put("CERT_NUMBER", "#CELL4#");
			aliasValueMap.put("EMP_TELPHONE", "#CELL5#");
			aliasValueMap.put("DEPT_NAME", "#CELL6#");
			aliasValueMap.put("EMP_ADDRESS", "#CELL7#");
			aliasValueMap.put("EMP_BIRTHDAY", "#CELL8#");
			aliasValueMap.put("SEX_CODE", "#CELL10#");
			aliasValueMap.put("NATIONALITY_CODE", "#CELL13#");
			aliasValueMap.put("MARITAL_STATUS_CODE", "#CELL16#");
			aliasValueMap.put("POST_TYPE_CODE", "#CELL19#");
			aliasValueMap.put("FINAL_SCHOOL", "#CELL21#");
			aliasValueMap.put("FINAL_EDU_CODE", "#CELL23#");
			aliasValueMap.put("SUBJECT", "#CELL25#");
			aliasValueMap.put("EMP_EMAIL", "#CELL26#");
			aliasValueMap.put("FINAL_GRAD_DATE", "#CELL27#");
			aliasValueMap.put("WORK_EXPERIENCE", "#CELL28#");
			
			aliasTypeMap.put("EMP_NAME", this.VARCHAR);
			aliasTypeMap.put("CERT_TYPE_CODE", this.VARCHAR);
			aliasTypeMap.put("CERT_NUMBER", this.VARCHAR);
			aliasTypeMap.put("EMP_TELPHONE", this.VARCHAR);
			aliasTypeMap.put("DEPT_NAME", this.VARCHAR);
			aliasTypeMap.put("EMP_ADDRESS", this.VARCHAR);
			aliasTypeMap.put("EMP_BIRTHDAY", this.VARCHAR);
			aliasTypeMap.put("SEX_CODE", this.VARCHAR);
			aliasTypeMap.put("NATIONALITY_CODE", this.VARCHAR);
			aliasTypeMap.put("MARITAL_STATUS_CODE", this.VARCHAR);
			aliasTypeMap.put("POST_TYPE_CODE", this.VARCHAR);
			aliasTypeMap.put("FINAL_SCHOOL", this.VARCHAR);
			aliasTypeMap.put("FINAL_EDU_CODE", this.VARCHAR);
			aliasTypeMap.put("SUBJECT", this.VARCHAR);
			aliasTypeMap.put("EMP_EMAIL", this.VARCHAR);
			aliasTypeMap.put("FINAL_GRAD_DATE", this.VARCHAR);
			aliasTypeMap.put("WORK_EXPERIENCE", this.VARCHAR);
		}
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",REC_EMPLOYEE_NO,CREATE_DATE,CREATED_BY,UPDATE_DATE,UPDATED_BY,REC_TYPE,ACTIVITY";
		String appendValue=",REC_EMPLOYEE_NO_SEQ.NEXTVAL," + "sysdate,'" + admin.getPersonId() + 
						"',sysdate,'" + admin.getPersonId()	+ "',0,1";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("REC_EMPLOYEE",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		this.excelUtilSer.importExcelData(request,response,map,modelMap);
		modelMap.put("refCurrent", "refCurrent");
		modelMap.put("navTabId", "hr3701");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	@RequestMapping(value="/importRecruitTempReward")
	public ModelAndView importRecruitTempReward(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		this.pathCpnyID=admin.getCpnyId();
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		
		aliasValueMap.put("PERSON_ID", "#CELL1#");
		aliasValueMap.put("REWARD_TYPE", "#CELL2#");
		aliasValueMap.put("REWARD_DATE", "#CELL3#");
		aliasValueMap.put("REMARKS", "#CELL4#");
		
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("REWARD_TYPE", this.VARCHAR);
		aliasTypeMap.put("REWARD_DATE", this.DATE_HMS);
		aliasTypeMap.put("REMARKS", this.VARCHAR);
		
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",REWARD_NO,CREATE_DATE,CREATED_BY,CREATED_IP,UPDATED_DATE,UPDATED_BY,UPDATED_IP,ACTIVITY";
		String appendValue=",hr_reward_seq.nextval" + ",sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP() + 
						"',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP()	+ "',1";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		String sqlGetPersonId="SELECT PERSON_ID FROM HR_EMPLOYEE E WHERE E.EMPID=#CELL1# AND CPNY_ID='"+this.pathCpnyID+"'";
		aliasValueI18nMap.put("PERSON_ID", sqlGetPersonId);
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_REWARD",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		this.excelUtilSer.importExcelData(request,response,map,modelMap);
		modelMap.put("refCurrent", "refCurrent");
		modelMap.put("navTabId", "hr0203");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  招聘批量导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importPARAMTemp")
	public ModelAndView importPARAMTemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		int resultNum=viewPaParamSer.cleanTempListById(request);
		
		aliasValueMap.put("PARAM_ITEM_NO", "#CELL1#");
		aliasValueMap.put("PARAM_ITEM_NAME", "#CELL2#");
		aliasValueMap.put("EMPID", "#CELL3#");
		aliasValueMap.put("START_MONTH", "#CELL4#");
		aliasValueMap.put("END_MONTH", "#CELL5#");
		aliasValueMap.put("RETURN_VALUE", "#CELL6#");
		aliasValueMap.put("REMARK", "#CELL7#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PARAM_ITEM_NO", this.VARCHAR);
		aliasTypeMap.put("PARAM_ITEM_NAME", this.VARCHAR);
		aliasTypeMap.put("EMPID",this.VARCHAR);
		aliasTypeMap.put("START_MONTH",this.VARCHAR);
		aliasTypeMap.put("END_MONTH", this.VARCHAR);
		aliasTypeMap.put("RETURN_VALUE",this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",PARAM_DATE_NO,CPNY_ID,UPLOAD_DATE,UPLOAD_BY,CREATE_DATE,CREATED_BY,CREATED_IP,ACTIVITY";
		String appendValue=",Pa_Param_Data_Seq.Nextval,'" + admin.getCpnyId() + "',sysdate,'" + admin.getPersonId()	+ "',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP() + "',0";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("PA_PARAM_DATA_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		
		String forwardUrl = "/pa/workManagement/viewPaParamUploudList?firstFlage=yes";
		String navTabId = "pa0824";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
	
		
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	/**
	 * 导入数据  发令批量导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importExperienceTemp")
	public ModelAndView importExperienceTemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("LOCAL_NAME", "#CELL0#");
		aliasValueMap.put("EMPID", "#CELL1#");
		aliasValueMap.put("START_DATE", "#CELL2#");
		aliasValueMap.put("TRANS_CODE", "#CELL4#");
		aliasValueMap.put("TRANS_REASON", "#CELL7#");
		aliasValueMap.put("DEPTNO", "#CELL10#");
		aliasValueMap.put("POST_FAMILY", "#CELL13#");
		aliasValueMap.put("NEW_POST_GRADE_NO", "#CELL16#");
		aliasValueMap.put("POSITION_NO", "#CELL19#");
		aliasValueMap.put("EMP_TYPE_CODE", "#CELL22#");
		aliasValueMap.put("MAIN_BUSINESS", "#CELL25#");
		aliasValueMap.put("COST_CENTER", "#CELL28#");
		aliasValueMap.put("REMARKS", "#CELL30#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.DMY_VARCHAR);
		aliasTypeMap.put("TRANS_CODE", this.VARCHAR);
		aliasTypeMap.put("TRANS_REASON", this.VARCHAR);
		aliasTypeMap.put("DEPTNO", this.VARCHAR);
		aliasTypeMap.put("POST_FAMILY", this.VARCHAR);
		aliasTypeMap.put("NEW_POST_GRADE_NO", this.VARCHAR);
		aliasTypeMap.put("POSITION_NO", this.VARCHAR);
		aliasTypeMap.put("EMP_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("MAIN_BUSINESS", this.VARCHAR);
		aliasTypeMap.put("COST_CENTER", this.VARCHAR);
		aliasTypeMap.put("REMARKS", this.VARCHAR);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",SEQ,REGISTER_SEQ,CPNY_ID,CREATE_DATE,CREATED_BY,CREATED_IP,UPDATE_DATE,UPDATED_BY,UPDATED_IP,ACTIVITY";
		String appendValue=",HR_EXPERIENCE_INSIDE_BATCH_SEQ.NEXTVAL,'" + paramMap.get("REGISTER_SEQ") + "','" + admin.getCpnyId()	+ "',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP() + 
						"',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP()	+ "',0";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_EXPERIENCE_INSIDE_BATCH",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		this.excelUtilSer.importExcelData(request,response,map,modelMap);
		modelMap.put("refCurrent", "refCurrent");
		modelMap.put("navTabId", "hr0205");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	@RequestMapping(value="/importSalaryIncementTemp")
	public ModelAndView importSalaryIncementTemp(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");
		aliasValueMap.put("BASIC_SALARY", "#CELL2#");
		aliasValueMap.put("POSITION_SALARY", "#CELL3#");
		aliasValueMap.put("TRANSPORT_SALARY", "#CELL4#");


		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("BASIC_SALARY", this.DMY_VARCHAR);
		aliasTypeMap.put("POSITION_SALARY", this.VARCHAR);
		aliasTypeMap.put("TRANSPORT_SALARY", this.VARCHAR);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",SEQ,REGISTER_SEQ,CPNY_ID,CREATE_DATE,CREATED_BY,CREATED_IP,UPDATE_DATE,UPDATED_BY,UPDATED_IP,ACTIVITY";
		String appendValue=",HR_EXPERIENCE_INSIDE_BATCH_SEQ.NEXTVAL,'" + paramMap.get("REGISTER_SEQ") + "','" + admin.getCpnyId()	+ "',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP() + 
						"',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP()	+ "',0";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_EMPLOYEE_Y",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		this.excelUtilSer.importExcelData(request,response,map,modelMap);
		modelMap.put("refCurrent", "refCurrent");
		modelMap.put("navTabId", "hr0517");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  发令批量导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importExperienceTempHAE")
	public ModelAndView importExperienceTempHAE(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("LOCAL_NAME", "#CELL0#");
		aliasValueMap.put("EMPID", "#CELL1#");
		aliasValueMap.put("START_DATE", "#CELL2#");
		aliasValueMap.put("TRANS_CODE", "#CELL4#");
		aliasValueMap.put("TRANS_REASON", "#CELL7#");
		aliasValueMap.put("DEPTNO", "#CELL10#");
		aliasValueMap.put("POST_FAMILY", "#CELL13#");
		aliasValueMap.put("NEW_POST_GRADE_NO", "#CELL16#");
		aliasValueMap.put("POSITION_NO", "#CELL19#");
		aliasValueMap.put("EMP_TYPE_CODE", "#CELL22#");
		aliasValueMap.put("MAIN_BUSINESS", "#CELL25#");
		aliasValueMap.put("COST_CENTER", "#CELL28#");
		aliasValueMap.put("PAY_STEP_NO", "#CELL31#");
		aliasValueMap.put("REMARKS", "#CELL33#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.VARCHAR);
		aliasTypeMap.put("TRANS_CODE", this.VARCHAR);
		aliasTypeMap.put("TRANS_REASON", this.VARCHAR);
		aliasTypeMap.put("DEPTNO", this.VARCHAR);
		aliasTypeMap.put("POST_FAMILY", this.VARCHAR);
		aliasTypeMap.put("NEW_POST_GRADE_NO", this.VARCHAR);
		aliasTypeMap.put("POSITION_NO", this.VARCHAR);
		aliasTypeMap.put("EMP_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("MAIN_BUSINESS", this.VARCHAR);
		aliasTypeMap.put("COST_CENTER", this.VARCHAR);
		aliasTypeMap.put("PAY_STEP_NO", this.VARCHAR);
		aliasTypeMap.put("REMARKS", this.VARCHAR);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",SEQ,REGISTER_SEQ,CPNY_ID,CREATE_DATE,CREATED_BY,CREATED_IP,UPDATE_DATE,UPDATED_BY,UPDATED_IP,ACTIVITY";
		String appendValue=",HR_EXPERIENCE_INSIDE_BATCH_SEQ.NEXTVAL,'" + paramMap.get("REGISTER_SEQ") + "','" + admin.getCpnyId()	+ "',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP() + 
						"',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP()	+ "',0";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_EXPERIENCE_INSIDE_BATCH",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		this.excelUtilSer.importExcelData(request,response,map,modelMap);
		modelMap.put("refCurrent", "refCurrent");
		modelMap.put("navTabId", "hr0205");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  发令批量导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importActivityEmpTemp")
	public ModelAndView importActivityEmpTemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("EMPID", "#CELL0#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",SEQ,ACTIVITY_SEQ,CREATE_DATE,CREATED_BY,CREATED_IP,UPDATE_DATE,UPDATED_BY,UPDATED_IP,ACTIVITY";
		String appendValue=",HR_ACTIVITY_INFO_SEQ.NEXTVAL,'" + paramMap.get("ACTIVITY_SEQ")	+ "',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP() + 
						"',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP()	+ "',1";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_ACTIVITY_OBJECT",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		this.excelUtilSer.importExcelData(request,response,map,modelMap);
		modelMap.put("refCurrent", "refCurrent");
		modelMap.put("navTabId", "hr0205");
		String result = this.evsManageSer.addEnsInfoProcedure(request,"updateActivityEmpInfo");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  招聘批量导入 小时工
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importTempEmp")
	public ModelAndView importTempEmp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("LOCAL_NAME", "#CELL0#");
		aliasValueMap.put("DEPTNO", "#CELL2#");
		aliasValueMap.put("ID_CARD_NO", "#CELL4#");
		aliasValueMap.put("DOB", "#CELL5#");
		aliasValueMap.put("SEXCODE", "#CELL7#");
		aliasValueMap.put("PHONE", "#CELL9#");
		aliasValueMap.put("BANK_NO", "#CELL10#");
		aliasValueMap.put("DATE_STARTED", "#CELL11#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("DEPTNO", this.VARCHAR);
		aliasTypeMap.put("ID_CARD_NO", this.VARCHAR);
		aliasTypeMap.put("DOB", this.VARCHAR);
		aliasTypeMap.put("SEXCODE", this.VARCHAR);
		aliasTypeMap.put("PHONE", this.VARCHAR);
		aliasTypeMap.put("BANK_NO", this.VARCHAR);
		aliasTypeMap.put("DATE_STARTED", this.VARCHAR);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",SEQ,CPNY_ID,CREATE_DATE,CREATED_BY,CREATED_IP,UPDATE_DATE,UPDATED_BY,UPDATED_IP,ACTIVITY";
		String appendValue=",HR_TEMP_EMPLOYEE_SEQ.NEXTVAL,'" + admin.getCpnyId()	+ "',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP() + 
						"',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP()	+ "',0";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_TEMP_EMPLOYEE",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		this.excelUtilSer.importExcelData(request,response,map,modelMap);
		modelMap.put("refCurrent", "refCurrent");
		modelMap.put("navTabId", "ess3421");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 部门信息导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importDeptTemp")
	public ModelAndView importDeptTemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("DEPTNO", "#CELL0#");
		aliasValueMap.put("ORG_NAME_LOCAL", "#CELL1#");
		aliasValueMap.put("ORG_NAME_KO","#CELL2#");
		aliasValueMap.put("PARENT_DEPT_NO",  "#CELL4#");
		aliasValueMap.put("DEPT_TYPE", "#CELL7#");
		aliasValueMap.put("ORG_LEVEL", "#CELL9#");
		aliasValueMap.put("MANAGER_EMP_ID", "#CELL10#");
		aliasValueMap.put("IS_PART_TIME", "#CELL11#");
		aliasValueMap.put("WORK_AREA", "#CELL13#");
		aliasValueMap.put("DATE_CREATED", "#CELL15#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("DEPTNO", this.VARCHAR);
		aliasTypeMap.put("ORG_NAME_LOCAL", this.VARCHAR);
		aliasTypeMap.put("ORG_NAME_KO", this.VARCHAR);
		aliasTypeMap.put("PARENT_DEPT_NO", this.VARCHAR);
		aliasTypeMap.put("DEPT_TYPE", this.VARCHAR);
		aliasTypeMap.put("ORG_LEVEL", this.VARCHAR);
		aliasTypeMap.put("MANAGER_EMP_ID", this.VARCHAR);
		aliasTypeMap.put("IS_PART_TIME", this.VARCHAR);
		aliasTypeMap.put("WORK_AREA", this.VARCHAR);
		aliasTypeMap.put("DATE_CREATED", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",RESUME_NO,CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + request.getParameter("RESUME_NO")	+ "','" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("ORG_INFO_EXCEL_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/org/orgManage/viewImportDeptTempList";
		String navTabId = "org0203";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		this.orgManageSer.valImportExcelDeptData(request);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	@RequestMapping(value="/importApplyAttenance")
	public ModelAndView importApplyAttenance(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("APPLY_NAME", "#CELL1#");
		aliasValueMap.put("LEAVE_TYPE_CODE",  "#CELL3#");
		aliasValueMap.put("LEAVE_FROM_DATE", "#CELL4#");
		aliasValueMap.put("LEAVE_FROM_TIME", "#CELL5#");
		aliasValueMap.put("LEAVE_TO_DATE", "#CELL6#");
		aliasValueMap.put("LEAVE_TO_TIME", "#CELL7#");
		aliasValueMap.put("LEAVE_REASON", "#CELL8#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("APPLY_NAME", this.VARCHAR);
		aliasTypeMap.put("LEAVE_TYPE_CODE", this.VARCHAR);
		aliasTypeMap.put("LEAVE_FROM_DATE", this.VARCHAR);
		aliasTypeMap.put("LEAVE_FROM_TIME", this.VARCHAR);
		aliasTypeMap.put("LEAVE_TO_DATE", this.VARCHAR);
		aliasTypeMap.put("LEAVE_TO_TIME", this.VARCHAR);
		aliasTypeMap.put("LEAVE_REASON", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",AFFIRM_FLAG,CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'14014308','" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("ESS_LEAVE_APPLY_TB_EXCEL_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/ess/infoApplyAttendance/viewImportAttendanceTempList";
		String navTabId = "";
		/*if  (request.getParameter("RESUME_NO") != null) {
			navTabId = "ar0230";
		} else {*/
			navTabId = "ess3401";
		//}
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		this.infoApplyLeaveSer.valImportExcelAttendanceData(request);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	

	@RequestMapping(value="/importPaemp1")
	public ModelAndView importPaemp1(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{

		
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		this.pathCpnyID=admin.getCpnyId();
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("PERSON_ID", "#CELL0#");
		aliasValueMap.put("TRAINING_DIFFERENTIATE","#CELL2#");
		aliasValueMap.put("TRAINING_METHOD", "#CELL4#");
		aliasValueMap.put("START_DATE", "#CELL5#");
		aliasValueMap.put("END_DATE", "#CELL6#");
		aliasValueMap.put("PLACE", "#CELL7#");
		aliasValueMap.put("COURSE_NAME", "#CELL8#");
		aliasValueMap.put("TRAINING_RESULT", "#CELL10#");
		aliasValueMap.put("MARK", "#CELL11#");
		aliasValueMap.put("REMARKS", "#CELL12#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("PERSON_ID", this.VARCHAR);
		aliasTypeMap.put("TRAINING_DIFFERENTIATE", this.VARCHAR);
		aliasTypeMap.put("TRAINING_METHOD", this.VARCHAR);
		aliasTypeMap.put("START_DATE", this.DATE_HMS);
		aliasTypeMap.put("END_DATE", this.DATE_HMS);
		aliasTypeMap.put("PLACE", this.VARCHAR);
		aliasTypeMap.put("COURSE_NAME", this.VARCHAR);
		aliasTypeMap.put("TRAINING_RESULT", this.VARCHAR);
		aliasTypeMap.put("MARK", this.VARCHAR);
		aliasTypeMap.put("REMARKS", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",TRAIN_NO,ACTIVITY,CREATED_BY,CREATE_DATE";
		String appendValue=",hr_trans_no_seq.nextval,'1','" + admin.getPersonId()+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String sqlGetPersonId="SELECT  NVL (MAX(PERSON_ID),0) FROM HR_EMPLOYEE E WHERE E.EMPID=#CELL0# AND CPNY_ID='"+this.pathCpnyID+"'";
		aliasValueI18nMap.put("PERSON_ID", sqlGetPersonId);
		
		
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("HR_TRAINING_INFO",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		modelMap.put("navTabId", "edu0200");
		/*String forwardUrl = "/pa/workManagement/viewImportTrainList";
		String navTabId = "viewTrainingBasic";*/
		this.excelUtilSer.importExcelData(request,response,map,modelMap);
	
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	

	@RequestMapping(value="/importPaemp")
	public ModelAndView importPaemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");
		//aliasValueMap.put("DEPTNO","#CELL3#");
		aliasValueMap.put("ACCOUNT_TYPE", "#CELL3#");
		aliasValueMap.put("ACCOUNT_NO", "#CELL4#");
		//aliasValueMap.put("ACCOUNT_ADDRESS", "#CELL8#");
		aliasValueMap.put("ACCOUNT_NAME", "#CELL5#");
		aliasValueMap.put("SECURITY_NO", "#CELL6#");
		aliasValueMap.put("TAX_NO", "#CELL7#");
		//aliasValueMap.put("FUND_NO", "#CELL11#");
		//aliasValueMap.put("SECURITY_PAY_DATE", "#CELL12#");
		//aliasValueMap.put("FUND_PAY_DATE", "#CELL13#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		//aliasTypeMap.put("DEPTNO", this.VARCHAR);
		aliasTypeMap.put("ACCOUNT_TYPE", this.VARCHAR);
		aliasTypeMap.put("ACCOUNT_NO", this.VARCHAR);
		//aliasTypeMap.put("ACCOUNT_ADDRESS", this.VARCHAR);
		aliasTypeMap.put("ACCOUNT_NAME", this.VARCHAR);
		aliasTypeMap.put("SECURITY_NO", this.VARCHAR);
		aliasTypeMap.put("TAX_NO", this.VARCHAR);
		//aliasTypeMap.put("FUND_NO", this.VARCHAR);
		//aliasTypeMap.put("SECURITY_PAY_DATE", this.VARCHAR);
		//aliasTypeMap.put("FUND_PAY_DATE", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("PA_EMP_ACCOUNT_EXCEL_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/pa/workManagement/viewImportPaEmpAccountList";
		String navTabId = "pa0818";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		this.paEmpAccountSer.valImportExcelPaEmpAccountData(request);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	@RequestMapping(value="/importApplyOt")
	public ModelAndView importApplyOt(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("APPLY_NAME", "#CELL1#");
		aliasValueMap.put("APPLY_OT_DATE",  "#CELL2#");
		aliasValueMap.put("OT_FROM_TIME", "#CELL3#");
		aliasValueMap.put("OT_TO_TIME", "#CELL4#");
		aliasValueMap.put("DEDUCT_YN", "#CELL5#");
		aliasValueMap.put("OFFSET_YN", "#CELL6#");
		//aliasValueMap.put("ADJUST_YN", "#CELL6#");
		//aliasValueMap.put("SPECIAL_YN", "#CELL7#");
		aliasValueMap.put("APPLY_OT_REMARK", "#CELL7#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("APPLY_NAME", this.VARCHAR);
		aliasTypeMap.put("APPLY_OT_DATE", this.VARCHAR);
		aliasTypeMap.put("OT_FROM_TIME", this.VARCHAR);
		aliasTypeMap.put("OT_TO_TIME", this.VARCHAR);
		aliasTypeMap.put("DEDUCT_YN", this.VARCHAR);
		aliasTypeMap.put("OFFSET_YN", this.VARCHAR);
		//aliasTypeMap.put("ADJUST_YN", this.VARCHAR);
		//aliasTypeMap.put("SPECIAL_YN", this.VARCHAR);
		aliasTypeMap.put("APPLY_OT_REMARK", this.VARCHAR);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",AFFIRM_FLAG,CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'14014306','" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',TO_CHAR(sysdate,'YYYY-MM-DD')";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("ESS_OT_APPLY_TB_EXCEL_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/ess/infoApply/viewImportOtTempList";
		String navTabId = "ess3403";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		this.infoApplySer.valImportExcelOtData(request);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 店铺员工排班
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importShopShiftTemp")
	public ModelAndView importShopShiftTemp(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		
		String type = StringUtil.checkNull(request.getParameter("TYPE"));
		String tableName = "";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");
		aliasValueMap.put("AR_DATE_STR","#CELL2#");
		aliasValueMap.put("SHIFT_FROM_TIME",  "#CELL3#");
		aliasValueMap.put("SHIFT_TO_TIME", "#CELL4#");
		aliasValueMap.put("WORKING_HOUR", "#CELL5#");
		aliasValueMap.put("MINUS_LENGTH", "#CELL6#");
		aliasValueMap.put("ITEM_NO", "#CELL8#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("AR_DATE_STR", this.VARCHAR);
		aliasTypeMap.put("SHIFT_FROM_TIME", this.VARCHAR);
		aliasTypeMap.put("SHIFT_TO_TIME", this.VARCHAR);
		aliasTypeMap.put("WORKING_HOUR", this.VARCHAR);
		aliasTypeMap.put("MINUS_LENGTH", this.VARCHAR);
		aliasTypeMap.put("ITEM_NO", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE,TYPE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate,'" + type + "'" ;
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("AR_SHIFT_EXCEL_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		
		String forwardUrl = "/ess/tempEmp/viewImportShopShiftTempList";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,type);
		this.tempEmpSer.valImportExcelData(request);
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  招聘批量导入 小时工
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importTempEmpSalary")
	public ModelAndView importTempEmpSalary(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("LOCAL_NAME", "#CELL0#");
		aliasValueMap.put("DEPTNO", "#CELL2#");
		aliasValueMap.put("IDCARD_NO", "#CELL4#");
		aliasValueMap.put("CELLPHONE", "#CELL5#");
		aliasValueMap.put("PA_MONTH", "#CELL6#");
		aliasValueMap.put("NET_SALARY", "#CELL7#");
		aliasValueMap.put("REMARK", "#CELL8#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("DEPTNO", this.VARCHAR);
		aliasTypeMap.put("IDCARD_NO", this.VARCHAR);
		aliasTypeMap.put("CELLPHONE", this.VARCHAR);
		aliasTypeMap.put("PA_MONTH", this.VARCHAR);
		aliasTypeMap.put("NET_SALARY", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",SEQ,CPNY_ID,CREATE_DATE,CREATED_BY,CREATED_IP,UPDATE_DATE,UPDATED_BY,UPDATED_IP,ACTIVITY";
		String appendValue=",PA_TEMP_SELLER_INFO_SEQ.NEXTVAL,'" + admin.getCpnyId()	+ "',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP() + 
						"',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP()	+ "',1";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("PA_TEMP_SELLER_INFO",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		this.excelUtilSer.importExcelData(request,response,map,modelMap);
		this.tempEmpSer.addEnsInfoProcedure(request,"PR_SET_LS_EMPID");
		modelMap.put("refCurrent", "refCurrent");
		modelMap.put("navTabId", "ess3445");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  招聘批量导入 小时工
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importChangeShopFranchise")
	public ModelAndView importChangeShopFranchise(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");
		aliasValueMap.put("AR_DATE_STR", "#CELL2#");
		aliasValueMap.put("DEPT_NAME", "#CELL4#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("AR_DATE_STR", this.VARCHAR);
		aliasTypeMap.put("DEPT_NAME", this.VARCHAR);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",CPNY_ID,UPLOAD_BY,UPLOAD_DATE";
		String appendValue=",'" + admin.getCpnyId()	+ "','" + admin.getPersonId()	+ "',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("ESS_CHANGE_SHOP_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		this.excelUtilSer.importExcelData(request,response,map,modelMap);
		this.tempEmpSer.addEnsInfoProcedure(request,"PR_IMPORT_CHANGE_SHOP");
		modelMap.put("refCurrent", "refCurrent");
		modelMap.put("navTabId", "ess3447");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/**
	 * 导入数据  考勤表导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/importArSummary")
	public ModelAndView importArSummary(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		
		aliasValueMap.put("EMPID", "#CELL0#");
		aliasValueMap.put("LOCAL_NAME", "#CELL1#");
		aliasValueMap.put("PROBATION_DAYS", "#CELL2#");
		aliasValueMap.put("REGULAR_DAYS", "#CELL3#");
		aliasValueMap.put("WORK_SCHEDULE_DAYS", "#CELL4#");
		aliasValueMap.put("PROB_NIGHT_SHIFT_COUNT", "#CELL5#");
		aliasValueMap.put("NIGHT_SHIFT_COUNT", "#CELL6#");
		aliasValueMap.put("PROB_WORKDAYOT150_HOURS", "#CELL7#");
		aliasValueMap.put("WORKDAYOT150_HOURS", "#CELL8#");
		aliasValueMap.put("PROB_WORKDAYOT200_HOURS", "#CELL9#");
		aliasValueMap.put("WORKDAYOT200_HOURS", "#CELL10#");
		aliasValueMap.put("PROB_WORKDAYOT210_HOURS", "#CELL11#");
		aliasValueMap.put("WORKDAYOT210_HOURS", "#CELL12#");
		aliasValueMap.put("PROB_SATURDAYOT200_HOURS", "#CELL13#");
		aliasValueMap.put("SATURDAYOT200_HOURS", "#CELL14#");
		aliasValueMap.put("PROB_SATURDAYOT270_HOURS", "#CELL15#");
		aliasValueMap.put("SATURDAYOT270_HOURS", "#CELL16#");
		aliasValueMap.put("PROB_WEEKLYOT200_HOURS", "#CELL17#");
		aliasValueMap.put("WEEKLYOT200_HOURS", "#CELL18#");
		aliasValueMap.put("PROB_WEEKLYOT270_HOURS", "#CELL19#");
		aliasValueMap.put("WEEKLYOT270_HOURS", "#CELL20#");
		aliasValueMap.put("PROB_HOLIDAYOT300_HOURS", "#CELL21#");
		aliasValueMap.put("HOLIDAYOT300_HOURS", "#CELL22#");
		aliasValueMap.put("PROB_HOLIDAYOT390_HOURS", "#CELL23#");
		aliasValueMap.put("HOLIDAYOT390_HOURS", "#CELL24#");
		aliasValueMap.put("RESIGN_ANNUALLEAVE_PAY_DAYS", "#CELL25#");
		aliasValueMap.put("PROB_PERSONALLEAVE_DAYS", "#CELL26#");
		aliasValueMap.put("PERSONALLEAVE_DAYS", "#CELL27#");
		aliasValueMap.put("PROB_SICKLEAVE_DAYS", "#CELL28#");
		aliasValueMap.put("SICKLEAVE_DAYS", "#CELL29#");
		aliasValueMap.put("PROB_CHILD_SICKLEAVE_DAYS", "#CELL30#");
		aliasValueMap.put("CHILD_SICKLEAVE_DAYS", "#CELL31#");
		aliasValueMap.put("PROB_LONG_SICKLEAVE_DAYS", "#CELL32#");
		aliasValueMap.put("LONG_SICKLEAVE_DAYS", "#CELL33#");
		aliasValueMap.put("MATERNITYLEAVE_DAYS", "#CELL34#");
		aliasValueMap.put("PROB_ABSENTEEISM_DAYS", "#CELL35#");
		aliasValueMap.put("ABSENTEEISM_DAYS", "#CELL36#");
		aliasValueMap.put("PROB_LATE_ARRIVE_MINUTES", "#CELL37#");
		aliasValueMap.put("LATE_ARRIVE_MINUTES", "#CELL38#");
		aliasValueMap.put("PROB_EARLY_LEAVE_MINUTES", "#CELL39#");
		aliasValueMap.put("EARLY_LEAVE_MINUTES", "#CELL40#");
		aliasValueMap.put("UNPAIDLEAVE_DAYS", "#CELL41#");

		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPID", this.VARCHAR);
		aliasTypeMap.put("LOCAL_NAME", this.VARCHAR);
		aliasTypeMap.put("PROBATION_DAYS", this.VARCHAR);
		aliasTypeMap.put("REGULAR_DAYS", this.VARCHAR);
		aliasTypeMap.put("WORK_SCHEDULE_DAYS", this.VARCHAR);
		aliasTypeMap.put("PROB_NIGHT_SHIFT_COUNT", this.VARCHAR);
		aliasTypeMap.put("NIGHT_SHIFT_COUNT", this.VARCHAR);
		aliasTypeMap.put("PROB_WORKDAYOT150_HOURS", this.VARCHAR);
		aliasTypeMap.put("WORKDAYOT150_HOURS", this.VARCHAR);
		aliasTypeMap.put("PROB_WORKDAYOT200_HOURS", this.VARCHAR);
		aliasTypeMap.put("WORKDAYOT200_HOURS", this.VARCHAR);
		aliasTypeMap.put("PROB_WORKDAYOT210_HOURS", this.VARCHAR);
		aliasTypeMap.put("WORKDAYOT210_HOURS", this.VARCHAR);
		aliasTypeMap.put("PROB_SATURDAYOT200_HOURS", this.VARCHAR);
		aliasTypeMap.put("SATURDAYOT200_HOURS", this.VARCHAR);
		aliasTypeMap.put("PROB_SATURDAYOT270_HOURS", this.VARCHAR);
		aliasTypeMap.put("SATURDAYOT270_HOURS", this.VARCHAR);
		aliasTypeMap.put("PROB_WEEKLYOT200_HOURS", this.VARCHAR);
		aliasTypeMap.put("WEEKLYOT200_HOURS", this.VARCHAR);
		aliasTypeMap.put("PROB_WEEKLYOT270_HOURS", this.VARCHAR);
		aliasTypeMap.put("WEEKLYOT270_HOURS", this.VARCHAR);
		aliasTypeMap.put("PROB_HOLIDAYOT300_HOURS", this.VARCHAR);
		aliasTypeMap.put("HOLIDAYOT300_HOURS", this.VARCHAR);
		aliasTypeMap.put("PROB_HOLIDAYOT390_HOURS", this.VARCHAR);
		aliasTypeMap.put("HOLIDAYOT390_HOURS", this.VARCHAR);
		aliasTypeMap.put("RESIGN_ANNUALLEAVE_PAY_DAYS", this.VARCHAR);
		aliasTypeMap.put("PROB_PERSONALLEAVE_DAYS", this.VARCHAR);
		aliasTypeMap.put("PERSONALLEAVE_DAYS", this.VARCHAR);
		aliasTypeMap.put("PROB_SICKLEAVE_DAYS", this.VARCHAR);
		aliasTypeMap.put("SICKLEAVE_DAYS", this.VARCHAR);
		aliasTypeMap.put("PROB_CHILD_SICKLEAVE_DAYS", this.VARCHAR);
		aliasTypeMap.put("CHILD_SICKLEAVE_DAYS", this.VARCHAR);
		aliasTypeMap.put("PROB_LONG_SICKLEAVE_DAYS", this.VARCHAR);
		aliasTypeMap.put("LONG_SICKLEAVE_DAYS", this.VARCHAR);
		aliasTypeMap.put("MATERNITYLEAVE_DAYS", this.VARCHAR);
		aliasTypeMap.put("PROB_ABSENTEEISM_DAYS", this.VARCHAR);
		aliasTypeMap.put("ABSENTEEISM_DAYS", this.VARCHAR);
		aliasTypeMap.put("PROB_LATE_ARRIVE_MINUTES", this.VARCHAR);
		aliasTypeMap.put("LATE_ARRIVE_MINUTES", this.VARCHAR);
		aliasTypeMap.put("PROB_EARLY_LEAVE_MINUTES", this.VARCHAR);
		aliasTypeMap.put("EARLY_LEAVE_MINUTES", this.VARCHAR);
		aliasTypeMap.put("UNPAIDLEAVE_DAYS", this.VARCHAR);


		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		//模板外的其它字段设置
		//删除旧的工资支付计划
		int deleteResultNum=viewPaParamSer.updateArSummaryHTSV(request, "deleteArSummaryHTSVTemp");
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField=",PAY_SCHEDULE_NO,CPNY_ID,CREATE_DATE,CREATED_BY,CREATED_IP,UPDATE_DATE,UPDATED_BY,UPDATED_IP,ACTIVITY";
		String appendValue=",'" + paramMap.get("TYPE") + "','" + admin.getCpnyId()	+ "',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP() + 
						"',sysdate,'" + admin.getPersonId()	+ "','" + admin.getAdminIP()	+ "',0";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("AR_SUMMARY_HTSV_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);

		this.excelUtilSer.importExcelData(request,response,map,modelMap);
		
		int resultNum=viewPaParamSer.updateArSummaryHTSV(request, "updateArSummaryHTSV");
		int callResultNum=viewPaParamSer.updateArSummaryHTSV(request, "callArSummaryManageP");
		modelMap.put("refCurrent", "refCurrent");
		modelMap.put("navTabId", "pa1302");
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	
	/*评价Excel导入*/
	@RequestMapping(value="importEmpCalendarExcel")
	public ModelAndView importEmpCalendarExcel (HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		String resumeSEQ = request.getParameter("resumeSEQ");
		aliasValueMap.put("EMPLOYEE_ID", "#CELL0#");
		aliasValueMap.put("EMPLOYEE_NAME", "#CELL1#");
		aliasValueMap.put("AR_DATE_STR", "#CELL2#");
		aliasValueMap.put("DATE_TYPE", "#CELL4#");
		aliasValueMap.put("SHIFT_NO", "#CELL7#");
		aliasValueMap.put("REMARK", "#CELL9#");
		
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPLOYEE_ID", this.VARCHAR);
		aliasTypeMap.put("EMPLOYEE_NAME", this.VARCHAR);
		aliasTypeMap.put("AR_DATE_STR", this.VARCHAR);
		aliasTypeMap.put("DATE_TYPE", this.VARCHAR);
		aliasTypeMap.put("SHIFT_NO", this.VARCHAR);
		aliasTypeMap.put("REMARK", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField =  ",CPNY_ID,UPDATE_DATE,UPDATED_BY,UPDATED_IP,UPLOAD_BY,UPLOAD_DATE";
		String appendValue = ",'"+admin.getCpnyId()+"',sysdate"+",'"+admin.getAdminID()+"','"+admin.getAdminIP()+"','"+admin.getAdminID()+"',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String sqlI18nContent = "SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID = LTRIM(RTRIM(#CELL0#,' '),' ') AND CPNY_ID = '"+admin.getCpnyId()+"' ";
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("AR_SCHEDULE_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		String forwardUrl = "/ar/attendanceMintenance/viewEmpCalendarCheckInfoList";
		String navTabId = "ar0602";
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		this.empCalendarSer.submitImportExcelCalendarData(request, "PKG_ATT_EXCEL_EXP.PR_CALENDAR_VALID_DATA");
		/*String statusCode=(String) modelMap.get("statusCode");
		modelMap.put("UPLOAD_BY", admin.getAdminID());
		modelMap.put("CPNY_ID", admin.getCpnyId());
		if(statusCode.indexOf("200")>-1){
			this.excelUtilSer.deleteWithTarget(modelMap, "deleteEmpCalendar");
			this.excelUtilSer.insertWithTarget(modelMap, "insertEmpCalendar");
		}*/
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
	
	/*评价Excel导入*/
	@RequestMapping(value="importEvsAffirmTargetExcel")
	public ModelAndView importEvsAffirmTargetExcel (HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap aliasValueMap = new LinkedHashMap();
		String resumeSEQ = request.getParameter("resumeSEQ");
		aliasValueMap.put("EMPLOYEE_ID", "#CELL0#");
		aliasValueMap.put("EMPLOYEE_NAME", "#CELL1#");
		aliasValueMap.put("EVS_GRADE", "#CELL2#");
		//aliasValueMap.put("EVALUATOR_SCORE", "#CELL3#");
		//aliasValueMap.put("AFFIRM_CONTENT", "#CELL4#");
		
		LinkedHashMap aliasTypeMap = new LinkedHashMap();
		aliasTypeMap.put("EMPLOYEE_ID", this.VARCHAR);
		aliasTypeMap.put("EMPLOYEE_NAME", this.VARCHAR);
		aliasTypeMap.put("EVS_GRADE", this.VARCHAR);
		//aliasTypeMap.put("EVALUATOR_SCORE", this.VARCHAR);
		//aliasTypeMap.put("AFFIRM_CONTENT", this.VARCHAR);
		
		//模板外的其它字段设置
		LinkedHashMap aliasValueAppendMap = new LinkedHashMap();
		String appendField =  ",RESUME_SEQ,UPDATE_DATE,UPDATED_BY,UPDATED_IP,UPLOAD_BY,UPLOAD_DATE";
		String appendValue = ","+resumeSEQ+",sysdate"+",'"+admin.getAdminID()+"','"+admin.getAdminIP()+"','"+admin.getAdminID()+"',sysdate";
		aliasValueAppendMap.put("appendField", appendField);
		aliasValueAppendMap.put("appendValue", appendValue);
		
		LinkedHashMap aliasValueI18nMap = new LinkedHashMap();
		String sqlI18nContent = "SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID = LTRIM(RTRIM(#CELL0#,' '),' ') AND CPNY_ID = '"+admin.getCpnyId()+"' ";
		LinkedHashMap map=this.excelUtilSer.putIntoAliasValueAndTypeMapForTemple("EVS_AFFIRM_TEMP",aliasValueMap,aliasTypeMap,aliasValueI18nMap,aliasValueAppendMap);
		//String forwardUrl = "/ess/infoApply/viewPersonOverTimeLimtList";
		String forwardUrl = "";
		String navTabId = "ar0704";
		//this.excelUtilSer.importExcelOTLimitData(request,response,map,modelMap);//导入到临时页面
		this.excelUtilSer.importExcelData(request,response,map,modelMap,forwardUrl,navTabId);
		//int updateGrade = this.evsManageSer.addActivityInfo(request, "updateGrade");
		String statusCode=(String) modelMap.get("statusCode");
		modelMap.put("UPLOAD_BY", admin.getAdminID());
		modelMap.put("RESUME_SEQ", resumeSEQ);
		if(statusCode.indexOf("200")>-1){
			int objectList = this.evsManageSer.addActivityInfo(request, "modifyEvsAffirmFromTemp");
		}
		return new ModelAndView("/pa/excelImport/alertMsg",modelMap);
	}
}
