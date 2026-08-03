<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function ClearToSubmit(){
	var $form = $("#viewClearAnnualLeave_form");
	var PAY_SCHEDULE_NO = $("#PAY_SCHEDULE_NO").val();
	if(PAY_SCHEDULE_NO == null || PAY_SCHEDULE_NO == ''){
		alertMsg.error('<spring:message code="ar.viewVacClearInfo.QINGXUANZEZHIFUJIHUA.b"/>');//请选择工资支付计划!
	}
	$.ajax({
  		type: 'POST',
  		url:$form.attr("action"),
  		data:$form.serializeArray(),
  		dataType:"json",
  		cache: false,
  		success: dialogAjaxDoneWithForm,
  		error: DWZ.ajaxError
  	});
}
</script>

	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" onclick="ClearToSubmit()">
							<!-- 清算 --><spring:message code="display.mutual.liquidation"/>
						</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">
							<!-- 关闭 --><spring:message code="ess.infoApply.close"/>
						</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
<div class="pageContent" layoutH="50">
           <form id="viewClearAnnualLeave_form" method="post" action="/ar/attendanceSettings/executeVacClear" class="pageForm required-validate">
						<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title"><!--工资支付计划--><spring:message code="ess.empInfo.pay_plan" />:</td>
								<td class="td_type">
								    <input type="hidden" id="clearYear" name="clearYear" value="${clearYear }">
									<select id="PAY_SCHEDULE_NO" name="PAY_SCHEDULE_NO">
										<c:forEach items="${paPayScheduleList}" var="paySchedule" varStatus="i">
											<c:choose>
												<c:when test="${PAY_SCHEDULE_NO == paySchedule.PAY_SCHEDULE_NO }">
													<option value="${paySchedule.PAY_SCHEDULE_NO }" selected="selected">${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }</option>
												</c:when>
												<c:otherwise>
													<option value="${paySchedule.PAY_SCHEDULE_NO }" >${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
	</form>
</div>