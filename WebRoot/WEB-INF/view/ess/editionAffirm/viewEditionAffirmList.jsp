<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/editionAffirm/viewEditionAffirmList" rel="pagerForm"
		method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="public.title.empIdAndName" />
						<!-- 工号/姓名 --></td>
					<td><input type="text" id="seach_KEY" name="seach_KEY"
						value="${KEY}" /></td>

					<td><spring:message code="public.title.deptName" />
						<!-- 部门 --></td>
					<td><ait:deptList name="seach_DEPTNO" selected="${DEPTNO}"
							id="AffirmList_seachDept" /> <ait:deptTreeIcon
							name="seach_DEPTNO" limit="hr"
							id="ffirmList_seachDept" selected="${DEPTNO}" />
					</td>
					<td>审批状态</td>
					<td><select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							<option value="-5">全部</option>
							<option value="0"
								<c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>进行中</option>
							<option value="1"
								<c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>已通过</option>
							<option value="2"
								<c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>已否决</option>
						 <option value="10" <c:if test="${AFFIRM_FLAG eq '10'}">selected</c:if>>本人审批</option>
						 <option value="11" <c:if test="${AFFIRM_FLAG eq '11'}">selected</c:if>>未到审批</option>
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
				<th width="10%">决裁情况
				</th>
				<th width="10%"><spring:message
						code="ar.attendanceView.viewNoSwipingCard.status" />
					<!-- 状态-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${editionAffirmList}" var="item" varStatus="i">
				<tr>
					<td style="text-align:left">${item.EMPID }
					<input type="hidden" name="APPLY_NO" value="${item.APPLY_NO}"/>
					</td>
					<td style="text-align:left">${item.LOCAL_NAME }</td>
					<td style="text-align:left">${item.EMP_TYPE_NAME }</td>
					<td style="text-align:left">${item.APPLY_TIME }</td>
					<td>${item.APPLY_LEAVE_TIME }</td>
					<td>${item.APPLY_REASON}</td>
					<td>
					<c:if test="${item.AFFIRM_FLAG eq '0'}">进行中</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '1'}">已通过</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '2'}">已否决</c:if>
				</td>
					
 					<td>
					<c:if test="${item.AFFIRM_FLAG_PERSON eq '1'}">
							<a
								href="/ess/editionAffirm/viewEditionAffirmsList?APPLY_NO=${item.APPLY_NO }&m=1"
								target="navTab" rel="ess2017_affirm">审批</a>
								</c:if>
					    <c:if test="${item.AFFIRM_FLAG_PERSON ne '1'}">
						   <a target="navTab" href="/ess/editionAffirm/viewEditionCheckInfo?pageNum=1&APPLY_NO=${item.APPLY_NO }&m=1">审批查看</a>
					     </c:if>
						</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ess/editionAffirm/viewEditionAffirmList " var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>