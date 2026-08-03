<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

var yeru = $('#PAY_SCHEDULE_NO',navTab.getCurrentPanel()).find("option:selected").text();
$('#YEAR_DATE',navTab.getCurrentPanel()).attr("value",$.trim(yeru).substring(0,4));
$('#SALARY_DISTIN',navTab.getCurrentPanel()).attr("value",$.trim(yeru).substring(11,13));
$('#DAY_DATE',navTab.getCurrentPanel()).attr("value",$.trim(yeru).substring(5,10));
 
</script>
<div class="pageHeader">

	<form action="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=210"
		method="post">
		<input id="SALARY_DISTIN" type="hidden">
		<input id="YEAR_DATE" type="hidden">
		<input id="DAY_DATE" type="hidden">
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
								<c:if
									test="${paySchedule.SALARY_DISTIN_NO eq '14013801' or paySchedule.SALARY_DISTIN_NO eq '14013802'}">
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
								</c:if>
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
