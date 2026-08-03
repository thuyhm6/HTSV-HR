<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/paView/viewPaAvg" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/>:</td>
					<td>
						<input id="KEY" name="KEY" value="${KEY}" type="text"/>
					</td>
					<td>
						<!-- 考勤月开始 --><spring:message code="ar.excelexport.title.armonthfrom"/>
					</td>
					<td>
						<ait:date yearName="paYearfrom" yearSelected="${paYearfrom}" monthName="paMonthfrom" monthSelected="${paMonthfrom}"/>
					</td>

					<td>
						<!-- 考勤月结束 --><spring:message code="ar.excelexport.title.armonthto"/>
					</td>
					<td>
						<ait:date yearName="paYearto" yearSelected="${paYearto}" monthName="paMonthto" monthSelected="${paMonthto}"/>
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
${dataTable}
</div>