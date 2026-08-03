<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
	$(function(){
		$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
		    "bAutoWidth":false,//表格宽度不自动变化
		    "bProcessing":false,
			"bLengthChange": false,  //关闭按多少条记录显示下拉框
			"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
			"bSort": true,   //关闭排序功能
			"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
			"scrollY": 500,
	        "scrollX": true,
	        "orderClasses": false
		});
	});
</script>
<div class="pageContent" sysLong='printDiv'>
<!--<div class="formBar">
	<ul class="toolBar">
		<li>
			<a class="buttonActive" onclick="print()" href="#"> <span><spring:message code="org.title.PRINT" /> 打印 </span> </a><br>
		</li>
	</ul>
</div>-->
	<table class="user_table" width="100%">
		
		<tr>
			<td width="15%" class="td_title"><spring:message code="org.title.dept" /><!-- 部门 --></td>
			<td width="35%" class="td_type">${currentOrgInfo.DEPTNAME}</td>
			<td width="15%" class="td_title"><spring:message code="org.title.DEPT_ID" /><!-- 部门ID --></td>
			<td width="35%" class="td_type">${currentOrgInfo.DEPTNO}</td>
		</tr>
		<tr>
			<td width="15%" class="td_title"><spring:message code="org.title.MINISTER" /><!-- 部门长 --></td>
			<td width="35%" class="td_type">
				<c:if test="${currentOrgInfo.EMPID eq null}">
				
				</c:if>
				<c:if test="${currentOrgInfo.EMPID ne null}">
					${currentOrgInfo.LOCAL_NAME}/${currentOrgInfo.EMPID}/${currentOrgInfo.POST_GRADE_NAME}
				
				</c:if>
			</td>
			<td width="15%" class="td_title"><spring:message code="org.title.DEPT_TYPE" /><!-- 组织类型 --></td>
			<td width="35%" class="td_type">${currentOrgInfo.DEPT_TYPE_NAME}</td>
		</tr>
		<tr>
			<td width="15%" class="td_title"><spring:message code="org.title.COST_CENTER" /><!-- 成本中心 --></td>
			<td width="35%" class="td_type">${currentOrgInfo.COST_CENTER_NAME}</td>
			<td width="15%" class="td_title"></td>
			<td width="35%" class="td_type"></td>
		</tr>
		<!--<tr>
			<td width="15%" class="td_title" ><spring:message code="org.title.WORD_AREA_NAME" /> 工作地 </td>
			<td width="35%" colspan="3" class="td_type">${currentOrgInfo.WORK_AREA_NAME}</td>
			<%-- <c:if test="${LoginUser.cpnyId ne 'HTSV' and LoginUser.cpnyId ne 'HAE'}">
			<td width="15%" class="td_title"><spring:message code="org.title.COST_CENTER" /></td>
			<td width="35%" class="td_type">${currentOrgInfo.COST_CENTER_NAME}</td>
			</c:if> --%>
		</tr>
	--></table>
	<div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;"><spring:message code="org.title.EMP_DETAILS" /><!-- 员工明细 --></div>
	<div style="font: bold 12px/ 20px arial, sans-serif; float: right; height: 30px; line-height: 30px;">
	Total:${fn:length(currentOrgEmpInfoList)}[${currentOrgInfo.EMP_CNT }]</div>
	<table class="list" width="100%">
		<thead>
			<tr>
				<th width="5%">No.</th>
				<th width="25%"><spring:message code="org.title.LOCAL_NAME" /><!-- 姓名 --></th>
				<th width="10%"><spring:message code="org.title.EMPID" /><!-- 工号 --></th>
				<th width="15%"><spring:message code="org.title.POST_GRADE_NAME" /><!-- 职级 --></th>
				<!--<th width="80px"><spring:message code="org.title.DUTY_NO" /> 岗位 </th>
				<th width="80px"><spring:message code="hrm.empinfo.POST_FAMILY" /> 职群 </th>-->
				<th width="30%"><spring:message code="org.title.EMP_TYPE" /><!-- 员工类型 --></th>
				<th width="15%"><spring:message code="org.title.EMP_OFFICE_NAME" /><!-- 员工状态 --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${currentOrgEmpInfoList}" var="item" varStatus="i">
				<tr>
					<td width="50px" class='td_center'>${i.count}</td>
					<td width="150px">${item.LOCAL_NAME}</td>
					<td width="80px">
						<a class="edit" href="#" onclick="navTabNum('/hrm/empinfo/viewPersonalInfo?PERSON_ID=${item.PERSON_ID}','pageNum=1&menuNo=125244&navTabId=hr2100','hr2100','<spring:message code="hrm.empinfo.COOMPREHENSIVE_INTRODUCTION.Z" />');$.pdialog.closeCurrent();">
							<span font-size="18">${item.EMPID}</span>
						</a>
					</td>
					<td width="80px">${item.POST_GRADE_NAME}</td>
					<!--<td width="80px">${item.DUTY_NAME}</td>
					<td width="80px">${item.POST_FAMILY_NAME}</td>-->
					<td width="150px">${item.EMP_TYPE_NAME}</td>
					<td width="80px">${item.EMP_OFFICE_NAME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
