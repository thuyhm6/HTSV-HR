<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function openMonthWorkScheduleExcel(a){
     var $this=$(a);
     var title = $this.attr("title"); 
     var $from = $("#viewMonthWorkSchedule");  
	 var url ="/ar/attendanceView/viewMonthWorkScheduleExcel";
	 alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
   
}
</script>
<div class="pageHeader" >
	<form id="viewMonthWorkSchedule" name="viewMonthWorkSchedule" onsubmit="return navTabSearch(this);" action="/ar/attendanceView/viewMonthWorkSchedule" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td  style="text-align:right"><!-- 年份 -->
					<spring:message code="pa.payear.title.payear"/>：
				</td>
				<td  style="text-align:left">
					<ait:date yearName="seach_paYear" yearSelected="${paYear}" monthName="seach_paMonth" monthSelected="${paMonth}"/>
				</td>
				<td>
					<spring:message code="public.title.deptName"/><!--部门-->：
				</td>
				<td>
					<ait:deptTree name="seach_DEPTNO" limit="ar" selected="${DEPTNO}" />
			   </td>
			   <td>考勤区间</td>
				<td  style="text-align:left">
					<select class="combox" id="combox_STAT_NO" name="seach_STAT_NO">
						<c:forEach items="${qujianList}" var="qujian" varStatus="i">
							<option value="${qujian.STAT_NO}" <c:if test="${STAT_NO eq qujian.STAT_NO }">selected</c:if>>${qujian.STAT_NAME}</option>
					    </c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<div class="subBar">
		 	<ul><li><div class="buttonActive"><div class="buttonContent"><button type="submit">
		 		&nbsp;<spring:message code="public.title.search"/><!--检索-->&nbsp;</button></div></div></li></ul>
		</div> 
	</div>
	</form>
</div>

<div class="pageContent">
	<div class="formBar">
		<ul class="toolBar">
			 <li>
				  <a class="edit" onclick="openMonthWorkScheduleExcel(this)"<%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
				  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
	           </li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th>序号</th>
				<th>部门</th>
				<th>工号</th>
				<th>姓名</th>
				<th>职位</th>
				<th>职级名称</th>
				<c:forEach items="${monthDay}" var="ma" varStatus="i">
				<th>${ma.MONTHDAY}</th>	
			    </c:forEach>  
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${monthWorkSchedule}" var="pa" varStatus="i">
				<tr target="EMPID" rel="${pa.EMPID}">
					<td style="text-align: center">${i.index+1 }</td>
					<td style="text-align: center">${pa.DEPT_NAME}</td>
					<td style="text-align: center">${pa.EMPID}</td>
					<td style="text-align: center">${pa.LOCAL_NAME}</td>
					<td style="text-align: center">${pa.POSITION_NAME}</td>
					<td style="text-align: center">${pa.DUTY_NAME}</td>
					<c:forEach items="${pa.monthDay}" var="monthDay" varStatus="i">
						<td style="text-align: center">${monthDay.BANCI}</td>
					</c:forEach>  
				</tr>
			</c:forEach>    
		</tbody>
	</table>
	<c:set value="/ar/attendanceView/viewMonthWorkSchedule" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>