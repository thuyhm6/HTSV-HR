<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	openOnRight('/hrm/empinfo/viewAddResumeInfo?SEQ=${SEQ}','viewHrResumeList_unit');
	$("#viewHrResumeList_search",navTab.getCurrentPanel()).click(function(){
		$("#viewHrResumeListForm",navTab.getCurrentPanel()).submit();
	});
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

</script>

<div class="pageHeader">
<form id="viewHrResumeListForm" onsubmit="return navTabSearch(this);" action="/hrm/empinfo/viewResumeList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="hrm.empinfo.Keyword"/><!-- 关键字 --></td>
		<td>
			<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
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
			<a class="buttonActive" href="#"  id="viewHrResumeList_search">
			<span><spring:message code="button.search"/><!-- 查询 --></span></a>
		</li>
		<li>
			<a class="buttonActive" onclick="openOnRight('/hrm/empinfo/viewAddResumeInfo?SEQ=0','viewHrResumeList_unit');" href="#">
				<span><spring:message code="button.add"/><!-- 添加 --></span>
			</a>
		</li>
		<li>
			<a class="buttonActive" onclick="validateDeleteHrResumeInfoCallback('viewAddHrResumeInfo',navTabAjaxDone)" href="#">
			<span><spring:message code="button.delete"/><!-- 删除 --></span></a>
		</li>
		<li>
			<a class="buttonActive" onclick="validateAddHrResumeInfoCallback('viewAddHrResumeInfo',navTabAjaxDone)" href="#">
			<span><spring:message code="button.sys.affirm.save"/><!-- 保存 --></span></a>
		</li>
		<li>
			<a class="delete" onclick="downloadExcel('viewHrResumeListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=193','/hrm/empinfo/viewResumeList')" href="#">
			<span><spring:message code="hrm.empinfo.EXPORT"/><!-- 导出到EXECL --></span></a>					
		</li>
	</ul>
</div>
	<div id="viewHrResumeList_left" sysLong="printDiv" style="float:left; display:block; overflow:auto;width:430px; height:auto; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${resumeSize}</div>
		<table class="list" width="1000px;">
			<thead>
				<tr>
					<th width="30px;">No.</th>
					<th width="80px"><spring:message code="hrm.empinfo.name"/><!-- 姓名 --></th>
					<th width="60px"><spring:message code="hrm.empinfo.SEXCODE"/><!-- 性别 --></th>
					<th width="60px"><spring:message code="hrm.empinfo.AGE"/><!-- 年龄 --></th>
					
					<th width="120px"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!-- 部门 --></th>
					<th width="80px"><spring:message code="hrm.empinfo.FAM_PHONE"/><!-- 联系电话 --></th>
					<c:if test="${LoginUser.cpnyId ne 'SPC_NJ'}">
					<th width="170px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!-- 变更者 --></th>
					<th width="100px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!-- 变更时间 --></th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resumeList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/hrm/empinfo/viewAddResumeInfo?SEQ=${item.SEQ}','viewHrResumeList_unit');">
						<td class='td_center'>${i.count}</td>
						<td>${item.TITLE}</td>
						<td>${item.SEX_NAME}</td>
						<td>${item.AGE}</td>
						<td>${item.DEPT_NAME}</td>
						<td>${item.PHONE}</td>
						<c:if test="${LoginUser.cpnyId ne 'SPC_NJ'}">
						<td>${item.UPDATED_BY}</td>
						<td>${item.UPDATE_DATE}</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewHrResumeList_unit','viewHrResumeList_left')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewHrResumeList_unit','viewHrResumeList_left')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewHrResumeList_left','viewHrResumeList_unit')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewHrResumeList_left')"></div>
	</div>
	<div id="viewHrResumeList_unit"  style="display:block;">
	</div>
</div>