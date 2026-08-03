<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	//查询
	$("#viewApplyAttenanceBatchInfoHAEList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewApplyAttenanceBatchInfoHAEListForm",navTab.getCurrentPanel()).submit();
	});
	
	//openOnRight('/ar/arShiftGroupManagement/viewArShiftRecordCheckInfoDetail?PERSON_ID=${viewArShiftRecordCheckList[0].PERSON_ID}','viewArShiftRecordCheckInfoDetail');
	openOnRight('/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoHAEDetail?BATCH_NO=${applyAttenanceBatchInfoHAEList[0].BATCH_NO}','viewApplyAttenanceBatchInfoHAEDetail');
	
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewApplyAttenanceBatchInfoHAEList&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
       	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewApplyAttenanceBatchInfoHAEList&seach_KEY='+name);
    });	

	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": $(document.body).height() - 240,
	    "scrollX": true,
	    "orderClasses": false
	});
});
</script>


<div class="pageHeader">
<form id="viewApplyAttenanceBatchInfoHAEListForm" onsubmit="return navTabSearch(this);" action="/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoHAEList" method="post">
<input type="hidden" value="ar" name="limit" />
<input type="hidden" value="1" name="firstView" />
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>
			<spring:message code="ess.workgroup.title.duration"/>
		</td>
		<td>
		    <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" value="${FROM_DATE}"/>
		~
		     <input type="text" name="seach_TO_DATE" id="seach_TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" value="${TO_DATE}"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>

<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewApplyAttenanceBatchInfoHAEList_Serch" href="#"><span><!-- 查询 --><spring:message code="ess.infoApply.SELECT" /></span></a></li>
	    <li>
			<a class="buttonActive" onclick="delBatchLeaveApplyCallback(3,'delBatchLeaveApplyAffirmForm',DWZ.ajaxDone)" href="#"><span><spring:message code="ess.affirmApply.title.quxiaoshenqing" /><!--取消申请--></span></a>					
		</li>
	</ul>
</div>

<div class="pageContent">
	<div id="viewApplyAttenanceBatchInfoHAEList" style="float:left; display:block; overflow:auto;width:300px; height:460px; border:solid 1px #CCC; line-height:21px; background:#fff">
		<table class="list"  id="daTable" style="width:300px;">
			<thead>
				Total:${applyAttenanceBatchInfoHAEListSize }
				<tr>
					<th width="2%">No.</th>
					<th ><!-- 申请日期 --><spring:message code="ess.empInfo.date_application" /></th>					
					<th><!-- 批量申请数量 --><spring:message code="ess.viewApplyAttendanceBatchInfoDetail.SHULIANG.b" /></th>
					<th><!-- 代申请人 --><spring:message code="ar.viewLeaveConfirmList.DAISHENQINGREN.b" /></th>
					<!--<th > 职级 <spring:message code="sys.postManage.title.postGrade" /></th>
					<th > 班次类型 <spring:message code="ar.viewshift.title.bancileixing" /></th>
					-->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${applyAttenanceBatchInfoHAEList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoHAEDetail?BATCH_NO=${item.BATCH_NO}','viewApplyAttenanceBatchInfoHAEDetail');">
						<td style="td_center">${i.count}</td>
						<td class='td_center' >${item.CREATE_DATE}</td>
						<td class='td_center' >${item.BATCH_NUM}</td>
						<td style="td_center">${item.CREATED_BY}</td>
						<!--<td class='td_center' >${item.POST_GRADE}</td>-->
						<!--<td class='td_center' >${item.SHIFT_NAME}</td>-->
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<!--<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewApplyAttenanceBatchInfoHAEDetail','viewApplyAttenanceBatchInfoHAEList')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewApplyAttenanceBatchInfoHAEDetail','viewApplyAttenanceBatchInfoHAEList')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewApplyAttenanceBatchInfoHAEList','viewApplyAttenanceBatchInfoHAEDetail')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewApplyAttenanceBatchInfoHAEList')"></div>
	    -->
	</div>
	<div id="viewApplyAttenanceBatchInfoHAEDetail"  style="display:block;">
	</div>
</div>