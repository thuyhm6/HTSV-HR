<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
 function  openCertificatewin(){
     var $form = $("#viewCertificateEmploymentInfo");
     var empid = $form.find("#seach_EMPID").val();
     var deptno = $form.find("#seach_DEPTNO").val();
     window.document.getElementById("onCertificate").href="/report/hr/viewCertificateList?pageNum=1&seach_EMPID="+empid+"&seach_DEPTNO="+deptno;
         
 }
 function  openCertificateExecl(a){
      var $this=$(a);
      var title = $this.attr("title"); 
      var $from = $("#viewCertificateEmploymentInfo");  
      
	  var url ="/report/hr/viewCertificateEmploymentExcel";
	   alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
    
 }
</script>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	 <form id="viewCertificateEmploymentInfo" method="post" action="/report/hr/viewCertificateEmploymentInfo">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="5%" align="right"><%--部门名称--%>
						<spring:message code="org.orgManage.title.deptName"/>：
					</td>
					<td width="12%" align="left">
						<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}"/>
					</td>
					<td width="5%" align="center"><%--工号/姓名--%>
						<spring:message code="public.title.empIdAndName"/>：
					</td>
					<td width="10%" align="center">
					    <input type="text" id="seach_EMPID" name="seach_EMPID" maxlength="25" size="8" value="${EMPID }">
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul><%--
					<li>
					 <a class="button" id="onCertificate" onclick="openCertificatewin()" target="dialog" width="1000" height="420" fresh="false">
					 	<span>检索<spring:message code="hr.viewCondSql.title.JIANSUO"/></span></a>
					</li>--%>
		           <li>
					  <a class="button" onclick="openCertificateExecl(this)" <%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
					  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
		           </li>
				</ul>
			</div>
		</div>
	 </form>
</div>