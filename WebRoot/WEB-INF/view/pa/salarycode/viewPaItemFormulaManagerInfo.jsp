<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	openOnRight('/pa/salarycode/viewAddPaItemFormulaInfo?SEQ=${SEQ}','viewPaItemFormulaManagerInfo_unit');
	$("#viewPaItemFormulaManagerInfo_search").click(function(){
		$("#viewPaItemFormulaManagerInfoForm").submit();
	});

	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": $(document.body).height() - 250,
        "scrollX": true,
        "orderClasses": false
	});
});
</script>
<div class="pageHeader">
<form id="viewPaItemFormulaManagerInfoForm" onsubmit="return navTabSearch(this);" action="/pa/salarycode/viewPaItemFormulaManagerInfo" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>工资项目</td>
		<td>
			<input type="text" name="seach_ITEM_NAME" value="${ITEM_NAME}"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>

<div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" id="viewPaItemFormulaManagerInfo_search" href="#"><span>查询</span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="openOnRight('/pa/salarycode/viewAddPaItemFormulaInfo?SEQ=0','viewPaItemFormulaManagerInfo_unit');" href="#">
					<span>添加</span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeletepaItemFormulaInfoCallback('viewAddpaItemFormulaInfo',navTabAjaxDone)" href="#"><span>删除</span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddpaItemFormulaInfoCallback('viewAddpaItemFormulaInfo',navTabAjaxDone)" href="#"><span>保存</span></a>
			</li>
			<li>
				<a class="delete" onclick="downloadExcel('viewPaItemFormulaManagerInfoForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=33','/pa/salarycode/viewPaItemFormulaManagerInfo')" href="#"><span>导出到Excel</span></a>					
			</li>
	</ul>
</div>

	<div id="viewPaItemFormulaManagerInfo_left" style="float:left; display:block; overflow:auto;width:430px; height:auto; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${orgPaItemFormulaSize}</div>
		<table class="list" width="1400px;">
			<thead>
				<tr>
					<th width="25px;">No.</th>
					<th width="80px">科目名称</th>
					<th width="50px">借贷区分</th>
					<th width="80px">会计科目</th>
					<th width="60px">汇总类型</th>
					<th width="100px">反记账</th>
					<th width="100px">WBS</th>
					<th width="60px">排序号</th>
					<th width="60px">状态</th>
					<th width="100px">变更者</th>
					<th width="50px">变更时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewPaItemFormulaList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/pa/salarycode/viewAddPaItemFormulaInfo?SEQ=${item.SEQ}','viewPaItemFormulaManagerInfo_unit');">
						<td>${i.count}</td>
						<td>${item.ITEM_NAME}</td>
						<td>${item.DR_CR}</td>
						<td>${item.ACCOUNT}</td>
						<td>${item.SUM_TYPE_NAME}</td>
						<td>${item.FAN_JIZHANG}</td>
						<td>${item.WBS}</td>
						<td>${item.ORDER_NO}</td>
						<td>${item.ACTIVITY_NAME}</td>
						<td>${item.UPDATED_BY}</td>
						<td>${item.UPDATE_DATE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewPaItemFormulaManagerInfo_unit','viewPaItemFormulaManagerInfo_left')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewPaItemFormulaManagerInfo_unit','viewPaItemFormulaManagerInfo_left')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewPaItemFormulaManagerInfo_left','viewPaItemFormulaManagerInfo_unit')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewPaItemFormulaManagerInfo_left')"></div>
	</div>
	<div id="viewPaItemFormulaManagerInfo_unit"  style="display:block;">
	</div>
</div>