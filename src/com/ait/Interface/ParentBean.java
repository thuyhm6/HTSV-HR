package com.ait.Interface;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("serial")
public abstract class ParentBean implements Serializable{

	public static final String DEFAULT_LANGUAGE_PREFERENCE = "en";

	public static final String DEFAULT_COUNTRY_PREFERENCE = "US";

	public static final Locale DEFAULT_Locale = new Locale("en", "US");

	private Integer id ;

	private Date created;

	private String createdBy;

	private Date updated;

	private String updatedBy;
	
	private Boolean useYn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getUseYn() {
		return useYn;
	}

	public void setUseYn(Boolean useYn) {
		this.useYn = useYn;
	}
}
