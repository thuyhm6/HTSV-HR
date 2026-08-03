<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	openOnRight('/org/orgManage/viewAddWorkAreaInfo?SEQ=${SEQ}','viewWorkAreaManagerInfo_unit');
	$("#viewWorkAreaManagerInfo_search").click(function(){
		$("#viewWorkAreaManagerInfoForm").submit();
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
<form id="viewWorkAreaManagerInfoForm" onsubmit="return navTabSearch(this);" action="/org/orgManage/viewWorkAreaManagerInfo" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="org.title.WorkAreaInfo_CODE_NO" /><!-- 工作区域代码 --></td>
		<td><input type="text" name="seach_CODE_NO" value="${CODE_NO}" /></td>
		<td><spring:message code="org.title.WorkAreaInfo_CODE_NAME" /><!-- 工作区域名 --></td>
		<td><input type="text" name="seach_CODE_NAME" value="${CODE_NAME}" /></td>
		<td><spring:message code="org.title.status" /><!-- 状态 --></td>
		<td><ait:SelectSyCodeByCpnyID id="seach_ACTIVITY" name="seach_ACTIVITY" parentNo="14013911" selected="${ACTIVITY}"/></td>
	</tr>
</table>
</div>
</form>
</div>

<div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" id="viewWorkAreaManagerInfo_search" href="#"><span><spring:message code="org.title.SELECT" /><!-- 查询 --></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="openOnRight('/org/orgManage/viewAddWorkAreaInfo?SEQ=0','viewWorkAreaManagerInfo_unit');" href="#">
					<span><spring:message code="org.title.INSERT" /><!-- 添加 --></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteWorkAreaInfoCallback('viewAddWorkAreaInfo',navTabAjaxDone)" href="#"><span><spring:message code="org.title.DELETE" /><!-- 删除 --></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddWorkAreaInfoCallback('viewAddWorkAreaInfo',navTabAjaxDone)" href="#"><span><spring:message code="org.title.SAVE" /><!-- 保存 --></span></a>
			</li>
			<!--<li>
				<a class="add" href="#"><span>印刷</span></a>
			</li>
			--><li>
				<a class="delete" onclick="downloadExcel('viewWorkAreaManagerInfoForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=34','/org/orgManage/viewWorkAreaManagerInfo')" href="#"><span><spring:message code="org.title.exportLOtImportExcel" /><!-- 导出到EXECL --></span></a>					
			</li>
	</ul>
</div>

	<div id="viewWorkAreaManagerInfo_left" style="float:left; display:block; overflow:auto;width:430px; height:auto; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${orgWorkAreaSize}</div>
		<table class="list" width="1400px;">
			<thead>
				<tr>
					<th width="25px;">No.</th>
					<th width="50px"><spring:message code="org.title.WorkAreaInfo_CODE_NO" /><!-- 工作区域代码 --></th>
					<th width="50px"><spring:message code="org.title.WorkAreaInfo_CODE_NAME" /><!-- 工作区域名 --></th>
					<th width="50px"><spring:message code="org.title.status" /><!-- 状态 --></th>
					<th width="100px"><spring:message code="org.title.ADDRESS" /><!-- 地址 --></th>
					<th width="100px"><spring:message code="org.title.WorkAreaInfo_REMARK" /><!-- 工作区域说明 --></th>
					<th width="50px"><spring:message code="org.title.COUNTRY" /><!-- 国家 --></th>
					<th width="50px"><spring:message code="org.title.UPDATED_IP" /><!-- 变更者 --></th>
					<th width="50px"><spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 --></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orgWorkAreaList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/org/orgManage/viewAddWorkAreaInfo?SEQ=${item.SEQ}','viewWorkAreaManagerInfo_unit');">
						<td width="25px;" class='td_center'>${i.count}</td>
						<td width="50px" style="text-align:left">${item.CODE_NO}</td>
						<td width="50px" style="text-align:left">${item.CODE_NAME}</td>
						<td width="50px" style="text-align:left">${item.ACTIVITY_NAME}</td>
						<td width="100px" style="text-align:left">${item.ADDRESS}</td>
						<td width="100px" style="text-align:left">${item.REMARK}</td>
						<td width="50px" style="text-align:left">${item.COUNTRY_NAME}</td>
						<td width="50px" style="text-align:left">[${item.EMPID}]${item.LOCAL_NAME} ${item.UPDATED_IP}</td>
						<td width="50px" style="text-align:left">${item.UPDATE_DATE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewWorkAreaManagerInfo_unit','viewWorkAreaManagerInfo_left')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewWorkAreaManagerInfo_unit','viewWorkAreaManagerInfo_left')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewWorkAreaManagerInfo_left','viewWorkAreaManagerInfo_unit')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewWorkAreaManagerInfo_left')"></div>
	</div>
	<div id="viewWorkAreaManagerInfo_unit"  style="display:block;">
	</div>
</div>