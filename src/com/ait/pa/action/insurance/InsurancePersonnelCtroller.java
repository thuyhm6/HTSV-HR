package com.ait.pa.action.insurance;

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

import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.insurance.InsurancePersonnelSer;
import com.ait.pa.service.wagebase.PaAccountSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: InsurancePersonnelCtroller.java
 * @Description:
 * @Create date: 2012-2-10 下午06:13:03
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/insurance")
public class InsurancePersonnelCtroller {
	Logger logger = Logger.getLogger(InsurancePersonnelCtroller.class);

	@Autowired
	private InsurancePersonnelSer insurancePersonnelSer;

	@Autowired
	private CompanySer companySer;

	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private ExcelUtilSer excelUtilSer;
	
	/**---------------参保对象-------start--------*/
	/**
	 * 跳转到参保人员页面（view Insurance Personnel）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewInsurancePersonnel")
	public ModelAndView viewInsurancePersonnelList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		List isPersonnelList = this.insurancePersonnelSer.getInsurancePersonnelList(request);
		int isPersonnelCnt = this.insurancePersonnelSer.getInsurancePersonnelCnt(request);
		
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin.getCpnyId() : paramMap.get("CPNY_ID").toString());
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("compList", this.companySer.getCompanyItemAllList(request));
		modelMap.put("isPersonnelList", isPersonnelList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, isPersonnelCnt);
		modelMap.put("toolbarInfo",request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : 
			toolMenuSer.getToolMenuForNo(request, "2496"));

		return new ModelAndView("/pa/insurance/viewInsurancePersonnel",modelMap);
	}

	@RequestMapping(value = "/updateIsCalcFlagByPersonId")
	@ResponseBody
	public String updateIsCalcFlagByPersonId(HttpServletRequest request)throws Exception{
		String result = "";
		if (this.insurancePersonnelSer.updateIsCalcFlagByPersonId(request)==1){
			result = "Y";
		}else{
			result = "N";
		}
		return result;
	}
	
	/**
	 * 跳转到添加参保人员页面（add Insurance Personnel View）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsurancePersonnelView")
	public ModelAndView updateInsurancePersonnelView(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) throws Exception {
		Object isPersonnel = this.insurancePersonnelSer.getInsurancePersonnelInfo(request);
		modelMap.put("isPersonnel", isPersonnel);

		return new ModelAndView("/pa/insurance/updateInsurancePersonnelView",modelMap);
	}

	/**
	 * 添加参保人员（add Insurance Personnel Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsurancePersonnelInfo")
	@ResponseBody
	public Map<String, Object> updateInsurancePersonnelInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.insurancePersonnelSer.updateInsurancePersonnelInfo(request);
			if (errorInt == 0) {
				map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
				map.put("navTabId", "pa0401");
				map.put("statusCode", "200");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 * 导出参保人员数据（view Insurance Personnel）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewInsurancePersonnelTranserExcel")
	public ModelAndView viewInsurancePersonnelTranserExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		List isPersonnelList = this.insurancePersonnelSer.getInsurancePersonnelList(request);
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin.getCpnyId() : paramMap.get("CPNY_ID").toString());
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("compList", this.companySer.getCompanyItemAllList(request));
		modelMap.put("isPersonnelList", isPersonnelList);

		return new ModelAndView("/pa/insurance/viewInsurancePersonnelTranserExcel",modelMap);
	}
	
	/**
	 * 查询所有参保人员
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lufeng@ait.net.cn
	* @date 2014-8-28 下午16:21:52
	* @version V1.0
	 */
	@RequestMapping(value = "/viewInsuranceObject")
	public ModelAndView viewInsuranceObjectList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		modelMap.put("isCalcList", this.insurancePersonnelSer.getInsuranceObjectList(request) );
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.insurancePersonnelSer.getInsuranceObjectListCnt(request) ) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "123443")) ;
		
		return new ModelAndView("/pa/insurance/viewInsuranceObject",modelMap);
	}
	
	/**
	 * 导出账户信息列表excel（get view Pa Account List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewInsuranceObjectExcel")
	public ModelAndView viewInsObjectExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		modelMap.put("isCalcList", this.insurancePersonnelSer.getInsuranceObjectList(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
		toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "123443")) ;
		return new ModelAndView("/pa/insurance/viewInsuranceObjectExcel",modelMap);
	}
	
	/**
	 * 保险计算对象
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lufeng@ait.net.cn
	* @date 2014-8-28 下午16:21:52
	* @version V1.0
	 */
	@RequestMapping("/insuranceCalculationObject")
	@ResponseBody
	public String bonusCalculationObject(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String result = "";
		if (this.insurancePersonnelSer.initInsCalcObject(request)==1){//保险维护-->参保人员
			result = "Y";
		}else{
			result = "N";
		}
		return result;
	}
	
	/**
	 * 删除参保人员（delete Insurance Personnel Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteViewInsuranceObject")
	@ResponseBody 
	public Map<String, Object> deleteViewInsuranceObject(HttpServletRequest request,
				HttpServletResponse response)throws Exception { 
		Map<String, Object> map = new HashMap<String,Object>(); 
		int errorInt=this.insurancePersonnelSer.deleteInsurancePersonnelInfo(request);
	    if(errorInt==1){
		    map.put("statusCode", "200"); 
		    map.put("message", "删除成功");
		    map.put("navTabId", "pa0413");
	    }else {
		    map.put("statusCode", "300"); 
		    map.put("message", "删除失败"); 
	    }
	    return map; 
	}
	
	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lufeng@ait.net.cn
	* @date 2014-8-28 下午16:21:52
	* @version V1.0
	 */
	@RequestMapping(value = "/updateInsCalcFlagByPersonId")
	@ResponseBody
	public String updateInsCalcFlagByPersonId(HttpServletRequest request)throws Exception{
		String result = "";
		if (this.insurancePersonnelSer.updateInsCalcFlagByPersonId(request)==1){//保险维护-->参保人员
				result = "Y";
			}else{
				result = "N";
			}
		return result;
	}
	/**
	 * 更新计算标识（公积金）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInsCalcGJJFlagByPersonId")
	@ResponseBody
	public String updateInsCalcGJJFlagByPersonId(HttpServletRequest request)throws Exception{
		String result = "";
		if (this.insurancePersonnelSer.updateInsCalcGJJFlagByPersonId(request)==1){//保险维护-->参保人员
				result = "Y";
			}else{
				result = "N";
			}
		return result;
	}
	
	/**
	 * 单条删除保险计算对象( delete insurance object)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delInsCalcObject")
	@ResponseBody
	public String delInsCalcObject(HttpServletRequest request) throws Exception {
		String result = "";
		boolean bol = insurancePersonnelSer.delInsCalcObject(request);
		if (bol) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
	
	
	/**
	 * 跳转到修改页面(updatePaComputeItemView)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateInsuranceObjectView")
	public ModelAndView updateInsuranceObjectView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Object insuranceObject = this.insurancePersonnelSer.getInsuranceObjectInfo(request);
		@SuppressWarnings("unused")
		Map map = new HashMap();
		map = (HashMap)insuranceObject;
		modelMap.put("insuranceObject", insuranceObject);
		modelMap.put("pageNum", request.getParameter("pageNum"));
		
		return new ModelAndView("/pa/insurance/updateInsuranceObjectView",modelMap);
	}
	
	/**
	 * 执行修改（updatePaAccountInfo）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateInsuranceObjectInfo")
	@ResponseBody
	public Map updateInsuranceObjectInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt=this.insurancePersonnelSer.updateInsuranceObjectInfo(request) ;
		if(errorInt==1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request) );
			map.put("forwardUrl", "/pa/insurance/viewInsuranceObject?pageNum="+request.getParameter("insurancePageNum")
					+"&menuNo=123443&navTabId=pa0413&seach_INSURANCE_GIVE_DATE="+request.getParameter("INSURANCE_GIVE_DATE")
					+"&seach_insYear="+request.getParameter("insurancePaYear")+"&seach_insMonth="+request.getParameter("insurancePaMonth"));
			map.put("navTabId", "pa0413");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request) );
		}
		return map;
	}
	
	/**
	 * 保险计算对象导入临时结果页面--查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewInsCalcObjectImportList")
	public ModelAndView viewInsCalcObjectImportList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List insObjTempList = this.insurancePersonnelSer.getInsCalcObjectTempList(request) ;
		int insObjTempCnt = this.insurancePersonnelSer.getInsCalcObjectTempCnt(request) ;
		int errorCnt = this.insurancePersonnelSer.getInsCalcObjectTempErrorCnt(request) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("insObjTempList", insObjTempList);
		modelMap.put("errCnt", errorCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, insObjTempCnt);
		
		return new ModelAndView("/pa/insurance/viewInsCalcObjectImportList",modelMap);
	}
	
	/**
	 * 导出保险计算对象临时表里的所有保险计算对象数据信息，进行修改，然后再导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/expInsObjectDataExcel")
	public void expInsObjectDataExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();
		aliasNameList.add("工资月");
		aliasNameList.add("社号");
		aliasNameList.add("姓名");
		aliasNameList.add("部门");
		
		aliasNameList.add("计算标识(社保)");
		if(admin.getCpnyId().equals("LGEHN")){
			aliasNameList.add("计算标识(公积金)");
		}
		aliasNameList.add("备注");
		aliasNameList.add("正/异常");
		aliasNameList.add("错误提示");
		
		List dataList = new ArrayList();
		List insObjectImportList = this.insurancePersonnelSer.getInsCalcObjectTempList(request);
		int j = 4;
		for(int i=0;i<insObjectImportList.size();i++){
			LinkedHashMap insObjMap = new LinkedHashMap();
			insObjMap = (LinkedHashMap)insObjectImportList.get(i);
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", insObjMap.get("PA_MONTH")!=null?insObjMap.get("PA_MONTH").toString():"");
			map.put("CELL1", insObjMap.get("PERSON_ID")!=null?insObjMap.get("PERSON_ID").toString():"");
			map.put("CELL2", insObjMap.get("LOCAL_NAME")!=null?insObjMap.get("LOCAL_NAME").toString():"");
			map.put("CELL3", insObjMap.get("DEPT_NAME")!=null?insObjMap.get("DEPT_NAME").toString():"");
			map.put("CELL"+j, insObjMap.get("CALC_FLAG")!=null?insObjMap.get("CALC_FLAG").toString():"");
			if(admin.getCpnyId().equals("LGEHN")){
			map.put("CELL"+j+1, insObjMap.get("CALC_GJJ_FLAG")!=null?insObjMap.get("CALC_GJJ_FLAG").toString():"");
			j=j+1;
			}
			map.put("CELL"+j+1, insObjMap.get("REMARK")!=null?insObjMap.get("REMARK").toString():"");
			String checkFlag = "";
			if(insObjMap.get("CHECK_FLAG")!=null && "1".equals(insObjMap.get("CHECK_FLAG").toString())){
				checkFlag = "异常";
			}else if(insObjMap.get("CHECK_FLAG")!=null && "0".equals(insObjMap.get("CHECK_FLAG").toString())){
				checkFlag = "正常";
			}
			map.put("CELL"+j+2, checkFlag);
			map.put("CELL"+j+3, insObjMap.get("CHECK_ERROR")!=null?insObjMap.get("CHECK_ERROR").toString():"");
			
			dataList.add(map);
		}
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(dataList);
		this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
	}
	
	/**
	 * 保存导入的保险计算对象数据 (import insurance object data)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveInsObjectDataImport")
	@ResponseBody
	public Map saveInsObjectDataImport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = insurancePersonnelSer.addImportInsObjectData(request);
			if (result == 1) {
				map.put("navTabId", "pa0413");
				map.put("message", "导入保险计算对象保存成功!");//保存导入保险计算对象数据成功!
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
				//map.put("forwardUrl","/pa/insurance/viewInsCalcObjectImportList");
			}else{
				map.put("message", "导入的保险计算对象数据有错误,请修改!");//导入的保险计算对象数据有错误,请修改!
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "导入保险计算对象保存失败,请重试!");//导入保险计算对象数据失败,请重试!
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 取消所有导入的保险计算对象( delete insurance object import)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author lufeng
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cancelInsObjectImport")
	@ResponseBody
	public Map cancelInsObjectImport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();

		String msg= ""+this.insurancePersonnelSer.cancelInsObjectImport(request);
		if("1".equals(msg)){
			map.put("statusCode", "200");
			map.put("message", "取消成功！");//保存成功
			map.put("navTabId", "pa0413");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", "取消失败！");//保存失败
		}
		return map;
	}

	/**
	 * 单条删除导入的保险计算对象( delete insurance object)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delInsObjectImport")
	@ResponseBody
	public String delInsObjectImport(HttpServletRequest request) throws Exception {
		String result = "";
		boolean bol = insurancePersonnelSer.delInsObjectImport(request);
		if (bol) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
	
	/**---------------参保对象-------end--------*/
	
	
	
	
	
	/**---------------公积金计算对象-------start--------*/
	/**
	 * 查询所有公积金计算人员
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author lufeng@ait.net.cn
	* @date 2014-9-22 下午04:51:10 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewFundObject")
	public ModelAndView viewFundObjectList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		modelMap.put("isCalcList", this.insurancePersonnelSer.getFundObjectList(request) );
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.insurancePersonnelSer.getFundObjectListCnt(request) ) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "477")) ;
		
		return new ModelAndView("/pa/insurance/viewFundObject",modelMap);
	}
	
	/**
	 * 导出公积金计算信息列表excel（get view Pa Account List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewFundObjectExcel")
	public ModelAndView viewFundObjectExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		modelMap.put("isCalcList", this.insurancePersonnelSer.getFundObjectList(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
		toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "477")) ;
		
		return new ModelAndView("/pa/insurance/viewFundObjectExcel",modelMap);
	}
	
	/**
	 * 公积金计算对象
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author lufeng@ait.net.cn
	* @date 2014-9-22 下午04:51:10
	* @version V1.0
	 */
	@RequestMapping("/fundCalculationObject")
	@ResponseBody
	public String fundCalculationObject(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String result = "";
		if (this.insurancePersonnelSer.initFundCalcObject(request)==1){//保险维护-->参保人员
			result = "Y";
		}else{
			result = "N";
		}
		return result;
	}
	
	/**
	 * 删除公积金计算人员（delete Insurance Personnel Info）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteViewFundObject")
	@ResponseBody 
	public Map<String, Object> deleteViewFundObject(HttpServletRequest request,
				HttpServletResponse response)throws Exception { 
		Map<String, Object> map = new HashMap<String,Object>(); 
		int errorInt=this.insurancePersonnelSer.deleteFundPersonnelInfo(request);
	    if(errorInt==1){
		    map.put("statusCode", "200"); 
		    map.put("message", "删除成功");
		    map.put("navTabId", "pa0421");
	    }else {
		    map.put("statusCode", "300"); 
		    map.put("message", "删除失败"); 
	    }
	    return map; 
	}
	
	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @author lufeng@ait.net.cn
	* @date 2014-9-22 下午04:51:10
	* @version V1.0
	 */
	@RequestMapping(value = "/updateFundCalcFlagByPersonId")
	@ResponseBody
	public String updateFundCalcFlagByPersonId(HttpServletRequest request)throws Exception{
		String result = "";
		if (this.insurancePersonnelSer.updateFundCalcFlagByPersonId(request)==1){//保险维护-->公积金人员
				result = "Y";
			}else{
				result = "N";
			}
		return result;
	}
	
	/**
	 * 单条删除公积金计算对象( delete insurance object)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delFundCalcObject")
	@ResponseBody
	public String delFundCalcObject(HttpServletRequest request) throws Exception {
		String result = "";
		boolean bol = insurancePersonnelSer.delFundCalcObject(request);
		if (bol) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
	
	
	/**
	 * 跳转到修改页面(updatePaComputeItemView)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateFundObjectView")
	public ModelAndView updateFundObjectView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Object insuranceObject = this.insurancePersonnelSer.getFundObjectInfo(request);
		@SuppressWarnings("unused")
		Map map = new HashMap();
		map = (HashMap)insuranceObject;
		modelMap.put("insuranceObject", insuranceObject);
		modelMap.put("pageNum", request.getParameter("pageNum"));
		
		return new ModelAndView("/pa/insurance/updateFundObjectView",modelMap);
	}
	
	/**
	 * 执行修改（updateFundObjectInfo）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateFundObjectInfo")
	@ResponseBody
	public Map updateFundObjectInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt=this.insurancePersonnelSer.updateFundObjectInfo(request) ;
		if(errorInt==1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success", request) );
			map.put("forwardUrl", "/pa/insurance/viewFundObject?pageNum="+request.getParameter("insurancePageNum")
					+"&menuNo=477&navTabId=pa0421&seach_INSURANCE_GIVE_DATE="+request.getParameter("INSURANCE_GIVE_DATE")
					+"&seach_insYear="+request.getParameter("insurancePaYear")+"&seach_insMonth="+request.getParameter("insurancePaMonth"));
			map.put("navTabId", "pa0421");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request) );
		}
		return map;
	}
	
	/**
	 * 公积金计算对象导入临时结果页面--查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewFundCalcObjectImportList")
	public ModelAndView viewFundCalcObjectImportList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List insObjTempList = this.insurancePersonnelSer.getFundCalcObjectTempList(request) ;
		int insObjTempCnt = this.insurancePersonnelSer.getFundCalcObjectTempCnt(request) ;
		int errorCnt = this.insurancePersonnelSer.getFundCalcObjectTempErrorCnt(request) ;
		
		modelMap.put("insObjTempList", insObjTempList);
		modelMap.put("errCnt", errorCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, insObjTempCnt);
		
		return new ModelAndView("/pa/insurance/viewFundCalcObjectImportList",modelMap);
	}
	
	/**
	 * 导出公积金计算对象临时表里的所有公积金计算对象数据信息，进行修改，然后再导入
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/expFundObjectImportDataExcel")
	public void expFundObjectImportDataExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();
		aliasNameList.add("工资月");
		aliasNameList.add("社号");
		aliasNameList.add("姓名");
		aliasNameList.add("部门");
		aliasNameList.add("计算标识");
		aliasNameList.add("备注");
		aliasNameList.add("正/异常");
		aliasNameList.add("错误提示");
		
		List dataList = new ArrayList();
		List insObjectImportList = this.insurancePersonnelSer.getFundCalcObjectTempList(request);
		for(int i=0;i<insObjectImportList.size();i++){
			LinkedHashMap insObjMap = new LinkedHashMap();
			insObjMap = (LinkedHashMap)insObjectImportList.get(i);
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", insObjMap.get("PA_MONTH")!=null?insObjMap.get("PA_MONTH").toString():"");
			map.put("CELL1", insObjMap.get("PERSON_ID")!=null?insObjMap.get("PERSON_ID").toString():"");
			map.put("CELL2", insObjMap.get("LOCAL_NAME")!=null?insObjMap.get("LOCAL_NAME").toString():"");
			map.put("CELL3", insObjMap.get("DEPT_NAME")!=null?insObjMap.get("DEPT_NAME").toString():"");
			map.put("CELL4", insObjMap.get("CALC_FLAG")!=null?insObjMap.get("CALC_FLAG").toString():"");
			map.put("CELL5", insObjMap.get("REMARK")!=null?insObjMap.get("REMARK").toString():"");
			String checkFlag = "";
			if(insObjMap.get("CHECK_FLAG")!=null && "1".equals(insObjMap.get("CHECK_FLAG").toString())){
				checkFlag = "异常";
			}else if(insObjMap.get("CHECK_FLAG")!=null && "0".equals(insObjMap.get("CHECK_FLAG").toString())){
				checkFlag = "正常";
			}
			map.put("CELL6", checkFlag);
			map.put("CELL7", insObjMap.get("CHECK_ERROR")!=null?insObjMap.get("CHECK_ERROR").toString():"");
			
			dataList.add(map);
		}
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(dataList);
		//this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
		String fileName = "viewFundCalcObjectImport";
		this.excelUtilSer.exportIsParamDataExcel(request, response, modelMap,sqlContentmap, aliasNameList, null,fileName);
	}
	
	/**
	 * 保存导入的公积金计算对象数据 (import insurance object data)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveFundObjectDataImport")
	@ResponseBody
	public Map saveFundObjectDataImport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try {
			result = insurancePersonnelSer.addImportFundObjectData(request);
			if (result == 1) {
				map.put("navTabId", "pa0421");
				map.put("message", "导入公积金计算对象保存成功!");//保存导入公积金计算对象数据成功!
				map.put("statusCode", "200");
				map.put("callbackType", "closeCurrent");
				//map.put("forwardUrl","/pa/insurance/viewFundCalcObjectImportList");
			}else{
				map.put("message", "导入的公积金计算对象数据有错误,请修改!");//导入的公积金计算对象数据有错误,请修改!
				map.put("statusCode", "200");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", "导入公积金计算对象保存失败,请重试!");//导入公积金计算对象数据失败,请重试!
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 取消所有导入的公积金计算对象( delete insurance object import)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author lufeng@ait.net.cn
	 * @date 2014-9-22 下午04:51:10
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cancelFundObjectImport")
	@ResponseBody
	public Map cancelFundObjectImport(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();

		String msg= ""+this.insurancePersonnelSer.cancelFundObjectImport(request);
		if("1".equals(msg)){
			map.put("statusCode", "200");
			map.put("message", "取消成功！");//保存成功
			map.put("navTabId", "pa0421");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", "取消失败！");//保存失败
		}
		return map;
	}

	/**
	 * 单条删除导入的公积金计算对象( delete insurance object)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delFundObjectImport")
	@ResponseBody
	public String delFundObjectImport(HttpServletRequest request) throws Exception {
		String result = "";
		boolean bol = insurancePersonnelSer.delFundObjectImport(request);
		if (bol) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
	/**---------------公积金计算对象-------end--------*/
}
