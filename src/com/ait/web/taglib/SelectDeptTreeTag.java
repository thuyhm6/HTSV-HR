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
public class SelectDeptTreeTag extends RequestContextAwareTag {

	protected String name = null;

	protected String selected = null;
	
	protected String limit = null; 
	
	protected String parameter = null;
	
	@Autowired
	private HrmDao hrmDao;	

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
			limit = "hr";
		}
		if (parameter != null) {
			parameter = eval("parameter", parameter, Object.class).toString();
		} else {
			parameter = "";
		}
		
		hrmDao = (HrmDao)getRequestContext().getWebApplicationContext()
									.getAutowireCapableBeanFactory()
									.getBean("hrmDaoImpl");

		
		long sysLong = System.currentTimeMillis() ;
		
		List deptList=new ArrayList();
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		
		if(limit.equals("hr")){
			deptList = hrmDao.getDeptTree("hrm.getDeptTreeForHr",paramMap);
		}else if(limit.equals("ar")){
			deptList = hrmDao.getDeptTree("hrm.getDeptTreeForAr",paramMap);
		}else if(limit.equals("pa")){
			deptList = hrmDao.getDeptTree("hrm.getDeptTreeForPa",paramMap);
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
			scriptStr.append("	onClick: onClick_" + sysLong + "");
			scriptStr.append("}");
			scriptStr.append("};");
			scriptStr.append("var zNodes_" + sysLong + " = ");
			
			scriptStr.append(JsonUtil.writeInternal(deptList)) ;
			
			scriptStr.append(";");
			
			scriptStr.append("function onClick_" + sysLong + "(event, treeId, treeNode) {");
			if (parameter != null && parameter.length() > 0){
				scriptStr.append("setWorkArea(treeNode,'" + name + "');");
			}
			scriptStr.append("var deptName = $(\"#deptName_" + sysLong + "\").val();");
			scriptStr.append("if(deptName == treeNode.DEPTNAME){");
			scriptStr.append("$(\"#deptName_" + sysLong + "\").attr(\"value\", '');");
			scriptStr.append("$(\":input[sysLong='" + sysLong + "']\").attr(\"value\", '');");
			
			//jjy
			scriptStr.append("$(\"#deptContent_" + sysLong + "\").fadeOut(\"fast\");");
			
			scriptStr.append("}else{");
			scriptStr.append("$(\"#deptName_" + sysLong + "\").attr(\"value\", treeNode.DEPTNAME);");
			scriptStr.append("$(\":input[sysLong='" + sysLong + "']\").attr(\"value\", treeNode.DEPTNO);");
			
			//jjy
			scriptStr.append("$(\"#deptContent_" + sysLong + "\").fadeOut(\"fast\");");
			
			scriptStr.append("}");
			scriptStr.append("}");
			scriptStr.append("function showTree_" + sysLong + "() {");
			//先清除文本框的值
			scriptStr.append("$(\"#deptName_"+sysLong+"\").val(\"\");");
			//清除文本框的值
			scriptStr.append("$(\"#seach_DEPTNO\").val(\"\");");
			scriptStr.append("var cityObj = $(\"#deptName_" + sysLong + "\");");
			scriptStr.append("var cityOffset = $(\"#deptName_" + sysLong + "\").offset();");
			scriptStr.append("$(\"#deptContent_" + sysLong + "\").offset({top:cityOffset.top + \"px\", left:cityOffset.left + cityObj.outerHeight() + \"px\" }).slideDown(\"fast\");");
			scriptStr.append("$(\"body\").bind(\"mousedown\", onBodyDown_" + sysLong + ");");
			scriptStr.append("}");
			scriptStr.append("function hideMenu_" + sysLong + "() {");
			scriptStr.append("$(\"#deptContent_" + sysLong + "\").fadeOut(\"fast\");");
			scriptStr.append("$(\"body\").unbind(\"mousedown\", onBodyDown_" + sysLong + ");");
			scriptStr.append("}");
			scriptStr.append("function onBodyDown_" + sysLong + "(event) {");
			scriptStr.append("if (!(event.target.id == \"menuBtn\" || event.target.id == \"deptContent_" + sysLong + "\" || $(event.target).parents(\"#deptContent_" + sysLong + "\").length>0)) {");
			scriptStr.append("hideMenu_" + sysLong + "();");
			scriptStr.append("}");
			scriptStr.append("}");
			scriptStr.append("$(document).ready(function(){");
			scriptStr.append("$.fn.zTree.init($(\"#deptTree_" + sysLong + "\"), setting_" + sysLong + ", zNodes_" + sysLong + ");");
			if (selected != null && selected.length() > 0){
				scriptStr.append("var $treeObj_" + sysLong + " = $.fn.zTree.getZTreeObj(\"deptTree_" + sysLong + "\");");
				scriptStr.append("var $nodeObj_" + sysLong + " = $treeObj_" + sysLong + ".getNodeByParam('DEPTNO','" + selected + "');");
				scriptStr.append("if($nodeObj_" + sysLong + " != null ){");
				scriptStr.append("$treeObj_" + sysLong + ".selectNode($nodeObj_" + sysLong + ");");
				scriptStr.append("onClick_" + sysLong + "(null, null, $nodeObj_" + sysLong + ");");
				scriptStr.append("}");
			}
			
			scriptStr.append("});");
			
			scriptStr.append("</SCRIPT>");			
			
			//生成INPUT和隐藏字段
			scriptStr.append("<input id=\"deptName_" + sysLong + "\" type=\"text\" onclick=\"showTree_" + sysLong + "()\" readonly  />");
			scriptStr.append("<input id=\"" + name + "\" name=\"" + name + "\" type=\"hidden\" sysLong=\"" + sysLong + "\"/>");
			scriptStr.append("<div id=\"deptContent_" + sysLong + "\" style=\"display:none; position: absolute;z-index:100;overflow:auto;height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;\" >");
			scriptStr.append("<ul id=\"deptTree_" + sysLong + "\" class=\"ztree\" style=\"margin-top:0; width:300px;overflow:auto\"></ul>");
			scriptStr.append("</div>");
			
			//System.out.println(scriptStr);
//			System.out.println(scriptStr.toString());
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

}
