<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

$(document).ready(function() {

	$("#pa1017_table").css("width", "100%");

	$("#pa1017_table", navTab.getCurrentPanel()).dataTable( {
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":true,
	    //"lengthMenu": [[20, 50, 100, -1], [20, 50, 100, "所有"]],
		//"bLengthChange": true,  //按多少条记录显示下拉框
		//"iDisplayLength": 20, //默认每页显示的记录数
		"bLengthChange":false,
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
         "searching": true,//本地搜索
			"bSort": true,   //排序功能
			"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		//"bScrollInfinite":true,
         "orderClasses": false,
         "order":[],//初始化不用自动排序
 		 "scrollX" : true,
         "scrollY": $(document.body).height() - 280,
         //"scrollCollapse": true,
         "deferRender":true,
         "scroller":true,
		"oLanguage" : {//多语言配置
			 //正在加载中......
			 "sProcessing": "<spring:message code='ess.message.loading' />",
		     //查询不到相关数据！
		     "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
		     //表中无数据存在！
		     "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
		     //快速筛选
		     "sSearch": "<spring:message code='ess.message.rapid_screening' />",
		     //每页 _MENU_ 条记录
		     "sLengthMenu": "<spring:message code='ess.message.page_of_lines' />",
		     //从 _START_ 到 _END_ /共 _TOTAL_ 条数据
		     "sInfo": "<spring:message code='ess.message.sum_begin_to_end' />",
		     //(从 _MAX_ 条记录过滤)
		     "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />",
		     "oPaginate": {
		         //上一页
		         "sPrevious": "<spring:message code='ess.message.previous_page' />",
		         //下一页
		         "sNext": "<spring:message code='ess.message.next_page' />"
			}
		}
	//多语言配置
			});
});

function changeSelect(obj) {

	var item_type = $("#ITEM_TYPE").attr("value");

	$("#ITEM_ID option").remove(); //清除下拉列表内容
	var selectId = $("#selectId").attr("value"); //获取item_id

	$('input[name="ITEM_ID_NAME"]').each(function() {//遍历每一个名字为interest的复选框，其中选中的执行函数  
				if ($(this).attr("title") == item_type) {
					var selectFlge = ""; //每次都重置
					if (selectId == $(this).attr("value")) {
						selectFlge = "selected"; //如果ID相同则默认选中
					}

					$("#ITEM_ID").append(
							"<option " + selectFlge + " value=\""
									+ $(this).attr("value") + "\">"
									+ $(this).attr("alt") + "</option>");
				}
			});

}

$(function() {

	changeSelect($("#ITEM_TYPE"));
});
</script>

<div style="display: none">
	<c:forEach items="${itemValueInfo}" var="item">
		<input type="hidden" value="${item.ITEM_ID}" name="ITEM_ID_NAME"
			alt="${item.ITEM_NAME}" title="${item.ITEM_TYPE}" />
	</c:forEach>
</div>


<div class="pageHeader">

	<form class="j-ajax" onsubmit="return  navTabSearch(this)"
		action="/pa/workManagement/detailItemCountInfo" method="post"
		id="detailItemCountInfo" name="detailItemCountInfo">

		<div class="searchBar">
			<table class="searchContent">

				<input type="hidden" id="selectId" value="${ITEM_ID}" />

				<tr>
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
				</tr>
				<tr>
					<td>
						<!--项目区分--><spring:message code="liang.public.title.ItemDistinguish" />
					</td>
					<td>

						<select name="ITEM_TYPE" id="ITEM_TYPE"
							onchange="changeSelect(this)">
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
						<select name="ITEM_ID" id="ITEM_ID">

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
				</ul>
			</div>

		</div>
	</form>
</div>



<div class="pageContent" id='pa1017_pageContent' width="100%">
	<h2>
		<%-- Total:${fn:length(detailItemCountInfoList)} --%>
	</h2>
	<table class="orderList" id="pa1017_table">
		<thead>
			<tr>
				<th>

					No.
				</th>
				<th>
					<!--姓名--><spring:message code="ess.infoApply.NAME" />
				</th>
				<th>
					<!--工号--><spring:message code="ess.infoApply.EMP_ID" />
				</th>
				<th>
					<!--部门名--><spring:message code="ess.infoApply.DEPT_NAME" />
				</th>
				<th>
					<!--职群--><spring:message code="ess.empInfo.zhiqun" />
				</th>
				<th>
					<!--职级--><spring:message code="hrm.contract.Rank" />
				</th>
				<th>
					<!--职责--><spring:message code="ess.infoApply.title.dutyName" />
				</th>
				<th>
					<!--实际支付--><spring:message code="pa.detailItemCountInfo.SHIJIQUFEN.b" />
				</th>
				<th>
					<!--适用式--><spring:message code="pa.detailPersonCountInfoRight.SHIYONGSHI.b" />
				</th>
				<th>
					<!--备注--><spring:message code="ess.empInfo.remarks" />
				</th>
			</tr>
		</thead>
		<tbody>
		    <c:set var="A" value="${0}" />
			<c:forEach items="${detailItemCountInfoList}" var="item"
				varStatus="i">
			<c:if test="${item.PAY_NUMBER > 0}">
				<tr>
					<td >
						<c:set var="A" value="${A+1}" />
						${A}
					</td>
					<td >
						${item.LOCAL_NAME}
					</td>
					<td >
						${item.EMPID}
					</td>
					<td >
						${item.DEPT_NAME}
					</td>
					<td >
						${item.POST_FAMILY_NAME}
					</td>
					<td >
						${item.POST_GRADE_NAME}
					</td>
					<td >
						${item.POSITION_NAME}
					</td>
					<td >
						<fmt:formatNumber value="${item.PAY_NUMBER}" pattern="#,##0" />
					</td>
					<td >
						${item.FORMULAR_VALUE}
					</td>
					<td >
						${item.REMARK}
					</td>
				</tr>
			</c:if>
			</c:forEach>
		</tbody>
	</table>


</div>
