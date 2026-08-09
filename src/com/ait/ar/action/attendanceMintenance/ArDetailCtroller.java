package com.ait.ar.action.attendanceMintenance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ArDetailSer;
import com.ait.ar.service.CycleSer;
import com.ait.ar.service.ItemsSer;
import com.ait.ar.service.ShiftSer;
import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.mail.ar.SendArDetailMail;
import com.ait.web.mail.ar.SendArDetailMailToDeptManager;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArDetailCtroller.java
 * @Description:
 * @Create date: 2012-2-7 下午04:28:49
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class ArDetailCtroller {

	@Autowired
	private ArDetailSer arDetailSer;

	@Autowired
	private ToolMenuSer toolMenuSer;
	
	@Autowired
	private EmpInfoSer  empInfoSer;
	
	@Autowired
	private CycleSer cycleSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	private ShiftSer shiftSer ;
	
	@Autowired
	private AuthorityUtil authorityUtil;
	
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;	
	
	@Autowired
	private ItemsSer itemsSer;
	
	@Autowired
	private InfoApplyLeaveSer infoApplyLeaveSer;

	/**
	 * 明细维护页面(detail Calculate)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArDetail")
	public ModelAndView viewArDetailList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {

		/*		// 本月的第一天
				Calendar calendar = new GregorianCalendar();
				calendar.set(Calendar.DATE, 1);
				SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");

				// 本月的最后一天
				Calendar calendar2 = new GregorianCalendar();
				calendar2.set(Calendar.DATE, 1);
				calendar2.roll(Calendar.DATE, -1);
				SimpleDateFormat simpleFormate2 = new SimpleDateFormat("yyyy-MM-dd");

				modelMap.put("sDate", simpleFormate.format(calendar.getTime()));
		        modelMap.put("eDate", simpleFormate2.format(calendar2.getTime())); //结束时间为月末最后一天
		*/
				
				  Date d=new Date();   
				 SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");   
			  //   modelMap.put("sDate", df.format(new Date(d.getTime() - 1 * 24 * 60 * 60 * 1000)));
			  //   modelMap.put("eDate", df.format(new Date(d.getTime() - 1 * 24 * 60 * 60 * 1000))); //开始时间和结束时间为同一天(昨天的日期)
				 modelMap.put("sDate", df.format(new Date(d.getTime() - 24*60*60*1000)));
			     modelMap.put("eDate", df.format(new Date(d.getTime() - 24*60*60*1000)));  
			        
			     String seach_FIRST_FLAG = request.getParameter("seach_FIRST_FLAG");
			     if(seach_FIRST_FLAG != null && !"".equals(seach_FIRST_FLAG)){
			 		List getArDetailList = this.arDetailSer.getArDetailList(request,
							modelMap);
					int getArDetailListCnt = this.arDetailSer.getArDetailListCnt(request,
							modelMap);
					modelMap.put("arDetailList", getArDetailList);
					modelMap.put(UiUtil.TOTAL_COUNT_NAME, getArDetailListCnt);
			    }
			     
				List getItemList = arDetailSer.getItemList(request);
				AdminBean admin = SessionUtil.getLoginUserFromSession(request);
				String supervisorId = admin.getAdminID();
				//此考勤员的考勤员权限是否有效
				String modifyYn=arDetailSer.getModifyYnBySupervisorId(supervisorId);
				// modelMap.put("sDate", request.getParameter("sDate") != null ? request
				// .getParameter("sDate") : arDetailSer.getStartDateStr());
				// modelMap.put("eDate", request.getParameter("eDate") != null ? request
				// .getParameter("eDate") : arDetailSer.getEndDateStr());
				modelMap.put("getItemList", getItemList);
				modelMap.put("supervisorId", supervisorId);
				modelMap.put("MODIFY_YN", modifyYn);
				modelMap.put("ItemList", JsonUtil.writeInternal(getItemList));
				modelMap.put("toolbarInfo",
						request.getParameter("menuNo") != null ? toolMenuSer
								.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
								request, "2363"));
				return new ModelAndView("/ar/attendanceMintenance/viewArDetail",
						modelMap);
	}

	// @RequestMapping(value = "/getArDetailList", method = RequestMethod.POST)
	// @ResponseBody
	// public Map getArDetailList(HttpServletRequest request) throws Exception {
	//
	// List getArDetailList = this.arDetailSer.getArDetailList(request);
	// int getArDetailListCnt = this.arDetailSer.getArDetailListCnt(request);
	// Map model = new HashMap();
	// model.put("Rows", getArDetailList);
	// model.put("Total", getArDetailListCnt);
	//
	// return model;
	// }

	/**
	 * 修改考勤明细信息(update ArDetail Info)
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateArDetailInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map updateArDetailInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		Map messMap = new LinkedHashMap();

		messMap = (LinkedHashMap) this.arDetailSer.updateArDetailInfo(request);
		if (messMap.get("scode").toString().equals("1")) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request));// 修改成功
			map.put("navTabId", "ar0201");
		} else if (messMap.get("scode").toString().equals("0")) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));// 修改失败
		} else if (messMap.get("scode").toString().equals("3")) {
			map.put("statusCode", "300");
			map.put("message", "保存失败,["+messMap.get("LOCAL_NAME").toString()+"]没有决裁者");// 修改失败
		} else {
			map.put("statusCode", "300");
			map.put("message", messMap.get("message").toString());
		}
		map.put("result", messMap.get("scode"));
		return map;

	}

	/**
	 * 删除考勤明细信息(delete ArDetail Info)
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteArDetailInfo")
	@ResponseBody
	public Map deleteArDetailInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		Map messMap = new LinkedHashMap();

		messMap = (LinkedHashMap) this.arDetailSer.deleteArDetailInfo(request);
		if (messMap.get("scode").toString().equals("1")) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_success", request));// 删除成功
			map.put("navTabId", "ar0201");
		} else if (messMap.get("scode").toString().equals("0")) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.delete_fail", request));// 删除失败
		} else {
			map.put("statusCode", "300");
			map.put("message", messMap.get("message").toString());
		}

		return map;

	}

	// public String addArDetailInfo(HttpServletRequest request)throws
	// Exception{
	//	 
	// return this.arDetailSer.addArDetailInfo(request) ;
	//		 
	// }
	//	
	/**
	 * 添加考勤明细信息(add ArDetail Info)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addArDetailInfo")
	@ResponseBody
	public Map addArDetailInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		Map messMap = new LinkedHashMap();

		messMap = (LinkedHashMap) this.arDetailSer.addArDetailInfo(request);
		if (messMap.get("scode").toString().equals("1")) {
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_success", request));// 保存成功
			map.put("navTabId", "ar0201");
		} else if (messMap.get("scode").toString().equals("0")) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.add_fail", request));// 保存失败
		} else {
			map.put("statusCode", "300");
			map.put("message", messMap.get("message").toString());
		}
		map.put("result", messMap.get("scode"));
		map.put("condition", messMap.get("condition"));
		return map;
	}

	/**
	 * 邮件发送每日员工考勤异常明细信息(SearchArDetailExceptionInfo)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
//	@SuppressWarnings("unchecked")
//	//@RequestMapping(value = "/SearchArDetailExceptionInfo")
//	@ResponseBody
	public void SearchArDetailExceptionInfo(String string)
			throws Exception {
        //System.out.println(string+"%%%%%%%%%%%%%%%%%%%%");
        HttpServletRequest request = null;
        request.setAttribute("", string);
		List arDetailExInfo = this.arDetailSer.SearchArDetailExceptionInfo(request);
		//System.out.println(arDetailExInfo.size()+"^^^^^^^^^^^^^^^^^^");
		Map map=new HashMap<String, String>();
		for(int i=0;i<arDetailExInfo.size();i++){
			Map map1=(Map) arDetailExInfo.get(i);
			//System.out.println(map1.get("EMAIL")+"************");
		}
		//System.out.println(map.get("EMAIL")+"*******");
		SendArDetailMail sendardetailmail = new SendArDetailMail();
		sendardetailmail.setList(arDetailExInfo);
		new Thread(sendardetailmail).start();
	}

	/**
	 * 每周五发送部门异常信息给领导(SearchArDetailExceptionInfo)
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SearchArDetailDeptExToMa")
	@ResponseBody
	public void SearchArDetailDeptExToMa(HttpServletRequest request)
			throws Exception {
		List managerinfo = ((ArDetailSer) this.arDetailSer).SearchManagerInfo(request);
		for (int i = 0; i < managerinfo.size(); i++) {
			Map map = (Map) managerinfo.get(i);
			System.out.println(map.get("PERSON_ID")+"***********");
			String MANAGER_EMP_ID = (String) map.get("PERSON_ID");
			String startDate=(String) map.get("startdate");
		    String endDate=(String) map.get("enddate");
		    String deptName=(String) map.get("DEPTNAME");
		    String eMail=(String) map.get("EMAIL");
			List arList = this.arDetailSer
					.SearchArDetailDeptExToMa(MANAGER_EMP_ID);
			SendArDetailMailToDeptManager sendArMailToManager = new SendArDetailMailToDeptManager();
			sendArMailToManager.setList(arList);
			sendArMailToManager.setArMailToManager(eMail);
			sendArMailToManager.setStartDate(startDate);
			sendArMailToManager.setEndDate(endDate);
			sendArMailToManager.setDeptName(deptName);
			//sendArMailToManager.send();
		    new Thread(sendArMailToManager).start();
		   }
	}
	
	/**
	 * 明细维护页面(detail Calculate)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/viewArDetailEssList")
	public ModelAndView viewArDetailEssList(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception {
		/*
        // 本月的第一天
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");

		// 本月的最后一天
		Calendar calendar2 = new GregorianCalendar();
		calendar2.set(Calendar.DATE, 1);
		calendar2.roll(Calendar.DATE, -1);
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat("yyyy-MM-dd");

		modelMap.put("sDate", simpleFormate.format(calendar.getTime()));
        modelMap.put("eDate", simpleFormate2.format(calendar2.getTime())); //结束时间为月末最后一天
        */
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Date d=new Date();   
		SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");  //数据库中的格式为yy/mm/dd 使用yy-mm-dd查不出数据 
	    modelMap.put("sDate", df.format(new Date(d.getTime() - 1 * 24 * 60 * 60 * 1000)));
		modelMap.put("eDate", df.format(new Date(d.getTime() - 1 * 24 * 60 * 60 * 1000))); //开始时间和结束时间为同一天(昨天的日期)
		//20150206 zyh start
		modelMap.put("cpny_id", admin.getCpnyId());
		//20150206 zyh end
		List getArDetailList = this.arDetailSer.getArDetailEssList(request,modelMap);
		int getArDetailListCnt = this.arDetailSer.getArDetailEssListCnt(request,modelMap);
		List getItemList = arDetailSer.getItemList(request);
		List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
		modelMap.put("jobTypeGroupList", jobTypeGroupList);
		HttpSession session = request.getSession();
	
		String supervisorId = admin.getAdminID();
		List getEmpTypeCodeList = this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
		
		//modelMap.put("sDate", request.getParameter("sDate") != null ? request.getParameter("sDate") : arDetailSer.getStartDateStr());
		//modelMap.put("eDate", request.getParameter("eDate") != null ? request.getParameter("eDate") : arDetailSer.getEndDateStr());
		modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
		modelMap.put("getItemList", getItemList);
		modelMap.put("supervisorId", supervisorId);
		modelMap.put("ItemList", JsonUtil.writeInternal(getItemList));
		modelMap.put("arDetailList", getArDetailList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, getArDetailListCnt);
		modelMap.put("toolbarInfo",request.getParameter("menuNo")!=null?toolMenuSer.getToolMenu(request):toolMenuSer.getToolMenuForNo(request,"2387"));
		return new ModelAndView("/ar/attendanceMintenance/viewArDetailEssList",modelMap);
	}
	
	/**
	 * 休假导入临时保存画面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewImportArDetailTempList")
	public ModelAndView viewImportArDetailTempList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paArDetailTempList = this.arDetailSer.getArDetailTempList(request);
		int paArDetailTempCnt = this.arDetailSer.getArDetailTempCnt(request , "T");
		int errorCnt = this.arDetailSer.getArDetailTempCnt(request , "E");
		
		modelMap.put("paArDetailTempList", paArDetailTempList);
		modelMap.put("paArDetailTempCnt", paArDetailTempCnt);

		modelMap.put("errCnt", errorCnt);
		modelMap.put("totalCnt", paArDetailTempCnt);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paArDetailTempCnt);
		return new ModelAndView("/ar/attendanceMintenance/viewImportArDetailTempList", modelMap);
	}
	

	/**
	 * 休假批量申请excel信息提交
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitImportExcelArDetailData")
	@ResponseBody
	public Map submitImportExcelArDetailData (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String, Object> jo = new HashMap<String, Object>();

		String msg= this.arDetailSer.submitImportExcelArDetailData(request);
		if("OK".equals(msg)){
			jo.put("statusCode", "200");
			jo.put("message", "提交成功");//保存成功
			jo.put("navTabId", "ar0201");
			jo.put("callbackType", "closeCurrent");
		}else{
			jo.put("statusCode", "200");
			jo.put("message", "提交失败");//保存失败
		}
		return jo;
	}
	

	/**
	 * 休假批量申请数据导出
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportBatchArDetailData")
	public void exportBatchArDetailData(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List aliasNameList = new ArrayList();

		aliasNameList.add(TipMessage.getTipMessage(
				"ar.viewardetail.title.date", request));// 日期
		aliasNameList.add(TipMessage.getTipMessage(
				"ar.excelexport.title.month", request));// 月份
		aliasNameList.add(TipMessage.getTipMessage("public.title.empId",
				request));// 工号
		aliasNameList.add(TipMessage
				.getTipMessage("public.title.name", request));// 姓名
		aliasNameList.add(TipMessage.getTipMessage("public.title.deptName",
				request));// 部门

		aliasNameList.add("考勤类型(请填写sheet2中的数据)");// 状态(请填写sheet2中的数据)
		aliasNameList.add("考勤开始日期");// 考勤的开始时间
		aliasNameList.add("考勤开始时间");// 考勤的开始时间
		aliasNameList.add("考勤结束日期");// 考勤的结束时间
		aliasNameList.add("考勤结束时间");// 考勤的结束时间
		aliasNameList.add("时长");// 长度
		aliasNameList.add("是否删除当天考勤(Y/N)");// 长度

		List list = new ArrayList();

		List paEssLeaveTempList = this.arDetailSer.getArDetailTempList(request);
		for(int i=0;i<paEssLeaveTempList.size();i++){
			LinkedHashMap dataMap = (LinkedHashMap)paEssLeaveTempList.get(i);
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", dataMap.get("AR_DATE_STR"));
			map.put("CELL1", dataMap.get("AR_MONTH"));
			map.put("CELL2", dataMap.get("EMPID"));
			map.put("CELL3", dataMap.get("LOCAL_NAME"));
			map.put("CELL4", dataMap.get("DEPT_NAME"));
			map.put("CELL5", dataMap.get("ITEM_NAME"));
			map.put("CELL6", dataMap.get("AR_START_DATE1"));
			map.put("CELL7", dataMap.get("AR_START_TIME"));
			map.put("CELL8", dataMap.get("AR_END_DATE1"));
			map.put("CELL9", dataMap.get("AR_END_TIME"));
			map.put("CELL10", dataMap.get("LENGTH"));
			map.put("CELL11", dataMap.get("DELETE_YN"));
			map.put("CELL12", dataMap.get("UPLOAD_ERROR_MSG") == null ? "" : dataMap.get("UPLOAD_ERROR_MSG"));
			list.add(map);
		}
		
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		mapNameList.add("考勤类型参考");
		String sqlItemName = "SELECT S.CONTENT FROM AR_ITEM_PARAM A,SY_GLOBAL_NAME S "
			+ "WHERE A.ITEM_NO=S.NO(+) AND S.LANGUAGE(+)='"
			+ admin.getLanguage()
			+ "'"
			+ " AND A.GROUP_NO = 'constant' " 
			+ " AND A.CPNY_ID='"
			+ admin.getCpnyId() + "' and A.ACTIVITY=1";
		mapList.add(sqlItemName);
		
		LinkedHashMap sqlContentmap = this.excelUtilSer
				.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,"temp_ess_vac");
	}
	
	
	/**
	 * 加班管理最新(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArOvertimeManagent")
	public ModelAndView viewArOvertimeManagentList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		
		//进行删除空数据
		String deleteYN = request.getParameter("deleteYN");
		if ("Y".equals(deleteYN)) {
			infoApplyLeaveSer.deleteAllDataForAdd(request);
		}
		//全部反应用
		modelMap.put("APP_OT_DATE",request.getParameter("APP_OT_DATE"));
		modelMap.put("fromTime1",request.getParameter("fromTime1"));
		modelMap.put("toTime1",request.getParameter("toTime1"));
		modelMap.put("reason",request.getParameter("reason"));
		modelMap.put("otherReason",request.getParameter("otherReason"));
		modelMap.put("FILLAFFIRMFLAG",request.getParameter("FILLAFFIRMFLAG"));
		modelMap.put("work_time_shift1",request.getParameter("work_time_shift1"));
		modelMap.put("CONFIRM_FLAG1",request.getParameter("CONFIRM_FLAG1"));
		modelMap.put("ADJSTYN_OT",request.getParameter("ADJSTYN_OT"));
		
		modelMap.put("ADJSTYN", (request.getParameter("ADJSTYN") != null&&!"".equals(request.getParameter("ADJSTYN")) ?  request.getParameter("ADJSTYN") : 0));
		modelMap.put("attenState", (request.getParameter("attenState") != null&&!"".equals(request.getParameter("attenState")) ?  request.getParameter("attenState") : 1));
		
		List shiftList = shiftSer.getShiftList1(request);
		List itemList = itemsSer.getItemParamList2(request);
		List workTimeLsit = shiftSer.getWorkTimeLsit(request);
		List dateTypeLsit = shiftSer.getDateTypeLsit(request);
		modelMap.put("workTimeList", workTimeLsit) ;
		modelMap.put("dateTypeLsit", dateTypeLsit) ;
		modelMap.put("itemList", itemList) ;
		modelMap.put("shiftList", shiftList) ;
		
		//班次显示，根据日期类型
		List workTimeLsit1 = shiftSer.getWorkTimeLsit1(request);
		modelMap.put("workTimeList1", workTimeLsit1) ;
		List workTimeLsit2 = shiftSer.getWorkTimeLsit2(request);
		modelMap.put("workTimeList2", workTimeLsit2) ;
		
		Date d=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		String FROM_DATE = request.getParameter("seach_FROM_DATE") != null ? request.getParameter("seach_FROM_DATE") :df.format(d) ;
		String TO_DATE = request.getParameter("seach_TO_DATE") != null ? request.getParameter("seach_TO_DATE") : df.format(d);
		modelMap.put("FROM_DATE",  FROM_DATE);
		modelMap.put("TO_DATE", TO_DATE);
		//删除多余的加班或者为0 的数据
		LinkedHashMap deleteMap = new LinkedHashMap();
		deleteMap.put("FROM_DATE", FROM_DATE);
		deleteMap.put("TO_DATE", TO_DATE);
		arDetailSer.deleteOtAdjustForOnlyOne(deleteMap);
		
		String   DEPTNO  =request.getParameter("seach_DEPTNO");
		modelMap.put("empName",request.getParameter("dwz.person.empName"));
		modelMap.put("empInfo",request.getParameter("dwz.person.empInfo"));
		modelMap.put("AFFIRM_FLAG",request.getParameter("seach_AFFIRM_FLAG"));
		modelMap.put("GROUP_ID",request.getParameter("seach_GROUP_ID"));
		modelMap.put("SHIFT_NO",request.getParameter("seach_SHIFT_NO"));
		modelMap.put("CONFIRM_FLAG",request.getParameter("seach_CONFIRM_FLAG"));
		modelMap.put("EMP_TYPE_CODE",request.getParameter("seach_EMP_TYPE_CODE"));
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("admin", admin);
		modelMap.put("DEPTNO", DEPTNO);
		Map paramMap=new LinkedHashMap();
		//审批code
		paramMap.put("PARENT_CODE_NO","14014304");
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		
		//原因code
		Map paramMap2=new LinkedHashMap();
		paramMap2.put("PARENT_CODE_NO","14014313");
		paramMap2.put("interLanguage",admin.getLanguage());
		paramMap2.put("CPNY_ID",admin.getCpnyId());
		List codeList2 = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap2, -1, -1) ;
		//班组code
		Map paramMap4=new LinkedHashMap();
		paramMap4.put("PARENT_CODE_NO","400223");
		paramMap4.put("interLanguage",admin.getLanguage());
		paramMap4.put("CPNY_ID",admin.getCpnyId());
		List codeList4 = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap4, -1, -1) ;

		modelMap.put("codeList", codeList);
		modelMap.put("codeList2", codeList2);
		modelMap.put("codeList4", codeList4);
		String firstFlag = request.getParameter("firstFlag");
		String nullYN = request.getParameter("nullYN");
		if(firstFlag != null && !"".equals(firstFlag)&&!"Y".equals(nullYN)){
			modelMap.put("oTAffirmList", arDetailSer.getOtAffirmInfoListBatch(request));
			LinkedHashMap linkMap2= (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo",linkMap2);
		}else {
			List nullOTTSTOAffirmList = infoApplyLeaveSer.getNullBatchOTTSTOAffirmInfoList(request);
			modelMap.put("nullOTTSTOAffirmList", nullOTTSTOAffirmList);
		}
		return new ModelAndView("/ar/attendanceMintenance/viewArOvertimeManagent", modelMap);
	}
	
	/**
	 * 加班管理测试版(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArOvertimeManagent_new")
	public ModelAndView viewArOvertimeManagentNewList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		
		
		//全部反应用
		modelMap.put("APP_OT_DATE",request.getParameter("APP_OT_DATE"));
		modelMap.put("fromTime1",request.getParameter("fromTime1"));
		modelMap.put("toTime1",request.getParameter("toTime1"));
		modelMap.put("reason",request.getParameter("reason"));
		modelMap.put("otherReason",request.getParameter("otherReason"));
		modelMap.put("FILLAFFIRMFLAG",request.getParameter("FILLAFFIRMFLAG"));
		modelMap.put("work_time_shift1",request.getParameter("work_time_shift1"));
		modelMap.put("CONFIRM_FLAG1",request.getParameter("CONFIRM_FLAG1"));
		modelMap.put("ADJSTYN_OT",request.getParameter("ADJSTYN_OT"));
		
		modelMap.put("ADJSTYN", (request.getParameter("ADJSTYN") != null&&!"".equals(request.getParameter("ADJSTYN")) ?  request.getParameter("ADJSTYN") : 0));
		modelMap.put("attenState", (request.getParameter("attenState") != null&&!"".equals(request.getParameter("attenState")) ?  request.getParameter("attenState") : 1));
		
		List shiftList = shiftSer.getShiftList1(request);
		List itemList = itemsSer.getItemParamList2(request);
		List workTimeLsit = shiftSer.getWorkTimeLsit(request);
		List dateTypeLsit = shiftSer.getDateTypeLsit(request);
		modelMap.put("workTimeList", workTimeLsit) ;
		modelMap.put("dateTypeLsit", dateTypeLsit) ;
		modelMap.put("itemList", itemList) ;
		modelMap.put("shiftList", shiftList) ;
		
		//班次显示，根据日期类型
		List workTimeLsit1 = shiftSer.getWorkTimeLsit1(request);
		modelMap.put("workTimeList1", workTimeLsit1) ;
		List workTimeLsit2 = shiftSer.getWorkTimeLsit2(request);
		modelMap.put("workTimeList2", workTimeLsit2) ;
		
		Date d=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		modelMap.put("FROM_DATE", df.format(d));
		modelMap.put("TO_DATE", df.format(d));
		
		String   DEPTNO  =request.getParameter("seach_DEPTNO");
		modelMap.put("empName",request.getParameter("dwz.person.empName"));
		modelMap.put("empInfo",request.getParameter("dwz.person.empInfo"));
		modelMap.put("AFFIRM_FLAG",request.getParameter("seach_AFFIRM_FLAG"));
		modelMap.put("GROUP_ID",request.getParameter("seach_GROUP_ID"));
		modelMap.put("SHIFT_NO",request.getParameter("seach_SHIFT_NO"));
		modelMap.put("CONFIRM_FLAG",request.getParameter("seach_CONFIRM_FLAG"));
		modelMap.put("EMP_TYPE_CODE",request.getParameter("seach_EMP_TYPE_CODE"));
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("admin", admin);
		modelMap.put("DEPTNO", DEPTNO);
		Map paramMap=new LinkedHashMap();
		//审批code
		paramMap.put("PARENT_CODE_NO","14014304");
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		
		//原因code
		Map paramMap2=new LinkedHashMap();
		paramMap2.put("PARENT_CODE_NO","14014313");
		paramMap2.put("interLanguage",admin.getLanguage());
		paramMap2.put("CPNY_ID",admin.getCpnyId());
		List codeList2 = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap2, -1, -1) ;
		//班组code
		Map paramMap4=new LinkedHashMap();
		paramMap4.put("PARENT_CODE_NO","400223");
		paramMap4.put("interLanguage",admin.getLanguage());
		paramMap4.put("CPNY_ID",admin.getCpnyId());
		List codeList4 = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap4, -1, -1) ;

		modelMap.put("codeList", codeList);
		modelMap.put("codeList2", codeList2);
		modelMap.put("codeList4", codeList4);
		String firstFlag = request.getParameter("firstFlag");
		if(firstFlag != null && !"".equals(firstFlag)){
			modelMap.put("oTAffirmList", arDetailSer.getOtAffirmInfoListBatch(request));
			//modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyLeaveSer.getBatchLeaveAffirmInfoListCnt(request));
		}else {
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		return new ModelAndView("/ar/attendanceMintenance/viewArOvertimeManagent_new", modelMap);
	}
	
	/**
	 * 加班管理测试版(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArOvertimeManagent_fast")
	public ModelAndView viewArOvertimeManagentFastList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List nullOTTSTOAffirmList = new ArrayList();
		String type = request.getParameter("type");
		if(type !=null && !"".equals(type)){
			nullOTTSTOAffirmList = infoApplyLeaveSer.viewNullBatchOTTSTOAffirmInfoList(request);
		}else{
			nullOTTSTOAffirmList = infoApplyLeaveSer.getNullBatchOTTSTOAffirmInfoList(request);
		}
		modelMap.put("nullOTTSTOAffirmList", nullOTTSTOAffirmList);
		modelMap.put("nullOTTSTOAffirmListCnt",nullOTTSTOAffirmList==null ? 0: nullOTTSTOAffirmList.size());
		modelMap.put("OT_TYPE_CODE" , JsonUtil.writeInternal(empInfoSer.getCodeList("31", request)));
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, 0);
				//c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				//c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("END_DATE",last);
			}
		}
		//时间下拉菜单
		GregorianCalendar today = new GregorianCalendar();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String timeStr = "[{";
		String spacing = "10";
		while (today.before(tomorrow) || today.equals(tomorrow)) {

			String time = sdf.format(today.getTime());
			timeStr = timeStr + "'CODENO':'"+time+"','CODENAME':'"+time+"'},{";
			today.add(Calendar.MINUTE, Integer.parseInt(spacing));
		}
		timeStr = timeStr +"}]";
		modelMap.put("TIME_STR", timeStr);
		modelMap.put("type", type);
		return new ModelAndView("/ar/attendanceMintenance/viewArOvertimeManagent_fast", modelMap);
	}
	
	/**
	 * 加班管理测试版(可添加删除审批者)(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyOtManagentByAnyApproverList")
	public ModelAndView viewApplyOtManagentByAnyApproverList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List nullOTTSTOAffirmList = new ArrayList();
		String type = request.getParameter("type");
		if(type !=null && !"".equals(type)){
			nullOTTSTOAffirmList = infoApplyLeaveSer.viewNullBatchOTTSTOAffirmInfoList(request);
		}else{
			nullOTTSTOAffirmList = infoApplyLeaveSer.getNullBatchOTTSTOAffirmInfoList(request);
		}
		modelMap.put("nullOTTSTOAffirmList", nullOTTSTOAffirmList);
		modelMap.put("nullOTTSTOAffirmListCnt",nullOTTSTOAffirmList==null ? 0: nullOTTSTOAffirmList.size());
		modelMap.put("OT_TYPE_CODE" , JsonUtil.writeInternal(empInfoSer.getCodeList("31", request)));
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); 
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
		//时间下拉菜单
		GregorianCalendar today = new GregorianCalendar();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String timeStr = "[{";
		String spacing = "15";
		while (today.before(tomorrow) || today.equals(tomorrow)) {
			
			String time = sdf.format(today.getTime());
			timeStr = timeStr + "'CODENO':'"+time+"','CODENAME':'"+time+"'},{";
			today.add(Calendar.MINUTE, Integer.parseInt(spacing));
			
		}
		timeStr = timeStr +"}]";
		modelMap.put("TIME_STR", timeStr);
		
		return new ModelAndView("/ar/attendanceMintenance/viewApplyOtManagentByAnyApproverList", modelMap);
	}
	
	@RequestMapping(value = "/getAddOtApplyInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map getAddOtApplyInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List nullOTTSTOAffirmList = new ArrayList();
		try{
			nullOTTSTOAffirmList = infoApplyLeaveSer.viewNullBatchOTTSTOAffirmInfoList(request);
			if (nullOTTSTOAffirmList != null) {
				map.put("statusCode", "200");
				map.put("nullOTTSTOAffirmList", nullOTTSTOAffirmList);
			}else{
				map.put("statusCode", "300");
			}
		} catch (CommonException e) {
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("statusCode", "300");
		}
		
		return map;
	}
	
	/**
	 * 倒休管理(view adjust holiday mangement)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArAdjustHolidayManagent") 
	public ModelAndView viewArAdjustHolidayManagentList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List AdjustHolidayList = new ArrayList();
		String type = request.getParameter("type");
		if(type !=null && !"".equals(type)){
			AdjustHolidayList = arDetailSer.viewArAdjustHolidayManagentNull(request);
		}else{
			AdjustHolidayList = arDetailSer.viewArAdjustHolidayManagent(request);
		}
		modelMap.put("AdjustHolidayList", AdjustHolidayList);
		/*LinkedHashMap linkMap2= (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
		modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
		modelMap.put("personInfo",linkMap2);*/
		modelMap.put("OT_TYPE_CODE" , JsonUtil.writeInternal(empInfoSer.getCodeList("31", request)));
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("FROM_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("TO_DATE",last);
			}
		}
		//时间下拉菜单
		GregorianCalendar today = new GregorianCalendar();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String timeStr = "[{";
		String spacing = "30";
		while (today.before(tomorrow) || today.equals(tomorrow)) {
			
			String time = sdf.format(today.getTime());
			timeStr = timeStr + "'CODENO':'"+time+"','CODENAME':'"+time+"'},{";
			today.add(Calendar.MINUTE, Integer.parseInt(spacing));
			
		}
		timeStr = timeStr +"}]";
		modelMap.put("TIME_STR", timeStr);
		return new ModelAndView("/ar/attendanceMintenance/viewArAdjustHolidayManagent", modelMap);
	}
	
	/**
	 * SST加班管理最新(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArOvertimeSSTManagent")
	public ModelAndView viewArOvertimeSSTManagent(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		//进行删除空数据
		String deleteYN = request.getParameter("deleteYN");
		if ("Y".equals(deleteYN)) {
			infoApplyLeaveSer.deleteAllDataForAdd(request);
		}
		
		//全部反应用
		modelMap.put("APP_OT_DATE",request.getParameter("APP_OT_DATE"));
		modelMap.put("fromTime1",request.getParameter("fromTime1"));
		modelMap.put("toTime1",request.getParameter("toTime1"));
		modelMap.put("reason",request.getParameter("reason"));
		modelMap.put("otherReason",request.getParameter("otherReason"));
		modelMap.put("FILLAFFIRMFLAG",request.getParameter("FILLAFFIRMFLAG"));
		modelMap.put("work_time_shift1",request.getParameter("work_time_shift1"));
		
		List shiftList = shiftSer.getShiftList1(request);
		List itemList = itemsSer.getItemParamList2(request);
		List workTimeLsit = shiftSer.getWorkTimeLsit(request);
		List dateTypeLsit = shiftSer.getDateTypeLsit(request);
		modelMap.put("workTimeList", workTimeLsit) ;
		modelMap.put("dateTypeLsit", dateTypeLsit) ;
		modelMap.put("itemList", itemList) ;
		modelMap.put("shiftList", shiftList) ;
		
		//班次显示，根据日期类型
		List workTimeLsit1 = shiftSer.getWorkTimeLsit1(request);
		modelMap.put("workTimeList1", workTimeLsit1) ;
		List workTimeLsit2 = shiftSer.getWorkTimeLsit2(request);
		modelMap.put("workTimeList2", workTimeLsit2) ;
		if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
			Date d=new Date();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
			modelMap.put("FROM_DATE", df.format(d));
			modelMap.put("TO_DATE", df.format(d));
		}else {
			modelMap.put("FROM_DATE", request.getParameter("seach_FROM_DATE"));
			modelMap.put("TO_DATE", request.getParameter("seach_TO_DATE"));
		}
		
		String   DEPTNO  =request.getParameter("seach_DEPTNO");
		//modelMap.put("empName",request.getParameter("dwz.person.empName"));
		modelMap.put("empInfo",request.getParameter("empInfo"));
		modelMap.put("AFFIRM_FLAG",request.getParameter("seach_AFFIRM_FLAG"));
		modelMap.put("GROUP_ID",request.getParameter("seach_GROUP_ID"));
		modelMap.put("SHIFT_NO",request.getParameter("seach_SHIFT_NO"));
		modelMap.put("KEY",request.getParameter("seach_KEY"));
		modelMap.put("CONFIRM_FLAG",request.getParameter("seach_CONFIRM_FLAG"));
		modelMap.put("EMP_TYPE_CODE",request.getParameter("seach_EMP_TYPE_CODE"));
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("admin", admin);
		modelMap.put("DEPTNO", DEPTNO);
		Map paramMap=new LinkedHashMap();
		//审批code
		paramMap.put("PARENT_CODE_NO","14014304");
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		
		//原因code
		Map paramMap2=new LinkedHashMap();
		paramMap2.put("PARENT_CODE_NO","14014313");
		paramMap2.put("interLanguage",admin.getLanguage());
		paramMap2.put("CPNY_ID",admin.getCpnyId());
		List codeList2 = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap2, -1, -1) ;
		//班组code
		Map paramMap4=new LinkedHashMap();
		paramMap4.put("PARENT_CODE_NO","400223");
		paramMap4.put("interLanguage",admin.getLanguage());
		paramMap4.put("CPNY_ID",admin.getCpnyId());
		List codeList4 = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap4, -1, -1) ;

		modelMap.put("codeList", codeList);
		modelMap.put("codeList2", codeList2);
		modelMap.put("codeList4", codeList4);
		//NULL数据源
		String nullYN = request.getParameter("nullYN");
		
		String firstFlag = request.getParameter("firstFlag");
		if(firstFlag != null && !"".equals(firstFlag)&&!"Y".equals(nullYN)){
			modelMap.put("oTAffirmList", arDetailSer.getOtAffirmInfoListBatchSST(request));
			//modelMap.put(UiUtil.TOTAL_COUNT_NAME, infoApplyLeaveSer.getBatchLeaveAffirmInfoListCnt(request));
		}else {
			List nullOTSSTAffirmList = infoApplyLeaveSer.getNullBatchOTSSTAffirmInfoList(request);
			modelMap.put("nullOTSSTAffirmList", nullOTSSTAffirmList);
		}
		return new ModelAndView("/ar/attendanceMintenance/viewArOvertimeSSTManagent", modelMap);
	}
	
	
	/**
	 * SST加班管理最新(view personal information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewArOvertimeNULLSSTManagent")
	public ModelAndView viewArOvertimeNULLSSTManagent(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		//进行删除空数据
		String deleteYN = request.getParameter("deleteYN");
		if ("Y".equals(deleteYN)) {
			infoApplyLeaveSer.deleteAllDataForAdd(request);
		}
		
		//全部反应用
		modelMap.put("APP_OT_DATE",request.getParameter("APP_OT_DATE"));
		modelMap.put("fromTime1",request.getParameter("fromTime1"));
		modelMap.put("toTime1",request.getParameter("toTime1"));
		modelMap.put("reason",request.getParameter("reason"));
		modelMap.put("otherReason",request.getParameter("otherReason"));
		modelMap.put("FILLAFFIRMFLAG",request.getParameter("FILLAFFIRMFLAG"));
		modelMap.put("work_time_shift1",request.getParameter("work_time_shift1"));
		
		List shiftList = shiftSer.getShiftList1(request);
		List itemList = itemsSer.getItemParamList2(request);
		List workTimeLsit = shiftSer.getWorkTimeLsit(request);
		List dateTypeLsit = shiftSer.getDateTypeLsit(request);
		modelMap.put("workTimeList", workTimeLsit) ;
		modelMap.put("dateTypeLsit", dateTypeLsit) ;
		modelMap.put("itemList", itemList) ;
		modelMap.put("shiftList", shiftList) ;
		
		//班次显示，根据日期类型
		List workTimeLsit1 = shiftSer.getWorkTimeLsit1(request);
		modelMap.put("workTimeList1", workTimeLsit1) ;
		List workTimeLsit2 = shiftSer.getWorkTimeLsit2(request);
		modelMap.put("workTimeList2", workTimeLsit2) ;
		if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
			Date d=new Date();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
			modelMap.put("FROM_DATE", df.format(d));
			modelMap.put("TO_DATE", df.format(d));
		}else {
			modelMap.put("FROM_DATE", request.getParameter("seach_FROM_DATE"));
			modelMap.put("TO_DATE", request.getParameter("seach_TO_DATE"));
		}
		
		String   DEPTNO  =request.getParameter("seach_DEPTNO");
		//modelMap.put("empName",request.getParameter("dwz.person.empName"));
		modelMap.put("empInfo",request.getParameter("empInfo"));
		modelMap.put("AFFIRM_FLAG",request.getParameter("seach_AFFIRM_FLAG"));
		modelMap.put("GROUP_ID",request.getParameter("seach_GROUP_ID"));
		modelMap.put("SHIFT_NO",request.getParameter("seach_SHIFT_NO"));
		modelMap.put("KEY",request.getParameter("seach_KEY"));
		modelMap.put("CONFIRM_FLAG",request.getParameter("seach_CONFIRM_FLAG"));
		modelMap.put("EMP_TYPE_CODE",request.getParameter("seach_EMP_TYPE_CODE"));
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("admin", admin);
		modelMap.put("DEPTNO", DEPTNO);
		Map paramMap=new LinkedHashMap();
		//审批code
		paramMap.put("PARENT_CODE_NO","14014304");
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;
		
		//原因code
		Map paramMap2=new LinkedHashMap();
		paramMap2.put("PARENT_CODE_NO","14014313");
		paramMap2.put("interLanguage",admin.getLanguage());
		paramMap2.put("CPNY_ID",admin.getCpnyId());
		List codeList2 = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap2, -1, -1) ;
		//班组code
		Map paramMap4=new LinkedHashMap();
		paramMap4.put("PARENT_CODE_NO","400223");
		paramMap4.put("interLanguage",admin.getLanguage());
		paramMap4.put("CPNY_ID",admin.getCpnyId());
		List codeList4 = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap4, -1, -1) ;

		modelMap.put("codeList", codeList);
		modelMap.put("codeList2", codeList2);
		modelMap.put("codeList4", codeList4);
		String firstFlag = request.getParameter("firstFlag");
		if(firstFlag != null && !"".equals(firstFlag)){
		   List nullOTSSTAffirmList = infoApplyLeaveSer.getNullBatchOTSSTAffirmInfoList(request);
			modelMap.put("nullOTSSTAffirmList", nullOTSSTAffirmList);
		}else {
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		return new ModelAndView("/ar/attendanceMintenance/viewArOvertimeSSTManagent", modelMap);
	}
	
	/**
	 * 加班管理页面(刘孟--废弃)(detail Calculate)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArOvertime")
	public ModelAndView viewArOvertimeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

/*		// 本月的第一天
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");

		// 本月的最后一天
		Calendar calendar2 = new GregorianCalendar();
		calendar2.set(Calendar.DATE, 1);
		calendar2.roll(Calendar.DATE, -1);
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat("yyyy-MM-dd");

		modelMap.put("sDate", simpleFormate.format(calendar.getTime()));
        modelMap.put("eDate", simpleFormate2.format(calendar2.getTime())); //结束时间为月末最后一天
        
*/
		
		/* Date d=new Date();   
		 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
	  //   modelMap.put("sDate", df.format(new Date(d.getTime() - 1 * 24 * 60 * 60 * 1000)));
	  //   modelMap.put("eDate", df.format(new Date(d.getTime() - 1 * 24 * 60 * 60 * 1000))); //开始时间和结束时间为同一天(昨天的日期)
		 modelMap.put("sDate", df.format(new Date(d.getTime() - 24*60*60*1000)));
	     modelMap.put("eDate", df.format(new Date(d.getTime() - 24*60*60*1000)));  */
		
		
	     String seach_FIRST_FLAG = request.getParameter("seach_FIRST_FLAG");
	     if(seach_FIRST_FLAG != null && !"".equals(seach_FIRST_FLAG)){
	 		List getArDetailList = this.arDetailSer.getArDetailList(request,
					modelMap);
			int getArDetailListCnt = this.arDetailSer.getArDetailListCnt(request,
					modelMap);
			modelMap.put("arDetailList", getArDetailList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, getArDetailListCnt);
			List shiftList = this.shiftSer.getShiftList1(request); 
			modelMap.put("shiftList", shiftList) ;
	    }
	     
		List getItemList = arDetailSer.getItemList(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String supervisorId = admin.getAdminID();
		//此考勤员的考勤员权限是否有效
		//String modifyYn=arDetailSer.getModifyYnBySupervisorId(supervisorId);
		// modelMap.put("sDate", request.getParameter("sDate") != null ? request
		// .getParameter("sDate") : arDetailSer.getStartDateStr());
		// modelMap.put("eDate", request.getParameter("eDate") != null ? request
		// .getParameter("eDate") : arDetailSer.getEndDateStr());
		modelMap.put("getItemList", getItemList);
		modelMap.put("supervisorId", supervisorId);
		//modelMap.put("MODIFY_YN", modifyYn);
		modelMap.put("ItemList", JsonUtil.writeInternal(getItemList));
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "14013744"));
		return new ModelAndView("/ar/attendanceMintenance/viewArOvertime",
				modelMap);
	}
	/**
	 * 
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/countArDetailLengthInfo")
	@ResponseBody
	public Map countArDetailLengthInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String result =  this.arDetailSer.countArDetailLengthInfo(request); 
			map.put("statusCode", "200");
			map.put("message",result );// 处理成功
			map.put("navTabId", "ar0104");
			map.put("callbackType", "closeCurrent");
		 
		map.put("result", result);

		return map;
	}
	
	/**
	 * 加班管理的批量添加(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delLOvertimeApplyInBatch")
	@ResponseBody
	public Map<String, Object> delLOvertimeApplyInBatch(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String msg = "删除";
		String op_flag = request.getParameter("OP_FLAG");
		if("1".equals(op_flag)){
			msg = "提交";
		}
		
		try {                   
			result = arDetailSer.delOvertimeApplyInBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess3403");
				map.put("message", msg + "成功！");
				map.put("statusCode", "200");
				map.put("formId", "viewArOvertimeManagent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
			map.put("formId", "viewArOvertimeManagent");
		} catch (Exception e) {
			map.put("message", msg + "失败！");//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
			map.put("formId", "viewArOvertimeManagent");
		}
		map.put("result", result);
		return map;
	}	
	
	/**
	 * 加班管理 保存操作 
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveArOvertimeManagent")
	@ResponseBody
	public Map<String, Object> saveArOvertimeManagent(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String msg = "保存";
//		String op_flag = request.getParameter("OP_FLAG");
//		if("1".equals(op_flag)){
//			msg = "提交";
//		}
//		
		try {                   
			result = arDetailSer.saveArOvertimeManagent(request);
			if (result == 1) {
				map.put("navTabId", "ar0701_3");
				map.put("message", msg + "成功！");
				map.put("statusCode", "200");
				map.put("formId", "viewArOvertimeManagent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
			map.put("formId", "viewArOvertimeManagent");
		} catch (Exception e) {
			map.put("message", msg + "失败！");//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
			map.put("formId", "viewArOvertimeManagent");
		}
		map.put("result", result);
		return map;
	}	
	
	/**
	 * 倒休管理的批量申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delOtAdjustApplyAffirmForm")
	@ResponseBody
	public Map<String, Object> delOtAdjustApplyAffirmForm(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String msg = "删除";
		String op_flag = request.getParameter("OP_FLAG");
		if("1".equals(op_flag)){
			msg = "提交";
		}
		
		try {                   
			result = arDetailSer.delOtAdjustApplyAffirmForm(request);
			if (result == 1) {
				map.put("navTabId", "ess3403");
				map.put("message", msg + "成功！");
				map.put("statusCode", "200");
				map.put("formId", "viewArAdjustHolidayManagent");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", msg + "失败！");//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}	
	
	/**
	 * SST加班管理的批量添加(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delLOvertimeApplyInBatchSST")
	@ResponseBody
	public Map<String, Object> delLOvertimeApplyInBatchSST(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String msg = "删除";
		String op_flag = request.getParameter("OP_FLAG");
		if("1".equals(op_flag)){
			msg = "提交";
		}
		
		try {
			result = arDetailSer.delLOvertimeApplyInBatchSST(request);
			if(!"2".equals(op_flag)){
				if (result == 1) {
					map.put("navTabId", "ess3403");
					map.put("message", msg + "成功！");
					map.put("statusCode", "200");
					map.put("formId", "viewArOvertimeSSTManagent");
				}
			}else {
				if (result == 1) {
					map.put("navTabId", "ess3403");
					map.put("statusCode", "200");
					map.put("callbackType", "forward");
					map.put("forwardUrl", "/ar/attendanceMintenance/viewArOvertimeNULLSSTManagent");
				}
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
			map.put("formId", "viewArOvertimeSSTManagent");
		} catch (Exception e) {
			map.put("message", msg + "失败！");//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
			map.put("formId", "viewArOvertimeManagent");
		}
		map.put("result", result);
		return map;
	}	
	/**
	 * 加班搜索(ar)(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSearchApplyOtInfoList")
	public ModelAndView viewSearchApplyOtInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List applyOtInfoList = (List) arDetailSer.getSearchApplyOtInfoList(request);
		List shiftList = shiftSer.getShiftList1(request);
		modelMap.put("shiftList", shiftList) ;
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				/*c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("END_DATE",last);*/
				modelMap.put("START_DATE", DateUtil.getSysdateStr("dd/MM/yyyy"));
				modelMap.put("END_DATE", DateUtil.getSysdateStr("dd/MM/yyyy"));
			}
		}
		modelMap.put("SHIFT_NAME", request.getParameter("seach_SHIFT_NO"));
		modelMap.put("otCoordList", applyOtInfoList);
		modelMap.put("otCoordListCnt",applyOtInfoList==null ? 0: applyOtInfoList.size());
		return new ModelAndView("/ar/attendanceMintenance/viewSearchApplyOtInfoList", modelMap);
	}   
	/**
	 * 加班搜索(ar)(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewMyhomeCarInfoList")
	public ModelAndView viewMyhomeCarInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		List shiftList = shiftSer.getShiftList1(request);
		List itemList = itemsSer.getItemParamList2(request);
		modelMap.put("itemList", itemList) ;
		modelMap.put("shiftList", shiftList) ;
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("length",request.getParameter("seach_length"));
		Date d=new Date();   
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		modelMap.put("FROM_DATE", df.format(d));
		modelMap.put("TO_DATE", df.format(d)); 
		modelMap.put("DEPTNO", request.getParameter("seach_DEPTNO")); 
		modelMap.put("CONFIRM_FLAG", request.getParameter("seach_CONFIRM_FLAG")); 
		modelMap.put("empName", request.getParameter("dwz.person.empName")); 
		modelMap.put("empInfo", request.getParameter("dwz.person.empInfo")); 
		modelMap.put("GROUP_NO", request.getParameter("seach_GROUP_NO")); 
		modelMap.put("SHIFT_NO", request.getParameter("seach_SHIFT_NO")); 
		modelMap.put("ITEM_NO", request.getParameter("seach_ITEM_NO")); 
		modelMap.put("AFFIRM_FLAG", request.getParameter("seach_AFFIRM_FLAG")); 
		modelMap.put("CONFIRM_FLAG", request.getParameter("seach_CONFIRM_FLAG")); 
		modelMap.put("EMP_TYPE_CODE", request.getParameter("seach_EMP_TYPE_CODE")); 
		modelMap.put("EmpOffice", request.getParameter("seach_EmpOffice")); 
		String firstFlag= request.getParameter("firstFlag");
		modelMap.put("ADJSTYN", (request.getParameter("ADJSTYN") != null&&!"".equals(request.getParameter("ADJSTYN")) ?  request.getParameter("ADJSTYN") : 0));
		if (firstFlag != null&& !"".equals(firstFlag)) {
			modelMap.put("otCarList", arDetailSer.getMyhomeCarInfoList(request));
			LinkedHashMap linkMap2= (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo",linkMap2);
		}
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "218096"));
		return new ModelAndView("/ar/attendanceMintenance/viewMyhomeCarInfoList", modelMap);
	}   
	
	
	/**
	 * 倒休搜索(ar)(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSearchAdjustHolidayInfoList")
	public ModelAndView viewSearchAdjustHolidayInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("adjustCoordList", arDetailSer.getSearchApplyAdjustInfoList(request));
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_FROM_DATE")==""||request.getParameter("seach_FROM_DATE")==null )&& (request.getParameter("seach_TO_DATE")==""||request.getParameter("seach_TO_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("FROM_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("TO_DATE",last);
			}
		}
		return new ModelAndView("/ar/attendanceMintenance/viewSearchAdjustHolidayInfoList", modelMap);
	}   
	
	/**
	 * 考勤中的考勤管理(/view Apply Leave Batch Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyAttenanceManagentInfoList")
	public ModelAndView viewApplyAttenanceManagentInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List itemList = itemsSer.getItemParamList2(request);
		modelMap.put("itemList", itemList) ;
		modelMap.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		modelMap.put("defaultCpny", admin.getCpnyId());
		String PERSON_ID = request.getParameter("dwz.person.personId");
		String empName = request.getParameter("dwz.person.empName");
		String empInfo = request.getParameter("dwz.person.empInfo");
		request.setAttribute("PERSON_ID", PERSON_ID);
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		modelMap.put("personInfo", linkMap);
		Date d=new Date();   
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		modelMap.put("applyBatchdate", df.format(new Date(d.getTime())));
		modelMap.put("FROM_DATE", df.format(new Date(d.getTime())));
		modelMap.put("TO_DATE", df.format(new Date(d.getTime())));
		
		String   AFFIRM_FLAG  =request.getParameter("seach_AFFIRM_FLAG");
		String   GROUP_ID  =request.getParameter("seach_GROUP_ID");
		String   EMP_TYPE_CODE  =request.getParameter("seach_EMP_TYPE_CODE");
		String   EmpOffice  =request.getParameter("seach_EmpOffice");
		String   DEPTNO  =request.getParameter("seach_DEPTNO");
		String   ITEM_NO  =request.getParameter("seach_ITEM_NO");
		modelMap.put("PERSON_ID", PERSON_ID);
		modelMap.put("AFFIRM_FLAG", AFFIRM_FLAG);
		modelMap.put("GROUP_ID", GROUP_ID);
		modelMap.put("DEPTNO", DEPTNO);
		modelMap.put("EMP_TYPE_CODE", EMP_TYPE_CODE);
		modelMap.put("EmpOffice", EmpOffice);
		modelMap.put("empName", empName);
		modelMap.put("empInfo", empInfo);
		modelMap.put("ITEM_NO", ITEM_NO);
		
		modelMap.put("authority", authorityUtil.isArUser(admin.getPersonId()));
		modelMap.put("admin", admin);
		
		//进行删除空数据
		String deleteYN = request.getParameter("deleteYN");
		if ("Y".equals(deleteYN)) {
			infoApplyLeaveSer.deleteAllDataForAdd(request);
		}
		
		//存放全部反应的内容
		modelMap.put("FROM_DATE1", request.getParameter("FROM_DATE1"));
		modelMap.put("TO_DATE1", request.getParameter("TO_DATE1"));
		modelMap.put("fromTime1", request.getParameter("fromTime1"));
		modelMap.put("toTime1", request.getParameter("toTime1"));
		modelMap.put("ITEM_NO1", request.getParameter("ITEM_NO1"));
		modelMap.put("otherReason1", request.getParameter("otherReason1"));
		modelMap.put("FILLAFFIRMFLAG1", request.getParameter("FILLAFFIRMFLAG1"));
		
		Map paramMap=new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO","14014304");
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		List codeList = basicMaintenanceDao.getParamCodeListByCpnyID(paramMap, -1, -1) ;

		modelMap.put("codeList", codeList);
	
		/*for (Iterator iterator = leaveAffirmList.iterator(); iterator.hasNext();) {
			LinkedHashMap arDetial =  (LinkedHashMap) iterator.next();
		}*/
		String firstFlag = request.getParameter("firstFlag");
		String nullYN = request.getParameter("nullYN");
		if(firstFlag != null && !"".equals(firstFlag)&&!"Y".equals(nullYN)){
			List leaveAffirmList = arDetailSer.getApplyAttenanceManagentInfoList(request);
			List leaveAffirmListForMoreDay = arDetailSer.getBatchLeaveAffirmMoreDayInfoList(request);
			leaveAffirmList.addAll(leaveAffirmListForMoreDay);
			modelMap.put("leaveAffirmList",leaveAffirmList);
			LinkedHashMap linkMap2= (LinkedHashMap)empInfoSer.viewHrPersonalInfo(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo",linkMap2);
			//modelMap.put("leaveAffirmListForMoreDay",leaveAffirmListForMoreDay);
		}else if(firstFlag != null && !"".equals(firstFlag)&&"Y".equals(nullYN)) {
			List nullLeaveAffirmList = infoApplyLeaveSer.getNullBatchLeaveAffirmInfoList(request);
			modelMap.put("nullLeaveAffirmList",nullLeaveAffirmList );
		}else {
			List nullLeaveAffirmList = infoApplyLeaveSer.getNullBatchLeaveAffirmInfoList(request);
			modelMap.put("nullLeaveAffirmList",nullLeaveAffirmList );
		}
		return new ModelAndView("/ar/attendanceMintenance/viewApplyAttenanceManagentInfoList", modelMap);
	}
	/**
	 * 考勤中的考勤管理(/view Apply Leave Batch Info List) New
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyAttenanceManagentInfoList_new")
	public ModelAndView viewApplyAttenanceManagentInfoNewList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String type = request.getParameter("type");
		List nullLeaveAffirmList = new ArrayList();
		if(type !=null && !"".equals(type)){
			 nullLeaveAffirmList = infoApplyLeaveSer.viewAddAttendanceApplyInfoForBatch(request);
		}else{
			 nullLeaveAffirmList = infoApplyLeaveSer.getNullBatchLeaveAffirmInfoList(request);
		}
		modelMap.put("nullLeaveAffirmList",nullLeaveAffirmList );
		modelMap.put("nullLeaveAffirmListCnt",nullLeaveAffirmList==null ? 0: nullLeaveAffirmList.size());
		/*if (nullLeaveAffirmList.size() > 0) {
			for (int i = 0; i < nullLeaveAffirmList.size(); i++) {
				if (nullLeaveAffirmList.get(i) != null && !nullLeaveAffirmList.get(i).equals("")) {
						Map applyMap = (Map) nullLeaveAffirmList.get(i);// 取出部门ID
						applyMap.put("fileList", infoApplyLeaveSer.getFileList(applyMap));
				}
			}
		}*/
		//modelMap.put("LEAVE_TYPE_CODE" , JsonUtil.writeInternal(essEmpInfoSer.getCodeList("21", request)));//有问题
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				/*c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("END_DATE",last);*/
				modelMap.put("START_DATE", DateUtil.getSysdateStr("dd.MM.yyyy"));
				modelMap.put("END_DATE", DateUtil.getSysdateStr("dd.MM.yyyy"));
			}
		}
		//时间下拉菜单
		GregorianCalendar today = new GregorianCalendar();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String timeStr = "[{";
		String spacing = "10";
		while (today.before(tomorrow) || today.equals(tomorrow)) {

			String time = sdf.format(today.getTime());
			timeStr = timeStr + "'CODENO':'"+time+"','CODENAME':'"+time+"'},{";
			today.add(Calendar.MINUTE, Integer.parseInt(spacing));
		}
		timeStr = timeStr +"}]";
		modelMap.put("TIME_STR", timeStr);
		modelMap.put("type", type);

		return new ModelAndView("/ar/attendanceMintenance/viewApplyAttenanceManagentInfoList_new", modelMap);
	}
	
	/**
	 * 考勤中的考勤管理(可以添加删除审批者)(/view Apply Leave Batch Info List) New
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewApplyAttManagentByAnyApproverList")
	public ModelAndView viewApplyAttManagentByAnyApproverList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String type = request.getParameter("type");
		List nullLeaveAffirmList = new ArrayList();
		if(type !=null && !"".equals(type)){
			 nullLeaveAffirmList = infoApplyLeaveSer.viewAddAttendanceApplyInfoForBatch(request);
		}else{
			 nullLeaveAffirmList = infoApplyLeaveSer.getNullBatchLeaveAffirmInfoList(request);
		}
		modelMap.put("nullLeaveAffirmList",nullLeaveAffirmList );
		modelMap.put("nullLeaveAffirmListCnt",nullLeaveAffirmList==null ? 0: nullLeaveAffirmList.size());
		if (nullLeaveAffirmList.size() > 0) {
			for (int i = 0; i < nullLeaveAffirmList.size(); i++) {
				if (nullLeaveAffirmList.get(i) != null && !nullLeaveAffirmList.get(i).equals("")) {
						Map applyMap = (Map) nullLeaveAffirmList.get(i);// 取出部门ID
						applyMap.put("fileList", infoApplyLeaveSer.getFileList(applyMap));
				}
			}
		}
		//modelMap.put("LEAVE_TYPE_CODE" , JsonUtil.writeInternal(essEmpInfoSer.getCodeList("21", request)));//有问题
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); 
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
		//时间下拉菜单
		GregorianCalendar today = new GregorianCalendar();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		GregorianCalendar tomorrow = new GregorianCalendar();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String timeStr = "[{";
		String spacing = "30";
		while (today.before(tomorrow) || today.equals(tomorrow)) {
			
			String time = sdf.format(today.getTime());
			timeStr = timeStr + "'CODENO':'"+time+"','CODENAME':'"+time+"'},{";
			today.add(Calendar.MINUTE, Integer.parseInt(spacing));
			
		}
		timeStr = timeStr +"}]";
		modelMap.put("TIME_STR", timeStr);
		
		return new ModelAndView("/ar/attendanceMintenance/viewApplyAttManagentByAnyApproverList", modelMap);
	}

	@RequestMapping(value = "/getAddAttendanceApplyInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map getAddAttendanceApplyInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List nullLeaveAffirmList = new ArrayList();
		try{
			nullLeaveAffirmList = infoApplyLeaveSer.viewAddAttendanceApplyInfoForBatch(request);
			if (nullLeaveAffirmList != null) {
				map.put("statusCode", "200");
				map.put("nullLeaveAffirmList", nullLeaveAffirmList);
			}else{
				map.put("statusCode", "300");
			}
		} catch (CommonException e) {
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("statusCode", "300");
		}
		
		return map;
	}
	
	/**
	 * 考勤管理(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delAttendanceApplyInBatchForBatch")
	@ResponseBody
	public Map<String, Object> delLeaveApplyInBatchForBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String op_flag = request.getParameter("OP_FLAG");
		String msg = "add";
		if("0".equals(op_flag)){
		    msg = "delete";
		}
		if("1".equals(op_flag)){
			msg = "submit";
		}
		try {
			result = arDetailSer.delLeaveApplyInBatchForBatch(request);
			if (result == 1) {
				map.put("navTabId", "ess0246");
				map.put("message", msg + " success！");//"批量删除休假申请决裁成功!"
				map.put("statusCode", "200");
				map.put("formId", "viewApplyAttenanceBatchInfoList");
			}
		} catch (CommonException e) {
			map.put("message", e.getMessage());
			map.put("statusCode", "300");
		} catch (Exception e) {
			map.put("message", msg + " fail！");//"批量删除休假申请决裁出错,请重新操作!"
			map.put("statusCode", "300");
		}
		map.put("result", result);
		return map;
	}
	/**
	 * 考勤里-考勤搜索(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAttendanceManagentForSerchInfoList")
	public ModelAndView viewAttendanceManagentForSerchInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List shiftList = shiftSer.getShiftList1(request);
		modelMap.put("shiftList", shiftList) ;
		List leaveCoordList = (List) arDetailSer.getLeaveManagentForSearchInfoList(request);
		String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				/*c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				modelMap.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				modelMap.put("END_DATE",last);*/
				modelMap.put("START_DATE", DateUtil.getSysdateStr("dd/MM/yyyy"));
				modelMap.put("END_DATE", DateUtil.getSysdateStr("dd/MM/yyyy"));
			}
		}
		modelMap.put("SHIFT_NAME", request.getParameter("seach_SHIFT_NO"));
		List itemList = itemsSer.getItemParamList2(request);
		modelMap.put("itemList", itemList) ;
		modelMap.put("leaveCoordList", leaveCoordList);
		modelMap.put("leaveCoordListCnt",leaveCoordList==null ? 0: leaveCoordList.size());
		return new ModelAndView("/ar/attendanceMintenance/viewAttendanceManagentForSerchInfoList", modelMap);
	}           

	
	/**
	 * 综合工时人员加班
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewSearchOtInfo")
	public ModelAndView viewSearchOtInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String firstFlag= request.getParameter("firstFlag");
		if (firstFlag != null&& !"".equals(firstFlag)) {
			modelMap.put("FROM_DATE", DateUtil.getSysdateStr("yyyy/MM/dd"));
			modelMap.put("TO_DATE", DateUtil.getSysdateStr("yyyy/MM/dd"));
		}else{ 
			List otInfoList = arDetailSer.viewSearchOtInfo(request);
			modelMap.put("otInfoList", otInfoList);
			modelMap.put("otInfoListSize", otInfoList == null ? 0 : otInfoList.size());
			LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.viewHrPersonalInfo2(request);
			modelMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			modelMap.put("personInfo",linkMap);
		}
		return new ModelAndView("/ar/attendanceMintenance/viewSearchOtInfo", modelMap);
	}
	//查询一般讲师
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAdjustRecords")
	public ModelAndView viewAdjustRecords(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List elist = arDetailSer.viewAdjustRecords(request);
		modelMap.put("adjustItemList", elist);
		modelMap.put("adjustItemListCount", elist != null ? elist.size() : 0);
		modelMap.put("id",request.getParameter("ID"));
		return new ModelAndView("/ar/attendanceMintenance/viewAdjustRecords", modelMap);
	}
	
	// 综合简介查询弹出页面
	@RequestMapping(value = "/viewEmpInfoListTanchu")
	public ModelAndView viewEmpInfoTanchuList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		String searchChange = StringUtil.checkNull(request
				.getParameter("searchChange"));
		if (firstFlag != null && !"".equals(firstFlag)) {
			// 员工信息查询
			List elist = new ArrayList();
			elist = empInfoSer.getEmpInfoListAr(request);
			modelMap.put("empInfo", elist);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, empInfoSer
					.getEmpInfoListArCnt(request));
		} else {
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		
		//个人支付工资明细
		if("viewPaResultList".equals(searchChange)){
			modelMap.put("PAY_DATE_PRO", StringUtil.checkNull(request
					.getParameter("PAY_DATE_PRO")));
			modelMap.put("PAY_DATE", StringUtil.checkNull(request
					.getParameter("PAY_DATE")));
			modelMap.put("SALARY_DISTIN", StringUtil.checkNull(request
					.getParameter("SALARY_DISTIN")));
			modelMap.put("SALARY_DISTIN_NO", StringUtil.checkNull(request
					.getParameter("SALARY_DISTIN_NO")));
		}
		//考勤搜搜
		if("viewAttendanceManagentForSerchInfoList".equals(searchChange)){
			modelMap.put("FROM_DATE", StringUtil.checkNull(request
					.getParameter("seach_FROM_DATE")));
			modelMap.put("TO_DATE", StringUtil.checkNull(request
					.getParameter("seach_TO_DATE")));
			modelMap.put("APPLY_CODE", StringUtil.checkNull(request
					.getParameter("APPLY_CODE")));
		}
		//考勤管理
		if("viewApplyAttenanceManagentInfoList".equals(searchChange)){
			modelMap.put("FROM_DATE", StringUtil.checkNull(request
					.getParameter("seach_FROM_DATE")));
			modelMap.put("TO_DATE", StringUtil.checkNull(request
					.getParameter("seach_TO_DATE")));
			modelMap.put("APPLY_CODE", StringUtil.checkNull(request
					.getParameter("APPLY_CODE")));
		}
		//倒休管理
		if("viewArAdjustHolidayManagent".equals(searchChange)){
			modelMap.put("FROM_DATE", StringUtil.checkNull(request
					.getParameter("seach_FROM_DATE")));
			modelMap.put("TO_DATE", StringUtil.checkNull(request
					.getParameter("seach_TO_DATE")));
		}
		//倒休搜索
		if("viewSearchAdjustHolidayInfoList".equals(searchChange)){
			modelMap.put("FROM_DATE", StringUtil.checkNull(request
					.getParameter("seach_FROM_DATE")));
			modelMap.put("TO_DATE", StringUtil.checkNull(request
					.getParameter("seach_TO_DATE")));
		}
		//加班管理
		if("viewArOvertimeManagent".equals(searchChange)){
			modelMap.put("FROM_DATE", StringUtil.checkNull(request
					.getParameter("seach_FROM_DATE")));
			modelMap.put("TO_DATE", StringUtil.checkNull(request
					.getParameter("seach_TO_DATE")));
		}
		//加班搜索
		if("viewSearchApplyOtInfoList".equals(searchChange)){
			modelMap.put("FROM_DATE", StringUtil.checkNull(request
					.getParameter("seach_FROM_DATE")));
			modelMap.put("TO_DATE", StringUtil.checkNull(request
					.getParameter("seach_TO_DATE")));
		}
		//考勤申请
		if("viewApplyAttenanceBatchInfoList".equals(searchChange)){
			modelMap.put("applyBatchdate", StringUtil.checkNull(request
					.getParameter("seach_applyBatchdate")));
		}
		//倒休申请
		if("viewAdjustLeaveTSTOBatchList".equals(searchChange)){
			modelMap.put("FROM_DATE", StringUtil.checkNull(request
					.getParameter("seach_FROM_DATE")));
			modelMap.put("TO_DATE", StringUtil.checkNull(request
					.getParameter("seach_TO_DATE")));
		}
		//加班查询
		if("viewCoordApplyOtInfoList".equals(searchChange)){
			modelMap.put("FROM_DATE", StringUtil.checkNull(request
					.getParameter("seach_FROM_DATE")));
			modelMap.put("TO_DATE", StringUtil.checkNull(request
					.getParameter("seach_TO_DATE")));
		}
		//倒休查询
		if("viewCoordApplyAdjustInfoList".equals(searchChange)){
			modelMap.put("FROM_DATE", StringUtil.checkNull(request
					.getParameter("seach_FROM_DATE")));
			modelMap.put("TO_DATE", StringUtil.checkNull(request
					.getParameter("seach_TO_DATE")));
		}
		//考勤查询
		if("viewCoordApplyAttendanceInfoList".equals(searchChange)){
			modelMap.put("FROM_DATE", StringUtil.checkNull(request
					.getParameter("seach_FROM_DATE")));
			modelMap.put("TO_DATE", StringUtil.checkNull(request
					.getParameter("seach_TO_DATE")));
		}
		//考勤查询
		if("viewPersonOverTimeLimtList".equals(searchChange)){
			modelMap.put("AR_MONTH", StringUtil.checkNull(request
					.getParameter("seach_AR_MONTH")));
		}
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		/*
		 * String TANCHUEMPOFFICE =
		 * request.getParameter("TANCHUEMPOFFICE")==null
		 * ?"15119":request.getParameter("TANCHUEMPOFFICE");
		 * modelMap.put("TANCHUEMPOFFICE", TANCHUEMPOFFICE);
		 */
		modelMap.put("TANCHUDEPTNO", request.getParameter("TANCHUDEPTNO"));
		modelMap.put("authority", authorityUtil
				.isSuperUser(admin.getPersonId()));
		modelMap.put("searchChange", searchChange);
		modelMap.put("defaultCpny",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));
		modelMap.put("KEY", StringUtil.checkNull(request
				.getParameter("seach_KEY")));
		modelMap.put("PERSON_ID", StringUtil.checkNull(request
				.getParameter("PERSON_ID")));
		// 信息搜索用到的参数
		modelMap.put("dataSearch", StringUtil.checkNull(request
				.getParameter("dataSearch")));
		// 分页
		modelMap.put("pageNum", request.getParameter("pageNum"));
		modelMap.put("numPerPage", request.getParameter("numPerPage"));
		return new ModelAndView("/ar/attendanceMintenance/viewEmpInfoListTanchu", modelMap);
	}
	
	/**
	 * 加班搜索(ar)(view Apply Leave Info List)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewOverTimeLimit")
	public ModelAndView viewOverTimeLimit(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List OverTimeLimitList = (List) empInfoSer.getEmpInfoList(request);
		String firstFlag = request.getParameter("firstFlag");
		modelMap.put("OverTimeLimitList", OverTimeLimitList);
		modelMap.put("OverTimeLimitListCnt",OverTimeLimitList==null ? 0: OverTimeLimitList.size());
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME, OverTimeLimitList==null ? 0: OverTimeLimitList.size());
		modelMap.put("POST_FAMILY", StringUtil.checkNull(request.getParameter("POST_FAMILY")));
		modelMap.put("seach_OT_LIMIT", StringUtil.checkNull(request.getParameter("seach_OT_LIMIT")));
		modelMap.put("seach_OT_LIMIT_100", StringUtil.checkNull(request.getParameter("seach_OT_LIMIT_100")));
		modelMap.put("TANCHUDEPTNO", StringUtil.checkNull(request.getParameter("TANCHUDEPTNO")));
		modelMap.put("OT_TOTAIL_MONTH", StringUtil.checkNull(request.getParameter("OT_TOTAIL_MONTH")));
		modelMap.put("KEY", StringUtil.checkNull(request.getParameter("seach_KEY")));
		return new ModelAndView("/ar/attendanceMintenance/viewOverTimeLimit", modelMap);
	} 
	
	/**
	 * update Over Time Limit 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateOverTimeLimit",method = RequestMethod.POST)
	@ResponseBody
	public Map updateOverTimeLimit(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.empInfoSer.updateHrEmployee(request);
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			map.put("formId", "searchViewPaPayObjForm");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			map.put("formId", "searchViewPaPayObjForm");
		}
		return map;
	}
	
	//加班上限模板下载
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/overTimeLimitImportDemo")
	public void overTimeLimitImportDemo(HttpServletRequest request,	HttpServletResponse response,ModelMap modelMap) throws Exception{
		List aliasNameList = new ArrayList();
		List list = new ArrayList();
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();
		String flag=StringUtil.checkNull(request.getParameter("flag"));
		String name = arDetailSer.overTimeLimitImportDemo(request, aliasNameList, list , mapList, mapNameList,flag);
		LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
		this.excelUtilSer.exportExcelMoreSheet(request, response, modelMap,sqlContentmap, aliasNameList, null,mapNameList,mapList ,name);
	}
}