<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/bonus/viewBonusTypeParam" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="pa.bonus.title.bonusTypeName"/><!--类型名称-->：
					<input type="text" name="seach_ALIAS_NAME" value="${ALIAS_NAME}" />
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
	
	<c:set value="/pa/bonus/addBonusTypeParamView" var="add_Url"/>
	<c:set value="600" var="add_width" />
	<c:set value="400" var="add_height" />
	<c:set value="/pa/bonus/deleteBonusTypeParamInfo?PARAM_NO={sid}" var="delete_Url"/>
	<c:set value="/pa/bonus/updateBonusTypeParamView?PARAM_NO={sid}" var="edit_Url"/>
	<c:set value="600" var="edit_width" />
	<c:set value="400" var="edit_height" />
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	      
		<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="100"><spring:message code="pa.insurance.title.companyLegalPerson"/><!--公司法人--></th>
				<th width="80"><spring:message code="pa.bonus.title.bonusTypeName"/><!--中文别名--></th>
				<th width="100"><spring:message code="pa.bonus.title.ifRelatedWithSalary"/><!--是否和工资关联--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bonusTypeParamList}" var="bonusTypeParam">			
				<tr target="sid" rel="${bonusTypeParam.PARAM_NO}">
					<td class="td_center">${bonusTypeParam.CPNY_NAME}</td>						
					<td class="td_center">${bonusTypeParam.ALIAS_NAME}</td>
				    <td class="td_center">
				        <c:if test="${bonusTypeParam.PA_RELEVANCE_FLAG == 1}">
				        <spring:message code="pa.insurance.title.yes"/><!--是--></c:if>
						<c:if test="${bonusTypeParam.PA_RELEVANCE_FLAG == 0}">
						<spring:message code="pa.insurance.title.no"/><!--否--></c:if>
                    </td>
				</tr>			
			</c:forEach>
			
		</tbody>
	</table>
	<c:set value="/pa/bonus/viewBonusTypeParam" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>