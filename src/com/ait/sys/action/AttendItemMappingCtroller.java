package com.ait.sys.action;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.ait.ar.service.ItemsSer;
import com.ait.ar.service.SummaryItemSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.salary.PaComputeItemSer;
import com.ait.pa.service.salary.PaInputItemParamSer;
import com.ait.pa.service.wagebase.PaBasicItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.AttendItemMappingSer;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.sys.service.salaryMappingSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/sys/attendancesetting")
public class AttendItemMappingCtroller {
	Logger logger = Logger.getLogger(AttendItemMappingCtroller.class);

	@Autowired
	private AttendItemMappingSer attendItemMappingSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private ItemsSer itemsSer;
	@Autowired
	private SummaryItemSer summaryItemSer;
	@Autowired
	private CompanySer companySer;
	@Autowired
	private AuthorityUtil authorityUtil;
	
	@Autowired
	private EmpInfoSer empInfoSer;

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
	@RequestMapping(value = "/viewAttendItemMappingList")
	public ModelAndView viewAttendItemMappingList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List attendItemList = this.attendItemMappingSer
				.getAttendItemMappingList(request);
		List companyList = companySer.getAllCompanyItemList(request);
		int AttendItemCnt = this.attendItemMappingSer
				.getAttendItemMappingCnt(request);
		modelMap.put("companyList", companyList);
		modelMap.put("add_name", TipMessage.getTipMessage(
				"sys.basic.title.designatedLegalPerson", request));// 指定法人
		modelMap.put("attendItemList", attendItemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, AttendItemCnt);
		modelMap.put("companyList", companyList);
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2526"));
		return new ModelAndView(
				"/sys/attendancesetting/viewAttendItemMappingList", modelMap);
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
	@RequestMapping(value = "/newAttendItemMappingView")
	public ModelAndView newAttendItemMappingView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List attendItemList = this.attendItemMappingSer
				.getAttendItemMappingList(request);
		modelMap.put("attendItemList", attendItemList);
		modelMap.put("TYPE", request.getParameter("seach_TYPE"));
		return new ModelAndView(
				"/sys/attendancesetting/newAttendItemMappingView", modelMap);
	}

	/**
	 * 指定法人(add Pa Basic Item Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAttendItemMappingInfo")
	@ResponseBody
	public Map updateAttendItemMappingInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] item_no = request.getParameterValues("ITEM_NO"); // 项目
		String project_type[] = request.getParameterValues("PROJECT_TYPE"); // 明细or汇总
		String group_no = "constant";
		for (int j = 0; j < item_no.length; j++) {
			String[] cpny_ids = request.getParameterValues("CPNY_ID" + j);
			if (cpny_ids != null && cpny_ids.length > 0) {
				if (project_type[j] == "1" || project_type[j].equals("1")) {
					this.itemsSer.updateItemParamInfoAll(item_no[j]);
				} else if (project_type[j] == "2"
						|| project_type[j].equals("2")) {
					this.summaryItemSer
							.updateSummaryItemParamInfoAll(item_no[j]);
				}
				for (int i = 0; i < cpny_ids.length; i++) {
					String cpny_id = cpny_ids[i];
					if (project_type[j] == "1" || project_type[j].equals("1")) {
						int num = this.itemsSer.checkForItemParam(request,
								cpny_id, item_no[j], group_no);
						if (num == 0) {
							int resultNum = this.itemsSer.addItemParamInfo(
									request, cpny_id, item_no[j]);
							if (resultNum == 1) {
								map.put("statusCode", "200");
								map.put("message", TipMessage
										.getTipMessage(
												"alert.message.update_success",
												request));
								map.put("navTabId", "ar0317");
								map.put("callbackType", "closeCurrent");
							} else {
								map.put("statusCode", "300");
								map.put("message", TipMessage.getTipMessage(
										"alert.message.update_fail", request));
							}
						} else {
							int numInt = this.itemsSer.updateItemParamInfo(
									request, cpny_id, item_no[j]);
							if (numInt == 1) {
								map.put("statusCode", "200");
								map.put("message", TipMessage
										.getTipMessage(
												"alert.message.update_success",
												request));
								map.put("navTabId", "ar0317");
								map.put("callbackType", "closeCurrent");
							} else {
								map.put("statusCode", "300");
								map.put("message", TipMessage.getTipMessage(
										"alert.message.update_fail", request));
							}
						}
					} else if (project_type[j] == "2"
							|| project_type[j].equals("2")) {
						int num = this.summaryItemSer.checkForItemParam(
								request, item_no[j], cpny_id, group_no);
						if (num == 0) {
							int resultNum = this.summaryItemSer
									.addSummaryItemParamInfo(request, cpny_id,
											item_no[j]);
							if (resultNum == 1) {
								map.put("statusCode", "200");
								map.put("message", TipMessage
										.getTipMessage(
												"alert.message.update_success",
												request));
								map.put("navTabId", "ar0317");
								map.put("callbackType", "closeCurrent");
							} else {
								map.put("statusCode", "300");
								map.put("message", TipMessage.getTipMessage(
										"alert.message.update_fail", request));
							}
						} else {
							int numInt = this.summaryItemSer
									.updateSummaryItemParamInfo(request,
											cpny_id, item_no[j]);
							if (numInt == 1) {
								map.put("statusCode", "200");
								map.put("message", TipMessage
										.getTipMessage(
												"alert.message.update_success",
												request));
								map.put("navTabId", "ar0317");
								map.put("callbackType", "closeCurrent");
							} else {
								map.put("statusCode", "300");
								map.put("message", TipMessage.getTipMessage(
										"alert.message.update_fail", request));
							}
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * 考勤代码查看(view Pa Basic Item)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAttendItemCheckList")
	public ModelAndView viewAttendItemCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
		modelMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		List attendItemList = this.attendItemMappingSer
				.getAttendItemCheckMapList(request);
		int attendItemCnt = this.attendItemMappingSer
				.getAttendItemCheckMapCnt(request);
		modelMap.put("attendItemList", attendItemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendItemCnt);
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2526"));
		return new ModelAndView(
				"/sys/attendancesetting/viewAttendItemCheckList", modelMap);
	}

	/**
	 * 到公共添加页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addAttendItemAffirmView")
	public ModelAndView addAttendItemAffirmView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String personName = admin.getLocalName();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID",
				paramMap.get("CPNY_ID") == null ? admin.getCpnyId() : paramMap
						.get("CPNY_ID").toString());
		modelMap.put("date", date);
		modelMap.put("personName", personName);
		return new ModelAndView(
				"/sys/attendancesetting/addAttendItemAffirmView", modelMap);
	}

	/**
	 * 申请新代码（考勤代码包括明细项目和汇总项目）
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addAffirmAttendItemInfo")
	@ResponseBody
	public Map addAffirmAttendItemInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String item_id = request.getParameter("ITEM_ID");
	    String activity_type = request.getParameter("ACTIVITY_TYPE");
		if(activity_type=="1" || "1".equals(activity_type)){
			 int resultNum = this.attendItemMappingSer.addAffirmAttendItemInfo(request);
			 if (resultNum == 1) {
				 map.put("statusCode", "200");
				 map.put("message", TipMessage.getTipMessage(
						 "pa.salarycode.affirm.success", request));
				 map.put("navTabId", "pa1104");
		     } else {
			     map.put("statusCode", "300");
			     map.put("message", TipMessage.getTipMessage(
					 "pa.salarycode.affirm.fail", request));
		     }
	    }else{
	    	String project_type = request.getParameter("PROJECT_TYPE");
	    	if(project_type == "1" || "1".equals(project_type)){
	    		int errorInt = this.itemsSer.checkForItemParamUnique(request);
	    		if(errorInt == 0){
	    			int resultNum1 = this.attendItemMappingSer.addAffirmAttendInfo(request);
	   			 if (resultNum1 == 1) {
	   				 map.put("statusCode", "200");
	   				 map.put("message", TipMessage.getTipMessage(
	   						 "pa.salarycode.affirm.success", request));
	   				 map.put("navTabId", "pa1104");
	   		     } else {
	   			     map.put("statusCode", "300");
	   			     map.put("message", TipMessage.getTipMessage(
	   					 "pa.salarycode.affirm.fail", request));
	   		     }
	    	   }else{
	    		   map.put("statusCode", "300");
	   			     map.put("message", TipMessage.getTipMessage(
	   					 "ar.item.title.itemapply", request));
	    	   }
	    	}else{
	    		int errorInt = this.summaryItemSer.checkForItemParamUnique(request);
	    		if(errorInt == 0){
	    			int resultNum1 = this.attendItemMappingSer.addAffirmAttendInfo(request);
	   			 if (resultNum1 == 1) {
	   				 map.put("statusCode", "200");
	   				 map.put("message", TipMessage.getTipMessage(
	   						 "pa.salarycode.affirm.success", request));
	   				 map.put("navTabId", "pa1104");
	   		     } else {
	   			     map.put("statusCode", "300");
	   			     map.put("message", TipMessage.getTipMessage(
	   					 "pa.salarycode.affirm.fail", request));
	   		     }
	    	   }else{
	    		   map.put("statusCode", "300");
	   			     map.put("message", TipMessage.getTipMessage(
	   					 "ar.item.title.itemapply", request));
	    	   }
	    	}
	    }
		return map;
	}

	/**
	 * 去代码启用申请页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAttendItemAffirmInfoView")
	public ModelAndView addAttendItemAffirmInfoView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String personName = admin.getLocalName();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间
		Object attendItemInfo = this.attendItemMappingSer
				.getAttendItemMappingInfo(request);
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("attendItemInfo", attendItemInfo);
		modelMap.put("date", date);
		modelMap.put("personName", personName);
		return new ModelAndView(
				"/sys/attendancesetting/addAttendItemAffirmInfoView", modelMap);
	}

}
