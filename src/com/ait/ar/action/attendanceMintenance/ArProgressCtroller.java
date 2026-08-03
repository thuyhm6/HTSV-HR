package com.ait.ar.action.attendanceMintenance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.dao.ArProgressDao;
import com.ait.ar.service.ArMonthCalculateSer;
import com.ait.ar.service.ArMonthSer;
import com.ait.ar.service.ArProgressSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArProgressCtroller.java
 * @Description:
 * @Create date: 2012-2-12 上午11:59:07
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class ArProgressCtroller {
	Logger logger = Logger.getLogger(ArProgressCtroller.class);

	@Autowired
	private ArProgressSer arProgressSer;
	@Autowired
	private ArMonthCalculateSer arMonthCalculateSer;

	/**
	 * 考勤锁定页面(view ArProgress)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArProgress")
	public ModelAndView viewArProgressList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List arProgressList = this.arProgressSer.getArProgressList(request);
		int arProgressCnt = this.arProgressSer.getArProgressCnt(request) ;
		//取得考勤区间
		List statnoList = this.arMonthCalculateSer.getStatNoList(request) ;
		
		
		
		
		modelMap.put("statnoList", statnoList);
		modelMap.put("arProgressList", arProgressList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, arProgressCnt) ;
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		modelMap.put("CPNY_ID", admin.getCpnyId());
		if (admin.getCpnyId().equals("TSTO")){
		List deptList = this.arMonthCalculateSer.getDeptAreaList(request) ;
		modelMap.put("deptList", deptList) ;
		modelMap.put("DEPT_NO", request.getParameter("DEPT_NO"));
		}
		return new ModelAndView("/ar/attendanceMintenance/viewArProgress",
				modelMap);
	}

	/**
	 * 更新考勤锁定(update ArProgress Info)
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateArProgressInfo")
	@ResponseBody
	public String updateArProgressInfo(HttpServletRequest request)
			throws Exception {

		this.arProgressSer.updateArProgressInfo(request);

		return "Y";
	}
}
