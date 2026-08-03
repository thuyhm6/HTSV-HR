<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
</script> 
<div class="pageHeader">
 
	<form onsubmit="return navTabSearch(this);" action="/sys/arAffirm/viewArAffirmFinalList" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
	              <td>
					<spring:message code="sys.affirm.title.applyType"/><!--申请类型-->
	              </td> 
	              <td>
	              	<select name="seach_APPLY_TYPE">
					    <option value="">select</option>
						<c:forEach items="${applyList}" var="apply">
						  <option value="${apply.CODE_NO}" <c:if test="${apply.CODE_NO eq APPLY_TYPE}">selected="selected"</c:if>>${apply.CODENAME}</option>
						</c:forEach>
					</select>
	              </td> 
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="pageContent">
	<c:set value="navTab" var="add_tab"/>
	<c:set value="800" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/sys/arAffirm/addArAffirmFinalView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/sys/arAffirm/deleteArAffirmFinalInfo?APPLY_PARAM_NO={sid}" var="delete_Url"/>
	<c:set value="navTab" var="edit_tab"/>
	<c:set value="800" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/sys/arAffirm/updateArAffirmFinalView?APPLY_PARAM_NO={sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206" nowrapTD="false">
			<thead>
			<tr>
				<th><spring:message code="sys.affirm.title.applyType"/><!--申请类型--></th>
				<th><spring:message code="sys.affirm.title.ifReference"/><!--是否参考--></th>       
				<th><spring:message code="sys.affirm.title.startFlag"/><!--开始标志--></th>  
				<th><spring:message code="sys.affirm.title.startMigrationDirection"/><!--开始偏移方向--></th>  
				<th><spring:message code="sys.affirm.title.startLength"/><!--开始长度--></th> 
				<th><spring:message code="sys.affirm.title.endFlag"/><!--结束标志--></th> 
				<th><spring:message code="sys.affirm.title.endMigrationDirection"/><!--结束偏移方向--></th> 
				<th><spring:message code="sys.affirm.title.endLength"/><!--结束长度--> </th>				 
				<th>审批线等级长度<!--审批线等级长度--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arAffirmList}" var="item" varStatus="i">
			
				<tr target="sid" rel="${item.APPLY_PARAM_NO}">
				    <td>${item.APPLY_NAME}</td>	
				    <td>
						<c:if test="${item.REFERENCN_FLAG eq '1'}"><spring:message code="sys.affirm.title.yes"/><!--是--></c:if>
						<c:if test="${item.REFERENCN_FLAG eq '0'}"><spring:message code="sys.affirm.title.no"/><!--否--></c:if>
					</td>
					<td>
						<c:if test="${item.REFERENCN_FROM_FLAG eq '1'}"><spring:message code="sys.affirm.title.yes"/><!--是--></c:if>
						<c:if test="${item.REFERENCN_FROM_FLAG eq '0'}"><spring:message code="sys.affirm.title.no"/><!--否--></c:if>
					</td>
					<td>${item.REFERENCN_FROM_RELATION}</td>
					<td>${item.REFERENCN_FROM_OFFSET}</td>
					<td>
						<c:if test="${item.REFERENCN_TO_FLAG eq '1'}"><spring:message code="sys.affirm.title.yes"/><!--是--></c:if>
						<c:if test="${item.REFERENCN_TO_FLAG eq '0'}"><spring:message code="sys.affirm.title.no"/><!--否--></c:if>
					</td>
					<td>${item.REFERENCN_TO_RELATION}</td>
					<td>${item.REFERENCN_TO_OFFSET}</td>
					<td>${item.AFFIRM_LEVEL}</td>			
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/sys/arAffirm/viewArAffirmFinalList" var="pageUrl"/>
	 <%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
