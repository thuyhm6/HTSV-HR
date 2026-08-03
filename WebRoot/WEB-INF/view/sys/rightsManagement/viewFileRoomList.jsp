<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	//查询
	$("#viewFileRoomList_Serch").click(function(){
		$("#viewFileRoomListForm").submit();
	});
	openOnRight('/evs/manage/viewAddFileRoomInfo?SEQ=${SEQ}','viewFileRoomList_unit');
});

$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
    "bAutoWidth":false,//表格宽度不自动变化
    "bProcessing":false,
	"bLengthChange": false,  //关闭按多少条记录显示下拉框
	"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	"bSort": true,   //关闭排序功能
	"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
	"bScrollInfinite":true,
	"scrollY": $(document.body).height() - 200,
    "scrollX": true,
    "orderClasses": false
});
</script>


<div class="pageHeader">
<form id="viewFileRoomListForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewFileRoomList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
					<td>
						关键字
					</td>
					<td>
						<input type="text" name="seach_KEY" value="${KEY}" />
					</td>
					<td>
						期间
					</td>
					<td>
						<input type="text" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${START_DATE}"/>~
						<input type="text" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${END_DATE}"/>
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
				<a class="buttonActive" id="viewFileRoomList_Serch" href="#">
					<span><spring:message code="org.title.SELECT" /><!-- 查询 --></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="openOnRight('/evs/manage/viewAddFileRoomInfo?SEQ=0','viewFileRoomList_unit');" href="#">
					<span><spring:message code="org.title.INSERT" /><!-- 添加 --></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteFileRoomInfoCallback('viewAddFileRoomInfo',navTabAjaxDone)" href="#"><span><spring:message code="org.title.DELETE" /><!-- 删除 --></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddFileRoomInfoCallback('viewAddFileRoomInfo',navTabAjaxDone)" href="#"><span><spring:message code="org.title.SAVE" /><!-- 保存 --></span></a>
			</li>
	</ul>
</div>
	<div id="viewFileRoomList_left" sysLong="printDiv" style="float:left; display:block; overflow:auto;width:430px; height:auto; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${orgFileRoomSize}</div>
		<table class="list" width="400px;">
			<thead>
				<tr>
					<th width="30px;">No.</th>
					<th width="150px">标题</th>
					<th width="120px"><spring:message code="org.title.UPDATED_IP" /><!-- 变更者 --></th>
					<th width="100px">上传日期</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${fileRoomList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/evs/manage/viewAddFileRoomInfo?SEQ=${item.SEQ}','viewFileRoomList_unit');">
						<td class='td_center'>${i.count}</td>
						<td class='td_center'>${item.TITLE}</td>
						<td>${item.UPDATED_BY}</td>
						<td>${item.CREATE_DATE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewFileRoomList_unit','viewFileRoomList_left')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewFileRoomList_unit','viewFileRoomList_left')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewFileRoomList_left','viewFileRoomList_unit')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewFileRoomList_left')"></div>
	</div>
	<div id="viewFileRoomList_unit"  style="display:block;">
	</div>
</div>