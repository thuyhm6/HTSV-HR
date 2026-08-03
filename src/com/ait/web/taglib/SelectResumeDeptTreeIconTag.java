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
public class SelectResumeDeptTreeIconTag extends RequestContextAwareTag {

	protected String name = null;

	protected String id = null;
	
	protected String selected = null;
	
	protected String resumeNo = null;
	
	protected String isHtml = null;

	
	@Autowired
	private HrmDao hrmDao;	

	/**
	 * tag body
	 */
	@SuppressWarnings("unchecked")
	public int doStartTagInternal() throws JspException {

		name = eval("name", name, Object.class).toString();
		
		id = eval("id", id, Object.class).toString();
		
		if(isHtml != null){
			isHtml = eval("isHtml", isHtml, Object.class).toString();
		}else{
			isHtml = "";
		}

		if (selected != null) {
			selected = eval("selected", selected, Object.class).toString();
		} else {
			selected = "";
		}		
		if (resumeNo != null) {
			resumeNo = eval("resumeNo", resumeNo, Object.class).toString();
		} else {
			resumeNo = "";
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
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("RESUME_NO", resumeNo);
		String cpnyId = admin.getCpnyId();
		
		deptList = hrmDao.getDeptTree("hrm.getDeptTreeForResume",paramMap);
		
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
			
			

			scriptStr.append("var deptName = $(\"#" + id + "\").val();");
			scriptStr.append("if(deptName == treeNode.DEPTNAME){");
			scriptStr.append("$(\"#" + id + "\").attr(\"value\", '');");
			
			//jjy
			scriptStr.append("$(\"#deptContentIcon_" + sysLong + "\").fadeOut(\"fast\");");
			
			scriptStr.append("}else{");
			scriptStr.append("$(\"#" + id + "\").attr(\"value\", treeNode.DEPTNAME);");
			scriptStr.append("$(\":input[sysLong='" + id + "']\").attr(\"value\", treeNode.DEPTNO);");
			
			//jjy
			scriptStr.append("$(\"#deptContentIcon_" + sysLong + "\").fadeOut(\"fast\");");
			
			scriptStr.append("}");
			
			scriptStr.append("var deptNo = treeNode.DEPTNO;");
			scriptStr.append("var resumeNo = " + resumeNo + ";");
			if(isHtml.equals("viewAddOrgInfo")){//判断是否是指定页面
			scriptStr.append("			$.ajax({");
			scriptStr.append("				type: 'post',");
			scriptStr.append("				url:'/org/orgManage/getDeptNoMax',");
			scriptStr.append("				data:[{name: 'parent_dept_no', value: deptNo},{name: 'resumeNo', value: resumeNo},{name: 'cpnyId', value: '"+admin.getCpnyId()+"'}],");
			scriptStr.append("				dataType:'json',");	
			scriptStr.append("				cache: false,");
			scriptStr.append("				success: function(res){");
			scriptStr.append("								$('#DEPTNO').val(res.DeptNoMax)");
			scriptStr.append("						},");
			scriptStr.append("				error: DWZ.ajaxError");
			scriptStr.append("			});");			
			}
			
			scriptStr.append("}");
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
			scriptStr.append("$.fn.zTree.init($(\"#deptTreeIcon_" + sysLong + "\"), setting_" + sysLong + ", zNodes_" + sysLong + ");");
			if (selected != null && selected.length() > 0){
				scriptStr.append("var $treeObj_" + sysLong + " = $.fn.zTree.getZTreeObj(\"deptTreeIcon_" + sysLong + "\");");
				scriptStr.append("var $nodeObj_" + sysLong + " = $treeObj_" + sysLong + ".getNodeByParam('DEPTNO','" + selected + "');");
				scriptStr.append("if($nodeObj_" + sysLong + " != null ){");
				scriptStr.append("$treeObj_" + sysLong + ".selectNode($nodeObj_" + sysLong + ");");
				scriptStr.append("onClick_" + sysLong + "(null, null, $nodeObj_" + sysLong + ");");
				scriptStr.append("}");
			}
			
			scriptStr.append("});");
			
			scriptStr.append("</SCRIPT>");			
			
			//生成INPUT和隐藏字段
			scriptStr.append("<span class=\"tree_icon\"" + " onclick=\"showTree_" + sysLong + "()\"/>");
			scriptStr.append("<div id=\"deptContentIcon_" + sysLong + "\" style=\"display:none; position: absolute;z-index:100;overflow:auto;height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;\" >");
			scriptStr.append("<ul id=\"deptTreeIcon_" + sysLong + "\" class=\"ztree\" style=\"margin-top:0; width:300px;overflow:auto\"></ul>");
			scriptStr.append("</div>");
			
			writer.print(scriptStr.toString()) ;
		} catch (Exception ex) {
			throw new JspTagException(ex.getMessage());
		} 

		return EVAL_PAGE;
	}

	public String getIsHtml() {
		return isHtml;
	}

	public void setIsHtml(String isHtml) {
		this.isHtml = isHtml;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResumeNo() {
		return resumeNo;
	}

	public void setResumeNo(String resumeNo) {
		this.resumeNo = resumeNo;
	}

}
