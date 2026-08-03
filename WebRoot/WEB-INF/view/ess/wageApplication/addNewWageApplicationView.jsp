<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script src="/resources/js/ajaxfileupload.js" type="text/javascript"></script>
<script>
//添加决裁者
function addRowByIDPa0701(currentRowID) {
	var count = parseInt($("#affirmCount").val());
	var htm = '<tr id="rowIdPa0701' + count + '"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
	htm += '<td class="td_type" style="text-align: center" width="33%">';
	htm += '<input id="AFFIRMOR_IDPa0701' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
	htm += '<input id="EMPINFOPa0701' + count
			+ '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor(this,'
			+ count + ',event)" class="required"/>';
	htm += '</td>';
	htm += '<td class="td_type" style="text-align: center" width="33%">';
	htm += '<img src="/resources/images/+.gif" title="添加"';
	htm += 'border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDPa0701(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
	htm += '<img src="/resources/images/-.gif" title="删除"';
	htm += 'border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listPa0701.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevel();"/></td></tr>';

	//当前行之后插入一行
	$("#rowIdPa0701" + currentRowID).after(htm);
	changeAffirmLevel();
	$("#affirmCount").val(++count);
}
//修改决裁者等级
function changeAffirmLevel() {
	var tb2 = document.getElementById("addAffirm_listPa0701");
	var rowCount = tb2.rows.length;
	for ( var m = 0; m < rowCount; m++) {
		tb2.rows[m].cells[0].innerHTML = m + 1;
	}
}
var keyCodeInit = 0;
function submitKeyClick_affirmor(obj, index, event) {
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
	var e = event ? event : window.event;
	var keyCode = e.which ? e.which : e.keyCode;
	if (keyCode == 13) {
		keyCodeInit = keyCode;
		var empid = obj.value;
		var empIdStr = obj.id;
		var personIdStr = "personId" + empIdStr.substring(7);
		var empNameStr = "empName" + empIdStr.substring(7);
		$.ajax( {
			type : 'POST',
			url : encodeURI('/sys/affirm/getPersonCnt?navTabId='
					+ navTabId + '&EMPID=' + empid + '&LOCAL_NAME='
					+ localName + '&IDCARD_NO=' + idcardNo),
			dataType : "json",
			cache : false,
			success : function(jsonObject) {
				if (jsonObject.perCnt == 0) {
					alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
				}
				if (jsonObject.perCnt > 1) {
					document.getElementById("onck").href = encodeURI(encodeURI("/sys/arAffirmPost/viewAffirmorsEmpIdList?pageNum=1&navTabId=" + navTabId
							+ '&seach_EMPID=' + empid
							+ '&seach_LOCAL_NAME=' + localName
							+ '&seach_IDCARD_NO=' + idcardNo
							+ '&empId_sy0482=' + empIdStr
							+ '&personId_sy0482=' + personIdStr
							+ '&empName_sy0482=' + empNameStr));
					document.getElementById("onck").click();
				}
				if (jsonObject.perCnt == 1) {
					$("#EMPINFOPa0701" + index).val(
							'[' + jsonObject.empId + ']-'
									+ jsonObject.empName);
					$("#AFFIRMOR_IDPa0701" + index).val(jsonObject.personId);
				}
			},
			error : DWZ.ajaxError
		});
	}
}

/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
document.onkeydown = function(event) {
	var target, code, tag;
	if (!event) {
		event = window.event; //针对ie浏览器  
		target = event.srcElement;
		code = event.keyCode;
		if (code == 13) {
			tag = target.tagName;
			if (tag == "TEXTAREA") {
				return true;
			} else {
				return false;
			}
		}
	} else {
		target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
		code = event.keyCode;
		if (code == 13) {
			tag = target.tagName;
			if (tag == "INPUT") {
				return false;
			} else {
				return true;
			}
		}
	}
}
function validateCallbackViewPaTempSales(form, callback) {
	var $form = $("#viewAddPaTempSales");
	if (!$form.valid()) {
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

function saveTempSalary(flag){
	$("#FLAG").attr('value',flag);
	if(flag == '0'){
		$('#applyContent').attr('value','@@@');
		$("#viewAddPaTempSales").submit();
	}else if(document.getElementById("applyContent") && document.getElementById("applyContent")!= null){
		$('#fileUrl').attr('value',$("#file1").val());
		$("#viewAddPaTempSales").submit();
	}else{
		alertMsg.info('尚未添加任何信息，无法提交!');
	}
}
function openFile(){
	$.pdialog.open("/ess/wageApplication/uploadApplicationFile", "login", "上传附件", {mask:true,width:420,height:200});
}
function deleteFile(id){
	alertMsg.confirm("确定要删除附件么?",{okCall:function(){
		$.ajax({
			type: 'POST',
			url:"/ess/wageApplication/deleteApplicationFile",
			data:{"NEWNAME":id},
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
					alertMsg.correct(data.message);
					document.getElementById(id).style.display= "none";
				}else{
					if(data.result=="2"){
						alertMsg.info(data.message);
					}else{
						alertMsg.error(data.message);
					}
				}   
	   	 	}  ,
			error: DWZ.ajaxError
		});
	}});
}
</script>
<a id="importExcel_ess333"  href="#" target="navTab" mask="true"><span style="display:none;">导入结果</span></a>
<div class="pageContent">
	<div style="float:right">
		<a class="buttonActive" href="/ess/wageApplication/exportEssApplicationModule">
			<span><!--下载导入模板--><spring:message code="pa.insurance.title.downloadImportTemplate"/></span> 
		</a>
		<a class="buttonActive" href="/pa/excelImport/importExcelData?importFunName=/importEssApplication"
			target="dialog" mask="true" width="500" height="200"> 
			<span><!--EXCEL导入 --><spring:message code="ar.addempshift.title.excelimport"/></span> 
		</a>
	</div>
