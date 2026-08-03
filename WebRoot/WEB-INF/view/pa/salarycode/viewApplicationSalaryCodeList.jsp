<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
// 	function creatSalaryCode() {
	
// 		var selectobj = document.getElementById('salary_code');
// 		for ( var i = selectobj.options.length; i > 0; i--) {
// 			selectobj.options[i] = null;
// 		}
// 		var PROJECT_TYPE = document.getElementById('seach_TYPE').value;
//         jQuery.ajax({
// 			type : "POST",
// 			url : "/pa/salarycode/findSalaryCodeObject",
// 			dataType : "json",
// 			data : {
// 				"PROJECT_TYPE" : PROJECT_TYPE
// 			},
// 			async : false,
// 			success : function(response) {
// 				var list = response;
// 				for ( var i = 0; i < list.length; i++) {
// 					var selectObj = document.getElementById('salary_code');
// 					var obj = list[i];
//					alert(obj);
// 					selectObj.options[i] = new Option(obj.ITEM_NAME,obj.ITEM_NO);
// 				}
// 			}
// 		});
// 	}
$(document).ready(function(){
	if($("#pa1109_seach_JobTypeGroupNo").val() != ''){
		var EMP_TYPE = $("#pa1109_seach_EmpTypeCodeNo").val();
		ajaxEmpTypeForGroupToList(EMP_TYPE,"pa1109_seach_JobTypeGroupNo","pa1109_seach_EmpTypeCodeNo",
				"seach_CPNY","limit");
		//此方法在dwz.ajax.js要传进的参数分别为 emp_type/-1，人员类型组select id，人员类型select id，法人选项id，权限super/hr/ar/pa
	}
});
</script>
<div class="pageHeader">

	<form onsubmit="return navTabSearch(this);"
		action="/pa/salarycode/viewApplicationSalaryCodeList" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				   <td>法人
				</td>
			<td>
				<c:if test="${authority eq '1'}">
				<select id="seach_CPNY" name="seach_CPNY" onchange="reloadPage();">
					<c:forEach items="${companyList}" var="item" varStatus="i">
						<option value="${item.CPNY_ID }" <c:if test="${CPNY eq item.CPNY_ID}">selected</c:if>>${item.CPNY_ID }</option>
					</c:forEach>
				</select>
				</c:if>
				<c:if test="${authority eq '0'}">
					${CPNY }
					<input type="hidden" id="seach_CPNY" name="seach_CPNY" value="${CPNY }" readonly="readonly"/>
				</c:if>
	   			 </td>
					<td>
						<!-- 工号/姓名： --> <spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /></td>
					<td><input type="text" name="seach_KEY" value="${KEY}" /></td>
                    <td>
						<!-- 工资项目： --> <spring:message
							code="pa.diff.title.salaryItem"/></td>
					<td><select name="seach_SALARYCODE"><option value="">请选择</option>
				    <c:forEach items="${salaryCodeList}" var="item">
					<option value="${item.ITEM_NO}" <c:if test="${item.ITEM_NO eq SALARYCODE}">selected</c:if>>${item.ITEM_NAME}</option>
				    </c:forEach>
			     </select>
		          </td>
				  <td><!-- 在职区分： --> <spring:message
			code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME" /> </td>
						<td>
		 					<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${CPNY}" limit="all"/>
						</td>
					
				</tr>
				<tr>
						<td>人员类型组</td>
						<td>
						<input type="hidden" name="limit" id="limit" value="pa">
						<input type="hidden" name="gtype" id="gtype" value="group">
						<ait:SelectEmpTypeCode id="pa1109_seach_JobTypeGroupNo" name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" cnpyID="${CPNY}" limit="pa" type="group" 
						onChangeName="ajaxEmpTypeForGroupToList(-1,pa1109_seach_JobTypeGroupNo,pa1109_seach_EmpTypeCodeNo,seach_CPNY,limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 		<ait:SelectEmpTypeCode id="pa1109_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" cnpyID="${CPNY}" limit="pa"/>
						</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<c:set value="navTab" var="add_tab"/>
	<c:set value="/pa/salarycode/addApplicationSalaryView" var="add_Url"/>
	<c:set value="/pa/salarycode/deleteApplicationSalaryInfo?PERSON_ID={PERSON_ID}" var="delete_Url"/>
	<c:set value="navTab" var="edit_tab"/>
	<c:set value="/pa/salarycode/updateApplicationSalaryView?PERSON_ID={PERSON_ID}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="5%"><spring:message
						code="ar.viewcycleparameter.content.choose" />
				</th>
				<th width="8%">
					法人
				</th>
				<th width="8%">
					<!-- 工号 -->
					<spring:message code="display.emp.ben.serviceno" />
				</th>
				<th width="10%"><spring:message
						code="hr.viewPersonalInfo.title.LOCAL_NAME" /> <!--姓名-->
				</th>
				<th width="15%"><spring:message code="public.title.deptName" />
					<!--部门-->
				</th>
				<th width="52%"><spring:message code="pa.diff.title.salaryItem" />
					<!--工资项目-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${applicationSalaryList}" var="item" varStatus="i">

				<tr height="20" target="PERSON_ID" rel="${item.PERSON_ID}">
					<td>${i.index+1}</td>
					<td>${item.CPNY_ID}</td>
					<td>${item.EMPID}</td>
					<td>${item.LOCAL_NAME}</td>
					<td>${item.DEPT_NAME}</td>
					<td>${item.SALARY_NAME}<input type="hidden" name="ITEM_NO" value="${item.ITEM_NO }"/></td>
				</tr>

			</c:forEach>

		</tbody>

	</table>

	<c:set value="/pa/salarycode/viewApplicationSalaryCodeList"
		var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
