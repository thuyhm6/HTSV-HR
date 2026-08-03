package com.ait.ar.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.EmpCalendarDao;
import com.ait.ar.service.EmpCalendarSer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;

import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: EmpCalendarSerImp.java
 * @Description:
 * @Create date: 2012-2-6 上午10:50:39
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class EmpCalendarSerImp implements EmpCalendarSer {

	@Autowired
	private EmpCalendarDao empCalendarDao;
	@Autowired
	PaTempSalesDAO paTempSalesDAO;

	/**
	 * 个人日历查看页面(get EmpCalendar ViewHtml)
	 * 
	 * @param request
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String getEmpCalendarViewHtml(HttpServletRequest request) {

		String actionType = ObjectUtils.toString(request
				.getParameter("actionType"));

		List calendarList = this.getEmpCalendarList(request);

		String temp = this.getFrist(request, calendarList, actionType);

		int out = Integer.parseInt(temp.substring(temp.lastIndexOf("*") + 1,
				temp.length()));
		String frist = temp.substring(0, temp.lastIndexOf("*"));
		String Default = this.getDefault(request, out, calendarList, actionType);
		return frist + Default;
	}

	/**
	 * 查看个人日历(get EmpCalendar List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getEmpCalendarList(HttpServletRequest request) {
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
		String noEmpFlag = request.getParameter("NO_EMP");
		
		paramMap.put("AR_MONTH", arMonth);
		if (ObjectUtils.toString(request.getParameter("person_id")).equals("")) {
			if("Y".equals(noEmpFlag)){
				paramMap.put("PERSON_ID", "");
			}else{
				paramMap.put("PERSON_ID", admin.getPersonId());
			}
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("interLanguage", admin.getLanguage());
			if(admin.getStatNo() != null && !admin.getStatNo().equals("")){
				paramMap.put("STAT_NO", admin.getStatNo());
			}
		} else {
			if("Y".equals(noEmpFlag)){
				paramMap.put("PERSON_ID", "");
			}else{
				paramMap.put("PERSON_ID", ObjectUtils.toString(request
						.getParameter("person_id"))==null?"":ObjectUtils.toString(request
								.getParameter("person_id")).split(",")[0]);
			}
			paramMap.put("CPNY_ID", ObjectUtils.toString(request
					.getParameter("cpny_id")));
			paramMap.put("interLanguage", admin.getLanguage());
			paramMap.put("STAT_NO", request.getParameter("STAT_NO") != null ? request
							.getParameter("STAT_NO") : "");
		}
		
		retrunList = empCalendarDao.getEmpCalendarList(paramMap);

		return retrunList;
	}

	/**
	 * 得到第1天是星期几(get )
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
				frist += "<td ><div></div></td>";
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
		
		return "<tr height=\"60px\">" + frist + "</tr>*" + out;
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
			if (r == 0 ||  r == 14 ||  r == 28) {// 加换行
				Default += "<tr   height=\"60px\"   style=\"background:#FFFFCC\">";
			}
			if( r == 7 || r == 21 )
			{
				Default += "<tr height=\"60px\">";
			}
				//---------------------------------------------
			List shiftList = (List) empCalendarDao.getShiftList(paramMap);// 获取班次

			boolean flag = false;
			if (!ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO")).equals(
					"")) {
				flag = true;
			}

			String calendarHtml = "";
			String remark = StringUtil.checkNull(calendarMap.get("REMARK"));

			calendarHtml += "<td>";
			calendarHtml += "<div><p>";

			calendarHtml += "<span class=\"day_input\"><input name=\"day\" value=\""
					+ calendarMap.get("IDAY").toString() + "\"  title=\""+ calendarMap.get("DDATE_STR").toString() + "\" "
					+ "\" type= \"checkbox\" /></span>";


			calendarHtml += "<b>"
					+ this.getDayColorAndMonth(calendarMap.get("IMONTH").toString(),
			    			NumberUtils.parseNumber(calendarMap.get("IDAY").toString(), Integer.class), 
			    			NumberUtils.parseNumber(calendarMap.get("IWEEK").toString(), Integer.class),calendarMap.get("TYPEID").toString()) 
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b><span class=\"onwork\">";
/*
 * 	calendarHtml += "<b>"
					+ this.getDayColor(NumberUtils.parseNumber(calendarMap.get(
							"IDAY").toString(), Integer.class), NumberUtils
							.parseNumber(calendarMap.get("IWEEK").toString(),
									Integer.class)) + "</b><span class=\"onwork\">";
 * 			
*/			
//			String type = flag ? "&nbsp;&nbsp<font color=\"red\">休</font>&nbsp;&nbsp;班"
//					: "&nbsp;&nbsp工&nbsp;&nbsp;班";
			String language = "vi";
			if(paramMap.get("interLanguage") != null && !"".equals(paramMap.get("interLanguage").toString())){
				language = paramMap.get("interLanguage").toString();
			}
			
			//个人日历添加日期类型可修改
			List datetypetList = (List) empCalendarDao.getDateTypeList(paramMap);// 获取类型
			
			calendarHtml += "";
	        calendarHtml += "<select name=\"DATE_TYPE_\"  "
		        //style=\" width:60px; \"
				+ calendarMap.get("IDAY").toString() + "\" id=\"DATE_TYPE_"
				+ calendarMap.get("IDAY").toString() + "\">";
			for (int k = 0; k < datetypetList.size(); k++) {
				LinkedHashMap map = (LinkedHashMap) datetypetList.get(k);
				String temp = "";
				String tempType = "";
				
				if (flag) {
					tempType = calendarMap.get("SCHEDULE_TYPEID") != null ? 
							calendarMap.get("SCHEDULE_TYPEID").toString() : "";
				} else {
					tempType = calendarMap.get("TYPEID") !=null ? 
							calendarMap.get("TYPEID").toString() : "";
				}
							
				if (tempType.equals(map.get("DATATYPE").toString())) {
					temp = "selected";
				}
				String tempColor="";
				if ("1441".equals(tempType) || "1442".equals(tempType) ){
					 tempColor = "style=\"color:red;\"";
				}else {
					tempColor = "style=\"\"";
				}
				calendarHtml += "<option  value=\"" + map.get("DATATYPE").toString()
						+ "\"" + temp +">";
				calendarHtml += map.get("DATATYPENAME").toString()
						 + "</option>";
			}
			
			calendarHtml += "</span></p></select>";
			if (remark.length() > 6) {
				calendarHtml += "<br><span style=\"color: red;\"  title=\""+ remark  +"\">"+ "" + remark.substring(0, 6) + " ..." +"</span>";
			}
			/*String type = "";
			if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
					"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"1441")){
				//公班
//				if(flag){
					type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.gongxiu",language)+"</font></b>";
//				}else{
//					type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.gongxiu",language)+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//				}
				
			}else if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
					"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"1442")){
//				if(flag){
					//节班
					type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language)+"</font></b>";
//				}else{
//					type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language)
//							+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//				}
				
			}else if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
			"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
			"90000425")){
				
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewComanyCalendar.DAIXINJIA.b",language)+"</font></b>";
			
		}else{
				//工班
//				if(flag){
					type = "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong",language)+"</font></b>";
//				}else{
//					type = "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong",language)+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//				}
			}
			
			calendarHtml += type + "</span></p>";*/
			calendarHtml += "<h4>";
			
			
				if (r == 0 || r==2 ||r==1||r==3 ||r==4 ||r==5 ||r==6  || r == 14 || r == 15 || r == 16||r == 17||r == 18|| r == 19|| r == 20||r == 28 || r == 29 || r == 30 || r == 31|| r == 32|| r == 33|| r == 34 || r == 35) {// 加换行
					calendarHtml += "<select name=\"SHIFT_NO_\" style=\"background:#FFFFCC\" "
						+ calendarMap.get("IDAY").toString() + "\" id=\"SHIFT_NO_"
						+ calendarMap.get("IDAY").toString() + "\">";
				}
				
				if( r == 7 || r == 8 || r == 9 || r == 10 || r == 11 || r == 12 || r == 13 ||r == 21 ||r == 22 ||r == 23 ||r == 24 ||r == 25||r == 26||r == 27 )
				{
						calendarHtml += "<select name=\"SHIFT_NO_\"  "
						+ calendarMap.get("IDAY").toString() + "\" id=\"SHIFT_NO_"
						+ calendarMap.get("IDAY").toString() + "\">";
				}
			
					
			for (int j = 0; j < shiftList.size(); j++) {
				LinkedHashMap map = (LinkedHashMap) shiftList.get(j);
				String temp = "";
				String tempShitNo = "";

				if (flag) {
					tempShitNo = calendarMap.get("SCHEDULE_SHIFT_NO") != null ? 
							calendarMap.get("SCHEDULE_SHIFT_NO").toString() : "";
				} else {
					tempShitNo = calendarMap.get("SHIFT_NO") !=null ? 
							calendarMap.get("SHIFT_NO").toString() : "";
				}
				if (tempShitNo.equals(map.get("SHIFT_NO").toString())) {
					temp = "selected";
				}
				calendarHtml += "<option value=\"" + map.get("SHIFT_NO").toString()
						+ "\"" + temp + ">";
				calendarHtml += map.get("SHIFT_SHORTNAME").toString()
						 + "</option>";
			}

			calendarHtml += "</h4>";
			calendarHtml += "</div></td>";
			//-----------------------------------------------
			//Default += this.createViewCalendarHtml(paramMap, calendarMap, actionType);
			Default += calendarHtml;
			if (r == 6 || r == 13 || r == 20 || r == 27 || r == 34) {// 加换行
				Default += "</tr>";
			}
			r += 1;
		}
		// 补空格
		if (rows > 0) {
			String temp = "";
			for (int i = 0; i < rows; i++) {
				temp += "<td border=\"2\"><div></div></td>";
			}
			Default += temp + "</tr>";
		}
