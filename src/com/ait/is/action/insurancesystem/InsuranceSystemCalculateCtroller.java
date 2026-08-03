package com.ait.is.action.insurancesystem;

import java.util.ArrayList;
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

import com.ait.is.service.InsuranceSystemCalculateSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright: AIT Company: AIT
 * 
 * @fileName: InsuranceSystemCalculateCtroller.java
 * @Description:
 * @Create date: 2014-1-17 下午02:55:16
 * @Create by: heran(heran@ait.net.cn)
 * @version 5.5
 */
@Controller
@RequestMapping(value = "/is/insurancesystem")
public class InsuranceSystemCalculateCtroller {
	Logger logger = Logger.getLogger(InsuranceSystemCalculateCtroller.class);

	@Autowired
	private InsuranceSystemCalculateSer insuranceSystemCalculateSer;
	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 跳转到基准管理页面（get Benchmark management）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ViewBenchmarkManagementForSearch")
	public ModelAndView ViewBenchmarkManagementForSearch(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String method = request.getParameter("method");// 方法区分
		String logtype = request.getParameter("logtype") != null ? request
				.getParameter("logtype") : "";
		String type = "";
		if (logtype.equals("UPDATE")) {
			type = "update";
		}
		List standardNotSerious = new ArrayList<Object>();
		List standardSerious = new ArrayList<Object>();
		String searchDate = request.getParameter("SearchDate");// 版本日期
		Map map = ObjectBindUtil.getRequestParamData(request, "seach_");
		map.put("versionDate", searchDate);
		if ("now".equals(searchDate)) {
			// standardNotSerious =
			// paBenServices.getModifyStandardNotSeriousModifyBz(map);

			standardNotSerious = this.insuranceSystemCalculateSer
					.getInsuranceSystemInfoListForSearch(request);
			standardSerious = this.insuranceSystemCalculateSer
					.getModifyStandardSeriousBz(request);
			request.setAttribute("allow", true);
		} else {

			standardNotSerious = this.insuranceSystemCalculateSer.getPaBenStandardNotSeriousBz(request);
			standardSerious = this.insuranceSystemCalculateSer.getPaBenStandardSeriousBz(request);
			/*standardNotSerious = this.insuranceSystemCalculateSer
					.getInsuranceSystemInfoListForSearch(request);
			standardSerious = this.insuranceSystemCalculateSer
					.getModifyStandardSeriousBz(request);*/
		}
		
		if ("create".equals(method)) {// 生成版本
			if (this.insuranceSystemCalculateSer.ifUpdatedVersionBz(request) == 30) {
				// 对象最大年月
				String yearMonth = this.insuranceSystemCalculateSer
						.getMaxManageCreateDateBz(request);
				map.put("yearMonth", yearMonth);
				int calFlag = this.insuranceSystemCalculateSer
						.selectPaBenFalgByFalgBz(map);
				if (calFlag != 0) {// 已经核算
					request.setAttribute("message", "当前对象已经核算，暂时不可生成版本");
				} else {
					int result = this.insuranceSystemCalculateSer
							.createBenchmarkStandardVersionBz(map);
					if (result == 1) {
						this.insuranceSystemCalculateSer
								.freshPaBenManageBz(map);// 刷新上下限
						request.setAttribute("message", "版本生成成功！");
					}
				}

			}
		}

		List versionList = this.insuranceSystemCalculateSer
				.getVersionDateListBz(request);
		modelMap.put("type", type);
		modelMap.put("standardNotSerious", standardNotSerious);
		modelMap.put("searchDate", searchDate);
		modelMap.put("standardSerious", standardSerious);
		modelMap.put("versionList", versionList);
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "124901"));
		modelMap.put("defaultCpny", admin.getCpnyId());
		return new ModelAndView(
				"/is/insurancesystem/ViewBenchmarkManagementForSearch",
				modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ViewBenchmarkManagementForSearchUpdate")
	@ResponseBody
	public Map ViewBenchmarkManagementForSearchUpdate(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		String logtype = request.getParameter("logtype");
		if (logtype.equals("UPDATE")) {
			map.put("type", "UPDATE");
		} else if (logtype.equals("insert")) {
			map.put("type", "INSERT");
		}
		return map;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateBenchmarkManagement")
	@ResponseBody
	public Map updateBenchmarkManagement(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

		int result = this.insuranceSystemCalculateSer.updateBenchmarkManagement(request);
		map.put("statusCode", "200");
		map.put("message", TipMessage.getTipMessage("display.emp.statistics.mes191",request));//保存失败
		return map;

	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchBenchmarkManagement")
	@ResponseBody
	public Map searchBenchmarkManagement(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

		//int result = this.insuranceSystemCalculateSer.updateBenchmarkManagement(request);
		//map.put("statusCode", "200");
		//map.put("message", TipMessage.getTipMessage("display.emp.statistics.mes191",request));//保存失败
		return map;

	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createVersion")
	@ResponseBody
	public Map createVersion(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		int count=this.insuranceSystemCalculateSer.ifUpdatedVersionBz(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("adminID", admin.getAdminID());
		if (count== 30) {
			
			// 对象最大年月
			String yearMonth = this.insuranceSystemCalculateSer.getMaxManageCreateDateBz(request) !=null? this.insuranceSystemCalculateSer.getMaxManageCreateDateBz(request) : "";
			map.put("yearMonth", yearMonth);
			int calFlag = this.insuranceSystemCalculateSer
					.selectPaBenFalgByFalgBz(map);
			if (calFlag != 0) {// 已经核算
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("display.emp.statistics.mes192",request));//保存失败
			} else {
				int result = this.insuranceSystemCalculateSer
						.createBenchmarkStandardVersionBz(map);
				if (result == 1) {
					this.insuranceSystemCalculateSer.freshPaBenManageBz(map);// 刷新上下限
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("display.emp.statistics.mes193",request));//保存失败
				}
			}

		}
		if(count!=30){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("display.emp.statistics.mes190",request));//保存失败
			
		}
		return map;

	}
}
