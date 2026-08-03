<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	//查询
	$("#viewArShiftRecordCheckList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewArShiftRecordCheckListForm",navTab.getCurrentPanel()).submit();
	});
	
	openOnRight('/ar/arShiftGroupManagement/viewArShiftRecordCheckInfoDetail?PERSON_ID=${viewArShiftRecordCheckList[0].PERSON_ID}','viewArShiftRecordCheckInfoDetail');
	
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewArShiftRecordCheckList&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
       	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewArShiftRecordCheckList&seach_KEY='+name);
    });	

	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": $(document.body).height() - 300,
	    "scrollX": true,
	    "orderClasses": false
	});
});

/*function searchPop_ar0607(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));
	var refreshUrl = '/ar/arShiftGroupManagement/viewArShiftRecordCheckList?firstView=1';
	var refreshMenuCode = 'ar0607';
	var refreshMenuName = encodeURI(encodeURI('班组履历的查询'));
	$("#searchPop_ar0607", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name
							+ '&refreshUrl='
							+ refreshUrl
							+ '&refreshMenuCode='
							+ refreshMenuCode
							+ '&refreshMenuName=' + refreshMenuName);
	if (flag == 'onkeyup')
		$("#searchPop_ar0607", navTab.getCurrentPanel()).click();
}*/
</script>


<div class="pageHeader">
<form id="viewArShiftRecordCheckListForm" onsubmit="return navTabSearch(this);" action="/ar/arShiftGroupManagement/viewArShiftRecordCheckList" method="post">
<input type="hidden" value="ar" name="limit" />
<input type="hidden" value="1" name="firstView" />
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /></td>
		<td>
		    <div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}" /></div>
			<div style="float:left"><a class="btnLook" href="#" lookupGroup="person" id="searchPop_ar0607" ></a></div>
			<!--
			<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}" onkeydown="javascript:if(event.keyCode == 13)searchPop_ar0607('onkeyup');"/></div>
			<div style="float:left"><a class="btnLook" href="#" lookupGroup="person" id="searchPop_ar0607" onclick="searchPop_ar0607()"></a></div>
			-->
		</td>
		<td></td>
	</tr>
	<tr>
		<td><!-- 班组类型 --><spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" /></td>
		<td><ait:SelectSyCodeByCpnyID name="seach_SHIFT_NO"
				parentNo="400223" cnpyID="${LoginUser.cpnyId}" limit="all"
				selected="${SHIFT_NO }" />
		</td>
		<td><!-- 职级 --><spring:message code="org.title.POST_GRADE_NAME" /></td>
		<td>
			<ait:SelectSyCodeCombinByCpnyID name="seach_POST_GRADE_NO" combinParentNo="14015813,14015815" 
			cnpyID="${LoginUser.cpnyId}"  limit="all" selected="${POST_GRADE_NO }"/>
		</td>
		<td><!-- 员工类型 --><spring:message code="ess.infoApply.employee_type" /></td>
		<td><ait:SelectSyCodeByCpnyID name="seach_EMP_TYPE_CODE"
				parentNo="13864" cnpyID="${LoginUser.cpnyId}" limit="all"
				selected="${EMP_TYPE_CODE }" />
		</td>
	</tr>
    <tr>
        <td>
			<!-- 部门： --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
		</td>
		<td><ait:deptList name="seach_DEPTNO"
				cpnyId="${LoginUser.cpnyId}" limit="ar"
				id="viewArShiftRecordCheckList_seachDept" /> <ait:deptTreeIcon
				name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="ar"
				id="viewArShiftRecordCheckList_seachDept" selected="${DEPTNO}" /></td>
        <td><!--<input name="seach_EMP_OFFICE"  value="Y" type="checkbox" <c:if test="${EMP_OFFICE == 'Y' }">checked</c:if>/>退休者包括与否--></td>
    </tr>
</table>
</div>
</form>
</div>

<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewArShiftRecordCheckList_Serch" href="#"><span><!-- 查询 --><spring:message code="ess.infoApply.SELECT" /></span></a></li>
	</ul>
</div>

<div class="pageContent">
	<div id="viewArShiftRecordCheckList" style="float:left; display:block; overflow:auto;width:430px; height:450px; border:solid 1px #CCC; line-height:21px; background:#fff">
		<table class="list"  id="daTable" style="width:600px;">
			<thead>
				Total:${viewArShiftRecordCheckListSize }
				<tr>
					<th width="2%">No.</th>
					<th><!-- 工号 --><spring:message code="ess.infoApply.EMP_ID" /></th>
					<th><!-- 姓名 --><spring:message code="public.title.name" /></th>
					<th ><!-- 部门名 --><spring:message code="ess.infoApply.DEPT_NAME" /></th>
					<th ><!-- 职级 --><spring:message code="sys.postManage.title.postGrade" /></th>
					<th ><!-- 班次类型 --><spring:message code="ar.viewshift.title.bancileixing" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewArShiftRecordCheckList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/ar/arShiftGroupManagement/viewArShiftRecordCheckInfoDetail?PERSON_ID=${item.PERSON_ID}','viewArShiftRecordCheckInfoDetail');">
						<td style="text-align:left">${i.count}</td>
						<td class='td_center' >${item.EMPID}</td>
						<td style="text-align:left">${item.LOCAL_NAME}</td>
						<td class='td_center' >${item.DEPTNAME}</td>
						<td class='td_center' >${item.POST_GRADE}</td>
						<td class='td_center' >${item.SHIFT_NAME}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewArShiftRecordCheckInfoDetail','viewArShiftRecordCheckList')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewArShiftRecordCheckInfoDetail','viewArShiftRecordCheckList')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewArShiftRecordCheckList','viewArShiftRecordCheckInfoDetail')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewArShiftRecordCheckList')"></div>
	</div>
	<div id="viewArShiftRecordCheckInfoDetail"  style="display:block;">
	</div>
</div>