</div>
<div class="pageContent">
	<div>
		<table width="100%" class="user_table margin_b" layoutH="330">
			<tr>
				<td class="td_title" style="text-align: center" width="20%">申请者/工号</td>
				<td class="td_title" style="text-align: center" width="20%">费用类型</td>
				<td class="td_title" style="text-align: center" width="20%">申请费用发放期间</td>
				<td class="td_title" style="text-align: center" width="20%">金额</td>
				<td class="td_title" style="text-align: center" width="20%">备注</td>
			</tr>
			<c:forEach items="${excelList}" var="excel" varStatus="j">
				<tr>
					<td class="td_type" style="text-align: center" width="20%">${excel.EMPNAME}[${excel.COSTEMP}]</td>
					<td class="td_type" style="text-align: center" width="20%">${excel.TYPENAME}</td>
					<td class="td_type" style="text-align: center" width="20%">${excel.START_DATE}~${excel.END_DATE}</td>
					<td class="td_type" style="text-align: center" width="20%">${excel.MONEY}</td>
					<td class="td_type" style="text-align: center" width="20%">${excel.DEMO}</td>
				</tr>
				<c:if test="${fn:length(messageList)==i.count}">
					<tr>
						<td class="td_type" width="15%" style="text-align: center"></td>
						<td class="td_type" width="15%" style="text-align: center"></td>
						<td class="td_type" width="30%" style="text-align: center"></td>
						<td class="td_type" width="15%" style="text-align: center">追加款：${excel.ZHENG }</td>
						<td class="td_type" width="15%" style="text-align: center">追减款：${excel.FU}</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<c:set value="/ess/wageApplication/addNewWageApplicationView" var="pageUrl" />
		<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</div>
</div>
<div class="pageContent">
	<form id="viewAddPaTempSales" method="post" action="/ess/wageApplication/saveNewWageApplicationView"
		class="pageForm required-validate" onsubmit="return validateCallbackViewPaTempSales(this, navTabAjaxDone)">
		<div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<c:if test="${fn:length(excelList)>0}">
					<tr>
						<td class="td_title" style="text-align: center" width="20%">申请内容：</td>
						<td class="td_type" width="80%" size="200" colspan="3"><textarea type="text" id="applyContent" name="TITLE" class="required" cols="180" rows="5"></textarea></td>
					</tr>
					<tr>
						<td class="td_title" style="text-align: center" width="20%">上传附件：</td>
						<td class="td_type" width="80%" colspan="3"><%--<input type="file" name="file" id="file1" size="100">--%>
						<input type="text" id="fileAppUrl" onClick="openFile();" size="100"><a class="buttonActive" style="float:right;padding-right:400px" href="javascript:void(0)" onClick="openFile();"><span>上传附件</span></a><BR>
						<c:forEach items="${fileList}" var="file" varStatus="k">
							<a id="${file.NEWNAME}">${file.FILENAME}&nbsp;&nbsp;<img src="/resources/images/N.gif" title="删除" style="cursor:hand" onclick="deleteFile('${file.NEWNAME}')"/></br></a>
						</c:forEach>
						<input type="hidden" name="oldAppName" id="oldAppName"/>
						<input type="hidden" name="newAppName" id="newAppName"/>
						</td>
					</tr>
					<tr><td class="td_type" colspan="4" height="10px"></td></tr>
				</c:if>
				<tr>
					<td class="td_title" style="text-align: center" width="20%" rowspan="2">决裁线</td>
					<td class="td_title" style="text-align: center" width="10%">决裁等级</td>
					<td class="td_title" style="text-align: center" width="40%">决裁者</td>
					<td class="td_title" style="text-align: center" width="30%">是否新增</td>
				</tr>
				<tr>
					<td colspan="3">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_listPa0701">
							<tbody>
								<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">
									<tr id="rowIdPa0701${j.index}">
										<td class="td_type" style="text-align: center" width="33%">${j.count}</td>
										<td class="td_type" style="text-align: center" width="33%">
											[${affirmor.EMPID}]-${affirmor.LOCAL_NAME }
											<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID }" />
										</td>
										<td class="td_type" style="text-align: center" width="33%">
										 <c:if test="${fn:length(affirmorList) gt j.count}">
											<img src="/resources/images/+.gif" title="添加" border="0"
												align="absmiddle" style="cursor: hand" onclick="addRowByIDPa0701(${j.index})" />
										 </c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<input type="hidden" name="count" id="count" value="0">
		<input type="hidden" name="affirmCount" id="affirmCount" value="${fn:length(affirmorList)}">
		<input type="hidden" id="FLAG" name="FLAG" value="1"/>
			
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="saveTempSalary(1)"><!-- 提交 --><spring:message code="public.title.submit"/></button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close" onclick="saveTempSalary(0)"><!--取消 --><spring:message code="public.title.cancle"/></button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>