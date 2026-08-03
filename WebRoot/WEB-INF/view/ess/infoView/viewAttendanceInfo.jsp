<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
	function viewAttendanceInfo_ess0700(){
		var essYear=$("#essYear",navTab.getCurrentPanel()).val();
		var essMonth=$("#essMonth",navTab.getCurrentPanel()).val();
		$("#viewPaInfoForm_ess0700",navTab.getCurrentPanel()).attr("action", "/ess/infoView/viewAttendanceInfo?essYear="+essYear+"&essMonth="+essMonth);
  		$("#viewPaInfoForm_ess0700",navTab.getCurrentPanel()).submit();

	}
</script>

<div class="pageHeader">
	<form id="viewPaInfoForm_ess0700" onsubmit="return navTabSearch(this);" action="/ess/infoView/viewAttendanceInfo" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/>:</td>
					<td>
						${view_EMPID} / ${view_LOCALNAME}
					</td>
					<td>
						<!-- 考勤月 --><spring:message code="ar.excelexport.title.armonth"/>
					</td>
					<td>
						<ait:date yearName="essYear" yearSelected="${essYear}" monthName="essMonth" monthSelected="${essMonth}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="viewAttendanceInfo_ess0700()"><spring:message code="button.search"/></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="pageContent">${dataTable}</div>