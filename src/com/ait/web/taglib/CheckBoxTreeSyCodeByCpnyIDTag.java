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
import com.ait.hrm.dao.InformationRetrievalDao;
import com.ait.hrm.service.InformationRetrievalSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

@SuppressWarnings("serial")
@Component
public class CheckBoxTreeSyCodeByCpnyIDTag extends RequestContextAwareTag {

	protected String name = null; 

	protected String parentNo = null;
	
	protected String cnpyID = null; 
	
	protected String divName = null;   //显示与隐藏的div
	
	protected String formName = null; 
	
	protected String treeDivName = null; //tree的div
	
	protected String funcName = null; // 不同名称function的div
	
	
	
	 
	

	/**
	 * tag body
	 */
	@SuppressWarnings("unchecked")
	public int doStartTagInternal() throws JspException {

		name = eval("name", name, Object.class).toString();
        
		parentNo = eval("parentNo", parentNo, Object.class).toString();
		
		funcName = eval("funcName", funcName, Object.class).toString();
		
		
		 	
		if (cnpyID != null) {
			cnpyID = eval("cnpyID", cnpyID, Object.class).toString();
		} else {
			cnpyID = "";
		}
	 
		divName = eval("divName", divName, Object.class).toString();
		
		formName = eval("formName", formName, Object.class).toString();
		
		treeDivName = eval("treeDivName", treeDivName, Object.class).toString();
		 
		 

		long sysLong = System.currentTimeMillis() ;
		
		List codeList=new ArrayList();
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
	
		paramMap.put("CPNYID", admin.getCpnyId());
		
		paramMap.put("PARENT_CODE_NO",parentNo);
 
	//    codeList = informationRetrievalDao.getCodeParamList(paramMap);
		 
		
		
		
		try {
			StringBuffer scriptStr = new StringBuffer(200) ;
			
			JspWriter writer = pageContext.getOut();
			//生成所需JS
			long timeStamp=System.currentTimeMillis();
			scriptStr.append("<SCRIPT type='text/javascript'>");			
		 
			// ajax
			scriptStr.append(" function createCodeTree_"+funcName+"_"+parentNo+"(code_no) ");
			scriptStr.append("  {   ");
			scriptStr.append("    var $form=$('#"+formName+"');  ");     //form ID
			scriptStr.append("    var offset = $form.find('#"+name+"').offset();  "); 
			scriptStr.append("     $.ajax({  ");  
			scriptStr.append("     type: 'GET',  ");   
			scriptStr.append("     url:'/hrm/informationRetrieval/getCodeParamList?PARENT_CODE_NO='+code_no,  ");  
			scriptStr.append("     dataType:'json',  ");  
			scriptStr.append("     cache: false,  ");  
			scriptStr.append("     success: function(data){  "); 
		
			scriptStr.append("    $form.find('#"+divName+"').show();    ");   //div id
			scriptStr.append("    $form.find('#"+divName+"').offset({ top: offset.top + 20 });    ");
		
			scriptStr.append("     var setting = {  "); 
		    scriptStr.append("     view:{ selectedMulti: false },  "); 
		    scriptStr.append("     check:{ enable: true }, "); 
		    scriptStr.append("     data: { "); 
		    scriptStr.append("     simpleData: {id: 'CODE_NO', pId: 'PARENT_CODE_NO' }, "); 
		    scriptStr.append("     key: {name:'CODE_NAME'}  "); 
		    scriptStr.append("     } "); 
		    scriptStr.append("     };  "); 
		    scriptStr.append("     var zNodes = data; "); 
		    scriptStr.append("     var t = $('#"+treeDivName+"'); ");   //TreeDivName   li：treeDemo
		    scriptStr.append("      $.fn.zTree.init(t, setting, zNodes); "); 
		    scriptStr.append("       } "); 
		    scriptStr.append("       }); "); 
		    scriptStr.append("       } ");
			
			//取树的key和value           //返回树的key值 （code_no）
		    scriptStr.append("   function  getCheckedTreeKey(treeId) { ");
		    scriptStr.append("      var codeTree = $.fn.zTree.getZTreeObj(treeId) ; ");
		    scriptStr.append("      var checkCode = codeTree.getCheckedNodes() ; ");
		    scriptStr.append("      if($(checkCode).size() == 0){ ");
		    scriptStr.append("          return ''; ");
		    scriptStr.append("         } ");
		    scriptStr.append("        var jsonID = '(' ; ");
		    scriptStr.append("        $(checkCode).each(function(index, codeObj){ ");
		    scriptStr.append("        if(checkCode.length==1){ jsonID= codeObj.CODE_NO ;   return ; }");
		    scriptStr.append("        if (jsonID.length > 1){ ");
		    scriptStr.append("            jsonID += ','; ");
		    scriptStr.append("           } ");
		    scriptStr.append("          else{ jsonID += '';  } ");
		    scriptStr.append("         jsonID += \"'\"+codeObj.CODE_NO+\"'\"  ; ");
		    scriptStr.append("          });");
		    scriptStr.append("        if(checkCode.length!=1) { ");
		    scriptStr.append("         jsonID += ')'  } ; ");
		    scriptStr.append("          return jsonID ;");
		    scriptStr.append("         } ");
		    
		  //取树的key和value           //返回树的name值 （code_name）
		    scriptStr.append("   function  getCheckedTreeContent(treeId) { ");
		    scriptStr.append("      var codeTree = $.fn.zTree.getZTreeObj(treeId) ; ");
		    scriptStr.append("      var checkCode = codeTree.getCheckedNodes() ; ");
		    scriptStr.append("      if($(checkCode).size() == 0){ ");
		    scriptStr.append("          return ''; ");
		    scriptStr.append("         } ");
		    scriptStr.append("        var jsonValue = '(' ; ");
		    scriptStr.append("        $(checkCode).each(function(index, codeObj){ ");
		    scriptStr.append("        if(checkCode.length==1){ jsonValue= codeObj.CODE_NAME ;   return ; }");
		    scriptStr.append("        if (jsonValue.length > 1){ ");
		    scriptStr.append("            jsonValue += ','; ");
		    scriptStr.append("           } ");
		    scriptStr.append("          else{ jsonValue += '';  } ");
		    scriptStr.append("         jsonValue +=  \"'\"+codeObj.CODE_NAME+\"'\" ; ");
		    scriptStr.append("          });");
		    scriptStr.append("        if(checkCode.length!=1){ ");
		    scriptStr.append("         jsonValue += ')' ;  } ");
	        scriptStr.append("          return jsonValue ;");
		    scriptStr.append("         } ");
		    //清空树的所选中的checkbox 
		    scriptStr.append("   function  getCheckedIsFalseTree(treeId) { ");
		    scriptStr.append("      var codeTree = $.fn.zTree.getZTreeObj(treeId) ; ");
		    scriptStr.append("       codeTree.checkAllNodes(false) ; } ");
		   
		    scriptStr.append("</SCRIPT>");					 
					 
		 
         //生成INPUT和隐藏div
          
           scriptStr.append("<input id=\""+ name + "\" type=\"text\" onclick=\"createCodeTree_"+funcName+"_"+parentNo+"('"+parentNo+"')\" readonly  />");
		    
           scriptStr.append("<div id=\"" + divName + "\"  style=\"display:none;position: absolute;z-index:100;overflow:auto;height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;\" >");
           scriptStr.append("  <ul id=\""+treeDivName+"\" class=\"ztree\"></ul>");
           scriptStr.append("   </div> ");
		    
		    
		    
			 
			 //System.out.println(scriptStr.toString());
			
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

	 
	 
	public String getCnpyID() {
		return cnpyID;
	}

	public void setCnpyID(String cnpyID) {
		this.cnpyID = cnpyID;
	}
	
	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	
	public String getParentNo() {
		return parentNo;
	}

	public String getDivName() {
		return divName;
	}

	public void setDivName(String divName) {
		this.divName = divName;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getTreeDivName() {
		return treeDivName;
	}

	public void setTreeDivName(String treeDivName) {
		this.treeDivName = treeDivName;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

}
