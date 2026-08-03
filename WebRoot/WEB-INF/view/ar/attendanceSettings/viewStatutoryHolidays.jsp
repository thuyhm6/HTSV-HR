<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewStatutoryHolidays" method="post" id="viewStatutoryHolidays">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<ait:date yearName="seach_year" yearMinus="10" yearPlus="10" yearSelected="${year}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="button.search"/></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="pageContent">
	
	<c:set value="dialog" var="add_tab"/>
	<c:set value="600" var="add_width"/>
	<c:set value="300" var="add_height"/>
	<c:set value="/ar/attendanceSettings/addStatutoryHolidaysView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="navTabAjaxDoneWithForm" var="delete_callback"/>
	<c:set value="/ar/attendanceSettings/deleteStatutoryHolidaysInfo?DDATE_STR={dataStr}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="300" var="edit_height"/>
	<c:set value="/ar/attendanceSettings/updateStatutoryHolidaysView?DDATE_STR={dataStr}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="700px;" layoutH="206">
		<thead>
			<tr>
				<th width="10">No.</th>
				<th width="10"><spring:message code="org.title.DATE"/><!-- 日期 --></th>
				<th width="10"><spring:message code="sys.affirm.title.type"/><!-- 类型 --></th>
				<th width="10"><spring:message code="hrm.empinfo.REMARK"/><!-- 备注 --></th>
				<th width="20"><spring:message code="org.title.UPDATED_IP"/><!-- 变更者 --></th>
				<th width="20"><spring:message code="org.title.UPDATE_DATE"/><!-- 变更时间 --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${statutoryHolidaysList}" var="cycle" varStatus="i">
				<tr target="dataStr" rel="${cycle.DDATE}">
					<td>${i.count }</td>
					<td>${cycle.DDATE}</td>
					<td>${cycle.CONTENT}</td>
					<td>${cycle.REMARK}</td>
					<td>${cycle.EMPID}--${cycle.LOCAL_NAME}--${cycle.UPDATED_IP}</td>
					<td>${cycle.UPDATE_DATE}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>