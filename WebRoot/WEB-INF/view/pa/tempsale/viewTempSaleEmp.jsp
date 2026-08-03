<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//导入数据
function importExcelTempSalesData(){
	$("#importExcelDialog_pa0701").attr('href','/pa/excelImport/importExcelData?importFunName=/importExcelTempSalesData');
	$("#importExcelDialog_pa0701").click();
}
function submitTempSalary(flag){//1:提交 2：删除
	$("#FLAG").val(flag);
	var msg = "";
	if(flag == 1){
		msg = "确定要提交吗?";
	}else{
		msg = "确定要删除吗?";
	}
	alertMsg.confirm(msg, {
        okCall: function(){
		$("#submitTempSalary").submit();
        }
    });
}
function selectAll(parentCheckBox){
	var flag = parentCheckBox.checked;
	var ids= document.getElementsByName("isChecked");
	for(var i=0;i<ids.length;i++){
		ids[i].checked=flag;
	}
}
function validateCallbackTempSalary(form, callback) {
	var $form = $("#submitTempSalary");
		if (!$form.valid()) {
			return false;
		}
		var checked=false;
		var ids= document.getElementsByName("isChecked");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			//请选择信息再进行保存操作
			if($("#FLAG").val() == 1){
				alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked.tijiao"/>');
			}else{
				alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked.shanchu"/>');
			}
			return false;
		}
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		return false;
}

function openUpdateTempSalaryWin(){
	var checked=false;
	var eventId="";
	var ids= document.getElementsByName("isChecked");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			eventId = ids[i].value;
			break;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked.xiugai"/>');
		return false;
	}else{
		$.pdialog.open("/pa/tempsale/viewUpdatePaTempSales?EVENT_ID=" + eventId, "updateTempSalaryWin", "临促工资修改", {width:800,height:500,mask:true});
	}
}


function exportContractInfo_pa0714() {
	var sform = document.getElementById("tempSaleEmp");
	alertMsg.confirm("Do you want to export?", {
		okCall : function() {
			//用于excel导出的表单参数处理
			var eForm = document.getElementById("excelExportForm_pa0714"); 
			document.getElementById("pa0714Link").innerHTML = "EXCEL密码设置";
			eForm.KEY.value 			= sform.seach_KEY.value;
			eForm.DEPTNO.value 		= sform.seach_DEPTNO.value;
			eForm.IS_BLACK_LIST.value 			= sform.seach_IS_BLACK_LIST.value;
			eForm.EVS_GRADE.value 		= sform.seach_EVS_GRADE.value;
			$("#importExcelDialog_pa0714").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/pa/tempsale/viewTempSaleSEmpInfoExcel"
					+"&navTabId=pa0714"
					+"&formId=excelExportForm_pa0714");
			$("#importExcelDialog_pa0714").attr('width', "300");
			$("#importExcelDialog_pa0714").attr('height', "150");
			$("#importExcelDialog_pa0714").click();
		}
	});
}
</script>
<a id="importExcelDialog_pa0714" href="#" target="dialog" mask="true">
<span id="pa0714Link" style="display: none"></span></a> 
<div class="pageHeader">
<form id="tempSaleEmp" onsubmit="return navTabSearch(this);"
	action="/pa/tempsale/viewTempSaleEmp" rel="pagerForm" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>关键字</td>
		<td><input type="text" id="seach_KEY" name="seach_KEY"
			value="${KEY}" />
			<input type="hidden" id="seach_FIRST_FLAG" name="seach_FIRST_FLAG" value="1" />
		</td>
		<td>支社</td>
		<td>
						<select name="seach_DEPTNO">
							<OPTION value="">全部</OPTION>
							<c:forEach items="${branchList}" var="item" varStatus="i">
								<OPTION value="${item.ACC_ORG_CODE }" <c:if test="${item.ACC_ORG_CODE eq DEPTNO}">selected</c:if>>${item.CONTENT}</OPTION>
							</c:forEach>
						</select>
		</td>
		<td>黑名单与否</td>
		<td>
			<select name="seach_IS_BLACK_LIST">
				<option value="">请选择</option>
				<option value="Y" <c:if test="${IS_BLACK_LIST eq 'Y' }">selected</c:if>>Y</option>
				<option value="N" <c:if test="${IS_BLACK_LIST eq 'N' }">selected</c:if>>N</option>
			</select>
		</td>
		<td>评价等级</td>
		<td><ait:SelectSyCodeByCpnyID id="seach_EVS_GRADE" name="seach_EVS_GRADE" parentNo="123195" selected="${EVS_GRADE}" cnpyID="${defaultCpny}" limit="all"/></td>
	</tr>
