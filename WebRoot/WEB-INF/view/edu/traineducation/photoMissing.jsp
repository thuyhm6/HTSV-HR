<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
</script>
<div class="pageContent" layoutH="10">
        <span>Total:${photoMissingListCount }</span>
		<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="10%">NO</td>
		<td class="td_title" width="10%"><!-- 社号 --><spring:message code="ess.infoApply.EMPID" /></td>
		<td class="td_title" width="10%"><!-- 姓名 --><spring:message code="ess.infoApply.NAME" /></td>
		<td class="td_title" width="20%"><!-- 部门 --><spring:message code="ess.infoApply.DEPT" /></td>
		</tr>
		<c:forEach items="${photoMissingList}" var="d" varStatus="i">
		<tr>
		<td class="td_type"  width="10%" >${i.count }</td>
		<td class="td_type"  width="10%" >${d.EMPID }</td>
		<td class="td_type"  width="10%" >${d.LOCAL_NAME }</td>
		<td class="td_type"  width="20%" >${d.ORG_NAME_LOCAL }</td>
		</tr>
		</c:forEach>
		</table>
		<div class="subBar" style="padding-left: 650px;">
		</div>
</div>
