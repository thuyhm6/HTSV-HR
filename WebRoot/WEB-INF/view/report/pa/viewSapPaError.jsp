<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader" style="border:1px #B8D0D6 solid">
	 <form id="viewSapPaError" method="post" action="/report/pa/viewSapPaError">
			<table class="searchContent">
				<tr>
					<td style="text-align: center"><%--部门、工资年、月为必选项，请选择！--%>
						<spring:message code="pa.message.pa.check.potionsmustchoosed"/>
					</td>
				</tr>
			</table>
	 </form>
</div>