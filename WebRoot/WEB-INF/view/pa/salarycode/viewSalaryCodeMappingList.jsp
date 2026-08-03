<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/pa/salarycode/viewSalaryCodeMappingList" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				    <td>法人
					</td>
					<td>
				<c:if test="${authority eq '1'}">
				<select id="seach_CPNY" name="seach_CPNY">
					<c:forEach items="${companyList}" var="item" varStatus="i">
						<option value="${item.CPNY_ID }" <c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>${item.CPNY_ID }</option>
					</c:forEach>
				</select>
				</c:if>
				<c:if test="${authority eq '0'}">
					<input type="text" id="seach_CPNY" name="seach_CPNY" value="${CPNY}" readonly="readonly"/>
						</c:if>
	   			 </td>
					<td>
						<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->
	   			 </td>
					<td>
						<select id="seach_TYPE" name="seach_TYPE" value="${TYPE }"> 
						<option value="">
						</option>
						<option value="基础项目" <c:if test="${TYPE eq '基础项目'}">selected</c:if>>
							<spring:message code="pa.wagebase.title.basicItemInfo"/>
						</option>
						<option value="输入项目" <c:if test="${TYPE eq '输入项目'}">selected</c:if>>
							<spring:message code="pa.salary.title.inputItem"/>
						</option>
						<option value="计算项目" <c:if test="${TYPE eq '计算项目'}">selected</c:if>>
							<spring:message code="pa.salary.title.caculateItem"/>
						</option>
						<option value="保险输入项目" <c:if test="${TYPE eq '保险输入项目'}">selected</c:if>>
							保险输入项目
						</option>
						<option value="保险计算项目" <c:if test="${TYPE eq '保险计算项目'}">selected</c:if>>
							保险计算项目
						</option>
						</select>
					</td>
					<td>
						<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
	   			 </td>
					<td>
						<input type="text" name="seach_KEY" value="${KEY }" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search"/><!--检索-->
								</button>
								
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
</div>	

<div class="pageContent">
 	<c:set value="/pa/salarycode/updateSalaryCodeMappingView?ITEM_NO={ITEM_NO}" 
 		var="edit_Url" /> 
 	<c:set value="600" var="edit_width" /> 
 	<c:set value="350" var="edit_height" /> 
 	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%> 

	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
			    <th width="10%"> 
					法人
				</th>
			    <th width="10%"> 
					<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->
				</th>
				<th width="10%">
					<spring:message code="pa.insurance.title.projectID"/><!--项目ID-->
				</th>
				<th width="10%">
					<spring:message code="pa.insurance.title.projectID"/><!--项目ID-->CHRS1.0
				</th>
				<th width="20%">
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
				</th>
				<th width="10%">
					<spring:message code="pa.insurance.title.finance"/><!-- 是否传递财务 -->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${salaryCodeList}" var="item" varStatus="i">
				<tr target="ITEM_NO" rel="${item.ITEM_NO}&PROJECT_TYPE=
				${item.PROJECT_TYPE eq '基础项目' ? 1 : item.PROJECT_TYPE eq '输入项目' ? 2 : item.PROJECT_TYPE eq '计算项目' ? 3 : item.PROJECT_TYPE eq '保险输入项目' ? 4 : 5}&CPNY_ID=${item.CPNY_ID}">
				    <td >
						${item.CPNY_ID}
					</td>
				    <td >
						${item.PROJECT_TYPE}
					</td>
					<td>
						${item.ITEM_ID}
					</td>
					<td>
						${item.MAP_CODE}
					</td>
					<td>
						${item.ITEM_NAME}
					</td>
					<td >
						${item.TO_FINANCE_FLAG eq 'Y'? '是' :item.TO_FINANCE_FLAG eq 'N'? '否':'未输入'}
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	<c:set value="/pa/salarycode/viewSalaryCodeMappingList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>