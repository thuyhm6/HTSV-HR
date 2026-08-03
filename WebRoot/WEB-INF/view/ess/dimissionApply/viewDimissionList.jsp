<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
<form id="viewDimissionList" method="post" action="/ess/dimissionApply/viewDimissionList" class="pageForm required-validate" 
     	onsubmit="return navTabSearch(this);">
    <div class="searchBar">
			<table class="searchContent">
		        <tr>
	                <td><!-- 工号/姓名 -->
						<spring:message code="public.title.empIdAndName"/>
					</td>
					<td>
						<input type="text"  name="seach_KEY" value="${KEY}"/>
					</td>
					
			   	<td>人员类型组 </td>
						<td>
						<input type="hidden" id="ess2001_limit" name="limit" value="hr">
						<input type="hidden" id="ess2001_seach_CPNY" name="seach_CPNY" value="${defaultCpny}">
			<ait:SelectEmpTypeCode  id="ess2001_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="hr" type="group"
			onChangeName="ajaxEmpTypeForGroupToList(-1,ess2001_seach_JobTypeGroupNo,ess2001_seach_EmpTypeCodeNo,ess2001_seach_CPNY,ess2001_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 	<ait:SelectEmpTypeCode id="ess2001_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="hr"/>
						</td>
					<td>在职状态</td>
					<td>
		 				<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
				            	<div class="buttonContent">
				                	<button type="submit">
				                    	<spring:message code="public.title.search"/><!-- 检索 -->
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
	<c:set value="navTab" var="add_tab"/>
	<c:set value="addDimissionInfoView" var="add_rel"/>
	<c:set value="离职申请" var="add_name" />
	<c:set value="/ess/dimissionApply/addDimissionView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/ess/dimissionApply/deleteDimissionInfo?APPLY_NO={APPLY_NO}" var="delete_Url"/>     
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
<table class="table" width="100%" layoutH="160" nowrapTD="false">
	<thead>
		<tr>
				<th width="5%"><!-- 工号 --><spring:message code="display.emp.ben.serviceno"/></th>
				<th width="5%"><!-- 姓名 --><spring:message code="inct.salesman.Name"/></th>
				<th width="10%"><!-- 人员类型 --><spring:message code="is.company.title.PERSON_TYPE"/></th>
				<th width="10%"><spring:message code="ess.infoApply.title.essApplyTime"/><!-- 申请日期 --></th>
				<th width="10%"><spring:message code="display.pa.ecc.expectresigndate"/><!-- 预离职日期 --></th>
				<th width="21%"><spring:message code="ess.trans.title.resignReason"/><!-- 离职原因--></th>
				<th width="5%">查看详情</th>
				<th width="10%"><spring:message code="ar.attendanceView.viewNoSwipingCard.status"/><!-- 状态--></th>
			</tr>
	</thead>
	<tbody>
		<c:forEach items="${dimissionList}" var="item" varStatus="i">
			<tr target="APPLY_NO" rel="${item.APPLY_NO}">
					<input type="hidden" name="PERSON_ID_${i.index}" value="${item.PERSON_ID}" /></td>
					<input type="hidden" name="APPLY_NO_${i.index}" value="${item.APPLY_NO}" /></td>
					<td style="text-align:center">${item.EMPID}</td>
					<td style="text-align:center">${item.LOCAL_NAME}</td>
					<td style="text-align:center">${item.EMP_TYPE_NAME}</td>
					<td style="text-align:center">${item.APPLY_TIME}</td>
					<td style="text-align:center">${item.APPLY_LEAVE_TIME}</td>
					<td>${item.APPLY_REASON}</td>
					<td style="text-align:center">
					  <a target="dialog" mask="true" width="1150" height="550" href="/ess/editionAffirm/viewEditionCheckInfo?pageNum=1&APPLY_NO=${item.APPLY_NO }&checkflag=1">查看</a>
					</td>
					<td style="text-align:center">
					<c:if test="${item.AFFIRM_FLAG eq '-1'}">
					    提交
					</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '0'}">
					    审批中
					</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '1'}">
					    已通过
					</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '2'}">
					    已否决
					</c:if>
					</td>
				</tr>
		</c:forEach>
	</tbody>
</table>

<c:set value="/ess/dimissionApply/viewDimissionList" var="pageUrl" /> 
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>