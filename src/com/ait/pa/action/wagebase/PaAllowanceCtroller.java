package com.ait.pa.action.wagebase;

import java.util.ArrayList;
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

import com.ait.ar.action.attendanceSettings.CycleCtroller;
import com.ait.ar.service.CycleSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.wagebase.PaAccountSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 
* @ClassName: PaAllowanceCtroller 
* @Description: TODO
* @author yuanxq@ait.net.cn
* @date 2014-6-25 下午05:33:26 
*
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "/pa/wagebase")
public class PaAllowanceCtroller {
	Logger logger = Logger.getLogger(PaAllowanceCtroller.class);
	@Autowired
	private PaAccountSer paAccountSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	private CompanySer companySer;
	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private AuthorityUtil util;
	@Autowired
    private CycleSer cycleSer;
	/**
	 * 职责津贴标准
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaAllowance")
	public ModelAndView viewPaAllowanceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int dataListCnt = paAccountSer.getAllowanceCnt(request);
		modelMap.put("personType",paAccountSer.getPersonType(request));
		modelMap.put("cpnyList", companySer.getAllCompanyItemList(request));
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("paBasicItemList", paAccountSer.getAllowanceList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, dataListCnt);
		
		modelMap.put("superUser", util.isSuperUser(admin.getPersonId()));
		modelMap.put("CORPORATION", request.getParameter("seach_CORPORATION")==null?admin.getCpnyId():request.getParameter("seach_CORPORATION"));
		
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "216698"));
		return new ModelAndView("/pa/wagebase/viewPaAllowance", modelMap);
	}
	
	/**
	 * 工资计算对象上传数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportPaAccountExcelList")
	public ModelAndView viewImportPaAccountExcelList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List itemList = paAccountSer.getPaAccountDataImportResultList(request);
		int impTotalCnt = paAccountSer.getPaAccountDataImportResultListCnt(request);
		int impErrCnt   = paAccountSer.getPaAccountDataImportResultListErrCnt(request);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
	modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "278660"));
		return new ModelAndView("/pa/wagebase/viewImportPaAccountExcelList", modelMap);
	}
	
	
	
	/**
	 * 职责津贴标准上传数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportPaAllowanceExcelTempList")
	public ModelAndView viewImportPaAllowanceExcelTempList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List itemList = paAccountSer.getEvaluationDataImportResultList(request);
		int impTotalCnt = paAccountSer.getEvaluationDataImportResultListCnt(request);	
		int impErrCnt   = paAccountSer.getEvaluationDataImportErrCnt(request);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "218434"));
		return new ModelAndView("/pa/wagebase/importPaAllowanceExcelTemp", modelMap);
	}
	
	/**
	 * 职责津贴个人标准上传数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportPaAllowanceExcelSelfList")
	public ModelAndView viewImportPaAllowanceExcelSelfList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List itemList = paAccountSer.getEvaluationDataImportResultSelfList(request);
		int impTotalCnt = paAccountSer.getEvaluationDataImportResultSelfListCnt(request);	
		int impErrCnt   = paAccountSer.getEvaluationDataImportErrSelfCnt(request);
		modelMap.put("MDATA", itemList);
		modelMap.put("errCnt", impErrCnt);
		modelMap.put("totalCnt", impTotalCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, impTotalCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217792"));
		return new ModelAndView("/pa/wagebase/viewImportPaAllowanceExcelSelfList", modelMap);
	}
	
	/**
	 * 职责津贴标准导出数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportPaAllowanceExcelTempListExcel")
	public void viewImportPaAllowanceExcelTempListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		/*List itemList = paAccountSer.getEvaluationDataImportResultList(request);
		modelMap.put("paBasicItemList", itemList);
		return new ModelAndView("/pa/wagebase/viewImportPaAllowanceExcelTempListExcel", modelMap);*/
		
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		aliasNameList.add("法人");
		aliasNameList.add("职责");
		aliasNameList.add("人员类型");
		aliasNameList.add("部门");
		aliasNameList.add("数值");
		aliasNameList.add("验证结果");
		List paTempSalesTempList = paAccountSer.getEvaluationDataImportResultList(request);
		for(int i=0;i<paTempSalesTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)paTempSalesTempList.get(i);
			map.put("CELL0", map1.get("CPNY_NAME"));
			map.put("CELL2", map1.get("DUTY_ALLOWANCE"));
			map.put("CELL3", map1.get("TYPE_ALLOWANCE"));
			map.put("CELL1", map1.get("DEPT_ID"));
			map.put("CELL4", map1.get("POSITION_ALLOWANCE"));
			map.put("CELL5", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
			list.add(map);
		}
		mapNameList.add("部门名称参考");
		mapNameList.add("人员类型参考");
		mapNameList.add("职责参考");
		mapList.add("SELECT ORG_NAME_LOCAL CONTENT FROM HR_DEPARTMENT WHERE ACTIVITY=1 AND CPNY_ID = '"+admin.getCpnyId()+"'ORDER BY DEPTNO");
		mapList.add("SELECT distinct NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 1368");
		mapList.add("SELECT DISTINCT POSITION_NO CONTENT FROM HR_EMPLOYEE HR WHERE HR.CPNY_ID = '"+admin.getCpnyId()+"' AND POSITION_NO IS NOT NULL");
		String name = "paAllowanceExcel";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	
	/**
	 * 工资计算对象导出临时数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportPaAccountExcelListExcel")
	public void viewImportPaAccountExcelListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		aliasNameList.add("社号");
		aliasNameList.add("员工姓名");
		aliasNameList.add("计算标识");
		aliasNameList.add("修改原因");
		aliasNameList.add("上传人");
		aliasNameList.add("验证结果");
		List paAccountTempList = paAccountSer.getPaAccountDataImportResultList(request);
		for(int i=0;i<paAccountTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)paAccountTempList.get(i);
			map.put("CELL0", map1.get("EMPID"));
			map.put("CELL1", map1.get("CHINESENAME"));
			map.put("CELL2", map1.get("CALC_FLAG"));
			map.put("CELL3", map1.get("UPDATE_REMARK"));
			map.put("CELL4", map1.get("UPLOAD_BY"));
			map.put("CELL5", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
			list.add(map);
		}
		String name = "paAccountExcel";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	
	
	/**
	 * 职责津贴标准导出数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewImportPaAllowanceExcelSelfListExcel")
	public void viewImportPaAllowanceExcelSelfListExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		/*List itemList = paAccountSer.getEvaluationDataImportResultSelfList(request);
		modelMap.put("paBasicItemList", itemList);
		return new ModelAndView("/pa/wagebase/viewImportPaAllowanceExcelSelfListExcel", modelMap);*/
		
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		aliasNameList.add("社号");
		//aliasNameList.add("姓名");
		//aliasNameList.add("数值");
		aliasNameList.add("支付比例");
		//aliasNameList.add("开始月");
		aliasNameList.add("有效月数");
		aliasNameList.add("备注(可以为空)");
		aliasNameList.add("验证结果");
		List paTempSalesTempList = paAccountSer.getEvaluationDataImportResultSelfList(request);
		for(int i=0;i<paTempSalesTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)paTempSalesTempList.get(i);
			map.put("CELL0", map1.get("EMPID"));
			//map.put("CELL1", map1.get("CHINESENAME"));
			//map.put("CELL2", map1.get("POSITION_ALLOWANCE"));
			map.put("CELL1", map1.get("PERCENT_ALLOWANCE"));
			//map.put("CELL4", map1.get("START_DATE"));
			map.put("CELL2", map1.get("VALID_MONTH"));
			map.put("CELL3", map1.get("DEMO_ALLOWANCE"));
			map.put("CELL4", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
			list.add(map);
		}
		String name = "paAllowSelfExcel";
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
	
