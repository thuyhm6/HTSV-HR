package com.ait.ess.action;

import java.util.HashMap;
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

import com.ait.ess.service.DimissionEditionSer;
import com.ait.hrm.service.ContractInfoSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.org.service.OrgManageSer;
import com.ait.pa.service.salary.PaResultSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/ess/dimissionApply")
public class DimissionEditionCtroller {

	@Autowired
	private DimissionEditionSer dimissionEditionSer;
	@Autowired
	private OrgManageSer orgManageSer;
	@Autowired
	private CompanySer companySer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private AuthorityUtil authorityUtil;

	/****
	 * 离职交接表List（模版）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewDimissionEditionList")
	public ModelAndView viewDimissionEditionList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		try {
			List editionList = dimissionEditionSer
					.getDimissionEditionList(request);
			int editionCnt = dimissionEditionSer
					.getDimissionEditionCnt(request);
			modelMap.put("searchMap",
					ObjectBindUtil.getRequestParamData(request));
			modelMap.put("editionList", editionList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, editionCnt);
			modelMap.put(
					"toolbarInfo",
					request.getParameter("menuNo") != null ? toolMenuSer
							.getToolMenu(request) : toolMenuSer
							.getToolMenuForNo(request, "124942"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("/ess/dimissionApply/viewDimissionEditionList",
				modelMap);
	}
	
	/**
	 * 去添加模版页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addDimissionEditionView")
	public ModelAndView addDimissionEditionView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List empTypeNameList = this.dimissionEditionSer
				.getEmpTypeNameListAdd(request);
		modelMap.put("empTypeNameList", empTypeNameList);
		return new ModelAndView("/ess/dimissionApply/addDimissionEditionView",
				modelMap);
	}

	/**
	 * 添加模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addDimissionEditionInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map addDimissionEditionInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.dimissionEditionSer
					.getDimissionEditionListAdd(request);
			if (errorInt == 0) {
				int result = this.dimissionEditionSer
						.addDimissionEditionInfo(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));// 添加成功
					map.put("navTabId", "sys2014");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));// 保存失败
				}
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"ess.edition.title.editionexist", request));//版本号已经存在，请重新添加
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 去修改模版页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateDimissionEditionInfoView")
	public ModelAndView updateDimissionEditionInfoView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List empTypeNameList = this.dimissionEditionSer
				.getEmpTypeNameListUpdate(request);
		List editionList = dimissionEditionSer
				.getDimissionEditionListUpdate(request);
		modelMap.put("editionList", editionList);
		modelMap.put("empTypeNameList", empTypeNameList);
		modelMap.put("editionInfo",
				dimissionEditionSer.getDimissionEditionInfo(request));
		return new ModelAndView(
				"/ess/dimissionApply/updateDimissionEditionInfoView", modelMap);
	}

	/**
	 * 修改模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateDimissionEditionInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map updateDimissionEditionInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.dimissionEditionSer
					.updateDimissionEditionInfo(request);
			int result = this.dimissionEditionSer
					.addDimissionEditionInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));// 修改成功
				map.put("navTabId", "sys2014");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));// 修改失败
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 删除模版
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteDimissionEditionInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDimissionEditionInfo(
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.dimissionEditionSer
					.deleteDimissionEditionInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));// 删除成功
				map.put("navTabId", "sys2014");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));// 删除失败
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/************************************交接类型管理**********************************************/
	
	/**
	 * 查找离职交接内容表的离职交接内容类型的List 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/viewEditionItemTypeList")
	public ModelAndView viewEditionItemTypeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("cpnyList", this.companySer.getCompanyItemAllList(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin.getCpnyId() : paramMap.get("CPNY_ID").toString());
		List editionItemTypeList = this.dimissionEditionSer.getEditionItemTypeList(request);
		int editionItemTypeCnt = this.dimissionEditionSer.getEditionItemTypeCnt(request);
		modelMap.put("editionItemTypeList", editionItemTypeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, editionItemTypeCnt);
		
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer
						.getToolMenuForNo(request, "124942"));
		return new ModelAndView("/ess/dimissionApply/viewEditionItemTypeList", modelMap);
	}

	/**
	 * 去添加交接类型页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEditionItemTypeInfoView")
	public ModelAndView addEditionItemTypeInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List editionList = dimissionEditionSer.getDimissionEditionlist(request);
		modelMap.put("editionList", editionList);
		return new ModelAndView("/ess/dimissionApply/addEditionItemTypeInfoView",
				modelMap);
	}

	/**
	 * 添加交接类型
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEditionItemTypeInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map addEditionItemTypeInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
//			int errorInt = this.dimissionEditionSer
//					.getDimissionEditionListAdd(request);
//			if (errorInt == 0) {
				int result = this.dimissionEditionSer
						.addEditionItemTypeInfo(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));// 添加成功
					map.put("navTabId", "sys2016");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));// 保存失败
				}
//			}else{
//				map.put("statusCode", "300");
//				map.put("message", TipMessage.getTipMessage(
//						"ess.edition.title.editionexist", request));//版本号已经存在，请重新添加
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 去修改交接类型页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateEditionItemTypeInfoView")
	public ModelAndView updateEditionItemTypeInfoView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List editionList = dimissionEditionSer.getDimissionEditionlist(request);
		modelMap.put("editionList", editionList);
		modelMap.put("editionItemTypeInfo",
				dimissionEditionSer.getEditionItemTypeInfo(request));
		return new ModelAndView(
				"/ess/dimissionApply/updateEditionItemTypeInfoView", modelMap);
	}

	/**
	 * 修改交接类型
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateEditionItemTypeInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map updateEditionItemTypeInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.dimissionEditionSer
					.updateEditionItemTypeInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));// 修改成功
				map.put("navTabId", "sys2016");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));// 修改失败
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 删除交接类型
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteEditionItemTypeInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteEditionItemTypeInfo(
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.dimissionEditionSer
					.deleteEditionItemTypeInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));// 删除成功
				map.put("navTabId", "sys2016");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));// 删除失败
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	/************************************交接类型项目管理**********************************************/
	
