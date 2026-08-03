<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function reportExcel(a) {
		var $this = $(a);
		var title = $this.attr("title");
		var $form = $("#viewPaAccount");

		var url = "/pa/wagebase/viewPaAccountTranserExcel";
		alertMsg.confirm(title, {
			okCall : function() {
				window.location = url + (url.indexOf('?') == -1 ? "?" : "&")
						+ $form.serialize();
			}
		});
	}

	//excel导出
	function reportExcel() {
		var sform = document.getElementById("viewPaAccount");
		alertMsg
				.confirm(
						"确定要导出数据吗?",
						{
							okCall : function() {
								//用于excel导出的表单参数处理
								var eForm = document
										.getElementById("excelExportForm_history");
								document.getElementById("allow0102").innerHTML = "EXCEL密码设置";
								eForm.EMP_OFFICE.value = sform.seach_EMP_OFFICE.value;
								eForm.DEPTNO.value = sform.seach_DEPTNO.value;
								eForm.YEAR.value = sform.seach_YEAR.value;
								eForm.MONTH.value = sform.seach_MONTH.value;
								eForm.KEY.value = sform.seach_KEY.value;
								eForm.EXCEPTION_PA_COUNT.value = sform.seach_EXCEPTION_PA_COUNT.value;
								eForm.JobTypeGroupNo.value = sform.seach_JobTypeGroupNo.value;
								eForm.EmpTypeCodeNo.value = sform.seach_EmpTypeCodeNo.value;

								$("#importExcelDialog_allow0102")
										.attr(
												'href',
												"/sys/encryptExcel"
														+ "?exportFunName=/pa/wagebase/viewPaAccountHistoryExcel"
														+ "&navTabId=pa0909"
														+ "&formId=excelExportForm_history");
								$("#importExcelDialog_allow0102").attr('width',
										"300");
								$("#importExcelDialog_allow0102").attr(
										'height', "150");
								$("#importExcelDialog_allow0102").click();
							}
						});
	}

	function doExceptionChoseCheck() {
		if (!document.getElementById('do_EXCEPTION_PA_COUNT').checked) {
			document.getElementById('seach_EXCEPTION_PA_COUNT').value = 'NO';
		} else if (document.getElementById('do_EXCEPTION_PA_COUNT').checked) {
			document.getElementById('seach_EXCEPTION_PA_COUNT').value = 'YES';
		}
	}

	$(document).ready(
			function() {
				if ($("#pa0502_seach_JobTypeGroupNo_history").val() != '') {
					var EMP_TYPE = $("#pa0502_seach_EmpTypeCodeNo_history")
							.val();
					ajaxEmpTypeForGroupToList(EMP_TYPE,
							"pa0502_seach_JobTypeGroupNo_history",
							"pa0502_seach_EmpTypeCodeNo_history",
							"pa0502_seach_CPNY", "pa0502_limit");
					//要传进的参数分别为 -1，人员类型组select 对象，人员类型select name，法人选项id，要查询的是否为group，权限super/hr/ar/pa
				}
			});
</script>
<a id="importExcelDialog_allow0102" href="#" target="dialog" mask="true">
	<span id="allow0102" style="display: none"></span>
