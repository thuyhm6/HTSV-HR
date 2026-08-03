<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/wageApplication/viewCheckApplicationList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>社号/姓名：</td>
				<td><input type="text" name="seach_EMPID" value="${EMPID}"/></td>
				<td>开始时间：</td>
				<td> <input type="text" name="seach_STARTTIME" class="date required" readonly="true" value="${STARTTIME}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td>结束时间：</td>
				<td><input type="text" name="seach_ENDTIME" class="date required" readonly="true" value="${ENDTIME}"/>
					<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
				</td>
				<td>审批状态：</td>
				<td>
					 <select name="seach_STATE" value="${STATE}">
						 <option value="">全部</option>
						 <option value="0" <c:if test="${STATE eq '0'}">selected</c:if>>未审批</option>
						 <option value="3" <c:if test="${STATE eq '3'}">selected</c:if>>审批中</option>
						 <option value="1" <c:if test="${STATE eq '1'}">selected</c:if>>通过</option>
						 <option value="2" <c:if test="${STATE eq '2'}">selected</c:if>>否决</option>
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
								<!--检索--><spring:message code="public.title.search"/>
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
	<table class="table" width="100%" layoutH="172">
	<thead>
		<tr>
			<th width="15%" align="center"><spring:message code="ess.viewApply.title.applyName"/><!-- 申请者 --></th>
			<th width="30%" align="center"><spring:message code="hr.viewCondSql.title.NEIRONG"/><!--申请内容--></th>
			<th width="10%" align="center">附件查看</th>
			<th width="10%" align="center"><spring:message code="ess.viewApply.title.applyDate"/><!--申请日期--></th>
			<%--<th width="10%" align="center"><spring:message code="ar.viewArAdjustRest.title.chakanxiangxi"/><!--详细查看--></th>--%>
			<th width="10%" align="center"><spring:message code="ess.trans.title.affirmStatus"/><!--审批状态--></th>
			<th width="15%" align="center">Type</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${wageList}" var="wage" varStatus="i">			
			<tr target="waid" rel="${wage.id}" align="center">
				<td width="15%">${wage.EMPNAME}[${wage.CREATEDID}]</td>
				<td width="30%">${wage.TITLE}</td>
				<td><a href="/ess/wageApplication/showWageAppliFileList?APPLY_NO=${wage.ID}&PERSON_ID=${wage.CREATED_BY}" target="dialog" mask="true" width="730" height="500">附件查看</a></td>
				<td width="10%">${wage.CREATEDATE}</td>
				<%--<td width="10%"><a href="/ess/wageApplication/showApplicationList?APPLY_NO=${wage.ID}&PERSON_ID=${wage.CREATED_BY}" target="dialog" mask="true" width="730" height="500">点击查看</a></td>--%>
				<td width="10%">
					<c:if test="${wage.FLAG eq '0'}">未审批</c:if><c:if test="${wage.FLAG eq '3'}">审批中</c:if>
					<c:if test="${wage.FLAG eq '1'}">通过</c:if><c:if test="${wage.FLAG eq '2'}">否决</c:if>
				</td>
				<td width="15%">
					<c:if test="${wage.CHECKOR_ID eq LoginUser.personId}">
				    	<a class="add" href="/ess/wageApplication/checkApplicationInfo?APPLY_NO=${wage.ID}&ESS_AFFIRM_NO=${wage.ESS_AFFIRM_NO}&IS_CHECK=${wage.CHECK_FLAG}"  
				    		title="CHECK" target="dialog" mask="true" width="1200" height="450">
				    		<font color="red"><c:if test="${wage.CHECK_FLAG eq '0'}">check</c:if><c:if test="${wage.CHECK_FLAG eq '1'}">已check</c:if></font></a>
					</c:if>
				</td>
			</tr>			
		</c:forEach>
		</tbody>			
	</table>		
	<c:set value="/ess/wageApplication/viewCheckApplicationList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>