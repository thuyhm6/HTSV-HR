package com.ait.ar.action.attendanceMintenance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.ait.ar.service.ArMonthCalculateSer;
import com.ait.ar.service.ArMonthSer;
import com.ait.ar.service.CycleSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArMonthCtroller.java
 * @Description:
 * @Create date: 2012-2-11 下午03:06:26
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class ArMonthCtroller {
	Logger logger = Logger.getLogger(ArMonthCtroller.class);

	@Autowired
	private ArMonthSer arMonthSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private ArMonthCalculateSer arMonthCalculateSer;
	@Autowired
    private CycleSer cycleSer;
	/**
	 * 汇总维护画面(view ArMonth)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArMonth")
	public ModelAndView viewArMonthList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String statNo = request.getParameter("seach_STAT_NO") !=null ? request.getParameter("seach_STAT_NO") : "";
		String supervisorId = admin.getPersonId();
		
		List getArColumnsList = arMonthSer.getArColumns(request); // 获取显示汇总项目列名
		
		
		String arMonth = this.getToday("yyyy") + this.getToday("MM");

		if (request.getParameter("seach_year_ar0106") != null
				&& request.getParameter("seach_month_ar0106") != null) {
			
			arMonth = request.getParameter("seach_year_ar0106") + request.getParameter("seach_month_ar0106");
		}

		List list = new ArrayList();
//		list = arMonthSer.getMonthlyStatusList(request, arMonth); // 取得考勤锁定状态
		
		List getArMonthList = new ArrayList();
		int getArMonthListCnt = 0;
		//当区间不为空的时候才可以查看页面信息 防止修改
		if(!"".equals(statNo)){
			list = arMonthSer.getMonthlyStatusList(request, arMonth, statNo); // 取得考勤锁定状态
			getArMonthList = this.arMonthSer.getArMonthList(request, getArColumnsList, arMonth);
			getArMonthListCnt = this.arMonthSer.getArMonthListCnt(request, arMonth);
		}
		//取考勤区间
		List statnoList = this.arMonthCalculateSer.getStatNoList(request) ;
		List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		modelMap.put("STAT_NO", statNo) ;
		modelMap.put("statnoList", statnoList) ;
		modelMap.put("arMonth", arMonth);
		modelMap.put("supervisorId", supervisorId);
//		modelMap.put("arColumnsList", JsonUtil.writeInternal(getArColumnsList));
		modelMap.put("arColumnsList", getArColumnsList);
		modelMap.put("mothlyLock", list.size() == 0 ? "0" : Integer.parseInt(ObjectUtils.toString(((LinkedHashMap) list
				.get(0)).get("ATT_MO_LOCK_FLAG"))));
		modelMap.put("getArMonthList", getArMonthList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, getArMonthListCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2376")) ;
		 
		return new ModelAndView("/ar/attendanceMintenance/viewArMonth",
				modelMap);
	}
		
	
	
	/**
	 * 汇总个人信息画面(view ArMonth)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArMonthPersonInfo")
	public ModelAndView viewArMonthPersonInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String statNo = request.getParameter("seach_STAT_NO") !=null ? request.getParameter("seach_STAT_NO") : "";
		String supervisorId = admin.getPersonId();
		
		
		
		String arMonth = this.getToday("yyyy") + this.getToday("MM");

		if (request.getParameter("seach_year_ar0106") != null
				&& request.getParameter("seach_month_ar0106") != null) {
			
			arMonth = request.getParameter("seach_year_ar0106") + request.getParameter("seach_month_ar0106");
		}

		List getArMonthListYN = new ArrayList();
		// 获取显示汇总项目列名
		List getArColumnsListYN = arMonthSer.getArColumnsYN(request); 
		//取考勤区间
		//List statnoList = this.arMonthCalculateSer.getStatNoList(request) ;
		String empid = request.getParameter("seach_condition")!=null?request.getParameter("seach_condition"):"";
		//request.setAttribute("numPerPage", 0);
		if(!"".equals(empid) && empid != null){
			 getArMonthListYN = this.arMonthSer.getArMonthListYN(request, getArColumnsListYN, arMonth);
		}
		
		String datetable = arMonthSer.makeTableHTML("", getArColumnsListYN, getArMonthListYN);
		
	//	modelMap.put("STAT_NO", statNo) ;
		modelMap.put("condition", empid) ;
		//modelMap.put("statnoList", statnoList) ;
		modelMap.put("arMonth", arMonth);
		modelMap.put("supervisorId", supervisorId);
		modelMap.put("datetable", datetable);
		return new ModelAndView("/ar/attendanceMintenance/viewArMonthPersonInfo",
				modelMap);
	}

	/**
	 * 汇总个人信息画面(view ArMonth) ess 专用只看个人考勤信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArMonthPersonInfoEssList")
	public ModelAndView viewArMonthPersonInfoEssList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String statNo = request.getParameter("seach_STAT_NO") !=null ? request.getParameter("seach_STAT_NO") : "";
		String supervisorId = admin.getPersonId();
		
		
		
		String arMonth = this.getToday("yyyy") + this.getToday("MM");

		if (request.getParameter("seach_year_ar0106") != null
				&& request.getParameter("seach_month_ar0106") != null) {
			
			arMonth = request.getParameter("seach_year_ar0106") + request.getParameter("seach_month_ar0106");
		}

		List getArMonthListYN = new ArrayList();
		// 获取显示汇总项目列名
		List getArColumnsListYN = arMonthSer.getArColumnsYN(request); 
		//取考勤区间
		//List statnoList = this.arMonthCalculateSer.getStatNoList(request) ;
		String empid = request.getParameter("seach_condition")!=null?request.getParameter("seach_condition"):"";
		empid=admin.getEmpID();
		//request.setAttribute("numPerPage", 0);
		//if(!"".equals(empid) && empid != null){
			 getArMonthListYN = this.arMonthSer.getArMonthEssListYN(request, getArColumnsListYN, arMonth);
		//}
		
		String datetable = arMonthSer.makeTableHTML("", getArColumnsListYN, getArMonthListYN);
		
	//	modelMap.put("STAT_NO", statNo) ;
		//modelMap.put("condition", empid) ;
		//modelMap.put("statnoList", statnoList) ;
		modelMap.put("arMonth", arMonth);
		modelMap.put("supervisorId", supervisorId);
		modelMap.put("datetable", datetable);
		return new ModelAndView("/ar/attendanceMintenance/viewArMonthPersonInfoEssList",
				modelMap);
	}
	
	
	@RequestMapping(value = "/checkMonthlyStatus", method = RequestMethod.POST)
	@ResponseBody
	public String checkMonthlyStatus(HttpServletRequest request)
			throws Exception {

		return this.arMonthSer.retrieveMonthlyStatus(request);
	}

	/**
	 * 修改考勤汇总(update ArMonth Info)
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateArMonthInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map updateArMonthInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.arMonthSer.updateArMonthInfo(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "ar0106");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		
		return map;
	}
	
	/**
	 * 根据格式参数返回当前日期
	 * @param pOutformat
	 * @return String
	 */
    private String getToday(String pOutformat) {

        SimpleDateFormat pOutformatter = new SimpleDateFormat(pOutformat,
                java.util.Locale.CHINA);

        String rDateString = null;
        Date vDate = new Date();

        try {
            rDateString = pOutformatter.format(vDate);

        } catch (Exception e) {
        }

        return rDateString;
    }
    
}
