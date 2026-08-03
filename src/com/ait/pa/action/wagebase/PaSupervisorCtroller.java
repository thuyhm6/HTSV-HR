package com.ait.pa.action.wagebase;

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

import com.ait.ar.service.CycleSer;
import com.ait.pa.service.wagebase.PaSupervisorSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: PaSupervisorCtroller.java
 * @Description:
 * @Create date: 2012-1-14 下午01:43:31
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/wagebase")
public class PaSupervisorCtroller {
	Logger logger = Logger.getLogger(PaSupervisorCtroller.class);

	@Autowired
	private PaSupervisorSer paSupervisorSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private CycleSer cycleSer;

	/**
	 * 查看考勤员(view PaSupervisor List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaSupervisor")
	public ModelAndView viewPaSupervisorList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
        AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List<LinkedHashMap<String, Object>> paSupervisorList = this.paSupervisorSer
				.getPaSupervisorList(request);
		int paSupervisorCnt = this.paSupervisorSer.getPaSupervisorCnt(request);
		List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("paSupervisorList", paSupervisorList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paSupervisorCnt);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "17813"));

		return new ModelAndView("/pa/wagebase/viewPaSupervisor", modelMap);
	}

	/**
	 * 添加跳转(add PaSupervisor View)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addPaSupervisorView")
	public ModelAndView addPaSupervisorView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List empTypeCodeList = this.cycleSer.getEmpTypeCodeListSUPERVISOR(request) ;
		List jobTypeGroupList=this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("empTypeCodeList", empTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);

		return new ModelAndView("/pa/wagebase/addPaSupervisorView", modelMap);
	}

	/**
	 * 部门树(get Dept Tree)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDeptTree")
	@ResponseBody
	public List getDeptTree(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List getDeptTree = this.paSupervisorSer.getDeptTree(request);
		return getDeptTree;
	}

	/**
	 * 保存方法
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPaSupervisorInfo")
	@ResponseBody
	public Map<String, Object> addPaSupervisorInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = paSupervisorSer.addPaSupervisorInfo(request);

		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));
			map.put("navTabId", "pa0820");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));
		}
		return map;
	}

	/**
	 * 获得人员列表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewWageBearList")
	public ModelAndView viewKeeperList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
        AdminBean admin = SessionUtil.getLoginUserFromSession(request);
        List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("personList", paSupervisorSer.getPersonListView(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paSupervisorSer
				.getPersonListCnt(request));

		return new ModelAndView("/pa/wagebase/viewKeeperList", modelMap);
	}

	/**
	 * 修改跳转(update PaSupervisor View)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaSupervisorView")
	public ModelAndView updatePaSupervisorView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
         AdminBean admin =SessionUtil.getLoginUserFromSession(request);
		//List empTypeCodeList = this.cycleSer.getEmpTypeCodeListSUPERVISOR(request) ;
		List statisticList = this.cycleSer.getPaSupervisorJobTypeGroupCodeList(request); 
		List jobTypeGroupList=this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		//modelMap.put("empTypeCodeList", empTypeCodeList) ;
		modelMap.put("statisticList", statisticList) ;
		modelMap.put("jobTypeGroupList", jobTypeGroupList) ;
		
		modelMap.put("paSupervisorInfo", paSupervisorSer
				.getPaSupervisor(request));
		modelMap.put("PA_SUPERVISOR_ID", request.getParameter("PERSON_ID"));

		return new ModelAndView("/pa/wagebase/updatePaSupervisorView", modelMap);
	}

	/**
	 * 修改保存(update PaSupervisor Info)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaSupervisorInfo")
	@ResponseBody
	public Map<String, Object> updatePaSupervisorInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = paSupervisorSer.updatePaSupervisorInfo(request);

		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "pa0820");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 删除考勤员信息(delete PaSupervisor Info)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePaSupervisorInfo")
	@ResponseBody
	public Map<String, Object> deletePaSupervisorInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.paSupervisorSer.deletePaSupervisorInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));
			map.put("navTabId", "pa0820");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));
		}
		return map;
	}
}
