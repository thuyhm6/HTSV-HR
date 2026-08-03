<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//导入数据
function importExcelTempSalesAccrualData_pa0711(){
	$("#importExcelDialog_pa0711").attr('href','/pa/excelImport/importExcelData?importFunName=/importExcelTempSalesAccrualData');
	$("#importExcelDialog_pa0711").click();
}
function submitTempSalary_pa0711(flag){//1:提交 2：删除
	$("#FLAG_pa0711").val(flag);
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
		$("#submitTempSalary_pa0711").submit();
        }
    });
}
function validateCallbackTempSalary_pa0711(form, callback) {
	var $form = $("#submitTempSalary_pa0711");
		if (!$form.valid()) {
			return false;
		}
		var checked=false;
		var ids= document.getElementsByName("pa0711Check");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			//请选择信息再进行保存操作
			if($("#FLAG_pa0711").val() == 1){
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
</script>
<a id="importExcelDialog_pa0711"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_pa0711"  href="#" target="navTab" mask="true"><span style="display:none;">临促预提工资导入结果</span></a>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);"
	action="/pa/tempsale/viewTempSaleAccrualList" rel="pagerForm" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>月份</td>
		<td>
	    	<ait:dateProMonth yearName="seach_YEAR" yearSelected="${YEAR}" monthName="seach_MONTH" monthSelected="${MONTH}"/>
		</td>
		<td>决裁状态</td>
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
<form id="submitTempSalary_pa0711" method="post" action="/pa/tempsale/submitTempSalary" class="pageForm required-validate" 
     	onsubmit="return validateCallbackTempSalary_pa0711(this, navTabAjaxDone)">
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" onclick="submitTempSalary_pa0711(2)"><span>删除</span></a></li>
		<li><a class="buttonActive"
			href="/pa/tempsale/downloadExcelTemplate?type=3"><span>
		<spring:message code="pa.insurance.title.downloadImportTemplate" /><!--下载导入模板--></span>
		</a></li>
		<li><a class="buttonActive" onclick="importExcelTempSalesAccrualData_pa0711()"> <span><spring:message
			code="ar.addempshift.title.excelimport" /><!-- EXCEL导入 --></span> </a></li>
		<li><a class="buttonActive" onclick="submitTempSalary_pa0711(1)"><span>提交</span></a></li>
	</ul>
</div>
<table class="table" width="100%" layoutH="210" nowrapTD="false">
	<thead>
		<tr>
			<th align="center"><input type="checkbox" class="checkboxCtrl" group="pa0711Check" /></th>
			<th align="center">大区</th>
			<th align="center">支社</th>
			<th align="center">支付月份</th>
			<th align="center">对应共同社编</th>
			<th align="right">总金额</th>
			<th align="center">审批查看</th>
			<th align="center">附件查看</th>
			<th align="center">审批状态</th>
			<th align="center">数据状态</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${paTempSalesList}" var="item" varStatus="i">
			<tr>
				<td>
				  <c:if test="${item.ACTIVITY ne '0'}">
					<c:if test="${item.SUBMIT_STATUS eq '0'}">
						<input type="checkbox" name="pa0711Check" value="${item.EVENT_ID }" submitFlag="0"/>
					</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '-1' and item.SUBMIT_STATUS eq '1'}">
						<input type="checkbox" name="pa0711Check" value="${item.EVENT_ID }" submitFlag="1"/>
					</c:if>
				  </c:if>
				</td>
				<td>${item.PAY_AREA_NAME }</td>
				<td>
				   <c:if test="${item.ACTIVITY ne '0'}">
					    <c:if test="${item.SUBMIT_STATUS eq '0'}">
								<a href="/pa/tempsale/viewTempSaleAccrualAffirmInfoList1?pageNum=1&navTabId=pa0701_EMPINFO&EVENT_ID=${item.EVENT_ID }&SUBMIT_STATUS=${item.SUBMIT_STATUS}" title="审批查看" 
									target="navTab" ref="pa0701_EMPINFO"><font color="red">${item.EVENT_DEPTNO }</font></a>
					    </c:if>
					    <c:if test="${item.SUBMIT_STATUS ne '0'}">
								${item.EVENT_DEPTNO }
						</c:if>
				   </c:if>
				   <c:if test="${item.ACTIVITY eq '0'}">
				      <font color=grey>${item.EVENT_DEPTNO }</font>
				   </c:if>
				</td>
				<td>${item.PAY_DATE }</td>
				<td>${item.COMMON_EMPID }</td>
				<td>${item.TOTAL_SALARY }</td>
				<td style="text-align: center">
					    <c:if test="${item.SUBMIT_STATUS eq '0'}">
								<a href="/pa/tempsale/viewTempSaleAccrualAffirmInfoList1?pageNum=1&navTabId=pa0701_EMPINFO&EVENT_ID=${item.EVENT_ID }&SUBMIT_STATUS=${item.SUBMIT_STATUS}" title="审批查看" 
									target="navTab" ref="pa0701_EMPINFO">查看</a>
					    </c:if>
					    <c:if test="${item.SUBMIT_STATUS ne '0'}">
								<a href="/pa/tempsale/viewTempSaleAccrualAffirmInfoList?pageNum=1&navTabId=pa0701_EMPINFO&EVENT_ID=${item.EVENT_ID }&SUBMIT_STATUS=${item.SUBMIT_STATUS}" title="审批查看" 
									target="navTab" ref="pa0701_EMPINFO">查看</a>
							
						</c:if>
				</td>
				<td style="text-align: left">
					 <c:forEach items="${item.fileList}" var="file" varStatus="j">	
						<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME }</a></div>
					</c:forEach>
				</td>
				<td>
					<c:if test="${item.SUBMIT_STATUS eq '0'}">暂存</c:if>
					<c:if test="${item.SUBMIT_STATUS eq '1'}">
						<c:if test="${item.AFFIRM_FLAG eq '-1'}">提交</c:if>
						<c:if test="${item.AFFIRM_FLAG eq '0'}">审批中</c:if>
						<c:if test="${item.AFFIRM_FLAG eq '1'}">通过</c:if> 
						<c:if test="${item.AFFIRM_FLAG eq '2'}">否决</c:if>
					</c:if>
				</td>
				<td>
				<c:if test="${item.ACTIVITY eq '1'}">正常</c:if>
				<c:if test="${item.ACTIVITY eq '0'}"><font color="red">删除</font></c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<input type="hidden" id="FLAG_pa0711" name="FLAG" value="0" />
<input type="hidden" id="accural_pa0711" name="accural" value="Y" />
</form>
<c:set value="/pa/tempsale/viewTempSaleAccrualList" var="pageUrl" /> 
<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>