//		System.out.println("---------------------整个样式-------------------------------"+Default);
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
	private String createViewCalendarHtml(LinkedHashMap paramMap, LinkedHashMap calendarMap, String actionType) {

		List shiftList = (List) empCalendarDao.getShiftList(paramMap);// 获取班次
		List datetypetList = (List) empCalendarDao.getDateTypeList(paramMap);// 获取类型
		
		boolean flag = false;
		if (!ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO")).equals(
				"")) {
			flag = true;
		}

		String calendarHtml = "";
		String remark = StringUtil.checkNull(calendarMap.get("REMARK"));

		calendarHtml += "<td>";
		calendarHtml += "<div><p>";

		calendarHtml += "<span class=\"day_input\"><input name=\"day\"  value=\""
		    +calendarMap.get("IDAY").toString() + "\"  title=\""+ calendarMap.get("DDATE_STR").toString() + "\" "
				+ "\" type= \"checkbox\" /></span>";


		calendarHtml += "<b>"
				+ this.getDayColorAndMonth(calendarMap.get("IMONTH").toString(),
		    			NumberUtils.parseNumber(calendarMap.get("IDAY").toString(), Integer.class), 
		    			NumberUtils.parseNumber(calendarMap.get("IWEEK").toString(), Integer.class),calendarMap.get("TYPEID").toString())
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b><span class=\"onwork\">";
/*		
		calendarHtml += "<b>"
				+ this.getDayColor(NumberUtils.parseNumber(calendarMap.get(
						"IDAY").toString(), Integer.class), NumberUtils
						.parseNumber(calendarMap.get("IWEEK").toString(),
								Integer.class)) + "</b><span class=\"onwork\">";
*/		
//		String type = flag ? "&nbsp;&nbsp<font color=\"red\">休</font>&nbsp;&nbsp;班"
//				: "&nbsp;&nbsp工&nbsp;&nbsp;班";
		String language = "vi";
		if(paramMap.get("interLanguage") != null && !"".equals(paramMap.get("interLanguage").toString())){
			language = paramMap.get("interLanguage").toString();
		}
		
		//个人日历添加日期类型可修改
			calendarHtml += "";
	        calendarHtml += "<select name=\"DATE_TYPE_\"  "
		        //style=\" width:60px; \"
				+ calendarMap.get("IDAY").toString() + "\" id=\"DATE_TYPE_"
				+ calendarMap.get("IDAY").toString() + "\">";
		for (int i = 0; i < datetypetList.size(); i++) {
			LinkedHashMap map = (LinkedHashMap) datetypetList.get(i);
			String temp = "";
			String tempType = "";
			
			if (flag) {
				tempType = calendarMap.get("SCHEDULE_TYPEID") != null ? 
						calendarMap.get("SCHEDULE_TYPEID").toString() : "";
			} else {
				tempType = calendarMap.get("TYPEID") !=null ? 
						calendarMap.get("TYPEID").toString() : "";
			}
			
			if (tempType.equals(map.get("DATATYPE").toString())) {
				temp = "selected";
			}
			String tempColor="";
			if ("1441".equals(tempType) || "1442".equals(tempType) ){
				 tempColor = "style=\"color:red;\"";
			}else {
				tempColor = "style=\"\"";
			}
			calendarHtml += "<option  value=\"" + map.get("DATATYPE").toString()
					+ "\"" + temp +">";
			calendarHtml += map.get("DATATYPENAME").toString()
					 + "</option>";
		}
		
		calendarHtml += "</span></p></select>";
		if (remark.length() > 6) {
			calendarHtml += "<br><span style=\"color: red;\"  title=\""+ remark  +"\">"+ "" + remark.substring(0, 6) + " ..." +"</span>";
		}
		
		/*String type = "";
		if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
			"1441")){
			//公班
//			if(flag){
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.gongxiu",language)+"</font></b>";
//			}else{
//				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.gongxiu",language)+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//			}
			
		}else if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
			"1442")){
//			if(flag){
				//节班
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language)+"</font></b>";
//			}else{
//				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language)
//						+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//			}
			
		}else if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
		"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
		"90000425")){
//		if(flag){
			//节班
			type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewComanyCalendar.DAIXINJIA.b",language)+"</font></b>";
//		}else{
//			type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language)
//					+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//		}
		
	}else{
			//工班
//			if(flag){
				type = "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong",language)+"</font></b>";
//			}else{
//				type = "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong",language)+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//			}
		}
		
		calendarHtml += type + "</span></p>";*/
		calendarHtml += "<h4>";
		
				calendarHtml += "<select name=\"SHIFT_NO_\"  "
				+ calendarMap.get("IDAY").toString() + "\" id=\"SHIFT_NO_"
				+ calendarMap.get("IDAY").toString() + "\">";
		for (int i = 0; i < shiftList.size(); i++) {
			LinkedHashMap map = (LinkedHashMap) shiftList.get(i);
			String temp = "";
			String tempShitNo = "";

			if (flag) {
				tempShitNo = calendarMap.get("SCHEDULE_SHIFT_NO") != null ? 
						calendarMap.get("SCHEDULE_SHIFT_NO").toString() : "";
			} else {
				tempShitNo = calendarMap.get("SHIFT_NO") !=null ? 
						calendarMap.get("SHIFT_NO").toString() : "";
			}
			if (tempShitNo.equals(map.get("SHIFT_NO").toString())) {
				temp = "selected";
			}
			calendarHtml += "<option value=\"" + map.get("SHIFT_NO").toString()
					+ "\"" + temp + ">";
			calendarHtml += map.get("SHIFT_SHORTNAME").toString()
					 + "</option>";
		}

		calendarHtml += "</h4>";
		calendarHtml += "</div></td>";
		
