<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

//C12 hrto销售系统
function makeC12PortalSap() {

		$.ajax({
			type: 'POST',
			cache: false,
			url: '/report/hr/addC12DateForInterface',
			success : function(responseText) {
					if (responseText == "Y"){
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasSuccesed"/>');
					}else{
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasFaild"/>');
					}
				}
			});
		
		return false;
}

//C12 hrto考勤食堂 部门
function makeC12PortalSapForMessDept() {

		$.ajax({
			type: 'POST',
			cache: false,
			url: '/report/hr/addC12DateForMessDeptInterface',
			success : function(responseText) {
					if (responseText == "Y"){
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasSuccesed"/>');
					}else{
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasFaild"/>');
					}
				}
			});
		
		return false;
}

//C12 hrto考勤食堂 人事
function makeC12PortalSapForMessUserInfo() {

		$.ajax({
			type: 'POST',
			cache: false,
			url: '/report/hr/addC12DateForMessUserInfoInterface',
			success : function(responseText) {
					if (responseText == "Y"){
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasSuccesed"/>');
					}else{
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasFaild"/>');
					}
				}
			});
		
		return false;
}


//C12 hrtosap人事信息
function makeC12PortalForSapPersonnelInfo() {

		$.ajax({
			type: 'POST',
			cache: false,
			url: '/report/hr/addC12DateForSapPersonnelInfoInterface',
			success : function(responseText) {
					if (responseText == "Y"){
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasSuccesed"/>');
					}else{
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasFaild"/>');
					}
				}
			});
		
		return false;
}


//C12 hrtosap奖金
function makeC12PortalForSapMoneyAward() {

		$.ajax({
			type: 'POST',
			cache: false,
			url: '/report/hr/addC12DateForSapMoneyAwardInterface',
			success : function(responseText) {
					if (responseText == "Y"){
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasSuccesed"/>');
					}else{
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasFaild"/>');
					}
				}
			});
		
		return false;
}
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/report/hr/viewC12HrToMarketInterface" method="post" rel="pagerForm" >
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>



<div class="pageContent">
	
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="l-button"
					style="width: 100px; float: left; margin-left: 10px;"
					onclick="makeC12PortalSap()">
					<spring:message code="hr.wHrToMarketInterface.title.SALES_SYSTEM"/>
					<!-- HRTO销售系统 -->
				</a>
			</li> 
			<li>
				<a class="l-button"
					style="width: 120px; float: left; margin-left: 10px;"
					onclick="makeC12PortalSapForMessDept()">
					<spring:message code="hr.wHrToMarketInterface.title.ATTENDANCE_DEPT"/>
					<!-- HRTO考勤食堂 部门 -->
				</a>
			</li> 
			<!-- <li>
				<a class="l-button"
					style="width: 120px; float: left; margin-left: 10px;"
					onclick="makeC12PortalSapForMessUserInfo()">
					HRTO考勤食堂 人事
				</a>
			</li> 
			 -->
			<li>
				<a class="l-button"
					style="width: 120px; float: left; margin-left: 10px;"
					onclick="makeC12PortalForSapPersonnelInfo()">
					<spring:message code="hr.wHrToMarketInterface.title.STUFF_INFO"/><!-- HRTOSAP员工信息 -->
				</a>
			</li> 
			<!--
			<li>
				<a class="l-button"
					style="width: 120px; float: left; margin-left: 10px;"
					onclick="makeC12PortalForSapMoneyAward()">
					HRTOSAP奖金
				</a>
			</li> 
			 -->
		</ul>
	</div>	
	
	
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="10%" align="center"><%--工号--%>
					<spring:message code="public.title.empId"/>
				</th>
				<th width="10%" align="center">
					<spring:message code="public.title.empName"/> <%--姓名--%>
				</th>
				<th width="10%" align="center">
					<spring:message code="public.title.empStatus"/> <%--状态--%>
				</th>
				<th width="10%" align="center">
					<spring:message code="public.title.empStore"/> <%--所属门店--%> 
				</th>
				<th width="10%" align="center">
					<spring:message code="public.title.empDept"/> <%--所属部门--%> 
				</th>
				<th width="10%" align="center">
					<spring:message code="public.title.empDeptName"/> <%--部门名称--%>  
				</th>
				<th width="10%" align="center">
					<spring:message code="public.title.empTypeCode"/> <%--人员类别代码--%>   
				</th>
				<th width="10%" align="center">
					<spring:message code="public.title.empTypeName"/> <%--人员类别名称--%>    
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td width="10%" align="center">${item.EMPID }</td>
					<td width="10%" align="center">${item.LOCAL_NAME }</td>
					<td width="10%" align="center">${item.CPNY_ID }</td>
					<td width="10%" align="center">${item.EMP_OFFICE }</td>
					<td width="10%" align="center">${item.DEPTNO }</td>
					<td width="10%" align="center">${item.DEPTNAME }</td>
					<td width="10%" align="center">${item.EMP_TYPE_CODE }</td>
					<td width="10%" align="center">${item.EMP_TYPE_NAME }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:set value="/report/hr/viewHrToMarketInterface" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
</div>

