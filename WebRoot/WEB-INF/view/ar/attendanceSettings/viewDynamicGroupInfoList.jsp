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
function exportDynamicGroupInfoExcel(){
	
	var DEPTNO = document.subForm1.seach_DEPTNO.value;
	var KEY = document.subForm1.seach_KEY.value;
	var EmpOffice = document.subForm1.seach_EmpOffice.value;
	var GROUP_NO = document.getElementById("NO").value;

   
	document.getElementById("exportDynamicGroupInfoList").href="/pa/excelExport/exportDynamicGroupInfoList?GROUP_NO="+GROUP_NO+"&NO="+GROUP_NO
				+"&KEY="+KEY+"&DEPTNO="+DEPTNO+"&EmpOffice="+EmpOffice;

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
			success:  function(json){
				DWZ.ajaxDone;
				if (json.statusCode == DWZ.statusCode.ok){
					openOnRight("/ar/attendanceSettings/viewDynamicGroupInfoList?NO=${request_NO}&pageNum=1&menuNo=2358","jbsxBox");
				}
			},
			error: DWZ.ajaxError
		});
		
	}
}
</script>
<div class="pageHeader">
	<form name="subForm1" onsubmit="return divSearch(this, 'jbsxBox');" action="/ar/attendanceSettings/viewDynamicGroupInfoList?NO=${request_NO }&pageNum=1" method="post"  rel="pagerForm">
		<div class="searchBar" >
			<table class="searchContent">
				<tr>
					<td><!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/></td>
					<td><input type="text" name="seach_KEY" value="${KEY}"/></td>
					<td><!-- 部门 --><spring:message code="public.title.deptName"/></td>
					<td>
					<ait:deptList name="seach_DEPTNO" limit="ar"  id="viewDynamicGroupInfoList_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO" limit="ar" id="viewDynamicGroupInfoList_seachDept" selected="${DEPTNO}"/>
					</td>
					<td><!-- 在职状态 --><spring:message code="hrm.contract.Job_status"/></td>
						<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
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
	<form name="deleteForm" id="deleteForm"  method="post" action="/ar/attendanceSettings/deleteDynamicGroupPerson" class="pageForm required-validate" 
     	onsubmit="return validateCallback1(this, navTabAjaxDone);">	
	<!-- 添加页面为 viewDynamicGroupEmpList 显示人员List-->

	
		
	
	<c:set value="navTab" var="add_tab"/>
	<c:set value="/ar/attendanceSettings/viewDynamicGroupEmpList?NO=${request_NO}&pageNum=1" var="add_Url"/>
	<c:set value="0" var="delete_target_exit"/>
	<c:set value="javascript:validateCallback1(deleteForm,navTabAjaxDone);" var="delete_Url"/>

	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
		<div class="formBar">
		<ul class="toolBar">
			<li>
	<a id="exportDynamicGroupInfoList" class="buttonActive"  
			onclick="exportDynamicGroupInfoExcel();"><span><!-- EXCEL导出 --><spring:message code="ar.addempshift.title.excelexport"/></span>
	</a></li>
	</ul>
	</div>
	<input type="hidden" name="NO"  id="NO" value="${request_NO}"/>
	<table class="table" width="99%" layoutH="240">
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
			<c:forEach items="${dynamicGroupInfoList}" var="list">
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
	<c:set value="/ar/attendanceSettings/viewDynamicGroupInfoList?NO=${GROUP_NO}" var="pageUrl"/>
 
 	<form id="pagerForm" method="post" action="${pageUrl}">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
	</form>
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/>&nbsp;<!-- 显示 onchange="pageFromSea('${totalCount}');navTabPageBreak({numPerPage:this.value},'jbsxBox')" --></span>
				<select class="combox" name="numPerPage"  onchange="navTabPageBreak({numPerPage:this.value},'jbsxBox')"  >
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
			${totalCount}&nbsp;<spring:message code="public.title.tiao"/><!--条--></span>	
		</div>
		<div class="pagination" rel="jbsxBox" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div> 
	
</div>