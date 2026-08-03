package com.ait.sys.bean;

import java.util.Date;

public class ReportItem {

	private int ri_no;
	
	private int rt_no ;

	private ReportTable reportTable;

	private String item_name;

	private String item_en_name;

	private String item_kor_name;

	private String ref_table_name;

	private int ref_item_no;
	
	private String order_no ;
	
	private String ref_item_id;
	
	private String create_by ;
	
	private Date create_date ;
	
	private String update_by ;
	
	private Date update_date ;

	public ReportItem() {
	}

	public ReportItem(int ri_no, ReportTable reportTable, String item_name, String item_en_name, String item_kor_name, String ref_table_name, int ref_item_no) {
		this.setRi_no(ri_no);
		this.setReportTable(reportTable);
		this.setItem_name(item_name);
		this.setItem_en_name(item_en_name);
		this.setItem_kor_name(item_kor_name);
		this.setRef_table_name(ref_table_name);
		this.setRef_item_no(ref_item_no);
	}

	public ReportItem(int ri_no, ReportTable reportTable, String item_name, String ref_table_name, int ref_item_no) {
		this.setRi_no(ri_no);
		this.setReportTable(reportTable);
		this.setItem_name(item_name);
		this.setRef_table_name(ref_table_name);
		this.setRef_item_no(ref_item_no);
	}

	public String getItem_en_name() {
		return item_en_name;
	}

	public void setItem_en_name(String item_en_name) {
		this.item_en_name = item_en_name;
	}

	public String getItem_kor_name() {
		return item_kor_name;
	}

	public void setItem_kor_name(String item_kor_name) {
		this.item_kor_name = item_kor_name;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public int getRef_item_no() {
		return ref_item_no;
	}

	public void setRef_item_no(int ref_item_no) {
		this.ref_item_no = ref_item_no;
	}

	public String getRef_table_name() {
		return ref_table_name;
	}

	public void setRef_table_name(String ref_table_name) {
		this.ref_table_name = ref_table_name;
	}

	public ReportTable getReportTable() {
		return reportTable;
	}

	public void setReportTable(ReportTable reportTable) {
		this.reportTable = reportTable;
	}

	public int getRi_no() {
		return ri_no;
	}

	public void setRi_no(int ri_no) {
		this.ri_no = ri_no;
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

	
	public int getRt_no() {
		return rt_no;
	}

	
	public void setRt_no(int rt_no) {
		this.rt_no = rt_no;
	}

	public String getRef_item_id() {
		return ref_item_id;
	}

	public void setRef_item_id(String ref_item_id) {
		this.ref_item_id = ref_item_id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
}
