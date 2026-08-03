package com.ait.ess.action;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.ait.ar.service.ShiftSer;
import com.ait.ess.service.EssDeptEmpAttSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/ess/deptEmpAtt")
public class EssDeptEmpAttCtroller {

	Logger logger = Logger.getLogger(EssDeptEmpAttCtroller.class);
	
	@Autowired
	private EssDeptEmpAttSer essDeptEmpAttSer;
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;	
	@Autowired
	private ShiftSer shiftSer;
	/** 
	 * 班组变更查询
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArShiftGroupList")
	public ModelAndView viewArShiftGroupList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List arShiftGroupList = this.essDeptEmpAttSer.viewArShiftGroupList(request);
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap=new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO","400223");
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		modelMap.put("codeList", codeList);
		//List shiftList = shiftSer.getShiftList1(request);
		//modelMap.put("shiftList", shiftList) ;
		
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag !=null || !"".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("END_DATE",last);
			}
		}
		modelMap.put("SHIFT_NO", request.getParameterValues("seach_SHIFT_NO"));
		modelMap.put("arShiftGroupList", arShiftGroupList);
		return new ModelAndView("/ess/deptEmpAtt/viewArShiftGroupList", modelMap);
	}
	/**
	 * 班组变更信息(addArShiftGroupInfo)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addArShiftGroupInfo")
	@ResponseBody
	public Map addArShiftGroupInfo(HttpServletRequest request,HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.essDeptEmpAttSer.addArShiftGroupInfo(request);
			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success", request));
				map.put("formId", "viewArShiftGroupForm");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));
				map.put("formId", "viewArShiftGroupForm");
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail", request));
			map.put("formId", "viewArShiftGroupForm");
		}

		return map;

	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delArShiftGroupInfo")
	@ResponseBody
	public Map delArShiftGroupInfo(HttpServletRequest request,HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int errorInt = this.essDeptEmpAttSer.delArShiftGroupInfo(request);
			if (errorInt == 1) {
				map.put("statusCode", "200");
				map.put("message", "删除成功");
				map.put("formId", "viewArShiftGroupForm");
			} else {
				map.put("statusCode", "300");
				map.put("message", "删除失败");
				map.put("formId", "viewArShiftGroupForm");
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", "删除失败");
			map.put("formId", "viewArShiftGroupForm");
		}

		return map;

	}
}