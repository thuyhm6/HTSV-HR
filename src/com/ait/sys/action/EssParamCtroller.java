package com.ait.sys.action;

import java.util.HashMap;
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

import com.ait.ess.service.HumanAffirmApplySer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.EssParamSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC (c) Company: LDCC
 * 
 * @fileName EssParamCtroller.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 pm 04:37:54
 * @version 5.0
 */
@Controller
@RequestMapping(value = "/sys/essParam")
public class EssParamCtroller {

	Logger logger = Logger.getLogger(EssParamCtroller.class);

	@Autowired
	private EssParamSer essParamSer;

	@Autowired
	private CompanySer companySer;
	
	@Autowired
	private HumanAffirmApplySer humanAffirmApplySer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 跳转到查看ESS参数名称 Description:SELECT FROM SY_ESS_CHECK_INFO
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEssParamList")
	public ModelAndView viewEssParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List essParamList = this.essParamSer.getEssParamList(request);
		modelMap.put("essParamList", essParamList);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "4092"));
		return new ModelAndView("/sys/essParam/viewEssParamList", modelMap);
	}

	/**
	 * 跳转更新ESS参数名称页面（指定参数给某个法人） Description:the page of init data to
	 * sy_param_info_param
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateEssParamView")
	public ModelAndView updateEssParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Object essParam = this.essParamSer.getEssParam(request);
		modelMap.put("essParam", essParam);
		modelMap.put("companyList", companySer.getCompanyItemList(request));
		modelMap.put("reqest_PARAM_NO", StringUtil.checkNull(request.getParameter("PARAM_NO")));
		return new ModelAndView("/sys/essParam/updateEssParamView", modelMap);
	}

	/**
	 * 更新Ess参数名称（只更新国际化信息） Description:update globalName
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateEssParamInfo")
	@ResponseBody
	public Map updateEssParamInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.essParamSer.updateEssParamInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0205");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 跳转到查看ESS参数（每个法人只能看到自己的数据） Description:view sys_param_info_param
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEssCheckParamList")
	public ModelAndView viewEssCheckParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List essCheckParamList = this.essParamSer.getEssCheckParamList(request);
		modelMap.put("essParamList", essCheckParamList);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2466"));
		return new ModelAndView("/sys/essParam/viewEssCheckParamList", modelMap);
	}

	/**
	 * 更新Ess参数名称（只更新国际化信息） Description:update globalname
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateEssCheckParamInfo")
	@ResponseBody
	public Map updateEssCheckParamInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.essParamSer.updateEssCheckParamInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));
			map.put("navTabId", "sy0205");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOtConverParamList")
	public ModelAndView viewOtConverParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List otConverParamList = this.essParamSer.getOtConverParamList(request);
		int otConverParamCnt = this.essParamSer.getOtConverParamCnt(request);
		
		modelMap.put("otConverParamList", otConverParamList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, otConverParamCnt);
		modelMap.put("toolbarInfo",request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "122906"));
		
		return new ModelAndView("/sys/essParam/viewOtConverParamList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addOtConverParamView")
	public ModelAndView addLoginUserView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List overTimeApplyTypeList = this.humanAffirmApplySer.getOverTimeApplyTypeList(request);
		List converTypeList = this.humanAffirmApplySer.getConverTypeList(request);
		
		modelMap.put("overTimeApplyTypeList", overTimeApplyTypeList);
		modelMap.put("converTypeList", converTypeList);
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/sys/essParam/addOtConverParamView",modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addOtConverParamInfo")
	@ResponseBody
	public Map addOtConverParamInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = essParamSer.addOtConverParamInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
			map.put("navTabId", "sy0208");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateOtConverParamView")
	public ModelAndView updateOtConverParamView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Object otConverParam = this.essParamSer.getOtConverParam(request);
		List converTypeList = this.humanAffirmApplySer.getConverTypeList(request);
		
		modelMap.put("otConverParam", otConverParam);
		modelMap.put("converTypeList", converTypeList);
		
		return new ModelAndView("/sys/essParam/updateOtConverParamView",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateOtConverParamInfo")
	@ResponseBody
	public Map updateOtConverParamInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = essParamSer.updateOtConverParamInfo(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));
			map.put("navTabId", "sy0208");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteOtConverParam")
	@ResponseBody
	public Map deleteOtConverParam(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = essParamSer.deleteOtConverParam(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));
			map.put("navTabId", "sy0208");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));
		}
		return map;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewVacationStandardManage")
	public ModelAndView viewVacationStandardManageList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List vacationStandardManageList = this.essParamSer.getVacationStandardManageList(request);
		int vacationStandardManageCnt = this.essParamSer.getVacationStandardManageCnt(request);
		
		modelMap.put("vacationStandardManageList", vacationStandardManageList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, vacationStandardManageCnt);
		modelMap.put("toolbarInfo",request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "124828"));
		
		return new ModelAndView("/sys/essParam/viewVacationStandardManage", modelMap);
	}
	
	/**
	 * 休假基准添加页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-2 下午12:00:55 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addVacationStandardManage")
	public ModelAndView addVacationStandardManage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List workAreaList = this.essParamSer.getWorkAreaList(request);
		
		
		modelMap.put("workAreaList", workAreaList);
		
		
		return new ModelAndView("/sys/essParam/addVacationStandardManage", modelMap);
	}
	
	/**
	 * 添加休假基准
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-3 上午11:11:23 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveVacationStandardManage")
	@ResponseBody
	public Map saveVacationStandardManage(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = essParamSer.saveVacationStandardManage(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
			map.put("navTabId", "ess0229");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail", request));
		}
		return map;
	}
	
	/**
	 *  删除休假基准
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-3 下午1:56:18 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteVacationStandardManage")
	@ResponseBody
	public Map deleteVacationStandardManage(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = essParamSer.deleteVacationStandardManage(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success", request));
			map.put("navTabId", "ess0229");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail", request));
		}
		return map;
	}
	
	/**
	 * 休假基准修改页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-3 下午2:30:20 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateVacationStandardManag")
	public ModelAndView updateVacationStandardManag(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map ManageList = this.essParamSer.getManageList(request);
		if(ManageList.size()>0){
			ManageList.put("STANDARD_EXPLAIN", ManageList.get("STANDARD_EXPLAIN").toString().replace("<br>","\n"));
		}
		modelMap.put("ManageList", ManageList);
		//modelMap.put("companyList", companySer.getCompanyItemList(request));
		return new ModelAndView("/sys/essParam/updateVacationStandardManag", modelMap);
	}
	
	/**
	 * 修改休假基准
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-3 下午3:25:54 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editVacationStandardManage")
	@ResponseBody
	public Map editVacationStandardManage(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = essParamSer.updateVacationStandardManage(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));
			map.put("navTabId", "ess0229");
			map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));
		}
		return map;
	}
}
