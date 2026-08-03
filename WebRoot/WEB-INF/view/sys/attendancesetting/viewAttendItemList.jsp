<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/sys/attendancesetting/viewAttendItemList" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
<!-- 					<td><spring:message code="pa.insurance.title.projectName" /> -->
<!-- 						项目名称： <input type="text" name="seach_ITEM_NO" value="${ITEM_NO}" /> -->
						
<!-- 					</td> -->
					<td><spring:message code="pa.insurance.title.projectName" />
						<!--项目名称-->： <input type="text" name="seach_KEY" value="${KEY }" />
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
									<!--检索-->
								</button>

							</div>
						</div> 
		   		    </li>
				</ul>
			</div>
		</div>
</div>

		<div class="pageContent">
			<c:set value="/sys/attendancesetting/updateAttendItemView?ITEM_NO={ITEM_NO}" var="edit_Url" />
			<c:set value="600" var="edit_width" />
			<c:set value="500" var="edit_height" />
			<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>

			<table class="table" width="100%" layoutH="206">
				<thead>
					<tr>
						<th width="10%"><spring:message
								code="pa.insurance.title.projectType" />
							<!--项目类型--></th>
						<th width="15%"><spring:message
								code="pa.insurance.title.projectID" />
							<!--项目ID--></th>
							<th width="10%"><spring:message
								code="pa.insurance.title.projectID" />
							<!--项目ID-->CHRS1.0</th>
						<th width="10%"><spring:message
								code="pa.insurance.title.projectName" />
							<!--项目名称--></th>
						<th width="40%"><spring:message
								code="pa.insurance.title.description" />
							<!--描述--></th>
						<th width="80">
							<!-- 项目组 -->
							<spring:message code="ar.viewItem.title.xiangmuzu" />
						</th>
						<th width="5%">
							<!-- 活跃状态 -->
							<spring:message code="ar.viewcycle.title.huoyuezhuangtai" />
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${attendItemList}" var="item" varStatus="i">
						<tr target="ITEM_NO"
							rel="${item.ITEM_NO}&PROJECT_TYPE=${item.PROJECT_TYPE eq '明细项目' ? 1 : 2}">
							<td>${item.PROJECT_TYPE}</td>
							<td>${item.ITEM_ID}</td>
							<td>${item.ITEM_ID_MAPPING}</td>
							<td>${item.ITEM_NAME}</td>
							<td>${item.DESCRIPTION}</td>
							<td>${item.ITEM_GROUP_NAME}</td>
							<td><c:if test="${item.ACTIVITY eq 1}">
									<img src="/resources/images/a_1.gif" />
								</c:if> <c:if test="${item.ACTIVITY eq 0}">
									<img src="/resources/images/a_0.gif" />
								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</form>
	<c:set value="/sys/attendancesetting/viewAttendItemList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
</div>