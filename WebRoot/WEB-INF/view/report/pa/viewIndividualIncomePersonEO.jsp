<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

</script>
<div class="pageHeader">

	<form action="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=199"
		method="post">

		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						工资支付计划:
					</td>
					<td>
						<select id="PAY_SCHEDULE_NO" name="PAY_SCHEDULE_NO">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
								<c:choose>
									<c:when
										test="${PAY_SCHEDULE_NO == paySchedule.PAY_SCHEDULE_NO }">
										<option value="${paySchedule.PAY_SCHEDULE_NO }"
											selected="selected">
											${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }
										</option>
									</c:when>
									<c:otherwise>
										<option value="${paySchedule.PAY_SCHEDULE_NO }">
											${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>

				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button>
									导出到Excel
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>