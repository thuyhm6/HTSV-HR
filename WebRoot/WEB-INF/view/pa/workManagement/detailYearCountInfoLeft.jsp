<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function magnifier_pa0133_s(flag) {

	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));
	var PAY_DATE_PRO = $('#pa0133_PAY_DATE_PRO', navTab.getCurrentPanel())
			.val();
	var PAY_DATE = $('#pa0133_PAY_DATE', navTab.getCurrentPanel()).val();

	var refreshUrl = '/pa/workManagement/detailYearCountInfoLeft?PAY_DATE='
			+ PAY_DATE
			+ '^PAY_DATE_PRO='
			+ PAY_DATE_PRO ;
	var refreshMenuCode = 'pa0133';

	var refreshMenuName = encodeURI(encodeURI('<spring:message code="pa.viewPaMain.NIANGONGZIMINGXI.C" />'));//年工资明细
	//$('#searchPop',navTab.getCurrent())
	$("#magnifier_pa0133", navTab.getCurrentPanel())
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
		$("#magnifier_pa0133", navTab.getCurrentPanel()).click();
}

function changeUrlDetail(PAY_DATE_VAL, PERSON_ID) {
	openOnRight('/pa/workManagement/detailYearCountInfoRight?pFrom=year&PAY_DATE='
			+ PAY_DATE_VAL + '&PERSON_ID=' + PERSON_ID 
			+ '&START_MONTH='+$("#pa0133_PAY_DATE_PRO", navTab.getCurrentPanel()).val()
			+'&END_MONTH='+$("#pa0133_PAY_DATE", navTab.getCurrentPanel()).val(), 'detailYearCountInfoLeft_uitl');
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
		//您的电脑没有安装Microsoft Excel软件！
		alert("<spring:message code='pa.detailPersonCountInfoLeft.NINDEDIANNAOMEIYOUANZHUANGMERUANJIAN.b' />");
		return false
	}
	ExWBk.worksheets(1).Paste;
}

$(document).ready(
		function() {
			$("#pa0133_pageContent").css("height",
					$(document.body).height() - 200);
			$("#pa0133_pageContent")
					.css("width", $(document.body).width() - 20);
			$("#detailYearCountInfoLeft_left").css("width","70%");
			$("#pa0133_table").css("width",
					$("#detailYearCountInfoLeft_left").width() - 10);

			$("#pa0133_table", navTab.getCurrentPanel()).dataTable( {
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
			$("#pa0133_table", navTab.getCurrentPanel()).on( 'click', 'tr', function () {
				$("#pa0133_table", navTab.getCurrentPanel()).find('tr').each(
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
	<form class="j-ajax" onsubmit="return  navTabSearch(this)"
		action="/pa/workManagement/detailYearCountInfoLeft" method="post"
		id="detailYearCountInfoLeft" name="detailYearCountInfoLeft">
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
							value="${personInfo.LOCAL_NAME}"
							onkeydown="javascript:if(event.keyCode == 13)magnifier_pa0133_s('onkeyup');" />
						<input type="hidden" name="PERSON_ID"
							value="${personInfo.PERSON_ID}" />
					</td>
					<td>
						<a class="btnLook" id="magnifier_pa0133"
							onclick="magnifier_pa0133_s()" href="" lookupGroup="person">
						</a>
					</td>
					<td>
						<input type="hidden" name="empInfoShow" value="${empInfoShow }" />
						${empInfoShow }
					</td>
				</tr>
				<!-- 姓名 -->
				<tr>
					<td>
						<!-- 工资期间 --><spring:message code="pa.detailYearCountInfoLeft.GONGZIQIJIAN.b" />
					</td>
					<td>
						<input id="pa0133_PAY_DATE_PRO" type="text" name="PAY_DATE_PRO"
							class="Wdate required" onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})"
							value="${PAY_DATE_PRO}" readonly="true" />

						<input id="pa0133_PAY_DATE" type="text" name="PAY_DATE"
							class="Wdate required" onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})"
							value="${PAY_DATE}" readonly="true" width="50PX" />
					</td>
					<%-- <td>
						工资区分
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID id="pa0133_SALARY_DISTIN_NO"
							name="SALARY_DISTIN_NO" parentNo="14013797"
							cnpyID="${LoginUser.cpnyId}" selected="${SALARY_DISTIN_NO}" />
					</td> --%>
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

					<!-- <li>
						<div>
							<div align="center">
								<a class="button" onclick="exportExcel()"><span>excle导出</span>
								</a>
							</div>
						</div>
					</li> -->
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent" id="pa0133_pageContent">
	<div id="detailYearCountInfoLeft_left" sysLong="printDiv"
		style="float: left; display: block; overflow: auto; border: solid 1px #CCC; line-height: 21px; background: #fff;">
		<div style="font: bold 12px/ 20px arial, sans-serif;">
			Total:${fn:length(detailYearCountInfoLeft)}
		</div>
		<table id="pa0133_table" class="orderList"  >
			<thead>
				<tr>
					<th>
						NO
						<!--NO-->
					</th>
					<th>
						<!--支付日期--><spring:message code="display.pa.ecc.paydate" />
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
				<c:forEach items="${detailYearCountInfoLeft}" var="item" varStatus="i">
					<tr style="cursor: pointer;"
						onclick="changeUrlDetail('${item.PAY_DATE}','${personInfo.PERSON_ID}')">

						<td>
							${i.count}
						</td>
						<td>
							${item.PAY_DATE_FORMAT}
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
	<%--<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left"
			onclick="hiddenRight('detailYearCountInfoLeft_uitl','detailYearCountInfoLeft_left')"></div>
		<div id="layout4" class="w-layout-collapse-right"
			style="display: none;"
			onclick="showIdLeft('detailYearCountInfoLeft_uitl','detailYearCountInfoLeft_left')"></div>
		<div id="layout2" class="w-layout-collapse-right"
			onclick="hiddenleft('detailYearCountInfoLeft_left','detailYearCountInfoLeft_uitl')"></div>
		<div id="layout3" class="w-layout-collapse-left"
			style="display: none;"
			onclick="showId('detailYearCountInfoLeft_left')"></div>
	</div>
	--%>
	<div id="detailYearCountInfoLeft_uitl" style="display: block;">
	</div>
</div>