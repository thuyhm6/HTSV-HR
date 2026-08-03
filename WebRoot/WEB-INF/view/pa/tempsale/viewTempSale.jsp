<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//导入数据
function importExcelTempSalesData_pa0701(){
	$("#importExcelDialog_pa0701").attr('href','/pa/excelImport/importExcelData?importFunName=/importExcelTempSalesData');
	$("#importExcelDialog_pa0701").click();
}
function submitTempSalary_pa0701(flag){//1:提交 2：删除
	$("#FLAG_pa0701").val(flag);
	var msg = "";
	if(flag == 1){
		msg = "确定要提交吗?";
		$(":input[submitFlag='1']").each(function(){
				$(this).attr("checked",false);
			});
		
	}else{
		msg = "确定要删除吗?";
	}
	alertMsg.confirm(msg, {
        okCall: function(){
		$("#submitTempSalary_pa0701").submit();
        }
    });
}
function validateCallbackTempSalary_pa0701(form, callback) {
	var $form = $("#submitTempSalary_pa0701");
		if (!$form.valid()) {
			return false;
		}
		var checked=false;
		var ids= document.getElementsByName("pa0701Check");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			//请选择信息再进行保存操作
			if($("#FLAG_pa0701").val() == 1){
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

function openUpdateTempSalaryWin_pa0701(){
	var checked=false;
	var eventId="";
	var ids= document.getElementsByName("pa0701Check");
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
</script>
<a id="importExcelDialog_pa0701"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_pa0701"  href="#" target="navTab" mask="true"><span style="display:none;">临促工资导入结果</span></a>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);"
	action="/pa/tempsale/viewTempSale" rel="pagerForm" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>Event名称</td>
		<td><input type="text" id="seach_EVENT_NAME" name="seach_EVENT_NAME"
			value="${EVENT_NAME}" /></td>
		<td>支社</td>
		<td>
						<select name="seach_DEPTNO">
							<option value="">全部</option>
							<c:forEach items="${branchList}" var="item" varStatus="i">
								<option value="${item.ACC_ORG_CODE }" <c:if test="${item.ACC_ORG_CODE eq DEPTNO}">selected</c:if>>${item.CONTENT}</option>
							</c:forEach>
						</select>
		</td>
		<td><spring:message code="public.title.startDate" /><!-- 开始日期 -->
		</td>
		<td><input id="seach_START_DATE" type="text"
			name="seach_START_DATE" class="date required" readonly="true"
			value="${START_DATE}" /> <a class="inputDateButton"><spring:message
			code="public.title.choose" /><!-- 选择 --></a></td>
		<td><spring:message code="public.title.endDate" /><!-- 结束日期 -->
		</td>
		<td><input id="seach_END_DATE" type="text" name="seach_END_DATE"
			class="date required" readonly="true" value="${END_DATE}" /> <a
			class="inputDateButton"><spring:message
			code="public.title.choose" /><!-- 选择 --></a></td>
	</tr>
	<tr>
		<td>审批状态</td>
		<td><select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
			<option value="">全部</option>
			<option value="-2" <c:if test="${AFFIRM_FLAG eq '-2'}">selected</c:if>>暂存</option>
			<option value="-1" <c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>>提交</option>
			<option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>审批中</option>
			<option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
			<option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
		</select></td>
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
</ul>
</div>
</div>
</form>
</div>

<div class="pageContent">
<form id="submitTempSalary_pa0701" method="post" action="/pa/tempsale/submitTempSalary" class="pageForm required-validate" 
     	onsubmit="return validateCallbackTempSalary_pa0701(this, navTabAjaxDone)">
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive"
			href="/pa/tempsale/viewAddPaTempSales"
			target="dialog" mask="true"
			rel="addPaBasicData" width="900" height="450"><span> <spring:message
			code="button.add" /><!--添加--></span></a></li>
		<li><a class="buttonActive" onclick="submitTempSalary_pa0701(2)"><span>删除</span></a></li>
		<li><a class="buttonActive"
			href="/pa/tempsale/downloadExcelTemplate?type=1"><span>
		<spring:message code="pa.insurance.title.downloadImportTemplate" /><!--下载导入模板--></span>
		</a></li>
		<li><a class="buttonActive" onclick="importExcelTempSalesData_pa0701()"> <span><spring:message
			code="ar.addempshift.title.excelimport" /><!-- EXCEL导入 --></span> </a></li>
		<li><a class="buttonActive" onclick="submitTempSalary_pa0701(1)"><span>提交</span></a></li>
	</ul>
</div>
<table class="table" width="120%" layoutH="235" nowrapTD="false">
	<thead>
		<tr>
			<th width=30><input type="checkbox" class="checkboxCtrl" group="pa0701Check" /></th>
			<th width=300>Event名称</th>
			<th width=70>EventID</th>
			<th width=100>Event部门</th>
			<th width=70>开始日期</th>
			<th width=70>结束日期</th>
			<th width=55>总人数</th>
			<th width=60>总金额</th>
			<th width=65>工资支付月</th>
			<th width=55>审批查看</th>
			<th width=55>审批状态</th>
			<th width=55>数据状态</th>
			<th>备注</th>
			<th width=55>申请人</th>
			<th width=70>申请时间</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${paTempSalesList}" var="item" varStatus="i">
		   
			<tr>
				<td class="td_center" >
				<c:if test="${item.ACTIVITY ne '0'}">
					<c:if test="${item.SUBMIT_STATUS eq '0'}">
						<input type="checkbox" name="pa0701Check" value="${item.EVENT_ID }" submitFlag="0"/>
					</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '-1' and item.SUBMIT_STATUS eq '1'}">
						<input type="checkbox" name="pa0701Check" value="${item.EVENT_ID }" submitFlag="1"/>
					</c:if>
				</c:if>
				</td>
				<td style="text-align:left">
				   <c:if test="${item.ACTIVITY ne '0'}">
					<c:if test="${item.SUBMIT_STATUS eq '0'}">
						<a class="add" href="/pa/tempsale/viewUpdatePaTempSales?EVENT_ID=${item.EVENT_ID }" title="修改"
					         target="dialog"  rel="updateTempSalaryWin" width="750" height="500" mask="true"><font color="red">${item.EVENT_NAME }</font></a>
					</c:if>
					<c:if test="${item.SUBMIT_STATUS ne '0'}">
						${item.EVENT_NAME }
					</c:if>
				   </c:if>
				   <c:if test="${item.ACTIVITY eq '0'}">
				      <font color=grey>${item.EVENT_NAME }</font>
				   </c:if>
				</td>
				<td class="td_center">${item.EVENT_ID }</td>
				<td class="td_center">${item.EVENT_DEPTNO }</td>
				<td class="td_center">${item.START_DATE }</td>
				<td class="td_center">${item.END_DATE }</td>
				<td class="td_center">${item.TOTAL_NUM }</td>
				<td class="td_center">${item.TOTAL_SALARY }</td>
				<td class="td_center">${item.PAY_DATE }</td>
				<td class="td_center">
				<a href="/pa/tempsale/viewTempSaleEmpInfoList?pageNum=1&navTabId=pa0701_EMPINFO&EVENT_ID=${item.EVENT_ID }&SUBMIT_STATUS=${item.SUBMIT_STATUS}" title="审批查看" 
					target="navTab" rel="pa0701_EMPINFO">查看</a>
				</td>
				<td class="td_center">
					<c:if test="${item.SUBMIT_STATUS eq '0'}">暂存</c:if>
					<c:if test="${item.SUBMIT_STATUS eq '1'}">
						<c:if test="${item.AFFIRM_FLAG eq '-1'}">提交</c:if>
						<c:if test="${item.AFFIRM_FLAG eq '0'}">审批中</c:if>
						<c:if test="${item.AFFIRM_FLAG eq '1'}">通过</c:if>
						<c:if test="${item.AFFIRM_FLAG eq '2'}">否决</c:if>
					</c:if>
				</td>
				<td class="td_center">
					<c:if test="${item.ACTIVITY eq '1'}">正常</c:if>	
					<c:if test="${item.ACTIVITY eq '0'}"><font color=red>删除</font></c:if>	
				</td>
				<td>${item.REMARK }</td>
				<td class="td_center">
					${item.LOCAL_NAME }		
				</td>
				<td class="td_center">
					${item.CREATE_DATE }		
				</td>
				
			</tr>
		</c:forEach>
	</tbody>
</table>
<input type="hidden" id="FLAG_pa0701" name="FLAG" value="0" />
</form>
<c:set value="/pa/tempsale/viewTempSale" var="pageUrl" /> 
<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>