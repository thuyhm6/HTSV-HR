<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

$(document).ready(function() {
	$("#viewResultConfirmList3Rigth", navTab.getCurrentPanel()).dataTable( {
		"bPaginate" : false, //关闭分页
		"bAutoWidth" : false,//表格宽度不自动变化
		"bProcessing" : true,
		"bLengthChange" : false, //关闭按多少条记录显示下拉框
		"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"fixedColumns" : {
			leftColumns : 4
		},//锁表头
		"bSort" : true, //关闭排序功能
		"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite" : true,
		"scrollY" : $(document.body).height() - 320,
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
<c:if test="${currentIndex ne '2'}">
Total:${fn:length(viewResultConfirmList3Right)}
<div class="pageContent">
		<table id="viewResultConfirmList3Rigth" class="orderList"
			width="1200px">
			<thead>
				<tr>
					<th>
						No.
					</th>
					<th>
						<!--工号--><spring:message code="ess.infoApply.EMP_ID" />
					</th>
					<th>
						<!--姓名--><spring:message code="ess.infoApply.NAME" />
					</th>
					<th>
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</th>
					<th>
						<!--职级--><spring:message code="ess.infoApply.Rank" />
					</th>
					<th>
						<!--职责--><spring:message code="pa.salary.canShu.zhiZe" />
					</th>
					<th>
						<!--项目--><spring:message code="ess.empInfo.project" />
					</th>
					<th>
						<!--以前金额--><spring:message code="pa.detailItemDifCountInfo.YIQIANJINE.b" />
					</th>
					<th>
						<!--当月金额--><spring:message code="pa.detailItemDifCountInfo.DANGYUJINE.b" />
					</th>
					<th>
						<!--差异--><spring:message code="ess.infoApply.difference" />
					</th>
					<th>
						<!--适用式--><spring:message code="pa.detailPersonCountInfoRight.SHIYONGSHI.b" />
					</th>
					<th>
						<!--入职日期--><spring:message code="ess.empInfo.entry_date" />
					</th>
					<th>
						<!--试用终止日--><spring:message code="hrm.empinfo.START_PROBATION_DATE" />
					</th>
					<th>
						<!--离职日期--><spring:message code="ess.empInfo.leaveDate" />
					</th>


				</tr>
			</thead>


			<c:forEach items="${viewResultConfirmList3Right}" var="item"
				varStatus="i">

				<tr>
					<td class="td_type">
						${i.count}
					</td>

					<td class="td_type">
						${item.EMPID}
					</td>
					<td class="td_type">
						${item.LOCAL_NAME}
					</td>
					<td class="td_type">
						${item.DEPT_NAME}
					</td>
					<td class="td_type">
						${item.POST_GRADE_NAME}
					</td>

					<td class="td_type">
						${item.POSITION_NAME}
					</td>


					<td class="td_type">
						${item.ITEM_NAME}
					</td>
					<td class="td_type">
						${item.MONTH_PRO}
					</td>
					<td class="td_type">
						${item.MONTH_NOW}
					</td>
					<td class="td_type">
						<fmt:formatNumber value="${item.MONTH_NOW-item.MONTH_PRO}"
							pattern="#,##0" />
					</td>
					<td class="td_type">
					     
						<span title="${item.FORMULAR_VALUE}"> ${fn:substring(item.FORMULAR_VALUE,0,20)} 
						
						<c:if test="${fn:length(item.FORMULAR_VALUE)>20}" >
						...
						</c:if>
						</span>
					</td>
					<td class="td_type">
						${item.DATE_STARTED}
					</td>
					<td class="td_type">
						${item.END_PROBATION_DATE}
					</td>
					<td class="td_type">
						${item.DATE_LEFT}
					</td>

				</tr>



			</c:forEach>
		</table>

	</div>
</c:if>

<c:if test="${currentIndex eq '2'}">
	<script>

function downloadExl(url) {
	$('#viewResultConfirmList3Right_FROM').attr("action", url);
	$('#viewResultConfirmList3Right_FROM').attr("onsubmit", '');
	$('#viewResultConfirmList3Right_FROM').submit();
}
</script>
	<div class="pageHeader">
		<form id="viewResultConfirmList3Right_FROM"
			onsubmit="return navTabSearch(this);" action="" method="post">
			<input type="hidden" name="ITEM_ID" value="${ITEM_ID}">
			<input type="hidden" name="PAGE_TYPE" value="${PAGE_TYPE}">
			<input type="hidden" name="ITEM_TYPE" value="${ITEM_TYPE}">
			<input type="hidden" name="SELECT_TYPE" value="${SELECT_TYPE}">
			<div class="searchBar">

				<div class="subBar">
					<ul>

						<li>
							<a class="buttonActive"
								onclick="downloadExl('/pa/workManagement/viewResultConfirmList3RightExport')"
								href="#"> <span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel" /></span> </a>
						</li>


					</ul>
				</div>
			</div>
		</form>
	</div>



Total:${fn:length(viewResultConfirmList3Right)}


<div class="pageContent">
		<table class="table" width="100%" layoutH="100">
			<thead>
				<tr>
					<th>
						No.
					</th>
					<th>
						<!--工号--><spring:message code="ess.infoApply.EMP_ID" />
					</th>
					<th>
						<!--姓名--><spring:message code="ess.infoApply.NAME" />
					</th>
					<th>
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</th>
                    <th>
						<!--职群--><spring:message code="ess.empInfo.zhiqun" />
					</th>
					<th>
						<!--职级--><spring:message code="ess.infoApply.Rank" />
					</th>
                    <th>
						<!--职责--><spring:message code="sys.affirm.title.duty" />
					</th>
					<!--<th>
						主要业务<spring:message code="org.title.MAIN_BUSINESS" />
					</th>-->

					<th>
						<!--员工类型--><spring:message code="org.title.EMP_TYPE" />
					</th>

					<!--<th>
						项目名称<spring:message code="pa.insurance.title.projectName" />
					</th>-->
					<th>
						<!--以前金额--><spring:message code="pa.detailItemDifCountInfo.YIQIANJINE.b" />
					</th>
					<th>
						<!--当月金额--><spring:message code="pa.detailItemDifCountInfo.DANGYUJINE.b" />
					</th>

					<th>
						<!--差异--><spring:message code="ess.infoApply.difference" />
					</th>


				</tr>
			</thead>


			<c:forEach items="${viewResultConfirmList3Right}" var="item"
				varStatus="i">

				<tr>
					<td class="td_type">
						${i.count}
					</td>

					<td class="td_type">
						${item.EMPID}
					</td>
					<td class="td_type">
						${item.LOCAL_NAME}
					</td>
					<td class="td_type">
						${item.DEPT_NAME}
					</td>
					<td class="td_type">
						${item.POST_FAMILY_NAME}
					</td>
					<td class="td_type">
						${item.POST_GRADE_NAME}
					</td>
                    <td class="td_type">
						${item.POSITION_NAME}
					</td>
					<!--<td class="td_type">
						${item.MAIN_BUSINESS}
					</td>-->
					<td class="td_type">
						${item.EMP_TYPE_NAME}
					</td>

					<!--<td class="td_type">
						${item.ITEM_NAME}
					</td>-->
					<td class="td_type">
						${item.MONTH_PRO}
					</td>
					<td class="td_type">
						${item.MONTH_NOW}
					</td>
					<td class="td_type">
						<fmt:formatNumber value="${item.MONTH_NOW-item.MONTH_PRO}"
							pattern="#,##0" />
					</td>


				</tr>



			</c:forEach>
		</table>

	</div>



</c:if>
