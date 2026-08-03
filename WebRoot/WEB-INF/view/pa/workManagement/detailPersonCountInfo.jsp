<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function magnifier_detaiPersonCountInfo(flag) {

	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));
	var scheduleNo = $('#pa1014_PAY_SCHEDULE_NO', navTab.getCurrentPanel())
			.val();
	var pay_date = $("#pa1014_PAY_SCHEDULE_NO option[selected='selected']")
			.attr("title");
	var refreshUrl = '/pa/workManagement/detailPersonCountInfo?PAY_SCHEDULE_NO='
			+ scheduleNo + '&PAY_DATE_SS=' + pay_date;
	var refreshMenuCode = 'pa1014';
	var refreshMenuName = encodeURI(encodeURI('<spring:message code="pa.viewPaMain.GONGZIXIANGXIMINGXI.C" />'));//工资详细明细
	//$('#searchPop',navTab.getCurrent())
	$("#magnifier_detaiPersonCountInfo", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=pa&seach_KEY='
							+ name
							+ '&refreshUrl='
							+ refreshUrl
							+ '&refreshMenuCode='
							+ refreshMenuCode
							+ '&refreshMenuName=' + refreshMenuName);
	if (flag == 'onkeyup')
		$("#magnifier_detaiPersonCountInfo", navTab.getCurrentPanel()).click();
}

$(document).ready(function() {
	changePayDate();

});

function changePayDate() {

	var pay_date = $("#pa1014_PAY_SCHEDULE_NO option[selected='selected']")
			.attr("title");

	$("#pa1014_PAY_DATE").attr("value", pay_date);

}

function openDialog(obj) {

	$(obj).dialog();

}
</script>

<div class="pageContent">

	<form class="j-ajax" onsubmit="return  navTabSearch(this)"
		action="/pa/workManagement/detailPersonCountInfo" method="post"
		id="
