<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
<!--
function delFundCalcObjectTemp(personId,pa_month){
		var params = [];
		params.push({
			name: 'PERSON_ID',
			value: personId
		});
		params.push({
			name: 'PA_MONTH',
			value: pa_month
		});
		//if (confirm ("确定要删除吗?")){	  
			$.ajax({
			  url: '/pa/insurance/delFundObjectImport',
			  data: params,
			  cache: false,
			  success: function(responseText){
				if (responseText == "Y"){
					//alert("删除成功！");
					//页面重载
					navTabSearch(document.viewFundCalcObjectImportList);
				}else{
					alert("删除失败！");
				}
			  }
			});
		//}
	}
//-->
</script>	

<a id="importExcelDialog_pa0421_temp"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_pa0421_temp"  href="#" target="navTab" mask="true">
	<span style="display:none;">导入公积金对象</span>
</a>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/insurance/viewFundCalcObjectImportList" method="post" rel="pagerForm" 
		id="viewFundCalcObjectImportList" name="viewFundCalcObjectImportList">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<th>
					<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->：
				</th>
				<td>
					${totalCount}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
				<th>
					<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->：
				</th>
				<td>							
					${errCnt}
				</td>
				<th>
				<spring:message code="pa.salary.canShu.chuCuoYuFou"/><!-- 出错与否：-->
				</th>
				<th> 
					<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
						<option value="" <c:if test="${RESULT_FLAG eq '' }">selected</c:if>>
							<spring:message code="pa.salary.canShu.quanBu"/><!-- 全部 -->
						</option>
						<option value="1" <c:if test="${RESULT_FLAG eq '1' }">selected</c:if>>
							<spring:message code="pa.salary.canShu.shi"/><!-- 是 -->
						</option>
						<option value="0" <c:if test="${RESULT_FLAG eq '0' }">selected</c:if>>
							<spring:message code="pa.salary.canShu.fou"/><!--否 -->
						</option>
					</select>
				</th>
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
	<div style="float:right;">
		<a class="buttonActive" id ="exportExcel" title="导出导入的数据" href="/pa/insurance/expFundObjectImportDataExcel">
			<SPAN><!-- EXCEL导出 -->
				<spring:message code="ar.addempshift.title.excelexport"/>
			</SPAN>
		</a>
	</div>
	
	<div style="float:right; ">
		<a class="buttonActive" href="#" onclick="importFundCalcObjectData();" >
			<SPAN><!-- EXCEL导入 -->
				<spring:message code="ar.addempshift.title.excelimport"/>
			</SPAN>
	 	</a>
	</div>
	
	<div style="float:right; ">
		<a class="buttonActive" target="ajaxTodo" href="/pa/insurance/cancelFundObjectImport" title="确定要全部取消吗?">
			<span>取消</span>
		</a>
	</div>
	
	<div style="float:right; ">
		<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage"
			href="/pa/insurance/saveFundObjectDataImport"  title="确定要提交吗?">
			<span><!--提交-->
				<spring:message code="public.title.submit"/>
			</span>
		</a>
	</div>
			
	<table class="table" width="100%" layoutH="198">
		<thead>
			<tr>
				<th width="8%">工资月</th>
				<th width="8%">社号</th>
				<th width="8%">姓名</th>
				<th width="22%">部门</th>
				<th width="8%">计算标志</th>
				<th width="12%">备注</th>
				<th width="8%">正/异常</th>
				<th width="20%">错误提示</th>
				<th width="5%">删除</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${insObjTempList}" var="insObj" varStatus="i">
				<tr>
					<td style="text-align:center">${insObj.PA_MONTH}</td>
					<td style="text-align:center">${insObj.PERSON_ID}</td>
					<td style="text-align:center">${insObj.LOCAL_NAME}</td>
					<td>${insObj.DEPT_NAME}</td>
					<td style="text-align:center">${insObj.CALC_FLAG}</td>
					<td>${insObj.REMARK}</td>
					<td style="text-align:center">
						<center>
							<c:if test="${insObj.CHECK_FLAG eq '0'}" >
								<font color="green">正常</font>
							</c:if>
							<c:if test="${insObj.CHECK_FLAG eq '1'}" >
							  	<font color="red">异常</font>
							</c:if>
						</center>
					</td>
					<td>${insObj.CHECK_ERROR}</td>
					<td style="text-align:center">
						<img src="/resources/images/button/Delete_little.gif" onclick="delFundCalcObjectTemp('${insObj.PERSON_ID}','${insObj.PA_MONTH}')"
					 		style="cursor: hand" />
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/insurance/viewFundCalcObjectImportList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>