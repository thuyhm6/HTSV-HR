<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewExperienceList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewExperienceListForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewExperienceList_SEQ").change(function(){
		$("#viewExperienceListForm",navTab.getCurrentPanel()).submit();
	});
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewExperienceList&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
       	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewExperienceList&seach_KEY='+name);
    });

	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": $(document.body).height() - 370,
        "scrollX": true,
        "orderClasses": false
	});
});
</script>
<div class="pageHeader">
<form id="viewExperienceListForm" onsubmit="return navTabSearch(this);"
	action="/hrm/recruitManage/viewExperienceList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td width="7%"><spring:message code="hrm.empinfo.nameAndEmpid"/><!-- 社号/姓名 --></td>
		<td width="23%">
		<div style="float: left"><input type="text" name="seach_KEY"
			id="seach_KEY" value="${KEY}" /></div>
		<div style="float: left"><a class="btnLook" href=""
			lookupGroup="person"></a></div>
		</td>
		<td width="20%"><c:if test="${not empty personInfo}">
			<span style="margin-left: 50px;">${personInfo.LOCAL_NAME
			}&nbsp/&nbsp${personInfo.EMPID
			}&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME
			}</span>
		</c:if></td>
		<td width="20%"></td>
		
	</tr>
	<tr>
		<td><spring:message code="hrm.empinfo.starter_START_DATE"/><!-- 发令日期 --></td>
		<td><input type="text" id="seach_START_DATE"
			name="seach_START_DATE" class="Wdate"
			onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE }" />~
		<input type="text" id="seach_END_DATE" name="seach_END_DATE"
			class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
			value="${END_DATE }" /></td>
		<td><spring:message code="org.title.EXPERIENCE_TYPE_NAME"/><!-- 发令区分 --></td>
		<td><ait:selectCodeMulti id="seach_TRANS_CODE_Multi"
			name="seach_TRANS_CODE_NAME" parentNo="14013956"
			selected="${TRANS_CODE_Multi}" selectedNm="${TRANS_CODE_NAME}" /></td>
		<td><spring:message code="org.title.reason"/><!-- 发令原因 --></td>
		<td><ait:selectCodeMulti id="seach_TRANS_REASON_Multi" name="seach_TRANS_REASON_NAME" parentNo="14013956" sonFlag="1" selected="${TRANS_REASON_Multi}" selectedNm="${TRANS_REASON_NAME}"/>
		</td>
	</tr>
	<tr>
		<td><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!-- 部门 --></td>
		<td><ait:deptList name="seach_DEPTNO" limit="hr"
			id="viewExperienceList_seachDept" /> <ait:deptTreeIcon
			name="seach_DEPTNO" limit="hr" id="viewExperienceList_seachDept"
			selected="${DEPTNO}" /> <input type="checkbox" name="seach_SON_FLAG"
			value="1" <c:if test="${SON_FLAG eq '1' }">checked="checked"</c:if>>
			<spring:message code="hrm.empinfo.Department_include"/><!-- 下位部门包括 -->
		</td>
		<td><spring:message code="hrm.empinfo.POST_FAMILY"/><!-- 职群 --></td>
		<td><ait:selectCodeMulti id="seach_POST_FAMILY_Multi"
			name="seach_POST_FAMILY_NAME" parentNo="14015812"
			selected="${POST_FAMILY_Multi}" selectedNm="${POST_FAMILY_NAME}" /></td>
		<td><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME"/><!-- 员工类型 --></td>
		<td><ait:selectCodeMulti id="seach_EMP_TYPE_CODE_Multi"
			name="seach_EMP_TYPE_CODE_NAME" parentNo="13864"
			selected="${EMP_TYPE_CODE_Multi}" selectedNm="${EMP_TYPE_CODE_NAME}" /></td>
	</tr>
	<tr>
		
		<td><spring:message code="ess.infoApply.renzhizhuangtai"/><!-- 任职状态 --></td>
		<td><ait:selectCodeMulti id="seach_EMP_OFFICE_Multi"
			name="seach_EMP_OFFICE_NAME" parentNo="15118"
			selected="${EMP_OFFICE_Multi}" selectedNm="${EMP_OFFICE_NAME}" /></td>
		<td><spring:message code="hrm.empinfo.DATE_STARTED"/><!-- 入社日期 --></td>
		<td><input type="text" id="seach_START_DATE_JOIN"
			name="seach_START_DATE_JOIN" class="Wdate"
			onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
			value="${START_DATE_JOIN }" />~ <input type="text"
			id="seach_END_DATE_JOIN" name="seach_END_DATE_JOIN" class="Wdate"
			onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
			value="${END_DATE_JOIN }" /></td>
	</tr>
	
</table>
</div>
</form>
</div>
<div class="formBar">
<ul class="toolBar">
	<li><a class="buttonActive" id="viewExperienceList_Serch" href="#">
	<span><spring:message code="button.search"/><!-- 查询 --></span></a></li>
	<li>
	<!--<a class="delete" onclick= downloadExcel('viewExperienceListForm', '/hrm/recruitManage/viewExperienceListExport',
			'/hrm/recruitManage/viewExperienceList');>
			--><a class="buttonActive"
						href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=151">
						<span><spring:message code="hrm.empinfo.EXPORT"/><!-- 导出到EXECL --></span></a></li>
</ul>
</div>
<div class="pageContent">
<div class="user_table" style="font: bold 12px/ 20px arial, sans-serif;">Total:${viewExperienceListCnt}</div>
<table class="list" width="1300px;">
	<thead>
		<tr>
			<th width="30px">No.</th>
			<th width="50px"><spring:message code="hrm.empinfo.starter_START_DATE"/><!-- 发令日期 --></th>
			<th width="50px"><spring:message code="org.title.EXPERIENCE_TYPE_NAME"/><!-- 发令区分 --></th>
			<th width="50px"><spring:message code="hrm.empinfo.TRANS_REASON"/><!-- 发令原因 --></th>
			<th width="50px"><spring:message code="hrm.empinfo.name"/><!-- 姓名 --></th>
			<th width="50px"><spring:message code="hrm.empinfo.empid"/><!-- 社号 --></th>
			<th width="50px"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!-- 部门 --></th>
			<th width="50px"><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME"/><!-- 员工类型 --></th>
			<th width="50px"><spring:message code="hrm.contract.Rank"/><!-- 职级 --></th>
			<th width="50px"><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME"/><!-- 主要业务 --></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${viewExperienceList}" var="item" varStatus="i">
			<tr target="SEQ" rel="${item.SEQ}">
				<td class='td_center'>${i.count}</td>
				<td>${item.START_DATE}</td>
				<td>${item.TRANS_CODE_NAME}</td>
				<td>${item.TRANS_RESOURCE_NAME}</td>
				<td>${item.LOCAL_NAME}</td>
				<td>${item.EMPID}</td>
				<td>${item.DEPTNO_NAME}</td>
				<td>${item.EMP_TYPE_CODE_NAME}</td>
				<td>${item.POST_GRADE_NO_NAME}</td>
				<td>${item.MAIN_BUSINESS_NAME}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