" name="detailPersonCountInfo">

		<input type="hidden" id="pa1014_PAY_DATE" name="PAY_DATE_SS" />

		<div class="searchBar">
			<table class="searchContent">


				<input type="hidden" id="currentIndex" name="currentIndex" />
				<!-- 姓名 -->
				<tr>
					<td style="width: 10%">
						<!-- 工号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td style="width: 10%">


						<input type="text" name="seach_KEY" id="seach_KEY"
							value="${personInfo.LOCAL_NAME}"
							onkeydown="javascript:if(event.keyCode == 13)magnifier_detaiPersonCountInfo('onkeyup');" />

						<input type="hidden" name="PERSON_ID"
							value="${personInfo.PERSON_ID}" />

					</td>

					<td class="td_type">
						<a class="btnLook" id="magnifier_detaiPersonCountInfo"
							onclick="magnifier_detaiPersonCountInfo()" href=""
							lookupGroup="person"> </a>
						<span style="margin-left: 50px;" id="title_detaiPersonCountInfo">${LOCAL_TITLE}</span>
					</td>
					<td>
						<input type="hidden" name="empInfoShow" value="${empInfoShow }" />
						${empInfoShow }
					</td>
				</tr>
				<!-- 姓名 -->
				<tr>
					<td>
						<!--工资支付计划--><spring:message code="ess.empInfo.pay_plan" />:
					</td>
					<td>
						<select id="pa1014_PAY_SCHEDULE_NO" name="PAY_SCHEDULE_NO"
							onchange="changePayDate()">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
								<c:choose>
									<c:when
										test="${PAY_SCHEDULE_NO == paySchedule.PAY_SCHEDULE_NO }">
										<option value="${paySchedule.PAY_SCHEDULE_NO }"
											title="${paySchedule.PAY_DATE}" selected="selected">
											${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }
										</option>
									</c:when>
									<c:otherwise>
										<option value="${paySchedule.PAY_SCHEDULE_NO }"
											title="${paySchedule.PAY_DATE}">
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
							<div class="buttonContent" align="center">
								<button type="submit" class="button">
									<spring:message code="public.title.search" />
								</button>

							</div>
						</div>
					</li>
				</ul>
			</div>

		</div>
	</form>
	<table class="user_table" width="100%">
		<tr>
			<td class="td_title" >
				<!--部门--><spring:message code="ess.infoApply.DEPT" />
			</td>
			<td class="td_type" >
				${personInfo.DEPT_NAME}

			</td>
			<td class="td_title" >
				<!--职群--><spring:message code="ess.empInfo.zhiqun" />
			</td>
			<td class="td_type" >
				${personInfo.POST_FAMILY_NAME}
			</td>
			<td class="td_title" >
				<!--职级--><spring:message code="hrm.contract.Rank" />
			</td>
			<td class="td_type" >
				${personInfo.POST_GRADE_NAME}
			</td>
			<td class="td_title">
				<!--职责--><spring:message code="org.title.POSITION_NO" />
			</td>
			<td class="td_type">
				${personInfo.POSITION_NAME}
			</td>
		</tr>
		<tr>
			<td class="td_title">
				<!--入职日期--><spring:message code="ess.empInfo.entry_date" />
			</td>
			<td class="td_type">
				${personInfo.DATE_STARTED}
			</td>
			<td class="td_title">
				<!--转正日期--><spring:message code="hr.viewPersonalInfo.title.END_PROBATION_DATE" />
			</td>
			<td class="td_type">
				${personInfo.END_PROBATION_DATE}
			</td>
			<td class="td_title">
				<!--离职日期--><spring:message code="ess.empInfo.leaveDate" />
			</td>
			<td class="td_type">
				${personInfo.DATE_LEFT}
			</td>
			<td class="td_title">
				<!--实发工资--><spring:message code="ess.viewpersonalpainfo.shifagongzi" />
			</td>
			<td class="td_type">
				${personInfo.REAL_WAGES}
			</td>
		</tr>
	</table>
	<br>
	<br>
	<div class="pageContent" style="width: 30%; float: left;">
		<!--给予项目--><spring:message code="pa.detailPersonCountInfo.JIYUXIANGMU.b" />
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title">
					No.
				</td>
				<td class="td_title">
					<!--项目--><spring:message code="ess.empInfo.project" />
				</td>
				<td class="td_title">
					<!--金额--><spring:message code="ess.empInfo.amount_of_money" />
				</td>
				<td class="td_title">
					<!--公式--><spring:message code="pa.insurance.title.formula" />
				</td>
			</tr>
			<c:set var="A" value="${0}" />
			<c:forEach items="${detailPersonCountInfoList}" var="item">
				<c:if test="${item.ITEM_TYPE eq 1}">
					<tr>
						<td class="td_type">
							<c:set var="A" value="${A+1}" />
							${A}
						</td>
						<td class="td_type">
							${item.ITEM_NAME}
						</td>
						<td class="td_type">
							<fmt:formatNumber value="${item.ITEM_VALUE}" pattern="#,##0" />
						</td>
						<td class="td_type">
							<c:choose>
								<c:when test="${fn:length(item.ITEM_FORMULAR)>6 }">
									<span title="${item.ITEM_FORMULAR}">${fn:substring(item.ITEM_FORMULAR,
										0, 8)}...
										</span>
								</c:when>
								<c:otherwise>
								${item.ITEM_FORMULAR}
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
	</div>
	<div class="pageContent" style="width: 30%; float: left;">
		<!--扣除项目--><spring:message code="pa.detailPersonCountInfo.KOUCHUXIANGMU.b" />
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title">
					NO.
				</td>
				<td class="td_title">
					<!--项目--><spring:message code="ess.empInfo.project" />
				</td>
				<td class="td_title">
					<!--金额--><spring:message code="ess.empInfo.amount_of_money" />
				</td>
				<td class="td_title">
					<!--公式--><spring:message code="pa.insurance.title.formula" />
				</td>
			</tr>
			<c:set var="B" value="${0}" />
			<c:forEach items="${detailPersonCountInfoList}" var="item"
				varStatus="i">
				<c:if test="${item.ITEM_TYPE eq 2}">
					<tr>
						<td class="td_type">
							<c:set var="B" value="${B+1}" />
							${B}
						</td>
						<td class="td_type">
							${item.ITEM_NAME}
						</td>
						<td class="td_type">
							<fmt:formatNumber value="${item.ITEM_VALUE}" pattern="#,##0" />
						</td>
						<td class="td_type">
							<c:choose>
								<c:when test="${fn:length(item.ITEM_FORMULAR)>6 }">
									<span title="${item.ITEM_FORMULAR}">${fn:substring(item.ITEM_FORMULAR,
										0, 8)}...
										</span>
								</c:when>
								<c:otherwise>
								${item.ITEM_FORMULAR}
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>

			</c:forEach>

		</table>
	</div>
	<div class="pageContent" style="width: 33%; float: left;">
		<!--公司保险--><spring:message code="pa.detailPersonCountInfo.GONGSIBAOXIAN.b" />
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title">
					NO.
				</td>
				<td class="td_title">
					<!--项目--><spring:message code="ess.empInfo.project" />
				</td>
				<td class="td_title">
					<!--金额--><spring:message code="ess.empInfo.amount_of_money" />
				</td>
				<td class="td_title">
					<!--公式--><spring:message code="pa.insurance.title.formula" />
				</td>
			</tr>
			<c:set var="C" value="${0}" />
			<c:forEach items="${detailPersonCountInfoList}" var="item"
				varStatus="i">
				<c:if test="${item.ITEM_TYPE eq 3}">
					<tr>
						<td class="td_type">
							<c:set var="C" value="${C+1}" />
							${C}
						</td>
						<td class="td_type">
							${item.ITEM_NAME}
						</td>
						<td class="td_type">
							<fmt:formatNumber value="${item.ITEM_VALUE}" pattern="#,##0" />
						</td>
						<td class="td_type">
							<c:choose>
								<c:when test="${fn:length(item.ITEM_FORMULAR)>6 }">
									<span title="${item.ITEM_FORMULAR}">${fn:substring(item.ITEM_FORMULAR,
										0, 8)}...
										</span>
								</c:when>
								<c:otherwise>
								${item.ITEM_FORMULAR}
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
	</div>
</div>