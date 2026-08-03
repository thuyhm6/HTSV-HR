<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
	<div class="pageHeader" >
		<form id="searchPaProgressForm" name="searchPaProgressForm" onsubmit="return navTabSearch(this);" 
			action="/pa/salary/viewPaProgress" method="post" rel="pagerForm">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->：
							<ait:date yearName="seach_paSalaryLockYear" monthName="seach_paSalaryLockMonth" yearSelected="${paSalaryLockYear}" monthSelected="${paSalaryLockMonth}" />
						</td>
						<td><!-- 区间 -->
							<spring:message code="ar.viewcycleparameter.title.qujian"/>:
						</td>
						<td>
							<select class="combox" name="seach_STAT_NO">
							<option value="">select</option>
							<c:forEach items="${statnoList}" var="list">
								<option value="${list.STAT_NO}" <c:if test="${list.STAT_NO eq STAT_NO}">selected</c:if>>${list.STAT_NAME}</option>
							</c:forEach>
						</select>
						</td>
						<c:if test="${CPNY_ID eq 'TSTO'}">
							<td>
								大区:
							</td>
							<td>
								<select class="combox" name="seach_DEPT_NO">
									<option value="">select</option>
								     <c:forEach items="${deptList}" var="vlist" varStatus="i">
									      <option value="${vlist.DEPTNO}" <c:if test="${vlist.DEPTNO eq DEPT_NO}">selected</c:if>>${vlist.DEPTNAME}</option>
								    </c:forEach>
							   </select>
							
						   </td>
				   </c:if>
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
		<table class="table" width="100%" layoutH="171">
			<thead>
				<tr>
					<th width="8%">
						<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
					</th>
					 
					<th width="16%"><!-- 区间 -->
					 <spring:message code="ar.viewcycleparameter.title.qujian"/></th>
					 <c:if test="${CPNY_ID eq 'TSTO'}">
					 <th width="18%">
					   大区
					 </th>
					 </c:if>
					 <th width="8%"> 
					 考勤确认
					</th>
					<th width="8%"> 
					 保险
					</th>
					<th width="8%"> 
					提成
					</th>
					<th width="8%"> 
					  工资关闭
					</th>
					<th width="8%"> 
					  工资确认
					</th>
					<th width="8%"><!-- 工资开放 -->
						<spring:message code="pa.salary.title.salaryOpen"/>
					</th>
					<th width="8%"> 
					 财务传送
					</th>
				</tr>
			</thead>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.PA_MONTH_STR}">
					<td style="text-align:center" > 
						${item.PA_MONTH_STR}
					</td>
					 
					<td style="text-align:center" >${item.STAT_NAME}</td>
					
					 <c:if test="${CPNY_ID eq 'TSTO'}">
					 <td  style="text-align:center" >
					   ${item.AR_DEPT_NAME }  
					 </td>
					 </c:if>
					
					<td style="text-align:center" >
						<img src="/resources/images/${item.ATT_MO_LOCK_FLAG }.gif" style="cursor: hand" />
					</td>
					
					<td style="text-align:center" >
						<img src="/resources/images/${item.IS_LOCK_FLAG}.gif" style="cursor: hand" />
					</td>
					<td style="text-align:center" >
						<img src="/resources/images/${item.TICHENG }.gif" style="cursor: hand" />
					</td>
					<td style="text-align:center" >
						<img src="/resources/images/${item.PA_APPLY_LOCK_FLAG }.gif" style="cursor: hand" />
					</td>
					<td style="text-align:center" >
						<img src="/resources/images/${item.PA_LOCK_FLAG }.gif" style="cursor: hand" />
					</td>
					<td style="text-align:center" >
						<img src="/resources/images/${item.PA_OPEN_FLAG }.gif" style="cursor: hand" />
					</td>
					<td style="text-align:center" >
						<img src="/resources/images/${item.CAIWU }.gif" style="cursor: hand" />
					</td>
				 
				</tr>
			</c:forEach>
		</table>
		<c:set value="/pa/salary/viewPaProgress" var="pageUrl"/>
		<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
	</div>