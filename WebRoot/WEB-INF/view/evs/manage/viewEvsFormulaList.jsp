<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	openOnRight('/evs/manage/viewAddEvsFormulaInfo?SEQ=${SEQ}','viewEvsFormulaManagerInfo_unit');
	$("#viewEvsFormulaManagerInfo_search").click(function(){
		$("#viewEvsFormulaManagerInfoForm").submit();
	});
	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": 500,
        "scrollX": true,
        "orderClasses": false
	});
});
</script>
<div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" onclick="openOnRight('/evs/manage/viewAddEvsFormulaInfo?SEQ=0','viewEvsFormulaManagerInfo_unit');" href="#">
					<span><spring:message code="button.add"/><!--添加--></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteEvsFormulaInfoCallback('viewAddEvsFormulaInfo',navTabAjaxDone)" href="#"><span><spring:message code="button.delete"/><!--删除--></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddEvsFormulaInfoCallback('viewAddEvsFormulaInfo',navTabAjaxDone)" href="#"><span><spring:message code="button.sys.affirm.save"/><!--保存--></span></a>
			</li>
	</ul>
</div>

	<div id="viewEvsFormulaManagerInfo_left" style="float:left; display:block; overflow:auto;width:430px; height:550px; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${orgEvsFormulaSize}</div>
		<table class="list" width="700px;">
			<thead>
				<tr>
					<th width="25px;">No.</th>
					<th width="80px"><spring:message code="sys.arAffirmPost.title.code"/><!--代码--></th>
					<th width="150px"><spring:message code="ar.viewRetrieveSqlMasterList.MINGCHENG.b"/> <!--名称--></th>
					<th width="200px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
					<th width="100px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${EvsFormulaList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/evs/manage/viewAddEvsFormulaInfo?SEQ=${item.SEQ}','viewEvsFormulaManagerInfo_unit');">
						<td class='td_center'>${i.count}</td>
						<td style="text-align:left">${item.CODE_NO}</td>
						<td style="text-align:left">${item.CODE_NAME}</td>
						<td style="text-align:left">${item.UPDATED_BY}</td>
						<td style="text-align:left">${item.UPDATE_DATE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewEvsFormulaManagerInfo_unit','viewEvsFormulaManagerInfo_left')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewEvsFormulaManagerInfo_unit','viewEvsFormulaManagerInfo_left')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewEvsFormulaManagerInfo_left','viewEvsFormulaManagerInfo_unit')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewEvsFormulaManagerInfo_left')"></div>
	</div>
	<div id="viewEvsFormulaManagerInfo_unit"  style="display:block;">
	</div>
</div>