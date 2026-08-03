package com.ait.ar.service.impl;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

 
import bsh.This;

import com.ait.ar.dao.ArMonthDao;
import com.ait.ar.service.ArMonthSer;

import com.ait.sys.bean.AdminBean;

import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArMonthSerImp.java
 * @Description:
 * @Create date: 2012-2-11 下午03:08:24
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Service
public class ArMonthSerImp implements ArMonthSer {
Logger logger = Logger.getLogger(ArMonthSerImp.class);
	
	@Autowired
	private ArMonthDao arMonthDao;
	
	private String dataTable = "";
	/**
	 * 取汇总项目列名(get ArColumns)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArColumns(HttpServletRequest request) {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("AR_SUMMARY_TABLE", "AR_SUMMARY_"+paramMap.get("interCpnyID").toString());
		paramMap.put("PERSON_ID", admin.getPersonId() != null ? admin.getPersonId() : "");
		paramMap.put("USERNAME", admin.getUsername() != null ? admin.getUsername() : "");
		//"1"表示为工资的计算结果
		paramMap.put("FUNCTIONFLAG", request.getAttribute("FUNCTIONFLAG"));
		paramMap.put("DISTINGUISH", request.getAttribute("DISTINGUISH"));
		List retrunList = arMonthDao.getArColumns(paramMap);
		 
        return retrunList;
	}
	
	/**
	 * 取汇总项目列名YN(get ArColumns)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArColumnsYN(HttpServletRequest request) {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("AR_SUMMARY_TABLE", "AR_SUMMARY_"+paramMap.get("interCpnyID").toString());
		paramMap.put("PERSON_ID", admin.getPersonId() != null ? admin.getPersonId() : "");
		paramMap.put("USERNAME", admin.getUsername() != null ? admin.getUsername() : "");
		//"1"表示为工资的计算结果
		paramMap.put("FUNCTIONFLAG", request.getAttribute("FUNCTIONFLAG"));
		paramMap.put("DISTINGUISH", request.getAttribute("DISTINGUISH"));
		List retrunList = arMonthDao.getArColumnsYN(paramMap);
		 
        return retrunList;
	}
	
	/**
	 * 拼接查询条件(get ArColumnsParam)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String getArColumnsParam(HttpServletRequest request) {
		// TODO Auto-generated method stub
		 
		List paramList=arMonthDao.getArColumnsParam(request);
		  
	    String paramString="";
	    
	    for(int i=0;i<paramList.size();i++){
	    	
	    	 LinkedHashMap map=(LinkedHashMap)paramList.get(i);
	    	 paramString+=map.get("COLUMN_NAME");
	    	 if(i!=paramList.size()-1)
	    	 {
	    		 paramString+=",";
	    	 }
	     }
		 
        return paramString;
	}
	
	/**
	 * 拼接查询条件(get ArColumnsParam)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public String getArColumnsParam1(List paramList) {
		// TODO Auto-generated method stub
		
		String paramString="";
		
		for(int i=0;i<paramList.size();i++){
	    	
	    	 LinkedHashMap map=(LinkedHashMap)paramList.get(i);
	    	 if(i == 0){
	    		 paramString = "," + map.get("COLUMN_NAME");
	    	 }else{
	    		 paramString+=map.get("COLUMN_NAME");
	    	 }
	    	 
	    	 if(i!=paramList.size()-1)
	    	 {
	    		 paramString+=",";
	    	 }
	     }
		
        return paramString;
	}
	
	/**
	 * 取汇总数据(get ArMonth List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArMonthList(HttpServletRequest request, List getArColumnsList, String arMonth) {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("supervisor", admin.getPersonId());
		
		paramMap.put("arMonth", arMonth);
		
//		paramMap.put("sqlStatement", getArColumnsParam(request));
		
		paramMap.put("sqlStatement", this.getArColumnsParam1(getArColumnsList));
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				arMonthDao.getArMonthList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = arMonthDao.getArMonthList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	
	/**
	 * 取汇总数据(get ArMonth List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArMonthListYN(HttpServletRequest request, List getArColumnsList, String arMonth) {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("supervisor", admin.getPersonId());
		
		paramMap.put("arMonth", arMonth);
		
//		paramMap.put("sqlStatement", getArColumnsParam(request));
		
		paramMap.put("sqlStatement", this.getArColumnsParam1(getArColumnsList));
		
		retrunList = arMonthDao.getArMonthList(paramMap) ;
		 
		
		return retrunList ;
	}
	
	
	/**
	 * 取汇总数据(get ArMonth List) 只为ess个人考勤查看服务
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getArMonthEssListYN(HttpServletRequest request, List getArColumnsList, String arMonth) {
		
		List retrunList = new ArrayList() ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		paramMap.put("supervisor", admin.getPersonId());
		
		paramMap.put("PERSON_ID", admin.getPersonId());
		
		paramMap.put("arMonth", arMonth);
		
//		paramMap.put("sqlStatement", getArColumnsParam(request));
		
		paramMap.put("sqlStatement", this.getArColumnsParam1(getArColumnsList));
		
		retrunList = arMonthDao.getArMonthEssList(paramMap) ;
		 
		
		return retrunList ;
	}
	
	
	public int getArMonthListCnt(HttpServletRequest request, String arMonth){
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;

		paramMap.put("arMonth", arMonth);
		
		paramMap.put("supervisor", admin.getPersonId());
	     
		return arMonthDao.getArMonthListCnt(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public String retrieveMonthlyStatus(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		if(request.getParameter("arMonth")==null||"".equals(request.getParameter("arMonth")))
		{
			    Date date = new Date();
			    String nowmonth = new SimpleDateFormat("yyyyMM").format(date);   
		        paramMap.put("arMonth", nowmonth);
		}
		 List retrunList  = arMonthDao.retrieveMonthlyStatusList(paramMap);
		 String mothlyLock = "N";
		if (retrunList == null
				|| retrunList.size() == 0
				|| ((LinkedHashMap) retrunList.get(0)).get("ATT_MO_LOCK_FLAG") == null
				|| Integer.parseInt((((LinkedHashMap) retrunList.get(0))
						.get("ATT_MO_LOCK_FLAG")).toString()) == 0)
			mothlyLock = "Y";
		
		 
        return mothlyLock;
	}
	
	/**
	 * 修改考勤汇总(update ArMonth Info)
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int updateArMonthInfo(HttpServletRequest request){
		
		 
		String arMonth=request.getParameter("arMonth");
		// 页面参数
//		String jsonString = request.getParameter("jsonData") ;
//		List<LinkedHashMap<String, Object>> arMonthInfoList = ObjectBindUtil.getRequestJsonData(jsonString) ;
		
//		List paramList=arMonthDao.getArColumnsParam(request);
		
		Map tempMap = ObjectBindUtil.getRequestParamData(request) ;
		
		tempMap.put("AR_SUMMARY_TABLE", "AR_SUMMARY_"+tempMap.get("interCpnyID").toString());
		
		List paramList=arMonthDao.getArColumns(tempMap);
		
		String[] isCheck = request.getParameterValues("c1");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		List sqlList = new ArrayList();
		
		try {
			
			if(isCheck != null){
//				System.out.println("--------------进入循环--------------------------------------------------------");
				for(int j=0;j<isCheck.length;j++){
					
//					LinkedHashMap parammap=(LinkedHashMap)arMonthInfoList.get(j);
					
					LinkedHashMap parammap = new LinkedHashMap();
					
				    String sqlparamString="update AR_SUMMARY_" + admin.getCpnyId() + " set ";
				     
				    for(int i=0;i<paramList.size();i++){
				    	 
				    	LinkedHashMap map=(LinkedHashMap)paramList.get(i);
				    	 
				    	//考勤汇总项目
				    	String checkValue = tempMap.get(map.get("COLUMN_NAME") + "_" + isCheck[j]).toString();
				    	if( checkValue.equals("") || checkValue==null )
				    	{
				    		 checkValue = "0";
				    	}
				    	sqlparamString+=map.get("COLUMN_NAME")+"="+checkValue;
//				    	System.out.println("----------------------------------------------"+checkValue);
				    	if(i!=paramList.size()-1){
				    		sqlparamString+=",";
				    	}else{
				    		sqlparamString+=" ,UPDATE_DATE = SYSDATE, UPDATED_BY = " +admin.getPersonId();
				    	}
				     }
				    //isCheck PERSON_ID
				    sqlparamString+=" where ar_month='"+arMonth +"' and  PERSON_ID= "+"'"+isCheck[j] +"'";
				    Logger.getLogger(getClass()).debug("sqlparamString:"+sqlparamString);
				    parammap.put("sql", sqlparamString);
//					System.out.println("--------------sql--------------------------------------------------------"+sqlparamString);
				    sqlList.add(parammap);
				 }
				this.arMonthDao.updateArMonthInfo(sqlList) ;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	/**
	 * 取得考勤锁定状态(get MonthlyStatus List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getMonthlyStatusList(HttpServletRequest request, String arMonth) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PA_MONTH_STR", arMonth);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		List retrunList = arMonthDao.getMonthlyStatusList(paramMap);
		 
        return retrunList;
	}
	
	/**
	 * 取得考勤锁定状态(get MonthlyStatus List)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getMonthlyStatusList(HttpServletRequest request, String arMonth, String statNo) {
		// TODO Auto-generated method stub
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PA_MONTH_STR", arMonth);
		paramMap.put("STAT_NO", statNo);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		List retrunList = arMonthDao.getMonthlyStatusList(paramMap);
		 
        return retrunList;
	}

	/**
	 * 取得所查月份最后一天
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public Object getLastDayByMonth(String monthStr) {
		LinkedHashMap returnMap = new LinkedHashMap();
		LinkedHashMap paramMap = new LinkedHashMap();;
		paramMap.put("MONTH_STR", monthStr);
		
		returnMap = (LinkedHashMap) arMonthDao.getLastDayByMonth(paramMap);
		 
        return returnMap;
	}
	
	
	@SuppressWarnings("unchecked")
	public String makeTableHTML(String name,List itemList, List dataList){
		dataTable = "";
		makeTableBeginHTML(name);
		makeTableNormalTitleHTML(itemList,dataList);
		
		makeTableEndHTML();
		
		return dataTable;
	}
	
	
	private String makeTableBeginHTML(String name) {
		if (!dataTable.equals("")) {
			dataTable += "<tr height='1'>";
			dataTable += "<td colspan='2'>&nbsp;</td>";
			dataTable += "</tr>";
		}
		dataTable += "<tr>";
		dataTable += "<td  width='100%'>";
		dataTable += "<div class='panel'>";
		dataTable += "<h1 style='text-align:center'>";
		dataTable += name;
		dataTable += "</h1>";
		dataTable += "<div>";
		dataTable += "<table width='100%' border='1' cellpadding='0' cellspacing='0' class='user_table' >";
		return dataTable;
	}

	
	private String makeTableNormalTitleHTML(List itemList,List dataList) {
		//Date d = new Date();
		LinkedHashMap sMap = null;
		int a = 1;
		float b = 0.0f;
		int i = 0;
		int a1 = 1;
		float b1 = 0.0f;
		int i1 = 0;
		int a2 = 1;
		float b2 = 0.0f;
		int i2 = 0;
		double size = (double)itemList.size() /6 ;
		int itemsize = itemList.size();
		int dataListsize = dataList.size();
		for (; b < size; b++) {
			dataTable += "<tr>";
			for (; i < a * 6; i++) {
				// for (ReportItem reportItem : itemList) {
				LinkedHashMap reportItem = (LinkedHashMap)itemList.get(i);
				dataTable += "<td class='td_title' style='text-align:center' width='17%' >";
				dataTable += reportItem.get("ITEM_NAME");
			    dataTable += "</td>";
				if(i == itemList.size() - 1){
					if(itemList.size() > 6){
					for(int q = 1;q<=a*6-i-1;q++){
						dataTable += "<td class='td_title' style='text-align:center' >&nbsp;</td>";
					}
					}
					break;
				}
			}
			
			dataTable += "</tr>";
			
			if (dataList.size() > 0){
				
				for(;b1< size;b1++){
				dataTable += "<tr>";
				for(;i1<a1*6;i1++){
					sMap = (LinkedHashMap)dataList.get(0);
					LinkedHashMap reportItem = (LinkedHashMap)itemList.get(i1);
					//-for (ReportItem reportItem : itemList) {
						dataTable += "<td class='td_type' style='text-align:center' >";
						dataTable += StringUtil.checkNull(sMap.get(reportItem.get("COLUMN_NAME")),"-");
						dataTable += "</td>";
						if(i1 == itemList.size() - 1){
							if(itemList.size() > 6){
							for(int q = 1;q<=a*6-i-1;q++){
								dataTable += "<td class='td_type' style='text-align:center' >&nbsp;</td>";
							  }
							}
							break;
						}
					//}
					}
					dataTable += "</tr>";
					break;
				}
			}
			else {
				for(;b2< size ;b2++){
				dataTable += "<tr>";
				for(;i2<a2*6;i2++){
					dataTable += "<td class='td_type' style='text-align:center' >&nbsp;</td>";
					if(i2 == itemList.size() - 1){
						if(itemList.size() > 10){
						for(int q = 1;q<=a*6-i-1;q++){
							dataTable += "<td class='td_type' style='text-align:center' >&nbsp;</td>";
						}
						}
						break;
					}
				}
				dataTable += "</tr>";
				break;
				}
			}
			if (i == a * 6) {
				i = a * 6;
				a++;
			}
			if (i1 == a1 * 6 ) {
				i1 = a1 * 6;
				a1++;
			}
			if (i2 == a2 * 6) {
				i2 = a2 * 6;
				a2++;
			}
			
		}

		return dataTable;
	}
	

	private String makeTableEndHTML() {
		dataTable += "</table>";
		dataTable += "</div>";
		dataTable += "</div>";
		
		dataTable += "</td>";
		dataTable += "</tr>";
		return dataTable;
	}
	
	
}
