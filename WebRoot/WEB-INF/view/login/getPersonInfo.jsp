<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<head>
</head>
<div class="pageContent">
<script type="text/javascript">
// 初始调用
$(document).ready(function() {
});
</script>

		<table class="table" width="100%" layoutH="50">
			<thead>
				<tr>
					<th>
						No.
					</th>
					<th>
						<!-- 姓名 --><spring:message code="ess.infoApply.NAME" />
					</th>
					<th>
						<!-- 社号 --><spring:message code="ess.infoApply.EMPID" />
					</th>
					<th>
						<!-- 部门 --><spring:message code="ess.infoApply.DEPT" />
					</th>
					<th>
						<!-- 职级 --><spring:message code="hrm.contract.Rank" />
					</th>
					<th>
						<!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" />
					</th>
					<th>
						<!-- 员工类型 --><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" />
					</th>
					<th>
						<!-- 状态 --><spring:message code="ar.attendanceView.viewNoSwipingCard.status" />
					</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${monthPersonCountInfoSonList}" var="item"
					varStatus="i">

					<tr>
						<td style="text-align: center">
							${i.count}
							<span id="isSelectRow_${i.count}"></span>
						</td>
						<td style="text-align: center">
							${item.LOCAL_NAME}
						</td>
						<td style="text-align: center">
							${item.EMPID}
						</td>
						<td style="text-align: center">
							${item.DEPT_NAME}
						</td>
						<td style="text-align: center">
							${item.POST_GRADE}
						</td>
						<td style="text-align: center">
							${item.MAIN_BUSINESS}
						</td>
						<td style="text-align: center">
							${item.EMP_TYPE}
						</td>
						<td style="text-align: center">
							${item.EMP_OFFICE}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>
