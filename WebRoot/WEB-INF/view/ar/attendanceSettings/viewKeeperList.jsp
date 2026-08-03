<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	if($("#seach_JobTypeGroupNo").val() != ''){
		var EMP_TYPE = $("#seach_EmpTypeCodeNo").val();
		ajaxAdd_add(EMP_TYPE);
	}
});
var ajaxGet_add;
function ajaxAdd_add(EMP_TYPE) {
		if (ajaxGet_add != null) {
			ajaxGet_add.abort();
		}
		$.ajaxSettings.global = false;
		ajaxGet_add = $.ajax( {
			type : "POST",
			url : "/hrm/jobType/getEmpJobType",
			data : { JobTypeGroupNo : $("#seach_JobTypeGroupNo").val() , defaultCpny : "${defaultCpny}" , authorityFlag : ${authority}},
			dataType : "json",
			success : function(data) {
				$('#seach_EmpTypeCodeNo').html("");
				var html = '<option value="">请选择</option>';
				if (typeof (data['result']) != "undefined") {
					$.each(data['result'], function(commentIndex, comment) {
							html += '<option value="' + comment['CODE_NO'] + '">' + comment['CODE_NAME'] + '</option>';
						});
				}
				$('#seach_EmpTypeCodeNo').html(html);
				if(EMP_TYPE != -1){
					$("#seach_EmpTypeCodeNo").val(EMP_TYPE);
				}
			}
		});
		$.ajaxSettings.global = true;
}
</script>
<div class="pageHeader">
	<form method="post" action="/ar/attendanceSettings/viewKeeperList?pageNum=1" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="LIZHI" value="${LIZHI}" />
		
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 关键字 --><spring:message code="ar.viewkeeperlist.title.keyword"/>:</td>
				<td><input type="text" name="seach_KEY" value="${KEY}"/></td>
				<td><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>:</td>
				<td>
					<ait:deptTree name="seach_DEPTNO_TREE" limit="hr" selected="${DEPTNO_TREE}"/>
				</td>
				<td>在职状态：</td>
				<td>
					<select name="seach_EmpOffice" class="select">
					        <option value="00" <c:if test="${EmpOffice eq '00'}">selected</c:if>>
								全部
							</option>
							<option value="15119" <c:if test="${EmpOffice eq '15119' || EmpOffice == ''}">selected</c:if>>
								在职
							</option>
							<option value="15120" <c:if test="${EmpOffice eq '15120'}">selected</c:if>>
								离职
							</option>
							<option value="123450" <c:if test="${EmpOffice eq '123450'}">selected</c:if>>
								休职
							</option>
					</select>
				</td>						
			</tr>
			<tr>
				<td>人员类型组： </td>
				<td>
					<c:if test="${authority eq '1'}">
					<ait:SelectEmpTypeCode name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" cnpyID="${defaultCpny}" limit="super" type="group" onChangeName="ajaxAdd_add(-1)"/>
					</c:if>
					<c:if test="${authority ne '1'}">
					<ait:SelectEmpTypeCode name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" cnpyID="${defaultCpny}" limit="hr" type="group" onChangeName="ajaxAdd_add(-1)"/>
					</c:if>
				</td>
				<td>人员类型： </td>
				<td>
					<c:if test="${authority eq '1'}">
				 		<ait:SelectEmpTypeCode id="seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" cnpyID="${defaultCpny}" limit="super"/>
					</c:if>
					<c:if test="${authority ne '1'}">
				 		<ait:SelectEmpTypeCode id="seach_EmpTypeCodeNo"name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" cnpyID="${defaultCpny}" limit="hr"/>
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
				<th orderfield="empId"><!-- 工号 --><spring:message code="public.title.empId"/></th>
				<th orderfield="empName"><!-- 姓名 --><spring:message code="public.title.name"/></th>
				<th orderfield="empDept"><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></th>
				<th width="80"><!-- 确认 --><spring:message code="hr.viewPersonalInfo.title.AFFIRM"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${personList}" var="keeper">
			<tr>
				<td>${keeper.EMPID}</td>
				<td>${keeper.LOCAL_NAME}</td>
				<td>${keeper.DEPT_NAME}</td>
				<td>
					<a class="btnSelect" 
						href="javascript:
							$.bringBack({
								personId:'${keeper.PERSON_ID}',
								empDept:'${keeper.DEPT_NAME}',
								deptNo:'${keeper.DEPTNO}',
								empName:'${keeper.LOCAL_NAME}',
								cnName:'${keeper.CHINESE_PINYIN}',
								sex:'${keeper.SEX}',
								empId:'${keeper.EMPID}',
								duty:'${keeper.DUTY_NO }',
								posision:'${keeper.POSITION_NO }',
								empJobType:'${keeper.EMP_TYPE_NAME}',
								emppostGradeName:'${keeper.POST_GRADE_NO}',
								end:'${keeper.END_PROBATION_DATE}',
								jion:'${keeper.JOIN_COMPANY_DATE }',
								cpny:'${keeper.CPNY_ID}',
								type:'${keeper.EMP_TYPE_CODE}'
							})" title="<spring:message code='ar.alert.message.viewattendencekeeper.chazhaodaihui'/>"><!-- 选择 --><spring:message code="public.title.choose"/></a>
				</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/ar/attendanceSettings/viewKeeperList">
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>
			${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</div>