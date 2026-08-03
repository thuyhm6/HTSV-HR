package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.DailyDetailDao;
import com.ait.ar.service.DailyDetailSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: DailyDetailSerImp.java
 * @Description:
 * @Create date: 2012-2-6 上午10:50:39
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class DailyDetailSerImp implements DailyDetailSer {

	@Autowired
	private DailyDetailDao dailyDetailDao;

	/**
	 * 个人日历查看页面(get dailyDetail ViewHtml)
	 * 
	 * @param request
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String getDailyDetailViewHtml(HttpServletRequest request) {

		String actionType = ObjectUtils.toString(request
				.getParameter("actionType"));

		List calendarList = this.getDailyDetailList(request);

		String temp = this.getFrist(request, calendarList, actionType);

		int out = Integer.parseInt(temp.substring(temp.lastIndexOf("*") + 1,
				temp.length()));
		String frist = temp.substring(0, temp.lastIndexOf("*"));
		String Default = this.getDefault(request, out, calendarList, actionType);

		return frist + Default;
	}

	/**
	 * 查看个人日历(get dailyDetail List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getDailyDetailList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		String arMonth = "";
		String year = ObjectUtils.toString(request.getParameter("year"));
		String month = ObjectUtils.toString(request.getParameter("month"));

		if (year == null || year.length() == 0) {
			Calendar calendar = Calendar.getInstance();
			year = new java.text.SimpleDateFormat("yyyy").format(calendar
					.getTime());
			month = new java.text.SimpleDateFormat("MM").format(calendar
					.getTime());
		}
		arMonth = year + month;

		LinkedHashMap paramMap = new LinkedHashMap();
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		paramMap.put("AR_MONTH", arMonth);
		if (ObjectUtils.toString(request.getParameter("person_id")).equals("")) {
			
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("interLanguage", admin.getLanguage());
			if(admin.getStatNo() != null && !admin.getStatNo().equals("")){
				paramMap.put("STAT_NO", admin.getStatNo());
			}
		} else {
			paramMap.put("PERSON_ID", ObjectUtils.toString(request
					.getParameter("person_id")));
			paramMap.put("CPNY_ID", ObjectUtils.toString(request
					.getParameter("cpny_id")));
			paramMap.put("interLanguage", admin.getLanguage());
			paramMap.put("STAT_NO", request.getParameter("STAT_NO") != null ? request
							.getParameter("STAT_NO") : "");
		}
		
		retrunList = dailyDetailDao.getDailyDetailList(paramMap);

		return retrunList;
	}

	/**
	 * 得到第1天是星期几(get Frist)
	 * 
	 * @param List
	 * @param String
	 * @return String
	 * @throws
	 */
	private String getFrist(HttpServletRequest request, List calendarList, String actionType) {

		String frist = "";
		int out = 0;

		if(calendarList.size()>0){
			// 得到第1天是星期几
			LinkedHashMap calendarMap0 = (LinkedHashMap) calendarList.get(0);
			out = 7 - NumberUtils.parseNumber(calendarMap0.get("IWEEK").toString(),
					Integer.class);

			for (int i = 0; i < 7 - out; ++i) {
				frist += "<td><div></div></td>";
			}
			
			HttpSession session = request.getSession();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap paramMap = new LinkedHashMap();
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("interLanguage", admin.getLanguage());
			
			paramMap.put("DEPT_DISTINGUISH_NO", "1");
			paramMap.put("PERSON_ID", admin.getPersonId());
			
			for (int i = 0; i < out; i++) {
				LinkedHashMap calendarMap = (LinkedHashMap) calendarList.get(i);

				frist += this.createViewCalendarHtml(paramMap, calendarMap, actionType);
			}
		}
		
		return "<tr>" + frist + "</tr>*" + out;
	}

	/**
	 * 取默认日历(get Default)
	 * 
	 * @param int
	 * @param List
	 * @param String
	 * @return String
	 * @throws
	 */
	private String getDefault(HttpServletRequest request, int out, List calendarList, String actionType) {

		int r = 0;
		int rows = 0;
		if ((calendarList.size() - out) % 7 != 0) {// 算出 剩余的有几行
			rows = (calendarList.size() - out) / 7 + 1;
		} else {
			rows = (calendarList.size() - out) / 7;
		}
		rows = rows * 7;// 总共多少格子
		rows = rows - (calendarList.size() - out);// 到最后一行剩余几格子
		String Default = "";
		
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		
		paramMap.put("DEPT_DISTINGUISH_NO", "1");
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		for (int i = out; i < calendarList.size(); i++) {
			LinkedHashMap calendarMap = (LinkedHashMap) calendarList.get(i);
			if (r == 0 || r == 7 || r == 14 || r == 21 || r == 28) {// 加换行
				Default += "<tr>";
			}

			Default += this.createViewCalendarHtml(paramMap, calendarMap, actionType);

			if (r == 6 || r == 13 || r == 20 || r == 27 || r == 34) {// 加换行
				Default += "</tr>";
			}
			r += 1;
		}
		// 补空格
		if (rows > 0) {
			String temp = "";
			for (int i = 0; i < rows; i++) {
				temp += "<td><div></div></td>";
			}
			Default += temp + "</tr>";
		}
		return Default;
	}

	/**
	 * 动态拼装个人日历(create ViewCalendar Html)
	 * 
	 * @param LinkedHashMap
	 * @param String
	 * @return String
	 * @throws
	 */
	private String createViewCalendarHtml(LinkedHashMap paramMap, LinkedHashMap calendarMap,
			String actionType) {

//		List shiftList = (List) dailyDetailDao.getShiftList(paramMap);// 获取班次
//
		boolean flag = false;
		if (!ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO")).equals(
				"")) {
			flag = true;
		}
		//排班-公司日历-空
		paramMap.put("DAILY_SHIFT_NO", !ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO")).equals(
				"") ? ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO")) : (calendarMap.get("SHIFT_NO") !=null ? 
						calendarMap.get("SHIFT_NO").toString() : ""));
		
		String DAILY_SHIFT_NAME = "";
		
		DAILY_SHIFT_NAME = dailyDetailDao.getShiftName(paramMap);
			
		String calendarHtml = "";

		calendarHtml += "<td>";
		calendarHtml += "<div><p>";

