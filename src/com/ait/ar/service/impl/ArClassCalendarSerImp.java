/**
 * 
 */
package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.EmpCalendarDao;
import com.ait.ar.service.ArClassCalendarSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;

/**
 * @author xuehaifei
 *
 * 2014/07/09 01:26:37
 */
@Service
public class ArClassCalendarSerImp  implements ArClassCalendarSer{
	@Autowired
	private EmpCalendarDao empCalendarDao;

	/* @author xuehaifei
	 * 
	 * 班次日历
	 * 2014-7-9
	 */
	@SuppressWarnings("unchecked")
	public String getArClassCalendarViewHtml(HttpServletRequest request) {
		String actionType = ObjectUtils.toString(request
				.getParameter("actionType"));

		List calendarList = this.getArClassCalendarList(request);
		if(calendarList.size()==0){
			return "";
		}
		String temp = this.getFrist(request, calendarList, actionType);

		int out = Integer.parseInt(temp.substring(temp.lastIndexOf("*") + 1,
				temp.length()));
		String frist = temp.substring(0, temp.lastIndexOf("*"));
		String Default = this.getDefault(request, out, calendarList, actionType);
		return frist + Default;	
	}
	/**
	 * 查看班次日历(get EmpCalendar List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getArClassCalendarList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyid=admin.getCpnyId();
		String arMonth = "";
		String year = ObjectUtils.toString(request.getParameter("year"));
		String month = ObjectUtils.toString(request.getParameter("month"));
		String group_id= ObjectUtils.toString(request.getParameter("GROUP"));
		String MENU_CODE= ObjectUtils.toString(request.getParameter("MENU_CODE"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(group_id==null || group_id.length() == 0){
			//group_id=empCalendarDao.getDefaultGroup(paramMap);
			group_id="400224";
		}

//		String STAT_NO=group_id.equals("CH_W2")?"219948":"141436";
		if (year == null || year.length() == 0) {
			Calendar calendar = Calendar.getInstance();
			year = new java.text.SimpleDateFormat("yyyy").format(calendar
					.getTime());
			month = new java.text.SimpleDateFormat("MM").format(calendar
					.getTime());
		}
		arMonth = year + month;
       
		
		HttpSession session = request.getSession();
		//AdminBean user = SessionUtil.getLoginUserFromSession(request);
		
		paramMap.put("AR_MONTH", arMonth);
		paramMap.put("GROUP_ID", group_id);
		paramMap.put("interLanguage", admin.getLanguage());
//		paramMap.put("MENU_CODE", MENU_CODE);
//		paramMap.put("STAT_NO", STAT_NO);
 
		  if(group_id.equals("400224"))//如果是正常班则读取公司日历 
			  retrunList =  empCalendarDao.getArClassCalendarListForNormalShift(paramMap); 
		  else
			  retrunList = empCalendarDao.getArClassCalendarList(paramMap);
	    /*int countlist=retrunList.size();
         if (countlist==0){
     	    retrunList = empCalendarDao.getArClassCalendarListGs(paramMap);
          }*/
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
			
			//paramMap.put("DEPT_DISTINGUISH_NO", "1");
			paramMap.put("PERSON_ID", admin.getPersonId());
			
			for (int i = 0; i < out; i++) {
				LinkedHashMap calendarMap = (LinkedHashMap) calendarList.get(i);

				frist += this.createViewCalendarHtml(paramMap, calendarMap, actionType);
			}
		}
		
		return "<tr height=\"60px\">" + frist + "</tr>*" + out;
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

		List shiftList = (List) empCalendarDao.getShiftList(paramMap);// 获取班次
		List datetypetList = (List) empCalendarDao.getDateTypeList(paramMap);// 获取类型

		boolean flag = false;
		boolean flag_type = false;
		if (!ObjectUtils.toString(calendarMap.get("SCHEDULE_SHIFT_NO")).equals(
				"")) {
			flag = true;
		}
		if (!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
	             "")) {
			flag_type = true;
		}

		String calendarHtml = "";

		calendarHtml += "<td>";
		calendarHtml += "<div><p>";

		calendarHtml += "<span class=\"day_input\"><input name=\"day\" value=\""
				+ calendarMap.get("IDAY").toString()
				+ "\" type= \"checkbox\"  "
				+ "\" title= \""+ calendarMap.get("DDATE_STR").toString()+"\" /></span>";


		calendarHtml += "<b>"
				/*+ this.getDayColor(NumberUtils.parseNumber(calendarMap.get(
						"IDAY").toString(), Integer.class), NumberUtils
						.parseNumber(calendarMap.get("IWEEK").toString(),
								Integer.class)) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b><span class=\"onwork\">";*/
				+this.getDayColorByDateType(NumberUtils.parseNumber(calendarMap.get(
				"IDAY").toString(), Integer.class),NumberUtils
				.parseNumber(calendarMap.get("TYPEID").toString(),
						Integer.class)) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b><span class=\"onwork\">";
