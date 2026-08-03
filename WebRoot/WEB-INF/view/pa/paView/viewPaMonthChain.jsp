<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

$(document).ready(function() {
	$(".orderList", navTab.getCurrentPanel()).dataTable( {
		"bPaginate" : false, //关闭分页
		"bAutoWidth" : false,//表格宽度不自动变化
		"bProcessing" : true,
		"bLengthChange" : false, //关闭按多少条记录显示下拉框
		"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort" : true, //关闭排序功能
		"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite" : true,
		"scrollY" : $(document.body).height() - 250,
		"scrollX" : false,
		"orderClasses" : false,
		"oLanguage" : {
			//正在加载中......
	   	    "sProcessing": "<spring:message code='ess.message.loading' />",
	        //查询不到相关数据！
	        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
	        //表中无数据存在！
	        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
	        //快速筛选
	        "sSearch": "<spring:message code='ess.message.rapid_screening' />"
		}
	//多语言配置
			});
});

function changeSearcha(obj) {

	var currentIndex = '0';

	var ITEM_ID = obj.name;
	var ITEM_TYPE = obj.type;
	var SELECT_TYPE = obj.title;
	var PAY_DATE = $("#pa1012_PAY_DATE", navTab.getCurrentPanel()).val();
	var href1 = '/pa/workManagement/viewResultConfirmList3Right?currentIndex='
			+ 2 + '&ITEM_ID=' + ITEM_ID + '&PAGE_TYPE=3' + '&ITEM_TYPE='
			+ ITEM_TYPE + '&SELECT_TYPE=' + SELECT_TYPE + '&PAY_DATE=' + PAY_DATE;
	obj.href = href1;
	obj.click;

}

function downloadExl(url) {
	$('#searchViewPaEmpAccountForm').attr("action", url);
	$('#searchViewPaEmpAccountForm').attr("onsubmit", '');
	$('#searchViewPaEmpAccountForm').submit();
	$('#searchViewPaEmpAccountForm').attr("action",
			'/pa/workManagement/viewPaEmpAccount');
	$('#searchViewPaEmpAccountForm').attr("onsubmit",
			'return navTabSearch(this);');
}

function pa1012_Linkage() {
	var obj = document.getElementById("pa1012_SALARY_DISTIN_NO");
	var dis = $(obj).attr("value");
	$("#pa1012_PAY_DATE option").remove();
	$("#pa1012_PAY_DATE_PRO option").remove();

	$("#pa1012_Linkages option").each(
			function() {

				if ($(this).attr("title") == dis) {
					var ddq = "<option value='" + $(this).attr('value')
							+ "' title='" + $(this).attr('title')
							+ "'  onChange='pa1012_LinkageTwo(this)'>"
							+ $(this).html() + "</option>";

					$("#pa1012_PAY_DATE").append(ddq);
					$("#pa1012_PAY_DATE_PRO").append(ddq);

				}
			});

}
function pa1012_LinkageTwo(obj) {

}

$(document).ready(function() {
	pa1012_Linkage();
	seldChange();
});

