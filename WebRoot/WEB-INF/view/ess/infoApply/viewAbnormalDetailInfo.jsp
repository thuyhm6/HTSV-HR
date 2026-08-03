<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

$(document).ready(function() {
	$(".list", navTab.getCurrentPanel()).dataTable( {
		"bPaginate" : false, //关闭分页
		"bAutoWidth" : false,//表格宽度不自动变化
		"bProcessing" : true,
		"bLengthChange" : false, //关闭按多少条记录显示下拉框
		"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		//"fixedColumns" : {
			//leftColumns : 4
		//},//锁表头
		"bSort" : true, //关闭排序功能
		"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite" : true,
		"scrollY" : $(document.body).height() - 320,
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
</script>

<div class="pageContent">
    <table id="AbnormalDetailList" class="list" width="100%">
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
						<!--考勤日期--><spring:message code="ess.infoApply.attendance_date" />
					</th>
					<th>
						<!--工作时间--><spring:message code="ess.infoApply.working_hours" />
					</th>
					<th>
						<!--打卡时间--><spring:message code="ess.infoApply.card_clock_time" />
					</th>
					<th>
					    <c:if test="${ITEM_TYPE eq '218197'}" >
						<!--异常类型--><spring:message code="ess.infoApply.yichangleixing" />
						</c:if>
						<c:if test="${ITEM_TYPE eq '21'}" >
						<!--休假类型--><spring:message code="ess.viewApply.title.leaveApplyType" />
						</c:if>
						<c:if test="${ITEM_TYPE eq '31'}" >
						<!--加班类型--><spring:message code="ess.viewApply.title.overtimeApplyType" />
						</c:if>
					</th>
					<c:if test="${ITEM_TYPE eq '21' || ITEM_TYPE eq '31'}" >
					<th>
					    开始时间
					</th>
					<th>
					    结束时间
					</th>
					<th>
					    时长
					</th>
					</c:if>
				</tr>
			</thead>


			<c:forEach items="${AbnormalDetailList}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align: center">
						${i.count}
					</td>
					<td class="td_type" style="text-align: center">
						${item.EMPID}
					</td>
					<td class="td_type" style="text-align: center">
						${item.LOCAL_NAME}
					</td>
					<td class="td_type" style="text-align: center">
						${item.DEPT_NAME}
					</td>
					<td class="td_type" style="text-align: center">
						${item.AR_DATE_STR}
					</td>
					<td class="td_type" style="text-align: center">
						${item.SHIFT_START_TIME}<br>${item.SHIFT_END_TIME}
					</td>
					<td class="td_type" style="text-align: center">
						<!--进门卡--><spring:message code="ess.infoApply.in_door_card" />${item.INDOOR_TIME}<br><!--出门卡--><spring:message code="ess.infoApply.out_door_card" />${item.OUTDOOR_TIME}
					</td>
					<td class="td_type" style="text-align: center">
						${item.ITEM_NAME}
					</td>
					<c:if test="${ITEM_TYPE eq '21' || ITEM_TYPE eq '31'}" >
						<td class="td_type" style="text-align: center">
							${item.FROM_TIME}
						</td>
						<td class="td_type" style="text-align: center">
							${item.TO_TIME}
						</td>
						<td class="td_type" style="text-align: center">
							${item.QUANTITY}  ${item.UNIT }
						</td>
					</c:if>
			</c:forEach>
		</table>
</div>