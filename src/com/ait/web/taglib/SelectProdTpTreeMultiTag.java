package com.ait.web.taglib;
/**
 * 
 * @fileName SelectProdTpTreeMultiTag.java
 * @author 			@Date			@version		@Desc
 * ---------------------------------------------------------------------------------------------------------------
 * LI HUIHUA 		2014-8-25 		1.0				initial  
 *----------------------------------------------------------------------------------------------------------------
 */
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
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.web.messages.Messages;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.sys.dao.BasicMaintenanceDao;

@SuppressWarnings("serial")
@Component
public class SelectProdTpTreeMultiTag extends RequestContextAwareTag {

	protected String name = null;
	
	protected String parentNo = null;

	protected String selected = null;
	
	protected String selectedNm = null;
	
	protected String limit = null; 
	
	protected String parameter = null;
	
	protected int level = 999; 
	
	//是否按照汉字的拼音进行排序（PY--拼音排序；EN--英语排序；ZH--汉字排序）
	protected String orderType = null;

	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;	

	/**
	 * tag body
	 */
	@SuppressWarnings("unchecked")
	public int doStartTagInternal() throws JspException {

		name = eval("name", name, Object.class).toString();
		parentNo = eval("parentNo", parentNo, Object.class).toString();
		
		id = eval("id", id, Object.class).toString();

		if (selected != null) {
			selected = eval("selected", selected, Object.class).toString();
		} else {
			selected = "";
		}	
		
		if (selectedNm != null) {
			selectedNm = eval("selectedNm", selectedNm, Object.class).toString();
		} else {
			selectedNm = "";
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
		
		if (orderType != null) {
			orderType = eval("orderType", orderType, Object.class).toString();
		} else {
			orderType = "";
		}

		basicMaintenanceDao = (BasicMaintenanceDao)getRequestContext().getWebApplicationContext().getAutowireCapableBeanFactory().getBean("basicMaintenanceDaoImpl");

		
		long sysLong = System.currentTimeMillis() ;
		
		List codeList=new ArrayList();
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		paramMap.put("PARENT_CODE_NO",parentNo);
		paramMap.put("language",Messages.getLanguage(request));
		paramMap.put("interLanguage",admin.getLanguage());
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("ORDER_TYPE",orderType);
		
		codeList = basicMaintenanceDao.getParamCodeListByCpnyID2(paramMap, -1, -1) ;
		try {
			StringBuffer scriptStr = new StringBuffer(200) ;
			
			JspWriter writer = pageContext.getOut();
			//生成所需JS
			long timeStamp=System.currentTimeMillis();
			scriptStr.append("<SCRIPT type='text/javascript'>");			
			
			scriptStr.append("var setting_" + sysLong + " = {");
			scriptStr.append("view: {");
			scriptStr.append("	dblClickExpand: true,showLine: true,selectedMulti: false,expandSpeed: \"fast\"");
			scriptStr.append("},");
			scriptStr.append("check: {");
			scriptStr.append("	autoCheckTrigger: false,chkboxType:{\"Y\":\"s\",\"N\":\"ps\"},chkStyle:\"checkbox\",enable:true, nocheckInherit:true, radionType: \"level\"");
			scriptStr.append("},");
			scriptStr.append("data: {");
			scriptStr.append("	key: {");
			scriptStr.append("	checked:\"CHECKED\",name:\"CODE_NAME\", open:\"false\"");
			scriptStr.append("},");
			scriptStr.append("	simpleData: {");
			scriptStr.append("	enable:true,idKey:\"CODE_NO\",pIdKey:\"PARENT_CODE_NO\",rootPId: \"\"");
			scriptStr.append("	}");
			scriptStr.append("},");
			scriptStr.append("callback: {");
			scriptStr.append("	onCheck: onCheck_" + sysLong + "");
			scriptStr.append("}");
			scriptStr.append("};");
			scriptStr.append("var zNodes_" + sysLong + " = ");
			
			scriptStr.append(JsonUtil.writeInternal(codeList)) ;
			
			scriptStr.append(";");
			
			scriptStr.append("function onCheck_" + sysLong + "(treeId, treeNode) {");
			if (parameter != null && parameter.length() > 0){
				scriptStr.append("setWorkArea(treeNode,'" + name + "');");
			}
			scriptStr.append("var t= $.fn.zTree.getZTreeObj(\"prodTpTree_" + sysLong + "\");");
			scriptStr.append("var nodes = t.getCheckedNodes();");
			scriptStr.append("var prodNos = '';");
			scriptStr.append("var prodNames = '';");
			scriptStr.append("if(nodes.length>0){");
			scriptStr.append(" for(var i=0;i<nodes.length;i++){");
			scriptStr.append("  if(i==0){");
			scriptStr.append("    prodNos = prodNos + nodes[i].CODE_NO;");
			scriptStr.append("    prodNames = prodNames + nodes[i].CODE_NAME;");
			scriptStr.append("  }else{");
			scriptStr.append("    prodNos = prodNos + ',' + nodes[i].CODE_NO;");
			scriptStr.append("    prodNames = prodNames + ',' + nodes[i].CODE_NAME;");
			scriptStr.append("  }");
			scriptStr.append(" }");
			scriptStr.append("	  $(\"#" + id + sysLong  + "\").attr(\"value\", prodNos);");
			scriptStr.append("    $(\"#" + name + sysLong  +  "\").attr(\"value\", prodNames);");
			scriptStr.append("}else{");
			//先清除文本框的值
			scriptStr.append("    $(\"#" + id + sysLong + "\").attr(\"value\", '');");
			scriptStr.append("    $(\"#" + name + sysLong + "\").attr(\"value\", '');");		
			scriptStr.append("}");
			scriptStr.append("}");
			scriptStr.append("function showTree_" + sysLong + "() {");
			
			scriptStr.append("  var cityObj = $(\"#" + name + sysLong + "\");");
			scriptStr.append("  var cityOffset = $(\"#" + name + sysLong + "\").offset();");
			scriptStr.append("  $(\"#prodTpContent_" + sysLong + "\").offset({top:cityOffset.top + \"px\", left:cityOffset.left + cityObj.outerHeight() + \"px\" }).slideDown(\"fast\");");
			scriptStr.append("  $(\"body\").bind(\"mousedown\", onBodyDown_" + sysLong + ");");
			scriptStr.append("}");
			scriptStr.append("function hideMenu_" + sysLong + "() {");
			scriptStr.append("  $(\"#prodTpContent_" + sysLong + "\").fadeOut(\"fast\");");
			scriptStr.append("  $(\"body\").unbind(\"mousedown\", onBodyDown_" + sysLong + ");");
			scriptStr.append("}");
			scriptStr.append("function onBodyDown_" + sysLong + "(event) {");
			scriptStr.append("  if (!(event.target.id == \"menuBtn\" || event.target.id == \"prodTpContent_" + sysLong + "\" || $(event.target).parents(\"#prodTpContent_" + sysLong + "\").length>0)) {");
			scriptStr.append("    hideMenu_" + sysLong + "();");
			scriptStr.append("  }");
			scriptStr.append("}");
			scriptStr.append("$(document).ready(function(){");
			scriptStr.append("  $.fn.zTree.init($(\"#prodTpTree_" + sysLong + "\"), setting_" + sysLong + ", zNodes_" + sysLong + ");");
			if (selected != null && selected.length() > 0){				
				String sel[] = selected.split(",");
				for(int i=0; i<sel.length;i++ ){
					scriptStr.append("var $treeObj_" + sysLong + " = $.fn.zTree.getZTreeObj(\"prodTpTree_" + sysLong + "\");");
					scriptStr.append("var $nodeObj_" + sysLong + " = $treeObj_" + sysLong + ".getNodeByParam('CODE_NO','" + sel[i] + "');");
					scriptStr.append("if($nodeObj_" + sysLong + " != null ){");
					scriptStr.append("  $treeObj_" + sysLong + ".selectNode($nodeObj_" + sysLong + ");");
					scriptStr.append("  $treeObj_" + sysLong + ".checkNode($nodeObj_" + sysLong + ", true, true);");
					scriptStr.append("}");
				}
				
			}
			scriptStr.append("});");
			
			scriptStr.append("</SCRIPT>");			
			
			//生成INPUT和隐藏字段	
			scriptStr.append("<input id=\"" + name + sysLong + "\" name=\"" + name + "\" type=\"text\" readonly value=\"" + selectedNm + "\" sysLong=\"" + id + "\"/>");
			scriptStr.append("<span class=\"tree_icon\"" + " onclick=\"showTree_" + sysLong + "()\"/>");
			scriptStr.append("<input id=\"" + id + + sysLong + "\" name=\"" + id + "\" type=\"hidden\" value=\"" + selected + "\" sysLong=\"" + id + "\"/>");
			scriptStr.append("<div id=\"prodTpContent_" + sysLong + "\" style=\"display:none; position: absolute;z-index:100;overflow:auto;height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;\" >");
			scriptStr.append("<ul id=\"prodTpTree_" + sysLong + "\" class=\"ztree\" style=\"margin-top:0; width:300px;overflow:auto\"></ul>");
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

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	public String getSelectedNm() {
		return selectedNm;
	}

	public void setSelectedNm(String selectedNm) {
		this.selectedNm = selectedNm;
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
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	
	public String getParentNo() {
		return parentNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}
