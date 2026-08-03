<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
	function viewPaInfo_ess0900(){
		var essYear=$("#essYear",navTab.getCurrentPanel()).val();
		var essMonth=$("#essMonth",navTab.getCurrentPanel()).val();
		$("#viewPaInfoForm_ess0900",navTab.getCurrentPanel()).attr("action", "/ess/infoView/viewPaInfo?essYear="+essYear+"&essMonth="+essMonth);
  		$("#viewPaInfoForm_ess0900",navTab.getCurrentPanel()).submit();

	}
</script>
<div class="pageHeader">
	<form id="viewPaInfoForm_ess0900" onsubmit="return navTabSearch(this);" action="/ess/infoView/viewPaInfo" method="post">
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
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="viewPaInfo_ess0900()"><spring:message code="button.search"/></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="pageContent">${dataTable}</div>