</a>
<div class="pageHeader">
	<form id="viewPaAccount" name="viewPaAccount"
		onsubmit="return navTabSearch(this);"
		action="/pa/wagebase/viewPaAccountHistoryList" method="post"
		rel="pagerForm">
		<input type="hidden" name="firstFlag" value="1">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="public.title.empId" />
						<!--工号-->/ <spring:message code="public.title.name" />
						<!--姓名--></td>
					<td>
						<input type="text" name="seach_KEY" value="${KEY}" />
						<input type="hidden" id="seach_FIRST_FLAG" name="seach_FIRST_FLAG" value="1" />
					</td>
					<td><spring:message code="public.title.deptName" />
						<!--部门--></td>
					<td><ait:deptList name="seach_DEPTNO"
							cpnyId="${LoginUser.cpnyId}" limit="pa"
							id="viewPaAccount_seachDept" /> <ait:deptTreeIcon
							name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="pa"
							id="viewPaAccount_seachDept" selected="${DEPTNO}" /></td>
					<td><spring:message
							code="hr.viewPersonalInfo.title.STATUS_NAME" />
						<!--员工状态--></td>
					<td><ait:SelectSyCodeByCpnyID id="seach_EMP_OFFICE"
							name="seach_EMP_OFFICE" parentNo="15118" selected="${EMP_OFFICE}"
							cnpyID="${LoginUser.cpnyId}" limit="all" /></td>
					<td><input type="checkbox" id="do_EXCEPTION_PA_COUNT"
						name="do_EXCEPTION_PA_COUNT"
						<c:if test="${EXCEPTION_PA_COUNT ne 'NO'}">checked="checked"</c:if>
						onclick="doExceptionChoseCheck();" /> <input
						id="seach_EXCEPTION_PA_COUNT" name="seach_EXCEPTION_PA_COUNT"
						type="hidden" value="${EXCEPTION_PA_COUNT }">
						&nbsp;&nbsp;异常人员(注:姓名不一致、账号为空、银行为空等)</td>
				</tr>
				<tr>
					<td>人员类型组</td>
					<td><input type="hidden" id="pa0502_limit" name="limit"
						value="pa"> <input type="hidden" id="pa0502_seach_CPNY"
						name="seach_CPNYFYSQ" value="${defaultCpny}"> <ait:SelectEmpTypeCode
							id="pa0502_seach_JobTypeGroupNo_history"
							name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}"
							limit="pa" type="group"
							onChangeName="ajaxEmpTypeForGroupToList(-1,pa0502_seach_JobTypeGroupNo_history,pa0502_seach_EmpTypeCodeNo_history,pa0502_seach_CPNY,pa0502_limit)" />
					</td>



					<td><spring:message code="pa.insurance.title.salaryMonth" />
						<!--工资月--></td>
					<td><ait:date yearName="seach_YEAR" yearSelected="${YEAR}"
							monthName="seach_MONTH" monthSelected="${MONTH}" /></td>

					<td>人员类型</td>
					<td><ait:SelectEmpTypeCode
							id="pa0502_seach_EmpTypeCodeNo_history"
							name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="pa" />
					</td>
				 </tr>
				 <tr>
					<td>
						计算状态
						</td>
						<td>
							<select name="seach_CALC_FLAG">
							<option value="">全部</option>
							<option value="Y" <c:if test="${CALC_FLAG == 'Y'}">selected</c:if>>Y</option>
							<option value="N" <c:if test="${CALC_FLAG == 'N'}">selected</c:if>>N</option>
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
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
		</form>
</div>

<div class="pageContent">
	<c:set value="javascript:reportExcel();" var="excelD_Url" />
	<%@ include file="/WEB-INF/view/inc/includeExcelButton.jsp"%>
