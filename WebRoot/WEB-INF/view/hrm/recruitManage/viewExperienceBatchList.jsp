<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewExperienceBatchList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewExperienceBatchListForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewExperienceBatchList_SEQ").change(function(){
		$("#viewExperienceBatchListForm",navTab.getCurrentPanel()).submit();
	});
	//保存
	$("#viewExperienceBatchList_save",navTab.getCurrentPanel()).click(function(){	
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
			jsonData += ' "DEPTNO": "' + $("#viewExperienceBatchList_DEPTNO_" + i,navTab.getCurrentPanel()).val() + '" ,';
			//jsonData += ' "IS_PART_TIME": "' + IS_PART_TIME + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ,';
			jsonData += ' "RESUME_NO": "' + '${RESUME_NO}' + '" ,';
			jsonData += ' "MANAGER_PERSON_ID": "' + $("#viewExperienceBatchList_PERSON_ID_" + i,navTab.getCurrentPanel()).val() + '" ';
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
  				data:{empIds:$("#viewExperienceBatchList_SEQ",navTab.getCurrentPanel()).val(),type:typeStr},
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
<form id="viewExperienceBatchListForm" onsubmit="return navTabSearch(this);" action="/hrm/recruitManage/viewExperienceBatchList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="hrm.empinfo.REGISTRATION_DATE"/><!-- 注册日 --></td>
		<td>
			<select id="viewExperienceBatchList_SEQ" name="seach_SEQ">
				<c:forEach items="${viewRegisterInfoList}" var="result">
					<option value="${result.SEQ}" <c:if test="${result.SEQ eq SEQ}">selected</c:if>>${result.REGISTER_DATE}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
				</c:forEach>
			</select>
		</td>
		<td>
				<a class="w_button" href="/hrm/recruitManage/viewAddRegisterInfo?FLAG=2" target="dialog" mask="true" width="350" height="150" rel="addRegisterInfo">
					<span><spring:message code="hrm.empinfo.REGISTRATION"/><!-- 注册 --></span>
				</a>
		</td>
	</tr>
</table>
</div>
</form>
</div>

<div class="formBar">
	<ul class="toolBar">
		<c:if test="${ACTIVITY eq '1'}">
		<li><a class="buttonActive" id="viewExperienceBatchList_Serch" href="#">
		<span><spring:message code="button.search"/><!-- 查询 --></span></a></li>
		</c:if>
		<c:if test="${ACTIVITY ne '1'}">
		<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			<li><a class="buttonActive" href="/pa/excelExport/downloadExcelExpTemplate?file=MassAction_DeptChange">
			<span><spring:message code="ar.addempshift.title.downloadmodule"/><!-- 下载导入模板 --></span></a></li>
		</c:if>
		<c:if test="${LoginUser.cpnyId eq 'HAE'}">
			<li><a class="buttonActive" href="/pa/excelExport/downloadExcelExpTemplateHAE?file=MassAction_DeptChange">
			<span><spring:message code="ar.addempshift.title.downloadmodule"/><!-- 下载导入模板 --></span></a></li>
		</c:if>
		<li><a class="buttonActive" href="#" onclick="executeExperienceBatch('EXP_BATCH')">
		<span><spring:message code="hrm.recruitManage.Confirm"/><!-- 发令确定 --></span></a></li>
		<li><a class="buttonActive" id="viewExperienceBatchList_Serch" href="#">
		<span><spring:message code="button.search"/><!-- 查询 --></span></a></li>
		<li><a class="buttonActive" href="/hrm/recruitManage/deleteExperienceBatchInfo?SEQ={SEQ}" target="ajaxTodo" callback="navTabAjaxDoneWithForm" title="<spring:message code="hrm.alert.empinfo.Sure.delete"/>"><!-- 确定要删除吗? -->
		<span><spring:message code="button.delete"/><!-- 删除 --></span></a></li>
		<li><a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=39&SEQ=${ SEQ}">
		<span><spring:message code="hrm.empinfo.EXPORT"/><!-- 导出到EXECL --></span></a></li>
		<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			<li><a class="add" href="/pa/excelImport/importExcelData?importFunName=/importExperienceTemp&REGISTER_SEQ=${SEQ}" target="dialog" mask="true">
			<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span></a></li>
		</c:if>
		<c:if test="${LoginUser.cpnyId eq 'HAE'}">
			<li><a class="add" href="/pa/excelImport/importExcelData?importFunName=/importExperienceTempHAE&REGISTER_SEQ=${SEQ}" target="dialog" mask="true">
			<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span></a></li>
		</c:if>
		</c:if>
	</ul>
</div>

<div class="pageContent">
				<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewExperienceBatchListCnt}</div>
				<table class="list" width="2000px" layoutH="120">
					<thead>
						<tr>
							<th width="20px">No.</th>
							<th width="30px"><spring:message code="hrm.empinfo.Already_processed"/><!-- 已处理 --></th>
							<th width="30px"><spring:message code="hrm.empinfo.name"/><!-- 姓名 --></th>
							<th width="30px"><spring:message code="hrm.empinfo.empid"/><!-- 社号 --></th>
							<th width="50px"><spring:message code="hrm.empinfo.starter_START_DATE"/><!-- 发令日期 --></th>
							<th width="50px"><spring:message code="org.title.EXPERIENCE_TYPE_NAME"/><!-- 发令区分 --></th>
							<th width="50px"><spring:message code="org.title.TRANS_REASON_NAME"/><!-- 发令原因 --></th>
							<th width="50px"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!-- 部门 --></th>
							<th width="50px"><spring:message code="hrm.empinfo.POST_FAMILY"/><!-- 职群 --></th>
							<th width="50px"><spring:message code="hrm.contract.Rank"/><!-- 职级 --></th>
							<th width="50px"><spring:message code="hrm.contract.POSITION_NO"/><!-- 职责 --></th>
							<th width="50px"><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME"/><!-- 员工类型 --></th>
							<th width="50px"><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME"/><!-- 主要业务 --></th>
							<th width="50px"><spring:message code="hrm.empinfo.COST_CENTER_NAME_LOCAL"/><!-- 成本中心 --></th>
							<c:if test="${LoginUser.cpnyId eq 'HAE'}">
								<th width="50px"><spring:message code="hrm.recruitManage.NIANZI_DENGJI.Z"/><!-- 年资等级 --></th>
							</c:if>
							<th width="50px"><spring:message code="ar.viewarcardrecord.title.beizhu"/><!-- 备注 --></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewExperienceBatchList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>
									<input type="checkbox" name="ACTIVITY" value="1"
										<c:if test="${item.ACTIVITY eq '1' }">checked</c:if> disabled/>
									<input type="hidden" id="SEQ_${i.index}" value="${item.SEQ}"/>
								</td>
								<td>${item.LOCAL_NAME}</td>
								<td>${item.EMPID}</td>
								<td>${item.START_DATE}</td>
								<td>${item.TRANS_CODE_NAME}</td>
								<td>${item.TRANS_REASON_NAME}</td>
								<td>${item.DEPTNAME}</td>
								<td>${item.POST_FAMILY_NAME}</td>
								<td>${item.NEW_POST_GRADE_NO_NAME}</td>
								<td>${item.POSITION_NO_NAME}</td>
								<td>${item.EMP_TYPE_CODE_NAME}</td>
								<td>${item.MAIN_BUSINESS_NAME}</td>
								<td>${item.COST_CENTER_NAME}</td>
								<c:if test="${LoginUser.cpnyId eq 'HAE'}">
									<td>${item.PAY_STEP_NAME}</td>
								</c:if>
								<td>${item.REMARKS}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
