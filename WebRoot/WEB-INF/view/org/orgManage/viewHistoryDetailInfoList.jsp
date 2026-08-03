<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewHistoryOrgPanel_currentIndex").val('${currentIndex}');
	$("#viewHistoryDetailInfoList_list_${currentIndex }",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
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

<c:if test="${currentIndex eq '0'}">
<div class="pageContent">
<c:forEach items="${orgInfo}" var="item" varStatus="i">
	<table class="user_table" width="100%">
		<tr>
			<td width="15%" class="td_title"><spring:message code="org.title.dept" /><!-- 部门 --></td>
			<td width="35%" class="td_type">${item.DEPTNAME}</td>
			<td width="15%" class="td_title"><spring:message code="org.title.DEPT_ID" /><!-- 部门ID --></td>
			<td width="35%" class="td_type">${item.DEPTNO}</td>
		</tr>
		<tr>
			<td width="15%" class="td_title"><spring:message code="org.title.MINISTER" /><!-- 部门长 --></td>
			<td width="35%" class="td_type">${item.LOCAL_NAME}/${item.EMPID}/${item.POST_GRADE_NAME}</td>
			<td width="15%" class="td_title"><spring:message code="org.title.DEPT_TYPE" /><!-- 组织类型 --></td>
			<td width="35%" class="td_type">${item.DEPT_TYPE_NAME}</td>
		</tr>
		<tr>
			<!--<td width="15%" class="td_title"><spring:message code="org.title.WORD_AREA_NAME" /> 工作地 </td>
			<td width="35%" colspan="3" class="td_type">${item.WORK_AREA_NAME}</td>
				-->
			<td width="15%" class="td_title"><spring:message code="org.title.COST_CENTER" /><!-- 成本中心 --></td>
			<td width="35%" class="td_type">${item.COST_CENTER_NAME}</td>
			<td width="15%" class="td_title"></td>
			<td width="35%" class="td_type"></td>
		</tr>
	</table>
	<table class="user_table" width="100%">
		<tr>
			<td width="15%" class="td_title"><spring:message code="org.title.UPDATED_IP" /><!-- 变更者 --></td>
			<td width="35%" class="td_type">[${item.EMPID}]${item.UPDATED_BY} ${item.UPDATED_IP}</td>
			<td width="15%" class="td_title"><spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 --></td>
			<td width="35%" class="td_type">${item.UPDATE_DATE}</td>
		</tr>
	</table></br>
	</c:forEach>
</div>
</c:if>

<c:if test="${currentIndex eq '1'}">
<div class="pageContent">
			<div class="user_table">
				<div style="font:bold 12px/20px arial,sans-serif;float:left;height:25px;line-height:25px;">Total:${fn:length(orgResumeEmpList)}</div>
				<div style="float:right;height:25px;line-height:25px;">
					<!--<a class="w_button"  onclick="print();"><span>印刷</span></a>
					--><a class="w_button" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=35&DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }"><span><spring:message code="org.title.exportLOtImportExcel" /><!-- 导出到EXECL --></span></a>
				</div>
			</div>
	<div  sysLong="printDiv">
				<table id="viewHistoryDetailInfoList_list_${currentIndex }" class="list" width="100%">
					<thead>
						<tr>
							<th width="30px">No.</th>
							<th width="150px"><spring:message code="org.title.dept" /><!-- 部门 --></th>
							<th width="150px"><spring:message code="org.title.LOCAL_NAME" /><!-- 姓名 --></th>
							<th width="100px"><spring:message code="org.title.EMPID" /><!-- 工号 --></th>
							<th width="80px"><spring:message code="ess.trans.title.postGradeName" /><!-- 职级 --></th>
							<th width="150px"><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" /><!-- 主要业务 --></th>
							<th width="80px"><spring:message code="org.title.EMP_OFFICE_NAME" /><!-- 员工状态 --></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${orgResumeEmpList}" var="item" varStatus="i">
							<tr>
								<td class='td_center' width="30px" >${i.count}</td>
								<td width="150px" >${item.DEPTNAME}</td>
								<td width="150px">${item.LOCAL_NAME}</td>
								<td width="100px">${item.EMPID}</td>
								<td width="80px">${item.POST_GRADE_NAME}</td>
								<td width="150px">${item.MAIN_BUSINESS_NAME}</td>
								<td width="80px">${item.EMP_OFFICE_NAME}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
</c:if>

<c:if test="${currentIndex eq '2'}">
<div class="pageContent">
			<div class="user_table">
				<div style="font:bold 12px/20px arial,sans-serif;float:left;height:25px;line-height:25px;">Total:${fn:length(orgResumeOrgList)}</div>
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=36&RESUME_NO=${RESUME_NO }">
						<span><spring:message code="ar.addempshift.title.excelexport" /><!-- 导出到EXECL --></span></a>
				</div>
			</div>
	<div sysLong="printDiv">
				<table id="viewHistoryDetailInfoList_list_${currentIndex }" class="list" width="100%">
					<thead>
						<tr>
							<th width="30px">No.</th>
							<th width="100px"><spring:message code="org.title.DEPT_ID" /><!-- 部门ID --></th>
							<th width="150px"><spring:message code="org.title.dept" /><!-- 部门 --></th>
							<th width="100px"><spring:message code="org.title.DEPT_TYPE" /><!-- 组织类型 --></th>
							<th width="100px"><spring:message code="org.title.WORD_AREA_NAME" /><!--工作地--></th>
							<th width="150px"><spring:message code="org.title.MINISTER" /><!-- 部门长 --></th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${orgResumeOrgList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
								<td>${item.DEPTNO}</td>
								<td>${item.DEPTNAME}</td>
								<td>${item.DEPT_TYPE_NAME}</td>
								<td>${item.WORK_AREA_NAME}</td>
								<td>${item.LOCAL_NAME}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
	</div>
</c:if>