<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

function magnifier_payStub(flag) {

	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));
	var scheduleNo = $('#PAY_SCHEDULE_NO', navTab.getCurrentPanel())
			.val();
	var refreshUrl = '/pa/workManagement/payStub?PAY_SCHEDULE_NO=' + scheduleNo;
	var refreshMenuCode = 'pa0131';
	//工资条
	var refreshMenuName = encodeURI(encodeURI('<spring:message code="pa.viewPaMain.GONGZITIAO.C" />'));
	//$('#searchPop',navTab.getCurrent())
	$("#magnifiers_payStub", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=all&seach_KEY='
							+ name
							+ '&refreshUrl='
							+ refreshUrl
							+ '&refreshMenuCode='
							+ refreshMenuCode
							+ '&refreshMenuName=' + refreshMenuName);
	if (flag == 'onkeyup')
		$("#magnifiers_payStub", navTab.getCurrentPanel()).click();
}

function printData() {

	$("#viewPaMonthPersonInfoEssList_pageContent").jqprint( {
		debug : false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
		importCSS : false, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
		printContainer : true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
		operaSupport : true
	//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
			});

}

function printView(){
	document.getElementById("viewPaMonthPersonInfoEssList_pageContent").style.display = 'none';
	window.print();
	document.getElementById("viewPaMonthPersonInfoEssList_pageContent").style.display = '';
}


</script>
<div class="pageContent">
	<form class="j-ajax" onsubmit="return  navTabSearch(this)"
		action="/pa/workManagement/payStub" method="post"
		id="payStubSearchForm" name="payStub">
		<div class="searchBar">
			<table class="searchContent">
				<input type="hidden" id="currentIndex" name="currentIndex" />
				<tr>
					<td>
						<!-- 工号/姓名： --> <spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td><input type="text" name="seach_KEY" id="seach_KEY"
						value="${KEY}"
						onkeydown="javascript:if(event.keyCode == 13)magnifier_payStub('onkeyup');" />

						<input type="hidden" id='dwz.person.personid' name="PERSON_ID" value="${PERSON_ID}"></td>
					<td class="td_type"><a class="btnLook" id="magnifiers_payStub"
						onclick="magnifier_payStub()" href="" lookupGroup="person"> </a> 
						<!--<span style="margin-left: 50px;" id="title_payStub">${empInfoShow}</span>-->
					</td>
					<td><!--部门--><spring:message code="public.title.deptName" /></td>
					<td>
					 	<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPT_NAME" limit="pa" level2="" selectedNm="${DEPT_NAME}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
					</td>
					<td><!--支付计划--><spring:message code="ess.empInfo.pay_plan" /></td>
					<td>
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
					<td><spring:message code="hrm.empinfo.POST_FAMILY" /><!-- 职群 --></td>
					<td>
					    <select id="seach_WORKER_FLAG" name="seach_WORKER_FLAG">
					        <option value="" <c:if test="${POST_FAMILY eq null || POST_FAMILY eq ''}" >selected</c:if>><!--全部--><spring:message code="org.title.ALL" /></option>
						      <option value="1" <c:if test="${POST_FAMILY eq '1'}" >selected</c:if>><spring:message code="ar.addempshift.title.byperson" /></option>
						      <option value="2" <c:if test="${POST_FAMILY eq '2'}" >selected</c:if>>Worker</option>
					    </select>
					</td>
					<td><!-- 任职状态 --><spring:message code="ess.infoApply.renzhizhuangtai" /></td>
					<td>
					 <ait:SelectSyCodeByCpnyID id="seach_EMP_OFFICE" name="seach_EMP_OFFICE" parentNo="15118" selected="${EMP_OFFICE}"  limit="all"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent" align="center" >
								<button type="submit"
									onkeydown="javascript:if(event.keyCode == 13)return false;">
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>

					<li>
						<div class="buttonActive">
							<div class="buttonContent" align="center" style="page-break-after:always;">
								<button type="button" onclick="printData()">
									<!--印刷--><spring:message code="hrm.approve.PRINTING" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
