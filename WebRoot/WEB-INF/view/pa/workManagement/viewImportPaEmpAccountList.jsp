<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form id="viewPaEmpAccountDataExcelImportResult" name="viewPaEmpAccountDataExcelImportResult"
			action="/pa/workManagement/viewImportPaEmpAccountList?" 
			onsubmit="return navTabSearch(this);"
			method="post" >
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->
				</td>
				<td>
					${totalCnt}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
				<td>
					<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->
				</td>
				<td>							
					${errCnt}
				</td>
				<td>
					<spring:message code="pa.salary.canShu.chuCuoYuFou"/><!-- 出错与否-->
				</td>
				<td>							
					<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value="" <c:if test="${RESULT_FLAG eq '' }">selected</c:if>><!--全部--><spring:message code="org.title.ALL"/></option>
							<option value="E" <c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>><!--是--><spring:message code="org.title.YES"/></option>
							<option value="N" <c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>><!--否--><spring:message code="ar.viewcycle.content.no"/></option>
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
			<li>
				<a class="buttonActive" onclick="excelimport_pa0818();">
					<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a></li>
			<li>
				<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
					href="/pa/workManagement/submitImportExcelPaEmpAccountData" title="<spring:message code="org.title.IS_SUBMIT"/>"><span>
				<spring:message code="public.title.submit"/><!--提交--></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="220" nowrapTD="false">
		<thead>
			<tr>
				<th>Line<!--excel行号--></th>
				<th><!--姓名--><spring:message code="ess.infoApply.NAME" /></th>
				<th><!--工号--><spring:message code="ess.infoApply.EMP_ID" /></th>
				<th><!--部门--><spring:message code="ess.infoApply.DEPT"/></th>
				<th><!--银行类型--><spring:message code="pa.viewPaEmpAccount.YINHANGLEIXING.C" /></th>
				<th><!--银行账号--><spring:message code="rp.report.title.bankcardno" /></th>
				<th><!--开户行--><spring:message code="pa.wagebase.title.openAccountBanks" /></th>
				<!--<th>开户地址</th>-->
				<th><!--保险号码--><spring:message code="pa.viewPaEmpAccount.BAOXIANHAOMA.b" /></th>
				<th><!--税号--><spring:message code="pa.viewPaEmpAccount.SHUIHAO.b" /></th>
				<!--<th>公积金账号</th>-->
				<!--<th>社保缴纳时间</th>-->
				<!--<th>公积金缴纳时间</th>-->
				<th><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th><!--上传人--><spring:message code="org.title.UPLOAD_BY"/></th>
				<th><!--上传时间--><spring:message code="org.title.UPLOAD_DATE"/></th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paEmpAccountTempList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.LINE_ID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.DEPT_NAME}</td>
					<td class='td_center'>${item.ACCOUNT_TYPE}</td>
					<td class='td_center'>${item.ACCOUNT_NO}</td>
					<td class='td_center'>${item.ACCOUNT_NAME}</td>
					<!--<td class='td_center'>${item.ACCOUNT_ADDRESS}</td>-->
					<td class='td_center'>${item.SECURITY_NO}</td>
					<td class='td_center'>${item.TAX_NO}</td>
					<!--<td class='td_center'>${item.FUND_NO}</td>-->
					<!--<td class='td_center'>${item.SECURITY_PAY_DATE}</td>-->
					<!--<td class='td_center'>${item.FUND_PAY_DATE}</td>-->
					<td class='td_center'>${item.UPLOAD_ERROR_MSG}</td>
					<td class='td_center'>${item.UPLOAD_BY}</td>
					<td class='td_center'>${item.UPLOAD_DATE}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
</div>