	/**
	 * 职责津贴标准导出数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createImportPaAllowanceExcelTempListExcel")
	@ResponseBody
	public int createEvaluationDataImportResult(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = paAccountSer.importPaAllowanceExcelTempExcel(request);
		return result.equals("OK")?1:0;
	}
	
	/**
	 * 职责津贴个人导出数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createImportPaAllowanceExcelSelfListExcel")
	@ResponseBody
	public int createImportPaAllowanceExcelSelfListExcel(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = paAccountSer.importPaAllowanceExcelSelfExcel(request);
		return result.equals("OK")?1:0;
	}
	
	/**
	 * 提交工资计算对象数据
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createImportPaAccountExcelListExcel")
	@ResponseBody
	public int createImportPaAccountExcelListExcel(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap) throws Exception{
		String result = paAccountSer.importPaAccountExcelExcel(request);
		return result.equals("OK")?1:0;
	}
	
	/**
	 * 职责津贴标准(个人)
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaAllowanceSelf")
	public ModelAndView viewPaAllowanceSelfList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

        String seach_FIRST_FLAG = request.getParameter("seach_FIRST_FLAG");
        if(seach_FIRST_FLAG != null && !"".equals(seach_FIRST_FLAG)){
    		modelMap.put("dataList", paAccountSer.getPersonAllowance(request));
    		int dataListCnt = paAccountSer.getPersonAllowanceCnt(request);
    		modelMap.put(UiUtil.TOTAL_COUNT_NAME, dataListCnt);
        }
        
		modelMap.put("personType",paAccountSer.getPersonType(request));
		modelMap.put("cpnyList", companySer.getAllCompanyItemList(request));
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("superUser", util.isSuperUser(admin.getPersonId()));
		modelMap.put("CORPORATION", request.getParameter("seach_CORPORATION")==null?admin.getCpnyId():request.getParameter("seach_CORPORATION"));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217792"));
		return new ModelAndView("/pa/wagebase/viewPaAllowanceSelf", modelMap);
	}
	/**
	 * 修改个人职责津贴
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 上午11:45:18 
	* @version V1.0
	 */
	@RequestMapping(value = "/updateViewPaAllowanceSelf")
	public ModelAndView updateViewPaAllowanceSelf(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Object paBasicItemParamInfo = paAccountSer.getPaAllowanceSelf(request);
		modelMap.put("PERSON", paBasicItemParamInfo);
		return new ModelAndView("/pa/wagebase/updateViewPaAllowanceSelf",modelMap);
	}
	
