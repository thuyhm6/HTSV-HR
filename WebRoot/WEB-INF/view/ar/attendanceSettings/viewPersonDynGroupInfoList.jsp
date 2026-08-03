<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script src="/resources/js/jquery/jquery.number.js" type="text/javascript"></script>
<script type="text/javascript">
function check(){
	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行删除操作!
		alert("<spring:message code='ar.alert.message.viewdynamicgroup.chooseperson'/>");
		return false;
	}
	//确定要提交吗？
	if (confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>")){	
		return true;
       
  	}
  	return false;
}

function validateCallback1(form, callback) {

	var $form = $(form);

	if (!$form.valid()) {
		return;
	}

	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行删除操作!
		alert("<spring:message code='ar.alert.message.viewdynamicgroup.chooseperson'/>");
		return;
	}
	//确定要提交吗？
	if (confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>")){	
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		
	}
}
</script>
<div class="pageHeader">
<!--return divSearch(this, 'jbsxBox');  -->
	<form name="subForm12" onsubmit="return divSearch(this, 'jbsxBox1');" action="/ar/attendanceSettings/viewPersonDynGroupInfoList?NO=${request_NO }&pageNum=1" method="post"  rel="pagerForm">
		<div class="searchBar" >
			<table class="searchContent">
				<tr>
					<td><!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/></td>
					<td><input type="text" name="seach_KEY" value="${KEY}"/></td>
					<td><!-- 部门 --><spring:message code="public.title.deptName"/></td>
					<td>
					<ait:deptList name="seach_DEPTNO" limit="ar"  id="viewPersonDynGroupInfoList_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO" limit="ar" id="viewPersonDynGroupInfoList_seachDept" selected="${DEPTNO}"/>
					</td>
					<td>在职状态</td>
						<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
				</tr>
				<tr>
			   	<td>人员类型组 </td>
						<td>
						<input type="hidden" id="viewPersonDynGroupInfoList_limit" name="limit" value="ar">
						<input type="hidden" id="viewPersonDynGroupInfoList_seach_CPNY" name="seach_CPNY" value="${defaultCpny}">
			<ait:SelectEmpTypeCode  id="viewPersonDynGroupInfoList_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="ar" type="group"
			onChangeName="ajaxEmpTypeForGroupToList(-1,viewPersonDynGroupInfoList_seach_JobTypeGroupNo,viewPersonDynGroupInfoList_seach_EmpTypeCodeNo,viewPersonDynGroupInfoList_seach_CPNY,viewPersonDynGroupInfoList_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 	<ait:SelectEmpTypeCode id="viewPersonDynGroupInfoList_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="ar"/>
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
	<form name="deleteForm" id="deleteForm"  method="post" action="/ar/attendanceSettings/deletePersonDynGroupPerson" class="pageForm required-validate" 
     	onsubmit="return validateCallback1(this, navTabAjaxDone);">	
	<!-- 添加页面为 viewPersonDynGroupEmpList 显示人员List-->
	<c:set value="navTab" var="add_tab"/>
	<c:set value="/ar/attendanceSettings/viewPersonDynGroupEmpList?NO=${request_NO}&pageNum=1" var="add_Url"/>
	<c:set value="0" var="delete_target_exit"/>
	<c:set value="javascript:validateCallback1(deleteForm,navTabAjaxDone);" var="delete_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<input type="hidden" name="NO" value="${request_NO}"/>
	<table class="table" width="99%" layoutH="200">
		<thead>
			<tr>
				<th width="10" align="center" ><input type="checkbox" class="checkboxCtrl" group="c1"></th>
				<th width="45"><!-- 工号 --><spring:message code="public.title.empId"/></th>
				<th width="45"><!-- 姓名 --><spring:message code="public.title.name"/></th>
				<th width="45"><!-- 部门 --><spring:message code="public.title.deptName"/></th>
				<th width="45"><!-- 员工状态 --><spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${PersonDynGroupInfoList}" var="list">
				<tr target="GROUP_NO" rel="${request_NO}" >
				<td><input type="checkbox" name="c1" value="${list.PERSON_ID }"></td>
				<td>${list.EMPID}</td>
				<td>${list.LOCAL_NAME}</td>
				<td>${list.DEPTNAME}</td>
				<td>${list.STATUS}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	<c:set value="/ar/attendanceSettings/viewPersonDynGroupInfoList?NO=${GROUP_NO}" var="pageUrl"/>
	<form id="pagerForm" method="post" action="${pageUrl}">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
	</form>
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!-- 显示 --></span>
				<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'jbsxBox1')">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
				    <option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
					<option value="50"  <c:if test="${numPerPage == 50 }" >selected</c:if> >50</option>
					<option value="150"  <c:if test="${numPerPage == 150 }" >selected</c:if> >150</option>
					<option value="200"  <c:if test="${numPerPage == 200 }" >selected</c:if> >200</option>
				    <option value="500"  <c:if test="${numPerPage == 500 }" >selected</c:if> >500</option>
					<option value="700"  <c:if test="${numPerPage == 700 }" >selected</c:if> >800</option>
					<option value="1000"  <c:if test="${numPerPage == 1000 }" >selected</c:if> >1000</option>
					<option value="2000"  <c:if test="${numPerPage == 2000 }" >selected</c:if> >2000</option>
					<option value="3000"  <c:if test="${numPerPage == 2000 }" >selected</c:if> >3000</option>
					<option value="5000"  <c:if test="${numPerPage == 2000 }" >selected</c:if> >5000</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->
			${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>	
		</div>
		<div class="pagination" rel="jbsxBox" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
</div>