package com.ait.hrm.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.JobTypeSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.tempsale.PaTempSalesSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
@Controller
@RequestMapping(value = "/hrm/jobType")
public class JobTypeCtroller {
	
	@Autowired
	private JobTypeSer jobTypeSer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
    
	@Autowired
	private CompanySer companySer;
	
	@Autowired
	private ExcelUtilSer excelUtilSer;
	
	@Autowired
	private AuthorityUtil authorityUtil;
	
	@Autowired
	private EmpInfoSer empInfoSer;
	
    
	/**
	 * 区间参数 查询分页 页面(show CycleParameter view)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewJobTypeList")
	public ModelAndView viewJobTypeList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List jobTypeList = this.jobTypeSer.getJobTypeList(request) ;
		int jobTypeCnt = this.jobTypeSer.getJobTypeCnt(request) ;
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
		modelMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		modelMap.put("jobTypeList", jobTypeList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, jobTypeCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
		toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2563")) ;
		return new ModelAndView("/hrm/jobType/viewJobTypeList",modelMap);
	}
	
	/**
	 * 进入添加画面(show add view)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addJobTypeView",method = RequestMethod.GET)
	public ModelAndView addJobTypeView(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		List jobTypeNameList = this.jobTypeSer.getJobTypeNameList(request) ;
		List jobTypeGroupNameList = this.jobTypeSer.getJobTypeGroupNameList(request) ;
		List companyList = companySer.getCompanyItemAllList(request);
		modelMap.put("jobTypeNameList", jobTypeNameList);
		modelMap.put("companyList", companyList) ;
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		modelMap.put("cpnyList", companySer.getCompanyItemList(request));
	    modelMap.put("cpny_id", admin.getCpnyId());
		return new ModelAndView("/hrm/jobType/addJobTypeView",modelMap);
	}
	
	/**
	 * 保存人员类型信息(add jobType Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addJobTypeInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addJobTypeInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.jobTypeSer.checkJobType(request) ;
		if(errorInt == 0){
			int result = this.jobTypeSer.addJobTypeInfo(request);
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
				map.put("navTabId", "hr0034");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			}
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.info_exits",request));//该信息已经存在,不能重复添加
		}
		return map;
	}
	
	
	/**
	 * 删除人员类型信息(delete JobType Info)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteJobTypeInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map deleteJobTypeInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.jobTypeSer.deleteJobTypeInfo(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0034");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}	
		return map;
	}
	
	
	/**
	 * 进入修改页面(update JobType View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateJobTypeView",method = RequestMethod.GET)
	public ModelAndView updateJobTypeView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		List jobTypeNameList = this.jobTypeSer.getJobTypeNameList(request) ;
		List jobTypeGroupNameList = this.jobTypeSer.getJobTypeGroupNameList(request) ;
		List companyList = companySer.getCompanyItemAllList(request);
		modelMap.put("jobTypeInfo", jobTypeSer.getJobType(request)) ;
		modelMap.put("jobTypeList", jobTypeSer.getJobTypeList(request));
		modelMap.put("companyList", companyList);
		modelMap.put("jobTypeNameList", jobTypeNameList);
		modelMap.put("jobTypeGroupNameList", jobTypeGroupNameList);
		return new ModelAndView("/hrm/jobType/updateJobTypeView",modelMap);
	}
	
	/**
	 * 修改区间信息(update CycleParam Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateJobTypeInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateJobTypeInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt = this.jobTypeSer.checkJobType(request) ;
		if(errorInt == 0){
		    int result = this.jobTypeSer.updateJobTypeInfo(request);
		    if(result == 1){
			    map.put("statusCode", "200");
			    map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			    map.put("navTabId", "hr0034");
		    }else{
			    map.put("statusCode", "300");
			    map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		    }
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.info_exits",request));//该信息已经存在,不能重复添加
		}
		return map;
	}
	
	/**
	 * 下在导入模版
	 */
	@RequestMapping(value = "/downloadTempleteJobType")
	@SuppressWarnings("unchecked")
	public void downloadTempleteJobType(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		List aliasNameList = new ArrayList();

		aliasNameList.add("公司ID *");
		aliasNameList.add("人员类型组NO *");
		aliasNameList.add("人员类型 NO*");
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0","TSTO");
		map.put("CELL1","211811");
		map.put("CELL2","216173");

		list.add(map);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		List mapNameList = new ArrayList();
		mapNameList.add("公司(法人)参考");
		mapNameList.add("人员类型组参考");
		mapNameList.add("人员类型参考");
		
		List mapList = new ArrayList();
		String cpnySql = "SELECT '[' || T.CPNY_ID || ']' || T.CPNY_LOCATION CONTENT FROM HR_COMPANY T WHERE T.ACTIVITY = 1";
		String deptSql = "SELECT '[' || T.CODE_NO || ']' || U.CONTENT CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 211807";
		String typeSql = "SELECT '[' || T.CODE_NO || ']' || U.CONTENT CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 1368";
		mapList.add(cpnySql);
		mapList.add(deptSql);
		mapList.add(typeSql);
		
		String name = "LaborUnionJobTyop";
		//this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList,name);
	}

	/**
	 * 导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author wendi
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportJobTypeDataList")
	public ModelAndView viewImportExcelTempJobTypeDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List jobTypeTempList = this.jobTypeSer.getJobTypeImportTempList(request);
		int jobTypeTempCnt = this.jobTypeSer.getJobTypeImportTempCnt(request, "T");
		int errorCnt = this.jobTypeSer.getJobTypeImportTempCnt(request, "E");
		
		modelMap.put("jobTypeTempList", jobTypeTempList);
		modelMap.put("jobTypeTempCnt", jobTypeTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", jobTypeTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, jobTypeTempCnt);
		return new ModelAndView("/hrm/jobType/viewImportJobTypeDataList", modelMap);
	}
	
	/**
	 * 根据人员类型组获取人员类型
	 * @param request
	 * @author wendi
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/getEmpJobType")
	@ResponseBody
	public Map getEmpJobType(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List result = this.jobTypeSer.getEmpJobTypeList(request);
		map.put("statusCode", "200");
		map.put("result", result);
		return map;
	}
	
	/** 
	* @Title: getEmpForGroupToList 
	* @Description: TODO 根据人员类型组查询人员类型，为联动查询服务，11.20修改
	* @param @param object
	* @param @return    
	* @return List    
	* @throws 
	*/
	@RequestMapping(value = "/getEmpTypeForGroupToList")
	@ResponseBody
	public Map getEmpTypeForGroupToList(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List result = this.jobTypeSer.getEmpTypeForGroupToList(request);
		map.put("statusCode", "200");
		map.put("result", result);
		return map;
	}
	
	
	/**
	 * 人员类型excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author wendi
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelJobTypeData")
	@ResponseBody
	public Map submitImportExcelJobTypeData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.jobTypeSer.submitImportExcelJobTypeData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));//保存成功
			jo.put("navTabId", "hr0034");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));//保存失败
		}
		return jo;
	}
	
	/**
	 * 人员类型模板下载（验证后）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadJobTypelateByExcelData")
	public void downloadJobTypelateByExcelData(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = jobTypeSer.getJobTypelateInfoByExcelData(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
		
	}
}
