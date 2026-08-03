<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!--班组的月别列表查询-->
<script type="text/javascript">
$(document).ready(function(){
	//查询
	$("#viewArShiftMonthCheckList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewArShiftMonthCheckListForm",navTab.getCurrentPanel()).submit();
	});

	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": $(document.body).height() - 270,
	    "scrollX": false,
	    "orderClasses": false
	});
});
</script>
<div class="pageHeader">
<form id="viewArShiftMonthCheckListForm" onsubmit="return navTabSearch(this);" action="/ar/arShiftGroupManagement/viewArShiftMonthCheckList" method="post">
<div class="searchBar">
<table class="searchContent">
	       <tr>
				<td><!--年月--><spring:message code="ess.infoApply.YEAR_MONTH" /></td>
				<td><input id="seach_DDATE" type="text" name="seach_DDATE"  class="Wdate" onClick="WdatePicker({dateFmt:'MMyyyy',lang:'en'})" value="${ddate }"/></td>
				<td><!--班组类型--><spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" /></td>
				<td><ait:SelectSyCodeByCpnyID name="seach_SHIFT_NO" id="seach_SHIFT_NO"
						parentNo="400223" cnpyID="${LoginUser.cpnyId}" limit="all"
						selected="${SHIFT_NO }" />
			</tr>
</table>
</div>
</form>
</div>

<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewArShiftMonthCheckList_Serch" href="#"><span><!--查询--><spring:message code="public.title.search" /></span></a></li>
		<!--<li><a class="add" href="#" onclick="window.print()"><span>打印<spring:message code="hrm.approve.PRINTING" /></span></a></li>-->
		<li><a class="delete" onclick="downloadExcel('viewArShiftMonthCheckListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=57','/ar/arShiftGroupManagement/viewArShiftMonthCheckList')" href="#"><span><!--导出到EXCEL --><spring:message code="hrm.empinfo.EXPORT" /></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewArShiftMonthCheckListCnt}</div>
				<table class="list" width="100%">
					<thead>
						<tr>
							<th width="30px" colspan="3"></th>
							<th width="350px" colspan="3"><!--正常班--><spring:message code="ar.viewArShiftMonthCheckList.ZHENGCHANGBAN.b" /></th>
						</tr>
						<tr>
							<th width="30px">No.</th>
							<th width="200px"><!--日期--><spring:message code="ess.infoApply.date" /></th>
							<th width="200px"><!--星期--><spring:message code="ess.infoApply.week" /></th>
							<th width="100px"><!--班次性质--><spring:message code="ar.viewshift.title.bancixingzhi" /></th>
							<th width="100px"><!--班次--><spring:message code="ar.monthwork.title.WorkSchedule" /></th>
							<th width="100px"><!--工作时间--><spring:message code="ess.infoApply.working_hours" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewArShiftMonthCheckList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.DDATE}</td>
								<td class='td_center'>${item.IWEEK}</td>
								<td class='td_center'>${item.DATETYPENAME}</td>
								<td class='td_center'>${item.SHIFT_NAME}</td>
								<td class='td_center'>${item.WORKTIME}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
