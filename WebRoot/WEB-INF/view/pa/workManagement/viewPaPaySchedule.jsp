<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function downloadExl(url){
		$('#searchViewPaPayScheduleForm').attr("action",url) ;
		$('#searchViewPaPayScheduleForm').attr("onsubmit",'') ;
		$('#searchViewPaPayScheduleForm').submit() ;
		$('#searchViewPaPayScheduleForm').attr("action",'/pa/workManagement/viewPaPaySchedule') ;
		$('#searchViewPaPayScheduleForm').attr("onsubmit",'return navTabSearch(this);') ;
	}
</script>
<div class="pageHeader">
	<form id="searchViewPaPayScheduleForm" onsubmit="return navTabSearch(this);"
		action="/pa/workManagement/viewPaPaySchedule" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 期间--> <spring:message code="pa.viewPaPaySchedule.QIJIAN.C" />:</td>
					<td><input type="text" id="seach_START_PAY_DATE"
						name="seach_START_PAY_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" value="${START_PAY_DATE}"
						size="20" />
							-<input type="text" id="seach_END_PAY_DATE"
						name="seach_END_PAY_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" value="${END_PAY_DATE}"
						size="20" /></td>
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
								<button type="submit" class="button">
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>
							</div>
						</div>
					</li>
					<li>
						<a class="buttonActive"  href="/pa/workManagement/addPaPaySchedule" target="dialog" mask="true" 
							width="800" 
							height="400">
							<span><!-- 添加 --> <spring:message code="button.add" /></span>
						</a>
					</li>
					<!-- <li>
						<a class="buttonActive" title="确定是否删除?" callback="doAjaxDoneWithForm" href="/pa/workManagement/doDeletePayScheduleInfo?PAY_SCHEDULE_NO={PAY_SCHEDULE_NO}" target="ajaxTodo">
							<span>删除</span>
						</a>
					</li> -->
					<li>
						<a class="buttonActive" href="/pa/workManagement/updatePaPayScheduleView?PAY_SCHEDULE_NO={PAY_SCHEDULE_NO}" target="dialog" mask="true" 
							width="800" 
							height="400">
							<span><!-- 修改 --> <spring:message code="button.update" /></span>
						</a>
					</li>
					<!-- <li>
						<a class="buttonActive" onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=2')" href="#" >
							<span>导出到Excel</span>
						</a>
					</li> -->
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="120">
		<thead>
			<tr>
				<th>No.</th>
				<th><!-- 支付日期 --> <spring:message code="display.pa.ecc.paydate" /></th>
				<th><!-- 工资区分 --> <spring:message code="pa.viewPaPaySchedule.GONGZIQUFEN.C" /></th>
				<th><!-- 发令开始日期--> <spring:message code="pa.viewPaPaySchedule.FALINGKAISHIRIQI.C" /></th>
				<th><!-- 发令结束日期 --> <spring:message code="pa.viewPaPaySchedule.FALINGJIESHURIQI.C" /></th>
				<th><!-- 考勤开始日期 --> <spring:message code="pa.viewPaPaySchedule.KAIQINKAISHIRIQI.C" /></th>
				<th><!-- 考勤结束日期 --> <spring:message code="pa.viewPaPaySchedule.KAIQINJIESHURIQI.C" /></th>
				<th><!-- 开放日期 --> <spring:message code="pa.viewPaPaySchedule.KAIFANGRIQI.C" /></th>
				<th><!-- 传票日期 --> <spring:message code="pa.viewPaPaySchedule.CHUANPIAORIQI.C" /></th>
				<th><!-- 更意见 --> <spring:message code="hrm.contract.Change_opinion" /></th>
				<!-- <th>确定</th>
				<th>确定者</th>
				<th>确定时间</th> 
				<th>解除者</th>
				<th>解除时间</th>-->
				<th><!-- 变更者 --> <spring:message code="hrm.empinfo.UPDATED_BY" /></th>
				<th><!-- 变更时间 --> <spring:message code="hrm.empinfo.UPDATE_DATE" /></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${paPayScheduleList}" var="paySchedule"
			varStatus="i">
			<tr target="PAY_SCHEDULE_NO" rel="${paySchedule.PAY_SCHEDULE_NO}">
				<td style="text-align: center">${i.count}</td>
				<td style="text-align: center">${paySchedule.PAY_DATE}</td>
				<td style="text-align: center">${paySchedule.SALARY_DISTIN}</td>
				<td style="text-align: center">${paySchedule.HR_START_DATE}</td>
				<td style="text-align: center">${paySchedule.HR_END_DATE}</td>
				<td style="text-align: center">${paySchedule.AR_START_DATE}</td>
				<td style="text-align: center">${paySchedule.AR_END_DATE}</td>
				<td style="text-align: center">${paySchedule.PA_OPEN_DATE}</td>
				<td style="text-align: center">${paySchedule.PA_TRANS_DATE}</td>
				<td style="text-align: center">${paySchedule.EMP_OPINION}</td>
				<%-- <td style="text-align: center"><img src="/resources/images/${paySchedule.PA_LOCK_FLAG}.gif" style="cursor: hand" />
					<c:choose>
						<c:when test="${paySchedule.PA_LOCK_FLAG == 0 }">
							<a title="是否进行确定?"  callback="doAjaxDoneWithForm" href="/pa/workManagement/confirmOrRelievePaySchedule?PAY_SCHEDULE_NO=${paySchedule.PAY_SCHEDULE_NO }&PA_LOCK_FLAG=1" target="ajaxTodo">
							<img src="/resources/images/0.gif" style="cursor: hand" /></a>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${paySchedule.ISCANRELIEVE == 1 }">
									<a title="是否进行解除?"  callback="doAjaxDoneWithForm" href="/pa/workManagement/confirmOrRelievePaySchedule?PAY_SCHEDULE_NO=${paySchedule.PAY_SCHEDULE_NO }&PA_LOCK_FLAG=0" target="ajaxTodo">
									<img src="/resources/images/1.gif" style="cursor: hand" /></a>
								</c:when>
								<c:otherwise>
									<img title="当月工资已经发放不可以解除" src="/resources/images/1.gif" style="cursor: hand" />
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<td style="text-align: center">${paySchedule.CONFIRM_BY} ${paySchedule.CONFIRM_IP}</td>
				<td style="text-align: center">${paySchedule.CONFIRM_DATE}</td>
				<td style="text-align: center">${paySchedule.RELIEVE_BY} ${paySchedule.RELIEVE_IP}</td>
				<td style="text-align: center">${paySchedule.RELIEVE_DATE}</td> --%>
				<td style="text-align: center">${paySchedule.UPDATED_BY} ${paySchedule.UPDATED_IP}</td>
				<td style="text-align: center">${paySchedule.UPDATE_DATE}</td>
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
	<c:set value="/pa/workManagement/viewPaPaySchedule" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>