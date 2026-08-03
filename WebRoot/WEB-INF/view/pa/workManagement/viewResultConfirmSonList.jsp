<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function() {

	$("#viewResultConfirmList_currentIndex").val('${currentIndex}');
});
</script>
</head>
<c:if test="${currentIndex eq '0'}">
	<script src="/resources/js/highcharts/highcharts.js"
		type="text/javascript">
</script>
	<script>
$(function() {
	$("#pa1019_container").css("width", $(document.body).width() / 2 - 30);
	$("#pa1019_container2").css("width", $(document.body).width() / 2 - 30);
	$("#pa1019_container").css("height", $(document.body).height() - 300);
	$("#pa1019_container2").css("height", $(document.body).height() - 300);
	//日期
	var NOW_DATE = $("#pa1019_NOW_DATE").attr("value");
	var PRO_DATE = $("#pa1019_PRO_DATE").attr("value");
	//////////////人数
	var PERSON_NUM = Number($("#pa1019_PERSON_NUM").attr("value"));
	var PERSON_NUM_PRO = Number($("#pa1019_PERSON_NUM_PRO").attr("value"));
	//////////////工资总额
	var BASE_ALL = Number($("#pa1019_BASE_ALL").attr("value"));
	var BASE_ALL_PRO = Number($("#pa1019_BASE_ALL_PRO").attr("value"));
	//////////////扣除
	var WITHHOLD_TOTAL = Number($("#pa1019_WITHHOLD_TOTAL").attr("value"));
	var WITHHOLD_TOTAL_PRO = Number($("#pa1019_WITHHOLD_TOTAL_PRO").attr(
			"value"));
	//////////////实得
	var NET_PAY = Number($("#pa1019_NET_PAY").attr("value"));
	var NET_PAY_PRO = Number($("#pa1019_NET_PAY_PRO").attr("value"));

	$('#pa1019_container').highcharts( { //图表展示容器，与div的id保持一致
				chart : {
					type : 'line' //指定图表的类型，默认是折线图（line）
			},
			title : {
				//人数
				text : '<spring:message code="pa.title.pa.excel.thecountnumberofemployee" />' //指定图表标题
			},
			xAxis : {
				categories : [  PRO_DATE, NOW_DATE ]
			//指定x轴分组
				},
				yAxis : {
					title : {
					    //人
						text : '<spring:message code="pa.viewResultConfirmSonList.REN.b" />' //指定y轴的标题
			}
		},
		series : [ { //指定数据列
			        //员工
					name : '<spring:message code="sys.rights.title.employee" />', //数据列名
					data : [  PERSON_NUM_PRO, PERSON_NUM ]
				//数据
				} ]
			});

	$('#pa1019_container2').highcharts( { //图表展示容器，与div的id保持一致
				chart : {
					type : 'line' //指定图表的类型，默认是折线图（line）
				},
				title : {//金额
					text : '<spring:message code="ess.empInfo.amount_of_money" />' //指定图表标题
				},
				xAxis : {
					categories : [  PRO_DATE, NOW_DATE ]
				//指定x轴分组
				},
				yAxis : {
					title : {
						text : 'VND' //指定y轴的标题RMB
					}
				},
				series : [
						{ //指定数据列
							//工资总额
							name : '<spring:message code="pa.viewResultConfirmSonList.GONGZIZONGE.b" />', //数据列名
							data : [  BASE_ALL_PRO, BASE_ALL ]
						//数据
						},
						{
							name : '<spring:message code="pa.viewResultConfirmSonList.KOUCHUHEJI.b" />',//扣除合计
							data : [ 
									WITHHOLD_TOTAL_PRO, WITHHOLD_TOTAL ]
						}, {
							name : '<spring:message code="pa.viewResultConfirmSonList.SHIDEGONGZI.b" />',//实得工资
							data : [  NET_PAY_PRO, NET_PAY ]
						} ]
			});

	$(document.body).find("text").each(function() {

		if ($(this).text() == 'Highcharts.com') {
			$(this).text('')

		}
	});
});
</script>

	<div class="pageContent">

		<table class="user_table" width="100%">

			<tr>
				<td class="td_title" style="text-align: center;" colspan="4">
					<!--前月--><spring:message code="display.emp.ben.or.benhs68" />
				</td>
				<td class="td_title" style="text-align: center;" colspan="4">
					<!--当月--><spring:message code="pa.monthPersonCountInfoList.DANGYUE.b" />
				</td>
				<td class="td_title" style="text-align: center;" colspan="4">
					<!--增减--><spring:message code="org.title.ADDORDELETTE" />
				</td>
			</tr>
			<tr>
				<td class="td_title" style="text-align: center;">
					<!--对象人员--><spring:message code="pa.viewResultConfirmSonList.DUIXIANGRENYUAN.b" />
				</td>
				<td class="td_title" style="text-align: center;">
					<!--工资总额--><spring:message code="pa.viewResultConfirmSonList.GONGZIZONGE.b" />
				</td>
				<td class="td_title" style="text-align: center;">
					<!--扣除合计--><spring:message code="pa.viewResultConfirmSonList.KOUCHUHEJI.b" />
				</td>
				<td class="td_title" style="text-align: center;">
					<!--实得工资--><spring:message code="pa.viewResultConfirmSonList.SHIDEGONGZI.b" />
				</td>
				<td class="td_title" style="text-align: center;">
					<!--对象人员--><spring:message code="pa.viewResultConfirmSonList.DUIXIANGRENYUAN.b" />
				</td>
				<td class="td_title" style="text-align: center;">
					<!--工资总额--><spring:message code="pa.viewResultConfirmSonList.GONGZIZONGE.b" />
				</td>
				<td class="td_title" style="text-align: center;">
					<!--扣除合计--><spring:message code="pa.viewResultConfirmSonList.KOUCHUHEJI.b" />
				</td>
				<td class="td_title" style="text-align: center;">
					<!--实得工资--><spring:message code="pa.viewResultConfirmSonList.SHIDEGONGZI.b" />
				</td>

				<td class="td_title" style="text-align: center;">
					<!--对象人员--><spring:message code="pa.viewResultConfirmSonList.DUIXIANGRENYUAN.b" />
				</td>
				<td class="td_title" style="text-align: center;">
					<!--工资总额--><spring:message code="pa.viewResultConfirmSonList.GONGZIZONGE.b" />
				</td>
				<td class="td_title" style="text-align: center;">
					<!--扣除合计--><spring:message code="pa.viewResultConfirmSonList.KOUCHUHEJI.b" />
				</td>
				<td class="td_title" style="text-align: center;">
					<!--实得工资--><spring:message code="pa.viewResultConfirmSonList.SHIDEGONGZI.b" />
				</td>
				<c:forEach items="${viewResultConfirmSonList0}" var="item">
					<tr>
						<td class="td_type">
							${item.PERSON_NUM_PRO}
						</td>
						<td class="td_type">
							<fmt:formatNumber value="${item.SALARY_TOTAL_PRO}"
								pattern="#,##0" />
						</td>
						<td class="td_type">
							<fmt:formatNumber value="${item.WITHHOLD_TOTAL_PRO}"
								pattern="#,##0" />
						</td>
						<td class="td_type">
							<fmt:formatNumber value="${item.NET_PAY_PRO}"
								pattern="#,##0" />
						</td>
						<td class="td_type">
							${item.PERSON_NUM_CUR}
						</td>
						<td class="td_type">
							<fmt:formatNumber value="${item.SALARY_TOTAL_CUR}" pattern="#,##0" />
						</td>
						<td class="td_type">
							<fmt:formatNumber value="${item.WITHHOLD_TOTAL_CUR}"
								pattern="#,##0" />
						</td>
						<td class="td_type">
							<fmt:formatNumber value="${item.NET_PAY_CUR}" pattern="#,##0" />
						</td>
						<td class="td_type">
							${item.PERSON_NUM_DIF}
						</td>
						<td class="td_type">
							<fmt:formatNumber value="${item.SALARY_TOTAL_DIF}" pattern="#,##0" />
						</td>
						<td class="td_type">
							<fmt:formatNumber value="${item.WITHHOLD_TOTAL_DIF}"
								pattern="#,##0" />
						</td>
						<td class="td_type">
							<fmt:formatNumber value="${item.NET_PAY_DIF}" pattern="#,##0" />
						</td>
					</tr>
				</c:forEach>
			</tr>
		</table>
		<c:forEach items="${viewResultConfirmSonList0}" var="item">
			<input type="hidden" id="pa1019_PERSON_NUM"
				value="${item.PERSON_NUM_CUR}">
			<input type="hidden" id="pa1019_PERSON_NUM_PRO"
				value="${item.PERSON_NUM_PRO}">
			<input type="hidden" id="pa1019_NOW_DATE" value="${item.PAY_DATE_CUR}">
			<input type="hidden" id="pa1019_PRO_DATE" value="${item.PAY_DATE_PRO}">
			<input type="hidden" id="pa1019_BASE_ALL" value="${item.SALARY_TOTAL_CUR}">
			<input type="hidden" id="pa1019_BASE_ALL_PRO"
				value="${item.SALARY_TOTAL_PRO}">
			<input type="hidden" id="pa1019_WITHHOLD_TOTAL"
				value="${item.WITHHOLD_TOTAL_CUR}">
			<input type="hidden" id="pa1019_WITHHOLD_TOTAL_PRO"
				value="${item.WITHHOLD_TOTAL_PRO}">
			<input type="hidden" id="pa1019_NET_PAY" value="${item.NET_PAY_CUR}">
			<input type="hidden" id="pa1019_NET_PAY_PRO"
				value="${item.NET_PAY_PRO}">

		</c:forEach>
		<!-- 新的图表 -->
		<div id="pa1019_container"
			style="width: 300px; height: 200px; float: left;"></div>

		<div id="pa1019_container2"
			style="width: 300px; height: 400px; float: left;"></div>
	</div>
