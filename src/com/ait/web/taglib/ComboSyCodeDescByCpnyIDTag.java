package com.ait.web.taglib;

import java.util.Collection;
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
import com.ait.sys.bean.CodeBean;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.web.messages.Messages;
import com.ait.web.util.CodeUtil;
import com.ait.web.util.SessionUtil;

@SuppressWarnings("serial")
@Component
public class ComboSyCodeDescByCpnyIDTag extends RequestContextAwareTag {

	protected String name = null;
	
	protected String parentNo = null;

	protected String id = null;

	protected String selected = null;
	
	protected String limit = null; 
	
	protected String cnpyID = null;
	
	protected String onChangeName = null; 
	
	protected String onClickName = null; 
	
	//是否按照汉字的拼音进行排序（PY--拼音排序；EN--英语排序；ZH--汉字排序）
	protected String orderType = null;
	
	protected String disabled = null;
	
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;	

	/**
	 * tag body
	 */
	@SuppressWarnings("unchecked")
	public int doStartTagInternal() throws JspException {

		name = eval("name", name, Object.class).toString();
		parentNo = eval("parentNo", parentNo, Object.class).toString();
		
		if (id != null) {
			id = eval("id", id, Object.class).toString();
		} else {
			id = "";
		}		
		
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
		
		if (cnpyID != null) {
			cnpyID = eval("cnpyID", cnpyID, Object.class).toString();
		} else {
			cnpyID = "";
		}
		
		if (orderType != null) {
			orderType = eval("orderType", orderType, Object.class).toString();
		} else {
			orderType = "";
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
		if (disabled != null) {
			disabled = eval("disabled", disabled, Object.class).toString();
		} else {
			disabled = "";
		}
		
		basicMaintenanceDao = (BasicMaintenanceDao)getRequestContext().getWebApplicationContext().getAutowireCapableBeanFactory().getBean("basicMaintenanceDaoImpl");
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		try {
			
			JspWriter writer = pageContext.getOut();
			
			if(id!=null && !"".equals(id)){
				writer.print("<select id='"+id+"' name='"+name+"'");
			}else{
				writer.print("<select id='"+name+"' name='"+name+"'");
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
			
			
			
			
			
			
			if(!limit.equals("")&&limit.toLowerCase().equals("all")){
//				if(!parentNo.equals("123444")){//调令事由
//					writer.print("<option value=''>请选择</option>");
//				}

				if("ko".equals(admin.getLanguage())){
				    writer.print("<option value=''>선택하세요</option>");
				}else if("zh".equals(admin.getLanguage())){
					writer.print("<option value=''>请选择</option>");
				}else if("vi".equals(admin.getLanguage())){
					writer.print("<option value=''>请选择</option>");
				}else{
					writer.print("<option value=''>select</option>");
				}

			}
			
			if(CodeUtil.getCodeMap().size() == 0){
				
				Map paramMap=new LinkedHashMap();
				paramMap.put("PARENT_CODE_NO",parentNo);
				paramMap.put("language",Messages.getLanguage(request));
				paramMap.put("interLanguage",admin.getLanguage());
				paramMap.put("CPNY_ID",cnpyID);
				paramMap.put("ORDER_TYPE",orderType);
				
				List codeList = basicMaintenanceDao.getSyCodeDescByCpnyID(paramMap) ;
				
				for(Object map : codeList){
					Map temp = (Map) map;
					writer.print("<option value='"+temp.get("CODE_NO")+"'");
					if(temp.get("CODE_NO").equals(selected)){
						writer.print(" selected ");
					}
					writer.print(">"+temp.get("CODE_NAME")+"</option>");
				}
				
			}
			else{
				
				CodeBean parentCode = CodeUtil.getCodeMap().get(parentNo) ;
				
				if(parentCode != null && parentCode.getCpnyMap().get(cnpyID) != null){
					Collection<CodeBean> childCodeBeanList = parentCode.getChildCodeMap().values() ;
					
					for(CodeBean codeBean : childCodeBeanList){
						
						if(codeBean.getCpnyMap().get(cnpyID) != null){
							if(codeBean.getLanguageMap().get(admin.getLanguage()) != null){
								writer.print("<option value='" + codeBean.getCodeNo() + "'");
								if(codeBean.getCodeNo().equals(selected)){
									writer.print(" selected ");
								}
								writer.print(">" + codeBean.getLanguageMap().get(admin.getLanguage()) + "</option>");
							}
						}
						
					}
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getCnpyID() {
		return cnpyID;
	}

	public void setCnpyID(String cnpyID) {
		this.cnpyID = cnpyID;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	
	public String getDisabled() {
		return disabled;
	}
	
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
}
