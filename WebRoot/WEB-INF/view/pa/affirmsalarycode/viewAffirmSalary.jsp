<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

</script> 
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/pa/affirmsalarycode/viewAffirmSalaryCodeLis" method="post" rel="pagerForm">
<div class="pageContent">
	<table class="table" width="100%" layoutH="171">
		<thead>
			<tr>
			    <th width="5%"><spring:message code="sys.affirm.indexNum" /><!--序号--></th>
				<th width="15%"><spring:message code="ess.infoApply.title.affirmor"/><!--决裁者-->
					</th>
			    <th width="10%"> 
					<spring:message code="ess.viewApply.title.affirmCondition"/><!--决裁情况-->
				</th>
				<th width="15%">
					<spring:message code="hr.viewSuggestion.title.Suggestion"/><!--决裁意见-->
				</th>
				<th width="20%">
					<spring:message code="ess.salaryaffirm.title.affirmdate"/><!--决裁时间-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${salaryList}" var="item" varStatus="i">
				<tr target="NO" rel="${item.AFFIRM_ITEM_NO}">
				    <td>${i.index + 1}&nbsp;</td>
				    <td>
						${item.LOCAL_NAME }
					</td>
					<td>
						<c:if test="${item.ACTIVITY == 0}"><spring:message code="ess.trans.title.notAffirmed"/></c:if>
						<c:if test="${item.ACTIVITY == 1}"><spring:message code="ess.trans.title.affirmed"/></c:if>
						<c:if test="${item.ACTIVITY == 2}"><spring:message code="hr.viewTransactionTransViewList.title.FLAGVOTEDOWN"/></c:if>
					</td>
					<td>
						${item.AFFIRM_DESCR}
					</td>
					<td>
						${item.AFFIRM_DATE}
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	</div>
	</form>
</div>