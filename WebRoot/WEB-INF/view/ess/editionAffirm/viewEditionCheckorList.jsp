<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>


</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/editionAffirm/viewEditionCheckorList" rel="pagerForm"
		method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="public.title.empIdAndName" />
						<!-- 工号/姓名 --></td>
					<td><input type="text" id="seach_KEY" name="seach_KEY"
						value="${KEY}" /></td>

					<td><spring:message code="public.title.deptName" />
						<!-- 部门 -->:</td>
					<td>
						<ait:deptList name="seach_DEPTNO" selected="${DEPTNO}"
							id="viewffirmList_seachDept" /> <ait:deptTreeIcon
							name="seach_DEPTNO" limit="ar"
							id="viewAffirmList_seachDept" selected="${DEPTNO}" />
					</td>
					<td>审批状态</td>
					<td><select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							<option value="">全部</option>
							<option value="0"
								<c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>审批中</option>
							<option value="1"
								<c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>已通过</option>
							<option value="2"
								<c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>已否决</option>
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
									<spring:message code="public.title.search" />
									<!-- 检索 -->
								</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="200" nowrapTD="false">
		<thead>
			<tr>
				<th width="5%">
					<!-- 工号 -->
					<spring:message code="display.emp.ben.serviceno" />
				</th>
				<th width="5%">
					<!-- 姓名 -->
					<spring:message code="inct.salesman.Name" />
				</th>
				<th width="10%">
					<!-- 人员类型 -->
					<spring:message code="is.company.title.PERSON_TYPE" />
				</th>
				<th width="10%"><spring:message
						code="ess.infoApply.title.essApplyTime" />
					<!-- 申请日期 -->
				</th>
				<th width="10%"><spring:message
						code="display.pa.ecc.expectresigndate" />
					<!-- 预离职日期 -->
				</th>
				<th width="21%"><spring:message
						code="ess.trans.title.resignReason" />
					<!-- 离职原因-->
				</th>
				<th width="10%">
				Type
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${editionCheckList}" var="item" varStatus="i">
				<tr>
					<td style="text-align:left">${item.EMPID }</td>
					<td style="text-align:left">${item.LOCAL_NAME }</td>
					<td style="text-align:left">${item.EMP_TYPE_NAME }</td>
					<td style="text-align:left">${item.APPLY_TIME }</td>
					<td>${item.APPLY_LEAVE_TIME }</td>
					<td>${item.APPLY_REASON}</td>
					<td style="text-align: center">
						<c:if test="${item.AFFIRM_FLAG_PERSON eq '1'}">
							<a class="add" href="/ess/editionAffirm/checkDimissionApplyInfo?seach_APPLY_NO=${item.APPLY_NO}&seach_ESS_AFFIRM_NO=${item.ESS_AFFIRM_NO}&seach_IS_CHECK=1"  
					    	rel="ess2018_affirm_info" title="CHECK" target="navTab" mask="true"><font color="red">Check</font></a>
						</c:if>
						<c:if test="${item.AFFIRM_FLAG_PERSON ne '1'}">
						    <a rel="leaveAffirmRemark" href="/ess/editionAffirm/checkDimissionApplyInfo?seach_APPLY_NO=${item.APPLY_NO}" title="check详情"
				          		target="navTab" mask="true" rel="ess2018_affirm_info" id="leaveAffirmRemarkHref"><font color="red">已check</font></a>
						</c:if>
					</td>		
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ess/editionAffirm/viewEditionCheckorList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>