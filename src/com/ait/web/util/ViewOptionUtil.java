package com.ait.web.util;

import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ait.ar.bean.ArDetail;
import com.ait.ar.service.CycleSer;
import com.ait.sys.bean.ReportItem;
import com.ait.sys.bean.ReportTable;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.sys.dao.ViewOptionDao;
import com.ait.sys.dao.impl.ViewOptionDaoImpl;

@Service
public class ViewOptionUtil {
	
	@Autowired
	private ViewOptionDao viewDAO;

	private String dataTable = "";

	@SuppressWarnings("unchecked")
	public String makeDataTable(Map parameterObject) {
		dataTable = "";
		List list = this.getReportTablesList(parameterObject);
		List dataList = null;
		String CPNY_ID = parameterObject.get("interCpnyID") != null ? parameterObject.get("interCpnyID").toString() : "";
		String LANGUAGE = parameterObject.get("interLanguage") != null ? parameterObject.get("interLanguage").toString() : "";
		for (int i=0;i<list.size();i++) {
			LinkedHashMap lmap = (LinkedHashMap)list.get(i);
			lmap.put("CPNY_ID", CPNY_ID);
			lmap.put("LANGUAGE", LANGUAGE);
			List itemList = this.getReportItemsList(lmap);
			
			parameterObject.put("REPORT_TYPE", lmap.get("REPORT_TYPE") != null ? Integer.parseInt(lmap.get("REPORT_TYPE").toString()) : 0);
			String sql = this.getReportViewSQL(itemList,lmap.get("REPORT_TYPE") != null ? Integer.parseInt(lmap.get("REPORT_TYPE").toString()) : 0, CPNY_ID);
			parameterObject.put("sql", sql);
			dataList = this.getDataList(itemList,parameterObject);
			
			this.makeTableHTML(dataList, itemList, lmap);
		}
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	private List getReportTablesList(Object object) {
		List list = new ArrayList();
		try {
			list = viewDAO.retrieveReportTableList(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private List getReportItemsList(Object object) {
		List list = new ArrayList();
		try {
			list = viewDAO.retrieveReportItemList(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private String getReportViewSQL(List itemList,int report_type, String cpnyID) {
		String sql = "";
		for(int i=2;i<itemList.size();i++){
			LinkedHashMap tempMap = (LinkedHashMap)itemList.get(i);
			if (sql.equals("")){
				sql = "SELECT empid AS 工号,local_name as 姓名 ,get_dept_name(DEPTNO,'zh') AS 部门 ,PA_MONTH AS 工资月"+", " + tempMap.get("REF_ITEM_ID").toString() + " AS " + "\""+tempMap.get("REF_ITEM_NAME").toString()+"\"";
			}else{
				sql += "," + tempMap.get("REF_ITEM_ID").toString() + " AS "
				+ "\""+tempMap.get("REF_ITEM_NAME").toString()+"\"";
			}
		}
		sql += " FROM " + (report_type == 1 ? "PA_SUMMARY_"+cpnyID: "AR_SUMMARY_"+cpnyID);
		return sql;
	}

	@SuppressWarnings("unchecked")
	private String getReportViewAvgSQL(List itemList,int report_type, String cpnyID) {
		String sql = "";
		for(int i=2;i<itemList.size();i++){
			LinkedHashMap tempMap = (LinkedHashMap)itemList.get(i);
			if (sql.equals("")){
				sql = "SELECT '平均工资' AS  平均工资 ,avg (" + tempMap.get("REF_ITEM_ID").toString() + ") AS " + "\""+tempMap.get("REF_ITEM_NAME").toString()+"\"";
			}else{
				sql += ",avg (" + tempMap.get("REF_ITEM_ID").toString() + " )AS "
				+ "\""+tempMap.get("REF_ITEM_NAME").toString()+"\"";
			}
		}
		sql += " FROM " + (report_type == 1 ? "PA_SUMMARY_"+cpnyID: "AR_SUMMARY_"+cpnyID);
		return sql;
	}
	
	@SuppressWarnings("unchecked")
	private List getDataList(List itemList,Map parameterObject) {
		List list = new ArrayList();
		try {
			list = viewDAO.retrieveReportDataList(itemList,parameterObject);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return list;
	}

	private String makeTableHTML(List dataList, List itemList, Map lmap) {
		this.makeTableBeginHTML(lmap);	
		//if ("1".equals(lmap.get("VIEW_MODEL").toString())) {
		//		makeTableNormalTitleHTML(itemList);
		//		makeTableNormalContentHTML(dataList, itemList);
		//} else {
		//		makeTableSpecialTitleHTML(detailList);
		//		makeTableSpecialContentHTML(itemList,detailList,overTimeList);
		//	}
		makeTableNormalTitleHTML(itemList);
		makeTableNormalContentHTML(dataList, itemList);
		makeTableEndHTML();
		return dataTable;
	}

	@SuppressWarnings("unchecked")
	private String makeTableNormalContentHTML(List dataList, List itemList) {
		Map sMap = null ;
		if(dataList.size() > 0){
			for (int i = 0; i < dataList.size(); i++) {
				dataTable += "<tr>";
				sMap = (LinkedHashMap)dataList.get(i);
				dataTable += "<td class='td_type' width= '10%'>";
				dataTable += StringUtil.checkNull(sMap.get("工资月"));
				dataTable += "</td>";

				for(int j=0;j<itemList.size();j++){
					LinkedHashMap lmap = (LinkedHashMap)itemList.get(j);
					dataTable += "<td class='td_type'>";
					dataTable += StringUtil.checkNull(sMap.get(lmap.get("REF_ITEM_NAME")));
					dataTable += "</td>";
				}
				dataTable += "</tr>";
			}
		}else{
			dataTable += "<tr>";
			for(int j=0;j<itemList.size();j++){
				dataTable += "<td class='td_type'>&nbsp;</td>";
			}
			dataTable += "</tr>";
		}
		return dataTable;
	}

	private String makeTableSpecialContentHTML(List<ReportItem> itemList,List<ArDetail> detailList,List<Map> overTimeList) {
		for (ReportItem reportItem : itemList) {
			dataTable += "<tr>";
			dataTable += "<td class='info_content_01' height='30' nowrap >";
			dataTable += reportItem.getItem_name() ;
			dataTable += "</td>";
			if (reportItem.getRef_item_id().toUpperCase().equals("SHIFT")) {
				if(overTimeList != null){
					for (Map overTimeMap : overTimeList) 
						for (ArDetail arDetail : detailList) 
							if((overTimeMap.get("DATE_DAY")+"").equals(arDetail.getDate_day()+"")){
								dataTable += "<td class='info_content_01' nowrap>";
								dataTable += StringUtil.checkNull(arDetail.getShiftShortName());
								dataTable += "</td>";
							}
				}else{
					for (int i = 1; i <= 31; i++) {
						dataTable += "<td class='info_content_01' nowrap>&nbsp;</td>";
					}
				}
			} else if (reportItem.getRef_item_id().toUpperCase().equals("ATDETAIL")) {
				if(overTimeList != null){
					for (Map overTimeMap : overTimeList) 
						for (ArDetail arDetail : detailList) 
							if((overTimeMap.get("DATE_DAY")+"").equals(arDetail.getDate_day()+"")){
								dataTable += "<td class='info_content_01' nowrap>";
								dataTable += StringUtil.checkNull(arDetail.getItemShortName());
								dataTable += "</td>";
							}
				}else{
					for (int i = 1; i <= 31; i++) {
						dataTable += "<td class='info_content_01' nowrap>&nbsp;</td>";
					}
				}
			} else {
				if(overTimeList != null){
					for (Map overTimeMap : overTimeList){
							dataTable += "<td class='info_content_01' nowrap>";
							dataTable += StringUtil.checkNull(overTimeMap.get("OVERTIME"));
							dataTable += "</td>";
					}
				}else{
					for (int i = 1; i <= 31; i++) {
						dataTable += "<td class='info_content_01' nowrap>&nbsp;</td>";
					}
				}
			}
			dataTable += "</tr>";
		}
		return dataTable;
	}

	@SuppressWarnings("unchecked")
	private String makeTableNormalTitleHTML(List itemList) {
		dataTable += "<tr>";
		dataTable += "<td class='td_title'>";
		dataTable += "工资月";
		dataTable += "</td>";
		for(int i=0;i<itemList.size();i++){
			LinkedHashMap lmap = (LinkedHashMap)itemList.get(i);
			dataTable += "<td class='td_title'>";
			dataTable += lmap.get("REF_ITEM_NAME");
			dataTable += "</td>";
		}
		dataTable += "</tr>";
		return dataTable;
	}

	private String makeTableSpecialTitleHTML(List<ArDetail> detailList) {
		dataTable += "<tr>";
		dataTable += "<td width='70' height='30' class='info_title_01' nowrap>";
		dataTable += "</td>";
		if (detailList != null)
			for (ArDetail arDetail : detailList) {
				dataTable += "<td class='info_title_01' nowrap>";
				dataTable += arDetail.getDate_day();
				dataTable += "</td>";
			}
		else
			for (int i = 1; i <= 31; i++) {
				dataTable += "<td class='info_title_01' nowrap>";
				dataTable += i;
				dataTable += "</td>";
			}
		dataTable += "</tr>";
		return dataTable;
	}

	private String makeTableBeginHTML(Map lmap) {
//		if(!dataTable.equals("")){
//			dataTable += "<tr height='1'>";
//			dataTable += "<td colspan='2'>&nbsp;</td>";
//			dataTable += "</tr>";
//		}
//		dataTable += "<tr height='20'>";
//		dataTable += "<td  class='title1'>";
//		dataTable += lmap.get("TABLE_NAME").toString() ;
//		dataTable += "</td>";
//		dataTable += "</tr>";
//		dataTable += "<tr>";
//		dataTable += "<td>";
//		dataTable += "<table width='100%' border='"+num+"' cellpadding='0' cellspacing='1' class='dr_d' >";
//		return dataTable;
		
		dataTable += "<div class='panel'>";
		dataTable += "<h1>"+lmap.get("VIEW_NAME").toString()+"</h1>";
		dataTable += "<div>";
		dataTable += "<table width='100%' border='0' cellpadding='0' cellspacing='0'  class='user_table'>";
		
		return dataTable;
	}

	private String makeTableEndHTML() {
		dataTable += "</table>";
		dataTable += "</div>";
		dataTable += "</div>";
		return dataTable;
	}

	public String makeDataTablePa(Map parameterObject) {
		dataTable = "";
		List list = this.getReportTablesList(parameterObject);
		List dataList = null;
		String CPNY_ID = parameterObject.get("interCpnyID") != null ? parameterObject.get("interCpnyID").toString() : "";
		String LANGUAGE = parameterObject.get("interLanguage") != null ? parameterObject.get("interLanguage").toString() : "";
		for (int i=0;i<list.size();i++) {
			LinkedHashMap lmap = (LinkedHashMap)list.get(i);
			lmap.put("CPNY_ID", CPNY_ID);
			lmap.put("LANGUAGE", LANGUAGE);
			List itemList = this.getReportItemsList(lmap);
			
			parameterObject.put("REPORT_TYPE", lmap.get("REPORT_TYPE") != null ? Integer.parseInt(lmap.get("REPORT_TYPE").toString()) : 0);
			String sql = this.getReportViewSQL(itemList,1, CPNY_ID);
			String sqlAvg = this.getReportViewAvgSQL(itemList,1, CPNY_ID);
			parameterObject.put("sql", sql);
			parameterObject.put("sqlAvg", sqlAvg);
			dataList = this.getDataList(itemList,parameterObject);
			
			this.makeTableHTML(dataList, itemList, lmap);
		}
		return dataTable;
		
	}
}
