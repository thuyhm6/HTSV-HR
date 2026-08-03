package com.ait.ar.action.arShiftGroupManagement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ArShiftGroupHistorySer;
import com.ait.ar.service.CycleSer;
import com.ait.ar.service.EmpCalendarSer;
import com.ait.ess.service.EssDeptEmpAttSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: EmpCalendarCtroller.java
 * @Description:
 * @Create date: 2012-2-6 上午10:49:39
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/arShiftGroupManagement")
public class ArShiftGroupHistoryCtroller {
	Logger logger = Logger.getLogger(ArShiftGroupHistoryCtroller.class);
	@Autowired
	private ArShiftGroupHistorySer arShiftGroupHistorySer;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	/**
	 * 考勤基本事项
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArBaseEmpInfoList")
	public ModelAndView viewArBaseEmpInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List arShiftGroupHistoryList = this.arShiftGroupHistorySer.getArBaseEmpInfoList(request);
		int arShiftGroupHistoryCnt = this.arShiftGroupHistorySer.getArBaseEmpInfoCnt(request);

		modelMap.put("arShiftGroupHistoryList", arShiftGroupHistoryList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, arShiftGroupHistoryCnt);
		return new ModelAndView("/ar/arShiftGroupManagement/viewArBaseEmpInfoList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArBaseEmpInfoDetail")
	public ModelAndView viewArBaseEmpInfoDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("arBaseEmpInfoDetail", this.arShiftGroupHistorySer.getArBaseEmpInfoDetail(request));
		modelMap.put("arEmpShiftGroupFinalInfo", this.arShiftGroupHistorySer.getArEmpShiftGroupFinalInfo(request));
		return new ModelAndView("/ar/arShiftGroupManagement/viewArBaseEmpInfoDetail", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateArBaseEmpInfoDetail")
	@ResponseBody
	public Map updateArBaseEmpInfoDetail(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.arShiftGroupHistorySer.updateArBaseEmpInfoDetail(request);
		if (result == 1) {
			map.put("statusCode", "200");
			map.put("message", "保存成功");
		} else {
			map.put("statusCode", "300");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * 班组履历的查询
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArShiftRecordCheckList")
	public ModelAndView viewArShiftRecordCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List viewArShiftRecordCheckList = this.arShiftGroupHistorySer.getArShiftRecordCheckList(request);

		modelMap.put("viewArShiftRecordCheckList", viewArShiftRecordCheckList);
		modelMap.put("viewArShiftRecordCheckListSize", viewArShiftRecordCheckList == null ? 0:viewArShiftRecordCheckList.size());
		return new ModelAndView("/ar/arShiftGroupManagement/viewArShiftRecordCheckList", modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArShiftRecordCheckInfoDetail")
	public ModelAndView viewArShiftRecordCheckInfoDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("viewArShiftRecordCheckInfoDetail", this.arShiftGroupHistorySer.getArShiftRecordCheckInfoDetail(request));
		return new ModelAndView("/ar/arShiftGroupManagement/viewArShiftRecordCheckInfoDetail", modelMap);
	}
	
	
	/**
	 * 班组的月别列表查询
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArShiftMonthCheckList")
	public ModelAndView viewArShiftMonthCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		String cpnyId=admin.getCpnyId();
		String SHIFT_NO=(String) request.getParameter("seach_SHIFT_NO");
		String DDATE = request.getParameter("seach_DDATE");
		if((DDATE == null || DDATE == "")&&(SHIFT_NO != null && SHIFT_NO != "")){
			Date dt = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("MMyyyy");
		    DDATE=sdf.format(dt);
		    request.setAttribute("DDATE", DDATE);
		}
		if(SHIFT_NO==null || SHIFT_NO.length() == 0){
			request.setAttribute("SHIFT_NO", "400224");
			SHIFT_NO="400224";
		}
	    
		List viewArShiftMonthCheckList = this.arShiftGroupHistorySer.getArShiftMonthCheckListViewHtml(request);
		
		modelMap.put("shiftNo", SHIFT_NO);
		modelMap.put("ddate", DDATE);
		modelMap.put("viewArShiftMonthCheckList", viewArShiftMonthCheckList);
		modelMap.put("viewArShiftMonthCheckListCnt",viewArShiftMonthCheckList == null ? 0:viewArShiftMonthCheckList.size());

		return new ModelAndView("/ar/arShiftGroupManagement/viewArShiftMonthCheckList",modelMap);
	}
	
}
