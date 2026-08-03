<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function submitTxt() {

	$("#exportPayDetailTxtReport").submit();

}

function submitTxt2() {

	$("#exportPayDetailTxtReport2").submit();

}
</script>
<div class="pageHeader">
	<form id="exportPayDetailTxtReport"
		action="/pa/workManagement/exportPayDetailTxtReport" method="post">
		<input type="hidden" name="SPECIAL_INSURANCE" value="1">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
				 <td width="5%" style="text-align: center">
					
					</td>
					<td width="5%" style="text-align: center">
						工资支付日期：
					</td>
					<td width="20%">

						<select id="PAY_SCHEDULE_NO_exportPayDetailTxt"
							name="PAY_SCHEDULE_NO">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">

								<option value="${paySchedule.PAY_SCHEDULE_NO}">
									${paySchedule.PAY_DATE }-${paySchedule.SALARY_DISTIN}
								</option>
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
								<button type="button" onclick="submitTxt()">
									导出
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>





<div class="pageHeader">
	<form id="exportPayDetailTxtReport2"
		action="/pa/workManagement/exportPayDetailTxtReport" method="post">
		<div class="searchBar">
			<input type="hidden" name="SPECIAL_INSURANCE" value="2">
			<table class="searchContent">
				<tr>
			      <td width="5%" style="text-align: center">
						外地
					</td>
					<td width="5%" style="text-align: center">
						工资支付日期：
					</td>
					<td width="20%">

						<select id="PAY_SCHEDULE_NO_exportPayDetailTxt"
							name="PAY_SCHEDULE_NO">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">

								<option value="${paySchedule.PAY_SCHEDULE_NO}">
									${paySchedule.PAY_DATE }-${paySchedule.SALARY_DISTIN}
								</option>
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
								<button type="button" onclick="submitTxt2()">
									导出
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>