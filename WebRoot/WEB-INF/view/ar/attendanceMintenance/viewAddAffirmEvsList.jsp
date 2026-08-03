<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
<c:if test="${personidStr eq 'viewScheduleInfo'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
		  $("[id='EMPID_${empidStr}']",navTab.getCurrentPanel()).html(localName);
		  $("[id='EMPID_${empidStr}']",navTab.getCurrentPanel()).attr("sysPersonId",personId);
		  $("[id='EMPID_${empidStr}']",navTab.getCurrentPanel()).attr("sysEmpId",empId);
		  $("[id='POST_GRADE_${empidStr}']",navTab.getCurrentPanel()).html(postGradeNo);
		$.pdialog.closeCurrent();
	}
</c:if>
<c:if test="${personidStr eq 'viewEvsAffInfo'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	$("#affirmor${empidStr}",navTab.getCurrentPanel()).html(localName);
	$("#affirmorGrade${empidStr}",navTab.getCurrentPanel()).html(postGradeNo);
	$("#affirmor${empidStr}",navTab.getCurrentPanel()).attr("sysPersonId",personId);
	$.pdialog.closeCurrent();
}
</c:if>
<c:if test="${personidStr eq 'viewAddEvsAffInfo'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	$("#affirmor${empidStr}_key").val(localName);
	$("#affirmor${empidStr}_personId").val(personId);
	$("#affirmor${empidStr}_post").html(postGradeNo);
	$.pdialog.closeCurrent();
}
</c:if>
<c:if test="${personidStr eq 'applyAttendance'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	var index = $("#index").val();
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).html(empId);
	$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html(deptName);
	$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html(localName);
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',personId);
	$("#DUTY_NO_"+index,navTab.getCurrentPanel()).val(dutyNo);
	$("#SHIFT_NAME_"+index,navTab.getCurrentPanel()).html(shiftName);
	getAffirmor_ess3401(index);
	$.pdialog.closeCurrent();
}
</c:if>
<c:if test="${personidStr eq 'applyAttendance1'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	var index = $("#index").val();
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).html(empId);
	$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html(deptName);
	$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html(localName);
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',personId);
	$("#DUTY_NO_"+index,navTab.getCurrentPanel()).val(dutyNo);
	$("#SHIFT_NAME_"+index,navTab.getCurrentPanel()).html(shiftName);
	getAffirmor_ar0230(index);
	$.pdialog.closeCurrent();
}
</c:if>
<c:if test="${personidStr eq 'applyAttendanceApprover'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	var index = $("#index").val();
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).html(empId);
	$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html(deptName);
	$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html(localName);
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',personId);
	$("#DUTY_NO_"+index,navTab.getCurrentPanel()).val(dutyNo);
	$("#SHIFT_NAME_"+index,navTab.getCurrentPanel()).html(shiftName);
	getAffirmor_ess3469(index);
	$.pdialog.closeCurrent();
}
</c:if>
<c:if test="${personidStr eq 'applyAttManagementApprover'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	var index = $("#index").val();
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).html(empId);
	$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html(deptName);
	$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html(localName);
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',personId);
	$("#DUTY_NO_"+index,navTab.getCurrentPanel()).val(dutyNo);
	$("#SHIFT_NAME_"+index,navTab.getCurrentPanel()).html(shiftName);
	getAffirmor_ar0234(index);
	$.pdialog.closeCurrent();
}
</c:if>
<c:if test="${personidStr eq 'applyOt'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	var index = $("#index").val();
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).html(empId);
	$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html(deptName);
	$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html(localName);
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',personId);
	$("#OT_DUTY_NO_"+index,navTab.getCurrentPanel()).val(dutyNo);
	$("#OT_POST_FAMILY_"+index,navTab.getCurrentPanel()).val(postFamilyNo);
	$("#SHIFT_NAME_"+index,navTab.getCurrentPanel()).html(shiftName);
	getAffirmor_ess3403(index);
	$.pdialog.closeCurrent();
}
</c:if>
<c:if test="${personidStr eq 'applyOt1'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	var index = $("#index").val();
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).html(empId);
	$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html(deptName);
	$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html(localName);
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',personId);
	$("#OT_DUTY_NO_"+index,navTab.getCurrentPanel()).val(dutyNo);
	$("#OT_POST_FAMILY_"+index,navTab.getCurrentPanel()).val(postFamilyNo);
	$("#SHIFT_NAME_"+index,navTab.getCurrentPanel()).html(shiftName);
	getAffirmor_ar0701(index);
	$.pdialog.closeCurrent();
}
</c:if>
<c:if test="${personidStr eq 'applyOtApprover'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	var index = $("#index").val();
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).html(empId);
	$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html(deptName);
	$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html(localName);
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',personId);
	$("#OT_DUTY_NO_"+index,navTab.getCurrentPanel()).val(dutyNo);
	$("#SHIFT_NAME_"+index,navTab.getCurrentPanel()).html(shiftName);
	getAffirmor_ess3470(index);
	$.pdialog.closeCurrent();
}
</c:if>
<c:if test="${personidStr eq 'applyOtManagementApprover'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	var index = $("#index").val();
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).html(empId);
	$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html(deptName);
	$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html(localName);
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',personId);
	$("#OT_DUTY_NO_"+index,navTab.getCurrentPanel()).val(dutyNo);
	$("#SHIFT_NAME_"+index,navTab.getCurrentPanel()).html(shiftName);
	getAffirmor_ar0708(index);
	$.pdialog.closeCurrent();
}
</c:if>
<c:if test="${personidStr eq 'applyOtAd'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	var index = $("#index").val();
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).html(empId);
	$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html(deptName);
	$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html(localName);
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',personId);
	$("#DUTY_NO_"+index,navTab.getCurrentPanel()).val(dutyNo);
	ad_callength(index,1);
	getAffirmor_ar0706(index);
	$.pdialog.closeCurrent();
}
</c:if>
<c:if test="${personidStr eq 'org'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	var index = $("#index").val();
	$("#viewDeptManagerCheck_EMP_ID_DIV_"+ index ,navTab.getCurrentPanel()).html(empId);
	$("#viewDeptManagerCheck_BUSINESS_NAME_"+ index ,navTab.getCurrentPanel()).html(postGradeNo);
	$("#viewDeptManagerCheck_POST_GRADE_NAME_"+ index ,navTab.getCurrentPanel()).html(positionName);
	$("#viewDeptManagerCheck_EMP_NAME_"+ index ,navTab.getCurrentPanel()).html(localName);
	$("#viewDeptManagerCheck_EMP_OFFICE_NAME_"+ index ,navTab.getCurrentPanel()).html(statusName);
	$("#viewDeptManagerCheck_EMP_NAME_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',personId);
	$("#viewDeptManagerCheck_PERSON_ID_"+ index ,navTab.getCurrentPanel()).val(personId);
	$("#viewDeptManagerCheck_vacancy_" + index).removeAttr("checked");
	$.pdialog.closeCurrent();
}
</c:if>
<c:if test="${personidStr eq 'contract'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	var index = $("#index").val();
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).html(empId);
	$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html(deptName);
	$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html(localName);
	$("#POSITION_NAME_"+ index ,navTab.getCurrentPanel()).html(positionName);
	$("#POST_GRADE_"+ index ,navTab.getCurrentPanel()).html(postGradeNo);
	$("#DATE_STARTED_"+ index ,navTab.getCurrentPanel()).html(dateStarted);
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',personId);
	$.pdialog.closeCurrent();
}
</c:if>
<c:if test="${personidStr eq 'fixOt'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName,positionName,statusName,postFamilyNo,postFamily,dateStarted,dutyNo,shiftName){
	var index = $("#index").val();
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).html(empId);
	$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html(deptName);
	$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html(localName);
	$("#PERSON_ID_"+ index ,navTab.getCurrentPanel()).html(personId);
	$.pdialog.closeCurrent();
}
</c:if>
</script>
<div class="pageHeader">
	<form method="post" action="/ar/attendanceMintenance/viewAddAffirmEvsList" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 关键字 --><spring:message code="ar.viewkeeperlist.title.keyword"/></td>
				<td><input type="text" name="seach_KEY" value="${KEY}"/>
				<input type="hidden" name="limit" value="${limit}"/></td>
				<td><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></td>
				<td>
					<ait:deptList name="seach_DEPTNO_TREE" limit="${limit}" id="viewAddAffirmList_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO_TREE" limit="${limit}" id="viewAddAffirmList_seachDept" selected="${DEPTNO_TREE}"/>
					<input type="hidden" name="empidStr" value="${empidStr}"/>
					<input type="hidden" name="personidStr" value="${personidStr}"/>
					<input type="hidden" name="index" id="index" value="${index }">
				</td>
		        <td><!-- 在职状态 --><spring:message code="hrm.contract.Job_status"/></td>
				<td>
					<c:if test="${isEmployeement eq 1}">
						<input type="hidden" name="isEmployeement" value="${isEmployeement}"/><!-- 在职 --><spring:message code="ar.viewAddAffirmEvsList.ZAIZHI"/>
					</c:if>
					<c:if test="${isEmployeement ne 1}">
		 				<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</c:if>	
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 查询 --><spring:message code="button.search"/></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="130" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th orderfield="empId"><!-- 员工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
				<th orderfield="empName"><!-- 员工姓名 --><spring:message code="public.title.name"/></th>
				<th orderfield="empDept"><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></th>
				<th orderfield="empDept"><!-- 职级 --><spring:message code="ess.infoApply.Rank"/></th>
				<th orderfield="empDept"><!-- 在职区分 --><spring:message code="hr.viewPersonalInfo.title.IN_THE_DIFFERENCE"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${personList}" var="keeper">
			<tr onclick="clickTrBringBack('${keeper.LOCAL_NAME}','${keeper.PERSON_ID}','${keeper.EMPID}','${keeper.POSTGRADEBNO}','${keeper.EMP_OFFICE_NAME}'
					,'','${keeper.DEPT_NAME}','${keeper.POSITION_NAME}','${keeper.STATUS_NAME}','${keeper.POST_FAMILY_NO}','${keeper.POST_FAMILY}','${keeper.DATE_STARTED}','${keeper.DUTY_NO}','${keeper.SHIFT_NAME}')">
				<td>${keeper.EMPID}</td>
				<td>${keeper.LOCAL_NAME}</td>
				<td>${keeper.DEPT_NAME}</td>
				<td>${keeper.POSTGRADEBNO}</td>
				<td>${keeper.EMP_OFFICE_NAME}</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/ar/attendanceMintenance/viewAddAffirmEvsList">
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</div>