	@RequestMapping(value = "/updatePaAllowanceSelfInfo")
	@ResponseBody
	public Map updatePaAllowanceSelfInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = paAccountSer.updatePaAllowanceSelfInfo(request);
			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"liang.alert.message.ess.trans.Successful_operation", request));
				map.put("navTabId", "pa0509");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"liang.alert.message.ess.trans.operation_failure", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.operation_failure", request));
		}
		return map;
	}
	
	/**
	 * 转向职责津贴标准添加页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 下午01:52:59 
	* @version V1.0
	 */
	@RequestMapping(value="addPaAllowance")
	public ModelAndView addPaAllowanceView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		String allowanceId = request.getParameter("ALLOWANCE_ID");
		if(allowanceId != null){
			Object paBasicItemParamInfo = paAccountSer.getPaAllowance(request);
			modelMap.put("ALLOWANCE", paBasicItemParamInfo);
		}
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
		modelMap.put("personType",paAccountSer.getPersonType(request));
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		return new ModelAndView("/pa/wagebase/addPaAllowance",modelMap);
	}
	
	/**
	 * 保存职责津贴标准
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 下午01:52:59 
	* @version V1.0
	 */
	@RequestMapping(value="addPaAllowanceInfo")
	@ResponseBody
	public Map addPaAllowanceViewInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String allowanceId = request.getParameter("ALLOWANCE_ID");
			int errorInt = 0;
			if("".equals(allowanceId) || ""==allowanceId || allowanceId == null){
				errorInt = paAccountSer.insertPaAllowanceInfo(request);
			}else {
				errorInt = paAccountSer.updatePaAllowanceInfo(request);
			}
			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"liang.alert.message.ess.trans.Successful_operation", request));
				map.put("navTabId", "pa0508");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"liang.alert.message.ess.trans.operation_failure", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.operation_failure", request));
		}
		return map;
	}
	
	/**
	 * 修改职责津贴标准
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 上午11:45:18 
	* @version V1.0
	 */
	@RequestMapping(value = "/updatePaAllowance")
	public ModelAndView updateViewPaAllowance(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Object paBasicItemParamInfo = paAccountSer.getPaAllowance(request);
		modelMap.put("PERSON", paBasicItemParamInfo);
		return new ModelAndView("/pa/wagebase/addPaAllowance",modelMap);
	}
	
	/**
	 * 删除工资基础输入项目信息(delete Pa Basic Item Param Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePaAllowance")
	@ResponseBody
	public Map deletePaAllowanceInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int resultNum = paAccountSer.deletePaAllowanceInfo(request);
			if (resultNum == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"liang.alert.message.ess.trans.Successful_operation", request));
				map.put("navTabId", "pa0508");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"liang.alert.message.ess.trans.operation_failure", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"liang.alert.message.ess.trans.operation_failure", request));
		}
		return map;
	}
	
	/**
	 * 职责津贴标准导出Excel
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaAllowanceExcel")
	public void viewPaAllowanceExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add("法人");
		aliasNameList.add("部门");
		aliasNameList.add("职责");
		aliasNameList.add("人员类型");
		aliasNameList.add("数值");		
		//列名
		String[] columns = {"CPNY_NAME","DEPT_ID","DUTY_ALLOWANCE","TYPE_ALLOWANCE","POSITION_ALLOWANCE"};
		List aliasValueList  = paAccountSer.getAllowanceList(request);
		String name = "paAllowanceDataList";
		this.excelUtilSer.exportExcelByNamePwd(request,response,modelMap,aliasValueList,aliasNameList,columns,name,searchMap);
	}
	
	/**
	 * 职责津贴个人标准导出Excel
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaAllowanceSelfExcel")
	public void viewPaAllowanceSelfExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{		
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		String lang=searchMap.get("interLanguage").toString();
		//设置excel header
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.EMPID",lang));//社号
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPersonalInfo.title.LOCAL_NAME",lang));//姓名
		aliasNameList.add("G"+TipMessage.getTipMessage("pa.insurance.title.dataValue",lang));//数值
		if("TSTO".equals(admin.getCpnyId()) || admin.getCpnyId()=="TSTO"){
			aliasNameList.add("C数值");//支付比例
		}
		aliasNameList.add(TipMessage.getTipMessage("pa.allowance.zhifubili",lang));//支付比例
		aliasNameList.add(TipMessage.getTipMessage("display.emp.ben.transdate",lang));//发令日期
		aliasNameList.add(TipMessage.getTipMessage("pa.allowance.youxiaoqiyueshu",lang));//有效月数
		aliasNameList.add(TipMessage.getTipMessage("ar.viewarcardrecord.title.beizhu",lang));//备注
		
		List aliasValueList  = paAccountSer.getPersonAllowance(request);
		String name = "paAllowanceSelfDataList";
		//列名
		if("TSTO".equals(admin.getCpnyId()) || admin.getCpnyId()=="TSTO"){
			String[] columns = {"EMPID","CHINESENAME","POSITION_ALLOWANCE","POSITION_ALLOWANCE_C","PERCENT_ALLOWANCE","START_DATE","VALID_MONTH","DEMO_ALLOWANCE"};
			this.excelUtilSer.exportExcelByNamePwd(request,response,modelMap,aliasValueList,aliasNameList,columns,name,searchMap);
		}else{
			String[] columns = {"EMPID","CHINESENAME","POSITION_ALLOWANCE","PERCENT_ALLOWANCE","START_DATE","VALID_MONTH","DEMO_ALLOWANCE"};
			this.excelUtilSer.exportExcelByNamePwd(request,response,modelMap,aliasValueList,aliasNameList,columns,name,searchMap);
		}
	}
	/**
	 * 职责津贴个人标准导出Excel
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaGradeHaoFengExcel")
	public ModelAndView viewPaGradeHaoFengExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("dataList", paAccountSer.getPersonAllowance(request));
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "217792")) ;
		return new ModelAndView("/pa/wagebase/viewHaoFengSetListExcel",modelMap);
	}
	/**
	 * 职责津贴个人标准导出Excel模版
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportPaAllowanceSelfExcelModule")
	public void exportPaAllowanceSelfExcelModule(HttpServletRequest request, 
			HttpServletResponse response,ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId", request));//工号
		//aliasNameList.add(TipMessage.getTipMessage("public.title.name", request));//姓名
		//aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		aliasNameList.add(TipMessage.getTipMessage("pa.allowance.zhifubili", request));//比例
		//aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.startMonth", request));//开始月
		aliasNameList.add(TipMessage.getTipMessage("pa.allowance.youxiaoyue", request));//有效月
		aliasNameList.add(TipMessage.getTipMessage("hr.viewPromote.title.REMARK", request)+TipMessage.getTipMessage("liang.pa.title.Allow_Nulls", request));//备注
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "1200001");
		//map.put("CELL1", "测试李");
		//map.put("CELL2", "800");
		map.put("CELL1", "80");
		//map.put("CELL4", "2010/09/01");
		map.put("CELL2", "3");
		map.put("CELL3", "备注");
		list.add(map);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		//this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
		modelMap.put("FileName", "temp_PaPersonalAllowance");
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,new ArrayList(),new ArrayList());
	}
	
	/**
	 * 职责津贴个人标准导出Excel模版
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportPaAllowanceExcelModule")
	public void exportPaAllowanceExcelModule(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		List aliasNameList = new ArrayList();
		aliasNameList.add(TipMessage.getTipMessage("sys.essParam.title.legalPerson", request));//法人
		aliasNameList.add(TipMessage.getTipMessage("ess.infoApply.title.dutyName", request));//职责
		aliasNameList.add(TipMessage.getTipMessage("is.company.title.PERSON_TYPE", request));//人员类型
		aliasNameList.add(TipMessage.getTipMessage("ar.attendanceView.viewNoSwipingCard.deptName", request));//部门
		aliasNameList.add(TipMessage.getTipMessage("pa.insurance.title.dataValue", request));//数值
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "TSTO");
		map.put("CELL1", "Manager");
		map.put("CELL2", "专门职");
		map.put("CELL3", "XXX部门");
		map.put("CELL4", "80");
		list.add(map);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		List mapNameList = new ArrayList();
		mapNameList.add("部门名称参考");
		mapNameList.add("人员类型参考");
		mapNameList.add("职责参考");
		
		List mapList = new ArrayList();
		String deptSql = "SELECT ORG_NAME_LOCAL CONTENT FROM HR_DEPARTMENT WHERE ACTIVITY=1 AND CPNY_ID = '"+admin.getCpnyId()+"'ORDER BY DEPTNO";
		String typeSql = "SELECT S1.NO CODE_NO, NVL(S1.CONTENT, ' ') CONTENT FROM HR_JOB_TYPE_SETUP T, SY_GLOBAL_NAME S1, SY_GLOBAL_NAME S2" +
				" WHERE T.JOBTYPE_NO = S1.NO(+) AND S1.LANGUAGE(+) = 'zh' AND T.JOBTYPE_GROUP_NO = S2.NO(+) AND S2.LANGUAGE(+) = 'zh'" +
				" AND S2.NO = '211812' ORDER BY T.CPNY_ID";
		String positionSql = "SELECT DISTINCT POSITION_NO CONTENT FROM HR_EMPLOYEE HR WHERE HR.CPNY_ID = '"+admin.getCpnyId()+"' AND POSITION_NO IS NOT NULL";
		mapList.add(deptSql);
		mapList.add(typeSql);
		mapList.add(positionSql);
		
		//this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
		modelMap.put("FileName", "temp_PaAllowance");
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList);
	}
	
	/**
	 * 工资计算对象导出Excel模版
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportPaAccountExcelModule")
	public void exportPaAccountExcelModule(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		List aliasNameList = new ArrayList();
		aliasNameList.add("社号(必填)");
		aliasNameList.add("员工姓名(可为空)");
		aliasNameList.add("计算标识");
		aliasNameList.add("修改备注(可为空)");
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0", "12000003");
		map.put("CELL1", "张某某");
		map.put("CELL2", "Y");
		map.put("CELL3", "人员离职");
		list.add(map);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		List mapNameList = new ArrayList();
		List mapList = new ArrayList();
		//this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
		modelMap.put("FileName", "temp_PaAccount");
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList);
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
	@RequestMapping(value = "/importPaAllowanceData")
	public ModelAndView importArItemData(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		modelMap.put("id", request.getParameter("id"));
		modelMap.put("type", request.getParameter("type"));
		modelMap.put("importFunName", request.getParameter("importFunName"));
		return new ModelAndView("/ar/excelImport/importArItemData",modelMap);		
	} 
	
	
	
}