//		String type = flag ? "&nbsp;&nbsp<font color=\"red\">休</font>&nbsp;&nbsp;班"
//				: "&nbsp;&nbsp工&nbsp;&nbsp;班";
		String language = "vi";
		if(paramMap.get("interLanguage") != null && !"".equals(paramMap.get("interLanguage").toString())){
			language = paramMap.get("interLanguage").toString();
		}
		
		//String type = "";
		calendarHtml += "";
		        calendarHtml += "<select name=\"DATE_TYPE_\"  "
		        //style=\" width:60px; \"
				+ calendarMap.get("IDAY").toString() + "\" id=\"DATE_TYPE_"
				+ calendarMap.get("IDAY").toString() + "\">";
		for (int i = 0; i < datetypetList.size(); i++) {
			LinkedHashMap map = (LinkedHashMap) datetypetList.get(i);
			String temp = "";
			String tempType = "";
			tempType = calendarMap.get("TYPEID") != null ? 
						calendarMap.get("TYPEID").toString() : "";
			
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
		/*if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
			"1441")){
				type = "<b><font style=\"color:red\">公休</font></b>";
			
			
		}else if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
			"1442")){
				type = "<b><font style=\"color:red\">节假</font></b>";
		}else{
				type = "<b><font>平日</b>";
		}*/
 
	   //calendarHtml += type + "</span></p>";
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

		calendarHtml += "</select></h4>";
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
	
	private String getDayColorByDateType(int day, int dateType) {
		String daycolor = null;
		switch (dateType) {
		case 1440:
			daycolor = Integer.toString(day);
			break;
		case 1441:
			daycolor = "<span>" + day + "</span>";
			break;
		case 1442:
			daycolor = "<span>" + day + "</span>";
			break;
		case 90000425:
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
		}else {
			if (i == 0)
                return "<span class=\"nowork\"><b>Rest Day</b></span>";
            else
                return "<span class=\"onwork\"><b>Work Day</b></span>";
		}

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
		
	//	paramMap.put("DEPT_DISTINGUISH_NO", "1");
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

			calendarHtml += "<span class=\"day_input\"><input name=\"day\" value=\""
					+ calendarMap.get("IDAY").toString()
					+ "\" type= \"checkbox\""
					+ "\" title= \""+ calendarMap.get("DDATE_STR").toString()+"\" /></span>";


			calendarHtml += "<b>"
					/*+ this.getDayColor(NumberUtils.parseNumber(calendarMap.get(
							"IDAY").toString(), Integer.class), NumberUtils
							.parseNumber(calendarMap.get("IWEEK").toString(),
									Integer.class)) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b><span class=\"onwork\">";*/
					+ this.getDayColorByDateType(NumberUtils.parseNumber(calendarMap.get(
					"IDAY").toString(), Integer.class), NumberUtils
					.parseNumber(calendarMap.get("TYPEID").toString(),
							Integer.class)) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b><span class=\"onwork\">";
//			String type = flag ? "&nbsp;&nbsp<font color=\"red\">休</font>&nbsp;&nbsp;班"
//					: "&nbsp;&nbsp工&nbsp;&nbsp;班";
			String language = "vi";
			if(paramMap.get("interLanguage") != null && !"".equals(paramMap.get("interLanguage").toString())){
				language = paramMap.get("interLanguage").toString();
			}
			List datetypetList = (List) empCalendarDao.getDateTypeList(paramMap);// 获取类型
					calendarHtml += "";
			        calendarHtml += "<select  name=\"DATE_TYPE_\"  "
			        //style=\" width:100px; \"
					+ calendarMap.get("IDAY").toString() + "\" id=\"DATE_TYPE_"
					+ calendarMap.get("IDAY").toString() + "\">";
			for (int k = 0; k < datetypetList.size(); k++) {
				LinkedHashMap map = (LinkedHashMap) datetypetList.get(k);
				String temp = "";
				String tempType = "";
				tempType = calendarMap.get("TYPEID") != null ? 
							calendarMap.get("TYPEID").toString() : "";
				
				if (tempType.equals(map.get("DATATYPE").toString())) {
					temp = "selected";
				}
				String tempColor="";
				if ("1441".equals(tempType)|| "1442".equals(tempType)){
					 tempColor = "style=\"color:red;\"";
				}else {
					tempColor = "style=\"\"";
				}
				calendarHtml += "<option value=\"" + map.get("DATATYPE").toString()
						+ "\"" + temp +">";
				calendarHtml += map.get("DATATYPENAME").toString()
						 + "</option>";
			}
			
			calendarHtml += "</span></p></select>";
			/*String type = "";
			if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
					"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"1441")){
					type = "<b><font style=\"color:red\">公休</font></b>";
				
			}else if(!ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
					"") && ObjectUtils.toString(calendarMap.get("TYPEID")).equals(
				"1442")){
					type = "<b><font style=\"color:red\">节假</font></b>";
				
			}else{
					type = "<b><font>平日</b>";
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

	@SuppressWarnings("unchecked")
	public int updateArClassCalendarInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List getShiftNo(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList =empCalendarDao.getShiftNo(paramMap);
		return retrunList;
	}



}