//		System.out.println("--------------------------------"+calendarHtml);

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
	private String getDayShiftColor(int day, String isxiuxi) {
		String daycolor = null;
	 
		if( isxiuxi.equals("Y"))
		{
			daycolor = "<span>" + day + "</span>";
		}else 
		{
			daycolor = Integer.toString(day);
		}
		return daycolor;
	}
	
	private String getDayColorAndMonth(String month,int day, int week,String typeid) {
        String daycolor = "";
        if (!typeid.equals("1440") ){
        	   daycolor = "<span>" + day + "-" + month + "</span>";
        	   //daycolor = "<span>" + month + "月" + day + "日</span>";
	     }else {
	    	   daycolor = day + "-" + month;
		      
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
		}else if (language != null && language.equals("vi")) {
			if (i == 0)
                return "<span class=\"nowork\"><b>休息</b></span>";
            else
                return "<span class=\"onwork\"><b>工作</b></span>";
		}  else {
			if (i == 0)
                return "<span class=\"nowork\"><b>Rest Day</b></span>";
            else
                return "<span class=\"onwork\"><b>Work Day</b></span>";
		}
	}

	/**
	 * 个人日历页面修改(update EmpCalendar Info)
	 * @param request
	 * @return int
	 * @throws 
	 */
	public int updateEmpCalendarInfo(HttpServletRequest request) {

		List<LinkedHashMap<String, Object>> paramList = new ArrayList<LinkedHashMap<String, Object>>();
		
		// 页面提交的JSON信息
		String jsonString = request.getParameter("jsonData");

		List<LinkedHashMap<String, Object>> empCalendarList = ObjectBindUtil
				.getRequestJsonData(jsonString);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		if(empCalendarList != null){
			for(LinkedHashMap lmap : empCalendarList){
				lmap.put("CREATED_BY", admin.getPersonId());
				String udate=((String) lmap.get("DDATE_STR")).replace("-", "/");
			//	String udates=empCalendarDao.getRealDate(udate.replace("-", "/"));
				lmap.put("DDATE_STR", udate );
				lmap.put("adminIP",admin.getAdminIP());
				DateFormat dd=new SimpleDateFormat("yyyy/MM/dd");		
				Date date1=null;					
			    try {
					date1 = dd.parse(udate);
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			    Date nowdate = new Date();
			    Boolean flag=date1.before(nowdate);
			    if(flag){
			    	try {
						this.empCalendarDao.insertArShiftChange(lmap);
					} catch (Exception e) {
						e.printStackTrace();
					}	
			    }
				paramList.add(lmap);
			}
		}
		
		try {
			
			this.empCalendarDao.insertEmpCalendarInfo(paramList);
			
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
		paramMap.put("person_id", paramMap.get("person_id")==null?"":paramMap.get("person_id").toString().split(",")[0]);
		return empCalendarDao.getEmpInfo(paramMap);
	}
	/**
	 * 查找各大区
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-24 下午03:14:01 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getBigDistinct(){
		return empCalendarDao.getBigDistinct();
	}

	/* @author xuehaifei
	 * 
	 * 2014-7-28
	 * 
	 */
	@Override
	public int updateClassCalendarInfo(HttpServletRequest request) {
        List<LinkedHashMap<String, Object>> paramList = new ArrayList<LinkedHashMap<String, Object>>();
		
		// 页面提交的JSON信息
	    String jsonString = request.getParameter("jsonData");

		List<LinkedHashMap<String, Object>> empCalendarList = ObjectBindUtil
				.getRequestJsonData(jsonString);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(empCalendarList != null){
			
			for(LinkedHashMap lmap : empCalendarList){
				String cpnyid=admin.getCpnyId();
				String GROUP=(String) lmap.get("GROUP");
//				System.out.println(GROUP+"ss*******+{"+shift);
				if(GROUP==null || GROUP.length() == 0||cpnyid == "TSTO"){
					request.setAttribute("GROUP", "400224");
					GROUP="400224";
				}
				lmap.put("CREATED_BY", admin.getPersonId());
				lmap.put("CREATED_IP", admin.getAdminIP());
				String udate=(String) lmap.get("DDATE_STR");
				String udates= null ;
				//获取对应班次的中夜班津贴
				String allowance = this.empCalendarDao.getArDetailTSTONUM(lmap);
				lmap.put("allowance", allowance);
			/*	if(!GROUP.equals("CH_W2"))
					udates=empCalendarDao.getRealDate(udate.replace("-", "/"));
				else
			*/		udates = udate ;
//				DateFormat dd=new SimpleDateFormat("yyyy/MM/dd");		
//				Date date1=null;					
//			    try {
//					date1 = dd.parse(udates);
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//			    Date nowdate = new Date();
//			    Boolean flag=date1.before(nowdate);
//			    if(flag){
//			    	try {
//						this.empCalendarDao.insertArShiftChangeByShift(lmap);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}	
//			    }
				try {
					this.empCalendarDao.updateClassCalendarInfo(lmap);
					//后来加的，当班次改编，修改AR_DETAIL_表中的中夜班津贴。
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}

			}
		}
		
	  return 1;
	}
	/* @author xuehaifei
	 * 
	 * 2014-7-28
	 * 
	 */
	@Override
	public int createArDEtailClassCalendarInfo(HttpServletRequest request) {
        
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String monthStr=request.getParameter("MONTH_STR");
		LinkedHashMap map=new LinkedHashMap() ;
		map.put("CPNY_ID",admin.getCpnyId());
		map.put("IN_MONTH_STR",monthStr);
    	 try {
    		 if("TSTO".equals(admin.getCpnyId())){
    			this.empCalendarDao.createArDEtailClassCalendarInfo(map);
    			//生成考勤月的加班上限
    			this.empCalendarDao.createArDEtailClassOverTimeLimit(map);
    		 }else{
    			 for(int i = 1;i<=12;i++){
    				if(i < 10){
        				map.put("IN_MONTH_STR", monthStr.substring(0, 4) + "0" + i);
            			this.empCalendarDao.createArDEtailClassCalendarInfo(map);
    				}else{
        				map.put("IN_MONTH_STR", monthStr.substring(0, 4) + i);
            			this.empCalendarDao.createArDEtailClassCalendarInfo(map);
    				}
    			 }
    		 }
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}	 
			  
	  return 1;
	}
	
	/**
	 * 个人日历查看页面(get EmpCalendar ViewHtml)
	 * 
	 * @param request
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String getEmpCalendarViewHtmlPer(HttpServletRequest request) {

		String actionType = ObjectUtils.toString(request
				.getParameter("actionType"));

		List calendarList = this.getEmpCalendarList(request);

		String temp = this.getFristPer(request, calendarList, actionType);

		int out = Integer.parseInt(temp.substring(temp.lastIndexOf("*") + 1,
				temp.length()));
		String frist = temp.substring(0, temp.lastIndexOf("*"));
		String Default = this.getDefaultPer(request, out, calendarList, actionType);
		return frist + Default;
	}
	
	/**
	 * 得到第1天是星期几(get )
	 * 
	 * @param List
	 * @param String
	 * @return String
	 * @throws
	 */
	private String getFristPer(HttpServletRequest request, List calendarList, String actionType) {

		String frist = "";
		int out = 0;

		if(calendarList.size()>0){
			// 得到第1天是星期几
			LinkedHashMap calendarMap0 = (LinkedHashMap) calendarList.get(0);
			out = 7 - NumberUtils.parseNumber(calendarMap0.get("IWEEK").toString(),
					Integer.class);

			for (int i = 0; i < 7 - out; ++i) {
				frist += "<td ><div></div></td>";
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

				frist += this.createViewCalendarHtmlPer(paramMap, calendarMap, actionType);
			}
		}
		
		return "<tr height=\"60px\">" + frist + "</tr>*" + out;
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
	private String getDefaultPer(HttpServletRequest request, int out, List calendarList, String actionType) {

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
			if (r == 0 ||  r == 14 ||  r == 28) {// 加换行
				Default += "<tr   height=\"60px\"   style=\"background:#FFFFCC\">";
			}
			if( r == 7 || r == 21 )
			{
				Default += "<tr height=\"60px\">";
			}
				//---------------------------------------------
			List shiftList = (List) empCalendarDao.getShiftList(paramMap);// 获取班次

			boolean flag = false;
			if (!ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO")).equals(
					"")) {
				flag = true;
			}

			String calendarHtml = "";

			calendarHtml += "<td>";
			calendarHtml += "<div><p>";

			calendarHtml += "<span class=\"day_input\"></span>";


			calendarHtml += "<b>"
					+ this.getDayColorAndMonth(calendarMap.get("IMONTH").toString(),
			    			NumberUtils.parseNumber(calendarMap.get("IDAY").toString(), Integer.class), 
			    			NumberUtils.parseNumber(calendarMap.get("IWEEK").toString(), Integer.class),calendarMap.get("TYPEID").toString()) 
					+ "</b><span class=\"onwork\">";
/*
 * 	calendarHtml += "<b>"
					+ this.getDayColor(NumberUtils.parseNumber(calendarMap.get(
							"IDAY").toString(), Integer.class), NumberUtils
							.parseNumber(calendarMap.get("IWEEK").toString(),
									Integer.class)) + "</b><span class=\"onwork\">";
 * 			
*/			
//			String type = flag ? "&nbsp;&nbsp<font color=\"red\">休</font>&nbsp;&nbsp;班"
//					: "&nbsp;&nbsp工&nbsp;&nbsp;班";
			String language = "zh";
			if(paramMap.get("interLanguage") != null && !"".equals(paramMap.get("interLanguage").toString())){
				language = paramMap.get("interLanguage").toString();
			}
			
			String type = "";
			/*if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
					"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"1441")){
				//公班
//				if(flag){
					type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.gongxiu",language)+"</font></b>";
//				}else{
//					type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.gongxiu",language)+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//				}
				
			}else if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
			"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
			"90000425")){
//			if(flag){
				//待薪假
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewComanyCalendar.DAIXINJIA.b",language)+"</font></b>";
//			}else{
//				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language)
//						+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//			}
			
		}else if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
					"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"1442")){
//				if(flag){
					//节班
					type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language)+"</font></b>";
//				}else{
//					type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language)
//							+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//				}
				
			}else{
				//工班
//				if(flag){
					type = "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong",language)+"</font></b>";
//				}else{
//					type = "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong",language)+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//				}
			}
			
			calendarHtml += type + "</span></p>";*/
			
            String tempType = "";
			
			if (flag) {
				tempType = calendarMap.get("SCHEDULE_TYPEID") != null ? 
						calendarMap.get("SCHEDULE_TYPEID").toString() : "";
			} else {
				tempType = calendarMap.get("TYPEID") !=null ? 
						calendarMap.get("TYPEID").toString() : "";
			}
			
			if ("1441".equals(tempType)){
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.gongxiu",language)+"</font></b>";
			}else if("1442".equals(tempType)){
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language)+"</font></b>";
			}else if("90000425".equals(tempType)){
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewComanyCalendar.DAIXINJIA.b",language)+"</font></b>";
			}else {
				type = "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong",language)+"</font></b>";
			}

		    calendarHtml += type;
		    calendarHtml += "</span></p>";
			calendarHtml += "<h4>";
			
			for (int j = 0; j < shiftList.size(); j++) {
				LinkedHashMap map = (LinkedHashMap) shiftList.get(j);
				String tempShitNo = "";

				if (flag) {
					tempShitNo = calendarMap.get("SCHEDULE_SHIFT_NO") != null ? 
							calendarMap.get("SCHEDULE_SHIFT_NO").toString() : "";
				} else {
					tempShitNo = calendarMap.get("SHIFT_NO") !=null ? 
							calendarMap.get("SHIFT_NO").toString() : "";
				}
				if (tempShitNo.equals(map.get("SHIFT_NO").toString())) {
					calendarHtml += map.get("SHIFT_SHORTNAME").toString();
				}
			}

			calendarHtml += "</h4>";
			calendarHtml += "</div></td>";
			//-----------------------------------------------
			//Default += this.createViewCalendarHtml(paramMap, calendarMap, actionType);
			Default += calendarHtml;
			if (r == 6 || r == 13 || r == 20 || r == 27 || r == 34) {// 加换行
				Default += "</tr>";
			}
			r += 1;
		}
		// 补空格
		if (rows > 0) {
			String temp = "";
			for (int i = 0; i < rows; i++) {
				temp += "<td border=\"2\"><div></div></td>";
			}
			Default += temp + "</tr>";
		}
