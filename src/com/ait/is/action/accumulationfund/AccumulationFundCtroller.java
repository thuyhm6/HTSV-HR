package com.ait.is.action.accumulationfund;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.is.action.insurancesystem.InsuranceSystemCalculateCtroller;
import com.ait.is.service.AccumulationFundSer;


import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;

import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;


/**
 * 公积金Ctroller
* @ClassName: AccumulationFundCtroller 
* @Description: TODO
* @author lwei liangwei@ait.net.cn
* @date 2014-1-21 下午2:27:49 
*
 */
@Controller
@RequestMapping(value = "/is/accumulationfund")
public class AccumulationFundCtroller {
	Logger logger = Logger.getLogger(AccumulationFundCtroller.class);
	
	@Autowired
	private AccumulationFundSer accumulationFundSer ;
	@Autowired
	private ToolMenuSer toolMenuSer;

	/**
	 * 跳转到基准管理页面（get Benchmark management）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ViewCPFBenchmarkManagementForSearch")
	public ModelAndView ViewCPFBenchmarkManagementForSearch(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		
		String searchDate = request.getParameter("SearchDate");// 版本日期
		String logtype = request.getParameter("logtype_BX0201") != null ? request
				.getParameter("logtype_BX0201") : "";
		String type = "";
		if (logtype.equals("UPDATE")) {
			type = "update";
		}
		List BenchmarkManagementInfoList = new ArrayList<Object>();
		if ("now".equals(searchDate)) {
			// standardNotSerious =
			// paBenServices.getModifyStandardNotSeriousModifyBz(map);

			BenchmarkManagementInfoList = this.accumulationFundSer.getCPFBenchmarkManagementForSearchDq(request);//日期是当前的查询
			request.setAttribute("allow", true);
		} else {
		
			BenchmarkManagementInfoList= this.accumulationFundSer.getCPFBenchmarkManagementForSearch(request);
		}
		List versionList = this.accumulationFundSer.getVersionDateListBz(request);
		modelMap.put("itemList", BenchmarkManagementInfoList);
		modelMap.put("versionList", versionList);
		modelMap.put("type", type);
		modelMap.put("defaultCpny", admin.getCpnyId());		
		
		modelMap.put("searchDate", searchDate);
		modelMap.put(
				"toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "124911"));
		return new ModelAndView("/is/accumulationfund/ViewCPFBenchmarkManagementForSearch",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ViewBenchmarkManagementForSearchUpdate")
	@ResponseBody
	public Map ViewBenchmarkManagementForSearchUpdate(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		String logtype = request.getParameter("logtype_BX0201");
		if (logtype.equals("UPDATE")) {
			map.put("type", "UPDATE");
		} else if (logtype.equals("insert")) {
			map.put("type", "INSERT");
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateCPFBenchmarkManagement")
	@ResponseBody
	public Map updateBenchmarkManagement(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

		int result = this.accumulationFundSer.updateBenchmarkManagement(request);
		map.put("statusCode", "200");
		map.put("message", TipMessage.getTipMessage("display.emp.statistics.mes191",request));//保存失败
		return map;

	}
	
	/**
	 * 生产版本
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-11 上午11:26:31 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createVersion")
	@ResponseBody
	public Map createVersion(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		int count=this.accumulationFundSer.ifUpdatedVersionBz(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		map.put("adminID", admin.getAdminID());
		if (count== 5) {
			
			// 对象最大年月
			String yearMonth = this.accumulationFundSer.getMaxManageCreateDate(request) !=null? this.accumulationFundSer.getMaxManageCreateDate(request) : "";
			map.put("yearMonth", yearMonth);
			int calFlag = this.accumulationFundSer.selectPaBenFalgByFalg(map);
			if (calFlag != 0) {// 已经核算
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("display.emp.statistics.mes192",request));//保存失败
			} else {
				int result = this.accumulationFundSer
						.createBenchmarkStandardVersion(map);
				if (result == 1) {
					this.accumulationFundSer.freshPaBenManage(map);// 刷新上下限
					map.put("statusCode", "200");
					map.put("message", TipMessage.getTipMessage("display.emp.statistics.mes193",request));//保存失败
				}
			}

		}
		if(count!=5){
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("display.emp.statistics.mes190",request));//保存失败
			
		}
		//map.put("statusCode", "200");
		//map.put("message", TipMessage.getTipMessage("display.emp.statistics.mes193",request));//保存成功
		return map;
		
	}
	
	/**
	 * 公积金--基数管理
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-11 下午4:48:36 
	* @version V1.0
	 */
	@RequestMapping(value = "/ViewCPFBaseManagementForSearch")
	public ModelAndView ViewCPFBaseManagementForSearch(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		String method = request.getParameter("method");//方法区分
		
		List showList = this.accumulationFundSer.ViewCPFBaseManagementForSearch(request);
		request.setAttribute("showList", showList);
		
		if ("create".equals(method)) {
			if (showList == null || showList.size() == 0) {
				request.setAttribute("message", "没有符合条件的人员信息！");
			}
		}
		Map retunrMap=new HashMap<Object, Object>();
		modelMap.put("showList", showList);
		
		
		return new ModelAndView("/is/accumulationfund/ViewCPFBaseManagementForSearch",modelMap);
	}
	/**
	 * 公积金--基数管理 (检索) 数据生成+原数据清空
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-18 上午10:28:36 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createCPFBaseManagement")
	@ResponseBody
	public Map createCPFBaseManagement(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		int calFlag = this.accumulationFundSer.createDataToPaBenBaseBz(request);
		if(calFlag==1){
			map.put("statusCode", "200");
		}else{
			map.put("statusCode", "300");
			map.put("message", "createDataToPaBenBase information Exception. ");
		}
		return map;
	}
	
	/**
	 * 公积金--基数管理 (删除)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-19 下午2:06:54 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteCPFBaseManagement")
	@ResponseBody
	public Map deleteCPFBaseManagement(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		int calFlag =this.accumulationFundSer.deleteCPFBaseManagement(request);
		request.setAttribute("message", "删除成功！");
		if(calFlag==1){
			map.put("statusCode", "200");
			map.put("message", "删除成功");
		}else{
			map.put("statusCode", "300");
			map.put("message", "delete deleteCPFBaseManagement information Exception. ");
		}
		return map;
	}
	/**
	 * 公积金--基数管理 (修改) 进入页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-20 下午2:03:38 
	* @version V1.0
	 */
	@RequestMapping(value = "/updateCPFBaseManagement")
	public ModelAndView updateCPFBaseManagement(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		/*String[] ids = request.getParameterValues("check");
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				paBenHsServices.allowPaBenBaseNumUpdate(id);
			}
		}
		List list = paBenHsServices.getupdateCPFBaseManagement();//预修改的信息集合
		request.setAttribute("allowUpdate", list);
		System.out.println(request.getParameter("ids"));*/
		int calFlaga =this.accumulationFundSer.allowPaBenBaseNumUpdate(request);
		if(calFlaga==1){
			List list = this.accumulationFundSer.getupdateCPFBaseManagement(request);//预修改的信息集合
			modelMap.put("allowUpdate", list);
		}
		return new ModelAndView("/is/accumulationfund/updateCPFBaseManagement",modelMap);
	}
	/**
	 * 公积金--基数管理 (修改保存)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-21 下午2:30:50 
	* @version V1.0
	 */
	@RequestMapping(value = "/editCPFBaseManagement")
	@ResponseBody
	public Map editCPFBaseManagement(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.accumulationFundSer.editCPFBaseManagement(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "bx0202");
			//map.put("PERSON_ID", request.getParameter("PERSON_ID"));
			
		//	map.put("forwardUrl","/hrm/empinfo/viewCompetence?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			map.put("forwardUrl","/is/accumulationfund/ViewCPFBaseManagementForSearch?navTabId=bx0202") ;	
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
	/**
	 * 工资计算
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-25 下午1:55:18 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/computeCPFBaseManagement")
	@ResponseBody 
	public Map computeCPFBaseManagement(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		String calFlag =this.accumulationFundSer.computeCPFBaseManagement(request);
		map.put("message", calFlag);
		
		return map;
	}
	
	/**
	 * 进入发令页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-25 下午5:11:00 
	* @version V1.0
	 */
	@RequestMapping(value = "/orderCPFBaseManagement")
	public ModelAndView orderCPFBaseManagement(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		/*int calFlaga =this.accumulationFundSer.allowPaBenBaseNumUpdate(request);
		if(calFlaga==1){
			List list = this.accumulationFundSer.getupdateCPFBaseManagement(request);//预修改的信息集合
			modelMap.put("allowUpdate", list);
		}*/
		
		String yearMonth = this.accumulationFundSer.getMaxYearMonthOfComputionBz(request);//查询最大月
		if (yearMonth != null && !"".equals(yearMonth)) {
			request.setAttribute("yearMonth", yearMonth);
			request.setAttribute("paType", "PaApplyType1");
			String affirm = this.accumulationFundSer.afterComputationAffirmBz(request);
			if (affirm != null && "1".equals(affirm)) {//最大核算年月裁决通过
				int orderCount = this.accumulationFundSer.paBenBaseNumOrderCountBz();//发令人数 
				int orderTrueCount = this.accumulationFundSer.paBenBaseNumTrueOrderCountBz();//实发影响人数
				request.setAttribute("orderCount", orderCount);
				request.setAttribute("orderTrueCount", orderTrueCount);
				if (orderCount > orderTrueCount) {//如果发令有剩余，则显示剩余人员信息
					List falseOrder = this.accumulationFundSer.getPaBenBaseNumOrderListBz(request);
					request.setAttribute("falseOrder", falseOrder);//违法令过去的人员信息
				}
				request.setAttribute("alertMsg", yearMonth.substring(0, 4)+"年"+yearMonth.substring(4, 6)+"月公积金数据已经通过工资裁决，数据将发令到下个月！");
				/*String flag = request.getParameter("flag");//确定发令标识
				if ("Y".equals(flag)) {
					if (paBenHsServices.copyDataToPaBenManageBz(map)) {
						request.setAttribute("message", "发令成功！");
					}else{
						request.setAttribute("message", "发令失败！");
					}
				}*/
			}else {
				request.setAttribute("message", yearMonth.substring(0, 4)+"年"+yearMonth.substring(4, 6)+"月数据核算后尚未裁决通过，不可发令！");
			}
		}else{
			int orderCount = this.accumulationFundSer.paBenBaseNumOrderCountBz();//发令人数 
			int orderTrueCount = this.accumulationFundSer.paBenBaseNumTrueOrderCountBz();//实发影响人数
			request.setAttribute("orderCount", orderCount);
			request.setAttribute("orderTrueCount", orderTrueCount);
			if (orderCount > orderTrueCount) {//如果发令有剩余，则显示剩余人员信息
				List falseOrder = this.accumulationFundSer.getPaBenBaseNumOrderListBz(request);
				request.setAttribute("falseOrder", falseOrder);//违法令过去的人员信息
			}
			//String flag = request.getParameter("flag");//确定发令标识
			/*if ("Y".equals(flag)) {
				if (paBenHsServices.copyDataToPaBenManageBz(map)) {
					request.setAttribute("message", "发令成功！");
				}else{
					request.setAttribute("message", "发令失败！");
				}
			}*/
		}
		return new ModelAndView("/is/accumulationfund/orderCPFBaseManagement",modelMap);
	}
	/**
	 * 公积金 --发令--发令
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2014-2-28 下午4:12:09 
	* @version V1.0
	 */
	@RequestMapping(value = "/orderUpdateCPFBaseManagement")
	@ResponseBody
	public Map orderUpdateCPFBaseManagement(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.accumulationFundSer.copyDataToPaBenManageBz(request);
		if(result == 1){
			map.put("stat1usCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("navTabId", "bx0202");
			//map.put("PERSON_ID", request.getParameter("PERSON_ID"));
		//	map.put("forwardUrl","/hrm/empinfo/viewCompetence?PERSON_ID=" + request.getParameter("PERSON_ID")+"&navTabId=hr0101") ;
			map.put("forwardUrl","/is/accumulationfund/ViewCPFBaseManagementForSearch?navTabId=bx0202") ;	
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
		return map;
	}
//	/**
//	 * 公积金-基数管理  导出数据
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/insCPFBaseManagementNumListExcel")
//	public ModelAndView insCPFBaseManagementNumListExcel(HttpServletRequest request,
//				HttpServletResponse response,ModelMap modelMap) throws Exception{
//		
//		List insBaseNumInfoList = this.accumulationFundSer.getCPFBenchmarkManagementForSearch(request);
//		
//		modelMap.put("itemList", insBaseNumInfoList);
//		
//		return new ModelAndView("/is/insurancesystem/viewNOInsBaseNumInfoExcel",modelMap);
//	}
}
