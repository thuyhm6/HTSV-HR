<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

//判断工资是否开放
function getSalaryDispark() {

	var viewPaM = $("#viewPaMonthPersonInfoEssList_pay").attr("value");
	var PERSON_ID = viewPaM.split(',')[0];
	var PAY_SCHEDULE_NO = viewPaM.split(',')[1];
	var PA_OPEN_FLAG = viewPaM.split(',')[2];
	var PAY_DATE = viewPaM.split(',')[3];
	//alertMsg.info(PAY_DATE);

	//if (PAY_DATE <= getNowFormatDate()) {

		if (PA_OPEN_FLAG == "1") {
			$("#viewarmonthpersoninfo").attr(
					"action",
					"/pa/salary/viewPaMonthPersonInfoEssList?PERSON_ID="
							+ PERSON_ID + "&PAY_SCHEDULE_NO=" + PAY_SCHEDULE_NO
							+ "&PAY_DATE=" + PAY_DATE);
			$("#viewarmonthpersoninfo").submit();
		} else {
			alertMsg.info("<spring:message code='liang.pa.salary.title.salary_NotDispark' />");//工资还未开放!

		}
	/* } else {
		alertMsg.info("<spring:message code='liang.pa.salary.title.salary_NotDispark' />");工资未开放!

	} */
}

function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate + "";
	return currentdate;
}

