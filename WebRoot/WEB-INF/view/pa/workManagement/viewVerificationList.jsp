<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$("pa1013_Table").width($(document.body).width() - 70);
$(document).ready(function() {
	$("#pa1013_Table", navTab.getCurrentPanel()).dataTable( {
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
</script>
<div class="pageHeader">
	<form id="viewVerificationList" onsubmit="return navTabSearch(this);"
		action="/pa/workManagement/viewVerificationList" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="25%">
						<!--姓名/社号--><spring:message code="ess.infoApply.NAME_EMPID" />									
					</td>

					<td width="25%">
						<input type="text" class="text" name="seach_KEY">
					</td>


					<td>
						<!--工资支付计划--><spring:message code="ess.empInfo.pay_plan" />
					</td>


					<td>
						<select name="PAY_SCHEDULE_NO">
							<c:forEach items="${paPayScheduleList}" var="paySchedule"
								varStatus="i">
								<c:choose>
									<c:when test="${PAY_SCHEDULE_NO== paySchedule.PAY_SCHEDULE_NO}">
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
				</tr>
				<%-- <tr>
					<td>
						人事命令
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE"
							selected="${TRANS_CODE}" parentNo="14013956" limit="all"
							cnpyID="${defaultCpny}" />

					</td>
					<td>
						发令生成区分
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_TRANS_REASON"
							selected="${TRANS_REASON}" parentNo="14013966" limit="all"
							cnpyID="${defaultCpny}" />
					</td>
				</tr> --%>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" class="button">
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>
							</div>
						</div>
					</li>
				 

					<!-- <li>
						<a class="buttonActive"
							onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=11')"
							href="#"> <span>导出到Excel</span> </a>
					</li> -->
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent" id="pa1013_pageContent">

	<table class="orderList" id="pa1013_Table" width="99%">
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
					<!--部门--><spring:message code="ess.infoApply.DEPT" />
				</th>
				<th>
					<!--职群--><spring:message code="ess.empInfo.zhiqun" />
				</th>
				<th>
					<!--职级--><spring:message code="hrm.contract.Rank" />
				</th>
				<th>
					<!--职责--><spring:message code="pa.salary.canShu.zhiZe" />
				</th>
				<th>
					<!--员工类型--><spring:message code="org.title.EMP_TYPE" />
				</th>
				<th>
					<!--入职日期--><spring:message code="ess.empInfo.entry_date" />
				</th>
				<th>
					<!--离职日期--><spring:message code="ess.empInfo.leaveDate" />
				</th>
				<th>
					<!--发令日期--><spring:message code="hr.enpinfo.title.EMP.EXPDATE" />
				</th>
				<th>
					<!--发令类型--><spring:message code="hr.assignment.type" />
				</th>
			</tr>
		</thead>
		<c:forEach items="${viewVerificationList}" var="viewVerificationList"
			varStatus="i">
			<tr
				onclick="navTabNum('/pa/workManagement/detailPersonCountInfo?PERSON_ID=${viewVerificationList.PERSON_ID}&PAY_SCHEDULE_NO=${PAY_SCHEDULE_NO}','pageNum=1&menuNo=14013771&navTabId=pa1014','pa1014','<spring:message code="pa.viewPaMain.GONGZIXIANGXIMINGXI.C" />');">
				<td style="text-align: center">
					${i.count}
				</td>
				<td style="text-align: center">
					${viewVerificationList.LOCAL_NAME}
				</td>
				<td style="text-align: center">
					${viewVerificationList.EMPID}
				</td>
				<td style="text-align: center">
					${viewVerificationList.DEPT_NAME}
				</td>
				<td style="text-align: center">
					${viewVerificationList.POST_FAMILY_NAME}
				</td>
				<td style="text-align: center">
					${viewVerificationList.POST_GRADE_NAME}
				</td>
				<td style="text-align: center">
					${viewVerificationList.POSITION_NAME}
				</td>
				<td style="text-align: center">
					${viewVerificationList.EMP_TYPE_NAME }
				</td>
				<td style="text-align: center">
					${viewVerificationList.DATE_STARTED}
				</td>
				<td style="text-align: center">
					${viewVerificationList.DATE_LEFT}
				</td>
				<td style="text-align: center">
					${viewVerificationList.START_DATE}
				</td>
				<td style="text-align: center">
					${viewVerificationList.TRANS_CODE_NAME}
				</td>

			</tr>
		</c:forEach>



	</table>

</div>