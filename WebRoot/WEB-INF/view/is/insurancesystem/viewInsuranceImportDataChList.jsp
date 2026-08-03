<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
<!--
function pageFromSea(a){
	var seach_RESULT_FLAG=$("#seach_RESULT_FLAG",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_RESULT_FLAG",navTab.getCurrentPanel()).val();
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/is/insurancesystem/viewInsuranceImportDataChList?seach_RESULT_FLAG="+seach_RESULT_FLAG);
}
function delIsParamDataImport(data_no){
	var params = [];
	params.push({
		name: 'DATA_NO',
		value: data_no
	});
		$.ajax({
		  url: '/is/insurancesystem/delIsParamDataImport',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				//alert("删除成功！");
				//页面重载
				navTabSearch(document.viewInsuranceImportDataChList);
			}else{
				alert("删除失败！");
			}
		  }
		});
}
//-->
</script>

<script type="text/javascript">
function exportIsParamDataModel(){
	var url = "/is/insurancesystem/exportIsParamDataModule?navTabId=bx0121&type=AREA_FLAG";
	document.getElementById("exportIsParamDataExcel").href=encodeURI(url);
}

function importIsParamData(){
	$("#importExcelDialog_bx0121").attr('href','/pa/excelImport/importExcelData?importFunName=/importIsParamDataTemp');
	$("#importExcelDialog_bx0121").click();
}

function exportIsParamImportExcel(){
	var url = "/is/insurancesystem/viewImportInsuranceParamDataChExcel?navTabId=bx0121";
	document.getElementById("exportIsParamImportExcel").href=encodeURI(url);
}
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/is/insurancesystem/viewInsuranceImportDataChList" method="post" rel="pagerForm"
		id="viewInsuranceImportDataChList" name="viewInsuranceImportDataChList">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td width="10%">
					<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->：
				</td>
				<td width="20%">
					${totalCnt}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
				<td width="10%">
					<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->：
				</td>
				<td width="20%">							
					${errCnt}
				</td>
				<td width="10%">
					出错与否：
				</td>
				<td width="20%">							
					<select name="seach_CHECK_FLAG" id="seach_CHECK_FLAG">
							<option value=""  <c:if test="${CHECK_FLAG eq '' }">selected</c:if>>全部</option>
							<option value="0" <c:if test="${CHECK_FLAG eq '0' }">selected</c:if>>是</option>
							<option value="1" <c:if test="${CHECK_FLAG eq '1' }">selected</c:if>>否</option>
					</select>
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.search"/><!-- 检索 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="formBar">
		<ul>
			<%--	
			<li>
				<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage"
				 	href="/ess/infoApply/savePOtApplyImport?AFFIRM_FLAG=-1" 
					title="确定要保存吗?"><span>保存</span></a>
			</li>
			--%>
			<li>
				<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
					href="/is/insurancesystem/saveIsParamDataImport" title="确定要提交吗?"><span>提交</span>
				</a>
			</li>
			<li>
				<a class="buttonActive" target="ajaxTodo" href="/is/insurancesystem/deleteIsParamDataImport" title="确定要全部取消吗?"><span>取消</span></a>
			</li>
			
			<li>
				<a class="buttonActive" onclick="importIsParamData()">
					<span><spring:message code="ar.addempshift.title.excelimport" /><!-- EXCEL导入 --></span> 
				</a>
			</li>
			<li>
				<a class="buttonActive" id ="exportIsParamImportExcel" onclick="exportIsParamImportExcel();" href="#">
					<span><spring:message code="ar.addempshift.title.excelexport"/><%--导出Excel--%></span>
				</a>
			</li> 
			<%--<li>
				<a class="buttonActive" id ="exportIsParamDataExcel" onclick="exportIsParamDataModel();" href="#">
					<span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
				</a>
			</li>--%>
		</ul>
	</div>
		
	<table class="table" width="100%" layoutH="220" nowrapTD="false">
		<thead>
			<tr>
				<th width="15">
			    	<!--<input type="checkbox" class="checkboxCtrl" group="c1" />-->
			    	序号
			    </th>
			  	<th width="20"><!--法人-->
					法人
				</th>
				<th width="40"><!--大区编码-->
					大区编码
				</th>
				<th width="40"><!--福利地区-->
					福利地区
				</th>
				<th width="40"><!--福利项目-->
					福利项目
				</th>
				
				<th width="30"><!--地区比率-->
					地区比率
				</th>
				<th width="30"><!--地区金额-->
					地区金额
				</th>
				<th width="20"><!--启用状态-->
					启用状态
				</th>
				<th width="50"><!--备注-->
					备注
				</th>
				<th width="20" style="text-align: center"><!--正常与/否-->
					正常与/否
				</th>
				<th width="50" style="text-align: center"><!--错误原因-->
					错误原因
				</th>
				<th width="20"><!--操作-->
					操作
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${isParamImportList}" var="isData" varStatus="i">			
				<tr target="sid" rel="${isData.DATA_NO }">
					<td style="text-align:center">${i.index+1 }</td>
					<td style="text-align:center">${isData.CPNY_ID }</td>
					<td style="text-align:center">${isData.PAY_AREA_CD }</td>
					<td style="text-align:center">${isData.INSRAREA_ID }</td>
					<td style="text-align:center">${isData.INSURE_ID }</td>
					
					<td style="text-align:right">${isData.INSURE_RATE }</td>
					<td style="text-align:right">${isData.INSURE_VALUE }</td>
					<td style="text-align:center">
						<c:if test="${isData.ACTIVITY eq '1'}">
							<font color="green">启用</font>
						</c:if>
						<c:if test="${isData.ACTIVITY eq '0'}">
							<font color="blue">未启用</font>
						</c:if>
					</td>
					<td style="text-align:left">${isData.REMARK }</td>
					<td style="text-align: center">
						<c:if test="${isData.CHECK_FLAG eq '0'}" >
							<font color="green">正常</font>
						</c:if>
						<c:if test="${isData.CHECK_FLAG eq '1'}" >
						  	<font color="red">异常</font>
						</c:if>
					</td>	
					<td style="text-align: center">
						${isData.CHECK_ERROR }
					</td>						
					<td style="text-align: center">
						<img src="/resources/images/button/Delete_little.gif" onclick="delIsParamDataImport('${isData.DATA_NO}')" style="cursor: hand" />
					</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<c:set value="/is/insurancesystem/viewInsuranceImportDataChList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>