<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<script type="text/javascript">

$(document).ready(
		function() {

			$("#detailItemDifCountInfo_Table").attr("width",
					$("#pa1018_pageContent").width());

			$("#detailItemDifCountInfo_Table", navTab.getCurrentPanel())
					.dataTable( {
						"bPaginate" : false, //关闭分页
						"bAutoWidth" : false,//表格宽度不自动变化
						"bProcessing" : true,
						"bLengthChange" : false, //关闭按多少条记录显示下拉框
						"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
						"bSort" : true, //关闭排序功能
						"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
						"bScrollInfinite" : true,
						"scrollY" : $(document.body).height() - 300,
						"scrollX" : true,
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

function detailItemDifCountInfo_changeSelect(obj) {

	var ITEM_TYPE = $("#detailItemDifCountInfo_ITEM_TYPE").attr("value");

	$("#detailItemDifCountInfo_ITEM_ID option").remove(); //清除下拉列表内容
	var selectId = $("#pa1018_selectId").attr("value"); //获取item_id

	$("#detailItemDifCountInfo_ITEM_ID")
			.append("<option value=''><spring:message code='sys.affirm.title.choose' /></option>");//请选择

	$('input[name="ITEM_ID_NAME"]').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数  
				if ($(this).attr("title") == ITEM_TYPE) {
					//平日加班费,周末加班费,节日加班费
					if($(this).attr("alt")!='<spring:message code="pa.viewPaResultList.PINGRIJIABANFEI.C" />'&&$(this).attr("alt")!='<spring:message code="pa.viewPaResultList.ZHOUMOJIABANFEI.C" />'&&$(this).attr("alt")!='<spring:message code="pa.viewPaResultList.JIERIJIABANFEI.C" />'){
					var selectFlge = ""; //每次都重置
					if (selectId == $(this).attr("value")) {
						selectFlge = "selected"; //如果ID相同则默认选中
					}

					$("#detailItemDifCountInfo_ITEM_ID").append(
							"<option " + selectFlge + " value=\""
									+ $(this).attr("value") + "\">"
									+ $(this).attr("alt") + "</option>");
					}
				}
			});
}

$(function() {

	detailItemDifCountInfo_changeSelect($("#detailItemDifCountInfo_ITEM_TYPE"));
});

function downloadExl(url) {
	$('#detailItemDifCountInfo').attr("action", url);
	$('#detailItemDifCountInfo').attr("onsubmit", '');
	$('#detailItemDifCountInfo').submit();
	$('#detailItemDifCountInfo').attr("action",
			'/pa/workManagement/detailItemDifCountInfo');
	$('#detailItemDifCountInfo').attr("onsubmit", 'return navTabSearch(this);');
}

function pa1018_Linkage() {
	var obj = document.getElementById("pa1018_SALARY_DISTIN_NO");
	var dis = $(obj).attr("value");
	$("#pa1018_PAY_DATE option").remove();
	$("#pa1018_PAY_DATE_PRO option").remove();

	$("#pa1018_Linkages option").each(
			function() {

				if ($(this).attr("title") == dis) {
					var ddq = "<option value='" + $(this).attr('value')
							+ "' title='" + $(this).attr('title')
							+ "'  onChange='pa1018_LinkageTwo(this)'>"
							+ $(this).html() + "</option>";

					$("#pa1018_PAY_DATE").append(ddq);
					$("#pa1018_PAY_DATE_PRO").append(ddq);

				}
			});

}
function pa1018_LinkageTwo(obj) {

}

$(document).ready(function() {
	//pa1018_Linkage();
	seldChange();
});

function seldChange() {
	var date = $("#pa1018_PAY_DATE_SELD").attr("value");
	var date_pro = $("#pa1018_PAY_DATE_PRO_SELD").attr("value");
	var distin = $("#pa1018_SALARY_DISTIN_NO_SELD").attr("value");

	$("#pa1018_PAY_DATE option").each(function() {
		if ($(this).attr("title") == distin && $(this).attr("value") == date) {
			$(this).attr("selected", "selected")
		}
	});

	$("#pa1018_PAY_DATE_PRO option").each(
			function() {
				if ($(this).attr("title") == distin
						&& $(this).attr("value") == date_pro) {
					$(this).attr("selected", "selected")
				}
			});

}
</script>

<div style="display: none">
	<c:forEach items="${itemValueInfo}" var="item">
		<input type="hidden" value="${item.ITEM_ID}" name="ITEM_ID_NAME"
			alt="${item.ITEM_NAME}" title="${item.ITEM_TYPE}" />
	</c:forEach>
</div>


<div class="pageHeader">

	<form class="j-ajax" onsubmit="return  navTabSearch(this)"
		action="/pa/workManagement/detailItemDifCountInfo" method="post"
		id="detailItemDifCountInfo" name="detailItemDifCountInfo">
		<input type="hidden" name="PAGE_TYPE" value='1'>
		<div class="searchBar">
			<table class="searchContent">

				<input type="hidden" id="pa1018_selectId" value="${ITEM_ID}" />

				<!-- 支付计划 -->
				<tr>
					<td>
						<!--工资区分--><spring:message code="pa.monthPersonCountInfoList.GONGZIQUFEN.b" />
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="SALARY_DISTIN_NO"
							selected="${SALARY_DISTIN_NO}" parentNo="14013797"
							id="pa1018_SALARY_DISTIN_NO" cnpyID="${LoginUser.cpnyId}"
							/>
					</td>

					<td>
						<!--支付日期--><spring:message code="display.pa.ecc.paydate" />
					</td>
					<td>
						<select id="pa1018_PAY_DATE" name="PAY_DATE" >
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
								<c:choose>
									<c:when test="${PAY_DATE == paySchedule.PAY_DATE }">
										<option value="${paySchedule.PAY_DATE }" selected="selected">
											${paySchedule.PAY_DATE }
										</option>
									</c:when>
									<c:otherwise>
										<option value="${paySchedule.PAY_DATE}">
											${paySchedule.PAY_DATE }
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>

					</td>

				</tr>



				<select id="pa1018_Linkages" style="display: none">
					<c:forEach items="${paPayScheduleList}" var="paySchedule"
						varStatus="i">

						<option value="${paySchedule.PAY_DATE }"
							title="${paySchedule.SALARY_DISTIN_NO}">
							${paySchedule.PAY_DATE } 
						</option>

					</c:forEach>
				</select>

				<input type="hidden" id="pa1018_PAY_DATE_SELD" value="${PAY_DATE}" />
				<input type="hidden" id="pa1018_PAY_DATE_PRO_SELD"
					value="${PAY_DATE_PRO}" />
				<input type="hidden" id="pa1018_SALARY_DISTIN_NO_SELD"
					value="${SALARY_DISTIN_NO}" />


				<!-- 支付计划 -->
				<tr>
					<td>
						<!--项目区分--><spring:message code="liang.public.title.ItemDistinguish" />
					</td>
					<td>

						<select name="ITEM_TYPE" id="detailItemDifCountInfo_ITEM_TYPE"
							onchange="detailItemDifCountInfo_changeSelect(this)">
							<option value="1"
								<c:if test="${ITEM_TYPE eq '1'}">selected</c:if>>
								<!--给予项目--><spring:message code="pa.detailPersonCountInfo.JIYUXIANGMU.b" />
							</option>
							<option value="2"
								<c:if test="${ITEM_TYPE eq '2'}">selected</c:if>>
								<!--扣除项目--><spring:message code="pa.detailPersonCountInfo.KOUCHUXIANGMU.b" />
							</option>
							<option value="3"
								<c:if test="${ITEM_TYPE eq '3'}">selected</c:if>>
								<!--保险项目--><spring:message code="pa.detailItemCountInfo.BAOXIANXIANGMU.b" />
							</option>
						</select>

					</td>

					<td>
						<!--详细区分--><spring:message code="pa.detailItemCountInfo.XIANGXIQUFEN.b" />
					</td>
					<td>
						<select name="ITEM_ID" id="detailItemDifCountInfo_ITEM_ID">

						</select>

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

					<li>
						<a class="buttonActive"
							onclick="downloadExl('/pa/workManagement/detailItemDifCountInfoExport')"
							href="#"> <span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel" /></span> </a>
					</li>
				</ul>
			</div>

		</div>
	</form>
</div>




<h2>
	Total:${fn:length(detailItemDifCountInfo)}
</h2>
<div class="pageContent" id="pa1018_pageContent">
	<table id="detailItemDifCountInfo_Table" class="orderList">
		<thead>
			<tr>
				<th>
					No.
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
					<!--职责--><spring:message code="ess.infoApply.title.dutyName" />
				</th>
				<th>
					<!--入职日期--><spring:message code="ess.empInfo.entry_date" />
				</th>
				<th>
					<!--离职日期--><spring:message code="ess.empInfo.leaveDate" />
				</th>
				<th>
					<!--项目名称--><spring:message code="pa.insurance.title.projectName" />
				</th>
				<th>
					<!--以前金额--><spring:message code="pa.detailItemDifCountInfo.YIQIANJINE.b" />
				</th>
				<th>
					<!--当月金额--><spring:message code="pa.detailItemDifCountInfo.DANGYUJINE.b" />
				</th>
			</tr>
		</thead>


		<c:forEach items="${detailItemDifCountInfo}" var="item" varStatus="i">

			<tr>
				<td class="td_type">
					${i.count}
				</td>
				<td class="td_type">
					${item.EMPID}
				</td>
				<td class="td_type">
					${item.LOCAL_NAME}
				</td>
				<td class="td_type">
					${item.DEPT_NAME}
				</td>
				<td class="td_type">
					${item.POST_FAMILY_NAME}
				</td>
				<td class="td_type">
					${item.POST_GRADE_NAME}
				</td>
				<td class="td_type">
					${item.POSITION_NAME}
				</td>
				<td class="td_type">
					${item.DATE_STARTED}
				</td>
				<td class="td_type">
					${item.DATE_LEFT}
				</td>
				<td class="td_type">
					${item.ITEM_NAME}
				</td>
				<td class="td_type">
					${item.MONTH_PRO}
				</td>
				<td class="td_type">
					${item.MONTH_NOW}
				</td>
			</tr>
		</c:forEach>
	</table>

</div>


