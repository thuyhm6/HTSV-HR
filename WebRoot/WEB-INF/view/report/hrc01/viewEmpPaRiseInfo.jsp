<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
 function  openEmpPaRiseExecl(a){
      var $this=$(a);
      var title = $this.attr("title"); 
      var $from = $("#viewEmpPaRiseInfo");  
      
	  var url ="/report/hrc01/viewEmpPaRiseExcel";
	   alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
    
 }
 function doResignPersonPaCheck(){
	if(!document.getElementById('do_PERSONPA_RESIGNATION').checked){
		document.getElementById('seach_PERSONPA_RESIGNATION').value = 'NO';
	}else if(document.getElementById('do_PERSONPA_RESIGNATION').checked){
		document.getElementById('seach_PERSONPA_RESIGNATION').value = 'YES';
	}
 }
 function exportPaInfo(obj){
	var $form = $("#viewEmpPaRiseInfo");
	
	var deptNo = $form.find("#seach_DEPTNO").val();
	var empid = $form.find("#seach_EMPID").val();
	var gradeLevel = $form.find("#seach_GRADE_LEVEL").val();
	var personpa = $form.find("#seach_PERSONPA_RESIGNATION").val();
	$.ajax({ 
		async: false,
		type: "POST",
		url: "/pa/excelExport/exportPaRiseInfoExcel?seach_DEPTNO="+deptNo+"&seach_EMPID="+empid+
		     "&seach_GRADE_LEVEL="+gradeLevel+"&seach_PERSONPA_RESIGNATION="+personpa, 
		data: null,
		dataType: "json",
		success: function(resp){
			if(resp.pathStr=="N"){
				alertMsg.error('<spring:message code="pa.insurance.title.exportFaild"/>');
 			}else if(resp.pathStr=="K"){
 	 			alertMsg.error('<spring:message code="pa.insurance.title.exportExceling"/>');
			}else{
 				document.getElementById("exportPaRise").href="/pa/excelExport/downloadPaRiseInfo?pathstr="+resp.pathStr;
 				document.getElementById("exportPaRise").click();
 	 		} 
		} 
		});
	}
</script>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	 <form id="viewEmpPaRiseInfo" method="post" action="/report/hrc01/viewEmpPaRiseInfo">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="5%" align="right"><%--部门名称--%>
						<spring:message code="org.orgManage.title.deptName"/>：
					</td>
					<td width="15%" align="left">
						<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}"/>
					</td>
					<td width="5%" align="center"><%--工号/姓名--%>
						<spring:message code="public.title.empIdAndName"/>：
					</td>
					<td width="10%" align="center">
					    <input type="text" id="seach_EMPID" name="seach_EMPID" maxlength="25" size="8" value="${EMPID }">
					</td>
					<td width="5%" style="text-align: right"><%--职等>=--%>
						&nbsp;职等
					</td>
					<td width="15%" align="center">
						<select name="seach_GRADE_LEVEL" id="seach_GRADE_LEVEL">
									<option value="">
										<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /><!-- 请选择 -->
									</option>
									<c:forEach items="${gradeLevelList}" var="gradeLevel">
										<option value="${gradeLevel.GRADE_LEVEL_NO}"
											<c:if test="${gradeLevel.GRADE_LEVEL_NO eq GRADE_LEVEL}">selected</c:if>>
											>=${gradeLevel.GRADE_LEVEL_NAME}
										</option>
									</c:forEach>
								</select>
					</td>
					<td width="12%" style="text-align: right"><%-- 包含现离职人员 --%>
						<%--<spring:message code="hrm.includeoutstatus"/>--%>
						包含现离职人员：
					</td>
					<td style="text-align: left">
						<input type="checkbox" id="do_PERSONPA_RESIGNATION" name="do_PERSONPA_RESIGNATION" <c:if test="${PERSONPA_RESIGNATION ne 'NO'}">checked="checked"</c:if> onclick="doResignPersonPaCheck();"/>
						<input id="seach_PERSONPA_RESIGNATION" name="seach_PERSONPA_RESIGNATION" type="hidden" value="${PERSONPA_RESIGNATION }">
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<div class="subBar">
				<ul><%--
					<li>
					 <a class="button" id="empPaRise" onclick="openEmpPaRisewin()" target="dialog" width="1000" height="420" fresh="false">
					 	<span>检索<spring:message code="hr.viewCondSql.title.JIANSUO"/></span></a>
					</li>--%>
		           <li>
					  <a class="button" onclick="openEmpPaRiseExecl(this)" <%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
					  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
		           </li><%--
		           <li>
					  <a class="button" onclick="exportPaInfo(this);" title="<spring:message code='rp.report.title.exportYN'/>">
					  <span><spring:message code="ar.addempshift.title.excelexport"/></span></a>
					  <a class="buttonActive" id="exportPaRise" style="display: none">&nbsp;</a>
		           </li>--%>
				</ul>
			</div>
		</div>
	 </form>
</div>