<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function downloadExl(url){
		$('#searchViewPayObjStdForm').attr("action",url) ;
		$('#searchViewPayObjStdForm').attr("onsubmit",'') ;
		$('#searchViewPayObjStdForm').submit() ;
		$('#searchViewPayObjStdForm').attr("action",'/pa/workManagement/viewPayObjStd') ;
		$('#searchViewPayObjStdForm').attr("onsubmit",'return navTabSearch(this);') ;
	}
</script>
<div class="pageHeader">
	<form id="searchViewPayObjStdForm" onsubmit="return navTabSearch(this);"
		action="/pa/workManagement/viewPayObjStd" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 工资区分 --> <spring:message code="pa.viewPaPaySchedule.GONGZIQUFEN.C" />:</td>
					<td><ait:SelectSyCodeByCpnyID name="seach_SALARY_DISTIN_NO"
							selected="${SALARY_DISTIN_NO}" parentNo="14013797"
							cnpyID="${LoginUser.cpnyId}" limit="all"/></td>
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
						<a class="buttonActive"  href="/pa/workManagement/addPayObjStd" target="dialog" mask="true" 
							width="800" 
							height="320">
							<span><!-- 添加 --> <spring:message code="button.add" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" title="<spring:message code="edu.systemManager.QUEDINGSHIFOUSHANCHU.a" />" callback="doAjaxDoneWithForm" href="/pa/workManagement/doDeletePayObjStdInfo?PAY_OBJ_STD_NO={PAY_OBJ_STD_NO}" target="ajaxTodo">
							<span><!-- 删除 --> <spring:message code="button.delete" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" href="/pa/workManagement/updatePayObjStdView?PAY_OBJ_STD_NO={PAY_OBJ_STD_NO}" target="dialog" mask="true" 
							width="800" 
							height="320">
							<span><!-- 修改 --> <spring:message code="button.update" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=4')" href="#" >
							<span><!-- 导出到Excel --> <spring:message code="ess.infoApply.export_to_Excel" /></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="121">
		<thead>
			<tr>
				<th>No.</th>
				<th><!-- 工资区分 --><spring:message code="pa.viewPaPaySchedule.GONGZIQUFEN.C" /></th>
				<th><!--员工类型--><spring:message code="ess.infoApply.employee_type" /></th>
				<th><!--月份区分--><spring:message code="pa.viewPayObjectStd.YUEFENQUFEN.b" /></th>
				<th><!--日期--><spring:message code="ess.infoApply.date" /></th>
				<th><!--开始日期--><spring:message code="public.title.startDate" /></th>
				<th><!--结束日期--><spring:message code="public.title.endDate" /></th>
				<th><!--创建者--><spring:message code="sys.basic.title.createBy" /></th>
				<th><!--创建时间--><spring:message code="sys.basic.title.createDate" /></th>
				<th><!--变更者--><spring:message code="org.title.UPDATED_IP" /></th>
				<th><!--变更时间--><spring:message code="org.title.UPDATE_DATE" /></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${paPayObjStdList}" var="payObjStd"
			varStatus="i">
			<tr target="PAY_OBJ_STD_NO" rel="${payObjStd.PAY_OBJ_STD_NO}">
				<td style="text-align: center">${i.count}</td>
				<td style="text-align: center">${payObjStd.SALARY_DISTIN}</td>
				<td style="text-align: center">${payObjStd.EMP_TYPE}</td>
				<td style="text-align: center">
					<c:choose>
						<c:when test="${payObjStd.MONTH_TYPE  ==  'currentMonth'}"><!--当月--><spring:message code="pa.monthPersonCountInfoList.DANGYUE.b" /></c:when>
						<c:when test="${payObjStd.MONTH_TYPE  ==  'lastMonth'}"><!--上月--><spring:message code="ess.infoApply.LASTMONTH" /></c:when>
					</c:choose>
				</td>
				<td style="text-align: center">${payObjStd.DAY_STR}日</td>
				<td style="text-align: center">${payObjStd.START_DATE}</td>
				<td style="text-align: center">${payObjStd.END_DATE}</td>
				<td style="text-align: center">${payObjStd.CREATED_BY} ${payObjStd.CREATED_IP}</td>
				<td style="text-align: center">${payObjStd.CREATE_DATE}</td>
				<td style="text-align: center">${payObjStd.UPDATED_BY} ${payObjStd.UPDATED_IP}</td>
				<td style="text-align: center">${payObjStd.UPDATE_DATE}</td>
			</tr>
		</c:forEach>
		<c:if test="${totalCount == 0 }">
			<tr>
				<td style="text-align: center"></td>
				<td style="text-align: center"></td>
				<td style="text-align: center"></td>
				<td style="text-align: center"></td>
				<td style="text-align: center"></td>
				<td style="text-align: center"></td>
				<td style="text-align: center"></td>
				<td style="text-align: center"></td>
				<td style="text-align: center"></td>
				<td style="text-align: center"></td>
				<td style="text-align: center"></td>
			</tr>
		</c:if>
		</tbody>
	</table>
	<c:set value="/pa/workManagement/viewPayObjStd" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>