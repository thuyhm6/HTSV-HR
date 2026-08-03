<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

$(document).ready(
		function() {

			$("#detailPersonCountInfoLeft_left").css("height",
					$(document.body).height() - 180);
			$(".pa1016_table2", navTab.getCurrentPanel()).dataTable( {
				"bPaginate" : false, //关闭分页
				"bAutoWidth" : false,//表格宽度不自动变化
				"bProcessing" : true,
				"bLengthChange" : false, //关闭按多少条记录显示下拉框
				"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
				"bSort" : true, //关闭排序功能
				"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
				"bScrollInfinite" : true,
				"scrollY" : $(document.body).height() - 310,
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
			$("#pa1016_table2 tbody", navTab.getCurrentPanel()).on(
					'click',
					'tr',
					function() {
						$("#pa1016_table2 tbody tr", navTab.getCurrentPanel()).each(function () {
                            $(this).removeClass('selected');
                        });
						$(this).toggleClass('selected');
					});
		});

function magnifier_detaiPersonCountInfoLeft(flag) {

	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));
	var scheduleNo = $('#PAY_SCHEDULE_NO', navTab.getCurrentPanel()).val();
	var refreshUrl = '/pa/workManagement/detailPersonCountInfoLeft?PAY_SCHEDULE_NO=' + scheduleNo;
	var refreshMenuCode = 'pa1016';
	var refreshMenuName = encodeURI(encodeURI('<spring:message code="pa.detailPersonCountInfoLeft.GERENBIEHEDUI.b" />'));//个人别核对
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

function changeUrlDetail(PAY_SCHEDULE_NO, PERSON_ID) {
	//ITEM_TYPE = $("#detailPersonCountInfoLeft_ITEM_TYPE").attr("value");

	openOnRight(
			'/pa/workManagement/detailPersonCountInfoRight?PAY_SCHEDULE_NO='
					+ PAY_SCHEDULE_NO + '&PERSON_ID=' + PERSON_ID,
			'detailPersonCountInfoLeft_uitl');
}
</script>

<script language=javascript>
function exportExcel() {

	window.clipboardData.setData("Text", document.all('table2').outerHTML);
	try {
		var ExApp = new ActiveXObject("Excel.Application");
		var ExWBk = ExApp.workbooks.add();
		var ExWSh = ExWBk.worksheets(1);
		ExApp.DisplayAlerts = false;
		ExApp.visible = true;
	} catch (e) {
		alert("<spring:message code='pa.detailPersonCountInfoLeft.NINDEDIANNAOMEIYOUANZHUANGMERUANJIAN.b' />");//您的电脑没有安装Microsoft Excel软件！
		return false
	}
	ExWBk.worksheets(1).Paste;
}</script>
<div class="pageHeader">
	<form class="j-ajax" onsubmit="return  navTabSearch(this)"
		action="/pa/workManagement/detailPersonCountInfoLeft" method="post"
		id="detailPersonCountInfoLeft" name="detailPersonCountInfoLeft">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 工号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
						<input type="text" name="seach_KEY" id="seach_KEY"
							value="${personInfo.LOCAL_NAME}"
							onkeydown="javascript:if(event.keyCode == 13)magnifier_detaiPersonCountInfoLeft('onkeyup');" />
						<input type="hidden" name="PERSON_ID"
							value="${personInfo.PERSON_ID}" />
					</td>
					<td class="td_type">
						<a class="btnLook" id="magnifier_detaiPersonCountInfo"
							onclick="magnifier_detaiPersonCountInfoLeft('ss')" href=""
							lookupGroup="person"> </a>
						<span style="margin-left: 50px;"
							id="title_detaiPersonCountInfoLeft">${LOCAL_TITLE}</span>
					</td>
					<td>
						<input type="hidden" name="empInfoShow" value="${empInfoShow }" />
						${empInfoShow }
					</td>
					<td>
						<!--工资支付计划--><spring:message code="ess.empInfo.pay_plan" />:
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
					<td>
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="all" id="pa1016_seachDept" selected="${DEPT_NO}" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="all" id="pa1016_seachDept" selected="${DEPT_NO}" />
					</td>
					<c:if test="${CPNY_ID eq 'SPC_NJ'}">
						<td><spring:message code="hrm.empinfo.POST_FAMILY"/><!-- 职群 --></td>
						<td><ait:selectCodeMulti id="seach_POST_FAMILY_Multi"
								name="seach_POST_FAMILY_NAME" parentNo="14015812"
								selected="${POST_FAMILY_Multi}" selectedNm="${POST_FAMILY_NAME}" /></td>
					</c:if>
					<%-- <td>
						项目区分
					</td>
					<td>
						<select name="ITEM_TYPE" id="detailPersonCountInfoLeft_ITEM_TYPE">
							<option value="1"
								<c:if test="${ITEM_TYPE eq '1'}">selected</c:if>>
								给予项目
							</option>
							<option value="2"
								<c:if test="${ITEM_TYPE eq '2'}">selected</c:if>>
								扣除项目
							</option>
							<option value="3"
								<c:if test="${ITEM_TYPE eq '3'}">selected</c:if>>
								保险项目
							</option>
						</select>
					</td> --%>
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
</div>
<div class="pageContent" id="pa1016_pageContent">
	<div id="detailPersonCountInfoLeft_left" sysLong="printDiv"
		style="float: left; display: block; overflow: auto; width: 25%; height: 300px; border: solid 1px #CCC; background: #fff;">
		<div class="user_table"
			style="font: bold 12px/ 20px arial, sans-serif;">
			Total:${fn:length(detailPersonCountInfoLeft)}
		</div>
		<table id="pa1016_table2" class="orderList" width="100%;">
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
						<!--实发工资--><spring:message code="ess.viewpersonalpainfo.shifagongzi" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${detailPersonCountInfoLeft}" var="item"
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
							${item.REAL_WAGES}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="detailPersonCountInfoLeft_uitl" style="display: block;">
	</div>
</div>