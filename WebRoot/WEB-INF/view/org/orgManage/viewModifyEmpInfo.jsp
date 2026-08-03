<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": 350,
        "scrollX": true,
        "orderClasses": false
	});
});
function executeProcess() {
	var empIdsStr="";
	var flag=false;
	$("input[name='viewModifyEmpInfo_personId']").each(function(){
		if($(this).attr("checked") == "checked"){
			empIdsStr = empIdsStr + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	empIdsStr = empIdsStr + "'empty'";
	if(flag == false){
		alertMsg.error('<spring:message code="org.title.SELECT_ROMOVE_EMP"/>');
		return false;
	}
	if($("input[name='MOVE_DEPT_NO']").val()==null||$("input[name='MOVE_DEPT_NO']").val()==""){
		alertMsg.error('<spring:message code="alert.trans.message.selectDept"/>');//请选择部门
		return false;
	}
	alertMsg.confirm('<spring:message code="org.title.IS_REMOVE"/>',
  		  	{okCall:function(){
		  	$.ajax({
  				type:'POST',
  				url:'/org/orgManage/executeMoveEmp',
  				data:{empIds:empIdsStr,RESUME_NO:$("#viewComposeOrgResumeNo").val(),MOVE_DEPT_NO:$("input[name='MOVE_DEPT_NO']").val(),DEPTNO:$("input[name='DEPTNO']").val()},
  				dataType:"json",
  				cache: false,
  				success: divAjaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
</script>
<div class="pageContent">
			<div>
				<div style="font:bold 12px/20px arial,sans-serif;float:left;height:30px;line-height:30px;">Total:${fn:length(orgResumeEmpList)}</div>
				<c:if test="${resumeActivityInfo.ACTIVITY != '14013947'}">
				<div style="float:right;">
					To Department 
					<ait:resumeDeptList name="MOVE_DEPT_NO" resumeNo="${RESUME_NO}" id="viewModifyEmpInfo_deptList"/>
					<ait:resumeDeptTreeIcon name="MOVE_DEPT_NO" resumeNo="${RESUME_NO}"  id="viewModifyEmpInfo_deptList" selected=""/>
					<a class="w_button" onclick="executeProcess()"><span><spring:message code="org.title.REMOVE"/><!-- 移动 --></span></a>
				</div>
				</c:if>
			</div>
		<table class="orderList" width="75%">
			<thead>
				<tr>
					<th width="5%">No.</th>
					<th width="3%"><input type="checkbox" class="checkboxCtrl" group="viewModifyEmpInfo_personId" /></th>
					<th width="24%"><spring:message code="org.title.LOCAL_NAME" /><!-- 姓名 --></th>
					<th width="14%"><spring:message code="org.title.EMPID" /><!-- 工号 --></th>
					<th width="14%"><spring:message code="org.title.POST_GRADE_NAME" /><!-- 职级 --></th>
					<th width="14%"><spring:message code="org.title.POSITION_NO" /><!-- 职责 --></th>
					<th width="14%"><spring:message code="hrm.empinfo.POST_FAMILY" /><!-- 职群 --></th>
					<th width="12%"><spring:message code="org.title.OFFICE_NAME" /><!-- 在职状态 --></th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orgResumeEmpList}" var="item" varStatus="i">
					<tr>
						<td class='td_center'>${i.count}</td>
						<td class='td_center'><input type="checkbox" name="viewModifyEmpInfo_personId" value="${item.PERSON_ID}"/></td>
						<td style="text-align:left">${item.LOCAL_NAME}</td>
						<td style="text-align:left">${item.EMPID}</td>
						<td style="text-align:left">${item.POST_GRADE_NAME}</td>
						<td style="text-align:left">${item.POSITION_NO_NAME}</td>
						<td style="text-align:left">${item.POST_FAMILY_NAME}</td>
						<td style="text-align:left">${item.EMP_OFFICE_NAME}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>