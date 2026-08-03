package com.ait.ess.action;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ess.service.PersonalPaInfoSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.workManagement.viewPaParamSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.ViewOptionUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: PaInfoCtroller.java
 * @Description:
 * @Create date: 2012-5-23 下午06:50:02
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ess/infoView")
public class PersonalPaInfoCtroller {
	Logger logger = Logger.getLogger(PersonalPaInfoCtroller.class);

	@Autowired
	private PersonalPaInfoSer personalpaInfoSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private EmpInfoSer empInfoSer;

	@Autowired
	private viewPaParamSer viewPaParamSer;

	/**
	 * 个人工资页面(view Personal PaInfo)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPersonalPaInfo")
	public ModelAndView viewPersonalPaInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 默认
		String menuNo = "23540";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// HQ的特殊需求
		System.out.println(request.getParameter("essYear"));
		// 获取人员的基本信息
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
				.getPersonalInfoByPid(request);
		if (!"".equals(StringUtil.checkNull(request.getParameter("essYear")))
				&& !"".equals(StringUtil.checkNull(request
						.getParameter("essMonth")))
				&& "TSTO".equals(admin.getCpnyId())) {
			// 工资项目
			List addProList = this.personalpaInfoSer.getAddProList(request);
			// 考勤项目
			// List miProList = this.personalpaInfoSer.getMiProList(request) ;
			modelMap.put("addProList", addProList.size() > 0 ? addProList
					.get(0) : null);
			// modelMap.put("miProList",
			// miProList.size()>0?miProList.get(0):null);
		}
		modelMap.put("view_EMPID", admin.getEmpID());
		modelMap.put("view_LOCALNAME", admin.getLocalName());
		modelMap.put("dataTable", "");
		modelMap.put("linkMap", linkMap);
		modelMap.put("essYear", request.getParameter("essYear"));
		modelMap.put("essMonth", request.getParameter("essMonth"));
		modelMap.put("essGIVE_DATE", request.getParameter("essGIVE_DATE"));
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		return new ModelAndView("/ess/infoView/viewPersonalPaInfo", modelMap);
	}

	/**
	 * 个人工资页面(view Personal PaInfo) 页面导出
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPersonalPaInfoExcel")
	public ModelAndView viewPersonalPaInfoExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// HQ的特殊需求
		if (!"".equals(request.getParameter("essYear"))
				&& !"".equals(request.getParameter("essMonth"))
				&& "TSTO".equals(admin.getCpnyId())) {

			LinkedHashMap linkMap = (LinkedHashMap) empInfoSer
					.getPersonalInfoByPid(request);

			// 增加项
			List addProList = this.personalpaInfoSer.getAddProList(request);

			// 减项
			List miProList = this.personalpaInfoSer.getMiProList(request);

			modelMap.put("addProList", addProList.size() > 0 ? addProList
					.get(0) : null);

			modelMap.put("miProList", miProList.size() > 0 ? miProList.get(0)
					: null);
			modelMap.put("linkMap", linkMap);

		}

		modelMap.put("view_EMPID", admin.getEmpID());

		modelMap.put("view_LOCALNAME", admin.getLocalName());

		return new ModelAndView("/report/hr/viewPersonalPaInfoExcel", modelMap);
	}

	/**
	 * 关联出保险发放日
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author lwei liangwei@ait.net.cn
	 * @date 2013-9-18 下午6:56:53
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSalaryProvideDateEss")
	@ResponseBody
	public Map getSalaryProvideDateEss(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List getSalaryProvideDateEssList = this.personalpaInfoSer
				.getSalaryProvideDateEss(request);

		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		if (getSalaryProvideDateEssList.size() == 0) {
			map.put("", "当前月没有符合发放日期");
		} else {
			for (int i = 0; i < getSalaryProvideDateEssList.size(); i++) {
				// logger.debug("key"+i+"]:::"+(String)((Map)
				// getSalaryProvideDateEssList.get(i)).get("ESSGIVE_DATE"));
				// logger.debug("value"+i+"]:::"+((Map)
				// getSalaryProvideDateEssList.get(i)).get("ESSGIVE_DATE"));
				map.put((String) ((Map) getSalaryProvideDateEssList.get(i))
						.get("ESSGIVE_DATE"),
						((Map) getSalaryProvideDateEssList.get(i))
								.get("ESSGIVE_DATE"));
			}
		}
		return map;

	}

	// viewPersonalPaInfoPrint
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPersonalPaInfoPrint")
	public ModelAndView viewPersonalPaInfoPrint(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// HQ的特殊需求
		if (!"".equals(request.getParameter("essYear"))
				&& !"".equals(request.getParameter("essMonth"))
				&& "C02".equals(admin.getCpnyId())) {

			// 增加项
			List addProList = this.personalpaInfoSer.getAddProList(request);

			// 减项
			List miProList = this.personalpaInfoSer.getMiProList(request);

			modelMap.put("addProList", addProList);

			modelMap.put("miProList", miProList);

		}

		modelMap.put("view_EMPID", admin.getEmpID());

		modelMap.put("view_LOCALNAME", admin.getLocalName());
		modelMap.put("view_DeptNo", admin.getDeptNo());
		// modelMap.put("PA_MONTH",
		// request.getParameter("essYear")+request.getParameter("essMonth"));
		modelMap.put("essYear", request.getParameter("essYear"));
		modelMap.put("essMonth", request.getParameter("essMonth"));
		modelMap.put("essGIVE_DATE", request.getParameter("essGIVE_DATE"));

		if (admin.getLanguage().toString().equals("zh")) {
			modelMap.put("src", "\\resources\\images\\button\\paInfo_zh.jpg");
		} else {
			modelMap.put("src", "\\resources\\images\\button\\paInfo_ko.jpg");
		}
		// modelMap.put("src", src);

		return new ModelAndView("/report/hr/viewPersonalPaInfoPrint", modelMap);
	}

	/**
	 * 查询或打印前验证当前月工资是否开放
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author lwei liangwei@ait.net.cn
	 * @date 2013-11-6 下午8:18:23
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSalaryDispark")
	@ResponseBody
	public Map getSalaryDispark(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		int no = this.personalpaInfoSer.getSalaryDispark(request);

		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

		map.put("no", no);
		return map;

	}
}