</c:if>
<c:if test="${currentIndex eq '1'}">
	<script type="text/javascript">

// 初始调用
$(document).ready(function() {
	//布局
		/*	var t_sy0420 = $("#parentCodeTreeSy0420");
			t_sy0420  = $.fn.zTree.init(t_sy0420, setting_sy0420_view, zNodes);
		 */
		$('#demoTree2').treeTable( {
			expandLevel : 1
		});

	});

$(document).ready(
		function() {
			$(".tabsContent").css("height", $(document.body).height() - 230);
			$("#viewResultConfirmSon1_pageContent").css("height",
					$(document.body).height() - 240);
			$("#demoTree2", navTab.getCurrentPanel()).dataTable( {
				"bPaginate" : false, //关闭分页
				"bAutoWidth" : false,//表格宽度不自动变化
				"bProcessing" : true,
				"bLengthChange" : false, //关闭按多少条记录显示下拉框
				"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
				"bSort" : false, //关闭排序功能
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
		});

function changeUrlDetail(ITEM_ID, MONTH_DIF, ITEM_TYPE) {
	var PAY_DATE = $("#viewResultConfirmSon1_PAY_DATE").attr("value");

	openOnRight('/pa/workManagement/viewResultConfirmList3Right?ITEM_ID='
			+ ITEM_ID + '&MONTH_DIF=' + MONTH_DIF + '&ITEM_TYPE=' + ITEM_TYPE
			+ '&PAGE_TYPE=2' + '&PAY_DATE_SS=' + PAY_DATE + '&PAY_DATE=' + PAY_DATE,
			'viewResultConfirmSon1_uitl');

}
</script>
	<div class="pageContent" id="viewResultConfirmSon1_pageContent">
		<input type="hidden" id="viewResultConfirmSon1_PAY_DATE"
			value="${PAY_DATE}" />
		<div id="viewResultConfirmSon1_left" sysLong="printDiv"
			style="float: left; display: block; overflow: auto; width: 45%; border: solid 1px #CCC; line-height: 21px; background: #fff;">
			<div class="user_table"
				style="font: bold 12px/ 20px arial, sans-serif;">
				Total:${fn:length(viewResultConfirmSonList3)}
			</div>
			<table id="demoTree2" class="orderList" width="100%;">
				<thead>
					<tr>
						<th>
							<!--工资项目别案例--><spring:message code="pa.viewResultConfirmSonList.GONGZIXIANGMUBIEANLI.b" />
						</th>
						<th>
							<!--总人数--><spring:message code="empsubject.totalCnt" />
						</th>

						<th>
							<!--项目名称--><spring:message code="pa.insurance.title.projectName" />
						</th>
						<th>
							<!--上月金额--><spring:message code="pa.viewResultConfirmSonList.SHANGYUEJINE.b" />
						</th>
						<th>
							<!--本月金额--><spring:message code="pa.viewResultConfirmSonList.BENYUEJINE.b" />
						</th>
						<th>
							<!--差异--><spring:message code="ess.infoApply.difference" />
						</th>


					</tr>
				</thead>
				<c:forEach items="${viewResultConfirmSonList3}" var="item"
					varStatus="i">

					<c:if test="${item.MONTH_DIF eq '0'}">
						<c:set value="${item.PERSON_NUM+PERSON_NUM0}" var="PERSON_NUM0" />
					</c:if>
					<c:if test="${item.MONTH_DIF eq '1'}">
						<c:set value="${item.PERSON_NUM+PERSON_NUM1}" var="PERSON_NUM1" />
					</c:if>
					<c:if test="${item.MONTH_DIF eq '2'}">
						<c:set value="${item.PERSON_NUM+PERSON_NUM2}" var="PERSON_NUM2" />
					</c:if>
					<c:if test="${item.MONTH_DIF eq '3'}">
						<c:set value="${item.PERSON_NUM+PERSON_NUM3}" var="PERSON_NUM3" />
					</c:if>
					<c:if test="${item.MONTH_DIF eq '4'}">
						<c:set value="${item.PERSON_NUM+PERSON_NUM4}" var="PERSON_NUM4" />
					</c:if>
				</c:forEach>
				<tbody>

					<tr id='XZ' pid='0'>
						<td class="td_type">
							<!--新增对象--><spring:message code="pa.viewResultConfirmSonList.XINZENGDUIXIANG.b" />
						</td>
						<td>
							<span style="color: blue;">${PERSON_NUM0}</span>
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>

					</tr>
					<c:forEach items="${viewResultConfirmSonList3}" var="item"
						varStatus="i">
						<c:if test="${item.MONTH_DIF eq '0'}">
							<tr id="${item.ITEM_ID},${item.MONTH_DIF}" pid='XZ'>
								<td class="td_type">
									<span controller="true">${item.ITEM_NAME}</span>
								</td>
								<td>
									<span style="cursor: pointer; color: blue;"
										onclick="changeUrlDetail('${item.ITEM_ID}','${item.MONTH_DIF}','${item.ITEM_TYPE}' )">${item.PERSON_NUM}<span>
								</td>
								<td>
									${item.ITEM_NAME}
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_PRO}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW-item.MONTH_PRO}"
										pattern="#,##0" />
								</td>

							</tr>
						</c:if>
					</c:forEach>


					<tr id='TJ' pid='0'>
						<td class="td_type">
							<!--添加项目--><spring:message code="hrm.empinfo.ADD_PROJECT" />
						</td>
						<td>
							<span style="color: blue;">${PERSON_NUM1}</span>
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
					</tr>
					<c:forEach items="${viewResultConfirmSonList3}" var="item"
						varStatus="i">
						<c:if test="${item.MONTH_DIF eq '1'}">
							<tr id="${item.ITEM_ID},${item.MONTH_DIF}" pid='TJ'>
								<td class="td_type">
									<span controller="true">${item.ITEM_NAME}</span>
								</td>
								<td>
									<span style="cursor: pointer; color: blue;"
										onclick="changeUrlDetail('${item.ITEM_ID}','${item.MONTH_DIF}','${item.ITEM_TYPE}' )">${item.PERSON_NUM}<span>
								</td>

								<td>
									${item.ITEM_NAME}
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_PRO}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW}" pattern="#,##0" />
								</td>

								<td>
									<fmt:formatNumber value="${item.MONTH_NOW-item.MONTH_PRO}"
										pattern="#,##0" />
								</td>
							</tr>
						</c:if>
					</c:forEach>
					<tr id='CW' pid='0'>
						<td class="td_type">
							<!--除外项目--><spring:message code="pa.viewResultConfirmSonList.CHUWAIXIANGMU.b" />
						</td>
						<td>
							<span style="color: blue;">${PERSON_NUM2}</span>
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
					</tr>
					<c:forEach items="${viewResultConfirmSonList3}" var="item"
						varStatus="i">
						<c:if test="${item.MONTH_DIF eq '2'}">
							<tr id="${item.ITEM_ID},${item.MONTH_DIF}" pid='CW'>
								<td class="td_type">
									<span controller="true">${item.ITEM_NAME}</span>
								</td>
								<td>
									<span style="cursor: pointer; color: blue;"
										onclick="changeUrlDetail('${item.ITEM_ID}','${item.MONTH_DIF}','${item.ITEM_TYPE}' )">${item.PERSON_NUM}<span>
								</td>
									<td>
									${item.ITEM_NAME}
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_PRO}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW}" pattern="#,##0" />
								</td>

								<td>
									<fmt:formatNumber value="${item.MONTH_NOW-item.MONTH_PRO}"
										pattern="#,##0" />
								</td>
							</tr>
						</c:if>
					</c:forEach>
					<tr id='JE' pid='0'>
						<td class="td_type">
							<!--金额变更--><spring:message code="pa.viewResultConfirmSonList.JINEBIANGENG.b" />
						</td>
						<td>
							<span style="color: blue;">${PERSON_NUM4}</span>
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
					</tr>
					<c:forEach items="${viewResultConfirmSonList3}" var="item"
						varStatus="i">


						<c:if test="${item.MONTH_DIF eq '4'}">

							<tr id="${item.ITEM_ID},${item.MONTH_DIF}" pid='JE'>
								<td class="td_type">
									<span controller="true">${item.ITEM_NAME}</span>


								</td>
								<td>
									<span style="cursor: pointer; color: blue;"
										onclick="changeUrlDetail('${item.ITEM_ID}','${item.MONTH_DIF}','${item.ITEM_TYPE}' )">${item.PERSON_NUM}<span>
								</td>
								<td>
									${item.ITEM_NAME}
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_PRO}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW}" pattern="#,##0" />
								</td>

								<td>
									<fmt:formatNumber value="${item.MONTH_NOW-item.MONTH_PRO}"
										pattern="#,##0" />
								</td>
							</tr>
						</c:if>
					</c:forEach>
					<tr id='MY' pid='0'>
						<td class="td_type">
							<!--没有变更--><spring:message code="pa.viewResultConfirmSonList.MEIYOUBIANGENG.b" />
						</td>
						<td>
							<span style="color: blue;">${PERSON_NUM3}</span>
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>

					</tr>
					<c:forEach items="${viewResultConfirmSonList3}" var="item"
						varStatus="i">


						<c:if test="${item.MONTH_DIF eq '3'}">

							<tr id="${item.ITEM_ID},${item.MONTH_DIF}" pid='MY'>
								<td class="td_type">
									<span controller="true">${item.ITEM_NAME}</span>

								</td>
								<td>
									<span style="cursor: pointer; color: blue;"
										onclick="changeUrlDetail('${item.ITEM_ID}','${item.MONTH_DIF}','${item.ITEM_TYPE}' )">${item.PERSON_NUM}<span>
								</td>
								<td>
									${item.ITEM_NAME}
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_PRO}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW-item.MONTH_PRO}"
										pattern="#,##0" />
								</td>
							</tr>
						</c:if>
					</c:forEach>


				</tbody>
			</table>
		</div>
		<%--


		<div class="w-layout-collapse">
			<div id="layout5" class="w-layout-collapse-left"
				onclick="hiddenRight('viewResultConfirmSon1_uitl','viewResultConfirmSon1_left')"></div>
			<div id="layout4" class="w-layout-collapse-right"
				style="display: none;"
				onclick="showIdLeft('viewResultConfirmSon1_uitl','viewResultConfirmSon1_left')"></div>
			<div id="layout2" class="w-layout-collapse-right"
				onclick="hiddenleft('viewResultConfirmSon1_left','viewResultConfirmSon1_uitl')"></div>
			<div id="layout3" class="w-layout-collapse-left"
				style="display: none;"
				onclick="showId('viewResultConfirmSon1_left')"></div>
		</div>
		--%>
		<div id="viewResultConfirmSon1_uitl" style="display: block;">
		</div>
	</div>
