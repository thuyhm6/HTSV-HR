package com.ait.ar.action.attendanceSettings;
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

import com.ait.ar.service.ItemsSer;
import com.ait.ar.service.SummaryItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.AttendItemSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/sys/attendancesetting")
public class AffirmAttendItemCtroller {
	Logger logger = Logger.getLogger(AffirmAttendItemCtroller.class);

	@Autowired
	private AttendItemSer attendItemSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private ItemsSer itemsSer;
	@Autowired
	private SummaryItemSer summaryItemSer;

	/**
	 * 跳转到基础项目库首页(view Pa Basic Item)(决裁查看)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmAttendItemList")
	public ModelAndView viewAffirmAttendItemList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List attendItemList = this.attendItemSer
				.getAffirmAttendItemList(request);
		int attendItemCnt = this.attendItemSer.getAffirmAttendItemCnt(request);
		String cpny_id = admin.getCpnyId();
		if (cpny_id != null && !"".equals(cpny_id)) {
			modelMap.put("CPNY_ID", cpny_id);
		}
		modelMap.put("attendItemList", attendItemList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendItemCnt);
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217847"));
		return new ModelAndView(
				"/sys/attendancesetting/viewAffirmAttendItemList", modelMap);
	}

	/**
	 * 跳转到基础项目库首页(view Pa Basic Item)(决裁列表)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmAttendList")
	public ModelAndView viewAffirmAttendList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List attendList = this.attendItemSer.getAffirmAttendList(request);
		int attendCnt = this.attendItemSer.getAffirmAttendCnt(request);

		modelMap.put("attendList", attendList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendCnt);
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "217848"));
		return new ModelAndView("/sys/attendancesetting/viewAffirmAttendList",
				modelMap);
	}
	
	/**
	 * 跳转到决裁页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAffirmArItemInfo")
	public ModelAndView viewAffirmArItemInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List attendList = this.attendItemSer.getAffirmAttendList(request);
		Object arItemInfo = null;
		if(attendList.size()>0){
			arItemInfo = attendList.get(0);
		}
		modelMap.put("arItemInfo", arItemInfo);
		return new ModelAndView("/sys/attendancesetting/viewAffirmAttendList",
				modelMap);
	}
	
	

	/**
	 * 进行决裁，如果决裁通过则考勤代码直接进入相应的考勤代码库中
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAffirmAttendInfo")
	@ResponseBody
	public Map updateAffirmAttendInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String project_type = request.getParameter("PROJECT_TYPE");
		String affirm_type = request.getParameter("ACTIVITY_TYPE");
		String flag = request.getParameter("flag");
		String item_no = request.getParameter("ITEM_NO");
		String cpny_id = request.getParameter("cpny");
		String group_no = "constant";
		if (flag == "1" || "1".equals(flag)) {
			if (affirm_type == "1" || "1".equals(affirm_type)) {
				int resultInt = this.attendItemSer
						.updateAffirmAttendInfo(request);
				if (resultInt == 1) {
					if (project_type == "1" || project_type.equals("1")) {
							int result = this.itemsSer.addItemInfo(request, cpny_id, item_no);
						    int errorNum = this.itemsSer.addItemParamInfo(request, cpny_id, item_no);
							if (result == 1 && errorNum==1) {
								map.put("statusCode", "200");
								map.put("message", TipMessage.getTipMessage(
										"ess.viewApply.title.pass", request));
								map.put("navTabId", "ar0321");
								map.put("callbackType", "closeCurrent");
							} else {
								map.put("statusCode", "300");
								map.put("message", TipMessage.getTipMessage(
										"pa.salarycode.title.affirm_fail",
										request));
							}
						} 
				else if (project_type == "2" || project_type.equals("2")) {
						
							int result = this.summaryItemSer.addSummaryItemInfoAffirm(request, cpny_id, item_no);
							int errorNum = this.summaryItemSer.addSummaryItemParamInfo(request, cpny_id, item_no);
							if (result == 1 && errorNum==1) {
								map.put("statusCode", "200");
								map.put("message", TipMessage.getTipMessage(
										"ess.viewApply.title.pass", request));
								map.put("navTabId", "ar0321");
								map.put("callbackType", "closeCurrent");
							} else {
								map.put("statusCode", "300");
								map.put("message", TipMessage.getTipMessage(
										"pa.salarycode.title.affirm_fail",
										request));
							}
						} 
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"pa.salarycode.title.affirm_fail",
							request));
				}
			} else {
				if (affirm_type == "2" || "2".equals(affirm_type)) {
					int resultInt = this.attendItemSer
							.updateAffirmAttendInfo(request);
					if (resultInt == 1) {
						if (project_type == "1" || project_type.equals("1")) {
							int errorInt = this.itemsSer.checkForItemParam(request, cpny_id, item_no, group_no);
							if (errorInt == 0) {
								int result = this.itemsSer
										.addItemParamInfo(request, cpny_id, item_no);
								if (result == 1) {
									map.put("statusCode", "200");
									map.put("message", TipMessage
											.getTipMessage(
													"ess.viewApply.title.pass",
													request));
									map.put("navTabId", "ar0321");
									map.put("callbackType", "closeCurrent");
								} else {
									map.put("statusCode", "300");
									map.put("message",
											TipMessage
													.getTipMessage(
															"pa.salarycode.title.affirm_fail",
															request));
								}
							} else {
								map.put("statusCode", "300");
								map.put("message", TipMessage.getTipMessage(
										"pa.salarycode.title.affirm_fail",
										request));
							}
						} else if (project_type == "2"
								|| project_type.equals("2")) {
							int errorInt = this.summaryItemSer
									.checkForItemParam(request, item_no, cpny_id, group_no);
							if (errorInt == 0) {
								int result = this.summaryItemSer
										.addSummaryItemParamInfo(request, cpny_id, item_no);
								if (result == 1) {
									map.put("statusCode", "200");
									map.put("message", TipMessage
											.getTipMessage(
													"ess.viewApply.title.pass",
													request));
									map.put("navTabId", "ar0321");
									map.put("callbackType", "closeCurrent");
								} else {
									map.put("statusCode", "300");
									map.put("message",
											TipMessage
													.getTipMessage(
															"pa.salarycode.title.affirm_fail",
															request));
								}
							} else {
								map.put("statusCode", "300");
								map.put("message", TipMessage.getTipMessage(
										"pa.salarycode.title.affirm_fail",
										request));
							}
						} 
					} else {
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage(
								"pa.salarycode.title.affirm_fail", request));
					}
				}
			}
		}else{
			int resultInt = this.attendItemSer
					.updateAffirmAttendInfo(request);
			if(resultInt==1){
				map.put("statusCode", "200");
			map.put("message", TipMessage
					.getTipMessage(
							"ess.viewApply.title.reject",
							request));
			map.put("navTabId", "ar0321");
			map.put("callbackType", "closeCurrent");
			}
		}
		return map;
	}

	/**
	 * 执行删除 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAffirmAttendItemInfo")
	@ResponseBody
	public Map<String, Object> deleteAffirmAttendItemInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
				int resultNum = this.attendItemSer
						.deleteAffirmAttendItemInfo(request);
				if (resultNum == 1) {
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_success", request));
					map.put("navTabId", "ar0320");
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));
			    }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
}
