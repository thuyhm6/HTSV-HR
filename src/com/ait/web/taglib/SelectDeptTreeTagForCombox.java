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

@SuppressWarnings({ "serial", "unused" })
@Component
public class SelectDeptTreeTagForCombox extends RequestContextAwareTag {

	protected String deptId = null;
	
	protected String deptName = null;

	protected String selectedId = null;
	
	protected String selectedName = null;
	
	protected String formName = null;
	
//	protected String limit = null; 
	
	@Autowired
	private HrmDao hrmDao;	

	/**
	 * tag body
	 */
	@SuppressWarnings("unchecked")
	public int doStartTagInternal() throws JspException {
		deptId = eval("deptId", deptId, Object.class).toString();
		deptName = eval("deptName", deptName, Object.class).toString();
		
		if (selectedId != null) {
			selectedId = eval("selectedId", selectedId, Object.class).toString();
		} else {
			selectedId = "";
		}
		
		if (selectedName != null) {
			selectedName = eval("selectedName", selectedName, Object.class).toString();
		} else {
			selectedName = "";
		}
		
		if (formName != null) {
			formName = eval("formName", formName, Object.class).toString();
		} else {
			formName = "";
		}
		
		/*if (limit != null) {
			limit = eval("limit", limit, Object.class).toString();
		} else {
			limit = "hr";
		}*/
		
	 	//ajaxDao = (AjaxDao)getRequestContext().getWebApplicationContext() .getAutowireCapableBeanFactory().getBean("ajaxDaoImpl");
		/*
		String deptname="";
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		if(!selected.equals("")){
			Map object=new LinkedHashMap();
			object.put("deptno",selected);
			object.put("interLanguage",admin.getLanguage());
			deptname =  ((Map) hrmSer.getDeptById(object)).get("DEPT_NAME").toString();
		}
		*/
	 	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long sysLong = System.currentTimeMillis() ;
		//System.out.println(sysLong) ;
		List deptList=new ArrayList();
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		/*paramMap.put("ADMINID", admin.getAdminID()) ;
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());*/
		
		/*if(limit.equals("hr")){
			deptList = hrmDao.getDeptTree("hrm.getDeptTreeForHr",paramMap);
		}else if(limit.equals("ar")){
			deptList = hrmDao.getDeptTree("hrm.getDeptTreeForAr",paramMap);
		}*/
		//deptList = hrmDao.getDeptTree("hrm.getDeptTreeForHr",paramMap);
		//deptList = ajaxDao.getDeptInfoTree(paramMap);
		try {
			StringBuffer scriptStr = new StringBuffer(200) ;
			JspWriter writer = pageContext.getOut();
			//生成所需JS
			//long timeStamp=System.currentTimeMillis();
			scriptStr.append(" ");	
			scriptStr.append("<SCRIPT type='text/javascript'>");
			scriptStr.append("function seach_createTree_"+sysLong+"(){");
			scriptStr.append("$.ajax({");	
			scriptStr.append("async : false,");	
			scriptStr.append("cache:false,");
			scriptStr.append("type: 'POST',");
			scriptStr.append("dataType : \"json\",");
			if(formName!=null && !"".equals(formName)){
				scriptStr.append("url: \"/sys/ajax/getOrgInfoTreeDate?seach_CPNY_ID=\"+$(\"#"+formName+"\").find(\"#combox_CPNY_ID\").val(),");
			}else{
				scriptStr.append("url: \"/sys/ajax/getOrgInfoTreeDate?seach_CPNY_ID=\"+$(\"#combox_CPNY_ID\").val(),");
			}
			scriptStr.append("error: function () {");
			scriptStr.append("alert('Request Failed!');");	
			scriptStr.append("},");
			scriptStr.append("success:function(zNodes){");	
			scriptStr.append("if($(zNodes).size() > 0){");
			scriptStr.append("var setting = {");
			scriptStr.append("view: {");
			scriptStr.append("dblClickExpand: false,");
			scriptStr.append("showLine: true,");
			scriptStr.append("selectedMulti: false,");
			scriptStr.append("expandSpeed: \"fast\"");
			scriptStr.append("},");
			scriptStr.append("data: {");
			scriptStr.append("key: {");
			scriptStr.append("name: \"DEPTNAME\"");
			scriptStr.append("},");
			scriptStr.append("simpleData: {");
			scriptStr.append("enable:true,");
			scriptStr.append("idKey: \"DEPTNO\",");
			scriptStr.append("},");
			scriptStr.append("simpleData: {");
			scriptStr.append("enable:true,");
			scriptStr.append("idKey: \"DEPTNO\",");
			scriptStr.append("pIdKey: \"PARENT_DEPT_NO\",");
			scriptStr.append("rootPId: \"\"");
			scriptStr.append("}},");
			scriptStr.append("callback: {");
			scriptStr.append("onClick: function (e, treeId, treeNode) {");
			scriptStr.append("var deptName = $(\"#" + deptName + "[sysLong='" + sysLong + "']\").val();");
			scriptStr.append("if(deptName == treeNode.DEPTNAME && treeId != 'rollback'){");
			scriptStr.append("$(\"#" + deptName + "[sysLong='" + sysLong + "']\").attr(\"value\", '');");
			scriptStr.append("$(\"#" + deptId + "[sysLong='" + sysLong + "']\").attr(\"value\", '');");
			scriptStr.append("}else{");
			scriptStr.append("$(\"#" + deptName + "[sysLong='" + sysLong + "']\").attr(\"value\", treeNode.DEPTNAME);");
			scriptStr.append("$(\"#" + deptId + "[sysLong='" + sysLong + "']\").attr(\"value\", treeNode.DEPTNO);");
			scriptStr.append("}}}};");
			scriptStr.append("$.fn.zTree.init($(\"#seach_deptTree_"+sysLong+"\"), setting, zNodes);");
			scriptStr.append("var cityObj = $(\"#" + deptName + "[sysLong='" + sysLong + "']\");");
			scriptStr.append("var cityOffset = $(\"#" + deptName + "[sysLong='" + sysLong + "']\").offset();");
			scriptStr.append("$(\"#seach_deptContent_"+ sysLong + "\").offset({left:cityOffset.left + \"px\", top:cityOffset.top + cityObj.outerHeight() + \"px\"}).slideDown(\"fast\");");
			scriptStr.append("$(\"body\").bind(\"mousedown\", "); 
			scriptStr.append("function(event) {"); 
			scriptStr.append("if (!(event.target.id == \"menuBtn\" || event.target.id == \"seach_deptContent_"+ sysLong + "\" || $(event.target).parents(\"#seach_deptContent_"+ sysLong + "\").length>0)) {"); 
			scriptStr.append("$(\"#seach_deptContent_"+ sysLong + "\").fadeOut(\"fast\");"); 
			scriptStr.append("$(\"body\").unbind(\"mousedown\");"); 
			scriptStr.append("}});");
			scriptStr.append("var $treeObj_" + sysLong + " = $.fn.zTree.getZTreeObj(\"seach_deptTree_" + sysLong + "\");");
			scriptStr.append("var $nodeObj_" + sysLong + " = $treeObj_" + sysLong + ".getNodeByParam('DEPTNO',$(\"#" + deptId + "[sysLong='" + sysLong + "']\").val());");
            scriptStr.append("if($nodeObj_" + sysLong + " != null ){");
    		scriptStr.append("$treeObj_" + sysLong + ".selectNode($nodeObj_" + sysLong + ");");
			//scriptStr.append("setting.callback.onClick(null, 'rollback', $nodeObj_" + sysLong + ");");
			scriptStr.append("}");
			scriptStr.append("} } }); return false ;}"); 
			
			scriptStr.append("</SCRIPT>");	
			//生成INPUT和隐藏字段
			 
			scriptStr.append("<input type=\"text\" id=\"" + deptName + "\" name=\"" + deptName + "\" value=\"" + selectedName + "\"  readonly sysLong=\"" + sysLong + "\"  onclick=\"seach_createTree_" + sysLong + "();\"/> "); 
			scriptStr.append("<input id=\"" + deptId + "\" name=\"" + deptId + "\" type=\"hidden\" value=\"" + selectedId + "\" sysLong=\"" + sysLong + "\"/>");
			scriptStr.append(" <div id=\"seach_deptContent_" + sysLong + "\" class=\"menuContent\" style=\"display:none; position: absolute;z-index:999;overflow:auto;height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;\">");
			scriptStr.append("<ul id=\"seach_deptTree_" + sysLong + "\" class=\"ztree\" height=\"100%\"></ul>");
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

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public String getSelectedName() {
		return selectedName;
	}

	public void setSelectedName(String selectedName) {
		this.selectedName = selectedName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	/*public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}*/

}