</table>
<div class="subBar">
<ul>
	<li>
	<div class="buttonActive">
	<div class="buttonContent">
	<button type="submit"><spring:message
		code="public.title.search" /><!-- 检索 --></button>
	</div>
	</div>
	</li>
	<li>
					<div class="buttonActive"><div class="buttonContent"><!-- 检索 -->
						<button type="button" onclick="exportContractInfo_pa0714()" title="<spring:message code='rp.report.title.exportYN'/>">
							<%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/>
						</button>
					</div></div>
				</li>
</ul>
</div>
</div>
</form>
</div>

<div class="pageContent">
<c:if test="${toolbarInfo.UPDATER eq '1'}">
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" width="800" height="280"
			href="/pa/tempsale/viewPaTempSalesEmpInfoUpdate?ID_CARD={ID_CARD}"
			target="dialog" mask="true"
			rel="viewPaTempSalesEmpInfoUpdate"><span><spring:message 
			code="button.update" /><!--修改--></span></a></li>
	</ul>
</div>
</c:if>
<table class="table" width="100%" layoutH="210" nowrapTD="false">
	<thead>
		<tr>
			<th>NO</th>
			<th>姓名</th>
			<th>出生日期</th>
			<th>身份证号</th>
			<th>银行账号</th>
			<th>开户行</th>
			<th>联系方式</th>
			<th>评价等级</th>
			<th>黑名单与否</th>
			<th>开始日期</th>
			<th>所属部门</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${paTempSalesEmpList}" var="item" varStatus="i">
			<tr target="ID_CARD" rel="${item.ID_CARD }">
				<td class="td_center" >${i.count }</td>
				<td class="td_center" >${item.LOCAL_NAME }</td>
				<td class="td_center" >${item.BIRTH_DATE }</td>
				<td class="td_center" >${item.ID_CARD }</td>
				<td class="td_center" >${item.BANK_NO }</td>
				<td class="td_center" >${item.BANK_NAME }</td>
				<td class="td_center" >${item.CELLPHONE }</td>
				<td class="td_center" >${item.EVS_GRADE_NAME }</td>
				<td class="td_center" >${item.IS_BLACK_LIST }</td>
				<td class="td_center" >${item.START_DATE }</td>
				<td class="td_center" >${item.DEPT_NAME_DISPLAY }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/pa/tempsale/viewTempSaleEmp?seach_KEY=${KEY}&seach_IS_BLACK_LIST=${IS_BLACK_LIST }&seach_EVS_GRADE=${EVS_GRADE }" var="pageUrl" /> 
<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>

<form id="excelExportForm_pa0714" name="excelExportForm_pa0714" method="post">
	<input type="hidden" id="password" 			name="password" 		value="" />
	<input type="hidden" id="DEPTNO" 				name="DEPTNO" 			value="" />
	<input type="hidden" id="IS_BLACK_LIST" 			name="IS_BLACK_LIST" 			value="" />
	<input type="hidden" id="EVS_GRADE" 			name="EVS_GRADE" 			value="" />
	<input type="hidden" id="KEY" 		name="KEY" 		value="" />
</form>
</div>