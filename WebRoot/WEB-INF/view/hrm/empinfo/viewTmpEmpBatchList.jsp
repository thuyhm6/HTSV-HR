<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewTmpEmpBatchList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewTmpEmpBatchListForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewTmpEmpBatchList_SEQ").change(function(){
		$("#viewTmpEmpBatchListForm",navTab.getCurrentPanel()).submit();
	});
	//保存
	$("#viewTmpEmpBatchList_save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("input[name='ACTIVITY']",navTab.getCurrentPanel()).each(function(i, obj){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			//var IS_PART_TIME = 0;
			//if(obj.checked){
			//	IS_PART_TIME = 1;
			//}
			jsonData += ' "DEPTNO": "' + $("#viewTmpEmpBatchList_DEPTNO_" + i,navTab.getCurrentPanel()).val() + '" ,';
			//jsonData += ' "IS_PART_TIME": "' + IS_PART_TIME + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ,';
			jsonData += ' "RESUME_NO": "' + '${RESUME_NO}' + '" ,';
			jsonData += ' "MANAGER_PERSON_ID": "' + $("#viewTmpEmpBatchList_PERSON_ID_" + i,navTab.getCurrentPanel()).val() + '" ';
			jsonData += '}';
		});
		jsonData += ']';

		if (jsonData.length == 2) {
			alertMsg.info("<spring:message code='hrm.empinfo.NOTSAVE_DATA'/>");//没有需要保存的数据
			return;
		}

		alertMsg.confirm("<spring:message code='hrm.empinfo.SAVE_CONFIRM'/>",//确定要保存吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/org/orgManage/saveDeptManager',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: divAjaxDone,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
});


