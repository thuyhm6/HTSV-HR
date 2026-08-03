package com.ait.org.action;

import java.math.BigInteger;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.org.service.OrgManageSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.wagebase.PaSupervisorSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.DateUtil;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: OrgManageCtroller.java 
 * @Create by: wzc(weizhengchen@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/org/orgManage")
public class OrgManageCtroller {
	Logger logger = Logger.getLogger(OrgManageCtroller.class);

	@Autowired
	private OrgManageSer orgManageSer;
	
	@Autowired
	private EmpInfoSer empInfoSer;

	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	
	@Autowired
	private ExcelUtilSer excelUtilSer;
	
	@Autowired
	private PaSupervisorSer paSupervisorSer;

	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	/**
	 * 组织查看(横向) Description:It's the page to show orgnazation
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgInfo")
	public ModelAndView viewOrgInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String dLevel = ObjectUtils.toString(request.getParameter("deptl"));

		Map param = new LinkedHashMap();
		param.put("DEPTL", dLevel);
		param.put("interLanguage", admin.getLanguage());
		param.put("specialParam", admin.getSpecialParam());
		param.put("userNo", admin.getUserNo());
		param.put("deptNo", admin.getDeptNo());
		param.put("interCpnyID", admin.getCpnyId());
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		param.put("endEddate", paramMap.get("endEddate"));
		modelMap.put("endEddate", paramMap.get("endEddate"));
		List deptInfoList = this.orgManageSer.getDeptInfo(paramMap);
		List deptLevelList = this.orgManageSer.getDeptLevel(param);
		modelMap.put("DEPTL_LEVEL", dLevel);
		modelMap.put("deptInfoList", deptInfoList);
		modelMap.put("deptLevelList", deptLevelList);

		modelMap.put("photoId", "viewOrgInfo");
		modelMap.put("VIEW_CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/org/orgManage/viewOrgInfo", modelMap);
	}


	/**
	 * 组织查看(横向) Description:It's the page to show orgnazation
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgInfoData")
	@ResponseBody
	public List viewOrgInfoData(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String dLevel = ObjectUtils.toString(request.getParameter("deptl"));

		Map param = new LinkedHashMap();
		param.put("DEPTL", dLevel);
		param.put("interLanguage", admin.getLanguage());
		param.put("specialParam", admin.getSpecialParam());
		param.put("userNo", admin.getUserNo());
		param.put("deptNo", admin.getDeptNo());
		param.put("interCpnyID", admin.getCpnyId());
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		param.put("endEddate", paramMap.get("endEddate"));
		modelMap.put("endEddate", paramMap.get("endEddate"));
		List deptInfoList = this.orgManageSer.getDeptInfo(paramMap);
		List deptLevelList = this.orgManageSer.getDeptLevel(param);
		modelMap.put("DEPTL_LEVEL", dLevel);
		modelMap.put("deptInfoList", deptInfoList);
		modelMap.put("deptLevelList", deptLevelList);

		modelMap.put("photoId", "viewOrgInfo");
		modelMap.put("VIEW_CPNY_ID", admin.getCpnyId());

		return deptInfoList;
	}
	
	/**
	 * 纵向部门树 Description:It's the page to show orgnazation
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgInfoTree")
	public ModelAndView viewOrgInfoTree(HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("endEddate", paramMap.get("endEddate"));
		return new ModelAndView("/org/orgManage/viewOrgInfoTree", modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getOrgInfoTreeDate")
	@ResponseBody
	public List getOrgInfoTreeDate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("endEddate", request.getParameter("endEddate"));
		List deptInfoTreeList = this.orgManageSer.getDeptInfoTree(paramMap);
		return deptInfoTreeList;
	}
	/**
	 * 组织维护 Description:in the page you can add or delete or update a department
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrganizationInfo")
	public ModelAndView viewOrganizationInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List deptInfoList = this.orgManageSer.getOrganizationInfoList(request);
		int deptInfoCnt = this.orgManageSer.getOrganizationInfoCnt(request);

		modelMap.put("deptInfoList", deptInfoList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, deptInfoCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2585"));
		return new ModelAndView("/org/orgManage/viewOrganizationInfo", modelMap);
	}
	
	
	/**
	 * 部门信息列表的导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewDeptInfoListExcel")
	public void viewDeptInfoListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang=searchMap.get("interLanguage").toString();
		String name = "viewDeptInfoListExcel";
		List aliasNameList = new ArrayList();
		List aliasValueList  = new ArrayList();
		//提取导出数据列表
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();		
		//默认查询汇总list
			//设置excel header
			aliasNameList.add(TipMessage.getTipMessage("org.orgManage.title.deptName", request));//部门名称
			aliasNameList.add(TipMessage.getTipMessage("org.title.DEPT_CODE", request)); //部门代码
			aliasNameList.add(TipMessage.getTipMessage("org.orgManage.title.deptType", request));//部门类型 
			aliasNameList.add(TipMessage.getTipMessage("org.title.MANAGER_EMP_NAME", request));//部门领导
			aliasNameList.add(TipMessage.getTipMessage("org.orgManage.title.deptBeginTime", request));//部门开始时间
			aliasNameList.add(TipMessage.getTipMessage("org.orgManage.title.parentDept", request));//上级部门
			aliasNameList.add(TipMessage.getTipMessage("org.title.en.ORG_TYPE", request));	//ORG_TYPE
			aliasNameList.add(TipMessage.getTipMessage("org.title.en.DEPT_LEVEL", request));//DEPT_LEVEL
			aliasNameList.add(TipMessage.getTipMessage("org.title.en.AU_CODE", request));//AU_CODE
			aliasNameList.add(TipMessage.getTipMessage("org.title.en.DEPT_CODE", request));//DEPT_CODE
			aliasNameList.add(TipMessage.getTipMessage("org.title.UPDATED_IP", request));//变更者
			aliasNameList.add(TipMessage.getTipMessage("org.orgManage.title.deptEndTime", request));//部门结束时间
			aliasValueList	=  this.orgManageSer.getOrganizationInfoList(request);
			//列名
			String[] columns = { "CONTENT", "DEPTNO", "DEPT_TYPE", "MANAGER_EMP_NAME", "DATE_CREATED",
					"PARENT_DEPT_NAME_ZH", "ORG_TYPE", "DEPT_LEVEL", "ACC_DIV_CODE", "ACC_ORG_CODE","LOCAL_NAME","DATE_ENDED"};
			//数据集
			this.excelUtilSer.exportExcelByNamePwd(
					request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
	}	

	/**
	 * 新建组织 Description:the page to add a new department
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addOrganizationView")
	public ModelAndView addOrganizationView(HttpServletRequest request,
			ModelMap modelMap) throws Exception {

		List deptList = this.orgManageSer.getSelectDept(request);
		List postList = this.orgManageSer.getPostForChoose(request);
		List companyList = this.orgManageSer.getCpnyNameForChoose(request);
		//List distinguishList = this.orgManageSer.getChildCodeList(request,
				//"13786");// 部门区分DEPT_DISTINGUISH_NO3643
		//List deptTypeList = this.orgManageSer.getChildCodeList(request, "4603");// 部门类型
		//List areaList = this.orgManageSer.getChildCodeList(request, "4602");// 所属地区WORK_AREA
																			// 4602
																			// 13781
		modelMap.put("deptList", deptList);
		modelMap.put("postList", postList);
		//modelMap.put("distinguishList", distinguishList);
		//modelMap.put("deptTypeList", deptTypeList);
		//modelMap.put("areaList", areaList);
		modelMap.put("companyList", companyList);

		return new ModelAndView("/org/orgManage/addOrganizationView", modelMap);
	}

	/**
	 * 根据法人切换数据信息 Description:get datas according different cpnyIDs
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDetailsByCpnyId")
	@ResponseBody
	public void getDetailsByCpnyId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map modelMap = new HashMap<String, Object>();
		modelMap.put("postList", this.orgManageSer.getPostForChoose(request));
		//modelMap.put("distinguishList", this.orgManageSer.getChildCodeList(
				//request, "13786"));// 4604
		//modelMap.put("typeList", this.orgManageSer.getChildCodeList(request,
				//"4603"));
		//modelMap.put("areaList", this.orgManageSer.getChildCodeList(request,
				//"4602"));// 13781
		response.getWriter().print(JsonUtil.writeInternal(modelMap));
	}

	/**
	 * 修改组织 Description:the page to update a department
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateOrganizationView")
	public ModelAndView updateOrganizationView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List functionList = this.orgManageSer.getChildCodeList(request, "219764");// function
		List companyList = this.orgManageSer.getCpnyNameForChoose(request);
		List payAreaList = orgManageSer.getPayAreaInfoList(request);
		modelMap.put("organizationInfo", orgManageSer
				.getOrganizationInfo(request));
		modelMap.put("companyList", companyList);
		modelMap.put("functionList", functionList);
		modelMap.put("payAreaList", payAreaList);
		return new ModelAndView("/org/orgManage/updateOrganizationView",
				modelMap);
	}

	/**
	 * 添加部门 Description:add a new department
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addOrganizationInfo")
	@ResponseBody
	public Map addOrganizationInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.addOrganizationInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "org0103");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	/**
	 * 删除部门 Description:delete a department
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteOrganizationInfo")
	@ResponseBody
	public Map deleteOrganizationInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.deleteOrganizationInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "org0103");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	/**
	 * 更新部门 Description:update a department
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateOrganizationInfo")
	@ResponseBody
	public Map updateOrganizationInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.updateOrganizationInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "org0103");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 验证要结束的部门下是否有员工，如果有员工，则不能结束 Description:if there are employees in the
	 * department
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/validateEmpExist")
	@ResponseBody
	public void validateEmpExist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List empList = this.orgManageSer.getEmpInfoInOrg(request);
		Map modelMap = new HashMap<String, Object>();
		if (empList == null || (empList != null && empList.size() == 0)) {
			modelMap.put("flagYn", "Y");
		} else {
			modelMap.put("flagYn", "N");
		}
		response.getWriter().print(JsonUtil.writeInternal(modelMap));
	}
	
	/**
	 * 部门组织结构查询(export the org info)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgStructureInfo")
	public ModelAndView viewOrgStructureInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//分页查询用
	    //List orgStructureInfoList = this.orgManageSer.getOrgStructureInfoList(request);
		//int orgStructureInfoListCnt = this.orgManageSer.getOrgStructureInfoListCnt(request);
		
		List orgStructureInfoList = this.orgManageSer.getOrgStructureInfoExcelList(request);
		modelMap.put("orgStructureInfoList",orgStructureInfoList);
		modelMap.put("defaultCpny", admin.getCpnyId());
		String activity = request.getParameter("seach_ACTIVITY")!=null?
				request.getParameter("seach_ACTIVITY").toString():"";
		String parent_deptNo = request.getParameter("seach_PARENT_DEPT_NO")!=null?
				request.getParameter("seach_PARENT_DEPT_NO").toString():"";
		modelMap.put("ACTIVITY", activity);
		modelMap.put("PARENT_DEPT_NO", parent_deptNo);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "123476")) ;
		
		return new ModelAndView("/org/orgManage/viewOrgStructureInfo",modelMap);
	}
	
	/**
	 * 部门组织结构导出(export the org info)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOrgStructureInfoExcel")
	public ModelAndView viewOrgStructureInfoExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("orgStructureInfoList",this.orgManageSer.getOrgStructureInfoExcelList(request));
		
		return new ModelAndView("/org/orgManage/viewOrgStructureInfoExcel",modelMap);
	}
	
	/**
	 * 报表中心处开启部门，重新使用该部门
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/manageOrgStructureInfo")
	@ResponseBody
	public String openOrgStructureInfo(HttpServletRequest request) throws Exception {
		int result = -1;
		String flag = request.getParameter("FLAG")!=null?request.getParameter("FLAG").toString():"";
		if("1".equals(flag)){
			//如果是开启
			result = this.orgManageSer.openOrgStructureInfo(request);
		}else{
		    //如果是关闭
			result = this.orgManageSer.closeOrgStructureInfo(request);
		}
		if(result == 1) {
			return "Y";
		}else if(result == 3){//要关闭的部门还存在未离职人员，不允许关闭！
			return "E";
		}else{
			return "N";
		}
	}
	
	/**
	 * 报表中心处新建组织 Description:the page to add a new department
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addOrgStructureView")
	public ModelAndView addOrgStructureView(HttpServletRequest request,
			ModelMap modelMap) throws Exception {

		List deptList = this.orgManageSer.getSelectDept(request);
		List postList = this.orgManageSer.getPostForChoose(request);
		List companyList = this.orgManageSer.getCpnyNameForChoose(request);
		List distinguishList = this.orgManageSer.getChildCodeList(request,"4604");// 部门区分DEPT_DISTINGUISH_NO3643
		List deptTypeList = this.orgManageSer.getChildCodeList(request, "4603");// 部门类型
		List areaList = this.orgManageSer.getChildCodeList(request, "4602");// 所属地区WORK_AREA// 4602// 13781
		
		modelMap.put("deptList", deptList);
		modelMap.put("postList", postList);
		modelMap.put("distinguishList", distinguishList);
		modelMap.put("deptTypeList", deptTypeList);
		modelMap.put("areaList", areaList);
		modelMap.put("companyList", companyList);

		return new ModelAndView("/org/orgManage/addOrgStructureView", modelMap);
	}
	
	/**
	 * 报表中心处添加部门 Description:add a new department
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addOrgStructureInfo")
	@ResponseBody
	public Map addOrgStructureInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.addOrganizationInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "rpt0108");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 * 报表中心处修改组织 Description:the page to update a department
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateOrgStructureView")
	public ModelAndView updateOrgStructureView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List postList = this.orgManageSer.getPostForChoose(request);
		List distinguishList = this.orgManageSer.getChildCodeList(request,"4604");// 部门区分DEPT_DISTINGUISH_NO 4604
		List deptTypeList = this.orgManageSer.getChildCodeList(request, "4603");// 部门类型
		List areaList = this.orgManageSer.getChildCodeList(request, "4602");// 所属地区WORK_AREA// 13781
		List companyList = this.orgManageSer.getCpnyNameForChoose(request);

		modelMap.put("organizationInfo", orgManageSer.getOrganizationInfo(request));
		modelMap.put("postList", postList);
		modelMap.put("distinguishList", distinguishList);
		modelMap.put("deptTypeList", deptTypeList);
		modelMap.put("areaList", areaList);
		modelMap.put("companyList", companyList);

		return new ModelAndView("/org/orgManage/updateOrgStructureView",
				modelMap);
	}
	
	/**
	 * 报表中心处更新部门 Description:update a department
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateOrgStructureInfo")
	@ResponseBody
	public Map updateOrgStructureInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.updateOrganizationInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "rpt0108");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}
	
	/**
	 * 大区信息查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPayAreaInfo")
	public ModelAndView viewPayAreaInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List payAreaInfoList = this.orgManageSer.getPayAreaInfoList(request);
		modelMap.put("payAreaInfoList", payAreaInfoList);
		modelMap.put("count", payAreaInfoList.size());
		return new ModelAndView("/org/orgManage/viewPayAreaInfo", modelMap);
	}
	
	/**
	 * 更新大区
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePayAreaInfo")
	@ResponseBody
	public Map updatePayAreaInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.updatePayAreaInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "org1201");
			map.put("forwardUrl","/org/orgManage/viewPayAreaInfo?navTabId=org1201") ;
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}
	
	/**
	 * 根据parent_code_no获取code列表信息，供页面code检索条件用
	 * 
	 * @param request
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSyCodeList")
	@ResponseBody
	public Map getSyCodeList(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = this.orgManageSer.getSyCodeList(request);
		if (list != null && list.size() > 0) {
			map.put("codeList", list);
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "bx0121");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}
	
	/**
	 * 组织排序设置
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgInfoOrderNoList")
	public ModelAndView viewOrgInfoOrderNoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List deptInfoList = this.orgManageSer.getOrgInfoOrderList(request);
		int deptInfoCnt = this.orgManageSer.getOrgOrgInfoOrderCnt(request);
		modelMap.put("deptInfoList", deptInfoList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, deptInfoCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2585"));
		return new ModelAndView("/org/orgManage/viewOrgInfoOrderNoList", modelMap);
	}
	
	/**
	 * 修改组织设置 
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateOrgInfoView")
	public ModelAndView updateOrgInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List functionList = this.orgManageSer.getChildCodeList(request, "219764");// function
		List companyList = this.orgManageSer.getCpnyNameForChoose(request);
		List payAreaList = orgManageSer.getPayAreaInfoList(request);
		modelMap.put("organizationInfo", orgManageSer.getOrgOrderInfo(request));
		modelMap.put("companyList", companyList);
		modelMap.put("functionList", functionList);
		modelMap.put("payAreaList", payAreaList);
		return new ModelAndView("/org/orgManage/updateOrgInfoView",
				modelMap);
	}
	
	/**
	 * 更新部门 排序no
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateOrgInfo")
	@ResponseBody
	public Map updateOrgInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int resultInt = 0;
		if(!"".equals(request.getParameter("ORDERNO"))){
			resultInt = this.orgManageSer.checkOrganizationNo(request);
		}
		if(resultInt == 0){
		    int result = this.orgManageSer.updateOrgInfo(request);
		    if (result == 1) {
			    map.put("statusCode", "200");
			    map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			    map.put("navTabId", "org11order");
			    map.put("callbackType", "closeCurrent");
		    } else {
			    map.put("statusCode", "300");
			    map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		    }
		}else{
	    	map.put("statusCode", "300");
		    map.put("message", TipMessage.getTipMessage("alert.message.org.norepeat_reset", request));
	    }
		return map;
	}

	/**
	 * 部门信息列表的导出（排序设置页面）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOrgInfoListExcel")
	public void viewOrgInfoListExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map searchMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String lang=searchMap.get("interLanguage").toString();
		String name = "viewOrgInfoListExcel";
		List aliasNameList = new ArrayList();
		List aliasValueList  = new ArrayList();
		//提取导出数据列表
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map<String, Object> map = new HashMap<String, Object>();		
		//默认查询汇总list
			//设置excel header
			aliasNameList.add(TipMessage.getTipMessage("org.orgManage.title.deptName", request));//部门名称
			aliasNameList.add(TipMessage.getTipMessage("org.title.DEPT_CODE", request)); //部门代码
			aliasNameList.add(TipMessage.getTipMessage("org.orgManage.title.deptType", request));//部门类型
			aliasNameList.add(TipMessage.getTipMessage("org.orgManage.title.parentDept", request));//上级部门
			aliasNameList.add(TipMessage.getTipMessage("org.title.en.ORG_TYPE", request));	
			aliasNameList.add(TipMessage.getTipMessage("org.title.en.AU_CODE", request));
			aliasNameList.add(TipMessage.getTipMessage("org.title.en.DEPT_CODE", request));
			aliasNameList.add(TipMessage.getTipMessage("org.title.FOR_PACAL", request));//工资所属 
			aliasNameList.add(TipMessage.getTipMessage("org.title.DEPT_PAYAREA", request));//大区属性 
			aliasNameList.add(TipMessage.getTipMessage("org.title.DEPT_BRANCH", request));//支社属性
			aliasNameList.add(TipMessage.getTipMessage("org.title.ORG_SORT_NO", request));//组织排序No 
			aliasValueList	=  this.orgManageSer.getOrgInfoOrderList(request);
			//列名
			String[] columns = { "CONTENT", "DEPTNO", "DEPT_TYPE",
					"PARENT_DEPT_NAME_ZH", "ORG_TYPE",  "ACC_DIV_CODE", "ACC_ORG_CODE","FOR_PACAL","DEPT_PAYAREA","DEPT_BRANCH","ORDERNO"};
			//数据集
			this.excelUtilSer.exportExcelByNamePwd(
					request, response, modelMap, aliasValueList, aliasNameList, columns, name, searchMap);
	}
	
	/**
	 * 下在导入模版（部门排序页面）
	 */
	@RequestMapping(value = "/downloadTempleteDeptOrder")
	@SuppressWarnings("unchecked")
	public void downloadTempleteDeptOrder(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();

		aliasNameList.add(TipMessage.getTipMessage("org.title.COMPANYID", request) +" *");//公司ID
		aliasNameList.add(TipMessage.getTipMessage("org.title.deptNO", request) +" *");//部门NO
		aliasNameList.add(TipMessage.getTipMessage("org.titlr.SORTNO", request) +" *");//排序NO
		List list = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("CELL0",admin.getCpnyId());
		map.put("CELL1","198659");
		map.put("CELL2","1111");

		list.add(map);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		List mapNameList = new ArrayList();
		mapNameList.add(TipMessage.getTipMessage("org.title.COMPANY_REFER", request));//公司(法人)参考
		mapNameList.add(TipMessage.getTipMessage("org.title.DEPTNO_REFER", request));//部门NO参考
		
		List mapList = new ArrayList();
		String cpnySql = "SELECT '[' || T.CPNY_ID || ']' || T.CPNY_LOCATION CONTENT FROM HR_COMPANY T WHERE T.ACTIVITY = 1";
		String deptSql = "SELECT '[' || U.DEPTNO || ']' || U.ORG_NAME_LOCAL CONTENT FROM HR_DEPARTMENT U WHERE U.CPNY_ID = '"+admin.getCpnyId()+"' AND U.USE_YN = 'Y'";
		mapList.add(cpnySql);
		mapList.add(deptSql);
		
		String name = "LaborUnionDeptOrder";
		//this.excelUtilSer.exportExcel(request, response, modelMap,sqlContentmap, aliasNameList, null);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList,name);
	}
	
	/**
	 * 导入临时保存画面（部门排序页面导入）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author wendi
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportDeptNoDataList")
	public ModelAndView viewImportDeptNoDataList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List deptOrderTempList = this.orgManageSer.getDeptOrderImportTempList(request);
		int deptOrderTempCnt = this.orgManageSer.getDeptOrderImportTempCnt(request, "T");
		int errorCnt = this.orgManageSer.getDeptOrderImportTempCnt(request, "E");
		
		modelMap.put("deptOrderTempList", deptOrderTempList);
		modelMap.put("deptOrderTempCnt", deptOrderTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", deptOrderTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, deptOrderTempCnt);
		return new ModelAndView("/org/orgManage/viewImportDeptNoDataList", modelMap);
	}
	
	/**
	 * 组织排序模板下载（验证后）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadDeptOrderlateByExcelData")
	public void downloadDeptOrderlateByExcelData(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String name = orgManageSer.getDeptOrderlateInfoByExcelData(request, aliasNameList, list , mapList, mapNameList);
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
		
	}
	/**
	 * 组织排序excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author wendi
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelDeptOrderData")
	@ResponseBody
	public Map submitImportExcelDeptOrderData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.orgManageSer.submitImportExcelDeptOrderData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));//保存成功
			jo.put("navTabId", "org11order");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));//保存失败
		}
		return jo;
	}
	
	
	
	
	
	
	/**
	 * 获取部门列表信息，供页面部门检索条件用
	 * 
	 * @param request
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDeptList")
	@ResponseBody
	public Map getDeptList(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = this.orgManageSer.getDeptList(request);
		if (list != null && list.size() > 0) {
			map.put("deptList", list);
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "rpt0108");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}
	
	//新组织代码
	
	/**
	 * 获取部门列表信息，供页面部门检索条件用
	 * 
	 * @param request
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getResumeDeptList")
	@ResponseBody
	public Map getResumeDeptList(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = this.orgManageSer.getResumeDeptList(request);
		if (list != null && list.size() > 0) {
			map.put("deptList", list);
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "rpt0108");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}
	
	/**
	 * 概要改编list
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgIndex")
	public ModelAndView viewOrgIndex(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/org/orgManage/viewOrgIndex", modelMap);
	}
	
	
	/**
	 * 概要改编list
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResumeList")
	public ModelAndView viewResumeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List orgResumeList = this.orgManageSer.viewResumeList(request);
		modelMap.put("orgResumeList", orgResumeList);

		LinkedHashMap param = null;
		
		if(orgResumeList!=null && orgResumeList.size() > 0){
			param = (LinkedHashMap)orgResumeList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("orgResumeSize", orgResumeList.size());
		}else{
			modelMap.put("orgResumeSize", 0);
		}

		return new ModelAndView("/org/orgManage/viewResumeList", modelMap);
	}

	/**
	 * 概要改编修改
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddResumeInfo")
	public ModelAndView viewAddResumeInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List orgResumeList = this.orgManageSer.viewResumeList(request);

		LinkedHashMap param = null;
		
		if(orgResumeList!=null && orgResumeList.size() > 0){
			param = (LinkedHashMap)orgResumeList.get(0);

			LinkedHashMap fileParam = new LinkedHashMap();
			fileParam.put("APPLY_TYPE", "RESUME");
			fileParam.put("APPLY_NO", param.get("SEQ"));
			List fileList = infoApplyLeaveDao.getEssFileList(fileParam);
			param.put("fileList",fileList);
			
		}else{
			List list = this.orgManageSer.getResumneNo(request);
			param = (LinkedHashMap)list.get(0);
		}
		modelMap.put("resumeInfo", param);

		return new ModelAndView("/org/orgManage/viewAddResumeInfo", modelMap);
	}
	

	/**
	 * 添加概要改编
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addResumeInfo")
	@ResponseBody
	public Map addResumeInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.addResumeInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("navTabId", "org0201");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
		}
		return map;
	}
	

	/**
	 * 删除概要改编
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteResumeInfo")
	@ResponseBody
	public Map deleteResumeInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.deleteResumeInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "org0201");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	
	/**
	 * 概要改编流程
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewResumeProcess")
	public ModelAndView viewResumeProcess(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		String resumeNo = request.getParameter("RESUME_NO");
		List orgResumeList = this.orgManageSer.viewResumeList(request);

		List orgResumeProcess = this.orgManageSer.viewResumeProcess(request,orgResumeList);

		Map map = new LinkedHashMap();
		if(orgResumeProcess != null && orgResumeProcess.size()>0){
			map = (LinkedHashMap)orgResumeProcess.get(0);
		}
		modelMap.put("resumeProcessInfo", map);
		modelMap.put("orgResumeList", orgResumeList);
		modelMap.put("RESUME_NO", resumeNo);
	
		return new ModelAndView("/org/orgManage/viewResumeProcess", modelMap);
	}
	

	/**
	 * 组织图构成
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewComposeOrg")
	public ModelAndView viewComposeOrg(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List orgResumeList = this.orgManageSer.viewResumeList(request);
		
		String RESUME_NO = getCurrentResumeNO( request, orgResumeList);
		modelMap.put("RESUME_NO", RESUME_NO);
		
		modelMap.put("orgResumeList", orgResumeList);

		return new ModelAndView("/org/orgManage/viewComposeOrg", modelMap);
	}
	
	/**
	 * 改编流程执行
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/executeResumeProcess")
	@ResponseBody
	public Map executeResumeProcess(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.orgManageSer.executeResumeProcess(request);
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.org.execute_success",request));
			map.put("formId", "viewResumeProcessForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", result);
		}
		return map;
	}

	/**
	 * 进入部门添加页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewModifyOrgInfoTree")
	public ModelAndView viewModifyOrgInfoTree(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		modelMap.put("RESUME_NO", paramMap.get("RESUME_NO"));
		modelMap.put("DEPTNO", paramMap.get("DEPTNO"));
		return new ModelAndView("/org/orgManage/viewModifyOrgInfoTree", modelMap);
	}
	
	/**
	 * 部门修改
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewModifyOrgInfo")
	public ModelAndView viewModifyOrgInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		List orgResumeDeptList = this.orgManageSer.viewResumeDeptList(request);

		LinkedHashMap param = null;
		
		if(orgResumeDeptList!=null && orgResumeDeptList.size() > 0){
			param = (LinkedHashMap)orgResumeDeptList.get(0);
		}
		modelMap.put("orgInfo", param);
		modelMap.put("RESUME_NO", paramMap.get("RESUME_NO"));
		modelMap.put("PARENT_DEPT_NO", paramMap.get("PARENT_DEPT_NO"));
		modelMap.put("resumeActivityInfo", this.orgManageSer.getResumeActivity(request, StringUtil.checkNull(paramMap.get("RESUME_NO"))));
		

		return new ModelAndView("/org/orgManage/viewModifyOrgInfo", modelMap);
	}
	

	/**
	 * 人员移动
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewModifyEmpInfo")
	public ModelAndView viewModifyEmpInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List orgResumeEmpList = this.orgManageSer.viewResumeEmpList(request);

		modelMap.put("orgResumeEmpList", orgResumeEmpList);
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		modelMap.put("resumeActivityInfo", this.orgManageSer.getResumeActivity(request, StringUtil.checkNull(paramMap.get("RESUME_NO"))));
		modelMap.put("RESUME_NO", paramMap.get("RESUME_NO"));

		return new ModelAndView("/org/orgManage/viewModifyEmpInfo", modelMap);
	}
	
	private String getCurrentResumeNO(HttpServletRequest request, List resumeList){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		String resumeNo = StringUtil.checkNull(paramMap.get("RESUME_NO"));
		if(resumeNo == null || "".equals(resumeNo)){
			if(resumeList != null && resumeList.size() > 0){
				Map resumeInfoMap = (LinkedHashMap) resumeList.get(0);
				resumeNo = resumeInfoMap.get("NO").toString();
			}
		}
		return resumeNo;
	}

	/**
	 * 进入部门添加页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddOrgInfo")
	public ModelAndView viewAddOrgInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		List list = this.orgManageSer.getResumneNo(request);

		modelMap.put("RESUME_NO", paramMap.get("RESUME_NO"));
		modelMap.put("PARENT_DEPT_NO", paramMap.get("PARENT_DEPT_NO"));
		modelMap.put("START_DATE", DateUtil.getSysdateStr("dd/MM/yyyy"));
		
		modelMap.put("resumeActivityInfo", this.orgManageSer.getResumeActivity(request, StringUtil.checkNull(paramMap.get("RESUME_NO"))));

		return new ModelAndView("/org/orgManage/viewAddOrgInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDeptNoMax")
	@ResponseBody
	public Map getDeptNoMax(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		 String DeptNoMax = "";
		 String cpnyId = (String) paramMap.get("cpnyId");
			 List list = this.orgManageSer.getDeptNoMax(request);
			 Object o[] = list.toArray();
			 BigInteger DeptNoMaxNu = new BigInteger((String) o[0]).add(new BigInteger("1"));
			 DeptNoMax = String.valueOf(DeptNoMaxNu);
		    if(DeptNoMaxNu.compareTo(new BigInteger("10")) < 0){
		    	DeptNoMax = "0" + DeptNoMax;
		    }
		    if(DeptNoMax.length()>2){
		    	DeptNoMax = DeptNoMax.substring(DeptNoMax.length()-2);
		    }
		    DeptNoMax =  paramMap.get("parent_dept_no") + DeptNoMax;
		modelMap.put("DeptNoMax", DeptNoMax);
		return modelMap;
	}

	/**
	 * 移动部门人员
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/executeMoveEmp")
	@ResponseBody
	public Map executeMoveEmp(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.orgManageSer.executeMoveEmp(request);
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.org.execute_success",request));
			map.put("divId", "viewModifyOrgEmpInfo_unit");
			map.put("divIdUrl", "/org/orgManage/viewModifyEmpInfo?DEPTNO=" + paramMap.get("DEPTNO") + "&RESUME_NO=" + paramMap.get("RESUME_NO"));
		} else {
			map.put("statusCode", "300");
			map.put("message", result);
		}
		return map;
	}
	
	/**
	 * 添加组织
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addOrgInfo")
	@ResponseBody
	public Map addOrgInfo(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.addOrgInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("divId", "viewModifyOrgInfo_tree_unit");
			map.put("divIdUrl", "/org/orgManage/viewModifyOrgInfoTree?DEPTNO=" + paramMap.get("DEPTNO") + "&RESUME_NO=" + paramMap.get("RESUME_NO"));
		} else if (result == 2) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.org.deptno_repeat",request));
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
		}
		return map;
	}
	

	/**
	 * 删除组织
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteOrgInfo")
	@ResponseBody
	public Map deleteOrgInfo(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.orgManageSer.deleteOrgInfo(request);
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.delete_success",request));
			map.put("formId", "viewComposeOrgForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", result);
		}
		return map;
	}
	
	/**
	 * 部门顺序再定义
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewModifyOrgOrderNo")
	public ModelAndView viewModifyOrgOrderNo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);


		List modifyOrgOrderNoList = this.orgManageSer.viewModifyOrgOrderNo(request);

		modelMap.put("modifyOrgOrderNoList", modifyOrgOrderNoList);


		modelMap.put("RESUME_NO", paramMap.get("RESUME_NO"));
		modelMap.put("PARENT_DEPT_NO", paramMap.get("PARENT_DEPT_NO"));
		
		return new ModelAndView("/org/orgManage/viewModifyOrgOrderNo", modelMap);
	}
	

	/**
	 * 部门顺序再定义
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyOrgOrderNo")
	@ResponseBody
	public Map modifyOrgOrderNo(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.orgManageSer.modifyOrgOrderNo(request);
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("divId", "viewModifyOrgInfo_tree_unit");
			map.put("divIdUrl", "/org/orgManage/viewModifyOrgInfoTree?DEPTNO=" + paramMap.get("PARENT_DEPT_NO") + "&RESUME_NO=" + paramMap.get("RESUME_NO"));
		} else {
			map.put("statusCode", "300");
			map.put("message", result);
		}
		return map;
	}
	
	/**
	 * 部门合并
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewModifyOrgMerge")
	public ModelAndView viewModifyOrgMerge(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);


		List modifyOrgOrderNoList = this.orgManageSer.viewModifyOrgOrderNo(request);

		modelMap.put("modifyOrgOrderNoList", modifyOrgOrderNoList);


		modelMap.put("deptTree", JsonUtil.writeInternal(this.paSupervisorSer.getDeptTreeResumeNo(request)));
		modelMap.put("RESUME_NO", paramMap.get("RESUME_NO"));
		modelMap.put("PARENT_DEPT_NO", paramMap.get("PARENT_DEPT_NO"));
		
		return new ModelAndView("/org/orgManage/viewModifyOrgMerge", modelMap);
	}
	

	/**
	 * 部门顺序再定义
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyOrgMerge")
	@ResponseBody
	public Map modifyOrgMerge(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.orgManageSer.modifyOrgMerge(request);
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("divId", "viewModifyOrgInfo_tree_unit");
			map.put("divIdUrl", "/org/orgManage/viewModifyOrgInfoTree?DEPTNO=" + paramMap.get("PARENT_DEPT_NO") + "&RESUME_NO=" + paramMap.get("RESUME_NO"));
		} else {
			map.put("statusCode", "300");
			map.put("message", result);
		}
		return map;
	}
	
	/**
	 * 部门合并
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewModifyOrgSplit")
	public ModelAndView viewModifyOrgSplit(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);


		List modifyOrgOrderNoList = this.orgManageSer.viewModifyOrgOrderNo(request);

		modelMap.put("modifyOrgOrderNoList", modifyOrgOrderNoList);


		modelMap.put("deptTree", JsonUtil.writeInternal(this.paSupervisorSer.getDeptTreeResumeNo(request)));
		modelMap.put("RESUME_NO", paramMap.get("RESUME_NO"));
		modelMap.put("PARENT_DEPT_NO", paramMap.get("PARENT_DEPT_NO"));
		
		return new ModelAndView("/org/orgManage/viewModifyOrgSplit", modelMap);
	}
	

	/**
	 * 部门顺序再定义
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyOrgSplit")
	@ResponseBody
	public Map modifyOrgSplit(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.orgManageSer.modifyOrgSplit(request);
		if ("OK".equals(result)) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("DEPTNO", paramMap.get("DEPTNO"));
			map.put("divId", "viewModifyOrgInfo_tree_unit");
			map.put("divIdUrl", "/org/orgManage/viewModifyOrgInfoTree?DEPTNO=" + paramMap.get("NEW_DEPTNO") + "&RESUME_NO=" + paramMap.get("RESUME_NO"));
		} else {
			map.put("statusCode", "300");
			map.put("message", result);
		}
		return map;
	}

	/**
	 * 部门任职者检查
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgSplitTemp")
	public ModelAndView viewOrgSplitTemp(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		List viewOrgSplitTemp = this.orgManageSer.viewOrgSplitTemp(request);

		modelMap.put("viewOrgSplitTemp", viewOrgSplitTemp);
		modelMap.put("DEPTNO", paramMap.get("DEPTNO"));
		modelMap.put("FLAG", paramMap.get("FLAG"));
		
		return new ModelAndView("/org/orgManage/viewOrgSplitTemp", modelMap);
	}
	
	/**
	 * 部门任职者检查
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewDeptManagerCheck")
	public ModelAndView viewDeptManagerCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		

		List deptManagerCheckList = this.orgManageSer.viewDeptManagerCheck(request);

		List orgResumeList = this.orgManageSer.viewResumeList(request);
		String RESUME_NO = getCurrentResumeNO( request, orgResumeList);
		modelMap.put("RESUME_NO", RESUME_NO);
		
		modelMap.put("orgResumeList", orgResumeList);
		modelMap.put("deptManagerCheckList", deptManagerCheckList);
		modelMap.put("deptManagerCheckCnt", deptManagerCheckList.size());
		modelMap.put("resumeActivityInfo", this.orgManageSer.getResumeActivity(request, RESUME_NO));

		
		return new ModelAndView("/org/orgManage/viewDeptManagerCheck", modelMap);
	}
	

	/**
	 * 修改部门领导相关信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveDeptManager")
	@ResponseBody
	public Map saveDeptManager(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.saveDeptManager(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("formId", "viewModifyOrgInfo_tree_unit");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
		}
		return map;
	}
	
	/**
	 * 概要  发令list
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgExperirnceInsideList")
	public ModelAndView viewOrgExperirnceInsideList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List orgExperirnceInsideList = this.orgManageSer.viewOrgExperirnceInsideList(request);
		modelMap.put("orgExperirnceInsideList", orgExperirnceInsideList);

		LinkedHashMap param = null;
		
		if(orgExperirnceInsideList!=null && orgExperirnceInsideList.size() > 0){
			param = (LinkedHashMap)orgExperirnceInsideList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("orgExperirnceInsideSize", orgExperirnceInsideList.size());
		}else{
			modelMap.put("orgExperirnceInsideSize", 0);
		}

		List orgResumeList = this.orgManageSer.viewResumeList(request);
		String RESUME_NO = getCurrentResumeNO( request, orgResumeList);
		modelMap.put("RESUME_NO", RESUME_NO);
		modelMap.put("orgResumeList", orgResumeList);
		modelMap.put("resumeActivityInfo", this.orgManageSer.getResumeActivity(request, RESUME_NO));
		return new ModelAndView("/org/orgManage/viewOrgExperirnceInsideList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddOrgExperirnceInsideInfo")
	public ModelAndView viewAddOrgExperirnceInsideInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List orgOrgExperirnceInsideList = this.orgManageSer.viewOrgExperirnceInsideList(request);

		LinkedHashMap param = null;
		
		if(orgOrgExperirnceInsideList!=null && orgOrgExperirnceInsideList.size() > 0){
			param = (LinkedHashMap)orgOrgExperirnceInsideList.get(0);
		}
		modelMap.put("OrgExperirnceInsideInfo", param);

		return new ModelAndView("/org/orgManage/viewAddOrgExperirnceInsideInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addOrgExperirnceInsideInfo")
	@ResponseBody
	public Map addOrgExperirnceInsideInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.addOrgExperirnceInsideInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("navTabId", "org0205");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
		}
		return map;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteOrgExperirnceInsideInfo")
	@ResponseBody
	public Map deleteOrgExperirnceInsideInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.deleteOrgExperirnceInsideInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));
			map.put("formId", "viewOrgExperirnceInsideListForm");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));
		}
		return map;
	}
	

	/**
	 * 概要  预发令 核查list
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgPreExperirnceInsideList")
	public ModelAndView viewOrgPreExperirnceInsideList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String DEPT_NO_SQL = "SELECT DEPTNO || ' > ' || ORG_NAME_LOCAL DESCRIPTION,DEPTNO CODE_NO,ORG_NAME_LOCAL CODENAME FROM HR_DEPARTMENT WHERE CPNY_ID = '" + admin.getCpnyId() + "' ";
		String COST_CENTER_SQL = "SELECT CODE_NO || ' > ' || CODE_NAME DESCRIPTION, CODE_NO,CODE_NAME CODENAME FROM ORG_COST_CENTER WHERE ACTIVITY = '14013912' AND CPNY_ID = '" + admin.getCpnyId() + "' ";
		
		List preExpInfoList = this.orgManageSer.viewOrgPreExperirnceInsideList(request);
		modelMap.put("preExpInfoList", preExpInfoList);

		modelMap.put("preExpInfoListSize", preExpInfoList == null ? 0 : preExpInfoList.size());
		
		modelMap.put("cbzx" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(COST_CENTER_SQL)));  //成本中心
		modelMap.put("dept" , JsonUtil.writeInternal(empInfoSer.getCodeListBySql(DEPT_NO_SQL)));  //部门
		
		List orgResumeList = this.orgManageSer.viewResumeList(request);
		String RESUME_NO = getCurrentResumeNO( request, orgResumeList);
		modelMap.put("RESUME_NO", RESUME_NO);
		modelMap.put("orgResumeList", orgResumeList);
		modelMap.put("resumeActivityInfo", this.orgManageSer.getResumeActivity(request, RESUME_NO));
		return new ModelAndView("/org/orgManage/viewOrgPreExperirnceInsideList", modelMap);
	}
	

	/**
	 * 变更履历部门list
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgChangeInfoList")
	public ModelAndView viewOrgChangeInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List orgChangeInfoList = this.orgManageSer.viewOrgChangeInfoList(request);
		modelMap.put("orgChangeInfoList", orgChangeInfoList);

		LinkedHashMap param = null;
		
		if(orgChangeInfoList!=null && orgChangeInfoList.size() > 0){
			param = (LinkedHashMap)orgChangeInfoList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("orgChangeInfoSize", orgChangeInfoList.size());
		}else{
			modelMap.put("orgChangeInfoSize", 0);
		}
		
		List orgResumeList = this.orgManageSer.viewResumeList(request);
		modelMap.put("orgResumeList", orgResumeList);
		String RESUME_NO = getCurrentResumeNO( request, orgResumeList);
		modelMap.put("RESUME_NO", RESUME_NO);
		modelMap.put("resumeActivityInfo", this.orgManageSer.getResumeActivity(request, RESUME_NO));
		return new ModelAndView("/org/orgManage/viewOrgChangeInfoList", modelMap);
	}
	

	/**
	 * 变更履历个人list
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEmpChangeInfoList")
	public ModelAndView viewEmpChangeInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List empChangeInfoList = this.orgManageSer.viewEmpChangeInfoList(request);
		modelMap.put("empChangeInfoList", empChangeInfoList);

		modelMap.put("empChangeInfoListSize", empChangeInfoList == null ? 0 : empChangeInfoList.size());
		
		List orgResumeList = this.orgManageSer.viewResumeList(request);
		modelMap.put("orgResumeList", orgResumeList);

		String RESUME_NO = getCurrentResumeNO( request, orgResumeList);
		modelMap.put("RESUME_NO", RESUME_NO);
		return new ModelAndView("/org/orgManage/viewEmpChangeInfoList", modelMap);
	}
	
	/**
	 * 修改部门领导相关信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveOrgChangeInfo")
	@ResponseBody
	public Map saveOrgChangeInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.saveOrgChangeInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("navTabId", "org0208");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
		}
		return map;
	}

	/**
	 * 变更查看List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewChangeInfoList")
	public ModelAndView viewChangeInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){
			modelMap.put("currentIndex", 1);
		}else{
			modelMap.put("currentIndex", 0);
		}
		
		return new ModelAndView("/org/orgManage/viewChangeInfoList", modelMap);
	}
	

	/**
	 * 变更详细信息查看List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewChangeDetailInfoList")
	public ModelAndView viewChangeDetailInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){
			List empChangeInfoList = this.orgManageSer.viewChangeDetailEmpInfoList(request);
			modelMap.put("empChangeInfoList", empChangeInfoList);
			modelMap.put("currentIndex", 1);
		}else{
			List orgChangeInfoList = this.orgManageSer.viewChangeDetailOrgInfoList(request);
			modelMap.put("orgChangeInfoList", orgChangeInfoList);
			modelMap.put("currentIndex", 0);
		}
		
		return new ModelAndView("/org/orgManage/viewChangeDetailInfoList", modelMap);
	}
	

	/**
	 * 变更查看List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCommonOrgTreeInfo")
	public ModelAndView viewCommonOrgTreeInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List orgResumeList = this.orgManageSer.viewResumeList(request);
		String RESUME_NO = getCurrentResumeNO( request, orgResumeList);
		modelMap.put("RESUME_NO", RESUME_NO);
		modelMap.put("orgResumeList", orgResumeList);
		modelMap.put("targetRel", request.getParameter("targetRel"));
		modelMap.put("resumeActivityInfo", this.orgManageSer.getResumeActivity(request, RESUME_NO));
		
		return new ModelAndView("/org/orgManage/viewCommonOrgTreeInfo", modelMap);
	}
	

	/**
	 * 部门业务管理List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgBusinessManagerInfo")
	public ModelAndView viewOrgBusinessManagerInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/org/orgManage/viewOrgBusinessManagerInfo", modelMap);
	}
	

	/**
	 * 部门成本中心管理List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgCostCenterManagerInfo")
	public ModelAndView viewOrgCostCenterManagerInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/org/orgManage/viewOrgCostCenterManagerInfo", modelMap);
	}
	

	/**
	 * 部门产品类型管理List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgProductManagerInfo")
	public ModelAndView viewOrgProductManagerInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/org/orgManage/viewOrgProductManagerInfo", modelMap);
	}
	
	/**
	 * 工作地管理List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewWorkAreaManagerInfo")
	public ModelAndView viewWorkAreaManagerInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List orgWorkAreaList = this.orgManageSer.viewWorkAreaList(request);
		modelMap.put("orgWorkAreaList", orgWorkAreaList);

		LinkedHashMap param = null;
		
		if(orgWorkAreaList!=null && orgWorkAreaList.size() > 0){
			param = (LinkedHashMap)orgWorkAreaList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("orgWorkAreaSize", orgWorkAreaList.size());
		}else{
			modelMap.put("orgWorkAreaSize", 0);
		}
		return new ModelAndView("/org/orgManage/viewWorkAreaManagerInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddWorkAreaInfo")
	public ModelAndView viewAddWorkAreaInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List orgWorkAreaList = this.orgManageSer.viewWorkAreaList(request);

		LinkedHashMap param = null;
		
		if(orgWorkAreaList!=null && orgWorkAreaList.size() > 0){
			param = (LinkedHashMap)orgWorkAreaList.get(0);
		}
		modelMap.put("WorkAreaInfo", param);
		modelMap.put("START_DATE", DateUtil.getSysdateStr("yyyy.MM.dd"));
		modelMap.put("END_DATE", "9999.12.31");

		return new ModelAndView("/org/orgManage/viewAddWorkAreaInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addWorkAreaInfo")
	@ResponseBody
	public Map addWorkAreaInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.addWorkAreaInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("navTabId", "org0305");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
		}
		return map;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteWorkAreaInfo")
	@ResponseBody
	public Map deleteWorkAreaInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.deleteWorkAreaInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));
			map.put("navTabId", "org0305");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));
		}
		return map;
	}
	

	/**
	 * 成本中心管理List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCostCenterManagerInfo")
	public ModelAndView viewCostCenterManagerInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List orgCostCenterList = this.orgManageSer.viewCostCenterList(request);
		modelMap.put("orgCostCenterList", orgCostCenterList);

		LinkedHashMap param = null;
		
		if(orgCostCenterList!=null && orgCostCenterList.size() > 0){
			param = (LinkedHashMap)orgCostCenterList.get(0);
			if(request.getParameter("seachSEQ") != null){
				modelMap.put("SEQ", request.getParameter("seachSEQ"));
			}else{
				modelMap.put("SEQ", param.get("SEQ"));
			}
			modelMap.put("orgCostCenterSize", orgCostCenterList.size());
		}else{
			modelMap.put("orgCostCenterSize", 0);
		}
		return new ModelAndView("/org/orgManage/viewCostCenterManagerInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddCostCenterInfo")
	public ModelAndView viewAddCostCenterInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List orgCostCenterList = this.orgManageSer.viewCostCenterList(request);

		LinkedHashMap param = null;
		
		if(orgCostCenterList!=null && orgCostCenterList.size() > 0){
			param = (LinkedHashMap)orgCostCenterList.get(0);
		}
		modelMap.put("CostCenterInfo", param);
		modelMap.put("START_DATE", DateUtil.getSysdateStr("dd/MM/yyyy"));
		modelMap.put("END_DATE", "31/12/9999");

		return new ModelAndView("/org/orgManage/viewAddCostCenterInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addCostCenterInfo")
	@ResponseBody
	public Map addCostCenterInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.orgManageSer.addCostCenterInfo(request);
		if ("0".equals(result)) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
		} else if ("2".equals(result)) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.org.cost_center_repeat",request));
		} else {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("forwardUrl", "/org/orgManage/viewCostCenterManagerInfo?seachSEQ=" + result);
		}
		return map;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteCostCenterInfo")
	@ResponseBody
	public Map deleteCostCenterInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.deleteCostCenterInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));
			map.put("navTabId", "org0304");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));
		}
		return map;
	}
	
	

	/**
	 * 部门业务管理List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBusinessManagerInfo")
	public ModelAndView viewBusinessManagerInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		if(request.getParameter("FLAG") == null){
			this.orgManageSer.deleteEmptyBusinessInfo(request);
		}else{
			this.orgManageSer.addBusinessInfo(request);
		}
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		List orgBusinessList = this.orgManageSer.viewBusinessList(request);
		modelMap.put("orgBusinessList", orgBusinessList);

		LinkedHashMap param = null;
		
		if(orgBusinessList!=null && orgBusinessList.size() > 0){
			param = (LinkedHashMap)orgBusinessList.get(0);
			modelMap.put("SEQ", param.get("SEQ"));
			modelMap.put("DEPTNO", param.get("DEPTNO"));
			modelMap.put("orgBusinessSize", orgBusinessList.size());
		}else{
			modelMap.put("orgBusinessSize", 0);
			modelMap.put("DEPTNO", paramMap.get("DEPTNO"));
		}
		modelMap.put("CODE_NAME" , JsonUtil.writeInternal(empInfoSer.getCodeListDESCRIPTION("400098", request))); //主要业务
		modelMap.put("RESUME_NO", paramMap.get("RESUME_NO"));
		return new ModelAndView("/org/orgManage/viewBusinessManagerInfo", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveBusinessInfo")
	@ResponseBody
	public Map saveBusinessInfo(HttpServletRequest request) throws Exception  {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.saveBusinessInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("divId", "viewBusinessManagerInfo_right_unit");
			map.put("divIdUrl", "/org/orgManage/viewBusinessManagerInfo?DEPTNO=" + paramMap.get("DEPTNO") + "&RESUME_NO=" + paramMap.get("RESUME_NO"));
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addBusinessInfo")
	@ResponseBody
	public Map addBusinessInfo(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.addBusinessInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.org.add_success",request));
			map.put("divId", "viewBusinessManagerInfo_right_unit");
			map.put("divIdUrl", "/org/orgManage/viewBusinessManagerInfo?DEPTNO=" + paramMap.get("DEPTNO") + "&RESUME_NO=" + paramMap.get("RESUME_NO"));
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.org.add_failure",request));
		}
		return map;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteBusinessInfo")
	@ResponseBody
	public Map deleteBusinessInfo(HttpServletRequest request) throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.deleteBusinessInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));
			map.put("divId", "viewBusinessManagerInfo_right_unit");
			map.put("divIdUrl", "/org/orgManage/viewBusinessManagerInfo?DEPTNO=" + paramMap.get("DEPTNO") + "&RESUME_NO=" + paramMap.get("RESUME_NO"));
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));
		}
		return map;
	}
	
	/**
	 * 现组织查询
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCurrentOrgInfo")
	public ModelAndView viewCurrentOrgInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List orgCostCenterList = this.orgManageSer.viewCostCenterList(request);

		LinkedHashMap param = null;
		
		if(orgCostCenterList!=null && orgCostCenterList.size() > 0){
			param = (LinkedHashMap)orgCostCenterList.get(0);
		}
		modelMap.put("CostCenterInfo", param);

		return new ModelAndView("/org/orgManage/viewCurrentOrgInfo", modelMap);
	}
	

	/**
	 * 当前组织 及 组织内人员信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCurrentOrgDetailInfo")
	public ModelAndView viewCurrentOrgDetailInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List currentOrgEmpInfoList = this.orgManageSer.viewCurrentOrgDetailEmpInfo(request);

		List currentOrgInfoList = this.orgManageSer.viewCurrentOrgDetailOrgInfo(request);

		LinkedHashMap param = null;
		
		if(currentOrgInfoList!=null && currentOrgInfoList.size() > 0){
			param = (LinkedHashMap)currentOrgInfoList.get(0);
		}
		modelMap.put("currentOrgEmpInfoList", currentOrgEmpInfoList);
		modelMap.put("currentOrgInfo", param);

		return new ModelAndView("/org/orgManage/viewCurrentOrgDetailInfo", modelMap);
	}
	
	/**
	 * 根据日期获取概要NO
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewHistoryOrgInfo")
	public ModelAndView viewHistoryOrgInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String orgHistoryResumneNo = "";
		List orgHistoryResumneNoList = this.orgManageSer.getHistoryResumneNo(request);
		if(orgHistoryResumneNoList != null && orgHistoryResumneNoList.size() > 0){
			LinkedHashMap map= (LinkedHashMap)orgHistoryResumneNoList.get(0);
			orgHistoryResumneNo = StringUtil.checkNull(map.get("RESUME_NO"));
			modelMap.put("RESUME_DATE", StringUtil.checkNull(map.get("RESUME_DATE")));
		}
		modelMap.put("RESUME_NO", orgHistoryResumneNo);
		
		return new ModelAndView("/org/orgManage/viewHistoryOrgInfo", modelMap);
	}
	

	/**
	 * 历史组织 组织树
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewHistoryOrgTreeInfo")
	public ModelAndView viewHistoryOrgTreeInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("RESUME_NO", paramMap.get("RESUME_NO"));
		
		return new ModelAndView("/org/orgManage/viewHistoryOrgTreeInfo", modelMap);
	}
	

	/**
	 * 历史组织 标签页
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewHistoryOrgPanel")
	public ModelAndView viewHistoryOrgPanel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){
			modelMap.put("currentIndex", 1);
		}else if("2".equals(currentIndex)){
			modelMap.put("currentIndex", 2);
		}else{
			modelMap.put("currentIndex", 0);
		}
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		modelMap.put("SON_FLAG", request.getParameter("SON_FLAG"));
		
		return new ModelAndView("/org/orgManage/viewHistoryOrgPanel", modelMap);
	}
	

	/**
	 * 历史组织 详细信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewHistoryDetailInfoList")
	public ModelAndView viewHistoryDetailInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		if("0".equals(currentIndex)){
			List orgResumeDeptList = this.orgManageSer.viewResumeDeptList(request);
			modelMap.put("orgInfo", orgResumeDeptList);
			modelMap.put("currentIndex", 0);
		}else if("1".equals(currentIndex)){
			List orgResumeEmpList = this.orgManageSer.viewHistoryResumeEmpList(request);
			modelMap.put("orgResumeEmpList", orgResumeEmpList);
			modelMap.put("currentIndex", 1);
		}else{
			List orgResumeOrgList = this.orgManageSer.viewResumeOrgList(request);
			modelMap.put("orgResumeOrgList", orgResumeOrgList);
			modelMap.put("currentIndex", 2);
		}
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		modelMap.put("SON_FLAG", request.getParameter("SON_FLAG"));
		
		return new ModelAndView("/org/orgManage/viewHistoryDetailInfoList", modelMap);
	}
	

	/**
	 * 删除附件
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteFile")
	@ResponseBody
	public Map deleteFile(HttpServletRequest request)
			throws Exception {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.deleteFile(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("divId", paramMap.get("typeId"));
			map.put("divIdUrl", paramMap.get("typeValue"));
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	/**
	 * 保存预发令信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPreExperirnceInsideInfo")
	@ResponseBody
	public Map addPreExperirnceInsideInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.addPreExperirnceInsideInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation",request));
			map.put("navTabId", "org0206");
		} else {
			map.put("statusCode", "300");
			map.put("message",TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure",request));
		}
		return map;
	}
	
	/**
	 * 组织履历查看List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgChangePanel")
	public ModelAndView viewOrgChangePanelList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){
			modelMap.put("currentIndex", 1);
		}else{
			modelMap.put("currentIndex", 0);
		}
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		
		return new ModelAndView("/org/orgManage/viewOrgChangePanel", modelMap);
	}
	

	/**
	 * 组织履历查看List
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOrgChangeList")
	public ModelAndView viewOrgChangeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String currentIndex = request.getParameter("currentIndex");
		if("1".equals(currentIndex)){
			List orgManagerList = this.orgManageSer.viewOrgManagerList(request);
			modelMap.put("orgManagerList", orgManagerList);
			modelMap.put("currentIndex", 1);
		}else{
			List orgInfoList = this.orgManageSer.viewOrgInfoList(request);
			modelMap.put("orgInfoList", orgInfoList);
			modelMap.put("currentIndex", 0);
		}
		modelMap.put("RESUME_NO", request.getParameter("RESUME_NO"));
		modelMap.put("DEPTNO", request.getParameter("DEPTNO"));
		
		return new ModelAndView("/org/orgManage/viewOrgChangeList", modelMap);
	}
	
	/**
	 * 第一次进入页面取RESUME_SEQ，以便其他信息查询
	 * @param request
	 * @param resumeList
	 * @param modelMap
	 */
	private void getResumeSeq(HttpServletRequest request,List resumeList,ModelMap modelMap){
		String RESUME_SEQ = StringUtil.checkNull(request.getParameter("RESUME_NO"));
		//第一次进入页面取RESUME_SEQ，以便其他信息查询
		if("".equals(RESUME_SEQ)){
			if(resumeList != null && resumeList.size() > 0){
				Map map = (Map)resumeList.get(0);
				modelMap.put("RESUME_NO", map.get("NO"));
			}
		}
	}

	/**
	 * 检查部门领导是否重复任职
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkDeptManager")
	@ResponseBody
	public Map checkDeptManager(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.orgManageSer.checkDeptManager(request);
		map.put("result", result);
		return map;
	}
	
	/**
	 * 休假导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportDeptTempList")
	public ModelAndView viewImportDeptTempList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paDeptTempList = this.orgManageSer.getDeptTempList(request);
		int paDeptTempCnt = this.orgManageSer.getDeptTempCnt(request , "T");
		int errorCnt = this.orgManageSer.getDeptTempCnt(request , "E");
		
		modelMap.put("paDeptTempList", paDeptTempList);
		modelMap.put("paDeptTempCnt", paDeptTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paDeptTempCnt);
		return new ModelAndView("/org/orgManage/viewImportDeptTempList", modelMap);
	}

	/**
	 * 休假批量申请excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelDeptData")
	@ResponseBody
	public Map submitImportExcelDeptData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.orgManageSer.submitImportExcelDeptData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");//保存成功
			jo.put("navTabId", "org0203");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");//保存失败
		}
		return jo;
	}
	
}