	/**
	 * 查找离职交接内容表的离职交接内容类型的List 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */ 
	@SuppressWarnings( { "unchecked" })
	@RequestMapping(value = "/viewEditionItemTypeData")
	public ModelAndView viewEditionItemTypeData(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("cpnyList", this.companySer.getCompanyItemAllList(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		String EDITION_NO = request.getParameter("EDITION_NO");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin.getCpnyId() : paramMap.get("CPNY_ID").toString());
		List editionItemTypeList = this.dimissionEditionSer.getEditionItemTypeList(request);
		List editionList = dimissionEditionSer.getDimissionEditionList(request);
		modelMap.put("editionItemTypeList", editionItemTypeList);
		modelMap.put("editionList", editionList);
		modelMap.put("EDITION_NO", EDITION_NO);
		return new ModelAndView("/ess/dimissionApply/viewEditionItemTypeData", modelMap);
	}
	
	/****
	 * 离职交接表List（模版）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEditionItemTypeParamList")
	public ModelAndView viewEditionItemTypeParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		try {
			List editionItemParamList = dimissionEditionSer.getEditionItemTypeParamList(request);
			int editionItemParamCnt = dimissionEditionSer.getEditionItemTypeParamCnt(request);
			modelMap.put("searchMap",
					ObjectBindUtil.getRequestParamData(request));
			modelMap.put("editionItemParamList", editionItemParamList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, editionItemParamCnt);
			modelMap.put(
					"toolbarInfo",
					request.getParameter("menuNo") != null ? toolMenuSer
							.getToolMenu(request) : toolMenuSer
							.getToolMenuForNo(request, "124942"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String type="";
		if(request.getParameter("viewEditionItemTypeParamList_sys2014")!=null&&!request.getParameter("viewEditionItemTypeParamList_sys2014").equals("")){
			type=request.getParameter("viewEditionItemTypeParamList_sys2014");
			modelMap.put("type", type);
		}
		return new ModelAndView("/ess/dimissionApply/viewEditionItemTypeParamList",
				modelMap);
	}

	/**
	 * 去交接类型具体项目的添加页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEditionItemTypeParamInfoView")
	public ModelAndView addEditionItemTypeParamInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("EDITION_NO", request.getParameter("EDITION_NO"));
		modelMap.put("CPNY_ID",admin.getCpnyId());
		modelMap.put("EDITION_ITEM_TYPE", request.getParameter("EDITION_ITEM_TYPE"));
		return new ModelAndView("/ess/dimissionApply/addEditionItemTypeParamInfoView",
				modelMap);
	}

	/**
	 * 添加交接类型的具体项目
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addEditionItemTypeParamInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map addEditionItemTypeParamInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
//			int errorInt = this.dimissionEditionSer
//					.getDimissionEditionListAdd(request);
//			if (errorInt == 0) {
				int result = this.dimissionEditionSer
						.addEditionItemTypeParamInfo(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_success", request));// 添加成功
					map.put("rel", "viewEditionItemTypeData");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));// 保存失败
				}
//			}else{
//				map.put("statusCode", "300");
//				map.put("message", TipMessage.getTipMessage(
//						"ess.edition.title.editionexist", request));//版本号已经存在，请重新添加
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 添加交接类型的具体项目
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateEditionItemParamInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map updateEditionItemParamInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
				int result = this.dimissionEditionSer
						.updateEditionItemTypeParamInfo(request);
				if (result == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_success", request));// 添加成功
					map.put("rel", "viewEditionItemTypeData");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.update_fail", request));// 保存失败
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 添加交接类型的具体项目
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateEditionItemTypeParam", method = RequestMethod.POST)
	@ResponseBody
	public Map updateEditionItemTypeParam(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
				int result = this.dimissionEditionSer
						.updateEditionItemTypeParam(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"确认成功", request));// 添加成功
					map.put("rel", "viewEditionItemTypeData");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"确认失败", request));// 添加成功
			map.put("rel", "viewEditionItemTypeData");
		}
		return map;
	}
	
	/**
	 * 去修改交接项目页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateEditionItemTypeParamInfoView")
	public ModelAndView updateEditionItemTypeParamInfoView(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List editionTypeParamList = dimissionEditionSer.getEditionItemTypeParamList(request);
		if(editionTypeParamList.size()>0){
			modelMap.put("editionItemParamInfo",
					editionTypeParamList.get(0));
		}
		return new ModelAndView(
				"/ess/dimissionApply/updateEditionItemTypeParamInfoView", modelMap);
	}

	/**
	 * 修改交接类型
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateEditionItemTypeParamInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map updateEditionItemTypeParamInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.dimissionEditionSer
					.updateEditionItemTypeParamInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));// 修改成功
				map.put("navTabId", "sys2017");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));// 修改失败
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 删除交接类型
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteEditionItemTypeParamInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteEditionItemTypeParamInfo(
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.dimissionEditionSer
					.deleteEditionItemTypeParamInfo(request);
			if (result == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_success", request));// 删除成功
				map.put("rel", "viewEditionItemTypeData");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.delete_fail", request));// 删除失败
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	/******************************给交接项目添加审批人******************************/
	
	/**
	 * 到交接项目预审批人匹配页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEditionItemMapping")
	public ModelAndView viewEditionItemMapping(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List EditionList = dimissionEditionSer.getDimissionEditionlist(request);
		String edition_no = request.getParameter("EDITION_NO");
		Map map=new HashMap<String,List>();
		String editionFlag = request.getParameter("editionFlag");
		if(editionFlag != null && !"".equals(editionFlag)){
		   map = this.dimissionEditionSer.findEditionItem(request);
		}
		modelMap.put("map", map);
		modelMap.put("edition_no", edition_no);
		modelMap.put("EditionList", EditionList);
		return new ModelAndView("/ess/dimissionApply/viewEditionItemMapping",modelMap);
	}
	
	/**
	 * 获得部门列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewOrgList")
	public ModelAndView viewOrgList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("deptList", orgManageSer.getOrganizationInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, orgManageSer.getOrganizationInfoCnt(request));
		
		return new ModelAndView("/ess/dimissionApply/viewOrgList", modelMap);
	}
	
	/**********************************交接进度查询*********************************/
	
	/**
	 * 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description:离职交接确认
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEditionCheckList")
	public ModelAndView viewEditionCheckList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String cpny_id = StringUtil.checkNull(paramMap.get("COMPANY_ID"),admin.getCpnyId());
		modelMap.put("defaultCpny",cpny_id);
		
		List editionCheckList = this.dimissionEditionSer.getEditionCheckList(request);
		int editionCheckCnt = this.dimissionEditionSer.getEditionCheckCnt(request);
		modelMap.put("editionCheckList", editionCheckList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, editionCheckCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218350")) ;
		
		return new ModelAndView("/ess/dimissionApply/viewEditionCheckList",modelMap);
	}
	
	/**
	 * 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description:离职交接进度查询
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEditionCheckorList")
	public ModelAndView viewEditionCheckorList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String cpny_id = StringUtil.checkNull(paramMap.get("COMPANY_ID"),admin.getCpnyId());
		modelMap.put("defaultCpny",cpny_id);
		
		List editionCheckList = this.dimissionEditionSer.getEditionCheckList(request);
		int editionCheckCnt = this.dimissionEditionSer.getEditionCheckCnt(request);
		modelMap.put("editionCheckList", editionCheckList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, editionCheckCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218350")) ;
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		return new ModelAndView("/ess/dimissionApply/viewEditionCheckorList",modelMap);
	}
	
	/**
	 * 修改申请加确定离职时间
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn 
	* @date 2014-7-14 下午4:46:40 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateEditionItemParamInfo")
	@ResponseBody
	public Map updateEditionItemParamInfo (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();
		int errorInt=this.dimissionEditionSer.updateEditionItemParamInfo(request) ;
		
		if(errorInt==1){
			jo.put("statusCode", "200");
			jo.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//保存成功
		}else{
			jo.put("statusCode", "300");
			jo.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
		return jo;
	}
	
	/**
	 *离职交接项目审批
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author wendi@ait.net.cn 
	* @date 2014-7-14 下午4:46:40 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePersonEditionParamInfo")
	@ResponseBody
	public Map updatePersonEditionParamInfo (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
				int result = this.dimissionEditionSer
						.updatepersonEditionParamInfo(request);
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"确认成功", request));// 添加成功
					map.put("rel", "viewEditionItemTypeData");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"确认失败", request));// 添加成功
			map.put("rel", "viewEditionItemTypeData");
		}
		return map;
	}
}
