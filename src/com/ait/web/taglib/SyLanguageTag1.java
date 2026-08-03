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
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.ait.org.service.OrgManageSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.SyLanguageSer;
import com.ait.sys.service.SysSer;
import com.ait.web.messages.Messages;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

public class SyLanguageTag1 extends RequestContextAwareTag {
	
	protected String name = "proName_";//所有页面默认名字proName_ + 语言
	
	protected String languageNo = null;

	private SyLanguageSer syLanguageSer;
	
	protected String deptNameYn = null;
	
	protected String readonlyName = null;
	
	private OrgManageSer orgManageSer;
	
	@Override
	public int doStartTagInternal() throws JspException {

		if (languageNo != null) {
			languageNo = eval("languageNo", languageNo, Object.class).toString();
		} else {
			languageNo = "";
		}
		if (deptNameYn != null) {
			deptNameYn = eval("deptNameYn", deptNameYn, Object.class).toString();
		} else {
			deptNameYn = "";
		}
		
		List languageList = new ArrayList();
		
		syLanguageSer=(SyLanguageSer)getRequestContext().getWebApplicationContext().getAutowireCapableBeanFactory().getBean("syLanguageSerImpl");
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		String localLanguage = admin.getLanguage() != null ? admin.getLanguage() : "";
		
		Map paramMap=new LinkedHashMap();
		if(deptNameYn==null||(deptNameYn!=null&&deptNameYn.equals(""))){
			if(languageNo.equals("")){
				languageList = syLanguageSer.getSyLanguageListByActivity();
			}else{
				paramMap.put("NO",languageNo);
				languageList = syLanguageSer.getSyLanguageNameListByActivity(paramMap);
			}
		}else{
			orgManageSer=(OrgManageSer)getRequestContext().getWebApplicationContext().getAutowireCapableBeanFactory().getBean("orgManageSerImpl");
			if(languageNo.equals("")){
				
				languageList = orgManageSer.getDeptNameListByActivity();
			}else{
				paramMap.put("DEPTNO",languageNo);
				languageList = orgManageSer.getDeptNameNameListByActivity(paramMap);
			}
		}
		
		
		try {			

			JspWriter writer = pageContext.getOut();
			
			for(int i = 0;i < languageList.size();i++){
				Map paramObj = new LinkedHashMap();
				paramObj = (Map)languageList.get(i);
				writer.print("<dl>");
				writer.print("<dt>"+paramObj.get("DESCRIPTION")+"名称</dt>");
				writer.print("<dd>");
				writer.print("<input id='" + name + paramObj.get("LANGUAGE")+"' name='" + name + paramObj.get("LANGUAGE")+"' ");
				writer.print(" value='"+StringUtil.checkNull(paramObj.get("CONTENT"))+"'");// readonly=" + readlonyName + " " +
				writer.print(" readonly='" + readonlyName + "'");
				if(!localLanguage.equals("") &&
						localLanguage.equals(StringUtil.checkNull(paramObj.get("LANGUAGE")))){
					writer.print(" class='required'/>");
				}
				writer.print("</dd>");
				writer.print("</dl>");
				writer.toString();
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
	
	
	public String getReadonlyName() {
		return readonlyName;
	}

	public void setReadonlyName(String readonlyName) {
		this.readonlyName = readonlyName;
	}

	public String getLanguageNo() {
		return languageNo;
	}

	public void setLanguageNo(String languageNo) {
		this.languageNo = languageNo;
	}

	public String getDeptNameYn() {
		return deptNameYn;
	}

	public void setDeptNameYn(String deptNameYn) {
		this.deptNameYn = deptNameYn;
	}

}
