package com.ait.sys.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportTable {

	private int rt_no;

	private String menu_code;

	private String table_name;// 分类表名称

	private String table_en_name;

	private String table_kor_name;

	private int report_type;// 分类( 1:ar |2: pa )

	private int view_model;// 显示的模式
	
	private String create_by ;
	
	private Date create_date ;
	
	private String update_by ;
	
	private Date update_date ;

	private List<ReportItem> reportItems = new ArrayList();

	public ReportTable() {
	}
	
	public ReportTable(int rt_no){
		this.setRt_no(rt_no);
	}

	public ReportTable(int rt_no, String menu_code, String table_name, String table_en_name, String table_kor_name, int report_type, int view_model) {
		this.setRt_no(rt_no);
		this.setMenu_code(menu_code);
		this.setTable_name(table_name);
		this.setTable_en_name(table_en_name);
		this.setTable_kor_name(table_kor_name);
		this.setReport_type(report_type);
		this.setView_model(view_model);
	}

	public ReportTable(int rt_no, String menu_code, String table_name, int report_type, int view_model) {
		this.setRt_no(rt_no);
		this.setMenu_code(menu_code);
		this.setTable_name(table_name);
		this.setReport_type(report_type);
		this.setView_model(view_model);
	}

	public String getMenu_code() {
		return menu_code;
	}

	public void setMenu_code(String menu_code) {
		this.menu_code = menu_code;
	}

	public int getReport_type() {
		return report_type;
	}

	public void setReport_type(int report_type) {
		this.report_type = report_type;
	}

	public String getTable_en_name() {
		return table_en_name;
	}

	public void setTable_en_name(String table_en_name) {
		this.table_en_name = table_en_name;
	}

	public String getTable_kor_name() {
		return table_kor_name;
	}

	public void setTable_kor_name(String table_kor_name) {
		this.table_kor_name = table_kor_name;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public int getView_model() {
		return view_model;
	}

	public void setView_model(int view_model) {
		this.view_model = view_model;
	}

	public List<ReportItem> getReportItems() {
		return reportItems;
	}

	public void setReportItems(List<ReportItem> reportItems) {
		this.reportItems = reportItems;
	}

	public int getRt_no() {
		return rt_no;
	}

	public void setRt_no(int rt_no) {
		this.rt_no = rt_no;
	}

	
	public String getCreate_by() {
		return create_by;
	}

	
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	
	public Date getCreate_date() {
		return create_date;
	}

	
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	
	public String getUpdate_by() {
		return update_by;
	}

	
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	
	public Date getUpdate_date() {
		return update_date;
	}

	
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

}
