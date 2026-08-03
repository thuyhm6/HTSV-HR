<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/paView/viewPaYear" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/>:</td>
					<td>
						<input id="KEY" name="KEY" value="${KEY}" type="text"/>
					</td>
					<td>
						<!-- 年份 --><spring:message code="pa.payear.title.payear"/>
					</td>
					<td>
						<ait:date yearName="paYear" yearSelected="${paYear}"/>
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