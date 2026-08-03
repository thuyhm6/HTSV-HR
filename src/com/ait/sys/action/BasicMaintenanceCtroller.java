package com.ait.sys.action;

import java.util.Collection;
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

import com.ait.hrm.dao.HrmDao;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.bean.CodeBean;
import com.ait.sys.service.BasicMaintenanceSer;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.SyLanguageSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CodeUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * 
 * Copyright: LDCC (c) Company: LDCC
 * 
 * @fileName BasicMaintenanceCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 pm 04:37:47
 * @version 5.0
 * 
 */
@Controller
@RequestMapping(value = "/sys/basicMaintenance")
public class BasicMaintenanceCtroller {
	Logger logger = Logger.getLogger(BasicMaintenanceCtroller.class);

	@Autowired
	private CodeUtil codeUtil;

	@Autowired
	private BasicMaintenanceSer basicMaintenanceSer;

	@Autowired
	private CompanySer companySer;

	@Autowired
	private SyLanguageSer syLanguageSer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private HrmDao hrmDao;
	
	/**
	 * 代码管理页面 Description:the page to manage the data of SY_CODE
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCodeManage")
	public ModelAndView viewCodeManageList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List codeList = this.basicMaintenanceSer
				.getCodeListByParentCode(request);
		modelMap.put("codeLists", codeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.basicMaintenanceSer
				.getCodeListByParentCodeCnt(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2469"));
		return new ModelAndView("/sys/basicMaintenance/viewCodeManage",
				modelMap);
	}

	/**
	 * 根据父级parentNo(Parent_code_no)获取所有子code Description:get codes according to
	 * PARENT_CODE_NO
	 * 
	 * @param parentNo
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getChildCodeList(String parentNo, List list) {
		CodeBean parentCode = CodeUtil.getCodeMap().get(parentNo);
		Collection<CodeBean> childCodeBeanList = parentCode.getChildCodeMap()
				.values();
		list.addAll(childCodeBeanList);
		for (CodeBean codeBean : childCodeBeanList) {
			getChildCodeList(codeBean.getCodeNo().toString(), list);
		}
		return list;
	}

	/**
	 * 代码管理页面获取左侧的代码树 Description:get parent codes
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getParentTreeData")
	@ResponseBody
	public List getParentTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		List codeInfoTreeList = this.basicMaintenanceSer
				.getParentCodeList(request);
		return codeInfoTreeList;
	}

	/**
	 * 根据父级parentNo(Parent_code_no)获取所有子code Description:get codes according to
	 * PARENT_CODE_NO
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCodeListByParentCode")
	public ModelAndView viewCodeListByParentCodeList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List codeList = this.basicMaintenanceSer
				.getCodeListByParentCode(request);
		modelMap.put("codeLists", codeList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.basicMaintenanceSer
				.getCodeListByParentCodeCnt(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2469"));
		return new ModelAndView(modelMap);
	}


	/**
	 * 对code 进行添加，更新和删除操作 Description:
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCodeRelation")
	@ResponseBody
	public Map getCodeRelation(HttpServletRequest request)
			throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		List codeList = null;
		Map map = new LinkedHashMap();
		if("BUSINESS".equals(StringUtil.checkNull(paramMap.get("type")))){
			paramMap.put("DEPTNO", paramMap.get("PARENT_CODE_NO"));
			codeList = hrmDao.getDeptTree("hrm.getBusinessCenter",paramMap);
		}else{
			codeList = this.basicMaintenanceSer.getCodeListByParentCodeWithParam(request);
		}
		map.put("result", codeList);
		return map;
	}
	
	/**
	 * 对code 进行添加，更新和删除操作 Description:
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveAndUpdateAndDeleteCodeInfo")
	@ResponseBody
	public String saveAndUpdateAndDeleteCodeInfo(HttpServletRequest request)
			throws Exception {
		this.basicMaintenanceSer.addCodeInfo(request);
		this.basicMaintenanceSer.updateCodeInfo(request);
		this.basicMaintenanceSer.deleteCodeInfo(request);
		return "Y";
	}

	/**
	 * 添加code Description: add a Code
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAddCodeView")
	public ModelAndView getAddCodeView(HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		modelMap.put("PARENT_CODE_NO", request
				.getParameter("seach_PARENT_CODE_NO"));
		return new ModelAndView(modelMap);
	}

	/**
	 * 
	 * Description:
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delCodeByCodeNo")
	@ResponseBody
	public Map delCodeByCodeNo(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.basicMaintenanceSer.deleteCodeInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("rel", "jbsxBox_sy0420");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}

	/**
	 * 保存code Description:save a code
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveCode")
	@ResponseBody
	public Map saveCode(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.basicMaintenanceSer.addCodeInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("rel", "jbsxBox_sy0420");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	/**
	 * 修改code页面 Description:the page of update a code
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getEditCodeView")
	public ModelAndView getEditCodeView(HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		modelMap.put("SY_CODE", this.basicMaintenanceSer
				.getCodeByCodeNo(request));
		return new ModelAndView(modelMap);
	}

	/**
	 * 修改code Description:update a code
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editCode")
	@ResponseBody
	public Map editCode(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.basicMaintenanceSer.updateCodeInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			// map.put("navTabId", "sy0420");
			map.put("rel", "jbsxBox_sy0420");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 查看代码参数页面 Description:the page to show code params
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	// 系统代码参数
	@RequestMapping(value = "/viewCodePamers")
	public ModelAndView viewCodePamersList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("cParmersLists", this.basicMaintenanceSer
				.getCodePamersList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.basicMaintenanceSer
				.getCodePamersListCnt(request));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2569"));
		return new ModelAndView(modelMap);
	}

	/**
	 * 添加代码参数 Description: add a code param
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addCodeParamView")
	public ModelAndView addCodeParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("companyList", companySer.getCompanyItemList(request));
		List info = this.basicMaintenanceSer.getCodeTreeForAll(request);
		modelMap.put("codeLists", info);
		return new ModelAndView(modelMap);
	}

	/**
	 * 所有的code数据 Description:get all Code
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCodeTreeForAll")
	@ResponseBody
	public List getCodeTreeForAll(HttpServletRequest request) throws Exception {
		List info = this.basicMaintenanceSer.getCodeTreeForAll(request);
		return info;
	}

	/**
	 * 根据父级取子code Description: get codes by PARENT_CODE_NO
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCodeTreeByParentCode")
	public ModelAndView getCodeTreeByParentCode(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List codeList = this.basicMaintenanceSer
				.getCodeTreeByParentCode(request);
		modelMap
				.put("companyList", this.companySer.getCompanyItemList(request));
		modelMap.put("languageList", this.syLanguageSer
				.getSyLanguageListByActivity());
		modelMap.put("codeLists", codeList);
		return new ModelAndView(modelMap);
	}

	/**
	 * 保存代码参数 Description:save code Param
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveCodeParam")
	@ResponseBody
	public Map saveCodeParam(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.basicMaintenanceSer.saveCodeParam(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "sy0430");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	/**
	 * 代码参数设置跳转编辑页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editCodeParamView")
	public ModelAndView editCodeParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("companyList", companySer.getCompanyItemList(request));
		Map temp = new LinkedHashMap();
		temp.put("DEPTH_START", 10);
		List info = this.basicMaintenanceSer.getCodeTreeForAll(request, temp);
		modelMap.put("codeLists", info);
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
		return new ModelAndView(modelMap);
	}

	/**
	 * 获取前2级得code Description:get code where depth<2
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAllParentCodeList")
	@ResponseBody
	public List getAllParentCodeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map temp = new LinkedHashMap();
		temp.put("DEPTH_START", 3);
		List codeInfoTreeList = this.basicMaintenanceSer.getCodeTreeForAll(
				request, temp);
		return codeInfoTreeList;
	}

	/**
	 * 修改代码参数 Description:update the code param
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editCodeParam")
	@ResponseBody
	public Map editCodeParam(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.basicMaintenanceSer.editCodeParam(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0430");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 代码参数设置跳转编辑页面 Description:page of edit code param
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getEditCodeParamView")
	public ModelAndView getEditCodeParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
		modelMap.put("CODE_NO", request.getParameter("CODE_NO"));
		modelMap.put("companyList", companySer.getCompanyItemList(request));
		return new ModelAndView(modelMap);
	}

	/**
	 * get Code by PARENT_CODE_NO Description:
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getChildCodeListByParentNo")
	@ResponseBody
	public List getChildCodeListByParentNo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map temp = ObjectBindUtil.getRequestParamDataForCode(request);
		List codeInfoTreeList = this.basicMaintenanceSer.getcodeTreeForEdit(
				request, temp);
		return codeInfoTreeList;
	}

	/**
	 * 根据公司ID取得CODE列表(get ParamCodeList By CpnyID)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getParamCodeListByCpnyID")
	@ResponseBody
	public List getParamCodeListByCpnyID(HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		List codeList = basicMaintenanceSer.getParamCodeListByCpnyID(request,
				modelMap);
		return codeList;
	}

	/**
	 * 将所有的code加载到内存中 Description:add all code to a map
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/refreshData")
	@ResponseBody
	public void refreshData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		codeUtil.refreshCodes();
	}
}