//		System.out.println("---------------------整个样式-------------------------------"+Default);
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
	private String createViewCalendarHtmlPer(LinkedHashMap paramMap, LinkedHashMap calendarMap,
			String actionType) {

		List shiftList = (List) empCalendarDao.getShiftList(paramMap);// 获取班次
		List datetypetList = (List) empCalendarDao.getDateTypeList(paramMap);// 获取类型
		
		boolean flag = false;
		if (!ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO")).equals(
				"")) {
			flag = true;
		}

		String calendarHtml = "";

		calendarHtml += "<td>";
		calendarHtml += "<div><p>";

		calendarHtml += "<span class=\"day_input\"></span>";


		calendarHtml += "<b>"
				+ this.getDayColorAndMonth(calendarMap.get("IMONTH").toString(),
		    			NumberUtils.parseNumber(calendarMap.get("IDAY").toString(), Integer.class), 
		    			NumberUtils.parseNumber(calendarMap.get("IWEEK").toString(), Integer.class),calendarMap.get("TYPEID").toString()) 
				+ "</b><span class=\"onwork\">";
/*		
		calendarHtml += "<b>"
				+ this.getDayColor(NumberUtils.parseNumber(calendarMap.get(
						"IDAY").toString(), Integer.class), NumberUtils
						.parseNumber(calendarMap.get("IWEEK").toString(),
								Integer.class)) + "</b><span class=\"onwork\">";
*/		
//		String type = flag ? "&nbsp;&nbsp<font color=\"red\">休</font>&nbsp;&nbsp;班"
//				: "&nbsp;&nbsp工&nbsp;&nbsp;班";
		String language = "zh";
		if(paramMap.get("interLanguage") != null && !"".equals(paramMap.get("interLanguage").toString())){
			language = paramMap.get("interLanguage").toString();
		}
		
		String type = "";
		/*if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
			"1441")){
			//公班
//			if(flag){
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.gongxiu",language)+"</font></b>";
//			}else{
//				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.gongxiu",language)+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//			}
					
		}else if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
		"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
		"90000425")){

			type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewComanyCalendar.DAIXINJIA.b",language) +"</font></b>";
		
	}else if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
			"1442")){
//			if(flag){
				//节班
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language) +"</font></b>";
//			}else{
//				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language)
//						+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//			}
			
		}else{
			//工班
//			if(flag){
				type = "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong",language)+"</font></b>";
//			}else{
//				type = "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong",language)+"&nbsp;</font>"+TipMessage.getTipMessage("ar.viewempcalender.title.ban",language)+"</b>";
//			}
		}
		
		calendarHtml += type + "</span></p>";*/
		
			String tempType = "";
			
			if (flag) {
				tempType = calendarMap.get("SCHEDULE_TYPEID") != null ? 
						calendarMap.get("SCHEDULE_TYPEID").toString() : "";
			} else {
				tempType = calendarMap.get("TYPEID") !=null ? 
						calendarMap.get("TYPEID").toString() : "";
			}
			
			if ("1441".equals(tempType)){
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.gongxiu",language)+"</font></b>";
			}else if("1442".equals(tempType)){
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewempcalender.title.jie",language)+"</font></b>";
			}else if("90000425".equals(tempType)){
				type = "<b><font style=\"color:red\">"+TipMessage.getTipMessage("ar.viewComanyCalendar.DAIXINJIA.b",language)+"</font></b>";
			}else {
				type = "<b><font>"+TipMessage.getTipMessage("ar.viewempcalender.title.gong",language)+"</font></b>";
			}

		calendarHtml += type;
		calendarHtml += "</span></p>";
		calendarHtml += "<h4>";
		
		for (int i = 0; i < shiftList.size(); i++) {
			LinkedHashMap map = (LinkedHashMap) shiftList.get(i);
			String tempShitNo = "";

			if (flag) {
				tempShitNo = calendarMap.get("SCHEDULE_SHIFT_NO") != null ? 
						calendarMap.get("SCHEDULE_SHIFT_NO").toString() : "";
			} else {
				tempShitNo = calendarMap.get("SHIFT_NO") !=null ? 
						calendarMap.get("SHIFT_NO").toString() : "";
			}
			if (tempShitNo.equals(map.get("SHIFT_NO").toString())) {
				calendarHtml += map.get("SHIFT_SHORTNAME").toString();
			}
		}

		calendarHtml += "</h4>";
		calendarHtml += "</div></td>";
		
//		System.out.println("--------------------------------"+calendarHtml);

		return calendarHtml;
	}
	
	//
	public List viewEmpCalendarCheckInfoList (String target, HttpServletRequest request) {
		List returnList = new ArrayList();
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		String flag = StringUtil.checkNull(request.getAttribute("RESULT_FLAG"));
		if (!equals(flag) && flag != "") {
			paramMap.put("RESULT_FLAG", flag);
		}
		
		return returnList = this.empCalendarDao.viewEmpCalendarCheckInfoList(target, paramMap);
	}
	
	@Override
	public String submitImportExcelCalendarData(HttpServletRequest request, String target){
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", target);
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	
}
