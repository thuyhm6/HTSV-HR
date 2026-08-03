<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
 function  openEmpOnStatusExecl(a){
      var $this=$(a);
      var title = $this.attr("title"); 
      var $from = $("#viewEmpOnStatusInfo");  
      
	  var url ="/report/hrc01/viewEmpOnStatusExcel";
	   alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
    
 }
 <%--
 function doResignPersonPaCheck(){
	if(!document.getElementById('do_PERSONPA_RESIGNATION').checked){
		document.getElementById('seach_PERSONPA_RESIGNATION').value = 'NO';
	}else if(document.getElementById('do_PERSONPA_RESIGNATION').checked){
		document.getElementById('seach_PERSONPA_RESIGNATION').value = 'YES';
	}
 }
 function exportPaInfo(obj){
	var $form = $("#viewEmpOnStatusInfo");
	
	var deptNo = $form.find("#seach_DEPTNO").val();
	var empid = $form.find("#seach_EMPID").val();
	var gradeLevel = $form.find("#seach_GRADE_LEVEL").val();
	var personpa = $form.find("#seach_PERSONPA_RESIGNATION").val();
	$.ajax({ 
		async: false,
		type: "POST",
		url: "/pa/excelExport/viewEmpOnStatusExcel?seach_DEPTNO="+deptNo+"&seach_EMPID="+empid+
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
--%>	
</script>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	 <form id="viewEmpOnStatusInfo" method="post" action="/report/hrc01/viewEmpOnStatusInfo">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="5%" style="text-align:center"><%--工资月--%>
						<spring:message code="ar.viewarprogress.title.gongziyue"/>：
					</td>
					<td width="6%" style="text-align:left">
						<select id="seach_YEAR_ONSTATUS" name="seach_YEAR_ONSTATUS" style="width:75px">
							<c:forEach var="i" begin="2000" end="2020" step="1"> 
						    	<option value="${i}" <c:if test="${YEAR_ONSTATUS eq i }">selected</c:if> >${i}</option>
						    </c:forEach> 
						 </select>
						<select id="seach_MONTH_ONSTATUS" name="seach_MONTH_ONSTATUS" >
							<option value="01" <c:if test="${MONTH_ONSTATUS eq '01' }">selected</c:if>>01</option>
							<option value="02" <c:if test="${MONTH_ONSTATUS eq '02' }">selected</c:if>>02</option>
							<option value="03" <c:if test="${MONTH_ONSTATUS eq '03' }">selected</c:if>>03</option>
							<option value="04" <c:if test="${MONTH_ONSTATUS eq '04' }">selected</c:if>>04</option>
							<option value="05" <c:if test="${MONTH_ONSTATUS eq '05' }">selected</c:if>>05</option>
							<option value="06" <c:if test="${MONTH_ONSTATUS eq '06' }">selected</c:if>>06</option>
							<option value="07" <c:if test="${MONTH_ONSTATUS eq '07' }">selected</c:if>>07</option>
							<option value="08" <c:if test="${MONTH_ONSTATUS eq '08' }">selected</c:if>>08</option>
							<option value="09" <c:if test="${MONTH_ONSTATUS eq '09' }">selected</c:if>>09</option>
							<option value="10" <c:if test="${MONTH_ONSTATUS eq '10' }">selected</c:if>>10</option>
							<option value="11" <c:if test="${MONTH_ONSTATUS eq '11' }">selected</c:if>>11</option>
							<option value="12" <c:if test="${MONTH_ONSTATUS eq '12' }">selected</c:if>>12</option>
						</select>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
					  <a class="button" onclick="openEmpOnStatusExecl(this)" <%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
					  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
		           	</li>
				</ul>
			</div>
		</div>
	 </form>
</div>