
package com.ait.web.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.ait.hrm.dao.HrmDao;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.PostDao;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;

/**
 * 
* @ClassName: SelectTransferEmpInfoTag 
* @Description: TODO
* @author yorio youjia@ait.net.cn
* @date Aug 27, 2013 3:05:25 PM 
*
 */
public class SelectTransferEmpInfoTag extends RequestContextAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -115060950241451913L;

	protected String searchDeptName = null;

	protected String searchPostGradeName = null;

	protected String searchPersonName = null;

	protected String select1Name = null;

	protected String select2Name = null;
	
	protected String leftBtnName = null; 
	
	protected String rightBtnName = null; 
	
	protected String limit = null; 
	
	protected String select3Name = null; 
	
	protected String personidName = null; 


	


	@Autowired
	private HrmDao hrmDao;	
	
	@Autowired
	private PostDao postDao;	

	/**
	 * tag body
	 */
	@SuppressWarnings("unchecked")
	public int doStartTagInternal() throws JspException {
		

		if (searchDeptName != null) {
			searchDeptName = eval("searchDeptName", searchDeptName, Object.class).toString();
		} else {
			searchDeptName = "";
		}
		
		if (searchPostGradeName != null) {
			searchPostGradeName = eval("searchPostGradeName", searchPostGradeName, Object.class).toString();
		} else {
			searchPostGradeName = "";
		}

		if (searchPersonName != null) {
			searchPersonName = eval("searchPersonName", searchPersonName, Object.class).toString();
		} else {
			searchPersonName = "";
		}

		if (select1Name != null) {
			select1Name = eval("select1Name", select1Name, Object.class).toString();
		} else {
			select1Name = "";
		}
		if (select2Name != null) {
			select2Name = eval("select2Name", select2Name, Object.class).toString();
		} else {
			select2Name = "";
		}
		if (select3Name != null) {
			select3Name = eval("select3Name", select3Name, Object.class).toString();
		} else {
			select3Name = "";
		}
		if (limit != null) {
			limit = eval("limit", limit, Object.class).toString();
		} else {
			limit = "hr";
		}
		
		if (leftBtnName != null) {
			leftBtnName = eval("leftBtnName", leftBtnName, Object.class).toString();
		} else {
			leftBtnName = "";
		}
		
		if (rightBtnName != null) {
			rightBtnName = eval("rightBtnName", rightBtnName, Object.class).toString();
		} else {
			rightBtnName = "";
		}
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

		hrmDao = (HrmDao)getRequestContext().getWebApplicationContext()
									.getAutowireCapableBeanFactory()
									.getBean("hrmDaoImpl");

		postDao = (PostDao)getRequestContext().getWebApplicationContext()
									.getAutowireCapableBeanFactory()
									.getBean("postDaoImpl");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		String language = admin.getLanguage();
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		paramMap.put("interLanguage", language);
		List postGradeList = postDao.getPostGradeList(paramMap) ;
		List deptList=new ArrayList();
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
			JspWriter writer = pageContext.getOut();

			StringBuffer scriptStr = new StringBuffer(200) ;

			scriptStr.append("<SCRIPT type='text/javascript'>");			
			
			scriptStr.append("var setting"+searchDeptName+"_test = {");
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
			scriptStr.append("	onClick: onClick"+searchDeptName+"_test");
			scriptStr.append("}");
			scriptStr.append("};");
			scriptStr.append("var zNodes"+searchDeptName+"_test = ");
			
			scriptStr.append(JsonUtil.writeInternal(deptList)) ;
			
			scriptStr.append(";");
			
			scriptStr.append("function onClick"+searchDeptName+"_test(event, treeId, treeNode) {");
			scriptStr.append("var deptName = $(\"#deptName"+searchDeptName+"_test\").val();");
			
			scriptStr.append("if(deptName == treeNode.DEPTNAME){");
			scriptStr.append("	$(\"#deptName"+searchDeptName+"_test\").attr(\"value\", '');");
			scriptStr.append("	$(\":input[sysLong='test']\").attr(\"value\", '');");

			//jjy
			scriptStr.append("$(\"#deptContent"+searchDeptName+"_test\").fadeOut(\"fast\");");
			
			scriptStr.append("}else{");
			scriptStr.append("	$(\"#deptName"+searchDeptName+"_test\").attr(\"value\", treeNode.DEPTNAME);");
			
			scriptStr.append("	$(\":input[sysLong='"+searchDeptName+"test']\").attr(\"value\", treeNode.DEPTNO);");
			
			//jjy
			scriptStr.append("$(\"#deptContent"+searchDeptName+"_test\").fadeOut(\"fast\");");
			
			scriptStr.append("}");
			scriptStr.append("}");
			scriptStr.append("function showTree"+searchDeptName+"_test() {");
			//先清除文本框的值
			scriptStr.append("$(\"#deptName"+searchDeptName+"_test\").val(\"\");");
			scriptStr.append("$(\":input[sysLong='test']\").attr(\"value\", '');");
			scriptStr.append("var cityObj = $(\"#deptName"+searchDeptName+"_test\");");
			scriptStr.append("var cityOffset = $(\"#deptName"+searchDeptName+"_test\").offset();");
			scriptStr.append("$(\"#deptContent"+searchDeptName+"_test\").offset({top:cityOffset.top + \"px\", left:cityOffset.left + cityObj.outerHeight() + \"px\" }).slideDown(\"fast\");");
			scriptStr.append("$(\"body\").bind(\"mousedown\", onBodyDown"+searchDeptName+"_test);");
			scriptStr.append("}");
			scriptStr.append("function hideMenu"+searchDeptName+"_test() {");
			scriptStr.append("$(\"#deptContent"+searchDeptName+"_test\").fadeOut(\"fast\");");
			scriptStr.append("$(\"body\").unbind(\"mousedown\", onBodyDown"+searchDeptName+"_test);");
			scriptStr.append("}");
			scriptStr.append("function onBodyDown"+searchDeptName+"_test(event) {");
			scriptStr.append("if (!(event.target.id == \"menuBtn\" || event.target.id == \"deptContent"+searchDeptName+"_test\" || $(event.target).parents(\"#deptContent"+searchDeptName+"_test\").length>0)) {");
			scriptStr.append("hideMenu"+searchDeptName+"_test();");
			scriptStr.append("}");
			scriptStr.append("}");
			scriptStr.append("$(document).ready(function(){");
			scriptStr.append("$.fn.zTree.init($(\"#deptTree"+searchDeptName+"_test\"), setting"+searchDeptName+"_test, zNodes"+searchDeptName+"_test);");
//			if (selected != null && selected.length() > 0){
//				scriptStr.append("var $treeObj_test = $.fn.zTree.getZTreeObj(\"deptTree_test\");");
//				scriptStr.append("var $nodeObj_test = $treeObj_test.getNodeByParam('DEPTNO','" + selected + "');");
//				scriptStr.append("if($nodeObj_test != null ){");
//				scriptStr.append("$treeObj_test.selectNode($nodeObj_test);");
//				scriptStr.append("onClick_test(null, null, $nodeObj_test);");
//				scriptStr.append("}");
//			}
			
			
			//搜索人员按钮
			scriptStr.append("$('#seaEmpBtn"+searchDeptName+"').click(function(){");
			
			scriptStr.append("	var selectType = document.getElementById('"+select3Name+"').value;");
			if("ko".equals(language)){
				scriptStr.append("if(selectType==''){alertMsg.error('발령유형을 선택 해 주세요');return false;}");
			}else{
				scriptStr.append("if(selectType==''){alertMsg.error('请先选择调令类型');return false;}");
			}
			
			
			
			scriptStr.append("	var dep = document.getElementById('"+searchDeptName+"').value;");
			scriptStr.append("	var postno = document.getElementById('postNoName').value;");
			scriptStr.append("	var ggs = document.getElementById('"+searchPostGradeName+"').value;");
			scriptStr.append("	var name = document.getElementById('"+searchPersonName+"').value;");
			scriptStr.append("	var sel = $('#"+select1Name+"');");
			//获得异动类型
			//scriptStr.append("	var selTransType = $('#"+select1Name+"');");
			if(!select3Name.equals("")){
				scriptStr.append("	var select3Name = $('#"+select3Name+"').val();");
			}
		
			scriptStr.append("	sel.empty();");
			if("ko".equals(language)){
				scriptStr.append("if($('#deptName"+searchDeptName+"_test').val()==''&&$('#"+searchPersonName+"').val()==''&&$('#"+searchPostGradeName+"').val()==''){alertMsg.error('조회 조건을 선택해주세요.');return false;}");
			}else{
				scriptStr.append("if($('#deptName"+searchDeptName+"_test').val()==''&&$('#"+searchPersonName+"').val()==''&&$('#"+searchPostGradeName+"').val()==''){alertMsg.error('条件不足,请选择查看条件');return false;}");
			}
			
			//searchPersonName,searchPostGradeName
			
			scriptStr.append("	$.ajax({");
			scriptStr.append("		cache : false,");
			scriptStr.append("		type : 'post',");
			scriptStr.append("		url : '/hrm/transferOrder/viewOrderList?',");
			scriptStr.append("		data : 'seach_DEPTNO=' + dep + '&ggs=' + ggs + '&name=' + name+'&postno='+postno");
			if(!select3Name.equals("")){
				scriptStr.append("+'&selectType='+select3Name");
			}
			scriptStr.append(",");
			scriptStr.append("		dataType : 'json',");
			scriptStr.append("		success : function(data) {");
			
			scriptStr.append("			$.each(data, function(key, value) {");
			
			if("ko".equals(language)){
				scriptStr.append("	if(key=='len'){alertMsg.error('조건에 부합되는 사람을 찾을 수 없습니다 ');return false;}	");
			}else{
				scriptStr.append("	if(key=='len'){alertMsg.error('没有查询到符合条件的人员');return false;}	");
			}
			
			scriptStr.append("				if ($(data).size() > 0) {");
			
			scriptStr.append("					sel.append('<option value=\"' + value + '\" selected>' + key + '</option>');");
			scriptStr.append("				}");
			
			scriptStr.append("			});");
			scriptStr.append("		}");
			scriptStr.append("	});");
			scriptStr.append("});");
						
			scriptStr.append("$('#"+leftBtnName+"').click(function() {");
			//获取选择的值  
			scriptStr.append("	$('#"+select1Name+" option:selected').each(function(i) {");
			//在右边添加所选值，并且添加之后在左边删除所选值
			scriptStr.append("				$('#"+select2Name+"').append('<option value=\"' + this.value + '\">'+ this.text + '</option>');");
			scriptStr.append("			}");
			scriptStr.append("		).remove();");
			scriptStr.append("	});");
						
			//为删除按钮增加事件 
			scriptStr.append("$('#"+rightBtnName+"').click(function() {");
			//获取所选择的值
			scriptStr.append("		$('#"+select2Name+" option:selected').each(function(i) {");
						//在左边添加所选值，并且添加之后在右边删除所选值 
			scriptStr.append("				$('#"+select1Name+"').append('<option>' + this.text + '</option>');");
			scriptStr.append("			}).remove();");
			scriptStr.append("	});");
			
			scriptStr.append("});");
			//职级关联职级名称
			scriptStr.append("function postNoNameTr(){");
			scriptStr.append("var postGradeNo=$('#"+searchPostGradeName+"').val();");
			//scriptStr.append("$('#"+searchPostGradeName+"').length=0;");
			                                    
			scriptStr.append("var postGradeNo1= document.getElementById('postNoName');");
			scriptStr.append("    postGradeNo1.length=0;");
			scriptStr.append("	$.ajax({");
			scriptStr.append("		cache : false,");
			scriptStr.append("		type : 'post',");
			scriptStr.append("		url : '/hrm/transferOrder/getPositionInfoByPostGradeNo?',");
			scriptStr.append("		data : 'postGradeNo=' + postGradeNo ");
			scriptStr.append(",");
			scriptStr.append("		dataType : 'json',");
			scriptStr.append("		success : function(data) {");
			scriptStr.append("$('#"+searchPostGradeName+"').length=0;");
			scriptStr.append("			$.each(data, function(key, value) {");
			
			scriptStr.append("				document.getElementById('postNoName').options.add(new Option(value,key));");
			scriptStr.append("			});");
			scriptStr.append("		}");
			scriptStr.append("	});");
			scriptStr.append("}");
			scriptStr.append("</SCRIPT>");			
			
			
			
			StringBuffer sbScript = new StringBuffer();
			
			sbScript.append("<table width='100%' border='0' cellpadding='0' cellspacing='0' class='user_table'>");
			sbScript.append("	<tr>");
			sbScript.append("		<td class='td_title' width='10%'>");
			
			if("ko".equals(language)){
				sbScript.append("사번/이름");
			}else{
				sbScript.append("工号/姓名");
			}
			
			sbScript.append("		</td>");
			sbScript.append("		<td class='td_type' width='10%'>");
			sbScript.append("			<input type='text' id='"+searchPersonName+"' name='"+searchPersonName+"' />");
			
			sbScript.append("		</td>");
			sbScript.append("		<td class='td_color' rowspan='3' align='center'  width='100'>");
			if("ko".equals(language)){
				//<div class="buttonActive"><span>检索</span></div>
				//sbScript.append("			<input id='seaEmpBtn"+searchDeptName+"' type='button' value='조회' style='height=10px;width=20px;' />");		
				sbScript.append("<div align='center' id='seaEmpBtn"+searchDeptName+"' class='buttonActive align_center'><span align='center'>조회</span></div>	");
			}else{
				//sbScript.append("			<input id='seaEmpBtn"+searchDeptName+"' type='button' value='检索' style='height=10px;width=20px;' />");		
				sbScript.append("<div align='center' id='seaEmpBtn"+searchDeptName+"' class='buttonActive align_center'><span align='center'>检索</span></div>	");
			}
			sbScript.append("		</td>");
			sbScript.append("		<td class='td_type' rowspan='3' colspan='3' width='25%' height='21' align='center' valign='middle'>");					
			sbScript.append("			<div align='center'>");

			if("ko".equals(language)){
				sbScript.append("			<h4 align='center'>검색결과</h4>");
			}else{
				sbScript.append("			<h4 align='center'>检索结果</h4>");
			}
			sbScript.append("				<select name='"+select1Name+"' id='"+select1Name+"' multiple='multiple' size='12'style='width: 200px; height: 140px;'>");
			sbScript.append("				</select>");
			sbScript.append("			</div>");
			sbScript.append("		</td>");
			sbScript.append("		<td rowspan='3' class='td_color' width='100' align='center' valign='middle'>");
			sbScript.append("			<div align='center'>");
			sbScript.append("				<img id='"+leftBtnName+"' alt='' src='/resources/images/newImages/arrow_right_all.jpg'>");
			sbScript.append("				<br/><br/><br/><br/>");
			sbScript.append("				<img alt='' id='"+rightBtnName+"' src='/resources/images/newImages/arrow_left_all.jpg'>");
			sbScript.append("			</div>");
			sbScript.append("		</td>");
			sbScript.append("		<td class='td_type' rowspan='3' colspan='3'  height='21' align='center' valign='middle'  width='25%' >");
			sbScript.append("			<div align='center'>");
//			sbScript.append("				<h4 align='center'>选择调令</h4>");
			if("ko".equals(language)){
				sbScript.append("			<h4 align='center'>선택된사원</h4>");
			}else{
				sbScript.append("			<h4 align='center'>已选员工</h4>");
			}
			sbScript.append("				<select id='"+select2Name+"' multiple='multiple' size='12' style='width: 200px; height: 140px;'>");
			sbScript.append("				</select>");
			sbScript.append("			</div>");
			sbScript.append("		</td>");
			sbScript.append("		<td  rowspan='3' class='td_color' width='100'>");
			sbScript.append("			<div align='center'>");
//			sbScript.append("				<input type='button' value='确认' onclick='falingOk()'/>");
			if("ko".equals(language)){
				//sbScript.append("				<input type='button' value='' onclick='falingOk"+leftBtnName+"()'/>");
				sbScript.append("<div align='center' onclick='falingOk"+leftBtnName+"()' class='buttonActive align_center'><span>확인</span></div>	");
			}else{
				//sbScript.append("				<input type='button' value='确认' onclick='falingOk"+leftBtnName+"()'/>");
				sbScript.append("<div align='center' onclick='falingOk"+leftBtnName+"()' class='buttonActive align_center'><span>确认</span></div>	");
			}
			sbScript.append("			</div>");
			sbScript.append("		</td>");
			sbScript.append("	</tr>");
			sbScript.append("	<tr>");
//			sbScript.append("		<td class='td_title'>工号사번/姓名이름</td>");
			sbScript.append("		<td class='td_title' width='10%'>");
			if("ko".equals(language)){
				sbScript.append("부서");				
			}else{
				sbScript.append("部门");				
			}
			sbScript.append("		</td>");
			sbScript.append("		<td class='td_type'>");
			//生成INPUT和隐藏字段
			sbScript.append("		<input id=\"deptName"+searchDeptName+"_test\" type=\"text\" onclick=\"showTree"+searchDeptName+"_test()\" readonly  />");
			sbScript.append("		<input id=\""+searchDeptName+"\" name=\""+searchDeptName+"\" type=\"hidden\" sysLong=\""+searchDeptName+"test\"/>");
			sbScript.append("		<div id=\"deptContent"+searchDeptName+"_test\" style=\"display:none; position: absolute;z-index:0;overflow:auto;height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;\" >");
			sbScript.append("			<ul id=\"deptTree"+searchDeptName+"_test\" class=\"ztree\" style=\"margin-top:0; width:300px;overflow:auto\"></ul>");
			sbScript.append("		</div>");
			
			sbScript.append("		</td>");
			sbScript.append("	</tr>");
			sbScript.append("	<tr>");
			sbScript.append("		<td class='td_title'>");
//			sbScript.append("			职级직급");
			if("ko".equals(language)){
				sbScript.append("직급");
			}else{
				sbScript.append("职级");
			}
			sbScript.append("		</td>");
			sbScript.append("		<td class='td_type'>");
			sbScript.append("			<select onchange='postNoNameTr()' name='"+searchPostGradeName+"' id='"+searchPostGradeName+"' >");
			

			if("ko".equals(language)){
				sbScript.append("					<option value=''>선택하세요</option>");
			}else{
				sbScript.append("				<option value=''>请选择</option>");
			}
			for(int i=0 ; i < postGradeList.size() ; i++){
				LinkedHashMap linkMap = (LinkedHashMap)postGradeList.get(i);
				sbScript.append("			<option value='"+linkMap.get("POST_GRADE_NO")+"'>"+linkMap.get("GRADENAME")+"</option>");
			}
//			sbScript.append("		 		<c:forEach items='"+postGradeList+"' var='grade'>");
//			sbScript.append("					<option value='${grade.POST_GRADE_NO}'>${grade.GRADENAME}</option>");
//			sbScript.append("				</c:forEach>");
			sbScript.append("			</select>");
			
			sbScript.append("<select id='postNoName' name='postNoName'></select>");
			sbScript.append("		</td>");
			sbScript.append("	</tr>");
			
			sbScript.append("	<tr>");
			sbScript.append("	</tr>");
			sbScript.append("</table>");
//			System.out.print(scriptStr.toString()+sbScript.toString());
			writer.println(scriptStr.toString()+sbScript.toString());
			
		} catch (IOException ex) {
			throw new JspTagException(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}


	private Object eval(String attName, String attValue, Class clazz) throws JspException {
		Object obj = ExpressionEvaluatorManager.evaluate(attName, attValue, clazz, this, pageContext);
		if (obj == null) {
			// throw new NullAttributeException(attName, attValue);
			return "";
		} else {
			return obj;
		}
	}

	public void setSearchDeptName(String searchDeptName) {
		this.searchDeptName = searchDeptName;
	}

	public void setSearchPostGradeName(String searchPostGradeName) {
		this.searchPostGradeName = searchPostGradeName;
	}

	public void setSearchPersonName(String searchPersonName) {
		this.searchPersonName = searchPersonName;
	}

	public void setSelect1Name(String select1Name) {
		this.select1Name = select1Name;
	}

	public void setSelect2Name(String select2Name) {
		this.select2Name = select2Name;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}


	public void setLeftBtnName(String leftBtnName) {
		this.leftBtnName = leftBtnName;
	}


	public void setRightBtnName(String rightBtnName) {
		this.rightBtnName = rightBtnName;
	}
	public String getSelect3Name() {
		return select3Name;
	}


	public void setSelect3Name(String select3Name) {
		this.select3Name = select3Name;
	}
	public String getPersonidName() {
		return personidName;
	}


	public void setPersonidName(String personidName) {
		this.personidName = personidName;
	}
	
}
