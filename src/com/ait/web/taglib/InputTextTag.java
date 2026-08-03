/*
 * @(#)DateTag.java 1.0 2006-12-11 下午01:14:31
 *
 *Copyright 2001 - 2006 AIT. All Rights Reserved.
 */
package com.ait.web.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;

/**
 * 
* @ClassName: TextDate 
* @Description: TODO
* @author zhengxiaochen zhengxiaochen@ait.net.cn
* @date 2013-9-9 下午04:44:47 
*
 */

@SuppressWarnings("serial")
@Component
public class InputTextTag extends RequestContextAwareTag {

	protected String id = null;//编号
	
	protected String name = null;//名称
	
	protected String value = null;//输出值
	
	protected String alt = null;//提示信息
	
	protected String inputType = null;//输入值类型
	
	protected String maxLength = null;
	
	protected String onChange = null;
	
	protected String onKeyUp = null;

	protected String inputSize = null;

	protected String style = null;
	
	public int doStartTagInternal() throws JspException {

		if(id != null){
			id = eval("id",id,Object.class).toString();
		}else{
			id = "";
		}
		
		if(name != null){
			name = eval("name",name,Object.class).toString();
		}else{
			name = "";
		}
		
		if(alt != null){
			alt=eval("alt",alt,Object.class).toString();
		}else{
			alt = "";
		}
		
		if(inputType != null){
			inputType = eval("inputType",inputType,Object.class).toString();
		}else{
			inputType = "";
		}

		if(maxLength != null){
			maxLength = eval("maxLength",maxLength,Object.class).toString();
		}else{
			maxLength = "";
		}
		
		if(value != null){
			value = eval("value",value,Object.class).toString();
		}else{
			value = "";
		}
		
		if(onChange != null){
			onChange = eval("onChange",onChange,Object.class).toString();
		}else{
			onChange = "";
		}
		if(onKeyUp != null){
			onKeyUp = eval("onKeyUp",onKeyUp,Object.class).toString();
		}else{
			onKeyUp = "";
		}
		     
		if(inputSize != null){
			inputSize = eval("inputSize",inputSize,Object.class).toString();
		}else{
			inputSize = "";
		}
		
		if(style != null){
			style = eval("style",style,Object.class).toString();
		}else{
			style = "";
		}
		
		JspWriter writer = pageContext.getOut();
		
		StringBuffer sbString=new StringBuffer();
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		String language = admin.getLanguage();

		try {
			sbString.append("<script type=\"text/javascript\">");
			
			sbString.append("$(document).ready(function(){");
			
			sbString.append("	$(\"input[type='text'][id='" + id + "'][name='" + name + "'][inputType='" + inputType + "']\").blur(function(){");
			
			if("date".equals(inputType)){//日期类型验证
				
				sbString.append("		var regxText=/^(?:19[7-9]\\d|2\\d{3,3})(?:0[1-9]|1[0-2])$/;");

			}
			
			if("MMYYYYdate".equals(inputType)){//日期类型验证
				
				sbString.append("		var regxText=/^(?:0[1-9]|1[0-2])(?:19[7-9]\\d|2\\d{3,3})$/;");

			}
			
			if("number".equals(inputType)){//数字类型验证
				
				sbString.append("		var regxText=/^[0-9]+(.[0-9]{2})?$/;");
			
			}
			
			sbString.append("		if($(this).val() != null && $(this).val() != \"\"){");

			sbString.append("			if($(this).attr(\"inputType\") == '" + inputType + "'){");
			
			sbString.append("				if(!regxText.test($(this).val())){");

			if("zh".equals(language)){
				if("date".equals(inputType)){
					sbString.append("				alertMsg.error(\"日期格式不正确\");");
				}
				if("MMYYYYdate".equals(inputType)){
					sbString.append("				alertMsg.error(\"日期格式不正确\");");
				}
				if("number".equals(inputType)){
					sbString.append("				alertMsg.error(\"数字格式不正确\");");
				}
			}else{
				if("date".equals(inputType)){
					sbString.append("				alertMsg.error(\"Định dạng thời gian không đúng\");");
				}
				if("MMYYYYdate".equals(inputType)){
					sbString.append("				alertMsg.error(\"Định dạng thời gian không đúng\");");
				}
				if("number".equals(inputType)){
					sbString.append("				alertMsg.error(\"Định dạng số không đúng\");");
				}
			}
			
			sbString.append("					return;");
			
			sbString.append("				}");
			
			sbString.append("			}");
			
			sbString.append("		}");
			
			sbString.append("	});");
			
			sbString.append("});");
			
			sbString.append("</script>");

			sbString.append("<input type=\"text\" ");
			
			sbString.append(" id=\"" + id + "\" name=\"" + name + "\" class=\"required textInput\" inputType=\"" + inputType + "\" ");
			
			sbString.append(" maxlength=\"" + maxLength + "\" size=\"" + inputSize + "\" value =\"" + value + "\" alt =\"" + alt + "\" ");
			
			sbString.append("  style =\"" + style + "\" ");
			
			if(!"".equals(onChange)){
				sbString.append(" onChange=\"" + onChange + "\" ");
			}
			
			if(!"".equals(onKeyUp)){
				sbString.append("onKeyUp=\"" + onKeyUp + "\" ");
			}
			
			sbString.append(" /> ");
			
			writer.println(sbString.toString());
			
		} catch (IOException ex) {
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public String getOnKeyUp() {
		return onKeyUp;
	}

	public void setOnKeyUp(String onKeyUp) {
		this.onKeyUp = onKeyUp;
	}

	public String getInputSize() {
		return inputSize;
	}

	public void setInputSize(String inputSize) {
		this.inputSize = inputSize;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}
