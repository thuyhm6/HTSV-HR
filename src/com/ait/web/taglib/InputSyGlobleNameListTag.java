package com.ait.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.RequestContextAwareTag;



@SuppressWarnings("serial")
@Component
public class InputSyGlobleNameListTag extends RequestContextAwareTag {

	protected String name = null;
	
	protected String id = null;

	protected String selected = null;
	
	protected String limit = null; 
	
	protected String parameter = null;

	protected String cpnyId = null;
	
	protected String parentNo = null;

	/**
	 * tag body
	 */
	@SuppressWarnings("unchecked")
	public int doStartTagInternal() throws JspException {


		name = eval("name", name, Object.class).toString();
		id = eval("id", id, Object.class).toString();

		if (cpnyId != null) {
			cpnyId = eval("cpnyId", cpnyId, Object.class).toString();
		} else {
			cpnyId = "";
		}		
		
		if (selected != null) {
			selected = eval("selected", selected, Object.class).toString();
		} else {
			selected = "";
		}		
		
		if (limit != null) {
			limit = eval("limit", limit, Object.class).toString();
		} else {
			limit = "hr";
		}
		if (parameter != null) {
			parameter = eval("parameter", parameter, Object.class).toString();
		} else {
			parameter = "";
		}
		
		long sysLong = System.currentTimeMillis() ;
		
		String cpnyIdStr = "";
		if(!"".equals(cpnyId)){
			cpnyIdStr = ",CPNY_ID:\"" + cpnyId + "\""; 
		}
		try {
			StringBuffer scriptStr = new StringBuffer(200) ;
			
			JspWriter writer = pageContext.getOut();
			//生成所需JS
			long timeStamp=System.currentTimeMillis();
			scriptStr.append("<SCRIPT type='text/javascript'>");			
			
			scriptStr.append(" var ajaxGet_" + sysLong + ";");
			scriptStr.append(" function  ajaxAdd() { ");
			scriptStr.append(" if (ajaxGet_" + sysLong + " != null) { ajaxGet_" + sysLong + ".abort(); } ");
			scriptStr.append(" $.ajaxSettings.global = false; ");
			scriptStr.append(" ajaxGet_" + sysLong + " = $.ajax( { type : \"POST\", url : \"/org/orgManage/getSyGlobleNamesList\", data : { deptName : $(\"#" + id + "\").val() " + cpnyIdStr + " }, ");
			scriptStr.append(" dataType : \"json\", success : function(data) { $('#deptTree_" + sysLong + "').html(\"\"); var html = ''; ");
			scriptStr.append(" if (typeof (data['globleNameList']) != \"undefined\") { $.each(data['globleNameList'], ");
			scriptStr.append(" function(commentIndex, comment) { if(commentIndex%2==0){html += '<li class=\"deptTreeLi\" onclick=\"selectedIt(\\'' + comment['DEPTNAME'] + '\\',\\'' + comment['DEPTNO'] + '\\')\"><span>' + comment['DEPTNO'] + '</span><b>' + comment['DEPTNAME'] + '</b></li>';}else{html +='<li onclick=\"selectedIt(\\'' + comment['DEPTNAME'] + '\\',\\'' + comment['DEPTNO'] + '\\')\"><span>' + comment['DEPTNO'] + '</span><b>' + comment['DEPTNAME'] + '</b></li>';} }); } ");
			scriptStr.append(" $('#deptTree_" + sysLong + "').html(html); $(\"#deptContent_" + sysLong + "\").css(\"display\", \"block\"); } }); $.ajaxSettings.global = true; } ");
			scriptStr.append(" function mouseoverF() { $(\"#" + id + "\").unbind(\"blur\"); $(\"#deptContent_" + sysLong + "\").mouseout( ");
			scriptStr.append(" function() { $('#" + id + "').blur(function() { $('#deptContent_" + sysLong + "').css('display', 'none'); });  } );}");
			scriptStr.append(" function selectedIt(deptName,deptNo) { $('#" + id + "').val(deptName); $('#" + name + sysLong + "').val(deptNo); $('#deptContent_" + sysLong + "').css('display', 'none'); } ");
			scriptStr.append(" function cleanValue() { $('#" + id + "').val(''); $('#" + name + sysLong + "').val('');  $('#deptContent_" + sysLong + "').css('display', 'none');}");
			scriptStr.append(" function closedept() { $('#deptContent_" + sysLong + "').css('display', 'none');}");

			scriptStr.append("</SCRIPT>");			
			
			//生成INPUT和隐藏字段
			scriptStr.append("<input id=\"" + id + "\" name=\"  " + name + " \" type=\"text\" onkeyup=\"ajaxAdd()\" onfocus=\"ajaxAdd()\"/>");
			scriptStr.append("<input id=\"" + name + sysLong + "\" name=\"" + name + "\" type=\"hidden\" value=\"" + selected + "\" sysLong=\"" + id + "\"/>");
			scriptStr.append("<div id=\"deptContent_" + sysLong + "\" onmouseover=\"mouseoverF()\" class=\"deptContent\" style=\"display:none;\"><div class=\"ztree_dept\"><div class=\"ztree_dept_title\"><table width=\"100%\"><tr onclick=\"cleanValue()\"><th style=\"width:30%\">Code</th><th style=\"text-align:center;width:70%\">部门名称</th></tr></table></div><div class=\"ztree_dept_type\">");
			scriptStr.append("<ul id=\"deptTree_" + sysLong + "\" class=\"ztree_dept_table\" ></ul>");
			scriptStr.append("</div><div class=\"ztree_dept_color\"><a href=\"#\" onclick=\"closedept()\" class=\"ztree_dept_color_a\"><span>关闭</span></a></div></div></div>");
			
			writer.print(scriptStr.toString()) ;
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

	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
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
		this.cpnyId = cpnyId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