function seldChange() {
	var date = $("#pa1012_PAY_DATE_SELD").attr("value");
	var date_pro = $("#pa1012_PAY_DATE_PRO_SELD").attr("value");
	var distin = $("#pa1012_SALARY_DISTIN_NO_SELD").attr("value");

	$("#pa1012_PAY_DATE option").each(function() {
		if ($(this).attr("title") == distin && $(this).attr("value") == date) {
			$(this).attr("selected", "selected")
		}
	});

	$("#pa1012_PAY_DATE_PRO option").each(
			function() {
				if ($(this).attr("title") == distin
						&& $(this).attr("value") == date_pro) {
					$(this).attr("selected", "selected")
				}
			});

}
</script>
<div class="pageHeader">
	<form id="searchViewPaEmpAccountForm"
		onsubmit="return navTabSearch(this);"
		action="/pa/paView/viewPaMonthChain" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<!-- 支付计划 -->
				<tr>
					<td>
						<!--工资区分 --><spring:message code="pa.monthPersonCountInfoList.GONGZIQUFEN.b" />
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="SALARY_DISTIN_NO"
							selected="${SALARY_DISTIN_NO}" parentNo="14013797"
							id="pa1012_SALARY_DISTIN_NO" cnpyID="${LoginUser.cpnyId}"
							onChangeName="pa1012_Linkage()" />
					</td>

					<td>
						<!--支付日期 --><spring:message code="display.pa.ecc.paydate" />
					</td>
					<td>

						<select id="pa1012_PAY_DATE_PRO" name="PAY_DATE_PRO">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
								<c:choose>
									<c:when test="${PAY_DATE_PRO == paySchedule.PAY_DATE }">
										<option value="${paySchedule.PAY_DATE }" selected="selected">
											${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
										</option>
									</c:when>
									<c:otherwise>
										<option value="${paySchedule.PAY_DATE }">
											${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						---
						<select id="pa1012_PAY_DATE" name="PAY_DATE"
							onchange="pa1012_LinkageTwo(this)">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
								<c:choose>
									<c:when test="${PAY_DATE == paySchedule.PAY_DATE }">
										<option value="${paySchedule.PAY_DATE }" selected="selected">
											${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
										</option>
									</c:when>
									<c:otherwise>
										<option value="${paySchedule.PAY_DATE}">
											${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN}
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>

					</td>

				</tr>



				<select id="pa1012_Linkages" style="display: none">
					<c:forEach items="${paPayScheduleList}" var="paySchedule"
						varStatus="i">

						<option value="${paySchedule.PAY_DATE }"
							title="${paySchedule.SALARY_DISTIN_NO}">
							${paySchedule.PAY_DATE }  
						</option>

					</c:forEach>
				</select>

				<input type="hidden" id="pa1012_PAY_DATE_SELD" value="${PAY_DATE}" />
				<input type="hidden" id="pa1012_PAY_DATE_PRO_SELD"
					value="${PAY_DATE_PRO}" />
				<input type="hidden" id="pa1012_SALARY_DISTIN_NO_SELD"
					value="${SALARY_DISTIN_NO}" />


				<!-- 支付计划 -->
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" class="button">
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>
							</div>
						</div>
					</li>


					<!-- <li>
						<a class="buttonActive"
							onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=11')"
							href="#"> <span>导出到Excel</span> </a>
					</li> -->
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent" width="100%">
	Total:${fn:length(getPaMonthList)}
	<table class="orderList list" width="100%">
		<thead>
			<tr>
				<th rowspan="2">
					No.
				</th>
				<th rowspan="2">
					<!--项目 --><spring:message code="ess.empInfo.project" />
				</th>
				<th colspan="3">
					<!--员工人数 --><spring:message code="pa.viewPaMonthChain.YUANGONGRENSHU.b" />
				</th>
				<th colspan="3">
					<!--金额 --><spring:message code="ess.empInfo.amount_of_money" />
				</th>
				<th colspan="3">
					<!--平均 --><spring:message code="pa.viewPaMonthChain.PINGJUN.b" />
				</th>
				<th rowspan="2">
					<!--金额增加 --><spring:message code="pa.viewPaMonthChain.JINEZENGJIA.b" />
				</th>
				<th rowspan="2">
					<!--金额减少 --><spring:message code="pa.viewPaMonthChain.JINEJIANSHAO.b" />
				</th>
				<th rowspan="2">
					<!--人员增加 --><spring:message code="pa.viewPaMonthChain.RENYUANZENGJIA.b" />
				</th>
				<th rowspan="2">
					<!--人员减少 --><spring:message code="pa.viewPaMonthChain.RENYUANJIANSHAO.b" />
				</th>
			</tr>

			<tr>


				<th>
					<!--前月 --><spring:message code="display.emp.ben.or.benhs68" />
				</th>
				<th>
					<!--当月 --><spring:message code="pa.monthPersonCountInfoList.DANGYUE.b" />
				</th>
				<th>
					<!--增减 --><spring:message code="org.title.ADDORDELETTE" />
				</th>
				<th>
					<!--前月 --><spring:message code="display.emp.ben.or.benhs68" />
				</th>
				<th>
					<!--当月 --><spring:message code="pa.monthPersonCountInfoList.DANGYUE.b" />
				</th>
				<th>
					<!--增减 --><spring:message code="org.title.ADDORDELETTE" />
				</th>
				<th>
					<!--前月 --><spring:message code="display.emp.ben.or.benhs68" />
				</th>
				<th>
					<!--当月 --><spring:message code="pa.monthPersonCountInfoList.DANGYUE.b" />
				</th>
				<th>
					<!--增减 --><spring:message code="org.title.ADDORDELETTE" />
				</th>


			</tr>
		</thead>
		<tbody>

			<c:forEach items="${getPaMonthList}" var="item" varStatus="i">
				<tr>
					<td>
						${i.count}
					</td>
					<td>
						${item.ITEM_NAME}
					</td>
					<td>
						<a mask="true" width="1100" height="400" type="${item.ITEM_TYPE}"
							title="PERSON_NUM_PRO" style="color: blue; cursor: pointer;"
							target="dialog" name="${item.ITEM_ID}"
							onclick="changeSearcha(this)">${item.PERSON_NUM_PRO}</a>
					</td>
					<td>
						<a mask="true" width="1100" height="400" type="${item.ITEM_TYPE}"
							title="PERSON_NUM" style="color: blue; cursor: pointer;"
							target="dialog" name="${item.ITEM_ID}"
							onclick="changeSearcha(this)"> ${item.PERSON_NUM}</a>
					</td>
					<td>
						${item.PERSON_NUM_DIF}
					</td>

					<td>
						<fmt:formatNumber value="${item.COUNT_NUM_PRO}" pattern="#,##0" />
					</td>
					<td>
						<fmt:formatNumber value="${item.COUNT_NUM}" pattern="#,##0" />
					</td>
					<td>
						<fmt:formatNumber value="${item.COUNT_NUM_DIF}" pattern="#,##0" />
					</td>
					<td>

						<fmt:formatNumber value="${item.PERSON_NUM_AVG_PRO}"
							pattern="#,##0" />
					</td>
					<td>

						<fmt:formatNumber value="${item.PERSON_NUM_AVG}"
							pattern="#,##0" />

					</td>
					<td>

						<fmt:formatNumber value="${item.PERSON_NUM_AVG_DIF}"
							pattern="#,##0" />

					</td>
					<td>
						<a mask="true" width="1100" height="400" title="COUNT_UP"
							style="color: blue; cursor: pointer;" target="dialog"
							type="${item.ITEM_TYPE}" name="${item.ITEM_ID}"
							onclick="changeSearcha(this)">${item.COUNT_UP}</a>
					</td>
					<td>
						<a mask="true" width="1100" height="400" title="COUNT_LOW"
							style="color: blue; cursor: pointer;" target="dialog"
							type="${item.ITEM_TYPE}" name="${item.ITEM_ID}"
							onclick="changeSearcha(this)">${item.COUNT_LOW}</a>
					</td>
					<td>
						<a mask="true" width="1100" height="400" title="PERSON_UP"
							style="color: blue; cursor: pointer;" target="dialog"
							type="${item.ITEM_TYPE}" name="${item.ITEM_ID}"
							onclick="changeSearcha(this)">${item.PERSON_UP}</a>
					</td>
					<td>
						<a mask="true" width="1100" heigh="400" title="PERSON_LOW"
							style="color: blue; cursor: pointer;" target="dialog"
							type="${item.ITEM_TYPE}" name="${item.ITEM_ID}"
							onclick="changeSearcha(this)">${item.PERSON_LOW}</a>
					</td>

				</tr>

			</c:forEach>

			<c:set var="Y" value="${''}">
			</c:set>
			<c:set var="N" value="${''}">
			</c:set>

		</tbody>

	</table>

</div>