</c:if>

<c:if test="${currentIndex eq '2'}">

	<script type="text/javascript">

$(document).ready(
		function() {
			$(".tabsContent").css("height", $(document.body).height() - 230);
			$("#viewResultConfirmSon2_left").css("height",
					$(document.body).height() - 430);
			$("#viewResultConfirmSon2_pageContent").css("height",
					$(document.body).height() - 425);
			$("#viewResultConfirmSon2_pageContent2").css("height",
					$(document.body).height() - 450);
			$("#table2#", navTab.getCurrentPanel()).dataTable( {
				"bPaginate" : false, //关闭分页
				"bAutoWidth" : false,//表格宽度不自动变化
				"bProcessing" : true,
				"bLengthChange" : false, //关闭按多少条记录显示下拉框
				"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
				"bSort" : true, //关闭排序功能
				"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
				"bScrollInfinite" : true,
				"scrollY" : $(document.body).height() - 500,
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

function changeUrlDetail(PERSON_ID, AR_START_DATE, AR_END_DATE) {
	var ITEM_TYPE = $("#viewResultConfirmSon2_ITEM_TYPE").attr("value");
	var PAY_DATE = $("#viewResultConfirmSon1_PAY_DATE").attr("value");
	/*
	openOnRight('/pa/workManagement/viewResultConfirmList2Right?AR_START_DATE='
			+ AR_START_DATE + '&PERSON_ID=' + PERSON_ID + '&AR_END_DATE='
			+ AR_END_DATE, 'viewResultConfirmSon2_uitl');*/

	openOnRight(
			'/pa/workManagement/viewResultConfirmList2Bottom?AR_START_DATE='
					+ AR_START_DATE + '&PERSON_ID=' + PERSON_ID
					+ '&AR_END_DATE=' + AR_END_DATE + '&PAY_DATE=' + PAY_DATE,
			'viewResultConfirmList2Bottom');
}
</script>


	<div class="pageContent" id="viewResultConfirmSon2_pageContent">

		<div id="viewResultConfirmSon2_left" sysLong="printDiv" width="100%"
			style="float: left; display: block; overflow: auto; width: 100%; border: solid 1px #CCC; line-height: 21px; background: #fff;">
			<div class="user_table"
				style="font: bold 12px/ 20px arial, sans-serif;">
				Total:${fn:length(viewResultConfirmSonList2)}
			</div>

			<table id="table2" class="orderList" width="100%;">

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
							<!--职责--><spring:message code="ess.infoApply.title.dutyName" />
						</th>
						<th>
							<!--项目--><spring:message code="ess.empInfo.project" />
						</th>
						<th>
							<!--实际金额--><spring:message code="pa.viewResultConfirmSonList.SHIJIJINE.b" />
						</th>
						<th>
							<!--计算式--><spring:message code="pa.viewResultConfirmSonList.JISUANSHI.b" />
						</th>
					</tr>
				</thead>
				<tbody>


					<c:forEach items="${viewResultConfirmSonList2}" var="item"
						varStatus="i">


						<tr style="cursor: pointer;"
							onclick="changeUrlDetail('${item.PERSON_ID}','${item.AR_START_DATE}','${item.AR_END_DATE}')">



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
								${item.ITEM_NAME}
							</td>
							<td>
								${item.CAL_VALUE}
							</td>
							<td>
								${item.FORMULAR_VALUE}
							</td>

						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
		<%--


		<div class="w-layout-collapse">
			<div id="layout5" class="w-layout-collapse-left"
				onclick="hiddenRight('viewResultConfirmSon2_uitl','viewResultConfirmSon2_left')"></div>
			<div id="layout4" class="w-layout-collapse-right"
				style="display: none;"
				onclick="showIdLeft('viewResultConfirmSon2_uitl','viewResultConfirmSon2_left')"></div>
			<div id="layout2" class="w-layout-collapse-right"
				onclick="hiddenleft('viewResultConfirmSon2_left','viewResultConfirmSon2_uitl')"></div>
			<div id="layout3" class="w-layout-collapse-left"
				style="display: none;"
				onclick="showId('viewResultConfirmSon2_left')"></div>
		</div>
		
		<div id="viewResultConfirmSon2_uitl" style="display: block;">
		</div>--%>
	</div>
	<div class="pageContent" id="viewResultConfirmSon2_pageContent2">
		<div id="viewResultConfirmList2Bottom" style="display: block;">
		</div>
	</div>
</c:if>

<c:if test="${currentIndex eq '3'}">




	<script type="text/javascript">

// 初始调用
$(document).ready(function() {
	//布局
		/*	var t_sy0420 = $("#parentCodeTreeSy0420");
			t_sy0420  = $.fn.zTree.init(t_sy0420, setting_sy0420_view, zNodes);
		 */
		$('#demoTree1').treeTable( {
			expandLevel : 1
		});

	});

$(document).ready(

		function() {
			$("#viewResultConfirmSon3_pageContent").css("height",
					$(document.body).height() - 240);
			$(".tabsContent").css("height", $(document.body).height() - 230);

			$("#demoTree1", navTab.getCurrentPanel()).dataTable( {
				"bPaginate" : false, //关闭分页
				"bAutoWidth" : false,//表格宽度不自动变化
				"bProcessing" : true,
				"bLengthChange" : false, //关闭按多少条记录显示下拉框
				"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
				"bSort" : false, //关闭排序功能
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
		});

function changeUrlDetail(ITEM_ID, MONTH_DIF, ITEM_TYPE) {
	var PAY_DATE = $("#viewResultConfirmSon3_PAY_DATE").attr("value");
	openOnRight('/pa/workManagement/viewResultConfirmList3Right?ITEM_ID='
			+ ITEM_ID + '&MONTH_DIF=' + MONTH_DIF + '&ITEM_TYPE=' + ITEM_TYPE
			+ '&PAGE_TYPE=2' + '&PAY_DATE_SS=' + PAY_DATE + '&PAY_DATE=' + PAY_DATE,
			'viewResultConfirmSon3_uitl');

}
</script>
	<div class="pageContent" id="viewResultConfirmSon3_pageContent">
		<input type="hidden" id="viewResultConfirmSon3_PAY_DATE"
			value="${PAY_DATE}" />
		<div id="viewResultConfirmSon3_left" sysLong="printDiv"
			style="float: left; display: block; overflow: auto; width: 45%; border: solid 1px #CCC; line-height: 21px; background: #fff;">
			<div class="user_table"
				style="font: bold 12px/ 20px arial, sans-serif;">
				Total:${fn:length(viewResultConfirmSonList2)}
			</div>

			<table id="demoTree1" class="orderList" width="100%;">

				<thead>
					<tr>
						<th>
							<!--工资项目别案例--><spring:message code="pa.viewResultConfirmSonList.GONGZIXIANGMUBIEANLI.b" />
						</th>
						<th>
							<!--总人数--><spring:message code="empsubject.totalCnt" />
						</th>

						<th>
							<!--项目名称--><spring:message code="pa.insurance.title.projectName" />
						</th>
						<th>
							<!--上月金额--><spring:message code="pa.viewResultConfirmSonList.SHANGYUEJINE.b" />
						</th>
						<th>
							<!--本月金额--><spring:message code="pa.viewResultConfirmSonList.BENYUEJINE.b" />
						</th>
						<th>
							<!--差异--><spring:message code="ess.infoApply.difference" />
						</th>


					</tr>
				</thead>

				<c:forEach items="${viewResultConfirmSonList3}" var="item"
					varStatus="i">
					<c:if test="${item.MONTH_DIF eq '0'}">
						<c:set value="${item.PERSON_NUM+PERSON_NUM0}" var="PERSON_NUM0" />
					</c:if>

					<c:if test="${item.MONTH_DIF eq '1'}">
						<c:set value="${item.PERSON_NUM+PERSON_NUM1}" var="PERSON_NUM1" />
					</c:if>
					<c:if test="${item.MONTH_DIF eq '2'}">
						<c:set value="${item.PERSON_NUM+PERSON_NUM2}" var="PERSON_NUM2" />
					</c:if>
					<c:if test="${item.MONTH_DIF eq '3'}">
						<c:set value="${item.PERSON_NUM+PERSON_NUM3}" var="PERSON_NUM3" />
					</c:if>
					<c:if test="${item.MONTH_DIF eq '4'}">
						<c:set value="${item.PERSON_NUM+PERSON_NUM4}" var="PERSON_NUM4" />
					</c:if>
				</c:forEach>

				<tbody>
					<tr id='XZ' pid='0'>
						<td class="td_type">
							<!--新增对象--><spring:message code="pa.viewResultConfirmSonList.XINZENGDUIXIANG.b" />
						</td>
						<td>
							<span style="color: blue;">${PERSON_NUM0}</span>
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>

					</tr>

					<c:forEach items="${viewResultConfirmSonList3}" var="item"
						varStatus="i">


						<c:if test="${item.MONTH_DIF eq '0'}">
							<tr id="${item.ITEM_ID},${item.MONTH_DIF}" pid='XZ'>
								<td class="td_type">
									<span controller="true">${item.ITEM_NAME}</span>
								</td>
								<td>
									<span style="cursor: pointer; color: blue;"
										onclick="changeUrlDetail('${item.ITEM_ID}','${item.MONTH_DIF}','${item.ITEM_TYPE}' )">${item.PERSON_NUM}<span>
								</td>
								<td>
									${item.ITEM_NAME}
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_PRO}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW-item.MONTH_PRO}"
										pattern="#,##0" />
								</td>

							</tr>
						</c:if>
					</c:forEach>
					<tr id='TJ' pid='0'>
						<td class="td_type">
							<!--添加项目--><spring:message code="hrm.empinfo.ADD_PROJECT" />
						</td>
						<td>
							<span style="color: blue;">${PERSON_NUM1}</span>
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>

					</tr>

					<c:forEach items="${viewResultConfirmSonList3}" var="item"
						varStatus="i">


						<c:if test="${item.MONTH_DIF eq '1'}">
							<tr id="${item.ITEM_ID},${item.MONTH_DIF}" pid='TJ'>
								<td class="td_type">
									<span controller="true">${item.ITEM_NAME}</span>
								</td>
								<td>
									<span style="cursor: pointer; color: blue;"
										onclick="changeUrlDetail('${item.ITEM_ID}','${item.MONTH_DIF}','${item.ITEM_TYPE}' )">${item.PERSON_NUM}<span>
								</td>
								<td>
									${item.ITEM_NAME}
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_PRO}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW-item.MONTH_PRO}"
										pattern="#,##0" />
								</td>

							</tr>
						</c:if>
					</c:forEach>
					<tr id='CW' pid='0'>
						<td class="td_type">
							<!--除外项目--><spring:message code="pa.viewResultConfirmSonList.CHUWAIXIANGMU.b" />
						</td>
						<td>
							<span style="color: blue;">${PERSON_NUM2}</span>
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>

					</tr>
					<c:forEach items="${viewResultConfirmSonList3}" var="item"
						varStatus="i">


						<c:if test="${item.MONTH_DIF eq '2'}">

							<tr id="${item.ITEM_ID},${item.MONTH_DIF}" pid='CW'>
								<td class="td_type">
									<span controller="true">${item.ITEM_NAME}</span>

								</td>
								<td>
									<span style="cursor: pointer; color: blue;"
										onclick="changeUrlDetail('${item.ITEM_ID}','${item.MONTH_DIF}','${item.ITEM_TYPE}' )">${item.PERSON_NUM}<span>
								</td>
								<td>
									${item.ITEM_NAME}
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_PRO}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW-item.MONTH_PRO}"
										pattern="#,##0" />
								</td>

							</tr>
						</c:if>
					</c:forEach>
					<tr id='JE' pid='0'>
						<td class="td_type">
							<!--金额变更--><spring:message code="pa.viewResultConfirmSonList.JINEBIANGENG.b" />
						</td>
						<td>
							<span style="color: blue;">${PERSON_NUM4}</span>
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>

					</tr>
					<c:forEach items="${viewResultConfirmSonList3}" var="item"
						varStatus="i">


						<c:if test="${item.MONTH_DIF eq '4'}">

							<tr id="${item.ITEM_ID},${item.MONTH_DIF}" pid='JE'>
								<td class="td_type">
									<span controller="true">${item.ITEM_NAME}</span>


								</td>
								<td>
									<span style="cursor: pointer; color: blue;"
										onclick="changeUrlDetail('${item.ITEM_ID}','${item.MONTH_DIF}','${item.ITEM_TYPE}' )">${item.PERSON_NUM}<span>
								</td>
								<td>
									${item.ITEM_NAME}
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_PRO}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW-item.MONTH_PRO}"
										pattern="#,##0" />
								</td>

							</tr>
						</c:if>
					</c:forEach>
					<tr id='MY' pid='0'>
						<td class="td_type">
							<!--没有变更--><spring:message code="pa.viewResultConfirmSonList.MEIYOUBIANGENG.b" />
						</td>
						<td>
							<span style="color: blue;">${PERSON_NUM3}</span>
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>
						<td>
							--
						</td>

					</tr>
					<c:forEach items="${viewResultConfirmSonList3}" var="item"
						varStatus="i">


						<c:if test="${item.MONTH_DIF eq '3'}">

							<tr id="${item.ITEM_ID},${item.MONTH_DIF}" pid='MY'>
								<td class="td_type">
									<span controller="true">${item.ITEM_NAME}</span>

								</td>
								<td>
									<span style="cursor: pointer; color: blue;"
										onclick="changeUrlDetail('${item.ITEM_ID}','${item.MONTH_DIF}','${item.ITEM_TYPE}' )">${item.PERSON_NUM}<span>
								</td>
								<td>
									${item.ITEM_NAME}
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_PRO}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW}" pattern="#,##0" />
								</td>
								<td>
									<fmt:formatNumber value="${item.MONTH_NOW-item.MONTH_PRO}"
										pattern="#,##0" />
								</td>

							</tr>
						</c:if>
					</c:forEach>


				</tbody>
			</table>
		</div>
		<%--


		<div class="w-layout-collapse">
			<div id="layout5" class="w-layout-collapse-left"
				onclick="hiddenRight('viewResultConfirmSon3_uitl','viewResultConfirmSon3_left')"></div>
			<div id="layout4" class="w-layout-collapse-right"
				style="display: none;"
				onclick="showIdLeft('viewResultConfirmSon3_uitl','viewResultConfirmSon3_left')"></div>
			<div id="layout2" class="w-layout-collapse-right"
				onclick="hiddenleft('viewResultConfirmSon3_left','viewResultConfirmSon3_uitl')"></div>
			<div id="layout3" class="w-layout-collapse-left"
				style="display: none;"
				onclick="showId('viewResultConfirmSon3_left')"></div>
		</div>
		--%>
		<div id="viewResultConfirmSon3_uitl" style="display: block;">
		</div>
	</div>

