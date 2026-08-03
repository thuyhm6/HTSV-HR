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
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.web.messages.Messages;
import com.ait.web.util.SessionUtil;

@SuppressWarnings("serial")
@Component
public class SelectSyCodeTag extends RequestContextAwareTag {

	protected String name = null;
	
	protected String parentNo = null;

	protected String selected = null;
	
	protected String limit = null;
	
	protected String disabled = null; 
	
	protected String onChangeName = null; 
	
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;	

	/**
	 * tag body
	 */
	@SuppressWarnings("unchecked")
	public int doStartTagInternal() throws JspException {

		name = eval("name", name, Object.class).toString();
		
		if(!"".equals(parentNo) && parentNo != null){
			parentNo = eval("parentNo", parentNo, Object.class).toString();
		}else{
			parentNo = null;
		}
		
		if (selected != null) {
			selected = eval("selected", selected, Object.class).toString();
		} else {
			selected = "";
		}
		
		if (disabled != null) {
			disabled = eval("disabled", disabled, Object.class).toString();
		} else {
			disabled = "";
		}	
		
		if (limit != null) {
			limit = eval("limit", limit, Object.class).toString();
		} else {
			limit = "";
		}
		if (onChangeName != null) {
			onChangeName = eval("onChangeName", onChangeName, Object.class).toString();
		} else {
			onChangeName = "";
		}
		
		basicMaintenanceDao = (BasicMaintenanceDao)getRequestContext().getWebApplicationContext().getAutowireCapableBeanFactory().getBean("basicMaintenanceDaoImpl");
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap=new LinkedHashMap();
		List codeList = new ArrayList();
		if(parentNo != null){
			paramMap.put("PARENT_CODE_NO",parentNo);
			paramMap.put("language",Messages.getLanguage(request));
			paramMap.put("interLanguage",admin.getLanguage());
			
			codeList = basicMaintenanceDao.getCodeListByParentCode(paramMap, -1, -1) ;
		}
		
		try {			

			JspWriter writer = pageContext.getOut();
			
			if (disabled == null || disabled.equals("")) {
				writer.print("<select id='"+name+"' name='"+name+"'");
			} else {
				writer.print("<select id='"+name+"' name='"+name+"' disabled='"+disabled+"' ");
			}	

			if(!onChangeName.equals("")){
				writer.print("onchange='"+onChangeName+"'");
			}
			writer.print(">");
			
			if(!limit.equals("")&&limit.toLowerCase().equals("all")){
				writer.print("<option value=''>请选择</option>");
			}
			
			if(parentNo != null){
				for(Object map : codeList){
					Map temp = (Map) map;
					writer.print("<option value='"+temp.get("CODE_NO")+"'");
					if(temp.get("CODE_NO").equals(selected)){
						writer.print(" selected ");
					}
					writer.print(">"+temp.get("CONTENT")+"</option>");
				}
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

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	
	public String getParentNo() {
		return parentNo;
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

	public String getOnChangeName() {
		return onChangeName;
	}

	public void setOnChangeName(String onChangeName) {
		this.onChangeName = onChangeName;
	}
	
}
