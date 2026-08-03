<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function magnifier_pa0132_s(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));
	var scheduleNo = $('#pa0132_PAY_SCHEDULE_NO', navTab.getCurrentPanel())
			.val();

	var refreshUrl = '/pa/workManagement/detailmonthCountInfoLeft?PAY_SCHEDULE_NO=' + scheduleNo;
	var refreshMenuCode = 'pa0132';

	var refreshMenuName = encodeURI(encodeURI('<spring:message code="pa.viewPaMain.YUEGONGZIMINGXI.C" />'));//月工资明细
	//$('#searchPop',navTab.getCurrent())
	$("#magnifier_pa0132", navTab.getCurrentPanel())
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
		$("#magnifier_pa0132", navTab.getCurrentPanel()).click();
}
function changeUrlDetail(PAY_SCHEDULE_NO, PERSON_ID) {
	openOnRight('/pa/workManagement/detailYearCountInfoRight?PAY_SCHEDULE_NO='
			+ PAY_SCHEDULE_NO + '&PERSON_ID=' + PERSON_ID+'&pFrom=month' , 'detailmonthCountInfoLeft_uitl');
}
$(document).ready(
		function() {
			$("#pa0132_pageContent").css("height",
					$(document.body).height() - 200);
			$("#pa0132_pageContent")
					.css("width", $(document.body).width() - 20);
			$("#detailmonthCountInfoLeft_left").css("width",
					"70%");
			$("#pa0132_table").css("width",
					$("#detailmonthCountInfoLeft_left").width() - 10);

			$("#pa0132_table", navTab.getCurrentPanel()).dataTable( {
				"bPaginate" : false, //关闭分页
				"bAutoWidth" : false,//表格宽度不自动变化
				"bProcessing" : false,
				"bLengthChange" : false, //关闭按多少条记录显示下拉框
				"bFilter" : false, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
				"bSort" : true, //关闭排序功能
				"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
				"bScrollInfinite" : true,
				"scrollY" : $(document.body).height() - 250,
				"scrollX" : true,
				"orderClasses" : false
			});
			$("#pa0132_table", navTab.getCurrentPanel()).on( 'click', 'tr', function () {
				$("#pa0132_table", navTab.getCurrentPanel()).find('tr').each(
					function(){
						if($(this).hasClass('selected') == true)
							$(this).toggleClass('selected');
					}		
				);
			     $(this).toggleClass('selected');
			});
		});
</script>
<div class="pageHeader">
	<form onsubmit="return  navTabSearch(this)"
		action="/pa/workManagement/detailmonthCountInfoLeft" method="post"
		id="detailmonthCountInfoLeft" name="detailmonthCountInfoLeft">
		<div class="searchBar">
			<table class="searchContent">
				<!-- 姓名 -->
				<tr>
					<td>
						<!-- 工号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
						<input type="text" name="seach_KEY" id="seach_KEY"
							value="${KEY}"
							onkeydown="javascript:if(event.keyCode == 13)magnifier_pa0132_s('onkeyup');" />
					</td>
					<td class="td_type">
						<a class="btnLook" id="magnifier_pa0132"
							onclick="magnifier_pa0132_s()" href="" lookupGroup="person">
						</a>
						<span style="margin-left: 50px;" id="title_pa0132">${LOCAL_TITLE}</span>
					</td>
					<td>
						<input type="hidden" name="empInfoShow" value="${empInfoShow }" />
						${empInfoShow}
					</td>
				</tr>
				<!-- 姓名 -->
				<tr>
					<td>
						<!--工资支付计划--><spring:message code="ess.empInfo.pay_plan" />:
					</td>
					<td>
						<select id="pa0132_PAY_SCHEDULE_NO" name="PAY_SCHEDULE_NO">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
								<c:choose>
									<c:when
										test="${PAY_SCHEDULE_NO == paySchedule.PAY_SCHEDULE_NO }">
										<option value="${paySchedule.PAY_SCHEDULE_NO }"
											selected="selected">
											${paySchedule.PAY_DATE} ${paySchedule.SALARY_DISTIN}
										</option>
									</c:when>
									<c:otherwise>
										<option value="${paySchedule.PAY_SCHEDULE_NO }">
											${paySchedule.PAY_DATE} ${paySchedule.SALARY_DISTIN}
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td>
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							id="pa0132_DEPTNO" selected="${DEPT_NO}" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							id="pa0132_DEPTNO" selected="${DEPT_NO}" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent" align="center">
								<button type="submit">
									<spring:message code="public.title.search" />
								</button>

							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent" id="pa0132_pageContent">
	<div id="detailmonthCountInfoLeft_left" sysLong="printDiv"
		style="float: left; display: block; overflow: auto; border: solid 1px #CCC; line-height: 21px; background: #fff;">
		<div style="font: bold 12px/ 20px arial, sans-serif;">
			Total:${fn:length(detailmonthCountInfoLeft)}
		</div>
		<table id="pa0132_table" class="orderList">
			<thead>
				<tr>
					<th>
						NO
						<!--NO-->
					</th>
					<th>
						<!--工号--><spring:message code="ess.infoApply.EMP_ID" />
					</th>
					<th>
						<!--姓名--><spring:message code="ess.infoApply.NAME" />
					</th>
					<th>
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</th>
					<th>
						<!--职群--><spring:message code="ess.empInfo.zhiqun" />
					</th>
					<th>
						<!--职级--><spring:message code="ess.infoApply.Rank" />
					</th>
					<th>
						<!--职责--><spring:message code="ess.trans.title.dutyName" />
					</th>
					<th>
						<!--税前工资--><spring:message code="pa.viewResultConfirmSonList.SHUIQIANGONGZI.b" />
					</th>
					<th>
						<!--代扣合计--><spring:message code="pa.detailmonthCountInfoLeft.DAIKOUHEJI.b" />
					</th>
					<th>
						<!--实得工资--><spring:message code="pa.viewResultConfirmSonList.SHIDEGONGZI.b" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${detailmonthCountInfoLeft}" var="item"
					varStatus="i">
					<tr style="cursor: pointer;"
						onclick="changeUrlDetail('${item.PAY_SCHEDULE_NO}','${item.PERSON_ID}')">
						<td>
							${i.count}
						</td>
						<td>
							${item.EMPID}
						</td>
						<td>
							${item.LOCAL_NAME}
						</td>
						<td>
							${item.DEPT_NAME}
						</td>
						<td>
							${item.POST_FAMILY_NAME}
						</td>
						<td>
							${item.POST_GRADE_NAME}
						</td>
						<td>
							${item.POSITION_NAME}
						</td>
						<td>
							${item.INCOME_BEFORE_TAX}
						</td>
						<td>
							${item.WITHHOLD_TOTAL}
						</td>
						<td>
							${item.REAL_WAGES}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<%--
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left"
			onclick="hiddenRight('detailmonthCountInfoLeft_uitl','detailmonthCountInfoLeft_left')"></div>
		<div id="layout4" class="w-layout-collapse-right"
			style="display: none;"
			onclick="showIdLeft('detailmonthCountInfoLeft_uitl','detailmonthCountInfoLeft_left')"></div>
		<div id="layout2" class="w-layout-collapse-right"
			onclick="hiddenleft('detailmonthCountInfoLeft_left','detailmonthCountInfoLeft_uitl')"></div>
		<div id="layout3" class="w-layout-collapse-left"
			style="display: none;"
			onclick="showId('detailmonthCountInfoLeft_left')"></div>
	</div>
	--%>
	<div id="detailmonthCountInfoLeft_uitl" style="display: block;">
	</div>
</div>