function executeExperienceBatch(typeStr) {
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Perform_operation'/>",//确定要执行此操作吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type:'POST',
  				url:'/hrm/recruitManage/executeRecruit',
  				data:{empIds:$("#viewTmpEmpBatchList_SEQ",navTab.getCurrentPanel()).val(),type:typeStr},
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

function updateTmpEmpBatchInfo(activity) {
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Perform_operation'/>",//确定要执行此操作吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type:'POST',
  				url:'/hrm/empinfo/updateTmpEmpBatchInfo',
  				data:{SEQ:$("#viewTmpEmpBatchList_SEQ",navTab.getCurrentPanel()).val(),ACTIVITY:activity},
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

function deleteTmpEmpBatchInfo(activity) {
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete'/>",//确定要执行此操作吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type:'POST',
  				url:'/hrm/empinfo/deleteTmpEmpBatchInfo',
  				data:{SEQ:$("#viewTmpEmpBatchList_SEQ",navTab.getCurrentPanel()).val(),ACTIVITY:activity},
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
</script>
<div class="pageHeader">
<form id="viewTmpEmpBatchListForm" onsubmit="return navTabSearch(this);" action="/hrm/empinfo/viewTmpEmpBatchList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="hrm.empinfo.TRANS_CODE_NAME"/><!-- 注册日 --></td>
		<td>
			<select id="viewTmpEmpBatchList_SEQ" name="seach_SEQ">
				<c:forEach items="${viewRegisterInfoList}" var="result">
					<option value="${result.SEQ}" <c:if test="${result.SEQ eq SEQ}">selected</c:if>>${result.REGISTER_DATE}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
				</c:forEach>
			</select>
		</td>
		<c:if test="${LoginUser.empID eq '43180052' || LoginUser.empID eq '11111111'}">
			<td>
				<a class="buttonActive" href="/hrm/recruitManage/viewAddRegisterInfo?FLAG=3" target="dialog" mask="true" width="350" height="150" rel="addRegisterInfo">
					<span><spring:message code="hrm.empinfo.REGISTRATION"/><!-- 注册 --></span>
				</a>
			</td>
			<c:if test="${empty tmpEmpAffirmList}">
				<td>
					<a class="buttonActive" href="#" onclick="updateTmpEmpBatchInfo('3')">
						<span><spring:message code="ar.viewardetail.title.release"/><!-- 发令确定 --></span>
					</a>
				</td>
			</c:if>
			<c:if test="${not empty tmpEmpAffirmList}">
				<td>
					<a class="buttonActive" href="#" onclick="updateTmpEmpBatchInfo('2')">
						<span><spring:message code="ess.title.close"/><!-- 发令确定 --></span>
					</a>
				</td>
			</c:if>
		</c:if>
	</tr>
</table>
</div>
</form>
</div>
<c:if test="${LoginUser.empID eq '43180052' || LoginUser.empID eq '11111111'}">
	<div class="formBar">
		<ul class="toolBar">
			
			<li><a class="buttonActive" href="/pa/excelExport/downloadExcelExpTemplate?file=MassAction_SalaryChange">
			<span><spring:message code="ar.addempshift.title.downloadmodule"/><!-- 下载导入模板 --></span></a></li>
			<%-- <li><a class="buttonActive" href="#" onclick="executeExperienceBatch('SALARY_BATCH')">
			<span><spring:message code="hrm.recruitManage.Confirm"/><!-- 发令确定 --></span></a></li> --%>
			<li><a class="buttonActive" id="viewTmpEmpBatchList_Serch" href="#">
			<span><spring:message code="button.search"/><!-- 查询 --></span></a></li>
			<li>
				<a class="buttonActive" href="#" onclick="deleteTmpEmpBatchInfo()">
					<span><spring:message code="button.delete"/><!-- 删除 --></span>
				</a>
			</li>
			<%-- <li><a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=39&SEQ=${ SEQ}">
			<span><spring:message code="hrm.empinfo.EXPORT"/><!-- 导出到EXECL --></span></a></li> --%>
			<li><a class="add" href="/pa/excelImport/importExcelData?importFunName=/importSalaryIncementTemp&REGISTER_SEQ=${SEQ}" target="dialog" mask="true">
			<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span></a></li>
	
		</ul>
	</div>
</c:if>
<div class="pageContent">
				<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewTmpEmpBatchListCnt}</div>
				<table class="list" width="100%" layoutH="120">
					<thead>
						<tr>
							<th width="2%">No.</th>
							<th width="6%"><spring:message code="hrm.empinfo.empid"/><!-- 社号 --></th>
							<th width="8%"><spring:message code="hrm.empinfo.name"/><!-- 姓名 --></th>
							<th width="8%"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!-- 部门 --></th>
							<th width="8%"><spring:message code="hrm.empinfo.POST_FAMILY"/><!-- 职群 --></th>
							<th width="6%"><spring:message code="hrm.contract.Rank"/><!-- 职级 --></th>
							<th width="8%"><spring:message code="hrm.contract.POSITION_NO"/><!-- 职责 --></th>
							<th width="6%"><spring:message code="ess.viewpersonalpainfo.jibengongzi"/><!-- 发令日期 --></th>
							<th width="6%"><spring:message code="pa.insurance.title.zhizejintie"/><!-- 发令区分 --></th>
							<th width="6%"><spring:message code="edu.trainAgreement.JIAOTONGFEI.a"/><!-- 发令原因 --></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${tmpEmpAffirmList}" var="item" varStatus="i">
							<tr target="REGISTER_SEQ" rel="${item.REGISTER_SEQ}">
								<td class='td_center'>${i.count}</td>
								<td>${item.EMPID}</td>
								<td>${item.LOCAL_NAME}</td>
								<td>${item.DEPTNAME}</td>
								<td>${item.POST_FAMILY_NAME}</td>
								<td>${item.POST_GRADE_NAME}</td>
								<td>${item.POSITION_NAME}</td>
								<td style="text-align: right;">${item.BASIC_SALARY}</td>
								<td style="text-align: right;">${item.POSITION_SALARY}</td>
								<td style="text-align: right;">${item.TRANSPORT_SALARY}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
