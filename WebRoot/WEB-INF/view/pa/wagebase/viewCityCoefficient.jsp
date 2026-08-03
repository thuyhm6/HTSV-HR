<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
		<form onsubmit="return navTabSearch(this);" action="/pa/wagebase/viewCityCoefficient" method="post" rel="pagerForm">
		
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<spring:message code="pa.wagebase.title.workArea"/><!-- 工作地 -->：
						<ait:SelectSyCodeByCpnyID name="seach_WORK_AREA" parentNo="4604" cnpyID="${defaultCpny}" limit="all" selected="${WORK_AREA}"/>
					</td>
					<td>
						<spring:message code="pa.wagebase.title.cityCoefficient"/><!--同城系数-->：
						<select name="seach_CITY_COEFFICIENT">
							<option value=""><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:--></option>
							<option value="0">0</option>
							<option value="0.5">0.5</option>
							<option value="1">1</option>
						 </select>
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

<div class="pageContent">
	<c:set value="/pa/wagebase/addCityCoefficientView" var="add_Url"/>
	<c:set value="/pa/wagebase/updateCityCoefficientView?WORK_AREA={sid}" var="edit_Url"/>	
	<c:set value="/pa/wagebase/deleteCityCoefficientInfo?WORK_AREA={sid}" var="delete_Url"/>	
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th width="50"><spring:message code="pa.wagebase.title.workArea"/><!-- 工作地 --></th>
				<th width="100"><spring:message code="pa.wagebase.title.socialArea"/><!--社保地--></th>
				<th width="120"><spring:message code="pa.wagebase.title.cityCoefficient"/><!--同城系数--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paCityCoefficientList}" var="item">			
				<tr target="sid" rel="${item.S_WORK_AREA}&E_WORK_AREA=${item.E_WORK_AREA}">
					<td>${item.S_WORK_AREA_NAME}</td>
					<td>${item.E_WORK_AREA_NAME}</td>
					<td>${item.COEFFICIENT}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
	<c:set value="/pa/wagebase/viewCityCoefficient" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>	
</div>