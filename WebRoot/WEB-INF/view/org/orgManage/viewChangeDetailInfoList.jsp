<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(
		function() {
			$("#viewChangeInfoList_currentIndex").val('${currentIndex}');
			$("#viewChangeInfoList_list_${currentIndex}",navTab.getCurrentPanel()).dataTable( {
				"bPaginate" : false, //关闭分页
				"bAutoWidth" : false,//表格宽度不自动变化
				"bProcessing" : true,
				"bLengthChange" : false, //关闭按多少条记录显示下拉框
				"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
				"bSort" : true, //关闭排序功能
				"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
				"bScrollInfinite" : true,
				"scrollY" : $(document.body).height() - 200,
				"scrollX" : true,
				"orderClasses" : false
			});
		});
</script>
<c:if test="${currentIndex eq '1'}">
	<div class="pageContent">
		<div class="user_table"
			style="font: bold 12px/ 20px arial, sans-serif;">
			Total:${fn:length(empChangeInfoList)}
		</div>
		<table id="viewChangeInfoList_list_${currentIndex }" class="list"
			width="100%">
			<thead>
				<tr>
					<th width="30px">
						No.
					</th>
					<th width="180px">
						<spring:message code="org.title.dept" /><!-- 部门 -->
					</th>
					<th width="100px">
						<spring:message code="org.title.LOCAL_NAME" /><!-- 姓名 -->
					</th>
					<th width="100px">
						<spring:message code="org.title.EMP_OFFICE_NAME" /><!-- 状态 -->
					</th>
					<th width="80px">
						<spring:message code="org.title.EMPID" /><!-- 工号 -->
					</th>
					<th width="100px">
						<spring:message code="org.title.POST_GRADE_NAME" /><!-- 职务 -->
					</th>
					<th width="120px">
						<spring:message code="hrm.empinfo.HEAD_DEPARTMENT" /><!-- 部门长 -->
					</th>
					<th width="100px">
						<spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" /><!-- 主要业务 -->
					</th>
					<th width="80px">
						<spring:message code="org.title.COST_CENTER_ID.Z" /><!-- 成本中心ID -->
					</th>
					<th width="80px">
						<spring:message code="org.title.COST_CENTER" /><!-- 成本中心 -->
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${empChangeInfoList}" var="item" varStatus="i">
					<tr>
						<td class='td_center'>
							${i.count}
						</td>
						<td>
							${item.DEPT_NAME}
						</td>
						<td>
							${item.LOCAL_NAME}
						</td>
						<td>
							${item.EMP_OFFICE_NAME }
						</td>
						<td>
							${item.EMPID}
						</td>
						<td>
							${item.POST_GRADE_NAME}
						</td>
						<td>
							${item.HEAD_DEPARTMENT}
						</td>
						<td>
							${item.BUSINESS_NAME}
						</td>
						<td>
							${item.COST_CENTER}
						</td>
						<td>
							${item.COST_CENTER_NAME}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</c:if>
<c:if test="${currentIndex eq '0'}">
	<div class="pageContent">
		<div class="user_table"
			style="font: bold 12px/ 20px arial, sans-serif;">
			Total:${fn:length(orgChangeInfoList)}
		</div>
		<table id="viewChangeInfoList_list_${currentIndex }" class="list"
			width="100%">
			<thead>
				<tr>
					<th width="30px">
						No.
					</th>
					<th width="150px">
						<spring:message code="org.title.DEPT_ID" /><!-- 部门ID -->
					</th>
					<th width="180px">
						<spring:message code="org.title.dept" /><!-- 部门 -->
					</th>
					<th width="120px">
						<spring:message code="org.title.DEPT_TYPE" /><!-- 组织类型 -->
					</th>
					<th width="80px">
						<spring:message code="org.title.COST_CENTER_ID.Z" /><!-- 成本中心ID -->
					</th>
					<th width="80px">
						<spring:message code="org.title.COST_CENTER" /><!-- 成本中心 -->
					</th>
					<th width="180px">
						<spring:message code="org.title.MINISTER" /><!-- 部门长 -->
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orgChangeInfoList}" var="item" varStatus="i">
					<tr>
						<td class='td_center'>
							${i.count}
						</td>
						<td>
							${item.DEPTNO}
						</td>
						<td>
							${item.DEPTNAME}
						</td>
						<td>
							${item.DEPT_TYPE}
						</td>
						<td>
							${item.COST_CENTER}
						</td>
						<td>
							${item.COST_CENTER_NAME}
						</td>
						<td>
							${item.HEAD_DEPARTMENT}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</c:if>