//		calendarHtml += "<span class=\"day_input\"><input name=\"day\" value=\""
//				+ calendarMap.get("IDAY").toString()
//				+ "\" type= \"checkbox\" /></span>";


		calendarHtml += "<b>"
				+ this.getDayColor(NumberUtils.parseNumber(calendarMap.get(
						"IDAY").toString(), Integer.class), NumberUtils
						.parseNumber(calendarMap.get("IWEEK").toString(),
								Integer.class)) + "</b><span class=\"onwork\">";
//		String type = flag ? "&nbsp;&nbsp<font color=\"red\">休</font>&nbsp;&nbsp;班"
//				: "&nbsp;&nbsp工&nbsp;&nbsp;班";
		String language = "zh";
		if(paramMap.get("interLanguage") != null && !"".equals(paramMap.get("interLanguage").toString())){
			language = paramMap.get("interLanguage").toString();
		}
		
		calendarMap.put("VIEW_LANGUAGE", language);
		calendarMap.put("DAILY_SHIFT_NO", !ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO")).equals("") 
				? ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO")) : (calendarMap.get("SHIFT_NO") !=null ? 
						calendarMap.get("SHIFT_NO").toString() : ""));
		//添加休假情况 必须为工作日
		if(calendarMap.get("WORKDAYFLAG")!=null && calendarMap.get("WORKDAYFLAG").toString().equals("1")
				&& calendarMap.get("VIEW_PERSON_ID")!=null && calendarMap.get("DDATE")!=null){
			List leaveList = dailyDetailDao.getLeaveList(calendarMap);
			if(leaveList !=null && leaveList.size() > 0){
				calendarHtml += "<font style=\"color:red\">";
				for(int i=0;i<leaveList.size();i++){
					LinkedHashMap leavetemp = (LinkedHashMap)leaveList.get(i);
					calendarHtml += (leavetemp.get("LEAVE_TYPE_NAME")!=null?leavetemp.get("LEAVE_TYPE_NAME").toString():"") + "&nbsp;&nbsp;"
							+ (leavetemp.get("FROM_TIME")!=null?leavetemp.get("FROM_TIME").toString():"") + "--"
									+ (leavetemp.get("TO_TIME")!=null?leavetemp.get("TO_TIME").toString():"") + "<br>";
				}
				calendarHtml += "</font>";
			}
		}
		
		String type = "";
		if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
			"1441")){
			//公班
			if(flag){
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.gongxiu",language)+"&nbsp;</font><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</font></b>";
			}else{
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.gongxiu",language)+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
			}
			
		}else if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
			"1442")){
			if(flag){
				//节班
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language)
						+"&nbsp;</font><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</font></b>";
			}else{
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language)
						+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
			}
			
		}else{
			//工班
			if(flag){
				type = "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong",language)+"&nbsp;</font><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</font></b>";
			}else{
				type = "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong",language)+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
			}
		}
		
		calendarHtml += type + "</span></p>";
		calendarHtml += "<h4>";

