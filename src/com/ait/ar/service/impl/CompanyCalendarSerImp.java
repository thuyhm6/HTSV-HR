package com.ait.ar.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.CompanyCalendarDao;
import com.ait.ar.service.CompanyCalendarSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: CompanyCalendarSerImp.java
 * @Description:
 * @Create date: 2012-1-12 下午08:19:39
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class CompanyCalendarSerImp implements CompanyCalendarSer {

	Logger logger = Logger.getLogger(CompanyCalendarSerImp.class);
	
	@Autowired
	private CompanyCalendarDao companyCalendarDao;
	
	/**
	 * 日历查询(get CompanyCalendar ViewHtml)
	 * @param request
	 * @return String
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String getCalendarViewHtml(HttpServletRequest request) {
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		String actionType = ObjectUtils.toString(request.getParameter("actionType")) ;
		String empid = admin.getEmpID();
		
		Map calendarDayMap = new LinkedHashMap();
		List  data = this.getCalendarList(request,actionType) ;
		
		//把当天有多条培训记录的数据存到List
		for (int i=0;i<data.size();i++){
			LinkedHashMap calendarMap = (LinkedHashMap) data.get(i);
			String ddate =calendarMap.get("DDATE").toString();
			if(!calendarDayMap.containsKey(ddate))
				calendarDayMap.put(ddate, new ArrayList());
			((List)calendarDayMap.get(ddate)).add(calendarMap);
		}
		List calendarList = new ArrayList(calendarDayMap.values());
		if(calendarList.size() == 0){
			return "";
		}else{
			String temp = this.getFristTrain(calendarList,actionType,empid);
			
			int out = Integer.parseInt(temp.substring(temp.lastIndexOf("*") + 1,
	                temp.length()));
	        String frist = temp.substring(0, temp.lastIndexOf("*"));
	        String Default = this.getDefaultTrain(out, calendarList,actionType,empid);
	        
	        return frist + Default ; 
		}
        
	}
	
	/**
	 * 公司日历查询(get CompanyCalendar ViewHtml)
	 * @param request
	 * @return String
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String getCompanyCalendarViewHtml(HttpServletRequest request) {
		
		String actionType = ObjectUtils.toString(request.getParameter("actionType")) ;
		
		List calendarList = new ArrayList();
		calendarList = this.getCompanyCalendarList(request) ;
		
		if(calendarList.size() == 0){
			return "";
		}else{
			String temp = this.getFrist(calendarList, actionType) ;
			
			int out = Integer.parseInt(temp.substring(temp.lastIndexOf("*") + 1,
	                temp.length()));
	        String frist = temp.substring(0, temp.lastIndexOf("*"));
	        String Default = this.getDefault(out, calendarList, actionType);
	        
	        return frist + Default ; 
		}
        
	}
	
	/**
	 * 取首个星期(get Frist)
	 * @param calendarList
	 * @param actionType
	 * @return String
	 * @throws 
	 */
	private String getFrist(List calendarList, String actionType) {
		
        String frist = "";
        int out = 0;
        
        // 得到第1天是星期几
        LinkedHashMap calendarMap0 = (LinkedHashMap) calendarList.get(0);
        out = 7 - NumberUtils.parseNumber(calendarMap0.get("IWEEK").toString(), Integer.class) ;
        
        for(int i = 0 ; i < 7 - out  ; ++i){
        	frist += "<td><div></div></td>";
        }
        
        for (int i = 0; i < out ; i++) {
        	
        	if(i < calendarList.size()){
        		
        		LinkedHashMap calendarMap = (LinkedHashMap) calendarList.get(i);
            	
            	frist += this.createViewCalendarHtml(calendarMap, actionType) ;
        	}
        	
        }
        
        return "<tr>" + frist + "</tr>*" + out;
    }
	
	private String getDefault(int out, List calendarList, String actionType) {

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
        for (int i = out; i < calendarList.size(); i++) {
        	LinkedHashMap calendarMap = (LinkedHashMap) calendarList.get(i);
            if (r == 0 || r == 7 || r == 14 || r == 21 || r == 28) {// 加换行
                Default += "<tr>";
            }

            Default += this.createViewCalendarHtml(calendarMap, actionType) ;

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
	 * 取首个星期(get Frist)
	 * @param calendarList
	 * @param actionType
	 * @return String
	 * @throws 
	 */
	private String getFristTrain(List calendarList,String actionType,String empid) {
		
        String frist = "";
        int out = 0;
        
        // 得到第1天是星期几
        LinkedHashMap calendarMap0 = (LinkedHashMap) ((List)calendarList.get(0)).get(0);
        out = 7 - NumberUtils.parseNumber(calendarMap0.get("IWEEK").toString(), Integer.class) ;
        
        for(int i = 0 ; i < 7 - out  ; ++i){
        	frist += "<td><div></div></td>";
        }
        
        for (int i = 0; i < out ; i++) {
        	
        	if(i < calendarList.size()){
        		
        		List dayList = (List) calendarList.get(i);
          
        		frist += this.createCalendarHtml(dayList,actionType,empid) ;
        	}
        	
        }
        
        return "<tr>" + frist + "</tr>*" + out;
    }
	
	private String getDefaultTrain(int out, List calendarList,String actionType,String empid) {

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
        for (int i = out; i < calendarList.size(); i++) {
        	List calendarMap = (List) calendarList.get(i);
            if (r == 0 || r == 7 || r == 14 || r == 21 || r == 28) {// 加换行
                Default += "<tr>";
            }

            Default += this.createCalendarHtml(calendarMap,actionType,empid) ;

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
	 * 创建日历  for 培训日历
	 * @param calendarMap
	 * @param actionType
	 * @return String
	 * @throws 
	 */
	private String createCalendarHtml(List dayData,String actionType,String empid){
		String calendarHtml = "" ;
		
		calendarHtml += "<td width=\"14%\" height=\"42\" >";
		calendarHtml += "<div>";

		//calendarHtml += "<tr>";
		for(int i=0;i<dayData.size();i++){
			Map calendarMap = (Map) dayData.get(i);
			if(i==0)
				calendarHtml += "<p><b><span>"+calendarMap.get("IDAY")+ "</span></b></p>";
		    if (actionType.equals("") && calendarMap.get("COURSENAME")!=null){
		    	calendarHtml += "<h4>";
		    	calendarHtml += "<a style=\"color:blue\" href=\"/edu/trainfile/trainCalendarDetail?PLAN_NO="+calendarMap.get("PLANNO").toString()+"&TR_DATE_STR="+calendarMap.get("DDATE")+ "\"  target=\"dialog\" mask=\"true\" width=\"800\" height=\"600\" >";
				calendarHtml += "<span>" + calendarMap.get("COURSE_NAME_CODE").toString()+" (Kỳ thứ "+calendarMap.get("PERIOD_TIME").toString()+")"+ "</span>";
				calendarHtml += "</a>";
				calendarHtml += "</h4>";
		    }
		    // 当是个人日历时
		    if(calendarMap.get("COURSENAME")!=null && actionType !=null && actionType.equals("personal")){
		    	if(calendarMap.get("EMPID")!=null && calendarMap.get("EMPID").toString().equals(empid)){
		    		calendarHtml += "<h4>";
			    	calendarHtml += "<a style=\"color:blue\" href=\"/edu/trainfile/trainCalendarDetail?PLAN_NO="+calendarMap.get("PLANNO").toString()+"&TR_DATE_STR="+calendarMap.get("DDATE")+ "\"  target=\"dialog\" mask=\"true\" width=\"800\" height=\"600\" >";
					calendarHtml += "<span>" + calendarMap.get("COURSE_NAME_CODE").toString()+" (Kỳ thứ "+calendarMap.get("PERIOD_TIME").toString()+")"+"</span>";
					calendarHtml += "</a>";
					calendarHtml += "</h4>";
		    	}
		    	
		    }
		}
		calendarHtml += "</div></td>";
		
		return calendarHtml ;
	}
	
	/**
	 * 创建日历(create ViewCalendar Html)
	 * @param calendarMap
	 * @param actionType
	 * @return String
	 * @throws 
	 */
	private String createViewCalendarHtml(LinkedHashMap calendarMap, String actionType){
		String calendarHtml = "" ;
		String remark = StringUtil.checkNull(calendarMap.get("REMARK"));
		
		calendarHtml += "<td>";
		calendarHtml += "<div><p>";

		if (actionType != null && actionType.equals("edit")){
			calendarHtml += "<span class=\"day_input\"><input name=\"day\" value=\"" + calendarMap.get("IDAY").toString() + "\" type= \"checkbox\" title=\""+calendarMap.get("MODFIY_DATE_STR").toString()  +"\" /></span>";
		}
		
		//calendarHtml += "<tr>";
		calendarHtml += "<b>" + this.getDayColorAndMonth(calendarMap.get("IMONTH").toString(),
    			NumberUtils.parseNumber(calendarMap.get("IDAY").toString(), Integer.class), 
    			NumberUtils.parseNumber(calendarMap.get("IWEEK").toString(), Integer.class),calendarMap.get("TYPEID").toString());
		calendarHtml += "</b>";
		if (remark.length() > 6) {
			calendarHtml += "<span  title=\""+ remark  +"\">"+ " ~ " + remark.substring(0, 6) + " ..." +"</span>";
		}
		calendarHtml += this.getWorkName(NumberUtils.parseNumber(calendarMap.get("WORKDAYFLAG").toString(), Integer.class), calendarMap.get("LANGUAGE")!=null?calendarMap.get("LANGUAGE").toString():"") + "</p>";
		calendarHtml += "<h4>";
		calendarHtml += "<span>" + calendarMap.get("TYPENAME").toString() + "</span>";
		calendarHtml += "<span>" + calendarMap.get("SHIFT_NAME").toString() + "</span>";
		calendarHtml += "</h4>";
		calendarHtml += "</div></td>";
		
		return calendarHtml ;
	}
	
	private String getweekName(int i, String language) {
        String weekName = null;
        switch (i) {
        case 0:
            weekName = "<b>日</b>";
            break;
        case 1:
            weekName = "一";
            break;
        case 2:
            weekName = "二";
            break;
        case 3:
            weekName = "三";
            break;
        case 4:
            weekName = "四";
            break;
        case 5:
            weekName = "五";
            break;
        case 6:
            weekName = "<b>六</b>";
            break;
        }
        
        return weekName;
    }

	private String getWorkName(int i, String language) {
    	if(language != null){
    		if (i == 0)
    			//休息
                return "<span class=\"nowork\"><b>"+TipMessage.getTipMessage("ar.viewCompanyCalendar.title.rest",language)+"</b></span>";
            else
            	//工作
                return "<span class=\"onwork\"><b>"+TipMessage.getTipMessage("ar.viewCompanyCalendar.title.work",language)+"</b></span>";
    	}
    	
    	return "";
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
		
	private String getDayColor(int day, int week,String typeid) {
        String daycolor = null;
        if (!typeid.equals("1440") ){
        	   daycolor = "<span>" + day + "</span>";
	     }else {
		        switch (week) {
		        case 0:
		     //       daycolor = "<span>" + day + "</span>";
		        	 daycolor = Integer.toString(day);
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
		    //        daycolor = "<span>" + day + "</span>";
		        	 daycolor = Integer.toString(day);
		            break;
		        }
        }
        return daycolor;
    }
	
	/**
	 * 日历查询(get CompanyCalendar List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getCalendarList(HttpServletRequest request,String actionType) {
		List retrunList = new ArrayList() ;
		
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		String arMonth = "" ;
		String year = ObjectUtils.toString(request.getParameter("year")) ;
		String month = ObjectUtils.toString(request.getParameter("month")) ;
		
		if(year == null || year.length() == 0){
			Calendar calendar = Calendar.getInstance();
			year = new java.text.SimpleDateFormat("yyyy").format(calendar.getTime());
			month = new java.text.SimpleDateFormat("MM").format(calendar.getTime());
		}
		arMonth = year + month ;
		
		LinkedHashMap paramMap = new LinkedHashMap();
//		if(admin.getStatNo() != null && !admin.getStatNo().equals("")){
//			paramMap.put("STAT_NO", admin.getStatNo());
//		}
		paramMap.put("AR_MONTH", arMonth);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		
		if(actionType != null && actionType.equals("personal")){
			retrunList = companyCalendarDao.getPersonalCalendarList(paramMap);
		} else {
			retrunList = companyCalendarDao.getCalendarList(paramMap) ;
		}
		return retrunList ;
	}
	
	
	/**
	 * 公司日历查询(get CompanyCalendar List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getCompanyCalendarList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		String arMonth = "" ;
		String year = ObjectUtils.toString(request.getParameter("year")) ;
		String month = ObjectUtils.toString(request.getParameter("month")) ;
		
		if(year == null || year.length() == 0){
			Calendar calendar = Calendar.getInstance();
			year = new java.text.SimpleDateFormat("yyyy").format(calendar.getTime());
			month = new java.text.SimpleDateFormat("MM").format(calendar.getTime());
		}
		arMonth = year + month ;
		
		LinkedHashMap paramMap = new LinkedHashMap();
		if(admin.getStatNo() != null && !admin.getStatNo().equals("")){
			paramMap.put("STAT_NO", admin.getStatNo());
		}
		paramMap.put("AR_MONTH", arMonth);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		
		retrunList = companyCalendarDao.getCompanyCalendarList(paramMap) ;
		
		return retrunList ;
	}
	
	/**
	 * 保存公司日历(add CompanyCalendar Info)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int addCompanyCalendarInfo(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return this.companyCalendarDao.addCompanyCalendarInfo(paramMap);
	}

	@SuppressWarnings("unchecked")
	public int updateCompanyCalendarInfo(HttpServletRequest request) {
	
		// 页面提交的JSON信息
		String jsonString = request.getParameter("jsonData") ;

		List<LinkedHashMap<String, Object>> companyCalendarList = ObjectBindUtil.getRequestJsonData(jsonString) ;
		
		return this.companyCalendarDao.updateCompanyCalendarInfo(companyCalendarList) ;
	}

	/**
	 * 取班次信息(get Shift List)
	 * @param request
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List getShiftList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		// session用户信息
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language",Messages.getLanguage(request));
		//paramMap.put("DEPT_DISTINGUISH_NO", "1");
		paramMap.put("PERSON_ID", admin.getPersonId());
		return this.companyCalendarDao.getShiftList(paramMap);
	}

	@Override
	public int addCompanyCalendarInfoBanCi(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return this.companyCalendarDao.addCompanyCalendarInfoBanCi(paramMap);
	}
	/**
	 * 保存法定节假日(add statutoryHolidays  Info)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int addStatutoryHolidaysInfo(HttpServletRequest request) {
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		// session用户信息
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("CREATED_IP", admin.getAdminIP());
		return this.companyCalendarDao.addStatutoryHolidaysInfo(paramMap);
	}
	@SuppressWarnings("unchecked")
	public int updateStatutoryHolidaysInfo(HttpServletRequest request) {
	
		 
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.companyCalendarDao.updateStatutoryHolidaysInfo(paramMap) ;
	}
	/**
	 * 取一个节假日信息(get Shift List)
	 * @param request
	 * @return List
	 * @throws Exception
	 */
	@Override
	public Object getOneStatutoryHolidayInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		// session用户信息
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);               
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language",Messages.getLanguage(request));
		paramMap.put("DEPT_DISTINGUISH_NO", "1");
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("DDATE_STR", request.getParameter("DDATE_STR"));
		return this.companyCalendarDao.getOneStatutoryHolidayInfo(paramMap);
	}
	@SuppressWarnings("unchecked")
	public int deleteStatutoryHolidaysInfo(HttpServletRequest request) {
	
		 
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		return this.companyCalendarDao.deleteStatutoryHolidaysInfo(paramMap) ;
	}
	
	/**
	 * 获取年假信息
	 */
	public List viewVacEmpList(HttpServletRequest request) {
		List list = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		/*paramMap.put("KEY", request.getParameter("seach_KEY"));
		paramMap.put("YEAR", request.getParameter("seach_YEAR"));
		paramMap.put("SON_FLAG", request.getParameter("seach_SON_FLAG"));
		paramMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		paramMap.put("EMP_OFFICE", request.getParameter("seach_EMP_OFFICE"));*/
		list = companyCalendarDao.viewVacEmpList(paramMap);
		return list;
	}
	
	/**
	 * 清算上一年年假
	 */
	public String executeVacClear(HttpServletRequest request) {
		String result = "ok";
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		result = companyCalendarDao.executeVacClear(paramMap);
		return result;
	}

	/**
	 * 保存年假信息
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpVacInfo(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.companyCalendarDao.saveEmpVacInfo(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 获取倒休信息
	 */
	public List viewTxEmpList(HttpServletRequest request) {
		List list = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("KEY", request.getParameter("seach_KEY"));
		paramMap.put("YEAR", request.getParameter("seach_YEAR"));
		paramMap.put("SON_FLAG", request.getParameter("seach_SON_FLAG"));
		paramMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		list = companyCalendarDao.viewTxEmpList(paramMap);
		return list;
	}
	/**
	 * 获取倒休信息
	 */
	public List viewTxEmpTSTOList(HttpServletRequest request) {
		List list = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("KEY", request.getParameter("seach_KEY"));
		paramMap.put("SH_AR_MONTH", request.getParameter("seach_AR_MONTH"));
		paramMap.put("SON_FLAG", request.getParameter("seach_SON_FLAG"));
		paramMap.put("DEPTNO", request.getParameter("seach_DEPTNO"));
		paramMap.put("EMP_OFFICE", request.getParameter("seach_EMP_OFFICE"));
		list = companyCalendarDao.viewTxEmpTSTOList(paramMap);
		return list;
	}
	
	/**
	 * 保存倒休信息
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpTxInfo(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.companyCalendarDao.saveEmpTxInfo(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 保存倒休信息
	 */
	@SuppressWarnings("unchecked")
	public int saveEmpTxInfo2(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.companyCalendarDao.saveEmpTxInfo2(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}
