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

import com.ait.pa.service.salary.PaComputeItemSer;
import com.ait.pa.service.salary.PaInputItemParamSer;
import com.ait.pa.service.wagebase.PaBasicItemSer;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.salaryMappingSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/sys/salarymapping")
public class salaryMappingCtroller {

	Logger logger = Logger.getLogger(salaryMappingCtroller.class);

	@Autowired
	private salaryMappingSer salaryMappingSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private PaBasicItemSer paBasicItemSer;
	@Autowired
	private PaInputItemParamSer paInputItemParamSer;
	@Autowired
	private PaComputeItemSer paComputeItemSer;
	@Autowired
	private CompanySer companySer;

	/**
	 * 跳转到基础项目库首页(view Pa Basic Item)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSalaryMappingList")
	public ModelAndView viewSalaryMappingList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List salaryMappingList = this.salaryMappingSer
				.getSalaryMappingList(request);
		List companyList = companySer.getAllCompanyItemList(request);
		int salaryMappingCnt = this.salaryMappingSer
				.getSalaryMappingCnt(request);
		String CPNY = request.getParameter("seach_CPNY");
		modelMap.put("seach_CPNY", CPNY);
		modelMap.put("CPNY", CPNY);
		modelMap.put("salaryMappingList", salaryMappingList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, salaryMappingCnt);
		modelMap.put("companyList", companyList);
		return new ModelAndView("/sys/salarymapping/viewSalaryMappingList",
				modelMap);
	}

	/**
	 * 去修改页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/newSalaryMappingView")
	public ModelAndView newSalaryMappingView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Object salaryMappingInfo = this.salaryMappingSer
				.getSalaryMappingInfo(request);
		modelMap.put("salaryMappingInfo", salaryMappingInfo);
		return new ModelAndView("/sys/salarymapping/newSalaryMappingView",
				modelMap);
	}

	/**
	 * 指定法人(add Pa Basic Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateSalaryMappingInfo")
	@ResponseBody
	public Map updateSalaryMappingInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String itemType = request.getParameter("ITEM_TYPE");
		String[] cpny_ids = request.getParameterValues("CPNY_ID");
		String item_no = request.getParameter("ITEM_NO");
		String param_item_no = request.getParameter("ITEM_NO");
		if (!itemType.equals("6")) {
			this.paInputItemParamSer.updatePaInputItemParamInfoAll(request);
		} else if (itemType.equals("6")) {
			this.paComputeItemSer.updatePaComputeItemParamInfoAll(request);
		}
		if (cpny_ids != null) {
			for (int i = 0; i < cpny_ids.length; i++) {
				String cpny_id = cpny_ids[i];
				if (!itemType.equals("6")) {
					int resultNum = this.paInputItemParamSer
								.addPaInputItemParamInfo(request, cpny_id,
										param_item_no);
						if (resultNum == 1) {
							map.put("statusCode", "200");
							map.put("message", TipMessage.getTipMessage(
									"alert.message.update_success", request));
							map.put("formId", "viewSalaryMappingListForm");
						} else {
							map.put("statusCode", "300");
							map.put("message", TipMessage.getTipMessage(
									"alert.message.update_fail", request));
							map.put("formId", "viewSalaryMappingListForm");
						}
				} else if (itemType.equals("6")) {
					int returnNum = this.paComputeItemSer
								.addPaComputeItemParamInfo(request, cpny_id,
										item_no);
						if (returnNum == 1) {
							map.put("statusCode", "200");
							map.put("message", TipMessage.getTipMessage(
									"alert.message.add_success", request));
							map.put("formId", "viewSalaryMappingListForm");
						} else {
							map.put("statusCode", "300");
							map.put("message", TipMessage.getTipMessage(
									"alert.message.add_fail", request));
							map.put("formId", "viewSalaryMappingListForm");
						}
				}

			}
		} else {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.sys.affirm.pleaseChooseOneContent", request));
			map.put("formId", "viewSalaryMappingListForm");
		}
		return map;
	}

}
