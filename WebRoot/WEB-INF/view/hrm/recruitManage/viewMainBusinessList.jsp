<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	openOnRight('/hrm/recruitManage/viewAddMainBusinessInfo?CODE_NO=${item.CODE_NO}','viewMainBusinessList_unit');
});

$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
    "bAutoWidth":false,//表格宽度不自动变化
    "bProcessing":false,
	"bLengthChange": false,  //关闭按多少条记录显示下拉框
	"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	"bSort": true,   //关闭排序功能
	"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
	"bScrollInfinite":true,
	"scrollY": 470,
    "scrollX": true,
    "orderClasses": false
});
</script>

<div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" onclick="validateAddMainBusinessInfoCallback('viewAddMainBusinessInfo',navTabAjaxDone)" href="#"><span>保存</span></a>
			</li>
			<li>
				<a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=60"><span>导出到EXECL</span></a>					
			</li>
	</ul>
</div>
	<div id="viewMainBusinessList_left" style="float:left; display:block; overflow:auto;width:430px; height:520px; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${resumeSize}</div>
		<table class="list" width="900px;">
			<thead>
				<tr>
					<th width="30px;">No.</th>
					<th width="60px">CODE</th>
					<th width="80px">名称</th>
					<th width="150px">变更者</th>
					<th width="100px">变更时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resumeList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/hrm/recruitManage/viewAddMainBusinessInfo?CODE_NO=${item.CODE_NO}','viewMainBusinessList_unit');">
						<td class='td_center'>${i.count}</td>
						<td>${item.DESCRIPTION}</td>
						<td>${item.CODE_NAME}</td>
						<td>${item.UPDATED_BY}</td>
						<td>${item.UPDATE_DATE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewMainBusinessList_unit','viewMainBusinessList_left')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewMainBusinessList_unit','viewMainBusinessList_left')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewMainBusinessList_left','viewMainBusinessList_unit')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewMainBusinessList_left')"></div>
	</div>
	<div id="viewMainBusinessList_unit"  style="display:block;">
	</div>
</div>