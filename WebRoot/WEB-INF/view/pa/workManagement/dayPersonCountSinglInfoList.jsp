<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function changeSearcha(obj) {

	var currentIndex = '0';

	var PERSON_ID = obj.name;
	var href1 = '/pa/workManagement/monthPersonCountInfoSonList?currentIndex='
			+ currentIndex + '&PERSON_ID_ID=' + PERSON_ID;
	obj.href = href1;
	obj.click;

}

function downloadExl(url) {
	$('#searchViewPaEmpAccountForm').attr("action", url);
	$('#searchViewPaEmpAccountForm').attr("onsubmit", '');
	$('#searchViewPaEmpAccountForm').submit();
	$('#searchViewPaEmpAccountForm').attr("action",
			'/pa/workManagement/viewPaEmpAccount');
	$('#searchViewPaEmpAccountForm').attr("onsubmit",
			'return navTabSearch(this);');
}
function searchPopPaEmpAccount(flag) {
	var name = encodeURI(encodeURI($('#seach_KEY', navTab.getCurrentPanel())
			.val()));
	var refreshUrl = '/pa/workManagement/viewPaEmpAccount?noParam=Y';
	var refreshMenuCode = 'pa0818';
	var refreshMenuName = encodeURI(encodeURI('账户信息'));
	$('#searchPopPaEmpAccountId', navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=pa&seach_KEY='
							+ name
							+ '&refreshUrl='
							+ refreshUrl
							+ '&refreshMenuCode='
							+ refreshMenuCode
							+ '&refreshMenuName=' + refreshMenuName);
	if (flag == 'onkeyup')
		$("#searchPopPaEmpAccountId", navTab.getCurrentPanel()).click();
}
function addPaEmpAccountInfo(url) {
	$.pdialog.open(url, "pa0818_add", "账户添加", {
		width : 1000,
		height : 600,
		mask : true
	});
}
</script>
<div class="pageHeader">
	<form id="dayPersonCountInfoList" onsubmit="return navTabSearch(this);"
		action="/pa/workManagement/dayPersonCountInfoList" method="post">
		<div class="searchBar">
			<table class="searchContent">
			 
				<tr>
					<td>
						姓名/社号
					</td>
					<td>
						<input type="text" class="text" name="seach_KEY">
					</td>
				</tr>
				<tr>
					<td>
						工资支付计划
					</td>


					<td>
						<select id="PAY_DATE" name="PAY_DATE">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
								<c:choose>
									<c:when
										test="${PAY_SCHEDULE_NO == paySchedule.PAY_SCHEDULE_NO }">
										<option value="${paySchedule.PAY_DATE }" selected="selected">
											${paySchedule.PAY_DATE }
										</option>
									</c:when>
									<c:otherwise>
										<option value="${paySchedule.PAY_DATE}">
											${paySchedule.SALARY_DISTIN }-${paySchedule.PAY_DATE }
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
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
						</div>
					</li>
					<li>
						<a class="buttonActive"
							onclick="addPaEmpAccountInfo('/pa/workManagement/addPaEmpAccount')"
							href="#"> <span>印刷</span> </a>
					</li>

					<li>
						<a class="buttonActive"
							onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=11')"
							href="#"> <span>导出到Excel</span> </a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">


	<table class="table" width="1400" layoutH="171">

		<thead>


			<tr>
				<th>
					No
				</th>
				<th>
					姓名
				</th>
				<th>
					工号
				</th>
				<th>
					部门名
				</th>
				<th>
					职级
				</th>

			</tr>
		</thead>
		<c:forEach items="${dayPersonCountInfoList}" var="dayPersonCountInfoList"
			varStatus="i">
			<tr
				onclick="openOnRight('/pa/workManagement/dayPersonCountSinglInfoList?PERSON_ID='${dayPersonCountInfoList.LOCAL_NAME},'dialog');">
				<td style="text-align: center">
					${i.count}
				</td>
				<td style="text-align: center">
					${dayPersonCountInfoList.LOCAL_NAME}
				</td>
				<td style="text-align: center">
					${dayPersonCountInfoList.EMPID}
				</td>
				<td style="text-align: center">
					${dayPersonCountInfoList.DEPT_NAME}
				</td>
				<td style="text-align: center">
					${dayPersonCountInfoList.POST_GRADE_NAME}
				</td>
			</tr>
		</c:forEach>



	</table>

</div>