//		calendarHtml += "<select name=\"SHIFT_NO_"
//				+ calendarMap.get("IDAY").toString() + "\" id=\"SHIFT_NO_"
//				+ calendarMap.get("IDAY").toString() + "\">";
//		for (int i = 0; i < shiftList.size(); i++) {
//			LinkedHashMap map = (LinkedHashMap) shiftList.get(i);
//			String temp = "";
//			String tempShitNo = "";
//
//			if (flag) {
//				tempShitNo = calendarMap.get("SCHEDULE_SHIFT_NO") != null ? 
//						calendarMap.get("SCHEDULE_SHIFT_NO").toString() : "";
//			} else {
//				tempShitNo = calendarMap.get("SHIFT_NO") !=null ? 
//						calendarMap.get("SHIFT_NO").toString() : "";
//			}
//			if (tempShitNo.equals(map.get("SHIFT_NO").toString())) {
//				temp = "selected";
//			}
//			calendarHtml += "<option value=\"" + map.get("SHIFT_NO").toString()
//					+ "\"" + temp + ">";
//			calendarHtml += map.get("SHIFT_NAME").toString()
//					 + "</option>";
//			calendarHtml += map.get("SHIFT_NAME").toString();
//		}
		
		calendarHtml += DAILY_SHIFT_NAME;

		calendarHtml += "</h4>";
		calendarHtml += "</div></td>";

		return calendarHtml;
	}

	private String getDayColor(int day, int week) {
		String daycolor = null;
		switch (week) {
		case 0:
			daycolor = "<span>" + day + "</span>";
			break;
		case 1:
			daycolor = Integer.toString(day);
			break;
		case 2:
			daycolor = Integer.toString(day);
			break;
		case 3:
			daycolor = Integer.toString(day);
			break;
		case 4:
			daycolor = Integer.toString(day);
			break;
		case 5:
			daycolor = Integer.toString(day);
			break;
		case 6:
			daycolor = "<span>" + day + "</span>";
			break;
		}
		return daycolor;
	}

	private String getWorkName(int i, String language) {
		if (language != null && language.equals("zh")) {
			if (i == 0)
                return "<span class=\"nowork\"><b>休息</b></span>";
            else
                return "<span class=\"onwork\"><b>工作</b></span>";
		} else if (language != null && language.equals("ko")) {
			if (i == 0)
                return "<span class=\"nowork\"><b>休息</b></span>";
            else
                return "<span class=\"onwork\"><b>工作</b></span>";
		} else if (language != null && language.equals("vi")) {
			if (i == 0)
                return "<span class=\"nowork\"><b>休息</b></span>";
            else
                return "<span class=\"onwork\"><b>工作</b></span>";
		} else {
			if (i == 0)
                return "<span class=\"nowork\"><b>Rest Day</b></span>";
            else
                return "<span class=\"onwork\"><b>Work Day</b></span>";
		}
	}

	/**
	 * 个人日历页面修改(update dailyDetail Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	public int updateDailyDetailInfo(HttpServletRequest request) {

		List<LinkedHashMap<String, Object>> paramList = new ArrayList<LinkedHashMap<String, Object>>();
		
		// 页面提交的JSON信息
		String jsonString = request.getParameter("jsonData");

		List<LinkedHashMap<String, Object>> empCalendarList = ObjectBindUtil
				.getRequestJsonData(jsonString);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		if(empCalendarList != null){
			for(LinkedHashMap lmap : empCalendarList){
				lmap.put("CREATED_BY", admin.getPersonId());
				
				paramList.add(lmap);
			}
		}
		
		try {
			
			this.dailyDetailDao.insertDailyDetailInfo(paramList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	  return 1;
	  
	}

	/**
	 * 查看个人信息(get Emp Info)
	 * 
	 * @param request
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Object getEmpInfo(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return dailyDetailDao.getEmpInfo(paramMap);
	}
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getDailyDetailPersonList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		paramMap.put("CPNY_ID",admin.getCpnyId());
		
		paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		
		retrunList = dailyDetailDao.getDailyDetailPersonList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		return retrunList;
	}
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@Override
	public int getDailyDetailPersonCnt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("AR_SUPERVISOR_ID", admin.getPersonId());
		return dailyDetailDao.getDailyDetailPersonCnt(paramMap) ;
	}
}
