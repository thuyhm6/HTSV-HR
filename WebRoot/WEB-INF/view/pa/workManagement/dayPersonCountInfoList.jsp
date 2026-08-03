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

function searchPopPaEmpAccount(flag) {
	var name = encodeURI(encodeURI($('#seach_KEY', navTab.getCurrentPanel())
			.val()));
	var refreshUrl = '/pa/workManagement/viewPaEmpAccount?noParam=Y';
	var refreshMenuCode = 'pa0818';
	var refreshMenuName = encodeURI(encodeURI('<spring:message code="hr.viewCondSql.title.ZHANGHUXINXI" />'));//账户信息
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

function downloadExl(url) {
	$('#dayPersonCountInfoList').attr("action", url);
	$('#dayPersonCountInfoList').attr("onsubmit", '');
	$('#dayPersonCountInfoList').submit();
	$('#dayPersonCountInfoList').attr("action",
			'/pa/workManagement/dayPersonCountInfoList');
	$('#dayPersonCountInfoList').attr("onsubmit", 'return navTabSearch(this);');
}
</script>
<script>
$(document).ready(function() {

	$("#pa1015_pageContent").css("height", $(document.body).height() - 180);
	$("#pa1015_table").css("width", $(document.body).width() - 50);
	$("#pa1015_table", navTab.getCurrentPanel()).dataTable( {
		"bPaginate" : false, //关闭分页
		"bAutoWidth" : false,//表格宽度不自动变化
		"bProcessing" : true,
		"bLengthChange" : false, //关闭按多少条记录显示下拉框
		"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort" : true, //关闭排序功能
		"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite" : true,
		"scrollY" : $(document.body).height() - 250,
		"scrollX" : true,
		"orderClasses" : false,
		"oLanguage" : {
			//正在加载中......
	   	    "sProcessing": "<spring:message code='ess.message.loading' />",
	        //查询不到相关数据！
	        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
	        //表中无数据存在！
	        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
	        //快速筛选
	        "sSearch": "<spring:message code='ess.message.rapid_screening' />"
		}

	//多语言配置
			});
});
</script>
<div class="pageHeader">
	<form id="dayPersonCountInfoList" onsubmit="return navTabSearch(this);"
		action="/pa/workManagement/dayPersonCountInfoList" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!--工资支付计划--><spring:message code="ess.empInfo.pay_plan" />
					</td>


					<td>
						<select name="PAY_SCHEDULE_NO">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
								<c:choose>
									<c:when
										test="${PAY_SCHEDULE_NO == paySchedule.PAY_SCHEDULE_NO }">
										<option value="${paySchedule.PAY_SCHEDULE_NO}"
											selected="selected">
											${paySchedule.SALARY_DISTIN }-${paySchedule.PAY_DATE }
										</option>
									</c:when>
									<c:otherwise>
										<option value="${paySchedule.PAY_SCHEDULE_NO}">
											${paySchedule.SALARY_DISTIN }-${paySchedule.PAY_DATE }
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td>
						<!--日割与否--><spring:message code="pa.dayPersonCountInfoList.RIGEYUFOU.b" />
					</td>
					<td>
						<select name="IS_CAL_BY_DAY">
							<option value="Y"
								<c:if test="${IS_CAL_BY_DAY eq 'Y'}"> selected="selected"</c:if>>
								<!--是--><spring:message code="ess.infoApply.yes" />
							</option>
							<option value="N"
								<c:if test="${IS_CAL_BY_DAY eq 'N'}"> selected="selected"</c:if>>
								<!--否--><spring:message code="ess.infoApply.no" />
							</option>

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

					<c:if test="${LoginUser.cpnyId eq 'TSTO'}">
						<li>
							<a class="buttonActive"
								onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=201')"
								href="#"> <span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel" /></span> </a>
						</li>
					</c:if>

					<c:if test="${LoginUser.cpnyId eq 'SST'}">
						<li>
							<a class="buttonActive"
								onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=202')"
								href="#"> <span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel" /></span> </a>
						</li>
					</c:if>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent" id='pa1015_pageContent'>
	Total:${fn:length(dayPersonCountInfoList)}
	<table class="orderList" id='pa1015_table'>
		<thead>
			<tr>
				<th>
					No
				</th>
				<th>
					<!--姓名--><spring:message code="ess.infoApply.NAME" />
				</th>
				<th>
					<!--工号--><spring:message code="ess.infoApply.EMP_ID" />
				</th>
				<th>
					<!--部门名--><spring:message code="ess.infoApply.DEPT_NAME" />
				</th>
				<th>
					<!--职级--><spring:message code="hrm.contract.Rank" />
				</th>
				<th>
					<!--日割与否--><spring:message code="pa.dayPersonCountInfoList.RIGEYUFOU.b" />
				</th>
				<th>
					<!--项目名--><spring:message code="pa.dayPersonCountInfoList.XIANGMUMING.b" />
				</th>
				<th>
					<!--金额--><spring:message code="ess.empInfo.amount_of_money" />
				</th>
				<th>
					<!--公式--><spring:message code="pa.insurance.title.formula" />
				</th>
			</tr>
		</thead>
		<c:forEach items="${dayPersonCountInfoList}"
			var="dayPersonCountInfoList" varStatus="i">
			<tr>
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
					${dayPersonCountInfoList.POST_GRADE}
				</td>
				<td style="text-align: center">
					${dayPersonCountInfoList.IS_CAL_BY_DAY}
				</td>
				<td style="text-align: center">
					${dayPersonCountInfoList.ITEM_NAME}
				</td>
				<td style="text-align: center">
					${dayPersonCountInfoList.CAL_VALUE}
				</td>
				<td style="text-align: center">
					${dayPersonCountInfoList.FORMULAR_VALUE}
				</td>
			</tr>
		</c:forEach>



	</table>

</div>