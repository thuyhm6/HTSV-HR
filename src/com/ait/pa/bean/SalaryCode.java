package com.ait.pa.bean;

public class SalaryCode {
    private String item_no;
    
    private String item_name;
    
    private String project_type;
    
    private String item_id;
    
    private String data_type;
    
    private String descr;

	public SalaryCode() {
		super();
	}
	
	

	public SalaryCode(String item_no, String item_name, String project_type,
			String item_id, String data_type, String descr) {
		super();
		this.item_no = item_no;
		this.item_name = item_name;
		this.project_type = project_type;
		this.item_id = item_id;
		this.data_type = data_type;
		this.descr = descr;
	}



	public String getItem_no() {
		return item_no;
	}

	public void setItem_no(String item_no) {
		this.item_no = item_no;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getProject_type() {
		return project_type;
	}

	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
}
