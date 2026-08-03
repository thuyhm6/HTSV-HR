package com.ait.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.RequestContextAwareTag;



@SuppressWarnings("serial")
@Component
public class SelectDeptListTag extends RequestContextAwareTag {

	protected String name = null;
	
	protected String id = null;

	protected String selected = null;
	
	protected String limit = null; 
	
	protected String parameter = null;

	protected String cpnyId = null;
	
	protected String onClickName = null;

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
		if (onClickName != null) {
			onClickName = eval("onClickName", onClickName, Object.class).toString();
		}
		
		long sysLong = System.currentTimeMillis() ;
		
		String cpnyIdStr = "";
		String limitStr = "";
		if(!"".equals(cpnyId)){
			cpnyIdStr = ",CPNY_ID:\"" + cpnyId + "\""; 
		}
		if(!"".equals(limit)){
			if("hr".equals(limit) || "HR".equals(limit)){
				limitStr = ",limit:\"super\"";
			}else{
				limitStr = ",limit:\"" + limit + "\"";
			}
		}
		try {
			StringBuffer scriptStr = new StringBuffer(200) ;
			
			JspWriter writer = pageContext.getOut();
			//生成所需JS
			long timeStamp=System.currentTimeMillis();
			scriptStr.append("<SCRIPT type='text/javascript'>");			
			
			scriptStr.append(" var ajaxGet_" + sysLong + ";");
			scriptStr.append(" function  ajaxAdd_" + sysLong + "() { ");
			scriptStr.append("  if (ajaxGet_" + sysLong + " != null) { ");
			scriptStr.append("    ajaxGet_" + sysLong + ".abort(); ");
			scriptStr.append("  } ");
			scriptStr.append("  $.ajaxSettings.global = false; ");
			scriptStr.append("  ajaxGet_" + sysLong + " = $.ajax( { ");
			scriptStr.append("     type : \"POST\", ");
			scriptStr.append("     url : \"/org/orgManage/getDeptList\", ");
			scriptStr.append("     data : { deptName : $(\"#" + id + "\").val() " + cpnyIdStr + limitStr + " }, ");
			scriptStr.append("     dataType : \"json\", ");
			scriptStr.append("     success : function(data) { ");
			scriptStr.append("         $('#deptTree_" + sysLong + "').html(\"\"); ");
			scriptStr.append("         var html = ''; ");
			scriptStr.append("         if (typeof (data['deptList']) != \"undefined\") {");
			scriptStr.append("            $.each(data['deptList'], ");
			scriptStr.append("            function(commentIndex, comment) { ");
			scriptStr.append("                if(commentIndex%2==0){");
			scriptStr.append("                   html += '<li class=\"deptTreeLi\" onclick=\"selectedIt" + sysLong + "(\\'' + comment['DEPTNAME'] + '\\',\\'' + comment['DEPTNO'] + '\\')\"><span>' + comment['DEPTNO'] + '</span><b>' + comment['DEPTNAME'] + '</b></li>';");
			scriptStr.append("                }else{");
			scriptStr.append("                   html +='<li onclick=\"selectedIt" + sysLong + "(\\'' + comment['DEPTNAME'] + '\\',\\'' + comment['DEPTNO'] + '\\')\"><span>' + comment['DEPTNO'] + '</span><b>' + comment['DEPTNAME'] + '</b></li>';");
			scriptStr.append("                } ");
			scriptStr.append("            }); ");
			scriptStr.append("         } ");
			scriptStr.append("         $('#deptTree_" + sysLong + "').html(html); ");
			scriptStr.append("         $(\"#deptContent_" + id + sysLong + "\").css(\"display\", \"block\"); ");
			scriptStr.append("     }");
			scriptStr.append("  }); ");
			scriptStr.append("  $.ajaxSettings.global = true; ");
			scriptStr.append(" } ");
			scriptStr.append(" function mouseoverF" + sysLong + "() { ");
			scriptStr.append(" 		$(\"#" + id + "\").unbind(\"blur\"); ");
			scriptStr.append(" 		$(\"#deptContent_" + id + sysLong + "\").mouseout( ");
			scriptStr.append(" 				function() { ");
			scriptStr.append(" 					$('#" + id + "').blur(");
			scriptStr.append(" 						function() { ");
			scriptStr.append(" 							$('#deptContent_" + id + sysLong + "').css('display', 'none'); ");
			scriptStr.append(" 					});");
			scriptStr.append(" 				}");
			scriptStr.append(" 		);");
			scriptStr.append(" }");
			scriptStr.append(" function selectedIt" + sysLong + "(deptName,deptNo) { ");
			if (parameter != null && parameter.length() > 0){
				scriptStr.append("$(\"#WORK_AREA\",navTab.getCurrentPanel()).val(treeNode.WORK_AREA);");
				scriptStr.append("$(\"#COST_CENTER\",navTab.getCurrentPanel()).val(treeNode.DEPTNO);");
			}
			if (onClickName != null){
				scriptStr.append("codeRelation(deptNo,'MAIN_BUSINESS','" + onClickName + "','Please choose','BUSINESS');");
				scriptStr.append("$(\"#COST_CENTER\",navTab.getCurrentPanel()).val(deptNo);");
			}
			scriptStr.append(" 		$('#" + id + "').val(deptName); ");
			scriptStr.append(" 		$(\":input[sysLong='" + id + "']\").val(deptNo); ");
			scriptStr.append(" 		$('#deptContent_" + id + sysLong + "').css('display', 'none'); ");
			scriptStr.append(" } ");
			scriptStr.append(" function cleanValue" + sysLong + "() { ");
			scriptStr.append(" 		$('#" + id + "').val(''); ");
			scriptStr.append(" 		$(\":input[sysLong='" + id + "']\").val('');  ");
			scriptStr.append(" 		$('#deptContent_" + id + sysLong + "').css('display', 'none');");
			scriptStr.append(" }");
			scriptStr.append(" function closedept" + sysLong + "() {");
			scriptStr.append(" 		$('#deptContent_" + id + sysLong + "').css('display', 'none');");
			scriptStr.append(" }");

			scriptStr.append("</SCRIPT>");			
			
			//生成INPUT和隐藏字段
			scriptStr.append("<input id=\"" + id + "\" name=\"seach_deptName\" type=\"text\" onkeyup=\"ajaxAdd_" + sysLong + "()\" onfocus=\"ajaxAdd_" + sysLong + "()\"/>");
			scriptStr.append("<input id=\"" + name + sysLong + "\" name=\"" + name + "\" type=\"hidden\" value=\"" + selected + "\" sysLong=\"" + id + "\"/>");
			scriptStr.append("<div id=\"deptContent_" + id + sysLong + "\" onmouseover=\"mouseoverF" + sysLong + "()\" class=\"deptContent\" style=\"display:none;\">");
			scriptStr.append(" 		<div class=\"ztree_dept\">");
			scriptStr.append(" 			<div class=\"ztree_dept_title\">");
			scriptStr.append(" 				<table width=\"100%\">");
			scriptStr.append(" 					<tr onclick=\"cleanValue" + sysLong + "()\">");
			scriptStr.append(" 						<th style=\"width:30%\">Code</th>");
			scriptStr.append(" 						<th style=\"text-align:center;width:70%\">Dept Name</th>");
			scriptStr.append(" 					</tr>");
			scriptStr.append(" 				</table>");
			scriptStr.append(" 			</div>");
			scriptStr.append("  		<div class=\"ztree_dept_type\">");
			scriptStr.append("				<ul id=\"deptTree_" + sysLong + "\" class=\"ztree_dept_table\" >");
			scriptStr.append(" 				</ul>");
			scriptStr.append("			</div>");
			scriptStr.append(" 			<div class=\"ztree_dept_color\">");
			scriptStr.append(" 				<a href=\"#\" onclick=\"closedept" + sysLong + "()\" class=\"ztree_dept_color_a\">");
			scriptStr.append(" 					<span>close</span>");
			scriptStr.append(" 				</a>");
			scriptStr.append(" 			</div>");
			scriptStr.append(" 		</div>");
			scriptStr.append(" </div>");
			
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

	public String getOnClickName() {
		return onClickName;
	}

	public void setOnClickName(String onClickName) {
		this.onClickName = onClickName;
	}

}
