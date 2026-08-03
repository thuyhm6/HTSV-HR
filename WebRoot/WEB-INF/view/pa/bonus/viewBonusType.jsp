<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/bonus/viewBonusType" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="pa.bonus.title.bonusTypeName"/><!--类型名称-->：
					<input type="text" name="seach_TYPE_NAME" value="${TYPE_NAME }" />
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!--检索--></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent" >	
	<c:set value="/pa/bonus/addBonusTypeView" var="add_Url"/>
	<c:set value="600" var="add_width" />
	<c:set value="400" var="add_height" />
	<c:set value="/pa/bonus/deleteBonusTypeInfo?NO={sid}" var="delete_Url"/>
	<c:set value="/pa/bonus/updateBonusTypeView?NO={sid}" var="edit_Url"/>
	<c:set value="600" var="edit_width" />
	<c:set value="400" var="edit_height" />
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="50"><spring:message code="pa.insurance.title.bonusTypeID"/><!--奖金类型ID--></th>
				<th width="100"><spring:message code="pa.bonus.title.bonusTypeName"/><!--奖金类型名称--></th>
				<th width="100"><spring:message code="pa.insurance.title.description"/><!--描述--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bonusTypeList}" var="type" varStatus="i">
			
				<tr target="sid" rel="${type.TYPE_NO}&TYPE_ID=${type.TYPE_ID }">
					<td class="td_center">${type.TYPE_ID}</td>
					<td class="td_center">${type.TYPE_NAME}</td>
					<td class="td_center">${type.DESCR}</td>
				</tr>			
			</c:forEach>
			
		</tbody>
    <c:set value="/pa/bonus/viewBonusType" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</table>	
</div>