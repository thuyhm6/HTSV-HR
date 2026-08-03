<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/viewApply/viewPersonalInfoList" rel="pagerForm" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
				    <spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 -->：
				</td>
				<td>
					<input type="text" name="seach_KEY" value="${KEY}" />
				</td>
				<td>
				     <spring:message code="public.title.deptName"/><!-- 部门 -->:
				</td>				
				<td>
					 <ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPT_NO}"/>
				</td>
				<td>
				     <spring:message code="public.title.startDate"/><!-- 开始日期 -->:
				</td>
				<td>
					<input id="dob" type="text" name="seach_START_DATE" class="date required" readonly="true" value="${START_DATE}"/>
				           <a class="inputDateButton"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td>
				     <spring:message code="public.title.endDate"/><!-- 结束日期 -->:
				</td>				
				<td>
					<input id="dob" type="text" name="seach_END_DATE" class="date required" readonly="true" value="${END_DATE}"/>
				           <a class="inputDateButton"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>				
			</tr>
		</table>
	</div>
	<div class="formBar">
			<tr>
				<ul>
					<li>
						<div class="subBar">
	                            <div class="buttonActive"><div class="buttonContent">
	                                 <button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button></div></div>
					    </div>	
					</li>
			</tr>
	</div>	
	</form>
</div>

<div class="pageContent" >

	<table class="table" width="100%" layoutH="138"  >
		<thead>
			<tr>
				<th width="5%"><spring:message code="ess.viewApply.title.number"/><!--序号--></th>
				<th width="10%"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="10%"><spring:message code="ess.viewApply.title.applyName"/><!--申请者--></th>
				<th width="10%"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="15%"><spring:message code="public.title.positionName"/><!--职岗位--></th>
				<th width="10%"><spring:message code="public.title.postName"/><!--职级名称（职务）--></th>
				<th width="10%"><spring:message code="ess.viewApply.title.applyDate"/><!--申请日期--></th>
				<th width="10%"><spring:message code="ess.viewApply.title.applyContent"/><!--申请内容--></th>
				<th width="10%"><spring:message code="ess.viewApply.title.humanAffirm"/><!--人事确认--></th>
				<th width="10%"><spring:message code="button.delete"/><!--删除--></th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${personApplyList}" var="personApply" varStatus="i">
			
				<tr target="sid" rel="${personApply.PERSON_ID}">
				    <td>${i.index+1}</td>	
					<td>${personApply.EMPID}</td>
					<td>${personApply.LOCAL_NAME}</td>
					<td>${personApply.DEPT_NAME}</td>
					<td>${personApply.POST_NAME}</td>
					<td>${personApply.POSITION_NAME}</td>
					<td>${personApply.CREATE_DATE}</td>
					<td class='td_center'>
                        <a href="/ess/viewApply/viewPersonInfo?PERSON_ID=${personApply.PERSON_ID}" target="navTab">
                           <spring:message code="ess.viewApply.title.viewContent"/><!--查看内容-->
						</a>
                    </td>
					<td>
                         <c:if test="${personApply.ACTIVITY eq 0}">
                            <spring:message code="ess.viewApply.title.notConfirmed"/><!--人事未确认--> 
                         </c:if>
                         <c:if test="${personApply.ACTIVITY eq 1}">
                            <spring:message code="ess.viewApply.title.confirmedPass"/><!--人事确认已通过--> 
                         </c:if>
                         <c:if test="${personApply.ACTIVITY eq 2}">
                            <spring:message code="ess.viewApply.title.confirmedReject"/><!--人事确认已否决-->
                         </c:if>
                    </td>
					<td>
					    <c:if test="${personApply.ACTIVITY eq 0}">
	                        <a href="/ess/viewApply/delPersonInfoApply?PERSON_ID=${personApply.PERSON_ID}" target="ajaxTodo" title="确定要删除吗?" >
	                           <span><img src="/resources/images/button/Delete_little.gif"></span>
							</a>
						</c:if>
                    </td>				
				</tr>			
			</c:forEach>
		</tbody>
    <c:set value="/ess/viewApply/viewPersonalInfoList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</table>
	
</div>