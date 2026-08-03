<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewOrgChangePanel_currentIndex").val('${currentIndex}');
	$("#viewOrgChangePanel_currentIndex_${currentIndex }",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": 450,
        "scrollX": true,
        "orderClasses": false
	});
});
</script>
<c:if test="${currentIndex eq '0'}">
<div class="pageContent">
			<div class="user_table">
				<div style="font:bold 12px/20px arial,sans-serif;float:left;height:25px;line-height:25px;">Total:${fn:length(orgInfoList)}</div>
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=42&RESUME_NO=${RESUME_NO }&DEPTNO=${DEPTNO}"><span><spring:message code="org.title.exportLOtImportExcel" /><!-- 导出到EXECL --></span></a>
				</div>
			</div>
				<table class="list" width="100%" id="viewOrgChangePanel_currentIndex_${currentIndex}">
					<thead>
						<tr>
							<th width="30px">No.</th>
							<th width="80px"><spring:message code="org.title.DATE" /><!-- 日期 --></th>
							<th width="100px"><spring:message code="org.title.EXPERIENCE_TYPE" /><!-- 变更类型 --> </th>
							<th width="100px"><spring:message code="org.title.DEPT_ID" /><!-- 部门ID --></th>
							<th width="200px"><spring:message code="org.title.dept" /><!-- 部门 --></th>
							<th width="150px"><spring:message code="org.title.MINISTER" /><!-- 部门长 --></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${orgInfoList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
								<td style="text-align:left">${item.CHANGE_DATE}</td>
								<td style="text-align:left">${item.EXPERIENCE_TYPE_NAME}</td>
								<td>${item.DEPTNO }</td>
								<td>${item.DEPTNAME}</td>
								<td>${item.LOCAL_NAME}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
</c:if>
<c:if test="${currentIndex eq '1'}">
<div class="pageContent">
			<div class="user_table">
				<div style="font:bold 12px/20px arial,sans-serif;float:left;height:25px;line-height:25px;">Total:${fn:length(orgManagerList)}</div>
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=41&RESUME_NO=${RESUME_NO }&DEPTNO=${DEPTNO}"><span><spring:message code="org.title.exportLOtImportExcel" /><!-- 导出到EXECL --></span></a>
				</div>
			</div>
				<table class="list" width="100%"  id="viewOrgChangePanel_currentIndex_${currentIndex}">
					<thead>
						<tr>
							<th width="30px">No.</th>
							<th width="80px"><spring:message code="org.title.STARTDATE" /><!-- 开始日期 --></th>
							<th width="100px"><spring:message code="org.title.DEPT_ID" /><!-- 部门ID --></th>
							<th width="200px"><spring:message code="org.title.dept" /><!-- 部门 --></th>
							<th width="150px"><spring:message code="org.title.MINISTER" /><!-- 部门长 --></th>
							<th width="100px"><spring:message code="org.title.EMPID" /><!-- 工号 --></th>
							<th width="80px"><spring:message code="org.title.OFFICE_NAME" /><!-- 在职状态 --></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${orgManagerList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.CHANGE_DATE}</td>
								<td class='td_center'>${item.DEPTNO}</td>
								<td class='td_center'>${item.DEPTNAME}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.EMP_OFFICE_NAME}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
</c:if>