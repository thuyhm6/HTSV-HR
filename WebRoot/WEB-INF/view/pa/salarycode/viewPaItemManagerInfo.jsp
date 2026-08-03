<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	openOnRight('/pa/salarycode/viewAddPaItemInfo?SEQ=${SEQ}','viewPaItemManagerInfo_unit');
	$("#viewPaItemManagerInfo_search").click(function(){
		$("#viewPaItemManagerInfoForm").submit();
	});

	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": 400,
        "scrollX": true,
        "orderClasses": false
	});
});
</script>
<div class="pageHeader">
<form id="viewPaItemManagerInfoForm" onsubmit="return navTabSearch(this);" action="/pa/salarycode/viewPaItemManagerInfo" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>工资项目</td>
		<td>
			<select name="seach_PA_ITEM">
				<c:forEach items="${getPaItemList}" var="item" varStatus="i">
				<option value="${item.ITEM_NO }" <c:if test="${item.ITEM_NO eq PA_ITEM }">selected</c:if>>${item.DESCR }</option>
				</c:forEach>
			</select>
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
				<a class="buttonActive" id="viewPaItemManagerInfo_search" href="#"><span>查询</span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="openOnRight('/pa/salarycode/viewAddPaItemInfo?SEQ=0','viewPaItemManagerInfo_unit');" href="#">
					<span>添加</span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeletepaItemInfoCallback('viewAddPaItemInfo',navTabAjaxDone)" href="#"><span>删除</span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddpaItemInfoCallback('viewAddPaItemInfo',navTabAjaxDone)" href="#"><span>保存</span></a>
			</li>
			<li>
				<a class="delete" onclick="downloadExcel('viewPaItemManagerInfoForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=33','/pa/salarycode/viewPaItemManagerInfo')" href="#"><span>导出到Excel</span></a>					
			</li>
	</ul>
</div>

	<div id="viewPaItemManagerInfo_left" style="float:left; display:block; overflow:auto;width:430px; height:450px; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${orgPaItemSize}</div>
		<table class="list" width="1400px;">
			<thead>
				<tr>
					<th width="25px;">No.</th>
					<th width="80px">代码</th>
					<th width="100px">代码名称</th>
					<th width="100px">工资区分</th>
					<th width="100px">状态</th>
					<th width="100px">开始日期</th>
					<th width="100px">废除日期</th>
					<th width="50px">变更者</th>
					<th width="50px">变更时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewPaItemList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/pa/salarycode/viewAddPaItemInfo?SEQ=${item.SEQ}','viewPaItemManagerInfo_unit');">
						<td>${i.count}</td>
						<td>${item.PA_ITEM_SEQ}</td>
						<td>${item.ITEM_NAME}</td>
						<td>${item.PA_DES}</td>
						<td>${item.ACTIVITY_NAME}</td>
						<td>${item.START_DATE}</td>
						<td>${item.END_DATE}</td>
						<td>${item.UPDATED_BY}</td>
						<td>${item.UPDATE_DATE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewPaItemManagerInfo_unit','viewPaItemManagerInfo_left')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewPaItemManagerInfo_unit','viewPaItemManagerInfo_left')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewPaItemManagerInfo_left','viewPaItemManagerInfo_unit')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewPaItemManagerInfo_left')"></div>
	</div>
	<div id="viewPaItemManagerInfo_unit"  style="display:block;">
	</div>
</div>