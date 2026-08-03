package com.ait.web.taglib;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.ait.hrm.dao.HrmDao;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@SuppressWarnings("serial")
@Component
public class SelectDeptTreeResumeTag extends RequestContextAwareTag {

	protected String name = null;

	protected String id = null;
	
	protected String selected = null;
	
	protected String limit = null; 
	
	protected String parameter = null;
	
	protected String cpnyId = null;

	protected String style = null;

	protected String clickFun = null;
	
	protected String resumeNo = null;

	protected String current = null;
	
	@Autowired
	private HrmDao hrmDao;	

	/**
	 * tag body
	 */
	@SuppressWarnings("unchecked")
	public int doStartTagInternal() throws JspException {

		name = eval("name", name, Object.class).toString();
		
		id = eval("id", id, Object.class).toString();

		if (selected != null) {
			selected = eval("selected", selected, Object.class).toString();
		} else {
			selected = "";
		}		
		if (cpnyId != null) {
			cpnyId = eval("cpnyId", cpnyId, Object.class).toString();
		} else {
			cpnyId = "";
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

		if (style != null) {
			style = eval("style", style, Object.class).toString();
		} else {
			style = "";
		}

		if (clickFun != null) {
			clickFun = eval("clickFun", clickFun, Object.class).toString();
		} else {
			clickFun = "";
		}
		
		if (resumeNo != null) {
			resumeNo = eval("resumeNo", resumeNo, Object.class).toString();
		} else {
			resumeNo = "";
		}
		
		if (current != null) {
			current = eval("current", current, Object.class).toString();
		} else {
			current = "";
		}
		
		hrmDao = (HrmDao)getRequestContext().getWebApplicationContext()
									.getAutowireCapableBeanFactory()
									.getBean("hrmDaoImpl");

		
		long sysLong = System.currentTimeMillis() ;
		
		List deptList=new ArrayList();
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("RESUME_NO", resumeNo);
		

		if(current != null && "1".equals(current)){
			deptList = hrmDao.getDeptTree("hrm.getDeptTreeForCurrent",paramMap);
			
		}else{
			deptList = hrmDao.getDeptTree("hrm.getDeptTreeForResume",paramMap);
		}
		try {
			StringBuffer scriptStr = new StringBuffer(200) ;
			
			JspWriter writer = pageContext.getOut();
			//生成所需JS
			long timeStamp=System.currentTimeMillis();
			scriptStr.append("<SCRIPT type='text/javascript'>");			
			
			scriptStr.append("var setting_" + sysLong + " = {");
			scriptStr.append("view: {");
			scriptStr.append("	dblClickExpand: false,showLine: true,selectedMulti: false,expandSpeed: \"fast\"");
			scriptStr.append("},");
			scriptStr.append("data: {");
			scriptStr.append("	key: {");
			scriptStr.append("	children:\"children\",name:\"DEPTNAME\",title:\"\"");
			scriptStr.append("},");
			scriptStr.append("	simpleData: {");
			scriptStr.append("		enable:true,idKey:\"DEPTNO\",pIdKey:\"PARENT_DEPT_NO\",rootPId: \"\"");
			scriptStr.append("	}");
			scriptStr.append("},");
			scriptStr.append("callback: {");
			scriptStr.append("	onClick: onClick_" + id + "");
			scriptStr.append("}");
			scriptStr.append("};");
			scriptStr.append("var zNodes_" + sysLong + " = ");
			
			scriptStr.append(JsonUtil.writeInternal(deptList)) ;
			
			scriptStr.append(";");
			
			if(clickFun == null || !"1".equals(clickFun)){
				scriptStr.append("function onClick_" + id + "(event, treeId, treeNode) {");

				scriptStr.append("openOnRight('/org/orgManage/" + id + 
						"?DEPTNO=' + treeNode.DEPTNO + '&RESUME_NO=" + resumeNo + 
						"&currentIndex=' + $(\"#" + id + "_currentIndex\").val() ,'" + id + "_right_unit');");
				
				scriptStr.append("}");
			}

			
			scriptStr.append("function showTree_" + sysLong + "() {");
			scriptStr.append(" var cityObj = $(\"#" + id + "\");");
			scriptStr.append("var cityOffset = $(\"#" + id + "\").offset();");
			scriptStr.append("$(\"#deptContentIcon_" + sysLong + "\").offset({top:cityOffset.top + \"px\", left:cityOffset.left + cityObj.outerHeight() + \"px\" }).slideDown(\"fast\");");
			scriptStr.append("$(\"body\").bind(\"mousedown\", onBodyDown_" + sysLong + ");");
			scriptStr.append("}");
			scriptStr.append("function hideMenu_" + sysLong + "() {");
			scriptStr.append("$(\"#deptContentIcon_" + sysLong + "\").fadeOut(\"fast\");");
			scriptStr.append("$(\"body\").unbind(\"mousedown\", onBodyDown_" + sysLong + ");");
			scriptStr.append("}");
			scriptStr.append("function onBodyDown_" + sysLong + "(event) {");
			scriptStr.append("if (!(event.target.id == \"menuBtn\" || event.target.id == \"deptContentIcon_" + sysLong + "\" || $(event.target).parents(\"#deptContentIcon_" + sysLong + "\").length>0)) {");
			scriptStr.append("hideMenu_" + sysLong + "();");
			scriptStr.append("}");
			scriptStr.append("}");
			scriptStr.append("$(document).ready(function(){");
			scriptStr.append("$.fn.zTree.init($(\"#" + id + "\"), setting_" + sysLong + ", zNodes_" + sysLong + ");");
			if (selected != null && selected.length() > 0){
				scriptStr.append("var $treeObj_" + sysLong + " = $.fn.zTree.getZTreeObj(\"" + id + "\");");
				scriptStr.append("var $nodeObj_" + sysLong + " = $treeObj_" + sysLong + ".getNodeByParam('DEPTNO','" + selected + "');");
				scriptStr.append("if($nodeObj_" + sysLong + " != null ){");
				scriptStr.append("$treeObj_" + sysLong + ".selectNode($nodeObj_" + sysLong + ");");
				scriptStr.append("onClick_" + id + "(null, null, $nodeObj_" + sysLong + ");");
				scriptStr.append("}");
			}
			
			scriptStr.append("});");
			
			scriptStr.append("</SCRIPT>");			
			
			//生成INPUT和隐藏字段
			scriptStr.append("<div id=\"deptContentIcon_" + sysLong + "\" style=\" " + style + "\" >");
			scriptStr.append("<ul id=\"" + id + "\" class=\"ztree\" style=\"margin-top:0; width:270px;overflow:auto\"></ul>");
			scriptStr.append("</div>");
			
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCpnyId() {
		return cpnyId;
	}

	public void setCpnyId(String cpnyId) {
		this.cpnyId = cpnyId;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getClickFun() {
		return clickFun;
	}

	public void setClickFun(String clickFun) {
		this.clickFun = clickFun;
	}

	public String getResumeNo() {
		return resumeNo;
	}

	public void setResumeNo(String resumeNo) {
		this.resumeNo = resumeNo;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

}
