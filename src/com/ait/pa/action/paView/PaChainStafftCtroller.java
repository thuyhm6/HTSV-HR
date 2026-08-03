package com.ait.pa.action.paView;

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

import com.ait.pa.service.paView.PaChainStafftSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/pa/paView")
public class PaChainStafftCtroller {
	Logger logger = Logger.getLogger(PaChainStafftCtroller.class);

	@Autowired
	private PaChainStafftSer PaChainStafftSer;

	/**
	 * 工资账户信息
	 * 
	 */
	@RequestMapping(value = "/viewPaChainStaff")
	public ModelAndView viewPaChainStaffList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paEmpAccountList = this.PaChainStafftSer
				.getPaEmpAccountList(request);
		modelMap.put("paEmpAccountList", paEmpAccountList);
		modelMap.put("KEY", request.getParameter("seach_KEY"));
		modelMap.put("START_DATE_STARTED", request.getParameter("seach_START_DATE_STARTED"));
		modelMap.put("END_DATE_STARTED", request.getParameter("seach_END_DATE_STARTED"));
		modelMap.put("ACCOUNT_TYPE", request.getParameter("seach_ACCOUNT_TYPE"));
		modelMap.put("EMP_TYPE_CODE", request.getParameter("seach_EMP_TYPE_CODE"));
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		modelMap.put("EmpOffice", request.getParameter("seach_EmpOffice"));
		
		return new ModelAndView("/pa/paView/viewPaChainStaff", modelMap);

	}

}
