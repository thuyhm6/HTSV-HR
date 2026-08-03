package com.ait.web.taglib;

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
import com.ait.sys.service.SysSer;
import com.ait.web.messages.Messages;
import com.ait.web.util.SessionUtil;

@SuppressWarnings("serial")
@Component
public class SelectTableTag extends RequestContextAwareTag {

	protected String name = null;

	protected String selected = null;
	
	protected String limit = null; 
	
	protected String table = null; 
	
	protected String cpnyId = null;
	
	protected String language=null;
	protected String user=null;
	
	

	protected String type=null;
	
	protected String onChangeName = null; 


	



	protected String parameter = null; 
	@Autowired
	private SysSer sysSer;	
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;
	
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
		if (onChangeName != null) {
			onChangeName = eval("onChangeName", onChangeName, Object.class).toString();
		} else {
			onChangeName = "";
		}
		sysSer=(SysSer)getRequestContext().getWebApplicationContext().getAutowireCapableBeanFactory().getBean("sysSerImpl");
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		Map paramMap=new LinkedHashMap();
		paramMap.put("language",Messages.getLanguage(request));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		Map paramMap1=new LinkedHashMap();
		paramMap1.put("TABLENAME",table);
		paramMap1.put("PARENTTABLEFIELD",parameter);
		paramMap1.put("CPNY_ID",cpnyId);
		paramMap1.put("language",language);
		paramMap1.put("interLanguage", language);
		paramMap1.put("user", user);
		paramMap1.put("ADMINID", admin.getAdminID()) ;
		paramMap1.put("CPNYID", admin.getCpnyId());
		paramMap1.put("userNo", admin.getUserNo());
		paramMap1.put("deptNo", admin.getDeptNo());
		paramMap1.put("specialParam", admin.getSpecialParam());
		//String tableName=m1.get("PARENT_TABLE_NAME").toString();
		
		
		
		
		
			
		
		
		
		//List codeList = basicMaintenanceDao.getParamCodeListByTableName(paramMap1) ;
		List codeList =null;
		List codeList1=null;
		if(type.equals("3")){
			 codeList = sysSer.getDeptListByCpnyID(paramMap1);
		}
		else if(type.equals("1")){
			codeList1=sysSer.getSelectTableByHrDept(paramMap1);
		}else if(type.equals("2")){
			codeList = sysSer.getSelectTable(paramMap1);
		}else{
			codeList = sysSer.getSelectTable(paramMap1);
		}
	
		try {			

			JspWriter writer = pageContext.getOut();
			if(type.equals("3")){
				writer.print("<select id='"+name+"' name='"+name+"'");
				
				
				
				
				writer.print(">");
				if(!limit.equals("")&&limit.toLowerCase().equals("all")){
					writer.print("<option value='0'>请选择</option>");
				}
				for(Object map : codeList){
					Map temp = (Map) map;
					writer.print("<option  value='"+temp.get("CODE_NO")+"'");
					if(temp.get("CODE_NO").equals(selected)){
						writer.print(" selected ");
					}
					int level=Integer.parseInt(temp.get("DEPTLEVEL").toString());
					writer.print(">");
					for(int j=0;j<level;j++){
						writer.print("&nbsp;&nbsp;");
					}
					writer.print(temp.get("CODE_NAME")+"</option>");
				}
				writer.print("</select>");
			}else {
			
			
			
			
			writer.print("<select id='"+name+"' name='"+name+"'");
			if(!onChangeName.equals("")){
				writer.print(" onchange='"+onChangeName+"'");
			}
			
			
			writer.print(" >");
			if(!limit.equals("")&&limit.toLowerCase().equals("all")){
				writer.print("<option value='0'>请选择</option>");
			}
			
			for(Object map : codeList){
				Map temp = (Map) map;
				writer.print("<option  value='"+temp.get("CODE_NO")+"'");
				if(temp.get("CODE_NO").equals(selected)){
					writer.print(" selected ");
				}
				writer.print(">"+temp.get("CODE_NAME")+"</option>");
			}
			writer.print("</select>");
			}
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

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getCpnyId() {
		return cpnyId;
	}

	public void setCpnyId(String cpnyId) {
		this.cpnyId= cpnyId;
	}
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getOnChangeName() {
		return onChangeName;
	}

	public void setOnChangeName(String onChangeName) {
		this.onChangeName = onChangeName;
	}
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
