package com.ait.pa.action.workManagement;

import java.util.Calendar;
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

import com.ait.pa.service.workManagement.PaWorkerSalaryTableSer;
import com.ait.web.i18n.TipMessage;

import common.Logger;

@Controller
@RequestMapping(value = "pa/workManagement")
public class PaWorkerSalaryTableCtroller {
	Logger logger = Logger.getLogger(PaWorkerSalaryTableCtroller.class);
	@Autowired
	private PaWorkerSalaryTableSer paWorkerSalaryTableSer;
	
//	查询
	@RequestMapping(value = "viewPaWorkerSalaryTable")
	public ModelAndView paWorkerSalaryTableList (HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paWorkerSalaryTableList = this.paWorkerSalaryTableSer.paWorkerSalaryTableList(request);
		modelMap.put("paWorkerSalaryTableList", paWorkerSalaryTableList);
		Calendar c = Calendar.getInstance();
		modelMap.put("paYear", request.getParameter("seach_paYear") == null ? String.valueOf(c.get(Calendar.YEAR)) : request.getParameter("seach_paYear"));
		modelMap.put("paMonth", request.getParameter("seach_paMonth") == null ? String.valueOf(c.get(Calendar.MONTH)+1) : request.getParameter("seach_paMonth"));
		
		return new ModelAndView("pa/workManagement/viewPaWorkerSalaryTable", modelMap);
		
	}
	
//	Insert
	
	@RequestMapping(value = "/addPaWorkerSalaryTable", method = RequestMethod.POST)
	@ResponseBody
	public Map addPaWorkerSalaryTable (HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.paWorkerSalaryTableSer.addPaWorkerSalaryTable(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
			map.put("formId", "viewPaWorkerSalaryTable");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			map.put("formId", "viewPaWorkerSalaryTable");
		}
		
		return map;
		
	}
	
//	Update
	@RequestMapping(value = "/updatePaWorkerSalaryTable", method = RequestMethod.POST)
	@ResponseBody
	public Map updatePaWorkerSalaryTable (HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.paWorkerSalaryTableSer.updatePaWorkerSalaryTable(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success", request));
			map.put("formId", "viewPaWorkerSalaryTable");
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
			map.put("formId", "viewPaWorkerSalaryTable");
		}
		
		return map;
		
	}

}
