<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$("#viewActivityEmpList_search",$.pdialog.getCurrent()).click(function(){
	openOnRight('/hrm/empinfo/viewActivityEmpList?seach_ACTIVITY_SEQ=${ACTIVITY_SEQ}','viewActivityEmpList_unit');
});
$(".list",$.pdialog.getCurrent()).dataTable({"bPaginate": false,    //关闭分页
    "bAutoWidth":false,//表格宽度不自动变化
    "bProcessing":false,
	"bLengthChange": false,  //关闭按多少条记录显示下拉框
	"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	"bSort": true,   //关闭排序功能
	"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
	"bScrollInfinite":true,
	"scrollY": 470,
    "scrollX": true,
    "orderClasses": false
});
</script>
<div class="pageContent" id="viewActivityEmpList_unit">
<div class="formBar">
	<ul class="toolBar">
		<li>
			<a class="buttonActive" href="#"  id="viewActivityEmpList_search">
			<span><spring:message code="button.search" /><!-- 查询 --></span></a>
		</li>
		<li>
			<a class="delete" href="/hrm/empinfo/deleteActivityEmpInfo?SEQ={SEQ}&ACTIVITY_SEQ=${ACTIVITY_SEQ}" target="ajaxTodo" callback="divAjaxDone" title="确定要删除吗?">
			<span><spring:message code="button.delete" /><!-- 删除 --></span></a>
		</li>
		<li>
			<a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=195&ACTIVITY_SEQ=${ACTIVITY_SEQ}">
			<span><spring:message code="hrm.empinfo.EXPORT" /><!-- 导出到EXECL --></span></a>					
		</li>
		<li>
			<a class="delete" href="/ess/infoApplyLeave/downloadFile?fileName=/resources/template/Activity_Employee.xls&file=Activity_Employee.xls">
			<span><spring:message code="hrm.contract.Download_templates" /><!-- 下载模板 --></span></a>					
		</li>
		<li>
			<a class="delete" href="/pa/excelImport/importExcelData?importFunName=/importActivityEmpTemp&ACTIVITY_SEQ=${ACTIVITY_SEQ}" target="dialog" mask="true" rel="ActivityEmp">
			<span><spring:message code="hrm.contract.Excel_import" /><!-- Excel导入 --></span></a>					
		</li>
		<li>
			<a class="buttonActive" href="/hrm/empinfo/sendActivityEmail?ACTIVITY_SEQ=${ACTIVITY_SEQ}" target="ajaxTodo" callback="divAjaxDone" title="确定要发送吗?">
			<span><spring:message code="hrm.empinfo.send_email" /><!-- 发送通知邮件 --></span></a>
		</li>
	</ul>
</div>
	<div id="viewActivityList_left" sysLong="printDiv" style="float:left; display:block;  border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${activityEmpListSize}</div>
		<table class="list" width="100%">
			<thead>
				<tr>
					<th width="30px;">No.</th>
					<th width="100px"><spring:message code="hrm.empinfo.empid" /><!-- 社号 --></th>
					<th width="120px"><spring:message code="hrm.empinfo.name" /><!-- 姓名 --></th>
					<th width="150px"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 --></th>
					<th width="150px"><spring:message code="hrm.contract.Rank" /><!-- 职级 --></th>
					<th width="150px"><spring:message code="hrm.recruitManage.DATE_STARTED" /><!-- 入职日期 --></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${activityEmpList}" var="item" varStatus="i">
					<tr target="SEQ" rel="${item.SEQ}">
						<td class='td_center'>${i.count}</td>
						<td>${item.EMPID}</td>
						<td>${item.LOCAL_NAME}</td>
						<td>${item.DEPTNAME}</td>
						<td>${item.POST_GRADE_NAME}</td>
						<td>${item.DATE_STARTED}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>