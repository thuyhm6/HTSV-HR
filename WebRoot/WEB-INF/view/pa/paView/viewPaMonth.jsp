<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/paView/viewPaMonth" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/>:</td>
					<td>
						<input id="KEY" name="KEY" value="${KEY}" type="text"/>
					</td>
					<td>
						<!-- 考勤月 --><spring:message code="ar.excelexport.title.armonth"/>
					</td>
					<td>
						<ait:date yearName="paYear" yearSelected="${paYear}" monthName="paMonth" monthSelected="${paMonth}"/>
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
<div class="pageContent">${dataTable}</div>