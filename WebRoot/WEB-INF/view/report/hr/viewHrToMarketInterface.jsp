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
	//hrto销售系统
	function makeEmcDataToSap() {
		$.ajax({
			type: 'POST',
			cache: false,
			url: '/report/hr/addEmcDataToSap',
			success : function(responseText) {
					if(responseText == "Y"){
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasSuccesed"/>');
					}else{
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasFaild"/>');
					}
				}
			});
		return false;
	}
	//hrto考勤食堂 部门
	function makeAttDataToSap() {
		$.ajax({
			type: 'POST',
			cache: false,
			url: '/report/hr/addAttDataToSap',
			success : function(responseText) {
					if(responseText == "Y"){
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasSuccesed"/>');
					}else{
						alert('<spring:message code="alert.message.pa.salary.title.generateSAPHasFaild"/>');
					}
				}
			});
		return false;
	}
	//hrtosap人事信息
	function makeEmyDataToSap() {
		$.ajax({
			type: 'POST',
			cache: false,
			url: '/report/hr/addEmyDataToSap',
			success : function(responseText) {
					if(responseText == "Y"){
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
	<form onsubmit="return navTabSearch(this);" action="/report/hr/viewHrToMarketInterface" method="post" rel="pagerForm" >
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
				<li><div class="buttonActive">
					<div class="buttonContent"><button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button></div>
				</div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="l-button" style="width: 100px; float: left; margin-left: 10px;"
					onclick="makeEmcDataToSap()"><!-- HRTO销售系统 -->
					<spring:message code="hr.wHrToMarketInterface.title.SALES_SYSTEM"/>
				</a>
			</li> 
			<li>
				<a class="l-button" style="width: 120px; float: left; margin-left: 10px;"
					onclick="makeAttDataToSap()"><!-- HRTO考勤食堂 部门 -->
					<spring:message code="hr.wHrToMarketInterface.title.ATTENDANCE_DEPT"/>
				</a>
			</li>
			<li>
				<a class="l-button" style="width: 120px; float: left; margin-left: 10px;"
					onclick="makeEmyDataToSap()"><!-- HRTOSAP员工信息 -->
					<spring:message code="hr.wHrToMarketInterface.title.STUFF_INFO"/>
				</a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="10%" align="center"><%--工号--%>
					<spring:message code="public.title.empId"/>
				</th>
				<th width="10%" align="center"><%--姓名--%>
					<spring:message code="public.title.empName"/> 
				</th>
				<th width="10%" align="center"><%--状态--%>
					<spring:message code="public.title.empStatus"/> 
				</th>
				<th width="10%" align="center"><%--所属门店--%> 
					<spring:message code="public.title.empStore"/> 
				</th>
				<th width="10%" align="center"><%--所属部门--%> 
					<spring:message code="public.title.empDept"/> 
				</th>
				<th width="10%" align="center"><%--部门名称--%>  
					<spring:message code="public.title.empDeptName"/> 
				</th>
				<th width="10%" align="center"><%--人员类别代码--%>
					<spring:message code="public.title.empTypeCode"/>    
				</th>
				<th width="10%" align="center"><%--人员类别名称--%> 
					<spring:message code="public.title.empTypeName"/>    
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
