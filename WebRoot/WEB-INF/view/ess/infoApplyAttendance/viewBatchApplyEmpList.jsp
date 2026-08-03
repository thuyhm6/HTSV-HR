<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function submitToSave(){
			var checked = false;
			var $form = $("#saveEmpForm");
			var checks = document.getElementsByName("orgId");
			for(var i=0;i<checks.length;i++){
				if(checks[i].checked){
					checked=true;
				}
			}
			if(!checked){
				//请选择人员再添加操作!
				alertMsg.error("<spring:message code='ar.alert.message.viewdynamicgroup.choosepersonforadd'/>");
				return false;
			}
			if('${addType}'=='21'){
				$.ajax({
					type:'POST',
					url:$form.attr("action"),
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: dialogAjaxDoneWithForm,
					error: DWZ.ajaxError
				});
		    }else if('${addType}'=='31'){
		    	$.ajax({
					type:'POST',
					url:'/ess/infoApplyAttendance/addOTApplyInfoForBatchEmp',
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: dialogAjaxDoneWithForm,
					error: DWZ.ajaxError
				});
			}				
	}
/*function addApplyAttenanceBatchInfo(){
$.ajax({
		type:'post',
		url:'/ess/infoApplyAttendance/addAttendanceApplyInfoForBatch',
		dataType:null,
		success: function(data){ //请求成功后处理函数。
			navTab.reload('/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoList?type=add&firstFlag=N&seach_START_DATE=${START_DATE}&seach_END_DATE=${END_DATE}');
  	 	},
		error: DWZ.ajaxError});
}*/
</script>
<div class="pageHeader">
	<form method="post" action="/ess/infoApplyAttendance/viewBatchApplyEmpList?supervisor=1" onsubmit="return dwzSearch(this,'dialog');" rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 关键字 --><spring:message code="ar.viewkeeperlist.title.keyword"/>:</td>
				<td><input type="text" name="seach_KEY" value="${KEY}"/></td>
				<td><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>:</td>
				<td>
					<ait:deptTree name="seach_DEPTNO" limit="ar" selected="${DEPTNO}"/>
				</td>
			</tr>
			<tr>
			    <td>
			        <!--班组类型--><spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" />
			    </td>
				<td>
					<ait:SelectSyCodeByCpnyID name="seach_SHIFT_NO" parentNo="400223" cnpyID="${LoginUser.cpnyId}" limit="all" selected="${SHIFT_NO }" />
				</td>
				<td>
					<!-- 在职状态--><spring:message code="org.title.OFFICE_NAME"/>
				</td>
				<td>
				    <ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE" parentNo="15118" selected="${EMP_OFFICE}" cnpyID="${LoginUser.cpnyId}" limit="ALL"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 查询 --><spring:message code="button.search"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" onclick="submitToSave()"><!-- 选择带回 --><spring:message code="public.title.chooseback"/></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
    <form method="post" action="/ess/infoApplyAttendance/addAttendanceApplyInfoForBatchEmp" onsubmit="" id="saveEmpForm">
	<table class="table" layoutH="135" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th width="30"><input type="checkbox" class="checkboxCtrl" group="orgId" /></th>
				<th><!-- 员工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
				<th><!-- 员工姓名 --><spring:message code="public.title.name"/></th>
				<th><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></th>
				<th><!-- 职(岗)位 --><spring:message code="public.title.positionName"/></th>
				<th><!-- 员工状态 --><spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${personList}" var="keeper" varStatus="i">
			<tr>
				<td>&nbsp;&nbsp;<input type="checkbox" name="orgId" id="person_${i.index+1}" value="${keeper.PERSON_ID}"/></td>
				<td>${keeper.EMPID}</td>
				<td>${keeper.LOCAL_NAME}</td>
				<td>${keeper.DEPT_NAME}</td>
				<td>${keeper.POSITION_NAME}</td>
				<td>${keeper.EMP_OFFICE_NAME}</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	</form>
	<form id="pagerForm" method="post" action="/ess/infoApplyAttendance/viewBatchApplyEmpList?supervisor=1">
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