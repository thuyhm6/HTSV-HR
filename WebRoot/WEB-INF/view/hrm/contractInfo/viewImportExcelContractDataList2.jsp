<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form id="viewImportExcelContractDataList" name="viewImportExcelContractDataList"
			action="/hrm/contractInfo/viewImportExcelContractDataList" 
			onsubmit="return navTabSearch(this);"
			method="post" 
			rel="pagerForm" >
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<th>
					<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->：
				</th>
				<td>
					${totalCnt}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
				<th>
					<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->：
				</th>
				<td>							
					${errCnt}
				</td>
				<th>
					<!-- 出错与否： --><spring:message code="org.title.IS_ERROR" />
				</th>
				<td>							
					<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value="" <c:if test="${RESULT_FLAG eq '' }">selected</c:if>> <!-- 全部 --><spring:message code="org.title.ALL" /> </option>
							<option value="E" <c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>> <!-- 是 --><spring:message code="org.title.YES" /></option>
							<option value="N" <c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>> <!-- 否   --> <spring:message code="ar.viewcycle.content.no" /> </option>
					</select>
				</td>
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
			<li><a class="buttonActive" onclick="importExcelContract_hr0301()"> <span><spring:message
			code="ar.addempshift.title.excelimport" /><!-- EXCEL导入 --></span> </a></li>
			<li><a class="buttonActive"
			href="/hrm/contractInfo/downloadExcelTempTemplate"><span>
				<spring:message code="inct.salesman.downloadToExcel" /><!--excel导出--></span></a>
			</li>
			<li><a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
			href="/hrm/contractInfo/submitImportExcelContractData2"  title="确定要提交吗?"><span>
				<spring:message code="public.title.submit"/><!--提交--></span></a>
			</li>
		</ul>
	</div>
		
	<table class="table" width="100%" layoutH="220" nowrapTD="false">
		<thead>
			<tr>
				<th>Line<!--excel行号--></th>
				<th> <!-- 社号 --> <spring:message code="hrm.empinfo.empid" /></th>
				<th><!-- 姓名  --> <spring:message code="hrm.empinfo.name" /></th>
				<th><!-- 部门 --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /></th>
				<th><!-- 工作地  --><spring:message code="org.title.WORD_AREA_NAME" /></th>
				<th><!-- 08年后合同次数 --><spring:message code="hrm.contractInfo.EIGHT_AFTER_CONTRACT_COUNT" /></th>
				<th><!-- 人员类型 --><spring:message code="hrm.contract.PERSONNEL_TYPE" /></th>
				<th><!-- 合同编号 --><spring:message code="hrm.contractInfo.CONTRACT_ID" /></th>
				<th><!-- 合同类型 --><spring:message code="hrm.contract.CONTRACT_TYPE" /></th>
				<th><!-- 合同版本 --><spring:message code="hrm.contract.Contract_version"/></th>
				<th><!-- 开始日期 --><spring:message code="hrm.recruitManage.START_DATE1" />   </th>
				<th><!-- 结束日期  --><spring:message code="hrm.recruitManage.END_DATE1"/></th>
				<th><!-- 备注 --><spring:message code="org.title.REMARK" />   </th>
				<th><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${contractTempList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.LINE_ID}</td>
					<td style="text-align:left">${item.EMPID}</td>
					<td style="text-align:left">${item.LOCAL_NAME}</td>
					<td style="text-align:left">${item.DEPT_NAME}</td>
					<td style="text-align:left">${item.WORK_AREA}</td>
					<td style="text-align:left">${item.TOTAL_PERIOD_08}</td>
					<td style="text-align:left">${item.EMP_TYPE_NAME}</td>
					<td style="text-align:left">${item.CONTRACT_NUMBER}</td>
					<td class='td_center'>${item.CONTRACT_TYPE}</td>
					<td class='td_center'>${item.CONTRACT_VERSION}</td>
					<td class='td_center'>${item.START_CONTRACT_DATE}</td>
					<td class='td_center'>${item.END_CONTRACT_DATE}</td>
					<td style="text-align:left">${item.REMARK}</td>
					<td style="text-align:left">${item.UPLOAD_ERROR_MSG}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
		
	<c:set value="/hrm/contractInfo/viewImportExcelContractDataList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>