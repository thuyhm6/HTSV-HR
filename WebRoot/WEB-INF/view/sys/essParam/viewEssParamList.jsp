<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	 
</script> 
<div class="pageContent">
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="800" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/sys/essParam/updateEssParamView?PARAM_NO={sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="138" nowrapTD="false">
			<thead>
			<tr>
				<th width="10"><spring:message code="sys.arAffirmPost.title.classification"/><!--分类--></th>
				<th width="190"><spring:message code="sys.essParam.title.essParamName"/><!--ESS参数名称--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${essParamList}" var="essParam" varStatus="i">
				<tr height="30" target="sid" rel="${essParam.PARAM_NO}">
					<td>
						<c:if test="${essParam.MODULE eq 'ESS'}">
						<spring:message code="sys.essParam.title.essParameter"/><!--ESS参数--></c:if>
						<c:if test="${essParam.MODULE eq 'HRM'}">
						<spring:message code="sys.essParam.title.hrParameter"/><!--人事参数--></c:if>
					</td>
					<td>
						${essParam.PARAM_NAME} 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table> 
</div>