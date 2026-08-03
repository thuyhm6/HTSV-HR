package com.ait.pa.action.workManagement;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.pa.service.workManagement.PaPayScheduleSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/pa/workManagement")
public class PaPayScheduleCtroller {
	Logger logger = Logger.getLogger(PaPayScheduleCtroller.class);

	@Autowired
	private PaPayScheduleSer paPayScheduleSer;

	/**
	 * 工资计划管理
	 * 
	 */
	@RequestMapping(value = "/viewPaPaySchedule")
	public ModelAndView viewPaPayScheduleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleList(request);
		int paPayScheduleCnt = this.paPayScheduleSer.getPayScheduleCnt(request);

		modelMap.put("paPayScheduleList", paPayScheduleList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paPayScheduleCnt);
		modelMap.put("START_PAY_DATE", request.getParameter("seach_START_PAY_DATE"));
		modelMap.put("END_PAY_DATE", request.getParameter("seach_END_PAY_DATE"));
		modelMap.put("SALARY_DISTIN_NO", request.getParameter("seach_SALARY_DISTIN_NO"));
		
		return new ModelAndView("/pa/workManagement/viewPaPaySchedule", modelMap);

	}

	/**
	 * 工资计划管理 添加页面
	 * 
	 */
	@RequestMapping(value = "/addPaPaySchedule",method = RequestMethod.GET)
	public ModelAndView addPaPaySchedule(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		return new ModelAndView("/pa/workManagement/addPaPaySchedule", modelMap);

	}

	/**
	 * 工资计划管理 保存信息
	 * 
	 */
	@RequestMapping(value = "/addPaPayScheduleInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addPaPayScheduleInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.paPayScheduleSer.addPaPayScheduleInfo(request);
	
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
//			map.put("message", "保存成功");
			map.put("formId", "searchViewPaPayScheduleForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
//			map.put("message", "保存失败");
			map.put("formId", "searchViewPaPayScheduleForm");
		}
		return map;

	}
	
	/**
	 * 进入修改页面(update PaPaySchedule view)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaPayScheduleView",method = RequestMethod.GET)
	public ModelAndView updatePaPayScheduleView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("paPayScheduleInfo", this.paPayScheduleSer.getPaPayScheduleInfo(request)) ;
		
		return new ModelAndView("/pa/workManagement/updatePaPayScheduleView",modelMap);
	}
	
	/**
	 * 修改区间信息(update PaPaySchedule Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaPayScheduleInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updatePaPayScheduleInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.paPayScheduleSer.updatePaPayScheduleInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("formId", "searchViewPaPayScheduleForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			map.put("formId", "searchViewPaPayScheduleForm");
		}
		return map;
	}
	
	/**
	 * 确认或解除工资支付计划信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmOrRelievePaySchedule",method = RequestMethod.POST)
	@ResponseBody
	public Map confirmOrRelievePaySchedule(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.paPayScheduleSer.confirmOrRelievePaySchedule(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("formId", "searchViewPaPayScheduleForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			map.put("formId", "searchViewPaPayScheduleForm");
		}
		return map;
	}
	
	/**
	 * 删除工资支付计划信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doDeletePayScheduleInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map doDeletePayScheduleInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.paPayScheduleSer.doDeletePayScheduleInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("formId", "searchViewPaPayScheduleForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			map.put("formId", "searchViewPaPayScheduleForm");
		}
		return map;
	}
}
