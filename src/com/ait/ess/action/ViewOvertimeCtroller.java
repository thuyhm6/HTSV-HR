package com.ait.ess.action;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.ait.ess.service.viewOverInfoSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;


@Controller
@RequestMapping(value="/ess/viewOvertime")
public class ViewOvertimeCtroller {

	
	
	
	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");

	Logger logger = Logger.getLogger(ViewApplyCtroller.class);

	@Autowired
	private EmpInfoSer empInfoSer;

	@Autowired
	private viewOverInfoSer viewOverinfoSer;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewOvertimeInfoList")
	public ModelAndView viewOvertimeInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("personOtApplyList", viewOverinfoSer.viewOvertimeInfoList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, viewOverinfoSer.viewOvertimeInfoListCnt(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		return new ModelAndView("/ess/viewOvertime/viewOvertimeInfoList");
		
		
	}
	
	
	//页面跳转到加班申请页面
	@RequestMapping(value="/addOvertimeInfo")
	public ModelAndView goAddOvertime(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List otDeductTimeList = this.viewOverinfoSer.getOtDeductTimeList(request) ;
		
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("otDeductTimeList", otDeductTimeList);
		modelMap.put("personInfo", empInfoSer.getPersonalInfo(request));
		List selectcode = viewOverinfoSer.getSelectCode();
		modelMap.put("selectcode", selectcode);
		
		LinkedHashMap map = new LinkedHashMap();
		map.put("interLanguage", admin.getLanguage());
		map.put("PERSON_ID", admin.getPersonId());
		map.put("APPLY_TYPE_NO", 31);
		List affirmorList = this.viewOverinfoSer.getAffirmorList(map);
		modelMap.put("affirmorList", affirmorList);
		
		return new ModelAndView("/ess/viewOvertime/addOvertimeInfo",modelMap);
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addOvertimeApply")
	@ResponseBody
	public Map addOvertimeApply(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = viewOverinfoSer.addOvertimeApply(request);
			if (result == 1) {
				map.put("navTabId", "ess0201");
				map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_success",request));//"保存加班申请成功!"
				map.put("statusCode", "200");
			}

		} catch (Exception e) {
			map.put("message", TipMessage.getTipMessage("alert.message.ess.infoApply.addOvertimeApply_fail",request));//"保存加班申请出错,请重新申请!"
			map.put("statusCode", "300");
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDateByPersonIdAndCpny")
	@ResponseBody
	public Map getDateByPersonIdAndCpny(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		List list = viewOverinfoSer.getDateByPersonIdAndCpny(request);
		Map<String, Object> map1 = (Map)list.get(0);	
		map.put("result", map1);
		return map1;
	}
	/**
	 * 查看决裁者信息(view affirmor information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmorByPersonIdAndAppCode")
	public ModelAndView viewAffirmorByPersonIdAndAppCode(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		
		return new ModelAndView(
				"/ess/viewOvertime/viewAffirmorByPersonIdAndAppCode", modelMap);
	}
}