</div>
<table class="table" width="100%" layoutH="246">
	<thead>
		<tr>
			<th width="4%" orderField="PA_MONTH" class="${orderDirection}">工资月
			</th>
			<th width="6%" orderField="EMPID" class="${orderDirection}"><spring:message
					code="public.title.empId" />
				<!--工号--></th>
			<th width="6%"
				orderField="nlssort(CHINESE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')"
				class="${orderDirection}"><spring:message
					code="pa.title.message.empHrmName" />
				<!--人事姓名--></th>
			<th width="6%"
				orderField="nlssort(CARD_NAME,'NLS_SORT=SCHINESE_PINYIN_M')"
				class="${orderDirection}"><spring:message
					code="pa.title.message.empPaName" />
				<!--账号名--></th>
			<th width="8%"
				orderField="nlssort(DEPTNAME,'NLS_SORT=SCHINESE_PINYIN_M')"
				class="${orderDirection}"><spring:message
					code="public.title.deptName" />
				<!--部门--></th>
			<th width="6%"
				orderField="nlssort(JOIN_COMPANY_DATE,'NLS_SORT=SCHINESE_PINYIN_M')"
				class="${orderDirection}"><spring:message
					code="pa.insurance.title.entryCpmpanyDate" />
				<!--入司日期--></th>
			<th width="6%"
				orderField="nlssort(DATE_LEFT,'NLS_SORT=SCHINESE_PINYIN_M')"
				class="${orderDirection}"><spring:message
					code="pa.insurance.title.resignDate" />
				<!--离职日期--></th>
			<!-- 				<th width="8%" orderField="nlssort(BANK_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}"> -->
			<!-- 					<spring:message code="pa.wagebase.title.openAccountBanks"/>开户行 -->
			<!-- 				</th> -->
			<th width="8%"
				orderField="nlssort(BANK_BRANCH_NAME,'NLS_SORT=SCHINESE_PINYIN_M')"
				class="${orderDirection}"><spring:message
					code="pa.title.message.bankBranchNameInfo" />
				<%--银行支行名称--%></th>
			<th width="12%" orderField="CARD_NO" class="${orderDirection}">
				<spring:message code="pa.wagebase.title.accountNo" />
				<!--账号--></th>

			<th width="10%" orderField="CALC_FLAG" class="${orderDirection}">
				<!--计算标识--> <spring:message code="pa.wagebase.title.caculateFlag" />
			</th>
			<th width="10%" orderField="UPDATE_NAME" class="${orderDirection}">
				<!--计算标识--> 修改人</th>
			<th width="10%" orderField="UPDATE_REMARK" class="${orderDirection}">
				<!--计算标识--> 修改原因</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${itemList}" var="item" varStatus="i">
			<tr target="sid" rel="${item.PERSON_ID}">
				<td style="text-align:center;">${item.PA_MONTH}</td>
				<td style="text-align:center;">${item.EMPID}</td>
				<td style="text-align:center;">${item.CHINESE_NAME}</td>
				<td style="text-align:center;">${item.CARD_NAME}</td>
				<td style="text-align:center;">${item.DEPTNAME}</td>
				<td style="text-align:center;">${item.JOIN_COMPANY_DATE}</td>
				<td style="text-align:center;">${item.DATE_LEFT}</td>
				<!-- 					<td style="text-align:center;">${item.BANK_NAME}</td> -->
				<td style="text-align:center;">${item.BANK_BRANCH_NAME}</td>
				<td style="text-align:center;">${item.CARD_NO}</td>
				<td style="text-align:center;">${item.CALC_FLAG}</td>
				<td style="text-align:center;">${item.UPDATE_NAME}</td>
				<td style="text-align:center;">${item.UPDATE_REMARK}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/pa/wagebase/viewPaAccountHistoryList" var="pageUrl" />
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
<form id="excelExportForm_history" name="excelExportForm_history"
	method="post">
	<input type="hidden" id="password" name="password" value="" /> <input
		type="hidden" id="EMP_OFFICE" name="EMP_OFFICE" value="" /> <input
		type="hidden" id="DEPTNO" name="DEPTNO" value="" /> <input
		type="hidden" id="KEY" name="KEY" value="" /> <input type="hidden"
		id="JobTypeGroupNo" name="JobTypeGroupNo" value="" /> <input
		type="hidden" id="YEAR" name="YEAR" value="" /> <input type="hidden"
		id="MONTH" name="MONTH" value="" /> <input type="hidden"
		id="EmpTypeCodeNo" name="EmpTypeCodeNo" value="" /> <input
		type="hidden" id="EXCEPTION_PA_COUNT" name="EXCEPTION_PA_COUNT"
		value="" />
		</form>

