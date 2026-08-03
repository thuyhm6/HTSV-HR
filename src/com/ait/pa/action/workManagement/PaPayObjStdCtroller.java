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

import com.ait.pa.service.workManagement.PaPayObjStdSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/pa/workManagement")
public class PaPayObjStdCtroller {
	Logger logger = Logger.getLogger(PaPayObjStdCtroller.class);

	@Autowired
	private PaPayObjStdSer paPayObjStdSer;

	/**
	 * 对象基准管理
	 * 
	 */
	@RequestMapping(value = "/viewPayObjStd")
	public ModelAndView viewPayObjStdList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paPayObjStdList = this.paPayObjStdSer
				.getPayObjStdList(request);
		int paPayObjStdCnt = this.paPayObjStdSer.getPayObjStdCnt(request);

		modelMap.put("paPayObjStdList", paPayObjStdList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paPayObjStdCnt);
		modelMap.put("SALARY_DISTIN_NO", request.getParameter("seach_SALARY_DISTIN_NO"));
		
		return new ModelAndView("/pa/workManagement/viewPayObjStd", modelMap);

	}

	/**
	 * 对象基准管理 添加页面
	 * 
	 */
	@RequestMapping(value = "/addPayObjStd",method = RequestMethod.GET)
	public ModelAndView addPayObjStd(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		return new ModelAndView("/pa/workManagement/addPayObjStd", modelMap);

	}

	/**
	 * 对象基准管理 保存信息
	 * 
	 */
	@RequestMapping(value = "/addPayObjStdInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map addPaPayObjStdInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.paPayObjStdSer.addPayObjStdInfo(request);
	
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
//			map.put("message", "保存成功");
			map.put("formId", "searchViewPayObjStdForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
//			map.put("message", "保存失败");
			map.put("formId", "searchViewPayObjStdForm");
		}
		return map;

	}
	
	/**
	 * 进入修改页面(update PaPayObjStd view)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePayObjStdView",method = RequestMethod.GET)
	public ModelAndView updatePayObjStdView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		modelMap.put("paPayObjStdInfo", this.paPayObjStdSer.getPayObjStdInfo(request)) ;
		
		return new ModelAndView("/pa/workManagement/updatePayObjStdView",modelMap);
	}
	
	/**
	 * 修改区间信息(update PaPayObjStd Info)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePayObjStdInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updatePayObjStdInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.paPayObjStdSer.updatePayObjStdInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("formId", "searchViewPayObjStdForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			map.put("formId", "searchViewPayObjStdForm");
		}
		return map;
	}
	
	/**
	 * 删除工资支付计划信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doDeletePayObjStdInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map doDeletePayObjStdInfo(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.paPayObjStdSer.doDeletePayObjStdInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("formId", "searchViewPayObjStdForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			map.put("formId", "searchViewPayObjStdForm");
		}
		return map;
	}
}
