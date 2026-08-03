<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
<c:if test="${personidStr eq 'viewDeptManagerCheck'}">
	function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand){
		viewDeptManagerCheck_display_name('${empidStr}');
	  	$("[id='viewDeptManagerCheck_PERSON_ID_" + '${empidStr}' + "']").val(personId);
	  	$("[id='viewDeptManagerCheck_EMP_NAME_" + '${empidStr}' + "']").val(localName);
	  	$("[id='viewDeptManagerCheck_DISPLAY_DIV_" + '${empidStr}' + "']").html(localName);
	  	$("[id='viewDeptManagerCheck_EMP_ID_DIV_" + '${empidStr}' + "']").html(empId);
	  	$("[id='viewDeptManagerCheck_POST_GRADE_NAME_" + '${empidStr}' + "']").html(postGradeNo);
	  	$("[id='viewDeptManagerCheck_S_BAND_" + '${empidStr}' + "']").html(sBand);
	  	$("[id='viewDeptManagerCheck_EMP_OFFICE_NAME_" + '${empidStr}' + "']").html(statusName);
	  	$("#viewDeptManagerCheck_vacancy_" + '${empidStr}').removeAttr("checked");
	}
</c:if>
<c:if test="${personidStr eq 'applyAttendance'}">
function clickTrBringBack(localName,personId,empId,postGradeNo,statusName,sBand,deptName){
	var index = $("#index").val();
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).html(empId);
	$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html(deptName);
	$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html(localName);
	$("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',personId);
	$.pdialog.closeCurrent();
}
</c:if>
</script>
<div class="pageHeader">
	<form method="post" action="/ar/attendanceMintenance/viewAddAffirmList" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
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
					<input type="hidden" name="empNameStr" value="${empNameStr}"/>
					<input type="hidden" name="positionIdStr" value="${positionIdStr}"/>
					<input type="hidden" name="deptIdStr" value="${deptIdStr}"/>
				</td>
		        <td><!-- 在职状态 --><spring:message code="hrm.contract.Job_status"/></td>
				<td>
					<c:if test="${isEmployeement eq 1}">
						<input type="hidden" name="isEmployeement" value="${isEmployeement}"/><!-- 在职 --><spring:message code="hrm.empinfo.JOB"/>
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
				<th orderfield="empDept"><!-- 职级 --><spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/></th>
				<th orderfield="empDept"><!-- 在职区分 --><spring:message code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME"/></th>
				<th width="80"><!-- 确认 --><spring:message code="hr.viewPersonalInfo.title.AFFIRM"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${personList}" var="keeper">
			<tr onclick="<c:if test="${personidStr eq 'viewDeptManagerCheck'}">
							clickTrBringBack('${keeper.LOCAL_NAME}','${keeper.PERSON_ID}','${keeper.EMPID}','${keeper.POSTGRADEBNO}','${keeper.EMP_OFFICE_NAME}','','${keeper.DEPT_NAME}');</c:if>
							javascript:
					$.bringBackById({
					    ${personInfoStr}:'${keeper.LOCAL_NAME}/${keeper.POSTGRADEBNO}/${keeper.DEPTNONAME}',
						person_id:'${keeper.PERSON_ID}',
						personId:'${keeper.PERSON_ID}',
						empId:'${keeper.EMPID}',
						cpny_id:'${keeper.CPNY_ID}',
						STAT_NO:'${keeper.STAT_NO}',
						empName:'${keeper.LOCAL_NAME}',
						empDept:'${keeper.DEPT_NAME}',
						${empidStr}:'${keeper.EMPID}', 
						${empNameStr}:'${keeper.LOCAL_NAME}',
						${personidStr}:'${keeper.PERSON_ID}',
						${positionIdStr}:'${keeper.POSITION_NAME}',
						${deptIdStr}:'${keeper.DEPT_NAME}',
						EmpId${empidStr}:'${keeper.EMPID}',
						Dept${empidStr}:'${keeper.DEPT_NAME}',
						Name${empidStr}:'${keeper.LOCAL_NAME}',
						Phone${empidStr}:'${keeper.OFFICE_PHONE}',
						Grade${empidStr}:'${keeper.POSTGRADEBNO}'
					})">
				<td>${keeper.EMPID}</td>
				<td>${keeper.LOCAL_NAME}</td>
				<td>${keeper.DEPT_NAME}</td>
				<td>${keeper.POSTGRADEBNO}</td>
				<td>${keeper.EMP_OFFICE_NAME}</td>
				<td>
					<a class="btnSelect" 
						href="javascript:
							$.bringBackById({
							    ${personInfoStr}:'${keeper.LOCAL_NAME}/${keeper.POSITION_NAME}/${keeper.DEPTNONAME}',
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								STAT_NO:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}',
								${positionIdStr}:'${keeper.POSITION_NAME}',
								${deptIdStr}:'${keeper.DEPT_NAME}',
								${empidStr}:'${keeper.EMPID}', 
								${empNameStr}:'${keeper.LOCAL_NAME}',
								${personidStr}:'${keeper.PERSON_ID}'
							})" title="<spring:message code='ar.alert.message.viewattendencekeeper.chazhaodaihui'/>"><!-- 选择 --><spring:message code="public.title.choose"/></a>
				</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/ar/attendanceMintenance/viewAddAffirmList">
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