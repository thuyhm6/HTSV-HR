<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
<form action="/paEcc/benchmark/searchEccBenchEmp" method="post" name="searchForm" onsubmit="return dwzSearch(this,'dialog');" rel="pagerForm">
<input type="hidden" name="pageNum" value="${pageNum}" />
<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
				     <spring:message code="public.title.deptName"/><!--部门-->：
				</td>
				<td>
				     <ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}"/>
				</td>
				<td>
					<spring:message code="public.title.empId"/><!--工号-->:
				</td>
				<td>
					<input type="text" name="seach_EMPID" value="${EMPID }" />
				</td>
				<td>
					<spring:message code="public.title.name"/><!--姓名-->：
				</td>
				<td>
					<input type="text" name="seach_LOCAL_NAME" value="${LOCAL_NAME }" />
				</td>
				<!-- 
				<td>预离职日期</td>
				<td>
					<input type="text" id="seach_STIME" name="seach_STIME" value="${STIME}" class="date"
										yearstart="-20" yearend="20" readonly="true" />
				</td>
				 -->
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<!-- <div class="buttonActive">
						<div class="buttonContent"><button type="submit">???</button></div>
					</div> -->
					<div class="buttonActive">
						<div class="buttonContent"><button type="submit"><spring:message code="public.title.search"/><!--检索--></button></div>
					</div>
				</li>
			</ul>
		</div>
	</div>
</form>
</div>
<div class="pageContent">
	<table width="100%" class="table" targetType="dialog" layoutH="118">
	<thead>
	<tr>
		<th align="center" orderField="EMPID" width="100">
			<spring:message code="public.title.empId"/><!--工号--></th>
		<th align="center" orderField="nlssort(LOCAL_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" >
			<spring:message code="public.title.name"/><!--姓名--></th>
		<th align="center" orderField="nlssort(DEPT_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" >
			<spring:message code="public.title.deptName"/><!--部门--></th>
		<!-- 
		<th align="center" orderField="nlssort(POST_GRADE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" >
			<spring:message code="pa.insurance.title.postGrade"/><!--职级</th>
		<th align="center" orderField="nlssort(IN_THE_DIFFERENCE,'NLS_SORT=SCHINESE_PINYIN_M')" >
			<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/><!-- 试用与否 </th>
		 -->
		 <th align="center" orderField="" >
			<!-- 是否离职 --><spring:message code="zxc.hr.contract.EMP_OFFICE"></spring:message> </th>
		<th align="center" orderField="HR.JOIN_COMPANY_DATE" >
			<spring:message code="pa.insurance.title.entryCpmpanyDate"/><!--入司日期--></th>
		<th align="center" orderField="HR.DATE_LEFT" >
			<spring:message code="pa.insurance.title.resignDate"/><!--离职日期--></th>
		<th align="center" width="80"><!-- 确认 -->
		<spring:message code="hr.viewPersonalInfo.title.AFFIRM"/></th>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${empinfos }" var="emp">
			<tr>
				<td>${emp.EMPID}</td>
				<td>
					${emp.LOCAL_NAME}
				</td>
				<td>
					${emp.DEPTNAME}
				</td>
				<!-- 
				<td>&nbsp;
				</td>
				 -->
				<td>
					<c:if test="${emp.ACTIVITY eq 1}">
						<spring:message code="hr.viewRelation.title.NO"></spring:message>
					</c:if>
					<c:if test="${emp.ACTIVITY eq 0}">
						<spring:message code="hr.viewRelation.title.YES"></spring:message>
					</c:if>
				</td>
				<td>${emp.DATE_START }</td>
				<td>${emp.DATE_LEFT }</td>
				<td width="80">
					<a class="btnSelect" 
						href="javascript:
							$.bringBack({
								empId:'${emp.EMPID}',
								empName:'${emp.LOCAL_NAME}'
							})" title="<spring:message code='ar.alert.message.viewattendencekeeper.chazhaodaihui'/>">
							<!-- 选择 --><spring:message code="public.title.choose"/></a></td>
				
			</tr>
		</c:forEach>
	</tbody>
	</table>
<form id="pagerForm" method="post" action="/paEcc/benchmark/searchEccBenchEmp">
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
</form>
<!-- 
<c:set value="/paEcc/searchEccBenchEmp" var="pageUrl"/>
//@include file="/WEB-INF/view/inc/initPagination.jsp"%>  
 -->
</div>