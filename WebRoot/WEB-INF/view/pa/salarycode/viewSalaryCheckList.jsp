<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
// function newCode(){
// 	$("#newCode_001").attr("href","/pa/salarycode/newSalaryCodeAffirmView");
// 	$("#newCode_001").click();
// }
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/pa/salarycode/viewSalaryCheckList" method="post" rel="pagerForm">
		<div class="searchBar">
		
		
			<table class="searchContent">
				<tr>
               <td>法人
				</td>
			<td>
				<c:if test="${authority eq '1'}">
				
				<select id="seach_CPNY_ID" name="defaultCpny" onchange="reloadPage();">
				    <option value="">全部</option>
					<c:forEach items="${companyList}" var="item" varStatus="i">
						<option value="${item.CPNY_ID }" <c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>${item.CPNY_ID }</option>
					</c:forEach>
				</select>
				</c:if>
				<c:if test="${authority eq '0'}">
					<input type="text" id="seach_CPNY_ID" name="seach_defaultCpny" value="${defaultCpny}" readonly="readonly"/>
						</c:if>
	   			 </td>
					<td>
						<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->：
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
						</select>
					</td>
					<td>
						<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
				</td>
			<td>
						<input type="text" name="seach_KEY" value="${KEY }" />
					</td>
					<td>
						<spring:message code="ar.viewcycle.title.zhuangtai"/><!--状态-->
				</td>
			<td>
						<select id="seach_CPNY_FLAG" name="seach_CPNY_FLAG" value="${CPNY_FLAG}"> 
						<option value="">
						</option>
						<option value="1" <c:if test="${CPNY_FLAG==1}">selected</c:if>>
							<spring:message code="sys.arAffirmPost.title.able"/>
						</option>
						<option value="0" <c:if test="${CPNY_FLAG == 0}">selected</c:if>>
							<spring:message code="sys.arAffirmPost.title.enable"/><!--未启用-->
						</option>
						</select>
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
	
	</form>
</div>

<div class="pageContent">
	<c:set value="/pa/salarycode/addSalaryCodeAffirmView" var="add_Url" /> 
 	<c:set value="600" var="add_width" /> 
 	<c:set value="500" var="add_height" />
    <c:set value="新增代码申请" var="add_name" />
 	<c:set value="/pa/salarycode/addPaBasicParamAffirmview?NO={ITEM_NO}" 
 		var="edit_Url" /> 
 	<c:set value="600" var="edit_width" /> 
 	<c:set value="500" var="edit_height" /> 
 	<c:set value="代码启用申请" var="edit_name" /> 
 	<%@ include file="/WEB-INF/view/inc/includeButton2.jsp"%> 
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
			    <th width="10%"><spring:message
						code="sys.affirm.indexNum" />
					<!--序号--></th>
			    <th width="30%"> 
					<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->
				</th>
				
				<th width="40%">
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
				</th>
				
				<th width="20%">
					<spring:message code="ar.viewcycle.title.zhuangtai" /><!--状态-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${salaryCheckList}" var="item" varStatus="i">
				<tr target="ITEM_NO" rel="${item.ITEM_NO}&PROJECT_TYPE=${item.PROJECT_TYPE eq '基础项目' ? 1 : item.PROJECT_TYPE eq '输入项目' ? 2 : 3}">
				    <td>${i.index + 1}&nbsp;</td>
				    <td >
						${item.PROJECT_TYPE}
					</td>
					<td>
						${item.ITEM_NAME}
					</td>
					<td>
						    <c:if test="${ item.CPNY_FLAG eq 1 || item.CPNY_FLAG ==1}">
						    <spring:message code="sys.arAffirmPost.title.able"/><!--启用--></c:if>
						   <c:if test="${ item.CPNY_FLAG eq 0 || item.CPNY_FLAG ==0}">
						    <spring:message code="sys.arAffirmPost.title.enable"/><!--未启用--></c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/salarycode/viewSalaryCheckList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>