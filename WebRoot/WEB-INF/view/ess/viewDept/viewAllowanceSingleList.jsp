<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<table class="table" width="100%" layoutH="100" nowrapTD="false">
	<thead>
		<tr>
			<th>NO</th>
			<th>
				<!--姓名 --> 姓名
			</th>
			<th>
				<!--部门 --> 社号
			</th>
			<th>
				<!--部门 --> 部门
			</th>
			<th>
				<!--部门 --> 职级
			</th>
			<th>
				<!--等级名--> 班组
			</th>
			<th>
				<!--主要业务 --> 工作时间
			</th>
			<th>
				<!--主要业务 --> 日期
			</th>
			<th>
				<!--标准职务 --> 中夜班津贴
			</th>
		</tr>
	</thead>
	<tbody>

		<c:forEach items="${viewAllowanceSingleList}" var="item" varStatus="i">
			<tr target="sid" rel="">
				<td style="text-align: center">${i.count}</td>
				<td style="text-align: center">${item.LOCAL_NAME}</td>
				<td style="text-align: center">${item.EMPID}</td>
				<td style="text-align: center">${item.DEPTNAME}</td>
				<td style="text-align: center">${item.POST_NAME}</td>
				<td style="text-align: center">${item.GROUP_NAME}</td>
				<td style="text-align: center">${item.WORK_TIME}</td>
				<td style="text-align: center">${item.AR_DATE_STR}</td>
				<td style="text-align: center">${item.ALLOWANCE}</td>
			</tr>
		</c:forEach>

	</tbody>
</table>