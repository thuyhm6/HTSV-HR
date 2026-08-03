package com.ait.web.taglib;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.ait.org.service.OrgManageSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.sys.dao.CompanyDao;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.SyLanguageSer;
import com.ait.sys.service.SysSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

public class SyCompanyTag extends RequestContextAwareTag {
	
	protected String activity = null;
	
	protected String name = null;
	
	protected String selected = null;
	
	protected String limit = null; 
	
	protected String language = null; 
	
	
	protected String onChangeName = null; 
	
	protected String onClickName = null; 
	
	protected String cpnyId = null;
	
	protected String target = null;
	
	protected String disabled = null;
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getCpnyId() {
		return cpnyId;
	}

	public void setCpnyId(String cpnyId) {
		this.cpnyId = cpnyId;
	}

	public String getOnChangeName() {
		return onChangeName;
	}

	public void setOnChangeName(String onChangeName) {
		this.onChangeName = onChangeName;
	}

	public String getOnClickName() {
		return onClickName;
	}

	public void setOnClickName(String onClickName) {
		this.onClickName = onClickName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	@Autowired
	private CompanyDao companyDao;	

	/**
	 * tag body
	 */
	@SuppressWarnings("unchecked")
	public int doStartTagInternal() throws JspException {

		name = eval("name", name, Object.class).toString();
		
		if (selected != null) {
			selected = eval("selected", selected, Object.class).toString();
		} else {
			selected = "";
		}		
		
		if (limit != null) {
			limit = eval("limit", limit, Object.class).toString();
		} else {
			limit = "";
		}
		
		if (activity != null) {
			activity = eval("activity", activity, Object.class).toString();
		} else {
			activity = "";
		}	
		
		if (language != null) {
			language = eval("language", language, Object.class).toString();
		} else {
			language = "";
		}	
		if (onChangeName != null) {
			onChangeName = eval("onChangeName", onChangeName, Object.class).toString();
		} else {
			onChangeName = "";
		}
		if (onClickName != null) {
			onClickName = eval("onClickName", onClickName, Object.class).toString();
		} else {
			onClickName = "";
		}
		if(cpnyId != null){
			cpnyId = eval("cpnyId", cpnyId, Object.class).toString();
		}else{
			cpnyId = "";
		}
		if(target != null){
			target = eval("target", target, Object.class).toString();
		}else{
			target = "";
		}
		if(disabled != null){
			disabled = eval("disabled", disabled, Object.class).toString();
		}else{
			disabled = "";
		}
		
		
		Map paramMap=new LinkedHashMap();
		
		paramMap.put("ACTIVITY",activity);
		
		paramMap.put("interLanguage",language);
		
		
		
		companyDao = (CompanyDao)getRequestContext().getWebApplicationContext().getAutowireCapableBeanFactory().getBean("companyDaoImpl");
		
		List cnpyList = companyDao.getCompanyItemAllList(paramMap) ;
		
		try {			

			JspWriter writer = pageContext.getOut();
			
			if(!"C02".equals(cpnyId) && "config".equals(target)){
				writer.print("<select class='select' id='"+name+"' name='"+name+"' disabled='disabled'");
			}else{
				writer.print("<select class='select' id='"+name+"' name='"+name+"'");
			}
			
			if(!onClickName.equals("")){
				writer.print("onclick='"+onClickName+"'");
			}
			
			if(!onChangeName.equals("")){
				writer.print("onchange='"+onChangeName+"'");
			}
			if(!disabled.equals("")){
				writer.print("disabled='"+disabled+"'");
			}
			
			
			
			writer.print(">");

			if(!limit.equals("")&&limit.toLowerCase().equals("all")&&"login".equals(target)){
				writer.print("<option value=''>"+TipMessage.getTipMessage("pa.insurance.title.pleaseChoose",language)+"</option>");
			}
			
			for(Object map : cnpyList){
				Map temp = (Map) map;
				writer.print("<option value='"+temp.get("CPNY_ID")+"'");
				if("config".equals(target)){
					if(cpnyId.equals(temp.get("CPNY_ID"))){
						writer.print(" selected ");
					}
				}
				if(temp.get("CPNY_ID").equals(selected)){
					writer.print(" selected ");
				}
				writer.print(">"+temp.get("CONTENT")+"</option>");
			}
			writer.print("</select>");
			
		} catch (Exception ex) {
			throw new JspTagException(ex.getMessage());
		} 

		return EVAL_PAGE;
	}

	@SuppressWarnings("unchecked")
	private Object eval(String attName, String attValue, Class clazz) throws JspException {
		Object obj = ExpressionEvaluatorManager.evaluate(attName, attValue, clazz, this, pageContext);
		if (obj == null) {
			return "";
		} else {
			return obj;
		}
	}

}
