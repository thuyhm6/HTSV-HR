<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	//查询
	$("#viewResumeList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewResumeListForm",navTab.getCurrentPanel()).submit();
	});
	$("#seach_EXPERIENCE_TYPE",navTab.getCurrentPanel()).change(function(){
		$("#viewResumeListForm",navTab.getCurrentPanel()).submit();
	});
	
	openOnRight('/org/orgManage/viewAddResumeInfo?SEQ=${SEQ}','viewResumeList_unit');
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


<!--<div class="pageHeader">
<form id="viewResumeListForm" onsubmit="return navTabSearch(this);" action="/org/orgManage/viewResumeList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="org.title.EXPERIENCE_TYPE" /> 变更类型 </td>
		<td>
		 	<ait:SelectSyCodeByCpnyID id="seach_EXPERIENCE_TYPE" name="seach_EXPERIENCE_TYPE" parentNo="14013935" selected="${EXPERIENCE_TYPE }" limit="all"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>

--><div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
			<!--<li>
				<a class="buttonActive" id="viewResumeList_Serch" href="#">
					<span><spring:message code="org.title.SELECT" /> 查询 </span>
				</a>
			</li>
			--><li>
				<a class="buttonActive" onclick="openOnRight('/org/orgManage/viewAddResumeInfo?SEQ=0','viewResumeList_unit');" href="#">
					<span><spring:message code="org.title.INSERT" /><!-- 添加 --></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteResumeInfoCallback('viewAddResumeInfo',navTabAjaxDone)" href="#"><span><spring:message code="org.title.DELETE" /><!-- 删除 --></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddResumeInfoCallback('viewAddResumeInfo',navTabAjaxDone)" href="#"><span><spring:message code="org.title.SAVE" /><!-- 保存 --></span></a>
			</li>
			<!--<li>
				<a class="add" onclick="print();" href="#"><span><spring:message code="org.title.PRINT" /> 打印 </span></a>
			</li>
			--><li>
				<a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=5"><span><spring:message code="org.title.exportLOtImportExcel" /><!-- 导出到EXECL --></span></a>					
			</li>
	</ul>
</div>
	<div id="viewResumeList_left" sysLong="printDiv" style="float:left; display:block; overflow:auto;width:430px; height:auto; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${orgResumeSize}</div>
		<table class="list" width="1400px;">
			<thead>
				<tr>
					<th width="30px;">No.</th>
					<th width="60px"><spring:message code="org.title.DATE" /><!-- 日期 --></th>
					<th width="80px"><spring:message code="org.orgManage.GAIBIAN_NAME.Z" /><!-- 组织改编名称 --></th>
					<th width="80px"><spring:message code="org.title.ORG_CHANGE_NO" /><!-- 组织变更代码 --></th>
					<!--<th width="80px"><spring:message code="org.title.EXPERIENCE_TYPE" /> 变更类型 </th>-->
					<th width="80px"><spring:message code="org.title.status" /><!-- 状态 --></th>
					<th width="80px"><spring:message code="org.title.IS_CURRENT_ORG" /><!-- 现组织与否 --></th>
					<th width="100px"><spring:message code="org.title.REMARK" /><!-- 备注 --></th>
					<th width="100px"><spring:message code="org.orgManage.GAIBIAN_YUANYIN.Z" /><!-- 改编原因 --></th>
					<th width="80px"><spring:message code="org.title.COPY_ORG_CODE" /><!-- 复制组织改编代码 --></th>
					<th width="120px"><spring:message code="org.title.UPDATED_IP" /><!-- 变更者 --></th>
					<th width="100px"><spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 --></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orgResumeList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/org/orgManage/viewAddResumeInfo?SEQ=${item.SEQ}','viewResumeList_unit');">
						<td class='td_center'>${i.count}</td>
						<td class='td_center'>${item.CHANGE_DATE}</td>
						<td>${item.RESUME_NAME}</td>
						<td>${item.NO}</td>
						<!--<td>${item.EXPERIENCE_TYPE_NAME}</td>
						--><td>${item.ACTIVITY_NAME}</td>
						<td>${item.IS_CURRENT_ORG}</td>
						<td>${item.REMARK}</td>
						<td>${item.CHANGE_REASON}</td>
						<td>${item.FROM_NO}</td>
						<td>${item.LOCAL_NAME} ${item.UPDATED_IP}</td>
						<td>${item.UPDATE_DATE}</td>
						<input type="hidden" name="ACTIVITY" id="ACTIVITY_${i.index }" value="${item.ACTIVITY}">
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewResumeList_unit','viewResumeList_left')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewResumeList_unit','viewResumeList_left')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewResumeList_left','viewResumeList_unit')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewResumeList_left')"></div>
	</div>
	<div id="viewResumeList_unit"  style="display:block;">
	</div>
</div>