</form>
	<div class="pageContent" id="viewPaMonthPersonInfoEssList_pageContent"
		sysLong='printDiv'
		style="width: 710px; padding-left: 10px;  text-align: left;overflow: hidden;"  >
	<c:if test="${empty payInfolist}">
		<div style="height: 1154px;"></div>
	</c:if>
	<c:forEach items="${payInfolist}" var="pay">
		<c:if test="${pay.personInfo.REAL_WAGES ne null }">
		<div style="height: 987px;position:relative; display:block;page-break-before: always; ">
		<table width="100%" style="height: 40px;">
			<tr >
				<td width="40%"><img
					src='/resources/images/logo.png'></td>
			    <c:if test="${PAY_SCHEDULE_NO eq '158' || PAY_SCHEDULE_NO eq '171' || PAY_SCHEDULE_NO eq '184'}" >
			        <td style="font-size: 20px; text-align: center;font-weight: bold;"
					td_title" width="50%"> THƯỞNG TẾT  ${pay.personInfo.PAY_YEAR}</td>
				<td width="25%"></td>
			    </c:if>
			    <c:if test="${PAY_SCHEDULE_NO ne '158' && PAY_SCHEDULE_NO ne '171' && PAY_SCHEDULE_NO ne '184'}" >
				<td style="font-size: 20px; text-align: center;font-weight: bold;"
					td_title" width="50%"> PHIẾU LƯƠNG THÁNG  ${pay.personInfo.HR_END_DATE}</td>
				<td width="25%"></td>
				</c:if>
			</tr>
		</table>
		<br />
		
		<h2 class="thisPageStyle_pa0131"
			style="background-image: url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg'); background-repeat: no-repeat; font-size: 13px;position: relative; bottom: 3px; padding: 0; margin: 0;">
			&nbsp;&nbsp;<!--基本事项--><spring:message code="ess.empInfo.basic_matters" /></h2>
		<table class="user_table" width="100%" style="border-top: 1px solid #8aa6d7; border-left: 1px solid #DBDBD8;border-collapse:collapse;border-spacing:0;padding: 0; margin: 0;">
			<tr>
				<td class="td_title" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;">
				<!--姓名--><spring:message code="org.title.LOCAL_NAME" /></td>
				<td class="td_type" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">${pay.personInfo.LOCAL_NAME}</td>
				<td class="td_title" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;">
				<!--工号--><spring:message code="ess.infoApply.EMP_ID" /></td>
				<td class="td_type" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">${pay.personInfo.EMPID}</td>
				<td class="td_title" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;">
				<!--部门--><spring:message code="ess.infoApply.DEPT" /></td>
				<td class="td_type" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">${pay.personInfo.DEPT_NAME}</td>
				<td class="td_title" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;">
				<!--员工类型--><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /></td>
				<td class="td_type" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">${pay.personInfo.EMP_TYPE_NAME}</td>
			</tr>
			<tr>
				<td class="td_title" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;">
				<!--职群--><spring:message code="hrm.empinfo.POST_FAMILY" /></td>
				<td class="td_type" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">${pay.personInfo.POST_FAMILY_NAME}</td>
				<td class="td_title" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;">
				<!--职级--><spring:message code="sys.postManage.title.postGrade" /></td>
				<td class="td_type" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">${pay.personInfo.POST_GRADE_NAME}</td>
				<td class="td_title" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;">
				<!--职责--><spring:message code="ess.trans.title.dutyName" /></td>
				<td class="td_type" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">${pay.personInfo.POSITION_NAME}</td>
				<td class="td_title" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;">
				<!--在职状态--><spring:message code="sys.postManage.title.position" /></td>
				<td class="td_type" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">${pay.personInfo.POSITION_N}</td>
			</tr>
		</table>
		<br>
		<table class="user_table" width="100%">
			<tr>
			<td class="td_title" style="text-align: center; text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;
					border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8; width: 50%" >Số người phụ thuộc</td>
				<td style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;" class="td_type">
					${pay.personInfo.P_TAX_FAMILY_DEDUCT_COUNT}
				</td>
		    <td class="td_title" style="text-align: center; text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;
					border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8; width: 50%" >Số sổ BHXH</td>
				<td style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;" class="td_type">
					${pay.personInfo.SOCIAL_INSURANCE_NO}
				</td>
			</tr>
		</table>
		<br>
		<h2 class="thisPageStyle_pa0131"
			style="background-image: url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg'); background-repeat: no-repeat; font-size: 13px;position: relative; bottom: 3px;padding: 0; margin: 0;">
			&nbsp;&nbsp; 
			<!--帐号--><spring:message code="ess.empInfo.account_number" />
		</h2>

		<table class="user_table" width="100%" style="border-top: 1px solid #8aa6d7; border-left: 1px solid #DBDBD8;border-collapse:collapse;border-spacing:0;padding: 0; margin: 0;">
			<tr>
				<td class="td_title" style="text-align: center; text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;
					border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;"  width="13%">
				<!--银行--><spring:message code="ess.empInfo.bank" />
				</td>

				<td class="td_title" style="text-align: center;text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;
					border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;" width="13%">
					<!--帐号--><spring:message code="ess.empInfo.account_number" />
				</td>


			</tr>
			<c:forEach items="${pay.paEmpAccount}" var="item">
				<tr>
					<td style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;" class="td_type">
						${item.ACCOUNT_TYPE }</td>
					<td style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;" class="td_type">
						${item.ACCOUNT_NO } </td> </tr> </c:forEach> 
		</table>
		<br>
		<h2 class="thisPageStyle_pa0131"
			style="background-image: url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg'); background-repeat: no-repeat; font-size: 13px;position: relative; bottom: 3px;padding: 0; margin: 0;">
			&nbsp;&nbsp;<!--标准项目--><spring:message code="pa.viewSalaryCodeList.BIAOZHUNXIANGMU.b" /></h2>
		<table class="user_table" width="100%"style="border-top: 1px solid #8aa6d7; border-left: 1px solid #DBDBD8;border-collapse:collapse;border-spacing:0;padding: 0; margin: 0;">
			<tr>
				<td class="td_title" style="text-align: center; text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;
					border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;" width="13%">
					<!--项目--><spring:message code="ess.empInfo.project" /></td>
				<td class="td_title" style="text-align: center;text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;
					border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;" width="13%">
					<!--金额--><spring:message code="ess.empInfo.amount_of_money" /></td>
			</tr>
			<c:forEach items="${pay.payStubList}" var="item">
				<c:if test="${item.ITEM_TYPE eq 4}">
						<tr>
							<td class="td_type" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">${item.ITEM_NAME}</td>
							<td class="td_type" style="text-align: right;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">
								<fmt:formatNumber value="${item.ITEM_VALUE}" pattern="#,##0" /></td>
						</tr>
				</c:if>
			</c:forEach>
		</table>
		<br> 
		<div style="display: flex; justify-content: space-between;">
			<div style="width: 33,3%; ">
				<h2 class="thisPageStyle_pa0131"
					style="background-image: url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg'); background-repeat: no-repeat; font-size: 13px;">
					&nbsp;&nbsp; <!--出勤明细--><spring:message code="ess.empInfo.attendance_detail" /></h2>
			</div>
			<div style="width: 38,3%; ">
				<h2 class="thisPageStyle_pa0131"
					style="background-image: url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg'); background-repeat: no-repeat; font-size: 13px;">
					&nbsp;&nbsp;<!--工资明细--><spring:message code="ess.empInfo.salary_detail" /></h2>
			</div>
			<div style="width: 28%; ">
				<h2 class="thisPageStyle_pa0131"
					style="background-image: url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg'); background-repeat: no-repeat; font-size: 13px;">
					&nbsp;&nbsp; <!--扣除明细--><spring:message code="ess.empInfo.deduction_detail" /></h2>
			</div>
		</div>
		<c:set var="dataSize" value="0"></c:set>
		<c:set var="maxHeightData" value="0"></c:set>
		<c:forEach items="${pay.payStubList}" var="item" varStatus="i">
			<c:if test="${item.ITEM_TYPE eq 1}">
				<c:set var="dataSize" value="${dataSize + 1 }"></c:set>
			</c:if>
		</c:forEach>
		<c:set var="maxHeightData" value="${dataSize }">
		</c:set>
		<c:set var="dataSize" value="0"></c:set>
		<c:forEach items="${pay.payStubList}" var="item" varStatus="i">
			<c:if test="${item.ITEM_TYPE eq 2}">
				<c:set var="dataSize" value="${dataSize + 1 }"></c:set>
			</c:if>
		</c:forEach>
		<c:if test="${dataSize > maxHeightData }">
			<c:set var="maxHeightData" value="${dataSize }"></c:set>
		</c:if>
		<c:set var="dataSize" value="0"></c:set>
		<c:forEach items="${pay.payStubList}" var="item" varStatus="i">
			<c:if test="${item.ITEM_TYPE eq 3}">
				<c:set var="dataSize" value="${dataSize + 1 }"></c:set>
			</c:if>
		</c:forEach>
		<c:if test="${dataSize > maxHeightData }">
			<c:set var="maxHeightData" value="${dataSize }"></c:set>
		</c:if>
		<div style="display: flex; justify-content: space-between; width: 100%;">
		<div style="width: 33.3%; border: 1px solid #DBDBD8; left: 2px;">
			<!-- 津贴明细 -->
			<table class="user_table" width="100%" style = "border-left:hidden; border-right:hidden;border-top: 1px solid #8aa6d7; border-collapse:collapse;border-spacing:0;padding: 0; margin: 0;" >
				<tr>
					<td class="td_title" width="60%" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;
						border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;" width="13%"><!--考勤项目--><spring:message code="ess.empInfo.attendance_item" /></td>
					<td class="td_title" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;
						border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;" width="10%"><!--数值--><spring:message code="ess.empInfo.numerical_value" /></td>
				</tr>
				<c:set var="A" value="${0}" />
				<c:forEach items="${pay.payStubList}" var="item" varStatus="i">
					<c:if test="${item.ITEM_TYPE eq 1}">
						<tr>
							<td class="td_type" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">${item.ITEM_NAME}</td>
							<td class="td_type" style="text-align: right;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">${item.ITEM_VALUE}</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
		<div style="width: 33.3%; border: 1px solid #DBDBD8">
			<%-- 扣除明细--%>
			<table class="user_table" width="100%" style = "border-left:hidden; border-right:hidden; border-top: 1px solid #8aa6d7; border-collapse:collapse;border-spacing:0;padding: 0; margin: 0;">
				<tr>
					<td class="td_title" width="60%" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;
						border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;"width="13%"><!--项目--><spring:message code="ess.empInfo.project" /></td>
					<td class="td_title" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;
						border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;"width="10%"><!--金额--><spring:message code="ess.empInfo.amount_of_money" /></td>
				</tr>
				<c:set var="B" value="${0}" />
				<c:forEach items="${pay.payStubList}" var="item" varStatus="i">
					<c:if test="${item.ITEM_TYPE eq 2}">
						<tr>
							<td class="td_type" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">${item.ITEM_NAME}</td>
							<td class="td_type" style="text-align: right;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;"><fmt:formatNumber
									value="${item.ITEM_VALUE}" pattern="#,##0" /></td>
						</tr>
					</c:if>

				</c:forEach>
			</table>
		</div>
		<div style="width: 33%; border: 1px solid #DBDBD8">
			<%--社会保险--%>
			<table class="user_table" width="100%" style = "border-left:hidden; border-right:hidden; border-top: 1px solid #8aa6d7; border-collapse:collapse;border-spacing:0;padding: 0; margin: 0;">
				<tr>
					<td class="td_title" width="60%" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;
						border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;" width="13%"><!--项目--><spring:message code="ess.empInfo.project" /></td>
					<td class="td_title" style="text-align: center; padding:3px 3px 3px 5px;white-space:nowrap;line-height:18px;font-size: 13px;
						border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;" width="10%"><!--金额--><spring:message code="ess.empInfo.amount_of_money" /></td>
				</tr>
				<c:set var="C" value="${0}" />
				<c:forEach items="${pay.payStubList}" var="item" varStatus="i">
					<c:if test="${item.ITEM_TYPE eq 3}">
						<tr>
							<td class="td_type" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">${item.ITEM_NAME}</td>
							<td class="td_type" style="text-align: right;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;"><fmt:formatNumber
									value="${item.ITEM_VALUE}" pattern="#,##0" /></td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
		</div>
		
		<div style="width: 100%; display: flex; justify-content: space-between; border-top: 1px solid #8aa6d7; border-bottom: 1px solid #8aa6d7;">
		<div style="width: 33.3%; ">
			<table class="user_table" style="position: relative; border-left: 1px solid #DBDBD8;border-collapse:collapse;border-spacing:0;padding: 0; margin: 0;" width="100%">
				<tr>
					<td class="td_type" width="70%" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;">&nbsp</td>
					
				</tr>
			</table>
		</div>
		<div style="width: 33.3%;  ">
			<table class="user_table" style="position: relative; border-left: hidden;border-collapse:collapse;border-spacing:0;padding: 0; margin: 0;" width="100%">
				<tr>
					<td class="td_type" width="60%" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;"><!--合--><spring:message code="ess.empInfo.he" /></td>
					<td class="td_type" style="text-align: right;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;"><c:set value="${0}" var="countAll2" /> <c:forEach
							items="${pay.payStubList}" var="item" varStatus="i">
							<c:if test="${item.ITEM_TYPE eq 2}">
								<c:set value="${item.ITEM_VALUE+countAll2}" var="countAll2" />
							</c:if>
						</c:forEach> <fmt:formatNumber value="${countAll2}" pattern="#,##0" /></td>
				</tr>
			</table>
		</div>
		<div style="width: 33.3%; ">
			<table class="user_table" style="position: relative; border-left: hidden;border-collapse:collapse;border-spacing:0;padding: 0; margin: 0;" width="100%">
				<tr>
					<td class="td_type" width="60%" style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;"><!--合--><spring:message code="ess.empInfo.he" /></td>
					<td class="td_type" style="text-align: right;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 13px;"><c:set value="${0}" var="countAll3" /> <c:forEach
							items="${pay.payStubList}" var="item" varStatus="i">
							<c:if test="${item.ITEM_TYPE eq 3}">
								<c:set value="${item.ITEM_VALUE+countAll3}" var="countAll3" />
							</c:if>
						</c:forEach> <fmt:formatNumber value="${countAll3}" pattern="#,##0" /></td>
				</tr>
			</table>
		</div>
		</div>
		<div style="width: 100%;">
			<table class="user_table"
				style="height: 30px; border-left: 1px solid #DBDBD8;border-collapse:collapse;border-spacing:0;padding: 0; margin: 0;" width="100%">
				<tr>
					<td style="text-align: center;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 14px;font-weight: bold;"><!--实际支付额--><spring:message code="ess.empInfo.actual_payment_amount" /></td>
					<td style="text-align: right;border-right: 1px solid #DBDBD8; border-bottom: 1px solid #DBDBD8;padding:3px 3px 3px 5px;color:#333;line-height:18px;font-size: 14px;font-weight: bold;"><fmt:formatNumber
							value="${pay.personInfo.REAL_WAGES}" pattern="#,##0" /> &nbsp;</td>
				</tr>
			</table>
			
		</div>
		<c:if test="${PAY_SCHEDULE_NO eq '158' || PAY_SCHEDULE_NO eq '171' || PAY_SCHEDULE_NO eq '184'}" > 
		
		</c:if>
	    <c:if test="${PAY_SCHEDULE_NO ne '158' && PAY_SCHEDULE_NO ne '171' && PAY_SCHEDULE_NO ne '184'}" >
		<div style="width: 100%; padding-top: 5px">
		<br>
		<h4 class="thisPageStyle_pa0131"
			style="background-image: url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg'); background-repeat: no-repeat; font-size: 13px;position: relative; bottom: 3px;padding: 0; margin: 0;">
			&nbsp;&nbsp;<!--标准项目--><spring:message code="display.pa.ecc.otherItem" /></h4>
			<br>
			<table class="user_table"
				style="position: relative; bottom: 5px; height: 30px;border-top: 1px solid #8aa6d7; border-left: 1px solid #DBDBD8;border-collapse:collapse;border-spacing:0;padding: 0; margin: 0;" width="100%">
				<c:forEach items="${pay.getPaInputTaxItem}" var="item">
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
		
		<%-- <div style="width: 100%; float: left; position: relative; top: 50px"><span style="font-size:14px">※ Mọi thắc mắc về nội dung trên phiếu lương, vui lòng liên hệ phòng Nhân sự trước 15h ngày ${pay.personInfo.END_DATE}!</span></div> --%>
		<div style="width: 100%; padding-top: 10px"><span style="font-size:14px">※ ${pay.personInfo.EMP_PA_OPINION}!</span></div>
		
		
</div>
		</c:if>
		</c:forEach>
</div>
</div>