$(function() {

	if ($("#display_PAY_SCHEDULE_NO").attr("value") == null
			|| $("#display_PAY_SCHEDULE_NO").attr("value") == "") {

		$("#viewPaMonthPersonInfoEssList_pageContent").css("display", "none");
	}
});
</script>
<div class="pageHeader">
	<form id="viewarmonthpersoninfo" onsubmit="return  navTabSearch(this)"
		name="viewarmonthpersoninfo"
		action="/pa/salary/viewPaMonthPersonInfoEssList?pageNum=1&numPerPage=0"
		method="post">
		<div class="searchBar">
			<input type="hidden" value="${CurrentOperator}" id="CurrentOperator">
			<table class="searchContent">
				<tr>
					<td>
					<!--工资支付计划--><spring:message code="ess.empInfo.pay_plan" />
					</td>
					<input type="hidden" value="${PAY_SCHEDULE_NO}"
						id="display_PAY_SCHEDULE_NO" />
					<td>

						<select id="viewPaMonthPersonInfoEssList_pay"
							onchange="getSalaryDispark();">
							<c:forEach items="${paPayScheduleNoByPersonId}" var="item">
								<option
									value="${item.PERSON_ID},${item.PAY_SCHEDULE_NO},${item.PA_OPEN_FLAG},${item.PAY_DATE}"
									<c:if test="${item.PAY_SCHEDULE_NO eq PAY_SCHEDULE_NO}">selected</c:if>>
									${item.SALARY_DISTIN}--${item.PA_OPEN_DATE}

								</option>
							</c:forEach>
						</select>

					</td>
					<td>
						<div class="subBar">
							<ul>
								<li>
									<div class="buttonActive">
										<div class="buttonContent">
											<button type="button" onclick="getSalaryDispark();">
												<!-- 查询 -->
												<div><spring:message code="button.search"  /></div>
											</button>
										</div>
									</div>
								</li>
								<li>
									<div class="buttonActive">
										<div class="buttonContent">
											<button type="button"  onclick="print()">
												<!-- 打印 -->
												<div><spring:message code="rp.report.title.print"  /></div>
											</button>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<c:if test="${personInfo.PAY_DATE ne null }">
	<div class="pageContent" id="viewPaMonthPersonInfoEssList_pageContent"
		sysLong='printDiv'
		style="width: 800px; height: 1200px; padding-left: 10px; text-align: left;overflow: hidden;" >

		<table width="100%">
			<tr height="80px">
				<td width="40%"><img
					src='/resources/css/dwzUI/themes/partner/images/logo.jpg'></td>
				<c:if test="${PAY_SCHEDULE_NO eq '158' || PAY_SCHEDULE_NO eq '171' || PAY_SCHEDULE_NO eq '184'}" > 
					<td style="font-size: 20px; text-align: center;font-weight: bold;"
					td_title" width="40%">THƯỞNG TẾT  ${personInfo.HR_END_DATE} </td>
				</c:if>
				<c:if test="${PAY_SCHEDULE_NO ne '158' && PAY_SCHEDULE_NO ne '171' && PAY_SCHEDULE_NO ne '184'}" >
					<td style="font-size: 20px; text-align: center;font-weight: bold;"
					td_title" width="40%">PHIẾU LƯƠNG THÁNG  ${personInfo.HR_END_DATE} </td>
				</c:if>
				
			</tr>
		</table>
		<br>
		<h2 class="thisPageStyle_pa0131"
			style="background-image: url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg'); background-repeat: no-repeat">
			&nbsp;&nbsp;<!--基本事项--><spring:message code="ess.empInfo.basic_matters" />
		</h2>


		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"
					style="text-align: center; ">
					<!--姓名 --><spring:message code="ess.infoApply.NAME" />
					</td>
				<td class="td_type" style="text-align: center;">${personInfo.LOCAL_NAME}</td>
				<td class="td_title" style="text-align: center;"><!--工号 --><spring:message code="ess.infoApply.EMP_ID" /></td>
				<td class="td_type" style="text-align: center;">${personInfo.EMPID}</td>
				<td class="td_title" style="text-align: center;"><!--部门 --><spring:message code="ess.infoApply.DEPT" /></td>
				<td class="td_type" style="text-align: center;">${personInfo.DEPT_NAME}</td>
				<td class="td_title" style="text-align: center;"><!--员工类型 --><spring:message code="ess.infoApply.employee_type" /></td>
				<td class="td_type" style="text-align: center;">${personInfo.EMP_TYPE_NAME}</td>
			</tr>
			<tr>
				<td class="td_title" style="text-align: center;"><!--职群 --><spring:message code="ess.empInfo.zhiqun" /></td>
				<td class="td_type" style="text-align: center;">${personInfo.POST_FAMILY_NAME}</td>
				<td class="td_title" style="text-align: center;"><!--职级 --><spring:message code="ess.infoApply.Rank" /></td>
				<td class="td_type" style="text-align: center;">${personInfo.POST_GRADE_NAME}</td>
				<td class="td_title" style="text-align: center;"><!--职责--><spring:message code="ess.trans.title.dutyName" /></td>
				<td class="td_type" style="text-align: center;">${personInfo.POSITION_NAME}</td>
				<td class="td_title" style="text-align: center;"><!--员工状态 --><spring:message code="sys.postManage.title.position" /></td>
				<td class="td_type" style="text-align: center;">${personInfo.POSITION_N}</td>
			</tr>
		</table>
		<br>
		<table class="user_table" width="100%">
			<tr>
			<td class="td_title" style="text-align: center; width: 50%" >Số người phụ thuộc</td>
				<td style="text-align: center;" class="td_type">
					${personInfo.P_TAX_FAMILY_DEDUCT_COUNT}
				</td>
			</tr>
		</table>
		<br>
		<h2 class="thisPageStyle_pa0131"
			style="background-image: url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg'); background-repeat: no-repeat">
			&nbsp;&nbsp; 
			<!--帐号--><spring:message code="ess.empInfo.account_number" />
		</h2>


		<table class="user_table" width="100%">
			<tr>
				<td class="td_title" style="text-align: center;" width="13%">
				<!--银行--><spring:message code="ess.empInfo.bank" />
				</td>

				<td class="td_title" style="text-align: center;" width="13%">
					<!--帐号--><spring:message code="ess.empInfo.account_number" />
				</td>


			</tr>
			<c:forEach items="${paEmpAccount}" var="item">
				<tr>
					<td style="text-align: center;" class="td_type">
						${item.ACCOUNT_TYPE }</td>
					<td style="text-align: center;" class="td_type">
						${item.ACCOUNT_NO } </td> </tr> </c:forEach> 
		</table>
		<br>
		<h2 class="thisPageStyle_pa0131"
			style="background-image: url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg'); background-repeat: no-repeat">
			&nbsp;&nbsp;<!--标准项目--><spring:message code="pa.viewSalaryCodeList.BIAOZHUNXIANGMU.b" /></h2>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title" style="text-align: center;" width="13%">
					<!--项目--><spring:message code="ess.empInfo.project" /></td>
				<td class="td_title" style="text-align: center;" width="13%">
					<!--金额--><spring:message code="ess.empInfo.amount_of_money" /></td>
			</tr>
			<c:forEach items="${payStubList}" var="item">
				<c:if test="${item.ITEM_TYPE eq 4}">
						<tr>
							<td class="td_type" style="text-align: center;">${item.ITEM_NAME}</td>
							<td class="td_type" style="text-align: right;"><fmt:formatNumber value="${item.ITEM_VALUE}" pattern="#,##0" /></td>
						</tr>
				</c:if>
			</c:forEach>
		</table>
		<br> 
						<div style="width: 33%; float: left;"> <h2
						class="thisPageStyle_pa0131" style="background-image:
						url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg');
						background-repeat: no-repeat">&nbsp;&nbsp;<!-- 出勤明细 --><spring:message code="ess.empInfo.attendance_detail" /></h2> </div> <div
						style="width: 34%; float: left;"> <h2 class="thisPageStyle_pa0131"
						style="background-image:
						url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg');
						background-repeat: no-repeat">&nbsp;&nbsp;<!-- 工资明细--><spring:message code="ess.empInfo.salary_detail" /></h2> </div> <div
						style="width: 33%; float: left;"> <h2 class="thisPageStyle_pa0131"
						style="background-image:
						url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg');
						background-repeat: no-repeat">&nbsp;&nbsp;<!-- 扣除明细  --><spring:message code="ess.empInfo.deduction_detail" /></h2> </div>
					<c:set var="dataSize" value="0"></c:set>
		<c:set var="maxHeightData" value="0"></c:set>
		<c:forEach items="${payStubList}" var="item" varStatus="i">
			<c:if test="${item.ITEM_TYPE eq 1}">
				<c:set var="dataSize" value="${dataSize + 1 }"></c:set>
			</c:if>
		</c:forEach>
		<c:set var="maxHeightData" value="${dataSize }">
		</c:set>
		<c:set var="dataSize" value="0"></c:set>
		<c:forEach items="${payStubList}" var="item" varStatus="i">
			<c:if test="${item.ITEM_TYPE eq 2}">
				<c:set var="dataSize" value="${dataSize + 1 }"></c:set>
			</c:if>
		</c:forEach>
		<c:if test="${dataSize > maxHeightData }">
			<c:set var="maxHeightData" value="${dataSize }"></c:set>
		</c:if>
		<c:set var="dataSize" value="0"></c:set>
		<c:forEach items="${payStubList}" var="item" varStatus="i">
			<c:if test="${item.ITEM_TYPE eq 3}">
				<c:set var="dataSize" value="${dataSize + 1 }"></c:set>
			</c:if>
		</c:forEach>
		<c:if test="${dataSize > maxHeightData }">
			<c:set var="maxHeightData" value="${dataSize }"></c:set>
		</c:if>
		<div
			style="width: 33%; float: left; height: ${maxHeightData*30+20}px;
						border: 1px solid #DBDBD8; left: 2px;">
			<!-- 津贴明细 -->
			<table class="user_table" width="100%" style = "border-left:hidden; border-right:hidden;">
				<tr>
					<td class="td_title" width="60%" style="text-align: center;"><!--考勤项目 --><spring:message code="ess.empInfo.attendance_item" /></td>
					<td class="td_title" style="text-align: center;"><!--数值 --><spring:message code="ess.empInfo.numerical_value" /></td>
				</tr>
				<c:set var="A" value="${0}" />
				<c:forEach items="${payStubList}" var="item" varStatus="i">
					<c:if test="${item.ITEM_TYPE eq 1}">
						<tr>
							<td class="td_type" style="text-align: center;">${item.ITEM_NAME}</td>
							<td class="td_type" style="text-align: right;">${item.ITEM_VALUE}</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
		<div
			style="width: 34%; float: left; height:
						${maxHeightData*30+20}px; border: 1px solid #DBDBD8">
			<%-- 扣除明细
						--%>
			<table class="user_table" width="100%" style = "border-left:hidden; border-right:hidden;">
				<tr>
					<td class="td_title" width="60%" style="text-align: center;"><!--项目 --><spring:message code="ess.empInfo.project" /></td>
					<td class="td_title" style="text-align: center;"><!--金额 --><spring:message code="ess.empInfo.amount_of_money" /></td>
				</tr>
				<c:set var="B" value="${0}" />
				<c:forEach items="${payStubList}" var="item" varStatus="i">
					<c:if test="${item.ITEM_TYPE eq 2}">
						<tr>
							<td class="td_type" style="text-align: center;">${item.ITEM_NAME}</td>
							<td class="td_type" style="text-align: right;"><fmt:formatNumber
									value="${item.ITEM_VALUE}" pattern="#,##0" /></td>

						</tr>
					</c:if>

				</c:forEach>

			</table>

		</div>
		<div
			style="width: 33%; float: left; height: ${maxHeightData*30+20}px; border: 1px solid #DBDBD8">
			<%--
			社会保险
			--%>
			<table class="user_table" width="100%" style = "border-left:hidden; border-right:hidden;">

				<tr>

					<td class="td_title" width="60%" style="text-align: center;"><!--项目 --><spring:message code="ess.empInfo.project" /></td>
					<td class="td_title" style="text-align: center;"><!--金额 --><spring:message code="ess.empInfo.amount_of_money" /></td>

				</tr>
				<c:set var="C" value="${0}" />
				<c:forEach items="${payStubList}" var="item" varStatus="i">
					<c:if test="${item.ITEM_TYPE eq 3}">
						<tr>

							<td class="td_type" style="text-align: center;">${item.ITEM_NAME}</td>
							<td class="td_type" style="text-align: right;"><fmt:formatNumber
									value="${item.ITEM_VALUE}" pattern="#,##0" /></td>

						</tr>
					</c:if>
				</c:forEach>
			</table>


		</div>

		<div style="width: 33%; float: left; ">
			<table class="user_table" style="position: relative;" width="100%">
				<tr>
					<td class="td_type" width="70%">&nbsp</td>
					<%-- <td class="td_type"><c:set value="${0}" var="countAll1" /> <c:forEach
							items="${payStubList}" var="item" varStatus="i">
							<c:if test="${item.ITEM_TYPE eq 1}">
								<c:set value="${item.ITEM_VALUE+countAll1}" var="countAll1" />
							</c:if>
						</c:forEach> <fmt:formatNumber value="${countAll1}" pattern="#,##0" /></td> --%>
				</tr>
			</table>
		</div>
		<div style="width: 34%; float: left; ">
			<table class="user_table" style="position: relative; border-right:hidden ;" width="100%">
				<tr>
					<td class="td_type" width="60%" style="text-align: center;"><!--合 --><spring:message code="ess.empInfo.he" /></td>
					<td class="td_type" style="text-align: right;"><c:set value="${0}" var="countAll2" /> <c:forEach
							items="${payStubList}" var="item" varStatus="i">
							<c:if test="${item.ITEM_TYPE eq 2}">
								<c:set value="${item.ITEM_VALUE+countAll2}" var="countAll2" />
							</c:if>
						</c:forEach> <fmt:formatNumber value="${countAll2}" pattern="#,##0" /></td>
				</tr>
			</table>
		</div>
		<div style="width: 33%; float: left; ">
			<table class="user_table" style="position: relative;" width="100%">
				<tr>
					<td class="td_type" width="60%" style="text-align: center;"><!--合 --><spring:message code="ess.empInfo.he" /></td>
					<td class="td_type" style="text-align: right;"><c:set value="${0}" var="countAll3" /> <c:forEach
							items="${payStubList}" var="item" varStatus="i">
							<c:if test="${item.ITEM_TYPE eq 3}">
								<c:set value="${item.ITEM_VALUE+countAll3}" var="countAll3" />
							</c:if>
						</c:forEach> <fmt:formatNumber value="${countAll3}" pattern="#,##0" /></td>
				</tr>
			</table>
		</div>
		<div style="width: 100%; float: left; position: relative; top: 5px;">
			<table class="user_table"
				style="position: relative; bottom: 5px; height: 30px" width="100%">
				<tr>
					<td style="text-align: center;font-weight: bold;"><!--实际支付额 --><spring:message code="ess.empInfo.actual_payment_amount" /></td>
					<td style="text-align: right;font-weight: bold;"><fmt:formatNumber
							value="${personInfo.REAL_WAGES}" pattern="#,##0" /> &nbsp;</td>
				</tr>
			</table>
		</div>
		<c:if test="${PAY_SCHEDULE_NO eq '158' || PAY_SCHEDULE_NO eq '171' || PAY_SCHEDULE_NO eq '184'}" > 
		
		</c:if>
		<c:if test="${PAY_SCHEDULE_NO ne '158' && PAY_SCHEDULE_NO ne '171' && PAY_SCHEDULE_NO ne '184'}" > 
			<div style="width: 100%; float: left; position: relative; top: 5px">
		<br>
		<h4 class="thisPageStyle_pa0131"
			style="background-image: url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg'); background-repeat: no-repeat; font-size: 13px;position: relative; bottom: 3px;padding: 0; margin: 0;">
			&nbsp;&nbsp;<!--标准项目--><spring:message code="display.pa.ecc.otherItem" /></h4>
			<br>
			<table class="user_table"
				style="position: relative; bottom: 5px; height: 30px;border-top: 1px solid #8aa6d7; border-left: 1px solid #DBDBD8;border-collapse:collapse;border-spacing:0;padding: 0; margin: 0;" width="100%">
				<c:forEach items="${paInputTaxItem}" var="item">
				<tr>
					<td style="text-align: left;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;" class="td_type">
						${item.REMARK }</td>
					<td style="text-align: right;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;" class="td_type">
						<fmt:formatNumber value="${item.RETURN_VALUE }" pattern="#,##0.0" /> &nbsp;</td>
				</tr>
			</c:forEach>
			</table>
			
		</div>
		</c:if>
		
		<div style="width: 100%; float: left; position: relative; top: 10px"><span style="font-size:14px">※ ${personInfo.EMP_PA_OPINION}!</span></div>
</c:if>