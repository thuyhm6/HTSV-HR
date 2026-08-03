<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
 function  openPersonRecodewin(){
     var $form = $("#viewHrRegisterList");
     var empid = $form.find("#seach_EMPID").val();
     var deptno = $form.find("#seach_DEPTNO").val();
     var idcardno = $form.find("#seach_IDCARD_NO").val();
     window.document.getElementById("onPersonRecode").href=encodeURI(encodeURI("/report/hr/viewPersonRecodeList?pageNum=1&seach_EMPID="+empid+"&seach_DEPTNO="+deptno
         +"&seach_IDCARD_NO="+idcardno));
         
 }
 function  openPaTranserExecl(a){
     var $this=$(a);
     var title = $this.attr("title"); 
     var $form = $("#viewHrRegisterList");  
     
	  var url ="/report/hr/viewHrRegisterListTranserExcel";
	   alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $form.serialize();
					}});
}
</script>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	 <form id="viewHrRegisterList" method="post" action="/report/hr/viewPersonRecodeList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="5%" align="right"><%--部门名称--%>
						<spring:message code="org.orgManage.title.deptName"/>：
					</td>
					<td width="12%" align="left">
						<ait:deptTree name="seach_DEPTNO" limit="ar" selected="${DEPTNO}"/>
					</td>
					<td width="5%" align="center"><%--工号/姓名--%>
						<spring:message code="public.title.empIdAndName"/>：
					</td>
					<td width="10%" align="center">
					    <input type="text" id="seach_EMPID" name="seach_EMPID" maxlength="25" size="8" value="${EMPID }">
					</td>
			<!--	<td width="5%" align="center"><%--身份证号/护照号--%>
						<spring:message code="rp.report.title.idcardorpassport"/>：
					</td>
					<td width="10%" align="center">
					    <input type="text" id="seach_IDCARD_NO" name="seach_IDCARD_NO" maxlength="25" size="8" value="${IDCARD_NO }">
					</td>-->
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<a class="button" id="onPersonRecode" onclick="openPersonRecodewin()" target="dialog" width="1000" height="420" fresh="false">
							<span><%--检索--%><spring:message code="public.title.search"/></span></a>
						<a class="button" onclick="openPaTranserExecl(this)"  <%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
					  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>	
					</li>
				</ul>
			</div>
		</div>
	 </form>
</div>