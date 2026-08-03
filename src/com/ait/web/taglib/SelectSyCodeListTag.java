package com.ait.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.RequestContextAwareTag;



@SuppressWarnings("serial")
@Component
public class SelectSyCodeListTag extends RequestContextAwareTag {

	protected String name = null;
	
	protected String id = null;
	
	protected String parentNo = null;
	
	protected String selected = null;
	
	protected String limit = null; 
	
	protected String parameter = null;

	protected String cpnyId = null;

	protected String disabled = null; 
	
	protected String onChangeName = null; 
	
	protected String codeName = null;
	/**
	 * tag body
	 */
	public int doStartTagInternal() throws JspException {
		parentNo = eval("parentNo", parentNo, Object.class).toString();
		name = eval("name", name, Object.class).toString();
		id = eval("id", id, Object.class).toString();
		cpnyId = eval("cpnyId", cpnyId, Object.class).toString();
		parentNo = eval("parentNo", parentNo, Object.class).toString();
		
		if (codeName != null) {
			codeName = eval("codename", codeName, Object.class).toString();
		} else {
			codeName = "";
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
		
		if (disabled != null) {
			disabled = eval("disabled", disabled, Object.class).toString();
		} else {
			disabled = "";
		}	
		
		if (onChangeName != null) {
			onChangeName = eval("onChangeName", onChangeName, Object.class).toString();
		} else {
			onChangeName = "";
		}
		
		try {
			long sysLong = System.currentTimeMillis() ;
			StringBuffer scriptStr = new StringBuffer(200) ;
			JspWriter writer = pageContext.getOut();
			//生成所需JS
			scriptStr.append("<SCRIPT type='text/javascript'> ");
			
			scriptStr.append(" var ajaxGet_" + sysLong + "; ");
			scriptStr.append(" function  ajaxAddCode() {    ");
			scriptStr.append("    if(ajaxGet_" + sysLong + " != null) {  ");
			scriptStr.append("       ajaxGet_" + sysLong + ".abort();    ");
			scriptStr.append("    }                                      ");
			scriptStr.append("    $.ajaxSettings.global = false;         ");
			scriptStr.append("    ajaxGet_" + sysLong + " =              ");
			scriptStr.append("          $.ajax( {                        ");
			scriptStr.append("              type : \"POST\",             ");
			scriptStr.append("              url  : \"/org/orgManage/getSyCodeList\",       ");
			scriptStr.append("              data : { CODE_NAME : $(\"#" + id + "\").val(), ");
			scriptStr.append("                       PARENT_CODE_NO :\"" + parentNo + "\", ");
			scriptStr.append("                       DEFAULT_CODE_NAME :\"" + selected + "\", ");
			scriptStr.append("                       CPNY_ID : \"" + cpnyId + "\" },       ");
			scriptStr.append("              dataType : \"json\",                           ");
			scriptStr.append("              success : function(data) {                     "); 
			scriptStr.append("                   $('#codeTree_" + sysLong + "').html(\"\");   ");
			scriptStr.append("                   var html = '';                               ");
			scriptStr.append("                   if(typeof (data['codeList']) != \"undefined\"){  ");
			scriptStr.append("                      $.each(data['codeList'],                      ");
			scriptStr.append("                         function(commentIndex, comment) {          ");
			scriptStr.append("                             if(commentIndex%2==0){                 ");
			scriptStr.append("                                 html += '<li class=\"deptTreeLi\"  ");
			scriptStr.append("                                              onclick=\"selectedCodeIt(\\'' + comment['CODENAME'] + '\\',     ");
			scriptStr.append("                                                                       \\'' + comment['CODENO'] + '\\')\">    ");
			scriptStr.append("                                      <span>'+comment['CODENO']+'</span><b>'+comment['CODENAME']+'</b></li>'; ");
			scriptStr.append("                             }else{                                                                           ");
			scriptStr.append("                                 html +='<li onclick=\"selectedCodeIt(\\'' + comment['CODENAME'] + '\\',      ");
			scriptStr.append("                                                                      \\'' + comment['CODENO'] + '\\')\">     ");
			scriptStr.append("                                      <span>'+comment['CODENO']+'</span><b>'+comment['CODENAME']+'</b></li>'; ");
			scriptStr.append("                             } ");
			scriptStr.append("                         } ");
			scriptStr.append("                      ); ");
			scriptStr.append("                   }    ");
			scriptStr.append("                   $('#codeTree_" + sysLong + "').html(html); ");
			scriptStr.append("                   $(\"#codeContent_" + sysLong + "\").css(\"display\", \"block\"); ");
			scriptStr.append("              }                    ");
			scriptStr.append("          });                      ");
			scriptStr.append("    $.ajaxSettings.global = true;  ");
			scriptStr.append(" } ");
			
			scriptStr.append(" function mouseoverCode() {        ");
			scriptStr.append("    $(\"#" + id + "\").unbind(\"blur\");          ");
			scriptStr.append("    $(\"#codeContent_" + sysLong + "\").mouseout( ");
			scriptStr.append("       function() {                               ");
			scriptStr.append("          $('#" + id + "').blur(function() {      ");
			scriptStr.append("              $('#codeContent_" + sysLong + "').css('display', 'none'); ");
			scriptStr.append("          });    ");
			scriptStr.append("       }         ");
			scriptStr.append("     );          ");
			scriptStr.append(" }               ");
			
			scriptStr.append(" function selectedCodeIt(codeName,codeNo) {      ");
			scriptStr.append("   $('#" + id + "').val(codeName);               ");
			scriptStr.append("   $('#" + name + sysLong + "').val(codeNo);     "); 
			scriptStr.append("   $('#codeContent_" + sysLong + "').css('display', 'none'); ");
			scriptStr.append(" }    ");
			
			scriptStr.append(" function cleanCodeValue() {             ");
			scriptStr.append("   $('#" + id + "').val('');             ");
			scriptStr.append("   $('#" + name + sysLong + "').val(''); ");
			scriptStr.append("   $('#codeContent_" + sysLong + "').css('display', 'none'); ");
			scriptStr.append(" }  ");
			
			scriptStr.append(" function closeCode() { ");
			scriptStr.append("   $('#codeContent_" + sysLong + "').css('display', 'none');  ");
			scriptStr.append(" } ");

			scriptStr.append("</SCRIPT>  ");			
			
			//生成INPUT和隐藏字段
			scriptStr.append("<input id=\"" + id + "\" name=\"seach_codeName\" type=\"text\" onkeyup=\"ajaxAddCode()\" onfocus=\"ajaxAddCode()\"/>  ");
			scriptStr.append("<input id=\"" + name + sysLong + "\" name=\"" + name + "\" type=\"hidden\" value=\"" + selected + "\" sysLong=\"" + id + "\"/>");
			scriptStr.append("<div id=\"codeContent_" + sysLong + "\" onmouseover=\"mouseoverCode()\" class=\"deptContent\" style=\"display:none;\">  ");
			scriptStr.append("   <div class=\"ztree_dept\">   ");
			scriptStr.append("      <div class=\"ztree_dept_title\">   ");
			scriptStr.append("         <table width=\"100%\">   ");
			scriptStr.append("            <tr onclick=\"cleanCodeValue()\">  ");
			scriptStr.append("               <th style=\"width:30%\">Code</th>   ");
			scriptStr.append("               <th style=\"text-align:center;width:70%\">"+codeName+"名称</th>   ");
			scriptStr.append("               </tr>   ");
			scriptStr.append("         </table>   ");
			scriptStr.append("      </div>    ");
			scriptStr.append("      <div class=\"ztree_dept_type\">   ");
			scriptStr.append("         <ul id=\"codeTree_" + sysLong + "\" class=\"ztree_dept_table\" ></ul>  ");
			scriptStr.append("      </div>     ");
			scriptStr.append("      <div class=\"ztree_dept_color\">    ");
			scriptStr.append("         <a href=\"#\" onclick=\"closeCode()\" class=\"ztree_dept_color_a\">   ");
			scriptStr.append("            <span>关闭</span>     ");
			scriptStr.append("         </a>    ");
			scriptStr.append("      </div>     ");
			scriptStr.append("   </div>        ");
			scriptStr.append("</div>           ");
			
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
	
	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
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
	
	public String getDisabled() {
		return disabled;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	
	public String getParentNo() {
		return parentNo;
	}
	
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getOnChangeName() {
		return onChangeName;
	}

	public void setOnChangeName(String onChangeName) {
		this.onChangeName = onChangeName;
	}
}