</c:if>

<c:if test="${currentIndex eq '6'}">
<script>

$(document).ready(function() {
	$("#pageContent6").css("height", $(document.body).height() - 292);
	$(".tabsContent").css("height", $(document.body).height() - 240);
	$("#viewResultConfirmSonList_List6").width($(document.body).width() - 70);
	$("#viewResultConfirmSonList_List6", navTab.getCurrentPanel()).dataTable( {
		"bPaginate" : false, //关闭分页
		"bAutoWidth" : false,//表格宽度不自动变化
		"bProcessing" : true,
		"bLengthChange" : false, //关闭按多少条记录显示下拉框
		"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort" : true, //关闭排序功能
		"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite" : true,
		"scrollY" : $(document.body).height() - 360,
		"scrollX" : true, //横向滚动条  true不锁
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
</script>
	<div class="pageHeader">
		<form onsubmit="return navTabSearcha(this);"
			action="/pa/workManagement/viewResultConfirmSonList?currentIndex=6"
			method="post">
			<input type="hidden" name='PAY_DATE' value="${PAY_DATE}" />
			<input type="hidden" name='SALARY_DISTIN_NO'
				value="${SALARY_DISTIN_NO}" />
				<input type="hidden" id='viewResultConfirmSonList6_KEY' name="KEY"
								class="text" value="${KEY}" />
			<%-- <div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							部门
						</td>
						<td>
							<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="all" id="pa1016_seachDept" selected="${DEPT_NO}" />
							<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
								limit="all" id="pa1016_seachDept" selected="${DEPT_NO}" />
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										<spring:message code="public.title.search" />
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div> --%>
		</form>
	</div>
	<div class="pageContent" id="pageContent6" width="100%">
		Total:${fn:length(viewResultConfirmSonList6)}
		<table class="orderList " id="viewResultConfirmSonList_List6">
			<thead>
				<tr>
					<th>
						No
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
						<!--社会保险(个人)--><spring:message code="pa.viewResultConfirmSonList.SHEHUIBAOXIANGEREN.b" />
					</th>
					<th>
						<!--医疗保险（个人）--><spring:message code="pa.viewResultConfirmSonList.YILIAOBAOXIANGEREN.b" />
					</th>
					<th>
						<!--失业保险（个人）--><spring:message code="pa.viewResultConfirmSonList.SHIYEBAOXIANGEREN.b" />
					</th>
					<th>
						<!--个人社保合计--><spring:message code="pa.viewResultConfirmSonList.GERENSHEBAOHEJI.b" />
					</th>
					<!--<th>
						公积金（个人）<spring:message code="pa.viewResultConfirmSonList.GONGJIJINGEREN.b" />
					</th>
					<th>
						个人社保公积金合计<spring:message code="pa.viewResultConfirmSonList.GERENSHEBAOGONGJIJINHEJI.b" />
					</th>-->
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${viewResultConfirmSonList6}" var="PST"
					varStatus="i">
					<tr>
						<td style="text-align: center">
							${i.count}
						</td>
                        <td style="text-align: center">
							${PST.EMPID}
						</td>						<td style="text-align: center">
							${PST.LOCAL_NAME}
						</td>
						<td style="text-align: center">
							${PST.DEPT_NAME}
						</td>
						<td style="text-align: center">
							${PST.POST_FAMILY_NAME}
						</td><td style="text-align: center">
							${PST.POST_GRADE_NAME}
						</td>
						<td style="text-align: center">
							${PST.POSITION_NAME}
						</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${PST.SOCIAL_INS_PERSONAL}" pattern="#,##0" />
						</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${PST.MEDICAL_INS_PERSONAL}" pattern="#,##0" />
						</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${PST.UNEMPLOYMENT_INS_PERSONAL}" pattern="#,##0" />
						</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${PST.PERSONAL_INS_TOTAL}" pattern="#,##0" />
						</td>
						<!--<td style="text-align: center">
							<fmt:formatNumber value="${PST.CPF_PER}" pattern="#,##0" />
						</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${PST.ENDOWMENT_PER+PST.MEDICARE_PER+PST.UNEMPLOYMENT_PER+PST.CPF_PER}" pattern="#,##0" />
						</td>-->
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</c:if>

<c:if test="${currentIndex eq '5'}">


	<script>

$(document).ready(function() {
	$("#pageContent5").css("height", $(document.body).height() - 292);
	$(".tabsContent").css("height", $(document.body).height() - 240);
	$("#viewResultConfirmSonList_List5").width($(document.body).width() - 70);
	$("#viewResultConfirmSonList_List5", navTab.getCurrentPanel()).dataTable( {
		"bPaginate" : false, //关闭分页
		"bAutoWidth" : false,//表格宽度不自动变化
		"bProcessing" : true,
		"bLengthChange" : false, //关闭按多少条记录显示下拉框
		"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort" : true, //关闭排序功能
		"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite" : true,
		"scrollY" : $(document.body).height() - 300,
		"scrollX" : true, //横向滚动条  true不锁
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
</script>

	<div class="pageHeader">
		<form onsubmit="return navTabSearcha(this);"
			action="/pa/workManagement/viewResultConfirmSonList?currentIndex=5"
			method="post">
			<input type="hidden" name='PAY_DATE' value="${PAY_DATE}" />
			<input type="hidden" name='SALARY_DISTIN_NO'
				value="${SALARY_DISTIN_NO}" />
				<input type="hidden" id='viewResultConfirmSonList5_KEY' name="KEY"
								class="text" value="${KEY}" />
			<%-- <div class="searchBar">
				<table class="searchContent">

					<tr>
						<td>
							姓名/GEN 搜索
						</td>
						<td>
							<input type="text" id='viewResultConfirmSonList5_KEY' name="KEY"
								class="text" value="${KEY}" />
						</td>


					</tr>

				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										<spring:message code="public.title.search" />
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div> --%>
		</form>
	</div>


	<div class="pageContent" id="pageContent5" width="100%">

		Total:${fn:length(viewResultConfirmSonList5)}

		<table class="orderList " id="viewResultConfirmSonList_List5">

			<thead>


				<tr>
					<th>
						No
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
						<!--考勤扣除--><spring:message code="pa.viewResultConfirmSonList.KAOQINKOUCHU.b" />
					</th>
					<th>
						<!--个人社保合计--><spring:message code="pa.viewResultConfirmSonList.GERENSHEBAOHEJI.b" />
					</th>
					<th>
						<!--扣税基准--><spring:message code="pa.viewResultConfirmSonList.KOUSHUIJIZHUN.b" />
					</th>
					<th>
						<!--特殊扣税基准--><spring:message code="pa.viewResultConfirmSonList.TESHUKOUSHUIJIZHUN.b" />
					</th>
					<th>
						<!--特殊扣税人数--><spring:message code="pa.viewResultConfirmSonList.TESHUKOUSHUIRENSHU.b" />
					</th>
					<th>
						<!--应纳税所得额--><spring:message code="pa.viewResultConfirmSonList.YINGNASHUISUODEE.b" />
					</th>
					<c:if test="${LoginUser.cpnyId eq 'HTSV' || LoginUser.cpnyId eq 'HAE'|| LoginUser.cpnyId eq 'SPC_DL'}">
						<th>
							<!--税率--><spring:message code="pa.viewResultConfirmSonList.SHUILV.b" />
						</th>
						<th>
							<!--速算扣除数--><spring:message code="pa.viewResultConfirmSonList.SUSUANKOUCHUSHU.b" />
						</th>
						<!--<th>
							速算扣除数<spring:message code="pa.viewResultConfirmSonList.SUSUANKOUCHUSHU.b" />
						</th>
					</c:if>-->
					<th>
						<!--个人所得税--><spring:message code="ess.viewpersonalpainfo.gerensuodeshui" />
					</th>
					<th>
						<!--实发工资--><spring:message code="ess.viewpersonalpainfo.shifagongzi" />
					</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${viewResultConfirmSonList5}" var="PST"
					varStatus="i">
					<tr>
						<td style="text-align: center">
							${i.count}
						</td>
                        <td style="text-align: center">
							${PST.EMPID}
						</td>
						<td style="text-align: center">
							${PST.LOCAL_NAME}
						</td>
						<td style="text-align: center">
							${PST.DEPT_NAME}
						</td>

						<td style="text-align: center">
							${PST.POST_FAMILY_NAME}
						</td>

						<td style="text-align: center">
							${PST.POST_GRADE_NAME}
						</td>

						<td style="text-align: center">
							${PST.POSITION_NAME}
						</td>

						<td style="text-align: center">
							<fmt:formatNumber value="${PST.INCOME_BEFORE_TAX}" pattern="#,##0" />

						</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${PST.ATT_DEDUCT_TOTAL}"
								pattern="#,##0" />

						</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${PST.PERSONAL_INS_TOTAL}"
								pattern="#,##0" />

						</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${PST.P_TAX_DEDUCT_STD}" pattern="#,##0" />

						</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${PST.P_TAX_FAMILY_DEDUCT_STD}" pattern="#,##0" />

						</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${PST.P_TAX_FAMILY_DEDUCT_COUNT}" pattern="#,##0" />

						</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${PST.TAXABLE_INCOME}" pattern="#,##0" />

						</td>
						<c:if test="${LoginUser.cpnyId eq 'HTSV' || LoginUser.cpnyId eq 'HAE'|| LoginUser.cpnyId eq 'SPC_DL'}">
							<td style="text-align: center">
								<fmt:formatNumber value="${PST.PT_TAX_RATE}" />
							</td>
							<td style="text-align: center">
								<fmt:formatNumber value="${PST.QUICK_DEDUCTION_TAX}" />
							</td>
							<!--<td style="text-align: center">
								<fmt:formatNumber value="${PST.REDUCE_NUM}"
									pattern="#,##0" />
	
							</td>-->
						</c:if>
						<td style="text-align: center">
							<fmt:formatNumber value="${PST.PERSONAL_TAX}"
								pattern="#,##0" />

						</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${PST.REAL_WAGES}"
								pattern="#,##0" />

						</td>
					</tr>
				</c:forEach>
			</tbody>



		</table>

	</div>


</c:if>


