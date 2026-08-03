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

import com.ait.pa.service.paView.PaMonthChainSer;
import com.ait.pa.service.workManagement.PaPayScheduleSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/pa/paView")
public class PaMonthChainCtroller {
	Logger logger = Logger.getLogger(PaMonthChainCtroller.class);

	@Autowired
	private PaMonthChainSer PaMonthChain;
	@Autowired
	private PaPayScheduleSer paPayScheduleSer;

	/**
	 * 工资账户信息
	 * 
	 */
	@RequestMapping(value = "/viewPaMonthChain")
	public ModelAndView viewPaMonthChain(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		modelMap.put("paPayScheduleList", paPayScheduleList);

		modelMap.put("PAY_SCHEDULE_NO1", request
				.getParameter("PAY_SCHEDULE_NO1"));

		modelMap.put("PAY_DATE", request.getParameter("PAY_DATE"));
		modelMap.put("PAY_DATE_PRO", request.getParameter("PAY_DATE_PRO"));
		modelMap.put("SALARY_DISTIN_NO", request
				.getParameter("SALARY_DISTIN_NO"));

		List getPaMonthList = this.PaMonthChain.getPaMonthList(request);
		modelMap.put("getPaMonthList", getPaMonthList);

		return new ModelAndView("/pa/paView/viewPaMonthChain", modelMap);

	}

}
