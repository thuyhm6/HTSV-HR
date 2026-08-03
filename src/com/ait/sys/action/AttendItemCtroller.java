package com.ait.sys.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;


import com.ait.ar.service.ItemsSer;
import com.ait.ar.service.SummaryItemSer;
import com.ait.pa.service.salary.PaComputeItemSer;
import com.ait.pa.service.salary.PaInputItemSer;
import com.ait.pa.service.salarycode.salaryCodeSer;
import com.ait.pa.service.wagebase.PaBasicItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.AttendItemSer;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
@Controller
@RequestMapping(value = "/sys/attendancesetting")
public class AttendItemCtroller {
	Logger logger = Logger.getLogger(AttendItemCtroller.class);

		@Autowired
		private AttendItemSer attendItemSer;
		@Autowired
		private ToolMenuSer toolMenuSer;
		@Autowired
		private CompanySer companySer;
		@Autowired
		private ItemsSer itemsSer;
		@Autowired
		private SummaryItemSer summaryItemSer;

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
		@RequestMapping(value = "/viewAttendItemList")
		public ModelAndView viewAttendItemList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {

			List attendItemList = this.attendItemSer.getAttendItemList(request);
			int attendItemCnt = this.attendItemSer.getAttendItemCnt(request);
			
			List companyList = this.companySer.getCompanyItemList(request);

			modelMap.put("companyList", companyList);
			modelMap.put("attendItemList", attendItemList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, attendItemCnt);
			modelMap.put(
					"toolbarInfo",
					request.getParameter("menuNo") != null ? toolMenuSer
							.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
							request, "216690"));
			return new ModelAndView("/sys/attendancesetting/viewAttendItemList", modelMap);
		}

		/**
		 * 到公共添加页面
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/addAttendItemView")
		public ModelAndView addAttendItemView(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
			modelMap.put("CPNY_ID",
					paramMap.get("CPNY_ID") == null ? admin.getCpnyId() : paramMap
							.get("CPNY_ID").toString());

			return new ModelAndView("/sys/attendancesetting/addAttendItemView", modelMap);
		}

		/**
		 * 执行添加(add Pa Basic Item Info)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/addAttendItemInfo")
		@ResponseBody
		public Map addAttendItemInfo(HttpServletRequest request) throws Exception {
			Map<String, Object> map = new HashMap();
			String project_type = request.getParameter("PROJECT_TYPE");
			if (project_type == "1" || project_type.equals("1")) {
				int errorInt = this.itemsSer.checkItemInfoUnique(request);
				try {
					if (errorInt == 0) {
						int resultNum = this.itemsSer.addItemInfo(request);
						if (resultNum == 1) {
							map.put("statusCode", "200");
							map.put("message", TipMessage.getTipMessage(
									"alert.message.add_success", request));
							map.put("navTabId", "ar0314");
						} else {
							map.put("statusCode", "300");
							map.put("message", TipMessage.getTipMessage(
									"alert.message.add_fail", request));
						}
					} else {
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage(
								"alert.message.ID_conflict", request));
					}
				} catch (Exception e) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.add_fail", request));
				}
			} else if (project_type == "2" || project_type.equals("2")) {
				int errorInt = this.summaryItemSer.checkSummaryItemInfo(request);
                System.out.println(request.toString());
				if (errorInt == 0) {
					int resultNum = this.summaryItemSer.addSummaryItemInfo(request);
					if (resultNum == 1) {
						map.put("statusCode", "200");
						map.put("message", TipMessage.getTipMessage(
								"alert.message.add_success", request));
						map.put("navTabId", "ar0314");
					} else {
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage(
								"alert.message.add_fail", request));
					}
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.ID_conflict", request));
				}
			} 
			return map;
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
		@RequestMapping(value = "/updateAttendItemView",method = RequestMethod.GET)
		public ModelAndView updateAttendItemView(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			Object AttendItemInfo = this.attendItemSer.getAttendItemInfo(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			modelMap.put("CPNY_ID", admin.getCpnyId());
			modelMap.put("AttendItemInfo", AttendItemInfo);
			return new ModelAndView("/sys/attendancesetting/updateAttendItemView", modelMap);
		}

		/**
		 * 修改工资项目信息(update Pa Basic Item Info)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/updateAttendItemInfo")
		@ResponseBody
		public Map updateAttendItemInfo(HttpServletRequest request)
				throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			String project_type = request.getParameter("PROJECT_TYPE");
			if (project_type == "1" || project_type.equals("1")) {
				int errorInt = this.itemsSer.checkItemInfoUnique(request);
				if (errorInt == 0) {
					int result = this.itemsSer.updateItemInfo(request);
					if (result == 1) {
						map.put("statusCode", "200");
						map.put("message", TipMessage.getTipMessage(
								"alert.message.update_success", request));
						map.put("navTabId", "ar0314");
					} else {
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage(
								"alert.message.update_fail", request));
					}
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.ID_update_conflict", request));
				}
			}else if (project_type == "2" || project_type.equals("2")) {
				int errorInt = this.summaryItemSer.checkSummaryItemInfo(request);
				if (errorInt == 0) {
					int result = this.summaryItemSer.updateSummaryItemInfo(request);
					if (result == 1) {
						map.put("statusCode", "200");
						map.put("message", TipMessage.getTipMessage(
								"alert.message.update_success", request));
						map.put("navTabId", "ar0314");
					} else {
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage(
								"alert.message.update_fail", request));
					}
				} else {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.ID_update_conflict", request));
				}
			}
			return map;
		}

		/**
		 * 执行删除 删除前判断有没有使用的数据
		 * 
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/deleteAttendItemInfo")
		@ResponseBody
		public Map<String, Object> deleteAttendItemInfo(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			String project_type = request.getParameter("PROJECT_TYPE");
			if (project_type == "1" || project_type.equals("1")) {
				try {
					if(this.itemsSer.checkForItemDelete(request) == 0){
						int result = itemsSer.deleteItemInfo(request); 
						if(result == 1){
							map.put("statusCode", "200");
							map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
							map.put("navTabId", "ar0314");
						}else{
							map.put("statusCode", "300");
							map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
						}
					}else{
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage("alert.message.delete_info_use",request));//该信息使用中,不能删除
					}

				} catch (Exception e) {
					map.put("statusCode", "300");
					map.put("message", TipMessage.getTipMessage(
							"alert.message.delete_fail", request));
				}

			} else if (project_type == "2" || project_type.equals("2")) {
				if(this.summaryItemSer.checkForItemDelete(request) == 0){

					int result = this.summaryItemSer.deleteSummaryItemInfo(request) ; 
					if(result == 1){
						map.put("statusCode", "200");
						map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
						map.put("navTabId", "ar0314");
					}else{
						map.put("statusCode", "300");
						map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
					}
				}
			}
			return map;
			}

}
