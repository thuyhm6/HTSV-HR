<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	openOnRight('/evs/manage/viewAddResumeInfo?SEQ=${SEQ}&evsType=${evsType}','viewEvsResumeList_${evsType}_unit');
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

<div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" href="#" onclick="navTab.reload('/evs/manage/viewResumeList?evsType=${evsType }', { navTabId:'${navTabId}'});"><span><spring:message code="button.search"/><!--查询--></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="openOnRight('/evs/manage/viewAddResumeInfo?SEQ=0&evsType=${evsType}','viewEvsResumeList_${evsType}_unit');" href="#">
					<span><spring:message code="button.add"/><!--添加--></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteEvsResumeInfoCallback('viewAddEvsResumeInfo_${evsType}',navTabAjaxDone)" href="#"><span><spring:message code="button.delete"/><!--删除--></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddEvsResumeInfoCallback('viewAddEvsResumeInfo_${evsType}',navTabAjaxDone)" href="#"><span><spring:message code="button.sys.affirm.save"/><!--保存--></span></a>
			</li>
			<li>
				<a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=60&EVS_TYPE=${evsType}"><span><spring:message code="hrm.empinfo.EXPORT"/><!--导出到Excel--></span></a>					
			</li>
	</ul>
</div>
	<div id="viewEvsResumeList_${evsType}_left" sysLong="printDiv" style="float:left; display:block; overflow:auto;width:430px; height:auto; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${resumeSize}</div>
		<table class="list" width="1000px;">
			<thead>
				<tr>
					<th width="30px;">No.</th>
					<th width="100px"><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></th>
					<th width="80px"><spring:message code="evs.viewResumeList.PINGJIANIAN.a"/><!--评价年--></th>
					<th width="80px"><spring:message code="evs.viewResumeList.PINGJIAYUE.a"/><!--评价月--></th>
					<th width="80px"><spring:message code="evs.viewResumeList.GONGZUOJINGXINGZHUANGTAI.a"/><!--工作进行状态--></th>
					<th width="150px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
					<th width="100px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resumeList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/evs/manage/viewAddResumeInfo?SEQ=${item.SEQ}&evsType=${evsType}','viewEvsResumeList_${evsType}_unit');">
						<td class='td_center'>${i.count}</td>
						<td>${item.RESUME_NAME}</td>
						<td>${item.EVS_YEAR}</td>
						<td>${item.EVS_MONTH_NAME}</td>
						<td>
							<c:if test="${item.ACTIVITY eq 2}"><spring:message code="evs.viewResumeList.KAOHEZHUNBEIJIEDUAN.a"/><!--考核准备阶段--></c:if>
							<c:if test="${item.ACTIVITY eq 3}"><spring:message code="evs.viewResumeList.KAOHEJINXINGJIEDUAN.a"/><!--考核进行阶段--></c:if>
							<c:if test="${item.ACTIVITY eq 4}"><spring:message code="evs.viewResumeList.KAOHEWANCHENGJIEDUAN.a"/><!--考核完成阶段--></c:if>
						</td>
						<td>${item.UPDATED_BY}</td>
						<td>${item.UPDATE_DATE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewEvsResumeList_${evsType}_unit','viewEvsResumeList_${evsType}_left')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewEvsResumeList_${evsType}_unit','viewEvsResumeList_${evsType}_left')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewEvsResumeList_${evsType}_left','viewEvsResumeList_${evsType}_unit')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewEvsResumeList_${evsType}_left')"></div>
	</div>
	<div id="viewEvsResumeList_${evsType}_unit"  style="display:block;">
	</div>
</div>