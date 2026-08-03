<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallback2(form, callback) {
	
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}

	var checked=false;
	var ids= document.getElementsByName("c2");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行添加操作!
		alert("<spring:message code='ar.alert.message.viewdynamicgroup.choosepersonforadd'/>");
		return false;
	}
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});

	return false;
}
</script>
<div class="pageHeader">
	<form name="subForm12" onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewPersonDynGroupEmpList?NO=${request_NO }&pageNum=1" method="post"  rel="pagerForm">
		<div class="searchBar" >
			<table class="searchContent">
				<tr>
					<td><!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/>:</td>
					<td><input type="text" name="seach_KEY" value="${KEY}"/></td>
					<td><!-- 部门 --><spring:message code="public.title.deptName"/>:</td>
					<td>
						<ait:deptTree name="seach_DEPTNO" limit="ar" selected="${DEPTNO}"/>
					</td>
					<td>在职状态：</td>
						<td>
		 <ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
				</tr>
				<tr>
				<td>人员类型组： </td>
						<td>
						<select name="seach_JobTypeGroupNo" class="select">
									<option value="">
										<!-- 全部 --><spring:message code="ar.viewarcardrecord.title.quanbu"/>
									</option>
									<c:forEach items="${jobTypeGroupList}" var="item">
										<option value="${item.JOBTYPE_GROUP_NO}" 
											<c:if test="${item.JOBTYPE_GROUP_NO eq JobTypeGroupNo}">selected</c:if>>
											${item.JOBTYPE_GROUP_NAME}
										</option>
									</c:forEach>
							</select>
						</td>
						<td>人员类型： </td>
						<td>
						<select name="seach_EmpTypeCodeNo" class="select">
									<option value="">
										<!-- 全部 --><spring:message code="ar.viewarcardrecord.title.quanbu"/>
									</option>
									<c:forEach items="${getEmpTypeCodeList}" var="item">
										<option value="${item.EMP_TYPE_CODE}" 
											<c:if test="${item.EMP_TYPE_CODE eq EmpTypeCodeNo}">selected</c:if>>
											${item.EMP_TYPE_NAME}
										</option>
									</c:forEach>
							</select>
						</td>
				</tr>
			</table>
			<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 检索 --><spring:message code="public.title.search"/></button></div></div></li>
								
							</ul>
			</div>
		</div>
		
	</form>	
</div>
<div class="pageContent">
	<form name="subForm2" onsubmit="return validateCallback2(this, navTabAjaxDone);" class="pageForm required-validate" 
		 action="/ar/attendanceSettings/addPersonDynGroupPerson" method="post" rel="pagerForm">
		 
	<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div>
			</li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close" onclick="history.back(-1);"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div>
			</li>
		</ul>
	</div>
		
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="10" align="center" ><input type="checkbox" class="checkboxCtrl" group="c2" /><!-- 全选 --><spring:message code="ar.alert.message.viewdynamicgroup.chooseall"/></th>
				<th width="45"><!-- 工号 --><spring:message code="public.title.empId"/></th>
				<th width="45"><!-- 姓名 --><spring:message code="public.title.name"/></th>
				<th width="45"><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></th>
				<th width="45"><!-- 员工状态 --><spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${PersonDynGroupEmpList}" var="list" varStatus="i">
				<tr target="PERSON_ID" rel="${list.PERSON_ID}">
					<td><input type="checkbox" name="c2" value="${list.PERSON_ID}"></td>
					<td>${list.EMPID}</td>
					<td>${list.LOCAL_NAME}</td>
					<td>${list.DEPT_NAME}</td>
					<td>${list.STATUS}</td>
				</tr>
			</c:forEach>
			<input type=hidden value="${request_NO}" name="NO"/>
		</tbody>
	
	</table>
	</form>
	<c:set value="/ar/attendanceSettings/viewPersonDynGroupEmpList?KEY=${KEY}&DEPTNO=${DEPTNO}" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
